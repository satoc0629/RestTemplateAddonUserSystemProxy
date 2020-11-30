package RestTemplateAddonUserSystemProxy;

import javax.net.ssl.X509TrustManager;

public class TrustManagerInstant implements X509TrustManager {
    @Override
    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String s) {
    }

    @Override
    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String s) {
    }

    @Override
    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return new java.security.cert.X509Certificate[0];
    }
}