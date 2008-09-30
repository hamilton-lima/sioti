package com.sioti.map;

import java.util.Map;

import pulpcore.Stage;
import pulpcore.animation.Fixed;
import pulpcore.image.CoreGraphics;
import pulpcore.image.CoreImage;
import pulpcore.math.Tuple2i;
import pulpcore.sprite.ImageSprite;
import pulpcore.sprite.Sprite;

import com.sioti.Layer1ImageManager;
import com.sioti.service.Layer1DataService;
import com.sioti.service.Setup;
import com.sioti.util.Log;

public class SpriteMap extends Sprite {

	private Layer1DataService layer1DataService;

	private ImageSprite character;

	private String buffer = null;

	private Tuple2i viewPortStart;

	private ChunkOfMap chunk;

	private int maxLinesInView;

	private int maxColumnsInView;

	private boolean showCoords;

	public SpriteMap(int x, int y, int width, int height, Tuple2i startPoint) {
		super(x, y, width, height);

		this.viewPortStart = setStartPointInTheCenter(startPoint);
		this.maxColumnsInView = Stage.getWidth()
				/ Layer1ImageManager.TILE_WIDTH;
		this.maxLinesInView = Stage.getHeight()
				/ Layer1ImageManager.TILE_HEIGHT;
		this.showCoords = false;
	}

	private Tuple2i setStartPointInTheCenter(Tuple2i startPoint) {
		int centerX = Stage.getWidth() / Layer1ImageManager.TILE_WIDTH / 2;
		int centerY = Stage.getHeight() / Layer1ImageManager.TILE_HEIGHT / 2;

		Tuple2i result = new Tuple2i(startPoint.x - centerX, startPoint.y
				- centerY);
		return result;
	}

	private static final double MARGIN_TO_SCROLL_MAP = 100;

	private static final int SKIP_TO_SCROLL_MAP = 1;

	private static final int COORD_H_SPACE = 5;

	private static final int COORD_V_SPACE = 20;

	private static final boolean SHOW_LOG_DETAILS = false;

	public void update(int elapsedTime) {

		if (character.x.getAsInt() < MARGIN_TO_SCROLL_MAP) {
			viewPortStart.x -= SKIP_TO_SCROLL_MAP;
			character.x.set(character.x.getAsInt() + SKIP_TO_SCROLL_MAP);
			setDirty(true);
		}

		if (character.getViewY() < MARGIN_TO_SCROLL_MAP) {
			viewPortStart.y = viewPortStart.y - SKIP_TO_SCROLL_MAP;
			character.y.set(character.y.getAsInt() + SKIP_TO_SCROLL_MAP);
			setDirty(true);
		}

		if ((Stage.getWidth() - character.getViewX() - character.getImage()
				.getWidth()) < MARGIN_TO_SCROLL_MAP) {
			viewPortStart.x = viewPortStart.x + SKIP_TO_SCROLL_MAP;
			character.x.set(character.x.getAsInt() - SKIP_TO_SCROLL_MAP);
			setDirty(true);
		}

		if ((Stage.getHeight() - character.getViewY() - character.getImage()
				.getHeight()) < MARGIN_TO_SCROLL_MAP) {
			viewPortStart.y = viewPortStart.y + SKIP_TO_SCROLL_MAP;
			character.y.set(character.y.getAsInt() - SKIP_TO_SCROLL_MAP);
			setDirty(true);
		}

	}

	protected void drawSprite(CoreGraphics g) {
		if (needsUpdate()) {
			getData();
		}

		Tuple2i draw = new Tuple2i(0, 0);
		Tuple2i view = new Tuple2i(viewPortStart.x, viewPortStart.y);

		Tuple2i start = new Tuple2i(viewPortStart.x - chunk.getX(),
				viewPortStart.y - chunk.getY());

		Tuple2i max = new Tuple2i(maxColumnsInView + start.x, maxLinesInView
				+ start.y);

		Map images = Layer1ImageManager.getInstance().getImages();
		String key = null;

		if (Log.DEBUG & SHOW_LOG_DETAILS) {
			logDrawDetails(start, max);
		}

		for (int i = start.y; i < max.y; i++) {
			for (int j = start.x; j < max.x; j++) {
				key = chunk.getData()[i].substring(j, j + 1);
				g.drawImage((CoreImage) images.get(key), draw.x, draw.y);

				if (showCoords) {
					g.drawString(Integer.toString(view.x), draw.x
							+ COORD_H_SPACE, draw.y + COORD_H_SPACE);
					g.drawString(Integer.toString(view.y), draw.x
							+ COORD_H_SPACE, draw.y + COORD_V_SPACE
							+ COORD_H_SPACE);
				}

				draw.x += Layer1ImageManager.TILE_WIDTH;
				view.x++;
			}
			draw.y += Layer1ImageManager.TILE_HEIGHT;
			draw.x = 0;

			view.y++;
			view.x = viewPortStart.x;
		}

	}

	private void logDrawDetails(Tuple2i start, Tuple2i max) {

		Log.debug("---------");
		Log.debug("chunk.getData() lines       : " + chunk.getData().length);
		Log.debug("chunk.getData()[0] length() : "
				+ chunk.getData()[0].length());
		Log.debug("coords in array, start : " + start.x + "," + start.y);
		Log.debug("coords in array, max   : " + max.x + "," + max.y);
		Log.debug("viewport : " + viewPortStart.x + "," + viewPortStart.y);
		Log.debug("chunk    : " + chunk.getX() + "," + chunk.getY());

	}

	private void getData() {
		Log.debug("spritemap call to get layer1");
		if (layer1DataService != null && character != null) {
			buffer = layer1DataService
					.getData(viewPortStart.x, viewPortStart.y);
			Log.debug("spritemap result chars from call to get layer1: "
					+ buffer.length());
			try {
				this.chunk = new ChunkOfMap(buffer, Setup.getInstance()
						.getChunkWidth());
			} catch (Exception e) {
				Log.error(e, "invalid data of map");
			}
		}
	}

	// TODO add a margin to dont stop the char on the reload ?
	private boolean needsUpdate() {
		if (chunk == null) {
			return true;
		}

		if (viewPortStart.x < chunk.getX()) {
			return true;
		}

		if (viewPortStart.y < chunk.getY()) {
			return true;
		}

		Tuple2i start = new Tuple2i(viewPortStart.x - chunk.getX(),
				viewPortStart.y - chunk.getY());

		Tuple2i max = new Tuple2i(maxColumnsInView + start.x, maxLinesInView
				+ start.y);

		if (max.x > chunk.getData()[0].length()) {
			return true;
		}

		if (max.y > chunk.getData().length) {
			return true;
		}

		return false;
	}

	public Layer1DataService getLayer1DataService() {
		return layer1DataService;
	}

	public void setLayer1DataService(Layer1DataService layer1DataService) {
		this.layer1DataService = layer1DataService;
	}

	// set the character
	public void setCharacter(ImageSprite character) {
		this.character = character;
	}

	public boolean isShowCoords() {
		return showCoords;
	}

	public void setShowCoords(boolean showCoords) {
		this.showCoords = showCoords;
	}

}
