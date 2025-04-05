package com.entities;

public class mobs {

	private int level;
	private double health;
	private double attack;

	public void heal(double damage) {
		if (health == 0) {
			System.out.println("die");
			return;
		}
		this.health -= damage;
	}

	public double getDamage() {
		return attack;
	}

	public mobs(int level, double health, double attack) {
		super();
		this.level = level;
		this.health = health;
		this.attack = attack;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public double getAttack() {
		return attack;
	}

	public void setAttack(double attack) {
		this.attack = attack;
	}

}
