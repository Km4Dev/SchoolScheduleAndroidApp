package com.example.schoolfish1;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DayView extends Activity implements OnClickListener{

	private Button currentDay; 
	private ImageView nextDay; 
	private ImageView prevDay; 
	private final DateFormat dateFormatter = new DateFormat();
	private static final String dateTemplate = "D MMMM yyyy";
	private GregorianCalendar myCal; 
	private MyDate day; 
	private RelativeLayout rl; 
	private EventsDataSource dbHelper; 
	private List<Event> allEvents; 
	private static final int REQUEST_CODE = 1; 
	private int date;
	private int weekday;
	private int month;
	private int year; 
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_view);
        
        Bundle b = getIntent().getExtras();
        date = b.getInt("date"); 
        weekday = b.getInt("weekday");
        month = b.getInt("month"); 
        year = b.getInt("year"); 
        
        myCal = new GregorianCalendar(year, month, date); 
        
        day = new MyDate(myCal.get(myCal.DAY_OF_WEEK), myCal.get(myCal.DATE), myCal.get(myCal.MONTH), myCal.get(myCal.YEAR)); //date the user is currently on 
        
        currentDay = (Button) this.findViewById(R.id.currentDay);
		currentDay.setText(day.getMonthString(month) + " " + date + " " + year);
		
		nextDay = (ImageView) this.findViewById(R.id.nextDay); 
		nextDay.setOnClickListener(this); 
		
		prevDay = (ImageView) this.findViewById(R.id.prevDay); 
		prevDay.setOnClickListener(this); 
		
		rl = (RelativeLayout) findViewById(R.id.day_event_frame);
		
		//open SQLite database to read events
		dbHelper = new EventsDataSource(this);
		dbHelper.open(); 
		/*
		Event newEvent1 = new Event(2012, 10, 19, "9:00", 60, "CPM 200", "Data Structures", "Lecture", "Lecture for Data Structures, followed by a 1 hour lab");
		newEvent1 = dbHelper.createEvent(newEvent1); 
		Event newEvent2 = new Event(2012, 10, 19, "11:00", 90, "CPM 204", "Computer Architecture", "Lecture", "Lecture for Comp Arch");
		newEvent2 = dbHelper.createEvent(newEvent2);
		
		Event newEvent3 = new Event(2012, 10, 19, "13:00", 60, "CPM 200", "Data Structures", "Lecture", "Lecture for Data Structures, followed by a 1 hour lab");
		newEvent3 = dbHelper.createEvent(newEvent3); 
		Event newEvent4 = new Event(2012, 10, 19, "15:00", 90, "CPM 204", "Computer Architecture", "Lecture", "Lecture for Comp Arch");
		newEvent4 = dbHelper.createEvent(newEvent4);
		*/
		//dbHelper.deleteEvent(25);
		
		//myCal.get(myCal.DAY_OF_WEEK), myCal.get(myCal.DATE), myCal.get(myCal.MONTH), myCal.get(myCal.YEAR)
		allEvents = dbHelper.getAllEvents(); 
		for(Event thisEvent : allEvents){
			if(myCal.get(myCal.DATE) == thisEvent.getDate() && myCal.get(myCal.MONTH) == thisEvent.getMonth() && myCal.get(myCal.YEAR) == thisEvent.getYear() ){
				addEventView(this, rl, thisEvent);
			}
			Log.d("Events present in databases have ID: ", " " + thisEvent.getId()); 
		}


	}
	
	@Override
	public void onClick(View v) {
		
		if(v == prevDay){
			myCal.add(myCal.DATE, -1); 
			Log.d("New date is ", + myCal.get(myCal.MONTH) + " " + myCal.get(myCal.DATE)); 
			
	        setContentView(R.layout.activity_day_view);
	        currentDay = (Button) this.findViewById(R.id.currentDay);
			currentDay.setText(day.getMonthString( myCal.get(myCal.MONTH)) + " " + myCal.get(myCal.DATE) + " " + myCal.get(myCal.YEAR));
			
			nextDay = (ImageView) this.findViewById(R.id.nextDay); 
			nextDay.setOnClickListener(this); 
			
			prevDay = (ImageView) this.findViewById(R.id.prevDay); 
			prevDay.setOnClickListener(this); 
			
			rl = (RelativeLayout) findViewById(R.id.day_event_frame);
			
			//open SQLite database to read events
			dbHelper = new EventsDataSource(this);
	        
	        dbHelper.open(); //open db connection    
			allEvents = dbHelper.getAllEvents(); 
			for(Event thisEvent : allEvents){
				if(myCal.get(myCal.DATE) == thisEvent.getDate() && myCal.get(myCal.MONTH) == thisEvent.getMonth() && myCal.get(myCal.YEAR) == thisEvent.getYear() ){
					addEventView(this, rl, thisEvent);
				}
				Log.d("DayView", "Events present in databases have ID: " + thisEvent.getId()); 
			} 
			
		}
		
		if(v == nextDay){
			myCal.add(myCal.DATE, 1); 
			
			Log.d("New date is ", + myCal.get(myCal.MONTH) + " " + myCal.get(myCal.DATE)); 
			
	        setContentView(R.layout.activity_day_view);
	        currentDay = (Button) this.findViewById(R.id.currentDay);
			currentDay.setText(day.getMonthString( myCal.get(myCal.MONTH)) + " " + myCal.get(myCal.DATE) + " " + myCal.get(myCal.YEAR));
			
			nextDay = (ImageView) this.findViewById(R.id.nextDay); 
			nextDay.setOnClickListener(this); 
			
			prevDay = (ImageView) this.findViewById(R.id.prevDay); 
			prevDay.setOnClickListener(this); 
			
			rl = (RelativeLayout) findViewById(R.id.day_event_frame);
			
			//open SQLite database to read events
			dbHelper = new EventsDataSource(this);
	        
	        dbHelper.open(); //open db connection    
			allEvents = dbHelper.getAllEvents(); 
			for(Event thisEvent : allEvents){
				if(myCal.get(myCal.DATE) == thisEvent.getDate() && myCal.get(myCal.MONTH) == thisEvent.getMonth() && myCal.get(myCal.YEAR) == thisEvent.getYear() ){
					addEventView(this, rl, thisEvent);
				}
				Log.d("DayView", "Events present in databases have ID: " + thisEvent.getId()); 
			
			}
		}
	}

	public void addEventView(Context ctx, RelativeLayout rl, Event event){
		int width = 443; 
		int leftMargin = 24;  
				
		TextView tv = new TextView(ctx);
		tv.setBackgroundResource(R.color.orange);
		tv.setText(event.getSubject() + "\n" + event.getEventType() + "\n" + event.getLocation()); 
		tv.setHeight((int) (event.getDuration() * getResources().getDisplayMetrics().density)); 
		tv.setWidth((int) (width * getResources().getDisplayMetrics().density)); 
		tv.setTag(event); 
		
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
	
	public void onPause(){
		dbHelper.close(); //close db connection
		super.onPause(); 
	}//end onPause()
	
	  @Override
	  protected void onResume() {
		dbHelper.open(); //open db connection
	    super.onResume();
	    
		allEvents = dbHelper.getAllEvents(); 
		for(Event thisEvent : allEvents){
			if(myCal.get(myCal.DATE) == thisEvent.getDate() && myCal.get(myCal.MONTH) == thisEvent.getMonth() && myCal.get(myCal.YEAR) == thisEvent.getYear() ){
				addEventView(this, rl, thisEvent);
			}
			Log.d("DayView-onResume", "Events present in databases have ID: " + thisEvent.getId()); 
		} 
	  }//end onResume()


	  @Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
 
	    if (resultCode == 1 && requestCode == 0) {
	      if (intent.hasExtra("result") && intent.getExtras().getString("result").equals("Event Deleted")) {
	        Log.d("onActivityResult", "Event Deleted");
	        
	        setContentView(R.layout.activity_day_view);
	        currentDay = (Button) this.findViewById(R.id.currentDay);
			currentDay.setText(day.getMonthString(month) + " " + date + " " + year);
			
			nextDay = (ImageView) this.findViewById(R.id.nextDay); 
			nextDay.setOnClickListener(this); 
			
			prevDay = (ImageView) this.findViewById(R.id.prevDay); 
			prevDay.setOnClickListener(this); 
			
			rl = (RelativeLayout) findViewById(R.id.day_event_frame);
			
			//open SQLite database to read events
			dbHelper = new EventsDataSource(this);
	        
	        dbHelper.open(); //open db connection    
			allEvents = dbHelper.getAllEvents(); 
			for(Event thisEvent : allEvents){
				if(myCal.get(myCal.DATE) == thisEvent.getDate() && myCal.get(myCal.MONTH) == thisEvent.getMonth() && myCal.get(myCal.YEAR) == thisEvent.getYear() ){
					addEventView(this, rl, thisEvent);
				}
				Log.d("DayView-onResume", "Events present in databases have ID: " + thisEvent.getId()); 
			} 

	      }//end event deleted
	      
	    }
	  } //end onActivityResult
	  
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.activity_day_view, menu);
	        return true;
	    }
}
