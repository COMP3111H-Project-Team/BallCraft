package hkust.comp3111h.ballcraft.ui;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.R;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

public class BallSelectMenu extends Activity {
	
	private Activity self;
	
	@Override
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
		
		Gallery ballSelectGallery = (Gallery) this.findViewById(R.id.ball_select_menu_gallery);
		ballSelectGallery.setAdapter(new BallSelectAdapter());
	}

    /**
     * Used to set the content of the gallery
     */
    class BallSelectAdapter extends BaseAdapter {
    	
    	private LayoutInflater inflater;
    	
    	public BallSelectAdapter() {
    		inflater = self.getLayoutInflater();
    	}

		@Override
		public int getCount() {
			return BallCraft.balls.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = inflater.inflate(R.layout.ball_select_item, null);
			TextView ballNameView = (TextView) v.findViewById(R.id.ball_select_item_name_display);
			TextView ballDescriptionView = (TextView) v.findViewById(R.id.ball_select_item_description_display);
			
			ImageView ballImageView = (ImageView) v.findViewById(R.id.ball_select_item_image);
			ImageView ballLockedView = (ImageView) v.findViewById(R.id.ball_select_item_locked_image);
			
			TextView ballMassView = (TextView) v.findViewById(R.id.ball_select_item_mass_display);
			TextView ballFrictionView = (TextView) v.findViewById(R.id.ball_select_item_friction_display);
			TextView ballMagicView = (TextView) v.findViewById(R.id.ball_select_item_magic_display);
			
			ballNameView.setText(BallCraft.getBallNameById(position));
			ballDescriptionView.setText(BallCraft.getBallDescriptionById(position));
			
			ballImageView.setImageResource(BallCraft.getBallImageResourceById(position));
			ballMassView.setText("Mass " 
					+ this.getValueDisplayFromInt(BallCraft.getBallMassById(position)));
			ballFrictionView.setText("Friction " 
					+ this.getValueDisplayFromInt(BallCraft.getBallFrictionById(position)));
			ballMagicView.setText("Magic " 
					+ this.getValueDisplayFromInt(BallCraft.getBallMagicById(position)));
			
			if (BallCraft.ballUnlocked(position)) {
				ballImageView.setAlpha(200);
				ballLockedView.setVisibility(View.INVISIBLE);
			} else { // the ball is not unlocked yet
				ballImageView.setAlpha(80);
				ballLockedView.setAlpha(180);
			}
			
			return v;
		}
		
		private String getValueDisplayFromInt(int value) {
			String display = "";
			for (int i = 0; i < value; i++) {
				display += "+";
			}
			return display;
		}
    }

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		self.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
    
}
