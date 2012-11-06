package com.example.schoolfish1;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

//Maintains the database connection and supports adding/fetching events 
//instantiates new EventTable object, which holds details specific to that table in the database 
public class EventsDataSource{

	  // Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper; //creates the database for us 
	  private String[] allColumns = { MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_YEAR, 
			  MySQLiteHelper.COLUMN_MONTH, MySQLiteHelper.COLUMN_DATE, MySQLiteHelper.COLUMN_WEEKDAY, 
			  MySQLiteHelper.COLUMN_STARTTIME, MySQLiteHelper.COLUMN_DURATION, MySQLiteHelper.COLUMN_LOCATION, 
			  MySQLiteHelper.COLUMN_LOCATION, MySQLiteHelper.COLUMN_SUBJECT, MySQLiteHelper.COLUMN_EVENTTYPE, 
			  MySQLiteHelper.COLUMN_DESCRIPTION}; //array of all columns' names

	  //creates new database instance (of existing Events database) for us to read/write to 
	  public EventsDataSource(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	  }

	  //gets writable database
	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  //Adds a new Event object to database 
	  public Event createEvent(Event event) {
		//ContentValues object holds key-value pairs, mapping the column name ("event") to each individually inserted event object
	    ContentValues values = new ContentValues();
	    
	    int id = event.getId(); 
	    int year = event.getYear(); 
	    int month = event.getMonth(); 
	    int date = event.getDate();
	    String weekday = event.getWeekday(); 
	    int startTime = event.getStartTime(); 
	    int duration = event.getDuration(); 
	    String location = event.getLocation(); 
	    String subject = event.getSubject();
	    String eventType = event.getEventType(); 
	    String description = event.getDescription(); 
	    
	    values.put(MySQLiteHelper.COLUMN_ID, id);
	    values.put(MySQLiteHelper.COLUMN_YEAR, year);
	    values.put(MySQLiteHelper.COLUMN_MONTH, month);
	    values.put(MySQLiteHelper.COLUMN_DATE, date);
	    values.put(MySQLiteHelper.COLUMN_WEEKDAY, weekday);
	    values.put(MySQLiteHelper.COLUMN_STARTTIME, startTime);
	    values.put(MySQLiteHelper.COLUMN_DURATION, duration);
	    values.put(MySQLiteHelper.COLUMN_LOCATION, location);
	    values.put(MySQLiteHelper.COLUMN_SUBJECT, subject);
	    values.put(MySQLiteHelper.COLUMN_EVENTTYPE, eventType);
	    values.put(MySQLiteHelper.COLUMN_DESCRIPTION, description);
	    
	    //open database
	    open();
	    
	    //then, insert that new ContentValues object into the database itself  and return unique ID from id column for that new row
	    long insertId = database.insert(MySQLiteHelper.TABLE_EVENTS, null, values);
	    
	    //query the database to retrieve that newly inserted row and use the returned info to create a new Event object, and return it to calling method
	    Cursor cursor = database.query(MySQLiteHelper.TABLE_EVENTS, allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
	    cursor.moveToFirst();
	   	Event newEvent = cursorToEvent(cursor);
	    cursor.close();
	    
	    close(); //close database 
	    return newEvent;
	  }
	  
	  public void updateEvent(Event event){
		  long id = event.getId(); 
		  System.out.println("Event being updated with id: " + id); 

		  //database.update(MySQLiteHelper.TABLE_EVENTS, MySQLiteHelper.COLUMN_ID + " = " + id, null); 
	  }

	  //use event object's ID field to query that ID's row in Event table, and delete it 
	  public void deleteEvent(Event event) {
	    long id = event.getId();
	    System.out.println("Event deleted with id: " + id);
	    database.delete(MySQLiteHelper.TABLE_EVENTS, MySQLiteHelper.COLUMN_ID
	        + " = " + id, null);
	  }

	  //returns List object of all events stored in events table (returns allColumns - i.e. both ID and event objects)
	  public List<Event> getAllEvents() {
	    List<Event> events = new ArrayList<Event>();

	    Cursor cursor = database.query(MySQLiteHelper.TABLE_EVENTS, allColumns, null, null, null, null, null);

	    //use cursor to iterate through all table rows 
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Event event = cursorToEvent(cursor);
	      events.add(event);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return events;
	  }

	  //private helper method to create a new Event object with its ID field set to the Event table's unique row ID 
	  private Event cursorToEvent(Cursor cursor) {
	    Event event = new Event();
	    event.setId(cursor.getInt(0));
	    return event;
	  }
	  
}
