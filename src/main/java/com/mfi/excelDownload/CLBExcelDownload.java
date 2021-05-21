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

import com.mfi.service.LoanAccountService;
import com.mfi.exporter.CLBExcelExporter;
import com.mfi.exporter.TRIExcelExporter;
import com.mfi.model.LoanAccount;

@Controller
public class CLBExcelDownload {
	@Autowired
	LoanAccountService repo;
	
	@Autowired
	CLBExcelExporter excel;
	
	@Autowired
	private ServletContext context;
	
	List<LoanAccount> excelList=new ArrayList<LoanAccount>(); 
	
	@RequestMapping("customerLedger")
	public String customerLedgerBalance(Model model) {
		List<LoanAccount> accounts = repo.selectAll();
		model.addAttribute("accounts",accounts);
		//System.out.println(accounts.size());
		excelList.addAll(accounts);
		return "mfi/reports/MFI_RPT_CLB";
	}
	@GetMapping(value="/customerLedgerBalanceExport")
	public void customerLedgerBalanceExport(HttpServletRequest request, HttpServletResponse response) {
		boolean isFlag=excel.CLBExport(excelList, context, request, response);
		LocalDate date = LocalDate.now();
		if(isFlag) {
			String fullPath = request.getServletContext().getRealPath("/resources/reports/"+"CLBReport"+".xls");
			filedownload(fullPath, response, "CLBReport("+date+").xls");
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
