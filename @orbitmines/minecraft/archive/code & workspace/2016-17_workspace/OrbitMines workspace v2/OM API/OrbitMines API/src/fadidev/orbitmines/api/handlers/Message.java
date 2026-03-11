package fadidev.orbitmines.api.handlers;

import fadidev.orbitmines.api.OrbitMinesAPI;

/**
 * Created by Fadi on 3-9-2016.
 */
public abstract class Message {

    public abstract String get(OMPlayer player, String... args);

    public void broadcast(String... args){
        for(OMPlayer omp : OrbitMinesAPI.getApi().getOMPlayers()){
            omp.getPlayer().sendMessage(get(omp, args));
        }
    }
}
