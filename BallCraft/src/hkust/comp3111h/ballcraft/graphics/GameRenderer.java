package hkust.comp3111h.ballcraft.graphics;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.MapModeDef;
import hkust.comp3111h.ballcraft.TerrainDef;
import hkust.comp3111h.ballcraft.client.ClientGameState;
import hkust.comp3111h.ballcraft.client.GameActivity;
import hkust.comp3111h.ballcraft.client.Player;
import hkust.comp3111h.ballcraft.server.Ball;
import hkust.comp3111h.ballcraft.server.Plane;
import hkust.comp3111h.ballcraft.server.Wall;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

public class GameRenderer implements GLSurfaceView.Renderer {

    private long time = 0;

    private Context context;
    
    public GameRenderer(Context context) {
        this.context = context;
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 500f);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(GL10.GL_LIGHT0);

        // set light according to whether it is day or night
        int mapMode = ClientGameState.getClientGameState().getMapMode();
        float [] lightAmbient = MapModeDef.getMapModeAmbientLightById(mapMode);
        float [] lightDiffuse = MapModeDef.getMapModeDiffuseLightById(mapMode);
        float [] lightSpecular = MapModeDef.getMapModeSpecularLightById(mapMode);
        float [] lightPosition = { 200f, 0, 100f, 1f };

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
                
        Particle.loadTexture(gl, context);
        Mine.loadTexture(gl, context);
        BallShade.loadTexture(gl, context);
        Ball.loadTexture(gl, context);
    }

    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        
        ArrayList<Ball> balls = ClientGameState.getClientGameState().balls;
        
        Ball self = (Ball) balls.get(BallCraft.myself);

        float xPos = self.getPosition().x;
        float yPos = self.getPosition().y;
        
        GLU.gluLookAt(gl, xPos, yPos + 80, 200, xPos, yPos, 5, 0, 0, 1);
            
        for (Ball b : balls) {
            b.draw(gl);
            BallShade.draw(gl, b.getPosition().x + 10, b.getPosition().y + 3);
        }
        
        for (Plane p : ClientGameState.getClientGameState().planes) {
            p.draw(gl);
        }
        
        for (Wall w : ClientGameState.getClientGameState().walls) {
            w.draw(gl);
        }
        
        synchronized(ClientGameState.getClientGameState().drawableMisc)
        {
	        for (Drawable d : ClientGameState.getClientGameState().drawableMisc) {
	            if (d instanceof ParticleSystem) {
	                ((ParticleSystem) d).move();
	            }
	            d.draw(gl);
	        }
        }
            
        long elapsed = System.currentTimeMillis() - time;
        GameActivity.display("fps: " + 1000 / elapsed);
        time = System.currentTimeMillis();
    }
    
}
