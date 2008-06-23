package com.sioti.jme_samples;

import com.jme.app.SimpleGame;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Sphere;
import com.jme.scene.Node;
import com.jme.math.Vector3f;
import com.jme.math.Quaternion;
import com.jme.math.FastMath;
import com.jme.animation.SpatialTransformer;
import com.jme.light.PointLight;
import com.jme.light.SimpleLightNode;
import com.jme.renderer.ColorRGBA;

/**
 * Started Date: Jul 21, 2004<br>
 * <br>
 * 
 * This class demonstrates animation via a controller, as well as LightNode.
 * 
 * @author Jack Lindamood
 */
public class HelloAnimation extends SimpleGame {
	public static void main(String[] args) {
		HelloAnimation app = new HelloAnimation();
		app.setDialogBehaviour(SimpleGame.ALWAYS_SHOW_PROPS_DIALOG);
		app.start();
	}

	protected void simpleInitGame() {
		Sphere s = new Sphere("My sphere", 30, 30, 5);
		// I will rotate this pivot to move my light
		Node pivot = new Node("Pivot node");
		// This light will rotate around my sphere. Notice I don't
		// give it a position
		PointLight pl = new PointLight();
		// Color the light red
		pl.setDiffuse(ColorRGBA.red);
		// Enable the light
		pl.setEnabled(true);
		// Remove the default light and attach this one
		lightState.detachAll();
		lightState.attach(pl);
		// This node will hold my light
		SimpleLightNode ln = new SimpleLightNode("A node for my pointLight", pl);
		// I set the light's position thru the node
		ln.setLocalTranslation(new Vector3f(0, 10, 0));
		// I attach the light's node to my pivot
		pivot.attachChild(ln);
		// I create a box and attach it too my lightnode.
		// This lets me see where my light is
		Box b = new Box("Blarg", new Vector3f(-.3f, -.3f, -.3f), new Vector3f(
				.3f, .3f, .3f));
		ln.attachChild(b);
		// I create a controller to rotate my pivot
		SpatialTransformer st = new SpatialTransformer(1);
		// I tell my spatial controller to change pivot
		st.setObject(pivot, 0, -1);
		// Assign a rotation for object 0 at time 0 to rotate 0
		// degrees around the z axis
		Quaternion x0 = new Quaternion();
		x0.fromAngleAxis(0, new Vector3f(0, 0, 1));
		st.setRotation(0, 0, x0);
		// Assign a rotation for object 0 at time 2 to rotate 180
		// degrees around the z axis
		Quaternion x180 = new Quaternion();
		x180.fromAngleAxis(FastMath.DEG_TO_RAD * 180, new Vector3f(0, 0, 1));
		st.setRotation(0, 2, x180);
		// Assign a rotation for object 0 at time 4 to rotate 360
		// degrees around the z axis
		Quaternion x360 = new Quaternion();
		x360.fromAngleAxis(FastMath.DEG_TO_RAD * 360, new Vector3f(0, 0, 1));
		st.setRotation(0, 4, x360);
		// Prepare my controller to start moving around
		st.interpolateMissing();
		// Tell my pivot it is controlled by st
		pivot.addController(st);
		// Attach pivot and sphere to graph
		rootNode.attachChild(pivot);
		rootNode.attachChild(s);
	}
}