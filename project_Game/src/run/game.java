package run;

import java.io.IOException;

import javax.swing.JFrame;

import menu.MainMenu;

public class game {

	public static void main(String[] args) {
//		try {
//			JFrame frame = new JFrame("Game");
//			Sprite panel = new Sprite();
//
//			frame.add(panel);
//			frame.pack();
//			frame.setLocationRelativeTo(null);
//			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			frame.setVisible(true);
//		} catch (IOException e) {
//			System.out.println("loi khoi tao game");
//			e.printStackTrace();
//		}
		try {
			JFrame frame = new JFrame("Game Title");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(800, 600);

			// Hiển thị menu chính
			frame.setContentPane(new MainMenu());
			frame.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
