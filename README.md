# Lxhttpclient

引入: <br/>
allprojects {<br/>
		repositories {<br/>
			...<br/>
			maven { url 'https://jitpack.io' }<br/>
		}<br/>
}<br/>
  
<br/>
api 'com.google.code.gson:gson:2.8.5'<br/>
api 'com.github.flappygod:Lxhttpclient:2.0'<br/>


使用：<br/>
HashMap<String, Object> hashMap = new HashMap<String, Object>();<br/>
hashMap.put("act", "index");<br/>
LXHttpsClient.getInstacne().get(url, hashMap, callback, null);<br/>
LXHttpsClient.getInstacne().post(url, hashMap, callback, null);<br/>
LXHttpsClient.getInstacne().postParam(url, hashMap, callback, null);<br/>
