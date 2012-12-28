package com.example.schoolfish1;

public class Event {
	private int id; 
	private int year; 
	private int month; 
	private int date;
	private String startTime; //24 hour time format, without colon(:) separator
	private int duration; //in 15 minute increments
	private String location; 
	private String subject; //must limit to already added subjects in Subjects db (or 'none')
	private String eventType; //must limit to predetermined event types like Lecture, Exam, etc. 
	private String description; //description of event
	
	public Event(){
		
	}
	
	public Event(int year, int month, int date, String startTime, int duration, String location, String subject, String eventType, String description){
		this.year = year; 
		this.month = month; 
		this.date = date; 
		this.startTime = startTime;
		this.duration = duration;
		this.location = location;
		this.subject = subject; 
		this.eventType = eventType; 
		this.description = description; 
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	//Will be used by adapter in printing event information to the GridView 
	public String toString(){
		String output; 
		if(eventType == null || eventType == "" || eventType.equalsIgnoreCase("Other")){
			output = subject + "\n" + description; 
		}else if(subject.equalsIgnoreCase("None")){
			output = description; 
		}else{
			output = subject + "\n" + eventType; 
		}
		return output; 
	}
	
}
