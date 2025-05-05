package com.graphics;

public class Camera {
	private int x, y;
	private int screenWidth, screenHeight;
	private int mapWidth, mapHeight;

	public Camera(int screenWidth, int screenHeight, int mapWidth, int mapHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
	}

	public void update(int playerX, int playerY) {
		// Camera đi theo nhân vật
		x = playerX - screenWidth / 2;
		y = playerY - screenHeight / 2;

		// Xử lý giới hạn X
		x = Math.max(0, Math.min(x, mapWidth - screenWidth));

		// Xử lý giới hạn Y như bình thường
		y = Math.max(0, Math.min(y, mapHeight - screenHeight));
	}

	// Trả về vị trí đã dịch chuyển theo camera
	public int applyX(int worldX) {
		return worldX - x;
	}

	public int applyY(int worldY) {
		return worldY - y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
