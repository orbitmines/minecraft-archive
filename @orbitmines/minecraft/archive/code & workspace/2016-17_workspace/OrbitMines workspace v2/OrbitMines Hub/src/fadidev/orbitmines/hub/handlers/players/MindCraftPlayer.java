package fadidev.orbitmines.hub.handlers.players;

import fadidev.orbitmines.api.handlers.Database;
import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.hub.OrbitMinesHub;
import fadidev.orbitmines.hub.handlers.HubMessages;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MindCraftPlayer {

	private static OrbitMinesHub hub;
	private HubPlayer omp;
	private int wins;
	private int bestGame;
	
	private int currentTurn;
	private List<String> blocksFromTurns;
	private List<String> statusFromTurns;
	private String correctTurn;
	
	public MindCraftPlayer(HubPlayer omp, int wins, int bestGame){
	    hub = OrbitMinesHub.getHub();
		this.omp = omp;
		this.wins = wins;
		this.bestGame = bestGame;
		this.currentTurn = 1;
		this.blocksFromTurns = null;
		this.statusFromTurns = null;
		this.correctTurn = null;
	}

    public HubPlayer getPlayer() {
        return omp;
    }

    public int getWins(){
		return wins;
	}
	public void setWins(int wins){
		this.wins = wins;
	}
	public void addWin(){
		this.wins = getWins() +1;
		
		Database.get().update("MasterMind-Wins", "wins", "" + getWins(), "uuid", getPlayer().getUUID().toString());
	}

	public int getBestGame(){
		return bestGame;
	}
	public void setBestGame(int bestGame){
		this.bestGame = bestGame;
		
		Database.get().update("MasterMind-BestGame", "turns", "" + getBestGame(), "uuid", getPlayer().getUUID().toString());
	}

	public int getCurrentTurn(){
		return currentTurn;
	}
	public void setCurrentTurn(int currentTurn){
		this.currentTurn = currentTurn;
	}

	public List<String> getBlocksFromTurns(){
		return blocksFromTurns;
	}
	public void setBlocksFromTurns(List<String> blocksFromTurns){
		this.blocksFromTurns = blocksFromTurns;
	}

	public List<String> getStatusFromTurns(){
		return statusFromTurns;
	}
	public void setStatusFromTurns(List<String> statusFromTurns){
		this.statusFromTurns = statusFromTurns;
	}

	public String getCorrectTurn(){
		return correctTurn;
	}
	public void setCorrectTurn(String correctTurn){
		this.correctTurn = correctTurn;
	}

	public void join(){
	    Player p = getPlayer().getPlayer();
		omp.setInMindcraft(true);

		List<String> blockData = Arrays.asList("0|0|0|0", "0|0|0|0", "0|0|0|0", "0|0|0|0", "0|0|0|0", "0|0|0|0", "0|0|0|0", "0|0|0|0", "0|0|0|0", "0|0|0|0", "0|0|0|0", "0|0|0|0");
		
		setBlocksFromTurns(blockData);
		setStatusFromTurns(blockData);
		
		setCorrectTurn(getRandomCorrectTurn());
		setCurrentTurn(1);
		
		p.teleport(hub.getMindCraft().getLocation());
		p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 1);

		Title t = new Title("§c§lMindCraft", HubMessages.BASED_ON_MASTER_MIND.get(omp), 20, 40, 20);
		t.send(p);
		
		p.sendMessage("");
		p.sendMessage(" §c§lMindCraft");

        switch(omp.getLanguage()){
            case DUTCH:
                omp.sendTimeMessage(1, " §7- Doel: Raad de goede combinatie van kleuren.",  Sound.UI_BUTTON_CLICK);
                omp.sendTimeMessage(4, " §7- Klik met het Wol in je inventory op de eerste rij voor je om te raden.",  Sound.UI_BUTTON_CLICK);
                omp.sendTimeMessage(7, " §7- Klaar? Klik op de Redstone Torch in je inventory om je beurt te eindigen.",  Sound.UI_BUTTON_CLICK);
                omp.sendTimeMessage(10, " §7- Het Glass aan de rechterkant laat zien hoe goed je elke beurt gedaan hebt.",  Sound.UI_BUTTON_CLICK);
                omp.sendTimeMessage(13, " §7- §aGroen§7: Eén van de kleuren is goed en op de goede plek.",  Sound.UI_BUTTON_CLICK);
                omp.sendTimeMessage(16, " §7- §eGeel§7: Eén van de kleuren is goed, maar niet op de goede plek.",  Sound.UI_BUTTON_CLICK);
                omp.sendTimeMessage(19, " §7- §fWit§7: Eén van de kleuren is niet goed.",  Sound.UI_BUTTON_CLICK);
                break;
            case ENGLISH:
                omp.sendTimeMessage(1, " §7- Goal: Guess the correct combination of colors.",  Sound.UI_BUTTON_CLICK);
                omp.sendTimeMessage(4, " §7- Click with a Wool on the first row to set your turn.",  Sound.UI_BUTTON_CLICK);
                omp.sendTimeMessage(7, " §7- Done? Click the Redstone Torch in your inventory to end your turn.",  Sound.UI_BUTTON_CLICK);
                omp.sendTimeMessage(10, " §7- The Glass Bar on the right displays how well you did on a turn.",  Sound.UI_BUTTON_CLICK);
                omp.sendTimeMessage(13, " §7- §aGreen§7: One of the colors is correct and at the right place.",  Sound.UI_BUTTON_CLICK);
                omp.sendTimeMessage(16, " §7- §eYellow§7: One of the colors is correct but not on the right place.",  Sound.UI_BUTTON_CLICK);
                omp.sendTimeMessage(19, " §7- §fWhite§7: One of the colors isn't correct.",  Sound.UI_BUTTON_CLICK);
                break;
        }

		omp.sendTimeMessages(22, Sound.UI_BUTTON_CLICK, "", " §f§l" + HubMessages.GOOD_LUCK.get(omp) + "!");
		
		p.getInventory().clear();
		giveItems();
	}
	
	public void reset(){
        getPlayer().getPlayer().getInventory().clear();
		omp.setInMindcraft(false);
		setCurrentTurn(1);
		setBlocksFromTurns(null);
		setStatusFromTurns(null);
		setCorrectTurn(null);

        hub.getLobbyKit().get(getPlayer().getLanguage()).setItems(getPlayer().getPlayer());
	}
	
	public void leave(){
		Player p = getPlayer().getPlayer();
		p.getInventory().clear();
		
		reset();

		p.sendMessage(HubMessages.LEAVE_MINDCRAFT.get(omp));
		p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);
	}
	
	private String getRandomCorrectTurn(){
		List<String> canBe = new ArrayList<>();
        canBe.add("1");
        canBe.add("3");
        canBe.add("4");
        canBe.add("5");
        canBe.add("11");
        canBe.add("14");
		int first = getRandomIntForCorrectTurn(canBe);
        canBe.remove("" + first);
		int second = getRandomIntForCorrectTurn(canBe);
        canBe.remove("" + second);
		int third = getRandomIntForCorrectTurn(canBe);
        canBe.remove("" + third);
		int fourth = getRandomIntForCorrectTurn(canBe);
        canBe.remove("" + fourth);
		
		String correctTurn = first + "|" + second + "|" + third + "|" + fourth;
		
		return correctTurn;
	}
		
	private int getRandomIntForCorrectTurn(List<String> canBe){
		int rInt = new Random().nextInt(canBe.size());
		return Integer.parseInt(canBe.get(rInt));
	}
	
	public void giveItems(){
		hub.getMindCraftKit().get(omp.getLanguage()).replaceItems(getPlayer().getPlayer());
	}
}
