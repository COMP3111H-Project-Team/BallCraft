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

    private GameActivity self;
    
    private GLSurfaceView mGLView;
    private SensorManager sensorManager;
    
    private static RelativeLayout flashBangMask;

    private RelativeLayout backScreen;
    
    private RelativeLayout endGameLayout;
    
    private GameRenderer renderer;
    
    private static TextView loseView = null;

    private static Button skill1Button;
    private static Button skill2Button;
    
    private static boolean skill1CooledDown = true;
    private static boolean skill2CooledDown = true;
    
    private static int skill1;
    private static int skill2;
    
    private static boolean dead = false;
    
    private int finalScore;
    
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
	            skill1Button.setBackgroundResource(R.drawable.disabled_button);
		        skill1Button.getBackground().setAlpha(180);
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
	            skill2Button.setBackgroundResource(R.drawable.disabled_button);
		        skill2Button.getBackground().setAlpha(180);
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
        
        flashBangMask = (RelativeLayout) this.findViewById(R.id.game_activity_flashbang_mask);
        AlphaAnimation flashBangAnim = new AlphaAnimation(0, 0);
        flashBangAnim.setDuration(0);
        flashBangAnim.setFillAfter(true);
        flashBangMask.setAnimation(flashBangAnim);

        backScreen = (RelativeLayout) this.findViewById(R.id.game_activity_menu);
        backScreen.setVisibility(View.INVISIBLE);
        
        self.endGameLayout = (RelativeLayout) this.findViewById(R.id.game_activity_end_game_layout);
        self.endGameLayout.setVisibility(View.INVISIBLE);
        
        TextView pauseButton = (TextView) this
                .findViewById(R.id.game_activity_pause_button);
        pauseButton.setTypeface(MyApplication.getFont());
        pauseButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO
            }
            
        });
        
        TextView resumeButton = (TextView) this
                .findViewById(R.id.game_activity_resume_button);
        resumeButton.setTypeface(MyApplication.getFont());
        resumeButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                self.onBackPressed();
            }

        });

        TextView exitButton = (TextView) this
                .findViewById(R.id.game_activity_exit_button);
        exitButton.setTypeface(MyApplication.getFont());
        exitButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                self.endGame();
            }

        });
    }
    
    /**
     * end the game but not yet exit, still stay in the game activity
     */
    private void endGame() {
        Client.deactivate();
        
        // hide the back key menu
        self.backScreen.setVisibility(View.INVISIBLE);
        AlphaAnimation screenAnim = new AlphaAnimation(0, 0);
        screenAnim.setDuration(0);
        screenAnim.setFillAfter(true);
        self.backScreen.setAnimation(screenAnim);
        
        // show the end game menu
        self.endGameLayout.setVisibility(View.VISIBLE);
        AlphaAnimation endGameLayoutAnim = new AlphaAnimation(0.8f, 0.8f);
        endGameLayoutAnim.setDuration(0);
        endGameLayoutAnim.setFillAfter(true);
        self.endGameLayout.setAnimation(endGameLayoutAnim);
        
        skill1Button.setVisibility(View.INVISIBLE);
        skill2Button.setVisibility(View.INVISIBLE);
        
        TextView endGameTitle = (TextView) self.findViewById(R.id.game_activity_end_game_title);
        endGameTitle.setTypeface(MyApplication.getFont());
        
        int selfScore = ClientGameState.getClientGameState().selfScoreEarned;
        int enemyScore = ClientGameState.getClientGameState().enemyScoreEarned;
        
        finalScore = selfScore;
        
        if (selfScore > 3 * enemyScore) {
            endGameTitle.setText("An Incredible Triumph !!!");
            endGameTitle.setTextColor(Color.RED);
            finalScore += 30;
            GameData.setWinCount(GameData.getWinCount() + 1);
        } else if (selfScore > 2 * enemyScore) {
            endGameTitle.setText("Significant Victory !!!");
            endGameTitle.setTextColor(Color.MAGENTA);
            finalScore += 20;
            GameData.setWinCount(GameData.getWinCount() + 1);
        } else if (selfScore > enemyScore) {
            endGameTitle.setText("You Win !!!");
            endGameTitle.setTextColor(Color.YELLOW);
            finalScore += 10;
            GameData.setWinCount(GameData.getWinCount() + 1);
        } else if (selfScore == enemyScore) {
            endGameTitle.setText("Well, well, it's a Draw.");
            endGameTitle.setTextColor(Color.WHITE);
            GameData.setDrawCount(GameData.getDrawCount() + 1);
        } else if (selfScore * 3 < enemyScore) {
            endGameTitle.setText("A Total Failure ...");
            endGameTitle.setTextColor(Color.DKGRAY);
            GameData.setLoseCount(GameData.getLoseCount() + 1);
        } else if (selfScore * 2 < enemyScore) {
            endGameTitle.setText("Terrible Defeat ...");
            endGameTitle.setTextColor(Color.GRAY);
            GameData.setLoseCount(GameData.getLoseCount() + 1);
        } else if (selfScore < enemyScore) {
            endGameTitle.setText("You Lose ...");
            endGameTitle.setTextColor(Color.LTGRAY);
            GameData.setLoseCount(GameData.getLoseCount() + 1);
        }
        
        TextView endGameScoreDisplay = (TextView) self.findViewById(R.id.game_activity_end_game_score_view);
        endGameScoreDisplay.setTypeface(MyApplication.getFont());
        endGameScoreDisplay.setText(selfScore + " : " + enemyScore);
        
        TextView endGameExpEarned = (TextView) self.findViewById(R.id.game_activity_end_game_exp_earned);
        endGameExpEarned.setTypeface(MyApplication.getFont());
        endGameExpEarned.setText("EXP EARNED: " + finalScore);
        
        Button exitGameButton = (Button) self.findViewById(R.id.game_activity_end_game_button);
        exitGameButton.setTypeface(MyApplication.getFont());
        exitGameButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                self.exitGame();
                self.finish();
                self.overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
            }
            
        });
    }
    
    /**
     * exit the game and deal with relevant settings
     */
    private void exitGame() {
        Server.stop();
        Client.stop();
        ClientGameState.clear();
        
        if (BallCraft.isServer) {
            ServerGameState.clear();
        }
        
        GameRenderer.stopRendering();
       
        ServerAdapter.sendEndGameMessageToServer();
        
        int oldScore = GameData.getExperience();
        int newScore = oldScore + finalScore;
        
        GameData.setExperience(newScore);
        if (!BallCraft.isSinglePlayer()) {
            ServerAdapter.sendGameInterruptMessage();
        }
        
        for (int i = 0; i < BallDef.balls.length; i++) {
            int updateExp = BallDef.getBallUnlockExpById(i);
            if (oldScore < updateExp && newScore >= updateExp) {
                Intent intent = new Intent(self, BallUnlockedMenu.class);
                intent.putExtra(BallUnlockedMenu.unlockedIndicator, i);
                self.startActivity(intent);
		        self.overridePendingTransition(android.R.anim.fade_in,
		                android.R.anim.fade_out);
                break;
            }
        }
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
		            skill1Button.setBackgroundResource(SkillDef.getButtonById(skill1));
		            skill1Button.getBackground().setAlpha(180);
                }
                skill1CooledDown = true;
            } else if (msg.what == 2) {
                if (!dead) {
	                skill2Button.setEnabled(true);
		            skill2Button.setBackgroundResource(SkillDef.getButtonById(skill2));
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
            skill1Button.setBackgroundResource(R.drawable.disabled_button);
            skill1Button.getBackground().setAlpha(180);
            
            skill2Button.setEnabled(false);
            skill2Button.setBackgroundResource(R.drawable.disabled_button);
            skill2Button.getBackground().setAlpha(180);
        }
        
    };
    
    public static Handler skillEnableHandler = new Handler() {
        
        @Override
        public void handleMessage(Message msg) {
            dead = false;
            if (skill1CooledDown) {
	            skill1Button.setEnabled(true);
	            skill1Button.setBackgroundResource(SkillDef.getButtonById(skill1));
	            skill1Button.getBackground().setAlpha(180);
            }
            if (skill2CooledDown) {
	            skill2Button.setEnabled(true);
	            skill2Button.setBackgroundResource(SkillDef.getButtonById(skill2));
	            skill2Button.getBackground().setAlpha(180);
            }
        }
        
    };

    @Override
    public void onBackPressed() {
        if (endGameLayout.getVisibility() == View.VISIBLE) {
            // simply ignore
            return;
        } else if (backScreen.getVisibility() == View.INVISIBLE) {
            backScreen.setVisibility(View.VISIBLE);
            
            AlphaAnimation screenAnim = new AlphaAnimation(0.5f, 0.5f);
            screenAnim.setDuration(0);
            screenAnim.setFillAfter(true);
            backScreen.setAnimation(screenAnim);
            
            skill1Button.setVisibility(View.INVISIBLE);
            skill2Button.setVisibility(View.INVISIBLE);
        } else {
            backScreen.setVisibility(View.INVISIBLE);
            
            AlphaAnimation screenAnim = new AlphaAnimation(0f, 0f);
            screenAnim.setDuration(0);
            screenAnim.setFillAfter(true);
            backScreen.setAnimation(screenAnim);
            
            skill1Button.setVisibility(View.VISIBLE);
            skill2Button.setVisibility(View.VISIBLE);
        }
    }
    
    public static Handler loseViewHanlder = new Handler() {
        
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 2) { // display died message
                loseView.setVisibility(View.VISIBLE);
            } else if (msg.what == 1) { // display score
                loseView.setVisibility(View.VISIBLE);
                loseView.setText(msg.arg1 + " : " + msg.arg2);
            } else if (msg.what == 0) { // invisible
                loseView.setVisibility(View.INVISIBLE);
            }
        }
        
    };
    
    public static Handler flashBangStartHandler = new Handler() {
        
        @Override
        public void handleMessage(Message msg) {
	        AlphaAnimation flashBangAnim = new AlphaAnimation(0, 1);
	        flashBangAnim.setDuration(200);
	        flashBangAnim.setFillAfter(true);
	        flashBangMask.setAnimation(flashBangAnim);
        }
        
    };
    
    public static Handler flashBangEndHandler = new Handler() {
        
        @Override
        public void handleMessage(Message msg) {
	        AlphaAnimation flashBangAnim = new AlphaAnimation(1, 0);
	        flashBangAnim.setDuration(3000);
	        flashBangAnim.setFillAfter(true);
	        flashBangMask.setAnimation(flashBangAnim);
        }
        
    };
    
}
