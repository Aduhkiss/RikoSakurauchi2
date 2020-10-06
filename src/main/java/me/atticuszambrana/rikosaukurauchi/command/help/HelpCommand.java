package me.atticuszambrana.rikosaukurauchi.command.help;

import org.javacord.api.event.message.MessageCreateEvent;

import me.atticuszambrana.rikosaukurauchi.common.Command;
import me.atticuszambrana.rikosaukurauchi.common.CommandSection;

public class HelpCommand extends Command {
	
	public HelpCommand() {
		super("help", "Shows all commands you can execute, and what they do", CommandSection.HELP);
	}

	@Override
	public void execute(String[] args, MessageCreateEvent event) {
		
	}

}
