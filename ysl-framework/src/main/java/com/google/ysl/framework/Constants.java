/**
 * <p>Title: Constants.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (C) 2011 YSL. All rights reserved. </p> 
 * <p>Company: </p>
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date May 9, 2011
 * @version 1.0
 */
package com.google.ysl.framework;

/**
 * <p>Title: Constants</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date May 9, 2011
 *
 */
public class Constants {

	/**
	 * @Fields server name :
	 */
	public static final String SERVER_NAME = "ysl-framework";

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
	 * @Fields attached path :
	 */
	public static final String ATTACHED_PATH = "/attached";

	/**
	 * @Fields attached document path :
	 */
	public static final String ATTACHED_DOCUMENT_PATH = ATTACHED_PATH
			+ "/document";

	/**
	 * @Fields attached photo path :
	 */
	public static final String ATTACHED_PHOTO_PATH = ATTACHED_PATH + "/photo";

	/**
	 * @Fields attached picture path :
	 */
	public static final String ATTACHED_PICTURE_PATH = ATTACHED_PATH
			+ "/picture";

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
	public static final String HOST = "www.ysl-framework.com";

	/**
	 * @Fields URL : 
	 */
	public static final String URL = "http://www.ysl-framework.com";

}
