package com.dante.util.datetime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class DateUtil {
	
	String str;
	String oldFormat;
	String newFormat;
	Date currentDate;

	@Before
	public void buildDate() {

		str = "08/29/1991";
		oldFormat = "MM/dd/yyyy";
		newFormat = "yyyy-MM-dd";
		
		Calendar calendar = Calendar.getInstance();
		currentDate = calendar.getTime();

	}

	@Test
	public void convertStringToDate() {

		System.out.println("Current String: " + str);

		try {

			/**
			 * Convert String to Date by Format [MM/dd/yyyy]
			 */
			DateFormat dateFormat = new SimpleDateFormat(oldFormat);
			Date date = dateFormat.parse(str); // String to date

			System.out.format("Parse String to Date by Format[%s]: %s%n", oldFormat, date);

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void convertDateToString() {

		System.out.println("Current Date: " + currentDate);

		try {

			/**
			 * Convert Date to String by format[yyyy-MM-dd]
			 */
			DateFormat formatDate = new SimpleDateFormat(newFormat);
			String convertDate = formatDate.format(currentDate); // Date to String

			System.out.format("Parse Date to String by Format[%s]: %s%n", newFormat, convertDate);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void getCurrentTime() {

		DateFormat formatDate = new SimpleDateFormat("HH:mm:ss");
		String convertDate = formatDate.format(currentDate); 

		System.out.println(convertDate);

	}

	@Test
	public void getCurrentDateTime() {

		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();

		System.out.format("Current date: [%s]%n", date);
		
	}

	@Test
	public void getCalendar() {
		
		Calendar calendar = Calendar.getInstance(); // Get current calendar
		
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int date = calendar.get(Calendar.DATE);
		int dateOfMonth = calendar.get(Calendar.DAY_OF_WEEK);

		System.out.println(hour);
		System.out.println(date);
		System.out.println(dateOfMonth);
		System.out.format("Calendar.DAY_OF_WEEK: %s%n", calendar.get(Calendar.DAY_OF_WEEK));
		System.out.format("Calendar.DAY_OF_MONTH: %s%n", calendar.get(Calendar.DAY_OF_MONTH));
		System.out.format("Calendar.DAY_OF_YEAR: %s%n", calendar.get(Calendar.DAY_OF_YEAR));
		System.out.format("Current Date: %s%n", calendar.getTime());

	}

	@Test
	public void illustration() throws ParseException {

		String startDateString = "06/27/2007";
		System.out.println("String: " + startDateString);
		// This object can interpret strings representing dates in the format
		// MM/dd/yyyy
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		// Convert from String to Date
		Date startDate = df.parse(startDateString);

		// Print the date, with the default formatting.
		// Here, the important thing to note is that the parts of the date
		// were correctly interpreted, such as day, month, year etc.
		System.out.println("Date with the default formatting: " + startDate);

		// Once converted to a Date object, you can convert
		// back to a String using any desired format.
		String startDateString1 = df.format(startDate);
		System.out.println("Date in format MM/dd/yyyy: " + startDateString1);

		// Converting to String again, using an alternative format
		DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
		String startDateString2 = df2.format(startDate);
		System.out.println("Date in format dd/MM/yyyy: " + startDateString2);

	}

}
