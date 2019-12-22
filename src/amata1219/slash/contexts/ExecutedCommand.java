package amata1219.slash.contexts;

import org.bukkit.command.Command;

public class ExecutedCommand {
	
	public final Command command;
	public final String aliasUsed;
	
	public ExecutedCommand(Command command, String aliasUsed){
		this.command = command;
		this.aliasUsed = aliasUsed;
	}

}
