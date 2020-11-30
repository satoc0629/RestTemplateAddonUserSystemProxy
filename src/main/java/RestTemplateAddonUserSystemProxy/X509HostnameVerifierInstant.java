package RestTemplateAddonUserSystemProxy;

import org.apache.http.conn.ssl.X509HostnameVerifier;

public class X509HostnameVerifierInstant implements X509HostnameVerifier {
    @Override
    public void verify(String hostname, javax.net.ssl.SSLSocket ssl) {
    }

    @Override
    public void verify(String hostname, java.security.cert.X509Certificate cert) {
    }
    @Override
    public void verify(String hostname, String[] cns, String[] subjectAlts) {
    }
    @Override
    public boolean verify(String hostname, javax.net.ssl.SSLSession session) {
        return true;
    }
}
