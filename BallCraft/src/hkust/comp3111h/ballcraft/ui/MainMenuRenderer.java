package hkust.comp3111h.ballcraft.ui;

import hkust.comp3111h.ballcraft.R;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;

public class MainMenuRenderer implements GLSurfaceView.Renderer {
	
	private Tet testPlane;
	
	private Bitmap bitmap;
	
	private int [] textures;
	
	private float turnX = 0;
	private float turnY = 0;
	
	public MainMenuRenderer(Context context) {
		testPlane = new Tet();
		initLabel(context);
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
		
		/*
		if (MainMenu.touchTurn != 0) {
			gl.glRotatef(MainMenu.touchTurn, 0, 1, 0);
			turned += MainMenu.touchTurn;
		}
		
		if (MainMenu.touchTurnUp != 0) {
			gl.glRotatef(MainMenu.touchTurn, 1, 0, 0);
			turnedUp += MainMenu.touchTurnUp;
		}
		*/
		
		turnX += MainMenu.turnX;
		turnY += MainMenu.turnY;
		
		gl.glColor4f(0.5f, 0.5f, 0.5f, 0.6f);
		testPlane.draw(gl);
		
		gl.glGenTextures(1, textures, 0);
		//...and bind it to our array
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

		//Create Nearest Filtered Texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

		//Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);

		//Use the Android GLUtils to specify a two-dimensional texture image from our bitmap
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

		//Clean up
		bitmap.recycle();
	}
	
	private void initLabel(Context context) {
		textures = new int [1];
		bitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_4444);
		Canvas canvas = new Canvas(bitmap);
		bitmap.eraseColor(0);

		Drawable background = context.getResources().getDrawable(R.drawable.black);
		background.setBounds(0, 0, 256, 256);
		background.draw(canvas); // draw the background to our bitmap

		Paint textPaint = new Paint();
		textPaint.setTextSize(32);
		textPaint.setAntiAlias(true);
		textPaint.setARGB(0xff, 0x00, 0x00, 0x00);
		canvas.drawText("Hello World", 16,112, textPaint);
	}
	
}