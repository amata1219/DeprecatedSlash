package amata1219.slash.executor;

import org.bukkit.command.CommandSender;

import amata1219.slash.ContextualExecutor;
import amata1219.slash.contexts.RawCommandContext;
import amata1219.slash.effect.TargetedEffect;

public class EchoExecutor implements ContextualExecutor {
	
	private final TargetedEffect<CommandSender> effect;
	
	public EchoExecutor(TargetedEffect<CommandSender> effect){
		this.effect = effect;
	}

	@Override
	public void executeWith(RawCommandContext context) {
		effect.apply(context.sender);
	}

}
