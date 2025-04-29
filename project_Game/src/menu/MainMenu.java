package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.graphics.Sprite;

public class MainMenu extends JPanel implements ActionListener {

	private JButton StartButton;
	private JButton exitButton;

	public MainMenu() throws IOException {
		setLayout(null);
		StartButton = new JButton("Start");
		exitButton = new JButton("Exit");

		StartButton.addActionListener(this);
		exitButton.addActionListener(this);

		StartButton.setBounds(100, 200, 200, 50);
		exitButton.setBounds(100, 270, 200, 50);

		add(StartButton);
		add(exitButton);
	}

	private void StartGame() throws IOException {
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
		Sprite spritePanel = new Sprite();
		frame.setContentPane(spritePanel);
		frame.revalidate();
		frame.pack(); // Đảm bảo cửa sổ được đóng gói lại sau khi thay đổi nội dung
		frame.setLocationRelativeTo(null); // căn giữa màn hình
		frame.setVisible(true); // Đảm bảo frame hiển thị
		spritePanel.requestFocusInWindow(); // dùng được KeyListener
	}

	private void exitGame() {
		System.exit(0);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
		if (src == StartButton) {
			try {
				StartGame();
			} catch (IOException ex) {
				System.out.println("lỗi khởi tạo");
			}
		} else if (src == exitButton)
			exitGame();
	}
}
