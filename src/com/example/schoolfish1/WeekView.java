package com.example.schoolfish1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.example.schoolfish1.MonthView.GridCellAdapter;
import com.example.schoolfish1.MonthView.WeekTabAdapter;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class WeekView extends Activity implements OnClickListener {
	
	private EventsDataSource datasource;
	private Button currentWeek;
	private ImageView prevWeek;
	private ImageView nextWeek;
	private GridView weekdayHeaderView; 
	private GridView weekGridView;
	private GridView weekMarginView; 
	//private WeekGridAdapter weekGridAdapter;
	//private WeekMarginAdapter weekMarginAdapter; 
	private int thisWeek;
	private int thisMonth;
	private int thisYear; 
	private final DateFormat dateFormatter = new DateFormat();
	private static final String dateTemplate = "MMMM yyyy";
	
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
		  
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view_scroll);
        
        Bundle b = getIntent().getExtras();
        thisWeek = b.getInt("week");
        thisMonth = b.getInt("month"); 
        thisYear = b.getInt("year"); 
        
        prevWeek = (ImageView) this.findViewById(R.id.prevWeek);
		prevWeek.setOnClickListener(this);

		currentWeek = (Button) this.findViewById(R.id.currentWeek);
		currentWeek.setText(thisMonth + " " + thisYear);

		nextWeek = (ImageView) this.findViewById(R.id.nextWeek);
		nextWeek.setOnClickListener(this);
        
	    datasource = new EventsDataSource(this);
	    datasource.open();
	    
	    final String[] weekdays = new String[] {"Mon", "Tue", "Wed", "Thu", "Fri"}; 
		weekdayHeaderView = (GridView) findViewById(R.id.weekHeader);
		ArrayAdapter<String> weekdayAdapter = new ArrayAdapter<String>(this, R.layout.calendar_header_cell, weekdays);
		weekdayHeaderView.setAdapter(weekdayAdapter); 
		
		/*
		weekGridView = (GridView) this.findViewById(R.id.weekCalendar);
		weekGridAdapter = new WeekGridAdapter(getApplicationContext(), R.id.week_day_gridcell, thisWeek, thisMonth, thisYear);
		weekGridAdapter.notifyDataSetChanged();
		weekGridView.setAdapter(weekGridAdapter);
		
		
		weekMarginView = (GridView) this.findViewById(R.id.weekTimes); 
		weekMarginAdapter = new WeekMarginAdapter(getApplicationContext(), R.id.week_margin); 
		weekMarginAdapter.notifyDataSetChanged(); 
		weekMarginView.setAdapter(weekMarginAdapter); 	
	    */
	    /*
	     Game Plan:
	     Create a 3-dimensional array for every month(access code/sql id = mmyy?)
	     mm-yy = array of weeks
	     week = array of days
	     day = array of times from 7am-8pm
	     */
	    
	    /*
	    List<Event> values = datasource.getAllEvents();

	    // Use the SimpleCursorAdapter to show the
	    // elements in a ListView
	    ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(this,
	        android.R.layout.simple_list_item_1, values);
	    setListAdapter(adapter);
	  }

	  // Will be called via the onClick attribute
	  // of the buttons in main.xml
	  public void onClick(View view) {
	    @SuppressWarnings("unchecked")
	    ArrayAdapter<Event> adapter = (ArrayAdapter<Event>) getListAdapter();
	    Event event = null;
	    switch (view.getId()) {
	    case R.id.add:
	      String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
	      int nextInt = new Random().nextInt(3);
	      // Save the new comment to the database
	      event = datasource.createEvent(comments[nextInt]);
	      adapter.add(event);
	      break;
	    case R.id.delete:
	      if (getListAdapter().getCount() > 0) {
	        event = (Event) getListAdapter().getItem(0);
	        datasource.deleteComment(event);
	        adapter.remove(event);
	      }
	      break;
	    }
	    adapter.notifyDataSetChanged();
	    */
	  }
	  
	  
		/**
		 * 
		 * @param month
		 * @param year
		 */
	  /*
		private void setGridCellAdapterToDate(int month, int year)
			{
				adapter = new GridCellAdapter(getApplicationContext(), R.id.calendar_day_gridcell, month, year);
				calendar.set(year, month - 1, calendar.get(Calendar.DAY_OF_MONTH));
				currentMonth.setText(dateFormatter.format(dateTemplate, calendar.getTime()));
				adapter.notifyDataSetChanged();
				calendarView.setAdapter(adapter);
			}
	   */
	  
	@Override
	public void onClick(View v)
		{
		
			if (v == prevWeek)
				{
					thisWeek--; 
				/*
					if (thisWeek <= 0)
						{
							
						}
					else
						{
							
						}
					setWeekGridAdapterToDate(month, year);
				*/
				}
			if (v == nextWeek)
				{
					thisWeek++; 
				/*
					if (week > 11)
						{

						}
					else
						{

						}
					Log.d(tag, "Setting Next Month in GridCellAdapter: " + "Month: " + month + " Year: " + year);
					setGridCellAdapterToDate(month, year);
					*/
				}

		}


	  @Override
	  protected void onResume() {
	    datasource.open();
	    super.onResume();
	  }

	  @Override
	  protected void onPause() {
	    datasource.close();
	    super.onPause();
	  }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_week_view, menu);
        return true;
    }
