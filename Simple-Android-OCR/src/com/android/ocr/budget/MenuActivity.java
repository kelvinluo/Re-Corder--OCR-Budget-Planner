package com.android.ocr.budget;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.graphics.Shader;

import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.graphics.PorterDuff;

public class MenuActivity extends ActionBarActivity{
	private DisplayMetrics displaymetrics = new DisplayMetrics();
	private int height;
	private int width;
	public double new_total = 0.0;
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
	    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		height = displaymetrics.heightPixels;
		width = displaymetrics.widthPixels;

		//Customizing the TITLE
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		getSupportActionBar().setCustomView(R.layout.abs_layout);
		
		TextView menuTitle = (TextView) findViewById(R.id.menu_title);
		
		Typeface tfFreshman = Typeface.createFromAsset(getAssets(),
		        "Freshman.ttf");
		Typeface tfChapaza = Typeface.createFromAsset(getAssets(),
		        "Chapaza.ttf");
		
		Typeface tfAmericanCaptain = Typeface.createFromAsset(getAssets(),
		        "American_Captain.ttf");
		
		menuTitle.setTypeface(tfFreshman);
		
		// Set up the action bar.
		final ActionBar actionBar = getSupportActionBar();
		//actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		final ViewPagerParallax pager = (ViewPagerParallax) findViewById(R.id.pager);
		pager.set_max_pages(2);
		pager.setBackgroundAsset(R.drawable.menu_blue);
		pager.setAdapter(mSectionsPagerAdapter);
		pager.setCurrentItem(1);
		
		pager.setPageTransformer(false, new ViewPager.PageTransformer() {
		    @Override
		    public void transformPage(View page, float position) {
		        final float normalizedposition = Math.abs(Math.abs(position) - 1);
		        page.setAlpha(normalizedposition);
		        page.setScaleX(normalizedposition / 2 + 0.5f);
		        page.setScaleY(normalizedposition / 2 + 0.5f);
		        }
		});
		
