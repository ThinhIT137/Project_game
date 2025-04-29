package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.graphics.Sprite;

public class GameOverPanel extends JPanel implements ActionListener {

	private boolean check_returnMenu = true;
	private boolean check_continue = false;
	private JButton continue_Button;
	private JButton play_Again_Button;
	private JButton main_Menu_Button;

	public GameOverPanel() {
		setLayout(null);

		continue_Button = new JButton("Tiếp tục (Hồi sinh)");
		play_Again_Button = new JButton("Chơi lại");
		main_Menu_Button = new JButton("Quay lại menu chính");

		continue_Button.addActionListener(this);
		play_Again_Button.addActionListener(this);
		main_Menu_Button.addActionListener(this);

		continue_Button.setBounds(100, 150, 200, 50);
		play_Again_Button.setBounds(100, 200, 200, 50);
		main_Menu_Button.setBounds(100, 270, 200, 50);

		add(continue_Button);
		add(play_Again_Button);
		add(main_Menu_Button);
	}

	private void PlayAgain() throws IOException {
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
		Sprite spritePanel = new Sprite();
		frame.setContentPane(spritePanel);
		frame.revalidate();
		frame.pack(); // Đảm bảo cửa sổ được đóng gói lại sau khi thay đổi nội dung
		frame.setLocationRelativeTo(null); // căn giữa màn hình
		frame.setVisible(true); // Đảm bảo frame hiển thị
		spritePanel.requestFocusInWindow(); // dùng được KeyListener
	}

	private void mainMenu() throws IOException {
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
		MainMenu main = new MainMenu();
		frame.setContentPane(main); // Chuyển về menu chính
		frame.revalidate();
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
		try {
			if (src == continue_Button)
				check_continue = true;
			else if (src == play_Again_Button)
				PlayAgain();
			else if (src == main_Menu_Button) {
				check_returnMenu = true;
				mainMenu();
			}
		} catch (IOException ex) {
			System.out.println("error");
			ex.printStackTrace();
		}
	}

	public boolean isCheck_continue() {
		return check_continue;
	}

	public void setCheck_continue(boolean check_continue) {
		this.check_continue = check_continue;
	}

	public boolean isCheck_returnMenu() {
		return check_returnMenu;
	}

	public void setCheck_returnMenu(boolean check_returnMenu) {
		this.check_returnMenu = check_returnMenu;
	}

}
