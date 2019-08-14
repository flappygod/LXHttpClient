package com.flappygo.lilin.lxhttpclient.Verifier;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

//默认信任所有的服务器
public class DefaultTrustManager implements X509TrustManager {

	public X509Certificate[] getAcceptedIssuers() {
		return new X509Certificate[] {};
	}

	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		/*if (chain == null && authType == null) {
			throw new CertificateException();
		}*/
	}

	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {

		/*if (chain == null && authType == null) {
			throw new CertificateException();
		}*/
	}

}
