package com.sioti;
import pulpcore.Stage;
import pulpcore.scene.Scene2D;
import pulpcore.sprite.Button;
import pulpcore.sprite.ImageSprite;
import pulpcore.sprite.Sprite;

public class OptionScene extends Scene2D {
    
    Button toggleButton;
    Button backButton;
    Button errorButton;
    
    public void load() {
        toggleButton = Button.createLabeledToggleButton("Some Toggle", 320, 300);
        toggleButton.setAnchor(Sprite.CENTER);
        errorButton = Button.createLabeledToggleButton("Some Error", 320, 350);
        errorButton.setAnchor(Sprite.CENTER);
        backButton = Button.createLabeledButton("<< Back", 320, 400);
        backButton.setAnchor(Sprite.CENTER);
        
        add(new ImageSprite("background.png", 0, 0));
        add(toggleButton);
        add(errorButton);
        add(backButton);
    }
    
    public void update(int elapsedTime) {
        if (backButton.isClicked()) {
            // Go back to the previous scene
            Stage.popScene();
        }
        if (errorButton.isClicked()) {
            // Force showing the uncaught exception scene
            throw new RuntimeException();
        }
    }
}
