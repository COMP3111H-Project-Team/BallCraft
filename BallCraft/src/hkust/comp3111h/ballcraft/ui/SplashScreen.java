package hkust.comp3111h.ballcraft.ui;

import hkust.comp3111h.ballcraft.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends Activity {
	
	private Activity self;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		self = this;
		
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, 
    			WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
		this.setContentView(R.layout.splash_screen);
		
		handler.sendEmptyMessageDelayed(0, 3000);
	}
	
	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Intent intent = new Intent(SplashScreen.this, MainMenu.class);
			self.startActivity(intent);
			self.finish();
			self.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		}
	};
	
}
