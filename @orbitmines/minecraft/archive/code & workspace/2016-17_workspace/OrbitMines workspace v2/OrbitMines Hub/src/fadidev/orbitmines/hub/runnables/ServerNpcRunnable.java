package fadidev.orbitmines.hub.runnables;

import fadidev.orbitmines.api.runnables.OMRunnable;
import fadidev.orbitmines.api.utils.enums.Server;
import fadidev.orbitmines.hub.OrbitMinesHub;

/**
 * Created by Fadi on 10-9-2016.
 */
public class ServerNpcRunnable extends OMRunnable {

    private OrbitMinesHub hub;

    public ServerNpcRunnable() {
        super(TimeUnit.SECOND, 5);

        hub = OrbitMinesHub.getHub();
    }

    @Override
    public void run() {
        for(Server server : hub.getServerNpcs().keySet()){
            server.updateNPC(hub.getServerNpcs().get(server).getArmorStand());
        }
    }
}
