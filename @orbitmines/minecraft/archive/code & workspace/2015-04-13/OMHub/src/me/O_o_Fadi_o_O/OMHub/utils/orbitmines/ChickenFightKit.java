package me.O_o_Fadi_o_O.OMHub.utils.orbitmines;

import java.util.List;

import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.ServerData.ServerStorage;

public class ChickenFightKit {

	private ChickenFightKitEnum kitenum;
	private int price;
	
	public ChickenFightKit(ChickenFightKitEnum kitenum, int price){
		this.kitenum = kitenum;
		this.price = price;
	}

	public ChickenFightKitEnum getKitEnum(){
		return kitenum;
	}
	public void setKitEnum(ChickenFightKitEnum kitenum){
		this.kitenum = kitenum;
	}

	public int getPrice(){
		return price;
	}

	public void setPrice(int price){
		this.price = price;
	}
	
	public static List<ChickenFightKit> getCFKits(){
		return ServerStorage.cfkits;
	}
	
	public static ChickenFightKit getCFKit(ChickenFightKitEnum kitenum){
		for(ChickenFightKit cfkit : getCFKits()){
			if(cfkit.getKitEnum() == kitenum){
				return cfkit;
			}
		}
		return null;
	}
	
	public static ChickenFightKit addCFKit(ChickenFightKitEnum kitenum, int price){
		ChickenFightKit cfkit = new ChickenFightKit(kitenum, price);
		ServerStorage.cfkits.add(cfkit);
		return cfkit;
	}
}
