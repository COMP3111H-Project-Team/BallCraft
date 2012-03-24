package hkust.comp3111h.ballcraft.ui;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.R;
import android.app.Activity;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BallSelectMenu extends Activity {
	
	private BallSelectMenu self;
	
	private int currBallPos = 0;
	
	private RelativeLayout ballDisplayView;
	
	private TextView ballNameView;
	private TextView ballDescriptionView;
	private ImageView ballImageView;
	private ImageView ballLockedView;
	private TextView ballMassView;
	private TextView ballFrictionView;
	private TextView ballMagicView;
	
	private TextView prevBallView;
	private TextView nextBallView;
	
	private Animation fadeOut;
	private Animation fadeIn;
	
	private boolean init = true;
	
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
		
		this.initLayout();
		
	}
	
	private void initLayout() {
		ballDisplayView = (RelativeLayout) this.findViewById(R.id.ball_select_item_layout);
		
		ballNameView = (TextView) this.findViewById(R.id.ball_select_item_name_display);
		ballDescriptionView = (TextView) this.findViewById(R.id.ball_select_item_description_display);
	
		ballImageView = (ImageView) this.findViewById(R.id.ball_select_item_image);
		ballLockedView = (ImageView) this.findViewById(R.id.ball_select_item_locked_image);
	
		ballMassView = (TextView) this.findViewById(R.id.ball_select_item_mass_display);
		ballFrictionView = (TextView) this.findViewById(R.id.ball_select_item_friction_display);
		ballMagicView = (TextView) this.findViewById(R.id.ball_select_item_magic_display);
		
		prevBallView = (TextView) this.findViewById(R.id.ball_select_item_previous);
		prevBallView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (self.canGoPrev()) {
					currBallPos--;
					self.updateLayout();
				}
			}
			
		});
		
		nextBallView = (TextView) this.findViewById(R.id.ball_select_item_next);
		nextBallView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (self.canGoNext()) {
					currBallPos++;
					self.updateLayout();
				}
			}
		});
		
		fadeOut = AnimationUtils.loadAnimation(self, R.anim.fade_out);
		fadeIn = AnimationUtils.loadAnimation(self, R.anim.fade_in);
		
		this.updateLayout();
	}
	
	private boolean canGoPrev() {
		return currBallPos != 0;
	}
	
	private boolean canGoNext() {
		return currBallPos != BallCraft.balls.length - 1;
	}
	
	private void updateLayout() {
		if (init) {
			init = false;
			self.refreshBallDispaly();
		} else {
			ballDisplayView.startAnimation(fadeOut);
			fadeOut.setAnimationListener(new AnimationListener() {
	
				@Override
				public void onAnimationEnd(Animation animation) {
					self.refreshBallDispaly();
				}
	
				@Override
				public void onAnimationRepeat(Animation animation) {
					
				}
	
				@Override
				public void onAnimationStart(Animation animation) {
					
				}
				
			});
		}
		
	}
	
	private void refreshBallDispaly() {
		// ballDisplayView.startAnimation(fadeIn);
		
		ballNameView.setText(BallCraft.getBallNameById(currBallPos));
		ballDescriptionView.setText(BallCraft.getBallDescriptionById(currBallPos));
		
		ballImageView.setImageResource(BallCraft.getBallImageResourceById(currBallPos));
		ballMassView.setText("Mass " 
				+ self.getValueDisplayFromInt(BallCraft.getBallMassById(currBallPos)));
		ballFrictionView.setText("Friction " 
				+ self.getValueDisplayFromInt(BallCraft.getBallFrictionById(currBallPos)));
		ballMagicView.setText("Magic " 
				+ self.getValueDisplayFromInt(BallCraft.getBallMagicById(currBallPos)));
		
		if (BallCraft.ballUnlocked(currBallPos)) {
			ballImageView.setAlpha(200);
			ballLockedView.setVisibility(View.INVISIBLE);
		} else { // the ball is not unlocked yet
			ballImageView.setAlpha(80);
			ballLockedView.setVisibility(View.VISIBLE);
		}
		
		if (self.canGoPrev()) {
			prevBallView.setTextColor(Color.WHITE);
		} else {
			prevBallView.setTextColor(Color.rgb(60, 60, 60));
		}
		
		if (self.canGoNext()) {
			nextBallView.setTextColor(Color.WHITE);
		} else {
			nextBallView.setTextColor(Color.rgb(60, 60, 60));
		}
	}
	
	private String getValueDisplayFromInt(int value) {
		String display = "";
		for (int i = 0; i < value; i++) {
			display += "+";
		}
		return display;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		self.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
    
}
