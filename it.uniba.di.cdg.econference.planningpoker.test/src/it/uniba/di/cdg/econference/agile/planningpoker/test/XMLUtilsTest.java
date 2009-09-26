package it.uniba.di.cdg.econference.agile.planningpoker.test;

import it.uniba.di.cdg.econference.planningpoker.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class XMLUtilsTest {


	public static void main(String[] args) {
		Date date = Calendar.getInstance().getTime();
		System.out.println(DateUtils.formatDate(date));
	}

}
