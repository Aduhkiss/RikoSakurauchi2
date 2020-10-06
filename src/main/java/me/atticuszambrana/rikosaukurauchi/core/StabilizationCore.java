package me.atticuszambrana.rikosaukurauchi.core;

import org.javacord.api.event.connection.LostConnectionEvent;
import org.javacord.api.event.connection.ReconnectEvent;
import org.javacord.api.listener.connection.LostConnectionListener;
import org.javacord.api.listener.connection.ReconnectListener;

public class StabilizationCore implements LostConnectionListener,ReconnectListener{

	public void onReconnect(ReconnectEvent event) {
		System.out.println("[RECONNECT] Guys everythings ok! I was able to re-connect to Discord!");
	}

	public void onLostConnection(LostConnectionEvent event) {
		System.out.println("[DISCONNECT] Uh oh.. We lost connection to Discord! Did a plane crash into their datacenter?");
	}
	
}
