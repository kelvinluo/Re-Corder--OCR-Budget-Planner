package com.android.ocr.budget;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SetUpPageAdaptor extends FragmentPagerAdapter{
	
	String[] pages;
	String[] pageDescription;
	
	
	public SetUpPageAdaptor(FragmentManager fm, Context context) {
		super(fm);


		Resources resources = context.getResources();
		
		pages = resources.getStringArray(R.array.Tags);
			
		pageDescription = resources.getStringArray(R.array.Tag_Description);
	}
	
	@Override
	public Fragment getItem(int position){
		Bundle bundle = new Bundle();
		int image_values = position;
		bundle.putInt(SetUpFragment.ImageIDkey, image_values);
		SetUpFragment setUpFragment = new SetUpFragment();
		setUpFragment.setArguments(bundle);
		return setUpFragment;
	}
	
	@Override
	public CharSequence getPageTitle(int position){
		return pages[position];
	}
	
	@Override
	public int getCount(){
		return pages.length;
	}
}


