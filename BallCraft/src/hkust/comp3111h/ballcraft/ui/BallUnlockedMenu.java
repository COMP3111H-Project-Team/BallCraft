package hkust.comp3111h.ballcraft.ui;

import hkust.comp3111h.MyApplication;
import hkust.comp3111h.ballcraft.BallDef;
import hkust.comp3111h.ballcraft.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class BallUnlockedMenu extends Activity {

    private BallUnlockedMenu self;
    
    public static final String unlockedIndicator = "ballUnlocked";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        self = this;

        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.setContentView(R.layout.ball_unlock_menu);

        GLSurfaceView glView = (GLSurfaceView) this
                .findViewById(R.id.ball_unlockl_menu_gl_surface_view);
        glView.setRenderer(new GameMenuRenderer(this));
        
        this.initLayout();
    }
    
    private void initLayout() {
        Intent intent = self.getIntent();
        int unlockedBallId = intent.getIntExtra(unlockedIndicator, 0);
        
        TextView titleView = (TextView) self.findViewById(R.id.ball_unlock_menu_title_view);
        titleView.setTypeface(MyApplication.getFont());
        
        TextView subTitleView = (TextView) self.findViewById(R.id.ball_unlock_menu_subtitle_view);
        subTitleView.setTypeface(MyApplication.getFont());
        
        ImageView ballImageView = (ImageView) self.findViewById(R.id.ball_unlock_menu_ball_image_view);
        ballImageView.setImageResource(BallDef.getBallImageResourceById(unlockedBallId));
        ballImageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                self.finish();
	            self.overridePendingTransition(android.R.anim.fade_in,
	                    android.R.anim.fade_out);
            }
            
        });
        
        TextView ballNameView = (TextView) self.findViewById(R.id.ball_unlock_menu_ball_name_view);
        ballNameView.setTypeface(MyApplication.getFont());
        ballNameView.setText(BallDef.getBallNameById(unlockedBallId) + " !!!");
        int [] RGB = BallDef.getRGBById(unlockedBallId);
        ballNameView.setTextColor(Color.rgb(RGB[0], RGB[1], RGB[2]));
    }

    @Override
    public void onBackPressed() {
        // do nothing
    }

}
