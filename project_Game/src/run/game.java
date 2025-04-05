package run;

import javax.swing.JFrame;

import com.graphics.Sprite;

public class game {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Game");
		Sprite panel = new Sprite();

		frame.add(panel);
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}
}
