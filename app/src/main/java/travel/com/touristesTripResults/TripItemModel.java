package travel.com.touristesTripResults;

import java.util.ArrayList;

public class TripItemModel {

    private String title;

    private String message;


    public TripItemModel(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public TripItemModel() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
