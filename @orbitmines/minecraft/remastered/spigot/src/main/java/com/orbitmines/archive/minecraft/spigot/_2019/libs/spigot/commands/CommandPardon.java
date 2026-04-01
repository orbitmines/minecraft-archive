package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft._2019.libs.database.models.punishment.Pardon;
import com.orbitmines.archive.minecraft._2019.libs.database.models.punishment.Punishment;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomChannel;
import com.orbitmines.archive.minecraft._2019.libs.discord.OMDiscordBot;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.MessageArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.PlayerModelArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor2;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.TimeUtils;
import com.orbitmines.archive.minecraft._2019.utils.UUIDUtils;
import com.orbitmines.archive.minecraft._2019.utils.language.Language;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public abstract class CommandPardon<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    private CommandPardon(S plugin, String command, String... aliases) {
        super(plugin, command, aliases);

        withArg(
            new PlayerModelArgument<S, P>(false).
                withArg(
                    new MessageArgument<S, P>("reason").executes((Executor2<S, P,
                        PlayerModel, PlayerModelArgument<S, P>,
                        String, MessageArgument<S, P>>
                    ) (player, model, reason) -> {
                        pardon(player, model, getType(), reason);
                    })
                )
        );

        requires(StaffRank.MODERATOR);
    }

    public abstract Punishment.Type getType();

    public abstract String getLanguageNamespace();

    public abstract void onPardon(P pardonedBy, PlayerModel pardoned, Pardon pardon, Punishment punishment);

    public abstract String getPunishedByPrefix();

    public void pardon(P punishedBy, PlayerModel punished, Punishment.Type type, String reason) {
        Punishment activePunishment = Punishment.getActive(punished.getUUID(), type);

        if (activePunishment == null) {
            punishedBy.sendMessage("Commands", Color.RED, "spigot", "player.command." + getLanguageNamespace() + ".none_active", punished.getName(Name.RAW_COLORED) + "§7");
            return;
        }

        /* Create Pardon */
        Pardon pardon = new Pardon(activePunishment.getId(), punishedBy.getUUID(), reason);
        pardon.insert();
        activePunishment.setPardoned(true);
        activePunishment.update(Punishment.column.PARDONED);

        /* Handle Punish */
        onPardon(punishedBy, punished, pardon, activePunishment);

        /* Notify Discord of Punishment */
        notifyDiscord(punishedBy, punished, pardon, activePunishment);

        punishedBy.sendMessage("Commands", Color.SUCCESS, "spigot", "player.command." + getLanguageNamespace() + ".successful", punished.getName(Name.RAW_COLORED) + "§7");
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command." + getLanguageNamespace() + ".description");
    }

    private void notifyDiscord(P pardonedBy, PlayerModel pardoned, Pardon pardon, Punishment punishment) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor("PARDON (" + punishment.getType().toString() + ")");
        builder.setDescription("");
        builder.setColor(Color.LIME.getAwtColor());

        builder.addField("Player", pardoned.getRawName(), true);

        builder.addField("Date", DateUtils.format(punishment.getPunishedAt(), DateUtils.DATE_TIME_FORMAT), true);

        if (punishment.getDurationType() != Punishment.Duration.PERMANENT)
            builder.addField("Until", DateUtils.format(punishment.getPunishedUntil(), DateUtils.DATE_TIME_FORMAT), true);

        builder.addField("Would have expired in", punishment.getDurationType() == Punishment.Duration.PERMANENT ? "NEVER" : TimeUtils.humanFriendlyTimer(Language.ENGLISH, punishment.getMillisLeft()), true);

        String punishedBy = UUIDUtils.getName(punishment.getPunishedBy());
        builder.addField(getPunishedByPrefix(), punishedBy, true);

        builder.addField("Reason", punishment.getReason(), true);

        /* Pardon Fields */
        builder.addField("Pardoned Reason", pardon.getReason(), true);
        builder.addField("Pardoned On", DateUtils.format(pardon.getPardonedAt(), DateUtils.DATE_TIME_FORMAT), true);
        builder.addField("Pardoned By", pardonedBy.getRawName(), true);

//        if (Environment.get() == Environment.development) //TODO FIX, ALLOW DISCORD TO ACCESS OM IMAGES in /orbitmines/skins
//            try {
//                builder.setThumbnail(getPlugin().getSkinLibrary().getSkin(SkinLibrary.Type.BODY_3D, punished.getUUID()).toURI().toURL().toString());
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }

        OMDiscordBot bot = getPlugin().getDiscordBot();

        bot.withPlayerEmote(pardoned.getUUID(), pardoned.getRawName(), false, emote -> {
            TextChannel channel = bot.getTextChannel(CustomChannel.PUNISHMENTS);

            channel.sendMessage(bot.getPlayerDisplay(pardoned, emote, pardoned.getRawName()) + " has been PARDONED!").queue();
            channel.sendMessageEmbeds(builder.build()).queue();
        });
    }

    public static class Unban<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends CommandPardon<S, P> {

        public Unban(S plugin) {
            super(plugin, "unban");
        }

        @Override
        public Punishment.Type getType() {
            return Punishment.Type.BAN;
        }

        @Override
        public String getLanguageNamespace() {
            return "unban";
        }

        @Override
        public void onPardon(P pardonedBy, PlayerModel pardoned, Pardon pardon, Punishment punishment) {

        }

        @Override
        public String getPunishedByPrefix() {
            return "Banned by";
        }
    }

    public static class Unmute<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends CommandPardon<S, P> {

        public Unmute(S plugin) {
            super(plugin, "unmute");
        }

        @Override
        public Punishment.Type getType() {
            return Punishment.Type.MUTE;
        }

        @Override
        public String getLanguageNamespace() {
            return "unmute";
        }

        @Override
        public void onPardon(P pardonedBy, PlayerModel pardoned, Pardon pardon, Punishment punishment) {

        }

        @Override
        public String getPunishedByPrefix() {
            return "Muted by";
        }
    }
}
