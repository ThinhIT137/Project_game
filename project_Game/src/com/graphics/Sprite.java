package com.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.entities.Boss;
import com.entities.entities;
import com.entities.mobs;
import com.entities.player;

import menu.GameOverPanel;

public class Sprite extends JPanel implements Runnable {

	private GameOverPanel gameOverPanel;
	private JButton Settings;

	private player Player;
	private KeyControl keyControl;

	private List<mobs> mob = new ArrayList<>();
	private MapLoader map;
	private BufferedImage backGround;
	private BufferedImage block;

	private Camera cam;
	private CollisionManager check = new CollisionManager();

	private long lastTime = 0;
	private long Delay = 1000; // 1 giây
	private boolean jumpPressed = false;
	private boolean isGameOver = false;

	public Sprite() throws IOException {
		map = new MapLoader("assets/map/map1.txt");
		System.out.println("MapWidth = " + map.getWidth() + ", MapHeight = " + map.getHeight());
		cam = new Camera(1344, 768, map.getWidth(), map.getHeight());
		setPreferredSize(new Dimension(1344, 768));
//		setPreferredSize(new Dimension(map.getWidth(), map.getHeight())); // 1344x736
		backGround = ImageIO.read(new File("assets/map/Bgr.png"));
		block = ImageIO.read(new File("assets/map/block.jpg"));
		// Player
		Player = new player(0, 50.0, 10.0, map.getPlayer(), "assets/sprites/Player_test.png", 35);
		// Mobs
		LoadMobs(mob, map.getMobs(), "assets/sprites/Demon_Mobs.png", 42, 12, 33, 9, 11, 14, -17);
		keyControl = new KeyControl(Player, map, check);

		addKeyListener(keyControl); // lấy nút
		setFocusable(true); // Chọn để nhận sự kiện từ bàn phím
		requestFocus(); // nhận Event từ bàn phím
		new Thread(this).start(); // chạy Runnable
	}

	public void LoadMobs(List<mobs> mob, HashMap<int[], String> MobList, String file, int cols, int x, int y,
			int offsetX, int offsetY, int footOffset, int left) throws IOException {
		for (HashMap.Entry<int[], String> entry : MobList.entrySet()) {
			String value = entry.getValue();
			int[] key = entry.getKey();
			switch (value) {
			case "d": {
				mobs m = new mobs(1, 100.0, 10.0, 3, file, cols, offsetX, offsetY, footOffset, left, key);
				m.setCenterX(x);
				m.setCenterY(y);
				mob.add(m);
				break;
			}
			case "D": {
				mobs m = new mobs(10, 200.0, 15.0, 5, file, cols, offsetX, offsetY, footOffset, left, key);
				m.setCenterX(x);
				m.setCenterY(y);
				mob.add(m);
				break;
			}
			case "B": {
				Boss b = new Boss(100, 1000.0, 20.0, 5, "assets/sprites/BOSS.png", cols, offsetX, offsetY, footOffset,
						left, key);
				b.setCenterX(x);
				b.setCenterY(y);
				mob.add(b);
				break;
			}
			}
		}
	}

	private void drawExp(Graphics g) {
		g.setColor(Color.yellow);
		g.setFont(new Font("Arial", Font.BOLD, 20)); // Font chữ to, đậm
		int level_next = Player.getLevel() + 1;
		g.drawString("Level tiếp theo: " + level_next, 20, 30);

		g.setColor(Color.GREEN); // Chọn màu đỏ để vẽ Exp
		// Lấy lượng máu hiện tại
		int score = Player.getScore();
		int nextScore = Player.nextScore(score);

		// Vẽ text máu ở góc trên bên trái
		g.drawString("Điểm up level: " + score + " / " + nextScore, 20, 60);
	}

