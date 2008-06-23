package com.sioti.client;

import com.jme.light.DirectionalLight;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.state.FogState;
import com.jme.scene.state.LightState;
import com.jme.system.DisplaySystem;

public class LightManager {

	public static void addDefaultLight(Node node, DisplaySystem display) {

		DirectionalLight light = new DirectionalLight();
		light.setDiffuse(new ColorRGBA(1.0f, 1.0f, 1.0f, 1.0f));
		light.setAmbient(new ColorRGBA(0.5f, 0.5f, 0.5f, .5f));
		light.setDirection(new Vector3f(1, -1, 0));
		light.setShadowCaster(true);
		light.setEnabled(true);

		/** Attach the light to a lightState and the lightState to rootNode. */
		LightState lightState = display.getRenderer().createLightState();
		lightState.setEnabled(true);
		lightState.setGlobalAmbient(new ColorRGBA(.2f, .2f, .2f, 1f));
		lightState.attach(light);
		node.setRenderState(lightState);
	}

	public static void addFog(Node node, DisplaySystem display) {
		FogState fs = display.getRenderer().createFogState();
		fs.setDensity(0.5f);
		fs.setEnabled(true);
		fs.setColor(new ColorRGBA(0.5f, 0.5f, 0.5f, 0.5f));
		fs.setEnd(1000);
		fs.setStart(500);
		fs.setDensityFunction(FogState.DF_LINEAR);
		fs.setApplyFunction(FogState.AF_PER_VERTEX);
		node.setRenderState(fs);
	}

}
