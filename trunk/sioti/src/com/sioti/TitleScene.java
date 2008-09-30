package com.sioti;
import com.sioti.service.LoginService;
import com.sioti.service.SetupService;

import pulpcore.animation.event.SceneChangeEvent;
import pulpcore.image.CoreFont;
import pulpcore.scene.Scene2D;
import pulpcore.sprite.Button;
import pulpcore.sprite.Group;
import pulpcore.sprite.ImageSprite;
import pulpcore.sprite.Label;
import pulpcore.sprite.Sprite;

public class TitleScene extends Scene2D {
    
    Button playButton;
    Button optionsButton;
    Group componentLayer;
    
    public void load() {
        
        Label title = new Label(CoreFont.load("hello.font.png"), "SIOTI", 400, 300);
        title.setAnchor(Sprite.CENTER);
        playButton = Button.createLabeledButton("Play", 400, 450);
        playButton.setAnchor(Sprite.CENTER);
        
        componentLayer = new Group();
        componentLayer.add(playButton);
        
        add(new ImageSprite("background.png", 0, 0));
        add(title);
        addLayer(componentLayer);
    }
    
    public void update(int elapsedTime) {
    	if (playButton.isClicked()) {
            playButton.alpha.animateTo(0, 300);
    		LoginService.getInstance().login("foo", "bar");
    		SetupService.getInstance().getData();

            addEvent(new SceneChangeEvent(new MoveCharScene(), 300));
        }
    }
}
