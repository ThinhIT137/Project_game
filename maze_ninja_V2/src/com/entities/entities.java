package com.entities;

public interface entities {
	public double gravity = 1.5; // trọng lực

	public int getMaxHealth();

	public double getHealth();

	public void setHealth(double health);

	public int getLevel();

	public int getOx(); // tâm nhân vật

	public int getOy(); // tâm nhân vật

	public int getOffsetX(); // tâm cách đều 2 bên

	public int getOffsetY(); // tâm cách đầu

	public int getFootOffset(); // tâm cách chân

	public void stun(String name);
}
