package fadidev.orbitmines.hub.handlers;

import fadidev.orbitmines.api.utils.enums.Server;
import org.bukkit.block.Block;

import java.util.List;

/**
 * Created by Fadi on 8-9-2016.
 */
public class ServerPortal {

    private Server server;
    private List<Block> blocks;

    public ServerPortal(Server server, List<Block> blocks){
        this.server = server;
        this.blocks = blocks;
    }

    public Server getServer() {
        return server;
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}
