package run;

/**
 * game ninja đi mê cung tiêu diệt quái vật
 * @author Thịnh & Tùng
 * @version 1.0
 * 
 * @github ThinhIT137
 * **/

import java.awt.CardLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.graphics.GamePanel;
import com.graphics.menu;

public class game {
	private GamePanel gamePanel;
	private menu MENU;
	private JFrame frame;
	private CardLayout cardLayout;
	private JPanel rootPanel;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				new game().createAndShow();
			} catch (IOException e) {
				System.out.println("lỗi khởi tạo game");
				e.printStackTrace();
			}
		});
	}

	private void createAndShow() throws IOException {
		frame = new JFrame("My Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		cardLayout = new CardLayout();
		rootPanel = new JPanel(cardLayout);
		rootPanel.setPreferredSize(new Dimension(704, 704));

		// menu
		MENU = new menu(() -> {
			try {
				switchToGame();
			} catch (IOException e) {
				System.out.println("lỗi khởi tạo menu");
				e.printStackTrace();
			}
		});
		rootPanel.add(MENU, "MENU");

		// game
		gamePanel = new GamePanel(() -> {
			try {
				switchToGame();
			} catch (IOException e) {
				System.out.println("lỗi khởi tạo game");
				e.printStackTrace();
			}
		}, () -> switchToMenu());
		rootPanel.add(gamePanel, "GAME");

		frame.setContentPane(rootPanel);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	// menu -> game
	private void switchToGame() throws IOException {
		cardLayout.show(rootPanel, "GAME");

		gamePanel.getPauseOverlay().setVisible(false);
		gamePanel.getGameWinOverlay().setVisible(false);
		gamePanel.getGameOverOverlay().setVisible(false);
		gamePanel.getSprite().CreateGame();
		gamePanel.getSprite().requestFocusInWindow();

		rootPanel.setPreferredSize(new Dimension(1344, 768));
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

	// game -> menu
	private void switchToMenu() {
		cardLayout.show(rootPanel, "MENU");
		rootPanel.setPreferredSize(new Dimension(704, 704));
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

}
