package hkust.comp3111h.ballcraft.settings;

import hkust.comp3111h.MyApplication;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class GameSettings {
        
    private static SharedPreferences pref;
    
    public static boolean getMusicPref() {
        pref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        return pref.getBoolean("music_pref", false);
    }
    
    public static void setMusicPref(boolean on) {
        SharedPreferences.Editor editor = pref.edit(); 
        editor.putBoolean("music_pref", on);
        editor.commit();
    }
    
    public static boolean getVibrPref() {
        pref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        return pref.getBoolean("vibr_pref", false);
    }
    
    public static void setVibrPref(boolean on) {
        SharedPreferences.Editor editor = pref.edit(); 
        editor.putBoolean("vibr_pref", on);
        editor.commit();
    }
    
}
