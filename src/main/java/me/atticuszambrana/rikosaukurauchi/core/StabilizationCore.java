package me.atticuszambrana.rikosaukurauchi.core;

import org.javacord.api.event.connection.LostConnectionEvent;
import org.javacord.api.event.connection.ReconnectEvent;
import org.javacord.api.listener.connection.LostConnectionListener;
import org.javacord.api.listener.connection.ReconnectListener;

import me.atticuszambrana.rikosaukurauchi.email.AtticusEmail;

public class StabilizationCore implements LostConnectionListener,ReconnectListener {
	
	/*
	 * Basically a core system that is in charge of reporting everything bad that happens to our leadership and development team
	 * -Atticus
	 */
	
	public StabilizationCore() {
		
	}

	public void onReconnect(ReconnectEvent event) {
		System.out.println("[RECONNECT] Guys everythings ok! I was able to re-connect to Discord!");
	}

	public void onLostConnection(LostConnectionEvent event) {
		System.out.println("[DISCONNECT] Uh oh.. We lost connection to Discord! Did a plane crash into their datacenter?");
	}
	
	public void sendTestEmail() {
		AtticusEmail email = new AtticusEmail("Riko Sakurauchi has gone offline!", "<h4>You are recieving this message because your Riko Sakurauchi Server has lost connection to the Discord API.</h4>");
		email.send();
	}
	
}
