package com.wgh.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class TimeTest {

	@Test
	public void test() throws ParseException {
		
		Date date = new Date();
		long time = date.getTime();
//		Date parse = java.text.DateFormat.getDateTimeInstance().parse(Long.toString(time));
//		System.out.println("parse:" + parse);
		
		DateFormat format = DateFormat.getDateTimeInstance();
		System.out.println("format:" + format);
	}
	
	@Test
	public  void test01() throws ParseException {
      String str_date = "2012��4��19��";  
//      str_date = "2011---8---17";      
//      str_date = "2018-02-25";
      //�������Ե�һ��  
//      DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);  
      //�������Եڶ������Զ����ʽ  
//      dateFormat = new SimpleDateFormat("yyyy---MM---dd");     
//      DateFormat dateFormat = DateFormat.getDateInstance();   
//      dateFormat = new SimpleDateFormat();      
//      Date date = dateFormat.parse(str_date);   
//      System.out.println(date);
      
      Date date = java.text.DateFormat.getDateTimeInstance().parse("2013-5-6 13:13:56");
      System.out.println(date);
	}

}
