package hkust.comp3111h.ballcraft;

import java.util.ArrayList;
import java.util.Iterator;

import android.util.Log;

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
	private ArrayList<Ball> balls;
	private ArrayList<Skill> activeSkills;
	
	ParticleSystem system;
	
	public GameState(World world, ArrayList<Ball> balls)
	{
		this.world = world;
		this.balls = balls;
		activeSkills = new ArrayList<Skill>();
	}
	
    public void processPlayerInput(int playerId, GameInput input)
    {
    	balls.get(playerId).setAcceleration(input.acceleration);
    	activeSkills.addAll(input.getSkills());
    	input.clearSkill();
    }
    
    static GameState createTestGameState()
    {
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
		Unit.setWorld(100, -100, 100, -100);
		world.addObject(plane);

		Ball sphere = new Ball(10, 5, 0.6f);
		sphere.strip();
		sphere.build();
		sphere.setOrigin(new SimpleVector(0, 0, 0));
		world.addObject(sphere);
		balls.add(sphere);	

		for (int i = 0; i < 3; i++)
		{
			Ball sphere2 = new Ball(3, 5, 0.99f);
			sphere2.translate(30, 10 * i - 50, 0);
			sphere2.strip();
			sphere2.build();
			sphere2.setOrigin(new SimpleVector(0, 0, 0));
			world.addObject(sphere2);	
		}

		
		Camera cam = world.getCamera();
		cam.setPosition(0, 0, -200);
		cam.lookAt(sphere.getTransformedCenter());

		MemoryHelper.compact();
		GameState test = new GameState(world, balls);
		return test;
    }
    
    
    public void onEveryFrame(int msecElapsed)
    {
    	Iterator<Unit> i = Unit.units.iterator();
    	while(i.hasNext())
    	{
    	    i.next().move(msecElapsed); 
    	}
    	// particle testing code:
    	if (!activeSkills.isEmpty()) {
    		Iterator<Skill> is = activeSkills.iterator();
			system = new ParticleSystem();
    		
    		activeSkills.clear();
    	}
    }
    
    public World getWorld()
    {
    	return world;
    }
}
