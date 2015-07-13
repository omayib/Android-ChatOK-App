package id.co.technomotion.websocketsimple.model.event;

import id.co.technomotion.websocketsimple.model.Comment;

/**
 * Created by omayib on 7/10/15.
 */
public class OnPostCommentSucceeded implements ChatEvent {
    public final Comment comment;

    public OnPostCommentSucceeded(Comment comment) {
        this.comment = comment;
    }
}