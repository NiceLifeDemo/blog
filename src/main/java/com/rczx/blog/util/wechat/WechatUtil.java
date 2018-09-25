package com.rczx.blog.util.wechat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;


import com.rczx.blog.util.Common;
import com.rczx.blog.util.MD5Util;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


public class WechatUtil {
	
	
	
	/*private static String appid = "wxa9708c0207f1c901";
	
	private static String secret = "340f64dae25241c645d8a1f5c4e1e569";*/
	

	public static String sha1(String key) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			md.update(key.getBytes());
			String pwd = new BigInteger(1, md.digest()).toString(16);
			return pwd;
		} catch (Exception e) {
			e.printStackTrace();
			return key;
		}
	}

	/**
	 * 解析request中的xml 并将数据存储到一个Map中返回
	 * 
	 * @param request
	 */
	public static Map<String, String> parseXml(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			InputStream inputStream = request.getInputStream();
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			Element root = document.getRootElement();
			List<Element> elementList = root.elements();
			for (Element e : elementList)
				// 遍历xml将数据写入map
				map.put(e.getName(), e.getText());
			inputStream.close();
			inputStream = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;

				@SuppressWarnings("unchecked")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

	/**
	 * 将回复消息对象转换成xml字符串
	 * 
	 * @param reply
	 *            回复消息对象
	 * @return 返回符合微信接口的xml字符串
	 */
	public static String replyToXml(Reply reply) {
		String type = reply.getMsgType();
		if (Reply.TEXT.equals(type)) {
			xstream.omitField(Reply.class, "articles");
			xstream.omitField(Reply.class, "articleCount");
			xstream.omitField(Reply.class, "musicUrl");
			xstream.omitField(Reply.class, "hQMusicUrl");
		} else if (Reply.MUSIC.equals(type)) {
			xstream.omitField(Reply.class, "articles");
			xstream.omitField(Reply.class, "articleCount");
			xstream.omitField(Reply.class, "content");
		} else if (Reply.NEWS.equals(type)) {
			xstream.omitField(Reply.class, "content");
			xstream.omitField(Reply.class, "musicUrl");
			xstream.omitField(Reply.class, "hQMusicUrl");
		}
		xstream.autodetectAnnotations(true);
		xstream.alias("xml", reply.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(reply);
	}

	/**
	 * 存储数据的Map转换为对应的Message对象
	 * 
	 * @param map
	 *            存储数据的map
	 * @return 返回对应Message对象
	 */
	public static Message mapToMessage(Map<String, String> map) {
		if (map == null)
			return null;
		String msgType = map.get("MsgType");
		Message message = new Message();
		message.setToUserName(map.get("ToUserName"));
		message.setFromUserName(map.get("FromUserName"));
		message.setCreateTime(new Date(Long.parseLong(map.get("CreateTime"))));
		message.setMsgType(msgType);
		message.setMsgId(map.get("MsgId"));
		if (msgType.equals(Message.TEXT)) {
			message.setContent(map.get("Content"));
		} else if (msgType.equals(Message.IMAGE)) {
			message.setPicUrl(map.get("PicUrl"));
		} else if (msgType.equals(Message.LINK)) {
			message.setTitle(map.get("Title"));
			message.setDescription(map.get("Description"));
			message.setUrl(map.get("Url"));
		} else if (msgType.equals(Message.LOCATION)) {
			message.setLocationX(map.get("Location_X"));
			message.setLocationY(map.get("Location_Y"));
			message.setScale(map.get("Scale"));
			message.setLabel(map.get("Label"));
		} else if (msgType.equals(Message.EVENT)) {
			message.setEvent(map.get("Event"));
			message.setEventKey(map.get("EventKey"));
		}
		return message;
	}

	public static Map<String, String> getAccessToken(String code,String appid,String secret){
		Map<String, String> map = null;
		String access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?"
				+ "appid="+appid+"&"
				+ "secret="+secret+"&"
				+ "code="+code+"&"
				+ "grant_type=authorization_code";
        try {
        	String json = getUrl(access_token_url);
            JSONObject jsonObject = JSONObject.fromObject(json);
			if(jsonObject.has("access_token")){
				String token = jsonObject.getString("access_token");
				String openid = jsonObject.getString("openid");
				map = new HashMap<String,String>();
				map.put("token", token);
				map.put("openid", openid);
			}else if(jsonObject.has("errcode")){
			}else{
			}
		} catch(Exception e){
			e.printStackTrace();
		}
        
		return map;
	}
	
	
	
	
	
	/**
	 * 微信请求打开网页
	 * @param url 打开地址
	 * @return
	 */
    public static String getUrl(String url){
        String result = null;
        try {
            // 根据地址获取请求
            HttpGet request = new HttpGet(url);
            // 获取当前客户端对象
            HttpClient httpClient = new DefaultHttpClient();
            // 通过请求对象获取响应对象
            HttpResponse response = httpClient.execute(request);
            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity());
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    

    /**
	 * 获取openid
	 * @param 
	 * @return
	 */
    public static String getOpenid(HttpServletRequest request,String appid,String secret,String code,String state, String wehcatRedirectURI){
    /*	if (!Common.OPENID) {
			return "testopenid";
		}*/
    	//String code=request.getParameter("code");
    	
    	if (Common.isEmpty(code)) {
    		System.out.println("wechat code----------->重新取得code"+code);
    		
    		//重新取一次code
    		if (wehcatRedirectURI == null || "".equals(wehcatRedirectURI)) {
    			wehcatRedirectURI = Common.WECHAT_REDIRECT_URL+"index.shtml?appid="+appid;
    		}
    		
    		String wechatURL = Common.WECHAT_URL;
    		
    		wechatURL = wechatURL.replace("APPID", appid);
    		wechatURL = wechatURL.replace("REDIRCT_URI",wehcatRedirectURI);
    		wechatURL = wechatURL.replace("SCOPE", "snsapi_base");
    		
    		System.out.println("wechat uri-------------->"+wechatURL);
    		
    		String json = getUrl(wechatURL);
            //JSONObject jsonObject = JSONObject.fromObject(json);
            //System.out.println(jsonObject);
    		System.out.println("wechat code----------->重新取得之后的code"+code);
    	}
    	
    	String openid = null;
		Map<String, String> tokenMap = WechatUtil.getAccessToken(code,appid,secret);
		if (tokenMap != null && tokenMap.get("openid") != null) {
			openid = tokenMap.get("openid");
		}
		
		//如果实在取不到openid，则直接取一个当前的随机数来充当openid
		if (openid == null) {
			//openid = ""+System.currentTimeMillis();
			//System.out.println("wechat openid，不能取得openid，用随机数代替----->"+openid);
		}
		
    	return openid;
    }
    
    
    /**
  	 * 获取Token
  	 * @param 
  	 * @return
  	 */
      public static String getToken(HttpServletRequest request,String appid,String secret){
      	String code=request.getParameter("code");
  		Map<String, String> tokenMap = WechatUtil.getAccessToken(code,appid,secret);
  		String token = tokenMap.get("token");
      	return token;
      }
      
      
     
    
    /**
     * 用 URLEncoder.encode的方式转码 
     * @param str
     * @return
     */
    public static String urlEncodeUTF8(String str){ 
        String result = str; 
        try {
            result = URLEncoder.encode(str,"UTF-8"); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        return result; 
    } 
    
    /**
	 * 生成随机数
	 * 
	 * @return
	 */
	public static String CreateNoncestr() {
		Random random = new Random();
		return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "utf-8").toUpperCase();
	}
	
	
	public static String getSign(Map<String, String> params, String paternerKey ){
		/*String string1 = Pay.createSign(params, false);
		String stringSignTemp = string1 + "&key=" + paternerKey;
		String signValue = DigestUtils.md5Hex(stringSignTemp).toUpperCase();
		return  signValue;*/
		 ArrayList<String> list = new ArrayList<String>();
	        for(Entry<String,String> entry:params.entrySet()){
	            if(entry.getValue()!=""){
	                list.add(entry.getKey() + "=" + entry.getValue() + "&");
	            }
	        }
	        
	        int size = list.size();
	        String [] arrayToSort = list.toArray(new String[size]);
	        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
	        StringBuilder sb = new StringBuilder();
	        for(int i = 0; i < size; i ++) {
	            sb.append(arrayToSort[i]);
	        }
	       
		
		
		/*StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> es = params.entrySet();
		Iterator<Entry<String, String>> it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}*/
		sb.append("key=" + paternerKey);
		String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
		return sign;
	}
	
	
	/**xml转map
	 * @param xml
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 * @author Bear Hunter
	 * @Date 2016-6-17
	 */
	public static Map<String, String> doXMLParse(String xml)
			throws XmlPullParserException, IOException {

		InputStream inputStream = new ByteArrayInputStream(xml.getBytes());

		Map<String, String> map = null;

		XmlPullParser pullParser = XmlPullParserFactory.newInstance()
				.newPullParser();

		pullParser.setInput(inputStream, "UTF-8"); // 为xml设置要解析的xml数据

		int eventType = pullParser.getEventType();

		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				map = new HashMap<String, String>();
				break;

			case XmlPullParser.START_TAG:
				String key = pullParser.getName();
				if (key.equals("xml"))
					break;

				String value = pullParser.nextText();
				map.put(key, value);

				break;

			case XmlPullParser.END_TAG:
				break;

			}

			eventType = pullParser.next();

		}

		return map;
	}
	
	
	/**
	 * map转成xml
	 * 
	 * @param arr
	 * @return
	 */
	public static String ArrayToXml(Map<String, String> arr) {
		String xml = "<xml>";

		Iterator<Entry<String, String>> iter = arr.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			String val = entry.getValue();
			xml += "<" + key + ">" + val + "</" + key + ">";
		}

		xml += "</xml>";
		return xml;
	}
	
	/**InputStream转String
	 * @param in
	 * @return
	 * @throws IOException
	 * @author Bear Hunter
	 * @Date 2016-6-27
	 */
	public static String inputStream2String(InputStream   in)   throws   IOException{
        ByteArrayOutputStream   baos   =   new   ByteArrayOutputStream();
        int   i=-1;
        while((i=in.read())!=-1){
        baos.write(i);
        }
       return   baos.toString();
	} 
}

