package models;

/**
 * Created by The Architect on 4/19/2018.
 */

public class HomeModel {

    private int image;
    private String main_title;
    private String secondary_title;

    public HomeModel() {
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getMain_title() {
        return main_title;
    }

    public void setMain_title(String main_title) {
        this.main_title = main_title;
    }

    public String getSecondary_title() {
        return secondary_title;
    }

    public void setSecondary_title(String secondary_title) {
        this.secondary_title = secondary_title;
    }
}
