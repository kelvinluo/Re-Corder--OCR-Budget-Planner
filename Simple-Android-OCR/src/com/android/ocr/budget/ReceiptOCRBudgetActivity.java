package com.android.ocr.budget;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

public class ReceiptOCRBudgetActivity extends Activity {
	public static final String PACKAGE_NAME = "com.android.ocr.budget";
	public static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/Re'corder/";
	// You should have the trained data file in assets folder
	// You can get them at:
	// http://code.google.com/p/tesseract-ocr/downloads/list

	// You should have the trained data file in assets folder
	// You can get them at:
	// http://code.google.com/p/tesseract-ocr/downloads/list
	public static final String lang = "eng";
	private static final String TAG = "ReceiptOCRBudgetActivity.java";

	protected Button _button;
	// protected ImageView _image;
	protected EditText _field;
	public String _path; // public so onP
	protected boolean _taken;
	private String characterBlacklist; 
	private String characterWhitelist;
	private SharedPreferences prefs;
	public String datePathDir;
	public String userInfoDir;
	public String time;
	double highestPrice;
	double total;
	Time today = new Time(Time.getCurrentTimezone()); // current time
	ProgressDialog progress;
	protected static final String PHOTO_TAKEN = "photo_taken";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		today.setToNow(); // get current time
		int day = Integer.valueOf(today.monthDay);
		int month = Integer.valueOf(today.month) + 1; // add 1 because 0 index
		int year = Integer.valueOf(today.year);
		datePathDir = DATA_PATH + "Photo/" + month + "-" + day + "-" + year + "/";

		String[] paths = new String[] { DATA_PATH, DATA_PATH + "tessdata/", datePathDir};
		System.out.println("PATHS ==" + paths);
		for (String path : paths) {
			File dir = new File(path);
			System.out.println("PATH ==" + path);
			if (!dir.exists()) {
				if (!dir.mkdirs()) {
//					Log.v(TAG, "ERROR: Creation of directory " + path + " on sdcard failed");
					return;
				} else {
//					Log.v(TAG, "Created directory " + path + " on sdcard");
				}
			}
		}

//		Intent intent = new Intent(Intent.ACTION_VIEW);
//		intent.setDataAndType(Uri.parse("file:///storage/emulated/0/Re'corder/Photo/7-6-2014/19_25_39.jpg"),"image/*");
//		startActivity(intent);

		// lang.traineddata file with the app (in assets folder)
		// You can get them at:
		// http://code.google.com/p/tesseract-ocr/downloads/list
		// This area needs work and optimization
		if (!(new File(DATA_PATH + "tessdata/" + lang + ".traineddata")).exists()) {
			try {

				AssetManager assetManager = getAssets();
				InputStream in = assetManager.open("tessdata/" + lang + ".traineddata");
				//GZIPInputStream gin = new GZIPInputStream(in);
				OutputStream out = new FileOutputStream(DATA_PATH
						+ "tessdata/" + lang + ".traineddata");

				// Transfer bytes from in to out
				byte[] buf = new byte[1024];
				int len;
				//while ((lenf = gin.read(buff)) > 0) {
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				//gin.close();
				out.close();

//				Log.v(TAG, "Copied " + lang + " traineddata");
			} catch (IOException e) {
//				Log.e(TAG, "Was unable to copy " + lang + " traineddata " + e.toString());
			}
		}

		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		_path = DATA_PATH; // MUST define here or else algorithm will run again. 
		startCameraActivity();

