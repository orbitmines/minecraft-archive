package me.O_o_Fadi_o_O.OrbitMinesGalaxyGenerator.managers;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class GalaxyGenerator extends ChunkGenerator {

    private double plotsize;
    private double pathsize;
    private int roadheight;

    public GalaxyGenerator() {
        plotsize = 497;
        pathsize = 2;
        roadheight = 4;
    }

    @SuppressWarnings("deprecation")
	@Override
    public short[][] generateExtBlockSections(World world, Random random, int cx, int cz, BiomeGrid biomes) {
        int maxY = world.getMaxHeight();

        short[][] result = new short[maxY / 16][];

        double size = plotsize + pathsize;
        int valx;
        int valz;

        double n1;
        double n2;
        double n3;
        int mod2 = 0;
        int mod1 = 1;

        if(pathsize % 2 == 1){
            n1 = Math.ceil(((double) pathsize) / 2) - 2;
            n2 = Math.ceil(((double) pathsize) / 2) - 1;
            n3 = Math.ceil(((double) pathsize) / 2);
        }else{
            n1 = Math.floor(((double) pathsize) / 2) - 2;
            n2 = Math.floor(((double) pathsize) / 2) - 1;
            n3 = Math.floor(((double) pathsize) / 2);
        }

        if(pathsize % 2 == 1){
            mod2 = -1;
        }

        for(int x = 0; x < 16; x++){
            valx = (cx * 16 + x);

            for(int z = 0; z < 16; z++){
                int height = roadheight + 2;
                valz = (cz * 16 + z);

                biomes.setBiome(x, z, Biome.PLAINS);

                for(int y = 0; y < height; y++){
                    if(y == 0){
                    	setBlock(result, x, y, z, (short) org.bukkit.Material.OBSIDIAN.getId());
                    }
                    else if(y == roadheight){
                        if(valx % size == 0){
                    		setBlock(result, x, y, z, (short) org.bukkit.Material.STAINED_CLAY.getId());
                        }
                        else if(valz % size == 0){
                    		setBlock(result, x, y, z, (short) org.bukkit.Material.STAINED_CLAY.getId());
                        }
                        else{
                            if((valz - n2 + mod1) % size == 0 || (valz + n2 + mod2) % size == 0){
                            	setBlock(result, x, y, z, (short) org.bukkit.Material.AIR.getId());
                            }
                            else{
                                boolean found2 = false;
                                for(double i = n1; i >= 0; i--){
                                    if((valz - i + mod1) % size == 0 || (valz + i + mod2) % size == 0){
                                        found2 = true;
                                        break;
                                    }
                                }

                                if(found2){
                                    setBlock(result, x, y, z, (short) org.bukkit.Material.AIR.getId());
                                } 
                                else{
                                    boolean found3 = false;
                                    for(double i = n3; i >= 0; i--){
                                        if((valx - i + mod1) % size == 0 || (valx + i + mod2) % size == 0){
                                            found3 = true;
                                            break;
                                        }
                                    }

                                    if(found3){
                                        setBlock(result, x, y, z, (short) org.bukkit.Material.AIR.getId());
                                    } 
                                    else{
                                        setBlock(result, x, y, z, (short) org.bukkit.Material.AIR.getId());
                                    }
                                }
                            }
                        }
                    } 
                    else if(y == (roadheight + 1)){
                       
                    } 
                    else{
                        setBlock(result, x, y, z, (short) org.bukkit.Material.AIR.getId());
                    }
                }
            }
        }

        return result;
    }

    private void setBlock(short[][] result, int x, int y, int z, short blkid) {
        if(result[y >> 4] == null){
            result[y >> 4] = new short[4096];
        }
        result[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = blkid;
    }
    
    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
    	return Arrays.asList((BlockPopulator) new GalaxyPopulator());
   	}

    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, 0, roadheight + 2, 0);
    }
}
