package me.O_o_Fadi_o_O.OrbitMinesGalaxyGenerator.managers;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public class GalaxyPopulator extends BlockPopulator {
    private double plotsize;
    private double pathsize;

    private int roadheight;

    public GalaxyPopulator() {
        plotsize = 88;
        pathsize = 15;
        roadheight = 70;
    }

    @Override
	public void populate(World world, Random rand, Chunk chunk) {
        int cx = chunk.getX();
        int cz = chunk.getZ();

        int xx = cx << 4;
        int zz = cz << 4;

        double size = plotsize + pathsize;
        
        for(int x = 0; x < 16; x++){
        	int valx = (xx + x);

            for(int z = 0; z < 16; z++){
                int height = roadheight + 2;
                int valz = (zz + z);

                for(int y = 0; y < height; y++){
                    if(y == roadheight){

                    	if(valx % size == 0){
                    		setData(world, x, y, z, (byte) 15);
                        }
                        else if(valz % size == 0){
                        	setData(world, x, y, z, (byte) 15);
                        }
                        else{}
                    } 
                }
            }
        }
	}

    @SuppressWarnings("deprecation")
    private void setData(World w, int x, int y, int z, byte b) {
        w.getBlockAt(x, y, z).setData(b);
    }
}