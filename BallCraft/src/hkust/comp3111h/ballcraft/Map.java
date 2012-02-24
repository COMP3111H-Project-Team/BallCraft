package hkust.comp3111h.ballcraft;

import java.io.Serializable;

public class Map implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mapName;
	private String imageurl;
	private int tileSize;
	private int widthCount;
	private int heightCount;
	
	
	public String getName() {
		return mapName;
	}
	
	public void setName(String name) {
        this.mapName = name;
    }
    
	public void setTileSize(int size) {
        this.tileSize = size;
    }
    
	public int getTileSize() {
		return tileSize;
	}
    
	public String getImageurl() {
        return imageurl;
    }
    
	public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
	
	public void setWidthCount(int width) {
		this.widthCount = width;
	}
	
	public int getWidthCount() {
		return widthCount;
	}
	
	public void setHeightCount(int height) {
		this.heightCount = height;
	}
	
	public int getHeightCount() {
		return heightCount;
	}
	
}
