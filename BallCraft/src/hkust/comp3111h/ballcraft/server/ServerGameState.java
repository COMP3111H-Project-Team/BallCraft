package hkust.comp3111h.ballcraft.server;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.BallCraft.Status;
import hkust.comp3111h.ballcraft.client.GameInput;
import hkust.comp3111h.ballcraft.client.Map;
import hkust.comp3111h.ballcraft.client.MapParser;
import hkust.comp3111h.ballcraft.skills.Skill;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class ServerGameState {
    
    private static ArrayList<Unit> units;
    
    public static World world;

    private static ArrayList<Skill> activeSkills;

    private static ServerGameState stateInstance;
    
    private static int mapTerrain;
    private static int mapMode;

    public static ServerGameState getStateInstance() 
    {
        if (stateInstance == null) 
        {
            stateInstance = new ServerGameState();
        }
        return stateInstance;
    }
    
    public static void clear()
    {
        stateInstance = new ServerGameState();    	
    }

    private ServerGameState()
    {
        units = new ArrayList<Unit>();

        Vec2 gravity = new Vec2(0.0f, 0.0f);
        boolean doSleep = true;
        world = new World(gravity, doSleep);
        world.setContactListener(new BallContactListener());

        activeSkills = new ArrayList<Skill>();
    }

    public void processPlayerInput(int playerId, GameInput input) 
    {
      	if (units.get(playerId).getStatus() == Status.NORMAL)
    	{
    		units.get(playerId).applyForce(input.acceleration);
    	}
    	
    	if (input.skillActive())
    	{
    		ArrayList<Skill> skills = input.getSkills();
    		for (int i = 0; i < skills.size(); i++)
    		{
    			skills.get(i).setTime();
    			
    			int count = 0;
    			for (Skill skill:activeSkills)
    			{
    				if (skill.getID() == skills.get(i).getID() && skill.getPlayer() == skills.get(i).getPlayer())
    					count++;
    			}
    			if (count > skills.get(i).getMaxCount()) continue;
	    		
    			activeSkills.add(skills.get(i));
	    		Server.extraMessage("skillInit:" + skills.get(i).getID() + "&" + skills.get(i).getInitMsg());
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
            	skill.finish();
	    		Server.extraMessage("skillFinish:" + skill.getID() + "&" + skill.getInitMsg());
                activeSkills.remove(i);
                i--;
                continue;
            }
            skill.beforeStep();
        }
    }

    private void afterStep() 
    {
        for (int i = 0; i < activeSkills.size(); i++)
        {
            activeSkills.get(i).afterStep();
        }
    }
    
    public void loadMap(String name, int serverBall, int clientBall) 
    {
        units.add(new Ball(10, 50, 0.8f, new Vec2(0, 0)));
        units.add(new Ball(10, 5, 0.99f, new Vec2(30, 0)));

        Map map = MapParser.getMapFromXML(name);
        
        mapTerrain = map.getTerrain();
        mapMode = map.getMode();

        Vector<Unit> mapUnit = map.getUnit();
        Iterator<Unit> iterator = mapUnit.iterator();

        while (iterator.hasNext()) 
        {
            addUnit(iterator.next());
        }

        String initMsg = mapTerrain + "," + mapMode + "," + serverBall +"," +  clientBall + "MAPDEF";
        for (int i = 0; i < units.size(); i++)
        {
            initMsg += units.get(i).toSerializedString();
            if (i != units.size() - 1)
            { // not the last one
                initMsg += "/";
            }
        }
        
        for (int i = 0; i < BallCraft.maxPlayer; i++)
        {
        	ServerAdapter.sendInitMsgToClient(new String(initMsg), i);        	
        }
    }

    
    public void onEveryFrame(int msecElapsed) 
    {
        beforeStep();
        world.step(msecElapsed, 6, 2);
        afterStep();
    }

    /*
     * used for serialization: ByteArrayOutputStream bos = new
     * ByteArrayOutputStream(); ObjectOutput out; byte[] bytes = null; try { out
     * = new ObjectOutputStream(bos); // out.writeObject(Unit.data);
     * 
     * bytes = bos.toByteArray();
     * 
     * out.close(); return bytes; //bos.close(); } catch (IOException e) {
     * e.printStackTrace(); } return bytes; //return Unit.data;
     */

    public void addUnit(Unit unit)
    {
        units.add(unit);
    }

    public ArrayList<Unit> getUnits() 
    {
        return units;
    }
    
    public ArrayList<Ball> getBalls()
    {
        ArrayList<Ball> balls = new ArrayList<Ball>();
        for (Unit unit : units)
        {
            if (unit instanceof Ball) 
            {
                balls.add((Ball)unit);
            }
        }
        return balls;
    }

    public static void init()
    {
        stateInstance = new ServerGameState();
    }

}
