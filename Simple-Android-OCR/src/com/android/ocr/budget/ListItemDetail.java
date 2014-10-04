package com.android.ocr.budget;

import java.io.IOException;
import java.io.InputStream;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Build;

public class ListItemDetail extends ActionBarActivity {
	public static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/Re'corder/";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_item_detail);
		
		Bundle b = getIntent().getExtras();
		
	    int position = b.getInt("position");
	    
	    String[] directories = b.getStringArray("directories");
	    String[] totals = b.getStringArray("totals");
	    // Here we turn your string.xml in an array

	    TextView myTextView = (TextView) findViewById(R.id.my_textview);
	    ImageView picView = (ImageView) findViewById(R.id.picture_view);
	    myTextView.setText(totals[position]);
	    
	    Bitmap bmp = BitmapFactory.decodeFile(DATA_PATH + directories[position]);
	    System.out.println("DIRECTORY:" + DATA_PATH + directories[position]);
	    picView.setImageBitmap(Bitmap.createScaledBitmap(bmp, 1000, 1000, false));
		picView.setVisibility(View.VISIBLE);
	}
}