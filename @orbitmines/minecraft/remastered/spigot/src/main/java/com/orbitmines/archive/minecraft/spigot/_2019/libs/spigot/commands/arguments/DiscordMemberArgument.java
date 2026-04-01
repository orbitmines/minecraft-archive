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
        String[] s = string.split("#");
        String name = s[0];
        String discriminator = s.length > 1 ? s[1] : "";

        Member member = getFromDiscriminator(discriminator, bot.getGuild().getMembersByEffectiveName(name, true));
        if (member != null)
            return member;

        member = getFromDiscriminator(discriminator, bot.getGuild().getMembersByName(name, true));
        if (member != null)
            return member;

        member = getFromDiscriminator(discriminator, bot.getGuild().getMembersByNickname(name, true));
        if (member != null)
            return member;

        player.sendMessage("Commands", Color.ERROR, "spigot", "player.command.argument.discord_member.invalid");
        return null;
    }

    private Member getFromDiscriminator(String discriminator, List<Member> group) {
        for (Member member : group) {
            if (member.getUser().getDiscriminator().equals(discriminator))
                return member;
        }
        return null;
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

            examples.add(member.getEffectiveName() + "#" + member.getUser().getDiscriminator());

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

            String discriminator = member.getUser().getDiscriminator();

            if (stringify(member.getEffectiveName().toLowerCase(), discriminator).startsWith(remaining)) {
                builder.suggest(member.getEffectiveName() + "#" + member.getUser().getDiscriminator(), getTooltip(member));
                count++;
            } else if (stringify(member.getUser().getName().toLowerCase(), discriminator).startsWith(remaining)) {
                builder.suggest(member.getUser().getName() + "#" + member.getUser().getDiscriminator(), getTooltip(member));
                count++;
            } else if (member.getNickname() != null && stringify(member.getNickname().toLowerCase(), discriminator).startsWith(remaining)) {
                builder.suggest(member.getNickname() + "#" + member.getUser().getDiscriminator(), getTooltip(member));
                count++;
            }

            if (count == LIMIT)
                break;
        }

        return builder.buildFuture();
    }

    private String stringify(String name, String discriminator) {
        return name + "#" + discriminator;
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
