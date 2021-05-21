package com.mfi.exporter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.mfi.model.LoanAccount;
import com.mfi.model.Transaction;

@Service
@Transactional
public class CLBExcelExporter {
	public boolean CLBExport(List<LoanAccount> accounts, ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
		String filePath = context.getRealPath("/resources/reports");
		File file = new File(filePath);
		boolean exists = new File(filePath).exists();
		if (!exists) {
			new File(filePath).mkdirs();
		}

		try {
			FileOutputStream outputStream = new FileOutputStream(file + "/" + "CLBReport" + ".xls");
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet workSheet = workbook.createSheet("CLB");
			workSheet.setDefaultColumnWidth(30);

			HSSFCellStyle headerCellStyle = workbook.createCellStyle();

			HSSFRow headerRow = workSheet.createRow(0);

			HSSFCell no = headerRow.createCell(0);
			no.setCellValue("No");
			no.setCellStyle(headerCellStyle);

			HSSFCell name = headerRow.createCell(1);
			name.setCellValue("Customer Name");
			name.setCellStyle(headerCellStyle);

			HSSFCell nrc = headerRow.createCell(2);
			nrc.setCellValue("NRC");
			nrc.setCellStyle(headerCellStyle);

			HSSFCell loanAccount = headerRow.createCell(3);
			loanAccount.setCellValue("Loan Account Number");
			loanAccount.setCellStyle(headerCellStyle);

			HSSFCell loanAmount = headerRow.createCell(4);
			loanAmount.setCellValue("Loan Amount");
			loanAmount.setCellStyle(headerCellStyle);
			
			HSSFCell cuAccount = headerRow.createCell(5);
			cuAccount.setCellValue("Current Account Number");
			cuAccount.setCellStyle(headerCellStyle);

			HSSFCell cuAmount = headerRow.createCell(6);
			cuAmount.setCellValue("Current Balance");
			cuAmount.setCellStyle(headerCellStyle);
			
			HSSFCell saAccount = headerRow.createCell(7);
			saAccount.setCellValue("Saving Account Number");
			saAccount.setCellStyle(headerCellStyle);

			HSSFCell saAmount = headerRow.createCell(8);
			saAmount.setCellValue("Saving Balance");
			saAmount.setCellStyle(headerCellStyle);


			int i = 1;
			for (LoanAccount accountList : accounts) {
				HSSFRow bodyRow = workSheet.createRow(i);

				HSSFCellStyle bodyCellStyle = workbook.createCellStyle();

				HSSFCell noValue = bodyRow.createCell(0);
				noValue.setCellValue(i);
				noValue.setCellStyle(bodyCellStyle);

				HSSFCell nameValue = bodyRow.createCell(1);
				nameValue.setCellValue(accountList.getCustomer().getName());
				nameValue.setCellStyle(bodyCellStyle);

				HSSFCell nrcValue = bodyRow.createCell(2);
				nrcValue.setCellValue(accountList.getCustomer().getNrc());
				nrcValue.setCellStyle(bodyCellStyle);

				HSSFCell loanAccValue = bodyRow.createCell(3);
				
				loanAccValue.setCellValue(accountList.getLoanAccountNumber());
				loanAccValue.setCellStyle(bodyCellStyle);

				HSSFCell loanAmountValue = bodyRow.createCell(4);
				loanAmountValue.setCellValue(accountList.getLoanAmount());
				loanAmountValue.setCellStyle(bodyCellStyle);

				HSSFCell cuAccValue = bodyRow.createCell(5);
				cuAccValue.setCellValue(accountList.getCurrentAccount().getCurrentAccountNumber());
				cuAccValue.setCellStyle(bodyCellStyle);

				HSSFCell cuAmountValue = bodyRow.createCell(6);
				cuAmountValue.setCellValue(accountList.getCurrentAccount().getBalance());
				cuAmountValue.setCellStyle(bodyCellStyle);
				
				HSSFCell saAccValue = bodyRow.createCell(7);
				
				saAccValue.setCellValue(accountList.getSavingAccount().getSavingAccountNumber());
				saAccValue.setCellStyle(bodyCellStyle);

				HSSFCell saAmountValue = bodyRow.createCell(8);
				saAmountValue.setCellValue(accountList.getSavingAccount().getBalance());
				loanAmountValue.setCellStyle(bodyCellStyle);
				

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
