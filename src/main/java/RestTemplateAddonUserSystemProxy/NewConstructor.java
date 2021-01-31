package RestTemplateAddonUserSystemProxy;

import org.springframework.web.client.RestTemplate;

public class NewConstructor extends RestTemplate {
    public void method() {
        final javax.net.ssl.SSLContext sslContext;
        try {
            sslContext = javax.net.ssl.SSLContext.getInstance("TLS");
            javax.net.ssl.TrustManager trustManager =
                    new RestTemplateAddonUserSystemProxy.TrustManagerInstant();
            sslContext.init(null, new javax.net.ssl.TrustManager[]{
                    trustManager
            }, null);
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.lang.RuntimeException("sslContext init failed.", e);
        } catch (java.security.KeyManagementException e){
            throw new java.lang.RuntimeException("sslContext init failed.", e);
        }

        RestTemplateAddonUserSystemProxy.X509HostnameVerifierInstant localHostnameVerifier =
                new RestTemplateAddonUserSystemProxy.X509HostnameVerifierInstant();
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
