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
import android.widget.TextView;

public class MainMenu extends Activity {
	
	/*
	private GLSurfaceView mGLView = null;
	
	private MainMenuRenderer renderer = null;
	
	private float xPos = -1;
	private float yPos = -1;
	
	public static float touchTurn = 0;
	public static float touchTurnUp = 0;
	*/

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, 
    			WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    	
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        this.setContentView(R.layout.main_menu);
        
        TextView singlePlayerText = (TextView) this.findViewById(R.id.main_menu_single_player);
        singlePlayerText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainMenu.this, GameActivity.class);
				startActivity(intent);
			}
        });
		
        /*
		mGLView = new GLSurfaceView(this);
		mGLView.setEGLConfigChooser(new GLSurfaceView.EGLConfigChooser() {
			public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
				int[] attributes = new int[] { EGL10.EGL_DEPTH_SIZE, 16, EGL10.EGL_NONE };
				EGLConfig[] configs = new EGLConfig[1];
				int[] result = new int[1];
				egl.eglChooseConfig(display, attributes, configs, 1, result);
				return configs[0];
			}
		});
		
		renderer = new MainMenuRenderer(this);
		mGLView.setRenderer(renderer);
		
		this.setContentView(mGLView);
		*/
	}
	
	/*
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			xPos = event.getX();
			yPos = event.getY();
			return true;
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			xPos = -1;
			yPos = -1;
			touchTurn = 0;
			touchTurnUp = 0;
			return true;
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			float xd = event.getX() - xPos;
			float yd = event.getY() - yPos;
			
			xPos = event.getX();
			yPos = event.getY();
			
			touchTurn = xd / 3f;
			touchTurnUp = yd / 3f;
			
			return true;
		}
		
		try {
			Thread.sleep(15);
		} catch (Exception e) {
		}
		
		return super.onTouchEvent(event);
	}
	*/
}
