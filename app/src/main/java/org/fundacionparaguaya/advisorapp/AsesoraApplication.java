package org.fundacionparaguaya.advisorapp;

import android.app.Application;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Main entry point for the asesora application
 */

public class AsesoraApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Fresco.initialize(this);
    }
}
