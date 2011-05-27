/**
 * <p>Title: User.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (C) 2011 YSL. All rights reserved. </p> 
 * <p>Company: </p>
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date May 27, 2011
 * @version 1.0
 */
package com.google.ysl.framework.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.ysl.framework.common.hibernate.BaseEntity;

/**
 * <p>Title: User</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date May 27, 2011
 *
 */
public class User extends BaseEntity implements UserDetails {

	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "name", unique = true, nullable = false, insertable = true, updatable = true, length = 50)
	private String name;

	@Column(name = "password", unique = false, nullable = false, insertable = true, updatable = true, length = 32, columnDefinition = "char(32)")
	private String password;

	@Column(name = "SALT", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer salt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
	private Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_login_time", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	private Date lastLoginTime;

	@Column(name = "last_login_ip", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	private String lastLoginIp;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "current_login_time", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	private Date currentLoginTime;

	@Column(name = "current_login_ip", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	private String currentLoginIp;

	@Column(name = "login_count", unique = false, nullable = true, insertable = true, updatable = true)
	private Integer loginCount;

	@Column(name = "is_disabled", unique = false, nullable = false, insertable = true, updatable = true)
	private Boolean disabled;

	@Override
	public String toString() {
		return null;
	}

	@Override
	public boolean equals(Object o) {
		return false;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		return null;
	}

	public String getPassword() {
		return null;
	}

	public String getUsername() {
		return null;
	}

	public boolean isAccountNonExpired() {
		return false;
	}

	public boolean isAccountNonLocked() {
		return false;
	}

	public boolean isCredentialsNonExpired() {
		return false;
	}

	public boolean isEnabled() {
		return false;
	}

}
