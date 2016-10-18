package br.com.shellcode.shellalarm;

import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class TrayUtil {
	private static TrayIcon trayIcon = null;
	private static boolean initialized = false;

	public static void showNotification(String title, String msg, MessageType type) {
		if (initialized && trayIcon != null)
			trayIcon.displayMessage(title, msg, type);
	}

	public static void initialize() {
		if (initialized)
			return;
		initialized = true;

		// Check the SystemTray is supported
		if (!SystemTray.isSupported()) {
			System.out.println("SystemTray is not supported");
			return;
		}
		final PopupMenu popup = new PopupMenu();
		BufferedImage image;
		try {
			InputStream is = TrayUtil.class.getResourceAsStream("/images/ponto.png");
			image = ImageIO.read(is);
			trayIcon = new TrayIcon(image);
			trayIcon.setImageAutoSize(true);
			final SystemTray tray = SystemTray.getSystemTray();

			// Create a pop-up menu components
			MenuItem aboutItem = new MenuItem("About");
			CheckboxMenuItem cb1 = new CheckboxMenuItem("Set auto size");
			CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");
			Menu displayMenu = new Menu("Display");
			MenuItem errorItem = new MenuItem("Error");
			MenuItem warningItem = new MenuItem("Warning");
			MenuItem infoItem = new MenuItem("Info");
			MenuItem noneItem = new MenuItem("None");
			MenuItem exitItem = new MenuItem("Sair");
			exitItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				};
			});

			// Add components to pop-up menu
			popup.add(aboutItem);
			popup.addSeparator();
			popup.add(cb1);
			popup.add(cb2);
			popup.addSeparator();
			popup.add(displayMenu);
			displayMenu.add(errorItem);
			displayMenu.add(warningItem);
			displayMenu.add(infoItem);
			displayMenu.add(noneItem);
			popup.add(exitItem);

			trayIcon.setPopupMenu(popup);

			try {
				tray.add(trayIcon);
				showNotification("Alerta", "App em execução", MessageType.INFO);
			} catch (AWTException e) {
				System.out.println("TrayIcon could not be added.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
