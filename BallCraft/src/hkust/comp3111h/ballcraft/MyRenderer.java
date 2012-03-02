package hkust.comp3111h.ballcraft;

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
	private Cube cube = null;
	private Sphere sphere = null;
	
	public MyRenderer(Client client) {
		this.client = client;
		
		gamePlane = new Plane();
		cube = new Cube();
		sphere = new Sphere(15, 25);
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 1000.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}

	@SuppressWarnings("unchecked")
	public void onDrawFrame(GL10 gl) {

		ArrayList<UnitData> data = null;
		byte[] bytes = Server.process(0, client.getInput()).getUnitData();
		
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInput in;
		try
		{
			in = new ObjectInputStream(bis);
			data = (ArrayList<UnitData>) in.readObject();
			bis.close();
			in.close();
		} 
		catch (StreamCorruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glTranslatef(0, 0, -300);
		
		gl.glPushMatrix();
			gl.glColor4f(0.3f, 0.3f, 0.3f, 1f);
			gamePlane.draw(gl);
		gl.glPopMatrix();
				
		for (int i = 0; i < data.size(); i++) {
			UnitData datum = data.get(i);
			if (datum.identity == Unit.type.BALL) {
				gl.glPushMatrix();
					gl.glTranslatef(datum.position.x, - datum.position.y, 5);
					gl.glColor4f(1f, 0f, 0f, 1f);
					gl.glScalef(datum.size / 15, datum.size / 15, datum.size / 15);
					sphere.draw(gl);
				gl.glPopMatrix();
			}
		}
	}
}