	private void drawEntityHealth(Graphics g, entities Entity) {
		int EntityX = Entity.getOx(); // tọa độ Mob
		int EntityY = Entity.getOy();

		int maxHealth = Entity.getMaxHealth(); // Máu tối đa
		int currentHealth = (int) Entity.getHealth(); // Máu hiện tại

		int barWidth = 10; // thanh máu dài bằng width
		int barHeight = 2; // độ rộng
		// int healthWidth = (int) ((currentHealth / (float) maxHealth) * barWidth);

//		// Vẽ khung máu nền màu xám
//		g.setColor(Color.GRAY);
//		g.fillRect(cam.applyX(EntityX), cam.applyY(EntityY - 14), barWidth, barHeight);
//
//		// Vẽ máu còn lại màu đỏ
//		g.setColor(Color.RED);
//		g.fillRect(cam.applyX(EntityX), cam.applyY(EntityY - 14), healthWidth, barHeight);
//
//		// Vẽ viền
//		g.setColor(Color.BLACK);
//		g.drawRect(cam.applyX(EntityX), cam.applyY(EntityY - 14), barWidth, barHeight);

		// Vẽ phần Level
		g.setColor(Color.YELLOW); // Màu vàng cho level
		String levelText = "Level: " + Entity.getLevel(); // Văn bản hiển thị level
		FontMetrics fmLevel = g.getFontMetrics();
		int levelWidth = fmLevel.stringWidth(levelText); // Chiều rộng của phần level
		int levelX = EntityX + (barWidth - levelWidth) / 2; // Căn giữa phần level
		int levelY = EntityY - 45 + (barHeight + fmLevel.getAscent()) / 2; // Vị trí Y cho level (cao hơn HP)
		// Vẽ phần Level
		g.drawString(levelText, cam.applyX(levelX), cam.applyY(levelY));
		// Vẽ phần "HP:" màu đỏ
		g.setColor(Color.RED);
		String healthText = "HP: "; // Chữ "HP:" màu đỏ
		FontMetrics fm = g.getFontMetrics();
		int textWidth = fm.stringWidth(healthText); // Chiều rộng của phần "HP:"
		// Tính toán vị trí X và Y để căn giữa
		int textX = EntityX + (barWidth - fm.stringWidth(healthText + currentHealth + "/" + maxHealth)) / 2;
		int textY = EntityY - 30 + (barHeight + fm.getAscent()) / 2; // Căn giữa theo chiều dọc
		// Vẽ phần "HP:"
		g.drawString(healthText, cam.applyX(textX), cam.applyY(textY));
		// Vẽ phần currentHealth/maxHealth màu trắng
		g.setColor(Color.WHITE);
		String healthValues = currentHealth + "/" + maxHealth; // Phần "currentHealth/maxHealth" màu trắng
		int textXValues = textX + textWidth; // Vị trí X bắt đầu sau "HP:"
		g.drawString(healthValues, cam.applyX(textXValues), cam.applyY(textY));

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.blue);
		g.drawImage(backGround, cam.applyX(0), cam.applyY(0), map.getWidth(), map.getHeight(), this);

		for (int[] b : map.getBlock()) {
			g.drawImage(block, cam.applyX(b[0]), cam.applyY(b[1]), null);
		}

