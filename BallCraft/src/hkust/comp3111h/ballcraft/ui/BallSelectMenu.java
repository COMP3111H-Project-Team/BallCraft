package hkust.comp3111h.ballcraft.ui;

import hkust.comp3111h.MyApplication;
import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.BallDef;
import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.client.GameInitializer;
import hkust.comp3111h.ballcraft.server.ServerAdapter;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BallSelectMenu extends Activity {

    private BallSelectMenu self;

    private int currBallPos = 0;

    private TextView ballNameView;
    private TextView ballDescriptionView;
    private ImageView ballImageView;
    private ImageView ballSelectView;
    private ImageView ballLockedView;
    
    private RelativeLayout ballMassLayout;
    private RelativeLayout ballFrictionLayout;
    private RelativeLayout ballMagicLayout;
    
    private ValueDisplayView ballMassView;
    private ValueDisplayView ballFrictionView;
    private ValueDisplayView ballMagicView;

    private ImageView prevBallView;
    private ImageView nextBallView;

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

        this.setContentView(R.layout.ball_select_menu);

        GLSurfaceView glView = (GLSurfaceView) this
                .findViewById(R.id.ball_select_menu_gl_surface_view);
        glView.setRenderer(new GameMenuRenderer(this));

        this.initLayout();

    }

    private void initLayout() {
        ballNameView = (TextView) this
                .findViewById(R.id.ball_select_item_name_display);
        ballNameView.setTypeface(MyApplication.getFont());
        ballDescriptionView = (TextView) this
                .findViewById(R.id.ball_select_item_description_display);
        ballDescriptionView.setTypeface(MyApplication.getFont());

        ballImageView = (ImageView) this
                .findViewById(R.id.ball_select_item_image);
        ballSelectView = (ImageView) this
                .findViewById(R.id.ball_select_item_select_view);
        ballLockedView = (ImageView) this
                .findViewById(R.id.ball_select_item_locked_image);
        
        ballMassLayout = (RelativeLayout) this
                .findViewById(R.id.ball_select_item_mass_layout);
        ballFrictionLayout = (RelativeLayout) this
                .findViewById(R.id.ball_select_item_friction_layout);
        ballMagicLayout = (RelativeLayout) this
                .findViewById(R.id.ball_select_item_magic_layout);

        ballMassView = (ValueDisplayView) this
                .findViewById(R.id.ball_select_item_mass_value);
        ballFrictionView = (ValueDisplayView) this
                .findViewById(R.id.ball_select_item_friction_value);
        ballMagicView = (ValueDisplayView) this
                .findViewById(R.id.ball_select_item_magic_value);

        prevBallView = (ImageView) this
                .findViewById(R.id.ball_select_item_previous);
        prevBallView.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                if (self.canGoPrev()) {
                    currBallPos--;
                    self.updateLayout();
                }
            }

        });

        nextBallView = (ImageView) this.findViewById(R.id.ball_select_item_next);
        nextBallView.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                if (self.canGoNext()) {
                    currBallPos++;
                    self.updateLayout();
                }
            }
        });

        this.updateLayout();
    }

    private boolean canGoPrev() {
        return currBallPos != 0;
    }

    private boolean canGoNext() {
        return currBallPos != BallDef.balls.length - 1;
    }

    private void updateLayout() {
        /*
        if (init) {
            init = false;
            self.refreshBallDispaly();
        } else {
            ballDisplayView.startAnimation(fadeOut);
            fadeOut.setAnimationListener(new AnimationListener() {

                public void onAnimationEnd(Animation animation) {
                    self.refreshBallDispaly();
                }

                public void onAnimationRepeat(Animation animation) {

                }

                public void onAnimationStart(Animation animation) {

                }

            });
        }
        */
        self.refreshBallDispaly();

        if (BallDef.ballUnlocked(currBallPos)) {
            ballImageView.setAlpha(200);
            ballLockedView.setVisibility(View.INVISIBLE);
            ballMassLayout.setVisibility(View.VISIBLE);
            ballFrictionLayout.setVisibility(View.VISIBLE);
            ballMagicLayout.setVisibility(View.VISIBLE);
        } else { // the ball is not unlocked yet
            ballImageView.setAlpha(80);
            ballLockedView.setVisibility(View.VISIBLE);
            ballMassLayout.setVisibility(View.INVISIBLE);
            ballFrictionLayout.setVisibility(View.INVISIBLE);
            ballMagicLayout.setVisibility(View.INVISIBLE);
        }
        
        if (self.canGoPrev()) {
            // prevBallView.setTextColor(Color.WHITE);
            prevBallView.setColorFilter(Color.RED);
        } else {
            // prevBallView.setTextColor(Color.rgb(60, 60, 60));
            prevBallView.setColorFilter(Color.BLACK);
        }

        if (self.canGoNext()) {
            // nextBallView.setTextColor(Color.WHITE);
            nextBallView.setColorFilter(Color.RED);
        } else {
            // nextBallView.setTextColor(Color.rgb(60, 60, 60));
            nextBallView.setColorFilter(Color.BLACK);
        }
    }

    private void refreshBallDispaly() {
        // ballDisplayView.startAnimation(fadeIn);

        ballNameView.setText(BallDef.getBallNameById(currBallPos));
        ballDescriptionView.setText(BallDef
                .getBallDescriptionById(currBallPos));

        ballImageView.setImageResource(BallDef
                .getBallImageResourceById(currBallPos));
        
        ballMassView.setDisplayValue(BallDef.getBallMassById(currBallPos));
        
        ballFrictionView.setDisplayValue(BallDef.getBallFrictionById(currBallPos));
        
        ballMagicView.setDisplayValue(BallDef.getBallMagicById(currBallPos));
        
        /*
        ballMassView.setText("Mass "
                + self.getValueDisplayFromInt(BallDef
                        .getBallMassById(currBallPos)));
        ballFrictionView.setText("Friction "
                + self.getValueDisplayFromInt(BallDef
                        .getBallFrictionById(currBallPos)));
        ballMagicView.setText("Magic "
                + self.getValueDisplayFromInt(BallDef
                        .getBallMagicById(currBallPos)));
        */

        if (BallDef.ballUnlocked(currBallPos)) {
            ballImageView.setAlpha(200);
            ballSelectView.setVisibility(View.VISIBLE);
            ballLockedView.setVisibility(View.INVISIBLE);
            ballSelectView.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    Intent intent;
                    if (BallCraft.isServer)
                    {
                    	intent = new Intent(self, MapSelectMenu.class);
                    }
                    else
                    {
                    	intent = new Intent(self, GameInitializer.class);    
                    	ServerAdapter.sendClientInitMsg("" + currBallPos);
		                self.overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                    }
                    intent.putExtra("ballSelected", currBallPos);
                    self.startActivity(intent);
                    self.overridePendingTransition(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    BallSelectMenu.this.finish();
                }

            });
            
        } else { // the ball is not unlocked yet
            ballImageView.setAlpha(80);
            ballSelectView.setVisibility(View.INVISIBLE);
            ballLockedView.setVisibility(View.VISIBLE);
            ballLockedView.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    // do nothing when locked
                }
                
            });

        }

 
        if (self.canGoPrev()) {
            // prevBallView.setTextColor(Color.WHITE);
            prevBallView.setColorFilter(Color.RED);
        } else {
            // prevBallView.setTextColor(Color.rgb(60, 60, 60));
            prevBallView.setColorFilter(Color.BLACK);
        }

        if (self.canGoNext()) {
            // nextBallView.setTextColor(Color.WHITE);
            nextBallView.setColorFilter(Color.RED);
        } else {
            // nextBallView.setTextColor(Color.rgb(60, 60, 60));
            nextBallView.setColorFilter(Color.BLACK);
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        self.overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        return null;
    }

}
