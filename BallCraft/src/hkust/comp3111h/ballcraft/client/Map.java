package hkust.comp3111h.ballcraft.client;

import android.graphics.Bitmap;

//Map is composed of widthCount*hegihtCount small tiles.
//You can get a immutable tile by calling function map.getTile(layer id,width,height);
public class Map {

	private String mapName;
	private String imageurl;
	private int tileSize;
	private int widthCount;
	private int heightCount;
	private int[][][] layer;
	private Bitmap bitmap;
	
	public Map(){
		layer = new int[2][][];
	}
	
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
    
	public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
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
	
	public void setLayer(int id,int[][] array){
		layer[id] = array;
	}
	
	public int[][] getLayer(int id){
		return layer[id];
	}
	
	public Bitmap getTile(int layerId,int X, int Y) {
		int id = layer[layerId][X][Y];
		id --;
		int count = id /widthCount;
        int bitmapX = (id - (count * widthCount)) * tileSize;
        int bitmapY = count * tileSize;
        return Bitmap.createBitmap(bitmap, bitmapX, bitmapY, tileSize, tileSize); 
	}
}