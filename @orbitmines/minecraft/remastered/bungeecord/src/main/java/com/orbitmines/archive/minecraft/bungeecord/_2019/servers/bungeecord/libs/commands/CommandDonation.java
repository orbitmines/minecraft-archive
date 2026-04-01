package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.commands;

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.database.models.Donation;
import com.orbitmines.archive.minecraft._2019.libs.database.models.loot.LootItem;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomChannel;
import com.orbitmines.archive.minecraft._2019.libs.loot.Rarity;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.BungeePlayer;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.Bungeecord;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.discord.BungeeDiscordBot;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.pubsub.publishers.PlayerDonationPublisher;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.command.ConsoleCommand;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;

import java.util.UUID;

/*
* OrbitMines - @author Fadi Shawki - 2017
*/
public class CommandDonation extends ConsoleCommand {

    private final Bungeecord bungee;
    private final BungeeDiscordBot bot;

    public CommandDonation(Bungeecord bungee) {
        super("donation");

        this.bungee = bungee;
        this.bot = bungee.getDiscordBot();
    }

    /*
        /donation <id> {uuid} {price} {currency} {date} {time}
     */

    @Override
    public void execute(CommandSender sender, String[] a) {
        Donation.Type donation = Donation.Type.getById(Integer.parseInt(a[0]));
        String name = a[1];
        UUID uuid = UUID.fromString(a[2]);
        double price = Double.parseDouble(a[3]);
        String currency = a[4];

        String[] d = (a[5].substring(0, 6) + "20" + a[5].substring(6, 8)).split("/");
        String date = d[2] + "-" + d[1] + "-" + d[0] + " " + a[6] + ":00";
        boolean addToLoot = donation != Donation.Type.DONATION &&(a.length < 8 || Boolean.parseBoolean(a[7]));
        Donation.Type lootDonation = a.length < 9 ? null : (a[8].equals("-1") ? null : Donation.Type.getById(Integer.parseInt(a[8])));

        Donation donationModel = new Donation(uuid, donation, price, currency, DateUtils.parse(date, DateUtils.DATE_TIME_FORMAT));
        donationModel.insert();

        if (donation == Donation.Type.UNKNOWN)
            return;

        if (addToLoot) {
            String description = "§3§l§oDonation (" + date + ")";

            new LootItem(uuid, LootItem.Type.DONATION, Rarity.LEGENDARY, lootDonation != null ? lootDonation.getId() : donation.getId(), description).insert();

            BungeePlayer omp = bungee.getPlayer(uuid);
            if (omp != null)
                omp.sendMessage("Shop", Color.LIME, "bungeecord", "connection.thanks_for_donation", "§2/loot§7");
        }

        new PlayerDonationPublisher().publish(uuid, donationModel);

        bot.withPlayerEmote(uuid, name, false, emote -> {
            TextChannel channel = bot.getTextChannel(CustomChannel.DONATIONS);

            OfflinePlayer player = OfflinePlayer.get(uuid);

            channel.sendMessage(bot.getPlayerDisplay(player, emote, name) + " has made a donation to OrbitMines!").queue();

            EmbedBuilder builder = new EmbedBuilder();
            builder.setAuthor("Donation by " + name);
            builder.setColor(Color.TEAL.getAwtColor());

            builder.addField("Item", ChatColor.stripColor(donation.getTitle()), true);
            builder.addField("Date", date, true);

            builder.setThumbnail(donation.getImage().getUrl());

            channel.sendMessageEmbeds(builder.build()).queue(); 
        });
    }
}
