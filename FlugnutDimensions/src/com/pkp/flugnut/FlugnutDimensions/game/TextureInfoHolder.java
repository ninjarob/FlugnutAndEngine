package com.pkp.flugnut.FlugnutDimensions.game;

public class TextureInfoHolder {
    private TextureType type;
    private int height;
    private int width;
    private int startx;
    private int starty;
    private String path;

    public TextureInfoHolder(TextureType type, int width, int height, String path) {
        this.type = type;
        this.width = width;
        this.height = height;
        this.path = path;
    }

    public TextureType getType() {
        return type;
    }

    public void setType(TextureType type) {
        this.type = type;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setStartx(int startx) {
        this.startx = startx;
    }

    public void setStarty(int starty) {
        this.starty = starty;
    }

    public Integer getStartx() {
        return startx;
    }

    public void setStartx(Integer startx) {
        this.startx = startx;
    }

    public Integer getStarty() {
        return starty;
    }

    public void setStarty(Integer starty) {
        this.starty = starty;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}