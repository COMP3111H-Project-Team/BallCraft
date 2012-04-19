package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.graphics.GameRenderer;
import hkust.comp3111h.ballcraft.server.Server;
import hkust.comp3111h.ballcraft.server.ServerGameState;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameActivity extends Activity implements SensorEventListener {
	//Debug
	public final String TAG = "gameActivity";
	public final boolean D = true;

    private GameActivity self;

    private GLSurfaceView mGLView;
    private SensorManager sensorManager;

    private LinearLayout menuLayout;

    private static TextView debugView = null;
    private static String debugMsg = null;

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

    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * Initialize the main layout of the game view, including buttons, MUD, etc.
     */
    private void initLayout() {

        this.setContentView(R.layout.game_layout);

        mGLView = (GLSurfaceView) this.findViewById(R.id.game_activity_gl_surface_view);
        // here we need to wait for the init data to be sent from the server to create the render
        // and start drawing
        int time = 0;
        while (!Client.isGameInited()) {
            try {
            	Log.i("starting game", "waiting for server, " + time + " s");
            	time++;
                Thread.sleep(1000);                
            } catch (Exception e) {
            }
        }
        mGLView.setRenderer(new GameRenderer(this));
        
        TextView statusDisplay = (TextView) this.findViewById(R.id.game_activity_status_dispaly);
        statusDisplay.getBackground().setAlpha(200);

        Button skill1Button = (Button) this
                .findViewById(R.id.game_activity_skill_1_button);
        skill1Button.getBackground().setAlpha(80);
        skill1Button.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Client.castSkill(Skill.getSkill(BallCraft.Skill.MINE));
            }
        });

        Button skill2Button = (Button) this
                .findViewById(R.id.game_activity_skill_2_button);
        skill2Button.getBackground().setAlpha(80);
        skill2Button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Client.castSkill(Skill.getSkill(BallCraft.Skill.Propel));
            }
        });

        debugView = (TextView) this.findViewById(R.id.game_activity_debug_view);

        menuLayout = (LinearLayout) this.findViewById(R.id.game_activity_menu);
        menuLayout.setVisibility(View.INVISIBLE);

        Button resumeButton = (Button) this
                .findViewById(R.id.game_activity_resume_button);
        resumeButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                self.onBackPressed();
            }

        });

        Button exitButton = (Button) this
                .findViewById(R.id.game_activity_exit_button);
        exitButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Server.stop();
                Client.stop();
                ClientGameState.clear();
                if (BallCraft.isServer) ServerGameState.clear();
                self.finish();
                self.overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
            }

        });
    }

    private void initSensor() {
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    public void onSensorChanged(SensorEvent event) {
        Client.setInputAcceleration(event.values[SensorManager.DATA_Y] * 2,
                -event.values[SensorManager.DATA_X] * 2);
    }

    @Override
    public void onBackPressed() {
       if (menuLayout.getVisibility() == View.INVISIBLE) {
            menuLayout.setVisibility(View.VISIBLE);
            AlphaAnimation alphaAnim = new AlphaAnimation(0.5f, 0.5f);
            alphaAnim.setDuration(0);
            alphaAnim.setFillAfter(true);
            menuLayout.setAnimation(alphaAnim);
        } else {
            menuLayout.setVisibility(View.INVISIBLE);
            AlphaAnimation alphaAnim = new AlphaAnimation(0f, 0f);
            alphaAnim.setDuration(0);
            alphaAnim.setFillAfter(true);
            menuLayout.setAnimation(alphaAnim);
        }
    }

    /**
     * Used for displaying a debug message at the bottom of the screen
     */
    public static void displayDebugMsg() {
        debugView.setText(debugMsg);
    }

    private static Handler debugMsgHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            displayDebugMsg();
        }
    };

    public static void display(String msg) {
        debugMsg = msg;
        debugMsgHandler.sendEmptyMessage(0);
    }

    public static void display(float msg) {
        debugMsg = "" + msg;
        debugMsgHandler.sendEmptyMessage(0);
    }

    public static void display(double msg) {
        debugMsg = "" + msg;
        debugMsgHandler.sendEmptyMessage(0);
    }

    public static void display(int msg) {
        debugMsg = "" + msg;
        debugMsgHandler.sendEmptyMessage(0);
    }
}
