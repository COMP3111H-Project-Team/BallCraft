package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.BallDef;
import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.server.Server;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SinglePlayerGameInitializer extends Activity {

    private static SinglePlayerGameInitializer self;
    
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

        this.setContentView(R.layout.game_initializer_layout);
        
        this.initGame();
    }
    
    public void initGame() {
        Intent intent = this.getIntent();
        int ballSelected = intent.getIntExtra("ballSelected",
                BallDef.WoodBall.id);
        String mapSelected = intent.getStringExtra("mapSelected");
 
        Intent serverIntent = new Intent(this, Server.class);
        serverIntent.putExtra("ball", ballSelected);
        serverIntent.putExtra("map", mapSelected);
        this.startService(serverIntent);        	
        
        this.startService(new Intent(this, Client.class)); // start running client
                
        this.finish();
    }
    
    @Override
    public void onBackPressed() {
        // do nothing
    }

}
