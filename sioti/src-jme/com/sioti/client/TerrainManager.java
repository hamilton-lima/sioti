package com.sioti.client;

import javax.swing.ImageIcon;

import com.jme.image.Texture;
import com.jme.math.Vector3f;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;
import com.jmex.terrain.TerrainPage;
import com.jmex.terrain.util.FaultFractalHeightMap;
import com.jmex.terrain.util.ProceduralTextureGenerator;

public class TerrainManager {

	public static TerrainPage buildSampleTerrain(DisplaySystem display) {

		FaultFractalHeightMap heightMap = new FaultFractalHeightMap(257, 32, 0,
				255, 0.75f);
		Vector3f terrainScale = new Vector3f(10, 1, 10);
		heightMap.setHeightScale(0.001f);
		TerrainPage page = new TerrainPage("Terrain", 33, heightMap.getSize(),
				terrainScale, heightMap.getHeightMap(), false);

		page.setDetailTexture(1, 16);

		ProceduralTextureGenerator pt = new ProceduralTextureGenerator(
				heightMap);
		pt.addTexture(new ImageIcon(TerrainManager.class.getClassLoader()
				.getResource("media/texture/grassb.png")), -128, 0, 128);
		pt.addTexture(new ImageIcon(TerrainManager.class.getClassLoader()
				.getResource("media/texture/dirt.jpg")), 0, 128, 255);
		pt.addTexture(new ImageIcon(TerrainManager.class.getClassLoader()
				.getResource("media/texture/highest.jpg")), 128, 255, 384);

		pt.createTexture(512);

		TextureState ts = display.getRenderer().createTextureState();
		ts.setEnabled(true);
		Texture t1 = TextureManager.loadTexture(pt.getImageIcon().getImage(),
				Texture.MM_LINEAR_LINEAR, Texture.FM_LINEAR, true);
		ts.setTexture(t1, 0);

		Texture t2 = TextureManager.loadTexture(TerrainManager.class
				.getClassLoader().getResource("media/texture/detail.jpg"),
				Texture.MM_LINEAR_LINEAR, Texture.FM_LINEAR);
		ts.setTexture(t2, 1);
		t2.setWrap(Texture.WM_WRAP_S_WRAP_T);

		t1.setApply(Texture.AM_COMBINE);
		t1.setCombineFuncRGB(Texture.ACF_MODULATE);
		t1.setCombineSrc0RGB(Texture.ACS_TEXTURE);
		t1.setCombineOp0RGB(Texture.ACO_SRC_COLOR);
		t1.setCombineSrc1RGB(Texture.ACS_PRIMARY_COLOR);
		t1.setCombineOp1RGB(Texture.ACO_SRC_COLOR);
		t1.setCombineScaleRGB(1.0f);

		t2.setApply(Texture.AM_COMBINE);
		t2.setCombineFuncRGB(Texture.ACF_ADD_SIGNED);
		t2.setCombineSrc0RGB(Texture.ACS_TEXTURE);
		t2.setCombineOp0RGB(Texture.ACO_SRC_COLOR);
		t2.setCombineSrc1RGB(Texture.ACS_PREVIOUS);
		t2.setCombineOp1RGB(Texture.ACO_SRC_COLOR);
		t2.setCombineScaleRGB(1.0f);
		page.setRenderState(ts);

		return page;
	}

}
