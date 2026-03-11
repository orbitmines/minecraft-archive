package me.O_o_Fadi_o_O.OrbitMinesPoll;

import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Currency;

import org.bukkit.entity.Player;

public class Reward {

	private Currency currency;
	private int amount;
	
	public Reward(Currency currency, int amount){
		this.currency = currency;
		this.amount = amount;
	}
	
	public Currency getCurrency(){
		return currency;
	}
	
	public int getAmount(){
		return amount;
	}
	
	public void sendMessage(Player p){
		switch(getCurrency()){
			case ORBITMINES_TOKENS:
				p.sendMessage("§e§l+" + getRewardString());
				break;
			case VIP_POINTS:
				p.sendMessage("§b§l+" + getRewardString());
				break;
			default:
				break;
		
		}
	}
	
	public String getRewardString(){
		switch(getCurrency()){
			case ORBITMINES_TOKENS:
				return "§e§l" + getAmount() + " OMT";
			case VIP_POINTS:
				return "§b§l" + getAmount() + " VIP Points";
			default:
				return null;
		
		}
	}
	
	public void give(Player p){
		OMPlayer omp = OMPlayer.getOMPlayer(p);
		
		if(omp.isLoaded()){
			switch(currency){
				case ORBITMINES_TOKENS:
					omp.addOrbitMinesTokens(getAmount());
					break;
				case VIP_POINTS:
					omp.addVIPPoints(getAmount());
					break;
				default:
					break;
			}
		}
	}
}
