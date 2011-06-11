/**
 * <p>Title: Role.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (C) 2011 YSL. All rights reserved. </p> 
 * <p>Company: </p>
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date Jun 11, 2011
 * @version 1.0
 */
package com.google.ysl.framework.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.ysl.framework.common.hibernate.BaseEntity;

/**
 * <p>Title: Role</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date Jun 11, 2011
 *
 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "ysl_role", uniqueConstraints = {})
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role extends BaseEntity {

	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @Fields name : 角色名称
	 */
	@Column(name = "name", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	private String name;

	/**
	 * @Fields code : 角色代码
	 */
	@Column(name = "code", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
	private String code;

	/**
	 * @Fields description : 角色描述
	 */
	@Column(name = "description", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	private String description;

	/**
	 * @Fields disabled : 是否禁用 0:禁用 1:启用
	 */
	@Column(name = "is_disabled", unique = false, nullable = false, insertable = true, updatable = true)
	private Boolean disabled;

	/**
	 * <p>Title: Constructor.</p>
	 * <p>Description: </p>
	 */
	public Role() {
		super();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
		if (!(o instanceof Role)) {
			return false;
		}
		Role that = (Role) o;
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
