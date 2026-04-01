package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.builder;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.mojang.brigadier.context.CommandContext;
import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Argument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Executor;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Namespace;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.text.TextBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.text.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommandHelpBuilder<S extends OMServer<S, P>, P extends OMPlayer<S, P>> {

    private static final Color VALID = Color.BLUE;
    private static final Color INVALID = Color.ERROR;
    private static final Color CURRENT = Color.BLUE;
    private static final Color EMPTY = Color.BLUE;

    private final Executor<S, P> executor;

    public CommandHelpBuilder() {
        this.executor = this::handle;
    }

    public Executor<S, P> asExecutor() {
        return executor;
    }

    private void handle(S plugin, P player, Command<S, P> command, ArgumentBuilder<S, P, ?> lastArg, CommandContext context) {
        player.sendRawMessage("");
        player.sendRawMessage("Commands", Color.ERROR, command.getDescription(player) + ":");

        TextBuilder<P> builder = new TextBuilder<>();
        builder.add(Color.ERROR, p -> "Commands »").space();
        append(builder, player, command, lastArg, context);

        builder.send(player);
    }

    public void append(TextBuilder<P> builder, P player, Command<S, P> command, ArgumentBuilder<S, P, ?> lastArg, boolean applyNext) {
        append(builder, player, command, lastArg, "/" + command.getName(), applyNext);
    }

    public void append(TextBuilder<P> builder, P player, Command<S, P> command, ArgumentBuilder<S, P, ?> lastArg, String commandInput, boolean applyNext) {
        builder.add(VALID, p -> commandInput).
                hover(HoverEvent.Action.SHOW_TEXT, p -> getCommandToolTip(command, p, commandInput)).
                click(ClickEvent.Action.SUGGEST_COMMAND, p -> commandInput);

        int currentLevel = lastArg.getLevel() + 1;

        /* Current Arg -> Show delimiter what user has typed so far (This argument went wrong or they stopped here) */
        applyCurrentArg(builder, player, lastArg, currentLevel, false);

        if (applyNext)
            return;

        /* Next Args -> Show all available options */
        applyNextArgs(builder, player, lastArg, currentLevel + 1);
    }

    public void append(TextBuilder<P> builder, P player, Command<S, P> command, ArgumentBuilder<S, P, ?> lastArg, CommandContext context) {
        String commandInput = context.getInput().split(" ")[0];

        builder.add(VALID, p -> commandInput).
                hover(HoverEvent.Action.SHOW_TEXT, p -> getCommandToolTip(command, p, commandInput));

        /* Previous Args -> Show chosen args */
        applyPreviousArgs(builder, player, lastArg, context);

        int currentLevel = lastArg.getLevel() + 1;

        /* Current Arg -> Show delimiter what user has typed so far (This argument went wrong or they stopped here) */
        applyCurrentArg(builder, player, lastArg, currentLevel, true);

        /* Next Args -> Show all available options */
        applyNextArgs(builder, player, lastArg, currentLevel + 1);
    }

    private void applyPreviousArgs(TextBuilder<P> builder, P playerExecutor, ArgumentBuilder<S, P, ?> lastArg, CommandContext context) {
        ArrayList<ArgumentBuilder<S, P, ?>> previous = this.executor.allArgs(lastArg);

        int index = 0; /* allArgs includes command, so we start at 0, not one */
        for (ArgumentBuilder<S, P, ?> arg : previous) {
            if (arg instanceof Argument) {
                Argument argument = (Argument) arg;
                String input = executor.getString(context, argument);
                Object value = argument.getValue(playerExecutor, input);
                boolean valid = argument.isValid(value);

                if (valid) {
                    TextComponent<P> component = builder.space().
                            add(VALID, player -> input);

                    String tooltip = argument.getValidTooltip(playerExecutor, value);

                    if (tooltip != null)
                        component.hover(HoverEvent.Action.SHOW_TEXT, player -> tooltip);
                } else {
                    StringBuilder invalidReason = new StringBuilder(INVALID.getCc() + argument.invalidReason(playerExecutor, input, value));

                    if (argument.hasSuggestions()) {
                        Set<String> examples = argument.getExamples(playerExecutor, 5);
                        if (examples.size() != 0) {
                            invalidReason.append("\n\n§7").append(playerExecutor.translate("spigot", "word.examples")).append(":");

                            for (String example : examples) {
                                invalidReason.append("\n  §7- ").append(VALID.getCc()).append(example);
                            }
                        }
                    }

                    builder.space().
                            add(INVALID, player -> input).hover(HoverEvent.Action.SHOW_TEXT, player -> invalidReason.toString());
                }
            } else if (arg instanceof Namespace) {
                Set<String> availableNamespaces = getNamespaces(arg.getParent());

                String input = context.getInput().split(" ")[index];
                boolean valid = availableNamespaces.contains(input.toLowerCase());

                if (valid) {
                    builder.space().
                            add(VALID, player -> input);
                } else {
                    builder.space().
                            add(INVALID, player -> input).hover(HoverEvent.Action.SHOW_TEXT, player -> VALID.getCc() + formatNamespaces(availableNamespaces));
                }
            }

            index++;
        }
    }

    private void applyCurrentArg(TextBuilder<P> builder, P playerExecutor, ArgumentBuilder<S, P, ?> root, int level, boolean emphasize) {
        applyArg(builder, playerExecutor, root, level, emphasize);
    }

    private void applyNextArgs(TextBuilder<P> builder, P playerExecutor, ArgumentBuilder<S, P, ?> root, int level) {
        while (getChildrenAtLevel(root, level).size() != 0) {
            applyArg(builder, playerExecutor, root, level, false);

            level++;
        }
    }

    private void applyArg(TextBuilder<P> builder, P playerExecutor, ArgumentBuilder<S, P, ?> root, int level, boolean current) {
        Color color = current ? CURRENT : EMPTY;

        List<ArgumentBuilder<S, P, ?>> children = getChildrenAtLevel(root, level);

        if (children.size() == 0)
            return;

        boolean lonely = children.size() == 1;

        String argument = stringify(root, playerExecutor, level);

        StringBuilder argBuilder = new StringBuilder().
            append(color.getCc()).append("§l").append(argument);

        if (lonely) {
            Argument arg = (Argument) children.get(0);

            argBuilder.append("\n").append("§7").append(getDescription(arg, playerExecutor));

            appendExamples(argBuilder, arg, playerExecutor, 3);
        } else {
            for (ArgumentBuilder<S, P, ?> child : children) {
                argBuilder.
                    append("\n\n").
                    append(color.getCc()).append("§l").append(simpleStringify(child)).append("\n").
                    append("§7").append(getDescription(child, playerExecutor));

                if (child instanceof Argument)
                    appendExamples(argBuilder, (Argument) child, playerExecutor, 2);
            }
        }

        builder.space().
                add(color, player -> argument).
                    bold(current).
                    hover(HoverEvent.Action.SHOW_TEXT, player -> argBuilder.toString());
    }

    private void appendExamples(StringBuilder argBuilder, Argument argument, P player, int limit) {
        if (!argument.hasSuggestions())
            return;

        Set<String> examples = argument.getExamples(player, limit);

        if (examples.size() == 0)
            return;

        argBuilder.append("\n\n  §7").append(player.translate("spigot", "word.examples")).append(":");

        for (String example : examples) {
            argBuilder.append("\n   §7- ").append(VALID.getCc()).append(example);
        }
    }

    private String stringify(ArgumentBuilder<S, P, ?> root, P playerExecutor, int level) {
        List<ArgumentBuilder<S, P, ?>> relevantChildren = new ArrayList<>();
        for (ArgumentBuilder<S, P, ?> child : getChildrenAtLevel(root, level)) {
            if (child.getRank() == null || child.getRank() instanceof VipRank || playerExecutor.isEligible(child.getRank()))
                relevantChildren.add(child);
        }

        boolean allOptional = true;
        for (ArgumentBuilder<S, P, ?> child : relevantChildren) {
            if (isOptional(child))
                continue;

            allOptional = false;
            break;
        }

        boolean includesNamespace = includesNamespace(relevantChildren);

        StringBuilder argument = new StringBuilder();

        if (allOptional)
            argument.append("(");
        if (includesNamespace)
            argument.append("[");

        int index = 0;
        loop:
        for (ArgumentBuilder<S, P, ?> child : relevantChildren) {
            if (index != 0) {
//                /* Skip arguments with an identical name */
//                for (int i = 0; i < index; i++) {
//                    if (child instanceof Argument && children.get(i) instanceof Argument && child.)
//                        continue loop;
//                }

                argument.append("|");
            }

            boolean optional = !allOptional && isOptional(child);

            if (optional)
                argument.append("(");

            argument.append(simpleStringify(child));

            if (optional)
                argument.append(")");

            index++;
        }

        if (includesNamespace)
            argument.append("]");
        if (allOptional)
            argument.append(")");

        return argument.toString();
    }

    private String simpleStringify(ArgumentBuilder<S, P, ?> arg) {
        if (arg instanceof Argument)
            return toString((Argument) arg);
        else if (arg instanceof Namespace)
            return toString((Namespace) arg);
        else
            return "null";
    }

    /* If any of the previous arguments are executable, then this one is optional */
    private boolean isOptional(ArgumentBuilder<S, P, ?> arg) {
        while (arg.getParent() != null) {
            if (arg.getParent().isExecutable())
                return true;

            arg = arg.getParent();
        }

        return false;
    }

    private boolean includesNamespace(List<ArgumentBuilder<S, P, ?>> children) {
        for (ArgumentBuilder<S, P, ?> child : children) {
            if (child instanceof Namespace)
                return true;
        }

        return false;
    }

    private List<ArgumentBuilder<S, P, ?>> getChildrenAtLevel(ArgumentBuilder<S, P, ?> root, int level) {
        List<ArgumentBuilder<S, P, ?>> children = new ArrayList<>();
        addChildrenRecursively(children, level, root);

        return children;
    }

    private void addChildrenRecursively(List<ArgumentBuilder<S, P, ?>> children, int level, ArgumentBuilder<S, P, ?> parent) {
        if (parent.getChildren().size() == 0)
            return;

        int childLevel = parent.getChildren().get(0).getLevel();

        if (childLevel == level) {
            children.addAll(parent.getChildren());
            return;
        }

        for (ArgumentBuilder<S, P, ?> child : parent.getChildren()) {
            addChildrenRecursively(children, level, child);
        }
    }

    private String toString(Argument<S, P, ?, ?> argument) {
        return "<" + argument.getName() + ">";
    }

    private String toString(Namespace<S, P> namespace) {
        return namespace.getName();
    }

    private String getDescription(ArgumentBuilder arg, P player) {
        if (arg instanceof Argument)
            return ((Argument) arg).getDescription(player);
        else if (arg instanceof Namespace)
            return ((Namespace) arg).getDescription().toString(player);
        else
            return "null";
    }

    private String getCommandToolTip(Command<S, P> command, P player, String commandInput) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(VALID.getCc()).append("§l").append(commandInput);

        if (!command.getRank().isNone())
            stringBuilder.append("§r §7(").append(command.getRank().getDisplayName()).append("§7)");

        stringBuilder.append("\n").append("§7").append(command.getDescription(player));

        Set<String> aliases = getAlias(command, commandInput);
        if (aliases.size() > 0) {
            stringBuilder.append("\n\n  ").append("§7").append(player.translate("spigot", "word.aliases")).append(":");

            for (String alias : aliases) {
                stringBuilder.append("\n   §7- §9").append("/").append(alias);
            }
        }

        return stringBuilder.toString();
    }

    private Set<String> getAlias(Command command, String commandInput) {
        Set<String> all = command.getAllCommands();
        all.remove(commandInput.substring(1));
        return all;
    }

    private Set<String> getNamespaces(ArgumentBuilder<S, P, ?> builder) {
        Set<String> namespaces = new HashSet<>();

        for (ArgumentBuilder<S, P, ?> child : builder.getChildren()) {
            if (child instanceof Namespace)
                namespaces.add(((Namespace) child).getName().toLowerCase());
        }

        return namespaces;
    }

    /* Argument Formatters */
    private String formatNamespaces(Set<String> namespaces) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<");

        int index = 0;
        for (String namespace : namespaces) {
            if (index != 0)
                stringBuilder.append("|");

            stringBuilder.append(namespace);

            index++;
        }

        stringBuilder.append(">");

        return stringBuilder.toString();
    }
}
