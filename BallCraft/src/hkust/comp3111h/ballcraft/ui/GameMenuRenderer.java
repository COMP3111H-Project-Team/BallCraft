package hkust.comp3111h.ballcraft.ui;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

public class GameMenuRenderer implements GLSurfaceView.Renderer {
	
	private C60 c60;
	
	private float turnX = 0;
	private float turnY = 0;
	
	/*
	float [] fogColor = {
			0.5f, 0.5f, 0.5f, 1.0f
	};
	
	private FloatBuffer fogBuffer;
	*/
	
	public GameMenuRenderer(Context context) {
		c60 = new C60();
		// fogBuffer = makeFogBuffer();
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 1000.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glEnable(GL10.GL_DITHER);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glTranslatef(0, 0, -300);	
		
		gl.glRotatef(turnX, 0, 1, 0);
		gl.glRotatef(turnY, 1, 0, 0);
		
		turnX += MainMenu.turnX;
		turnY += MainMenu.turnY;
		
		gl.glColor4f(0.5f, 0.5f, 0.5f, 0.6f);
		c60.draw(gl);
	}
	
	/*
	private FloatBuffer makeFogBuffer() {
		ByteBuffer bb = ByteBuffer.allocateDirect(fogColor.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(fogColor);
		fb.position(0);
		return fb;
	}
	*/
	
}