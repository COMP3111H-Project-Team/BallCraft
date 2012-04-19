package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.BallDef;
import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.server.Server;
import hkust.comp3111h.ballcraft.ui.MainMenu;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class MultiPlayerGameInitializer extends Activity {
	//Dubug
	public final String TAG = "MultiInit";
	public final boolean D = true;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.setContentView(R.layout.game_initializer_layout);
        initGame();
    }

    private void initGame() {
        Intent intent = this.getIntent();
        int ballSelected = intent.getIntExtra("ballSelected",
                BallDef.WoodBall.id);
        String mapSelected = intent.getStringExtra("mapSelected");
        
        if (BallCraft.isServer)
        {
            Intent serverIntent = new Intent(this, Server.class);
            serverIntent.putExtra("ball", ballSelected);
            serverIntent.putExtra("map", mapSelected);
            this.startService(serverIntent);        	
        }
        
        Client.setContext(this);
        this.startService(new Intent(this, Client.class)); // start running client

        MapParser.setContext(this);

        if(D)Log.e(TAG,"start gameactivity");
        Intent gameIntent = new Intent(MainMenu.self, GameActivity.class);
        this.startActivity(gameIntent);

        this.finish();
    }

    @Override
    public void onBackPressed() {
        // do nothing
    }

}
