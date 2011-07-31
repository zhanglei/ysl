/**
 * <p>Title: SpringContextHolder.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (C) 2011 YSL. All rights reserved. </p> 
 * <p>Company: </p>
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date May 9, 2011
 * @version 1.0
 */
package com.googlecode.ysl.framework.common.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * <p>Title: SpringContextHolder</p>
 * <p>Description:以静态变量保存Spring ApplicationContext,可在任何代码任何地方任何时候中取出ApplicaitonContext.</p>
 * <p>Company: </p> 
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date May 9, 2011
 *
 */
public class SpringContextHolder implements ApplicationContextAware,
		DisposableBean {

	/**
	 * @Fields applicationContext : 
	 */
	private static ApplicationContext applicationContext = null;

	/**
	 * @Fields logger : 
	 */
	private static Logger logger = LoggerFactory
			.getLogger(SpringContextHolder.class);

	/**
	 * <p>Title: Constructor.</p>
	 * <p>Description: </p>
	 */
	public SpringContextHolder() {
		super();
	}

	/**
	 * <p>Title: assertContextInjected</p>
	 * <p>Description: 检查ApplicationContext不为空.</p>
	 * @return void
	 * @throws
	 *
	 */
	private static void assertContextInjected() {
		if (applicationContext == null) {
			throw new IllegalStateException(
					"applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
		}
	}

	/**
	 * <p>Title: clear</p>
	 * <p>Description: 清除SpringContextHolder中的ApplicationContext为Null.</p>
	 * @return void
	 * @throws
	 *
	 */
	public static void clear() {
		logger.debug("清除SpringContextHolder中的ApplicationContext:"
				+ applicationContext);
		applicationContext = null;
	}

	/**
	 * <p>Title: getBean</p>
	 * <p>Description: 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.</p>
	 * @param requiredType
	 * @return T
	 * @throws
	 *
	 */
	public static <T> T getBean(Class<T> requiredType) {
		assertContextInjected();
		return applicationContext.getBean(requiredType);
	}

	/**
	 * <p>Title: getBean</p>
	 * <p>Description: 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.</p>
	 * @param name
	 * @return T
	 * @throws
	 *
	 */
	public static <T> T getBean(String name) {
		assertContextInjected();
		return (T) applicationContext.getBean(name);
	}

	/**(non-Javadoc)
	 * <p>Title: destroy</p>
	 * <p>Description: 实现DisposableBean接口, 在Context关闭时清理静态变量.</p>
	 * @throws Exception
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	public void destroy() throws Exception {
		SpringContextHolder.clear();
	}

	/**(non-Javadoc)
	 * <p>Title: setApplicationContext</p>
	 * <p>Description: 实现ApplicationContextAware接口, 注入Context到静态变量中.</p>
	 * @param arg0
	 * @throws BeansException
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		logger.debug("注入ApplicationContext到SpringContextHolder:"
				+ applicationContext);

		if (SpringContextHolder.applicationContext != null) {
			logger.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:"
					+ SpringContextHolder.applicationContext);
		}

		SpringContextHolder.applicationContext = applicationContext; // NOSONAR
	}

	/**
	 * <p>Title: getApplicationContext</p>
	 * <p>Description: 取得存储在静态变量中的ApplicationContext.</p>
	 * @return ApplicationContext
	 * @throws
	 *
	 */
	public static ApplicationContext getApplicationContext() {
		assertContextInjected();
		return applicationContext;
	}
}
