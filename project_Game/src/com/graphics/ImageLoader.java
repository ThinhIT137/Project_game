package com.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;

import javax.imageio.ImageIO;

public class ImageLoader {
	private BufferedImage spriteSheet;
	private int frameWidth;
	private int frameHeight;
	private int cols;

	public ImageLoader(String spritePath, int cols) {
		try {
			this.spriteSheet = ImageIO.read(new File(spritePath));
			this.frameWidth = spriteSheet.getWidth();
			this.frameHeight = spriteSheet.getHeight();
			this.cols = cols;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage[] getFrames() {
		BufferedImage[] frames = new BufferedImage[cols];
		int singleFrameWidth = frameWidth / cols;
		for (int i = 0; i < cols; i++) {
			int x = i * singleFrameWidth;
			frames[i] = spriteSheet.getSubimage(x, 0, frameWidth / cols, frameHeight);
		}
		return frames;
	}

	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}

	public void setSpriteSheet(BufferedImage spriteSheet) {
		this.spriteSheet = spriteSheet;
	}

	public int getFrameWidth() {
		return frameWidth;
	}

	public void setFrameWidth(int frameWidth) {
		this.frameWidth = frameWidth;
	}

	public int getFrameHeight() {
		return frameHeight;
	}

	public void setFrameHeight(int frameHeight) {
		this.frameHeight = frameHeight;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

}
