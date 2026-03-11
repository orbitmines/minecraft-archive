package fadidev.orbitmines.hub.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import fadidev.orbitmines.hub.OrbitMinesHub;
import org.bukkit.Location;

/**
 * Created by Fadi on 7-9-2016.
 */
public class BuilderWorld2Command extends Command {

    private OrbitMinesHub hub;
    private String[] alias = { "/builderworld2" };

    public BuilderWorld2Command(){
        this.hub = OrbitMinesHub.getHub();
    }

    @Override
    public String[] getAlias() {
        return alias;
    }

    @Override
    public void dispatch(OMPlayer omp, String[] a) {
        if(omp.hasPerms(StaffRank.BUILDER) || hub.getBuilderPerms().contains(omp.getUUID()))
            omp.getPlayer().teleport(new Location(hub.getBuilderWorld2(), 0.5, 6, 0.5));
        else
            omp.unknownCommand(a[0]);
    }
}
