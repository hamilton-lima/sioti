package com.sioti.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

import com.jme.bounding.BoundingBox;
import com.jme.image.Texture;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.shape.Box;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;
import com.jme.util.export.binary.BinaryImporter;
import com.jmex.model.converters.Md2ToJme;
import com.sioti.client.util.NodeUtil;
import com.sioti.util.Log;

public class Player extends Node {

	private static final long serialVersionUID = 1L;

	private float height;

	public Player(String name, DisplaySystem display) {
		super(name);

		Box box = new Box("box", new Vector3f(0, 0, 0), 2f, 2f, 2f);
		//Sphere box = new Sphere("ball", 25, 25, 4f);
		box.setSolidColor(ColorRGBA.red);
		box.setModelBound(new BoundingBox());
		box.updateModelBound();

		attachChild(box);
		// attachChild(loadModel(display));
		updateWorldBound();

		height = NodeUtil.getHeight(this);
	}

	public float getHeight() {
		return height;
	}

	public Node loadModel(DisplaySystem display) {
		Md2ToJme converter = new Md2ToJme();
		ByteArrayOutputStream BO = new ByteArrayOutputStream();

		URL textu = Player.class.getClassLoader().getResource(
				"media/models/alita/alita2.jpg");
		URL freak = Player.class.getClassLoader().getResource(
				"media/models/alita/tris.md2");
		Node freakmd2 = null;

		try {
			long time = System.currentTimeMillis();
			converter.convert(freak.openStream(), BO);
			Log.debug("Time to convert from md2 to .jme:"
					+ (System.currentTimeMillis() - time));
		} catch (IOException e) {
			Log.debug("damn exceptions:" + e.getMessage());
		}

		try {
			long time = System.currentTimeMillis();
			freakmd2 = (Node) BinaryImporter.getInstance().load(
					new ByteArrayInputStream(BO.toByteArray()));
			Log.debug("Time to convert from .jme to SceneGraph:"
					+ (System.currentTimeMillis() - time));
		} catch (IOException e) {
			Log.warning("damn exceptions:" + e.getMessage());
		}

		TextureState ts = display.getRenderer().createTextureState();
		ts.setEnabled(true);
		ts.setTexture(TextureManager.loadTexture(textu,
				Texture.MM_LINEAR_LINEAR, Texture.FM_LINEAR));
		freakmd2.setRenderState(ts);
		freakmd2.setLocalScale(.3f);
		return freakmd2;
	}

}
