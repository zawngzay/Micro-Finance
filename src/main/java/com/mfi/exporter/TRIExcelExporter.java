package com.mfi.exporter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

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

@Service
@Transactional
public class TRIExcelExporter {
	public boolean triExport(ArrayList<String> triModel, ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
		String filePath = context.getRealPath("/resources/reports");
		File file = new File(filePath);
		boolean exists = new File(filePath).exists();
		if (!exists) {
			new File(filePath).mkdirs();
		}

		try {
			FileOutputStream outputStream = new FileOutputStream(file + "/" + "TriReport" + ".xls");
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet workSheet = workbook.createSheet("Tri");
			workSheet.setDefaultColumnWidth(30);

			HSSFCellStyle headerCellStyle = workbook.createCellStyle();

			HSSFRow headerRow = workSheet.createRow(0);

			HSSFCell accName = headerRow.createCell(0);
			accName.setCellValue("Account Name");
			accName.setCellStyle(headerCellStyle);

			HSSFCell debit = headerRow.createCell(1);
			debit.setCellValue("Assests");
			debit.setCellStyle(headerCellStyle);

			HSSFCell credit = headerRow.createCell(2);
			credit.setCellValue("Liabilities");
			credit.setCellStyle(headerCellStyle);

//			----------------------

			HSSFRow capitalRow = workSheet.createRow(1);

			HSSFCell capital = capitalRow.createCell(0);
			capital.setCellValue("Capital");

			HSSFCell capital1 = capitalRow.createCell(1);
			capital1.setCellValue("");

			HSSFCell capital2 = capitalRow.createCell(2);
			capital2.setCellValue(triModel.get(0));

//			------------
			HSSFRow cashRow = workSheet.createRow(2);

			HSSFCell cash = cashRow.createCell(0);
			cash.setCellValue("Cash");

			HSSFCell cash1 = cashRow.createCell(1);
			cash1.setCellValue(triModel.get(1));

			HSSFCell cash2 = cashRow.createCell(2);
			cash2.setCellValue("");

//			------------------
			
			HSSFRow incomeRow = workSheet.createRow(3);

			HSSFCell income = incomeRow.createCell(0);
			income.setCellValue("Income");

			HSSFCell income1 = incomeRow.createCell(1);
			income1.setCellValue("");

			HSSFCell income2 = incomeRow.createCell(2);
			income2.setCellValue(triModel.get(2));

//			---------------------
			HSSFRow loanRow = workSheet.createRow(4);

			HSSFCell loan = loanRow.createCell(0);
			loan.setCellValue("Loan");

			HSSFCell loan1 = loanRow.createCell(1);
			loan1.setCellValue(triModel.get(3));

			HSSFCell loan2 = loanRow.createCell(2);
			loan2.setCellValue("");

//			--------------
			
			HSSFRow currentRow = workSheet.createRow(5);

			HSSFCell current = currentRow.createCell(0);
			current.setCellValue("Current");

			HSSFCell current1 = currentRow.createCell(1);
			current1.setCellValue("");

			HSSFCell current2 = currentRow.createCell(2);
			current2.setCellValue(triModel.get(4));

//			---------------------
			HSSFRow savingRow = workSheet.createRow(6);

			HSSFCell saving = savingRow.createCell(0);
			saving.setCellValue("Saving");

			HSSFCell saving1 = savingRow.createCell(1);
			saving1.setCellValue("");

			HSSFCell saving2 = savingRow.createCell(2);
			saving2.setCellValue(triModel.get(5));

//			--------------
			
			HSSFRow totalRow = workSheet.createRow(7);

			HSSFCell total = totalRow.createCell(0);
			total.setCellValue("Total Balance");

			HSSFCell accestsTotal = totalRow.createCell(1);
			accestsTotal.setCellValue(triModel.get(6));

			HSSFCell liaTotal = totalRow.createCell(2);
			liaTotal.setCellValue(triModel.get(7));

	
			workbook.write(outputStream);
			outputStream.flush();
			outputStream.close();
			return true;

		} catch (Exception e) {
			return false;
		}

	}

}
