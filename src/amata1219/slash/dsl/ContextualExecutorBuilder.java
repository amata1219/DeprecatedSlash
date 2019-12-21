package amata1219.slash.dsl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.BiFunction;

import org.bukkit.command.CommandSender;

import amata1219.slash.dsl.Either.Success;

public class ContextualExecutorBuilder<S extends CommandSender> {
	
	public final BiFunction<S, RawCommandContext, Maybe<PartiallyParsedArguments>> argumentsParser;
	
	public ContextualExecutorBuilder(BiFunction<S, RawCommandContext, Maybe<PartiallyParsedArguments>> argumentsParser){
		this.argumentsParser = argumentsParser;
	}
	
	public ContextualExecutorBuilder<S> parsers(Message onMissingArguments, Parser<?>... parsers){
		BiFunction<S, RawCommandContext, Maybe<PartiallyParsedArguments>> combinedParser = (sender, context) -> {
			Either<Message, PartiallyParsedArguments> result = parse(new LinkedList<>(Arrays.asList(parsers)), new LinkedList<>(context.arguments), new ArrayList<>(), onMissingArguments);
			if(result instanceof Success) return Maybe.Some(((Success<Message, PartiallyParsedArguments>) result).value);
			else return Maybe.None();
		};
		return new ContextualExecutorBuilder<>(combinedParser);
	}
	
	private Either<Message, PartiallyParsedArguments> parse(Queue<Parser<?>> remainingParsers, Queue<String> remainingArguments, List<Object> accumulator, Message onMissingArguments){
		if(remainingParsers.isEmpty()) return Either.Success(new PartiallyParsedArguments(accumulator, remainingArguments));
		if(remainingArguments.isEmpty()) return Either.Failure(onMissingArguments);
		Parser<?> parser = remainingParsers.poll();
		String arg = remainingArguments.poll();
		return parser.parse(arg).flatMap(result -> {
			accumulator.add(result);
			return parse(remainingParsers, remainingArguments, accumulator, onMissingArguments);
		});
	}

}