		CirclePageIndicator titleIndicator = (CirclePageIndicator)findViewById(R.id.titles);
		titleIndicator.setViewPager(pager);
		titleIndicator.setCurrentItem(1);
		

		
//		// Set up the ViewPager with the sections adapter.
//		mViewPager = (ViewPager) findViewById(R.id.pager);
//		//mViewPager.setBackgroundDrawable(new DrawableBackground());
//		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
	}
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	/**
	 * 
	 * 
	 * 
	 * FRAGMENT
	 * 
	 * 
	 * 
	 *
	 */
	
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/Re'corder/";
		private File textFile = new File(DATA_PATH + "userinfo.txt");
		private DisplayMetrics displaymetrics = new DisplayMetrics();
		private int height;
		private int width;
		private static final String ARG_SECTION_NUMBER = "section_number";
		private View menu_layout;
		private ImageButton cameraButton;
		private TextView cameraClick;
		private TextView cameraDescription;
		private ImageView whiteBanner;
		private ImageView cameraButtonHalo;
		private ImageView graphView;
		private ImageButton setting;
		private double currentBalance = 0.0;
		private double totalBudget = 0.0;
		
		private TextView totalPercent;
		private TextView balancePercent;
		private TextView averagePercent;
		private TextView dayPercent;
		
		private TextView totalTag;
		private TextView balanceTag;
		private TextView averageTag;
		private TextView dayTag;
		
		private TextView totalView;
		private TextView balanceView;
		private TextView averageView;
		private TextView dayView;
		
		private TextView totalTitle;
		private TextView balanceTitle;
		private TextView averageTitle;
		private TextView dayTitle;
		
		private ListView graphList;
		
		private int daysLeft = 0;
		private double daylyBudget = 0.0;
		private double daylyUsedBudget = 0.0;
		
		private Typeface tfFreshman;
		private Typeface tfChapaza;
		private Typeface tfChamp;
		private UserInfoSearch userInfo = new UserInfoSearch();
		private Typeface tfAmericanCaptain;
		
		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			
			getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
			height = displaymetrics.heightPixels;
			width = displaymetrics.widthPixels;
			View rootView = inflater.inflate(R.layout.fragment_menu, container,
					false);
			menu_layout = rootView.findViewById(R.id.menu_screen);
			currentBalance = userInfo.getCurrentBalance();
			totalBudget = userInfo.getTotalBudget();
			LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 2f);
			layoutParams1.weight = 1;
			menu_layout.setLayoutParams(layoutParams1);
			menu_layout.setPadding(0, 0, 0, 0);
			
			tfFreshman = Typeface.createFromAsset(menu_layout.getContext().getAssets(),
			        "Freshman.ttf");
			tfChapaza = Typeface.createFromAsset(menu_layout.getContext().getAssets(),
			        "Chapaza.ttf");
			tfAmericanCaptain = Typeface.createFromAsset(menu_layout.getContext().getAssets(),
			        "American_Captain.ttf");
			tfChamp = Typeface.createFromAsset(menu_layout.getContext().getAssets(),
			        "CaviarDreams.ttf");
			
			cameraButton = new ImageButton(menu_layout.getContext());
			cameraButtonHalo =  new ImageView(menu_layout.getContext());
			cameraClick = new TextView(menu_layout.getContext());
			cameraDescription = new TextView(menu_layout.getContext());
			whiteBanner = new ImageView(menu_layout.getContext());
			graphView = new ImageView(menu_layout.getContext());
			
			totalView = new TextView(menu_layout.getContext());
			balanceView = new TextView(menu_layout.getContext());
			dayView = new TextView(menu_layout.getContext());
			averageView = new TextView(menu_layout.getContext());
			
			totalTitle = new TextView(menu_layout.getContext());
			balanceTitle = new TextView(menu_layout.getContext());
			dayTitle = new TextView(menu_layout.getContext());
			averageTitle = new TextView(menu_layout.getContext());
			
			totalTag = new TextView(menu_layout.getContext());
			balanceTag = new TextView(menu_layout.getContext());
			dayTag = new TextView(menu_layout.getContext());
			averageTag = new TextView(menu_layout.getContext());
			
			totalPercent = new TextView(menu_layout.getContext());
			balancePercent = new TextView(menu_layout.getContext());
			dayPercent = new TextView(menu_layout.getContext());
			averagePercent = new TextView(menu_layout.getContext());
			
			setting = new ImageButton(menu_layout.getContext());
			
			graphList = new ListView(menu_layout.getContext());
			
			daysLeft = userInfo.getDaysLeft();
			daylyBudget = userInfo.getDaylyBudget();
			daylyUsedBudget = userInfo.getDaylyUsedBudget();
			
			Bundle bundle = getArguments();

			
			int imageID = bundle.getInt(ARG_SECTION_NUMBER);
			if (imageID == 0){
				setValuesScreenOne(rootView,imageID);
				//menu_layout.setBackgroundColor(getResources().getColor(R.color.menuScreen1));
			}
			else if (imageID == 1){
				menu_layout.setAlpha(50);
				setValuesScreenTwo(rootView,imageID);
				//menu_layout.setBackgroundColor(getResources().getColor(R.color.menuScreen2));
			}
			else if (imageID == 2){
				setValuesScreenThree(rootView, imageID);
				//menu_layout.setBackgroundColor(getResources().getColor(R.color.menuScreen3));
			}
			return rootView;
		}
		
		public void sendNoticification(String title, String info){
			NotificationCompat.Builder mBuilder =
			        new NotificationCompat.Builder(getActivity())
			        .setSmallIcon(R.drawable.ic_launcher)
			        .setContentTitle(title)
			        .setContentText(info);
			// Creates an explicit intent for an Activity in your app
			Intent resultIntent = new Intent(getActivity(), MenuActivity.class);

			// The stack builder object will contain an artificial back stack for the
			// started Activity.
			// This ensures that navigating backward from the Activity leads out of
			// your application to the Home screen.
			TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
			// Adds the back stack for the Intent (but not the Intent itself)
			stackBuilder.addParentStack(MenuActivity.class);
			// Adds the Intent that starts the Activity to the top of the stack
			stackBuilder.addNextIntent(resultIntent);
			PendingIntent resultPendingIntent =
			        stackBuilder.getPendingIntent(
			            0,
			            PendingIntent.FLAG_UPDATE_CURRENT
			        );
			mBuilder.setContentIntent(resultPendingIntent);
			NotificationManager mNotificationManager =
			    (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
			// mId allows you to update the notification later on.
			Notification notification = mBuilder.build();
			notification.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;
			mNotificationManager.notify(0, notification);	
		}
		
		private void setValuesScreenOne(View view, int imageID)
		{
			
			System.out.println("Width: " + width);
			//int haloLeft = width/2 - 300;
			//int haloTop = 480;
			//int haloBot = 480;
			//paramsHalo.setMargins(haloTop, haloLeft, haloLeft, haloBot);
			
			int halo_button_y = (int)((((600*width/1080)) - ((350*width/1080)))/2);
			
			LinearLayout.LayoutParams paramsHalo = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f);
			paramsHalo.height = (int)((600*width/1080));
			paramsHalo.width = (int)((600*width/1080));
			
			cameraButtonHalo.setId(R.id.camera_halo);
			cameraButtonHalo.setImageResource(R.anim.anim_halo);
			cameraButtonHalo.setY((int)(height/2.8) - halo_button_y);
			cameraButtonHalo.setX(width/2 - paramsHalo.width/2);
			cameraButtonHalo.setScaleType(ScaleType.FIT_XY);
			cameraButtonHalo.setLayoutParams(paramsHalo);
			((RelativeLayout)menu_layout).addView(cameraButtonHalo);
			
			final AnimationDrawable loading_anim_drawable
			 =    (AnimationDrawable)cameraButtonHalo.getDrawable();
			
			loading_anim_drawable.setCallback(cameraButtonHalo);
			loading_anim_drawable.setVisible(true, true);
			
			loading_anim_drawable.start();
			
			
			LinearLayout.LayoutParams paramsButton = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f);
			paramsButton.height = (int)((350*width/1080));
			paramsButton.width = (int)((350*width/1080));
			
			cameraButton.setId(R.id.camera_but);
			cameraButton.setBackgroundResource(R.drawable.camera_button);
			Log.w("directoryHeight",Integer.toString(paramsButton.height));
			Log.w("directoryWidth",Integer.toString(paramsButton.width));
			cameraButton.setY((int)(height/2.8));
			cameraButton.setX(width/2 - paramsButton.width/2);
			cameraButton.setScaleType(ScaleType.FIT_XY);
			cameraButton.setLayoutParams(paramsButton);
			((RelativeLayout)menu_layout).addView(cameraButton);
			
			cameraButton.setOnClickListener(new View.OnClickListener() {
			    public void onClick(View v) {
			        Intent ocrActivity = new Intent(getActivity(), ReceiptOCRBudgetActivity.class);
			        startActivityForResult(ocrActivity, 1);
			        getActivity().finish();
			    }
			});
			
			LinearLayout.LayoutParams paramsDes = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f);
			
			cameraClick.setId(R.id.camera_des);
			cameraClick.setText("CLICK TO SCAN");
			cameraClick.setTypeface(tfAmericanCaptain);
			cameraClick.setLayoutParams(paramsDes);
			cameraClick.setGravity(Gravity.CENTER_HORIZONTAL);
			cameraClick.setY((int)((height/2.8) + (paramsButton.height) + 30));
			cameraClick.setTextSize(40);
			cameraClick.setTextColor(getResources().getColor(R.color.white));
			((RelativeLayout)menu_layout).addView(cameraClick);
			
		}
		
		private void setValuesScreenTwo(View view, int imageID)
		{
			LinearLayout.LayoutParams paramsButton = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);
			paramsButton.height = (int)((height/1920)*height);
			paramsButton.width = (int)((width/1080)*width);
			graphView.setId(R.id.circular_graph);
			
			Calendar cal = Calendar.getInstance();
			double dayOfMonth = (double)cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			double daysLeft = (double)userInfo.getDaysLeft();
			

			
			LinearLayout.LayoutParams paramImage = new LinearLayout.LayoutParams
	                (LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f);
			paramImage.width = 30;
			paramImage.height = 30;
			
			Matrix matrix=new Matrix();
			matrix.postRotate(90, 25, 25);
			
