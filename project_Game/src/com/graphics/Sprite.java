package com.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import com.entities.player;

public class Sprite extends JPanel implements Runnable, KeyListener {
	private player Player;

	private boolean movingRight;
	private boolean movingLeft;
	private boolean movingUp;
	private boolean movingDown;
	private boolean movingAttack;
	private boolean isIdle;

	private long lastKeyPressTime;
	private final long IDLE_DELAY = 2000; // 2 giây
	private double velocityY = 0; // vận tốc
	private final double gravity = 1.5; // trọng lực

	public Sprite() {
		Player = new player(10, 10.0, 10.0, 10, 10.0);
		new Thread(this).start();
		movingRight = false;
		movingLeft = false;
		movingUp = false;
		movingDown = false;
		movingAttack = false;
		isIdle = false;
		addKeyListener(this);
		setFocusable(true);
		requestFocus();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.blue);
		g.drawOval(Player.getX() - Player.getR() + Player.getOx(), Player.getY() - Player.getR() + Player.getOy(),
				2 * Player.getR(), 2 * Player.getR());
		g.drawImage(Player.getCurrentFrame(), Player.getX(), Player.getY(), null);
	}

	@Override
	public void run() {
		while (true) {

			if (movingRight)
				Player.setX(Player.getX() + 5);
			if (movingLeft)
				Player.setX(Player.getX() - 5);
			if (movingUp) {
//				Player.jump();
				velocityY += gravity; // tăng dần tốc độ rơi
				Player.setY((int) (Player.getY() + velocityY));
				if (velocityY == 0) {
					Player.setFrameIndex(Player.getFrameIndex() + 1);
				}
				if (Player.getY() == 195)
					Player.setFrameIndex(Player.getFrameIndex() + 1);

//				if (velocityY == 13.5)
//					Player.setFrameIndex(Player.getFrameEnd());

				if (Player.getY() >= 200) {
					Player.setY(200);
					movingUp = false;
					velocityY = 0;
				}
			} else {
				if (movingRight)
					Player.moveRight();
				if (movingLeft)
					Player.moveLeft();
			}

			repaint();

			try {
				Thread.sleep(50); // Giả lập tốc độ frame (10 FPS)
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		lastKeyPressTime = System.currentTimeMillis();
		if (movingAttack) {
			return;
		}

		switch (e.getKeyCode()) {
		case KeyEvent.VK_W: { // nhảy
			if (!movingUp) {
				movingUp = true;
				velocityY = -15;
				Player.jump();
			}
			break;
		}
		case KeyEvent.VK_S: { // Ngồi
			movingDown = true;
			movingRight = false;
			movingLeft = false;
			if (movingDown) {
				Player.crouch();
				Player.setFrameIndex(Player.getFrameStart());
				try {
					Thread.sleep(1);
					Player.setFrameIndex(Player.getFrameEnd());
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			System.out.println("S");
			break;
		}
		case KeyEvent.VK_A: { // Trái
			movingLeft = true;
			movingRight = false;
			Player.setFacingRight(true);
			break;
		}
		case KeyEvent.VK_D: { // Phải
			movingRight = true;
			movingLeft = false;
			Player.setFacingRight(false);
			System.out.println("D");
			break;
		}
		case KeyEvent.VK_K: { // Tấn công
			if (Player.getY() == 200) { // đang trên mặt đất mới được tấn công
				movingAttack = true;
				movingRight = false;
				movingLeft = false;
				movingUp = false;
				movingDown = false;
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
					movingAttack = false; // Kết thúc attack
					Player.setDefault();
				}).start();
				break;
			}
		}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (movingAttack)
			return;

		switch (e.getKeyCode()) {
		case KeyEvent.VK_A: { // trái
			if (movingLeft == true) {
				movingLeft = false;
			}
			break;
		}
		case KeyEvent.VK_S: { // ngồi
			if (movingDown == true) {
				movingDown = false;
				System.out.println("S");
			}
			break;
		}
		case KeyEvent.VK_D: { // phải
			if (movingRight == true) {
				movingRight = false;
			}
			break;
		}
		}
		if (!movingRight && !movingLeft && !movingUp && !movingDown) {
			Player.setDefault();
		}
	}
}
