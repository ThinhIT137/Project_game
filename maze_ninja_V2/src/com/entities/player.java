package com.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.graphics.AnimationFrameData;
import com.graphics.ImageLoader;

public class player implements entities {

	private int level;
	private double health;
	private int MaxHealth;
	private double attack;
	private int score = 0;
	private double velocityY = 0;
	private int demMob = 0;

	private int x, y, radius = 160;
	private ImageLoader imageLoader;
	private BufferedImage[] frames;
	private int frameStart;
	private int frameEnd;
	private int frameIndex;
	private boolean facingRight; // kiểm tra để đảo hoạt ảnh

	public void level_up() { // tăng cấp
		int sc = score / 10;
		while (sc > level) {
			level++;
			this.MaxHealth += 10;
			this.health += 10.0; // Tăng máu
			this.attack += 10.0; // Tăng sát thương
		}
	}

	public player(int level, double health, double attack, int[] location, String file, int size) throws IOException {
		super();
		this.level = level;
		this.health = health;
		this.MaxHealth = (int) health;
		this.attack = attack;
		this.imageLoader = new ImageLoader(file, size);
		this.frames = imageLoader.getFrames();
		this.x = location[0];
		this.y = location[1];
		facingRight = false;
		setDefault();
	}

	public void setDefault() { // set frame mặc định
		frameIndex = 0;
	}

	public void move() { // frame left right
		setAnimation("player", "run");
		nextFrame();
		System.out.println("Running... Frame: " + frameIndex + " | X: " + x + " | Y: " + y);
	}

	public void attack() { // frame attack
		setAnimation("player", "attack");
		System.out.println("Running... Frame: " + frameIndex + " | X: " + x + " | Y: " + y);
	}

	public void attack_damage(entities mob, String name_mobs) { // damage attack
		if (frameIndex == 18 || frameIndex == 22) {
			if (Math.abs(getOx() - mob.getOx()) <= 31 && Math.abs(getOy() - mob.getOy()) <= 5) {
				mob.stun(name_mobs);
				if (mob.getHealth() < attack || mob.getHealth() <= 0)
					mob.setHealth(0);
				else
					mob.setHealth(mob.getHealth() - attack);
				System.out.println("Quái bị đánh! Máu còn: " + mob.getHealth());
				demMob++;
				if (demMob == 5) {
					health = Math.min(health + 20 * (double) MaxHealth, (double) MaxHealth);
					demMob = 0;
				}
				System.out.println("Quái bị đánh! Máu còn: " + mob.getHealth() + " | Player HP: " + health
						+ " | Frame: " + frameIndex);
			}
		}
	}

	public void crouch() { // frame ngồi
		setAnimation("player", "crouch");
		System.out.println("Running... Frame: " + frameIndex + " | X: " + x + " | Y: " + y);
	}

	private void setAnimation(String entity, String action) { // set frame start và end
		AnimationFrameData set = new AnimationFrameData(entity, action);
		frameStart = set.getFrameStart();
		frameEnd = set.getFrameEnd();
	}

	private void nextFrame() { // frame kế tiếp
		frameIndex++;
		if (frameIndex < frameStart || frameIndex > frameEnd) {
			frameIndex = frameStart;
		}
	}

	public BufferedImage getCurrentFrame() { // set frame
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
		g.drawImage(frame, -10, 0, width - 10, height, width, 0, 0, height, null);
		g.dispose();

		return flipped;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
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

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public double getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}

	@Override
	public int getOx() {
		return this.x + 39;
	}

	@Override
	public int getOy() {
		return this.y + 33;
	}

	@Override
	public int getOffsetX() {
		return 14;
	}

	@Override
	public int getOffsetY() {
		return 13;
	}

	@Override
	public int getFootOffset() {
		return 14;
	}

	@Override
	public int getMaxHealth() {
		return MaxHealth;
	}

	public double getHealth() {
		return health;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int nextScore(int score) {
		int sc = score % 10;
		return sc == 0 ? score + 10 : ((int) (score / 10) * 10) + 10;
	}

	@Override
	public void stun(String name) {
	}

}
