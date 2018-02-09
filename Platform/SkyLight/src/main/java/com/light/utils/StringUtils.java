package com.light.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.util.HtmlUtils;

/**
 * 字符串工具类。
 * 
 * <p>(C) 2015 www.uzwork.com (UZWork)</p>
 * Date:  2015-07-10
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class StringUtils {
	
    private static SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_FORMAT);
    private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateUtils.TIME_FORMAT);
	
	/**
	 * 转化Object到String。
	 * 
	 * @param obj obj对象
	 * @return 转换后的字符串
	 */
	public static String getString(Object obj) {
		return obj != null ? obj.toString() : "";
	}

	/**
	 * 取得String的子字符串。
	 * 
	 * @param sourceString 字符串
	 * @param begin 开始位置
	 * @param end 结束位置
	 * @return 字符串
	 */
	public static String getSubString(String sourceString, int begin, int end) {
		String rtn = "";
		if (!isBlank(sourceString)) {
			if (sourceString.length() >= end) {
				rtn = sourceString.substring(begin, end);
			}
			else{
				rtn = sourceString;
			}
		}
		
		return rtn;
	}

	/**
	 *  指定目标编码转化String到String，主要用在JSTL的URL里的中文编码，从JSTL取得的编码需要toLowerCase。
	 *  
	 * @param srcString 原字符串
	 * @param charset 转换字符编码:UTF-8,GBK
	 * @return 转换后的字符串
	 */
	public static String URLEncodeString(String srcString, String charset) {
		String rtn = "";
		if (!isBlank(srcString)) {
			try {
				rtn = URLEncoder.encode(srcString, charset).toLowerCase();
			}
			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return rtn;
	}
	
	/**
	 *  指定目标编码转化String到String。
	 *  
	 * @param srcString 原字符串
	 * @param charset 转换字符编码:UTF-8,GBK
	 * @return 转换后的字符串
	 */
	public static String URLDecodeString(String srcString, String charset) {
		String rtn = "";
		if (!isBlank(srcString)) {
			try {
				rtn = URLDecoder.decode(srcString, charset);
			}
			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return rtn;
	}
	
	/**
	 * 指定原始编码和目标编码转化String到String。
	 * 
	 * @param srcString 原字符串
	 * @param charset 原字符编码
	 * @param newcharset 转换字符编码
	 * @return 转换后的字符串
	 */
	public static String getEncodeString(String srcString, String charset, String newcharset) {
		String rtn = "";
		if (srcString != null) {
			try {
				rtn = new String(srcString.getBytes(charset), newcharset);
			}
			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return rtn;
	}

	/**
	 * 分割字符串，注意此处SP是一个正则表达式，如果传递"|"，需要用"\\|"进行转义。
	 * 
	 * @param str 要分割的字符串
	 * @param sp 正则表达式
	 * @return 分割后的字符串数组
	 */
	public static String[] split(String str, String sp) {
		if (isBlank(str)) {
			return new String[0];
		}
		else if ("|".equals(sp)) {
			sp = "\\|";
		}
		return str.split(sp);
	}


	/**
	 * 字符串加密，Base64加密后会在后面加上一个"\n"，所以需要删除结尾的空格符。
	 * 
	 * @param str 待加密字符串
	 * @return 转换后的字符串
	 */
	public static String base64Encode(String str) {
		String rtn = "";
		if(!isBlank(str)){
			byte[] enStr = new Base64().encode(str.getBytes());
			rtn = new String(enStr);
		}
		return rtn;
	}

	/**
	 * 字符串解密，Base64解密后会在后面加上一个"\n"，需要删除结尾的空格符。
	 * 
	 * @param str 待解密字符串
	 * @return 转换后的字符串
	 */
	public static String base64Decode(String str) {
		String rtn = "";
		if (!isBlank(str)) {
			byte[] deStr = (new Base64()).decode(str.getBytes());
			rtn = new String(deStr);
		}
		
		return rtn;
	}

	/**
	 * 取得对象的整数形式，前提是这个对象可以转换为整数型。
	 * 
	 * @param obj 取整对象
	 * @return 整数
	 */
	public static int getInt(Object obj) {
		int rtn = 0;
		String string = getString(obj);
		if(!isBlank(string)){
			try {
				rtn = Integer.parseInt(string);
			}
			catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
		}
		return rtn;
	}
	
	/**
	 * 取得对象的boolean值。
	 * 
	 * @param obj
	 * @return boolean
	 */
	public static boolean getBoolean(Object obj) {
		boolean rtn = false;
		String string = getString(obj);
		if(!isBlank(string)){
			if("true".equalsIgnoreCase(string) || "Yes".equalsIgnoreCase(string) || "Y".equalsIgnoreCase(string)){
				rtn = true;
			}
		}
		return rtn;
	}
	
	/**
	 * 返回MD5加密后的字符串。
	 * 
	 * @param string 要加密的字符串
	 * @return 加密后的字符串
	 */
	public static String getMd5String(String string) {
		String rtn = "";
		if(!isBlank(string)){
			char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'}; 
			MessageDigest mdInst;
			try {
				mdInst = MessageDigest.getInstance("MD5");
				mdInst.update(string.getBytes());  
				
				byte[] md = mdInst.digest();  
	            // 把密文转换成十六进制的字符串形式  
	            int j = md.length;  
	            char str[] = new char[j * 2];  
	            int k = 0;  
	            for (int i = 0; i < j; i++) {  
	                byte byte0 = md[i];  
	                str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
	                str[k++] = hexDigits[byte0 & 0xf];  
	            }
	            rtn = new String(str);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		return rtn;
	}


    /**
     * 判断给定字符串是否为空。
     *
     * @param str 给定的字符串
     * @return boolean true：为空
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * 判断给定的字符串是否为数字。
     *
     * @param str 给定的字符串
     * @return boolean
     */
    public static boolean isNumeric(String str) {
        if (isBlank(str))
            return false;
        boolean rc = isLong(str);
        return (rc) ? rc : isDouble(str);
    }

    /**
     * 判断给定的字符串是否为小数。
     *
     * @param val 给定的字符串
     * @return boolean true：为小数
     */
    public static boolean isDecimal(String val) {
    	if (isBlank(val))
            return false;
        return isDouble(val);
    }

    /**
     * 将以逗号隔开的字符串转换为字符串数组。
     *
     * @param str
     * @return String array.
     */
    public static String[] delimiteToArray(String str) {
        return delimiteToArray(str, ",");
    }

    /**
     * 将以指定分隔符隔开的字符串转换为字符串数组，如果字符串为空直接返回。
     *
     * @param str
     * @param delimiter 指定分隔符，默认为逗号
     * @return String array
     */
    public static String[] delimiteToArray(String str, String delimiter) {
        String[] strArray = null;
        if (str != null) {
            if (delimiter == null)
                delimiter = ",";
            StringTokenizer tok = new StringTokenizer(str.toString(), delimiter);
            strArray = new String[tok.countTokens()];
            int count = 0;
            while (tok.hasMoreTokens()) {
                String v = tok.nextToken();
                if (!StringUtils.isBlank(v)) {
                	strArray[count++] = v;
                }
            }
        }
        return strArray;
    }

    /**
     * 将字符串数组转换为以逗号为间隔的字符串，并且该字符串前后都逗号，如：",VAL,VAL1,VAL2,"。
     * 如果该数组为空，将返回null。
     *
     * @param strArray
     * @return String
     */
    public static String arrayToDelimite(String[] strArray) {
        return arrayToDelimite(strArray, ",", true, true);
    }
   
    /**
     * 将字符串数组转换为以指定分隔符为间隔的字符串，并且该字符串前后追加指定分隔符。
     * 如果该数组为空，将返回null。
     *
     * @param strArray
     * @param delimiter 指定的字符串
     * @return String
     */
    public static String arrayToDelimite(String[] strArray, String delimiter) {
        return arrayToDelimite(strArray, delimiter, true, true);
    }

    /**
     * 将字符串数组转换为以指定间隔符号为间隔的字符串，并根据指定是否需要在该字符串前后追加指定分隔符。
     * 如果该数组为空，将返回null。
     *
     * @param strArray
     * @param delimiter 指定的字符串
     * @param prepend 是否在字符串前面追加分隔符
     * @param append 是否在字符串后面追加分隔符
     * @return String
     */
    public static String arrayToDelimite(String[] strArray, String delimiter,
                                          boolean prepend, boolean append) {
        return arrayToDelimite(strArray, delimiter, prepend, append, false);
    }

    /**
     * 将字符串数组转换为以指定间隔符号为间隔的字符串，并根据指定是否需要在该字符串前后追加指定分隔符。
     * 如果该数组为空，将返回null。
     *
     * @param strArray
     * @param delimiter 指定的字符串
     * @param prepend 是否在字符串前面追加分隔符
     * @param append 是否在字符串后面追加分隔符
     * @param eliminateDuplicates 是否除去重复的字符串
     * @return String
     */
    public static String arrayToDelimite(String[] strArray, String delimiter,
                                          boolean prepend, boolean append, boolean eliminateDuplicates) {
        if (delimiter == null)
            delimiter = ",";
        String retVal = null;
        if (strArray != null) {
            StringBuffer buff = new StringBuffer();
            int length = strArray.length;
            if (length > 0) {
                if (prepend)
                    buff.append(delimiter);
                boolean isDuplicateValue = false;
                buff.append(delimiter); //Always make sure the buff starts with a delimiter for duplicate checking
                for (int i = 0; i < length; i++) {
                    isDuplicateValue = (eliminateDuplicates ? (buff.indexOf(delimiter + strArray[i] + delimiter) != -1) : false) ;
                    if (!isDuplicateValue) {
                        buff.append(strArray[i]);
                        if (i < length-1)
                            buff.append(delimiter);
                    }
                }
                buff.deleteCharAt(0);   //remove the delimiter added for checking duplicates
                //If the last value is a duplicate value, remove the delimiter added to the end of the string
                if (isDuplicateValue) {
                    buff.deleteCharAt(buff.length()-1);
                }
                if (append)
                    buff.append(delimiter);
            }
            retVal = buff.toString();
        }

        return retVal;
    }

    /**
     * 删除字符串后面的空格。如果该字符串本身为空，直接返回。
     *
     * @param str
     * @return 转换后的字符串
     */
    public static String trimTail(String str) {
        if (str == null) {
            return str;
        }
        char[] val = str.toCharArray();
        int len = val.length;
        while (len > 0 && val[len - 1] == ' ') {
            len--;
        }
        return str.substring(0, len);
    }

    /**
     * 返回HTML字符串中出去HTML标签的纯文本内容。
     *
     * @param html
     * @return 转换后的内容
     */
    public static String htmlToText(String html) {
        String text = html.replaceAll("\\<.*?>", "");
        text = HtmlUtils.htmlUnescape(text);
        return text;
    }

    /**
     * 判断字符串是否为日期，默认的日期格式是"yyyy-MM-dd"。
     * 
     * @param str
     * @return boolean
     */
    public static boolean isDate(String str) {
        boolean rtnValue = true;
        try {
            dateFormat.setLenient(false);
            ParsePosition parsePos = new ParsePosition(0);
            dateFormat.parse(str, parsePos);
            if ( !(parsePos.getIndex() == str.length() && parsePos.getIndex() > 0)) {
                throw new ParseException("Unparseable date: \"" + str + "\"" , parsePos.getErrorIndex());
            }
        }
        catch (Exception eDateTest) {
        	rtnValue = false;
        }

        return rtnValue;
    }

    /**
     * 判断字符串是否为日期时间，默认的日期时间格式是"yyyy-MM-dd HH:mm:dd"。
     * 
     * @param str
     * @return boolean
     */
    public static boolean isDateTime(String str) {
        boolean rtnValue = true;
        dateTimeFormat.setLenient(false);
        try {
        	dateTimeFormat.parse(str);
        } catch (ParseException e) {
        	rtnValue = false;
        }
        return rtnValue;
    }

    /**
     * 判断字符串是否为Long类型。
     * 
     * @param str
     * @return boolean
     */
    public static boolean isLong(String str) {
        boolean rtnValue = true;
        try {
            Long.parseLong(str);
        }
        catch (Exception eLongTest) {
        	rtnValue = false;
        }
        return rtnValue;
    }

    /**
     * 判断字符串是否为Boolean类型，只有该字符串等于“true”才为boolean类型。
     * 
     * @param str
     * @return boolean
     */
    public static boolean isBoolean(String str) {
        boolean rtnValue = true;
        try {
            Boolean.parseBoolean(str);
        }
        catch (Exception eBooleanTest) {
        	rtnValue = false;
        }
        return rtnValue;
    }

    
    /**
     * 判断字符串是否为Short类型。
     * 
     * @param str
     * @return boolean
     */
    public static boolean isShort(String str) {
        boolean rtnValue = true;
        try {
            Short.parseShort(str);
        }
        catch (Exception eShortTest) {
        	rtnValue = false;
        }
        return rtnValue;
    }

    /**
     * 判断字符串是否为Int类型。
     * 
     * @param str
     * @return boolean
     */
    public static boolean isInt(String str) {
        boolean rtnValue = true;
        try {
            Integer.parseInt(str);
        }
        catch (Exception eIntTest) {
        	rtnValue = false;
        }
        return rtnValue;
    }

    /**
     * 判断字符串是否为Float类型。
     * 
     * @param str
     * @return boolean
     */
    public static boolean isFloat(String str) {
        boolean rtnValue = true;
        try {
            Float.parseFloat(str);
        }
        catch (Exception eFloatTest) {
        	rtnValue = false;
        }
        return rtnValue;
    }

    /**
     * 判断字符串是否为Double类型。
     * 
     * @param str
     * @return boolean
     */
    public static boolean isDouble(String str) {
        boolean rtnValue = true;
        try {
            Double.parseDouble(str);
        }
        catch (Exception eDoubleTest) {
        	rtnValue = false;
        }

        return rtnValue;
    }
    
    /**
     * 隐藏手机号码的中间4位，并将其返回
     * 
     * @param cellPhone
     * @return String
     */
    public static String getHiddenCellPhone(String cellPhone) {
        String rtn = "";
    	if(!isBlank(cellPhone) && cellPhone.length() == 11) {
    		rtn = cellPhone.substring(0, 3) + "****" + cellPhone.substring(7);
        }
        return rtn;
    }
    
	public static String encodeCDATA(String str) {
		
		return new StringBuffer("<![CDATA[").append(str).append("]]>").toString();
	}

	static {};
}