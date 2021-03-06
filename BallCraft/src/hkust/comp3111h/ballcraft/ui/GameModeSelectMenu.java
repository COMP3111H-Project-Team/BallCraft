package hkust.comp3111h.ballcraft.ui;

import hkust.comp3111h.ballcraft.BallDef;
import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.client.GameInitializer;
import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

public class GameModeSelectMenu extends Activity {

    private GameModeSelectMenu self;
    
    private RadioButton limitedTimeRadioButton;
    private RadioButton limitedScoreRadioButton;
    
    private RelativeLayout timeSelectLayout;
    private RadioButton time1MinRadioButton;
    private RadioButton time3MinRadioButton;
    private RadioButton time5MinRadioButton;
    private RadioButton time10MinRadioButton;
    
    private RelativeLayout scoreSelectLayout;
    private RadioButton score10RadioButton;
    private RadioButton score30RadioButton;
    private RadioButton score50RadioButton;
    private RadioButton score100RadioButton;
    
    private ImageView selectButton;
    
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

        this.setContentView(R.layout.game_mode_select_menu);

        GLSurfaceView glView = (GLSurfaceView) this
                .findViewById(R.id.game_mode_select_menu_gl_surface_view);
        glView.setRenderer(new GameMenuRenderer(this));

        self.initLayout();
    }
    
    private void initLayout() {
        self.limitedTimeRadioButton = 
                (RadioButton) self.findViewById(R.id.game_mode_type_limited_time);
       
        self.limitedScoreRadioButton = 
                (RadioButton) self.findViewById(R.id.game_mode_type_limited_score);
        
        self.timeSelectLayout = 
                (RelativeLayout) self.findViewById(R.id.game_mode_time_layout);
        
        self.time1MinRadioButton = 
                (RadioButton) self.findViewById(R.id.game_mode_time_1_min);
        
        self.time3MinRadioButton = 
                (RadioButton) self.findViewById(R.id.game_mode_time_3_min);
        
        self.time5MinRadioButton = 
                (RadioButton) self.findViewById(R.id.game_mode_time_5_min);
        
        self.time10MinRadioButton = 
                (RadioButton) self.findViewById(R.id.game_mode_time_10_min);
         
        self.scoreSelectLayout = 
                (RelativeLayout) self.findViewById(R.id.game_mode_score_layout);
        
        self.score10RadioButton = 
                (RadioButton) self.findViewById(R.id.game_mode_score_10);
        
        self.score30RadioButton = 
                (RadioButton) self.findViewById(R.id.game_mode_score_30);
        
        self.score50RadioButton = 
                (RadioButton) self.findViewById(R.id.game_mode_score_50);
        
        self.score100RadioButton = 
                (RadioButton) self.findViewById(R.id.game_mode_score_100);
        
        self.selectButton = 
                (ImageView) self.findViewById(R.id.game_mode_select_view);
        
        
        self.scoreSelectLayout.setVisibility(View.INVISIBLE);
  
        limitedTimeRadioButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                self.timeSelectLayout.setVisibility(View.VISIBLE);
                self.scoreSelectLayout.setVisibility(View.INVISIBLE);
            }
        
        });
         
        limitedScoreRadioButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                self.timeSelectLayout.setVisibility(View.INVISIBLE);
                self.scoreSelectLayout.setVisibility(View.VISIBLE);
            }
        
        });
        
        self.selectButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(self, GameInitializer.class);
                
                if (self.limitedTimeRadioButton.isChecked()) {
                    intent.putExtra("GAME_MODE", "LIMITED_TIME");
                    if (self.time1MinRadioButton.isChecked()) {
                        intent.putExtra("TIME_LIMIT", 1);
                    } else if (self.time3MinRadioButton.isChecked()) {
                        intent.putExtra("TIME_LIMIT", 3);
                    } else if (self.time5MinRadioButton.isChecked()) {
                        intent.putExtra("TIME_LIMIT", 5);
                    } else if (self.time10MinRadioButton.isChecked()) {
                        intent.putExtra("TIME_LIMIT", 10);
                    }
                } else if (self.limitedScoreRadioButton.isChecked()) {
                    intent.putExtra("GAME_MODE", "LIMITED_SCORE");
                    if (self.score10RadioButton.isChecked()) {
                        intent.putExtra("SCORE_LIMIT", 10);
                    } else if (self.score30RadioButton.isChecked()) {
                        intent.putExtra("SCORE_LIMIT", 30);
                    } else if (self.score50RadioButton.isChecked()) {
                        intent.putExtra("SCORE_LIMIT", 50);
                    } else if (self.score100RadioButton.isChecked()) {
                        intent.putExtra("SCORE_LIMIT", 100);
                    }
                }
                
                intent.putExtra("ballSelected", 
                        self.getIntent().getIntExtra("ballSelected", BallDef.WoodBall.id));
                intent.putExtra("mapSelected", 
                        self.getIntent().getStringExtra("mapSelected"));
                
                self.startActivity(intent);
                self.overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                self.finish();
            }
            
        });
        
    }
    
}
