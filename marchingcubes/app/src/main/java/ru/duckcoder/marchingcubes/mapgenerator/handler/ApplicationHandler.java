package ru.duckcoder.marchingcubes.mapgenerator.handler;

import com.jme3.app.SimpleApplication;
import ru.duckcoder.marchingcubes.mapgenerator.config.Config;

public class ApplicationHandler extends SimpleApplication {
    private static volatile ApplicationHandler instance;

    public static ApplicationHandler getInstance() {
        ApplicationHandler localInstance = instance;
        if (localInstance == null) {
            synchronized (ApplicationHandler.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ApplicationHandler();
                }
            }
        }
        return localInstance;
    }

    private Config config;

    private ApplicationHandler() {
        this.setShowSettings(false);
    }

    @Override
    public void simpleInitApp() {

    }
}
