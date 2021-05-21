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
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mfi.exporter.OverdueExcelExporter;
import com.mfi.model.LoanSchedule;
import com.mfi.service.RepaymentService;
import com.mfi.service.ReportService;

@Controller
public class OverdueExcelDownload {
	
	@Autowired
	ReportService reportService;
	@Autowired
	RepaymentService repaymentService;
	
	@Autowired
	OverdueExcelExporter excel;
	
	@Autowired
	private ServletContext context;
	

	List<LoanSchedule> excelList=new ArrayList<LoanSchedule>();
	
	@RequestMapping("overdueListing")
	public String overdueListing(Model model) {
		LocalDate now = LocalDate.now();
		List<LoanSchedule> LoanSchdule = repaymentService.selectByDueDate(now,"active");
		model.addAttribute("loanSchdule",LoanSchdule);
		excelList.addAll(LoanSchdule);
		//System.out.println(excelList.get(1).getLoanInfo().getCustomer().getName());
		return "mfi/reports/MFI_RPT_ODD";
	}
	
	@PostMapping("/overdueFilter")
	public String overdueFilter(@Param("start")String start, @Param("end")String end, Model model) {
		
		LocalDate startdate = LocalDate.parse(start);
		LocalDate enddate = LocalDate.parse(end);
		List<LoanSchedule> LoanSchdule = reportService.overDueFilter(startdate,enddate,"active");
		model.addAttribute("loanSchdule",LoanSchdule);
		excelList.addAll(LoanSchdule);
		return "mfi/reports/MFI_RPT_ODD";
	}
	
	@GetMapping(value="/overdueListingReportExport")
	public void customerListingReportExport(HttpServletRequest request, HttpServletResponse response) {
		boolean isFlag=excel.overdueExport(excelList, context, request, response);
		LocalDate date = LocalDate.now();
		if(isFlag) {
			String fullPath = request.getServletContext().getRealPath("/resources/reports/"+"OverdueListingReport"+".xls");
			filedownload(fullPath, response, "OverdueListingReport("+date+").xls");
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
