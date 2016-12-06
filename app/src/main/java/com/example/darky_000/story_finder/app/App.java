package com.example.darky_000.story_finder.app;

/**
 * Created by darky_000 on 12/5/2016.
 */
import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static App instance;

    /**
     * Return application context anywhere in the application
     * @return  Application context
     */
    public static Context getContext(){
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}