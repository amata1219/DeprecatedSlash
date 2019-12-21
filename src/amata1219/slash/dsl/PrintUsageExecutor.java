package amata1219.slash.dsl;

public class PrintUsageExecutor implements ContextualExecutor {
	
	public static final PrintUsageExecutor executor = new PrintUsageExecutor();

	@Override
	public void executeWith(RawCommandContext context) {
		context.sender.sendMessage(context.command.aliasUsed);
	}

}
