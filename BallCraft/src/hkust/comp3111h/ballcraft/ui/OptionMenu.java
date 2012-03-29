package hkust.comp3111h.ballcraft.ui;

import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.settings.GameSettings;
import android.app.Activity;
import android.content.SharedPreferences;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class OptionMenu extends Activity {

    private GLSurfaceView glView;

    private OptionMenu self;

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

        this.setContentView(R.layout.option_menu);

        glView = (GLSurfaceView) this
                .findViewById(R.id.option_menu_gl_surface_view);
        glView.setRenderer(new GameMenuRenderer(this));
        
        self.initLayout();
    }
    
    public void initLayout() {
        CheckBox musicCheck = (CheckBox) self.findViewById(R.id.option_menu_music_checkbox);
        CheckBox vibrCheck = (CheckBox) self.findViewById(R.id.option_menu_vibration_checkbox);
        
        musicCheck.setChecked(GameSettings.getMusicPref());
        vibrCheck.setChecked(GameSettings.getVibrPref());
        
        musicCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked) {
                GameSettings.setMusicPref(isChecked);
            }
            
        });
        
        vibrCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked) {
                GameSettings.setVibrPref(isChecked);
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
