package com.graphics;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Stop_JPanel extends JPanel {
	private imageButtonData img = new imageButtonData();

	public Stop_JPanel(Runnable onExit, Runnable onContinue, Runnable onQuit) throws IOException {
		setOpaque(false);
		setLayout(null);
		setSize(1344, 768);
		setPreferredSize(new Dimension(1344, 768));

		JLabel bg = new JLabel(img.getMenu(1));
		bg.setBounds((1344 - 704) / 2, (768 - 704) / 2, 704, 704);
		add(bg);

		// exit tắt
		JButton exit = new JButton(img.getExit());
		exit.setBounds((1344 - 704) / 2 + 672, (768 - 704) / 2, 32, 32); // luôn nằm giữa ngang
		exit.addActionListener(e -> onExit.run());
		add(exit);

		// continue
		JButton cont = new JButton(img.getButton(0));
		cont.setBounds((1344 - 200) / 2, 300, 192, 64); // luôn nằm giữa ngang
		cont.addActionListener(e -> onContinue.run());
		add(cont);

		// exit menu
		JButton quit = new JButton(img.getButton(3));
		quit.setBounds((1344 - 200) / 2, 400, 192, 64);
		quit.addActionListener(e -> onQuit.run());
		add(quit);

		setComponentZOrder(bg, getComponentCount() - 1);
	}
}
