package com.android.ocr.budget;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.Build;

public class LoadingScreen extends Activity {
	
	public static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/Re'corder/";
	private DisplayMetrics displaymetrics = new DisplayMetrics();
	private int height;
	private int width;
	private File textFile = new File(DATA_PATH + "userinfo.txt");
	private int setUpDay = 0;
	private int currentDay = 0;
	private FrameLayout loading_layout;
	private UserInfoSearch userInfo = new UserInfoSearch();
	private AnimationDrawable animRotation;
	public ImageView loading_animation;
	public ImageView loading_title;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading_screen);
	    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		height = displaymetrics.heightPixels;
		width = displaymetrics.widthPixels;
		
//		final Animation animRotation = AnimationUtils.loadAnimation(this, R.anim.anim_loading);
//		
//		loading_animation = (ImageView)findViewById(R.id.loading_anim);
//		loading_animation.startAnimation(animRotation);
//		
		
		
		loading_layout  = (FrameLayout)findViewById(R.id.container);
		loading_layout.setBackgroundResource(R.drawable.wallpaper);
		loading_layout.getLayoutParams().height = height;
		loading_layout.getLayoutParams().width = ((int)(1920*1920/height));
		loading_layout.setX(-(loading_layout.getLayoutParams().width/3)*2);
		
		loading_title = (ImageView)findViewById(R.id.loading_tt);
		loading_title.setBackgroundResource(R.drawable.loading_title);
		
		loading_title.getLayoutParams().width = (int)((width*31/32));
		loading_title.getLayoutParams().height = (int)((((width*31/32))*3/10));

		loading_title.setX((float)(width/31));
		loading_title.setY((float)((height/5)*2 + (int)((width/6) + (int)((((width/32)*31)/20)*3))));
		
//		final AnimationDrawable loading_anim_drawable
//		 =    (AnimationDrawable)loading_animation.getDrawable();
//		
//		loading_anim_drawable.setCallback(loading_animation);
//		loading_anim_drawable.setVisible(true, true);
//		
//		loading_anim_drawable.start();
		
		 Thread welcomeThread = new Thread() {

	            @Override
	            public void run() {
	                try {
	                    super.run();
	                    sleep(3000);
	                } catch (Exception e) {

	                } finally {

	    		    	if (textFile.exists() && !checkNewMonth()){
	    		    		boolean deleted = textFile.delete();
	    		    	}
	    		    	else if (textFile.exists() && !checkNewDay()){
	    		    		userInfo.resetDaylyBudget();
	    		    		userInfo.resetDay();
	    		    	}
	    		    	
	    		        if (!textFile.exists()){
	    		        	startRegistration();
	    		        }
	    		        else{
	    		        	startMenu();
	    		        }
	                }
	            }
	        };
	        welcomeThread.start();
		}

	public void startRegistration(){
		Intent registration = new Intent(this, RegistrationActivity.class);
		this.finish();
		startActivity(registration);
	}
	
	public void startMenu(){
		Intent menu = new Intent(this, MenuActivity.class);
		this.finish();
		startActivity(menu);
	}
	
	public boolean checkNewMonth(){
			String day = "";
			try {
				BufferedReader br = new BufferedReader(new FileReader(textFile));
				String line;
				while ((line = br.readLine()) != null){
					if (line.contains("<DAYSLEFT>")){
						day = line.substring(10);
						break;
					}
			    }
				br.close();
			}
			catch (IOException e) {
				Toast.makeText(this, "Profile Not Found", Toast.LENGTH_SHORT).show();
			}
			int days = Integer.parseInt(day);
			
			String month = "";
			try {
				BufferedReader br = new BufferedReader(new FileReader(textFile));
				String line;	
				while ((line = br.readLine()) != null){
					if (line.contains("<SETUPDATE>")){
						month = line.substring(11, 13);
						break;
					}
			    }
				br.close();
			}
			catch (IOException e) {
				Toast.makeText(this, "Profile Not Found", Toast.LENGTH_SHORT).show();
			}
			month = month.replace("/", "");
			int setUpMonth = Integer.parseInt(month);
			Time today = new Time(Time.getCurrentTimezone());
			today.setToNow(); // get current time
			int currentDay = Integer.valueOf(today.monthDay);
			int currentMonth = Integer.valueOf(today.month) + 1; // add 1 because 0 index
			
			return currentMonth == setUpMonth;
	}
	
	public boolean checkNewDay(){
		String day = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(textFile));
			String line;
			while ((line = br.readLine()) != null){
				if (line.contains("<SETUPDATE>")){
						day = line.substring(13);
						break;
					}
					
				}
			br.close();
		    }catch (IOException e) {
			Toast.makeText(this, "Profile Not Found", Toast.LENGTH_SHORT).show();
		}
		day = day.replace("/", "");
		setUpDay = Integer.parseInt(day);
		
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow(); // get current time
		currentDay = Integer.valueOf(today.monthDay);
		
		return setUpDay == currentDay;
	}
	

}