		if (!mob.isEmpty()) {
			for (mobs m : mob) {
//				g.drawOval(cam.applyX(m.getOx() - m.getRadius()), cam.applyY(m.getOy() - m.getRadius()),
//						m.getRadius() * 2, m.getRadius() * 2);
//				g.drawOval(cam.applyX(m.getOx() - 18), cam.applyY(m.getOy() - 18), 18 * 2, 18 * 2);
				g.drawImage(m.getCurrentFrame(), cam.applyX(m.getX()), cam.applyY(m.getY()), null);
				drawEntityHealth(g, m); // vẽ thanh máu mob
			}
		}

//		g.drawOval(cam.applyX(Player.getOx() - Player.getRadius()), cam.applyY(Player.getOy() - Player.getRadius()),
//				2 * Player.getRadius(), 2 * Player.getRadius());
		g.drawImage(Player.getCurrentFrame(), cam.applyX(Player.getX()), cam.applyY(Player.getY()), null);
		drawEntityHealth(g, Player);
		drawExp(g);
	}

	@Override
	public void run() {
		while (true) {

			// Nếu không game over thì update bình thường
			UpdatePlayer();
			UpdateMobs();
			cam.update(Player.getOx(), Player.getOy());
			repaint();

			try {
				Thread.sleep(50); // Delay mỗi frame
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void UpdatePlayer() {
		long currentTime = System.currentTimeMillis();

		// tăng cấp
		Player.level_up();

		int normalSpeed = 5; // tốc độ đi bộ
		int flashSpeed = 20; // tốc độ lướt
		int dx = normalSpeed; // set speed

		if (keyControl.isFlash()) { // shift
			if (currentTime - lastTime >= Delay) {
				lastTime = currentTime;
				dx = flashSpeed;
			} else {
				dx = normalSpeed;
			}
		} else
			dx = normalSpeed;

		Player.setVelocityY(Player.getVelocityY() + Player.gravity);// Trọng lực luôn được áp dụng
		int nextY = (int) (Player.getY() + Player.getVelocityY());// Tính toán vị trí Y mới

		// attack
		if (keyControl.isAttackPressed() && !keyControl.isMovingAttack()) {
			if (check.checkCollision(Player, "down", map) == 1) {
				keyControl.setMovingAttack(true);
				keyControl.setMovingRight(false);
				keyControl.setMovingLeft(false);
				keyControl.setMovingUp(false);
				keyControl.setMovingDown(false);
				Player.attack();
				new Thread(() -> {
					int start = Player.getFrameStart();
					int end = Player.getFrameEnd();
					for (int i = start; i <= end; i++) {
						Player.setFrameIndex(i);
						try {
							Thread.sleep(100);
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
					}
					keyControl.setMovingAttack(false);
					Player.setDefault();
				}).start();
			}
			keyControl.setAttackPressed(false);
		}

		// nhảy
		if (keyControl.isMovingUp())
			Player.up();
		if (Player.getVelocityY() < 0) { // Nếu đang bay lên
			Player.setY(nextY);
			if (check.checkCollision(Player, "up", map) == 1) { // Dừng lại khi chạm trần
				Player.down();
				Player.setVelocityY(0);
				while (check.checkCollision(Player, "up", map) == 1) {
					Player.setY(Player.getY() + 1); // Điều chỉnh vị trí Y khi va chạm với trần
				}
			}
		} else if (Player.getVelocityY() >= 0) { // Nếu đang rơi xuống
			Player.setY(nextY);
			if (check.checkCollision(Player, "down", map) == 1) {
				Player.setVelocityY(0); // Dừng rơi khi chạm đất
				while (check.checkCollision(Player, "down", map) == 1) {
					Player.setY(Player.getY() - 1); // Điều chỉnh vị trí Y khi va chạm với đất
				}
				keyControl.setMovingUp(false); // Reset trạng thái di chuyển lên
				jumpPressed = false; // Reset nhảy khi chạm đất
			}
		}

		if (Player.getVelocityY() == 0 && check.checkCollision(Player, "down", map) != 1) {
			Player.setY(Player.getY() + 2); // ép dính đất nếu cần
		}

		int lastX = Player.getX();
		if (keyControl.isMovingRight() && check.checkCollision(Player, "right", map) == 0) {
			Player.setX(Player.getX() + dx);
		}
		if (keyControl.isMovingLeft() && check.checkCollision(Player, "left", map) == 0) {
			Player.setX(Player.getX() - dx);
		}
		if (Player.getX() != lastX) {
			if (keyControl.isMovingRight() && !keyControl.isMovingUp()) {
				Player.move();
			}
			if (keyControl.isMovingLeft() && !keyControl.isMovingUp()) {
				Player.move();
			}
		}
	}

	private void UpdateMobs() {
		Iterator<mobs> it = mob.iterator();
		while (it.hasNext()) {
			mobs m = it.next();
			System.out.println(m.getX() + " " + m.getY());
			Player.attack_damage(m, "Demon");
			if (m.getHealth() == 0) {
				Player.setScore(Player.getScore() + 1);
				m.die("Demon");
				it.remove(); // ✅ safe remove
				continue; // bỏ qua update() khi đã xoá
			}
			m.Update("Demon", Player, 2, 18, check, map);
		}
	}

	public player getPlayer() {
		return Player;
	}

	public void setPlayer(player player) {
		Player = player;
	}

	public KeyControl getKeyControl() {
		return keyControl;
	}

	public void setKeyControl(KeyControl keyControl) {
		this.keyControl = keyControl;
	}

	public MapLoader getMap() {
		return map;
	}

	public void setMap(MapLoader map) {
		this.map = map;
	}

	public BufferedImage getBackGround() {
		return backGround;
	}

	public void setBackGround(BufferedImage backGround) {
		this.backGround = backGround;
	}

	public long getLastTime() {
		return lastTime;
	}

	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}

	public long getDelay() {
		return Delay;
	}

	public void setDelay(long delay) {
		Delay = delay;
	}

}
