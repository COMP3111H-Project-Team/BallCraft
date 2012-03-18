package hkust.comp3111h.ballcraft.ui;

import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.client.GameActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainMenu extends Activity {
	
	private Activity self;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		self = this;
		
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, 
    			WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        this.setContentView(R.layout.main_menu);
        
        initTextButtons();
	}
	
	private void initTextButtons() {
		ImageView singlePlayerImg = (ImageView) this.findViewById(R.id.main_menu_single_player_text);
		singlePlayerImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainMenu.this, GameActivity.class);
				self.startActivity(intent);
			}
		});
		
		ImageView multiPlayerImg = (ImageView) this.findViewById(R.id.main_menu_multi_player_text);
		multiPlayerImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainMenu.this, MultiPlayerMenu.class);
				self.startActivity(intent);
			}
		});
		
		ImageView optionImg = (ImageView) this.findViewById(R.id.main_menu_option_text);
		optionImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainMenu.this, OptionMenu.class);
				self.startActivity(intent);
			}
		});
	}
}
