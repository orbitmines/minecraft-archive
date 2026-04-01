package com.orbitmines.archive.minecraft._2019.libs.database.mysql;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Environment;
import com.orbitmines.archive.minecraft._2019.libs.Image;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomChannel;
import com.orbitmines.archive.minecraft._2019.libs.discord.OMDiscordBot;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.database.AsyncChecker;
import com.orbitmines.archive.minecraft._2019.utils.database.AsyncQuerier;
import com.orbitmines.archive.minecraft._2019.utils.database.DatabaseManager;
import com.orbitmines.archive.minecraft._2019.utils.database.SQLiteDatabase;
import com.orbitmines.archive.minecraft._2019.utils.database.model.mysql.MySQLModel;
import com.orbitmines.archive.minecraft._2019.utils.database.model.mysql.MySQLModelColumn;
import com.orbitmines.archive.minecraft._2019.utils.database.model.mysql.MySQLModelSelector;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public abstract class OMMySQLModel<Model extends OMMySQLModel, C extends MySQLModelColumn> extends MySQLModel<Model, C> {

    public OMMySQLModel() {
    }

    @Override
    protected SQLiteDatabase getDatabase() {
        return DatabaseManager.getInstance().getDefault();
    }

    private AsyncQuerier getAsyncQuerier() {
        return DatabaseManager.getInstance().getAsyncQuerier();
    }

    private AsyncChecker getAsyncChecker() {
        return DatabaseManager.getInstance().getAsyncChecker();
    }

    @Override
    public void insert() {
        if (getAsyncQuerier() == null) {
            onInsert();
            return;
        }

        if (getAsyncChecker().isAsync()) {
            onInsert();
        } else {
            this.inserted = true;
            getAsyncQuerier().queryAsync(this::onInsert);
        }
    }

    private void onInsert() {
        super.insert();

        if (lastSelectors != null)
            reloadAsLast(this.lastSelectors);

        if (logChannel != null)
            logToDiscord(LogAction.INSERT, getColumns());
    }

    @Override
    public void update(C... columns) {
        if (getAsyncQuerier() == null) {
            onUpdate(columns);
            return;
        }

        if (getAsyncChecker().isAsync()) {
            onUpdate(columns);
        } else {
            getAsyncQuerier().queryAsync(() -> onUpdate(columns));
        }
    }

    private void onUpdate(C... columns) {
        super.update(columns);

        if (logChannel != null)
            logToDiscord(LogAction.UPDATE, columns);
    }

    @Override
    public void delete() {
        if (getAsyncQuerier() == null) {
            onDelete();
            return;
        }

        if (getAsyncChecker().isAsync()) {
            onDelete();
        } else {
            this.destroyed = true;
            getAsyncQuerier().queryAsync(this::onDelete);
        }
    }

    private void onDelete() {
        super.delete();

        if (logChannel != null)
            logToDiscord(LogAction.DELETE, getColumns());
    }

    /*


        Reloading after Insert


     */

    private MySQLModelSelector[] lastSelectors;

    protected void setupReloadAfterInsert(MySQLModelSelector... lastSelectors) {
        this.lastSelectors = lastSelectors;
    }

    /*

        Logging

     */

    private Class<? extends MySQLModel> logModelClass;
    private Server logServer;
    private CustomChannel logChannel;

    protected void setupLog(Class<Model> clazz, Server server, CustomChannel channel) {
        logModelClass = clazz;
        logServer = server;
        logChannel = channel;
    }

    private void logToDiscord(LogAction action, C... columns) {
        new Thread(() -> {
            OMDiscordBot bot = OMDiscordBot.INSTANCE;
            TextChannel channel = bot.getTextChannel(logChannel);

            EmbedBuilder builder = new EmbedBuilder();
            builder.setAuthor(action.toString() + " " + logModelClass.getSimpleName());

            for (C column : columns) {
                String value = stringifyValue(column);
                builder.addField(column.getColumn().getName(), value != null ? value : "null", true);
            }

            builder.setFooter(logServer.getName() + " / " + Environment.get().toString() + " / " + DateUtils.format(DateUtils.now(), DateUtils.DATE_TIME_FORMAT), Image.icon(logServer).getUrl());
            builder.setColor(action.color.getAwtColor());

            channel.sendMessageEmbeds(builder.build()).queue();
        }).start();
    }

    @AllArgsConstructor
    private enum LogAction {

        INSERT(Color.LIME),
        UPDATE(Color.BLUE),
        DELETE(Color.RED);

        @Getter private final Color color;
    }
}
