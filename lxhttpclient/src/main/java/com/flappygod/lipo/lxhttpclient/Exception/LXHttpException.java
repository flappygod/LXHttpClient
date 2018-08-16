package com.flappygod.lipo.lxhttpclient.Exception;

public class LXHttpException extends Exception {

	private static final long serialVersionUID = 1L;

	// 错误码
	private int ResponseCode;
	// 请求地址
	private String urlStr;

	public LXHttpException(int ResponseCode, String urlStr, String detailMessage) {
		super(detailMessage);
		this.ResponseCode = ResponseCode;
		this.urlStr = urlStr;
	}

	/*********
	 * 获取code
	 * @return
	 */
	public int getResponseCode() {
		return ResponseCode;
	}

	/*********
	 * 设置错误码
	 * @param responseCode
	 */
	public void setResponseCode(int responseCode) {
		ResponseCode = responseCode;
	}

	public String getUrlStr() {
		return urlStr;
	}

	public void setUrlStr(String urlStr) {
		this.urlStr = urlStr;
	}

}
