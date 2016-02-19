package com.yoga.china.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具
 * @author sunsiyuan
 *
 */
public class TimeUtil {

	public static void main(String[] args) {
		
	}
	
	/**
	 * 格式化时间
	 * 
	 * @param timeStamp 时间戳
 	 * @param pattern 要转变的格式
	 * @return
	 * 
	 * @author 孙思远 2015年6月4日 上午9:48:55
	 */
	public static String format(long timeStamp, String pattern) {
		return format(new Date(timeStamp), pattern);
	}

	/**
	 * 按照格式把指定格式的字符串转化为时间
	 * 
	 * @param date 要转化的时间
	 * @param pattern date的格式
	 * @return
	 * 
	 * @author 孙思远 2015年5月28日 下午5:24:46
	 */
	public static Date parseTime(String date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}

	/**
	 * 将指定日期格式化为制定格式
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 * 
	 * @author 孙思远 2015年5月28日 下午5:22:36
	 */
	public static String format(Date date, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

	/**
	 * 获取当前时间
	 * 
	 * @param pattern
	 * @return
	 * @author 孙思远 2015年9月8日 上午11:43:53
	 */
	public static String getNowTime(String pattern) {
		return format(System.currentTimeMillis(), pattern);
	}
	
}
