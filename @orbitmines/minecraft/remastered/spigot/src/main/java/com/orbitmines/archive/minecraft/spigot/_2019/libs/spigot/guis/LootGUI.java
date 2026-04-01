package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.ServerList;
import com.orbitmines.archive.minecraft._2019.libs.database.models.loot.LootItem;
import com.orbitmines.archive.minecraft._2019.libs.database.models.loot.PeriodLootItem;
import com.orbitmines.archive.minecraft._2019.libs.database.models.vote.LastVote;
import com.orbitmines.archive.minecraft._2019.libs.loot.Rarity;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.loot.LootHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.loot.LootItemType;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.utils.SkullTexture;
import com.orbitmines.archive.minecraft._2019.utils.TimeUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.PlayerUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.text.TextBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilderInstance;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.MobEggBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.entities.Mob;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.PaginatableGUI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LootGUI<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends PaginatableGUI<P, P, LootItem[]> {

    public LootGUI(P viewer, P key) {
        super(54, "§0§lSpace Turtle", viewer, key, 9, 1);


        /* Votes */
        List<ServerList> serverLists = ServerList.ACTIVE;
        for (int i = 0; i < serverLists.size(); i++) {
            ServerList serverList = serverLists.get(i);

            set(1, 2 + i, new Item<P, MutableItemBuilder>(() -> {
                LastVote lastVote = this.key.getLastVote(serverList, false);
                boolean canVote = lastVote.canVote();

                ItemBuilder item = new ItemBuilder(canVote ? Material.TURTLE_EGG : Material.EGG, serverList.ordinal() + 1, (canVote ? "§9" : "§c") + "§lVote Link " + (serverList.ordinal() + 1));

                item.addLore("§7§o" + serverList.getDisplayName());
                item.addLore("");
                item.addLore("§7- §9§l250 Prisms");
                item.addLore("§7");

                if (canVote)
                    item.addLore("§a" + viewer.translate("spigot", "player.loot.votes.open_link"));
                else
                    item.addLore("§c" + viewer.translate("spigot", "player.loot.votes.vote_in", TimeUtils.humanFriendlyTimer(viewer.getLanguage(), lastVote.getMillisLeft())));

                if (canVote)
                    item.glow();

                return item;
            }, event -> {
                close();

                viewer.sendRawMessage(" §8§lOrbit§7§lMines §9§lVote Link " + (serverList.ordinal() + 1));

                TextBuilder<P> builder = new TextBuilder<>();
                builder.add(Color.SILVER, p -> "  - ");
                builder.add(Color.SILVER, p -> p.translate("spigot", "player.loot.votes.display_link", "§9§l" + serverList.getDisplayName() + "§r§7")).
                        click(ClickEvent.Action.OPEN_URL, p -> serverList.getUrl()).
                        hover(HoverEvent.Action.SHOW_TEXT, p -> "§7" + p.translate("spigot", "word.open_link") + " §9Vote Link " + (serverList.ordinal() + 1) + "§7.");

                builder.send(viewer);
                viewer.playSound(Sound.UI_BUTTON_CLICK);

                PlayerUtils.updateInventory(viewer.bukkit());
            }));
        }

        /* Period Loot */

        PeriodLootItem.Type[] periodLootTypes = PeriodLootItem.Type.values();
        for (int i = 0; i < periodLootTypes.length; i++) {
            PeriodLootItem.Type type = periodLootTypes[i];

            /* Dont display loot specificly for a server */
            if (type.getServer() != null && type.getServer() != viewer.server().getType())
                continue;

            boolean hasRank;

            switch (type) {
                case MONTHLY_VIP:
                case SURVIVAL_BACK_CHARGES:
                    hasRank = !this.key.getVipRank().isNone();
                    break;
                case SURVIVAL_SPAWNER_ITEM:
                    hasRank = this.key.getVipRank() == VipRank.EMERALD;
                    break;
                default:
                    hasRank = true;
                    break;
            }

            /* In order to center  */
            List<PeriodLootItem.Type> list = PeriodLootItem.Type.from(type.getServer());
            int size = list.size();
            int indexOf = list.indexOf(type);
            int row = type.getServer() == null ? 2 : 3;
            int slot = 4 - (size % 2 == 0 ? size / 2 : (size - 1) / 2);

            for (int j = 0; j < size; j++) {
                /* Skip middle slot whenever a the amount of types is even to center types correctly */
                if (slot == 4 && size % 2 == 0) {
                    slot++;
                }

                if (j == indexOf)
                    /* Found Slot */
                    break;

                slot++;
            }

            set(row, slot, new Item<P, MutableItemBuilder>(() -> {
                PeriodLootItem periodLootItem = this.key.getPeriodLootItem(type, false);

                boolean canCollect = hasRank && periodLootItem.canCollect();

                ItemBuilderInstance item = canCollect ? getClaimable(this.key, type) : new ItemBuilder(Material.EGG, 1);
                item.setDisplayName((canCollect ? "§a§l" : "§c§l") + getName(this.key, type));

                item.addLore("");
                for (String line : getDescription(type)) {
                    item.addLore(line);
                }
                item.addLore("");

                if (hasRank) {
                    if (canCollect)
                        item.addLore("§a" + viewer.translate("spigot", "player.period_loot.message.collect"));
                    else
                        item.addLore("§c" + viewer.translate("spigot", "player.period_loot.message.collect_in", TimeUtils.humanFriendlyTimer(viewer.getLanguage(), periodLootItem.getMillisLeft())));
                } else {
                    item.addLore("§c" + viewer.translate("spigot", "player.period_loot.message.insufficient_rank"));
                }

                if (canCollect)
                    item.glow();

                return item;
            }, event -> {
                PeriodLootItem periodLootItem = this.key.getPeriodLootItem(type, false);
                LootHandler handler = viewer.server().newLootHandler(this.key);

                if (!hasRank || !periodLootItem.canCollect() || !handler.canCollect(periodLootItem.getType()))
                    return;

                handler.collect(periodLootItem);
                update();
            }));
        }

        /* Loot */
        paginate(4, 0);

        setPreviousPage(5, 0, () -> new PlayerSkullBuilder("Green Arrow Left", SkullTexture.GREEN_ARROW_LEFT, 1, "§7« " + viewer.translate("spigot", "player.loot.paginate")));
        setNextPage(5, 8, () -> new PlayerSkullBuilder("Green Arrow Right", SkullTexture.GREEN_ARROW_RIGHT, 1, "§7" + viewer.translate("spigot", "player.loot.paginate") + " »"));

    }

    @Override
    public Item<P, MutableItemBuilder> getItem(PageItem<LootItem[]> pageItem) {
        return new Item<>(() -> {
            LootItem[] items = pageItem.getObject();

            if (items == null)
                return null;

            LootItemType loot = LootItemType.from(items[0].getType());
            int count = 0;

            for (LootItem item : items) {
                count += item.getCount();
            }

            ItemBuilder item = loot.getIcon(count);
            item.setDisplayName(loot.getDisplayName(count));

            if (loot != LootItemType.BUYCRAFT_VOUCHER) {
                Rarity rarity = items[0].getRarity();
                item.addLore("§7" + viewer.translate("spigot", "player.loot.rarity") + ": " + rarity.getDisplayName());
            }

            Server server = loot.getServer(count);

            if (server != null)
                item.addLore("§7Server: " + loot.getServer(count).getDisplayName());

            item.addLore("");

            for (String line : items[0].getDescription()) {
                item.addLore("§2§l§o" + line);
            }

            return item;
        }, event -> {
            LootItem[] items = pageItem.getObject();

            if (items == null)
                return;

            LootItemType loot = LootItemType.from(items[0].getType());
            int count = 0;

            for (LootItem item : items) {
                count += item.getCount();
            }

            Server server = loot.getServer(count);

            if (server != null && server != viewer.server().getType()) {
                viewer.sendMessage("Loot", Color.ERROR, "spigot", "player.loot.server_restricted", server.getDisplayName() + "§7");
                return;
            }

            viewer.server().newLootHandler(this.key).collect(loot, items);

            update();
        });
    }

    @Override
    public List<LootItem[]> getCollection() {
        List<List<LootItem>> collection = new ArrayList<>();

        loop:
        for (LootItem item : this.key.getLootItems(false)) {
            for (List<LootItem> list : collection) {
                if (!list.get(0).isSimilarTo(item))
                    continue;

                list.add(item);
                continue loop;
            }

            collection.add(new ArrayList<>(Collections.singletonList(item)));
        }

        return collection.stream().
                map(list -> list.toArray(new LootItem[0])).
                collect(Collectors.toList());
    }

    @Override
    public Class<LootItem[]> getTypeClass() {
        return LootItem[].class;
    }

    private String getName(P player, PeriodLootItem.Type type) {
        return player.translate("spigot", "player.period_loot." + type.toString().toLowerCase() + ".name");
    }

    private String[] getDescription(PeriodLootItem.Type type) {
        switch (type) {

            case DAILY:
                return new String[] {
                    "§7- §9§l250 Prisms"
                };
            case MONTHLY:
                return new String[] {
                    "§7- §9§l2,500 Prisms",
                    "§7- §e§l50 Solars"
                };
            case MONTHLY_VIP:
                return new String[] {
                        this.key.getVipRank() == VipRank.IRON ? VipRank.IRON.getDisplayName() : "§8§l" + VipRank.IRON.getName(),
                        this.key.getVipRank() == VipRank.IRON ? "§7- §e§l150 Solars" : "§8- §l150 Solars",
                        this.key.getVipRank() == VipRank.GOLD ? VipRank.GOLD.getDisplayName() : "§8§l" + VipRank.GOLD.getName(),
                        this.key.getVipRank() == VipRank.GOLD ? "§7- §e§l300 Solars" : "§8- §l300 Solars",
                        this.key.getVipRank() == VipRank.DIAMOND ? VipRank.DIAMOND.getDisplayName() : "§8§l" + VipRank.DIAMOND.getName(),
                        this.key.getVipRank() == VipRank.DIAMOND ? "§7- §e§l600 Solars" : "§8- §l600 Solars",
                        this.key.getVipRank() == VipRank.EMERALD ? VipRank.EMERALD.getDisplayName() : "§8§l" + VipRank.EMERALD.getName(),
                        this.key.getVipRank() == VipRank.EMERALD ? "§7- §e§l1,000 Solars" : "§8- §l1,000 Solars",
                };
            case SURVIVAL_BACK_CHARGES:
                return new String[] {
                    this.key.getVipRank() == VipRank.IRON ? VipRank.IRON.getDisplayName() : "§8§l" + VipRank.IRON.getName(),
                    this.key.getVipRank() == VipRank.IRON ? "§7- §6§l50 Back Charges" : "§8- §l50 Back Charges",
                    this.key.getVipRank() == VipRank.GOLD ? VipRank.GOLD.getDisplayName() : "§8§l" + VipRank.GOLD.getName(),
                    this.key.getVipRank() == VipRank.GOLD ? "§7- §6§l125 Back Charges" : "§8- §l125 Back Charges",
                    this.key.getVipRank() == VipRank.DIAMOND ? VipRank.DIAMOND.getDisplayName() : "§8§l" + VipRank.DIAMOND.getName(),
                    this.key.getVipRank() == VipRank.DIAMOND ? "§7- §6§200 Back Charges" : "§8- §l200 Back Charges",
                    this.key.getVipRank() == VipRank.EMERALD ? VipRank.EMERALD.getDisplayName() : "§8§l" + VipRank.EMERALD.getName(),
                    this.key.getVipRank() == VipRank.EMERALD ? "§7- §6§l300 Back Charges" : "§8- §l300 Back Charges",
                };
            case SURVIVAL_SPAWNER_ITEM:
                return new String[] {
                    this.key.getVipRank() == VipRank.EMERALD ? VipRank.EMERALD.getDisplayName() : "§8§l" + VipRank.EMERALD.getName(),
                    this.key.getVipRank() == VipRank.EMERALD ? "§7- §5§l1 Spawner Miner" : "§8- §l1 Spawner Miner"
                };
        }
        throw new IllegalArgumentException();
    }

    private ItemBuilderInstance getClaimable(P player, PeriodLootItem.Type type) {
        switch (type) {

            case DAILY:
                return new MobEggBuilder(Mob.CHICKEN, 1);
            case MONTHLY:
                return new MobEggBuilder(Mob.PUFFERFISH, 1);
            case MONTHLY_VIP:
                switch (player.getVipRank()) {

                    case IRON:
                        return new MobEggBuilder(Mob.POLAR_BEAR, 1);
                    case GOLD:
                        return new MobEggBuilder(Mob.BLAZE, 1);
                    case DIAMOND:
                        return new MobEggBuilder(Mob.VEX, 1);
                    case EMERALD:
                        return new MobEggBuilder(Mob.SLIME, 1);
                    default:
                        return new MobEggBuilder(Mob.ENDERMAN, 1);
                }
            case SURVIVAL_BACK_CHARGES:
                return new ItemBuilder(Material.ENDER_EYE);
            case SURVIVAL_SPAWNER_ITEM:
                return new ItemBuilder(Material.DIAMOND_PICKAXE).addFlag(ItemFlag.HIDE_ATTRIBUTES);
        }
        throw new IllegalArgumentException();
    }
}
