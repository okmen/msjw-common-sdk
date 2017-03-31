package cn.sdk.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


public class DateUtil2 {
	private static Logger log = Logger.getLogger( DateUtil2.class );
	/**
	 * 返回 2014-07-08 00:00:00格式 
	 * @param d
	 * @return
	 */
	public static String date2str( Date d ){
		if( d != null ){
			SimpleDateFormat sf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
			return sf.format( d );
		}
		return null;
	}
	
	/**
	 * 2014-07-08 00:00:00 转date
	 * @param str
	 * @return
	 */
	public static Date str2date( String str ){
		if( StringUtils.isNotBlank( str ) ){
			try {
				SimpleDateFormat sf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
				return sf.parse( str );
			} catch (ParseException e) {
				log.error("str2date, 异常, str=" + str, e);
			}
		}
		return null;
	}
	
	/**
	 * 返回 20140708000000格式 , 不是时间戳
	 * @param d
	 * @return
	 */
	public static String date2longTime( Date d ){
		if( d != null ){
			SimpleDateFormat sf = new SimpleDateFormat( "yyyyMMddHHmmss" );
			return sf.format( d );
		}
		return null;
	}
	
	public static Date longTime2date( String str ){
		if( StringUtils.isNotBlank( str ) ){
			try {
				SimpleDateFormat sf = new SimpleDateFormat( "yyyyMMddHHmmss" );
				return sf.parse( str );
			} catch (ParseException e) {
				log.error("longTime2date, 异常, str=" + str, e);
			}
		}
		return null;
	}
	
	/**
	 * 返回 2014-07-08
	 * @param d
	 * @return
	 */
	public static String date2dayStr( Date d ){
		if( d != null ){
			SimpleDateFormat sf = new SimpleDateFormat( "yyyy-MM-dd" );
			return sf.format( d );
		}
		return null;
	}
	
	/**
	 * 2014-07-08 转date
	 * @param str
	 * @return
	 */
	public static Date dayStr2date( String str ){
		if( StringUtils.isNotBlank( str ) ){
			try {
				SimpleDateFormat sf = new SimpleDateFormat( "yyyy-MM-dd" );
				return sf.parse( str );
			} catch (ParseException e) {
				log.error("dayStr2date, 异常, str=" + str, e);
			}
		}
		return null;
	}
	
	/**
	 * 返回前一天日期
	 * @param date
	 * @return
	 */
	public static Date getPrevDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);// 把日期往后增加一天.整数往后推,负数往前移动
		return calendar.getTime();
	}

	/**
	 * 返回前几天日期
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date getPrevDate(Date date, int i ) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -i);// 把日期往后增加一天.整数往后推,负数往前移动
		return calendar.getTime();
	}
	
	/**
	 * 返回后一天日期
	 * @param date
	 * @return
	 */
	public static Date getNextDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
		return calendar.getTime();
	}

	/**
	 * 返回后几天的日期
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date getNextDate(Date date, int i ) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, i);// 把日期往后增加一天.整数往后推,负数往前移动
		return calendar.getTime();
	}
 
	
	
}
