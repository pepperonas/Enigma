package com.pepperonas.enigma.app;

import android.app.Application;

import com.pepperonas.andcommon.AndCommon;

/**
 * @author Martin Pfeffer (pepperonas)
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AndCommon.init(this);

    }
}
