package com.flappygod.lipo.lxhttpclient.Verifier;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**********
 * 
 * 默认完全不进行验证
 * 
 * @author lijunlin
 */
public class DefaultHostnameVerifier implements HostnameVerifier {

	@Override
	public boolean verify(String hostname, SSLSession session) {
		return true;
	}

}
