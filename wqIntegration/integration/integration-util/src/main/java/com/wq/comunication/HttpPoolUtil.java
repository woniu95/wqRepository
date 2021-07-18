package com.wq.comunication;

import com.wq.io.IOUtils;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpPoolUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpPoolUtil.class);

	private MultiThreadedHttpConnectionManager connectionManager;

	private HttpClient client;

	// 全局最大连接数
	private final static Integer maxTotalConn = 50;

	// 每主机最大连接数
	private final static Integer maxConnPerHost = 5;

	// 超时时间, 20s
	private final static Integer connectionTimeout = 20 * 1000;

	private volatile static HttpPoolUtil httpPoolUtil = null;

	/**
	 * UTF-8编码
	 */
	private static final String CONTENT_CHARSET_UTF8 = "UTF-8";

	private HttpPoolUtil() {

		connectionManager = new MultiThreadedHttpConnectionManager();

		HttpConnectionManagerParams params = connectionManager.getParams();

		if (maxConnPerHost > maxTotalConn) {
			params.setMaxTotalConnections(maxConnPerHost);
		} else {
			params.setMaxTotalConnections(maxTotalConn);
		}

		params.setDefaultMaxConnectionsPerHost(maxConnPerHost);
		// 连接建立超时时间
		params.setConnectionTimeout(connectionTimeout);
		// 数据接收读取超时时间
		params.setSoTimeout(connectionTimeout);

		client = new HttpClient(connectionManager);

		client.getHostConfiguration().getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, CONTENT_CHARSET_UTF8);

	}

	/**
	 * 单例
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @return
	 */
	public static HttpPoolUtil getSingleton() {

		if (httpPoolUtil == null) {
			synchronized (HttpPoolUtil.class) {
				if (httpPoolUtil == null) {
					httpPoolUtil = new HttpPoolUtil();
				}
			}
		}
		return httpPoolUtil;
	}

	/**
	 * HTTP POST 调用
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @return
	 * @throws Exception
	 */
	public HttpResult post(String url, Map<String, String> params) throws Exception {

		PostMethodUTF8 method = new PostMethodUTF8(url);

		// 最多重试三次
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));

		for (Map.Entry<String, String> entry : params.entrySet()) {
			// 设置参数
			method.setParameter(entry.getKey(), entry.getValue());
		}

		return executeMethod(method);
	}

	public HttpResult doPost(String url, RequestEntity httpEntity) throws Exception {


		PostMethodUTF8 method = new PostMethodUTF8(url);
		// 最多重试三次
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		method.setRequestEntity(httpEntity);

		return executeMethod(method);
	}

	/**
	 * HTTP 调用(支持3次重试)
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * @param url
	 * @param params
	 * @return
	 */
	public HttpResult postWithRetry(String url, Map<String, String> params) {

		for (int i = 0; i < 3; i++) {

			try {
				// 调用接口
				HttpResult result = HttpPoolUtil.getSingleton().post(url, params);
				if (result.getResponseCode() == 200) {
					return result;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

			try {
				// 3秒后再次调用, 间隔越大, 成功率越高, 但不能过大, 易超时
				Thread.sleep(3000L);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
		}

		return null;
	}

	/**
	 * 方法用途: 发送GET请求
	 * 实现步骤: 用于发送GET请求，分为RESTUF和KV方式<br>
	 * @param:String url发送的请求地址 Map params 参数MAP boolean type 发送类型
	 * @return byte[] 得到响应的byte数据数组
	 * @throws IOException
	 */
	public  HttpResult doGet(String url, Map<String, String> params, boolean type) throws IOException {

		List<Header> headers = new ArrayList<Header>();
		headers.add(new Header("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)"));
		client.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
		client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
		StringBuffer queryString = new StringBuffer("");

		url += type ? "/" : "?";

		if (params != null) {
			try {

				for(Map.Entry<String, String> entry : params.entrySet()){
					String param = "";
					if (type) {
						param = URLEncoder.encode(entry.getValue(), CONTENT_CHARSET_UTF8) + "/";
					} else {
						param = entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), CONTENT_CHARSET_UTF8) + "&";
					}
					queryString.append(param);
				}
			} catch (UnsupportedEncodingException e) {
				logger.error(" http.doGet method hanppen UnsupportedEncodingException ", e);
			}

			queryString.delete(queryString.length() - 1, queryString.length());
		}
		logger.info(url + queryString.toString());
		GetMethod getMethod = new GetMethod(url + queryString.toString());

		return executeMethod(getMethod);
	}


	private HttpResult executeMethod(HttpMethod httpMethod) throws IOException {

		HttpResult ret = new HttpResult();
		try {

			long begin = System.currentTimeMillis();

			int statusCode = client.executeMethod(httpMethod);

			ret.setResponseCode(statusCode);

			// 如果返回的是200，则正常处理
			if (statusCode == HttpStatus.SC_OK) {
				String returnMsg = IOUtils.getStringFromInputStream(httpMethod.getResponseBodyAsStream(), CONTENT_CHARSET_UTF8);
				ret.setBody(returnMsg);
			}

			logger.info("HttpPoolUtil 远程调用 耗费时间: {} 毫秒, URL地址: {}",System.currentTimeMillis() - begin, httpMethod.getURI());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			httpMethod.releaseConnection();
		}
		return ret;
	}

	public static class PostMethodUTF8 extends PostMethod {
		public PostMethodUTF8(String url) {

			super(url);
		}

		@Override
		public String getRequestCharSet() {

			return CONTENT_CHARSET_UTF8;
		}
	}

	/**
	 * 封装HTTP返回值
	 * @author gongchao
	 * @version Revision: 0.01  Date: 2011-12-21 下午3:09:18
	 */
	public static class HttpResult {
		private String body;

		private int responseCode;

		public String getBody() {

			return body;
		}

		public void setBody(String body) {

			this.body = body;
		}

		public int getResponseCode() {

			return responseCode;
		}

		public void setResponseCode(int responseCode) {

			this.responseCode = responseCode;
		}

		@Override
		public String toString() {
			return "HttpResult{" + "body='" + body + '\'' + ", responseCode=" + responseCode + '}';
		}
	}

}
