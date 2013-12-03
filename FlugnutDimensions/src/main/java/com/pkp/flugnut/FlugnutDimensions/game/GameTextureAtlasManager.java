package com.pkp.flugnut.FlugnutDimensions.game;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: rkevan
 * Date: 11/9/13
 * Time: 9:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameTextureAtlasManager {
    //Refers to the atlas height
    private Integer height = 0;

    //refers to the atlas width
    private Integer width = 0;

    Map<TextureType, TextureInfoHolder> atlasMap;

    public GameTextureAtlasManager() {
        atlasMap = new HashMap<TextureType, TextureInfoHolder>();
    }

    public Map<TextureType, TextureInfoHolder> getAtlasMap() {
        return atlasMap;
    }

    public void setAtlasMap(Map<TextureType, TextureInfoHolder> atlasMap){
        this.atlasMap = atlasMap;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer totalheight) {
        this.height = totalheight;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public void addTexture(TextureInfoHolder tih) {
        atlasMap.put(tih.getType(), tih);
        tih.setStartx(0);
        tih.setStarty(height+1);
        this.height += tih.getHeight();
        this.width = tih.getWidth() > this.width ? tih.getWidth(): this.width;
    }

    public String getPath(TextureType textureType) {
        return atlasMap.get(textureType).getPath();
    }

    public Integer getStarty(TextureType textureType) {
        return atlasMap.get(textureType).getStarty();
    }

    public TextureInfoHolder getTextureInfoHolder(TextureType textureType) {
        return atlasMap.get(textureType);
    }

}
