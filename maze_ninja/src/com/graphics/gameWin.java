package com.graphics;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class gameWin extends JPanel {
	private imageButtonData img = new imageButtonData();

	public gameWin(Runnable onStart, Runnable onQuit) throws IOException {
		setOpaque(false);
		setLayout(null);
		setSize(1344, 768);
		setPreferredSize(new Dimension(1344, 768));

		JLabel bg = new JLabel(img.getMenu(2));
		bg.setBounds((1344 - 704) / 2, (768 - 704) / 2, 704, 704);
		add(bg);

		// nút chơi mới
		JButton cont = new JButton(img.getButton(1));
		cont.setBounds((1344 - 200) / 2, 300, 192, 64); // luôn nằm giữa ngang
		cont.addActionListener(e -> onStart.run());
		add(cont);

		// nút exit
		JButton quit = new JButton(img.getButton(3));
		quit.setBounds((1344 - 200) / 2, 400, 192, 64);
		quit.addActionListener(e -> onQuit.run());
		add(quit);

		setComponentZOrder(bg, getComponentCount() - 1);
	}
}
