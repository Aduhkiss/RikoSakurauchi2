package me.atticuszambrana.rikosaukurauchi.config;

public class ConfigFile {
	
	/**
	 * Gson will be converting this (as raw JSON) to and from Java object form when saving data, and using it in the program
	 * @author Atticus Zambrana
	 */
	
	private String discordToken;
	private String applicationId;
	private String commandPrefix;
	
	private String adminEmail;
	private String alertSender;
	private String smtpHost;
	private String smtpPort;
	
	private String sql_host;
	private String sql_database;
	private String sql_username;
	private String sql_password;
	
	public ConfigFile(String discordToken, String applicationId, String commandPrefix, 
			String adminEmail, String alertSender, String smtpHost, String smtpPort,
			String sql_host, String sql_database, 
			String sql_username, String sql_password) {
		
		this.discordToken = discordToken;
		this.applicationId = applicationId;
		this.commandPrefix = commandPrefix;
		
		this.adminEmail = adminEmail;
		this.alertSender = alertSender;
		this.smtpHost = smtpHost;
		this.smtpPort = smtpPort;
		
		this.sql_host = sql_host;
		this.sql_database = sql_database;
		this.sql_username = sql_username;
		this.sql_password = sql_password;
	}
	
	public String getSMTPHost() {
		return smtpHost;
	}
	
	public String getSMTPPort() {
		return smtpPort;
	}
	
	public String getAdminEmail() {
		return adminEmail;
	}
	
	public String getAlertSenderEmail() {
		return alertSender;
	}
	
	public String getDiscordToken() {
		return discordToken;
	}
	
	public String getAppId() {
		return applicationId;
	}
	
	public String getCommandPrefix() {
		return commandPrefix;
	}
	
	public String getSQLHost() {
		return sql_host;
	}
	
	public String getSQLDatabase() {
		return sql_database;
	}
	
	public String getSQLUsername() {
		return sql_username;
	}
	
	public String getSQLPassword() {
		return sql_password;
	}
}
