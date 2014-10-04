package com.android.ocr.budget;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.format.Time;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Build;

public class SetUpFragment extends Fragment implements OnTouchListener{
	public static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/Re'corder/";
	public View regi_layout;
	public static final String ImageIDkey = "imagekey";
	public static final String DescriptionKey = "descriptionkey";
	private EditText get_person_name;
	private EditText get_budget;
	private ImageView imageView;
	private ImageView slideView;
	private TextView textView;
	private TextView nameView;
	private TextView budgetView;
	private ImageButton done_button;
	private File textFile = new File(DATA_PATH + "userinfo.txt");
	private DisplayMetrics displaymetrics = new DisplayMetrics();
	private int height;
	private int width;
	private int day;
	private int month;
	private int year;
	
	Time today = new Time(Time.getCurrentTimezone());
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_registration, container, false);
		regi_layout = view.findViewById(R.id.regi_page);
		get_person_name = new EditText(regi_layout.getContext());
		get_budget = new EditText(regi_layout.getContext());
		imageView = new ImageView(regi_layout.getContext());
		slideView = new ImageView(regi_layout.getContext());
		textView = new TextView(regi_layout.getContext());
		nameView = new TextView(regi_layout.getContext());
		budgetView = new TextView(regi_layout.getContext());
		done_button = new ImageButton(regi_layout.getContext());
	
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		height = displaymetrics.heightPixels;
		width = displaymetrics.widthPixels;
		
	    if(!(view instanceof EditText)) {
	        view.setOnTouchListener(new OnTouchListener() {				
	        	@Override
				public boolean onTouch(View v, MotionEvent event) {
	        		   final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	        		   imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
					return false;
				}
	        });
	    }
	    
	    //If a layout container, iterate over children and seed recursion.
	    if (view instanceof ViewGroup) {

	        for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
	            View innerView = ((ViewGroup) view).getChildAt(i);
	            setupUI(innerView);
	        }
	    }
		
		Bundle bundle = getArguments();
		
		if(bundle != null)
		{
			int imageID = bundle.getInt(ImageIDkey);
			
			if (imageID == 0){
				setValuesScreenOne(view, imageID);
			}
			else if (imageID == 1){
				
				setValuesScreenTwo(view, imageID);
			}
			
		}
		
		return view;
		
	}
	
	private void setValuesScreenOne(View view, int imageID)
	{
		
		LinearLayout.LayoutParams paramImage = new LinearLayout.LayoutParams
                (LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f);
		paramImage.gravity = Gravity.CENTER_VERTICAL;
		paramImage.width = width;
		paramImage.height = width;
		
		LinearLayout.LayoutParams paramsSlide = new LinearLayout.LayoutParams
                (LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);
		paramsSlide.gravity = Gravity.CENTER_VERTICAL;
		
		LinearLayout.LayoutParams paramsDesciption = new LinearLayout.LayoutParams
                (LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f);
		paramsDesciption.gravity = Gravity.CENTER_VERTICAL;
		
		
		
		
		imageView.setLayoutParams(paramImage);
		imageView.setImageResource(R.drawable.regi_logo);
		imageView.setId(R.id.logo_regi);
		imageView.setY(width/4);
		((RelativeLayout)regi_layout).addView(imageView);
		
		
//		slideView.setLayoutParams(paramsSlide);
//		slideView.setId(R.id.set_up_slide);
//		//slideView.getLayoutParams().width = regi_layout.getWidth();
//		//slideView.getLayoutParams().height = (int)((regi_layout.getWidth()/10.0)*1.7);
//		slideView.setImageResource(R.drawable.set_up_slide);
//		slideView.setY(1300);
//		((RelativeLayout)regi_layout).addView(slideView);
//		
//		textView.setLayoutParams(paramsDesciption);
//		textView.setId(R.id.page_description);
//		textView.setText("Welcome to use Re Corder");
//		textView.setY(420);
//		textView.setGravity(Gravity.CENTER);
//		textView.setTextColor(getResources().getColor(R.color.white));
//		textView.setTextSize(24);
//		((RelativeLayout)regi_layout).addView(textView);
	}
	
	private void setValuesScreenTwo(View view, int imageID){
		
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;
		
		imageView.setVisibility(View.GONE); 
		
		slideView.setVisibility(View.GONE); 
		
		textView.setVisibility(View.GONE);
		
		
		LinearLayout.LayoutParams paramsDesciption = new LinearLayout.LayoutParams
                (LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f);
		
		double screen_scale = ((double)width/1080.0);
		
		nameView.setLayoutParams(paramsDesciption);
		nameView.setId(R.id.page_description);
		nameView.setText("PLEASE ENTER YOUR NAME:");
		nameView.setY((height/32)*5);
		nameView.setGravity(Gravity.CENTER_HORIZONTAL);
		nameView.setTextColor(getResources().getColor(R.color.white));
		nameView.setTextSize(15);
		((RelativeLayout)regi_layout).addView(nameView);
		
		LinearLayout.LayoutParams paramsBudgetView = new LinearLayout.LayoutParams
                (LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f);
		
		budgetView.setLayoutParams(paramsBudgetView);
		budgetView.setId(R.id.page_description);
		budgetView.setText("PLEASE ENTER YOUR BUDGET FOR THIS MONTH:");
		budgetView.setY((height/32)*11);
		budgetView.setGravity(Gravity.CENTER_HORIZONTAL);
		budgetView.setTextColor(getResources().getColor(R.color.white));
		budgetView.setTextSize(15);
		((RelativeLayout)regi_layout).addView(budgetView);
		
		LinearLayout.LayoutParams paramsButton = 
				new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f);
		paramsButton.height = (int)((130 * width)/1080);
		paramsButton.width = (int)((400 * width)/1080);
		System.out.println("Height: " + paramsButton.height + " Width£º" + paramsButton.width);
		done_button.setId(R.id.done_but);
		done_button.setBackgroundResource(R.drawable.done_button);
		done_button.setY((int)((height/32)*19));
		done_button.setX(width/2 - (int)(((480 * width)/2160)));
		done_button.setLayoutParams(paramsButton);
		((RelativeLayout)regi_layout).addView(done_button);
		
		
		
		get_person_name.setId(R.id.get_name);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
       (LayoutParams.MATCH_PARENT, (int)(140 * screen_scale), 1f);
		params.gravity = Gravity.CENTER_HORIZONTAL;
		get_person_name.setLayoutParams(params);
		get_person_name.setTextSize(17);
		get_person_name.setGravity(Gravity.CENTER_HORIZONTAL);
		((RelativeLayout)regi_layout).addView(get_person_name);
		get_person_name.setY((height/32)*6);

		get_budget.setId(R.id.get_budget);
		LinearLayout.LayoutParams paramsBudget = new LinearLayout.LayoutParams
                (LayoutParams.MATCH_PARENT, (int)(140 * screen_scale), 1f);
		paramsBudget.gravity = Gravity.CENTER_HORIZONTAL;
		get_budget.setLayoutParams(paramsBudget);
		get_budget.setGravity(Gravity.CENTER_HORIZONTAL);
		get_budget.setTextSize(17);
		((RelativeLayout)regi_layout).addView(get_budget);
		get_budget.setY((height/32)*13);
		
		View.OnClickListener get_done = new View.OnClickListener() {
		    public void onClick(View v) {
		    	generateNoteOnSD("@NewBudget@USER NAME:" + get_person_name.getText().toString());
		    	try {
		    		  today.setToNow(); // get current time
		    		  day = Integer.valueOf(today.monthDay);
		    		  month = Integer.valueOf(today.month) + 1; // add 1 because 0 index
		    		  year = Integer.valueOf(today.year);
		    		  String day_record = Integer.toString(year) + "/" + Integer.toString(month) + "/" + Integer.toString(day) + ":";
		    		  generateNoteOnSD("-----------------------------------------------------------------------------");
		    		  generateNoteOnSD("###Set Budget");
		    		  String budgetDate = "<SETUPDATE>" + month + "/" + day;
		    		  Calendar cal = Calendar.getInstance();
		    		  int dayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		    		  String daysLeft = "<DAYSLEFT>" + (dayOfMonth - day + 1);
		    		  String new_amount = get_budget.getText().toString();
		    		  double newBudget = 0.0;
		    		  String budgetRecorder = "";
		    		  if (new_amount.length() > 3 && new_amount.charAt(new_amount.length() - 3) == '.'){
		    			  newBudget = Double.parseDouble(new_amount);
		    		  }
		    		  else{
		    			  newBudget = (double)(Integer.parseInt(new_amount));
		    		  }
		    		  double daylyBudget = (Math.round((newBudget/(dayOfMonth - day + 1)) * 100.0)) / 100.0;
		    		  String daylyBudgetString = "<DAYLYBUDGET>" + daylyBudget;
		    		  String daylyUsedBudget = "<DAYLYUSEDBUDGET>0";
		    		  budgetRecorder = "$" + newBudget;
		    		  generateNoteOnSD(budgetDate);
		    		  generateNoteOnSD(daylyBudgetString);
		    		  generateNoteOnSD(daylyUsedBudget);
		    		  generateNoteOnSD(daysLeft);
		    		  generateNoteOnSD("<CURRENT BUDGET>" + newBudget);
		    		  generateNoteOnSD("<CURRENTUSED>0");
		    		  generateNoteOnSD("\n");
		    		  endActivity();
		    	} catch (NumberFormatException e) {
		    		Toast.makeText(getActivity(), "Please Enter A Valid Number", Toast.LENGTH_LONG).show();
		    	}
		    }
		  };
		done_button.setOnClickListener(get_done);
	}
	
	public void endActivity(){
		Intent menu = new Intent(getActivity(), MenuActivity.class);
		startActivity(menu);
		getActivity().finish();
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
            Toast.makeText(regi_layout.getContext(), "Profile Updated'", Toast.LENGTH_SHORT).show();
	    }
	    catch(IOException e)
	    {
	    }
	    
	   }
	
	public void setupUI(View view) {

	    //Set up touch listener for non-text box views to hide keyboard.

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
}
