// Homework Assignment Number 6 Final Project                                               //
// Class: CS6301 User Interface Design                                        //
// Group of 2 students                                                        //
//----------------------------------------------------------------------------//
// Name: ARJUN SHROFF MAHESH       Net ID: axs144930                         //
//----------------------------------------------------------------------------//
// Name: ARNAV SHARMA               Net ID: axs144130                         //
//----------------------------------------------------------------------------//
// Date created: 04.19.2015                                                   //
////////////////////////////////////////////////////////////////////////////////
//----------------------------------------------------------------------------//


// This part of code controls the home screen. What action does buttons perform on click is controlled through this class.//

package com.smartmobilesofware.ocrapiservice;
/**
 * Created by Arnav on 4/19/2015.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class SampleActivity extends Activity {
	private final int RESPONSE_OK = 200;

	private static final int IMAGE_PICKER_REQUEST = 2;
	static File myFile = new File(Environment.getExternalStorageDirectory()
			.getAbsoluteFile(), "events13.txt");
	private String apiKey;
	private String langCode;
	private String fileName;
	ListView mListView;
	DataAdapter mDataAdapter;
	String[] formatStrings = { "MMM dd yyyy", "MM/dd/yyyy", "MM-dd-yyyy" };

	private static final int CAMERA_REQUEST = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);


		final Button pickButton = (Button) findViewById(R.id.gallery);

		pickButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Starting image picker activity
				startActivityForResult(
						new Intent(
								Intent.ACTION_PICK,
								android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
						IMAGE_PICKER_REQUEST);
			}
		});

		mListView = (ListView) findViewById(R.id.listView);
		mDataAdapter = new DataAdapter(this, loadArrayFromFile());
		Button camera = (Button) findViewById(R.id.camera);
        camera.setBackgroundResource(R.drawable.androidcamera);
		camera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent cameraIntent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, CAMERA_REQUEST);
			}
		});

		final Button pickButton1 = (Button) findViewById(R.id.button);
		pickButton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent mIntent = new Intent(getBaseContext(), ListofEvents.class);
				startActivity(mIntent);
			}
		});

	}

	ArrayList<Eve> mEvents;

	private ArrayList<Eve> loadArrayFromFile() {
		mEvents = new ArrayList<Eve>();
		try {

			BufferedReader reader = new BufferedReader(new FileReader(
					new File(Environment.getExternalStorageDirectory()
							.getAbsoluteFile(), "events13.txt")));
			String line;

			// Read each line

			while ((line = reader.readLine()) != null) {

				// Split to separate the name from the capital
				String[] RowData = line.split(",");

				// Create a Contacts object for this row's data.
				Eve cur = new Eve();
				cur.setName(RowData[0]);
				// cur.setsdate(RowData[1]);
				// cur.setldate(RowData[2]);
				// cur.setEmail(RowData[3]);

				mEvents.add(cur);
				Collections.sort(mEvents, new Comparator<Eve>() {
					@Override
					public int compare(Eve c1, Eve c2) {
						return c1.getName().compareTo(c2.getName());
					}
				});
				// Add the Contacts object to the ArrayList (in this case we are
				// the ArrayList).

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mEvents;
	}

	public void convert() {
		apiKey = "QdgJmSnWjK";
		langCode = "en";

		// Checking are all fields set
		if (fileName != null && !apiKey.equals("") && !langCode.equals("")) {
			final ProgressDialog dialog = ProgressDialog.show(
					SampleActivity.this, "Loading ...", "Converting to text.",
					true, false);
			final Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					final OCRServiceAPI apiClient = new OCRServiceAPI(apiKey);
					apiClient.convertToText(langCode, fileName);

					// Doing UI related code in UI thread
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							dialog.dismiss();

							// Showing response dialog
							final AlertDialog.Builder alert = new AlertDialog.Builder(
									SampleActivity.this);

							String mEventInfo = apiClient.getResponseText();
							String[] mSplitInfo = mEventInfo.split("\n");

							String title = "";
							String date = "";
							String location = "";

							boolean first = false;
							for (int i = 0; i < mSplitInfo.length; i++) {
								if (!mSplitInfo[i].contains("-")) {
									title = title + mSplitInfo[i];
								} else if (mSplitInfo[i].contains("-")) {
									if (first == false) {
										first = true;
										int c = mSplitInfo[i].indexOf("-");
										date = mSplitInfo[i].substring(c + 1);
									}

									location = mSplitInfo[i + 1];
									break;

								}
							}

							final DateFormat df = new SimpleDateFormat(
									"MMM dd yyyy");
							final Calendar c = Calendar.getInstance();
							try {
								Log.i("anup_check", " title" + "" + title
										+ " location" + location);
								c.setTime(tryParse(date));
								Log.i("anup_Year = ",
										"" + c.get(Calendar.YEAR) + "Month = "
												+ (c.get(Calendar.MONTH) + 1)
												+ "" + "Day = "
												+ c.get(Calendar.DAY_OF_MONTH));

							} catch (Exception e) {
								e.printStackTrace();
							}

							alert.setMessage(apiClient.getResponseText());
							alert.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
										}
									});

							// Setting dialog title related from response code
							if (apiClient.getResponseCode() == RESPONSE_OK) {
								alert.setTitle("Success");
							} else {
								alert.setTitle("Failed");
							}

							// alert.show();
							// Calendar beginTime = Calendar.getInstance();
							// beginTime.set(2012, 0, 19, 7, 30);
							// Calendar endTime = Calendar.getInstance();
							// endTime.set(2012, 0, 19, 8, 30);

							if (title.equals("") || date.equals("")) {

								Toast.makeText(getBaseContext(),
										"Please scan again", Toast.LENGTH_SHORT)
										.show();
							} else {

								Intent intent = new Intent(Intent.ACTION_INSERT)
										.setData(Events.CONTENT_URI)
										.putExtra(
												CalendarContract.EXTRA_EVENT_BEGIN_TIME,
												c.getTimeInMillis())
										.putExtra(Events.TITLE, title)
										.putExtra(Events.EVENT_LOCATION,
												location);
								startActivity(intent);
							}
						}
					});
				}
			});
			thread.start();
		} else {
			Toast.makeText(SampleActivity.this, "All data are required.",
					Toast.LENGTH_SHORT).show();
		}
	}

	Date tryParse(String dateString) {
		for (String formatString : formatStrings) {
			try {
				return new SimpleDateFormat(formatString).parse(dateString);
			} catch (ParseException e) {

			}
		}

		return null;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// from gallery
		if (requestCode == IMAGE_PICKER_REQUEST && resultCode == RESULT_OK) {
			fileName = getRealPathFromURI(data.getData());
			convert();

		}// from camera
		else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
			Bitmap photo = (Bitmap) data.getExtras().get("data");
			Uri tempUri = getImageUri(getApplicationContext(), photo);
			fileName = getRealPathFromURI(tempUri);

			convert();

		}

	}

	public Uri getImageUri(Context inContext, Bitmap inImage) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		String path = Images.Media.insertImage(inContext.getContentResolver(),
				inImage, "Title", null);
		return Uri.parse(path);
	}

	public String getRealPathFromURI(Uri uri) {
		Cursor cursor = getContentResolver().query(uri, null, null, null, null);
		cursor.moveToFirst();
		int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
		return cursor.getString(idx);
	}

	/*
	 * Cuts selected file name from real path to show in screen.
	 */
	private String getStringNameFromRealPath(final String bucketName) {
		return bucketName.lastIndexOf('/') > 0 ? bucketName
				.substring(bucketName.lastIndexOf('/') + 1) : bucketName;
	}

	private static final String DATE_TIME_FORMAT = "yyyy MMM dd, HH:mm:ss";

	public static String getDateTimeStr(int p_delay_min) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
		if (p_delay_min == 0) {
			return sdf.format(cal.getTime());
		} else {
			Date l_time = cal.getTime();
			l_time.setMinutes(l_time.getMinutes() + p_delay_min);
			return sdf.format(l_time);
		}
	}

	public static String getDateTimeStr(String p_time_in_millis) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
		Date l_time = new Date(Long.parseLong(p_time_in_millis));
		return sdf.format(l_time);
	}

	// write text to file
	public void WriteBtn(String fname) {

		try {

			if (!myFile.exists()) {
				myFile.createNewFile();
			}

			FileWriter fw = new FileWriter(myFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(fname + ",");
			bw.flush();
			bw.newLine();
			bw.close();

			// display file saved message
			Toast.makeText(getBaseContext(), "File saved successfully!",
					Toast.LENGTH_SHORT).show();
			finish();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}