package id.co.technomotion.websocketsimple.model.event;

import id.co.technomotion.websocketsimple.model.Comment;

/**
 * Created by omayib on 7/10/15.
 */
public class OnUserJoined implements ChatEvent {
    public final Comment comment;

    public OnUserJoined(Comment comment) {
        this.comment = comment;
    }
}
