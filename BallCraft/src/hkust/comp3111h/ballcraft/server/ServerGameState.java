package hkust.comp3111h.ballcraft.server;

import hkust.comp3111h.ballcraft.client.GameInput;
import hkust.comp3111h.ballcraft.client.Map;
import hkust.comp3111h.ballcraft.client.MapParser;
import hkust.comp3111h.ballcraft.client.Skill;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class ServerGameState 
{
	private static ArrayList<Unit> units;
	public static World world;
	
	private static ArrayList<Skill> activeSkills;
	
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
        world.setContactListener(new BallContactListener());
        
        activeSkills = new ArrayList<Skill>();
	}
	
    public void processPlayerInput(int playerId, GameInput input)
    {
    	units.get(playerId).applyForce(input.acceleration.mul(1.0f));
    	if (input.skillActive()) {
    		ArrayList<Skill> skills = input.getSkills();
    		for (int i = 0; i < skills.size(); i++) {
    			skills.get(i).setTime();
	    		activeSkills.add(skills.get(i));
    		}
    	}
    }

    private void beforeStep() 
    {
    	for (int i = 0; i < activeSkills.size(); i++)
    	{
    		Skill skill = activeSkills.get(i);
    		if (!skill.isActive())
    		{
    			activeSkills.remove(i);
    			i--;
    			continue;
    		}
    		skill.beforeStep();		
    	}
	}

	public void loadMap(String name)
    {
    	units.add(new Ball(10, 50, 0.6f, new Vec2(0, 0)));

		for (int i = 0; i < 1; i++)
		{
			units.add(new Ball(10, 5, 0.99f, new Vec2(30, 5 * i - 5)));
		}	
		
        Map map = MapParser.getMapFromXML(name);
        
        Vector<Unit> mapUnit = map.getUnit();
        Iterator<Unit> iterator = mapUnit.iterator();
        
        while(iterator.hasNext())
        {
        	addUnit(iterator.next());
        }
                
    }
    
    public void onEveryFrame(int msecElapsed)
    {
    	beforeStep();
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

	public static void init() 
	{
		stateInstance = new ServerGameState();
	}
    
}
