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
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class WeekView extends Activity implements OnClickListener {
	
	private EventsDataSource datasource;
	private Button currentWeek;
	private ImageView prevWeek;
	private ImageView nextWeek;
	private GridView weekdayHeaderView; 
	//private GridView weekGridView;
	//private GridView weekMarginView; 
	//private WeekGridAdapter weekGridAdapter;
	//private WeekMarginAdapter weekMarginAdapter; 
	private int thisMonth;
	private int thisYear; 
	private int firstDayOfWeek; 
	private final DateFormat dateFormatter = new DateFormat();
	private static final String dateTemplate = "MMMM yyyy";
	private GregorianCalendar myCal; 
	private String[] weekdays; 
	private MyDate[] weekdates = new MyDate[5]; 
	private final String tag = "WeekView"; 
	private WeekHeaderAdapter weekHeaderAdapter; 
	
	//database access
	private List<Event> allEvents; 
	private EventsDataSource dbHelper; 
	private RelativeLayout rl; 
	
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
		  
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view_scroll);
        
        Bundle b = getIntent().getExtras();
        firstDayOfWeek= b.getInt("firstDayOfWeek"); 
        thisMonth = b.getInt("month"); 
        thisYear = b.getInt("year"); 
        
        Log.d(tag, "First day of week: " + firstDayOfWeek); 
        Log.d(tag, "This month: " + thisMonth); 
        Log.d(tag, "This year: " + thisYear); 
        
        if(firstDayOfWeek > 20){
        	//then the first week starts with last month's calendar
        	myCal = new GregorianCalendar(thisYear, thisMonth - 2, firstDayOfWeek); 
        }else{
        	myCal = new GregorianCalendar(thisYear, thisMonth - 1, firstDayOfWeek); 
        }
        
        //Week navigation and display views 
        prevWeek = (ImageView) this.findViewById(R.id.prevWeek);
		prevWeek.setOnClickListener(this);

		currentWeek = (Button) this.findViewById(R.id.currentWeek);
		currentWeek.setText(dateFormatter.format(dateTemplate, myCal.getTime()));

		nextWeek = (ImageView) this.findViewById(R.id.nextWeek);
		nextWeek.setOnClickListener(this);
		
		weekdayHeaderView = (GridView) findViewById(R.id.weekHeader);
		weekHeaderAdapter = new WeekHeaderAdapter(getApplicationContext(), R.layout.week_header_cell); 
		weekHeaderAdapter.notifyDataSetChanged(); 
		weekdayHeaderView.setAdapter(weekHeaderAdapter);
		
		rl = (RelativeLayout) findViewById(R.id.week_relative_layout); 
		
	    dbHelper = new EventsDataSource(this);
	    dbHelper.open();
	    
	    allEvents = dbHelper.getAllEvents(); 
		for(Event thisEvent : allEvents){
			for(MyDate dt : weekdates){
				if(dt.getDate() == thisEvent.getDate() && dt.getMonthNum() == thisEvent.getMonth() && dt.getYear() == thisEvent.getYear() ){
					addEventView(this, rl, thisEvent);
				}
			}
			Log.d("Events present in databases have ID: ", " " + thisEvent.getId()); 
		}
	    
	  }
	 

	  
	@Override
	public void onClick(View v)
		{
		
			if (v == prevWeek)
				{
					myCal.add(myCal.DAY_OF_MONTH, -12);//skip to Sunday before
			        setContentView(R.layout.activity_week_view_scroll);
					
			        for(int i = 0; i < 5; i++){
			        	myCal.add(myCal.DAY_OF_MONTH, 1); 
			        	weekdates[i] = new MyDate(myCal.get(myCal.DAY_OF_WEEK), myCal.get(myCal.DATE), myCal.get(myCal.MONTH), myCal.get(myCal.YEAR)); 
			        }
			        
			        weekdays = new String[] {"Mon " + weekdates[0].getDate(), "Tue " + weekdates[1].getDate(), "Wed " + weekdates[2].getDate(), "Thu " + weekdates[3].getDate(), "Fri " + weekdates[4].getDate()};
			        weekHeaderAdapter.notifyDataSetChanged(); 
			        
			      //redraw entire layout
					ViewGroup vg = (ViewGroup) findViewById (R.id.activity_week_view_scroll);
					vg.invalidate();

			        //Week navigation and display views 
			        prevWeek = (ImageView) this.findViewById(R.id.prevWeek);
					prevWeek.setOnClickListener(this);

					currentWeek = (Button) this.findViewById(R.id.currentWeek);
					currentWeek.setText(dateFormatter.format(dateTemplate, myCal.getTime()));

					nextWeek = (ImageView) this.findViewById(R.id.nextWeek);
					nextWeek.setOnClickListener(this);
					
					rl = (RelativeLayout) findViewById(R.id.week_relative_layout); 
					
				    dbHelper = new EventsDataSource(this);
				    dbHelper.open();
				    
				    allEvents = dbHelper.getAllEvents(); 
					for(Event thisEvent : allEvents){
						for(MyDate dt : weekdates){
							if(dt.getDate() == thisEvent.getDate() && dt.getMonthNum() == thisEvent.getMonth() && dt.getYear() == thisEvent.getYear() ){
								addEventView(this, rl, thisEvent);
							}
						}
						Log.d("Events present in databases have ID: ", " " + thisEvent.getId()); 
					}
					

				}
			if (v == nextWeek)
				{
					myCal.add(myCal.DAY_OF_MONTH, 2);//skip from Friday to Sunday
					
					setContentView(R.layout.activity_week_view_scroll);
					 
			        for(int i = 0; i < 5; i++){
			        	myCal.add(myCal.DAY_OF_MONTH, 1); 
			        	weekdates[i] = new MyDate(myCal.get(myCal.DAY_OF_WEEK), myCal.get(myCal.DATE), myCal.get(myCal.MONTH), myCal.get(myCal.YEAR)); 
			        }
					
			        weekdays = new String[] {"Mon " + weekdates[0].getDate(), "Tue " + weekdates[1].getDate(), "Wed " + weekdates[2].getDate(), "Thu " + weekdates[3].getDate(), "Fri " + weekdates[4].getDate()};
			        weekHeaderAdapter.notifyDataSetChanged(); 

					ViewGroup vg = (ViewGroup) findViewById (R.id.activity_week_view_scroll);
					vg.invalidate();
					
			        //Week navigation and display views 
			        prevWeek = (ImageView) this.findViewById(R.id.prevWeek);
					prevWeek.setOnClickListener(this);

					currentWeek = (Button) this.findViewById(R.id.currentWeek);
					currentWeek.setText(dateFormatter.format(dateTemplate, myCal.getTime()));

					nextWeek = (ImageView) this.findViewById(R.id.nextWeek);
					nextWeek.setOnClickListener(this);
					
					rl = (RelativeLayout) findViewById(R.id.week_relative_layout); 
					
				    dbHelper = new EventsDataSource(this);
				    dbHelper.open();
				    
				    allEvents = dbHelper.getAllEvents(); 
					for(Event thisEvent : allEvents){
						for(MyDate dt : weekdates){
							if(dt.getDate() == thisEvent.getDate() && dt.getMonthNum() == thisEvent.getMonth() && dt.getYear() == thisEvent.getYear() ){
								addEventView(this, rl, thisEvent);
							}
						}
						Log.d("Events present in databases have ID: ", " " + thisEvent.getId()); 
					}

				}

		}


	  @Override
	  protected void onResume() {
	    dbHelper.open();
	    super.onResume();
	  }

	  @Override
	  protected void onPause() {
	    dbHelper.close();
	    super.onPause();
	  }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_week_view, menu);
        return true;
    }

    //Inner class
	public class WeekHeaderAdapter extends BaseAdapter implements OnClickListener{
		private final Context _context;
		private Button weekHeader; 

		//Constructor
		public WeekHeaderAdapter(Context applicationContext, int weekTabView) {
			this._context = applicationContext;
			
			weekdates[0] = new MyDate(myCal.get(myCal.DAY_OF_WEEK), myCal.get(myCal.DATE), myCal.get(myCal.MONTH), myCal.get(myCal.YEAR)); 
	        for(int i = 1; i < 5; i++){
	        	myCal.add(myCal.DAY_OF_MONTH, 1); 
	        	weekdates[i] = new MyDate(myCal.get(myCal.DAY_OF_WEEK), myCal.get(myCal.DATE), myCal.get(myCal.MONTH), myCal.get(myCal.YEAR));
	        }
		    
		    //Weekdays Header Bar 
		    weekdays = new String[] {"Mon " + weekdates[0].getDate(), "Tue " + weekdates[1].getDate(), "Wed " + weekdates[2].getDate(), "Thu " + weekdates[3].getDate(), "Fri " + weekdates[4].getDate()}; 
			
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;

			if (v == null)
				{
					LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					v = inflater.inflate(R.layout.week_header_cell, parent, false);
				}

			
			// Get a reference to the Day gridcell
			weekHeader = (Button) v.findViewById(R.id.week_header_cell);
			weekHeader.setOnClickListener(this);
			
			// Set the Day GridCell
			weekHeader.setText(weekdays[position]);
			weekHeader.setTag(position); 

			return v;
		}

		@Override
		public void onClick(View v) {
			int position = (Integer) v.getTag();
			
			Log.d(tag, "Gridview Clicked");
        	Bundle b = new Bundle(); //create new bundle to pass info via intent into new activity
        	b.putInt("date", weekdates[position].getDate()); 
        	b.putInt("weekday", weekdates[position].getWeekdayIndex()); 
        	b.putInt("month", weekdates[position].getMonthNum()); 
        	b.putInt("year", weekdates[position].getYear());
			Intent i = new Intent("DayView");
			i.putExtras(b); 
            startActivity(i); 

		}//end onClick()
		
		public int getCount() {
			return weekdates.length;
		}

		public Object getItem(int position) {
			return weekdates[position];
		}

		public long getItemId(int position) {
			return position;
		}
		
	}//end custom adapter for weekHeaderView
	
	public void addEventView(Context ctx, RelativeLayout rl, Event event){
		int width = 58;  
				
		TextView tv = new TextView(ctx);
		tv.setBackgroundResource(R.color.orange);
		tv.setText(event.getSubject() + "\n" + event.getEventType() + "\n" + event.getLocation()); 
		tv.setHeight((int) (event.getDuration() * getResources().getDisplayMetrics().density)); 
		tv.setWidth((int) (width * getResources().getDisplayMetrics().density)); 
		tv.setTag(event); 
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); 
		
		int topMargin = 0; 
		String[] arr = event.getStartTime().split(":"); 
		int startHour = Integer.parseInt(arr[0]); 
		int startMinutes = Integer.parseInt(arr[1]); 
		switch(startHour){
			case 7 : 	topMargin += 1; 
						break; 
			case 8: 	topMargin += 61; 
						break; 
			case 9: 	topMargin += 122; 
						break;
			case 10: 	topMargin += 182; 
						break;
			case 11: 	topMargin += 243; 
						break; 
			case 12: 	topMargin += 304; 
						break; 
			case 13: 	topMargin += 364; 
						break; 
			case 14: 	topMargin += 425; 
						break;
			case 15: 	topMargin += 486; 
						break; 
			case 16: 	topMargin += 547; 
						break; 
			case 17: 	topMargin += 607; 
						break; 
			case 18: 	topMargin += 668;
						break; 
			case 19: 	topMargin += 729; 
						break; 
		}
		
		topMargin += startMinutes; 
		
		int leftMargin = 24; 
		int dt = event.getDate(); 
		int mth = event.getMonth(); 
		int yr = event.getYear(); 
		GregorianCalendar cal = new GregorianCalendar(yr, mth, 17); //create new calendar with event's date to get day of week 
		int weekdayIndex = cal.get(cal.DAY_OF_WEEK); 
		/*Log.d("Week", "Date: " + cal.get(cal.DATE)); 
		Log.d("Week", "Month: " + cal.get(cal.MONTH)); 
		Log.d("Week", "Year: " + cal.get(cal.YEAR)); 
		*/
		Log.d("Week", "Day of week: " + cal.get(cal.DAY_OF_WEEK)); 

		switch(weekdayIndex){
			//case 1 : 	leftMargin += 1; //Sun
						//break; 
			case 2: 	leftMargin += 0; //Mon
						break; 
			case 3: 	leftMargin += 61; //Tue
						break;
			case 4: 	leftMargin += 122; //Wed
						break;
			case 5: 	leftMargin += 182; //Thu
						break; 
			case 6: 	leftMargin += 243; //Fri
						break; 
			//case 7: 	leftMargin += 304; //Sat
						//break;
			default: 	break; 
		}
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.leftMargin = (int) (leftMargin * getResources().getDisplayMetrics().density);
		params.topMargin = (int) (topMargin * getResources().getDisplayMetrics().density);
		tv.setOnClickListener(new OnClickListener() {
			   public void onClick(View v) {
				   Log.d("View clicked", ((Event)v.getTag()).getDescription()); 
				   Event event = (Event)v.getTag(); 
				   Bundle b = new Bundle(); //create new bundle to pass info via intent into new activity
				   b.putInt("year", event.getYear()); //store year in bundle
				   b.putInt("month", event.getMonth()); //store month in bundle
				   b.putInt("date", event.getDate());
				   b.putInt("duration", event.getDuration()); 
				   b.putString("startTime", event.getStartTime());
				   b.putString("location", event.getLocation()); 
				   b.putString("subject", event.getSubject()); 
				   b.putString("eventType", event.getEventType()); 
				   b.putString("description", event.getDescription()); 
				   b.putInt("id", event.getId()); 

				   Intent newActivity = new Intent("EventView");
				   newActivity.putExtras(b); //Put your id to your next Intent
	        	   startActivityForResult(newActivity, 0);
			   }
		});//end OnClickListener
		
		rl.addView(tv, params);
	}

}
