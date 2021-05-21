package com.mfi.excelDownload;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mfi.exporter.LoanOutstandingExcelExporter;
import com.mfi.model.LOB;
import com.mfi.model.LoanInfo;
import com.mfi.model.LoanSchedule;
import com.mfi.service.LoanInfoService;
import com.mfi.service.ReportService;
@Controller
public class LoanOutstandingExcelDownload {
	@Autowired
	ReportService reportService;
	@Autowired
	LoanInfoService loanInfoService;
	@Autowired
	LoanOutstandingExcelExporter excel;
	@Autowired
	private ServletContext context;
	
	List<LOB> excelList=new ArrayList<LOB>();
	
	@RequestMapping("/loanOutstanding")
	public String loanOutstandingBalance(Model model) {
		List<LoanInfo> loanInfo = loanInfoService.selectSatus("Progress");
		List<LoanSchedule> loan = new ArrayList<LoanSchedule>();
		List<LOB> lobList = new ArrayList<LOB>();
		LOB lob = new LOB();
		int allLoan = 0;
		List<Double> complete = new  ArrayList<Double>();
		
		for(int i=0; i<loanInfo.size(); i++) {
			
			Integer loanInfoID =  loanInfo.get(i).getLoanInfoId();
			LoanSchedule completeSchdule = reportService.completeSchdule("Complete",loanInfoID);
			loan.add(completeSchdule);
			List<LoanSchedule> findAllLoan = reportService.findAllLoan(loanInfoID);
			allLoan=findAllLoan.size();
			model.addAttribute("allLoan",allLoan);
			Double completeAmount = reportService.getSum("Complete", loanInfoID);
			complete.add(completeAmount);	
			//System.out.println(allLoan);
			lob.setLoanInfo_id(loanInfoID);
			lob.setCustomerName(completeSchdule.getLoanInfo().getCustomer().getName());
			lob.setCustomerName(completeSchdule.getLoanInfo().getCustomer().getNrc());
			lob.setPaymentTermNo(completeSchdule.getPaymentTermNo());
			lob.setAllLoan(allLoan-completeSchdule.getPaymentTermNo());
			lob.setComplete(completeAmount);
			lob.setOutstanding(completeSchdule.getLoanInfo().getLoanAmount()-completeAmount);
			//reportService.lobSave(lob);	
			excelList.add(lob);
			
		}
		model.addAttribute("completeAmount",complete);
		model.addAttribute("completeSchdule",loan);
		model.addAttribute("lob",lob);
		
		return "mfi/reports/MFI_RPT_LOB";
	}
	@GetMapping(value="/lobReportExport")
	public void lobReportExport(HttpServletRequest request, HttpServletResponse response) {
		boolean isFlag=excel.lobReportExport(excelList, context, request, response);
		LocalDate date = LocalDate.now();
		if(isFlag) {
			String fullPath = request.getServletContext().getRealPath("/resources/reports/"+"LoanOutStandingReport"+".xls");
			filedownload(fullPath, response, "LoanOutstandingReport("+date+").xls");
			
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
