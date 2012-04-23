package hkust.comp3111h;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class MyApplication extends Application
{

    private static Context context;

    @Override
    public void onCreate()
    {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() 
    {
    	if (context == null)
    	{
    		Log.e("MyApplication", "null context");
    	}
    	return MyApplication.context;
    }
}