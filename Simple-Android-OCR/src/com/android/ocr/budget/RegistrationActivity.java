package com.android.ocr.budget;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

import com.viewpagerindicator.CirclePageIndicator;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	//SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		//mSectionsPagerAdapter = new SectionsPagerAdapter(
		//		getSupportFragmentManager());
		
		SetUpPageAdaptor setUpPageAdaptor = new SetUpPageAdaptor(getSupportFragmentManager(), getApplicationContext());
		// Set up the ViewPager with the sections adapter.
		final ViewPagerParallax pager = (ViewPagerParallax) findViewById(R.id.pager);
		pager.set_max_pages(3);
		pager.setBackgroundAsset(R.drawable.regi_green);
		pager.setAdapter(setUpPageAdaptor);
		
		pager.setPageTransformer(false, new ViewPager.PageTransformer() {
		    @Override
		    public void transformPage(View page, float position) {
		        final float normalizedposition = Math.abs(Math.abs(position) - 1);
		        page.setAlpha(normalizedposition);
		        }
		});
		
		CirclePageIndicator titleIndicator = (CirclePageIndicator)findViewById(R.id.titles);
		titleIndicator.setViewPager(pager);
		titleIndicator.setCurrentItem(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
  
	
}
