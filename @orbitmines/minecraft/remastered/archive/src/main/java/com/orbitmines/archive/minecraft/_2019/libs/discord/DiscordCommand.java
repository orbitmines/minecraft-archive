package com.orbitmines.archive.minecraft._2019.libs.discord;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.rank.Rank;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft._2019.utils.discord.commands.Command;
import lombok.Getter;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class DiscordCommand extends Command {

    @Getter protected final String description;
    protected final OMDiscordBot bot;

    @Getter private Rank rank;

    public DiscordCommand(OMDiscordBot bot, String description, String... alias) {
        super(alias);

        this.bot = bot;
        this.description = description;
    }

    public abstract void onDispatch(MessageReceivedEvent event, User user, Member member, MessageChannel channel, Message msg, String[] a);

    @Override
    public void dispatch(MessageReceivedEvent event, User user, MessageChannel channel, Message msg, String[] a) {
        Member member = bot.getGuild().getMember(user);

        if (!isEligible(member)) {
            if (this.rank instanceof StaffRank) {
                channel.sendMessage(member.getAsMention() + " that command doesn't exist. Use **!help** for help.").queue();
            } else {
                channel.sendMessage(member.getAsMention() + " you have to be a(n) " + this.rank.getName() + " in order to use this command.").queue();
            }

            return;
        }

        onDispatch(event, user, member, channel, msg, a);
    }

    public void requires(Rank rank) {
        this.rank = rank;
    }

    public boolean isEligible(Member member) {
        if (this.rank == null || this.rank.toString().equals(Rank.NONE))
            return true;

        if (this.rank instanceof StaffRank)
            return getStaffRank(member).ordinal() >= ((StaffRank) this.rank).ordinal();

        return getVipRank(member).ordinal() >= ((VipRank) this.rank).ordinal() || getStaffRank(member).ordinal() >= StaffRank.ADMIN.ordinal();
    }

    protected VipRank getVipRank(Member member) {
        for (Role role : member.getRoles()) {
            try {
                return VipRank.valueOf(role.getName().toUpperCase());
            } catch (IllegalArgumentException ex) {}
        }
        return VipRank.NONE;
    }

    protected StaffRank getStaffRank(Member member) {
        for (Role role : member.getRoles()) {
            try {
                return StaffRank.valueOf(role.getName().toUpperCase());
            } catch (IllegalArgumentException ex) {}
        }
        return StaffRank.NONE;
    }
}