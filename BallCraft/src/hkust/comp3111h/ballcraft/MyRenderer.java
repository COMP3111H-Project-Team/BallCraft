package hkust.comp3111h.ballcraft;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;

public class MyRenderer implements GLSurfaceView.Renderer {
	
	private Client client = null;
	
	private Plane gamePlane = null;
	private Cube cube = null;
	private Sphere sphere = null;
	
	private long time = 0;
	
	public MyRenderer(Client client) {
		this.client = client;
		
		gamePlane = new Plane();
		cube = new Cube();
		sphere = new Sphere(40, 40, 30);
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 1000.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		/*
		float lightAmbient [] = { 0.1f, 0.1f, 0.1f, 1.0f };
		float lightDiffuse [] = { 0.9f, 0.9f, 0.9f, 1.0f };
		float lightPos [] = {100f, 100f, -50f, 1f};
		
		float matAmbient [] = { 0.6f, 0.6f, 0.6f, 1.0f };
		float matDiffuse [] = { 1f, 1f, 1f, 1f };

		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_LIGHT0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, matAmbient, 0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, matDiffuse, 0);

		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbient, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDiffuse, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPos, 0);

		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glShadeModel(GL10.GL_SMOOTH);
		*/
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		
		FloatBuffer global_ambient = FloatBuffer.allocate(4);
        global_ambient.put(0.5f);
        global_ambient.put(0.5f);
        global_ambient.put(0.5f);
        global_ambient.put(1.0f);
        
        FloatBuffer position = FloatBuffer.allocate(4);
        position.put(10f);
        position.put(10f);
        position.put(10f);
        position.put(1f);
        
        /*
        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(GL10.GL_LIGHT0);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, position);
        
        float[] mycolor = { 0.8f, 0.7f, 0.6f, 1.0f };
        gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_AMBIENT_AND_DIFFUSE, 
        		mycolor,
        		0);
		*/
		
		/*
		gl.glEnable(GL10.GL_LIGHT0);
		
		float [] sunlightDir = { 0.0f, 2.0f, -1.0f, 1.0f };
		ByteBuffer bb = ByteBuffer.allocateDirect(sunlightDir.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(sunlightDir);
		fb.position(0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, fb);
		
		float [] sunlightInt = { 0.7f, 0.7f, 0.7f, 1.0f };
		bb = ByteBuffer.allocateDirect(sunlightDir.length * 4);
		bb.order(ByteOrder.nativeOrder());
		fb = bb.asFloatBuffer();
		fb.put(sunlightDir);
		fb.position(0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, fb);
		
		gl.glEnable(GL10.GL_COLOR_MATERIAL);
		gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_AMBIENT_AND_DIFFUSE, fb);
		*/
	}

	@SuppressWarnings("unchecked")
	public void onDrawFrame(GL10 gl) {
		
		/*
		long elapsed = System.currentTimeMillis() - time;
		Log.d("fps", "" + 1000 / elapsed);
		time = System.currentTimeMillis();
		*/

		ArrayList<UnitData> data = null;
		byte[] bytes = Server.process(0, client.getInput());
		
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
		gl.glTranslatef(0, 0, -300);
		
		/*
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_COLOR_MATERIAL);
		*/
		
		gl.glPushMatrix();
			gl.glColor4f(0.3f, 0.3f, 0.3f, 1f);
			gamePlane.draw(gl);
		gl.glPopMatrix();
				
		for (int i = 0; i < data.size(); i++) {
			UnitData datum = data.get(i);
			if (datum.identity == Unit.type.BALL) {
				gl.glPushMatrix();
					gl.glTranslatef(datum.position.x, - datum.position.y, 5);
					gl.glColor4f(1f, 1f, 1f, 1f);
					gl.glScalef(datum.size, datum.size, datum.size);
					sphere.draw(gl);
				gl.glPopMatrix();
			}
		}
	}
}
