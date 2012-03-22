package hkust.comp3111h.ballcraft.ui;

import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.client.GameActivity;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainMenu extends Activity implements SensorEventListener {
	
	private Activity self;
	
	private SensorManager sensorManager;
	
	public static float turnX = 0;
	public static float turnY = 0;
	
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
		this.setContentView(R.layout.main_menu);
		
		GLSurfaceView glView = (GLSurfaceView) this.findViewById(R.id.main_menu_gl_surface_view);
		glView.setRenderer(new GameMenuRenderer(this));
		
		final ImageView settingImage = (ImageView) this.findViewById(R.id.main_menu_setting_image_view);
		settingImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				self.startActivity(new Intent(MainMenu.this, OptionMenu.class));
				self.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			}
		});
			
		final Animation rotate360 = AnimationUtils.loadAnimation(this, R.anim.rotate360);
		rotate360.setRepeatCount(0);
		settingImage.startAnimation(rotate360);
		rotate360.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationEnd(Animation animation) {
				settingImage.clearAnimation();
				settingImage.startAnimation(rotate360);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationStart(Animation animation) {
			}
		});
		
		Button singlePlayerButton = (Button) this.findViewById(R.id.main_menu_single_player_button);
		singlePlayerButton.getBackground().setAlpha(180);
		singlePlayerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				self.startActivity(new Intent(MainMenu.this, GameActivity.class));
				self.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			}
			
		});
			
		Button multiPlayerButton = (Button) this.findViewById(R.id.main_menu_multi_player_button);
		multiPlayerButton.getBackground().setAlpha(180);
		multiPlayerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				self.startActivity(new Intent(MainMenu.this, BallSelectMenu.class));
				self.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			}
			
		});
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		turnX = event.values[SensorManager.DATA_Y] / 3f;
		turnY = event.values[SensorManager.DATA_X] / 3f;
	}
}
