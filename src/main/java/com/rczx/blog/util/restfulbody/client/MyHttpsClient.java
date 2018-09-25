package com.rczx.blog.util.restfulbody.client;


import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class MyHttpsClient {
    public MyHttpsClient() {
    }

    public static CloseableHttpClient getNoVerifyHttpsClients() {
        try {
            return httpsClients();
        } catch (Exception var1) {
            throw new RuntimeException(var1);
        }
    }

    private static CloseableHttpClient httpsClients() throws KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException, CertificateException {
        PlainConnectionSocketFactory plainsf = new PlainConnectionSocketFactory();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(createIgnoreVerifySSL(), NoopHostnameVerifier.INSTANCE);
        Registry r = RegistryBuilder.create().register("http", plainsf).register("https", sslsf).build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(r);
        return HttpClients.custom().setConnectionManager(cm).build();
    }

    private static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");
        X509TrustManager trustManager = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        sc.init((KeyManager[])null, new TrustManager[]{trustManager}, (SecureRandom)null);
        return sc;
    }
}
