package id.co.technomotion.websocketsimple.model;

/**
 * Created by omayib on 7/10/15.
 */
public class Comment {
    public final String username;
    public final String message;

    public Comment(String username, String message) {
        this.username = username;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "username='" + username + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
