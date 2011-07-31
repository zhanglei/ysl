/**
 * <p>Title: AbstractHibernateDAO.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (C) 2011 YSL. All rights reserved. </p> 
 * <p>Company: </p>
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date May 26, 2011
 * @version 1.0
 */
package com.googlecode.ysl.framework.common.hibernate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.ysl.framework.common.util.ReflectionUtils;

/**
 * <p>Title: AbstractHibernateDAO</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date May 26, 2011
 *
 */
public class AbstractHibernateDAO<T, PK> implements HibernateDAO<T,PK> {

	/**
	 * @Fields logger : 
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * @Fields persistentClass : 
	 */
	private Class<T> persistentClass;

	/**
	 * <p>Title: Constructor.</p>
	 * <p>Description: </p>
	 */
	public AbstractHibernateDAO() {
		super();
		this.persistentClass = ReflectionUtils
				.getSuperClassGenricType(getClass());
	}

	/**
	 * @return the persistentClass
	 */
	public Class<T> getPersistentClass() {
		return persistentClass;
	}
}
