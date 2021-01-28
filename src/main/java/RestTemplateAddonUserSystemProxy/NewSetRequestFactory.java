package RestTemplateAddonUserSystemProxy;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class NewSetRequestFactory extends RestTemplate {
    @Override
    public void setRequestFactory(ClientHttpRequestFactory requestFactory) {
        super.setRequestFactory(requestFactory);
        /*
            org.springframework.http.client.SimpleClientHttpRequestFactory
            org.springframework.http.client.Netty4ClientHttpRequestFactory
            org.springframework.http.client.OkHttp3ClientHttpRequestFactory
            org.springframework.http.client.AbstractClientHttpRequestFactoryWrapper
            org.springframework.http.client.InterceptingClientHttpRequestFactory
            org.springframework.http.client.BufferingClientHttpRequestFactory
            org.springframework.http.client.HttpComponentsClientHttpRequestFactory
            org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory

        各実装のオプショナル項目を確認する
        オプショナル項目を可能な限りHttpComponentsClientHttpRequestFactoryへ反映する
         */
        boolean bufferRequestBody = false;
        if (requestFactory instanceof org.springframework.http.client.BufferingClientHttpRequestFactory) {
            // bufferRequestBody
            bufferRequestBody = true;
        }


        final javax.net.ssl.SSLContext sslContext;
        try {
            sslContext = javax.net.ssl.SSLContext.getInstance("TLS");
            javax.net.ssl.TrustManager trustManager =
                    new TrustManagerInstant();
            sslContext.init(null, new javax.net.ssl.TrustManager[]{
                    trustManager
            }, null);
        } catch (java.security.KeyManagementException | java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException("sslContext init failed.", e);
        }

        X509HostnameVerifierInstant localHostnameVerifier =
                new X509HostnameVerifierInstant();
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(localHostnameVerifier);

        org.springframework.http.client.HttpComponentsClientHttpRequestFactory requestFactoryReplace =
                new org.springframework.http.client.HttpComponentsClientHttpRequestFactory(
                        org.apache.http.impl.client.HttpClientBuilder.create()
                                .useSystemProperties()
                                .setSSLContext(sslContext)
                                .build()
                );
        requestFactoryReplace.setBufferRequestBody(bufferRequestBody);

        this.setRequestFactory(requestFactoryReplace);
    }

    public void method() {
        final javax.net.ssl.SSLContext sslContext;
        try {
            sslContext = javax.net.ssl.SSLContext.getInstance("TLS");
            javax.net.ssl.TrustManager trustManager =
                    new TrustManagerInstant();
            sslContext.init(null, new javax.net.ssl.TrustManager[]{
                    trustManager
            }, null);
        } catch (java.security.KeyManagementException | java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException("sslContext init failed.", e);
        }

        X509HostnameVerifierInstant localHostnameVerifier =
                new X509HostnameVerifierInstant();
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(localHostnameVerifier);

        org.springframework.http.client.HttpComponentsClientHttpRequestFactory requestFactory =
                new org.springframework.http.client.HttpComponentsClientHttpRequestFactory(
                        org.apache.http.impl.client.HttpClientBuilder.create()
                        .useSystemProperties()
                        .setSSLContext(sslContext)
                        .build()
        );
        requestFactory.setBufferRequestBody(true);

        this.setRequestFactory(requestFactory);
    }
}