//			redBanner.setLayoutParams(paramImage);
//			redBanner.setId(R.id.red_banner);
//			redBanner.setBackgroundResource(R.drawable.red);
//			redBanner.setY(800 + yAdjust);
//			redBanner.setX(location - 70);
//			redBanner.setScaleType(ScaleType.MATRIX);
//			redBanner.setImageMatrix(matrix);
//			((RelativeLayout)menu_layout).addView(redBanner);
//			
//			blueBanner.setLayoutParams(paramImage);
//			blueBanner.setId(R.id.blue_banner);
//			blueBanner.setBackgroundResource(R.drawable.blue);
//			blueBanner.setY(970 + yAdjust);
//			blueBanner.setX(location - 70);
//			blueBanner.setScaleType(ScaleType.MATRIX);
//			blueBanner.setImageMatrix(matrix);
//			((RelativeLayout)menu_layout).addView(blueBanner);
//			
//			greenBanner.setLayoutParams(paramImage);
//			greenBanner.setId(R.id.green_banner);
//			greenBanner.setBackgroundResource(R.drawable.green);
//			greenBanner.setY(1140 + yAdjust);
//			greenBanner.setX(location - 70);
//			greenBanner.setScaleType(ScaleType.MATRIX);
//			greenBanner.setImageMatrix(matrix);
//			((RelativeLayout)menu_layout).addView(greenBanner);
//			
//			yellowBanner.setLayoutParams(paramImage);
//			yellowBanner.setId(R.id.yellow_banner);
//			yellowBanner.setBackgroundResource(R.drawable.yellow);
//			yellowBanner.setY(1310 + yAdjust);
//			yellowBanner.setX(location - 70);
//			yellowBanner.setImageMatrix(matrix);
//			yellowBanner.setScaleType(ScaleType.MATRIX);
//			((RelativeLayout)menu_layout).addView(yellowBanner);
//			
			int total_detail_size = 35;
			int title_size = 15;
			int total_per_size = 70;
			int secondary_detail_size = 20;
			int secondary_per_size = 25;
			
			int totalYAdjust = (int)((70*width)/1080);
			int yAdjust = (int)((60*width)/1080);
			
			int total_x = (width*26/64);
			int total_y = (height*51/64);
			
			int total_per_x = 0;
			int total_per_y = (height*20/64);
			if (((userInfo.getCurrentBalance()/userInfo.getTotalBudget())*100) >= 100){
				total_per_x = (width*79/256);
				total_per_y = (height*21/64);
			}
			else if (((userInfo.getCurrentBalance()/userInfo.getTotalBudget())*100) >= 10){
				total_per_x = (width*88/256);
			}
			else
			{
				total_per_x = (width*105/256);
			}
			
			
			int daily_x = (width*4/64);
			int daily_y = (height*26/32);
			 
			int daily_per_x = 0;
			int daily_per_y = (height*85)/128;
			if (userInfo.getDaylyBudget() == 0 || (int)((userInfo.getDaylyUsedBudget()/userInfo.getDaylyBudget())*100) >= 100){
				daily_per_x = (width*19/256);
				daily_per_y = (height*85)/128;
			}
			else if ((int)((userInfo.getDaylyUsedBudget()/userInfo.getDaylyBudget())*100) >= 10){
				daily_per_x = (width*12/128);
			}
			else{
				daily_per_x = (width*15/128);
			}
