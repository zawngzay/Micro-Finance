package com.mfi.exporter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import com.mfi.model.LOB;
import com.mfi.model.LoanSchedule;
import com.mfi.model.Transaction;

@Service
@Transactional
public class LoanOutstandingExcelExporter {
	public boolean lobReportExport(List<LOB> loanSchedules, ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
		String filePath = context.getRealPath("/resources/reports");
		File file = new File(filePath);
		boolean exists = new File(filePath).exists();
		if (!exists) {
			new File(filePath).mkdirs();
		}

		try {
			FileOutputStream outputStream = new FileOutputStream(file + "/" + "LoanOutStandingReport" + ".xls");
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet workSheet = workbook.createSheet("LoanOutStanding");
			workSheet.setDefaultColumnWidth(30);

			HSSFCellStyle headerCellStyle = workbook.createCellStyle();

			HSSFRow headerRow = workSheet.createRow(0);

			HSSFCell no = headerRow.createCell(0);
			no.setCellValue("Loan ID");
			no.setCellStyle(headerCellStyle);
			

			HSSFCell name = headerRow.createCell(1);
			name.setCellValue("Customer Name");
			name.setCellStyle(headerCellStyle);

			HSSFCell nrc = headerRow.createCell(2);
			nrc.setCellValue("NRC");
			nrc.setCellStyle(headerCellStyle);

			HSSFCell complete = headerRow.createCell(3);
			complete.setCellValue("Complete Term No");
			complete.setCellStyle(headerCellStyle);

			HSSFCell out = headerRow.createCell(4);
			out.setCellValue("Outstanding Term No");
			out.setCellStyle(headerCellStyle);


			HSSFCell comAmount = headerRow.createCell(5);
			comAmount.setCellValue("Complete Amount");
			comAmount.setCellStyle(headerCellStyle);

			HSSFCell outAmount = headerRow.createCell(6);
			outAmount.setCellValue("Outstanding Amount");
			outAmount.setCellStyle(headerCellStyle);

			int i = 1;
			for (LOB loanSchedule : loanSchedules) {
				HSSFRow bodyRow = workSheet.createRow(i);

				HSSFCellStyle bodyCellStyle = workbook.createCellStyle();

				HSSFCell noValue = bodyRow.createCell(0);
				noValue.setCellValue(loanSchedule.getLoanInfo_id());
				noValue.setCellStyle(bodyCellStyle);

				HSSFCell nameValue = bodyRow.createCell(1);
				nameValue.setCellValue(loanSchedule.getCustomerName());
				nameValue.setCellStyle(bodyCellStyle);

				HSSFCell nrcValue = bodyRow.createCell(2);
				nrcValue.setCellValue(loanSchedule.getNrc());
				nrcValue.setCellStyle(bodyCellStyle);

				HSSFCell completeValue = bodyRow.createCell(3);
				completeValue.setCellValue(loanSchedule.getPaymentTermNo());
				completeValue.setCellStyle(bodyCellStyle);

				HSSFCell outValue = bodyRow.createCell(4);
				outValue.setCellValue(loanSchedule.getAllLoan());
				outValue.setCellStyle(bodyCellStyle);

				HSSFCell comAmountValue = bodyRow.createCell(5);
				comAmountValue.setCellValue(loanSchedule.getComplete());
				comAmountValue.setCellStyle(bodyCellStyle);

				HSSFCell outAmountValue = bodyRow.createCell(6);
				outAmountValue.setCellValue(loanSchedule.getOutstanding());
				outAmountValue.setCellStyle(bodyCellStyle);

				

				i++;
			}

			workbook.write(outputStream);
			outputStream.flush();
			outputStream.close();
			return true;

		} catch (Exception e) {
			return false;
		}

	}

}
