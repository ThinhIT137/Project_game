package com.graphics;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class menu extends JPanel {
	private imageButtonData img = new imageButtonData();

	public menu(Runnable onStart) throws IOException {
		setLayout(null);
//		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(704, 704));

		JLabel bg = new JLabel(img.getMenu(0));
		bg.setBounds(0, 0, 704, 704);
		add(bg);

		// Nút Start
		JButton startBtn = new JButton(img.getButton(1));
		startBtn.setBounds(32, 448, 192, 64);
		startBtn.addActionListener(e -> onStart.run());
		add(startBtn);

		// Nút quit
		JButton exitBtn = new JButton(img.getButton(2));
		exitBtn.setBounds(32, 544, 192, 64);
		exitBtn.addActionListener(e -> System.exit(0));
		add(exitBtn);

		setComponentZOrder(bg, getComponentCount() - 1);
	}
}
