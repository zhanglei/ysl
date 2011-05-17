/**
 * <p>Title: Constants.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (C) 2011 YSL. All rights reserved. </p> 
 * <p>Company: </p>
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date May 17, 2011
 * @version 1.0
 */
package com.google.ysl.oauth;

/**
 * <p>Title: Constants</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date May 17, 2011
 *
 */
public class Constants {

	/**
	 * @Fields server name :
	 */
	public static final String SERVER_NAME = "ysl-oauth";

	/**
	 * @Fields session key :
	 */
	public static final String SESSION_KEY = "_session_key_";

	/**
	 * @Fields session expiry time :
	 */
	public static final int SESSION_EXPIRY_TIME = 60 * 60;

	/**
	 * @Fields cookie path :
	 */
	public static final String COOKIE_PATH = "/";

	/**
	 * @Fields cookie domain :
	 */
	public static final String COOKIE_DOMAIN = ".ysl.com";

	/**
	 * @Fields cookie expiry time :
	 */
	public static final int COOKIE_EXPIRY_TIME = 60 * 60;

	/**
	 * @Fields default encoding :
	 */
	public static final String ENCODING_DEFAULT = "UTF-8";

	/**
	 * @Fields File separator from System properties :
	 */
	public static final String FILE_SEP = System.getProperty("file.separator");

	/**
	 * @Fields HOST : 
	 */
	public static final String HOST = "open.ysl.com";

	/**
	 * @Fields URL : 
	 */
	public static final String URL = "http://open.ysl.com";

	/**
	 * @Fields OPEN_API_URL : 
	 */
	public static final String OPEN_API_URL = URL + "/api";

}
