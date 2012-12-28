package com.example.schoolfish1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddEventView extends Activity implements OnClickListener{

	private static final String tag = "AddEvent"; 
	private EditText subjectEdit; 
	private EditText eventTypeEdit;
	private EditText locationEdit; 
	private EditText descriptionEdit;
	private EditText durationEdit; 
	
	private int duration; 
	private String subject;
	private String eventType;
	private String location;
	private String description;
	
	private TimePicker timepicker;
	private DatePicker datepicker; 
	private Button saveButton; 
	private Button cancelButton; 
	
	private int hour; 
	private int minutes; 
	private int date; 
	private int month; 
	private int year; 
	
	//database access
	private EventsDataSource dbHelper; 
	private Context ctx = this; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_view);
        
        //get references to EditTexts in view
        subjectEdit = (EditText)this.findViewById(R.id.add_event_subject_edit); 
        eventTypeEdit = (EditText)this.findViewById(R.id.add_event_type_edit); 
        locationEdit = (EditText)this.findViewById(R.id.add_event_location_edit); 
        descriptionEdit = (EditText)this.findViewById(R.id.add_event_description_edit); 
        durationEdit = (EditText)this.findViewById(R.id.add_event_duration_edit); 
        
        timepicker = (TimePicker) findViewById(R.id.addEventTimePicker);
        datepicker = (DatePicker) findViewById(R.id.addEventDatePicker);
        
        saveButton = (Button) findViewById(R.id.saveAddEventButton); 
        saveButton.setOnClickListener(this); 
        
        cancelButton = (Button) findViewById(R.id.cancelAddEventButton);
        cancelButton.setOnClickListener(this); 
        
    }//end onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_event_view, menu);
        return true;
    }

	@Override
	public void onClick(View v) {
		
		if(v == saveButton){
	        //get contents of EditTexts as filled out by user 
	        subject = subjectEdit.getText().toString(); 
	        eventType = eventTypeEdit.getText().toString();
	        location = locationEdit.getText().toString(); 
	        description = descriptionEdit.getText().toString(); 
	        duration = Integer.parseInt(durationEdit.getText().toString()); 
  
	        //get contents of pickers
	        hour = timepicker.getCurrentHour(); 
	        minutes = timepicker.getCurrentMinute(); 
	        
	        date = datepicker.getDayOfMonth();
	        month = datepicker.getMonth(); 
	        year = datepicker.getYear(); 
	        
	        Log.d(tag, "Subject: " + subject); 
	        Log.d(tag, "Event Type: " + eventType); 
	        Log.d(tag, "Location: " + location); 
	        Log.d(tag, "Description: " + description); 
	        Log.d(tag, "Duration: " + duration); 
	        Log.d(tag, "Hour: " + hour); 
	        Log.d(tag, "Minutes: " + minutes); 
	        Log.d(tag, "Date: " + date); 
	        Log.d(tag, "Month: " + month); 
	        Log.d(tag, "Year: " + year); 

	        String timeString = hour + ":" + minutes; 
	        
	        //save changes in database
	        dbHelper = new EventsDataSource(ctx);
			dbHelper.open(); 
			Event newEvent = new Event(year, month, date, timeString, duration, location, subject, eventType, description);
			newEvent = dbHelper.createEvent(newEvent); 
			dbHelper.close(); 
	        //create toast to let user know that change was made and event was added
	        Toast.makeText(getBaseContext(), "Your event has been saved", Toast.LENGTH_SHORT).show(); 
			
	        AddEventView.this.finish();//return to previous activity
	        
		}else if(v == cancelButton){
			//cancel - go back to previous screen 
			AddEventView.this.finish();
		}
	}//end onClick
	
}
