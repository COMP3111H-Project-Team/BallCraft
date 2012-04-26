package hkust.comp3111h.ballcraft.ui;

import hkust.comp3111h.ballcraft.BallCraft;
import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.data.GameData;
import hkust.comp3111h.ballcraft.server.bt.BluetoothActivity;
import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class UsernameMenu extends Activity {
    
    private UsernameMenu self;
    
    private EditText usernameInput;
    private Button okButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        self = this;

        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        this.setContentView(R.layout.username_menu);
        
        GLSurfaceView glView = (GLSurfaceView) this
                .findViewById(R.id.username_menu_gl_surface_view);
        glView.setRenderer(new GameMenuRenderer(this));
        
        self.initLayout();
    }
    
    private void initLayout() {
        self.usernameInput = (EditText) self.findViewById(R.id.username_menu_input);
        
        if (GameData.isUsernameSet()) {
	        self.usernameInput.setText(GameData.getUsername());
        }
        
        self.okButton = (Button) self.findViewById(R.id.username_menu_button);
        okButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String username = self.usernameInput.getText().toString().trim();
                if (username.length() != 0) {
                    GameData.setUsername(username);
                    
                    Intent fromIntent = self.getIntent();
                    if (fromIntent.getBooleanExtra("start_multiplayer_game", false)) {
		                Intent Bluetooth = new Intent(self, BluetoothActivity.class);
		                self.startActivity(Bluetooth);
		                BallCraft.maxPlayer = 2;
		                self.overridePendingTransition(android.R.anim.fade_in,
		                        android.R.anim.fade_out);
		                self.finish();
                    } else {
                        self.onBackPressed();
                    }
                }
            }
            
        });
    }
    
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        self.overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }

}
