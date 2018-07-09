package codeBase.TestScript;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Functions {
	static int rowNum = 0;
	
	//Workbook Details
	String filePath = "../FinalAssignment/src/codeBase/Result/Result.xlsx";
	XSSFWorkbook wb = new XSSFWorkbook();
    XSSFSheet sheet = wb.createSheet("Test Result"); 
    
     
	//Get URL From Config.xlsx
	public String getUrl(String key) throws IOException
	{
		String value = null;
		
		String filePath = "../FinalAssignment/src/codeBase/Config/Config.xlsx";
		FileInputStream fis = new FileInputStream(filePath);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheet("Sheet1");
		XSSFRow row = sheet.getRow(0);
		
	       int colNum = row.getLastCellNum();
	       //System.out.println("Total Number of Columns in the  Config.xlsx is : "+colNum);
	       int rowNum = sheet.getLastRowNum()+1;
	      //System.out.println("Total Number of Rows in the Config.xlsx is : "+rowNum);
	       
	       for(int i=0;i<rowNum;i++){
		    	  row = sheet.getRow(i);
		    	  
		    	  if(row.getCell(0).getStringCellValue().equals(key)){
		    		  value = row.getCell(1).getStringCellValue();
		    		  //System.out.println("The URL is:" + value);
	               }

	       }
	       
	       fis.close();
	       
		return value;

	}
	
	
	//Get Value Of Object From ObjectRepository.xlsx
	public String getObjectRepositoryValue(String key, String type) throws IOException {
		String value = null;
		int flag = 0;
		
		String filePath = "../FinalAssignment/src/codeBase/Repository/ObjectRepository.xlsx";
		FileInputStream fis = new FileInputStream(filePath);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheet("Sheet1");
		XSSFRow row = sheet.getRow(0);
		
	       int colNum = row.getLastCellNum();
	       //System.out.println("Total Number of Columns in the  ObjectRepository.xlsx is : "+colNum);
	       int rowNum = sheet.getLastRowNum()+1;
	       //System.out.println("Total Number of Rows in the ObjectRepository.xlsx is : "+rowNum);
	       
	       for(int i=0;i<rowNum;i++){
		    	  row = sheet.getRow(i);
		    	  
		    	  if(row.getCell(0).getStringCellValue().equals(key)){
		    		  
		    		  if(row.getCell(1).getStringCellValue().equals(type)) {
		    		  value = row.getCell(2).getStringCellValue();
		    		  //System.out.println("The Locator Value is:" + value);
		    		  flag = 1;
		    		  }
	               }
		    	  
		    	  if(flag == 1) {
		    		  break;
		    	  }
		    	  
	       }
	       
	       fis.close();
		
		return value;
	}
	
	
	//Get Data From TestData.xlsx
	public String getTestData(String key) throws IOException {
		String value = null;
		int flag = 0;
		
		String filePath = "../FinalAssignment/src/codeBase/TestData/TestData.xlsx";
		FileInputStream fis = new FileInputStream(filePath);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheet("Sheet1");
		XSSFRow row = sheet.getRow(0);
		
	       int colNum = row.getLastCellNum();
	       //System.out.println("Total Number of Columns in the  TestData.xlsx is : "+colNum);
	       int rowNum = sheet.getLastRowNum()+1;
	       //System.out.println("Total Number of Rows in the TestData.xlsx is : "+rowNum);
	       
	       for(int i=0;i<rowNum;i++){
		    	  row = sheet.getRow(i);
		    	  
		    	  if(row.getCell(0).getStringCellValue().equals(key)){
		    		  value = row.getCell(1).getStringCellValue();
		    		  //System.out.println("The Test Data is:" + value);
		    		  flag = 1;
	               }
		    	  if(flag == 1) {
		    		  break;
		    	  }

	       }
	       
	       fis.close();
		
		return value;
	}
	
	
	//Write In Result.xlsx
	public void writeInResult(Object[] details) throws IOException {
 
        XSSFRow row = sheet.createRow(rowNum++); 
        XSSFCell cell = null;
        int colNum = 0;
        
        for(int i=0; i<4; i++) {
        	if(i==3) {
                cell = row.createCell(colNum++);
                cell.setCellValue((String) details[i]);
                
                //Creating The Link
                XSSFHyperlink href = wb.getCreationHelper().createHyperlink(HyperlinkType.URL);
                href.setAddress((String) details[i]);
                cell.setHyperlink(href);
                
                //UnderLine The Link & Color Is Blue
                Font font = wb.createFont();
                font.setUnderline(XSSFFont.U_DOUBLE);
                font.setColor(IndexedColors.BLUE.getIndex());
                CellStyle style = wb.createCellStyle();
                style.setFont(font);
                cell.setCellStyle(style);
        	}
        	else {
        		cell = row.createCell(colNum++);
                cell.setCellValue((String) details[i]);
        	}
        
	    FileOutputStream outputStream = new FileOutputStream(filePath);
	    wb.write(outputStream);
	    outputStream.close();
	}
	
	
	}	
	
	
}

