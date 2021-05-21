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

import com.mfi.model.CurrentAccount;
import com.mfi.model.LoanAccount;
import com.mfi.model.SavingAccount;
import com.mfi.model.Transaction;

@Service
@Transactional
public class TransactionExcelExporter {
	public boolean transactionExport(List<Transaction> trans, ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
		
		String filePath = context.getRealPath("/resources/reports");
		File file = new File(filePath);
		boolean exists = new File(filePath).exists();
		if (!exists) {
			new File(filePath).mkdirs();
		}

		try {
			FileOutputStream outputStream = new FileOutputStream(file + "/" + "TransactionReport" + ".xls");
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet workSheet = workbook.createSheet("Trans");
			workSheet.setDefaultColumnWidth(30);

			HSSFCellStyle headerCellStyle = workbook.createCellStyle();

			HSSFRow headerRow = workSheet.createRow(0);

			HSSFCell no = headerRow.createCell(0);
			no.setCellValue("No");
			no.setCellStyle(headerCellStyle);

			HSSFCell accountNumber = headerRow.createCell(1);
			accountNumber.setCellValue("Account Number");
			accountNumber.setCellStyle(headerCellStyle);

			HSSFCell coaId = headerRow.createCell(2);
			coaId.setCellValue("COA ID");
			coaId.setCellStyle(headerCellStyle);

			HSSFCell date = headerRow.createCell(3);
			date.setCellValue("Date");
			date.setCellStyle(headerCellStyle);

			HSSFCell amount = headerRow.createCell(4);
			amount.setCellValue("Amount");
			amount.setCellStyle(headerCellStyle);

			HSSFCell type = headerRow.createCell(5);
			type.setCellValue("Transaction Type");
			type.setCellStyle(headerCellStyle);

			HSSFCell name = headerRow.createCell(6);
			name.setCellValue("Account Name");
			name.setCellStyle(headerCellStyle);

			HSSFCell narration = headerRow.createCell(7);
			narration.setCellValue("Narrantion");
			narration.setCellStyle(headerCellStyle);

			int i = 1;
			for (Transaction transaction : trans) {
				HSSFRow bodyRow = workSheet.createRow(i);

				HSSFCellStyle bodyCellStyle = workbook.createCellStyle();

				HSSFCell noValue = bodyRow.createCell(0);
				noValue.setCellValue(i);
				noValue.setCellStyle(bodyCellStyle);

				HSSFCell accountNumberValue = bodyRow.createCell(1);
				accountNumberValue.setCellValue(transaction.getAccountNumber());
				accountNumberValue.setCellStyle(bodyCellStyle);

				HSSFCell coaIdValue = bodyRow.createCell(2);
				coaIdValue.setCellValue(transaction.getCoaId());
				coaIdValue.setCellStyle(bodyCellStyle);

				HSSFCell dateValue = bodyRow.createCell(3);
				DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
				String strDate = dateFormat.format(transaction.getCreatedDate());
				dateValue.setCellValue(strDate);
				dateValue.setCellStyle(bodyCellStyle);

				HSSFCell amountValue = bodyRow.createCell(4);
				amountValue.setCellValue(transaction.getAmount());
				amountValue.setCellStyle(bodyCellStyle);

				HSSFCell typeValue = bodyRow.createCell(5);
				typeValue.setCellValue(transaction.getTransactionType());
				typeValue.setCellStyle(bodyCellStyle);

				HSSFCell nameValue = bodyRow.createCell(6);
				nameValue.setCellValue(transaction.getAccountName());
				nameValue.setCellStyle(bodyCellStyle);

				HSSFCell narrationValue = bodyRow.createCell(7);
				narrationValue.setCellValue(transaction.getNarration());
				narrationValue.setCellStyle(bodyCellStyle);

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
