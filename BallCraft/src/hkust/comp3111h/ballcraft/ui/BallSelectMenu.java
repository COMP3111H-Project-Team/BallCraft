package hkust.comp3111h.ballcraft.ui;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class BallSelectMenu extends Activity implements SensorEventListener {
	
	private Activity self;
	
	private RelativeLayout rLayout;
	private GLSurfaceView glView;
	
	private SensorManager sensorManager;
	
	public static float turnX = 0;
	public static float turnY = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		self = this;
		
    	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, 
    			WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        this.initSensor();
        this.initLayout();
	}
	
    private void initSensor() {
		sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
		sensorManager.registerListener(this, 
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
    }
    
    private void initLayout() {
		// RelativeLayout, the main layout of the activity
		rLayout = new RelativeLayout(this);
		rLayout.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT));
		
		// glView
        glView = new GLSurfaceView(this);
        glView.setRenderer(new GameMenuRenderer(this));
		RelativeLayout.LayoutParams glvParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.FILL_PARENT);
		glView.setLayoutParams(glvParams);
		
		rLayout.addView(glView);
		
		this.setContentView(rLayout);
    }

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		turnX = event.values[SensorManager.DATA_Y] / 3f;
		turnY = event.values[SensorManager.DATA_X] / 3f;
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		self.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
    
}
