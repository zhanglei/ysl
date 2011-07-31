/**
 * <p>Title: HttpInclude.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (C) 2011 YSL. All rights reserved. </p> 
 * <p>Company: </p>
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date 2011-7-31
 * @version 1.0
 */
package com.googlecode.ysl.framework.web.httpinclude;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.ysl.framework.Constants;

/**
 * <p>Title: HttpInclude</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhanglei<zhanglei_job_email@163.com>
 * @date 2011-7-31
 *
 */
public class HttpInclude {

	/**
	 * @Fields logger : 
	 */
	private static Logger logger = LoggerFactory.getLogger(HttpInclude.class);

	/**
	 * @Fields request : 
	 */
	private HttpServletRequest request;

	/**
	 * @Fields response : 
	 */
	private HttpServletResponse response;

	/**
	 * <p>Title: Constructor.</p>
	 * <p>Description: </p>
	 * @param request
	 * @param response
	 */
	public HttpInclude(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	/**
	 * <p>Title: include</p>
	 * <p>Description: </p>
	 * @param includePath
	 * @return String
	 * @throws
	 *
	 */
	public String include(String includePath) {
		StringWriter sw = new StringWriter(8192);
		include(includePath, sw);
		return sw.toString();
	}

	/**
	 * <p>Title: include</p>
	 * <p>Description: </p>
	 * @param includePath
	 * @param writer
	 * @return void
	 * @throws
	 *
	 */
	public void include(String includePath, Writer writer) {
		try {
			if (isRemoteHttpRequest(includePath)) {
				getRemoteContent(includePath, writer);
			} else {
				getLocalContent(includePath, writer);
			}
		} catch (ServletException e) {
			throw new RuntimeException("include error,path:" + includePath
					+ " cause:" + e, e);
		} catch (IOException e) {
			throw new RuntimeException("include error,path:" + includePath
					+ " cause:" + e, e);
		}
	}

	/**
	 * <p>Title: isRemoteHttpRequest</p>
	 * <p>Description: </p>
	 * @param includePath
	 * @return
	 * @return boolean
	 * @throws
	 *
	 */
	private static boolean isRemoteHttpRequest(String includePath) {
		return includePath != null
				&& (includePath.toLowerCase().startsWith("http://") || includePath
						.toLowerCase().startsWith("https://"));
	}

	/**
	 * <p>Title: getLocalContent</p>
	 * <p>Description: </p>
	 * @param includePath
	 * @param writer
	 * @throws ServletException
	 * @throws IOException
	 * @return void
	 * @throws
	 *
	 */
	private void getLocalContent(String includePath, Writer writer)
			throws ServletException, IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(8192);

		CustomOutputHttpServletResponseWrapper customResponse = new CustomOutputHttpServletResponseWrapper(
				response, writer, outputStream);
		request.getRequestDispatcher(includePath).include(request,
				customResponse);

		customResponse.flushBuffer();
		if (customResponse.useOutputStream) {
			writer.write(outputStream.toString(response.getCharacterEncoding())); //TODO: response.getCharacterEncoding()有可能为null
		}
		writer.flush();
	}

	/**
	 * <p>Title: getRemoteContent</p>
	 * <p>Description: </p>
	 * @param urlString
	 * @param writer
	 * @throws MalformedURLException
	 * @throws IOException
	 * @return void
	 * @throws
	 *
	 */
	private void getRemoteContent(String urlString, Writer writer)
			throws MalformedURLException, IOException {
		URL url = new URL(getWithSessionIdUrl(urlString));
		URLConnection conn = url.openConnection();
		setConnectionHeaders(urlString, conn);
		InputStream input = conn.getInputStream();
		try {
			Reader reader = new InputStreamReader(input,
					Utils.getContentEncoding(conn, response));
			Utils.copy(reader, writer);
		} finally {
			if (input != null)
				input.close();
		}
		writer.flush();
	}

	/**
	 * <p>Title: setConnectionHeaders</p>
	 * <p>Description: </p>
	 * @param urlString
	 * @param conn
	 * @return void
	 * @throws
	 *
	 */
	private void setConnectionHeaders(String urlString, URLConnection conn) {
		conn.setReadTimeout(6000);
		conn.setConnectTimeout(6000);
		String cookie = getCookieString();
		conn.setRequestProperty("Cookie", cookie);
		//TODO: 用于支持 httpinclude_header.properties
		//		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.3) Gecko/20100401 Firefox/3.6.3");
		//		conn.setRequestProperty("Host", url.getHost());
		if (logger.isDebugEnabled()) {
			logger.debug("request properties:" + conn.getRequestProperties()
					+ " for url:" + urlString);
		}
	}

	/**
	 * <p>Title: getWithSessionIdUrl</p>
	 * <p>Description: </p>
	 * @param url
	 * @return
	 * @return String
	 * @throws
	 *
	 */
	private String getWithSessionIdUrl(String url) {
		return url;
	}

	/**
	 * @Fields SET_COOKIE_SEPARATOR : 
	 */
	private static final String SET_COOKIE_SEPARATOR = "; ";

