package com.example.schoolfish1;

public class MyDate{
	private int date; 
	private int monthNum; 
	private int year; 
	private int weekdayIndex;
	
	//Constructor
	public MyDate(int weekday, int date, int month, int year){
		this.weekdayIndex = weekday; 
		this.date = date; 
		this.monthNum = month; 
		this.year = year; 
		
	}
	
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public int getMonthNum() {
		return monthNum;
	}
	public void setMonthNum(int monthNum) {
		this.monthNum = monthNum;
	}
	public String getMonthString(int monthNum){
		String monthName;
		
		switch(monthNum){
		case 0: monthName = "January";
				break; 
		case 1: monthName = "February"; 
				break; 
		case 2: monthName = "March"; 
				break;
		case 3: monthName = "April"; 
				break; 
		case 4: monthName = "May"; 
				break;
		case 5: monthName = "June"; 
				break;
		case 6: monthName = "July"; 
				break; 
		case 7: monthName = "August"; 
				break; 
		case 8: monthName = "September"; 
				break; 
		case 9: monthName = "October"; 
				break; 
		case 10: monthName = "November"; 
				break; 
		case 11: monthName = "December"; 
				break; 
		default: monthName = "Invalid"; 
				break; 
		}	
		return monthName; 
	}
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getWeekdayIndex() {
		return weekdayIndex;
	}
	public void setWeekdayIndex(int weekdayIndex) {
		this.weekdayIndex = weekdayIndex;
	}
	public String getWeekdayString(int weekday){
		String weekdayString; 
		
		switch(weekday){
		case 0: weekdayString = "Sunday"; 
				break;
		case 1: weekdayString = "Monday"; 
				break;
		case 2: weekdayString = "Tuesday"; 
				break;
		case 3: weekdayString = "Wednesday"; 
				break; 
		case 4: weekdayString = "Thursday"; 
				break; 
		case 5: weekdayString = "Friday"; 
				break;
		case 6: weekdayString = "Saturday";
				break;
		default: weekdayString = "Invalid Weekday"; 
				break;
		}
		
		return weekdayString; 
	}
	
}
