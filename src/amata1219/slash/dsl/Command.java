package amata1219.slash.dsl;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import amata1219.slash.ArgList;

public interface Command extends CommandExecutor {
	
	void onCommand(CommandSender sender, ArgList args);
	
	@Override
	default boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args){
		onCommand(sender, new ArgList(args));
		return true;
	}

}
