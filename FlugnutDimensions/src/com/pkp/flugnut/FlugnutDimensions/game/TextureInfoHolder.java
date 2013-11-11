package com.pkp.flugnut.FlugnutDimensions.game;

public class TextureInfoHolder {
    private Integer startx;
    private Integer starty;
    private String path;

    public TextureInfoHolder(Integer startx, Integer starty, String path) {
        this.startx = startx;
        this.starty = starty;
        this.path = path;
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