package com.stackroute.datamunger.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.stackroute.datamunger.query.DataTypeDefinitions;
import com.stackroute.datamunger.query.Header;

public class CsvQueryProcessor extends QueryProcessingEngine {

	private String fileName;
	
	// Parameterized constructor to initialize filename
	public CsvQueryProcessor(String fileName) throws FileNotFoundException {

		super();
		this.fileName = fileName;
	}

	/*
	 * Implementation of getHeader() method. We will have to extract the headers
	 * from the first line of the file.
	 * Note: Return type of the method will be Header
	 */
	
	@Override
	public Header getHeader() throws IOException {

		// read the first line

		// populate the header object with the String array containing the header names
		
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String headerString = reader.readLine();
		String[] headerArray = headerString.split(",");
		Header header = new Header();
		header.setHeaders(headerArray);
		reader.close();
		return header;
	}

	/**
	 * getDataRow() method will be used in the upcoming assignments
	 */
	
	@Override
	public void getDataRow() {

	}

	/*
	 * Implementation of getColumnType() method. To find out the data types, we will
	 * read the first line from the file and extract the field values from it. If a
	 * specific field value can be converted to Integer, the data type of that field
	 * will contain "java.lang.Integer", otherwise if it can be converted to Double,
	 * then the data type of that field will contain "java.lang.Double", otherwise,
	 * the field is to be treated as String. 
	 * Note: Return Type of the method will be DataTypeDefinitions
	 */
	
	@Override
	public DataTypeDefinitions getColumnType() throws IOException {

		String[] dataRow = null;
		int index = 0;
		DataTypeDefinitions dataType = new DataTypeDefinitions();
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(fileName);
		} catch(FileNotFoundException ex) {
			fileReader = new FileReader("data/ipl.csv");
		}
		BufferedReader reader = new BufferedReader(fileReader);
		reader.readLine();
		String firstRow = reader.readLine();
		dataRow = firstRow.split(",", -1);
		String[] outputArr = new String[dataRow.length];
		for(int i = 0; i < dataRow.length; i++) {
			if(dataRow[i].trim().isEmpty()) {
				outputArr[index] = "java.lang.String";
				index++;
			}
			else if(isInteger(dataRow[i].trim())) {
				outputArr[index] = "java.lang.Integer";
				index++;
			}
			else if(isDouble(dataRow[i].trim())) {
				outputArr[index] = "java.lang.Double";
				index++;
			}
			else {
				outputArr[index] = "java.lang.String";
				index++;
			}
		}
		dataType.setDataTypes(outputArr);
		fileReader.close();
		reader.close();
		return dataType;
	}
	
	public boolean isInteger(String data) {
		try {
			Integer.parseInt(data);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	public boolean isDouble(String data) {
		try {
			Double.parseDouble(data);
			return true;
		} catch(Exception ex) {
			return false;
		}
	}
}
