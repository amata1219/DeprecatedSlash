package amata1219.slash;

import static amata1219.slash.Interval.*;
import static amata1219.slash.dsl.CommandMonad.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.base.Joiner;

import amata1219.slash.dsl.CommandMonad;
import amata1219.slash.dsl.component.ErrorMessage;

public class ArgList {

	private final String[] args;
	private int index;

	public ArgList(String[] args){
		this.args = args;
	}

	public int size(){
		return args.length;
	}
	
	public <R> CommandMonad<R> next(Function<String, R> converter, Supplier<String> error){
		R result = null;
		try{
			result = converter.apply(index < args.length ? args[index++] : null);
		}catch(Exception e){
			
		}
		return result != null ? Result(result) : Error(error.get());
	}
	
	public CommandMonad<String> next(Supplier<String> error){
		return next(Function.identity(), error);
	}
	
	public CommandMonad<String> next(ErrorMessage error){
		return next(Function.identity(), error);
	}

	public CommandMonad<Boolean> nextBool(Supplier<String> error){
		return next(Boolean::valueOf, error);
	}

	public CommandMonad<Character> nextChar(Supplier<String> error){
		return next(s -> s.length() == 1 ? s.charAt(0) : null, error);
	}

	public CommandMonad<Byte> nextByte(Supplier<String> error){
		return next(Byte::valueOf, error);
	}

	public CommandMonad<Short> nextShort(Supplier<String> error){
		return next(Short::valueOf, error);
	}

	public CommandMonad<Integer> nextInt(Supplier<String> error){
		return next(Integer::valueOf, error);
	}

	public CommandMonad<Long> nextLong(Supplier<String> error){
		return next(Long::valueOf, error);
	}

	public CommandMonad<Float> nextFloat(Supplier<String> error){
		return next(Float::valueOf, error);
	}

	public CommandMonad<Double> nextDouble(Supplier<String> error){
		return next(Double::valueOf, error);
	}
	
	public <T> CommandMonad<T> range(Interval<Integer> range, Function<Collection<String>, CommandMonad<T>> action){
		String[] ranged = Arrays.copyOfRange(args, range.lower.x, index = range.upper.x);
		return action.apply(Arrays.asList(ranged));
	}
	
	public <T> CommandMonad<T> range(int endInclusive, Function<Collection<String>, CommandMonad<T>> action){
		return range(Range(index + 1, endInclusive), action);
	}
	
	public CommandMonad<String> join(int endInclusive, Supplier<String> error){
		return range(endInclusive, ranged -> ranged.isEmpty() ? Error(error.get()) : Result(Joiner.on(' ').join(ranged)));
	}
	
	public ArgList skip(int count){
		index += count;
		return this;
	}

}
