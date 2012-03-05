package hkust.comp3111h.ballcraft.graphics;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.client.Client;
import hkust.comp3111h.ballcraft.server.ServerAdapter;
import hkust.comp3111h.ballcraft.server.Unit;
import hkust.comp3111h.ballcraft.server.UnitData;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

public class MyRenderer implements GLSurfaceView.Renderer {
	
	private Client client = null;
	
	private Plane gamePlane = null;
	private Sphere sphere = null;
	
	private long time = 0;
	
	private boolean skillActivated = false;
	private ParticleSystem system = null;
	
	public MyRenderer(Client client) {
		this.client = client;
		
		gamePlane = new Plane();
		sphere = new Sphere(30, 30, 1);
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 1000.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		/*
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_LIGHT0);
		
		float lightAmbient [] = { 0.7f, 0.7f, 0.7f, 1.0f };
		float lightDiffuse [] = { 0.7f, 0.7f, 0.7f, 1.0f };
		float matSpecular [] = { 1f, 1f, 1f, 1f };
		float matShininess [] = { 5.0f };
		float matAmbient [] = { 1f, 1f, 1f, 1f };
		float matDiffuse [] = { 1f, 1f, 1f, 1f };
		float lightPosition [] = { 100f, 100f, 100f, 0f };
		
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_SPECULAR, matSpecular, 0);
		gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_SHININESS, matShininess, 0);
		gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_AMBIENT, matAmbient, 0);
		gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_DIFFUSE, matDiffuse, 0);
		
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPosition, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbient, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDiffuse, 0);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		*/
		
		/*
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_LIGHT0);
		gl.glShadeModel(GL10.GL_SMOOTH);
		
		float lightAmbient [] = { 0.2f, 0.2f, 0.2f, 1.0f };
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbient, 0);
		
		float lightDiffuse [] = { 0.7f, 0.7f, 0.7f, 1.0f };
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDiffuse, 0);
		
		float lightSpecular [] = { 0.7f, 0.7f, 0.7f, 1.0f };
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, lightSpecular, 0);
		
		float lightPos [] = { 10f, 10f, 50f, 0f };
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPos, 0);
		
		float lightDir [] = { 0f, 0f, -1f };
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPOT_DIRECTION, lightDir, 0);
		*/
		
		// gl.glLightf(GL10.GL_LIGHT0, GL10.GL_SPOT_CUTOFF, 45.0f);
		
		// gl.glLoadIdentity();
		
		
		/*
		float matAmbient [] = { 1.0f, 1.0f, 1.0f, 1.0f };
		float matDiffuse [] = { 1.0f, 1.0f, 1.0f, 1.0f };

		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, matAmbient, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, matDiffuse, 0);

		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbient, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDiffuse, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPos, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, lightSpecular, 0);

		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glShadeModel(GL10.GL_SMOOTH);
		*/
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		gl.glShadeModel(GL10.GL_SMOOTH); gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}

	@SuppressWarnings("unchecked")
	public void onDrawFrame(GL10 gl) {
		
		/*
		long elapsed = System.currentTimeMillis() - time;
		Log.d("fps", "" + 1000 / elapsed);
		time = System.currentTimeMillis();
		*/
		
		ArrayList<UnitData> data = null;
		byte[] bytes = ServerAdapter.process(0, client.getInput());
		
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInput in;
		
		try {
			in = new ObjectInputStream(bis);
			data = (ArrayList<UnitData>) in.readObject();
			bis.close();
			in.close();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glTranslatef(-data.get(0).position.x, data.get(0).position.y, -300);
		
		gl.glPushMatrix();
			gl.glColor4f(0.6f, 0.6f, 0.6f, 1f);
			gamePlane.draw(gl);
		gl.glPopMatrix();
				
		for (int i = 0; i < data.size(); i++) {
			UnitData datum = data.get(i);
			if (datum.identity == Unit.type.BALL) {
				gl.glPushMatrix();
					gl.glTranslatef(datum.position.x, - datum.position.y, 5);
					gl.glColor4f(0f, 0f, 1f, 1f);
					gl.glScalef(datum.size, datum.size, datum.size);
					sphere.draw(gl);
				gl.glPopMatrix();
			}
		}
		
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
	}
}
