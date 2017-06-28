package cn.sdk.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.weaver.ast.Literal;

public class DateUtil2 {
	private static Logger log = Logger.getLogger(DateUtil2.class);
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 返回 2014-07-08 00:00:00格式
	 * 
	 * @param d
	 * @return
	 */
	public static String date2str(Date d) {
		if (d != null) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sf.format(d);
		}
		return null;
	}

	/**
	 * 2014-07-08 00:00:00 转date
	 * 
	 * @param str
	 * @return
	 */
	public static Date str2date(String str) {
		if (StringUtils.isNotBlank(str)) {
			try {
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				return sf.parse(str);
			} catch (ParseException e) {
				log.error("str2date, 异常, str=" + str, e);
			}
		}
		return null;
	}

	/**
	 * 返回 20140708000000格式 , 不是时间戳
	 * 
	 * @param d
	 * @return
	 */
	public static String date2longTime(Date d) {
		if (d != null) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
			return sf.format(d);
		}
		return null;
	}

	public static Date longTime2date(String str) {
		if (StringUtils.isNotBlank(str)) {
			try {
				SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
				return sf.parse(str);
			} catch (ParseException e) {
				log.error("longTime2date, 异常, str=" + str, e);
			}
		}
		return null;
	}

	/**
	 * 返回12:12:12
	 * 
	 * @param d
	 * @return
	 */
	public static String date2shortTime(Date d) {
		if (d != null) {
			SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
			return sf.format(d);
		}
		return null;
	}

	public static Date shortTime2date(String str) {
		if (StringUtils.isNotBlank(str)) {
			try {
				SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
				return sf.parse(str);
			} catch (ParseException e) {
				log.error("shortTime2date, 异常, str=" + str, e);
			}
		}
		return null;
	}

	/**
	 * 返回 2014-07-08
	 * 
	 * @param d
	 * @return
	 */
	public static String date2dayStr(Date d) {
		if (d != null) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			return sf.format(d);
		}
		return null;
	}

	/**
	 * 2014-07-08 转date
	 * 
	 * @param str
	 * @return
	 */
	public static Date dayStr2date(String str) {
		if (StringUtils.isNotBlank(str)) {
			try {
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				return sf.parse(str);
			} catch (ParseException e) {
				log.error("dayStr2date, 异常, str=" + str, e);
			}
		}
		return null;
	}

	/**
	 * 返回前一天日期
	 * 
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
	 * 
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date getPrevDate(Date date, int i) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -i);// 把日期往后增加一天.整数往后推,负数往前移动
		return calendar.getTime();
	}

	/**
	 * 返回后一天日期
	 * 
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
	 * 
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date getNextDate(Date date, int i) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, i);// 把日期往后增加一天.整数往后推,负数往前移动
		return calendar.getTime();
	}

	/**
	 * 根据当前日期获得所在周的日期区间（周一和周日日期）
	 * 
	 * @return
	 * @author zhaoxuepu
	 * @throws ParseException
	 */
	public static List<String> getTimeInterval(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		String imptimeBegin = sdf.format(cal.getTime());
		// System.out.println("所在周星期一的日期：" + imptimeBegin);
		cal.add(Calendar.DATE, 6);
		String imptimeEnd = sdf.format(cal.getTime());
		// System.out.println("所在周星期日的日期：" + imptimeEnd);
		List<String> list = new LinkedList<String>();
		list.add(imptimeBegin);
		list.add(imptimeEnd);
		return list;
	}

	/**
	 * 根据当前日期获得上周的日期区间（上周周一和周日日期）
	 * 
	 * @return
	 * @author zhaoxuepu
	 */
	public static List<String> getLastTimeInterval(){
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
		int offset1 = 1 - dayOfWeek;
		int offset2 = 7 - dayOfWeek;
		calendar1.add(Calendar.DATE, offset1 - 7);
		calendar2.add(Calendar.DATE, offset2 - 7);
		// System.out.println(sdf.format(calendar1.getTime()));// last Monday
		String lastBeginDate = sdf.format(calendar1.getTime());
		// System.out.println(sdf.format(calendar2.getTime()));// last Sunday
		String lastEndDate = sdf.format(calendar2.getTime());
		List<String> list = new LinkedList<String>();
		list.add(lastBeginDate);
		list.add(lastEndDate);
		return list;
	}

	public static void main(String[] args) {
		//System.out.println(getLastTimeInterval());
		
		System.out.println(getTimeInterval(new Date()));
		System.out.println(getLastTimeInterval());
	}
}
