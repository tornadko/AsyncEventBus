package asyncapp.tornado.alex.asyncapp.event;

/**
 * Created by Alex Kucherenko on 6/29/16.
 */
public class ActionEvent {

    private final boolean isSuccessful;
    private final String message;

    public ActionEvent(boolean isSuccessful, String message) {
        this.isSuccessful = isSuccessful;
        this.message = message;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public String getMessage() {
        return message;
    }
}
