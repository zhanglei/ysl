/**
 * <p>Title: SpringSecurityUtils.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (C) 2011 YSL. All rights reserved. </p> 
 * <p>Company: </p>
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date May 20, 2011
 * @version 1.0
 */
package com.google.ysl.framework.security.web.util;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * <p>Title: SpringSecurityUtils</p>
 * <p>Description: SpringSecurity�Ĺ�����</p>
 * <p>Company: </p> 
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date May 20, 2011
 *
 */
public final class SpringSecurityUtils {

	/**
	 * <p>Title: Constructor.</p>
	 * <p>Description: </p>
	 */
	private SpringSecurityUtils() {
		super();
	}

	/**
	 * <p>Title: getCurrentUser</p>
	 * <p>Description: 取得当前用户, 返回值为SpringSecurity的User类或其子类, 如果当前用户未登录则返回null.</p>
	 * @return UserDetails
	 * @throws
	 *
	 */
	public static UserDetails getCurrentUser() {
		Authentication authentication = getAuthentication();

		if (authentication == null) {
			return null;
		}

		Object principal = authentication.getPrincipal();
		if (!(principal instanceof User)) {
			return null;
		}

		return (UserDetails) principal;
	}

	/**
	 * <p>Title: getCurrentUserName</p>
	 * <p>Description: 取得当前用户的登录名, 如果当前用户未登录则返回空字符串.</p>
	 * @return String
	 * @throws
	 *
	 */
	public static String getCurrentUserName() {
		Authentication authentication = getAuthentication();

		if (authentication == null || authentication.getPrincipal() == null) {
			return "";
		}

		return authentication.getName();
	}

	/**
	 * <p>Title: getCurrentUserIp</p>
	 * <p>Description: 取得当前用户登录IP, 如果当前用户未登录则返回空字符串.</p>
	 * @return String
	 * @throws
	 *
	 */
	public static String getCurrentUserIp() {
		Authentication authentication = getAuthentication();

		if (authentication == null) {
			return "";
		}

		Object details = authentication.getDetails();
		if (!(details instanceof WebAuthenticationDetails)) {
			return "";
		}

		WebAuthenticationDetails webDetails = (WebAuthenticationDetails) details;
		return webDetails.getRemoteAddress();
	}

	/**
	 * <p>Title: hasAnyRole</p>
	 * <p>Description: 判断用户是否拥有角色, 如果用户拥有参数中的任意一个角色则返回true.</p>
	 * @param roles
	 * @return boolean
	 * @throws
	 *
	 */
	public static boolean hasAnyRole(String... roles) {
		Authentication authentication = getAuthentication();

		if (authentication == null) {
			return false;
		}

		Collection<GrantedAuthority> grantedAuthorityList = authentication
				.getAuthorities();
		for (String role : roles) {
			for (GrantedAuthority authority : grantedAuthorityList) {
				if (role.equals(authority.getAuthority())) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * <p>Title: saveUserDetailsToContext</p>
	 * <p>Description: 将UserDetails保存到Security Context.</p>
	 * @param userDetails 已初始化好的用户信息.
	 * @param request 用于获取用户IP地址信息,可为Null.
	 * @return void
	 * @throws
	 *
	 */
	public static void saveUserDetailsToContext(UserDetails userDetails,
			HttpServletRequest request) {
		PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(
				userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());

		if (request != null) {
			authentication.setDetails(new WebAuthenticationDetails(request));
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	/**
	 * <p>Title: getAuthentication</p>
	 * <p>Description: 取得Authentication, 如当前SecurityContext为空时返回null.</p>
	 * @return Authentication
	 * @throws
	 *
	 */
	private static Authentication getAuthentication() {
		SecurityContext context = SecurityContextHolder.getContext();

		if (context == null) {
			return null;
		}

		return context.getAuthentication();
	}

}
