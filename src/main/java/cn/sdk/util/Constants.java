package cn.sdk.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sdk.bean.DownValue;

public class Constants {
	
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
	public static final int PAGE_DEFAULT = 1;
	
	/**
	 * 默认页大小
	 */
	public static final int PAGESIZE_DEFAULT_5 = 5; 
	public static final int PAGESIZE_DEFAULT_10 = 10; 
	
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
	
	
	public static List<DownValue> SEX_MAP = new ArrayList<DownValue>();
	
	static {
		SEX_MAP.add(new DownValue(SEX_MAN, "男"));
		SEX_MAP.add(new DownValue(SEX_WOMAN, "女"));
	}

}