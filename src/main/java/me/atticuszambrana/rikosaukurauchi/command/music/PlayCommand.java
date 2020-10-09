package me.atticuszambrana.rikosaukurauchi.command.music;

import java.util.NoSuchElementException;

import org.javacord.api.DiscordApi;
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
import me.atticuszambrana.rikosaukurauchi.common.YouTubeVideo;
import me.atticuszambrana.rikosaukurauchi.lavaplayer.LavaplayerAudioSource;
import me.atticuszambrana.rikosaukurauchi.util.StringUtil;

public class PlayCommand extends Command {
	
	private DiscordApi discord;

	public PlayCommand(DiscordApi discord) {
		super("play", "Play a Song off YouTube", CommandSection.MUSIC);
		this.discord = discord;
	}
	
	private YouTubeVideo video = null;

	@Override
	public void execute(String[] args, MessageCreateEvent event) {
		
		// Check to see if the user provided arguments
		if(args.length == 0) {
			EmbedBuilder em = new EmbedBuilder();
			em.setTitle("Oh no!");
			em.setDescription("Please make sure to also type the YouTube ID of the video you want me to play!");
			event.getChannel().sendMessage(em);
			return;
		}
		
		String search = StringUtil.combine(args, 0);
//		Gson gson = new Gson();
//		try {
//			video = gson.fromJson(HttpUtil.get("https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=1&order=relevance&q=" + search + "&key=AIzaSyAqpMNEn_Kv_97t9NHAQyDLUH9JtmB3ZuA"), YouTubeVideo.class);
//		} catch (JsonSyntaxException | IOException e1) {
//			e1.printStackTrace();
//		}
		
		try {
			ServerVoiceChannel channel = event.getMessageAuthor().getConnectedVoiceChannel().get();
			
			channel.connect().thenAccept(audioConnection -> {
			    // Do stuff
				EmbedBuilder em = new EmbedBuilder();
				em.setTitle("Yay, I've joined!");
				em.setDescription("I have joined " + channel.getName() + "!");
				//event.getChannel().sendMessage(em);
				
				
				// Then play music, I have stolen this part of code directly from the Javacord Wiki so all credit goes to them
				// -Atticus
				
				// Create a player manager
				AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
				playerManager.registerSourceManager(new YoutubeAudioSourceManager());
				AudioPlayer player = playerManager.createPlayer();

				// Create an audio source and add it to the audio connection's queue
				AudioSource source = new LavaplayerAudioSource(discord, player);
				audioConnection.setAudioSource(source);
				
				// Take the input of the user, and pass it on to the YouTube API in order to pull the video ID
				// https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=1&order=relevance&q=sad%20songs%20illenium&key=AIzaSyAqpMNEn_Kv_97t9NHAQyDLUH9JtmB3ZuA


				// You can now use the AudioPlayer like you would normally do with Lavaplayer
				
				// Some really weird string cleaning i did
				playerManager.loadItem(("https://www.youtube.com/watch?v=" + search).substring(0, ("https://www.youtube.com/watch?v=" + search).length() - 1), new AudioLoadResultHandler() {
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
						EmbedBuilder em = new EmbedBuilder();
						em.setTitle("Oh no!");
						em.setDescription("I wasn't able to find any YouTube videos matching your query!");
						event.getChannel().sendMessage(em);
						return;
				    }

				    @Override
				    public void loadFailed(FriendlyException throwable) {
				        // Notify the user that everything exploded
						EmbedBuilder em = new EmbedBuilder();
						em.setTitle("Oh no!");
						em.setDescription("Some weird error happened, and I wasn't able to play the song you asked for sorry, this has been reported to the developers!");
						event.getChannel().sendMessage(em);
						//TODO: Report this issue to the developers, along with a full stack trace and all the information we can possibly get
						return;
				    }
				});
				
			}).exceptionally(e -> {
			    // Failed to connect to voice channel (no permissions?)
				EmbedBuilder em = new EmbedBuilder();
				em.setTitle("Hmm.. It didn't seem to work?");
				em.setDescription("An Error occurred that didn't allow me to join. Do I have the correct permissions?");
				event.getChannel().sendMessage(em);
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
