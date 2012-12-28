package com.example.schoolfish1;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class EditEventView extends Activity implements OnClickListener {

	private static final String tag = "EditEvent"; 
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
	private int id; 
	
	private int yearOld;
	private int monthOld;
	private int dateOld;
	private int durationOld;
	private String startTimeOld;
	private String locationOld;
	private String subjectOld;
	private String eventTypeOld; 
	private String descriptionOld; 
	
	//database access
	private EventsDataSource dbHelper; 
	private Context ctx = this; 	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event_view);
        
        
        //get event id that was passed to it, to be able to access and edit that particular event's info by id 
        Bundle b = getIntent().getExtras();
        id = b.getInt("id");   
        yearOld = b.getInt("year"); 
        monthOld = b.getInt("month");
        dateOld = b.getInt("date"); 
        durationOld = b.getInt("duration"); 
        startTimeOld = b.getString("startTime");
        locationOld = b.getString("location");
        subjectOld = b.getString("subject");
        eventTypeOld = b.getString("eventType");
        descriptionOld = b.getString("description"); 
        
        //get references to EditTexts in view
        subjectEdit = (EditText)this.findViewById(R.id.edit_event_subject_edit); 
        subjectEdit.setText(subjectOld);
        
        eventTypeEdit = (EditText)this.findViewById(R.id.edit_event_type_edit); 
        eventTypeEdit.setText(eventTypeOld);
        
        locationEdit = (EditText)this.findViewById(R.id.edit_event_location_edit);
        locationEdit.setText(locationOld);
            
        durationEdit = (EditText)this.findViewById(R.id.edit_event_duration_edit); 
        durationEdit.setText(durationOld + " "); 
        
        descriptionEdit = (EditText)this.findViewById(R.id.edit_event_description_edit); 
        descriptionEdit.setText(descriptionOld);
        
        String[] timeArr = startTimeOld.split(":"); 
        int currentHour = Integer.parseInt(timeArr[0]); 
        int currentMinute = Integer.parseInt(timeArr[1]); 
        timepicker = (TimePicker) findViewById(R.id.editEventTimePicker);
        timepicker.setCurrentHour(currentHour);
        timepicker.setCurrentMinute(currentMinute);
        
        datepicker = (DatePicker) findViewById(R.id.editEventDatePicker);
        datepicker.updateDate(yearOld, monthOld, dateOld); 
        
        saveButton = (Button) findViewById(R.id.saveEditEventButton); 
        saveButton.setOnClickListener(this); 
        
        cancelButton = (Button) findViewById(R.id.cancelEditEventButton);
        cancelButton.setOnClickListener(this); 

      
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_edit_event_view, menu);
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
	        duration = Integer.parseInt(durationEdit.getText().toString().trim()); 
  
	        //get contents of pickers
	        hour = timepicker.getCurrentHour(); 
	        minutes = timepicker.getCurrentMinute(); 
	        
	        date = datepicker.getDayOfMonth();
	        month = datepicker.getMonth(); 
	        year = datepicker.getYear();
	        
	        Log.d(tag, "Event ID" + id); 
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
			
			ContentValues cv = new ContentValues();
			cv.put("year", year);
			cv.put("month", month); 
			cv.put("date", date); 
			cv.put("starttime", hour + ":" + minutes); 
			cv.put("duration", duration);
			cv.put("location", location); 
			cv.put("subject", subject);
			cv.put("eventtype", eventType);
			cv.put("description", description); 
			dbHelper.updateEvent(id, cv); 
			
			Event newEvent = new Event(year, month, date, timeString, duration, location, subject, eventType, description);
			newEvent = dbHelper.createEvent(newEvent); 
			dbHelper.close(); 
	        //create toast to let user know that change was made and event was added
	        Toast.makeText(getBaseContext(), "Your event has been saved", Toast.LENGTH_SHORT).show(); 
			
	        EditEventView.this.finish();//return to previous activity
	        
		}else if(v == cancelButton){
			//cancel - go back to previous screen 
			EditEventView.this.finish();
		}
		
	}//end onClick
}
