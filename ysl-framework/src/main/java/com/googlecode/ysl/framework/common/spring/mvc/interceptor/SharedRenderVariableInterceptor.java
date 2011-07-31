/**
 * <p>Title: SharedRenderVariableInterceptor.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (C) 2011 YSL. All rights reserved. </p> 
 * <p>Company: </p>
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date May 28, 2011
 * @version 1.0
 */
package com.googlecode.ysl.framework.common.spring.mvc.interceptor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.googlecode.ysl.framework.Constants;
import com.googlecode.ysl.framework.security.web.util.SpringSecurityUtils;
import com.googlecode.ysl.framework.web.httpinclude.HttpInclude;
import com.googlecode.ysl.framework.web.scope.Flash;

/**
 * <p>Title: SharedRenderVariableInterceptor</p>
 * <p>Description: 拦截器,用于存放渲染视图时需要的的共享变量</p>
 * <p>Company: </p> 
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date May 28, 2011
 *
 */
public class SharedRenderVariableInterceptor extends HandlerInterceptorAdapter
		implements InitializingBean {

	/**
	 * @Fields globalRenderVariables : 系统启动并初始化一次的变量
	 */
	private Map<String, Object> globalRenderVariables = new HashMap<String, Object>();

	/**
	 * @Fields logger : 
	 */
	private static Logger logger = LoggerFactory
			.getLogger(SharedRenderVariableInterceptor.class);

	/**(non-Javadoc)
	 * <p>Title: afterPropertiesSet</p>
	 * <p>Description: 在系统启动时会执行</p>
	 * @throws Exception
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		initSharedRenderVariables();
	}

	/**(non-Javadoc)
	 * <p>Title: postHandle</p>
	 * <p>Description: </p>
	 * @param request
	 * @param response
	 * @param handler
	 * @param modelAndView
	 * @throws Exception
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		logger.info("请注意,在这里可以存放渲染视图时需要的的共享变量");

		if (modelAndView == null) {
			return;
		}

		String viewName = modelAndView.getViewName();
		if (viewName != null && !viewName.startsWith("redirect:")) {
			modelAndView.addAllObjects(globalRenderVariables);
			modelAndView.addAllObjects(perRequest(request, response));
		}
	}

	/**
	 * <p>Title: perRequest</p>
	 * <p>Description: </p>
	 * @param request
	 * @param response
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 *
	 */
	protected Map<String, Object> perRequest(HttpServletRequest request,
			HttpServletResponse response) {
		HashMap<String, Object> model = new HashMap<String, Object>();

		model.put("share_current_request_time", new Date());
		model.put("share_current_login_username", SpringSecurityUtils.getCurrentUserName());
		model.put("ctx", request.getContextPath());
		model.put("flash", Flash.current().getData());

		// 为freemarker,velocity提供<jsp:include
		// page="/some/page.jsp"/>功能,使用${httpInclude.include("/servlet/header.do")};
		model.put("httpInclude", new HttpInclude(request, response));

		return model;
	}

	/**
	 * <p>Title: initSharedRenderVariables</p>
	 * <p>Description: 用于初始化 sharedRenderVariables, 全局共享变量请尽量用global前缀</p>
	 * @return void
	 * @throws
	 *
	 */
	private void initSharedRenderVariables() {

		globalRenderVariables.put("global_system_start_time", new Date());

		globalRenderVariables.put("url_prefix", Constants.URL);

		globalRenderVariables.put("img_url_prefix", Constants.IMG_URL);

		globalRenderVariables.put("media_url_prefix", Constants.MEDIA_URL);

		globalRenderVariables.put("static_url_prefix", Constants.STATIC_URL);

	}

}
