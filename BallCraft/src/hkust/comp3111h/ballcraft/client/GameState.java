package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.server.Ball;
import hkust.comp3111h.ballcraft.server.Unit;
import hkust.comp3111h.ballcraft.server.Vector2f;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;


public class GameState 
{
	ArrayList<Ball> balls;
	
	public GameState(ArrayList<Ball> balls)
	{
		this.balls = balls;
	}
	
    public void processPlayerInput(int playerId, GameInput input)
    {
    	balls.get(playerId).setAcceleration(input.acceleration);
    	// input.clearSkill();
    }
    
    public void applyUpdate(GameUpdater updater) {
    	// TODO
    }
    
    public static GameState createTestGameState()
    {
    	ArrayList<Ball> balls = new ArrayList<Ball>();
		
		Ball sphere = new Ball(10, 50, 0.6f, new Vector2f(0, 0));
		balls.add(sphere);		

		for (int i = 0; i < 3; i++)
		{
			Ball sphere2 = new Ball(10, 5, 0.99f, new Vector2f(30, 50 * i - 50));
		}	
		Unit.setWorld(200, -200, 200, -200);
		GameState test = new GameState(balls);
		return test;
    }
    
    
    public void onEveryFrame(int msecElapsed)
    {
    	Unit.msecElapsed = msecElapsed;
    	Iterator<Unit> i = Unit.units.iterator();
    	while(i.hasNext())
    	{
    	    i.next().moved = false; 
    	} 
    	
    	i = Unit.units.iterator();
    	while(i.hasNext())
    	{
    	    Unit temp = i.next();
    		if (!temp.moved) 
    		{
        		temp.moved = true;
    			temp.move(); 
    		}
    	} 
    }
    
    public byte[] getUnitData()
    {
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	ObjectOutput out;
    	byte[] bytes = null;
		try 
		{
			out = new ObjectOutputStream(bos);
	    	out.writeObject(Unit.data);
	    	
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
    }
}
