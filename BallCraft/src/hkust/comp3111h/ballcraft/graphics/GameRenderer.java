package hkust.comp3111h.ballcraft.graphics;

import hkust.comp3111h.ballcraft.client.Client;
import hkust.comp3111h.ballcraft.client.ClientGameState;
import hkust.comp3111h.ballcraft.client.GameActivity;
import hkust.comp3111h.ballcraft.client.Player;
import hkust.comp3111h.ballcraft.server.Ball;
import hkust.comp3111h.ballcraft.server.Wall;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

public class GameRenderer implements GLSurfaceView.Renderer {

    private long time = 0;

    private Plane plane;
    
    private Context context;
    
    // testing:
    private ParticleSystem system;

    public GameRenderer(Context context) {
        this.context = context;
        plane = new Plane();
        
        // system = new ParticleSystem2(20, 20, 20);
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

        /*
         * used for dark environment
        float lightAmbient[] = { 0.1f, 0.1f, 0.1f, 1.0f };
        float lightDiffuse[] = { 0.2f, 0.2f, 0.2f, 1.0f };
        float lightSpecular[] = { 0.1f, 0.1f, 0.1f, 1.0f };
        */
        float lightAmbient[] = { 0.4f, 0.4f, 0.4f, 1.0f };
        float lightDiffuse[] = { 0.6f, 0.6f, 0.6f, 1.0f };
        float lightSpecular[] = { 0.9f, 0.9f, 0.9f, 1.0f };
        float lightPosition[] = { 100f, 100f, 100f, 1f };

        float matAmbient[] = { 0.4f, 0.4f, 0.4f, 1f };
        float matDiffuse[] = { 0.6f, 0.6f, 0.6f, 1f };
        float matSpecular[] = { 0.9f, 0.9f, 0.9f, 1f };
        float matShininess[] = { 8f };

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
        Plane.loadTexture(gl, context);
        Particle.loadTexture(gl, context);
        Mine.loadTexture(gl, context);
        Wall.loadTexture(gl, context);
    }

    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        
        ArrayList<Drawable> drawables = ClientGameState.getClientGameState().getDrawables();
        if (drawables.size() > Player.playerID) {
            Ball self = (Ball) drawables.get(Player.playerID);

            float xPos = self.getPosition().x;
            float yPos = self.getPosition().y;
            
            if (!Client.playerDied) {
	            GLU.gluLookAt(gl, xPos, yPos + 80, 200, xPos, yPos, 5, 0, 0, 1);
            } else {
	            GLU.gluLookAt(gl, xPos, yPos + 80, 200, xPos, yPos, 5, 0, 0, 1);
	            // GLU.gluLookAt(gl, xPos, yPos + 80, self.z + 200, xPos, yPos, 5, 0, 0, 1);
            }

            plane.draw(gl);

            self.draw(gl);
            
            for (Drawable d: drawables) {
                if (d!= self) {
                    if (d instanceof ParticleSystem) {
                        ((ParticleSystem) d).move();
                    }
                    d.draw(gl);
                }
            }
            
            /*
            system.move();
            system.draw(gl);
            */

            long elapsed = System.currentTimeMillis() - time;
            GameActivity.display("fps: " + 1000 / elapsed);
            time = System.currentTimeMillis();
        }
    }
}
