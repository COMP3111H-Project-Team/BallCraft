package hkust.comp3111h.ballcraft.data;

import hkust.comp3111h.MyApplication;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class GameData {
    
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
    
    public static int getExperience() {
        pref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        return pref.getInt("exp", 0);
    }
    
    public static void setExperience(int exp) {
        SharedPreferences.Editor editor = pref.edit(); 
        editor.putInt("exp", exp);
        editor.commit();
    }
    
    public static boolean isUsernameSet() {
        pref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        return pref.getString("username", null) != null;
    }
    
    public static String getUsername() {
        pref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        return pref.getString("username", "");
    }
    
    public static void setUsername(String username) {
        SharedPreferences.Editor editor = pref.edit(); 
        editor.putString("username", username);
        editor.commit();
    }
    
    public static int getWinCount() {
        pref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        return pref.getInt("winCount", 0);
    }
    
    public static void setWinCount(int winCount) {
        SharedPreferences.Editor editor = pref.edit(); 
        editor.putInt("winCount", winCount);
        editor.commit();
    }
    
    public static int getLoseCount() {
        pref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        return pref.getInt("loseCount", 0);
    }
    
    public static void setLoseCount(int loseCount) {
        SharedPreferences.Editor editor = pref.edit(); 
        editor.putInt("loseCount", loseCount);
        editor.commit();
    }
    
    public static int getDrawCount() {
        pref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        return pref.getInt("drawCount", 0);
    }
    
    public static void setDrawCount(int drawCount) {
        SharedPreferences.Editor editor = pref.edit(); 
        editor.putInt("drawCount", drawCount);
        editor.commit();
    }
    
}
