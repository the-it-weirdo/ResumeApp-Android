package com.example.kingominho;

public class Model {

    private int icon;
    private int image;
    private String title;
    private String desc;

    public Model(int icon, int image, String title, String desc) {
        this.image = image;
        this.title = title;
        this.desc = desc;
        this.icon = icon;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}