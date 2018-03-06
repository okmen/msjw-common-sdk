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
	
	/**
	 * 登录接口
	 */
	public final static String LOGIN_METHOD = "/admin/user/login.html";
	/**
	 * 房源状态key
	 */
	public static String ORDER_LOCK_HOUSE_KEY = "ORDER_LOCK_HOUSE_";
	public static String ORDER_SIGN_HOUSE_KEY = "ORDER_SIGN_HOUSE_";
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
	 * 性别 未知 
	 */
	public static final int SEX_UNKNOWN = 0;
	
	/**
	 * 性别 男 
	 */
	public static final int SEX_MAN = 1;
	
	/**
	 * 性别 女
	 */
	public static final int SEX_WOMAN = 2;
	
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
	 * 个人消息类型 -合同
	 */
	public static final int MESSAGE_STATUS_4 = 4;
	
	/**
	 * 合同状态 1 暂存 房东已签，租客待签
	 */
	public static final int CONTRACT_STATUS_1 = 1;
	
	/**
	 * 合同状态 2 生效 双方已签
	 */
	public static final int CONTRACT_STATUS_2 = 2;
	
	/**
	 * 合同状态 3 废除 解除合同
	 */
	public static final int CONTRACT_STATUS_3 = 3;
	
	/**
	 * 合同状态 4 续约中
	 */
	public static final int CONTRACT_STATUS_4 = 4;
	
	/**
	 * 合同状态 5 退租中
	 */
	public static final int CONTRACT_STATUS_5 = 5;
	
	
	
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
	 * 续租生成订单，带租客确认
	 */
	public static final int NEW_ORDER_STATUS_0 =0;
	/**
	 * 租客提交，待房东确认  (待处理)
	 */
	public static final int NEW_ORDER_STATUS_1 =1;
	/**
	 * 房东已取消 
	 */
	public static final int NEW_ORDER_STATUS_2 =2;
	/**
	 * 房东已签约，待租客签约
	 */
	public static final int NEW_ORDER_STATUS_3 =3;
	/**
	 * 待租客支付
	 */
	public static final int NEW_ORDER_STATUS_4 =4;
	/**
	 * 租客支付成功
	 */
	public static final int NEW_ORDER_STATUS_5 =5;
	/**
	 * 订单关闭
	 */
	public static final int NEW_ORDER_STATUS_99 =99;
	
	/**
	 *  订单类别 1 普通签约订单
	 */
	public static final int ORDER_TYPE_1 = 1;
	
	/**
	 *  订单类别 2 续租订单
	 */
	public static final int ORDER_TYPE_2 = 2;
	
	/**
	 *  订单类别 3 退租订单
	 */
	public static final int ORDER_TYPE_3 = 3;
	
	/**
	 * 我的房源租赁状态
	 */
	public static final int HOUSE_PERSON_STATUS_1000 =1000;//可出租 
	public static final int HOUSE_PERSON_STATUS_1001 =1001;//不可租 
	public static final int HOUSE_PERSON_STATUS_1002 =1002;//租赁中 
	public static final int HOUSE_PERSON_STATUS_1003 =1003;//已预定
	public static final int HOUSE_PERSON_STATUS_1004 =1004;//已下架
	public static final int HOUSE_PERSON_STATUS_1005 =1005;//待审核 
	public static final int HOUSE_PERSON_STATUS_1006 =1006;//审核不通过
	
	/**
	 * 个人房源验真状态
	 */
	public static final int CHECK_HOUSE_CODE_1001 = 1001;//待验真
	public static final int CHECK_HOUSE_CODE_1002 = 1002;//通过 
	public static final int CHECK_HOUSE_CODE_1003 = 1003;//不通过
	public static final int CHECK_HOUSE_CODE_1004 = 1004;//验真超时
	
	/**
	 * 委托书检验状态
	 */
	public static final int CHECK_PROXY_CODE_1001 = 1001;//待检验
	public static final int CHECK_PROXY_CODE_1002 = 1002;//通过 
	public static final int CHECK_PROXY_CODE_1003 = 1003;//不通过
	
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
	
	
	/**
	 * 资讯状态-待发布
	 */
	public static final int NEW_MSG_STATUS_1=1000;
	
	/**
	 * 资讯状态-发布
	 */
	public static final int NEW_MSG_STATUS_2=1001;
	
	/**
	 * 资讯状态-下架
	 */
	public static final int NEW_MSG_STATUS_3=1002;
	
	/**
	 * 流水清算状态 - 未清算
	 */
	public static final int WECHATPAY_DISPATCH_STATUS_0 = 0;
	/**
	 * 流水清算状态 - 清算到零钱成功
	 */
	public static final int WECHATPAY_DISPATCH_STATUS_1 = 1;
	
	/**
	 * 流水清算状态  - 业务数据执行受阻,原因查看reason字段
	 */
	public static final int WECHATPAY_DISPATCH_STATUS_2 = 2;
	
	/**
	 * 流水清算状态  - 已执行清算，但微信未成功受理,原因查看reason字段
	 */
	public static final int WECHATPAY_DISPATCH_STATUS_3 = 3;
	
	/**
	 * 流水清算状态  - 清算成功,但未成功记录清算记录,需人工恢复清算记录
	 */
	public static final int WECHATPAY_DISPATCH_STATUS_4 = 4;
	
	/**
	 * 合同费用 275分
	 */
	public static final int CONTRACT_FEE = 275;
	
	/**
	 * 合同备案状态：0 未备案
	 */
	public static final int BACKUP_FLAG_0 = 0;
	
	/**
	 * 合同备案状态：1  已备案
	 */
	public static final int BACKUP_FLAG_1 = 1;
	
	/**
	 * 合同备案审核状态：0  待审核
	 */
	public static final int BACKUP_AUDIT_0 = 0;
	
	/**
	 * 合同备案审核状态：1  审核不通过
	 */
	public static final int BACKUP_AUDIT_1 = 1;
	
	/**
	 * 合同备案审核状态：2  审核通过
	 */
	public static final int BACKUP_AUDIT_2 = 2;
	
	/**
	 * 合同备案审核状态：3 备案完成
	 */
	public static final int BACKUP_AUDIT_3 = 3;
	
	/**
	 * 公寓类别 1集中式
	 */
	public static final int HOUSE_TYPE_1 = 1;
	
	/**
	 * 公寓类别 2分散式
	 */
	public static final int HOUSE_TYPE_2 = 2;
	
	/**
	 * 主次卧 1主卧
	 */
	public static final int HOUSE_RECUMBENT_1 = 1;
	
	/**
	 * 主次卧 2次卧
	 */
	public static final int HOUSE_RECUMBENT_2 = 2;
	
	/**
	 * 房客签约：0未签约
	 */
	public static final int SIGN_STATUS_0 = 0;
	
	/**
	 * 房客签约：1已签约 
	 */
	public static final int SIGN_STATUS_1 = 1;
	
	/**
	 * 资质审核状态 0初始化 
	 */
	public static final int QUALIFICATION_STATUS_0 = 0;
	
	/**
	 * 资质审核状态 1通过 
	 */
	public static final int QUALIFICATION_STATUS_1 = 1;
	/**
	 * 资质审核状态 2驳回
	 */
	public static final int QUALIFICATION_STATUS_2 = 2;
	
	/**
	 * 58内容审核状态 0初始化 
	 */
	public static final int AUDIT_STATUS_58_0 = 0;
	
	/**
	 * 58内容审核状态 1通过 
	 */
	public static final int AUDIT_STATUS_58_1 = 1;
	/**
	 * 58内容审核状态 2驳回
	 */
	public static final int AUDIT_STATUS_58_2 = 2;
	
	/**
	 * 房型是否有效 0无效
	 */
	public static final int IS_HOUSETYPE_VALID_0 = 0;
	
	/**
	 * 房型是否有效 1有效
	 */
	public static final int IS_HOUSETYPE_VALID_1 = 1;
	
	/**
	 * 58风控接口数据类型sceneid 个人房源
	 */
	public static final String SCENEID_58_1 = "1";
	
	/**
	 * 58风控接口数据类型sceneid 品牌公寓
	 */
	public static final String SCENEID_58_2 = "2";
	
	/**
	 * 58风控接口数据类型sceneid 公租房
	 */
	public static final String SCENEID_58_3 = "3";
	
	/**
	 * 58风控接口数据类型sceneid 农民房
	 */
	public static final String SCENEID_58_4 = "4";
	
	/**
	 * 数据分析 时间类型  按周
	 */
	public static final String DATETYPE_WEEKS = "1";
	
	/**
	 * 数据分析 时间类型  按近30日 月
	 */
	public static final String DATETYPE_MONTH = "2";
	
	/**
	 * 获取合同备案流水号 类型
	 */
	public static final String TYPE_HT = "HT";
	
	/**
	 * 获取合同编号 类型（机构）
	 */
	public static final String TYPE_JG = "JG";
	
	public static List<DownValue<Integer>> SEX_LIST = new ArrayList<DownValue<Integer>>();
	public static List<DownValue<Integer>> ORDER_STATUS_LIST = new ArrayList<DownValue<Integer>>();
	public static List<DownValue<Integer>> BILL_STATUS_LIST = new ArrayList<DownValue<Integer>>();
	
	public static List<DownValue<Integer>> PAY_TYPE_LIST = new ArrayList<DownValue<Integer>>();
	public static List<DownValue<Integer>> CARD_TYPE_LIST = new ArrayList<DownValue<Integer>>();
	public static List<DownValue<String>> PAY_WAY_LIST = new ArrayList<DownValue<String>>();
	
	public static List<DownValue<Integer>> ORDER_RENTER_STATUS_LIST = new ArrayList<DownValue<Integer>>();
	public static List<DownValue<Integer>> ORDER_LANDLORD_STATUS_LIST = new ArrayList<DownValue<Integer>>();
	
	public static List<DownValue<Integer>> CONTRACT_STATUS_LIST = new ArrayList<DownValue<Integer>>();
	
	// 合同备案状态列表
	public static List<DownValue<Integer>> BACKUP_FLAG_LIST = new ArrayList<DownValue<Integer>>();
	// 合同备案审核状态列表
	public static List<DownValue<Integer>> BACKUP_AUDIT_LIST = new ArrayList<DownValue<Integer>>();
	
	static {
		SEX_LIST.add(new DownValue<Integer>(SEX_MAN, "男"));
		SEX_LIST.add(new DownValue<Integer>(SEX_WOMAN, "女"));
		SEX_LIST.add(new DownValue<Integer>(SEX_UNKNOWN, "未知"));
		
		
		BILL_STATUS_LIST.add(new DownValue<Integer>(BILL_STATUS_0, "未生成账单"));
		BILL_STATUS_LIST.add(new DownValue<Integer>(BILL_STATUS_1, "等待支付"));
		BILL_STATUS_LIST.add(new DownValue<Integer>(BILL_STATUS_2, "已支付"));
		
		CARD_TYPE_LIST.add(new DownValue<Integer>(CARD_TYPE_1, "储蓄卡"));
		CARD_TYPE_LIST.add(new DownValue<Integer>(CARD_TYPE_2, "信用卡"));
		
		PAY_WAY_LIST.add(new DownValue<String>(PAY_WAY_CHINA, "微信支付"));
		PAY_WAY_LIST.add(new DownValue<String>(PAY_WAY_CHINA, "银联支付"));
		
		ORDER_RENTER_STATUS_LIST.add(new DownValue<Integer>(NEW_ORDER_STATUS_1, "待房东确认"));
		ORDER_RENTER_STATUS_LIST.add(new DownValue<Integer>(NEW_ORDER_STATUS_3, "待签约"));
		ORDER_RENTER_STATUS_LIST.add(new DownValue<Integer>(NEW_ORDER_STATUS_4, "待支付"));
		ORDER_RENTER_STATUS_LIST.add(new DownValue<Integer>(NEW_ORDER_STATUS_5, "已完成"));
		ORDER_RENTER_STATUS_LIST.add(new DownValue<Integer>(NEW_ORDER_STATUS_99, "已关闭"));
		
		ORDER_LANDLORD_STATUS_LIST.add(new DownValue<Integer>(NEW_ORDER_STATUS_1, "待确认"));
		ORDER_LANDLORD_STATUS_LIST.add(new DownValue<Integer>(NEW_ORDER_STATUS_3, "待租客签约"));
		ORDER_LANDLORD_STATUS_LIST.add(new DownValue<Integer>(NEW_ORDER_STATUS_4, "待租客支付"));
		ORDER_LANDLORD_STATUS_LIST.add(new DownValue<Integer>(NEW_ORDER_STATUS_5, "已完成"));
		ORDER_LANDLORD_STATUS_LIST.add(new DownValue<Integer>(NEW_ORDER_STATUS_99, "已关闭"));
		
		CONTRACT_STATUS_LIST.add(new DownValue<Integer>(CONTRACT_STATUS_1, "签约中"));
		CONTRACT_STATUS_LIST.add(new DownValue<Integer>(CONTRACT_STATUS_2, "已生效"));
		CONTRACT_STATUS_LIST.add(new DownValue<Integer>(CONTRACT_STATUS_3, "已废除"));
		CONTRACT_STATUS_LIST.add(new DownValue<Integer>(CONTRACT_STATUS_4, "续约中"));
		CONTRACT_STATUS_LIST.add(new DownValue<Integer>(CONTRACT_STATUS_5, "退租中"));
		
		BACKUP_FLAG_LIST.add(new DownValue<Integer>(BACKUP_FLAG_0, "未备案"));
		BACKUP_FLAG_LIST.add(new DownValue<Integer>(BACKUP_FLAG_1, "已备案"));
		
		BACKUP_AUDIT_LIST.add(new DownValue<Integer>(BACKUP_AUDIT_0, "待审核"));
		BACKUP_AUDIT_LIST.add(new DownValue<Integer>(BACKUP_AUDIT_1, "审核不通过"));
		BACKUP_AUDIT_LIST.add(new DownValue<Integer>(BACKUP_AUDIT_2, "审核通过"));
		BACKUP_AUDIT_LIST.add(new DownValue<Integer>(BACKUP_AUDIT_3, "备案完成"));
		
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
