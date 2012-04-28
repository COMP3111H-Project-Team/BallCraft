package hkust.comp3111h.ballcraft.graphics;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.MapModeDef;
import hkust.comp3111h.ballcraft.TerrainDef;
import hkust.comp3111h.ballcraft.client.ClientGameState;
import hkust.comp3111h.ballcraft.graphics.balls.ParticleBall;
import hkust.comp3111h.ballcraft.graphics.balls.SolidBall;
import hkust.comp3111h.ballcraft.graphics.particles.DarkBallParticle;
import hkust.comp3111h.ballcraft.graphics.particles.ExplosionParticle;
import hkust.comp3111h.ballcraft.graphics.particles.FireBallParticle;
import hkust.comp3111h.ballcraft.graphics.particles.FlameThrowParticle;
import hkust.comp3111h.ballcraft.graphics.particles.MassOverlordParticle;
import hkust.comp3111h.ballcraft.graphics.particles.NaturesCureParticle;
import hkust.comp3111h.ballcraft.graphics.particles.RockBumpParticle;
import hkust.comp3111h.ballcraft.graphics.particles.SlipperyParticle;
import hkust.comp3111h.ballcraft.graphics.particles.WaterBallParticle;
import hkust.comp3111h.ballcraft.graphics.particles.WaterPropelParticle;
import hkust.comp3111h.ballcraft.graphics.skilleffects.Crush;
import hkust.comp3111h.ballcraft.graphics.skilleffects.GrowRoot;
import hkust.comp3111h.ballcraft.graphics.skilleffects.IronWill;
import hkust.comp3111h.ballcraft.graphics.skilleffects.Mine;
import hkust.comp3111h.ballcraft.graphics.skilleffects.RockBump;
import hkust.comp3111h.ballcraft.graphics.skilleffects.SkillEffect;
import hkust.comp3111h.ballcraft.server.Ball;
import hkust.comp3111h.ballcraft.server.Plane;
import hkust.comp3111h.ballcraft.server.Wall;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import org.jbox2d.common.Vec3;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

public class GameRenderer implements GLSurfaceView.Renderer {

    private long time = 0;

    private Context context;
    
    private static boolean rendering = false;
    
    private static int mapMode;
    private static boolean changeMapMode = false;
    
    public GameRenderer(Context context) {
        this.context = context;
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 400f);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(GL10.GL_LIGHT0);

