package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.MyApplication;
import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.BallDef;
import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.SkillDef;
import hkust.comp3111h.ballcraft.data.GameData;
import hkust.comp3111h.ballcraft.graphics.GameRenderer;
import hkust.comp3111h.ballcraft.server.Server;
import hkust.comp3111h.ballcraft.server.ServerAdapter;
import hkust.comp3111h.ballcraft.server.ServerGameState;
import hkust.comp3111h.ballcraft.skills.Skill;
import hkust.comp3111h.ballcraft.ui.BallUnlockedMenu;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameActivity extends Activity implements SensorEventListener {
	//Debug
	public final String TAG = "gameActivity";
	public final boolean D = true;

    private static GameActivity self;
    
    private GLSurfaceView mGLView;
    private SensorManager sensorManager;
    
    // private static RelativeLayout flashBangMask;

    private static RelativeLayout backScreen;
    
    private static RelativeLayout endGameLayout;
    
    private GameRenderer renderer;
    
    private static TextView loseView = null;
    
    private static Button skill1Button;
    private static Button skill2Button;
    
    private static TextView remainingTimeView;
    private static TextView scoreView;

    private static boolean skill1CooledDown = true;
    private static boolean skill2CooledDown = true;
    
    private static int skill1;
    private static int skill2;
    
    private static TextView pauseButton;
    private static TextView resumeButton;
    private static TextView exitButton;

    private static boolean dead = false;
    
    private static int finalScore;
    
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

        this.initLayout();
        this.initSensor();
    }

    /**
     * Initialize the main layout of the game view, including buttons, MUD, etc.
     */
    private void initLayout() {

        this.setContentView(R.layout.game_layout);

        mGLView = (GLSurfaceView) this.findViewById(R.id.game_activity_gl_surface_view);
        
        // here we need to wait for the init data to be sent from the server to create the render
        // and start drawing
        while (!Client.isGameInited()) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
        
        self.renderer = new GameRenderer(this);
        mGLView.setRenderer(self.renderer);
        GameRenderer.startRendering();
        
        /*
        TextView statusDisplay = (TextView) this.findViewById(R.id.game_activity_status_dispaly);
        statusDisplay.setTypeface(MyApplication.getFont());
        statusDisplay.getBackground().setAlpha(200);
        */
        
        int ballSelected = self.getIntent().getIntExtra("ballSelected", BallCraft.Ball.WOOD_BALL);
        final int [] skills = BallDef.getSkillsById(ballSelected);
        skill1 = skills[0];
        skill2 = skills[1];

        skill1Button = (Button) this
                .findViewById(R.id.game_activity_skill_1_button);
        skill1Button.setTypeface(MyApplication.getFont());
        skill1Button.setBackgroundResource(SkillDef.getButtonById(skill1));
        skill1Button.getBackground().setAlpha(180);
        skill1Button.setText(SkillDef.getSkillNameById(skill1));
        skill1Button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Client.castSkill(Skill.getSkill(skill1));
                skill1Button.setEnabled(false);
	            // skill1Button.setBackgroundResource(R.drawable.disabled_button);
		        skill1Button.getBackground().setAlpha(50);
                skill1CooledDown = false;
                Message msg = new Message();
                msg.what = 1;
                skillCoolDownHandler.sendMessageDelayed(msg, 
                        SkillDef.getCoolDownTimeById(skill1));
            }
            
        });

        skill2Button = (Button) this
                .findViewById(R.id.game_activity_skill_2_button);
        skill2Button.setTypeface(MyApplication.getFont());
        skill2Button.setBackgroundResource(SkillDef.getButtonById(skill2));
        skill2Button.getBackground().setAlpha(180);
        skill2Button.setText(SkillDef.getSkillNameById(skill2));
        skill2Button.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Client.castSkill(Skill.getSkill(skill2));
                skill2Button.setEnabled(false);
	            // skill2Button.setBackgroundResource(R.drawable.disabled_button);
		        skill2Button.getBackground().setAlpha(50);
                skill2CooledDown = false;
                Message msg = new Message();
                msg.what = 2;
                skillCoolDownHandler.sendMessageDelayed(msg, 
                        SkillDef.getCoolDownTimeById(skill2));
            }
            
        });
        
        loseView = (TextView) this.findViewById(R.id.game_activity_lose_text);
        loseView.setTypeface(MyApplication.getFont());
        loseView.setVisibility(View.INVISIBLE);
        
        remainingTimeView = (TextView) this.findViewById(R.id.game_activity_remaining_time_view);
        remainingTimeView.setTypeface(MyApplication.getFont());
        remainingTimeView.setVisibility(View.INVISIBLE);
        
        scoreView = (TextView) this.findViewById(R.id.game_activity_score_view);
        scoreView.setTypeface(MyApplication.getFont());
        scoreView.setText("0 : 0");
        scoreView.setTextColor(Color.YELLOW);
        
        /*
        flashBangMask = (RelativeLayout) this.findViewById(R.id.game_activity_flashbang_mask);
        AlphaAnimation flashBangAnim = new AlphaAnimation(0, 0);
        flashBangAnim.setDuration(0);
        flashBangAnim.setFillAfter(true);
        flashBangMask.setAnimation(flashBangAnim);
        */

        backScreen = (RelativeLayout) this.findViewById(R.id.game_activity_menu);
        backScreen.setVisibility(View.INVISIBLE);
        
        pauseButton = (TextView) this
                .findViewById(R.id.game_activity_pause_button);
        pauseButton.setTypeface(MyApplication.getFont());
        pauseButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO
            }
            
        });
        
        resumeButton = (TextView) this
                .findViewById(R.id.game_activity_resume_button);
        resumeButton.setTypeface(MyApplication.getFont());
        resumeButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                self.onBackPressed();
            }

        });

        exitButton = (TextView) this
                .findViewById(R.id.game_activity_exit_button);
        exitButton.setTypeface(MyApplication.getFont());
        exitButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                endGame();
            }

        });

    }
    
    /**
     * end the game but not yet exit, still stay in the game activity
     */
    public static void endGame() {
        Client.deactivate();
        
        Server.stop();
        Client.stop();
        ClientGameState.clear();
        if (BallCraft.isServer) {
            ServerGameState.clear();
        }
        
        GameRenderer.stopRendering();
        
        ServerAdapter.stopBTService();
        
        Intent intent = new Intent(self, EndGameMenu.class);
        self.startActivity(intent);
        self.finish();
        self.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    
    private void initSensor() {
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Client.setInputAcceleration(event.values[SensorManager.DATA_Y] * 2,
                -event.values[SensorManager.DATA_X] * 2);
    }
    
    public Handler skillCoolDownHandler = new Handler() {
           
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (!dead) {
	                skill1Button.setEnabled(true);
		            // skill1Button.setBackgroundResource(SkillDef.getButtonById(skill1));
		            skill1Button.getBackground().setAlpha(180);
                }
                skill1CooledDown = true;
            } else if (msg.what == 2) {
                if (!dead) {
	                skill2Button.setEnabled(true);
		            // skill2Button.setBackgroundResource(SkillDef.getButtonById(skill2));
		            skill2Button.getBackground().setAlpha(180);
                }
                skill2CooledDown = true;
            }
        }
      
    };
   
    public static Handler skillDisableHandler = new Handler() {
        
        @Override
        public void handleMessage(Message msg) {
            dead = true;
            skill1Button.setEnabled(false);
            // skill1Button.setBackgroundResource(R.drawable.disabled_button);
            skill1Button.getBackground().setAlpha(50);
            
            skill2Button.setEnabled(false);
            // skill2Button.setBackgroundResource(R.drawable.disabled_button);
            skill2Button.getBackground().setAlpha(50);
        }
        
    };
    
    public static Handler skillEnableHandler = new Handler() {
        
        @Override
        public void handleMessage(Message msg) {
            dead = false;
            if (skill1CooledDown) {
	            skill1Button.setEnabled(true);
	            // skill1Button.setBackgroundResource(SkillDef.getButtonById(skill1));
	            skill1Button.getBackground().setAlpha(180);
            }
            if (skill2CooledDown) {
	            skill2Button.setEnabled(true);
	            // skill2Button.setBackgroundResource(SkillDef.getButtonById(skill2));
	            skill2Button.getBackground().setAlpha(180);
            }
        }
        
    };

    @Override
    public void onBackPressed() {
        if (backScreen.getVisibility() == View.INVISIBLE) {
            backScreen.setVisibility(View.VISIBLE);
            
            AlphaAnimation screenAnim = new AlphaAnimation(0.5f, 0.5f);
            screenAnim.setDuration(0);
            screenAnim.setFillAfter(true);
            backScreen.setAnimation(screenAnim);
            
	        pauseButton.setEnabled(true);
	        resumeButton.setEnabled(true);
	        exitButton.setEnabled(true);
	        
            /*
            skill1Button.setVisibility(View.INVISIBLE);
            skill2Button.setVisibility(View.INVISIBLE);
            */
        } else {
            backScreen.setVisibility(View.INVISIBLE);
            
            AlphaAnimation screenAnim = new AlphaAnimation(0f, 0f);
            screenAnim.setDuration(0);
            screenAnim.setFillAfter(true);
            backScreen.setAnimation(screenAnim);
            
	        pauseButton.setEnabled(false);
	        resumeButton.setEnabled(false);
	        exitButton.setEnabled(false);
	        
            /*
            skill1Button.setVisibility(View.VISIBLE);
            skill2Button.setVisibility(View.VISIBLE);
            */
        }
    }
    
    public static Handler loseViewHanlder = new Handler() {
        
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 2) { // display died message
                loseView.setVisibility(View.VISIBLE);
            } else if (msg.what == 1) { // display score
                loseView.setVisibility(View.VISIBLE);
                int selfScore = msg.arg1;
                int enemyScore = msg.arg2;
                
                loseView.setText(selfScore + " : " + enemyScore);
                scoreView.setText(selfScore + " : " + enemyScore);
                
                if (selfScore > enemyScore) {
                    scoreView.setTextColor(Color.GREEN);
                } else if (selfScore == enemyScore) {
                    scoreView.setTextColor(Color.YELLOW);
                } else {
                    scoreView.setTextColor(Color.RED);
                }
            } else if (msg.what == 0) { // invisible
                loseView.setVisibility(View.INVISIBLE);
            }
        }
        
    };
    
    public static Handler remainingTimeHandler = new Handler() {
        
        @Override
        public void handleMessage(Message msg) {
            // TODO
        }
        
    };
    
}
