package com.graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.entities.player;

public class KeyControl implements KeyListener {
	private player Player;
	private MapLoader map;
	private CollisionManager check;
	private int dem = 0;

	private boolean movingRight;
	private boolean movingLeft;
	private boolean movingUp;
	private boolean movingDown;
	private boolean movingAttack;
	private boolean attackPressed = false;
	private boolean flash;

	public KeyControl(player player, MapLoader map, CollisionManager check) {
		this.Player = player;
		this.map = map;
		this.check = check;
		movingRight = false;
		movingLeft = false;
		movingUp = false;
		movingDown = false;
		movingAttack = false;
		flash = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (movingAttack) {
			return;
		}

		switch (e.getKeyCode()) {
		case KeyEvent.VK_W: { // nhảy
			if (!movingUp) {
				dem++;
				Player.setVelocityY(-15);
				Player.setDefault();
			}
			if (dem >= 2) {
				movingUp = true;
			}
			break;
		}
		case KeyEvent.VK_S: { // Ngồi
			if (!movingUp) {
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
			}
			break;
		}
		case KeyEvent.VK_SHIFT: {
			flash = true;
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
		case KeyEvent.VK_K: {
			attackPressed = true;
			break;
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
		case KeyEvent.VK_K: {
			attackPressed = false;
			break;
		}
		case KeyEvent.VK_SHIFT: {
			flash = false;
			break;
		}
		}
		if (!movingRight && !movingLeft && !movingUp && !movingDown) {
			Player.setDefault();
		}
	}

	public player getPlayer() {
		return Player;
	}

	public void setPlayer(player player) {
		Player = player;
	}

	public boolean isMovingRight() {
		return movingRight;
	}

	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

	public boolean isMovingLeft() {
		return movingLeft;
	}

	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}

	public boolean isMovingUp() {
		return movingUp;
	}

	public void setMovingUp(boolean movingUp) {
		this.movingUp = movingUp;
	}

	public boolean isMovingDown() {
		return movingDown;
	}

	public void setMovingDown(boolean movingDown) {
		this.movingDown = movingDown;
	}

	public boolean isMovingAttack() {
		return movingAttack;
	}

	public void setMovingAttack(boolean movingAttack) {
		this.movingAttack = movingAttack;
	}

	public boolean isFlash() {
		return flash;
	}

	public void setFlash(boolean flash) {
		this.flash = flash;
	}

	public boolean isAttackPressed() {
		return attackPressed;
	}

	public void setAttackPressed(boolean attackPressed) {
		this.attackPressed = attackPressed;
	}

	public int getDem() {
		return dem;
	}

	public void setDem(int dem) {
		this.dem = dem;
	}

}
