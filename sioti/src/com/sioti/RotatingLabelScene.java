package com.sioti;

import pulpcore.Input;
import pulpcore.image.CoreFont;
import pulpcore.scene.Scene2D;
import pulpcore.sound.Sound;
import pulpcore.sprite.ImageSprite;
import pulpcore.sprite.Label;
import pulpcore.sprite.Sprite;

public class RotatingLabelScene extends Scene2D {

	Label label;

	public void load() {
		add(new ImageSprite("background.png", 0, 0));

		CoreFont font = CoreFont.load("hello.font.png");
		label = new Label(font, "Hello World", 320, 240);
		label.setAnchor(Sprite.CENTER);
		add(label);

		Sound sound = Sound.load("sound.wav");
		sound.play();
	}

	public void update(int elapsedTime) {
		double angle = 0.006 * (Input.getMouseX() - 320);
		int duration = 100;
		label.angle.animateTo(angle, duration);
	}
}
