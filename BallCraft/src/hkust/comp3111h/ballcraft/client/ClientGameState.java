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
    
    private int selfScoreEarned = 0;
    private int selfCombo = 0;
    
    private int enemyScoreEarned = 0;
    private int enemyCombo = 0;

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
        
        this.checkSelfBallState();
        if (!BallCraft.isSinglePlayer()) {
            this.checkEnemyBallState();
        }
    }
    
    private void sendScore() {
        if (BallCraft.isServer) {
            ServerAdapter.sendScoreToServer(
                    Math.max(this.selfScoreEarned, this.enemyScoreEarned));
        }
    
    }
    
    private void checkSelfBallState() {
        Ball self = balls.get(BallCraft.myself);
        float zPos = self.z;
	        
        if (selfLastZPos <= 0 && zPos > 0) { // just died
            this.selfCombo = 0;
            this.enemyCombo++;
            this.enemyScoreEarned += this.enemyCombo;
            GameActivity.skillDisableHandler.sendEmptyMessage(0);
        } else if (selfLastZPos > 0 && zPos <= 0) { // just respawned
            GameActivity.skillEnableHandler.sendEmptyMessage(0);
        }
        
        if (selfLastZPos <= 200 && zPos > 200) {
            Message msg = new Message();
            if (BallCraft.isSinglePlayer()) { // single player, no need to display score
                msg.what = 2;
                GameActivity.loseViewHanlder.sendMessage(msg);
            } else { // multi-player
	            msg.what = 1;
	            msg.arg1 = this.selfScoreEarned;
	            msg.arg2 = this.enemyScoreEarned;
	            GameActivity.loseViewHanlder.sendMessage(msg);
            }
        } else if (selfLastZPos > 200 && zPos <= 200) {
            Message msg = new Message();
            msg.what = 0;
            GameActivity.loseViewHanlder.sendMessage(msg);
        }
        
        selfLastZPos = zPos;
        this.sendScore();
    }
    
    private void checkEnemyBallState() {
        Ball enemy;
        // only 2 players for now
        for (int i = 0; i < BallCraft.maxPlayer; i++) {
            if (i != BallCraft.myself) {
                enemy = balls.get(i);
		        float zPos = enemy.z;
		        
		        if (enemyLastZPos <= 0 && zPos > 0) { // enemy just died
		            this.enemyCombo = 0;
		            this.selfCombo++;
		            this.selfScoreEarned += this.selfCombo;
		        }
		        
		        if (enemyLastZPos <= 200 && zPos > 200) {
		            Message msg = new Message();
		            msg.what = 1;
		            msg.arg1 = this.selfScoreEarned;
		            msg.arg2 = this.enemyScoreEarned;
		            GameActivity.loseViewHanlder.sendMessage(msg);
		        } else if (enemyLastZPos > 200 && zPos <= 200) {
		            Message msg = new Message();
		            msg.what = 0;
		            GameActivity.loseViewHanlder.sendMessage(msg);
		        }
		        
		        enemyLastZPos = zPos;
		        this.sendScore();
            }
        }
    }

    public void addSkillEffect(int id, SkillEffect effect) {
        this.skillEffects.put(id, effect);
    }
    
    public ConcurrentHashMap<Integer, SkillEffect> getDrawables() {
        return this.skillEffects;
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

}
