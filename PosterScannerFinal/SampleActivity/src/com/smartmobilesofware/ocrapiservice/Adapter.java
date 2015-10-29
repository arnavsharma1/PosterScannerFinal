// Homework Assignment Number 5                                               //
// Class: CS6301 User Interface Design                                        //

// Name: ARNAV SHARMA               Net ID: axs144130                         //
//----------------------------------------------------------------------------//
// Date created: 04.19.2015                                                   //
////////////////////////////////////////////////////////////////////////////////
//----------------------------------------------------------------------------//


// This program displays the saved events in a list view on the view events screen. //

package com.smartmobilesofware.ocrapiservice;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {

	ArrayList<EventObj> mEventObjs;
	Activity mContext;

	public Adapter(ArrayList<EventObj> mArrayList, Activity context) {
		this.mEventObjs = mArrayList;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mEventObjs.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mEventObjs.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub

		LayoutInflater li = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row = li.inflate(R.layout.list_layout, null);

		TextView title, location;

		title = (TextView) row.findViewById(R.id.name);
		location = (TextView) row.findViewById(R.id.location);
		title.setText(mEventObjs.get(arg0).name);
		location.setText(mEventObjs.get(arg0).location);
        if ( arg0 % 2 == 0)
            row.setBackgroundResource(R.drawable.listview_selector_even);
        else
            row.setBackgroundResource(R.drawable.listview_selector_odd);
		return row;
	}

}
