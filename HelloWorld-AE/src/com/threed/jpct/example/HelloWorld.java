package com.threed.jpct.example;

import java.lang.reflect.Field;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Light;
import com.threed.jpct.Logger;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;
import com.threed.jpct.util.MemoryHelper;

/**
 * A simple demo. This shows more how to use jPCT-AE than it shows how to write
 * a proper application for Android. It includes basic activity management to
 * handle pause and resume...
 * 
 * @author EgonOlsen
 * 
 */
public class HelloWorld extends Activity implements SensorEventListener {

	// Used to handle pause and resume...
	private static HelloWorld master = null;

	private GLSurfaceView mGLView;
	private MyRenderer renderer = null;
	private FrameBuffer fb = null;
	private World world = null;
	private RGBColor back = new RGBColor(50, 50, 100);

	private Object3D cube = null;
	private Object3D sphere = null;
	private Object3D plane = null;
	
	private Light sun = null;
	
	private SensorManager sensorManager;
	
	private float acc;
	private float dir;
	
	protected void onCreate(Bundle savedInstanceState) {

		if (master != null) {
			copy(master);
		}

		super.onCreate(savedInstanceState);
		mGLView = new GLSurfaceView(getApplication());

		mGLView.setEGLConfigChooser(new GLSurfaceView.EGLConfigChooser() {
			public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
				// Ensure that we get a 16bit framebuffer. Otherwise, we'll fall
				// back to Pixelflinger on some device (read: Samsung I7500)
				int[] attributes = new int[] { EGL10.EGL_DEPTH_SIZE, 16, EGL10.EGL_NONE };
				EGLConfig[] configs = new EGLConfig[1];
				int[] result = new int[1];
				egl.eglChooseConfig(display, attributes, configs, 1, result);
				return configs[0];
			}
		});

		renderer = new MyRenderer();
		mGLView.setRenderer(renderer);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(mGLView);
		
		sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
		sensorManager.registerListener(this, 
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mGLView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mGLView.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		acc = - event.values[SensorManager.DATA_X];
		dir = event.values[SensorManager.DATA_Y];
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	private void copy(Object src) {
		try {
			Logger.log("Copying data from master Activity!");
			Field[] fs = src.getClass().getDeclaredFields();
			for (Field f : fs) {
				f.setAccessible(true);
				f.set(this, f.get(src));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/*
	public boolean onTouchEvent(MotionEvent me) {

		if (me.getAction() == MotionEvent.ACTION_DOWN) {
			xpos = me.getX();
			ypos = me.getY();
			return true;
		}

		if (me.getAction() == MotionEvent.ACTION_UP) {
			xpos = -1;
			ypos = -1;
			touchTurn = 0;
			touchTurnUp = 0;
			return true;
		}

		if (me.getAction() == MotionEvent.ACTION_MOVE) {
			float xd = me.getX() - xpos;
			float yd = me.getY() - ypos;

			xpos = me.getX();
			ypos = me.getY();

			touchTurn = xd / -100f;
			touchTurnUp = yd / -100f;
			return true;
		}

		try {
			Thread.sleep(15);
		} catch (Exception e) {
			// No need for this...
		}

		return super.onTouchEvent(me);
	}
	*/

	protected boolean isFullscreenOpaque() {
		return true;
	}

	class MyRenderer implements GLSurfaceView.Renderer {

		private long time = System.currentTimeMillis();
		private float xVel, yVel = 0.0f;

		public MyRenderer() {
		}

		public void onSurfaceChanged(GL10 gl, int w, int h) {
			if (fb != null) {
				fb.dispose();
			}
			fb = new FrameBuffer(gl, w, h);

			if (master == null) {
				world = new World();
				world.setAmbientLight(20, 20, 20);

				sun = new Light(world);
				sun.setIntensity(250, 250, 250);

				plane = Primitives.getPlane(10, 20);
				plane.strip();
				plane.build();
				plane.setOrigin(new SimpleVector(0, 0, 10));
				world.addObject(plane);
				
				cube = Primitives.getCube(10);
				cube.strip();
				cube.build();
				cube.setOrigin(new SimpleVector(20, 20, 0));
				cube.rotateX(1.57f);
				cube.rotateZ(0.78f);
				world.addObject(cube);

				sphere = Primitives.getSphere(10);
				sphere.strip();
				sphere.build();
				sphere.setOrigin(new SimpleVector(0, 0, 0));
				world.addObject(sphere);
				
				Camera cam = world.getCamera();
				cam.setPosition(-100, 0, -150);
				cam.lookAt(sphere.getTransformedCenter());

				sun.setPosition(new SimpleVector(-100, -100, -100));
				MemoryHelper.compact();

				if (master == null) {
					master = HelloWorld.this;
				}
			}
		}

		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		}

		public void onDrawFrame(GL10 gl) {
			
			float xAcc = acc;
			float yAcc = dir;
			
			/*
			Log.i("time", System.currentTimeMillis() - time + "");
			time = System.currentTimeMillis();
			*/
			
			long elapsed = System.currentTimeMillis() - time;
			time = System.currentTimeMillis();
			
			xVel += xAcc / 1000;
			yVel += yAcc / 1000;
			
			sphere.translate(new SimpleVector(xVel, yVel, 0));
			
			SimpleVector spherePos = sphere.getTransformedCenter();
			
			if (spherePos.x >= 100.0f || spherePos.x <= -100.0f) {
				sphere.translate(new SimpleVector(-xVel, 0, 0));
				xVel = - xVel / 2;
			}
			
			if (spherePos.y >= 100.0f || spherePos.y <= -100.0f) {
				sphere.translate(new SimpleVector(0, -yVel, 0));
				yVel = - yVel / 2;
			}
			
			double angle = Math.atan(xVel / yVel);
			
			float camX = spherePos.x - (float) (100.0d * Math.cos(angle));
			float camY = spherePos.y - (float) (100.0d * Math.sin(angle));
			
			/*
			Log.i("posX " + spherePos.x, "posY " + spherePos.y);
			Log.i("camX " + camX, "camY " + camY);
			*/
			
			Camera cam = world.getCamera();
			cam.setPosition(new SimpleVector(spherePos.x / 2, spherePos.y / 2, -150));
			// cam.setPosition(new SimpleVector(camX, camY, -150));
			cam.lookAt(sphere.getTransformedCenter());
			cam.rotateCameraZ(-1.5707963f);
			
			fb.clear(back);
			world.renderScene(fb);
			world.draw(fb);
			fb.display();
			
		}
	}
}
