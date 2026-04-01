package com.orbitmines.brigadier;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ExecuteCommandExceptionEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final CommandSender commandSender;
    private boolean cancelled;

    public ExecuteCommandExceptionEvent(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    public CommandSender getCommandSender() {
        return commandSender;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
