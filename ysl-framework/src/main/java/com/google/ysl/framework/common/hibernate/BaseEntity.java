/**
 * <p>Title: BaseEntity.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (C) 2011 YSL. All rights reserved. </p> 
 * <p>Company: </p>
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date May 9, 2011
 * @version 1.0
 */
package com.google.ysl.framework.common.hibernate;

import javax.persistence.MappedSuperclass;

/**
 * <p>Title: BaseEntity</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date May 9, 2011
 *
 */
@MappedSuperclass
public abstract class BaseEntity implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @Fields DATE_FORMAT : 
	 */
	protected static final String DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * @Fields TIME_FORMAT : 
	 */
	protected static final String TIME_FORMAT = "HH:mm:ss";

	/**
	 * @Fields DATE_TIME_FORMAT : 
	 */
	protected static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * @Fields TIMESTAMP_FORMAT : 
	 */
	protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
	
}