//			
			
			int day_x = (width*54/64);
			int day_y = (height*26/32);
			
			int day_per_x = (width*107/128);
			if (userInfo.getDaysLeft() < 10){
				day_per_x = (width*110/128);
			}
			
			int day_per_y = (height*85/128);
			
			LinearLayout.LayoutParams paramsDesciption = new LinearLayout.LayoutParams
	                (LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f);
			
			totalView.setLayoutParams(paramsDesciption);
			totalView.setId(R.id.total_description);
			totalView.setText("$" + userInfo.getCurrentBalance());
			totalView.setY(total_y);
			totalView.setX(total_x);
			totalView.setGravity(Gravity.LEFT);
			totalView.setTextColor(getResources().getColor(R.color.white));
			totalView.setTextSize(total_detail_size);
			totalView.setTypeface(tfAmericanCaptain);
			((RelativeLayout)menu_layout).addView(totalView);
			
			averageView.setLayoutParams(paramsDesciption);
			averageView.setId(R.id.average_description);
			averageView.setText("$" + userInfo.getDaylyUsedBudget());
			averageView.setY(daily_y);
			averageView.setX(daily_x);
			averageView.setGravity(Gravity.LEFT);
			averageView.setTextColor(getResources().getColor(R.color.white));
			averageView.setTextSize(secondary_detail_size);
			averageView.setTypeface(tfAmericanCaptain);
			((RelativeLayout)menu_layout).addView(averageView);

			dayView.setLayoutParams(paramsDesciption);
			dayView.setId(R.id.day_description);
			dayView.setText((int)daysLeft + " Days");
			dayView.setY(day_y);
			dayView.setX(day_x);
			dayView.setGravity(Gravity.LEFT);
			dayView.setTextColor(getResources().getColor(R.color.white));
			dayView.setTextSize(secondary_detail_size);
			dayView.setTypeface(tfAmericanCaptain);
			((RelativeLayout)menu_layout).addView(dayView);
			
			totalTitle.setLayoutParams(paramsDesciption);
			totalTitle.setId(R.id.total_title);
			totalTitle.setText("Monthly Budget");
			totalTitle.setY(total_y - (int)((yAdjust/3)*2));
			totalTitle.setX(total_x);
			totalTitle.setGravity(Gravity.LEFT);
			totalTitle.setTextColor(getResources().getColor(R.color.white));
			totalTitle.setTextSize(title_size);
			totalTitle.setTypeface(tfAmericanCaptain);
			((RelativeLayout)menu_layout).addView(totalTitle);
			
			averageTitle.setLayoutParams(paramsDesciption);
			averageTitle.setId(R.id.average_title);
			averageTitle.setText("Dayly Budget");
			averageTitle.setY(daily_y - (int)((yAdjust/3)*2));
			averageTitle.setX(daily_x);
			averageTitle.setGravity(Gravity.LEFT);
			averageTitle.setTextColor(getResources().getColor(R.color.white));
			averageTitle.setTextSize(title_size);
			averageTitle.setTypeface(tfAmericanCaptain);
			((RelativeLayout)menu_layout).addView(averageTitle);

			dayTitle.setLayoutParams(paramsDesciption);
			dayTitle.setId(R.id.day_title);
			dayTitle.setText("Days Left");
			dayTitle.setY(day_y - (int)((yAdjust/3)*2));
			dayTitle.setX(day_x);
			dayTitle.setGravity(Gravity.LEFT);
			dayTitle.setTextColor(getResources().getColor(R.color.white));
			dayTitle.setTextSize(title_size);
			dayTitle.setTypeface(tfAmericanCaptain);
			((RelativeLayout)menu_layout).addView(dayTitle);
			
			totalTag.setLayoutParams(paramsDesciption);
			totalTag.setId(R.id.total_tag);
			totalTag.setText("$" + Double.toString(totalBudget));
			totalTag.setY(total_y + totalYAdjust + 15);
			totalTag.setX(total_x);
			totalTag.setGravity(Gravity.LEFT);
			totalTag.setTextColor(getResources().getColor(R.color.silver));
			totalTag.setTextSize(total_detail_size);
			totalTag.setTypeface(tfAmericanCaptain);
			((RelativeLayout)menu_layout).addView(totalTag);
			
			averageTag.setLayoutParams(paramsDesciption);
			averageTag.setId(R.id.average_tag);
			averageTag.setText("$" + Double.toString(daylyBudget));
			averageTag.setY(daily_y + yAdjust);
			averageTag.setX(daily_x);
			averageTag.setGravity(Gravity.LEFT);
			averageTag.setTextColor(getResources().getColor(R.color.silver));
			averageTag.setTextSize(secondary_detail_size);
			averageTag.setTypeface(tfAmericanCaptain);
			((RelativeLayout)menu_layout).addView(averageTag);

			dayTag.setLayoutParams(paramsDesciption);
			dayTag.setId(R.id.day_tag);
			dayTag.setText((int)dayOfMonth + " Days");
			dayTag.setY(day_y + yAdjust);
			dayTag.setX(day_x);
			dayTag.setGravity(Gravity.LEFT);
			dayTag.setTextColor(getResources().getColor(R.color.silver));
			dayTag.setTextSize(secondary_detail_size);
			dayTag.setTypeface(tfAmericanCaptain);
			((RelativeLayout)menu_layout).addView(dayTag);
			
			totalPercent.setLayoutParams(paramsDesciption);
			totalPercent.setId(R.id.total_percent);
			if ((int)((userInfo.getCurrentBalance()/userInfo.getTotalBudget())*100) < 100){
				if ((int)((userInfo.getCurrentBalance()/userInfo.getTotalBudget())*100) >= 75){
					sendNoticification("Warning", "You have used more than 75% of your budget");
				}
				
				totalPercent.setText((int)((userInfo.getCurrentBalance()/userInfo.getTotalBudget())*100) + "%");
				totalPercent.setTextColor(getResources().getColor(R.color.white));
				totalPercent.setTextSize(total_per_size);
			}
			else
			{
				sendNoticification("Warning", "You have exceeded your budget");
				totalPercent.setText("100%");
				totalPercent.setTextColor(getResources().getColor(R.color.red));
				totalPercent.setTextSize((int)(total_per_size/1.2));
			}
			totalPercent.setX(total_per_x);
			totalPercent.setY(total_per_y);
			
			
			totalPercent.setTypeface(tfChamp);
			((RelativeLayout)menu_layout).addView(totalPercent);
			
			averagePercent.setLayoutParams(paramsDesciption);
			averagePercent.setId(R.id.average_percent);
			if (userInfo.getDaylyBudget() == 0 || (int)((userInfo.getDaylyUsedBudget()/userInfo.getDaylyBudget())*100) >= 100){
				sendNoticification("Warning", "You have exceeded your daily budget");
				averagePercent.setText("100%");
				averagePercent.setTextColor(getResources().getColor(R.color.red));
				averagePercent.setTextSize((int)(secondary_per_size/1.2));
			}
			else {
				averagePercent.setText((int)((userInfo.getDaylyUsedBudget()/userInfo.getDaylyBudget())*100) + "%");
				averagePercent.setTextColor(getResources().getColor(R.color.white));
				averagePercent.setTextSize(secondary_per_size);
			}
			averagePercent.setY(daily_per_y);
			averagePercent.setX(daily_per_x);
			averagePercent.setTypeface(tfChamp);
			((RelativeLayout)menu_layout).addView(averagePercent);

			dayPercent.setLayoutParams(paramsDesciption);
			dayPercent.setId(R.id.day_percent);
			dayPercent.setText(userInfo.getDaysLeft() + "");
			dayPercent.setY(day_per_y);
			dayPercent.setX(day_per_x);
			dayPercent.setTextColor(getResources().getColor(R.color.white));
			dayPercent.setTextSize(secondary_per_size);
			dayPercent.setTypeface(tfChamp);
			((RelativeLayout)menu_layout).addView(dayPercent);
			
			int budgetAngle = (int)((currentBalance/totalBudget)*360);
			
			int daylyBudgetAngle = (int)((daylyUsedBudget/daylyBudget)*360);
			if (daylyBudget == 0){
				daylyBudgetAngle = 361;
			}
			
			int daysAngle = (int)(((dayOfMonth - daysLeft)/dayOfMonth)*360);
			
			LinearLayout.LayoutParams paramsGraph = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f);
			paramsGraph.height = height;
			paramsGraph.width = width;
			//graphView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
			graphView.setImageDrawable(new Arc(budgetAngle, daylyBudgetAngle, daysAngle, getActivity()));
			//graphView.setImageResource(R.drawable.green);
			graphView.setLayoutParams(paramsGraph);
			graphView.setVisibility(View.VISIBLE);
			((RelativeLayout)menu_layout).addView(graphView);
			
			
			LinearLayout.LayoutParams paramsSetting = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f);
			paramsSetting.height = (int)(height/16);
			paramsSetting.width = (int)(height/16);
			
			setting.setId(R.id.camera_but);
			setting.setBackgroundResource(R.drawable.setting);
			setting.setY((int)(height*85/128));
			setting.setX(((int)(width*30/64)));
			setting.setLayoutParams(paramsSetting);
			((RelativeLayout)menu_layout).addView(setting);
			
			setting.setOnClickListener(new View.OnClickListener() {
			    public void onClick(View v) {
			    	final EditText input = new EditText(v.getContext());
			    	input.setGravity(Gravity.CENTER_HORIZONTAL);
			    	new AlertDialog.Builder(v.getContext())
		               .setTitle("Set Up A New Monthly Budget")
		               .setView(input)
		               .setMessage("Please Enter Your Budget")
		               .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int whichButton) {
		                		   Editable value = (Editable) input.getText();
		                		   refreshBudget(value.toString());
		                   }
		               }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int whichButton) {
		                       // Do nothing.
		                   }
		               }).show();
			    }
			});
			
