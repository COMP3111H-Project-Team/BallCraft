package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.graphics.skilleffects.SkillEffect;
import hkust.comp3111h.ballcraft.server.Ball;
import hkust.comp3111h.ballcraft.server.Plane;
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
    
    public int selfScoreEarned = 0;
    public int enemyScoreEarned = 0;
    
    private float lastZPos = 0;

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
        // stateInstance = new ClientGameState();
    	stateInstance.balls.clear();
    	stateInstance.walls.clear();
    	stateInstance.planes.clear();
    	stateInstance.skillEffects.clear();
    	stateInstance.lastZPos = 0;
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
        
        float zPos = balls.get(BallCraft.myself).z;
        
        if (lastZPos <= 0 && zPos > 0) {
            GameActivity.skillDisableHandler.sendEmptyMessage(0);
        } else if (lastZPos > 0 && zPos <= 0) {
            GameActivity.skillEnableHandler.sendEmptyMessage(0);
        }
        
        if (lastZPos <= 200 && zPos > 200) {
            Message msg = new Message();
            msg.what = 1;
            GameActivity.loseViewHandler.sendMessage(msg);
        } else if (lastZPos > 0 && zPos <= 0) {
            Message msg = new Message();
            msg.what = 0;
            GameActivity.loseViewHandler.sendMessage(msg);
        }
        lastZPos = zPos;
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
    
    public void clearAll() {
        balls.clear();
        walls.clear();
        planes.clear();
        skillEffects.clear();
    }
    
    public int getScoreEarned() {
        return this.selfScoreEarned;
    }
    
    public void setScoreEarned(int s) {
    	this.selfScoreEarned = s;
    }
    
    public int getEnemyScore() {
    	return this.enemyScoreEarned;
    }
    
    public void setEnemyScore(int s) {
    	this.selfScoreEarned = s;
    }

}