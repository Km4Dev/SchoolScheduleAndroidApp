package com.example.schoolfish1;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

//create one java class per SQLite table to maintain proper code organisation 
public class EventTable {
	  
	  public static final String TABLE_EVENTS = "events"; 
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_YEAR = "year";
	  public static final String COLUMN_MONTH = "month";
	  public static final String COLUMN_DATE = "date";
	  public static final String COLUMN_WEEKDAY = "weekday";
	  public static final String COLUMN_STARTTIME = "starttime";//24 hour time format
	  public static final String COLUMN_DURATION = "duration"; //in 15 minute increments
	  public static final String COLUMN_LOCATION = "location";
	  public static final String COLUMN_SUBJECT = "subject"; //subject will link to another database with that subject's details. limited in number (enum?)
	  public static final String COLUMN_EVENTTYPE = "eventtype"; //lecture? exam? 
	  public static final String COLUMN_DESCRIPTION = "description"; //description of event 

	  // Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " + TABLE_EVENTS + "(" 
		  + COLUMN_ID + " integer primary key autoincrement, " 
		  + COLUMN_YEAR + " integer "
		  + COLUMN_MONTH + " integer " 
		  + COLUMN_DATE + " integer "
		  + COLUMN_WEEKDAY + " integer "
		  + COLUMN_STARTTIME + " text "
		  + COLUMN_DURATION + " integer "
		  + COLUMN_LOCATION + " text "
		  + COLUMN_SUBJECT + " text "
		  + COLUMN_EVENTTYPE + " text "
		  + COLUMN_DESCRIPTION + " text );";

	  //Call that DB-creation SQL statement upon creation of EventTable object 
	public static void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE);
	}

	//if SQL versions do not match, upgrade it 
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(EventTable.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
		    onCreate(db);
		    
	}

}
