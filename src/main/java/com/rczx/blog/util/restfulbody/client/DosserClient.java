package com.rczx.blog.util.restfulbody.client;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DosserClient {
    private static final Logger logger = LoggerFactory.getLogger(DosserClient.class);
    public static boolean formatResponse = false;

    public DosserClient() {
    }

    public static String getHrefInPortal(String portalUrl, String relKey) {
        logger.info(portalUrl);
        logger.info(relKey);
        JSONObject jsonObject = getResponseJSONBody(portalUrl);
        JSONArray collectionArray = jsonObject.getJSONArray("collection");
        if(collectionArray.size() < 1) {
            throw new RuntimeException(portalUrl + "中没有找到key为" + relKey + "对应的url");
        } else {
            JSONArray links = collectionArray.getJSONObject(0).getJSONArray("links");
            Iterator var5 = links.iterator();

            JSONObject castLink;
            do {
                if(!var5.hasNext()) {
                    throw new RuntimeException(portalUrl + "中没有找到key为" + relKey + "对应的url");
                }

                Object link = var5.next();
                castLink = (JSONObject)link;
            } while(!relKey.equals(castLink.getString("rel")));

            return castLink.getString("href");
        }
    }

    public static JSONObject getResponseJSONBody(String url) {
        HttpUriRequest request = RequestBuilder.get(url).setHeader("Accept", "application/json").build();
        return getResponseJSONBody(request);
    }

    public static JSONObject getResponseJSONBody(HttpUriRequest request) {
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;

        JSONObject var5;
        try {
            client = MyHttpsClient.getNoVerifyHttpsClients();
            response = client.execute(request);
            HttpEntity e = response.getEntity();
            String responseEntity = EntityUtils.toString(e);
            EntityUtils.consume(e);
            var5 = JSON.parseObject(responseEntity);
        } catch (Exception var14) {
            throw new RuntimeException(var14);
        } finally {
            try {
                if(response != null) {
                    response.close();
                }

                if(client != null) {
                    client.close();
                }
            } catch (IOException var13) {
                var13.printStackTrace();
            }

        }

        return var5;
    }

    /** @deprecated */
    @Deprecated
    public static Map getFirst(String url, Map<String, String> param) {
        CloseableHttpClient clients = null;
        CloseableHttpResponse response = null;

        Map var7;
        try {
            clients = MyHttpsClient.getNoVerifyHttpsClients();
            response = clients.execute(new HttpGet(url + createQueryParam(param)));
            String e = EntityUtils.toString(response.getEntity());
            logger.info(e);
            JSONObject responseBodyJson = JSON.parseObject(e);
            if(!responseBodyJson.getBoolean("success").booleanValue()) {
                throw new RuntimeException("请求失败");
            }

            JSONObject data = responseBodyJson.getJSONArray("collection").getJSONObject(0);
            var7 = (Map)JSON.parseObject(data.toJSONString(), Map.class);
        } catch (Exception var16) {
            throw new RuntimeException(var16);
        } finally {
            try {
                if(response != null) {
                    response.close();
                }

                if(clients != null) {
                    clients.close();
                }
            } catch (IOException var15) {
                ;
            }

        }

        return var7;
    }

    /** @deprecated */
    @Deprecated
    public static Map getFirst(String url) {
        return getFirst(url, (Map)(new HashMap(0)));
    }

    /** @deprecated */
    @Deprecated
    public static Map getFirst(String url, String id) {
        String replace = url.replace("~", id);
        return getFirst(replace, (Map)(new HashMap(0)));
    }

    private static String createQueryParam(Map<String, String> paramMap) {
        StringBuilder parmStr = new StringBuilder("?");
        Iterator var2 = paramMap.entrySet().iterator();

        while(var2.hasNext()) {
            Entry entry = (Entry)var2.next();
            parmStr.append((String)entry.getKey() + "=" + (String)entry.getValue());
        }

        return parmStr.toString();
    }
}
