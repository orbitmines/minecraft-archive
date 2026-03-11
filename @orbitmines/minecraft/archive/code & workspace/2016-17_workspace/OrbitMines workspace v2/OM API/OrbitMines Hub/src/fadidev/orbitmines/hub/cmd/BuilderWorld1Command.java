package fadidev.orbitmines.hub.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import fadidev.orbitmines.hub.OrbitMinesHub;
import org.bukkit.Location;

/**
 * Created by Fadi on 7-9-2016.
 */
public class BuilderWorld1Command extends Command {

    private OrbitMinesHub hub;
    private String[] alias = { "/builderworld" };

    public BuilderWorld1Command(){
        this.hub = OrbitMinesHub.getHub();
    }

    @Override
    public String[] getAlias() {
        return alias;
    }

    @Override
    public void dispatch(OMPlayer omp, String[] a) {
        if(omp.hasPerms(StaffRank.BUILDER))
            omp.getPlayer().teleport(new Location(hub.getBuilderWorld1(), 0.5, 7, 0.5));
        else
            omp.unknownCommand(a[0]);
    }
}
