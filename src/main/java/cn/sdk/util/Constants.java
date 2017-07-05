package cn.sdk.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sdk.bean.DownValue;

public class Constants {
	
	/**
	 * 腾讯地图key
	 */
	public static final String QQMAP_KEY = "TFHBZ-SF426-3IDSN-EDKYV-MCVA7-UFBGW";
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
	 * 角色级别
	 */
	public static final String COM_CODE_JSJB = "JSJB";
	
	/**
	 * 新闻状态
	 */
	public static final String COM_CODE_XWJB = "XWJB";
	
	/**
	 * 默认密码
	 */
	public static final String PWD_DEFAULT = "123456";
	
	/**
	 * 管理员用户ID
	 */
	public static final int ADMIN_USER_ID = 0;  
	
	/**
	 * 管理员才有的菜单ID
	 */
	public static final List<Integer> ADMIN_MENU_ID_LIST = Arrays.asList(23,25);  
	
	/**
	 * 管理员角色id
	 */
	public static final int ADMIN_ROLE_ID = 0;
	
	/**
	 * 总局id
	 */
	public static final int ADMIN_AGENCY_ID = 1;
	
	/**
	 * 审核权限功能id
	 * 依次是 审核通过/驳回/发布
	 */
	public static final List<Integer> AUDIT_FUNCTION_ID_LIST = Arrays.asList(4,5,7);
	
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
	 *//*
	public static final String MAIN_STATUS_05 = "05"; */
	/**
	 * 审核通过 已发布	
	 */
	public static final String MAIN_STATUS_06 = "06";
	
	/**
	 * 未选中
	 */
	public static final int IS_CHECKED_NO = 0;
	
	/**
	 * 选中
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
	 * 审核表状态 - 审核结束
	 */
	public static final int AUDIT_STATUS_OVER = 2;
	
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
	
	/**
	 * 新闻发布流程
	 */
	public static final String NEWS_AUDIT_PUBLISH = "news_publish";
	
	/**
	 * 新闻删除流程
	 */
	public static final String NEWS_AUDIT_DELETE = "news_deleted ";
	
	public static final String NEWS_AUDIT_PUBLISH_MSG = "发布新闻审核流程";
	
	public static final int IS_TOP_YES = 1;
	
	/**
	 * 文章类型  0=图文 1=视屏 2=微博 3=h5
	 */
	public static final int NEWS_TYPE_TXT = 0; //图文
	public static final int NEWS_TYPE_VIDEO = 1; //视频
	public static final int NEWS_TYPE_WEIBO = 2; //微博
	public static final int NEWS_TYPE_H5 = 3; //h5
	
	/**
	 * 个人
	 */
	public static final String VIEW_RANGE_00 = "00";
	/**
	 * 本单位
	 */
	public static final String VIEW_RANGE_01 = "01";
	
	/**
	 * 全局
	 */
	public static final String VIEW_RANGE_02 = "02";
	
	/**
	 * 全部
	 */
	public static final String VIEW_RANGE_03 = "03";
	
	/**
	 * 分局编辑
	 */
	public static final int ROLE_LEVEL_1 = 1;
	
	/**
	 * 分局审核
	 */
	public static final int ROLE_LEVEL_2 = 2;
	
	/**
	 * 总局编辑
	 */
	public static final int ROLE_LEVEL_3 = 3;
	
	/**
	 * 总局审核
	 */
	public static final int ROLE_LEVEL_4 = 4;
	
	/**
	 * 来源： H5、GA
	 */
	public static final String SOURCE_H5 = "H5";
	public static final String SOURCE_GA = "GA";
	
	
	
	public static List<DownValue<Integer>> SEX_LIST = new ArrayList<DownValue<Integer>>();
	public static List<DownValue<String>> MAIN_STATUS_LIST = new ArrayList<DownValue<String>>();
	public static List<DownValue<String>> LEVEL_LIST = new ArrayList<DownValue<String>>();
	public static List<DownValue<String>> VIEW_RANGE_LIST = new ArrayList<DownValue<String>>();
	public static List<DownValue<Integer>> ROLE_LEVEL_LIST = new ArrayList<DownValue<Integer>>();
	
	static {
		SEX_LIST.add(new DownValue<Integer>(SEX_MAN, "男"));
		SEX_LIST.add(new DownValue<Integer>(SEX_WOMAN, "女"));
		
		MAIN_STATUS_LIST = Arrays.asList(
				new DownValue<String>(MAIN_STATUS_00, "草稿箱"),
				new DownValue<String>(MAIN_STATUS_01, "审核中"),
				new DownValue<String>(MAIN_STATUS_02, "申请撤回中"),
				new DownValue<String>(MAIN_STATUS_03, "申请删除中"),
				new DownValue<String>(MAIN_STATUS_04, "驳回"),
				//new DownValue<String>(MAIN_STATUS_05, "审核通过,待下级审核"),
				new DownValue<String>(MAIN_STATUS_06, "审核通过 已发布")
				);
		LEVEL_LIST = Arrays.asList(
				new DownValue<String>(LEVEL_GREAT, "优"),
				new DownValue<String>(LEVEL_QUALIFIED, "合格"),
				new DownValue<String>(LEVEL_NO_QUALIFIED, "不合格")
				);
		
		VIEW_RANGE_LIST = Arrays.asList(
				new DownValue<String>(VIEW_RANGE_00, "个人"),
				new DownValue<String>(VIEW_RANGE_01, "本单位"),
				new DownValue<String>(VIEW_RANGE_02, "全局"),
				new DownValue<String>(VIEW_RANGE_03, "全部")
				);
		 
		ROLE_LEVEL_LIST = Arrays.asList(
				new DownValue<Integer>(ROLE_LEVEL_1, "分局编辑"),
				new DownValue<Integer>(ROLE_LEVEL_2, "分局审核"),
				new DownValue<Integer>(ROLE_LEVEL_3, "总局编辑"),
				new DownValue<Integer>(ROLE_LEVEL_4, "总局审核")
				);
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
