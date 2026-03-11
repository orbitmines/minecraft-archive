package fadidev.orbitmines.survival.runnables;

import fadidev.orbitmines.api.handlers.Database;
import fadidev.orbitmines.api.handlers.StringInt;
import fadidev.orbitmines.api.runnables.OMRunnable;
import fadidev.orbitmines.api.utils.UUIDUtils;
import fadidev.orbitmines.survival.OrbitMinesSurvival;

import java.util.*;

/**
 * Created by Fadi-Laptop on 16-9-2016.
 */
public class TopMoneyRunnable extends OMRunnable {

    private OrbitMinesSurvival survival;
    public TopMoneyRunnable() {
        super(TimeUnit.HOUR, 1);

        survival = OrbitMinesSurvival.getSurvival();
    }

    @Override
    public void run() {
        Map<String, Integer> map = Database.get().getIntEntries("Survival-Money", "uuid", "money");
        survival.setTopMoney(getTopTen(map));
    }

    private List<StringInt> getTopTen(Map<String, Integer> map){
        StringInt player1 = new StringInt(null, 0);
        StringInt player2 = new StringInt(null, 0);
        StringInt player3 = new StringInt(null, 0);
        StringInt player4 = new StringInt(null, 0);
        StringInt player5 = new StringInt(null, 0);
        StringInt player6 = new StringInt(null, 0);
        StringInt player7 = new StringInt(null, 0);
        StringInt player8 = new StringInt(null, 0);
        StringInt player9 = new StringInt(null, 0);
        StringInt player10 = new StringInt(null, 0);

        for(String uuid : map.keySet()){
            int votes = map.get(uuid);
            if(votes >= player1.getInteger()){
                player10 = player9;
                player9 = player8;
                player8 = player7;
                player7 = player6;
                player6 = player5;
                player5 = player4;
                player4 = player3;
                player3 = player2;
                player2 = player1;
                player1 = new StringInt(uuid, votes);
            }
            else if(votes >= player2.getInteger()){
                player10 = player9;
                player9 = player8;
                player8 = player7;
                player7 = player6;
                player6 = player5;
                player5 = player4;
                player4 = player3;
                player3 = player2;
                player2 = new StringInt(uuid, votes);
            }
            else if(votes >= player3.getInteger()){
                player10 = player9;
                player9 = player8;
                player8 = player7;
                player7 = player6;
                player6 = player5;
                player5 = player4;
                player4 = player3;
                player3 = new StringInt(uuid, votes);
            }
            else if(votes >= player4.getInteger()){
                player10 = player9;
                player9 = player8;
                player8 = player7;
                player7 = player6;
                player6 = player5;
                player5 = player4;
                player4 = new StringInt(uuid, votes);
            }
            else if(votes >= player5.getInteger()){
                player10 = player9;
                player9 = player8;
                player8 = player7;
                player7 = player6;
                player6 = player5;
                player5 = new StringInt(uuid, votes);
            }
            else if(votes >= player6.getInteger()){
                player10 = player9;
                player9 = player8;
                player8 = player7;
                player7 = player6;
                player6 = new StringInt(uuid, votes);
            }
            else if(votes >= player7.getInteger()){
                player10 = player9;
                player9 = player8;
                player8 = player7;
                player7 = new StringInt(uuid, votes);
            }
            else if(votes >= player8.getInteger()){
                player10 = player9;
                player9 = player8;
                player8 = new StringInt(uuid, votes);
            }
            else if(votes >= player9.getInteger()){
                player10 = player9;
                player9 = new StringInt(uuid, votes);
            }
            else if(votes >= player10.getInteger()){
                player10 = new StringInt(uuid, votes);
            }
        }

        List<StringInt> playerMoney = Arrays.asList(player1, player2, player3, player4, player5, player6, player7, player8, player9, player10);

        for(StringInt player : playerMoney){
            if(player.getString() != null){
                String name = UUIDUtils.getName(UUID.fromString(player.getString()));

                if(name != null)
                    player.setString(name);
            }
        }

        return playerMoney;
    }
}
