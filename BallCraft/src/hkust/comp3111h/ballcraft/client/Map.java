package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.server.Unit;
import hkust.comp3111h.ballcraft.server.Wall;

import java.util.Vector;

import org.jbox2d.common.Vec2;


//Map is composed of widthCount*hegihtCount small tiles.
//notice :: width>height
//You can get a immutable tile by calling function map.getTile(layer id,width,height);
public class Map {

	private String mapName;
	private int widthCount;
	private int heightCount;
//	private Vector<Wall> walls;
	private Vec2 initPosition;
	private Vector<Unit> units;
	
	public Map(){
		units = new Vector<Unit>();
	}

	public String getName() {
		return mapName;
	}
	
	public void setName(String name) {
        this.mapName = name;
    }
	
	public void setWidth(int width) {
		this.widthCount = width;
	}
	
	public int getWidth() {
		return widthCount;
	}
	
	public void setHeight(int height) {
		this.heightCount = height;
	}
	
	public int getHeight() {
		return heightCount;
	}
	
	public void setInitPosition(int[] p) {
		initPosition = new Vec2(p[0],p[1]);
	}
	
	public Vec2 getInitPosition() {
		return initPosition;
	}
	
	
	public void addWall(int[] data) {
		//walls.add(new Wall(new Vec2(data[0],data[1]), new Vec2(data[2],data[3])));
		units.add(new Wall(new Vec2(data[0],data[1]), new Vec2(data[2],data[3])));
	}
	
	public Vector<Unit> getUnit() {
		return units;
	}
	/*
	public Vector<Wall> getWalls() {
		return walls;
	}
    */
	
	/*
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
	
	public void setwidth(int width) {
		this.widthCount = width;
	}
	
	public int getwidth() {
		return widthCount;
	}
	
	public void setheight(int height) {
		this.heightCount = height;
	}
	
	public int getheight() {
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
	*/
}