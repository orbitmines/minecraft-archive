package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.discord.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.DiscordUser;
import com.orbitmines.archive.minecraft._2019.libs.discord.DiscordCommand;
import com.orbitmines.archive.minecraft._2019.libs.discord.OMDiscordBot;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.pubsub.publishers.PlayerDiscordLinkPublisher;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DiscordLinkCommand extends DiscordCommand {

    public DiscordLinkCommand(OMDiscordBot bot, String... alias) {
        super(bot, "Link your Discord and OrbitMines account", alias);
    }

    @Override
    public void onDispatch(MessageReceivedEvent event, User user, Member member, MessageChannel channel, Message msg, String[] a) {
        if (a.length != 2) {
            channel.sendMessage(user.getAsMention() + " Use " + a[0].toLowerCase() + " <in game name>").queue();
            return;
        }

        PlayerModel model = PlayerModel.findBy(PlayerModel.class, PlayerModel.column.NAME.is(a[1]));

        if (model == null) {
            channel.sendMessage(user.getAsMention() + " That player cannot be found!").queue();
            return;
        }

        DiscordUser.Linker linker = new DiscordUser.Linker(bot);

        DiscordUser discordUser = linker.link(user, model);
        switch (discordUser.getResult()) {

            case SETTING_UP:
                channel.sendMessage(user.getAsMention() + " Setting up Discord Link... Use **/discordlink " + user.getName() + "** on the server.").queue();
                break;
            case ALREADY_LINKED:
                channel.sendMessage(user.getAsMention() + " That OrbitMines account has already been linked to your Discord account.").queue();
                break;
            case SETUP_COMPLETE:
                bot.withPlayerEmote(model.getUUID(), model.getRawName(), false, emote -> {
                    channel.sendMessage(user.getAsMention() + " You have successfully linked " + bot.getPlayerDisplay(model, emote, model.getRawName()) + " to your account!").queue();
                });

                new PlayerDiscordLinkPublisher().publish(discordUser);
                break;
        }
    }
}
