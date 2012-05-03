package hkust.comp3111h.ballcraft.client;

import hkust.comp3111h.MyApplication;
import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.BallDef;
import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.server.Ball;
import hkust.comp3111h.ballcraft.server.Plane;
import hkust.comp3111h.ballcraft.server.Server;
import hkust.comp3111h.ballcraft.server.Unit;
import hkust.comp3111h.ballcraft.server.Wall;
import hkust.comp3111h.ballcraft.ui.GameMenuRenderer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class GameInitializer extends Activity {
    
	// Debug
	public final String TAG = "MultiInit";
	public final boolean D = true;
	
	private static int ballSelected;
	
	private static GameInitializer self;
	
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
        
        GLSurfaceView glView = (GLSurfaceView) this
                .findViewById(R.id.game_initializer_menu_gl_surface_view);
        glView.setRenderer(new GameMenuRenderer(this));
        
        final TextView loadingView = (TextView) this.findViewById(R.id.game_initializer_loading_view);
        loadingView.setTypeface(MyApplication.getFont());
        if (BallCraft.isServer) {
            loadingView.setText("Loading...");
        } else {
            loadingView.setText("Waiting for server...");
        }

        initGame();
    }

    private void initGame() {
        Intent intent = this.getIntent();
        ballSelected = intent.getIntExtra("ballSelected",
                BallDef.WoodBall.id);
        String mapSelected = intent.getStringExtra("mapSelected");
        String gameMode = intent.getStringExtra("GAME_MODE");
        int limitValue = 0;
        
        if (BallCraft.isServer) {
            if (!BallCraft.isSinglePlayer()) {
		        if (gameMode.equals("LIMITED_TIME")) {
		            limitValue = intent.getIntExtra("TIME_LIMIT", 1);
		        } else {
		            limitValue = intent.getIntExtra("SCORE_LIMIT", 10);
		        }
	        }
	        
            Intent serverIntent = new Intent(this, Server.class);
            serverIntent.putExtra("ball", ballSelected);
            serverIntent.putExtra("map", mapSelected);
            
            if (!BallCraft.isSinglePlayer()) {
	            serverIntent.putExtra("GAME_MODE", gameMode);
	            serverIntent.putExtra("LIMIT_VALUE", limitValue);
            }
            this.startService(serverIntent);        	
        }
        
        this.startService(new Intent(this, Client.class)); // start running client

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

        Intent gameIntent = new Intent(self, GameActivity.class);
        gameIntent.putExtra("ballSelected", ballSelected);
        
        self.startActivity(gameIntent);
        self.overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
        self.finish();
    }

    @Override
    public void onBackPressed() {
    	AlertDialog.Builder builder = new AlertDialog.Builder(self);
    	builder.setTitle("Exit?");
    	builder.setMessage("Are you sure to stop loading game?");
    	builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            
            @Override
            public void onClick(DialogInterface dialog, int which) {
                self.finish();
                self.overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
            }
            
        });
    	
    	builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
            
        });
    	
    	builder.show();
    }

}
