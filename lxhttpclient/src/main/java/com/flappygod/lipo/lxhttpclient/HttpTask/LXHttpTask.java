package com.flappygod.lipo.lxhttpclient.HttpTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import com.flappygod.lipo.lxhttpclient.Holder.LXHttpHeaderHolder;
import com.flappygod.lipo.lxhttpclient.Exception.LXHttpException;
import com.flappygod.lipo.lxhttpclient.Interface.LXHttpTaskPreParseInterface;

/******************
 * LXHttpTask 请求任务
 */
public class LXHttpTask extends LXHttpTaskBase {

	// 请求的Param参数
	private HashMap<String, Object> hashMap;
	// 请求的地址
	private String url;
	// 读取超时时间
	private int readTimeOut = 60;
	// 连接超时时间
	private int connectTimeOut = 60;
	// 编码类型
	private String charset = "utf-8";
	// cookie
	private LXHttpHeaderHolder cookieHolder;
	// 用于增加的参数
	private HashMap<String, String> mRequestProperty;
	//重定向
	private boolean  instanceFollowRedirects=true;

	/**************
	 * 构造器
	 *
	 * @param url
	 *            地址
	 * @param hashMap
	 *            请求参数
	 */
	public LXHttpTask(String url, HashMap<String, Object> hashMap) {
		super();
		this.url = url;
		this.hashMap = hashMap;
	}

	public boolean isInstanceFollowRedirects() {
		return instanceFollowRedirects;
	}

	public void setInstanceFollowRedirects(boolean instanceFollowRedirects) {
		this.instanceFollowRedirects = instanceFollowRedirects;
	}

	public void addRequestProperty(String key, String value) {
		// 如果为空，创建
		if (mRequestProperty == null) {
			this.mRequestProperty = new HashMap<String, String>();
		}
		mRequestProperty.put(key, value);
	}

	public int getReadTimeOut() {
		return readTimeOut;
	}

	public void setReadTimeOut(int readTimeOut) {
		this.readTimeOut = readTimeOut;
	}

	public int getConnectTimeOut() {
		return connectTimeOut;
	}

	public void setConnectTimeOut(int connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public LXHttpHeaderHolder getHeaderHolder() {
		return cookieHolder;
	}

	public void setHeaderHolder(LXHttpHeaderHolder cookie) {
		this.cookieHolder = cookie;
	}



	/*************
	 * 以json的形式进行post
	 *
	 * @return 请求结果
	 * @throws Exception
	 *             异常
	 */
	public Object postAsJson() throws Exception {
		return postJson(getPostJsonStr(hashMap));
	}

	/*************
	 * 传入json进行post
	 *
	 * @return 请求结果
	 * @throws Exception
	 *             异常
	 */
	public Object postJson(JSONObject jb) throws Exception {
		return postJson(jb.toString());
	}

	/*************
	 * 以param的形式进行post
	 *
	 * @return 请求结果
	 * @throws Exception
	 *             异常
	 */
	public Object postAsParam() throws Exception {
		return postParam(getPostParamStr(hashMap,charset));
	}

	/*************
	 * 传入param进行post
	 *
	 * @return 请求结果
	 * @throws Exception
	 *             异常
	 */
	public Object postParam(HashMap<String, Object> param) throws Exception {
		return postParam(getPostParamStr(param,charset));
	}

	/********************
	 * 直接postJson数据
	 *
	 * @param jsonData
	 *            json数据
	 * @return 请求结果
	 * @throws Exception
	 *             异常
	 */
	private Object postJson(String jsonData) throws Exception {
		URL surl = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) surl.openConnection();

		conn.setInstanceFollowRedirects(instanceFollowRedirects);
		// 方式为post
		conn.setRequestMethod("POST");
		// 时间
		conn.setReadTimeout(readTimeOut * 1000);
		// 时间
		conn.setConnectTimeout(connectTimeOut * 1000);
		// 设置通用属性
		conn.setRequestProperty("Content-Type", "application/json;charset="
				+ charset);
		conn.setRequestProperty("Accept-Charset", charset);
		// 设置通用属性
		conn.setRequestProperty("accept", "*/*");
		// 在连接期间可以处理多个请求/响应
		conn.setRequestProperty("connection", "close");

		supplementaryConnection(conn);
		// 输出
		conn.setDoOutput(true);
		// 输入
		conn.setDoInput(true);
		// 获取URLConnection对象对应的输出流
		conn.getOutputStream().write(jsonData.getBytes(charset));
		conn.getOutputStream().flush();
		conn.getOutputStream().close();
		int responseCode = conn.getResponseCode();
		if (responseCode != HttpURLConnection.HTTP_OK) {
			generateSession(conn);
			throw new LXHttpException(responseCode, url,
					conn.getResponseMessage());
		} else {
			InputStream inputStream = conn.getInputStream();
			return preParseData(convertStreamToRetObject(inputStream,charset));
		}
	}

