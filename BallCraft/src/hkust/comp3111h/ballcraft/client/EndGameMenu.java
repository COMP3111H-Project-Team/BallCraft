package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.MyApplication;
import hkust.comp3111h.ballcraft.BallDef;
import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.data.GameData;
import hkust.comp3111h.ballcraft.ui.BallUnlockedMenu;
import hkust.comp3111h.ballcraft.ui.GameMenuRenderer;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class EndGameMenu extends Activity {
    
    private EndGameMenu self;
    
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

        this.setContentView(R.layout.end_game_layout);

        GLSurfaceView glView = (GLSurfaceView) this
                .findViewById(R.id.end_game_menu_gl_view);
        glView.setRenderer(new GameMenuRenderer(this));

        self.initLayout();
    }
    
    private void initLayout() {
        TextView endGameTitle = (TextView) self.findViewById(R.id.end_game_menu_end_game_title);
        endGameTitle.setTypeface(MyApplication.getFont());
        
        Intent intent = self.getIntent();
        
        int selfScore = intent.getIntExtra("selfScore", 0);
        int enemyScore = intent.getIntExtra("enemyScore", 0);

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
        
        TextView endGameScoreDisplay = (TextView) self.findViewById(R.id.end_game_menu_end_game_score_view);
        endGameScoreDisplay.setTypeface(MyApplication.getFont());
        endGameScoreDisplay.setText(selfScore + " : " + enemyScore);
        
        TextView endGameExpEarned = (TextView) self.findViewById(R.id.end_game_menu_end_game_exp_earned);
        endGameExpEarned.setTypeface(MyApplication.getFont());
        endGameExpEarned.setText("EXP EARNED: " + finalScore);
        
        Button exitGameButton = (Button) self.findViewById(R.id.end_game_menu_end_game_button);
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
    
    private void exitGame() {
    	Log.e("endgame","exit game");
    	/*
        Server.stop();
        Client.stop();
        ClientGameState.clear();
        
        if (BallCraft.isServer) {
            ServerGameState.clear();
        }
        
        GameRenderer.stopRendering();
        */
        int oldScore = GameData.getExperience();
        int newScore = oldScore + finalScore;
        
        GameData.setExperience(newScore);

        for (int i = 0; i < BallDef.balls.length; i++) {
            int updateExp = BallDef.getBallUnlockExpById(i);
            if (oldScore < updateExp && newScore >= updateExp) {
                Intent intent = new Intent(self, BallUnlockedMenu.class);
                intent.putExtra(BallUnlockedMenu.unlockedIndicator, i);
                self.startActivity(intent);
                self.finish();
                self.overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                break;
            }
        }
    }
    
    @Override
    public void onBackPressed() {
        // do nothing
    }
    
}
