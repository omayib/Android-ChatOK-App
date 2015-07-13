package id.co.technomotion.websocketsimple.Client;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;
import id.co.technomotion.websocketsimple.model.Comment;
import id.co.technomotion.websocketsimple.model.event.OnCommentNew;
import id.co.technomotion.websocketsimple.model.event.OnLoginSucceeded;
import id.co.technomotion.websocketsimple.model.event.OnPostCommentSucceeded;
import id.co.technomotion.websocketsimple.model.event.OnTypingStart;
import id.co.technomotion.websocketsimple.model.event.OnTypingStop;
import id.co.technomotion.websocketsimple.model.event.OnUserJoined;
import id.co.technomotion.websocketsimple.model.event.OnUserLeft;

/**
 * Created by omayib on 7/7/15.
 */
public class SocketChatListener {

    public SocketChatListener(Socket socketClient) {

        socketClient.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("connect");
            }
        });
        socketClient.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("EVENT_CONNECT_ERROR" +args[0]);
            }
        });
        socketClient.on(Socket.EVENT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("EVENT_ERROR");
            }
        });
        socketClient.on(SocketEvent.LOGIN, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                System.out.println("event login "+data.toString());
                EventBus.getDefault().post(new OnLoginSucceeded());
            }
        });
        socketClient.on(SocketEvent.USER_ADD, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                System.out.println("event add user " + data.toString());
            }
        });
        socketClient.on(SocketEvent.TYPING_START, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                System.out.println("event TYPING_START "+data.toString());

                EventBus.getDefault().post(new OnTypingStart(data.toString()));

            }
        });
        socketClient.on(SocketEvent.TYPING_STOP, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                System.out.println("event TYPING_STOP "+data.toString());
                EventBus.getDefault().post(new OnTypingStop(data.toString()));
            }
        });
        socketClient.on(SocketEvent.USER_JOINED, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                System.out.println("event USER_JOINED "+data.toString());
                EventBus.getDefault().post(new OnUserJoined(new Comment("username","joined")));

            }
        });
        socketClient.on(Socket.EVENT_MESSAGE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];

                System.out.println("event EVENT_MESSAGE " + data.toString());
            }
        });
        socketClient.on(SocketEvent.USER_LEFT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                EventBus.getDefault().post(new OnUserLeft(new Comment("username","left")));
                System.out.println("event USER_LEFT "+data.toString());

            }
        });
        socketClient.on(SocketEvent.NEW_MESSAGE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                System.out.println("event NEW_MESSAGE "+data.toString());
                String username="";
                String message="";
                try {
                    username=data.getString("username");
                    message=data.getString("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                EventBus.getDefault().post(new OnCommentNew(new Comment(username,message)));
                System.out.println("event NEW_MESSAGE "+data.toString());
            }
        });
        socketClient.on(SocketEvent.POST_COMMENT_SUCCEEDED, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                String username="";
                String message="";
                try {
                    username=data.getString("username");
                    message=data.getString("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                EventBus.getDefault().post(new OnPostCommentSucceeded(new Comment(username,message)));
                System.out.println("event POST_COMMENT_SUCCEEDED "+data.toString());
            }
        });
    }
}
