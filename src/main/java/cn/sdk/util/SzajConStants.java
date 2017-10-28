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
	 * 用户类型 -房东
	 */
	public static final int USER_TYPE_2 = 2;
	
	/**
	 * 用户类型 -租客
	 */
	public static final int USER_TYPE_1 = 1;
	
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
	 * 订单状态 - 已付款
	 */
	public static final int ORDER_STATUS_2 = 2;
	
	/**
	 * 订单状态 - 租客已取消
	 */
	public static final int ORDER_STATUS_4 = 4;
	
	/**
	 * 订单状态 - 房东已取消
	 */
	public static final int ORDER_STATUS_5 = 5;
	
	/**
	 * 账单状态-未生成账单
	 */
	public static final int BILL_STATUS_0 = 0;
	
	/**
	 * 账单状态-待付款
	 */
	public static final int BILL_STATUS_1 = 1;
	
	/**
	 * 账单状态 -已付款
	 */
	public static final int BILL_STATUS_2 = 2;
	
	/**
	 * 看房日程状态 -待看房
	 */
	public static final int HOUSE_SCHEDULE_WAIT = 1;
	
	/**
	 * 看房日程状态 -已看房
	 */
	public static final int HOUSE_SCHEDULE_END = 2;
	
	
	/**
	 * 个人消息类型 -预约
	 */
	public static final int MESSAGE_STATUS_1 = 1;
	
	/**
	 * 个人消息类型 -房屋
	 */
	public static final int MESSAGE_STATUS_2 = 2;
	
	/**
	 * 个人消息类型 -账单
	 */
	public static final int MESSAGE_STATUS_3 = 3;
	
	/**
	 * 合同状态 1 暂存
	 */
	public static final int CONTRACT_STATUS_1 = 1;
	
	/**
	 * 合同状态 2 生效
	 */
	public static final int CONTRACT_STATUS_2 = 2;
	
	/**
	 * 合同状态 3 废除
	 */
	public static final int CONTRACT_STATUS_3 = 3;
	
	/**
	 * 合同签订状态 0：未签 1：租客已签 2：房东已签  3：双方已签
	 */
	public static final int CONTRACT_SIGN_STATUS_0 = 0;
	
	/**
	 * 合同签订状态 0：未签 1：租客已签 2：房东已签  3：双方已签
	 */
	public static final int CONTRACT_SIGN_STATUS_1 = 1;
	
	/**
	 * 合同签订状态 0：未签 1：租客已签 2：房东已签  3：双方已签
	 */
	public static final int CONTRACT_SIGN_STATUS_2 = 2;
	
	/**
	 * 合同签订状态 0：未签 1：租客已签 2：房东已签  3：双方已签
	 */
	public static final int CONTRACT_SIGN_STATUS_3 = 3;
	
	/**
	 * 领签合同签订状态 已签署:signed 签署中:signing
	 */
	public static final String DOCUMENT_STATUS_SIGNING = "signing";
	
	/**
	 * 领签合同签订状态 已签署:signed 签署中:signing
	 */
	public static final String DOCUMENT_STATUS_SIGNED = "signed";
	
	/**
	 * 订单状态
	 * 1.等待公寓确认
	 * 2.等待房东确认
	 * 3.待付款（房东已确认）
	 * 4.待付款（公寓已确认）
	 * 5.待签约
	 * 6.支付成功
	 * 	            订单状态	                              合同状态
	 *	租客	等待公寓确认  1	                  待房东确认  1
	 *		等待房东确认  2	                  待续租  4
	 *		待付款（房东已确认）3	      待退租 5
	 *		待付款（公寓已确认）4	      待签约
	 *		待签约	5
	 *		支付成功	6
	 *	房东	待处理	7                              待租客签约 2
	 *		待租客签约	8                              待支付 3
	 *		待租客支付	9                              待续租
	 *		待租客续约	10                           待退租
	 *		待支付	11
	 *		已完成	12
	 *
	 */
	public static final int NEW_ORDER_STATUS_1 =1;//待确认
	public static final int NEW_ORDER_STATUS_2 =2;//房东已取消
	public static final int NEW_ORDER_STATUS_3 =3;//待签约
	public static final int NEW_ORDER_STATUS_4 =4;//待支付
	public static final int NEW_ORDER_STATUS_5 =5;//支付成功
	//public static final int ORDER_STATUS_1 =1;//待确认
	//public static final int ORDER_STATUS_1 =1;//待确认
	//public static final int ORDER_STATUS_1 =1;//待确认
	
	/**
	 * 我的房源租赁状态
	 */
	public static final int HOUSE_PERSON_STATUS_1000 =1000;//可出租 
	public static final int HOUSE_PERSON_STATUS_1001 =1001;//不可租 
	public static final int HOUSE_PERSON_STATUS_1002 =1002;//租赁中 
	public static final int HOUSE_PERSON_STATUS_1003 =1003;//已预定
	public static final int HOUSE_PERSON_STATUS_1004 =1004;//已下架
	
	/**
	 * 租客预约看房预约状态
	 */
	public static final int HOUSE_APPOINTMENT_STATUS_0 =0;//预约成功 
	public static final int HOUSE_APPOINTMENT_STATUS_1 =1;//预约中  
	public static final int HOUSE_APPOINTMENT_STATUS_2 =2;//租客取消
	public static final int HOUSE_APPOINTMENT_STATUS_3 =3;//房东取消
	
	
	/**
	 * 银行账户绑定状态-已绑定
	 */
	public static final int USER_BANK_ACCOUNT_STATUS_0 = 0;
	
	/**
	 * 银行账户绑定状态-未绑定
	 */
	public static final int USER_BANK_ACCOUNT_STATUS_1 = 1;
	
	/**
	 * 借记卡
	 */
	public static final int CARD_TYPE_1 = 1;
	
	/**
	 * 贷记卡
	 */
	public static final int CARD_TYPE_2 = 2;
	
	/**
	 * 支付类型 - 订单
	 */
	public static final String PAY_TYPE_ORDER = "order";
	
	/**
	 * 支付类型 - 账单
	 */
	public static final String PAY_TYPE_BILL = "bill";
	
	/**
	 * 流水清算状态 - 0
	 */
	public static final int PAY_FLOW_DISPATCH_0 = 0;
	
	/**
	 * 流水清算状态 - 1
	 */
	public static final int PAY_FLOW_DISPATCH_1 = 1;
	
	/**
	 * 押金状态 - 已支付
	 */
	public static final int DEPOSIT_MONEY_STATUS_0 = 0;
	/**
	 * 押金状态 - 已退还
	 */
	public static final int DEPOSIT_MONEY_STATUS_1 = 1;
	
	/**
	 * 押金状态 - 已扣留
	 */
	public static final int DEPOSIT_MONEY_STATUS_2 = 2;
	
	/**
	 * 支付方式 - 微信
	 */
	public static final String PAY_WAY_WECHAT = "wechat";
	
	/**
	 * 支付方式 - 银联
	 */
	public static final String PAY_WAY_CHINA = "china";
	/**
	 *  当前用户类型-租客
	 */
	public static final int THE_USER_TYPE_1 = 1;
	
	/**
	 * 当前用户类型-房东
	 */
	public static final int THE_USER_TYPE_2 = 2;
	
	public static List<DownValue<Integer>> SEX_LIST = new ArrayList<DownValue<Integer>>();
	public static List<DownValue<Integer>> ORDER_STATUS_LIST = new ArrayList<DownValue<Integer>>();
	public static List<DownValue<Integer>> BILL_STATUS_LIST = new ArrayList<DownValue<Integer>>();
	
	public static List<DownValue<Integer>> PAY_TYPE_LIST = new ArrayList<DownValue<Integer>>();
	public static List<DownValue<Integer>> CARD_TYPE_LIST = new ArrayList<DownValue<Integer>>();
	static {
		SEX_LIST.add(new DownValue<Integer>(SEX_MAN, "男"));
		SEX_LIST.add(new DownValue<Integer>(SEX_WOMAN, "女"));
		
		ORDER_STATUS_LIST.add(new DownValue<Integer>(ORDER_STATUS_1, "待付款"));
		ORDER_STATUS_LIST.add(new DownValue<Integer>(ORDER_STATUS_2, "已付款"));
		ORDER_STATUS_LIST.add(new DownValue<Integer>(ORDER_STATUS_4, "租客已取消"));
		ORDER_STATUS_LIST.add(new DownValue<Integer>(ORDER_STATUS_5, "房东已取消"));
		
		BILL_STATUS_LIST.add(new DownValue<Integer>(BILL_STATUS_0, "未生成账单"));
		BILL_STATUS_LIST.add(new DownValue<Integer>(BILL_STATUS_1, "等待支付"));
		BILL_STATUS_LIST.add(new DownValue<Integer>(BILL_STATUS_2, "已支付"));
		
		CARD_TYPE_LIST.add(new DownValue<Integer>(CARD_TYPE_1, "储蓄卡"));
		CARD_TYPE_LIST.add(new DownValue<Integer>(CARD_TYPE_2, "信用卡"));
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
