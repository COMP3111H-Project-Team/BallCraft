package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.graphics.Drawable;
import hkust.comp3111h.ballcraft.graphics.Mine;
import hkust.comp3111h.ballcraft.server.Ball;
import hkust.comp3111h.ballcraft.server.Plane;
import hkust.comp3111h.ballcraft.server.Wall;
import hkust.comp3111h.ballcraft.skills.Skill;

import java.util.ArrayList;
import java.util.HashMap;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class ClientGameState {

    private static ClientGameState stateInstance;

    public ArrayList<Ball> balls;
    public ArrayList<Wall> walls;
    public ArrayList<Plane> planes;
    public HashMap<Integer, Drawable> drawableMisc;
    
    private ArrayList<Skill> skills;

    public static World world;
    
    private int mapTerrain;
    private int mapMode;

    private ClientGameState() {
        balls = new ArrayList<Ball>();
        walls = new ArrayList<Wall>();
        planes = new ArrayList<Plane>();
        drawableMisc = new HashMap<Integer, Drawable>();
        
        skills = new ArrayList<Skill>();

        Vec2 gravity = new Vec2(0.0f, 0.0f);
        boolean doSleep = true;
        world = new World(gravity, doSleep);
    }

    public static ClientGameState getClientGameState() {
        if (stateInstance == null) {
            stateInstance = new ClientGameState();
        }
        return stateInstance;
    }

    /**
     * Apply a serialized GameUpdater to the ClientGameState to change the data
     * @param serialized The serialized string containing the data of the GameUpdater
     */
    public void applyUpdater(String serialized) {
        String[] ballStrs = serialized.split("/");
        for (int i = 0; i < ballStrs.length; i++) {
            balls.get(i).updateFromString(ballStrs[i]);
        }
    }

    public void addDrawable(int id, Drawable drawable) {
        this.drawableMisc.put(id, drawable);
    }
    
    public HashMap<Integer, Drawable> getDrawables() {
        return this.drawableMisc;
    }

    public void deleteDrawable(int id) {
        /*
    	for (Drawable d : drawableMisc) {
    		if (d instanceof Mine) {
    			if (((Mine) d).getID() == id)
    			{
    				drawableMisc.remove(d);
    				return;
    			}
    		}
    	}
    	*/
        drawableMisc.remove(new Integer(id));
    }
    
    public void addSkill(Skill skill) {
        skills.add(skill);
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setMapTerrain(int terrain) {
        this.mapTerrain = terrain;
    }
    
    public int getMapTerrain() {
        return this.mapTerrain;
    }
    
    public void setMapMode(int mode) {
        this.mapMode = mode;
    }
    
    public int getMapMode() {
        return this.mapMode;
    }
    
    public void clearAll() {
        balls.clear();
        walls.clear();
        planes.clear();
        drawableMisc.clear();
        skills.clear();
    }

}
