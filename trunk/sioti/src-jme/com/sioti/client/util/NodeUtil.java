package com.sioti.client.util;

import java.util.Iterator;
import java.util.Stack;

import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.sioti.util.Log;

/**
 * 
 * represents the type
 * 
 * @author limajuni
 */
public class NodeUtil {

	public static float getHeight(Node node) {

		float top = Float.MIN_VALUE;
		float bottom = Float.MAX_VALUE;

		Vector3f[] foo = new Vector3f[3];
		Stack toInspect = new Stack();
		toInspect.push(node);

		while (!toInspect.isEmpty()) {
			Node current = (Node) toInspect.pop();
			Iterator iterator = current.getChildren().iterator();

			while (iterator.hasNext()) {
				Spatial element = (Spatial) iterator.next();

				if (element instanceof Node) {
					toInspect.push((Node) element);
				} else {
					if (element instanceof TriMesh) {
						TriMesh mesh = (TriMesh) element;

						for (int i = 0; i < mesh.getTriangleCount(); i++) {
							mesh.getTriangle(i, foo);
							Log.debug("foo[0].y=" + foo[0].y);

							if (foo[0].y > top)
								top = foo[0].y;
							if (foo[1].y > top)
								top = foo[1].y;
							if (foo[2].y > top)
								top = foo[2].y;

							if (foo[0].y < bottom)
								bottom = foo[0].y;
							if (foo[1].y < bottom)
								bottom = foo[1].y;
							if (foo[2].y < bottom)
								bottom = foo[2].y;
						}
					}
				} // if node

			}
		}

		float height = top - bottom;
		Log.debug("height = " + height);
		return height;
	}

}
