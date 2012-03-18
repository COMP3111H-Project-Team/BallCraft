package hkust.comp3111h.ballcraft.server;

import hkust.comp3111h.ballcraft.client.GameInput;
import hkust.comp3111h.ballcraft.client.GameUpdater;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

public class ServerGameState 
{
	private static ArrayList<Unit> units;
	public static World world;
	
	private static ServerGameState stateInstance;
	
	public static ServerGameState getStateInstance() {
		if (stateInstance == null) {
			stateInstance = new ServerGameState();
		}
		return stateInstance;
	}
	
	private ServerGameState() {
		units = new ArrayList<Unit>();
		
    	Vec2 gravity = new Vec2(0.0f, 0.0f);
        boolean doSleep = true;
        world = new World(gravity, doSleep);
	}
	
    public void processPlayerInput(int playerId, GameInput input)
    {
    	units.get(playerId).applyForce(input.acceleration);
    }
    
    public void applyUpdate(GameUpdater updater) {
    	// TODO
    }
    
    public void createTestGameState()
    {
        
        BodyDef bodyDef = new BodyDef();
		 bodyDef.type = BodyType.STATIC;
		 bodyDef.position.set(0, -20);
		 Body body = ServerGameState.world.createBody(bodyDef);
		 PolygonShape dynamicBox = new PolygonShape();
		 dynamicBox.setAsBox(40, 0);
		body.createFixture(dynamicBox, 0);

        bodyDef = new BodyDef();
		 bodyDef.type = BodyType.STATIC;
		 bodyDef.position.set(0, 20);
		body = ServerGameState.world.createBody(bodyDef);
		 dynamicBox = new PolygonShape();
		 dynamicBox.setAsBox(40, 0);
		body.createFixture(dynamicBox, 0);

        bodyDef = new BodyDef();
		 bodyDef.type = BodyType.STATIC;
		 bodyDef.position.set(20, 0);
		body = ServerGameState.world.createBody(bodyDef);
		 dynamicBox = new PolygonShape();
		 dynamicBox.setAsBox(0, 40);
		body.createFixture(dynamicBox, 0);

        bodyDef = new BodyDef();
		 bodyDef.type = BodyType.STATIC;
		 bodyDef.position.set(-20, 0);
		body = ServerGameState.world.createBody(bodyDef);
		 dynamicBox = new PolygonShape();
		 dynamicBox.setAsBox(0, 40);
		body.createFixture(dynamicBox, 0);
        
		units.add(new Ball(10, 50, 0.6f, new Vec2(0, 0)));

		for (int i = 0; i < 1; i++)
		{
			units.add(new Ball(10, 5, 0.99f, new Vec2(30, 50 * i - 50)));
		}	
    }
    
    
    public void onEveryFrame(int msecElapsed)
    {
    	world.step(msecElapsed, 6, 2);
    }
    
    /* used for serialization:
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	ObjectOutput out;
    	byte[] bytes = null;
		try 
		{
			out = new ObjectOutputStream(bos);
	    	// out.writeObject(Unit.data);
	    	
	    	bytes = bos.toByteArray(); 

	    	out.close();
	    	return bytes;
	    	//bos.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}   
    	return bytes;
		//return Unit.data;
		 */
    
    public void addUnit(Unit unit) {
    	units.add(unit);
    }
    
    public ArrayList<Unit> getUnits() {
    	return units;
    }
    
}
