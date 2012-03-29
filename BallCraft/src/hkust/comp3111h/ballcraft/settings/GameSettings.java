package hkust.comp3111h.ballcraft.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class GameSettings {
    
    private static Context context;
    
    private static SharedPreferences pref;
    
    public static void setContext(Context con) {
        context = con;
    }

    public static boolean getMusicPref() {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getBoolean("music_pref", false);
    }
    
    public static void setMusicPref(boolean on) {
        SharedPreferences.Editor editor = pref.edit(); 
        editor.putBoolean("music_pref", on);
        editor.commit();
    }
    
    public static boolean getVibrPref() {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getBoolean("vibr_pref", false);
    }
    
    public static void setVibrPref(boolean on) {
        SharedPreferences.Editor editor = pref.edit(); 
        editor.putBoolean("vibr_pref", on);
        editor.commit();
    }
    
}
