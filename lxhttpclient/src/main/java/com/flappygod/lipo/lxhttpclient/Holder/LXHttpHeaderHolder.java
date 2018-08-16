package com.flappygod.lipo.lxhttpclient.Holder;


import java.util.List;
import java.util.Map;

/**************
 * 返回参数的holder
 */
public class LXHttpHeaderHolder {

    private Map<String, List<String>> headerFields;

    public LXHttpHeaderHolder(Map<String, List<String>> headerFields) {
        this.headerFields = headerFields;
    }

    public Map<String, List<String>> getHeaderFields() {
        return headerFields;
    }

    public void setHeaderFields(Map<String, List<String>> headerFields) {
        this.headerFields = headerFields;
    }

    public String getCookie() {
        Map<String, List<String>> cookies = headerFields;
        if(cookies!=null) {
            List<String> setCookies = cookies.get("Set-Cookie");
            if (setCookies != null && setCookies.size() > 0) {
                String cookie = setCookies.get(0);
                String[] str = cookie.split(";");
                return str[0];
            }
        }
        return "";
    }


}
