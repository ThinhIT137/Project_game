package com.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.graphics.AnimationFrameData;
import com.graphics.CollisionManager;
import com.graphics.ImageLoader;
import com.graphics.MapLoader;

public class mobs implements entities {

	private int level;
	private double health;
	private int MaxHealth;
	private double attack;

	private int OffsetX;
	private int OffsetY;
	private int FootOffset;

	private int startX, startY; // Vị trí ban đầu của mob
	private int x, y, radius = 160;
	int centerX, centerY; // từ cạnh trên trái đến tâm thêm centerX và centerY
	private int speed;
	private ImageLoader imageLoader;
	private BufferedImage[] frames;
	private int frameStart;
	private int frameEnd;
	private int frameIndex;
	private int left; // cho ảnh sang bên trái khi quay ảnh
	private boolean facingRight;
	private boolean isReturning;
	private boolean isDying = false;
	private boolean isHit = false;

	private int Delay = 1000;
	private boolean stunned = false; // bị đánh
	private long lastTime = 0;

	public mobs(int level, double health, double attack, int speed, String file, int cols, int offsetX, int offsetY,
			int footOffset, int left, int[] location) throws IOException {
		super();
		this.level = level;
		this.health = health;
		this.MaxHealth = (int) health;
		this.attack = attack;
		this.speed = speed;

		this.OffsetX = offsetX;
		this.OffsetY = offsetY;
		this.FootOffset = footOffset;

		this.startX = location[0];
		this.startY = location[1];
		this.x = location[0];
		this.y = location[1];
		isReturning = false;

		facingRight = false;
		this.imageLoader = new ImageLoader(file, cols);
		this.frames = imageLoader.getFrames();
		this.left = left;
		frameIndex = 0;
	}

	public void setDefault() {
		frameIndex = 0;
	}

	public void Update(String mob, player p, int d, int range, CollisionManager check, MapLoader map) {
		// range: là khoảng cách điểm gây sát thương
		// d: hoạt ảnh gây sát thương
		if (stunned && System.currentTimeMillis() - lastTime < Delay) {
			return;
		}
		if (stunned) {
			stunned = false;
		}
		double dx = getOx() - p.getOx();
		double dy = getOy() - p.getOy();
		double distance = Math.sqrt(dx * dx + dy * dy);
		if (distance < range) {
			Attack(mob, p, d, range);
		} else if (!isReturning && Math.abs(dx) <= 160 && Math.abs(dy) <= 46) {
			move(mob, p, check, map);
		} else {
			// bật chế độ quay về nếu quá xa hoặc đang rơi
			isReturning = distance > 160 || check.checkCollision(this, "down", map) == 0;
			if (isReturning) {
				moveBackToStart(mob, check, map);
			} else {
				idle(mob);
			}
		}
	}

	public void idle(String mobs) { // đứng khi không có người chơi
		setAnimation(mobs, "idle");
		nexFrame();
	}

	public void move(String mobs, player p, CollisionManager check, MapLoader map) { // left right
		setAnimation(mobs, "run");
		// chỉ chạy khi đang đứng trên block
		if (check.checkCollision(this, "down", map) != 0) {
			if (p.getOx() < getOx()) {
				// chạy sang trái
				facingRight = true;
				// nếu không va chạm trái => thử dịch sang trái
				if (check.checkCollision(this, "left", map) == 0) {
					x -= speed;
					// sau khi dịch, nếu dưới chân không còn block => rollback
					if (check.checkCollision(this, "down", map) == 0) {
						x += speed;
					}
				}
			} else {
				// chạy sang phải
				facingRight = false;
				if (check.checkCollision(this, "right", map) == 0) {
					x += speed;
					if (check.checkCollision(this, "down", map) == 0) {
						x -= speed;
					}
				}
			}
		} else {
			// đang rơi hoặc lơ lửng ⇒ không di chuyển
			setAnimation(mobs, "idle");
		}
		nexFrame(); // Cập nhật frame animation
	}

	public void moveBackToStart(String mobs, CollisionManager check, MapLoader map) {
		// nếu đã về gần start, reset
		if (Math.abs(x - startX) <= speed && Math.abs(y - startY) <= speed) {
			x = startX;
			y = startY;
			isReturning = false;
			setAnimation(mobs, "idle");
		} else {
			// chỉ di chuyển khi đang đứng trên block
			if (check.checkCollision(this, "down", map) != 0) {
				setAnimation(mobs, "run");
				if (startX < x) {
					// quay về trái
					facingRight = true;
					if (check.checkCollision(this, "left", map) == 0) {
						x -= speed;
						if (check.checkCollision(this, "down", map) == 0) {
							x += speed;
							setAnimation(mobs, "idle");
						}
					}
				} else {
					// quay về phải
					facingRight = false;
					if (check.checkCollision(this, "right", map) == 0) {
						x += speed;
						if (check.checkCollision(this, "down", map) == 0) {
							x -= speed;
							setAnimation(mobs, "idle");
						}
					}
				}
			} else {
				setAnimation(mobs, "idle");
			}
		}

		nexFrame();
	}

	public void Attack(String mobs, player p, int d, int range) { // d: hoạt ảnh gây sát thương
		setAnimation(mobs, "attack");
		AnimationFrameData P_frames = new AnimationFrameData("player", "crouch");
		int P_start = P_frames.getFrameStart();
		P_frames = new AnimationFrameData("player", "attack");
		int p_end = P_frames.getFrameEnd();
		if (p.getFrameIndex() < P_start || p.getFrameIndex() > p_end)
			if (frameIndex == frameStart + d) {
				if (Math.abs(getOx() - p.getOx()) <= range) {
					if (p.getHealth() < attack || p.getHealth() <= 0)
						p.setHealth(0);
					else
						p.setHealth(p.getHealth() - attack);
					System.out.println("Player bị đánh! Máu còn: " + p.getHealth());
				}
			}
		nexFrame();
	}

	public void StuneFrames(String mobs) {
		setAnimation(mobs, "stun");
		frameIndex = frameEnd;
	}

	private void setAnimation(String entity, String action) {
		AnimationFrameData mob = new AnimationFrameData(entity, action);
		frameStart = mob.getFrameStart();
		frameEnd = mob.getFrameEnd();
	}

	public void nexFrame() {
		frameIndex++;
		if (frameIndex > frameEnd || frameIndex < frameStart) {
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
		g.drawImage(frame, left, 0, width + left, height, width, 0, 0, height, null);
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

	public void setY(int y) {
		this.y = y;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
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

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getCenterX() {
		return centerX;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public boolean isDying() {
		return isDying;
	}

	public void setDying(boolean isDying) {
		this.isDying = isDying;
	}

	public boolean isHit() {
		return isHit;
	}

	public void setHit(boolean isHit) {
		this.isHit = isHit;
	}

	@Override
	public int getOx() {
		return this.x + centerX;
	}

	@Override
	public int getOy() {
		return this.y + centerY;
	}

	@Override
	public int getOffsetX() {
		return OffsetX;
	}

	@Override
	public int getOffsetY() {
		return OffsetY;
	}

	@Override
	public int getFootOffset() {
		return FootOffset;
	}

	@Override
	public int getMaxHealth() {
		return MaxHealth;
	}

	public double getHealth() {
		return health;
	}

	@Override
	public void stun(String name) {
		StuneFrames(name);
		stunned = true;
		lastTime = System.currentTimeMillis();
	}
}
