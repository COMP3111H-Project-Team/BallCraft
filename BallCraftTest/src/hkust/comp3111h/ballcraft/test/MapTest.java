package hkust.comp3111h.ballcraft.test;

import hkust.comp3111h.ballcraft.client.GameActivity;
import hkust.comp3111h.ballcraft.client.Map;
import hkust.comp3111h.ballcraft.client.Map.Data;
import hkust.comp3111h.ballcraft.client.MapParser;

import java.util.Iterator;
import java.util.Vector;

import org.jbox2d.common.Vec2;

import android.test.ActivityInstrumentationTestCase2;

public class MapTest  extends ActivityInstrumentationTestCase2<GameActivity> { 
	public MapTest() {
		super("hkust.comp3111h.ballcraft.client",GameActivity.class);
		// TODO Auto-generated constructor stub
	}

	private GameActivity mGameActivity;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mGameActivity = (GameActivity) getActivity();
		MapParser.setContext(mGameActivity);
	}
	
	private boolean inside(Vec2 v, Map map){
		int width = map.getWidth();
		int height = map.getHeight();
		int xoffset = width/2;
		int yoffset = height/2;
		v.x = v.x + xoffset;
		v.y = v.y + yoffset;
		if(v.x>=0 &&v.x <=width && v.y>=0 && v.y<=height){
			return true;
		} else {
			return false;
		}
	}
	
	public void testPrecondition(){
		assertNotNull(mGameActivity);
	}
	
	public void testMapName(){
		Map map = MapParser.getMapFromXML("map01.xml");
		assertEquals("map01",map.getName());
	}
	
	public void testSize(){
		Map map = MapParser.getMapFromXML("map02.xml");
		assertTrue(map.getHeight()>0 && map.getWidth()>0);
	}
	public void testInitPosition(){
		Map map = MapParser.getMapFromXML("map03.xml");
		Vec2 v = map.getInitPosition();
		assertTrue(inside(v,map));
	}
	
	public void testWalls(){
		boolean test = true;
		Map map = MapParser.getMapFromXML("map04.xml");
		Vector<Data> walls = map.getWallData();
		Iterator<Data> it = walls.iterator();
		Data data;
		while(it.hasNext()){
			data = it.next();
			if(inside(data.start,map) && inside(data.end,map)) {
			} else {
				test = false;
			}
		}
		assertTrue(test);
	}
}
