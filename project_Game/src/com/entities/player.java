package com.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.graphics.AnimationFrameData;
import com.graphics.ImageLoader;

public class player {

	private int level;
	private double health;
	private double attack;
	private int crit_chance;
	private double crit_damage;

	private int x, y, radius = 200, Ox, Oy;
	private ImageLoader imageLoader;
	private BufferedImage[] frames;
	private int frameStart;
	private int frameEnd;
	private int speed = 1;
	private int frameIndex;
	private boolean facingRight;

	Random rad = new Random();

	public int score() {
		int score = 0;

		// kill mob +=10;
		// kill boss +=100;

		return score;
	}

	public void level_up() {
		this.level++;
		this.health += 10.0;
		this.attack += 10.0;
		this.crit_chance += 1;
		this.crit_damage += 0.5;
	}

	public double getDamage() {
		int chance = rad.nextInt(100) + 1;
		if (chance < this.crit_chance)
			return this.crit_damage * this.attack;
		return this.attack;
	}

	public player(int level, Double health, Double attack, int crit_chance, Double crit_damage) {
		super();
		this.level = level;
		this.health = health;
		this.attack = attack;
		this.crit_chance = crit_chance;
		this.crit_damage = crit_damage;

		this.imageLoader = new ImageLoader("assets/sprites/player.png", 35);
		this.frames = imageLoader.getFrames();
		this.x = 100; // Vị trí ban đầu
		this.y = 200;
		this.Ox = (imageLoader.getFrameWidth() / imageLoader.getCols()) / 2 - 7;
		this.Oy = imageLoader.getFrameHeight() / 2 - 10;
		facingRight = false;
		setDefault();
	}

	public void setDefault() {
		frameIndex = 0;
	}

	public void isIdle() {
		AnimationFrameData set = new AnimationFrameData("player", "idel");
		frameStart = set.getFrameStart();
		frameEnd = set.getFrameEnd();
		nextFrame();
		System.out.println("Running... Frame: " + frameIndex + " | X: " + x);
	}

	public void moveRight() {
		AnimationFrameData set = new AnimationFrameData("player", "run");
		frameStart = set.getFrameStart();
		frameEnd = set.getFrameEnd();
		nextFrame();
		System.out.println("Running... Frame: " + frameIndex + " | X: " + x);
	}

	public void moveLeft() {
		AnimationFrameData set = new AnimationFrameData("player", "run");
		frameStart = set.getFrameStart();
		frameEnd = set.getFrameEnd();
		nextFrame();
		System.out.println("Running... Frame: " + frameIndex + " | X: " + x);
	}

	public void jump() { // get frame jump
		AnimationFrameData set = new AnimationFrameData("player", "jump");
		frameStart = set.getFrameStart();
		frameEnd = set.getFrameEnd();
		frameIndex = frameStart;
//		nextFrame();
		System.out.println("Running.. Frame: " + frameIndex + " | Y: " + y);
	}

	public void attack() { // get frame attack
		AnimationFrameData set = new AnimationFrameData("player", "attack");
		frameStart = set.getFrameStart();
		frameEnd = set.getFrameEnd();
		System.out.println("Running... Frame: " + frameIndex + " | X: " + x);
	}

	public void crouch() { // get frame crouch
		AnimationFrameData set = new AnimationFrameData("player", "crouch");
		frameStart = set.getFrameStart();
		frameEnd = set.getFrameEnd();
		System.out.println("Running... Frame: " + frameIndex + " | X: " + x);
	}

	public void nextFrame() {
		frameIndex++;
		if (frameIndex < frameStart || frameIndex > frameEnd) {
			frameIndex = frameStart;
		}
	}

	public BufferedImage getCurrentFrame() {
		BufferedImage frame = frames[frameIndex];

		if (!facingRight) {
			return frame; // Không lật khi đi phải
		}

		// Flip ảnh nhưng giữ nguyên vị trí nhân vật
		int width = frame.getWidth();
		int height = frame.getHeight();
		BufferedImage flipped = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = flipped.createGraphics();

		// Điều chỉnh tọa độ để nhân vật không bị lệch
		g.drawImage(frame, -30, 0, width - 30, height, width, 0, 0, height, null);
		g.dispose();

		return flipped;
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

	public int getCrit_chance() {
		return crit_chance;
	}

	public void setCrit_chance(int crit_chance) {
		this.crit_chance = crit_chance;
	}

	public double getCrit_damage() {
		return crit_damage;
	}

	public void setCrit_damage(double crit_damage) {
		this.crit_damage = crit_damage;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int d) {
		this.y = d;
	}

	public int getR() {
		return radius;
	}

	public void setR(int radius) {
		this.radius = radius;
	}

	public int getOx() {
		return Ox;
	}

	public void setOx(int Ox) {
		this.Ox = Ox;
	}

	public int getOy() {
		return Oy;
	}

	public void setOy(int Oy) {
		this.Oy = Oy;
	}

	public ImageLoader getImageLoader() {
		return imageLoader;
	}

	public void setImageLoader(ImageLoader imageLoader) {
		this.imageLoader = imageLoader;
	}

	public BufferedImage[] getFrames() {
		return frames;
	}

	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
	}

	public int getFrameStart() {
		return frameStart;
	}

	public void setFrameStart(int frameStart) {
		this.frameStart = frameStart;
	}

	public int getFrameEnd() {
		return frameEnd;
	}

	public void setFrameEnd(int frameEnd) {
		this.frameEnd = frameEnd;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getFrameIndex() {
		return frameIndex;
	}

	public void setFrameIndex(int frameIndex) {
		this.frameIndex = frameIndex;
	}

	public boolean isFacingRight() {
		return facingRight;
	}

	public void setFacingRight(boolean facingRight) {
		this.facingRight = facingRight;
	}

	public Random getRad() {
		return rad;
	}

	public void setRad(Random rad) {
		this.rad = rad;
	}

}
