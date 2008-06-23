package com.sioti.jme_samples;

import com.jme.app.SimpleGame;
import com.jme.scene.TriMesh;
import com.jme.scene.state.TextureState;
import com.jme.math.Vector3f;
import com.jme.math.Vector2f;
import com.jme.util.TextureManager;
import com.jme.image.Texture;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import java.net.URL;
import java.nio.FloatBuffer;
import com.jme.util.geom.BufferUtils;

/**
 * Started Date: Jul 21, 2004<br>
 * <br>
 * 
 * This program demonstrates using key inputs to change things.
 * 
 * @author Jack Lindamood
 */
public class HelloKeyInput extends SimpleGame {
	// The TriMesh that I will change
	TriMesh square;

	// A scale of my current texture values
	float coordDelta;

	public static void main(String[] args) {
		HelloKeyInput app = new HelloKeyInput();
		app.setDialogBehaviour(SimpleGame.ALWAYS_SHOW_PROPS_DIALOG);
		app.start();
	}

	protected void simpleInitGame() {
		// Vertex positions for the mesh
		Vector3f[] vertexes = { new Vector3f(0, 0, 0), new Vector3f(1, 0, 0),
				new Vector3f(0, 1, 0), new Vector3f(1, 1, 0) };
		// Texture Coordinates for each position
		coordDelta = 1;
		Vector2f[] texCoords = { new Vector2f(0, 0),
				new Vector2f(coordDelta, 0), new Vector2f(0, coordDelta),
				new Vector2f(coordDelta, coordDelta) };
		// The indexes of Vertex/Normal/Color/TexCoord sets. Every 3
		// makes a triangle.
		int[] indexes = { 0, 1, 2, 1, 3, 2 };
		// Create the square
		square = new TriMesh("My Mesh",
				BufferUtils.createFloatBuffer(vertexes), null, null,
				BufferUtils.createFloatBuffer(texCoords), BufferUtils
						.createIntBuffer(indexes));
		// Point to the monkey image
		URL monkeyLoc = HelloKeyInput.class.getClassLoader().getResource(
				"jmetest/data/images/Monkey.tga");
		// Get my TextureState
		TextureState ts = display.getRenderer().createTextureState();
		// Get my Texture
		Texture t = TextureManager.loadTexture(monkeyLoc, Texture.MM_LINEAR,
				Texture.FM_LINEAR);
		// Set a wrap for my texture so it repeats
		t.setWrap(Texture.WM_WRAP_S_WRAP_T);
		// Set the texture to the TextureState
		ts.setTexture(t);
		// Assign the TextureState to the square
		square.setRenderState(ts);
		// Scale my square 10x larger
		square.setLocalScale(10);
		// Attach my square to my rootNode
		rootNode.attachChild(square);
		// Assign the "+" key on the keypad to the command "coordsUp"
		KeyBindingManager.getKeyBindingManager().set("coordsUp",
				KeyInput.KEY_ADD);
		// Adds the "u" key to the command "coordsUp"
		KeyBindingManager.getKeyBindingManager()
				.add("coordsUp", KeyInput.KEY_U);
		// Assign the "-" key on the keypad to the command "coordsDown"
		KeyBindingManager.getKeyBindingManager().set("coordsDown",
				KeyInput.KEY_SUBTRACT);
	}

	// Called every frame update
	protected void simpleUpdate() {
		// If the coordsDown command was activated
		if (KeyBindingManager.getKeyBindingManager().isValidCommand(
				"coordsDown", false)) {
			// Scale my texture down
			coordDelta -= .01f;
			// Get my square's texture array
			FloatBuffer stBuffer = square.getTextureBuffer(0, 0);
			// Change the values of the texture array
			stBuffer.put(2, coordDelta);
			stBuffer.put(5, coordDelta);
			stBuffer.put(6, coordDelta);
			stBuffer.put(7, coordDelta);
			// The texture coordinates are updated

		}

		// if the coordsUp command was activated
		if (KeyBindingManager.getKeyBindingManager().isValidCommand("coordsUp",
				false)) {
			// Scale my texture up
			coordDelta += .01f;
			// Assign each texture value manually.
			FloatBuffer stBuffer = square.getTextureBuffer(0, 0);
			// Change the values of the texture array
			stBuffer.put(2, coordDelta);
			stBuffer.put(5, coordDelta);
			stBuffer.put(6, coordDelta);
			stBuffer.put(7, coordDelta);
			// The texture coordinates are updated
		}
	}
}