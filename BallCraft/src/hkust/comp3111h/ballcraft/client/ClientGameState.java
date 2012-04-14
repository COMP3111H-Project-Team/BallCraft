package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.graphics.Drawable;
import hkust.comp3111h.ballcraft.graphics.Mine;
import hkust.comp3111h.ballcraft.server.Ball;
import hkust.comp3111h.ballcraft.server.Plane;
import hkust.comp3111h.ballcraft.server.Wall;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class ClientGameState {

    private static ClientGameState stateInstance;

    public ArrayList<Ball> balls;
    public ArrayList<Wall> walls;
    public ArrayList<Plane> planes;
    public ArrayList<Drawable> drawableMisc;
    
    private ArrayList<Skill> skills;

    public static World world;
    
    private int mapTerrain;
    private int mapMode;

    private ClientGameState() {
        balls = new ArrayList<Ball>();
        walls = new ArrayList<Wall>();
        planes = new ArrayList<Plane>();
        drawableMisc = new ArrayList<Drawable>();
        
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

    public void addDrawable(Drawable drawable) {
        drawableMisc.add(drawable);
    }
    
    public ArrayList<Drawable> getDrawables() {
        return this.drawableMisc;
    }

    public void deleteMine(int id) {
    	for (Drawable d : drawableMisc) {
    		if (d instanceof Mine) {
    			if (((Mine) d).getID() == id)
    			{
    				drawableMisc.remove(d);
    				return;
    			}
    		}
    	}
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

}
