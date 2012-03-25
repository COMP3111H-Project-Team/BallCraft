package hkust.comp3111h.ballcraft.ui;

import hkust.comp3111h.ballcraft.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreen extends Activity {
	
	private Activity self;

	 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		self = this;
		
    	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, 
    			WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		this.setContentView(R.layout.splash_screen);
		
		final TextView tView = (TextView) this.findViewById(R.id.splash_text_view);
		Animation zoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
		
		zoomIn.setAnimationListener(new AnimationListener() {
			 
			public void onAnimationEnd(Animation animation) {
				Animation keepRotateAngle = AnimationUtils.loadAnimation(self, R.anim.keep_rotate_angle);
				tView.startAnimation(keepRotateAngle);
				keepRotateAngle.setAnimationListener(new AnimationListener() {
					 
					public void onAnimationEnd(Animation animation) {
						Animation rotateBack = AnimationUtils.loadAnimation(self, R.anim.rotate_back);
						tView.startAnimation(rotateBack);
						rotateBack.setAnimationListener(new AnimationListener() {
							 
							public void onAnimationEnd(Animation animation) {
								Animation rotateForth = AnimationUtils.loadAnimation(self, R.anim.rotate_forth);
								tView.startAnimation(rotateForth);
							}

							 
							public void onAnimationRepeat(Animation animation) {
							}
							
							 
							public void onAnimationStart(Animation animation) {
							}
						});
					}

					 
					public void onAnimationRepeat(Animation animation) {
					}

					 
					public void onAnimationStart(Animation animation) {
					}
				});
			}
			 
			public void onAnimationRepeat(Animation animation) {
			}
			
			 
			public void onAnimationStart(Animation animation) {
			}
		});
		tView.startAnimation(zoomIn);
		
		handler.sendEmptyMessageDelayed(0, 500);
	}
	
	public Handler handler = new Handler() {
		 
		public void handleMessage(Message msg) {
			Intent intent = new Intent(SplashScreen.this, MainMenu.class);
			self.startActivity(intent);
			self.finish();
			self.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		}
	};
	
}
