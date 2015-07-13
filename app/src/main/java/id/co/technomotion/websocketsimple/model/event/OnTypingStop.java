package id.co.technomotion.websocketsimple.model.event;

/**
 * Created by omayib on 7/11/15.
 */
public class OnTypingStop implements ChatEvent {
    public final String username;

    public OnTypingStop(String username) {
        this.username = username;
    }
}
