package com.mfi.excelDownload;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mfi.exporter.AccountListingExporter;
import com.mfi.model.AccountListing;
import com.mfi.model.CurrentAccount;
import com.mfi.model.LoanAccount;
import com.mfi.model.SavingAccount;
import com.mfi.service.CurrentAccountService;
import com.mfi.service.LoanAccountService;
import com.mfi.service.ReportService;
import com.mfi.service.SavingAccountService;

@Controller
public class AccountListingDownload {
	
	@Autowired
	CurrentAccountService currentService;
	@Autowired
	SavingAccountService savingService;
	@Autowired
	LoanAccountService loanAccountService;
	@Autowired
	ReportService reportService;
	@Autowired
	AccountListingExporter excel;
	@Autowired
	private ServletContext context;
	
	List<AccountListing> excelList=new ArrayList<AccountListing>();
	AccountListing account = new AccountListing();
	
	@RequestMapping("accountListing")
	public String accountListing(Model model) {
		List<CurrentAccount> currentAccount = currentService.selectAll();
		List<SavingAccount> savingAccount = savingService.selectAll();
		List<LoanAccount> loanAccount = loanAccountService.selectAll();
		model.addAttribute("currentAccount",currentAccount);
		model.addAttribute("savingAccount",savingAccount);
		model.addAttribute("loanAccount",loanAccount);
		
		for(int i=0; i<loanAccount.size();i++) {
			account.setName(loanAccount.get(i).getCustomer().getName());
			account.setId(loanAccount.get(i).getCustomer().getCustomerCode());
			account.setCuNumber(loanAccount.get(i).getCurrentAccount().getCurrentAccountNumber());
			account.setCuAmount(loanAccount.get(i).getCurrentAccount().getBalance());
			account.setSaNumber(loanAccount.get(i).getSavingAccount().getSavingAccountNumber());
			account.setSaAmount(loanAccount.get(i).getSavingAccount().getBalance());
			account.setLoNumber(loanAccount.get(i).getLoanAccountNumber());
			account.setLoAmount(loanAccount.get(i).getLoanAmount());
			
			excelList.add(account);
		}
		
		
		//excelList.add(account);
		return "mfi/reports/MFI_RPT_ACC";
	}

	
	@PostMapping("/accountFilter")
	public String accountFilter(@Param("start")String start, @Param("end")String end, Model model) {
		
		Date startdate = Date.valueOf(start);
		Date enddate = Date.valueOf(end);
		
		List<CurrentAccount> currentAccount = reportService.currentAccountFilter(startdate, enddate);
		List<SavingAccount> savingAccount = reportService.savingAccountFilter(startdate,enddate);
		List<LoanAccount> loanAccount = reportService.loanAccountFilter(startdate,enddate);
		model.addAttribute("currentAccount",currentAccount);
		model.addAttribute("savingAccount",savingAccount);
		model.addAttribute("loanAccount",loanAccount);
		for(int i=0; i<loanAccount.size();i++) {
			account.setName(loanAccount.get(i).getCustomer().getName());
			account.setId(loanAccount.get(i).getCustomer().getCustomerCode());
			account.setCuNumber(loanAccount.get(i).getCurrentAccount().getCurrentAccountNumber());
			account.setCuAmount(loanAccount.get(i).getCurrentAccount().getBalance());
			account.setSaNumber(loanAccount.get(i).getSavingAccount().getSavingAccountNumber());
			account.setSaAmount(loanAccount.get(i).getSavingAccount().getBalance());
			account.setLoNumber(loanAccount.get(i).getLoanAccountNumber());
			account.setLoAmount(loanAccount.get(i).getLoanAmount());
			
			excelList.add(account);
		}
		
		return "mfi/reports/MFI_RPT_CLB";
	}
	@GetMapping(value="/accListingReportExport")
	public void accListingReportExport(HttpServletRequest request, HttpServletResponse response) {
		boolean isFlag=excel.accListingReportExport(excelList, context, request, response);
		LocalDate date = LocalDate.now();
		if(isFlag) {
			String fullPath = request.getServletContext().getRealPath("/resources/reports/"+"AccountListingReport"+".xls");
			filedownload(fullPath, response, "AccountListingReport("+date+").xls");
			System.out.println("Success");
		}
	
		excelList.removeAll(excelList);
		
	}
	
	
	
	private void filedownload(String fullPath, HttpServletResponse response, String fileName) {
		File file = new File(fullPath);
		System.out.println(file.exists());
		final int BUFFER_SIZE = 40976;
		if(file.exists()) {
			
			try {
				
				FileInputStream inputStream = new FileInputStream(file);
				String mimeType = context.getMimeType(fullPath);
				response.setContentType(mimeType);
				response.setHeader("content-disposition","attachment; filename="+fileName);
				OutputStream outputStream = response.getOutputStream();
				byte[] buffer = new byte[BUFFER_SIZE];
				int bytesRead = -1;
				while((bytesRead = inputStream.read(buffer))!= -1){
					outputStream.write(buffer,0,bytesRead);
				}
				inputStream.close();
				outputStream.close();
				file.delete();
				
			}catch (Exception e) {
 				e.printStackTrace();
			}
		}
		
	}
}
