/**
 * @filename			DateTime.java
 * @function			日期处理类
 * @author				skyz <skyzhw@gmail.com>
 * @copyright 			ku6.com
 * @datetime			Jul 4, 2007
 * @lastmodify			Jul 4, 2007
 */
package cn.sdk.util;
import java.util.Date;
import java.util.Calendar;

/**
 * 日期处理类
 * 
 * @author skyz <skyzhw@gmail.com>
 * @version 1.0
 */

public final class DateTime {
	Date param = null;

	Calendar ca = Calendar.getInstance();

	/**
	 * 构造函数
	 * 
	 */
	public DateTime() {
		param = new Date();
		ca.setTime(param);
	}

	/**
	 * 带参数的构造函数,构造一个
	 * 
	 * @param vars当前时间类(Date)
	 */
	public DateTime(Date vars) {
		param = vars;
		ca.setTime(param);
	}

	/**
	 * 带参数的构造函数,构造一个
	 * 
	 * @param vars当前时间戳
	 */
	public DateTime(long vars) {
		param = new Date(vars);
		ca.setTime(param);
	}

	/**
	 * 带参数的构造函数,构造一个
	 * 
	 * @param vars当前时间戳
	 */
	public void setTime(long vars) {
		param = new Date(vars);
		ca.setTime(param);
	}
	public void setTime(){
		param = new Date();
		ca.setTime(param);
	}

	/**
	 * 返回当前年份
	 * 
	 * @return返回当前年份
	 */
	public int getYear() {
		return ca.get(Calendar.YEAR);
	}

	/**
	 * 返回当前月份
	 * 
	 * @return返回当前月份
	 */
	public int getMonth() {
		return 1 + ca.get(Calendar.MONTH);
	}

	/**
	 * 返回当前日期(天)
	 * 
	 * @return返回当前日期(天)
	 */
	public int getDay() {
		return ca.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 返回当前时间(时)
	 * 
	 * @return返回当前时间(时)
	 */
	public int getHour() {
		return ca.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 返回当前时间(分)
	 * 
	 * @return返回当前时间(分)
	 */
	public int getMinute() {
		return ca.get(Calendar.MINUTE);
	}

	/**
	 * 返回当前时间(秒)
	 * 
	 * @return返回当前时间(秒)
	 */
	public int getSecond() {
		return ca.get(Calendar.SECOND);
	}

	/**
	 * 返回当前时间(2007-10-11)
	 * 
	 * @return返回当前时间(2007-10-11)
	 */
	public String getDate() {
		return getYear() + "-" + getMonth() + "-" + getDay();
	}

	/**
	 * 返回当前时间(09:11:22)
	 * 
	 * @return返回当前时间(09:11:22)
	 */
	public String getTime() {
		return getHour() + ":" + getMinute() + ":" + getSecond();
	}
	public  String getDateTime(){
		return this.getYear()+" "+this.getTime();
	}
}
