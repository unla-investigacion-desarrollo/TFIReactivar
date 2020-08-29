package com.unla.reactivar.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
	
	public static Date fechaHoy() {
	
        TimeZone timeZone = TimeZone.getTimeZone("America/Argentina/Buenos_Aires");
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTimeZone(timeZone);
		
		Date date = calendar.getTime();
		
		return date;
	}

}
