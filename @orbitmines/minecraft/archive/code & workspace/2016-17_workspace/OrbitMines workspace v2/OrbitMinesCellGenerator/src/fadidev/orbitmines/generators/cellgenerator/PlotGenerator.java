package fadidev.orbitmines.generators.cellgenerator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PlotGenerator extends ChunkGenerator {

    private double plotsize;
    private double pathsize;
    private int roadheight;

    public PlotGenerator() {
        plotsize = 28;
        pathsize = 15;
        roadheight = 70;
    }

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
                int height = roadheight + 2 + 11 + 5;
                valz = (cz * 16 + z);

                biomes.setBiome(x, z, Biome.PLAINS);

                for(int y = 0; y < height; y++){
                    if(y == 0){
                    	setBlock(result, x, y, z, (short) org.bukkit.Material.BEDROCK.getId());
                    }
                    else if(y == roadheight || y == roadheight + 11){
                        if(valx % size != 0 && (valz % size == 1 || valz % size == -1 || valz % size == (plotsize + 14) || valz % size == -(plotsize + 14))){
                        	if((valz % size == 1 && valx % size == 1) || (valz % size == 1 && valx % size == -1) || (valz % size == -1 && valx % size == 1) || ((valz % size == -1 && valx % size == -1)) || (valz % size == (plotsize + 14) && valx % size == 1) || (valz % size == (plotsize + 14) && valx % size == -1) || (valz % size == -(plotsize + 14) && valx % size == 1) || ((valz % size == -(plotsize + 14) && valx % size == -1))){
                				setBlock(result, x, y, z, (short) Material.OBSIDIAN.getId());
                            }
                        	else{
                        		List<org.bukkit.Material> materials = Arrays.asList(Material.COBBLESTONE, org.bukkit.Material.STEP);//15, 12

                        		int index = 0;
                        		boolean found = false;
                        		for(int i = 12; i <= plotsize +2; i += 6){
                        			if(- valx % size <= i && - valx % size >= -i){
                        				setBlock(result, x, y, z, (short) materials.get(index).getId());
                        				found = true;

	                        			index++;
	                        			if(index == 2){
	                        				index = 0;
	                        			}
                        			}
                        		}

                        		if(!found){
                        			if(- valx % size <= (plotsize + 13) && - valx % size >= -(plotsize + 13)){
                        				setBlock(result, x, y, z, (short) org.bukkit.Material.STEP.getId());//12
                            		}
                        			else{
                        				setBlock(result, x, y, z, (short) org.bukkit.Material.OBSIDIAN.getId());
                            		}
                        		}
                        	}
                        }
                        else if(valz % size != 0 && (valx % size == 1 || valx % size == -1 || valx % size == (plotsize + 14) || valx % size == -(plotsize + 14))){
                        	if((valx % size == 1 && valz % size == 1) || (valx % size == 1 && valz % size == -1) || (valx % size == -1 && valz % size == 1) || ((valx % size == -1 && valz % size == -1)) || (valx % size == (plotsize + 14) && valz % size == 1) || (valx % size == (plotsize + 14) && valz % size == -1) || (valx % size == -(plotsize + 14) && valz % size == 1) || ((valx % size == -(plotsize + 14) && valz % size == -1))){
                				setBlock(result, x, y, z, (short) org.bukkit.Material.OBSIDIAN.getId());
                        	}
                        	else{
                        		List<org.bukkit.Material> materials = Arrays.asList(org.bukkit.Material.COBBLESTONE, org.bukkit.Material.STEP);//15, 12

                        		int index = 0;
                        		boolean found = false;
                        		for(int i = 12; i <= plotsize +2; i += 6){
                        			if(- valz % size <= i && - valz % size >= -i){
                        				setBlock(result, x, y, z, (short) materials.get(index).getId());
                        				found = true;

	                        			index++;
	                        			if(index == 2){
	                        				index = 0;
	                        			}
                        			}
                        		}

                        		if(!found){
                        			if(- valz % size <= (plotsize + 13) && - valz % size >= -(plotsize + 13)){
                        				setBlock(result, x, y, z, (short) org.bukkit.Material.STEP.getId());//12
                        			}
                        			else{
                                		setBlock(result, x, y, z, (short) org.bukkit.Material.OBSIDIAN.getId());
                        			}
                        		}
                        	}
                        }
                        else if(valx % size != 0 && (valz % size == 2 || valz % size == -2 || valz % size == (plotsize + 13) || valz % size == -(plotsize + 13))){
                        	if((valz % size == 2 && valx % size == 2) || (valz % size == 2 && valx % size == -2) || (valz % size == -2 && valx % size == 2) || ((valz % size == -2 && valx % size == -2)) || (valz % size == (plotsize + 13) && valx % size == 2) || (valz % size == (plotsize + 13) && valx % size == -2) || (valz % size == -(plotsize + 13) && valx % size == 2) || ((valz % size == -(plotsize + 13) && valx % size == -2))){
                				setBlock(result, x, y, z, (short) org.bukkit.Material.STEP.getId());//12
                            }
                        	else{
                        		List<org.bukkit.Material> materials = Arrays.asList(org.bukkit.Material.OBSIDIAN, org.bukkit.Material.COBBLESTONE);//0, 15

                        		int index = 0;
                        		boolean found = false;
                        		for(int i = 12; i <= plotsize +2; i += 6){
                        			if(- valx % size <= i && - valx % size >= -i){
                        				setBlock(result, x, y, z, (short) materials.get(index).getId());
                        				found = true;

	                        			index++;
	                        			if(index == 2){
	                        				index = 0;
	                        			}
                        			}
                        		}

                        		if(!found){
                        			if(- valx % size <= (plotsize + 12) && - valx % size >= -(plotsize + 12)){
                        				setBlock(result, x, y, z, (short) org.bukkit.Material.COBBLESTONE.getId());//15
                            		}
                        			else{
                        				setBlock(result, x, y, z, (short) org.bukkit.Material.STEP.getId());//12
                            		}
                        		}
                        	}
                        }
                        else if(valz % size != 0 && (valx % size == 2 || valx % size == -2 || valx % size == (plotsize + 13) || valx % size == -(plotsize + 13))){
                        	if((valx % size == 2 && valz % size == 2) || (valx % size == 2 && valz % size == -2) || (valx % size == -2 && valz % size == 2) || ((valx % size == -2 && valz % size == -2)) || (valx % size == (plotsize + 13) && valz % size == 2) || (valx % size == (plotsize + 13) && valz % size == -2) || (valx % size == -(plotsize + 13) && valz % size == 2) || ((valx % size == -(plotsize + 13) && valz % size == -2))){
                				setBlock(result, x, y, z, (short) org.bukkit.Material.STEP.getId());//12
                        	}
                        	else{
                        		List<org.bukkit.Material> materials = Arrays.asList(org.bukkit.Material.OBSIDIAN, org.bukkit.Material.COBBLESTONE);//0, 15

                        		int index = 0;
                        		boolean found = false;
                        		for(int i = 12; i <= plotsize +2; i += 6){
                        			if(- valz % size <= i && - valz % size >= -i){
                        				setBlock(result, x, y, z, (short) materials.get(index).getId());
                        				found = true;

	                        			index++;
	                        			if(index == 2){
	                        				index = 0;
	                        			}
                        			}
                        		}

                        		if(!found){
                        			if(- valz % size <= (plotsize + 12) && - valz % size >= -(plotsize + 12)){
                        				setBlock(result, x, y, z, (short) org.bukkit.Material.COBBLESTONE.getId());//15
                        			}
                        			else{
                                		setBlock(result, x, y, z, (short) org.bukkit.Material.STEP.getId());//12
                        			}
                        		}
                        	}
                        }
                        else if(valx % size != 0 && (valz % size == 3 || valz % size == -3 || valz % size == (plotsize + 12) || valz % size == -(plotsize + 12))){
                        	if((valz % size == 3 && valx % size == 3) || (valz % size == 3 && valx % size == -3) || (valz % size == -3 && valx % size == 3) || ((valz % size == -3 && valx % size == -3)) || (valz % size == (plotsize + 12) && valx % size == 3) || (valz % size == (plotsize + 12) && valx % size == -3) || (valz % size == -(plotsize + 12) && valx % size == 3) || ((valz % size == -(plotsize + 12) && valx % size == -3))){
                				setBlock(result, x, y, z, (short) org.bukkit.Material.COBBLESTONE.getId());//15
                            }
                        	else{
                        		List<org.bukkit.Material> materials = Arrays.asList(org.bukkit.Material.COBBLESTONE, org.bukkit.Material.STEP);//15, 12

                        		int index = 0;
                        		boolean found = false;
                        		for(int i = 12; i <= plotsize +2; i += 6){
                        			if(- valx % size <= i && - valx % size >= -i){
                        				setBlock(result, x, y, z, (short) materials.get(index).getId());
                        				found = true;

	                        			index++;
	                        			if(index == 2){
	                        				index = 0;
	                        			}
                        			}
                        		}

                        		if(!found){
                        			if(- valx % size <= (plotsize + 11) && - valx % size >= -(plotsize + 11)){
                        				setBlock(result, x, y, z, (short) org.bukkit.Material.STEP.getId());//12
                            		}
                        			else{
                        				setBlock(result, x, y, z, (short) org.bukkit.Material.COBBLESTONE.getId());//15
                            		}
                        		}
                        	}
                        }
                        else if(valz % size != 0 && (valx % size == 3 || valx % size == -3 || valx % size == (plotsize + 12) || valx % size == -(plotsize + 12))){
                        	if((valx % size == 3 && valz % size == 3) || (valx % size == 3 && valz % size == -3) || (valx % size == -3 && valz % size == 3) || ((valx % size == -3 && valz % size == -3)) || (valx % size == (plotsize + 12) && valz % size == 3) || (valx % size == (plotsize + 12) && valz % size == -3) || (valx % size == -(plotsize + 12) && valz % size == 3) || ((valx % size == -(plotsize + 12) && valz % size == -3))){
                				setBlock(result, x, y, z, (short) org.bukkit.Material.COBBLESTONE.getId());//15
                        	}
                        	else{
                        		List<org.bukkit.Material> materials = Arrays.asList(org.bukkit.Material.COBBLESTONE, org.bukkit.Material.STEP);//15, 12

                        		int index = 0;
                        		boolean found = false;
                        		for(int i = 12; i <= plotsize +2; i += 6){
                        			if(- valz % size <= i && - valz % size >= -i){
                        				setBlock(result, x, y, z, (short) materials.get(index).getId());
                        				found = true;

	                        			index++;
	                        			if(index == 2){
	                        				index = 0;
	                        			}
                        			}
                        		}

                        		if(!found){
                        			if(- valz % size <= (plotsize + 11) && - valz % size >= -(plotsize + 11)){
                        				setBlock(result, x, y, z, (short) org.bukkit.Material.STEP.getId());//12
                        			}
                        			else{
                                		setBlock(result, x, y, z, (short) org.bukkit.Material.COBBLESTONE.getId());//15
                        			}
                        		}
                        	}
                        }
                        else if(valx % size != 0 && (valz % size == 4 || valz % size == -4 || valz % size == (plotsize + 11) || valz % size == -(plotsize + 11))){
                        	if((valz % size == 4 && valx % size == 4) || (valz % size == 4 && valx % size == -4) || (valz % size == -4 && valx % size == 4) || ((valz % size == -4 && valx % size == -4)) || (valz % size == (plotsize + 11) && valx % size == 4) || (valz % size == (plotsize + 11) && valx % size == -4) || (valz % size == -(plotsize + 11) && valx % size == 4) || ((valz % size == -(plotsize + 11) && valx % size == -4))){
                				setBlock(result, x, y, z, (short) org.bukkit.Material.STEP.getId());//12
                            }
                        	else{
                        		List<org.bukkit.Material> materials = Arrays.asList(org.bukkit.Material.STEP, org.bukkit.Material.OBSIDIAN);//12, 0

                        		int index = 0;
                        		boolean found = false;
                        		for(int i = 12; i <= plotsize +2; i += 6){
                        			if(- valx % size <= i && - valx % size >= -i){
                        				setBlock(result, x, y, z, (short) materials.get(index).getId());
                        				found = true;

	                        			index++;
	                        			if(index == 2){
	                        				index = 0;
	                        			}
                        			}
                        		}

                        		if(!found){
                        			if(- valx % size <= (plotsize +10) && - valx % size >= -(plotsize +10)){
                        				setBlock(result, x, y, z, (short) org.bukkit.Material.OBSIDIAN.getId());
                            		}
                        			else{
                        				setBlock(result, x, y, z, (short) org.bukkit.Material.STEP.getId());//12
                            		}
                        		}
                        	}
                        }
                        else if(valz % size != 0 && (valx % size == 4 || valx % size == -4 || valx % size == (plotsize + 11) || valx % size == -(plotsize + 11))){
                        	if((valx % size == 4 && valz % size == 4) || (valx % size == 4 && valz % size == -4) || (valx % size == -4 && valz % size == 4) || ((valx % size == -4 && valz % size == -4)) || (valx % size == (plotsize + 11) && valz % size == 4) || (valx % size == (plotsize + 11) && valz % size == -4) || (valx % size == -(plotsize + 11) && valz % size == 4) || ((valx % size == -(plotsize + 11) && valz % size == -4))){
                				setBlock(result, x, y, z, (short) org.bukkit.Material.STEP.getId());//12
                        	}
                        	else{
                        		List<org.bukkit.Material> materials = Arrays.asList(org.bukkit.Material.STEP, org.bukkit.Material.OBSIDIAN);//12, 0

                        		int index = 0;
                        		boolean found = false;
                        		for(int i = 12; i <= plotsize +2; i += 6){
                        			if(- valz % size <= i && - valz % size >= -i){
                        				setBlock(result, x, y, z, (short) materials.get(index).getId());
                        				found = true;

	                        			index++;
	                        			if(index == 2){
	                        				index = 0;
	                        			}
                        			}
                        		}

                        		if(!found){
                        			if(- valz % size <= (plotsize +10) && - valz % size >= -(plotsize +10)){
                        				setBlock(result, x, y, z, (short) org.bukkit.Material.OBSIDIAN.getId());
                        			}
                        			else{
                                		setBlock(result, x, y, z, (short) org.bukkit.Material.STEP.getId());//12
                        			}
                        		}
                        	}
                        }
                        else if(valx % size != 0 && (valz % size == 5 || valz % size == -5 || valz % size == (plotsize +10) || valz % size == -(plotsize +10))){
                        	if((valz % size == 5 && valx % size == 5) || (valz % size == 5 && valx % size == -5) || (valz % size == -5 && valx % size == 5) || ((valz % size == -5 && valx % size == -5)) || (valz % size == (plotsize +10) && valx % size == 5) || (valz % size == (plotsize +10) && valx % size == -5) || (valz % size == -(plotsize +10) && valx % size == 5) || ((valz % size == -(plotsize +10) && valx % size == -5))){
                				setBlock(result, x, y, z, (short) org.bukkit.Material.OBSIDIAN.getId());
                            }
                        	else{
                        		List<org.bukkit.Material> materials = Arrays.asList(org.bukkit.Material.COBBLESTONE, org.bukkit.Material.STEP);//15, 12

                        		int index = 0;
                        		boolean found = false;
                        		for(int i = 12; i <= plotsize +2; i += 6){
                        			if(- valx % size <= i && - valx % size >= -i){
                        				setBlock(result, x, y, z, (short) materials.get(index).getId());
                        				found = true;

	                        			index++;
	                        			if(index == 2){
	                        				index = 0;
	                        			}
                        			}
                        		}

                        		if(!found){
                        			if(- valx % size <= (plotsize +9) && - valx % size >= -(plotsize +9)){
                        				setBlock(result, x, y, z, (short) org.bukkit.Material.STEP.getId());//12
                            		}
                        			else{
                        				setBlock(result, x, y, z, (short) org.bukkit.Material.OBSIDIAN.getId());
                            		}
                        		}
                        	}
                        }
                        else if(valz % size != 0 && (valx % size == 5 || valx % size == -5 || valx % size == (plotsize +10) || valx % size == -(plotsize +10))){
                        	if((valx % size == 5 && valz % size == 5) || (valx % size == 5 && valz % size == -5) || (valx % size == -5 && valz % size == 5) || ((valx % size == -5 && valz % size == -5)) || (valx % size == (plotsize +10) && valz % size == 5) || (valx % size == (plotsize +10) && valz % size == -5) || (valx % size == -(plotsize +10) && valz % size == 5) || ((valx % size == -(plotsize +10) && valz % size == -5))){
                				setBlock(result, x, y, z, (short) org.bukkit.Material.OBSIDIAN.getId());
                        	}
                        	else{
                        		List<org.bukkit.Material> materials = Arrays.asList(org.bukkit.Material.COBBLESTONE, org.bukkit.Material.STEP);//15, 12

                        		int index = 0;
                        		boolean found = false;
                        		for(int i = 12; i <= plotsize +2; i += 6){
                        			if(- valz % size <= i && - valz % size >= -i){
                        				setBlock(result, x, y, z, (short) materials.get(index).getId());
                        				found = true;

	                        			index++;
	                        			if(index == 2){
	                        				index = 0;
	                        			}
                        			}
                        		}

                        		if(!found){
                        			if(- valz % size <= (plotsize +9) && - valz % size >= -(plotsize +9)){
                        				setBlock(result, x, y, z, (short) org.bukkit.Material.STEP.getId());//12
                        			}
                        			else{
                                		setBlock(result, x, y, z, (short) org.bukkit.Material.OBSIDIAN.getId());
                        			}
                        		}
                        	}
                        }
                        else if(valx % size != 0 && (valz % size == 6 || valz % size == -6 || valz % size == (plotsize +9) || valz % size == -(plotsize +9))){
                        	List<org.bukkit.Material> materials = Arrays.asList(org.bukkit.Material.OBSIDIAN, org.bukkit.Material.COBBLESTONE);//0, 15

                    		int index = 0;
                    		boolean found = false;
                    		for(int i = 12; i <= plotsize +2; i += 6){
                    			if(- valx % size <= i && - valx % size >= -i){
                    				setBlock(result, x, y, z, (short) materials.get(index).getId());
                    				found = true;

                        			index++;
                        			if(index == 2){
                        				index = 0;
                        			}
                    			}
                    		}

                    		if(!found){
                    			setBlock(result, x, y, z, (short) org.bukkit.Material.COBBLESTONE.getId());//15
                    		}
                        }
                        else if(valz % size != 0 && (valx % size == 6 || valx % size == -6 || valx % size == (plotsize +9) || valx % size == -(plotsize +9))){
                    		List<org.bukkit.Material> materials = Arrays.asList(org.bukkit.Material.OBSIDIAN, org.bukkit.Material.COBBLESTONE);//0, 15

                    		int index = 0;
                    		boolean found = false;
                    		for(int i = 12; i <= plotsize +2; i += 6){
                    			if(- valz % size <= i && - valz % size >= -i){
                    				setBlock(result, x, y, z, (short) materials.get(index).getId());
                    				found = true;

                        			index++;
                        			if(index == 2){
                        				index = 0;
                        			}
                    			}
                    		}

                    		if(!found){
                    			setBlock(result, x, y, z, (short) org.bukkit.Material.COBBLESTONE.getId());//15
                    		}
                        }
                        else if(valz % size != 0 && (valx % size == 7 || valx % size == -7 || valx % size == (plotsize +8) || valx % size == -(plotsize +8))){
                        	if((valx % size == 7 && valz % size == 7) || (valx % size == 7 && valz % size == -7) || (valx % size == -7 && valz % size == 7) || ((valx % size == -7 && valz % size == -7)) || (valx % size == (plotsize +8) && valz % size == 7) || (valx % size == (plotsize +8) && valz % size == -7) || (valx % size == -(plotsize +8) && valz % size == 7) || ((valx % size == -(plotsize +8) && valz % size == -7))){
                				setBlock(result, x, (y +1), z, (short) org.bukkit.Material.TORCH.getId());//5
                				setBlock(result, x, y, z, (short) org.bukkit.Material.STONE.getId());
                            }
                        	else{
                        		List<org.bukkit.Material> materials = Arrays.asList(org.bukkit.Material.STEP, org.bukkit.Material.STEP);//0, 3

                        		int index = 0;
                        		boolean found = false;
                        		for(int i = 12; i <= plotsize +2; i += 6){
                        			if(- valz % size <= i && - valz % size >= -i){
                        				if(index == 1 && (- valz % size == 12 || - valz % size == -12 || - valz % size == 19 || - valz % size == -19 || - valz % size == 24 || - valz % size == -24 || - valz % size == 31 || - valz % size == -31 || - valz % size == 36 || - valz % size == -36 || - valz % size == 43 || - valz % size == -43 || - valz % size == 48 || - valz % size == -48 || - valz % size == 55 || - valz % size == -55 || - valz % size == 60 || - valz % size == -60 || - valz % size == 67 || - valz % size == -67 || - valz % size == 72 || - valz % size == -72 || - valz % size == 79 || - valz % size == -79 || - valz % size == 84 || - valz % size == -84)){
                            				setBlock(result, x, (y +1), z, (short) org.bukkit.Material.TORCH.getId());//5
                        				}
                        				else{
                                            // GATE
                                            if(!(- valz % size == 20 || - valz % size == -20 || - valz % size == 21 || - valz % size == -21 || - valz % size == 22 || - valz % size == -22 || - valz % size == 23 || - valz % size == -23)){
                                                setBlock(result, x, (y + 1), z, (short) materials.get(index).getId());
                                            }
                        				}

                        				setBlock(result, x, y, z, (short) org.bukkit.Material.STONE.getId());
                        				found = true;

	                        			index++;
	                        			if(index == 2){
	                        				index = 0;
	                        			}
                        			}
                        		}

                        		if(!found){
                        			if(- valz % size != (plotsize + 3) && - valz % size != -(plotsize + 3) && - valz % size != (plotsize +8) && - valz % size != -(plotsize +8)){
                        				setBlock(result, x, (y +1), z, (short) org.bukkit.Material.STEP.getId());//3
                        				setBlock(result, x, y, z, (short) org.bukkit.Material.STONE.getId());
                        			}
                        			else{
                                		setBlock(result, x, (y +1), z, (short) org.bukkit.Material.TORCH.getId());//5
                        				setBlock(result, x, y, z, (short) org.bukkit.Material.STONE.getId());
                        			}
                        		}
                        	}
                        }
                        else if(valx % size != 0 && (valz % size == 7 || valz % size == -7 || valz % size == (plotsize +8) || valz % size == -(plotsize +8))){
                        	if((valz % size == 7 && valx % size == 7) || (valz % size == 7 && valx % size == -7) || (valz % size == -7 && valx % size == 7) || ((valz % size == -7 && valx % size == -7)) || (valz % size == (plotsize +8) && valx % size == 7) || (valz % size == (plotsize +8) && valx % size == -7) || (valz % size == -(plotsize +8) && valx % size == 7) || ((valz % size == -(plotsize +8) && valx % size == -7))){
                				setBlock(result, x, (y +1), z, (short) org.bukkit.Material.TORCH.getId());//5
                				setBlock(result, x, y, z, (short) org.bukkit.Material.STONE.getId());
                            }
                        	else{
                        		List<org.bukkit.Material> materials = Arrays.asList(org.bukkit.Material.STEP, org.bukkit.Material.STEP);//0, 3

                        		int index = 0;
                        		boolean found = false;
                        		for(int i = 12; i <= plotsize +2; i += 6){
                        			if(- valx % size <= i && - valx % size >= -i){
                        				if(index == 1 && (- valx % size == 12 || - valx % size == -12 || - valx % size == 19 || - valx % size == -19 || - valx % size == 24 || - valx % size == -24 || - valx % size == 31 || - valx % size == -31 || - valx % size == 36 || - valx % size == -36 || - valx % size == 43 || - valx % size == -43 || - valx % size == 48 || - valx % size == -48 || - valx % size == 55 || - valx % size == -55 || - valx % size == 60 || - valx % size == -60 || - valx % size == 67 || - valx % size == -67 || - valx % size == 72 || - valx % size == -72 || - valx % size == 79 || - valx % size == -79 || - valx % size == 84 || - valx % size == -84)){
                            				setBlock(result, x, (y +1), z, (short) org.bukkit.Material.TORCH.getId());//5
                        				}
                        				else{
                        					setBlock(result, x, (y +1), z, (short) materials.get(index).getId());
                        				}

                        				setBlock(result, x, y, z, (short) org.bukkit.Material.STONE.getId());
                        				found = true;

	                        			index++;
	                        			if(index == 2){
	                        				index = 0;
	                        			}
                        			}
                        		}

                        		if(!found){
                        			if(- valx % size != (plotsize + 3) && - valx % size != -(plotsize + 3) && - valx % size != (plotsize +8) && - valx % size != -(plotsize +8)){
                        				setBlock(result, x, (y +1), z, (short) org.bukkit.Material.STEP.getId());//3
                        				setBlock(result, x, y, z, (short) org.bukkit.Material.STONE.getId());
                        			}
                        			else{
                                		setBlock(result, x, (y +1), z, (short) Material.TORCH.getId());//5
                        				setBlock(result, x, y, z, (short) org.bukkit.Material.STONE.getId());
                        			}
                        		}
                        	}
                        }
                        else if(valz % size == 0 || valx % size == 0 || valz % size == (plotsize + 15) || valx % size == (plotsize + 15) || valz % size == -(plotsize + 15) || valx % size == -(plotsize + 15)){
                        	if(valz % size == 0 && valx % size == 0 || valz % size == (plotsize + 15) && valx % size == (plotsize + 15) || valz % size == -(plotsize + 15) && valx % size == -(plotsize + 15)){
                        		setBlock(result, x, y, z, (short) org.bukkit.Material.STEP.getId());//12
                        	}
                        	else if(valz % size - valx % size <= 1 && valz % size - valx % size >= -1 || valz % size - valx % size <= (plotsize + 16) && valz % size - valx % size >= (plotsize + 14) || valz % size - valx % size <= -(plotsize + 14) && valz % size - valx % size >= -(plotsize + 16)){
                        		setBlock(result, x, y, z, (short) org.bukkit.Material.COBBLESTONE.getId());//15
                        	}
                        	else{
                        		List<org.bukkit.Material> materials = Arrays.asList(org.bukkit.Material.STEP, Material.OBSIDIAN);//12, 0

                        		int index = 0;
                        		boolean found = false;
                        		for(int i = 12; i <= plotsize +2; i += 6){
                        			if(valz % size - valx % size <= i && valz % size - valx % size >= -i){
                        				setBlock(result, x, y, z, (short) materials.get(index).getId());
                        				found = true;

	                        			index++;
	                        			if(index == 2){
	                        				index = 0;
	                        			}
                        			}
                        		}

                        		if(!found){
                        			if(- valx % size <= (plotsize + 13) && - valx % size >= -(plotsize + 13)){
                        				setBlock(result, x, y, z, (short) org.bukkit.Material.OBSIDIAN.getId());
                        			}
                        			else{
                                		setBlock(result, x, y, z, (short) org.bukkit.Material.COBBLESTONE.getId());//15
                        			}
                        		}
                        	}
                        }
                        else{
                            if((valz - n2 + mod1) % size == 0 || (valz + n2 + mod2) % size == 0){
                            	setBlock(result, x, y, z, (short) org.bukkit.Material.STONE.getId());
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
                                    setBlock(result, x, y, z, (short) org.bukkit.Material.STONE.getId());
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
                                        setBlock(result, x, y, z, (short) org.bukkit.Material.STONE.getId());
                                    }
                                    else{
                                        setBlock(result, x, y, z, (short) org.bukkit.Material.STONE.getId());
                                    }
                                }
                            }
                        }
                    }
                    else if(y > roadheight && y < roadheight + 11){
                        if(valx % size != 0 && (valz % size == 1 || valz % size == -1 || valz % size == (plotsize + 14) || valz % size == -(plotsize + 14))){

                        }
                        else if(valz % size != 0 && (valx % size == 1 || valx % size == -1 || valx % size == (plotsize + 14) || valx % size == -(plotsize + 14))){

                        }
                        else if(valx % size != 0 && (valz % size == 2 || valz % size == -2 || valz % size == (plotsize + 13) || valz % size == -(plotsize + 13))){

                        }
                        else if(valz % size != 0 && (valx % size == 2 || valx % size == -2 || valx % size == (plotsize + 13) || valx % size == -(plotsize + 13))){

                        }
                        else if(valx % size != 0 && (valz % size == 3 || valz % size == -3 || valz % size == (plotsize + 12) || valz % size == -(plotsize + 12))){

                        }
                        else if(valz % size != 0 && (valx % size == 3 || valx % size == -3 || valx % size == (plotsize + 12) || valx % size == -(plotsize + 12))){

                        }
                        else if(valx % size != 0 && (valz % size == 4 || valz % size == -4 || valz % size == (plotsize + 11) || valz % size == -(plotsize + 11))){

                        }
                        else if(valz % size != 0 && (valx % size == 4 || valx % size == -4 || valx % size == (plotsize + 11) || valx % size == -(plotsize + 11))){

                        }
                        else if(valx % size != 0 && (valz % size == 5 || valz % size == -5 || valz % size == (plotsize +10) || valz % size == -(plotsize +10))){

                        }
                        else if(valz % size != 0 && (valx % size == 5 || valx % size == -5 || valx % size == (plotsize +10) || valx % size == -(plotsize +10))){

                        }
                        else if(valx % size != 0 && (valz % size == 6 || valz % size == -6 || valz % size == (plotsize +9) || valz % size == -(plotsize +9))){

                        }
                        else if(valz % size != 0 && (valx % size == 6 || valx % size == -6 || valx % size == (plotsize +9) || valx % size == -(plotsize +9))){

                        }
                        else if(valz % size != 0 && (valx % size == 7 || valx % size == -7 || valx % size == (plotsize +8) || valx % size == -(plotsize +8))){
                            if((valx % size == 7 && valz % size == 7) || (valx % size == 7 && valz % size == -7) || (valx % size == -7 && valz % size == 7) || ((valx % size == -7 && valz % size == -7)) || (valx % size == (plotsize +8) && valz % size == 7) || (valx % size == (plotsize +8) && valz % size == -7) || (valx % size == -(plotsize +8) && valz % size == 7) || ((valx % size == -(plotsize +8) && valz % size == -7))){
                                setBlock(result, x, (y +1), z, (short) org.bukkit.Material.STEP.getId());//5
                                //setBlock(result, x, y, z, (short) org.bukkit.Material.STONE.getId());
                            }
                            else{
                                List<org.bukkit.Material> materials = Arrays.asList(org.bukkit.Material.STEP, org.bukkit.Material.STEP);//0, 3

                                int index = 0;
                                boolean found = false;
                                for(int i = 12; i <= plotsize +2; i += 6){
                                    if(- valz % size <= i && - valz % size >= -i){
                                        if(index == 1 && (- valz % size == 12 || - valz % size == -12 || - valz % size == 19 || - valz % size == -19 || - valz % size == 24 || - valz % size == -24 || - valz % size == 31 || - valz % size == -31 || - valz % size == 36 || - valz % size == -36 || - valz % size == 43 || - valz % size == -43 || - valz % size == 48 || - valz % size == -48 || - valz % size == 55 || - valz % size == -55 || - valz % size == 60 || - valz % size == -60 || - valz % size == 67 || - valz % size == -67 || - valz % size == 72 || - valz % size == -72 || - valz % size == 79 || - valz % size == -79 || - valz % size == 84 || - valz % size == -84)){
                                            setBlock(result, x, (y +1), z, (short) Material.STEP.getId());//5
                                        }
                                        else{
                                            //GATE
                                            if(y > roadheight && y < roadheight + 5 && (- valz % size == 20 || - valz % size == -20 || - valz % size == 21 || - valz % size == -21 || - valz % size == 22 || - valz % size == -22 || - valz % size == 23 || - valz % size == -23)){
                                                if(y == roadheight + 4)
                                                    setBlock(result, x, (y + 1), z, (short) Material.STEP.getId());
                                            }
                                            else{
                                                setBlock(result, x, (y + 1), z, (short) materials.get(index).getId());
                                            }
                                        }

                                        //setBlock(result, x, y, z, (short) org.bukkit.Material.STONE.getId());
                                        found = true;

                                        index++;
                                        if(index == 2){
                                            index = 0;
                                        }
                                    }
                                }

                                if(!found){
                                    if(- valz % size != (plotsize + 3) && - valz % size != -(plotsize + 3) && - valz % size != (plotsize +8) && - valz % size != -(plotsize +8)){
                                        setBlock(result, x, (y +1), z, (short) org.bukkit.Material.STEP.getId());//3
                                        //setBlock(result, x, y, z, (short) org.bukkit.Material.STONE.getId());
                                    }
                                    else{
                                        setBlock(result, x, (y +1), z, (short) org.bukkit.Material.STEP.getId());//5
                                        //setBlock(result, x, y, z, (short) org.bukkit.Material.STONE.getId());
                                    }
                                }
                            }
                        }
                        else if(valx % size != 0 && (valz % size == 7 || valz % size == -7 || valz % size == (plotsize +8) || valz % size == -(plotsize +8))){
                            if((valz % size == 7 && valx % size == 7) || (valz % size == 7 && valx % size == -7) || (valz % size == -7 && valx % size == 7) || ((valz % size == -7 && valx % size == -7)) || (valz % size == (plotsize +8) && valx % size == 7) || (valz % size == (plotsize +8) && valx % size == -7) || (valz % size == -(plotsize +8) && valx % size == 7) || ((valz % size == -(plotsize +8) && valx % size == -7))){
                                setBlock(result, x, (y +1), z, (short) org.bukkit.Material.TORCH.getId());//5
                                //setBlock(result, x, y, z, (short) org.bukkit.Material.STONE.getId());
                            }
                            else{
                                List<org.bukkit.Material> materials = Arrays.asList(org.bukkit.Material.STEP, org.bukkit.Material.STEP);//0, 3

                                int index = 0;
                                boolean found = false;
                                for(int i = 12; i <= plotsize +2; i += 6){
                                    if(- valx % size <= i && - valx % size >= -i){
                                        if(index == 1 && (- valx % size == 12 || - valx % size == -12 || - valx % size == 19 || - valx % size == -19 || - valx % size == 24 || - valx % size == -24 || - valx % size == 31 || - valx % size == -31 || - valx % size == 36 || - valx % size == -36 || - valx % size == 43 || - valx % size == -43 || - valx % size == 48 || - valx % size == -48 || - valx % size == 55 || - valx % size == -55 || - valx % size == 60 || - valx % size == -60 || - valx % size == 67 || - valx % size == -67 || - valx % size == 72 || - valx % size == -72 || - valx % size == 79 || - valx % size == -79 || - valx % size == 84 || - valx % size == -84)){
                                            setBlock(result, x, (y +1), z, (short) org.bukkit.Material.STEP.getId());//5
                                        }
                                        else{
                                            setBlock(result, x, (y +1), z, (short) materials.get(index).getId());
                                        }

                                        //setBlock(result, x, y, z, (short) org.bukkit.Material.STONE.getId());
                                        found = true;

                                        index++;
                                        if(index == 2){
                                            index = 0;
                                        }
                                    }
                                }

                                if(!found){
                                    if(- valx % size != (plotsize + 3) && - valx % size != -(plotsize + 3) && - valx % size != (plotsize +8) && - valx % size != -(plotsize +8)){
                                        setBlock(result, x, (y +1), z, (short) org.bukkit.Material.STEP.getId());//3
                                        //setBlock(result, x, y, z, (short) org.bukkit.Material.STONE.getId());
                                    }
                                    else{
                                        setBlock(result, x, (y +1), z, (short) Material.STEP.getId());//5
                                        //setBlock(result, x, y, z, (short) org.bukkit.Material.STONE.getId());
                                    }
                                }
                            }
                        }
                    }
                    else if(y > roadheight + 11){
                        setBlock(result, x, y, z, (short) Material.STONE.getId());
                    }
                    else{
                        setBlock(result, x, y, z, (short) org.bukkit.Material.STONE.getId());
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
        return Arrays.asList((BlockPopulator) new PlotPopulator());
    }

    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, 0, roadheight + 2, 0);
    }
}