        // set light according to whether it is day or night
        mapMode = ClientGameState.getClientGameState().getMapMode();
        this.loadMapMode(gl);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        gl.glEnable(GL10.GL_NORMALIZE);
        gl.glEnable(GL10.GL_RESCALE_NORMAL);
        
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA); 
        
        this.loadTextures(gl);
    }
    
    private void loadTextures(GL10 gl) {
        Plane.loadTexture(gl, context, 
                TerrainDef.getTerrainFloorTextureById(
                        ClientGameState.getClientGameState().getMapTerrain()));
        Wall.loadTexture(gl, context, 
                TerrainDef.getTerrainWallTextureBallId(
                        ClientGameState.getClientGameState().getMapTerrain()));
                
        Mine.loadTexture(gl, context);
        BallShade.loadTexture(gl, context);
        
        GrowRoot.loadTexture(gl, context);
        IronWill.loadTexture(gl, context);
        
        WaterBallParticle.loadTexture(gl, context);
        FireBallParticle.loadTexture(gl, context);
        DarkBallParticle.loadTexture(gl, context);
        
        WaterPropelParticle.loadTexture(gl, context);
        
        Crush.loadTexture(gl, context);
        RockBump.loadTexture(gl, context);
        MassOverlordParticle.loadTexture(gl, context);
        NaturesCureParticle.loadTexture(gl, context);
        SlipperyParticle.loadTexture(gl, context);
        FlameThrowParticle.loadTexture(gl, context);
        ExplosionParticle.loadTexture(gl, context);
        RockBumpParticle.loadTexture(gl, context);
    }
    
    public static void startRendering() {
        rendering = true;
    }
    
    public static void stopRendering() {
        rendering = false;
    }
    
    public static void changeLightMode(int mode) {
        mapMode = mode;
        changeMapMode = true;
    }
    
    private void loadMapMode(GL10 gl) {
        float [] lightAmbient = MapModeDef.getMapModeAmbientLightById(mapMode);
        float [] lightDiffuse = MapModeDef.getMapModeDiffuseLightById(mapMode);
        float [] lightSpecular = MapModeDef.getMapModeSpecularLightById(mapMode);
        float [] lightPosition = { 300f, 0f, 50f, 1f };

        float [] matAmbient = { 0.4f, 0.4f, 0.4f, 1f };
        float [] matDiffuse = { 0.6f, 0.6f, 0.6f, 1f };
        float [] matSpecular = { 0.9f, 0.9f, 0.9f, 1f };
        float [] matShininess = { 8f };

        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, matAmbient, 0);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, matDiffuse, 0);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, matSpecular, 0);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, matShininess, 0);

        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbient, 0);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDiffuse, 0);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, lightSpecular, 0);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPosition, 0);
    }
    
    public void onDrawFrame(GL10 gl) {
        if (changeMapMode) {
            this.loadMapMode(gl);
            changeMapMode = false;
        }
        if (rendering) {
	        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	        gl.glLoadIdentity();
	        
	        ArrayList<Ball> balls = ClientGameState.getClientGameState().balls;
	        
	        Ball self = (Ball) balls.get(BallCraft.myself);
	        self.useGraphicalPosForDrawing = true;
	
	        float xPos = self.getPosition().x;
	        float yPos = self.getPosition().y;
	        float zPos = self.z;
	        
	        GLU.gluLookAt(gl, xPos, yPos + 60 + zPos / 2, 200 - zPos, xPos, yPos, -zPos, 0, 0, 1);
	        
	        for (Plane p : ClientGameState.getClientGameState().planes) {
	            p.draw(gl);
	        }
	       
	        for (Wall w : ClientGameState.getClientGameState().walls) {
	            w.draw(gl);
	        }
		        
	        ConcurrentHashMap<Integer, SkillEffect> effects = 
	                ClientGameState.getClientGameState().skillEffects;
	        
	        // draw all texture effects
	        for (Integer key : effects.keySet()) {
	            SkillEffect d = effects.get(key);
	            // if (d instanceof TextureEffect) {
	            if (d.drawBeforeBalls) {
		            if (d.timeout()) {
		                effects.remove(key);
		            } else {
		                d.move();
			            d.draw(gl);
		            }
	            }
	        }
	         
	        for (Ball b : balls) {
	            if (b instanceof ParticleBall) {
	                ((ParticleBall) b).move();
	            }
	            if (b.useGraphicalPosForDrawing) {
	                b.setGraphicalPosition(new Vec3(xPos, yPos, zPos));
	            }
	            b.draw(gl);
	            if (b instanceof SolidBall) {
	                if (b.z <= 0) { // above the plane, draw shade
		                if (b.useGraphicalPosForDrawing) {
				            BallShade.draw(gl, b.getRadius(), 
				                    b.getGraphicalPosition().x + 8, b.getGraphicalPosition().y - 2);
		                } else {
				            BallShade.draw(gl, b.getRadius(), 
				                    b.getPosition().x + 8, b.getPosition().y - 2);
		                }
	                }
	            }
	        }
	        
	        // draw all particle system effects
	        for (Integer key : effects.keySet()) {
	            SkillEffect d = effects.get(key);
	            // if (d instanceof ParticleSystemEffect) {
	            if (!d.drawBeforeBalls) {
		            if (d.timeout()) {
		                effects.remove(key);
		            } else {
		                d.move();
			            d.draw(gl);
		            }
	            }
	        }
	        
	        long elapsed = System.currentTimeMillis() - time;
	        if (elapsed < 30) {
	            try {
	                Thread.sleep(30 - elapsed);
	            } catch (Exception e) {}
	        }
	    }
    }
    
}
