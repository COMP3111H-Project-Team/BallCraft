package hkust.comp3111h.ballcraft.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class GameData {
    
    private static Context context;
    
    private static SharedPreferences pref;
    
    public static void setContext(Context con) {
        context = con;
        setPref();
    }
    
    public static void setPref() {
        if (pref == null) {
	        pref = PreferenceManager.getDefaultSharedPreferences(context);
        }
    }

    public static boolean getMusicPref() {
        return pref.getBoolean("music_pref", false);
    }
    
    public static void setMusicPref(boolean on) {
        SharedPreferences.Editor editor = pref.edit(); 
        editor.putBoolean("music_pref", on);
        editor.commit();
    }
    
    public static boolean getVibrPref() {
        return pref.getBoolean("vibr_pref", false);
    }
    
    public static void setVibrPref(boolean on) {
        SharedPreferences.Editor editor = pref.edit(); 
        editor.putBoolean("vibr_pref", on);
        editor.commit();
    }
    
    public static int getExperience() {
        return pref.getInt("exp", 0);
    }
    
    public static void setExperience(int exp) {
        SharedPreferences.Editor editor = pref.edit(); 
        editor.putInt("exp", exp);
        editor.commit();
    }
    
    public static boolean isUsernameSet() {
        return pref.getString("username", null) != null;
    }
    
    public static String getUsername() {
        return pref.getString("username", "");
    }
    
    public static void setUsername(String username) {
        SharedPreferences.Editor editor = pref.edit(); 
        editor.putString("username", username);
        editor.commit();
    }
    
}