	/********************
	 * 直接postParam数据
	 *
	 * @param paramData
	 *            param数据
	 * @return 请求结果
	 * @throws Exception
	 *             异常
	 */
	private Object postParam(String paramData) throws Exception {
		URL surl = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) surl.openConnection();
		//重定向
		conn.setInstanceFollowRedirects(instanceFollowRedirects);
		// 方式为post
		conn.setRequestMethod("POST");
		// 时间
		conn.setReadTimeout(readTimeOut * 1000);
		// 时间
		conn.setConnectTimeout(connectTimeOut * 1000);
		// param类型
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded;charset=" + charset);
		conn.setRequestProperty("Accept-Charset", charset);
		// 设置通用属性
		conn.setRequestProperty("accept", "*/*");
		// 在连接期间可以处理多个请求/响应
		conn.setRequestProperty("connection", "close");

		supplementaryConnection(conn);

		// 发送POST请求必须设置如下两行
		conn.setDoOutput(true);
		conn.setDoInput(true);
		// 获取URLConnection对象对应的输出流
		conn.getOutputStream().write(paramData.getBytes(charset));
		conn.getOutputStream().flush();
		conn.getOutputStream().close();
		int responseCode = conn.getResponseCode();
		if (responseCode != HttpURLConnection.HTTP_OK) {
			generateSession(conn);
			throw new LXHttpException(responseCode, url,
					conn.getResponseMessage());
		} else {
			InputStream inputStream = conn.getInputStream();
			return preParseData(convertStreamToRetObject(inputStream,charset));
		}
	}

	/************
	 * 执行get请求
	 *
	 * @return 请求结果
	 * @throws Exception
	 */
	public Object get() throws Exception {
		String urlstr = url + getParamStr(hashMap, charset);
		URL surl = new URL(urlstr);
		HttpURLConnection conn = (HttpURLConnection) surl.openConnection();
		//重定向
		conn.setInstanceFollowRedirects(instanceFollowRedirects);
		// get方式
		conn.setRequestMethod("GET");
		// 时间
		conn.setReadTimeout(readTimeOut * 1000);
		// 时间
		conn.setConnectTimeout(connectTimeOut * 1000);
		// 请求类型
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded;charset=" + charset);
		conn.setRequestProperty("Accept-Charset", charset);
		// 设置通用属性
		conn.setRequestProperty("accept", "*/*");
		// 在连接期间可以处理多个请求/响应
		conn.setRequestProperty("connection", "close");
		// 处理
		supplementaryConnection(conn);

		int responseCode = conn.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			generateSession(conn);
			InputStream inputStream = conn.getInputStream();
			return preParseData(convertStreamToRetObject(inputStream,charset));
		} else {
			throw new LXHttpException(responseCode, urlstr,
					conn.getResponseMessage());
		}
	}

	/***********
	 * 对连接进行初始化
	 *
	 * @param conn
	 */
	private void supplementaryConnection(HttpURLConnection conn) {
		// 判断cookieHolder中的值
		if (cookieHolder != null && cookieHolder.getCookie() != null
				&& !cookieHolder.getCookie().equals("")) {
			conn.setRequestProperty("Cookie", cookieHolder.getCookie());
		}
		// 设置增添的参数
		if (mRequestProperty != null) {
			Iterator<Entry<String, String>> iter = mRequestProperty.entrySet()
					.iterator();
			while (iter.hasNext()) {
				Entry<String, String> entry = (Entry<String, String>) iter
						.next();
				conn.setRequestProperty(entry.getKey(), entry.getValue());
			}
		}
	}


	/************
	 * 保存当前的cookie
	 * @param conn
	 *            连接
	 */
	private void generateSession(HttpURLConnection conn) {
		cookieHolder = new LXHttpHeaderHolder(conn.getHeaderFields());
	}

	/****************
	 * 预处理数据
	 * @param data          数据
	 * @return
	 * @throws Exception
     */
	public  Object preParseData(Object data) throws Exception{
		return data;
	}

}
