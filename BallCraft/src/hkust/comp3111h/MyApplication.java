package hkust.comp3111h;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate()
    {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() 
    {
    	return MyApplication.context;
    }
    
    public static Typeface getFont() {
        return Typeface.createFromAsset(context.getAssets(), "comic.ttf");
    }
    
}