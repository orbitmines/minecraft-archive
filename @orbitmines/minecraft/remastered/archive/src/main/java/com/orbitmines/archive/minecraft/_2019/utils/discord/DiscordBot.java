package com.orbitmines.archive.minecraft._2019.utils.discord;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.discord.events.MessageListener;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.security.auth.login.LoginException;
import java.util.List;

public abstract class DiscordBot {

    @Getter private static DiscordBot instance;

    @Getter private final String token;
    @Getter private JDA jda;
    @Getter private final String serverId;

    public DiscordBot(String token, String serverId) {
        instance = this;

        this.token = token;
        this.serverId = serverId;
    }

    public abstract void processMessage(MessageReceivedEvent event);

    public void initialize() {
        initialize(OnlineStatus.ONLINE);
    }

    public void initialize(OnlineStatus status) {
        this.jda = buildJda(token, status);
        registerEvents();
    }

    private JDA buildJda(String token, OnlineStatus status) {
        JDABuilder jdaBuilder = JDABuilder.createDefault(token).
                setAutoReconnect(true).
                setActivity(null).
                setStatus(status);

        try {
            return jdaBuilder.build().awaitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setStatus(OnlineStatus status) {
        jda.getPresence().setStatus(status);
    }

    public void registerEvents() {
        jda.addEventListener(new MessageListener(this));
    }
    
    public Guild getGuild() {
        return jda.getGuildById(serverId);
    }

    public Guild getController() {
        return getGuild();
    }

    public Category getCategory(String category) {
        List<Category> list = getGuild().getCategoriesByName(category, true);
        return list.size() > 0 ? list.get(0) : null;
    }

    public Channel getChannel(DiscordChannel channel) {
        switch (channel.getChannelType()) {

            case TEXT:
                return getTextChannel(channel);
            case VOICE:
                return getVoiceChannel(channel);
            default:
                return null;
        }
    }

    public User getUserById(Long id) {
        return id != null ? jda.getUserById(id) : null;
    }

    public User getUserById(String id) {
        return id != null ? jda.getUserById(id) : null;
    }

    public TextChannel getTextChannel(DiscordChannel channel) {
        List<TextChannel> list = getGuild().getTextChannelsByName(channel.getName(), true);
        for (TextChannel c : list) {
            if (c.getParentCategory() != null && c.getParentCategory().getName().equals(channel.getCategoryName()))
                return c;
        }

        return null;
    }

    public VoiceChannel getVoiceChannel(DiscordChannel channel) {
        List<VoiceChannel> list = getGuild().getVoiceChannelsByName(channel.getName(), true);
        return list.size() > 0 ? list.get(0) : null;
    }

    public Role getRole(DiscordRole role) {
        List<Role> list = getGuild().getRolesByName(role.getName(), true);
        return list.size() > 0 ? list.get(0) : null;
    }

    public Role getRole(String name) {
        List<Role> list = getGuild().getRolesByName(name, true);
        return list.size() > 0 ? list.get(0) : null;
    }

    public RichCustomEmoji getEmote(DiscordEmote emote) {
        List<RichCustomEmoji> list = getGuild().getEmojisByName(emote.getName(), true);
        return list.size() > emote.index() ? list.get(emote.index()) : null;
    }

    public RichCustomEmoji getEmote(String name) {
        List<RichCustomEmoji> list = getGuild().getEmojisByName(name, true);
        return list.size() > 0 ? list.get(0) : null;
    }
}
