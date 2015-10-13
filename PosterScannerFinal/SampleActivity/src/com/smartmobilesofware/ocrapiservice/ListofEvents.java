// Homework Assignment Number 6 Final Project                                               //
// Class: CS6301 User Interface Design                                        //

//----------------------------------------------------------------------------//
// Name: ARNAV SHARMA               Net ID: axs144130                         //
//----------------------------------------------------------------------------//
// Date created: 04.19.2015                                                   //
////////////////////////////////////////////////////////////////////////////////
//----------------------------------------------------------------------------//


// This part of code calls the Calendar API and reads events for a particular range of dates. 	              //

package com.smartmobilesofware.ocrapiservice;
/**
 * Created by Arjun on 4/19/2015.
 */
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

public class ListofEvents extends Activity {

	ListView mListView;
	ArrayList<EventObj> mList;
	Adapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);

		mListView = (ListView) findViewById(R.id.sampleListView);
		mList = new ArrayList<EventObj>();
		
		mAdapter = new Adapter(mList, this);
		getEvents();
		mListView.setAdapter(mAdapter);

	}

	void getEvents() {

		// Starting image picker activity
		String[] projection = new String[] {
				CalendarContract.Events.CALENDAR_ID,
				CalendarContract.Events.TITLE,
				CalendarContract.Events.DESCRIPTION,
				CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND,
				CalendarContract.Events.ALL_DAY,
				CalendarContract.Events.EVENT_LOCATION };

		// 0 = January, 1 = February, ...

		Calendar startTime = Calendar.getInstance();
		startTime.set(2015, 00, 01, 00, 00);

		Calendar endTime = Calendar.getInstance();
		endTime.set(2016, 00, 01, 00, 00);

		// the range is all data from 2014

		String selection = "(( " + CalendarContract.Events.DTSTART + " >= "
				+ startTime.getTimeInMillis() + " ) AND ( "
				+ CalendarContract.Events.DTSTART + " <= "
				+ endTime.getTimeInMillis() + " ))";

		Cursor cursor = getBaseContext().getContentResolver().query(
				CalendarContract.Events.CONTENT_URI, projection, selection,
				null, null);

		String[] l_projection = new String[] { "title", "eventLocation" };

		if (cursor.moveToFirst()) {
			int l_cnt = 0;
			

			int l_colTitle = cursor.getColumnIndex(l_projection[0]);
			int location = cursor.getColumnIndex(l_projection[1]);

			do {
				String	l_title = cursor.getString(l_colTitle);
				String location_name = cursor.getString(location);
				EventObj mEventObj = new EventObj(l_title, location_name);
				mList.add(mEventObj);


			} while (cursor.moveToNext());

		}

	}

}
