/**
 * <p>Title: AbstractStruts2Action.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (C) 2011 YSL. All rights reserved. </p> 
 * <p>Company: </p>
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date Jun 16, 2011
 * @version 1.0
 */
package com.google.ysl.framework.common.struts2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.criterion.Criterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * <p>Title: AbstractStruts2Action</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date Jun 16, 2011
 *
 */
public abstract class AbstractStruts2Action<T> extends ActionSupport implements
		ModelDriven<T>, Preparable {

	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @Fields logger : 
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	private static final String ENCODING_PREFIX = "encoding";
	private static final String NOCACHE_PREFIX = "no-cache";
	private static final String ENCODING_DEFAULT = "UTF-8";
	private static final boolean NOCACHE_DEFAULT = true;

	private static ObjectMapper mapper = new ObjectMapper();

	// content-type 定义 //
	public static final String TEXT_TYPE = "text/plain";
	public static final String JSON_TYPE = "application/json";
	public static final String XML_TYPE = "text/xml";
	public static final String HTML_TYPE = "text/html";
	public static final String JS_TYPE = "text/javascript";
	public static final String EXCEL_TYPE = "application/vnd.ms-excel";

	// -- Header 定义 --//
	public static final String AUTHENTICATION_HEADER = "Authorization";

	// jqGrid组件相关的参数属性
	private List<T> gridModel = Collections.emptyList();
	private Integer rows = 0;
	private Integer page = 0;
	private Integer total = 0;
	private Long record = new Long(0);
	private String sord;
	private String sidx;

	// 添加和查询有关的成员变量search、searchField、searchString、searchOper
	private Boolean search;
	private String searchField;
	private String searchString;
	private String searchOper;

	private String ajaxResult;

	public abstract Long getResultSize();

	public abstract List<T> listResults(int firstResult, int maxResults);

	// 添加用于根据条件进行查询的方法
	public abstract Long getResultSize(Criterion... criterions);

	public abstract List<T> listResults(int firstResult, int maxResults,
			Criterion... criterions);

	public abstract void sortResults(List<T> results, String field, String order);

	/**
	 * <p>Title: setExpiresHeader</p>
	 * <p>Description: 设置客户端缓存过期时间 的Header.</p>
	 * @param response
	 * @param expiresSeconds
	 * @return void
	 * @throws
	 *
	 */
	public static void setExpiresHeader(HttpServletResponse response,
			long expiresSeconds) {
		// Http 1.0 header
		response.setDateHeader("Expires", System.currentTimeMillis()
				+ expiresSeconds * 1000);
		// Http 1.1 header
		response.setHeader("Cache-Control", "private, max-age="
				+ expiresSeconds);
	}

	/**
	 * <p>Title: setDisableCacheHeader</p>
	 * <p>Description: 设置禁止客户端缓存的Header.</p>
	 * @param response
	 * @return void
	 * @throws
	 *
	 */
	public static void setDisableCacheHeader(HttpServletResponse response) {
		// Http 1.0 header
		response.setDateHeader("Expires", 1L);
		response.addHeader("Pragma", "no-cache");
		// Http 1.1 header
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
	}

	/**
	 * <p>Title: setLastModifiedHeader</p>
	 * <p>Description: 设置LastModified Header.</p>
	 * @param response
	 * @param lastModifiedDate
	 * @return void
	 * @throws
	 *
	 */
	public static void setLastModifiedHeader(HttpServletResponse response,
			long lastModifiedDate) {
		response.setDateHeader("Last-Modified", lastModifiedDate);
	}

	/**
	 * <p>Title: setEtag</p>
	 * <p>Description: 设置Etag Header.</p>
	 * @param response
	 * @param etag
	 * @return void
	 * @throws
	 *
	 */
	public static void setEtag(HttpServletResponse response, String etag) {
		response.setHeader("ETag", etag);
	}

	/**
	 * <p>Title: checkIfModifiedSince</p>
	 * <p>
	 * Description: 
	 * 根据浏览器If-Modified-Since Header, 计算文件是否已被修改.
	 * 如果无修改, checkIfModify返回false ,设置304 not modify status.
	 * </p>
	 * @param request
	 * @param response
	 * @param lastModified 内容的最后修改时间.
	 * @return boolean
	 * @throws
	 *
	 */
	public static boolean checkIfModifiedSince(HttpServletRequest request,
			HttpServletResponse response, long lastModified) {
		long ifModifiedSince = request.getDateHeader("If-Modified-Since");
		if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return false;
		}
		return true;
	}

	/**
	 * <p>Title: checkIfNoneMatchEtag</p>
	 * <p>
	 * Description: 
	 * 根据浏览器 If-None-Match Header, 计算Etag是否已无效.
	 * 如果Etag有效, checkIfNoneMatch返回false, 设置304 not modify status.
	 * </p>
	 * @param request
	 * @param response
	 * @param etag 内容的ETag.
	 * @return boolean
	 * @throws
	 *
	 */
	public static boolean checkIfNoneMatchEtag(HttpServletRequest request,
			HttpServletResponse response, String etag) {
		String headerValue = request.getHeader("If-None-Match");
		if (headerValue != null) {
			boolean conditionSatisfied = false;
			if (!"*".equals(headerValue)) {
				StringTokenizer commaTokenizer = new StringTokenizer(
						headerValue, ",");

				while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
					String currentToken = commaTokenizer.nextToken();
					if (currentToken.trim().equals(etag)) {
						conditionSatisfied = true;
					}
				}
			} else {
				conditionSatisfied = true;
			}

			if (conditionSatisfied) {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				response.setHeader("ETag", etag);
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>Title: setFileDownloadHeader</p>
	 * <p>Description: 设置让浏览器弹出下载对话框的Header.</p>
	 * @param response
	 * @param fileName 下载后的文件名.
	 * @return void
	 * @throws
	 *
	 */
	public static void setFileDownloadHeader(HttpServletResponse response,
			String fileName) {
		try {
			// 中文文件名支持
			String encodedfileName = new String(fileName.getBytes(),
					"ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ encodedfileName + "\"");
		} catch (UnsupportedEncodingException e) {
		}
	}

	/**
	 * <p>Title: getParametersStartingWith</p>
	 * <p>
	 * Description: 
	 * 取得带相同前缀的Request Parameters.
	 * 返回的结果的Parameter名已去除前缀.
	 * </p>
	 * @param request
	 * @param prefix
	 * @return Map<String,Object>
	 * @throws
	 *
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> getParametersStartingWith(
			ServletRequest request, String prefix) {
		Assert.notNull(request, "Request must not be null");
		Enumeration paramNames = request.getParameterNames();
		Map<String, Object> params = new TreeMap<String, Object>();
		if (prefix == null) {
			prefix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values == null || values.length == 0) {
					// Do nothing, no values found at all.
				} else if (values.length > 1) {
					params.put(unprefixed, values);
				} else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}

	// 绕过jsp/freemaker直接输出文本的函数 //

	/**
	 * <p>Title: render</p>
	 * <p>
	 * Description: 
	 * 直接输出内容的简便函数.
	 * eg. render("text/plain", "hello", "encoding:GBK"); render("text/plain",
	 * "hello", "no-cache:false"); render("text/plain", "hello", "encoding:GBK",
	 * "no-cache:false");
	 * </p>
	 * @param contentType
	 * @param content
	 * @param headers 可变的header数组，目前接受的值为"encoding:"或"no-cache:",默认值分别为UTF-8和true.
	 * @return void
	 * @throws
	 *
	 */
	public void render(final String contentType, final String content,
			final String... headers) {
		try {
			// 分析headers参数
			String encoding = ENCODING_DEFAULT;
			boolean noCache = NOCACHE_DEFAULT;
			for (String header : headers) {
				String headerName = StringUtils.substringBefore(header, ":");
				String headerValue = StringUtils.substringAfter(header, ":");

				if (StringUtils.equalsIgnoreCase(headerName, ENCODING_PREFIX)) {
					encoding = headerValue;
				} else if (StringUtils.equalsIgnoreCase(headerName,
						NOCACHE_PREFIX)) {
					noCache = Boolean.parseBoolean(headerValue);
				} else
					throw new IllegalArgumentException(headerName
							+ "不是一个合法的header类型");
			}

			HttpServletResponse response = ServletActionContext.getResponse();

			// 设置headers参数
			String fullContentType = contentType + ";charset=" + encoding;
			response.setContentType(fullContentType);
			if (noCache) {
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
			}

			response.getWriter().write(content);
			response.getWriter().flush();

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * <p>Title: renderText</p>
	 * <p>Description: 直接输出文本.</p>
	 * @param text
	 * @param headers
	 * @return void
	 * @throws
	 *
	 */
	public void renderText(final String text, final String... headers) {
		render(TEXT_TYPE, text, headers);
	}

	/**
	 * <p>Title: renderHtml</p>
	 * <p>Description: 直接输出HTML.</p>
	 * @param html
	 * @param headers
	 * @return void
	 * @throws
	 *
	 */
	public void renderHtml(final String html, final String... headers) {
		render(HTML_TYPE, html, headers);
	}

	/**
	 * <p>Title: renderXml</p>
	 * <p>Description: 直接输出XML.</p>
	 * @param xml
	 * @param headers
	 * @return void
	 * @throws
	 *
	 */
	public void renderXml(final String xml, final String... headers) {
		render(XML_TYPE, xml, headers);
	}

	/**
	 * <p>Title: renderJson</p>
	 * <p>Description: 直接输出JSON.</p>
	 * @param jsonString json字符串.
	 * @param headers
	 * @return void
	 * @throws
	 *
	 */
	public void renderJson(final String jsonString, final String... headers) {
		render(JSON_TYPE, jsonString, headers);
	}

	/**
	 * <p>Title: renderJson</p>
	 * <p>Description: 直接输出JSON,使用Jackson转换Java对象.</p>
	 * @param data 可以是List<POJO>, POJO[], POJO, 也可以Map名值对.
	 * @param headers
	 * @return void
	 * @throws
	 *
	 */
	public void renderJson(final Object data, final String... headers) {
		HttpServletResponse response = initResponseHeader(JSON_TYPE, headers);
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * <p>Title: renderJson</p>
	 * <p>Description: 直接输出JSON.</p>
	 * @param map Map对象,将被转化为json字符串.
	 * @param headers
	 * @return void
	 * @throws
	 *
	 */
	//	public void renderJson(final Map map, final String... headers) {
	//		String jsonString = JSONObject.fromObject(map).toString();
	//		render(JSON_TYPE, jsonString, headers);
	//	}

	/**
	 * <p>Title: renderJson</p>
	 * <p>Description: 直接输出JSON.</p>
	 * @param object Java对象,将被转化为json字符串.
	 * @param headers
	 * @return void
	 * @throws
	 *
	 */
	//	public void renderJson(final Object object, final String... headers) {
	//		String jsonString = JSONObject.fromObject(object).toString();
	//		render(JSON_TYPE, jsonString, headers);
	//	}

	/**
	 * <p>Title: renderJsonp</p>
	 * <p>Description: 直接输出支持跨域Mashup的JSONP.</p>
	 * @param callbackName callback函数名.
	 * @param object Java对象,可以是List<POJO>, POJO[], POJO ,也可以Map名值对, 将被转化为json字符串.
	 * @param headers
	 * @return void
	 * @throws
	 *
	 */
	public void renderJsonp(final String callbackName, final Object object,
			final String... headers) {
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(object);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}

		String result = new StringBuilder().append(callbackName).append("(")
				.append(jsonString).append(");").toString();

		// 渲染Content-Type为javascript的返回内容,输出结果为javascript语句,
		// 如callback197("{html:'Hello World!!!'}");
		render(JS_TYPE, result, headers);
	}

	/**
	 * <p>Title: initResponseHeader</p>
	 * <p>Description: 分析并设置contentType与headers.</p>
	 * @param contentType
	 * @param headers
	 * @return HttpServletResponse
	 * @throws
	 *
	 */
	private static HttpServletResponse initResponseHeader(
			final String contentType, final String... headers) {
		// 分析headers参数
		String encoding = ENCODING_DEFAULT;
		boolean noCache = NOCACHE_DEFAULT;
		for (String header : headers) {
			String headerName = StringUtils.substringBefore(header, ":");
			String headerValue = StringUtils.substringAfter(header, ":");

			if (StringUtils.equalsIgnoreCase(headerName, ENCODING_PREFIX)) {
				encoding = headerValue;
			} else if (StringUtils.equalsIgnoreCase(headerName, NOCACHE_PREFIX)) {
				noCache = Boolean.parseBoolean(headerValue);
			} else {
				throw new IllegalArgumentException(headerName
						+ "不是一个合法的header类型");
			}
		}

		HttpServletResponse response = ServletActionContext.getResponse();

		// 设置headers参数
		String fullContentType = contentType + ";charset=" + encoding;
		response.setContentType(fullContentType);
		if (noCache) {
			setDisableCacheHeader(response);
		}

		return response;
	}

	/**
	 * <p>Title: getSession</p>
	 * <p>Description: 取得HttpSession的简化方法.</p>
	 * @return HttpSession
	 * @throws
	 *
	 */
	public HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	/**
	 * <p>Title: getRequest</p>
	 * <p>Description: 取得HttpRequest的简化方法.</p>
	 * @return HttpServletRequest
	 * @throws
	 *
	 */
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * <p>Title: getResponse</p>
	 * <p>Description: 取得HttpResponse的简化方法.</p>
	 * @return HttpServletResponse
	 * @throws
	 *
	 */
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * <p>Title: getParameter</p>
	 * <p>Description: 取得Request Parameter的简化方法.</p>
	 * @param name
	 * @return String
	 * @throws
	 *
	 */
	public String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	/**
	 * <p>Title: refreshGridModel</p>
	 * <p>Description: </p>
	 * @return String
	 * @throws
	 *
	 */
	public String refreshGridModel() {
		try {
			List<T> results = Collections.emptyList();
			record = this.getResultSize();
			int from = rows * (page - 1);
			int length = rows;
			results = this.listResults(from, length);
			this.setGridModel(results);
			total = (int) Math.ceil((double) record / (double) rows);
			return "jsongrid";
		} catch (Exception e) {
			e.printStackTrace();
			this.addActionError(e.getMessage());
			return ERROR;
		}
	}

	/**
	 * @return the gridModel
	 */
	public List<T> getGridModel() {
		return gridModel;
	}

	/**
	 * @param gridModel the gridModel to set
	 */
	public void setGridModel(List<T> gridModel) {
		this.gridModel = gridModel;
	}

	/**
	 * @return the rows
	 */
	public Integer getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(Integer rows) {
		this.rows = rows;
	}

	/**
	 * @return the page
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * @return the record
	 */
	public Long getRecord() {
		return record;
	}

	/**
	 * @param record the record to set
	 */
	public void setRecord(Long record) {
		this.record = record;
	}

	/**
	 * @return the sord
	 */
	public String getSord() {
		return sord;
	}

	/**
	 * @param sord the sord to set
	 */
	public void setSord(String sord) {
		this.sord = sord;
	}

	/**
	 * @return the sidx
	 */
	public String getSidx() {
		return sidx;
	}

	/**
	 * @param sidx the sidx to set
	 */
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	/**
	 * @return the search
	 */
	public Boolean getSearch() {
		return search;
	}

	/**
	 * @param search the search to set
	 */
	public void setSearch(Boolean search) {
		this.search = search;
	}

	/**
	 * @return the searchField
	 */
	public String getSearchField() {
		return searchField;
	}

	/**
	 * @param searchField the searchField to set
	 */
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	/**
	 * @return the searchString
	 */
	public String getSearchString() {
		return searchString;
	}

	/**
	 * @param searchString the searchString to set
	 */
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	/**
	 * @return the searchOper
	 */
	public String getSearchOper() {
		return searchOper;
	}

	/**
	 * @param searchOper the searchOper to set
	 */
	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	/**
	 * @return the ajaxResult
	 */
	public String getAjaxResult() {
		return ajaxResult;
	}

	/**
	 * @param ajaxResult the ajaxResult to set
	 */
	public void setAjaxResult(String ajaxResult) {
		this.ajaxResult = ajaxResult;
	}

}
