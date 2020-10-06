package me.atticuszambrana.rikosaukurauchi.common;

import org.javacord.api.event.message.MessageCreateEvent;

public abstract class Command {
	
	private String name;
	private String description;
	private CommandSection section;
	
	public Command(String name, String description, CommandSection section) {
		this.name = name;
		this.description = description;
		this.section = section;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public CommandSection getSection() {
		return section;
	}
	
	public abstract void execute(String[] args, MessageCreateEvent event);
}
