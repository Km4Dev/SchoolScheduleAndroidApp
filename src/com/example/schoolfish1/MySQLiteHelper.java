package com.example.schoolfish1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper{

	  public static final String TABLE_EVENTS = "events"; 
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_YEAR = "year";
	  public static final String COLUMN_MONTH = "month";
	  public static final String COLUMN_DATE = "date";
	  public static final String COLUMN_STARTTIME = "starttime";//24 hour time format
	  public static final String COLUMN_DURATION = "duration"; //in 15 minute increments
	  public static final String COLUMN_LOCATION = "location";
	  public static final String COLUMN_SUBJECT = "subject"; //subject will link to another database with that subject's details. limited in number (enum?)
	  public static final String COLUMN_EVENTTYPE = "eventtype"; //lecture? exam? 
	  public static final String COLUMN_DESCRIPTION = "description"; //description of event 
	  
	  private static final String DATABASE_NAME = "calendar.db";
	  private static final int DATABASE_VERSION = 2;
	  
	  public MySQLiteHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	 //Actually calls EventTable class' onCreate() and onUpgrade() methods
	@Override
	public void onCreate(SQLiteDatabase db) {
	    //EventTable.onCreate(db); 
		  // Database creation SQL statement
		Log.d("MySQLiteHelper", "onCreate"); 
		  String DATABASE_CREATE = "CREATE TABLE " + TABLE_EVENTS + "(" 
			  + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			  + COLUMN_YEAR + " INTEGER,"
			  + COLUMN_MONTH + " INTEGER," 
			  + COLUMN_DATE + " INTEGER,"
			  + COLUMN_STARTTIME + " TEXT,"
			  + COLUMN_DURATION + " INTEGER,"
			  + COLUMN_LOCATION + " TEXT,"
			  + COLUMN_SUBJECT + " TEXT,"
			  + COLUMN_EVENTTYPE + " TEXT,"
			  + COLUMN_DESCRIPTION + " TEXT);";
		  
		  db.execSQL(DATABASE_CREATE);
		  Log.d("MySQLiteHelper", "table created"); 
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    //EventTable.onUpgrade(db, oldVersion, newVersion);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
		 
        // Create tables again
        onCreate(db);
	}
}