	/**
	 * <p>Title: getCookieString</p>
	 * <p>Description: </p>
	 * @return
	 * @return String
	 * @throws
	 *
	 */
	private String getCookieString() {
		StringBuffer sb = new StringBuffer(64);
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (!Constants.SESSION_ID.equals(c.getName())) {
					sb.append(c.getName()).append("=").append(c.getValue())
							.append(SET_COOKIE_SEPARATOR);
				}
			}
		}

		String sessionId = Utils.getSessionId(request);
		if (sessionId != null) {
			sb.append(Constants.SESSION_ID).append("=").append(sessionId)
					.append(SET_COOKIE_SEPARATOR);
		}
		return sb.toString();
	}

	/**
	 * <p>Title: CustomOutputHttpServletResponseWrapper</p>
	 * <p>Description: </p>
	 * <p>Company: </p> 
	 * @author zhanglei<zhanglei_job_email@163.com>
	 * @date 2011-7-31
	 *
	 */
	public static class CustomOutputHttpServletResponseWrapper extends
			HttpServletResponseWrapper {
		/**
		 * @Fields useWriter : 
		 */
		public boolean useWriter = false;
		/**
		 * @Fields useOutputStream : 
		 */
		public boolean useOutputStream = false;

		/**
		 * @Fields printWriter : 
		 */
		private PrintWriter printWriter;

		/**
		 * @Fields servletOutputStream : 
		 */
		private ServletOutputStream servletOutputStream;

		/**
		 * <p>Title: Constructor.</p>
		 * <p>Description: </p>
		 * @param response
		 * @param customWriter
		 * @param customOutputStream
		 */
		public CustomOutputHttpServletResponseWrapper(
				HttpServletResponse response, final Writer customWriter,
				final OutputStream customOutputStream) {
			super(response);
			this.printWriter = new PrintWriter(customWriter);
			this.servletOutputStream = new ServletOutputStream() {
				@Override
				public void write(int b) throws IOException {
					customOutputStream.write(b);
				}

				@Override
				public void write(byte[] b) throws IOException {
					customOutputStream.write(b);
				}

				@Override
				public void write(byte[] b, int off, int len)
						throws IOException {
					customOutputStream.write(b, off, len);
				}
			};
		}

		/**(non-Javadoc)
		 * <p>Title: getWriter</p>
		 * <p>Description: </p>
		 * @return
		 * @throws IOException
		 * @see javax.servlet.ServletResponseWrapper#getWriter()
		 */
		@Override
		public PrintWriter getWriter() throws IOException {
			if (useOutputStream)
				throw new IllegalStateException(
						"getOutputStream() has already been called for this response");
			useWriter = true;
			return printWriter;
		}

		/**(non-Javadoc)
		 * <p>Title: getOutputStream</p>
		 * <p>Description: </p>
		 * @return
		 * @throws IOException
		 * @see javax.servlet.ServletResponseWrapper#getOutputStream()
		 */
		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			if (useWriter)
				throw new IllegalStateException(
						"getWriter() has already been called for this response");
			useOutputStream = true;
			return servletOutputStream;
		}

		/**(non-Javadoc)
		 * <p>Title: flushBuffer</p>
		 * <p>Description: </p>
		 * @throws IOException
		 * @see javax.servlet.ServletResponseWrapper#flushBuffer()
		 */
		@Override
		public void flushBuffer() throws IOException {
			if (useWriter)
				printWriter.flush();
			if (useOutputStream)
				servletOutputStream.flush();
		}

	}

	/**
	 * <p>Title: Utils</p>
	 * <p>Description: </p>
	 * <p>Company: </p> 
	 * @author zhanglei<zhanglei_job_email@163.com>
	 * @date 2011-7-31
	 *
	 */
	static class Utils {
		/**
		 * <p>Title: getContentEncoding</p>
		 * <p>Description: </p>
		 * @param conn
		 * @param response
		 * @return
		 * @return String
		 * @throws
		 *
		 */
		static String getContentEncoding(URLConnection conn,
				HttpServletResponse response) {
			String contentEncoding = conn.getContentEncoding();
			if (conn.getContentEncoding() == null) {
				contentEncoding = parseContentTypeForCharset(conn
						.getContentType());
				if (contentEncoding == null) {
					contentEncoding = response.getCharacterEncoding();
				}
			} else {
				contentEncoding = conn.getContentEncoding();
			}
			return contentEncoding;
		}

		static Pattern p = Pattern.compile("(charset=)(.*)",
				Pattern.CASE_INSENSITIVE);

		/**
		 * <p>Title: parseContentTypeForCharset</p>
		 * <p>Description: </p>
		 * @param contentType
		 * @return
		 * @return String
		 * @throws
		 *
		 */
		private static String parseContentTypeForCharset(String contentType) {
			if (contentType == null)
				return null;
			Matcher m = p.matcher(contentType);
			if (m.find()) {
				return m.group(2).trim();
			}
			return null;
		}

		/**
		 * <p>Title: copy</p>
		 * <p>Description: </p>
		 * @param in
		 * @param out
		 * @throws IOException
		 * @return void
		 * @throws
		 *
		 */
		private static void copy(Reader in, Writer out) throws IOException {
			char[] buff = new char[8192];
			while (in.read(buff) >= 0) {
				out.write(buff);
			}
		}

		/**
		 * <p>Title: getSessionId</p>
		 * <p>Description: </p>
		 * @param request
		 * @return
		 * @return String
		 * @throws
		 *
		 */
		private static String getSessionId(HttpServletRequest request) {
			HttpSession session = request.getSession(false);
			if (session == null) {
				return null;
			}
			return session.getId();
		}
	}

}
