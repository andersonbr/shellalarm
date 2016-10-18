package br.com.shellcode.shellalarm;

import java.awt.TrayIcon.MessageType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainApp {
	static String lastAlarm = "";
	static String lastNotif = "";

	public static void main(String[] args) {
		final List<String> horarios = new ArrayList<>();
		horarios.add("06:55");
		horarios.add("11:00");
		horarios.add("12:00");
		horarios.add("16:00");
		final List<String> horariosMenosUm = new ArrayList<>();

		TrayUtil.initialize();

		final JWindowAlarm window = new JWindowAlarm();

		for (String hora : horarios) {
			SimpleDateFormat df = new SimpleDateFormat("HH:mm");
			Date d;
			try {
				d = df.parse(hora);
				Calendar gc = new GregorianCalendar();
				gc.setTime(d);
				gc.add(Calendar.MINUTE, -1);
				Date d2 = gc.getTime();
				horariosMenosUm.add(df.format(d2));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				String horaString = new SimpleDateFormat("HH:mm").format(new Date());

				if (!horaString.equals(lastNotif) && horariosMenosUm.contains(horaString)) {
					lastNotif = horaString;
					System.out.println("NOTIFICANDO...");
					TrayUtil.showNotification("Notificação", "O alarme para batida de ponto iniciará em 1 minuto...",
							MessageType.WARNING);
				}

				if (!horaString.equals(lastAlarm) && horarios.contains(horaString)) {
					lastAlarm = horaString;
					System.out.println("ALARMANDO...");
					window.exibir(horaString);
				}
				System.out.println(horaString);
			}
		}, 0, 1, TimeUnit.SECONDS);
	}
}
