/**
 * <p>Title: ValidateImageServiceImpl.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (C) 2011 YSL. All rights reserved. </p> 
 * <p>Company: </p>
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date May 23, 2011
 * @version 1.0
 */
package com.googlecode.ysl.framework.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.googlecode.ysl.framework.service.ValidateImageService;

/**
 * <p>Title: ValidateImageServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date May 23, 2011
 *
 */
@Service("validateImageService")
public class ValidateImageServiceImpl implements ValidateImageService {

	/**
	 * <p>Title: Constructor.</p>
	 * <p>Description: </p>
	 */
	public ValidateImageServiceImpl() {
		super();
	}

	/**(non-Javadoc)
	 * <p>Title: createValidateCode</p>
	 * <p>Description: </p>
	 * @param disturbType
	 * @param fontSize
	 * @param bos
	 * @param width
	 * @param height
	 * @param validateCode
	 * @param codeLength
	 * @return
	 * @see com.google.ysl.framework.service.ValidateImageService#createValidateCode(int, int, java.io.ByteArrayOutputStream, int, int, java.lang.String, int)
	 */
	public String createValidateCode(int disturbType, int fontSize,
			ByteArrayOutputStream bos, int width, int height,
			String validateCode, int codeLength) {
		BufferedImage bImg = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = bImg.getGraphics();
		Random random = new Random();

		if (null == validateCode || StringUtils.isEmpty(validateCode)) {
			validateCode = default_validate_code;
		}
		if (fontSize >= height) {
			fontSize = height - 1;
		}

		drawOutline(g, width, height);
		switch (disturbType) {
		case disturb_type_simple:
			drawSimpleDisturb(g, random, width, height);
			break;
		case disturb_type_complex:
			drawDisturb(g, random, width, height);
			break;
		default:
			break;
		}

		String code = drawCode(g, random, validateCode, codeLength, width,
				height, fontSize);
		g.dispose();
		try {
			ImageOutputStream imOut = ImageIO.createImageOutputStream(bos);
			ImageIO.write(bImg, "JPEG", imOut);
			imOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return code;
	}

	/**
	 * <p>Title: drawOutline</p>
	 * <p>Description: 绘制边框</p>
	 * @param g
	 * @param width
	 * @param height
	 * @return void
	 * @throws
	 *
	 */
	private void drawOutline(Graphics g, int width, int height) {
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width - 1, height - 1);
	}

	/**
	 * <p>Title: drawDisturb</p>
	 * <p>Description: 绘制比较复杂的干扰线</p>
	 * @param g
	 * @param random
	 * @param width
	 * @param height
	 * @return void
	 * @throws
	 *
	 */
	private void drawDisturb(Graphics g, Random random, int width, int height) {
		int x, y, x1, y1;
		for (int i = 0; i < width; i++) {
			x = random.nextInt(width);
			y = random.nextInt(height);
			x1 = random.nextInt(12);
			y1 = random.nextInt(12);
			g.setColor(getRandomColor(random, 120, 255));
			g.drawLine(x, y, x + x1, y + y1);
			g.fillArc(x, y, x1, y1, random.nextInt(360), random.nextInt(360));
		}
	}

	/**
	 * <p>Title: drawSimpleDisturb</p>
	 * <p>Description: 绘制简单的干扰线</p>
	 * @param g
	 * @param random
	 * @param width
	 * @param height
	 * @return void
	 * @throws
	 *
	 */
	private void drawSimpleDisturb(Graphics g, Random random, int width,
			int height) {
		g.setColor(getRandomColor(random, 160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
	}

	/**
	 * <p>Title: getRandomColor</p>
	 * <p>Description: 取得随机颜色</p>
	 * @param random
	 * @param pMin
	 * @param pMax
	 * @return
	 * @return Color
	 * @throws
	 *
	 */
	private Color getRandomColor(Random random, int pMin, int pMax) {
		pMax = (Math.abs(pMax) > 255 ? 255 : Math.abs(pMax));
		pMin = (Math.abs(pMin) > 255 ? 255 : Math.abs(pMin));

		int r = pMin + random.nextInt(Math.abs(pMax - pMin));
		int g = pMin + random.nextInt(Math.abs(pMax - pMin));
		int b = pMin + random.nextInt(Math.abs(pMax - pMin));

		return new Color(r, g, b);
	}

	/**
	 * <p>Title: drawCode</p>
	 * <p>Description: 绘制验证码</p>
	 * @param g
	 * @param random
	 * @param validateCode
	 * @param codeLength
	 * @param width
	 * @param height
	 * @param fontSize
	 * @return
	 * @return String
	 * @throws
	 *
	 */
	private String drawCode(Graphics g, Random random, String validateCode,
			int codeLength, int width, int height, int fontSize) {
		int validateCodeLength = validateCode.length();
		Font font1 = new Font("Verdana", Font.BOLD, fontSize);
		Font font2 = new Font("serif", Font.BOLD, fontSize);

		StringBuffer sb = new StringBuffer();
		int x, y;
		for (int i = 0; i < codeLength; i++) {
			x = (width / codeLength - 1) * i
					+ random.nextInt(width / (codeLength * 2));
			y = random.nextInt(height - fontSize) + fontSize;
			sb.append(getRandomChar(validateCode, validateCodeLength, random));
			g.setColor(getRandomColor(random, 70, 150));
			if (sb.substring(i).getBytes().length > 1)
				g.setFont(font2);
			else
				g.setFont(font1);
			g.drawString(sb.substring(i), x, y);
		}
		return sb.toString();
	}

	/**
	 * <p>Title: getRandomChar</p>
	 * <p>Description: 取得随机字符</p>
	 * @param validateCode
	 * @param validateCodeLength
	 * @param random
	 * @return
	 * @return char
	 * @throws
	 *
	 */
	private char getRandomChar(String validateCode, int validateCodeLength,
			Random random) {
		return validateCode.charAt(random.nextInt(validateCodeLength));
	}
}
