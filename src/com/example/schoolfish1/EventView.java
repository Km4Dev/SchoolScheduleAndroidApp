package com.example.schoolfish1;

import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EventView extends Activity implements OnClickListener{
	
	//event info
	private int eventId; 
	//calendar and date display
	private final DateFormat dateFormatter = new DateFormat();
	private static final String dateTemplate = "MMMM dd yyyy";
	GregorianCalendar myCal;
	//private TextView eventInfo; 
	private TextView dateView;
	private TextView timeView;
	private TextView subjectView;
	private TextView eventTypeView; 
	private TextView locationView; 
	private TextView descriptionView; 
	private Button editEvent;
	private Button deleteEvent; 
	private Button createEvent; 
	//dialog constants
	private static final int DELETE_EVENT = 0; 
	//database
	private EventsDataSource dbHelper; 
	private Context ctx = this; 
	
	private static final String tag = "EventView"; 
	
	//date variables
	int year;
	int month; 
	int date; 
	int duration; 
	String startTime; 
	String location;
	String subject; 
	String eventType; 
	String description; 
	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);     
        
        Bundle b = getIntent().getExtras();
        year = b.getInt("year"); 
        month = b.getInt("month"); 
        date = b.getInt("date"); 
        duration = b.getInt("duration");
        startTime = b.getString("startTime");
        location = b.getString("location"); 
        subject = b.getString("subject");
        eventType = b.getString("eventType");
        description = b.getString("description"); 
        eventId = b.getInt("id"); 
        
        myCal = new GregorianCalendar(year, month, date); 
        
        /*eventInfo = (TextView) this.findViewById(R.id.eventInfo);
		eventInfo.setText(
				"Date :           " + dateFormatter.format(dateTemplate, myCal.getTime()) + 
				"\n\nTime :          " + startTime + " for " + duration + " minutes." + 
				"\n\nSubject :      " + subject + 
				"\n\nEvent :          " + eventType + 
				"\n\nLocation :    " + location + 
				"\n\n" + description
		);
		
		    
    <TextView
		android:id="@+id/eventInfo"
		style="@style/event_view_cell"/>	
		
		*/
        
        dateView = (TextView) this.findViewById(R.id.eventTableDate);
        dateView.setText(dateFormatter.format(dateTemplate, myCal.getTime())); 
        
        timeView = (TextView) this.findViewById(R.id.eventTableTime);
        timeView.setText(startTime + " for " + duration + " minutes"); 
        
        subjectView = (TextView) this.findViewById(R.id.eventTableSubject);
        subjectView.setText(subject); 
        
        eventTypeView = (TextView) this.findViewById(R.id.eventTableEvent);
        eventTypeView.setText(eventType);
        
        locationView = (TextView) this.findViewById(R.id.eventTableLocation);
        locationView.setText(location);
        
        descriptionView = (TextView) this.findViewById(R.id.eventTableDescription);
        descriptionView.setText(description); 
        
        createEvent = (Button) this.findViewById(R.id.createEventView); 
        createEvent.setOnClickListener(this); 
        createEvent.setTag("create"); 
        
        editEvent = (Button) this.findViewById(R.id.editEventView); 
        editEvent.setOnClickListener(this); 
        editEvent.setTag("edit"); 
        
        deleteEvent = (Button) this.findViewById(R.id.deleteEventView);
        deleteEvent.setOnClickListener(this); 
		deleteEvent.setTag("delete"); 
	}


	@Override
	public void onClick(View v) {
		
		String tag = (String) v.getTag(); 
		Log.d("View pressed", tag); 
		
		if(tag.equals("edit")){
			//create intent to event EDIT activity 
			Log.d(tag, "Button pressed - Edit"); 
			Bundle b = new Bundle(); //create new bundle to pass info via intent into new activity
			b.putInt("id", eventId); 
			b.putInt("year", year); 
			b.putInt("month", month); 
			b.putInt("date", date);
			b.putInt("duration", duration);
			b.putString("startTime", startTime);
			b.putString("location", location); 
			b.putString("subject", subject); 
			b.putString("eventType", eventType);
			b.putString("description", description); 

			Intent newActivity = new Intent("EditEventView");
			newActivity.putExtras(b); //Put your id to your next Intent
			//startActivityForResult(newActivity, 0);
			startActivity(newActivity);
			EventView.this.finish();
			
		}else if(tag.equals("delete")){
			//first, load dialog prompt confirming deletion
			Log.d(tag, "Button pressed - Delete"); 
			showDialog(DELETE_EVENT);

		}else if(tag.equals("create")){
			//create intent to event CREATE activity
			Log.d(tag, "Button pressed - Create"); 
			Intent newActivity = new Intent("AddEventView");
			//startActivityForResult(newActivity, 0);
			startActivity(newActivity); 
			EventView.this.finish();
 
		}

	}//end onClick()
	
	@Override
	protected Dialog onCreateDialog(int id){
	    switch (id) {
	    case DELETE_EVENT:
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle("Dialog Title");
	        //builder.setIcon(android.R.drawable.btn_star);
	        builder.setMessage("Would you like to DELETE this Event?");
	        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
	                  public void onClick(DialogInterface dialog, int which) {
	                		//if YES, make SQL call and delete event entry
	                			Log.d(tag, "User confirmed dialog option delete"); 
	                			//flag has been set to true by user, in the dialog selection. Delete event
	                			dbHelper = new EventsDataSource(ctx);
	                			dbHelper.open(); 	
	                			dbHelper.deleteEvent(eventId);
	                			dbHelper.close(); 
	                			 
	                			EventView.this.finish();//close this EventView as this event no longer exists - return to previous view 

	                        Toast.makeText(getApplicationContext(),
	                        "Event Deleted", Toast.LENGTH_SHORT).show();
	                        return;
	                } });
	        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	        	//user does not want to delete event - simply return without doing anything
	                  public void onClick(DialogInterface dialog, int which) {
	                        Toast.makeText(getApplicationContext(),
	                        "No Changes Made", Toast.LENGTH_SHORT).show();
	                      return;
	                } });
	        return builder.create();
	    }
	    return null;
	}//end onCreateDialog()
	  
	
}//end Class
