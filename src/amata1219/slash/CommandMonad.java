package amata1219.slash;

import static amata1219.slash.Interval.*;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import com.google.common.base.Supplier;

public interface CommandMonad<R> {
	
	public static void main(String[] $){
		
		Result(100).otherwise(Range(0, 10)::contains, () -> "");
		
	}
	
	public static <R> Maybe<R> Result(R result){
		return Maybe.unit(result);
	}
	
	public static <R> Error<R> Error(String error){
		return new Error<>(error);
	}
	
	@SuppressWarnings("unchecked")
	default <T> CommandMonad<T> flatBind(Function<R, CommandMonad<T>> mapper){
		return this instanceof Error ? (CommandMonad<T>) this : mapper.apply(((Result<R>) this).result);
	}
	
	default <T> CommandMonad<T> bind(Function<R, T> mapper){
		return flatBind(res -> Result(mapper.apply(((Result<R>) this).result)));
	}
	
	default CommandMonad<R> when(Predicate<R> predicate, Supplier<String> error){
		return this instanceof Error ? this : predicate.test(((Result<R>) this).result) ? Error(error.get()) : this;
	}
	
	default CommandMonad<R> otherwise(Predicate<R> predicate, Supplier<String> error){
		return when(predicate.negate(), error);
	}
	
	default void match(Consumer<String> error, Consumer<R> result){
		if(this instanceof Error) error.accept(((Error<R>) this).error);
		else result.accept(((Result<R>) this).result);
	}
	
	class Error<R> implements CommandMonad<R> {
		
		public final String error;
		
		private Error(String error){
			this.error = error;
		}
		
	}
	
	class Result<R> implements CommandMonad<R> {
		
		public final R result;
		
		private Result(R result){
			this.result = result;
		}
		
	}
	
}
