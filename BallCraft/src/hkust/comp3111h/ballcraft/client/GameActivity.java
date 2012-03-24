package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.graphics.GameRenderer;
import hkust.comp3111h.ballcraft.graphics.MiniMapView;
import hkust.comp3111h.ballcraft.server.Server;
import android.app.Activity;
import android.content.Intent;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends Activity implements SensorEventListener {
	

	private GLSurfaceView mGLView;
	private SensorManager sensorManager;
	
	private MiniMapView miniMap;
	
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
		
        MapParser.setContext(this);
        
		initLayout();
		initSensor();
    }
    

    /**
     * Initialize the main layout of the game view,
     * including buttons, MUD, etc.
     */
    private void initLayout() {
    	
		this.setContentView(R.layout.game_layout);
		
		mGLView = (GLSurfaceView) this.findViewById(R.id.game_activity_gl_surface_view);
		mGLView.setRenderer(new GameRenderer());
		
		
		Button skill1Button = (Button) this.findViewById(R.id.game_activity_skill_1_button);
		skill1Button.getBackground().setAlpha(80);
		skill1Button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
			
		Button skill2Button = (Button) this.findViewById(R.id.game_activity_skill_2_button);
		skill2Button.getBackground().setAlpha(80);
		skill2Button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		
		debugView = (TextView) this.findViewById(R.id.game_activity_debug_view);
		
		miniMap = (MiniMapView) this.findViewById(R.id.game_activity_mini_map_view);
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
		Client.setInputAcceleration(
				event.values[SensorManager.DATA_Y] * 5, 
				-event.values[SensorManager.DATA_X] * 5);
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
