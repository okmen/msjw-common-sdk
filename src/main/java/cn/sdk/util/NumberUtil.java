package cn.sdk.util;

import cn.sdk.util.DateUtil;

public class NumberUtil {
	/**
	 * 获取合同备案号
	 * @param source
	 * @return
	 */
	public static String getBackupNumber(Integer id){
		Integer year = DateUtil.getNowYear();
		String number = String.format("%06d",id);
		if (null != id && id > 999999) {
			number = String.format("%07d",id);
		}
		return "深房租网办" + year + number;
	}
	public static void main(String[] args) {
		String number1 = getBackupNumber(1);
		String number2 = getBackupNumber(999999);
		String number3 = getBackupNumber(1111111);
		System.out.println(number1);
		System.out.println(number2);
		System.out.println(number3);
	}
	
}
