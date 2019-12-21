package amata1219.slash.dsl;

import org.bukkit.command.CommandSender;

public interface MessageEffect {
	
	default void sendTo(CommandSender sender){
		sender.sendMessage(message());
	}
	
	String message();
	
}
