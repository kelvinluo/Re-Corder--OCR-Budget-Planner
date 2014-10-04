package com.android.ocr.budget;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import android.os.Environment;
import android.text.format.Time;
import android.widget.Toast;

public class UserInfoSearch {
	public static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/Re'corder/";
	private File textFile = new File(DATA_PATH + "userinfo.txt");
	
	public double getCurrentBalance(){
		String preAmount = "";
		double previousTotal = 0.0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(textFile));
			String line;
			while ((line = br.readLine()) != null){
				if (line.contains("<CURRENTUSED>")){
						preAmount = line.substring(13);
				}
		    }
			br.close();
		}
		catch (IOException e) {
			
		}
		previousTotal = Double.parseDouble(preAmount);
		return previousTotal;
	}
	
	
	public double getTotalBudget(){
		String preAmount = "";
		double previousTotal = 0.0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(textFile));
			String line;
			while ((line = br.readLine()) != null){
				if (line.contains("<CURRENT BUDGET>")){
					preAmount = line.substring(16);
					break;
				}
		    }
			br.close();
		}
		catch (IOException e) {
			
		}
		
		previousTotal = Double.parseDouble(preAmount);
		return previousTotal;
	}
	
	public int getDaysLeft(){
		String preAmount = "";
		double previousTotal = 0.0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(textFile));
			String line;
			while ((line = br.readLine()) != null){
				if (line.contains("<DAYSLEFT>")){
					preAmount = line.substring(10);
					break;
				}
		    }
			br.close();
		}
		catch (IOException e) {
			
		}
		
		previousTotal = Integer.parseInt(preAmount);
		return (int)previousTotal;
	}
	
	public String getSetUpDay(){
		String preAmount = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(textFile));
			String line;
			while ((line = br.readLine()) != null){
				if (line.contains("<SETUPDATE>")){
					preAmount = line.substring(11);
					break;
				}
		    }
			br.close();
		}
		catch (IOException e) {
			
		}
		return preAmount;
	}
	
	public double getDaylyBudget(){
		String preAmount = "";
		double previousTotal = 0.0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(textFile));
			String line;
			while ((line = br.readLine()) != null){
				if (line.contains("<DAYLYBUDGET>")){
					preAmount = line.substring(13);
					break;
				}
		    }
			br.close();
		}
		catch (IOException e) {
			
		}
		
		previousTotal = Double.parseDouble(preAmount);
		return previousTotal;
	}
	
	public double getDaylyUsedBudget(){
		String preAmount = "";
		double previousTotal = 0.0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(textFile));
			String line;
			while ((line = br.readLine()) != null){
				if (line.contains("<DAYLYUSEDBUDGET>")){
					preAmount = line.substring(17);
					break;
				}
		    }
			br.close();
		}
		catch (IOException e) {
			
		}
		
		previousTotal = Double.parseDouble(preAmount);
		return previousTotal;
	}
	
	public void resetDaylyBudget(){
		try{
			File tempFile = new File(DATA_PATH + "myTempFile.txt");
			BufferedReader reader = new BufferedReader(new FileReader(textFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			String currentLine;
			
			while((currentLine = reader.readLine()) != null) {
			    // trim newline when comparing with lineToRemove
				
				if (currentLine.contains("<DAYLYUSEDBUDGET>"))
				    {
						writer.write("<DAYLYUSEDBUDGET>0\r\n");
				    }
				else if (currentLine.contains("<DAYLYBUDGET>"))
					{
					String newDaylyBudget = "";
					if ((double)((getTotalBudget() - getCurrentBalance())/getDaysLeft()) < 0.0)
					{
						newDaylyBudget = "0.0";
					}
					else{
						newDaylyBudget = Double.toString((getTotalBudget() - getCurrentBalance())/getDaysLeft());
					}
						
						writer.write("<DAYLYBUDGET>" + newDaylyBudget + "\r\n");
					}
				else{
						writer.write(currentLine + "\r\n");
					}
					}
			
			boolean successful = tempFile.renameTo(textFile);
			writer.close();
			reader.close();
		}catch(IOException e){

		}
	}
	
	public void resetDay(){
		  Calendar cal = Calendar.getInstance();
		  int dayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int setUpDay = Integer.parseInt(getSetUpDay().substring(getSetUpDay().indexOf('/') + 1));
		try{
			File tempFile = new File(DATA_PATH + "myTempFile.txt");
			BufferedReader reader = new BufferedReader(new FileReader(textFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			String currentLine;

			while((currentLine = reader.readLine()) != null) {
			    // trim newline when comparing with lineToRemove
				if (currentLine.contains("<DAYSLEFT>"))
				    {
						writer.write("<DAYSLEFT>" + Integer.toString((dayOfMonth - setUpDay)) + "\r\n");
				    }
				else{
						writer.write(currentLine + "\r\n");
					}
				}
			
			boolean successful = tempFile.renameTo(textFile);
			writer.close();
			reader.close();
		}catch(IOException e){
			
		}
	}
	
}
