package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerSettings;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.publishers.PlayerLanguageChangePublisher;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.settings.PlayerVisibility;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.utils.LanguageUtils;
import com.orbitmines.archive.minecraft._2019.utils.EnumUtils;
import com.orbitmines.archive.minecraft._2019.utils.state.StateProvider;
import com.orbitmines.archive.minecraft._2019.utils.language.Language;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ColorUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.BannerBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemFlag;

import java.util.Arrays;

public class SettingsGUI<P extends OMPlayer> extends GUI<P> {

    private final P settingsFor;

    public SettingsGUI(P viewer, P settingsFor) {
        super(36, "§0§lSettings", viewer);

        this.settingsFor = settingsFor;

        int index = 0;
        for (PlayerSettings.Type type : PlayerSettings.Type.values()) {
            set(1, 1 + index, new Item<P, MutableItemBuilder>(() -> getItemBuilder(type).setDisplayName("§7§l" + type.getTitle(viewer))));

            set(2, 1 + index, new Item<P, MutableItemBuilder>(() -> {
                PlayerSettings settings = settingsFor.getSettings(type, false);
                PlayerSettings.Level level = settings.getLevel();

                Material material = ColorUtils.getStainedGlassMaterial(level.getColor());

                return new ItemBuilder(material, 1, level.getColor().getCc() + "§l" + level.getTranslation(viewer));
            }, event -> {
                PlayerSettings settings = settingsFor.getSettings(type, false);
                settings.setLevel(EnumUtils.next(PlayerSettings.Level.class, Arrays.asList(type.getLevels()), settings.getLevel()));

                settings.insertOrUpdate(PlayerSettings.column.LEVEL);

                viewer.playSound(Sound.UI_BUTTON_CLICK);
                update();

                if (settings.getType() == PlayerSettings.Type.PLAYER_VISIBILITY && settingsFor instanceof PlayerVisibility)
                    ((PlayerVisibility) settingsFor).updateVisibility();
            }));

            index++;
        }

        set(1, 7, new Item<P, MutableItemBuilder>(() -> {
            BannerBuilder builder = LanguageUtils.getBanner(settingsFor.getLanguage());
            builder.setDisplayName("§7§l" + viewer.translate("spigot", "word.language"));
            builder.addFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);

            return builder;
        }));

        set(2, 7, new Item<P, MutableItemBuilder>(() -> {
            String language = "§9" + settingsFor.getLanguage().getTranslationOf(settingsFor.getLanguage()) + (settingsFor.getLanguage() != Language.ENGLISH ? " §7/ §9" + Language.ENGLISH.getTranslationOf(settingsFor.getLanguage()) : "");

            return new ItemBuilder(Material.BLUE_STAINED_GLASS, 1, language);
        }, event -> {
            Language language = EnumUtils.next(Language.class, settingsFor.getLanguage());
            settingsFor.setLanguage(language);
            settingsFor.update(PlayerModel.column.LANGUAGE);

            viewer.playSound(Sound.UI_BUTTON_CLICK);
            update();

            new PlayerLanguageChangePublisher().publish(settingsFor, language);

            StateProvider.getInstance().setPlayerField(settingsFor.getUUID(), "language", language.toString());
        }));
    }

    @Override
    public void beforeUpdateAsync() {
        /* Trigger load */
        settingsFor.getFriends(false);
    }

    private ItemBuilder getItemBuilder(PlayerSettings.Type type) {
        switch (type) {

            case PRIVATE_MESSAGES:
                return new ItemBuilder(Material.WRITABLE_BOOK);
            case PLAYER_VISIBILITY:
                return new ItemBuilder(Material.ENDER_EYE);
            case SCOREBOARD_VISIBILITY:
                return new ItemBuilder(Material.PAPER);
            case GADGETS:
                return new ItemBuilder(Material.COMPASS);
            case STATS:
                return new ItemBuilder(Material.EMERALD);
        }
        throw new NullPointerException();
    }
}
