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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import com.google.ysl.framework.common.util.DateConvertUtils;

/**
 * <p>Title: BaseEntity</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date May 9, 2011
 *
 */
/**
 * <p>Title: BaseEntity</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date May 20, 2011
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

	/**
	 * @Fields id : PK
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false, insertable = true, updatable = false, length = 36, columnDefinition = "char(32)")
	private String id;

	/**
	 * @Fields version : Optimistic Locking
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Version
	@Column(name = "version", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
	private Date version;

	/**
	 * <p>Title: Constructor.</p>
	 * <p>Description: </p>
	 */
	public BaseEntity() {
		super();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the version
	 */
	public Date getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Date version) {
		this.version = version;
	}

	/**
	 * <p>Title: convertDateToString</p>
	 * <p>Description: </p>
	 * @param date
	 * @param dateFormat
	 * @return String
	 * @throws
	 *
	 */
	@Transient
	public String convertDateToString(java.util.Date date, String dateFormat) {
		return DateConvertUtils.format(date, dateFormat);
	}

	/**
	 * <p>Title: convertStringToDate</p>
	 * <p>Description: </p>
	 * @param <T>
	 * @param dateString
	 * @param dateFormat
	 * @param targetResultType
	 * @return T
	 * @throws
	 *
	 */
	@Transient
	public <T extends java.util.Date> T convertStringToDate(String dateString,
			String dateFormat, Class<T> targetResultType) {
		return DateConvertUtils.parse(dateString, dateFormat, targetResultType);
	}

	/**(non-Javadoc)
	 * <p>Title: toString</p>
	 * <p>Description: </p>
	 * @return
	 * @see java.lang.Object#toString()
	 */
	public abstract String toString();

	/**(non-Javadoc)
	 * <p>Title: equals</p>
	 * <p>Description: </p>
	 * @param o
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public abstract boolean equals(Object o);

	/**(non-Javadoc)
	 * <p>Title: hashCode</p>
	 * <p>Description: </p>
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	public abstract int hashCode();

}
