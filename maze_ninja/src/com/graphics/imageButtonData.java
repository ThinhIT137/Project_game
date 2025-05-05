package com.graphics;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class imageButtonData {
	private int cols_button = 6;
	private ImageIcon[] button;
	private ImageIcon pause;
	private ImageIcon exit;
	private int cols_menu = 4;
	private ImageIcon[] menu;

	public imageButtonData() throws IOException {
		ImageLoader button_image = new ImageLoader("assets/resources/Button.png", cols_button);
		ImageLoader menu_image = new ImageLoader("assets/resources/menu.png", cols_menu);
		this.button = button_image.getImageIconFrames();
		this.exit = new ImageIcon(ImageIO.read(new File("assets/resources/exit.png")));
		this.pause = new ImageIcon(ImageIO.read(new File("assets/resources/pause.png")));
		this.menu = menu_image.getImageIconFrames();
	}

	public ImageIcon getButton(int i) {
		return button[i];
	}

	public ImageIcon getMenu(int i) {
		return menu[i];
	}

	public ImageIcon[] getButton() {
		return button;
	}

	public void setButton(ImageIcon[] button) {
		this.button = button;
	}

	public ImageIcon getExit() {
		return exit;
	}

	public void setExit(ImageIcon exit) {
		this.exit = exit;
	}

	public ImageIcon getPause() {
		return pause;
	}

	public void setPause(ImageIcon pause) {
		this.pause = pause;
	}

}
