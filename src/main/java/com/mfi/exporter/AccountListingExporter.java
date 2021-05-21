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

import com.mfi.model.AccountListing;
import com.mfi.model.Customer;

@Service
@Transactional
public class AccountListingExporter {
	public boolean accListingReportExport(List<AccountListing> accounts, ServletContext context,
			HttpServletRequest request, HttpServletResponse response) {
		String filePath = context.getRealPath("/resources/reports");
		File file = new File(filePath);
		boolean exists = new File(filePath).exists();
		if (!exists) {
			new File(filePath).mkdirs();
		}

		try {
			FileOutputStream outputStream = new FileOutputStream(file + "/" + "AccountListingReport" + ".xls");
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet workSheet = workbook.createSheet("AccountsList");
			workSheet.setDefaultColumnWidth(30);

			HSSFCellStyle headerCellStyle = workbook.createCellStyle();

			HSSFRow headerRow = workSheet.createRow(0);

			HSSFCell name = headerRow.createCell(0);
			name.setCellValue("Customer Name");
			name.setCellStyle(headerCellStyle);

			HSSFCell customerCode = headerRow.createCell(1);
			customerCode.setCellValue("Custome id");
			customerCode.setCellStyle(headerCellStyle);

			HSSFCell cuNumber = headerRow.createCell(2);
			cuNumber.setCellValue("Current Account Number");
			cuNumber.setCellStyle(headerCellStyle);

			HSSFCell cuAmount = headerRow.createCell(3);
			cuAmount.setCellValue("Current Account Balance");
			cuAmount.setCellStyle(headerCellStyle);

			HSSFCell saNumber = headerRow.createCell(4);
			saNumber.setCellValue("Saving Account Number");
			saNumber.setCellStyle(headerCellStyle);

			HSSFCell saAmount = headerRow.createCell(5);
			saAmount.setCellValue("Saving Amount");
			saAmount.setCellStyle(headerCellStyle);

			HSSFCell loNumber = headerRow.createCell(6);
			loNumber.setCellValue("Loan Account Number");
			loNumber.setCellStyle(headerCellStyle);

			HSSFCell loAmount = headerRow.createCell(7);
			loAmount.setCellValue("Loan Amount");
			loAmount.setCellStyle(headerCellStyle);

			int i = 1;
			for (AccountListing account : accounts) {
				HSSFRow bodyRow = workSheet.createRow(i);

				HSSFCellStyle bodyCellStyle = workbook.createCellStyle();

				HSSFCell nameValue = bodyRow.createCell(0);
				nameValue.setCellValue(account.getName());
				nameValue.setCellStyle(bodyCellStyle);

				HSSFCell idValue = bodyRow.createCell(1);
				idValue.setCellValue(account.getId());
				idValue.setCellStyle(bodyCellStyle);

				HSSFCell cuValue = bodyRow.createCell(2);
				cuValue.setCellValue(account.getCuNumber());
				cuValue.setCellStyle(bodyCellStyle);

				HSSFCell cuAmountValue = bodyRow.createCell(3);
				cuAmountValue.setCellValue(account.getCuAmount());
				cuAmountValue.setCellStyle(bodyCellStyle);

				HSSFCell saValue = bodyRow.createCell(4);
				saValue.setCellValue(account.getSaNumber());
				saValue.setCellStyle(bodyCellStyle);

				HSSFCell saAmountValue = bodyRow.createCell(5);
				saAmountValue.setCellValue(account.getSaAmount());
				saAmountValue.setCellStyle(bodyCellStyle);

				HSSFCell loValue = bodyRow.createCell(6);
				loValue.setCellValue(account.getLoNumber());
				loValue.setCellStyle(bodyCellStyle);

				HSSFCell loAmountValue = bodyRow.createCell(7);
				loAmountValue.setCellValue(account.getLoAmount());
				loAmountValue.setCellStyle(bodyCellStyle);

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
