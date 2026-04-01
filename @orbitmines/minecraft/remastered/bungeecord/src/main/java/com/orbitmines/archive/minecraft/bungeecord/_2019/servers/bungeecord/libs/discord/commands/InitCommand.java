package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.discord.commands;

import com.orbitmines.archive.minecraft._2019.libs.discord.CustomRole;
import com.orbitmines.archive.minecraft._2019.libs.discord.DiscordCommand;
import com.orbitmines.archive.minecraft._2019.libs.discord.OMDiscordBot;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
// Guild removed in JDA 5 - methods moved to Guild

import java.util.ArrayList;
import java.util.List;

public class InitCommand extends DiscordCommand {

    public InitCommand(OMDiscordBot bot, String... alias) {
        super(bot, "Setup discord bot", alias);

        requires(StaffRank.DEVELOPER);
    }

    @Override
    public void onDispatch(MessageReceivedEvent event, User user, Member member, MessageChannel channel, Message msg, String[] args) {
        ArrayList<String> roles = new ArrayList<>();
        member.getRoles().forEach(role -> roles.add(role.getName()));

        Guild g = event.getGuild();
        Guild gc = g;

        Role r = bot.getRole(CustomRole.MEMBER);

        List<Member> members = g.getMembers();
        for (int i = 0; i<members.size(); i++) {
            Member m = members.get(i);
            if (!m.getRoles().contains(r) && !m.getUser().isBot())
                gc.addRoleToMember(m,r).queue();
        }
    }
}
