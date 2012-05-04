package hkust.comp3111h.ballcraft.ui;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.client.GameInitializer;
import hkust.comp3111h.ballcraft.data.GameData;
import hkust.comp3111h.ballcraft.server.bt.BluetoothActivity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainMenu extends Activity implements SensorEventListener {

    public static MainMenu self;

    private SensorManager sensorManager;

    public static float turnX = 0;
    public static float turnY = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        self = this;

        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
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

        GLSurfaceView glView = (GLSurfaceView) this
                .findViewById(R.id.main_menu_gl_surface_view);
        glView.setRenderer(new GameMenuRenderer(this));

        final ImageView settingImage = (ImageView) this
                .findViewById(R.id.main_menu_setting_image_view);
        settingImage.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                self.startActivity(new Intent(MainMenu.this, OptionMenu.class));
                self.overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
            }

        });

        final Animation rotate360 = AnimationUtils.loadAnimation(this,
                R.anim.rotate360);
        rotate360.setRepeatCount(0);
        settingImage.startAnimation(rotate360);
        rotate360.setAnimationListener(new AnimationListener() {

            public void onAnimationEnd(Animation animation) {
                settingImage.clearAnimation();
                settingImage.startAnimation(rotate360);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }

        });
        
        Animation opacityChange1 = AnimationUtils.loadAnimation(self, R.anim.opacity_change_1);
        Animation opacityChange2 = AnimationUtils.loadAnimation(self, R.anim.opacity_change_2);
        
        final ImageView startGameButton = (ImageView) this
                .findViewById(R.id.main_menu_start_game_image_view);
        startGameButton.setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {
			    if (GameData.isUsernameSet()) {
	                Intent Bluetooth = new Intent(MainMenu.this, BluetoothActivity.class);
	                MainMenu.this.startActivity(Bluetooth);
	                BallCraft.maxPlayer = 2;
	                self.overridePendingTransition(android.R.anim.fade_in,
	                        android.R.anim.fade_out);
                } else {
                    Intent intent = new Intent(MainMenu.this, UsernameMenu.class);
                    intent.putExtra("start_multiplayer_game", true);
                    self.startActivity(intent);
	                self.overridePendingTransition(android.R.anim.fade_in,
	                        android.R.anim.fade_out);
                }
            }
            
        });
        
        startGameButton.startAnimation(opacityChange1);
        
        final ImageView profileButton = (ImageView) this
                .findViewById(R.id.main_menu_profile_image_view);
        profileButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(self, ProfileMenu.class);
                self.startActivity(intent);
                self.overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
            }
            
        });
        
        profileButton.startAnimation(opacityChange2);
        
        /*
        final ImageView aboutButton = (ImageView) this
                .findViewById(R.id.main_menu_about_image_view);
        aboutButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(self, AboutMenu.class);
                self.startActivity(intent);
                self.overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
            }
            
        });
        
        aboutButton.startAnimation(opacityChange1);
        */
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        turnX = event.values[SensorManager.DATA_Y] / 2f;
        turnY = event.values[SensorManager.DATA_X] / 2f;
    }
    
 	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
 	    menu.clear();
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		
		return true;
	}
 	
 	// for debugging only!
 	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
 	    if (item.getItemId() == R.id.crash) {
 	        ArrayList<Integer> list = new ArrayList<Integer>();
 	        list.get(0);
 	    } else if (item.getItemId() == R.id.test) {
            BallCraft.maxPlayer = 1;
            Intent intent = new Intent(self,
                    GameInitializer.class);
            intent.putExtra("ballSelected", BallCraft.Ball.WATER_BALL);
            intent.putExtra("mapSelected", "map02.xml");
            
            self.startActivity(intent);
            self.overridePendingTransition(android.R.anim.fade_in,
                    android.R.anim.fade_out);
 	        /*
 	        Intent intent = new Intent(self, BallUnlockedMenu.class);
 	        intent.putExtra(BallUnlockedMenu.unlockedIndicator, 2);
 	        self.startActivity(intent);
            */
 	    }
        return super.onOptionsItemSelected(item);
    }
 	
}
