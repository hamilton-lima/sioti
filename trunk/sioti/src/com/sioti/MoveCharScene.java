package com.sioti;

import pulpcore.Input;
import pulpcore.animation.Easing;
import pulpcore.image.Colors;
import pulpcore.scene.Scene2D;
import pulpcore.sprite.FilledSprite;
import pulpcore.sprite.ImageSprite;

public class MoveCharScene extends Scene2D {

	ImageSprite character;

	public void load() {
		add(new FilledSprite(Colors.rgb(185, 209, 255)));
		character = new ImageSprite(CharManager.getInstance().getFront(), 0, 0);
		add(character);
	}

	private int SHIFT = 10;

	private int TIME = 100;

	public void update(int elapsedTime) {
		int x = character.x.getAsInt();
		int y = character.y.getAsInt();

		if (Input.isDown(Input.KEY_RIGHT)) {
			character.setImage(CharManager.getInstance().getRight());
			character.x.animateTo(x + SHIFT, TIME, Easing.REGULAR_OUT);
		}

		if (Input.isDown(Input.KEY_LEFT)) {
			character.setImage(CharManager.getInstance().getLeft());
			character.x.animateTo(x - SHIFT, TIME, Easing.REGULAR_OUT);
		}

		if (Input.isDown(Input.KEY_UP)) {
			character.setImage(CharManager.getInstance().getBack());
			character.y.animateTo(y - SHIFT, TIME, Easing.REGULAR_OUT);
		}

		if (Input.isDown(Input.KEY_DOWN)) {
			character.setImage(CharManager.getInstance().getFront());
			character.y.animateTo(y + SHIFT, TIME, Easing.REGULAR_OUT);
		}

	}

}
