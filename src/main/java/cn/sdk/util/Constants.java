package cn.sdk.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.sdk.bean.DownValue;

public class Constants {
	/**
	 * 性别
	 */
	public static final String COM_CODE_SEX = "SEX";
	
	/**
	 * 主题列表
	 */
	public static final String COM_CODE_ZTLB = "ZTLB";
	/**
	 * 单位列表
	 */
	public static final String COM_CODE_DWLB = "DWLB";
	/**
	 * 职位列表
	 */
	public static final String COM_CODE_ZWLB = "ZWLB";
	/**
	 * 新闻状态
	 */
	public static final String COM_CODE_XWZT = "XWZT";
	
	/**
	 * 新闻状态
	 */
	public static final String COM_CODE_XWJB = "XWJB";
	
	public static final String PWD_DEFAULT = "123456"; 
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
	 * 默认分页
	 */
	public static final String PAGE_DEFAULT = "1";
	
	/**
	 * 默认页大小
	 */
	public static final String  PAGESIZE_DEFAULT_5 = "5"; 
	public static final String PAGESIZE_DEFAULT_10 = "10"; 
	
	/**
	 * 草稿箱
	 */
	public static final String MAIN_STATUS_00 = "00";  
	/**
	 * 审核中
	 */
	public static final String MAIN_STATUS_01 = "01";  
	/**
	 * 申请撤回中
	 */
	public static final String MAIN_STATUS_02 = "02"; 
	/**
	 * 申请删除中
	 */
	public static final String MAIN_STATUS_03 = "03"; 
	/**
	 * 驳回
	 */
	public static final String MAIN_STATUS_04 = "04";
	/**
	 * 审核通过 待下级审核
	 */
	public static final String MAIN_STATUS_05 = "05"; 
	/**
	 * 审核通过 已发布	
	 */
	public static final String MAIN_STATUS_06 = "06";
	
	/**
	 * 选中
	 */
	public static final int IS_CHECKED_NO = 0;
	
	/**
	 * 未选中
	 */
	public static final int IS_CHECKED_YES = 1;
	
	/**
	 * 功能类型-按钮 
	 */
	public static final String FUNCTION_TYPE_BUTTON = "button";
	
	/**
	 * 功能类型-菜单
	 */
	public static final String FUNCTION_TYPE_MENU = "menu";
	
	/**
	 * 功能类型-功能
	 */
	public static final String FUNCTION_TYPE_FUNCTION = "function";
	
	/**
	 * 审核表状态 - 通过
	 */
	public static final int AUDIT_STATUS_YES = 0;
	
	/**
	 * 审核表状态 - 驳回
	 */
	public static final int AUDIT_STATUS_NO = 1;
	
	/**
	 * 审核表状态 - 已发布
	 */
	public static final int AUDIT_STATUS_PUBLISH = 2;
	
	/**
	 * 用户在线状态 - 不在线
	 */
	public static final int IS_ONLINE_NO = 0;
	
	/**
	 * 用户在线状态 - 在线
	 */
	public static final int IS_ONLINE_YES = 1;
	
	/**
	 * 优
	 */
	public static final String LEVEL_GREAT = "00";
	
	/**
	 * 合格
	 */
	public static final String LEVEL_QUALIFIED = "01";
	
	/**
	 * 不合格
	 */
	public static final String LEVEL_NO_QUALIFIED = "02";
	
	/**
	 * 推荐
	 */
	public static final String THEME_TJ = "2";
	
	public static final String NEWS_AUDIT_PUBLISH = "news_publish";
	
	public static final String NEWS_AUDIT_DELETE = "news_deleted ";
	
	public static final String NEWS_AUDIT_PUBLISH_MSG = "发布新闻审核流程";
	
	public static List<DownValue<Integer>> SEX_LIST = new ArrayList<DownValue<Integer>>();
	public static List<DownValue<String>> MAIN_STATUS_LIST = new ArrayList<DownValue<String>>();
	public static List<DownValue<String>> LEVEL_LIST = new ArrayList<DownValue<String>>();
	
	static {
		SEX_LIST.add(new DownValue<Integer>(SEX_MAN, "男"));
		SEX_LIST.add(new DownValue<Integer>(SEX_WOMAN, "女"));
		
		MAIN_STATUS_LIST = Arrays.asList(
				new DownValue<String>(MAIN_STATUS_00, "草稿箱"),
				new DownValue<String>(MAIN_STATUS_01, "审核中"),
				new DownValue<String>(MAIN_STATUS_02, "申请撤回中"),
				new DownValue<String>(MAIN_STATUS_03, "申请删除中"),
				new DownValue<String>(MAIN_STATUS_04, "驳回"),
				new DownValue<String>(MAIN_STATUS_05, "审核通过,待下级审核"),
				new DownValue<String>(MAIN_STATUS_06, "审核通过 已发布")
				);
		LEVEL_LIST = Arrays.asList(
				new DownValue<String>(LEVEL_GREAT, "优"),
				new DownValue<String>(LEVEL_QUALIFIED, "合格"),
				new DownValue<String>(LEVEL_NO_QUALIFIED, "不合格")
				);
	}
}
