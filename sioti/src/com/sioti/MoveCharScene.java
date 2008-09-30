package com.sioti;

import pulpcore.Input;
import pulpcore.Stage;
import pulpcore.animation.Easing;
import pulpcore.image.Colors;
import pulpcore.scene.Scene;
import pulpcore.scene.Scene2D;
import pulpcore.sprite.FilledSprite;
import pulpcore.sprite.ImageSprite;

import com.sioti.map.SpriteMap;
import com.sioti.service.Layer1DataService;
import com.sioti.service.Setup;
import com.sioti.util.Log;

public class MoveCharScene extends Scene2D {

	private ImageSprite character;

	private SpriteMap spriteMap;

	public void load() {
		Log.debug("movechar scene start");

		spriteMap = new SpriteMap(0, 0, Stage.getWidth(), Stage.getHeight(),
				Setup.getInstance().getStartPointA());
		spriteMap.setShowCoords(true);
		
		spriteMap.setLayer1DataService(Layer1DataService.getInstance());

		add(new FilledSprite(Colors.rgb(185, 209, 255)));
		add(spriteMap);

		character = new ImageSprite(CharManager.getInstance().getFront(), Stage
				.getWidth() / 2, Stage.getHeight() / 2);
		add(character);
		spriteMap.setCharacter(character);
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
