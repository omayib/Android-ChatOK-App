package id.co.technomotion.websocketsimple.model.event;

/**
 * Created by omayib on 7/11/15.
 */
public class OnTypingStart implements ChatEvent {
    public final String username;

    public OnTypingStart(String username) {
        this.username = username;
    }
}
