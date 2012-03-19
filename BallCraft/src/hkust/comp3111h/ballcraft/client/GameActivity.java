package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.graphics.GameRenderer;
import hkust.comp3111h.ballcraft.server.Server;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameActivity extends Activity implements SensorEventListener {
	

	private GLSurfaceView mGLView;
	private GameRenderer renderer = null;
	private SensorManager sensorManager;
	private RelativeLayout rLayout;
	
	private static TextView debugView = null;
	private static String debugMsg = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, 
    			WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        this.startService(new Intent(this, Server.class)); // start running server
        this.startService(new Intent(this, Client.class)); // start running client
		
		initLayout();
		initSensor();
		
		renderer = new GameRenderer();
		mGLView.setRenderer(renderer);
		
		setContentView(rLayout);
		
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
    
    /**
     * Initialize the main layout of the game view,
     * including buttons, MUD, etc.
     */
    private void initLayout() {
    	
		mGLView = new GLSurfaceView(getApplication());

		mGLView.setEGLConfigChooser(new GLSurfaceView.EGLConfigChooser() {
			public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
				int[] attributes = new int[] { EGL10.EGL_DEPTH_SIZE, 16, EGL10.EGL_NONE };
				EGLConfig[] configs = new EGLConfig[1];
				int[] result = new int[1];
				egl.eglChooseConfig(display, attributes, configs, 1, result);
				return configs[0];
			}
		});
		
		rLayout = new RelativeLayout(this);
		rLayout.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT));
		
		Button castBtn = new Button(this);
		castBtn.setLayoutParams(new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT));
		castBtn.setPadding(60, 50, 60, 50);
		castBtn.getBackground().setAlpha(80);
		castBtn.setText("Function 1");
		castBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/*
				if (input != null) {
					input.addSkill(new Skill(BallCraft.Skill.TEST_SKILL_1));
				}
				*/
			}
		});
		
		Button accBtn = new Button(this);
		RelativeLayout.LayoutParams accParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		accParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		accBtn.setLayoutParams(accParams);
		accBtn.setPadding(60, 50, 60, 50);
		accBtn.getBackground().setAlpha(80);
		accBtn.setText("Function 2");
		accBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/*
				input.addSkill(new Skill(BallCraft.Skill.TEST_SKILL_2));
				*/
			}
		});
		
		debugView = new TextView(this);
		RelativeLayout.LayoutParams debugParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		debugParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		debugView.setLayoutParams(debugParams);
		debugView.setPadding(10, 10, 10, 10);
		debugView.setTextColor(Color.GREEN);
		debugView.setText("Debug Message Display");
		
		rLayout.addView(mGLView);
		rLayout.addView(castBtn);
		rLayout.addView(accBtn);
		rLayout.addView(debugView);
    }
    
    private void initSensor() {
		sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
		sensorManager.registerListener(this, 
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
    }

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		Client.setInputAcceleration(event.values[SensorManager.DATA_Y], -event.values[SensorManager.DATA_X]);
	}
	
	/**
	 * Used for displaying a debug message at the bottom of the screen
	 */
	public static void displayDebugMsg() {
		debugView.setText(debugMsg);
	}
	
	private static Handler debugMsgHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			displayDebugMsg();
		}
	};
	
	public static void display(String msg) {
		debugMsg = msg;
		debugMsgHandler.sendEmptyMessage(0);
	}
	
	public static void display(float msg) {
		debugMsg = "" + msg;
		debugMsgHandler.sendEmptyMessage(0);
	}
	
	public static void display(double msg) {
		debugMsg = "" + msg;
		debugMsgHandler.sendEmptyMessage(0);
	}
	
	public static void display(int msg) {
		debugMsg = "" + msg;
		debugMsgHandler.sendEmptyMessage(0);
	}
}
