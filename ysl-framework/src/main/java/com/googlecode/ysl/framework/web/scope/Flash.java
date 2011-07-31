/**
 * <p>Title: Flash.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (C) 2011 YSL. All rights reserved. </p> 
 * <p>Company: </p>
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date 2011-7-31
 * @version 1.0
 */
package com.googlecode.ysl.framework.web.scope;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>Title: Flash</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date 2011-7-31
 *
 */
public class Flash {
	/**
	 * @Fields FLASH_IN_SESSION_KEY : 
	 */
	private static final String FLASH_IN_SESSION_KEY = "__flash__";

	/**
	 * @Fields data : 
	 */
	private Map<String, String> data = new HashMap<String, String>();
	
	/**
	 * @Fields out : 
	 */
	private Map<String, String> out = new HashMap<String, String>();

	/**
	 * <p>Title: restore</p>
	 * <p>Description: </p>
	 * @param request
	 * @return Flash
	 * @throws
	 *
	 */
	public static Flash restore(HttpServletRequest request) {
		Flash flash = new Flash();
		HttpSession session = request.getSession();
		Map<String, String> flashData = (Map<String, String>) session
				.getAttribute(FLASH_IN_SESSION_KEY);
		if (flashData != null) {
			flash.data = flashData;
		}
		return flash;
	}

	/**
	 * <p>Title: save</p>
	 * <p>Description: </p>
	 * @param request
	 * @param response
	 * @return void
	 * @throws
	 *
	 */
	public void save(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if (session != null) {
			session.setAttribute(FLASH_IN_SESSION_KEY, out);
		}
	}

	/**
	 * @Fields current : 
	 */
	private static ThreadLocal<Flash> current = new ThreadLocal<Flash>();

	/**
	 * <p>Title: current</p>
	 * <p>Description: </p>
	 * @return
	 * @return Flash
	 * @throws
	 *
	 */
	public static Flash current() {
		return current.get();
	}

	/**
	 * <p>Title: setCurrent</p>
	 * <p>Description: </p>
	 * @param f
	 * @return void
	 * @throws
	 *
	 */
	public static void setCurrent(Flash f) {
		current.set(f);
	}

	/**
	 * <p>Title: put</p>
	 * <p>Description: </p>
	 * @param key
	 * @param value
	 * @return void
	 * @throws
	 *
	 */
	public void put(String key, String value) {
		if (key.contains(":")) {
			throw new IllegalArgumentException(
					"Character ':' is invalid in a flash key.");
		}
		data.put(key, value);
		out.put(key, value);
	}

	/**
	 * <p>Title: now</p>
	 * <p>Description: </p>
	 * @param key
	 * @param value
	 * @return void
	 * @throws
	 *
	 */
	public void now(String key, String value) {
		if (key.contains(":")) {
			throw new IllegalArgumentException(
					"Character ':' is invalid in a flash key.");
		}
		data.put(key, value);
	}

	/**
	 * <p>Title: put</p>
	 * <p>Description: </p>
	 * @param key
	 * @param value
	 * @return void
	 * @throws
	 *
	 */
	public void put(String key, Object value) {
		if (value == null) {
			put(key, (String) null);
		}
		put(key, value + "");
	}

	/**
	 * <p>Title: error</p>
	 * <p>Description: </p>
	 * @param value
	 * @param args
	 * @return void
	 * @throws
	 *
	 */
	public void error(String value, Object... args) {
		put("error", String.format(value, args));
	}

	/**
	 * <p>Title: success</p>
	 * <p>Description: </p>
	 * @param value
	 * @param args
	 * @return void
	 * @throws
	 *
	 */
	public void success(String value, Object... args) {
		put("success", String.format(value, args));
	}

	/**
	 * <p>Title: discard</p>
	 * <p>Description: </p>
	 * @param key
	 * @return void
	 * @throws
	 *
	 */
	public void discard(String key) {
		out.remove(key);
	}

	/**
	 * <p>Title: discard</p>
	 * <p>Description: </p>
	 * @return void
	 * @throws
	 *
	 */
	public void discard() {
		out.clear();
	}

	/**
	 * <p>Title: keep</p>
	 * <p>Description: </p>
	 * @param key
	 * @return void
	 * @throws
	 *
	 */
	public void keep(String key) {
		if (data.containsKey(key)) {
			out.put(key, data.get(key));
		}
	}

	/**
	 * <p>Title: keep</p>
	 * <p>Description: </p>
	 * @return void
	 * @throws
	 *
	 */
	public void keep() {
		out.putAll(data);
	}

	/**
	 * <p>Title: get</p>
	 * <p>Description: </p>
	 * @param key
	 * @return String
	 * @throws
	 *
	 */
	public String get(String key) {
		return data.get(key);
	}

	/**
	 * <p>Title: remove</p>
	 * <p>Description: </p>
	 * @param key
	 * @return boolean
	 * @throws
	 *
	 */
	public boolean remove(String key) {
		return data.remove(key) != null;
	}

	/**
	 * <p>Title: clear</p>
	 * <p>Description: </p>
	 * @return void
	 * @throws
	 *
	 */
	public void clear() {
		data.clear();
	}

	/**
	 * <p>Title: contains</p>
	 * <p>Description: </p>
	 * @param key
	 * @return boolean
	 * @throws
	 *
	 */
	public boolean contains(String key) {
		return data.containsKey(key);
	}

	/**(non-Javadoc)
	 * <p>Title: toString</p>
	 * <p>Description: </p>
	 * @return
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return data.toString();
	}

	/**
	 * <p>Title: getData</p>
	 * <p>Description: </p>
	 * @return
	 * @return Map<String,String>
	 * @throws
	 *
	 */
	public Map<String, String> getData() {
		return data;
	}
}
