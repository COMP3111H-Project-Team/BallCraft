package hkust.comp3111h.ballcraft.ui;

import hkust.comp3111h.MyApplication;
import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.data.GameData;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileMenu extends Activity {

    private GLSurfaceView glView;

    private ProfileMenu self;

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

        this.setContentView(R.layout.profile_menu);

        glView = (GLSurfaceView) this
                .findViewById(R.id.profile_menu_gl_surface_view);
        glView.setRenderer(new GameMenuRenderer(this));
        
        self.initLayout();
    }
    
    public void initLayout() {
        final TextView usernameDisplay = (TextView) self.findViewById(R.id.profile_menu_username_display);
        usernameDisplay.setTypeface(MyApplication.getFont());
        usernameDisplay.setText("Player Name: " + GameData.getUsername());
        
        Button changeUsernameButton = (Button) self.findViewById(R.id.profile_menu_username_change_button);
        changeUsernameButton.setTypeface(MyApplication.getFont());
        changeUsernameButton.getBackground().setAlpha(100);
        changeUsernameButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final EditText usernameInput = new EditText(self);
                
                AlertDialog.Builder dialog = new AlertDialog.Builder(self);
                dialog.setTitle("Player Name");
                dialog.setMessage("Enter your player name here");
                dialog.setView(usernameInput);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        String val = usernameInput.getText().toString();
                        if (val != null) {
                            GameData.setUsername(val);
                            usernameDisplay.setText("Player Name: " + val);
                        }
                    }
                    
                });
                
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // cancelled
                    }
                    
                });
                
                dialog.show();
                
            }
            
        });
        
        final TextView expDisplay = (TextView) self.findViewById(R.id.profile_menu_exp_display);
        expDisplay.setTypeface(MyApplication.getFont());
        expDisplay.setText("Experience: " + GameData.getExperience());
        
        final TextView logDisplay = (TextView) self.findViewById(R.id.profile_menu_game_log_display);
        logDisplay.setTypeface(MyApplication.getFont());
        logDisplay.setText("Win: " + GameData.getWinCount() + "\nLose: " + GameData.getLoseCount()
                + "\nDraw: " + GameData.getDrawCount());
        
        Button clearAllButton = (Button) self.findViewById(R.id.profile_menu_clear_button);
        clearAllButton.setTypeface(MyApplication.getFont());
        clearAllButton.getBackground().setAlpha(100);
        clearAllButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(self);
                dialog.setTitle("Clear all data?");
                dialog.setMessage("These data cannot be restored once cleared!");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        GameData.setExperience(0);
                        GameData.setWinCount(0);
                        GameData.setLoseCount(0);
                        GameData.setDrawCount(0);
                        expDisplay.setText("Experience: 0");
                        logDisplay.setText("Win: 0\nLose: 0\nDraw: 0");
                    }
                    
                });
                
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // cancel
                    }
                    
                });
                
                dialog.show();
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
