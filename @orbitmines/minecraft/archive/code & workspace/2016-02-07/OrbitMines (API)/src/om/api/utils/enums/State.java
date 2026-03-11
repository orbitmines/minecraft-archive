package om.api.utils.enums;

import om.api.handlers.MGArena;


public enum State {
	
	WAITING("Join", "§a"),
	STARTING("Join", "§a"),
	WARMUP("Spectate", "§6"),
	IN_GAME("Spectate", "§6"),
	ENDING("Restarting", "§8"),
	RESTARTING("Restarting", "§8"),
	CLOSED("Closed", "§4");
	
	private String status;
	private String color;
	
	State(String status, String color){
		this.status = status;
		this.color = color;
	}
	
	public String getStatus(MGArena arena){
		switch(this){
			case STARTING:
				if(arena.getPlayers() == arena.getType().getMaxPlayers()){
					return "Full";
				}
				return status;
			case WAITING:
				if(arena.getPlayers() == arena.getType().getMaxPlayers()){
					return "Full";
				}
				return status;
			default:
				return status;
		}
	}
	
	public String getColor(){
		return color;
	}
}
