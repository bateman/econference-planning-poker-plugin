package it.uniba.di.cdg.econference.planningpoker.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
    public static String formatDate(Date date){
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	return formatter.format(date);
    }


    public static Date getDateFromString(String createdOn) {
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	try
    	{
    		Date date = formatter.parse(createdOn); 
    		return date;
    	} catch (ParseException e)
    	{
    		e.printStackTrace();
    		return null;
    	}
    }

}
