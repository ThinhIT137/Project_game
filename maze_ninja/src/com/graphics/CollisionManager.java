package com.graphics;

import com.entities.entities;

public class CollisionManager { // va chạm

	public CollisionManager() {
		super();
	}

	public int checkCollision(entities Entity, String direction, MapLoader map) {
		int tileSize = map.getTileSize(); // Ví dụ: 32
		int Px, Py;
		int offsetX = Entity.getOffsetX();
		int offsetY = Entity.getOffsetY();
		int footOffset = Entity.getFootOffset();

		switch (direction) {
		case "right":
			Px = Entity.getOx() + offsetX;
			Py = Entity.getOy();
			break;
		case "left":
			Px = Entity.getOx() - offsetX;
			Py = Entity.getOy();
			break;
		case "up":
			Px = Entity.getOx();
			Py = Entity.getOy() - offsetY;
			break;
		case "down":
			Px = Entity.getOx();
			Py = Entity.getOy() + footOffset;
			break;
		default:
			Px = Entity.getOx();
			Py = Entity.getOy();
			break;
		}

		// Chuyển từ pixel sang tile
		int tileX = Px / tileSize;
		int tileY = Py / tileSize;

		int[][] mapData = map.getMap(); // [row][col] = [y][x]
		if (tileY >= 0 && tileY < mapData.length && tileX >= 0 && tileX < mapData[0].length) {
			if (mapData[tileY][tileX] == 1) {
				return 1; // Va chạm với block không đi được
			} else if (mapData[tileY][tileX] == 3) {
				return 3; // rơi vào vực
			}
		}
		return 0; // không có block
	}

}
