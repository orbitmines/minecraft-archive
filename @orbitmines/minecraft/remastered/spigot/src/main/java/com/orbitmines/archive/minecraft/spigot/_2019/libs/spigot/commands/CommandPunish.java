package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft._2019.libs.database.models.punishment.Punishment;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomChannel;
import com.orbitmines.archive.minecraft._2019.libs.discord.OMDiscordBot;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.IntegerArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.MessageArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.PlayerModelArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.TimeUnitArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Namespace;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor2;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor4;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.publishers.PlayerBanPublisher;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.publishers.PlayerMessagePublisher;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.TimeUtils;
import com.orbitmines.archive.minecraft._2019.utils.language.Language;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.Calendar;
import java.util.Date;

public abstract class CommandPunish<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    private CommandPunish(S plugin, String command, String... aliases) {
        super(plugin, command, aliases);

        if (getType() != Punishment.Type.WARNING)
            withArg(
                new PlayerModelArgument<S, P>(false).
                    namespace(
                        new Namespace<S, P>("time", player -> player.translate("spigot", "player.command.namespace.punish.duration_type.time")).
                            withArg(
                                new IntegerArgument<S, P>("amount").
                                    withArg(
                                        new TimeUnitArgument<S, P>(TimeUtils.Unit.MINUTES, TimeUtils.Unit.HOURS, TimeUtils.Unit.DAYS).
                                            withArg(
                                                new MessageArgument<S, P>("reason").executes((Executor4<S, P,
                                                    PlayerModel, PlayerModelArgument<S, P>,
                                                    Integer, IntegerArgument<S, P>,
                                                    TimeUtils.Unit, TimeUnitArgument<S, P>,
                                                    String, MessageArgument<S, P>>
                                                ) (player, model, amount, unit, reason) -> {
                                                    punish(player, model, getType(), getDuration(unit), amount, reason);
                                                })
                                            )
                                    )
                            )
                    ).
                    namespace(
                        new Namespace<S, P>("permanent", player -> player.translate("spigot", "player.command.namespace.punish.duration_type.permanent")).
                            withArg(
                                new MessageArgument<S, P>("reason").executes((Executor2<S, P,
                                    PlayerModel, PlayerModelArgument<S, P>,
                                    String, MessageArgument<S, P>>
                                ) (player, model, reason) -> {
                                    punish(player, model, getType(), Punishment.Duration.PERMANENT, null, reason);
                                })
                            )
                    )
            );
        else
            withArg(
                new PlayerModelArgument<S, P>(false).
                    withArg(
                        new MessageArgument<S, P>("reason").executes((Executor2<S, P,
                            PlayerModel, PlayerModelArgument<S, P>,
                            String, MessageArgument<S, P>>
                        ) (player, model, reason) -> {
                            punish(player, model, getType(), null, null, reason);
                        })
                )
            );

