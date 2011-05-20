/**
 * <p>Title: DateConvertUtils.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (C) 2011 YSL. All rights reserved. </p> 
 * <p>Company: </p>
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date May 20, 2011
 * @version 1.0
 */
package com.google.ysl.framework.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;

/**
 * <p>Title: DateConvertUtils</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date May 20, 2011
 *
 */
public final class DateConvertUtils {

	/**
	 * <p>Title: Constructor.</p>
	 * <p>Description: </p>
	 */
	private DateConvertUtils() {
		super();
	}

	/**
	 * <p>Title: parse</p>
	 * <p>Description: </p>
	 * @param dateString
	 * @param dateFormat
	 * @return java.util.Date
	 * @throws
	 *
	 */
	public static java.util.Date parse(String dateString, String dateFormat) {
		return parse(dateString, dateFormat, java.util.Date.class);
	}

	/**
	 * <p>Title: parse</p>
	 * <p>Description: </p>
	 * @param <T>
	 * @param dateString
	 * @param dateFormat
	 * @param targetResultType
	 * @return T
	 * @throws
	 *
	 */
	public static <T extends java.util.Date> T parse(String dateString,
			String dateFormat, Class<T> targetResultType) {
		if (StringUtils.isEmpty(dateString))
			return null;
		DateFormat df = new SimpleDateFormat(dateFormat);
		try {
			long time = df.parse(dateString).getTime();
			java.util.Date t = targetResultType.getConstructor(long.class)
					.newInstance(time);
			return (T) t;
		} catch (ParseException e) {
			String errorInfo = "cannot use dateformat:" + dateFormat
					+ " parse datestring:" + dateString;
			throw new IllegalArgumentException(errorInfo, e);
		} catch (Exception e) {
			throw new IllegalArgumentException("error targetResultType:"
					+ targetResultType.getName(), e);
		}
	}

	/**
	 * <p>Title: format</p>
	 * <p>Description: </p>
	 * @param date
	 * @param dateFormat
	 * @return String
	 * @throws
	 *
	 */
	public static String format(java.util.Date date, String dateFormat) {
		if (date == null)
			return null;
		DateFormat df = new SimpleDateFormat(dateFormat);
		return df.format(date);
	}
	
}
