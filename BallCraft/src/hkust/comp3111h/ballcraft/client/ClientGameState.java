package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.graphics.Drawable;
import hkust.comp3111h.ballcraft.graphics.ParticleSystem;
import hkust.comp3111h.ballcraft.server.Ball;
import hkust.comp3111h.ballcraft.server.Plane;
import hkust.comp3111h.ballcraft.server.Unit;
import hkust.comp3111h.ballcraft.server.Wall;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import android.util.Log;

public class ClientGameState {

    private static ClientGameState stateInstance;

    private ArrayList<Drawable> drawables;
    
    private ArrayList<Ball> balls;
    private ArrayList<Wall> walls;
    private ArrayList<Plane> planes;
    private ArrayList<ParticleSystem> particleSystems;
    
    private ArrayList<Skill> skills;

    public static World world;
    
    private int mapTerrain;
    private int mapMode;

    private ClientGameState() {
        drawables = new ArrayList<Drawable>();
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
        String[] unitStrs = serialized.split("/");
        Log.w("" + drawables.size(), unitStrs.length + "");
        for (int i = 0; i < unitStrs.length; i++) {
            if (i < drawables.size()) {
                ((Unit) (drawables.get(i))).updateFromString(unitStrs[i]);
            } else {
                drawables.add(Unit.fromSerializedString(unitStrs[i]));
            }
            /*
            Unit unit = Unit.fromSerializedString(unitStrs[i]);
            if (unit instanceof Ball) {
                if (i < kk
            }
            */
        }
    }

    /*
    public void addDrawable(Drawable drawable) {
        drawables.add(drawable);
    }
    */

    public void addSkill(Skill skill) {
        skills.add(skill);
    }

    public ArrayList<Drawable> getDrawables() {
        return drawables;
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
