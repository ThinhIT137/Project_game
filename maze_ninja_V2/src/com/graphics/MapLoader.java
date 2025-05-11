package com.graphics;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapLoader {
	private int[][] map;
	private int tileSize = 32;
	int rows = 0, cols = 0;

	private int[] Player = new int[2];
	private List<int[]> Block = new ArrayList<>();
	private HashMap<int[], String> mobs = new HashMap<>();
	private BufferedImage imageMap;

	public void loadMap(String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line;

		while ((line = reader.readLine()) != null) {
			rows++;
			cols = Math.max(cols, line.length());
		}
		map = new int[rows][cols];
		reader.close();

		reader = new BufferedReader(new FileReader(filename));
		int row = 0;
		while ((line = reader.readLine()) != null) {
			for (int col = 0; col < line.length(); col++) {
				char c = line.charAt(col);
				switch (c) {
				case '1': {
					map[row][col] = 1;
					int[] b = new int[2];
					b[0] = col * tileSize;
					b[1] = row * tileSize;
					Block.add(b);
					break;
				}
				case '3': {
					map[row][col] = 3;
					break;
				}
				case 'P': {
					map[row][col] = 0;
					Player[0] = col * tileSize;
					Player[1] = row * tileSize - 14;
					break;
				}
				case 'd': {
					map[row][col] = 0;
					int[] mob = new int[2];
					mob[0] = col * tileSize;
					mob[1] = row * tileSize - 14;
					mobs.put(mob, "d");
					break;
				}
				case 'D': {
					map[row][col] = 0;
					int[] mob = new int[2];
					mob[0] = col * tileSize;
					mob[1] = row * tileSize - 14;
					mobs.put(mob, "D");
					break;
				}
				case 'B': {
					map[row][col] = 0;
					int[] mob = new int[2];
					mob[0] = col * tileSize;
					mob[1] = row * tileSize - 14;
					mobs.put(mob, "B");
					break;
				}
				default: {
					map[row][col] = 0;
					break;
				}
				}
			}
			row++;
		}
		reader.close();
	}

	public MapLoader(String filename) throws IOException {
		loadMap(filename);
	}

	public int[][] getMap() {
		return map;
	}

	public void setMap(int[][] map) {
		this.map = map;
	}

	public int getTileSize() {
		return tileSize;
	}

	public void setTileSize(int tileSize) {
		this.tileSize = tileSize;
	}

	public int getPlayer(int i) {
		return Player[i];
	}

	public int[] getPlayer() {
		return Player;
	}

	public void setPlayer(int[] player) {
		Player = player;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public int getWidth() {
		return cols * tileSize;
	}

	public int getHeight() {
		return rows * tileSize;
	}

	public BufferedImage getImageMap() {
		return imageMap;
	}

	public void setImageMap(BufferedImage imageMap) {
		this.imageMap = imageMap;
	}

	public List<int[]> getBlock() {
		return Block;
	}

	public void setBlock(List<int[]> block) {
		Block = block;
	}

	public int getTitle(int i, int j) {
		return map[i][j];
	}

	public HashMap<int[], String> getMobs() {
		return mobs;
	}

	public void setMobs(HashMap<int[], String> mobs) {
		this.mobs = mobs;
	}

}