/*    
	public class WeekMarginAdapter extends BaseAdapter implements OnClickListener{
		private final Context _context;
		private String[] marginTimes = new String[13];  
		private Button weekMargin; 

		//Constructor
		public WeekMarginAdapter(Context applicationContext, int weekTabView) {
			this._context = applicationContext;
			//initialise String array of margin times
			for(int i = 0; i < marginTimes.length; i++){
				String time = i + 7 + ":00"; 
				marginTimes[i] = time;  
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;	
			
			if (v == null)
				{
					LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					v = inflater.inflate(R.layout.week_margin, parent, false);
				}

			
			weekMargin = (Button)v.findViewById(R.id.week_margin);
			weekMargin.setText(marginTimes[position]);
			weekMargin.setTag(position); 

			return v;
		}

		@Override
		public void onClick(View v) {
			/*int weekNum = (Integer) v.getTag() + 1;
			
			Bundle b = new Bundle(); //create new bundle to pass info via intent into new activity
		    b.putInt("year", year); //store year in bundle
		    b.putInt("month", month); //store month in bundle

		    Intent newActivity = new Intent("WeekView");  //create new intent to start WeekView Activity   
		    
			Log.d(tag, "Parsed Week Number: " + weekNum);
			Log.d(tag, "This Year: " + year); 
			Log.d(tag, "This Month: " + month); 
			//if user clicks on week tab, start new activity of that week's calendar 
				
				switch(weekNum){
				    case 1:    
						    b.putInt("week", 0); //pass week number
			        		break;
			        		
				    case 2: 
						    b.putInt("week", 1); //pass week number
			        		break;
			        		
				    case 3:   
						    b.putInt("week", 2); //pass week number
			        		break;
			        		
				    case 4:   
						    b.putInt("week", 3); //pass week number
			        		break;
			        		
				    case 5:   
						    b.putInt("week", 4); //pass week number
			        		break;
			       
				    case 6: 
						    b.putInt("week", 5); //pass week number
			        		break;

				}//end switch
				
				//put bundle in Intent and start new activity with intent 
			    newActivity.putExtras(b); //Put your id to your next Intent
        		startActivity(newActivity); //start activity with intent 
        		*/
			
	//	}
		//end onClick()
/*		
		public int getCount() {
			return marginTimes.length;
		}

		public Object getItem(int position) {
			return marginTimes[position];
		}

		public long getItemId(int position) {
			return position;
		}
		
	}*/
	//end WeekGridAdapter inner class
	
	
	// Inner Class
/*	public class WeekGridAdapter extends BaseAdapter //implements OnClickListener
		{
			private final Context _context;

			private final List<String> list;
			private Button gridcell;
			
			// Days in Current Month
			public WeekGridAdapter(Context context, int textViewResourceId, int week, int month, int year)
				{
					super();
					this._context = context;
					this.list = new ArrayList<String>(65);
					for(int i = 0; i < 65; i++){
						list.add(" "); 
					}

					//printWeek(week, month, year);

				}

			public String getItem(int position)
				{
					return list.get(position);
				}

			@Override
			public int getCount()
				{
					return list.size();
				}
*/
			/**
			 * Prints Month
			 * 
			 * @param mm
			 * @param yy
			 */
			/*private void printWeek(int w, int mm, int yy)
				{
					//add events/weeks to list? 

				}
			*/
/*
			@Override
			public long getItemId(int position)
				{
					return position;
				}

			@Override
			public View getView(int position, View convertView, ViewGroup parent)
				{
					View row = convertView;
					if (row == null)
						{
							LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
							row = inflater.inflate(R.layout.week_day_gridcell, parent, false);
						}

					// Get a reference to the Day gridcell
					gridcell = (Button) row.findViewById(R.id.week_day_gridcell);
					//gridcell.setOnClickListener(this);

					// Set the Day GridCell
					gridcell.setText(" ");
					/*gridcell.setTag(theday + "-" + themonth + "-" + theyear);
					Log.d(tag, "Setting GridCell " + theday + "-" + themonth + "-" + theyear);
					 */
					
					//return row;
				//}

		//}//end WeekGridAdapter class 
	
	

}
