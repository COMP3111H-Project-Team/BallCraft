package hkust.comp3111h.ballcraft.ui;

import hkust.comp3111h.ballcraft.R;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;

public class BallSelectMenu extends Activity {
	
	private Activity self;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		self = this;
		
    	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, 
    			WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		this.setContentView(R.layout.ball_select_menu);
		
		GLSurfaceView glView = (GLSurfaceView) this.findViewById(R.id.ball_select_menu_gl_surface_view);
		glView.setRenderer(new GameMenuRenderer(this));
	}

    /**
     * Used to set the content of the gallery
     */
    class BallSelectAdapter extends BaseAdapter {

		
		public int getCount() {
			return 1;
		}

		
		public Object getItem(int position) {
			return null;
		}

		
		public long getItemId(int position) {
			return 0;
		}

		
		public View getView(int position, View convertView, ViewGroup parent) {
			return null;
		}
    }

	
	public void onBackPressed() {
		super.onBackPressed();
		self.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
    
}
