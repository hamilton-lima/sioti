package com.sioti.batch;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;

public class MapCreator {

	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void main(String[] args) throws InterruptedException,
			IOException {
		// This method ensures that all pixels have been loaded before returning
		// Image image = new ImageIcon("image.gif").getImage();
		BufferedImage image = ImageIO.read(new File("docs/mapa.bmp"));

		// Get the dimensions of the image; these will be non-negative
		int width = image.getWidth(null);
		int height = image.getHeight(null);

		// identify the colors
		char node = 'A';
		Map colors = new TreeMap();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {

				String hex = getColor(image, x, y);
				if (colors.get(hex) == null) {
					colors.put(hex, Character.valueOf(node++));
				}
			}
		}

		// show the colors
		Iterator iter = colors.keySet().iterator();
		while (iter.hasNext()) {
			Object key = iter.next();
			System.out.println(key);
			System.out.println(colors.get(key));
		}

		BufferedWriter writer = new BufferedWriter(new FileWriter(
				"docs/mapa.txt"));
		// write the file
		for (int y = 0; y < height; y++) {
			StringBuffer line = new StringBuffer(width);
			for (int x = 0; x < width; x++) {
				String hex = getColor(image, x, y);
				char toWrite = ((Character) colors.get(hex)).charValue();
				line.append(toWrite);
			}
			writer.write(line.toString());
			writer.write(Character.LINE_SEPARATOR);
		}

		writer.flush();
		writer.close();
	}

	private static String getColor(BufferedImage image, int x, int y) {
		int c = image.getRGB(x, y);
		int red = (c & 0x00ff0000) >> 16;
		int green = (c & 0x0000ff00) >> 8;
		int blue = c & 0x000000ff;
		String hex = Integer.toHexString(red) + Integer.toHexString(green)
				+ Integer.toHexString(blue);
		hex = hex.toUpperCase();
		return hex;
	}

}
