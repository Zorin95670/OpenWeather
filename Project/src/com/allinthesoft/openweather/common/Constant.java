package com.allinthesoft.openweather.common;

import java.io.File;
import java.io.IOException;

import com.allinthesoft.openweather.R;
import com.allinthesoft.openweather.core.exception.AndroidException;

public class Constant {

	private static String FILE_NAME = "data.json";

	private String fileDirectory;
	private boolean sqlConfigured, sqlBaseCountrySave;

	public Constant(String directory) throws AndroidException {
		setFileDirectory(directory);
		procedureInitFile();
	}

	public boolean isSqlConfigured() {
		return sqlConfigured;
	}

	public void setSqlConfigured(boolean sqlConfigured) {
		this.sqlConfigured = sqlConfigured;
	}

	public boolean isSqlBaseCountrySave() {
		return sqlBaseCountrySave;
	}

	public void setSqlBaseCountrySave(boolean sqlBaseCountrySave) {
		this.sqlBaseCountrySave = sqlBaseCountrySave;
	}

	public String getFileDirectory() {
		return fileDirectory;
	}

	public void setFileDirectory(String fileDirectory) {
		this.fileDirectory = fileDirectory;
	}
	
	/*
	 * PROCEDURE
	 */

	private void procedureInitFile() throws AndroidException {
		File file = new File(getFileDirectory(), FILE_NAME);
		if(!file.exists()){
			try{
			file.createNewFile();
			} catch (IOException e){
				throw new AndroidException(00);//R.string.error_constant_file_create);
			}
		} else {
			
		}
	}
}
