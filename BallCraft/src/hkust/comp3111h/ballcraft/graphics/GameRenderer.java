package hkust.comp3111h.ballcraft.graphics;

import hkust.comp3111h.ballcraft.client.ClientGameState;
import hkust.comp3111h.ballcraft.client.GameActivity;
import hkust.comp3111h.ballcraft.client.Player;
import hkust.comp3111h.ballcraft.server.Unit;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

public class GameRenderer implements GLSurfaceView.Renderer {
	
	private ClientGameState gameState;
	
	private long time = 0;
	
	private Plane plane;
	
	public GameRenderer() {
		gameState = ClientGameState.getClientGameState();
		plane = new Plane();
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 800f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_LIGHT0);
		
		float lightAmbient [] = { 0.4f, 0.4f, 0.4f, 1.0f };
		float lightDiffuse [] = { 0.6f, 0.6f, 0.6f, 1.0f };
		float lightSpecular [] = { 0.9f, 0.9f, 0.9f, 1.0f };
		float lightPosition [] = { 300f, 300f, 100f, 1f };
		
		float matAmbient [] = { 0.3f, 0.3f, 0.3f, 1f };
		float matDiffuse [] = { 0.6f, 0.6f, 0.6f, 1f };
		float matSpecular [] = { 1f, 1f, 1f, 1f };
		float matShininess [] = { 8f };
		
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, matSpecular, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, matShininess, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, matAmbient, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, matDiffuse, 0);
		
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, lightSpecular, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPosition, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbient, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDiffuse, 0);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// gamePlane.loadTexture(gl, client); // tex
		// gl.glEnable(GL10.GL_TEXTURE_2D); //tex
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}
	
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		
		ArrayList<Drawable> drawables = gameState.getDrawables();
		if (drawables.size() > Player.playerID) {
			Unit self = (Unit) drawables.get(Player.playerID);
			
			float xPos = self.getPosition().x;
			float yPos = self.getPosition().y;
			GLU.gluLookAt(gl, 
					xPos, yPos + 200, 300, 
					xPos, yPos, 5, 
					0, 0, 1);
			
			gl.glColor4f(1f, 1f, 1f, 1f);
			plane.draw(gl);
			
			gl.glColor4f(1f, 0f, 0f, 1f);
			self.draw(gl);
			
			gl.glColor4f(0f, 0f, 1f, 1f);
			for (int i = 1; i < drawables.size(); i++) {
				drawables.get(i).draw(gl);
			}
			
			long elapsed = System.currentTimeMillis() - time;
			GameActivity.display("fps: " + 1000 / elapsed);
			time = System.currentTimeMillis();
			
			/*
			float xPos = self.position.x;
			float yPos = - self.position.y;
			float xVel = self.velocity.x;
			float yVel = - self.velocity.y;
			double vel = 200; // Math.sqrt(xVel * xVel + yVel * yVel) * 100;
			double tanAngle = xVel / yVel;
			double angle = Math.atan(tanAngle);
			float xDiff = (float) (Math.sin(angle) * vel);
			float yDiff = (float) (Math.cos(angle) * vel);
		
			if (xVel > 0 && yVel > 0) { // first quadrant
				GLU.gluLookAt(gl, xPos - xDiff, yPos - yDiff, 300, xPos, yPos, 5f, 0, 0, 1f);
			} else if (xVel < 0 && yVel > 0) { // second quadrant
				GLU.gluLookAt(gl, xPos - xDiff, yPos - yDiff, 300, xPos, yPos, 5f, 0, 0, 1f);
			} else if (xVel < 0 && yVel < 0) { // third quadrant
				GLU.gluLookAt(gl, xPos + xDiff, yPos + yDiff, 300, xPos, yPos, 5f, 0, 0, 1f);
			} else if (xVel > 0 && yVel < 0) { // fourth quadrant
				GLU.gluLookAt(gl, xPos + xDiff, yPos + yDiff, 300, xPos, yPos, 5f, 0, 0, 1f);
			} else {
				gl.glTranslatef(-self.position.x, self.position.y, -300);
			}
			*/
			
			/*
			gl.glPushMatrix();
				gl.glColor4f(0.6f, 0.6f, 0.6f, 1f);
				gamePlane.draw(gl);
			gl.glPopMatrix();
					
			for (int i = 0; i < data.size(); i++) {
				UnitData datum = data.get(i);
				if (datum.identity == Unit.type.BALL) {
					gl.glPushMatrix();
						gl.glTranslatef(datum.position.x, - datum.position.y, 10);
						gl.glColor4f(0f, 0f, 1f, 1f);
						gl.glScalef(datum.size, datum.size, datum.size);
						sphere.draw(gl);
					gl.glPopMatrix();
				}
			}
			
			gl.glColor4f(1f, 1f, 0f, 1f);
			wall.draw(gl);
			
			
			if (ServerAdapter.skillActive()) {
				skillActivated = true;
				if (ServerAdapter.getSkill() == BallCraft.Skill.TEST_SKILL_1) {
					system = new ParticleSystem1(data.get(0).position.x, -data.get(0).position.y, 0);
				} else if (ServerAdapter.getSkill() == BallCraft.Skill.TEST_SKILL_2) {
					system = new ParticleSystem2(data.get(0).position.x, -data.get(0).position.y, 0);
				}
			}
			
			if (skillActivated) {
				system.move();
				system.draw(gl);
			}
			*/
		}
	}
}
