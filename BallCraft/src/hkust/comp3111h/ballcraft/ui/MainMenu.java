package hkust.comp3111h.ballcraft.ui;

import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.client.GameActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainMenu extends Activity {
	
	private Activity self;
	
	private RelativeLayout rLayout;
	private GLSurfaceView glView;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		self = this;
		
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, 
    			WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        
        this.initLayout();
        
        // this.setContentView(R.layout.main_menu);
        
        // initTextButtons();
	}
	
	private void initLayout() {
		// RelativeLayout, the main layout of the activity
		rLayout = new RelativeLayout(this);
		rLayout.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT));
		
		// glView
        glView = new GLSurfaceView(this);
        glView.setRenderer(new MainMenuRenderer(this));
		RelativeLayout.LayoutParams glvParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.FILL_PARENT);
		glView.setLayoutParams(glvParams);
		
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
		ImageView iv = new ImageView(this);
		RelativeLayout.LayoutParams ivParams = new RelativeLayout.LayoutParams(24, 24);
		ivParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		ivParams.setMargins(0, 10, 10, 0);
		iv.setLayoutParams(ivParams);
		iv.setImageResource(R.drawable.settings_button);
		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				self.startActivity(new Intent(MainMenu.this, OptionMenu.class));
			}
			
		});
		
		// LinearLayout containing the two options: single player and multi-player
		LinearLayout lLayout = new LinearLayout(this);
		RelativeLayout.LayoutParams lLayoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		lLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		lLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		lLayoutParams.setMargins(0, 0, 0, 20);
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
		singlePlayerButton.setBackgroundColor(Color.rgb(0, 238, 0));
		singlePlayerButton.getBackground().setAlpha(180);
		singlePlayerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				self.startActivity(new Intent(MainMenu.this, GameActivity.class));
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
		multiPlayerButton.setBackgroundColor(Color.rgb(0, 0, 238));
		multiPlayerButton.getBackground().setAlpha(180);
		multiPlayerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				self.startActivity(new Intent(MainMenu.this, MultiPlayerMenu.class));
			}
			
		});
		
		lLayout.addView(singlePlayerButton);
		lLayout.addView(multiPlayerButton);
		
		rLayout.addView(glView);
		rLayout.addView(titleView);
		rLayout.addView(iv);
		rLayout.addView(lLayout);
		
        this.setContentView(rLayout);
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
