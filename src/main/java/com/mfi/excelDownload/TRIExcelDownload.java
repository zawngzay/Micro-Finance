package com.mfi.excelDownload;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mfi.exporter.TRIExcelExporter;
import com.mfi.service.CoaAccountService;
import com.mfi.service.CurrentAccountService;
import com.mfi.service.LoanAccountService;
import com.mfi.service.SavingAccountService;

@Controller
public class TRIExcelDownload {

	@Autowired
	private CoaAccountService coaService;

	@Autowired
	private CurrentAccountService cuService;

	@Autowired
	private SavingAccountService saService;

	@Autowired
	private LoanAccountService laService;

	@Autowired
	TRIExcelExporter excel;

	@Autowired
	private ServletContext context;

	ArrayList<String> excelList = new ArrayList<String>();

	@RequestMapping("/triBalance")
	public String TriBalance(Model model) {
		DecimalFormat df = new DecimalFormat("#");
		df.setMaximumFractionDigits(8);

		String capitalAmount = df.format(coaService.selectAmount("Capital"));
		String cashAmount = df.format(coaService.selectAmount("Cash"));
		String incomeAmount = df.format(coaService.selectAmount("Income"));
		String loanAmount = df.format(coaService.selectAmount("Loan Account"));
		String currentAmount = df.format(coaService.selectAmount("Current Account"));
		String savingAmount = df.format(coaService.selectAmount("Saving Account"));
		String AssestsTotalBalance = df
				.format(coaService.selectAmount("Cash") + coaService.selectAmount("Loan Account"));
		String LiabilitiesTotalBalance = df
				.format(coaService.selectAmount("Capital") + coaService.selectAmount("Income")
						+ coaService.selectAmount("Saving Account") + coaService.selectAmount("Current Account"));

		model.addAttribute("capitalAmount", capitalAmount);
		model.addAttribute("cashAmount", cashAmount);
		model.addAttribute("incomeAmount", incomeAmount);
		model.addAttribute("loanAmount", loanAmount);
		model.addAttribute("currentAmount", currentAmount);
		model.addAttribute("savingAmount", savingAmount);
		model.addAttribute("AssestsTotalBalance", AssestsTotalBalance);
		model.addAttribute("LiabilitiesTotalBalance", LiabilitiesTotalBalance);
		excelList.add(capitalAmount);
		excelList.add(cashAmount);
		excelList.add(incomeAmount);
		excelList.add(loanAmount);
		excelList.add(currentAmount);
		excelList.add(savingAmount);
		excelList.add(AssestsTotalBalance);
		excelList.add(LiabilitiesTotalBalance);

		return "mfi/reports/MFI_RPT_TRI";

	}

	@GetMapping(value = "/TRIReportExport")
	public void triReportExport(HttpServletRequest request, HttpServletResponse response) {
		boolean isFlag = excel.triExport(excelList, context, request, response);
		LocalDate date = LocalDate.now();
		if (isFlag) {
			String fullPath = request.getServletContext().getRealPath("/resources/reports/" + "TriReport" + ".xls");
			filedownload(fullPath, response, "TriReport(" + date + ").xls");
		}

		excelList.removeAll(excelList);

	}

	private void filedownload(String fullPath, HttpServletResponse response, String fileName) {
		File file = new File(fullPath);
		System.out.println(file.exists());
		final int BUFFER_SIZE = 40976;
		if (file.exists()) {

			try {

				FileInputStream inputStream = new FileInputStream(file);
				String mimeType = context.getMimeType(fullPath);
				response.setContentType(mimeType);
				response.setHeader("content-disposition", "attachment; filename=" + fileName);
				OutputStream outputStream = response.getOutputStream();
				byte[] buffer = new byte[BUFFER_SIZE];
				int bytesRead = -1;
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
				inputStream.close();
				outputStream.close();
				file.delete();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