//			System.out.println("TOTAL: " + totalBudget);
//			System.out.println("BALANCE: " + currentBalance);
		}
	///////////////////////////////////////////////
		public void refreshBudget (String newBudget)
		{
			try{
				File tempFile = new File(DATA_PATH + "myTempFile.txt");
				BufferedReader reader = new BufferedReader(new FileReader(textFile));
				BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
				String currentLine;
				double new_bud = Double.parseDouble(newBudget);
				while((currentLine = reader.readLine()) != null) {
				    // trim newline when comparing with lineToRemove
					if (currentLine.contains("<CURRENT BUDGET>"))
					    {
							writer.write("<CURRENT BUDGET>" + new_bud + "\r\n");
					    }
					else{
							writer.write(currentLine + "\r\n");
						}
					}
				boolean successful = tempFile.renameTo(textFile);
				writer.close();
				reader.close();
				Intent registration = new Intent(getActivity(), LoadingScreen.class);
				getActivity().finish();
				startActivity(registration);
			}catch(IOException e){
				Toast.makeText(getActivity(), "Please Enter A Valid Number", Toast.LENGTH_LONG).show();
			}
		}
		
		public class Arc extends Drawable{
		    Paint paint1 = new Paint();
		    Paint paint2 = new Paint();
		    int backgroundColor;
		    int radius = 0;
		    int x = 0;
		    int y = 0;
		    int bot = 0;
		    int usageAngle = 0;
		    int daylyAngle = 0;
		    int dayLeft = 0;
		    Context basePad;
			public Arc(int usage, int dayly, int getDayLeft, Context context){
				x = (width - radius)/2;
				usageAngle = usage;
				basePad = context;
				daylyAngle = dayly;
				dayLeft = getDayLeft;
			}
		    
			@Override
			public void draw(Canvas canvas) {
				// TODO Auto-generated method stub
				
				float graphBrushWidth = (float)((90*width)/1080);
				
				backgroundColor = basePad.getResources().getColor(R.color.TRANSPARENT);
		        paint1.setColor(basePad.getResources().getColor(R.color.white));
		        paint1.setAntiAlias(true);  
		        canvas.drawColor(backgroundColor);
		        paint1.setStrokeWidth(graphBrushWidth);

		        paint1.setStyle(Style.STROKE);
//		        paint.setStyle(Paint.Style.FILL);
		        
		        paint2.setColor(basePad.getResources().getColor(R.color.total_ring2));
		        paint2.setAntiAlias(true);  
		        paint2.setStrokeWidth(graphBrushWidth);
		        paint2.setStyle(Style.STROKE);
		        
		        int[] graphX = new int[]{width/2, (width*5/32), (width*28/32)};
		        int[] graphY = new int[]{(height*3/8), (height*11/16), (height*11/16)};
		        int[] graphRadius = new int[]{(int)((width*6)/8), width/5, width/5};
		        int[] graphContent = new int[]{usageAngle, daylyAngle, dayLeft};
		        
		        for (int count = 0; count < 3; count++){
		        	paint1.setColor(basePad.getResources().getColor(R.color.white));
		        	paint2.setColor(basePad.getResources().getColor(R.color.total_ring2));
			        RectF oval = new RectF();
			        oval.left = graphX[count] - (int)(graphRadius[count]/2);
			        oval.top = graphY[count] - (int)(graphRadius[count]/2);
			        oval.right = graphX[count] + (int)(graphRadius[count]/2);
			        oval.bottom = graphY[count] + (int)(graphRadius[count]/2);
			        if (count != 0){
			        	graphBrushWidth = (float)((20*width)/1080);
			        	paint1.setStrokeWidth(graphBrushWidth);
			        	paint2.setStrokeWidth(graphBrushWidth);
			        }
			        if (graphContent[count] >= 360){
			        	paint1.setColor(basePad.getResources().getColor(R.color.red));
			        	paint2.setColor(basePad.getResources().getColor(R.color.red));
			        }
			        canvas.drawArc(oval, 270, graphContent[count] + 1, false, paint2);
				    canvas.drawArc(oval, 270 + graphContent[count], 361 - graphContent[count], false, paint1);
		        }
				
//		        graphBrushWidth = (float)((3*width)/1080);
//		        paint1.setStrokeWidth(graphBrushWidth);
//		        canvas.drawLine(total_x, total_y, total_x + ((160*width)/1080), total_y, paint1);
//		        canvas.drawLine(daily_x, daily_y, daily_x + ((160*width)/1080), daily_y, paint1);
//		        canvas.drawLine(day_x, day_y, day_x + ((160*width)/1080), day_y, paint1);
		        
//		        int bannerY = (int)((175*width)/1080);
//		        int bannerWidth = (int)((170*width)/1080);
//		        bot = y + radius;
		        
//		        Paint coverPaint = new Paint();
//		        coverPaint.setColor(basePad.getResources().getColor(R.color.white));
//		        coverPaint.setAntiAlias(true);  
//		        coverPaint.setStrokeWidth((float) 5.0);
//		        coverPaint.setStyle(Style.STROKE);
//		        coverPaint.setStyle(Paint.Style.FILL);
//		        
//		        RectF coverOval = new RectF();
//		        coverOval.left = x + 100;
//		        coverOval.top = y + 100;
//		        coverOval.right = x + 800;
//		        coverOval.bottom = bot - 100;
//		        canvas.drawArc(coverOval, 0, 360, true, coverPaint);
			}
			@Override
			public void setAlpha(int alpha) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void setColorFilter(ColorFilter cf) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public int getOpacity() {
				// TODO Auto-generated method stub
				return 0;
			}
		 }
		
		private ArrayAdapter<String> adapter;
		private List<String> directories;
		private void setValuesScreenThree(View view, int imageID)
		{
			LinearLayout.LayoutParams paramsList = new LinearLayout.LayoutParams
	                (LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f);
			graphList.setId(R.id.graph_list);
			graphList.setLayoutParams(paramsList);
			
			List<String> amount = new ArrayList<String>();
			directories = new ArrayList<String>();
			List<String> dates = new ArrayList<String>();
			getPhotoDirectoriess(amount, directories, dates);
			
			String[] values = new String[amount.size()];
			String[] directoriesArray = new String[directories.size()];
			String[] totals = new String[amount.size()];
			
			List<String> listTitle = new ArrayList<String>();
			
			for (int count = 0; count < amount.size(); count++){
				listTitle.add(dates.get(count) + "              " + amount.get(count));
			}
			
			values = listTitle.toArray(values);
			directoriesArray = directories.toArray(directoriesArray);
			totals = amount.toArray(totals);
			
			final String[] dirs = directoriesArray.clone();
			final String[] t = totals.clone();
			adapter = new ArrayAdapter<String>(menu_layout.getContext(), R.layout.list_item, listTitle);
			graphList.setAdapter(adapter);
			graphList.setDividerHeight(10);
			graphList.setPadding(0, width/8, 0, 0);
			
	        SwipeDismissListViewTouchListener touchListener =
	                new SwipeDismissListViewTouchListener(
	                		graphList,
	                        new SwipeDismissListViewTouchListener.OnDismissCallback() {
	                            @Override
	                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
	                                for (int position : reverseSortedPositions) {
	                                	adapter.remove(adapter.getItem(position));
	                                	removeFromList(position);
	                    				File badPhoto = new File(directories.get(position));
	                    				boolean deleted = badPhoto.delete();
	                                }
	                                adapter.notifyDataSetChanged();
	                            }
	                        });
	        graphList.setOnTouchListener(touchListener);
	        graphList.setOnScrollListener(touchListener.makeScrollListener());
			graphList.setOnItemClickListener(new OnItemClickListener(){
			    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
			            // Then you start a new Activity via Intent
			            Bundle b = new Bundle();
			            Intent intent = new Intent();
			            intent.setClass(menu_layout.getContext(), ListItemDetail.class);
			            b.putInt("position", position);
			            // Or / And
			            b.putLong("id", id);
			            b.putStringArray("directories", dirs);
			            b.putStringArray("totals", t);
			            intent.putExtras(b);
			            startActivity(intent);
			    }
			}
			);
			((RelativeLayout)menu_layout).addView(graphList);
		}
		
		private void removeFromList(int position){
			int count = 0;
			int currentPos = 0;
			try{
				File tempFile = new File(DATA_PATH + "myTempFile.txt");
				BufferedReader reader = new BufferedReader(new FileReader(textFile));
				BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
				String currentLine;

				while((currentLine = reader.readLine()) != null) {
				    // trim newline when comparing with lineToRemove
					if (currentLine.contains("@New Entry") && currentPos != position)
					    {
							currentPos += 1;
							writer.write(currentLine + "\r\n");
					    }
					else if (currentLine.contains("@New Entry") && position == currentPos)
						{
							count = 3;
							currentPos += 1;
						}
					else if (count != 0)
						{
							count -= 1;
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
		
		private void getPhotoDirectoriess(List<String> total, List<String> directories, List<String> date){
			try {
				BufferedReader br = new BufferedReader(new FileReader(textFile));
				String line;
				int count = 0;
				while ((line = br.readLine()) != null){
					if (count > 0){
						count--;
					}
					if (line.contains("@New Entry"))
					{
						count = 4;
					}
					else if (count == 3){
						date.add(line.substring(6));
					}
					else if (count == 2){
						total.add(line.substring(8));
					}
					else if (count == 1){
						directories.add(line.substring(48));
					}
			    }
				br.close();
			}
			catch (IOException e) {
				Toast.makeText(getActivity(), "Profile Not Found", Toast.LENGTH_SHORT).show();
			}
		}
	}

	
	
	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Bundle bundle = new Bundle();
			int image_values = position;
			bundle.putInt(PlaceholderFragment.ARG_SECTION_NUMBER, image_values);
			PlaceholderFragment setUpFragment = new PlaceholderFragment();
			setUpFragment.setArguments(bundle);
			return setUpFragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:	
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}
	  
}
