package me.atticuszambrana.rikosaukurauchi.core;

import java.util.HashMap;
import java.util.Map;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import me.atticuszambrana.rikosaukurauchi.command.help.HelpCommand;
import me.atticuszambrana.rikosaukurauchi.command.music.PlayCommand;
import me.atticuszambrana.rikosaukurauchi.common.Command;
import me.atticuszambrana.rikosaukurauchi.config.ConfigFile;
import me.atticuszambrana.rikosaukurauchi.util.StringUtil;

public class CommandListener implements MessageCreateListener {
	
	private Map<String, Command> Commands = new HashMap<>();
	// The actual config file passed to us by the Main class
	private ConfigFile config;
	private DiscordApi discord;
	
	public CommandListener(ConfigFile config, DiscordApi discord) {
		this.config = config;
		this.discord = discord;
		// Register all commands here
		
		// Help Commands
		register(new HelpCommand());
		// Music Commands
		register(new PlayCommand(discord));
	}
	
	public void register(Command cmd) {
		Commands.put(cmd.getName(), cmd);
	}

	public void onMessageCreate(MessageCreateEvent event) {
		String prefix = config.getCommandPrefix();
		
		if(event.getMessageContent().startsWith(prefix)) {
			for(Map.Entry<String, Command> ent : Commands.entrySet()) {
				String name = ent.getKey();
				Command cmd = ent.getValue();
				
				if(event.getMessageContent().startsWith(prefix + name)) {
					User author = event.getMessageAuthor().asUser().get();
					cmd.execute(StringUtil.toArrayWithoutFirst(event.getMessageContent()), event);
					System.out.println("[{SHARD " + event.getApi().getCurrentShard() + "} COMMAND] " + author.getDiscriminatedName() + " has run " + prefix + name);
				}
			}
		}
	}
	
}
