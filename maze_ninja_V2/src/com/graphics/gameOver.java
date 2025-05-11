package com.graphics;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class gameOver extends JPanel {

	private imageButtonData img = new imageButtonData();

	public gameOver(Runnable revive, Runnable onStart, Runnable onQuit) throws IOException {
		setOpaque(false);
		setLayout(null);
		setSize(1344, 768);
		setPreferredSize(new Dimension(1344, 768));

		// nền
		JLabel bg = new JLabel(img.getMenu(3));
		bg.setBounds((1344 - 704) / 2, (768 - 704) / 2, 704, 704);
		add(bg);

		// nút hồi sinh
		JButton re = new JButton("hồi sinh");
		re.setBounds((1344 - 200) / 2, 300, 192, 64);
		re.addActionListener(e -> revive.run());
		add(re);

		// nút start chơi lại
		JButton startBtn = new JButton(img.getButton(1));
		startBtn.setBounds((1344 - 200) / 2, 400, 192, 64);
		startBtn.addActionListener(e -> onStart.run());
		add(startBtn);

		// nút exit
		JButton quit = new JButton(img.getButton(3));
		quit.setBounds((1344 - 200) / 2, 500, 192, 64);
		quit.addActionListener(e -> onQuit.run());
		add(quit);

		setComponentZOrder(bg, getComponentCount() - 1);
	}

}
