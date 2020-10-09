package me.atticuszambrana.rikosaukurauchi;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.util.logging.ExceptionLogger;

import com.google.gson.Gson;

import me.atticuszambrana.rikosaukurauchi.common.OperatingSystem;
import me.atticuszambrana.rikosaukurauchi.config.ConfigFile;
import me.atticuszambrana.rikosaukurauchi.core.CommandListener;
import me.atticuszambrana.rikosaukurauchi.core.StabilizationCore;
import me.atticuszambrana.rikosaukurauchi.util.OSFinder;

public class Main {
	
	/**
	 * Riko Sakurauchi Discord bot (Version 2)
	 * Created by Atticus Zambrana
	 */
	
	/**
	 *    MAIN TODO LIST
	 *    
	 *    - Make our own unique logging system that logs to files, and displays timestamps
	 */
	
	private static DiscordApi discord;
	private static ConfigFile theConfig;
	
	public static void main(String[] args) {
		OperatingSystem os = OSFinder.getOS();
		//System.out.println("[DEBUG] You are running on: " + os.toString());
		
		System.out.println("RIKO SAKURAUCHI REBORN");
		System.out.println("This program has been brought to you by Atticus Zambrana (https://atticuszambrana.me)\n");
		
		// We want to search the system for any files called 'sakurauchi.config'
		// If we can find something, great! we will begin the loading process of the bot. However
		// If we cannot, then lets just shutdown the bot and tell the user to go download a copy from the project github
		// - Atticus
		
		StringBuilder con = new StringBuilder();
		try {
			File configFile = new File("sakurauchi.config");
			Scanner sc = new Scanner(configFile);
			while(sc.hasNextLine()) {
				String data = sc.nextLine();
				con.append(data);
			}
			sc.close();
		} catch(FileNotFoundException ex) {
			System.out.println("Uh oh.... It looks like we were unable to find a config file! Make sure to download a sample from our website (insert link here), and customize it to you're settings!");
			System.exit(1);
		}
		
		/*
		 * So by this point in the code, we know for a fact that the file exists, and we've already ingested the data that was kept in it. So now we just need to make sure
		 * that the data is correct, that its in JSON, and we need to convert it to a ConfigFile object.
		 * - Atticus
		 */
		
		// (Debug message, remove before release - Atticus)
		//System.out.println("DATA INGESTED: " + con.toString());
		
		//TODO: Make sure that the data that the program ingested is correct JSON syntax, if not, complain to the user -Atticus
		
		Gson gson = new Gson();
		
		theConfig = gson.fromJson(con.toString(), ConfigFile.class);
		// (Debug message, remove before release - Atticus)
		//System.out.println("FINAL: " + theConfig.getDiscordToken());
		
		//TODO: Add a webserver with several routes in order to pull data from the code
		
		// Finally, start the Discord bot
		//discord = new DiscordApiBuilder().setToken(theConfig.getDiscordToken()).login().join();
		
		new DiscordApiBuilder()
		.setToken(theConfig.getDiscordToken())
		.setWaitForServersOnStartup(false)
		.setRecommendedTotalShards().join()
		.loginAllShards()
		.forEach(shardFuture -> shardFuture
				.thenAccept(Main::onShardLogin)
				.exceptionally(ExceptionLogger.get())
		);
	}
	
	private static void onShardLogin(DiscordApi discord) {
		// Make the cache smaller, to reduce the memory usage on the physical machine
		discord.setMessageCacheSize(10, 60*60);
		
		// Add any and all modules that we want the bot to use
		StabilizationCore sCore = new StabilizationCore();
		discord.addReconnectListener(sCore);
		discord.addLostConnectionListener(sCore);
		//sCore.sendTestEmail();
		
		// Start the Command Listener
		CommandListener cListener = new CommandListener(theConfig, discord);
		discord.addMessageCreateListener(cListener);
		System.out.println("[SHARD " + discord.getCurrentShard() + "] Connected to Discord.");
	}
	
	public static ConfigFile getConfig() {
		return theConfig;
	}
}
