package fadidev.orbitmines.survival.runnables;

import fadidev.orbitmines.api.handlers.Database;
import fadidev.orbitmines.api.runnables.OMRunnable;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import fadidev.orbitmines.survival.OrbitMinesSurvival;
import fadidev.orbitmines.survival.utils.enums.Rarity;

import java.util.*;

/**
 * Created by Fadi-Laptop on 16-9-2016.
 */
public class MobHeadRunnable extends OMRunnable {

    private OrbitMinesSurvival survival;
    public MobHeadRunnable() {
        super(TimeUnit.HOUR, 1);

        survival = OrbitMinesSurvival.getSurvival();
    }

    @Override
    public void run() {
        Map<String, String> vipMap = Database.get().getStringEntries("Rank-VIP", "uuid", "vip");
        Map<String, String> staffMap = Database.get().getStringEntries("Rank-Staff", "uuid", "staff");

        Map<Rarity, List<UUID>> map = new HashMap<>();

        for(String uuid : vipMap.keySet()){
            if(uuid.length() <= 16)
                continue;

            VIPRank vipRank = VIPRank.valueOf(vipMap.get(uuid).toUpperCase());
            StaffRank staffRank = staffMap.containsKey(uuid) ? StaffRank.valueOf(staffMap.get(uuid).toUpperCase()) : StaffRank.USER;

            if(vipRank == VIPRank.USER && staffRank == StaffRank.USER)
                continue;

            int percentage = vipRank.ordinal() > staffRank.ordinal() + 1 ? vipRank.ordinal() : staffRank.ordinal() + 1;

            Rarity rarity = Rarity.from(percentage);

            if(!map.containsKey(rarity))
                map.put(rarity, new ArrayList<UUID>());

            map.get(rarity).add(UUID.fromString(uuid));
        }

        survival.setMobHeads(map);
    }
}
