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
	
	/**
	 * 获取合同编号
	 * @param source
	 * @return
	 */
	public static String getContractNumber(String Type, Integer id){
		Integer year = DateUtil.getNowYear();
		String number = String.format("%06d",id);
		if (null != id && id > 999999) {
			number = String.format("%07d",id);
		}
		return Type + year + number;
	}
	
	public static void main(String[] args) {
		String number3 = getContractNumber("JG", 1);
		System.out.println(number3);
	}
	
}
