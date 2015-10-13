// Homework Assignment Number 6 Final Project                                               //
// Class: CS6301 User Interface Design                                        //

//----------------------------------------------------------------------------//
// Name: ARNAV SHARMA               Net ID: axs144130                         //
//----------------------------------------------------------------------------//
// Date created: 04.19.2015                                                   //
////////////////////////////////////////////////////////////////////////////////
//----------------------------------------------------------------------------//


// This program displays the saved events in a list view on the view events screen. //

package com.smartmobilesofware.ocrapiservice;
/**
 * Created by Arnav on 4/19/2015.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class DataAdapter extends BaseAdapter {
    ArrayList<Eve> mEvents ;
    Context context;
    DataAdapter( Context mContext,ArrayList<Eve> events)
    {
        this.mEvents = events;

        this.context = mContext;
    }

    @Override
    public int getCount() {

        return mEvents.size();
    }

    @Override
    public Object getItem(int position) {
        return mEvents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)   context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       // View view = getView(position, convertView, parent);
        View view = inflater.inflate(R.layout.mylayout, null);

        TextView mTextView = (TextView) view.findViewById(R.id.textView);
        
        mTextView.setText(mEvents.get(position).getName());

        if ( position % 2 == 0)
            view.setBackgroundResource(R.drawable.listview_selector_even);
        else
            view.setBackgroundResource(R.drawable.listview_selector_odd);

        return view;
    }


}
