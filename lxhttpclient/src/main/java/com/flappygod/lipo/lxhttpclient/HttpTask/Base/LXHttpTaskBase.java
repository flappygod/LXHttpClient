package com.flappygod.lipo.lxhttpclient.HttpTask.Base;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/17.
 */

public class LXHttpTaskBase {

    /*********
     * 拼接当前的请求参数
     *
     * @param map 参数map
     * @return
     * @throws UnsupportedEncodingException 不支持encoding
     */
    public static String getParamStr(HashMap<String, Object> map, String charset)
            throws UnsupportedEncodingException {
        StringBuffer buffer = new StringBuffer();
        // 空的返回空字符串
        if (map == null) {
            return "";
        }
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet()
                .iterator();
        buffer.append("?");
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            buffer.append(entry.getKey());
            buffer.append("=");
            buffer.append(URLEncoder.encode(entry.getValue().toString(),
                    charset));
            buffer.append("&");
        }
        String str = buffer.toString();
        if (str.endsWith("&") || str.endsWith("?"))
            str = str.substring(0, str.length() - 1);
        return str;
    }

    /**************
     * 通过param构建出当前的ParamData
     *
     * @param requestParamsMap 请求参数集合
     * @return 构建出的参数
     * @throws UnsupportedEncodingException
     * @throws JSONException                异常
     */
    public static String getPostParamStr(HashMap<String, Object> requestParamsMap, String charset)
            throws UnsupportedEncodingException {
        StringBuffer params = new StringBuffer();
        Iterator<Map.Entry<String, Object>> it = requestParamsMap.entrySet()
                .iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> element = (Map.Entry<String, Object>) it
                    .next();
            params.append(element.getKey());
            params.append("=");
            params.append(URLEncoder.encode(element.getValue().toString(),
                    charset));
            params.append("&");
        }
        if (params.length() > 0) {
            params.deleteCharAt(params.length() - 1);
        }
        return params.toString().trim();
    }

    /**************
     * 通过param构建出当前的JSONData
     *
     * @param requestParamsMap 请求参数集合
     * @return 构建出的参数
     * @throws JSONException                异常
     * @throws UnsupportedEncodingException
     */
    public static String getPostJsonStr(HashMap<String, Object> requestParamsMap)
            throws JSONException, UnsupportedEncodingException {
        if (requestParamsMap == null) {
            return "";
        }
        JSONObject jb = new JSONObject();
        Iterator<Map.Entry<String, Object>> it = requestParamsMap.entrySet()
                .iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> element = (Map.Entry<String, Object>) it
                    .next();
            try {
                jb.put((String) element.getKey(), element.getValue());
            } catch (JSONException e) {
                throw e;
            }
        }
        return jb.toString().trim();
    }

    /**********************
     * 读取流数据
     *
     * @param is 输入流
     * @return 转换后的字符串
     * @throws IOException 异常
     */
    public static String convertStreamToStr(InputStream is, String charset) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is,
                charset));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                throw e;
            }
        }
        String data = sb.toString();
        return data;
    }


}
