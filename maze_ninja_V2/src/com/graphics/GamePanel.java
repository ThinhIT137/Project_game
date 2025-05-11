package com.graphics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

	private Sprite sprite;
	private JLayeredPane layered;
	private Stop_JPanel pauseOverlay;
	private gameWin gameWinOverlay;
	private gameOver gameOverOverlay;

	public GamePanel(Runnable onStart, Runnable onQuitToMenu) throws IOException {
		setLayout(new BorderLayout());
		layered = new JLayeredPane();
		layered.setPreferredSize(new Dimension(1344, 768));

		sprite = new Sprite(() -> { // khởi tạo sự kiện khi die và win
			sprite.setPaused(true);
			gameOverOverlay.setVisible(true);
		}, () -> {
			sprite.setPaused(true);
			gameWinOverlay.setVisible(true);
		});
		sprite.setBounds(0, 0, 1344, 768);
		layered.add(sprite, JLayeredPane.DEFAULT_LAYER);

		// win game
		gameWinOverlay = new gameWin(onStart, onQuitToMenu);
		gameWinOverlay.setBounds(0, 0, 1344, 768);
		gameWinOverlay.setVisible(false);
		layered.add(gameWinOverlay, JLayeredPane.PALETTE_LAYER);

		// over game
		gameOverOverlay = new gameOver(() -> revive(), onStart, onQuitToMenu);
		gameOverOverlay.setBounds(0, 0, 1344, 768);
		gameOverOverlay.setVisible(false);
		layered.add(gameOverOverlay, JLayeredPane.PALETTE_LAYER);

		// pause game
		pauseOverlay = new Stop_JPanel(() -> exit(), () -> {
			pauseOverlay.setVisible(false);
			sprite.setPaused(false);
			sprite.requestFocus();
		}, onQuitToMenu);
		pauseOverlay.setBounds(0, 0, 1344, 768);
		pauseOverlay.setVisible(false);
		layered.add(pauseOverlay, JLayeredPane.PALETTE_LAYER);

		// sự kiện nút pause ở sprite
		sprite.getPauseButton().addActionListener(e -> {
			pauseOverlay.setVisible(true);
			sprite.setPaused(true);
		});

		add(layered, BorderLayout.CENTER);
	}

	private void exit() {
		pauseOverlay.setVisible(false);
		sprite.setPaused(false);
	}

	private void revive() {
		gameOverOverlay.setVisible(false); // ẩn overlay
		sprite.revive();
		sprite.setPaused(false); // tiếp tục game
		sprite.requestFocus();
	}

	public JLayeredPane getLayered() {
		return layered;
	}

	public void setLayered(JLayeredPane layered) {
		this.layered = layered;
	}

	public Stop_JPanel getPauseOverlay() {
		return pauseOverlay;
	}

	public void setPauseOverlay(Stop_JPanel pauseOverlay) {
		this.pauseOverlay = pauseOverlay;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public gameOver getGameOverOverlay() {
		return gameOverOverlay;
	}

	public void setGameOverOverlay(gameOver gameOverOverlay) {
		this.gameOverOverlay = gameOverOverlay;
	}

	public gameWin getGameWinOverlay() {
		return gameWinOverlay;
	}

	public void setGameWinOverlay(gameWin gameWinOverlay) {
		this.gameWinOverlay = gameWinOverlay;
	}

}
