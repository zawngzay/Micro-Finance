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

import com.mfi.model.Customer;

@Service
@Transactional
public class CustomerListingExcelExporter {
	
	
	public boolean customerListingExport(List<Customer> customers, ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
		String filePath = context.getRealPath("/resources/reports");
		File file = new File(filePath);
		boolean exists = new File(filePath).exists();
		if (!exists) {
			new File(filePath).mkdirs();
		}

		try {
			FileOutputStream outputStream = new FileOutputStream(file + "/" + "CustomerListingReport" + ".xls");
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet workSheet = workbook.createSheet("Customers");
			workSheet.setDefaultColumnWidth(30);

			HSSFCellStyle headerCellStyle = workbook.createCellStyle();

			HSSFRow headerRow = workSheet.createRow(0);

			HSSFCell no = headerRow.createCell(0);
			no.setCellValue("No");
			no.setCellStyle(headerCellStyle);

			HSSFCell customerCode = headerRow.createCell(1);
			customerCode.setCellValue("Custome Code");
			customerCode.setCellStyle(headerCellStyle);
			
			HSSFCell customerName = headerRow.createCell(2);
			customerName.setCellValue("Custome Name");
			customerName.setCellStyle(headerCellStyle);

			HSSFCell nrc = headerRow.createCell(3);
			nrc.setCellValue("Customer NRC");
			nrc.setCellStyle(headerCellStyle);

			HSSFCell phone = headerRow.createCell(4);
			phone.setCellValue("Phone");
			phone.setCellStyle(headerCellStyle);

			HSSFCell email = headerRow.createCell(5);
			email.setCellValue("Email");
			email.setCellStyle(headerCellStyle);

			HSSFCell dob = headerRow.createCell(6);
			dob.setCellValue("Date Of Birth");
			dob.setCellStyle(headerCellStyle);

			HSSFCell gender = headerRow.createCell(7);
			gender.setCellValue("Gender");
			gender.setCellStyle(headerCellStyle);

			HSSFCell address = headerRow.createCell(8);
			address.setCellValue("Address");
			address.setCellStyle(headerCellStyle);
			
			HSSFCell job = headerRow.createCell(9);
			job.setCellValue("Current Job");
			job.setCellStyle(headerCellStyle);

			HSSFCell position = headerRow.createCell(10);
			position.setCellValue("Position");
			position.setCellStyle(headerCellStyle);

			HSSFCell company = headerRow.createCell(11);
			company.setCellValue("Company Name");
			company.setCellStyle(headerCellStyle);

			HSSFCell income = headerRow.createCell(12);
			income.setCellValue("Monthly Income");
			income.setCellStyle(headerCellStyle);

			HSSFCell status = headerRow.createCell(13);
			status.setCellValue("Status");
			status.setCellStyle(headerCellStyle);


			int i = 1;
			for (Customer customer : customers) {
				HSSFRow bodyRow = workSheet.createRow(i);

				HSSFCellStyle bodyCellStyle = workbook.createCellStyle();

				HSSFCell noValue = bodyRow.createCell(0);
				noValue.setCellValue(i);
				noValue.setCellStyle(bodyCellStyle);

				HSSFCell codeValue = bodyRow.createCell(1);
				codeValue.setCellValue(customer.getCustomerCode());
				codeValue.setCellStyle(bodyCellStyle);

				HSSFCell nameValue = bodyRow.createCell(2);
				nameValue.setCellValue(customer.getName());
				nameValue.setCellStyle(bodyCellStyle);

				HSSFCell nrcValue = bodyRow.createCell(3);
				nrcValue.setCellValue(customer.getNrc());
				nrcValue.setCellStyle(bodyCellStyle);

				HSSFCell phoneValue = bodyRow.createCell(4);
				phoneValue.setCellValue(customer.getPhone());
				phoneValue.setCellStyle(bodyCellStyle);

				HSSFCell emailValue = bodyRow.createCell(5);
				emailValue.setCellValue(customer.getEmail());
				emailValue.setCellStyle(bodyCellStyle);

				HSSFCell dobValue = bodyRow.createCell(6);
				DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
				String strDate = dateFormat.format(customer.getDob());
				dobValue.setCellValue(strDate);
				dobValue.setCellStyle(bodyCellStyle);

				HSSFCell genderValue = bodyRow.createCell(7);
				genderValue.setCellValue(customer.getGender());
				genderValue.setCellStyle(bodyCellStyle);
				
				HSSFCell addressValue = bodyRow.createCell(8);
				addressValue.setCellValue(customer.getAddress());
				addressValue.setCellStyle(bodyCellStyle);

				HSSFCell jobValue = bodyRow.createCell(9);
				jobValue.setCellValue(customer.getCurrentJob());
				jobValue.setCellStyle(bodyCellStyle);

				HSSFCell positionValue = bodyRow.createCell(10);
				positionValue.setCellValue(customer.getPositon());
				positionValue.setCellStyle(bodyCellStyle);

				HSSFCell companyValue = bodyRow.createCell(11);
				companyValue.setCellValue(customer.getCompanyName());
				companyValue.setCellStyle(bodyCellStyle);

				HSSFCell incomeValue = bodyRow.createCell(12);
				incomeValue.setCellValue(customer.getMonthlyIncome());
				incomeValue.setCellStyle(bodyCellStyle);

				HSSFCell statusValue = bodyRow.createCell(13);
				statusValue.setCellValue(customer.isDelStatus());
				statusValue.setCellStyle(bodyCellStyle);


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
