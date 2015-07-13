package id.co.technomotion.websocketsimple.Client;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by omayib on 7/7/15.
 */
public class SocketChat {
    Socket socketClient;
    private final String localhost="http://192.168.1.3:9000";// i am using speedy for local network
    private final String herokuapp="https://chatok.herokuapp.com:9000";

    public void init(){
        try {//"http://192.168.10.221:9000" sppedy http://192.168.1.3:9000

            // using SSL doesnt work
            /*
            SSLContext sslContext= SSLContext.getInstance("TLS", "HarmonyJSSE");
            System.out.println("sslContext "+sslContext.toString());
            System.out.println("sslContext "+sslContext.getProvider());
            System.out.println("sslContext "+sslContext.getProtocol());
            IO.setDefaultSSLContext(sslContext);
            */


            // using localhost, its work
            socketClient= IO.socket(localhost);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socketClient.connect();

        SocketChatListener chatListener=new SocketChatListener(socketClient);

    }
    private TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[] {};
        }

        public void checkClientTrusted(X509Certificate[] chain,
                                       String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain,
                                       String authType) throws CertificateException {
        }
    } };
    private static class RelaxedHostNameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    public void login(String username){
        socketClient.emit(SocketEvent.USER_ADD, username);
    }

    public void postComment(String comment){
        socketClient.emit(SocketEvent.NEW_MESSAGE, comment);
    }

    public void typing(){
        socketClient.emit(SocketEvent.TYPING_START);

    }
    public void typingFinished(){
        socketClient.emit(SocketEvent.TYPING_STOP);
    }
}
