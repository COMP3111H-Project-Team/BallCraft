package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.server.Plane;
import hkust.comp3111h.ballcraft.server.ServerGameState;
import hkust.comp3111h.ballcraft.server.Unit;
import hkust.comp3111h.ballcraft.server.Wall;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import org.jbox2d.common.Vec2;

//Map is composed of widthCount*hegihtCount small tiles.
//notice :: width>height
//You can get a immutable tile by calling function map.getTile(layer id,width,height);
public class Map {
    
	private String mapName;
	private int widthCount;
	private int heightCount;
	private int terrain;
	private int mode;
	private Vector<Unit> units;
	private Vector<Data> walls;
	private Vector<Data> traps;
	private Vector<Data> planes;
	private ArrayList<Vec2> initPosition;
	
	public class Data {
		public Data(Vec2 s, Vec2 e) {
			start = s;
			end = e;
		}
		public Vec2 start;
		public Vec2 end;
	}
	
	public Map(){
		units = new Vector<Unit>();
		walls = new Vector<Data>();
		planes = new Vector<Data>();
		traps = new Vector<Data>();
		initPosition = new ArrayList<Vec2>();
		ServerGameState.setInitPosition(initPosition);
	}


    public void setName(String name) {
        this.mapName = name;
    }
    
    public String getName(){
    	return mapName;
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
	
	public void setTerrain(int terrain){
		this.terrain = terrain;
	}
	
	public int getTerrain() {
		return terrain;
	}
	
	public void setMode(int mode) {
		this.mode = mode;
	}
	
	public int getMode() {
		return mode;
	}
	
	public void addInit(int[] p) {
		initPosition.add(new Vec2(p[0],p[1]));
	}
		
	public void addWall(int[] data) {
		walls.add(new Data(new Vec2(data[0],data[1]), new Vec2(data[2],data[3])));
	}
	
	public void addTrap(int[] data){
		traps.add(new Data(new Vec2(data[0],data[1]), new Vec2(data[2],data[3])));
	}
	
	public void addPlane(int[] data){
		planes.add(new Data(new Vec2(data[0],data[1]), new Vec2(data[2],data[3])));
	}
	
	public Vector<Data> getWallData(){
		return walls;
	}
	
	public Vector<Data> getTrapData(){
		return traps;
	}
	
	public Vector<Data> getPlaneData(){
		return planes;
	}
	
	public Vector<Unit> getUnit() {
		if(units.isEmpty()){
	        Iterator<Data> iterator = walls.iterator();
	        while (iterator.hasNext()) {
	        	Data walldata = iterator.next();
	        	units.add(new Wall(walldata.start,walldata.end));
	        }
	        
	        iterator = planes.iterator();
	        while (iterator.hasNext()) {
	            Data planeData = iterator.next();
	            units.add(new Plane(planeData.start, planeData.end));
	        }
		}
		return units;
	}
	
}
