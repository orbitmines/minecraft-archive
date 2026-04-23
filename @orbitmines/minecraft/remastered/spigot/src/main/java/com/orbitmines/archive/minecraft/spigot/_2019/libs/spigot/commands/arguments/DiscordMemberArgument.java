package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.discord.OMDiscordBot;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Argument;
import lombok.Getter;
import net.dv8tion.jda.api.entities.Member;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DiscordMemberArgument<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Argument<S, P, Member, DiscordMemberArgument> {

    private static final int LIMIT = 10;

    @Getter private final OMDiscordBot bot;
    @Getter protected final String name;

    public DiscordMemberArgument(OMDiscordBot bot, String name) {
        this.bot = bot;
        this.name = name;
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.argument.discord_member.description");
    }

    @Override
    public Member getValue(P player, String string) {
        Member member = firstOrNull(bot.getGuild().getMembersByEffectiveName(string, true));
        if (member != null)
            return member;

        member = firstOrNull(bot.getGuild().getMembersByName(string, true));
        if (member != null)
            return member;

        member = firstOrNull(bot.getGuild().getMembersByNickname(string, true));
        if (member != null)
            return member;

        player.sendMessage("Commands", Color.ERROR, "spigot", "player.command.argument.discord_member.invalid", string);
        return null;
    }

    private Member firstOrNull(List<Member> group) {
        return group.isEmpty() ? null : group.get(0);
    }

    @Override
    public String invalidReason(P player, String string, Member value) {
        return player.translate("spigot", "player.command.argument.player.invalid", string);
    }

    @Override
    public String getValidTooltip(P player, Member value) {
        return getTooltip(value).getString();
    }
    private Message getTooltip(Member member) {
        return member::getEffectiveName;
    }

    @Override
    public Set<String> getExamples(P p, int limit) {
        Set<String> examples = new HashSet<>();

        for (Member member : bot.getGuild().getMembers()) {
            if (member.getUser().isBot())
                continue;

            examples.add(member.getEffectiveName());

            if (examples.size() == limit)
                break;
        }

        return examples;
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(P p, CommandContext context, SuggestionsBuilder builder) throws CommandSyntaxException {
        String remaining = builder.getRemaining().toLowerCase();

        int count = 0;
        for (Member member : bot.getGuild().getMembers()) {
            if (member.getUser().isBot())
                continue;

            if (member.getEffectiveName().toLowerCase().startsWith(remaining)) {
                builder.suggest(member.getEffectiveName(), getTooltip(member));
                count++;
            } else if (member.getUser().getName().toLowerCase().startsWith(remaining)) {
                builder.suggest(member.getUser().getName(), getTooltip(member));
                count++;
            } else if (member.getNickname() != null && member.getNickname().toLowerCase().startsWith(remaining)) {
                builder.suggest(member.getNickname(), getTooltip(member));
                count++;
            }

            if (count == LIMIT)
                break;
        }

        return builder.buildFuture();
    }

    @Override
    public StringArgumentType.StringType getType() {
        return StringArgumentType.StringType.GREEDY_PHRASE;
    }

    @Override
    protected DiscordMemberArgument getInstance() {
        return this;
    }
}
