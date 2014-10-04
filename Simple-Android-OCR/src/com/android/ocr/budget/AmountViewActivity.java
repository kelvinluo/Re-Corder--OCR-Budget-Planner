package com.android.ocr.budget;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class AmountViewActivity extends Activity {
	
	private DisplayMetrics displaymetrics = new DisplayMetrics();
	private int height;
	private int width;
	
	public static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/Re'corder/";
	private double total;
	private String directoryName;
	private String time;
	private File textFile = new File(DATA_PATH + "userinfo.txt");
	private ArrayList<String> lines = new ArrayList<String>();
	private double previousTotal = 0.00;
	private View view;
	private FrameLayout amount_layout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_amount_view);
		
	    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		height = displaymetrics.heightPixels;
		width = displaymetrics.widthPixels;
		
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		getActionBar().setCustomView(R.layout.abs_layout);
		
		TextView menuTitle = (TextView) findViewById(R.id.menu_title);
		Typeface tfFreshman = Typeface.createFromAsset(getAssets(),
		        "Freshman.ttf");
		Typeface tfChapaza = Typeface.createFromAsset(getAssets(),
		        "Chapaza.ttf");
		
		Typeface tfAmericanCaptain = Typeface.createFromAsset(getAssets(),
		        "American_Captain.ttf");
		
		menuTitle.setTypeface(tfFreshman);
		
		Bundle b = getIntent().getExtras();
		total = b.getDouble("total");
		directoryName = b.getString("photoDirectory");
		time = b.getString("date");
		
		
        amount_layout  = (FrameLayout)findViewById(R.id.container);
        amount_layout.setBackgroundResource(R.drawable.amoung_background);
        amount_layout.getLayoutParams().height = height;
        amount_layout.getLayoutParams().width = (int)(height*(double)(3000/1920));
        
		ImageButton retakeBut = (ImageButton) findViewById(R.id.retake_button);
		retakeBut.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
				File badPhoto = new File(directoryName);
				boolean deleted = badPhoto.delete();
		    	goBackToPhoto();
		    }
		});
		
		EditText new_total = (EditText)findViewById(R.id.new_amount);
    	new_total.getLayoutParams().width = (int)(width/1.5);
    	new_total.getLayoutParams().height = (int)(height/14);
		new_total.setGravity(Gravity.CENTER_HORIZONTAL);
		new_total.setText(Double.toString(total));

		
		ImageButton doneBut = (ImageButton) findViewById(R.id.done_button);
		doneBut.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	EditText new_total = (EditText)findViewById(R.id.new_amount);
		    	String new_amount = new_total.getText().toString();
		    	try{
		    		if (new_amount.length() > 3 && new_amount.charAt(new_amount.length() - 3) == '.'){
		    			total = Double.parseDouble(new_amount);
		    		}
		    		else{
		    			total = (double)(Integer.parseInt(new_amount));
		    		}
		    		enterNewValue();
		    	}catch(NumberFormatException e){
		    		Toast.makeText(getApplicationContext(), "Please Enter A Valid Number", Toast.LENGTH_LONG).show();
		    	}
		    }
		});
		
		view = getWindow().getDecorView().findViewById(android.R.id.content);
		
	    if(!(view instanceof EditText)) {
	        view.setOnTouchListener(new OnTouchListener() {				
	        	@Override
				public boolean onTouch(View v, MotionEvent event) {
	        		   final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	        		   imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
					return false;
				}
	        });
	    }
		
	}
	
	protected View getView() {
		
		return view;
	}

	public void goBackToPhoto(){
    	File previousPhoto = new File(directoryName);
    	boolean deleted = previousPhoto.delete();
        Intent ocrActivity = new Intent(this, ReceiptOCRBudgetActivity.class);
        startActivityForResult(ocrActivity, 1);	
        finish();
	}
	
	public void enterNewValue(){
		UserInfoSearch userInfo = new UserInfoSearch();
		previousTotal = userInfo.getCurrentBalance();
		previousTotal += total;
		Double dailyTotal = userInfo.getDaylyUsedBudget();
		dailyTotal += total;
		
		//////////////////////////////////////////
		
		try{
			File tempFile = new File(DATA_PATH + "myTempFile.txt");
			BufferedReader reader = new BufferedReader(new FileReader(textFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			String currentLine;

			while((currentLine = reader.readLine()) != null) {
			    // trim newline when comparing with lineToRemove
				
				if (currentLine.contains("<CURRENTUSED>"))
				    {
						writer.write("<CURRENTUSED>" + previousTotal + "\r\n");
				    }
				else if (currentLine.contains("<DAYLYUSEDBUDGET>"))
			    {
					writer.write("<DAYLYUSEDBUDGET>" + dailyTotal + "\r\n");
			    }
				else{
						writer.write(currentLine + "\r\n");
					}
				}
			
			boolean successful = tempFile.renameTo(textFile);
			writer.close();
			reader.close();
		}catch(IOException e){
			Toast.makeText(this, "Fail to Write File", Toast.LENGTH_SHORT).show();
		}

		
		////////////////////////////////////////////////////////////////////////////
		
		generateNoteOnSD("@New Entry");
		generateNoteOnSD("Date: " + time);
		generateNoteOnSD("Amount: $" + total);
		generateNoteOnSD("Photo Directory: @" + directoryName);
		Intent menu = new Intent(this, MenuActivity.class);
		startActivity(menu);
		finish();
	}
	
	public void generateNoteOnSD(String sBody){
	    try
	    {
	        if (!textFile.exists()){
	        	textFile.createNewFile();
	        }
			FileWriter fw = new FileWriter(textFile.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(sBody + "\r\n");
			bw.close();
            Toast.makeText(this, "Profile Updated'", Toast.LENGTH_SHORT).show();
	    }
	    catch(IOException e)
	    {
	    }
	    
	   }
}
