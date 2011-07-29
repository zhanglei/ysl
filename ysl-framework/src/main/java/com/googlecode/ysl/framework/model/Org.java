/**
 * <p>Title: Org.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (C) 2011 YSL. All rights reserved. </p> 
 * <p>Company: </p>
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date Jun 11, 2011
 * @version 1.0
 */
package com.googlecode.ysl.framework.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;

import com.googlecode.ysl.framework.common.hibernate.BaseEntity;

/**
 * <p>Title: Org</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date Jun 11, 2011
 *
 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "ysl_org", uniqueConstraints = {})
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Org extends BaseEntity {

	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "name", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	private String name;

	@Column(name = "description", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	private String description;

	@ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, targetEntity = Org.class, optional = false)
	@JoinColumn(name = "parent_id", unique = false, nullable = true, insertable = true, updatable = true, columnDefinition = "char(32)")
	@ForeignKey(name = "fk_org")
	private Org parent;

	@Column(name = "is_disabled", unique = false, nullable = false, insertable = true, updatable = true)
	private Boolean disabled;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "org")
	private Set<User> users = new HashSet<User>();

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "parent")
	private Set<Org> childs = new HashSet<Org>();

	/**
	 * <p>Title: Constructor.</p>
	 * <p>Description: </p>
	 */
	public Org() {
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
	 * @return the parent
	 */
	public Org getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Org parent) {
		this.parent = parent;
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
	 * @return the users
	 */
	public Set<User> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(Set<User> users) {
		this.users = users;
	}

	/**
	 * @return the childs
	 */
	public Set<Org> getChilds() {
		return childs;
	}

	/**
	 * @param childs the childs to set
	 */
	public void setChilds(Set<Org> childs) {
		this.childs = childs;
	}

	/**
	 * <p>Title: isRoot</p>
	 * <p>Description: </p>
	 * @return Boolean
	 * @throws
	 *
	 */
	@Transient
	public Boolean isRoot() {
		return parent == null ? true : false;
	}

	/**
	 * <p>Title: getRootOrg</p>
	 * <p>Description: </p>
	 * @return Org
	 * @throws
	 *
	 */
	@Transient
	public Org getRootOrg() {
		if (isRoot()) {
			return this;
		} else {
			return parent.getRootOrg();
		}
	}

	/**
	 * <p>Title: addChild</p>
	 * <p>Description: </p>
	 * @param org
	 * @return void
	 * @throws
	 *
	 */
	@Transient
	public void addChild(Org org) {
		getChilds().add(org);
		org.setParent(this);
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
		if (!(o instanceof Org)) {
			return false;
		}
		Org that = (Org) o;
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
