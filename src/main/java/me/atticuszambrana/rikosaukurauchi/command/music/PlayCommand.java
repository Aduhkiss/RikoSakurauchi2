package me.atticuszambrana.rikosaukurauchi.command.music;

import java.util.NoSuchElementException;

import org.javacord.api.audio.AudioSource;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import me.atticuszambrana.rikosaukurauchi.common.Command;
import me.atticuszambrana.rikosaukurauchi.common.CommandSection;
import me.atticuszambrana.rikosaukurauchi.lavaplayer.LavaplayerAudioSource;

public class PlayCommand extends Command {

	public PlayCommand() {
		super("play", "Play a Song off YouTube", CommandSection.MUSIC);
	}

	@Override
	public void execute(String[] args, MessageCreateEvent event) {
		try {
			ServerVoiceChannel channel = event.getMessageAuthor().getConnectedVoiceChannel().get();
			
			channel.connect().thenAccept(audioConnection -> {
			    // Do stuff
				EmbedBuilder em = new EmbedBuilder();
				em.setTitle("Yay, I've joined!");
				em.setDescription("I have joined " + channel.getName() + "!");
				event.getChannel().sendMessage(em);
				
				
				// Then play music, I have stolen this part of code directly from the Javacord Wiki so all credit goes to them
				// -Atticus
				
				// Create a player manager
				AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
				playerManager.registerSourceManager(new YoutubeAudioSourceManager());
				AudioPlayer player = playerManager.createPlayer();

				// Create an audio source and add it to the audio connection's queue
				AudioSource source = new LavaplayerAudioSource(player);
				audioConnection.setAudioSource(source);

				// You can now use the AudioPlayer like you would normally do with Lavaplayer, e.g.,
				playerManager.loadItem("https://www.youtube.com/watch?v=u3pQv9mmZMk", new AudioLoadResultHandler() {
				    @Override
				    public void trackLoaded(AudioTrack track) {
				        player.playTrack(track);
				    }

				    @Override
				    public void playlistLoaded(AudioPlaylist playlist) {
				        for (AudioTrack track : playlist.getTracks()) {
				            player.playTrack(track);
				        }
				    }

				    @Override
				    public void noMatches() {
				        // Notify the user that we've got nothing
				    }

				    @Override
				    public void loadFailed(FriendlyException throwable) {
				        // Notify the user that everything exploded
				    }
				});
				
			}).exceptionally(e -> {
			    // Failed to connect to voice channel (no permissions?)
				EmbedBuilder em = new EmbedBuilder();
				em.setTitle("Hmm.. It didn't seem to work?");
				em.setDescription("An Error occurred that didn't allow me to join. Do I have the correct permissions?");
				event.getChannel().sendMessage(em);
			    e.printStackTrace();
			    return null;
			});
			
		} catch(NoSuchElementException ex) {
			EmbedBuilder em = new EmbedBuilder();
			em.setTitle("Oh no!");
			em.setDescription("You're not connected to any voice channels! Join one, then run the command again!");
			event.getChannel().sendMessage(em);
			return;
		}
		
	}

}
