package com.example.mcqs.Additional_info_page;

public class Info_class {
    private String title;
    private String Time;
    private String info;
    private String place_of_event;
    private int image;

    public Info_class(String title, String time, String info, String place_of_event, int image) {
        this.title = title;
        this.Time = time;
        this.info = info;
        this.place_of_event = place_of_event;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return Time;
    }

    public String getInfo() {
        return info;
    }

    public String getPlace_of_event() {
        return place_of_event;
    }

    public int getImage() {
        return image;
    }
}
