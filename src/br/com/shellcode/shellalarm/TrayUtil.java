package br.com.shellcode.shellalarm;

import java.awt.AWTException;
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

			MenuItem exitItem = new MenuItem("Sair");
			exitItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				};
			});
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