		//System.out.println("IMPORTANT!!" + Environment.getExternalStorageDirectory());
	}

	public class ButtonClickHandler implements View.OnClickListener {
		public void onClick(View view) {
//			Log.v(TAG, "Starting Camera app");
			startCameraActivity();
		}
	}

	// Simple android photo capture:
	// http://labs.makemachine.net/2010/03/simple-android-photo-capture/

	protected void startCameraActivity() {

		today.setToNow(); // get current time
		time = today.format("%k_%M_%S");
		_path = datePathDir + time + ".jpg";
		// TODO!!!
		File file = new File(_path);
		System.out.println("REAL PATH IS: " + file);
		//findPhotoPath(); // saves picture into local storage
		Uri outputFileUri = Uri.fromFile(file);
		final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
		startActivityForResult(intent, 0);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

//		Log.i(TAG, "resultCode: " + resultCode);

		if (resultCode == -1) {
			onPhotoTaken();
		} else {
//			Log.v(TAG, "User cancelled"); // TODO: MIGHT BE ABLE TO CHANGE THE DIRECTORY NAMES OR DELETE THEM IF CANCLED
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putBoolean(ReceiptOCRBudgetActivity.PHOTO_TAKEN, _taken);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
//		Log.i(TAG, "onRestoreInstanceState()");
		if (savedInstanceState.getBoolean(ReceiptOCRBudgetActivity.PHOTO_TAKEN)) {
			onPhotoTaken();
		}
	}

	public void onPhotoTaken() {
		//ProgressDialog.show(this, "Loading", "Wait while loading...");
		// Note: declare ProgressDialog progress as a field in your class.

		progress = ProgressDialog.show(this, "Loading", "Wait while loading...", true);
		new Thread(new Runnable() 
		{
			@Override
			public void run()
			{
				// do the thing that takes a long time
				_path = datePathDir + time + ".jpg";
				System.out.println("PATH ISLLLLL" + _path);
				_taken = true;
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 4;
				System.out.println("PATH IS NOW:" + _path);
				Bitmap bitmap = BitmapFactory.decodeFile(_path, options);

				try {
					ExifInterface exif = new ExifInterface(_path);
					int exifOrientation = exif.getAttributeInt(
							ExifInterface.TAG_ORIENTATION,
							ExifInterface.ORIENTATION_NORMAL);

//					Log.v(TAG, "Orient: " + exifOrientation);

					int rotate = 0;

					switch (exifOrientation) {
					case ExifInterface.ORIENTATION_ROTATE_90:
						rotate = 90;
						break;
					case ExifInterface.ORIENTATION_ROTATE_180:
						rotate = 180;
						break;
					case ExifInterface.ORIENTATION_ROTATE_270:
						rotate = 270;
						break;
					}

//					Log.v(TAG, "Rotation: " + rotate);

					if (rotate != 0) {

						// Getting width & height of the given image.
						int w = bitmap.getWidth();
						int h = bitmap.getHeight();

						// Setting pre rotate
						Matrix mtx = new Matrix();
						mtx.preRotate(rotate);

						// Rotating Bitmap
						bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, false);
					} 

					// Convert to ARGB_8888, required by tess
					bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

				} catch (IOException e) {
//					Log.e(TAG, "Couldn't correct orientation: " + e.toString());
				}

				// _image.setImageBitmap( bitmap );

//				Log.v(TAG, "Before baseApi");

				TessBaseAPI baseApi = new TessBaseAPI();
				baseApi.setDebug(true);
				baseApi.init(DATA_PATH, lang);
				baseApi.setImage(bitmap);
				///////////////////////////////////////
				characterBlacklist = ""; // TODO: refine
				characterWhitelist = "!?@#$%&*()<>_-+=/.,;:'\"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

				if (baseApi != null) { //NOTEEE

					baseApi.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, characterBlacklist); //ERROR!!
					baseApi.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, characterWhitelist);
				}

				String recognizedText = baseApi.getUTF8Text();

				baseApi.end();

				// You now have the text in recognizedText var, you can do anything with it.
				// We will display a stripped out trimmed alpha-numeric version of it (if lang is eng)
				// so that garbage doesn't make it to the display.

				ArrayList<Double> prices = new ArrayList<Double> ();
				Pattern p = Pattern.compile("(\\d+)(\\,\\d+||d+)(\\,\\d+||d+)(\\.)(\\d{2})(\\s)"); // typical money format. Space at end to advoid % HST, GST, etc. (9 digits)
				Matcher m = p.matcher(recognizedText);
				String result = null;
				highestPrice = 0.00d; // global to use in UI thread
				total = 0.00d;
				//		boolean foundMatch = m.matches();

				//StringBuffer result = new StringBuffer(); // can append
				while (m.find()) {
					result = m.group(0).replaceAll(",", ""); // 1,000 -> 1000
					total = Double.valueOf(result);
					prices.add(total);
					//m.appendReplacement(result, " " + m.group(0)); // space used for split into list later			
				}
				//m.appendTail(result); // select highest
				for (double matches : prices){
					double current = matches;
					if (current > highestPrice){
						highestPrice = current;
					}
				}


				runOnUiThread(new Runnable() {
					@Override
					public void run()
					{
//						progress.dismiss();
//						System.out.println("HIGHEST PRICE: " + highestPrice);
//						Toast.makeText(getApplicationContext(), "SUBTOTAL:" + highestPrice, Toast.LENGTH_LONG).show(); // NOTE: Toast must be run in UI Thread
//						
//						///////
						progress.dismiss();
						System.out.println("HIGHEST PRICE: " + highestPrice);
						Log.w("HighestPrice", Double.toString(highestPrice));
//						Toast.makeText(getApplicationContext(), "SUBTOTAL: $" + highestPrice, Toast.LENGTH_LONG).show(); // display to user then render background task
						File sdcard = Environment.getExternalStorageDirectory();
						File from = new File(datePathDir,time + ".jpg");
						System.out.println("From:" + from);
						File to = new File(datePathDir, time + ".jpg");
						boolean success = from.renameTo(to);
						if (highestPrice > 0.0){
							System.out.println("Changed Path: " + success);
							// TODO: UNDO button
						}
						
						else {
							Toast.makeText(getApplicationContext(), "Price not found. Please re-scan a better image", Toast.LENGTH_LONG).show();
						}
						startNewActivity();
					}
				});
			}
		}).start();


		//		recognizedText = recognizedText.trim();
		//	    
		//		if ( recognizedText.length() != 0 ) {
		//			_field.setText(_field.getText().toString().length() == 0 ? recognizedText : _field.getText() + " " + recognizedText);
		//			_field.setSelection(_field.getText().toString().length());
		//		}

		// Cycle done.
	}
	private void startNewActivity(){
		Intent intent = new Intent(this, AmountViewActivity.class);
		Bundle b = new Bundle();
		b.putString("photoDirectory", _path);
		b.putDouble("total", highestPrice);
		today.setToNow(); // get current time
		int day = Integer.valueOf(today.monthDay);
		int month = Integer.valueOf(today.month) + 1; // add 1 because 0 index
		int year = Integer.valueOf(today.year);
		String currentTime = Integer.toString(year) + "/" + Integer.toString(month) + "/" + Integer.toString(day);
		b.putString("date", currentTime);
		intent.putExtras(b);
		startActivity(intent);
		finish();
	}
}
