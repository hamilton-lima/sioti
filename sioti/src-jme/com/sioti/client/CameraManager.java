package com.sioti.client;

import java.util.HashMap;

import com.jme.bounding.BoundingBox;
import com.jme.input.ChaseCamera;
import com.jme.input.thirdperson.ThirdPersonMouseLook;
import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.scene.Node;

public class CameraManager {

	public static ChaseCamera buildChaseCamera(Camera camera, Node target,
			float minDistance, float maxDistance) {

		Vector3f targetOffset = new Vector3f();
		targetOffset.y = ((BoundingBox) target.getWorldBound()).yExtent * 1.5f;
		HashMap props = new HashMap();
		props.put(ThirdPersonMouseLook.PROP_MAXROLLOUT, "20");
		props.put(ThirdPersonMouseLook.PROP_MINROLLOUT, "10");
		props.put(ThirdPersonMouseLook.PROP_MAXASCENT, "" + 45
				* FastMath.DEG_TO_RAD);
		props.put(ThirdPersonMouseLook.PROP_MOUSEBUTTON_FOR_LOOKING,
				new Integer(2));
		
		props.put(ChaseCamera.PROP_INITIALSPHERECOORDS, new Vector3f(5, 0,
				30 * FastMath.DEG_TO_RAD));
		props.put(ChaseCamera.PROP_TARGETOFFSET, targetOffset);
		ChaseCamera chaser = new ChaseCamera(camera, target, props);
		chaser.setMaxDistance(200);
		chaser.setMinDistance(80);

		return chaser;
	}

}
