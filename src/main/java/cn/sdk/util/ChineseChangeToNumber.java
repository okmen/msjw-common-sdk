package cn.sdk.util;

import java.util.HashMap;

public class ChineseChangeToNumber {
	 //数字位  
     public static String[] chnNumChar = {"零","一","二","三","四","五","六","七","八","九"};  
     public static char[] chnNumChinese = {'零','一','二','三','四','五','六','七','八','九'};  
     //节权位  
     public static String[] chnUnitSection = {"","万","亿","万亿"};    
     //权位  
     public static String[] chnUnitChar = {"","十","百","千"};  
     public static HashMap intList = new HashMap(); 
     
	 public static int ChineseToNumber(String str){  
	        String str1 = new String();  
	        String str2 = new String();  
	        String str3 = new String();  
	        int k = 0;  
	        boolean dealflag = true;  
	        for(int i=0;i<str.length();i++){//先把字符串中的“零”除去  
	                if('零' == (str.charAt(i))){  
	                    str = str.substring(0, i) + str.substring(i+1);  
	                }  
	        }  
	        String chineseNum = str;  
	        for(int i=0;i<chineseNum.length();i++){  
	            if(chineseNum.charAt(i) == '亿'){  
	                str1 = chineseNum.substring(0,i);//截取亿前面的数字，逐个对照表格，然后转换  
	                k = i+1;  
	                dealflag = false;//已经处理  
	            }  
	            if(chineseNum.charAt(i) == '万'){  
	                str2 = chineseNum.substring(k,i);  
	                str3 = str.substring(i+1);  
	                dealflag = false;//已经处理  
	            }  
	        }  
	        if(dealflag){//如果没有处理  
	            str3 =  chineseNum;  
	        }  
	        int result = sectionChinese(str1) * 100000000 +  
	                sectionChinese(str2) * 10000 + sectionChinese(str3);  
	        return result;  
	    }  
	 
	 	static{  
	        for(int i=0;i<chnNumChar.length;i++){  
	            intList.put(chnNumChinese[i], i);  
	        }  
	          
	        intList.put('十',10);  
	        intList.put('百',100);  
	        intList.put('千', 1000);  
	    }
	 	
	    public static int sectionChinese(String str){  
	        int value = 0;  
	        int sectionNum = 0;  
	        for(int i=0;i<str.length();i++){  
	        	
	            int v = (int) ChineseChangeToNumber.intList.get(str.charAt(i));  
	            if( v == 10 || v == 100 || v == 1000 ){//如果数值是权位则相乘  
	                sectionNum = v * sectionNum;  
	                value = value + sectionNum;  
	            }else if(i == str.length()-1){  
	                value = value + v;  
	            }else{  
	                sectionNum = v;  
	            }  
	        }  
	        return value;  
	    } 
	    
}
