package hkust.comp3111h.ballcraft.ui;

import hkust.comp3111h.MyApplication;
import hkust.comp3111h.ballcraft.R;
import hkust.comp3111h.ballcraft.data.GameData;
import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
        musicCheck.setTypeface(MyApplication.getFont());
        musicCheck.setChecked(GameData.getMusicPref());
        musicCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked) {
                GameData.setMusicPref(isChecked);
            }
            
        });
        
        CheckBox vibrCheck = (CheckBox) self.findViewById(R.id.option_menu_vibration_checkbox);
        vibrCheck.setTypeface(MyApplication.getFont());
        vibrCheck.setChecked(GameData.getVibrPref());
        vibrCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked) {
                GameData.setVibrPref(isChecked);
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
