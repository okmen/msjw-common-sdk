package cn.sdk.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sdk.bean.DownValue;

/**
 * 深圳安居项目常量
 * @author Mbenben
 *
 */
public class SzajConStants {
	public static String SzajRedisKey = "SzajRedisKey_";
	
	/**
	 * 性别
	 */
	public static final String COM_CODE_SEX = "SEX";
	
	/**
	 * 订单状态
	 */
	public static final String COM_CODE_ORDER_STATUS = "ORDER_STATUS";
	
	/**
	 * 账单状态
	 */
	public static final String COM_CODE_BILL_STATUS = "BILL_STATUS";
	
	/**
	 * 是否删除 - 已删除
	 */
	public static final int IS_DELETED_YES = 1;
	
	/**
	 * 是否删除 - 未删除
	 */
	public static final int IS_DELETED_NO = 0;
	
	/**
	 * 性别 男 
	 */
	public static final int SEX_MAN = 1;
	
	/**
	 * 性别 女
	 */
	public static final int SEX_WOMAN = 2;
	
	/**
	 * 订单状态 - 待付款
	 */
	public static final int ORDER_STATUS_1 = 1;
	
	/**
	 * 订单状态 - 退款中
	 */
	public static final int ORDER_STATUS_2 = 2;
	
	/**
	 * 订单状态 - 已完成
	 */
	public static final int ORDER_STATUS_3 = 3;
	
	/**
	 * 订单状态 - 租客已取消
	 */
	public static final int ORDER_STATUS_4 = 4;
	
	/**
	 * 订单状态 - 房东已取消
	 */
	public static final int ORDER_STATUS_5 = 5;
	
	
	/**
	 * 账单状态-待付款
	 */
	public static final int BILL_STATUS_1 = 1;
	
	/**
	 * 账单状态 -已付款
	 */
	public static final int BILL_STATUS_2 = 2;
	
	public static List<DownValue<Integer>> SEX_LIST = new ArrayList<DownValue<Integer>>();
	public static List<DownValue<Integer>> ORDER_STATUS_LIST = new ArrayList<DownValue<Integer>>();
	public static List<DownValue<Integer>> BILL_STATUS_LIST = new ArrayList<DownValue<Integer>>();

	static {
		SEX_LIST.add(new DownValue<Integer>(SEX_MAN, "男"));
		SEX_LIST.add(new DownValue<Integer>(SEX_WOMAN, "女"));
		
		ORDER_STATUS_LIST.add(new DownValue<Integer>(ORDER_STATUS_1, "待付款"));
		ORDER_STATUS_LIST.add(new DownValue<Integer>(ORDER_STATUS_2, "退款中"));
		ORDER_STATUS_LIST.add(new DownValue<Integer>(ORDER_STATUS_3, "已完成"));
		ORDER_STATUS_LIST.add(new DownValue<Integer>(ORDER_STATUS_4, "租客已取消"));
		ORDER_STATUS_LIST.add(new DownValue<Integer>(ORDER_STATUS_5, "房东已取消"));
		
		BILL_STATUS_LIST.add(new DownValue<Integer>(BILL_STATUS_1, "待付款"));
		BILL_STATUS_LIST.add(new DownValue<Integer>(BILL_STATUS_2, "已付款"));
	}
	
	public static <T> Map<T, String> listToMap(List<DownValue<T>> list){
		Map<T, String> map = new HashMap<>();
		for (DownValue o : list) {
			T t = (T) o.getValue();
			String v = (String) o.getText();
			map.put(t, v);
		}
		return map;
	}
}
