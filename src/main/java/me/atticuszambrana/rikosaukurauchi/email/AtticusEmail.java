package me.atticuszambrana.rikosaukurauchi.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import me.atticuszambrana.rikosaukurauchi.Main;

public class AtticusEmail {
	
	private String from;
	private String to;
	private String subject;
	private String payload;
	
	private String smtpHost;
	
	public AtticusEmail(String from, String to, String subject, String payload) {
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.payload = payload;
		this.smtpHost = Main.getConfig().getSMTPHost();
	}
	
	public AtticusEmail(String subject, String payload) {
		this.from = Main.getConfig().getAlertSenderEmail();
		this.to = Main.getConfig().getAdminEmail();
		this.subject = subject;
		this.payload = payload;
		this.smtpHost = Main.getConfig().getSMTPHost();
	}
	
	public void send() {
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", smtpHost);
		properties.setProperty("mail.smtp.port", Main.getConfig().getSMTPPort());
		Session session = Session.getDefaultInstance(properties);
	      try {
	          // Create a default MimeMessage object.
	          MimeMessage message = new MimeMessage(session);
	          // Set From: header field of the header.
	          message.setFrom(new InternetAddress(from));
	          // Set To: header field of the header.
	          message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	          // Set Subject: header field
	          message.setSubject(subject);
	          // Send the actual HTML message, as big as you like
	          message.setContent(payload, "text/html");
	          Transport.send(message);
	       } catch (MessagingException mex) {
	          mex.printStackTrace();
	       }
	}
}
