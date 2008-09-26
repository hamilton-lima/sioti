package com.sioti.pulpcore;
import com.sioti.ProjectBuild;
import com.sioti.TitleScene;

import pulpcore.Build;
import pulpcore.CoreSystem;
import pulpcore.Stage;

public class LoadingScene extends pulpcore.scene.LoadingScene {
    
    public LoadingScene() {
        super("Sioti-" + ProjectBuild.VERSION + ".zip" , new TitleScene());
        
        CoreSystem.setTalkBackField("app.name", "Sioti");
        CoreSystem.setTalkBackField("app.version", ProjectBuild.VERSION);
        
        Stage.setUncaughtExceptionScene(new UncaughtExceptionScene());
        Stage.invokeOnShutdown(new Runnable() {
            public void run() {
                // Shutdown network connections, DB connections, etc. 
            }
        });
    }
    
    public void load() {
        
        String[] validHosts = {
            "pulpgames.net", "www.pulpgames.net", 
        };
        if (!Build.DEBUG && !CoreSystem.isValidHost(validHosts)) {
            CoreSystem.showDocument("http://www.pulpgames.net/");
        }
        else {
            super.load();
        }
    }
}
