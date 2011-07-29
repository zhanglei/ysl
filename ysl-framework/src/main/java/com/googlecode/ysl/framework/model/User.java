/**
 * <p>Title: User.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (C) 2011 YSL. All rights reserved. </p> 
 * <p>Company: </p>
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date May 27, 2011
 * @version 1.0
 */
package com.googlecode.ysl.framework.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.googlecode.ysl.framework.common.hibernate.BaseEntity;

/**
 * <p>Title: User</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date May 27, 2011
 *
 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "ysl_user", uniqueConstraints = {})
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends BaseEntity implements UserDetails {

	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @Fields loginName : 登陆账号
	 */
	@Column(name = "login_name", unique = true, nullable = false, insertable = true, updatable = true, length = 20)
	private String loginName;

	/**
	 * @Fields realName : 真实姓名
	 */
	@Column(name = "real_name", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
	private String realName;

	/**
	 * @Fields password : 密码
	 */
	@Column(name = "password", unique = false, nullable = false, insertable = true, updatable = true, length = 32, columnDefinition = "char(32)")
	private String password;

	/**
	 * @Fields salt : 随机加密字符串
	 */
	@Column(name = "salt", unique = false, nullable = false, insertable = true, updatable = true)
	private Integer salt;

	/**
	 * @Fields createTime : 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
	private Date createTime;

	/**
	 * @Fields lastLoginTime : 最后登录时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_login_time", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	private Date lastLoginTime;

	/**
	 * @Fields lastLoginIp : 最后登录IP
	 */
	@Column(name = "last_login_ip", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	private String lastLoginIp;

	/**
	 * @Fields currentLoginTime : 当前登录时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "current_login_time", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	private Date currentLoginTime;

	/**
	 * @Fields currentLoginIp : 当前登录IP
	 */
	@Column(name = "current_login_ip", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	private String currentLoginIp;

	/**
	 * @Fields loginCount : 总共登录次数
	 */
	@Column(name = "login_count", unique = false, nullable = true, insertable = true, updatable = true)
	private Integer loginCount;

	/**
	 * @Fields disabled : 是否禁用0:禁用1:启用
	 */
	@Column(name = "is_disabled", unique = false, nullable = false, insertable = true, updatable = true)
	private Boolean disabled;

	/**
	 * <p>Title: Constructor.</p>
	 * <p>Description: </p>
	 */
	public User() {
		super();
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the salt
	 */
	public Integer getSalt() {
		return salt;
	}

	/**
	 * @param salt the salt to set
	 */
	public void setSalt(Integer salt) {
		this.salt = salt;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the lastLoginTime
	 */
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	/**
	 * @param lastLoginTime the lastLoginTime to set
	 */
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * @return the lastLoginIp
	 */
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	/**
	 * @param lastLoginIp the lastLoginIp to set
	 */
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	/**
	 * @return the currentLoginTime
	 */
	public Date getCurrentLoginTime() {
		return currentLoginTime;
	}

	/**
	 * @param currentLoginTime the currentLoginTime to set
	 */
	public void setCurrentLoginTime(Date currentLoginTime) {
		this.currentLoginTime = currentLoginTime;
	}

	/**
	 * @return the currentLoginIp
	 */
	public String getCurrentLoginIp() {
		return currentLoginIp;
	}

	/**
	 * @param currentLoginIp the currentLoginIp to set
	 */
	public void setCurrentLoginIp(String currentLoginIp) {
		this.currentLoginIp = currentLoginIp;
	}

	/**
	 * @return the loginCount
	 */
	public Integer getLoginCount() {
		return loginCount;
	}

	/**
	 * @param loginCount the loginCount to set
	 */
	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	/**
	 * @return the disabled
	 */
	public Boolean getDisabled() {
		return disabled;
	}

	/**
	 * @param disabled the disabled to set
	 */
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**(non-Javadoc)
	 * <p>Title: getAuthorities</p>
	 * <p>Description: </p>
	 * @return
	 * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
	 */
	@Transient
	public Collection<GrantedAuthority> getAuthorities() {
		return null;
	}

	/**
	 * <p>Title: getAuthoritiesString</p>
	 * <p>Description: </p>
	 * @return String
	 * @throws
	 *
	 */
	@Transient
	public String getAuthoritiesString() {
		List<String> authorities = new ArrayList<String>();
		for (GrantedAuthority authority : this.getAuthorities()) {
			authorities.add(authority.getAuthority());
		}
		return StringUtils.join(authorities, ",");
	}

	/**(non-Javadoc)
	 * <p>Title: getPassword</p>
	 * <p>Description: </p>
	 * @return
	 * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
	 */
	public String getPassword() {
		return password;
	}

	/**(non-Javadoc)
	 * <p>Title: getUsername</p>
	 * <p>Description: </p>
	 * @return
	 * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	@Transient
	public String getUsername() {
		return loginName;
	}

	/**(non-Javadoc)
	 * <p>Title: isAccountNonExpired</p>
	 * <p>Description: </p>
	 * @return
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
	 */
	public boolean isAccountNonExpired() {
		return true;
	}

	/**(non-Javadoc)
	 * <p>Title: isAccountNonLocked</p>
	 * <p>Description: </p>
	 * @return
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
	 */
	public boolean isAccountNonLocked() {
		return true;
	}

	/**(non-Javadoc)
	 * <p>Title: isCredentialsNonExpired</p>
	 * <p>Description: </p>
	 * @return
	 * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**(non-Javadoc)
	 * <p>Title: isEnabled</p>
	 * <p>Description: </p>
	 * @return
	 * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
	 */
	public boolean isEnabled() {
		return !disabled;
	}

	/**(non-Javadoc)
	 * <p>Title: toString</p>
	 * <p>Description: </p>
	 * @return
	 * @see com.google.ysl.framework.common.hibernate.BaseEntity#toString()
	 */
	@Override
	public String toString() {
		if (this.getId() != null) {
			return new ToStringBuilder(this).append("id", this.getId())
					.toString();
		} else {
			return null;
		}
	}

	/**(non-Javadoc)
	 * <p>Title: equals</p>
	 * <p>Description: </p>
	 * @param o
	 * @return
	 * @see com.google.ysl.framework.common.hibernate.BaseEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof User)) {
			return false;
		}
		User that = (User) o;
		if (this.getId() != null && that.getId() != null) {
			return new EqualsBuilder().append(this.getId(), that.getId())
					.isEquals();
		}
		return false;
	}

	/**(non-Javadoc)
	 * <p>Title: hashCode</p>
	 * <p>Description: </p>
	 * @return
	 * @see com.google.ysl.framework.common.hibernate.BaseEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		if (this.getId() != null) {
			return new HashCodeBuilder().append(this.getId()).toHashCode();
		} else {
			return 0;
		}
	}
}
