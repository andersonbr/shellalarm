package br.com.shellcode.shellalarm;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class JWindowAlarm extends JWindow {
	private static final long serialVersionUID = 1L;
	private JLabel label2;
	private Audio audio;

	public JWindowAlarm() {
		final JWindow window = this;
		audio = new Audio("alarm.wav");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				// System.exit(0);
			}
		});
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				window.setVisible(false);
				audio.stop();
			}
		});
		setLocation(200, 200);
		setSize(200, 200);
		setBounds(60, 60, 250, 250);
		setAlwaysOnTop(true);
		setLocationByPlatform(true);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.RED);
		add(panel);
		JLabel label1 = new JLabel("ALARME DO PONTO");
		label1.setForeground(Color.WHITE);
		label1.setBounds(0, 100, 250, 20);
		label1.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label1);
		label2 = new JLabel(new SimpleDateFormat("HH:mm").format(new Date()));
		label2.setForeground(Color.WHITE);
		Font labelFont = label2.getFont();
		label2.setFont(new Font(labelFont.getName(), Font.BOLD, 22));
		label2.setBounds(0, 120, 250, 20);
		label2.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label2);
	}

	public void exibir(String hora) {
		setVisible(true);
		label2.setText(hora);
		audio.loop();
	}
}