        requires(StaffRank.MODERATOR);
    }

    public abstract Punishment.Type getType();

    public abstract String getLanguageNamespace();

    public abstract void onPunish(P punishedBy, PlayerModel punished, Punishment punishment);

    public abstract String getPunishedByPrefix();

    public abstract String getVerb();

    public void punish(P punishedBy, PlayerModel punished, Punishment.Type type, Punishment.Duration duration, Integer count, String reason) {
        if (punished.getStaffRank().ordinal() >= punishedBy.getStaffRank().ordinal()) {
            punishedBy.sendRawMessage("Commands", Color.ERROR, "You cannot punish that player!");
            return;
        }

        if (type != Punishment.Type.WARNING) {
            Punishment activePunishment = Punishment.getActive(punished.getUUID(), type);

            if (activePunishment != null) {
                if (activePunishment.getDurationType() != Punishment.Duration.PERMANENT)
                    punishedBy.sendMessage("Commands", Color.RED, "spigot", "player.command." + getLanguageNamespace() + ".already_punished_until", punished.getName(Name.RAW_COLORED) + "§7", "§c" + DateUtils.format(activePunishment.getPunishedUntil(), DateUtils.DATE_TIME_FORMAT) + "§7");
                else
                    punishedBy.sendMessage("Commands", Color.RED, "spigot", "player.command." + getLanguageNamespace() + ".already_permanently_punished", punished.getName(Name.RAW_COLORED) + "§7");

                return;
            }
        }

        Date punishedAt = DateUtils.now();
        Date punishedUntil = type != Punishment.Type.WARNING ? getPunishedUntil(punishedAt, duration, count) : new Date(punishedAt.getTime());

        /* Create Punishment */
        Punishment punishment = new Punishment(punished.getUUID(), type, punishedBy.getUUID(), punishedAt, punishedUntil, duration, count, reason);
        punishment.insert();

        /* Handle Punish */
        onPunish(punishedBy, punished, punishment);

        /* Notify Discord of Punishment */
        notifyDiscord(punishedBy, punished, punishment);

        if (type == Punishment.Type.WARNING) {
            punishedBy.sendMessage("Commands", Color.SUCCESS, "spigot", "player.command." + getLanguageNamespace() + ".successful", punished.getName(Name.RAW_COLORED) + "§7", "§c" + reason + "§7");
        } else {
            String length = duration == Punishment.Duration.PERMANENT ? "PERMANENT" : TimeUtils.humanFriendlyTimer(punishedBy.getLanguage(), punishment.getMillisLeft());

            punishedBy.sendMessage("Commands", Color.SUCCESS, "spigot", "player.command." + getLanguageNamespace() + ".successful", punished.getName(Name.RAW_COLORED) + "§7", "§c" + length + "§7", "§c" + reason + "§7");
        }
    }

    private Date getPunishedUntil(Date punishedAt, Punishment.Duration duration, Integer count) {
        Calendar c = Calendar.getInstance();
        c.setTime(punishedAt);

        if (duration == Punishment.Duration.PERMANENT) {
            c.add(Calendar.YEAR, 50);
            return c.getTime();
        }

        c.add(duration.getCalendar(), count);

        return c.getTime();
    }

    private Punishment.Duration getDuration(TimeUtils.Unit unit) {
        if (unit == null)
            return Punishment.Duration.PERMANENT;

        switch (unit) {

            case MINUTES:
                return Punishment.Duration.MINUTES;
            case HOURS:
                return Punishment.Duration.HOURS;
            case DAYS:
                return Punishment.Duration.DAYS;
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command." + getLanguageNamespace() + ".description");
    }

    private void notifyDiscord(P punishedBy, PlayerModel punished, Punishment punishment) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(punishment.getType().toString());
        builder.setDescription("");
        builder.setColor(punishment.getType().getColor().getAwtColor());

        builder.addField("Player", punished.getRawName(), true);

        builder.addField("Date", DateUtils.format(punishment.getPunishedAt(), DateUtils.DATE_TIME_FORMAT), true);

        if (punishment.getDurationType() != Punishment.Duration.PERMANENT && punishment.getType() != Punishment.Type.WARNING)
            builder.addField("Until", DateUtils.format(punishment.getPunishedUntil(), DateUtils.DATE_TIME_FORMAT), true);

        if (punishment.getType() != Punishment.Type.WARNING)
            builder.addField("Duration", punishment.getDurationType() == Punishment.Duration.PERMANENT ? "PERMANENTLY" : TimeUtils.humanFriendlyTimer(Language.ENGLISH, punishment.getMillisLeft()), true);

        builder.addField(getPunishedByPrefix(), punishedBy.getRawName(), true);

        builder.addField("Reason", punishment.getReason(), true);

//        if (Environment.get() == Environment.development) //TODO FIX, ALLOW DISCORD TO ACCESS OM IMAGES in /orbitmines/skins
//            try {
//                builder.setThumbnail(getPlugin().getSkinLibrary().getSkin(SkinLibrary.Type.BODY_3D, punished.getUUID()).toURI().toURL().toString());
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }

        OMDiscordBot bot = getPlugin().getDiscordBot();

        bot.withPlayerEmote(punished.getUUID(), punished.getRawName(), false, emote -> {
            TextChannel channel = bot.getTextChannel(CustomChannel.PUNISHMENTS);

            channel.sendMessage(bot.getPlayerDisplay(punished, emote, punished.getRawName()) + " has been " + getVerb() + "!").queue();
            channel.sendMessageEmbeds(builder.build()).queue();
        });
    }

    public static class Ban<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends CommandPunish<S, P> {

        public Ban(S plugin) {
            super(plugin, "ban");
        }

        @Override
        public Punishment.Type getType() {
            return Punishment.Type.BAN;
        }

        @Override
        public String getLanguageNamespace() {
            return "ban";
        }

        @Override
        public void onPunish(P punishedBy, PlayerModel punished, Punishment punishment) {
            /* Notify Bungeecord of Ban, so player gets kicked */
            new PlayerBanPublisher().publish(punishment);
        }

        @Override
        public String getPunishedByPrefix() {
            return "Banned by";
        }

        @Override
        public String getVerb() {
            return "BANNED";
        }
    }

    public static class Mute<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends CommandPunish<S, P> {

        public Mute(S plugin) {
            super(plugin, "mute");
        }

        @Override
        public Punishment.Type getType() {
            return Punishment.Type.MUTE;
        }

        @Override
        public String getLanguageNamespace() {
            return "mute";
        }

        @Override
        public void onPunish(P punishedBy, PlayerModel punished, Punishment punishment) {
            Language language = punished.getLanguage();

            new PlayerMessagePublisher().publish(
                    punished.getUUID(),
                    "§4§m---------------------------------------------\n" +
                    "  §c§l" + language.getString("spigot", "player.command.mute.message.title") + "\n" +
                    "     §7" + language.getString("spigot", "player.command.mute.message.warned_by", punishedBy.getName(Name.RAW_PREFIXED)) + "\n" +
                    "     §7" + language.getString("spigot", "player.command.mute.message.reason", "§c" + punishment.getReason() + "§7") + "\n" +
                    "     §7" + language.getString("spigot", "player.command.mute.message.duration",
                            "§c" + (
                                punishment.getDurationType() == Punishment.Duration.PERMANENT ?
                                    "§l" + language.getString("spigot", "player.command.mute.message.permanent") :
                                    TimeUtils.humanFriendlyTimer(language, punishment.getMillisLeft())
                            ) + "§7") + "\n" +
                    "§4§m---------------------------------------------"
            );

            //TODO Mute on Discord
        }

        @Override
        public String getPunishedByPrefix() {
            return "Muted by";
        }

        @Override
        public String getVerb() {
            return "MUTED";
        }
    }

    public static class Warn<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends CommandPunish<S, P> {

        public Warn(S plugin) {
            super(plugin, "warn");
        }

        @Override
        public Punishment.Type getType() {
            return Punishment.Type.WARNING;
        }

        @Override
        public String getLanguageNamespace() {
            return "warn";
        }

        @Override
        public void onPunish(P punishedBy, PlayerModel punished, Punishment punishment) {
            Language language = punished.getLanguage();

            new PlayerMessagePublisher().publish(
                punished.getUUID(),
                "§4§m---------------------------------------------\n" +
                "  §c§l" + language.getString("spigot", "player.command.warn.message.title") + "\n" +
                "     §7" + language.getString("spigot", "player.command.warn.message.warned_by", punishedBy.getName(Name.RAW_PREFIXED)) + "\n" +
                "     §7" + language.getString("spigot", "player.command.warn.message.reason", "§c" + punishment.getReason() + "§7") + "\n" +
                "§4§m---------------------------------------------"
            );
        }

        @Override
        public String getPunishedByPrefix() {
            return "Warned by";
        }

        @Override
        public String getVerb() {
            return "WARNED";
        }
    }
}
