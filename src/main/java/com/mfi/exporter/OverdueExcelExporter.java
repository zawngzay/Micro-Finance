package com.mfi.exporter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import com.mfi.model.LoanSchedule;
import com.mfi.model.Transaction;

@Service
@Transactional
public class OverdueExcelExporter {
	public boolean overdueExport(List<LoanSchedule> loanSchdule, ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
		String filePath = context.getRealPath("/resources/reports");
		File file = new File(filePath);
		boolean exists = new File(filePath).exists();
		if (!exists) {
			new File(filePath).mkdirs();
		}

		try {
			FileOutputStream outputStream = new FileOutputStream(file + "/" + "OverdueListingReport" + ".xls");
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet workSheet = workbook.createSheet("Overdue List");
			workSheet.setDefaultColumnWidth(30);

			HSSFCellStyle headerCellStyle = workbook.createCellStyle();

			HSSFRow headerRow = workSheet.createRow(0);

			HSSFCell no = headerRow.createCell(0);
			no.setCellValue("No");
			no.setCellStyle(headerCellStyle);

			HSSFCell accountNumber = headerRow.createCell(1);
			accountNumber.setCellValue("Customer Name");
			accountNumber.setCellStyle(headerCellStyle);

			HSSFCell coaId = headerRow.createCell(2);
			coaId.setCellValue("Customer NRC");
			coaId.setCellStyle(headerCellStyle);

			HSSFCell date = headerRow.createCell(3);
			date.setCellValue("Loan Info ID");
			date.setCellStyle(headerCellStyle);

			HSSFCell amount = headerRow.createCell(4);
			amount.setCellValue("Payment Term No");
			amount.setCellStyle(headerCellStyle);

			HSSFCell type = headerRow.createCell(5);
			type.setCellValue("Due Date");
			type.setCellStyle(headerCellStyle);

			HSSFCell name = headerRow.createCell(6);
			name.setCellValue("Total Repayment Amount");
			name.setCellStyle(headerCellStyle);


			int i = 1;
			for (LoanSchedule loans : loanSchdule) {
				HSSFRow bodyRow = workSheet.createRow(i);

				HSSFCellStyle bodyCellStyle = workbook.createCellStyle();

				HSSFCell noValue = bodyRow.createCell(0);
				noValue.setCellValue(i);
				noValue.setCellStyle(bodyCellStyle);

				HSSFCell nameValue = bodyRow.createCell(1);
				nameValue.setCellValue(loans.getLoanInfo().getCustomer().getName());
				nameValue.setCellStyle(bodyCellStyle);

				HSSFCell nrcValue = bodyRow.createCell(2);
				nrcValue.setCellValue(loans.getLoanInfo().getCustomer().getNrc());
				nrcValue.setCellStyle(bodyCellStyle);

				HSSFCell loanInfoIdValue = bodyRow.createCell(3);
				loanInfoIdValue.setCellValue(loans.getLoanInfo().getLoanInfoId());
				loanInfoIdValue.setCellStyle(bodyCellStyle);

				HSSFCell termValue = bodyRow.createCell(4);
				termValue.setCellValue(loans.getPaymentTermNo());
				termValue.setCellStyle(bodyCellStyle);

				HSSFCell dueDateValue = bodyRow.createCell(5);
				DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
				String strDate = dateFormat.format(loans.getDueDate());
				dueDateValue.setCellValue(strDate);
				dueDateValue.setCellStyle(bodyCellStyle);

				HSSFCell amountValue = bodyRow.createCell(6);
				amountValue.setCellValue(loans.getTotalRepaymentAmount());
				amountValue.setCellStyle(bodyCellStyle);
				

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
