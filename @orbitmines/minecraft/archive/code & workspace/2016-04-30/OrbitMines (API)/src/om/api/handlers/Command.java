package om.api.handlers;

import java.util.ArrayList;
import java.util.List;

import om.api.handlers.players.OMPlayer;

public abstract class Command {

	private static List<Command> cmds = new ArrayList<Command>();
	
	public Command(){
		cmds.add(this);
	}
	
	public abstract String[] getCMDs();
	public abstract void dispatch(OMPlayer omp, String[] a);
	
	public static List<Command> getCommands(){
		return cmds;
	}
}
