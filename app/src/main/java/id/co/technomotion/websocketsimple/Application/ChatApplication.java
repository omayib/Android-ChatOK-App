package id.co.technomotion.websocketsimple.Application;

import android.app.Application;

import id.co.technomotion.websocketsimple.Client.SocketChat;

/**
 * Created by omayib on 7/7/15.
 */
public class ChatApplication extends Application {
    private SocketChat chat;

    @Override
    public void onCreate() {
        super.onCreate();

        chat=new SocketChat();
    }

    public SocketChat getChat() {
        return chat;
    }
}
