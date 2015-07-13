package id.co.technomotion.websocketsimple.model.event;

import id.co.technomotion.websocketsimple.model.Comment;

/**
 * Created by omayib on 7/11/15.
 */
public class OnUserLeft implements ChatEvent {
    public final Comment comment;

    public OnUserLeft(Comment comment) {
        this.comment = comment;
    }
}
