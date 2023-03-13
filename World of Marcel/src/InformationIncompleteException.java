import org.json.JSONException;

public class InformationIncompleteException extends JSONException {

    public InformationIncompleteException(String errorMessage) {
        super(errorMessage);
    }
}
