package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.graphics.skilleffects.SkillEffect;
import hkust.comp3111h.ballcraft.server.Ball;
import hkust.comp3111h.ballcraft.server.Plane;
import hkust.comp3111h.ballcraft.server.ServerAdapter;
import hkust.comp3111h.ballcraft.server.Wall;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import android.os.Message;

public class ClientGameState {

    private static ClientGameState stateInstance;

    public ArrayList<Ball> balls;
    public ArrayList<Wall> walls;
    public ArrayList<Plane> planes;
    public ConcurrentHashMap<Integer, SkillEffect> skillEffects;

    public static World world;
    
    private int mapTerrain;
    private int mapMode;
    
    private float selfLastZPos;
    private float enemyLastZPos;
    
    public int selfScoreEarned = 0;
    public int enemyScoreEarned = 0;

    private ClientGameState() {
        balls = new ArrayList<Ball>();
        walls = new ArrayList<Wall>();
        planes = new ArrayList<Plane>();
        skillEffects = new ConcurrentHashMap<Integer, SkillEffect>();
        
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
    
    public static void clear()
    {
        stateInstance = new ClientGameState();    	
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

    public void addSkillEffect(int id, SkillEffect effect) {
        this.skillEffects.put(id, effect);
    }
    
    public ConcurrentHashMap<Integer, SkillEffect> getDrawables() {
        return this.skillEffects;
    }
    
    public SkillEffect getDrawable(int id) {
        return this.skillEffects.get(new Integer(id));
    }

    public void deleteDrawable(int id) {
        this.skillEffects.remove(new Integer(id));
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
    
    public int getScoreEarned() {
        return this.selfScoreEarned;
    }
    
    public void clearAll() {
        balls.clear();
        walls.clear();
        planes.clear();
        skillEffects.clear();
    }
    
    public void setScoreEarned(int s) {
    	this.selfScoreEarned = s;
    }

}