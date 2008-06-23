package com.sioti.client;

import java.util.HashMap;

import com.jme.app.BaseGame;
import com.jme.input.ChaseCamera;
import com.jme.input.InputHandler;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.input.ThirdPersonHandler;
import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.pass.BasicPassManager;
import com.jme.renderer.pass.ShadowedRenderPass;
import com.jme.scene.Node;
import com.jme.scene.state.CullState;
import com.jme.scene.state.ZBufferState;
import com.jme.system.DisplaySystem;
import com.jme.system.JmeException;
import com.jme.util.Timer;
import com.jmex.terrain.TerrainPage;

public class Client extends BaseGame {

	private static final float DISTANCE_FROM_THE_GROUND = 0;

	private Vector3f normal = new Vector3f();

	private Player player;

	private TerrainPage page;

	protected InputHandler input;

	protected Timer timer;

	private Camera camera;

	private ChaseCamera chaser;

	private Node scene;

	private int width, height, depth, freq;

	private boolean fullscreen;

	private static ShadowedRenderPass shadowPass = new ShadowedRenderPass();

	private BasicPassManager passManager;

	public static void main(String[] args) {
		Client app = new Client();

		// app.setDialogBehaviour(NEVER_SHOW_PROPS_DIALOG, Client.class
		app.setDialogBehaviour(FIRSTRUN_OR_NOCONFIGFILE_SHOW_PROPS_DIALOG,
				Client.class.getClassLoader().getResource("media/sioti_splash.png"));

		app.start();
	}

	/**
	 * initializes the scene
	 * 
	 * @see com.jme.app.SimpleGame#initGame()
	 */
	protected void initGame() {
		display.setTitle("S I O T I");
		scene = new Node("Scene graph node");

		ZBufferState buf = display.getRenderer().createZBufferState();
		buf.setEnabled(true);
		buf.setFunction(ZBufferState.CF_LEQUAL);
		scene.setRenderState(buf);

		LightManager.addDefaultLight(scene, display);
		LightManager.addFog(scene, display);
		initCulling();

		page = TerrainManager.buildSampleTerrain(display);
		buildPlayer(page);

		chaser = CameraManager.buildChaseCamera(camera, player, 80, 200);
		scene.attachChild(page);
		scene.attachChild(player);
		buildPassManager();
		setupInput();

		// update the scene graph for rendering
		scene.updateGeometricState(1.0f, true);
		scene.updateRenderState();
	}

	private void setupInput() {
		HashMap handlerProps = new HashMap();
		handlerProps.put(ThirdPersonHandler.PROP_DOGRADUAL, "true");
		handlerProps.put(ThirdPersonHandler.PROP_TURNSPEED, ""
				+ (1.0f * FastMath.PI));
		handlerProps.put(ThirdPersonHandler.PROP_LOCKBACKWARDS, "false");
		handlerProps.put(ThirdPersonHandler.PROP_CAMERAALIGNEDMOVE, "true");
		input = new ThirdPersonHandler(player, camera, handlerProps);
		input.setActionSpeed(100f);
	}

	private void buildPlayer(TerrainPage terrain) {
		player = new Player("the player", display);
		player.setLocalTranslation(new Vector3f(0, 0, 0));
		player.getLocalTranslation().y = terrain.getHeight(player
				.getLocalTranslation().x, player.getLocalTranslation().z);

	}

	protected void initSystem() {
		// store the properties information
		width = properties.getWidth();
		height = properties.getHeight();
		depth = properties.getDepth();
		freq = properties.getFreq();
		fullscreen = properties.getFullscreen();

		try {
			display = DisplaySystem.getDisplaySystem(properties.getRenderer());
			display.setMinStencilBits(8);
			display.createWindow(width, height, depth, freq, fullscreen);

			camera = display.getRenderer().createCamera(width, height);
		} catch (JmeException e) {
			e.printStackTrace();
			System.exit(1);
		}

		// set the background to black
		display.getRenderer().setBackgroundColor(ColorRGBA.white);

		buildCamera();

		/** Get a high resolution timer for FPS updates. */
		timer = Timer.getTimer();

		display.getRenderer().setCamera(camera);

		KeyBindingManager.getKeyBindingManager().set("exit",
				KeyInput.KEY_ESCAPE);

	}

	private void buildCamera() {
		camera.setFrustumPerspective(45.0f, (float) width / (float) height, 1,
				5000);
		camera.setLocation(new Vector3f(200, 1000, 200));

		// we changed the camera location/frustum
		camera.update();
	}

	protected void render(float arg0) {
		display.getRenderer().clearBuffers();
		display.getRenderer().draw(scene);
		passManager.renderPasses(display.getRenderer());
	}

	protected void update(float interpolation) {

		// update the time to get the framerate
		timer.update();
		interpolation = timer.getTimePerFrame();
		// update the keyboard input (move the player around)
		input.update(interpolation);
		// update the chase camera to handle the player moving around.
		chaser.update(interpolation);
		// if escape was pressed, we exit
		if (KeyBindingManager.getKeyBindingManager().isValidCommand("exit")) {
			finished = true;
		}

		// We don't want the chase camera to go below the world, so always keep
		// it 2 units above the level.
		if (camera.getLocation().y < (page.getHeight(camera.getLocation()) + 2)) {
			camera.getLocation().y = page.getHeight(camera.getLocation()) + 2;
			camera.update();
		}

		// player.getLocalTranslation().y = page.getHeight(player
		// .getLocalTranslation().x, player.getLocalTranslation().z)
		// + (player.getHeight() / 2);

		float characterMinHeight = page.getHeight(player.getLocalTranslation())
				+ page.getLocalTranslation().y + (player.getHeight() / 2)
				+ DISTANCE_FROM_THE_GROUND;

		if (!Float.isInfinite(characterMinHeight)
				&& !Float.isNaN(characterMinHeight)) {
			player.getLocalTranslation().y = characterMinHeight;
		}

		// inclina o personagem de acordo com o terreno
		//
		// get the normal of the terrain at our current location. We then apply
		// it to the up vector
		// of the player.
		page.getSurfaceNormal(player.getLocalTranslation(), normal);
		if (normal != null) {
			player.rotateUpTo(normal);
		}

		// Because we are changing the scene (moving the skybox and player) we
		// need to update
		// the graph.
		scene.updateGeometricState(interpolation, true);
	}

	private void initCulling() {
		CullState cs = display.getRenderer().createCullState();
		cs.setCullMode(CullState.CS_BACK);
		cs.setEnabled(true);
		scene.setRenderState(cs);
	}

	private void buildPassManager() {
		passManager = new BasicPassManager();

		shadowPass.add(scene);
		shadowPass.addOccluder(player);
		shadowPass.setRenderShadows(true);
		shadowPass.setEnabled(true);
		shadowPass.setLightingMethod(ShadowedRenderPass.MODULATIVE);
		passManager.add(shadowPass);
	}

	/**
	 * will be called if the resolution changes
	 * 
	 * @see com.jme.app.SimpleGame#reinit()
	 */
	protected void reinit() {
		display.recreateWindow(width, height, depth, freq, fullscreen);
	}

	/**
	 * close the window and also exit the program.
	 */
	protected void quit() {
		super.quit();
		System.exit(0);
	}

	protected void cleanup() {
	}

}
