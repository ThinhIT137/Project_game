package com.entities;

import java.io.IOException;

public class Boss extends mobs {

	public Boss(int level, double health, double attack, int speed, String file, int cols, int OffsetX, int OffsetY,
			int footOffset, int left, int[] location) throws IOException {
		super(level, health, attack, speed, file, cols, OffsetX, OffsetY, footOffset, left, location);
	}

	public void Combo(player p) {

	}
}
