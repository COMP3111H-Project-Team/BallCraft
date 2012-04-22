package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.BallDef;
import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.server.Ball;
import hkust.comp3111h.ballcraft.server.Plane;
import hkust.comp3111h.ballcraft.server.Server;
import hkust.comp3111h.ballcraft.server.Unit;
import hkust.comp3111h.ballcraft.server.Wall;
import hkust.comp3111h.ballcraft.ui.MainMenu;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MultiPlayerGameInitializer extends Activity {
    
	//Dubug
	public final String TAG = "MultiInit";
	public final boolean D = true;
	
	private static MultiPlayerGameInitializer self;
	
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
        
        this.startService(new Intent(this, Client.class)); // start running client

        this.finish();
    }
    
    public static void handleInitMsg(String msg) {
        String [] parts = msg.split("MAPDEF");
        String [] mapDefs = parts[0].split(",");
        
        int terrain = Integer.parseInt(mapDefs[0]);
        int mode = Integer.parseInt(mapDefs[1]);
        
        int ball1Type = Integer.parseInt(mapDefs[2]);
        int ball2Type = Integer.parseInt(mapDefs[3]);
        
        ClientGameState.getClientGameState().setMapTerrain(terrain);
        ClientGameState.getClientGameState().setMapMode(mode);
        
        String [] unitStrs = parts[1].split("/");
        for (int i = 0; i < unitStrs.length; i++) {
	        Unit unit = Unit.fromSerializedString(unitStrs[i]);
	        if (unit instanceof Ball) {
	            if (i == 0) {
		            ClientGameState.getClientGameState()
		                    .balls.add(Ball.getTypedBall((Ball) unit, ball1Type));
	            } else if (i == 1) {
		            ClientGameState.getClientGameState()
		                    .balls.add(Ball.getTypedBall((Ball) unit, ball2Type));
	            }
	            /*
	            ClientGameState.getClientGameState()
	                    .balls.add(Ball.getTypedBall((Ball) unit, BallCraft.Ball.DARK_BALL));
	                    // .balls.add((Ball) unit);
                */
	        } else if (unit instanceof Wall) {
	            ClientGameState.getClientGameState()
	                    .walls.add((Wall) unit);
	        } else if (unit instanceof Plane) {
	            ClientGameState.getClientGameState()
	                    .planes.add((Plane) unit);
	        }
        }
        
        Client.gameInited = true;
        Client.remoteServerInited = true;

        Intent gameIntent = new Intent(MainMenu.self, GameActivity.class);
        self.startActivity(gameIntent);
    }

    @Override
    public void onBackPressed() {
        // do nothing
    }

}
