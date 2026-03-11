package fadidev.orbitmines.hub.handlers;

import fadidev.orbitmines.hub.utils.enums.TicketType;

import java.util.ArrayList;
import java.util.List;

public class Ticket {

	public static List<TicketType> TICKETS = new ArrayList<>();
	public static List<TicketType> SG_KITS = new ArrayList<>();
	public static List<TicketType> SG_PERKS = new ArrayList<>();
	public static List<TicketType> UHC_KITS = new ArrayList<>();
	public static List<TicketType> UHC_PERKS = new ArrayList<>();
	public static List<TicketType> SKYWARS_KITS = new ArrayList<>();
	public static List<TicketType> SKYWARS_PERKS = new ArrayList<>();
	public static List<TicketType> CF_KITS = new ArrayList<>();
	public static List<TicketType> CF_PERKS = new ArrayList<>();
	public static List<TicketType> KITS = new ArrayList<>();

	private TicketType type;
	private int amount;
	
	public Ticket(TicketType type, int amount){
		this.type = type;
		this.amount = amount;
	}

	public TicketType getType() {
		return type;
	}

	public void setType(TicketType type) {
		this.type = type;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString(){
		return getType().ordinal() + ":" + getAmount();
	}
}
