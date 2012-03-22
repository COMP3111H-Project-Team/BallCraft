package hkust.comp3111h.ballcraft.ui;

import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.client.GameActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class GameMenu extends Activity implements SensorEventListener {
	
	private Activity self;
	
	private RelativeLayout rLayout;
	private LinearLayout lLayout;
	private GLSurfaceView glView;
	
	private SensorManager sensorManager;
	
	public static float turnX = 0;
	public static float turnY = 0;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		self = this;
		
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, 
    			WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
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
		
		initMainMenuLayout();
		
        this.setContentView(rLayout);
	}
	
	/**
	 * Initialize main menu layout
	 */
	private void initMainMenuLayout() {
		// TextView of "BallCraft"
		ImageView titleView = new ImageView(this);
		RelativeLayout.LayoutParams tvParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		tvParams.setMargins(0, 20, 0, 0);
		titleView.setLayoutParams(tvParams);
		titleView.setImageResource(R.drawable.title);
		
		// ImageView of the setting icon
		final ImageView iv = new ImageView(this);
		RelativeLayout.LayoutParams ivParams = new RelativeLayout.LayoutParams(40, 40);
		ivParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		ivParams.setMargins(0, 10, 10, 0);
		iv.setLayoutParams(ivParams);
		iv.setImageResource(R.drawable.setting);
		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				self.startActivity(new Intent(GameMenu.this, OptionMenu.class));
			}
			
		});
		
		final Animation rotate360 = AnimationUtils.loadAnimation(this, R.anim.rotate360);
		rotate360.setRepeatCount(0);
		iv.startAnimation(rotate360);
		rotate360.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationEnd(Animation animation) {
				iv.clearAnimation();
				iv.startAnimation(rotate360);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationStart(Animation animation) {
			}
		});
		
		// LinearLayout containing the two options: single player and multi-player
		lLayout = new LinearLayout(this);
		RelativeLayout.LayoutParams lLayoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		lLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		lLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		lLayoutParams.setMargins(0, 0, 0, 10);
		lLayout.setLayoutParams(lLayoutParams);
		lLayout.setOrientation(LinearLayout.HORIZONTAL);
		
		Button singlePlayerButton = new Button(this);
		LinearLayout.LayoutParams singlePlayerBtnParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		singlePlayerBtnParams.setMargins(0, 0, 5, 0);
		singlePlayerButton.setLayoutParams(singlePlayerBtnParams);
		singlePlayerButton.setPadding(20, 70, 20, 80);
		singlePlayerButton.setText("Single Player");
		singlePlayerButton.setTextSize(30);
		singlePlayerButton.setBackgroundColor(Color.rgb(255, 255, 255));
		singlePlayerButton.getBackground().setAlpha(180);
		singlePlayerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// initSinglePlayerMenuLayout();
				self.startActivity(new Intent(GameMenu.this, GameActivity.class));
			}
			
		});
		
		Button multiPlayerButton = new Button(this);
		LinearLayout.LayoutParams multiPlayerBtnParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		multiPlayerBtnParams.setMargins(5, 0, 0, 0);
		multiPlayerButton.setLayoutParams(multiPlayerBtnParams);
		multiPlayerButton.setPadding(20, 70, 20, 80);
		multiPlayerButton.setText("Multi-Player");
		multiPlayerButton.setTextSize(30);
		multiPlayerButton.setBackgroundColor(Color.rgb(255, 255, 255));
		multiPlayerButton.getBackground().setAlpha(180);
		multiPlayerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				self.startActivity(new Intent(GameMenu.this, MultiPlayerMenu.class));
			}
			
		});
		
		lLayout.addView(singlePlayerButton);
		lLayout.addView(multiPlayerButton);
		
		rLayout.addView(titleView);
		rLayout.addView(iv);
		rLayout.addView(lLayout);
	}
	
	private void initSinglePlayerMenuLayout() {
		rLayout.removeView(lLayout);
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
