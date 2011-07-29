/**
 * <p>Title: ValidateImageService.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (C) 2011 YSL. All rights reserved. </p> 
 * <p>Company: </p>
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date May 23, 2011
 * @version 1.0
 */
package com.googlecode.ysl.framework.service;

import java.io.ByteArrayOutputStream;

/**
 * <p>Title: ValidateImageService</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date May 23, 2011
 *
 */
public interface ValidateImageService {

	/**
	 * @Fields default_validate_code : 默认验证字符串
	 */
	String default_validate_code = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	/**
	 * @Fields disturb_type_default : 默认绘制干扰线的类型（不绘制干扰线）
	 */
	int disturb_type_default = 0;

	/**
	 * @Fields disturb_type_simple : 绘制单一色调的干扰线
	 */
	int disturb_type_simple = 1;

	/**
	 * @Fields disturb_type_complex : 绘制复杂的干扰线
	 */
	int disturb_type_complex = 2;

	/**
	 * <p>Title: createValidateCode</p>
	 * <p>Description: 生成验证图片并返回验证码</p>
	 * @param disturbType
	 * @param fontSize
	 * @param bos
	 * @param width
	 * @param height
	 * @param validateCode
	 * @param codeLength
	 * @return String
	 * @throws
	 *
	 */
	String createValidateCode(final int disturbType, final int fontSize,
			final ByteArrayOutputStream bos, final int width, final int height,
			final String validateCode, final int codeLength);

}
