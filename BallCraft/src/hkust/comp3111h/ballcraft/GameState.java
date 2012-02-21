package hkust.comp3111h.ballcraft;

import java.util.ArrayList;
import java.util.Iterator;

import com.threed.jpct.Camera;
import com.threed.jpct.Light;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;
import com.threed.jpct.util.MemoryHelper;

public class GameState 
{
	private World world = null;
	ArrayList<Unit> units;
	ArrayList<Ball> balls;
	
	public GameState(World world, ArrayList<Unit> units, ArrayList<Ball> balls)
	{
		this.world = world;
		this.units = units;
		this.balls = balls;
	}
	
    public void processPlayerInput(int playerId, GameInput input)
    {
    	balls.get(playerId).setAcceleration(input.acceleration);
    }
    
    static GameState createTestGameState()
    {
    	ArrayList<Unit> units = new ArrayList<Unit>();
    	ArrayList<Ball> balls = new ArrayList<Ball>();
		
		World world = new World();
		world.setAmbientLight(20, 20, 20);

		Light sun = new Light(world);
		sun.setIntensity(250, 250, 250);
		sun.setPosition(new SimpleVector(-100, -100, -100));

		Object3D plane = Primitives.getPlane(10, 20);
		plane.strip();
		plane.build();
		plane.setOrigin(new SimpleVector(0, 0, 10));
		world.addObject(plane);
		
		Object3D wall = Primitives.getPlane(10, 20);
		wall.strip();
		wall.build();
		wall.setCollisionMode(Object3D.COLLISION_CHECK_OTHERS);
		wall.setOrigin(new SimpleVector(100, 0, 10));
		wall.setOrientation(new SimpleVector(1, 0, 0), new SimpleVector(0, 0, 1));
		world.addObject(wall);
		
		Ball sphere = new Ball(10, 5, 0.4f);
		sphere.strip();
		sphere.build();
		sphere.setOrigin(new SimpleVector(0, 0, 0));
		world.addObject(sphere);
		units.add(sphere);		
		balls.add(sphere);		

		Camera cam = world.getCamera();
		cam.setPosition(-100, 0, -150);
		cam.lookAt(sphere.getTransformedCenter());

		MemoryHelper.compact(); // What is this???
		GameState test = new GameState(world, units, balls);
		return test;
    }
    
    
    public void onEveryFrame(int msecElapsed)
    {
    	Iterator<Unit> i = units.iterator();
    	while(i.hasNext())
    	{
    	    i.next().move(msecElapsed); 
    	} 
    }
    
    public World getWorld()
    {
    	return world;
    }
}
