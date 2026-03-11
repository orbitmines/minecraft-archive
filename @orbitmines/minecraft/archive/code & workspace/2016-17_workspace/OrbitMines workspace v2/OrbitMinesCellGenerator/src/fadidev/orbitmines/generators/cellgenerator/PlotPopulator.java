package fadidev.orbitmines.generators.cellgenerator;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PlotPopulator extends BlockPopulator {
    private double plotsize;
    private double pathsize;

    private int roadheight;

    public PlotPopulator() {
        plotsize = 28;
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
                int height = roadheight + 2 + 11;
                int valz = (zz + z);

                for(int y = 0; y < height; y++){
                    if(y == 0){
                    	//Bedrock, Data: 0
                    }
                    else if(y == roadheight || y == roadheight + 11){
						byte slab = (byte) (y == roadheight ? 3 : 11);

						if(valx % size != 0 && (valz % size == 1 || valz % size == -1 || valz % size == (plotsize + 14) || valz % size == -(plotsize + 14))){
                        	if((valz % size == 1 && valx % size == 1) || (valz % size == 1 && valx % size == -1) || (valz % size == -1 && valx % size == 1) || ((valz % size == -1 && valx % size == -1)) || (valz % size == (plotsize + 14) && valx % size == 1) || (valz % size == (plotsize + 14) && valx % size == -1) || (valz % size == -(plotsize + 14) && valx % size == 1) || ((valz % size == -(plotsize + 14) && valx % size == -1))){
                        		//Hardened Clay, Data: 0
                            }
                        	else{
                        		List<Byte> data = Arrays.asList((byte) 0, slab);
                        		
                        		int index = 0;
                        		boolean found = false;
                        		for(int i = 12; i <= plotsize +2; i += 6){
                        			if(- valx % size <= i && - valx % size >= -i){
                        				setData(world, valx, y, valz, data.get(index));
                        				found = true;
	                        			
	                        			index++;
	                        			if(index == 2){
	                        				index = 0;
	                        			}
                        			}
                        		}
                        		
                        		if(!found){
                        			if(- valx % size <= (plotsize + 13) && - valx % size >= -(plotsize + 13)){
                        				setData(world, valx, y, valz, slab);
                            		}
                        			else{
                        				//Hardened Clay, Data: 0
                            		}
                        		}
                        	}
                        }
                        else if(valz % size != 0 && (valx % size == 1 || valx % size == -1 || valx % size == (plotsize + 14) || valx % size == -(plotsize + 14))){
                        	if((valx % size == 1 && valz % size == 1) || (valx % size == 1 && valz % size == -1) || (valx % size == -1 && valz % size == 1) || ((valx % size == -1 && valz % size == -1)) || (valx % size == (plotsize + 14) && valz % size == 1) || (valx % size == (plotsize + 14) && valz % size == -1) || (valx % size == -(plotsize + 14) && valz % size == 1) || ((valx % size == -(plotsize + 14) && valz % size == -1))){
                        		//Hardened Clay, Data: 0
                        	}
                        	else{
                        		List<Byte> data = Arrays.asList((byte) 0, slab);
                        		
                        		int index = 0;
                        		boolean found = false;
                        		for(int i = 12; i <= plotsize +2; i += 6){
                        			if(- valz % size <= i && - valz % size >= -i){
                        				setData(world, valx, y, valz, data.get(index));
                        				found = true;
	                        			
	                        			index++;
	                        			if(index == 2){
	                        				index = 0;
	                        			}
                        			}
                        		}
                        		
                        		if(!found){
                        			if(- valz % size <= (plotsize + 13) && - valz % size >= -(plotsize + 13)){
                        				setData(world, valx, y, valz, slab);
                            		}
                        			else{
                        				//Hardened Clay, Data: 0
                            		}
                        		}
                        	}
                        }
                        else if(valx % size != 0 && (valz % size == 2 || valz % size == -2 || valz % size == (plotsize + 13) || valz % size == -(plotsize + 13))){
                        	if((valz % size == 2 && valx % size == 2) || (valz % size == 2 && valx % size == -2) || (valz % size == -2 && valx % size == 2) || ((valz % size == -2 && valx % size == -2)) || (valz % size == (plotsize + 13) && valx % size == 2) || (valz % size == (plotsize + 13) && valx % size == -2) || (valz % size == -(plotsize + 13) && valx % size == 2) || ((valz % size == -(plotsize + 13) && valx % size == -2))){
                				setData(world, valx, y, valz, slab);
                            }
                        	else{
                        		List<Byte> data = Arrays.asList((byte) 0, (byte) 0);

                        		int index = 0;
                        		boolean found = false;
                        		for(int i = 12; i <= plotsize +2; i += 6){
                        			if(- valx % size <= i && - valx % size >= -i){
                        				if(index == 1){
                            				setData(world, valx, y, valz, data.get(index));
                        				}
                        				found = true;

	                        			index++;
	                        			if(index == 2){
	                        				index = 0;
	                        			}
                        			}
                        		}

                        		if(!found){
                        			if(- valx % size <= (plotsize + 12) && - valx % size >= -(plotsize + 12)){
                        				setData(world, valx, y, valz, (byte) 0);
                            		}
                        			else{
                        				setData(world, valx, y, valz, slab);
                            		}
                        		}
                        	}
                        }
                        else if(valz % size != 0 && (valx % size == 2 || valx % size == -2 || valx % size == (plotsize + 13) || valx % size == -(plotsize + 13))){
                        	if((valx % size == 2 && valz % size == 2) || (valx % size == 2 && valz % size == -2) || (valx % size == -2 && valz % size == 2) || ((valx % size == -2 && valz % size == -2)) || (valx % size == (plotsize + 13) && valz % size == 2) || (valx % size == (plotsize + 13) && valz % size == -2) || (valx % size == -(plotsize + 13) && valz % size == 2) || ((valx % size == -(plotsize + 13) && valz % size == -2))){
                        		setData(world, valx, y, valz, slab);
                        	}
                        	else{
                        		List<Byte> data = Arrays.asList((byte) 0, (byte) 0);

                        		int index = 0;
                        		boolean found = false;
                        		for(int i = 12; i <= plotsize +2; i += 6){
                        			if(- valz % size <= i && - valz % size >= -i){
                        				if(index == 1){
                            				setData(world, valx, y, valz, data.get(index));
                        				}
                        				found = true;

	                        			index++;
	                        			if(index == 2){
	                        				index = 0;
	                        			}
                        			}
                        		}

                        		if(!found){
                        			if(- valz % size <= (plotsize + 12) && - valz % size >= -(plotsize + 12)){
                        				setData(world, valx, y, valz, (byte) 0);
                            		}
                        			else{
                        				setData(world, valx, y, valz, slab);
                            		}
                        		}
                        	}
                        }
                        else if(valx % size != 0 && (valz % size == 3 || valz % size == -3 || valz % size == (plotsize + 12) || valz % size == -(plotsize + 12))){
                        	if((valz % size == 3 && valx % size == 3) || (valz % size == 3 && valx % size == -3) || (valz % size == -3 && valx % size == 3) || ((valz % size == -3 && valx % size == -3)) || (valz % size == (plotsize + 12) && valx % size == 3) || (valz % size == (plotsize + 12) && valx % size == -3) || (valz % size == -(plotsize + 12) && valx % size == 3) || ((valz % size == -(plotsize + 12) && valx % size == -3))){
                        		setData(world, valx, y, valz, (byte) 0);
                            }
                        	else{
                        		List<Byte> data = Arrays.asList((byte) 0, slab);

                        		int index = 0;
                        		boolean found = false;
                        		for(int i = 12; i <= plotsize +2; i += 6){
                        			if(- valx % size <= i && - valx % size >= -i){
                        				setData(world, valx, y, valz, data.get(index));
                        				found = true;

	                        			index++;
	                        			if(index == 2){
	                        				index = 0;
	                        			}
                        			}
                        		}

                        		if(!found){
                        			if(- valx % size <= (plotsize + 11) && - valx % size >= -(plotsize + 11)){
                        				setData(world, valx, y, valz, slab);
                            		}
                        			else{
                        				setData(world, valx, y, valz, (byte) 0);
                            		}
                        		}
                        	}
                        }
                        else if(valz % size != 0 && (valx % size == 3 || valx % size == -3 || valx % size == (plotsize + 12) || valx % size == -(plotsize + 12))){
                        	if((valx % size == 3 && valz % size == 3) || (valx % size == 3 && valz % size == -3) || (valx % size == -3 && valz % size == 3) || ((valx % size == -3 && valz % size == -3)) || (valx % size == (plotsize + 12) && valz % size == 3) || (valx % size == (plotsize + 12) && valz % size == -3) || (valx % size == -(plotsize + 12) && valz % size == 3) || ((valx % size == -(plotsize + 12) && valz % size == -3))){
                        		setData(world, valx, y, valz, (byte) 0);
                        	}
                        	else{
                        		List<Byte> data = Arrays.asList((byte) 0, slab);

                        		int index = 0;
                        		boolean found = false;
                        		for(int i = 12; i <= plotsize +2; i += 6){
                        			if(- valz % size <= i && - valz % size >= -i){
                        				setData(world, valx, y, valz, data.get(index));
                        				found = true;

	                        			index++;
	                        			if(index == 2){
	                        				index = 0;
	                        			}
                        			}
                        		}

                        		if(!found){
                        			if(- valz % size <= (plotsize + 11) && - valz % size >= -(plotsize + 11)){
                        				setData(world, valx, y, valz, slab);
                            		}
                        			else{
                        				setData(world, valx, y, valz, (byte) 0);
                            		}
                        		}
                        	}
                        }
                        else if(valx % size != 0 && (valz % size == 4 || valz % size == -4 || valz % size == (plotsize + 11) || valz % size == -(plotsize + 11))){
                        	if((valz % size == 4 && valx % size == 4) || (valz % size == 4 && valx % size == -4) || (valz % size == -4 && valx % size == 4) || ((valz % size == -4 && valx % size == -4)) || (valz % size == (plotsize + 11) && valx % size == 4) || (valz % size == (plotsize + 11) && valx % size == -4) || (valz % size == -(plotsize + 11) && valx % size == 4) || ((valz % size == -(plotsize + 11) && valx % size == -4))){
                        		setData(world, valx, y, valz, slab);
                            }
                        	else{
                        		List<Byte> data = Arrays.asList( slab, (byte) 0);

                        		int index = 0;
                        		boolean found = false;
                        		for(int i = 12; i <= plotsize +2; i += 6){
                        			if(- valx % size <= i && - valx % size >= -i){
                        				if(index == 0){
                            				setData(world, valx, y, valz, data.get(index));
                        				}
                        				found = true;

	                        			index++;
	                        			if(index == 2){
	                        				index = 0;
	                        			}
                        			}
                        		}

                        		if(!found){
                        			if(- valx % size <= (plotsize +10) && - valx % size >= -(plotsize +10)){
                        				//Hardened Clay, Data: 0
                            		}
                        			else{
                        				setData(world, valx, y, valz, slab);
                            		}
                        		}
                        	}
                        }
                        else if(valz % size != 0 && (valx % size == 4 || valx % size == -4 || valx % size == (plotsize + 11) || valx % size == -(plotsize + 11))){
                        	if((valx % size == 4 && valz % size == 4) || (valx % size == 4 && valz % size == -4) || (valx % size == -4 && valz % size == 4) || ((valx % size == -4 && valz % size == -4)) || (valx % size == (plotsize + 11) && valz % size == 4) || (valx % size == (plotsize + 11) && valz % size == -4) || (valx % size == -(plotsize + 11) && valz % size == 4) || ((valx % size == -(plotsize + 11) && valz % size == -4))){
                        		setData(world, valx, y, valz, slab);
                        	}
                        	else{
                        		List<Byte> data = Arrays.asList( slab, (byte) 0);

                        		int index = 0;
                        		boolean found = false;
                        		for(int i = 12; i <= plotsize +2; i += 6){
                        			if(- valz % size <= i && - valz % size >= -i){
                        				if(index == 0){
                            				setData(world, valx, y, valz, data.get(index));
                        				}
                        				found = true;

	                        			index++;
	                        			if(index == 2){
	                        				index = 0;
	                        			}
                        			}
                        		}

                        		if(!found){
                        			if(- valz % size <= (plotsize +10) && - valz % size >= -(plotsize +10)){
                        				//Hardened Clay, Data: 0
                            		}
                        			else{
                        				setData(world, valx, y, valz, slab);
                            		}
                        		}
                        	}
                        }
                        else if(valx % size != 0 && (valz % size == 5 || valz % size == -5 || valz % size == (plotsize +10) || valz % size == -(plotsize +10))){
                        	if((valz % size == 5 && valx % size == 5) || (valz % size == 5 && valx % size == -5) || (valz % size == -5 && valx % size == 5) || ((valz % size == -5 && valx % size == -5)) || (valz % size == (plotsize +10) && valx % size == 5) || (valz % size == (plotsize +10) && valx % size == -5) || (valz % size == -(plotsize +10) && valx % size == 5) || ((valz % size == -(plotsize +10) && valx % size == -5))){
                        		//Hardened Clay, Data: 0
                            }
                        	else{
                        		List<Byte> data = Arrays.asList((byte) 0, slab);

                        		int index = 0;
                        		boolean found = false;
                        		for(int i = 12; i <= plotsize +2; i += 6){
                        			if(- valx % size <= i && - valx % size >= -i){
                        				setData(world, valx, y, valz, data.get(index));
                        				found = true;

	                        			index++;
	                        			if(index == 2){
	                        				index = 0;
	                        			}
                        			}
                        		}

                        		if(!found){
                        			if(- valx % size <= (plotsize +9) && - valx % size >= -(plotsize +9)){
                        				setData(world, valx, y, valz,  slab);
                            		}
                        			else{
                        				//Hardened Clay, Data: 0
                            		}
                        		}
                        	}
                        }
                        else if(valz % size != 0 && (valx % size == 5 || valx % size == -5 || valx % size == (plotsize +10) || valx % size == -(plotsize +10))){
                        	if((valx % size == 5 && valz % size == 5) || (valx % size == 5 && valz % size == -5) || (valx % size == -5 && valz % size == 5) || ((valx % size == -5 && valz % size == -5)) || (valx % size == (plotsize +10) && valz % size == 5) || (valx % size == (plotsize +10) && valz % size == -5) || (valx % size == -(plotsize +10) && valz % size == 5) || ((valx % size == -(plotsize +10) && valz % size == -5))){
                        		//Hardened Clay, Data: 0
                        	}
                        	else{
                        		List<Byte> data = Arrays.asList((byte) 0, slab);

                        		int index = 0;
                        		boolean found = false;
                        		for(int i = 12; i <= plotsize +2; i += 6){
                        			if(- valz % size <= i && - valz % size >= -i){
                        				setData(world, valx, y, valz, data.get(index));
                        				found = true;

	                        			index++;
	                        			if(index == 2){
	                        				index = 0;
	                        			}
                        			}
                        		}

                        		if(!found){
                        			if(- valz % size <= (plotsize +9) && - valz % size >= -(plotsize +9)){
                        				setData(world, valx, y, valz, slab);
                            		}
                        			else{
                        				//Hardened Clay, Data: 0
                            		}
                        		}
                        	}
                        }
                        else if(valx % size != 0 && (valz % size == 6 || valz % size == -6 || valz % size == (plotsize +9) || valz % size == -(plotsize +9))){
                    		List<Byte> data = Arrays.asList((byte) 0, (byte) 0);

                    		int index = 0;
                    		boolean found = false;
                    		for(int i = 12; i <= plotsize +2; i += 6){
                    			if(- valx % size <= i && - valx % size >= -i){
                    				if(index == 1){
                        				setData(world, valx, y, valz, data.get(index));
                    				}
                    				found = true;

                        			index++;
                        			if(index == 2){
                        				index = 0;
                        			}
                    			}
                    		}

                    		if(!found){
                    			setData(world, valx, y, valz, (byte) 0);
                    		}
                        }
                        else if(valz % size != 0 && (valx % size == 6 || valx % size == -6 || valx % size == (plotsize +9) || valx % size == -(plotsize +9))){
                    		List<Byte> data = Arrays.asList((byte) 0, (byte) 0);
                    		
                    		int index = 0;
                    		boolean found = false;
                    		for(int i = 12; i <= plotsize +2; i += 6){
                    			if(- valz % size <= i && - valz % size >= -i){
                    				if(index == 1){
                        				setData(world, valx, y, valz, data.get(index));
                    				}
                    				found = true;
                        			
                        			index++;
                        			if(index == 2){
                        				index = 0;
                        			}
                    			}
                    		}
                    		
                    		if(!found){
                    			setData(world, valx, y, valz, (byte) 0);
                    		}
                        }
                        else if(valz % size != 0 && (valx % size == 7 || valx % size == -7 || valx % size == (plotsize +8) || valx % size == -(plotsize +8))){
                        	if((valx % size == 7 && valz % size == 7) || (valx % size == 7 && valz % size == -7) || (valx % size == -7 && valz % size == 7) || ((valx % size == -7 && valz % size == -7)) || (valx % size == (plotsize +8) && valz % size == 7) || (valx % size == (plotsize +8) && valz % size == -7) || (valx % size == -(plotsize +8) && valz % size == 7) || ((valx % size == -(plotsize +8) && valz % size == -7))){
                        		setData(world, valx, (y +1), valz, (byte) 5);
                            }
                        	else{
                        		List<Byte> data = Arrays.asList((byte) 11, (byte) 3);

                        		int index = 0;
                        		boolean found = false;
                        		for(int i = 12; i <= plotsize +2; i += 6){
                        			if(- valz % size <= i && - valz % size >= -i){
                        				if(index == 1 && (- valz % size == 12 || - valz % size == -12 || - valz % size == 19 || - valz % size == -19 || - valz % size == 24 || - valz % size == -24 || - valz % size == 31 || - valz % size == -31 || - valz % size == 36 || - valz % size == -36 || - valz % size == 43 || - valz % size == -43 || - valz % size == 48 || - valz % size == -48 || - valz % size == 55 || - valz % size == -55 || - valz % size == 60 || - valz % size == -60 || - valz % size == 67 || - valz % size == -67 || - valz % size == 72 || - valz % size == -72 || - valz % size == 79 || - valz % size == -79 || - valz % size == 84 || - valz % size == -84)){
                        					setData(world, valx, (y +1), valz, (byte) 5);
                        				}
                        				else{
                        					setData(world, valx, (y +1), valz, data.get(index));
                        				}

                        				found = true;

	                        			index++;
	                        			if(index == 2){
	                        				index = 0;
	                        			}
                        			}
                        		}

                        		if(!found){
                        			if(- valz % size != (plotsize + 3) && - valz % size != -(plotsize + 3) && - valz % size != (plotsize +8) && - valz % size != -(plotsize +8)){
                        				setData(world, valx, (y +1), valz, (byte) 3);
                        			}
                        			else{
                        				setData(world, valx, (y +1), valz, (byte) 5);
                        			}
                        		}
                        	}
                        }
                        else if(valx % size != 0 && (valz % size == 7 || valz % size == -7 || valz % size == (plotsize +8) || valz % size == -(plotsize +8))){
                        	if((valz % size == 7 && valx % size == 7) || (valz % size == 7 && valx % size == -7) || (valz % size == -7 && valx % size == 7) || ((valz % size == -7 && valx % size == -7)) || (valz % size == (plotsize +8) && valx % size == 7) || (valz % size == (plotsize +8) && valx % size == -7) || (valz % size == -(plotsize +8) && valx % size == 7) || ((valz % size == -(plotsize +8) && valx % size == -7))){
                        		setData(world, valx, (y +1), valz, (byte) 5);
                            }
                        	else{
                        		List<Byte> data = Arrays.asList((byte) 11, (byte) 3);

                        		int index = 0;
                        		boolean found = false;
                        		for(int i = 12; i <= plotsize +2; i += 6){
                        			if(- valx % size <= i && - valx % size >= -i){
                        				if(index == 1 && (- valx % size == 12 || - valx % size == -12 || - valx % size == 19 || - valx % size == -19 || - valx % size == 24 || - valx % size == -24 || - valx % size == 31 || - valx % size == -31 || - valx % size == 36 || - valx % size == -36 || - valx % size == 43 || - valx % size == -43 || - valx % size == 48 || - valx % size == -48 || - valx % size == 55 || - valx % size == -55 || - valx % size == 60 || - valx % size == -60 || - valx % size == 67 || - valx % size == -67 || - valx % size == 72 || - valx % size == -72 || - valx % size == 79 || - valx % size == -79 || - valx % size == 84 || - valx % size == -84)){
                        					setData(world, valx, (y +1), valz, (byte) 5);
                        				}
                        				else{
                        					setData(world, valx, (y +1), valz, data.get(index));
                        				}

                        				found = true;

	                        			index++;
	                        			if(index == 2){
	                        				index = 0;
	                        			}
                        			}
                        		}

                        		if(!found){
                        			if(- valx % size != (plotsize + 3) && - valx % size != -(plotsize + 3) && - valx % size != (plotsize +8) && - valx % size != -(plotsize +8)){
                        				setData(world, valx, (y +1), valz, (byte) 3);
                        			}
                        			else{
                        				setData(world, valx, (y +1), valz, (byte) 5);
                        			}
                        		}
                        	}
                        }
                        else if(valz % size == 0 || valx % size == 0 || valz % size == (plotsize + 15) || valx % size == (plotsize + 15) || valz % size == -(plotsize + 15) || valx % size == -(plotsize + 15)){
                        	if(valz % size == 0 && valx % size == 0 || valz % size == (plotsize + 15) && valx % size == (plotsize + 15) || valz % size == -(plotsize + 15) && valx % size == -(plotsize + 15)){
                        		setData(world, valx, y, valz,(byte) 3);
                        	}
                        	else if(valz % size - valx % size <= 1 && valz % size - valx % size >= -1 || valz % size - valx % size <= (plotsize + 16) && valz % size - valx % size >= (plotsize + 14) || valz % size - valx % size <= -(plotsize + 14) && valz % size - valx % size >= -(plotsize + 16)){
                        		setData(world, valx, y, valz, (byte) 0);
                        	}
                        	else{
                        		List<Byte> data = Arrays.asList(slab, (byte) 0);
                        		
                        		int index = 0;
                        		boolean found = false;
                        		for(int i = 12; i <= plotsize +2; i += 6){
                        			if(valz % size - valx % size <= i && valz % size - valx % size >= -i){
                        				if(index == 0){
                        					setData(world, valx, y, valz, data.get(index));
                        				}
                        				found = true;
	                        			
	                        			index++;
	                        			if(index == 2){
	                        				index = 0;
	                        			}
                        			}
                        		}
                        		
                        		if(!found){
                        			if(- valx % size <= (plotsize + 13) && - valx % size >= -(plotsize + 13)){
                        				//Hardened Clay, Data: 0
                        			}
                        			else{
                        				setData(world, valx, y, valz, (byte) 0);
                        			}
                        		}
                        	}
                        }
                    }
                    else if(y > roadheight && y < roadheight + 10){
                        if(valz % size != 0 && (valx % size == 7 || valx % size == -7 || valx % size == (plotsize +8) || valx % size == -(plotsize +8))){
                            if((valx % size == 7 && valz % size == 7) || (valx % size == 7 && valz % size == -7) || (valx % size == -7 && valz % size == 7) || ((valx % size == -7 && valz % size == -7)) || (valx % size == (plotsize +8) && valz % size == 7) || (valx % size == (plotsize +8) && valz % size == -7) || (valx % size == -(plotsize +8) && valz % size == 7) || ((valx % size == -(plotsize +8) && valz % size == -7))){
                                setData(world, valx, (y +1), valz, (byte) 5);
                            }
                            else{
                                List<Byte> data = Arrays.asList((byte) 11, (byte) 3);

                                int index = 0;
                                boolean found = false;
                                for(int i = 12; i <= plotsize +2; i += 6){
                                    if(- valz % size <= i && - valz % size >= -i){
                                        if(index == 1 && (- valz % size == 12 || - valz % size == -12 || - valz % size == 19 || - valz % size == -19 || - valz % size == 24 || - valz % size == -24 || - valz % size == 31 || - valz % size == -31 || - valz % size == 36 || - valz % size == -36 || - valz % size == 43 || - valz % size == -43 || - valz % size == 48 || - valz % size == -48 || - valz % size == 55 || - valz % size == -55 || - valz % size == 60 || - valz % size == -60 || - valz % size == 67 || - valz % size == -67 || - valz % size == 72 || - valz % size == -72 || - valz % size == 79 || - valz % size == -79 || - valz % size == 84 || - valz % size == -84)){
                                            setData(world, valx, (y +1), valz, (byte) 5);
                                        }
                                        else{
                                            if(y > roadheight && y < roadheight + 5 && (- valz % size == 20 || - valz % size == -20 || - valz % size == 21 || - valz % size == -21 || - valz % size == 22 || - valz % size == -22 || - valz % size == 23 || - valz % size == -23)){
                                                if(y == roadheight + 4)
                                                    setData(world, valx, (y +1), valz, (byte) 13);
                                            }
                                            else{
                                                setData(world, valx, (y +1), valz, data.get(index));
                                            }
                                        }

                                        found = true;

                                        index++;
                                        if(index == 2){
                                            index = 0;
                                        }
                                    }
                                }

                                if(!found){
                                    if(- valz % size != (plotsize + 3) && - valz % size != -(plotsize + 3) && - valz % size != (plotsize +8) && - valz % size != -(plotsize +8)){
                                        setData(world, valx, (y +1), valz, (byte) 3);
                                    }
                                    else{
                                        setData(world, valx, (y +1), valz, (byte) 5);
                                    }
                                }
                            }
                        }
                        else if(valx % size != 0 && (valz % size == 7 || valz % size == -7 || valz % size == (plotsize +8) || valz % size == -(plotsize +8))){
                            if((valz % size == 7 && valx % size == 7) || (valz % size == 7 && valx % size == -7) || (valz % size == -7 && valx % size == 7) || ((valz % size == -7 && valx % size == -7)) || (valz % size == (plotsize +8) && valx % size == 7) || (valz % size == (plotsize +8) && valx % size == -7) || (valz % size == -(plotsize +8) && valx % size == 7) || ((valz % size == -(plotsize +8) && valx % size == -7))){
                                setData(world, valx, (y +1), valz, (byte) 5);
                            }
                            else{
                                List<Byte> data = Arrays.asList((byte) 11, (byte) 3);

                                int index = 0;
                                boolean found = false;
                                for(int i = 12; i <= plotsize +2; i += 6){
                                    if(- valx % size <= i && - valx % size >= -i){
                                        if(index == 1 && (- valx % size == 12 || - valx % size == -12 || - valx % size == 19 || - valx % size == -19 || - valx % size == 24 || - valx % size == -24 || - valx % size == 31 || - valx % size == -31 || - valx % size == 36 || - valx % size == -36 || - valx % size == 43 || - valx % size == -43 || - valx % size == 48 || - valx % size == -48 || - valx % size == 55 || - valx % size == -55 || - valx % size == 60 || - valx % size == -60 || - valx % size == 67 || - valx % size == -67 || - valx % size == 72 || - valx % size == -72 || - valx % size == 79 || - valx % size == -79 || - valx % size == 84 || - valx % size == -84)){
                                            setData(world, valx, (y +1), valz, (byte) 5);
                                        }
                                        else{
                                            setData(world, valx, (y +1), valz, data.get(index));
                                        }

                                        found = true;

                                        index++;
                                        if(index == 2){
                                            index = 0;
                                        }
                                    }
                                }

                                if(!found){
                                    if(- valx % size != (plotsize + 3) && - valx % size != -(plotsize + 3) && - valx % size != (plotsize +8) && - valx % size != -(plotsize +8)){
                                        setData(world, valx, (y +1), valz, (byte) 3);
                                    }
                                    else{
                                        setData(world, valx, (y +1), valz, (byte) 5);
                                    }
                                }
                            }
                        }
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