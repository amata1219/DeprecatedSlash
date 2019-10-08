package amata1219.slash;

import java.util.function.Function;

import amata1219.slash.Result.Just;
import amata1219.slash.Result.Nothing;

public interface CommandMonad<R> {
	
	public static void main(String[] $){
	}
	
	/*
	 * default void match(Consumer<E> whenE, Consumer<R> whenR){
		if(this instanceof Error) whenE.accept(((Error<E, R>) this).error);
		else whenR.accept(((Result<E, R>) this).result);
	}
	
	default Either<E, R> when(boolean predicate, E error){
		return when(__ -> predicate, error);
	}
	
	default Either<E, R> when(Predicate<R> predicate, E error){
		return this instanceof Error ? this : predicate.test(((Result<E, R>) this).result) ? new Error<>(error) : this;
	}
	
	@SuppressWarnings("unchecked")
	default <R> Either<E, T> flatMap(Function<R, Either<E, T>> mapper){
		return this instanceof Error ? ((Error<E, T>) this) : mapper.apply(((Result<E, R>) this).result);
	}
	
	default <R> Either<E, T> map(Function<R, T> mapper){
		return flatMap(result -> new Result<E, T>(mapper.apply(result)));
	}
	 */
	
	public static <R> Result<R> Result(R result){
		return Result.unit(result);
	}
	
	public static <R> Error<R> Error(String error){
		return new Error<>(error);
	}
	
	/*default CommandMonad<R> whenN(String error){
		return this instanceof Error ? this : ((Result<R>) this).result instanceof Just ? this : Error(error);
	}
	}*/
	
	default <T> CommandMonad<T> flatBind(Function<R, CommandMonad<T>> mapper){
		if(this instanceof Error || this instanceof Nothing) return cast();
		else return mapper.apply(asJust().value);
	}
	
	default <T> CommandMonad<T> bind(Function<R, T> mapper){
		return flatBind(res -> Result(mapper.apply(asJust().value)));
	}
	
	@SuppressWarnings("unchecked")
	default <T> CommandMonad<T> cast(){
		if(this instanceof Just) throw new IllegalStateException("Just<R> can not be cast to Just<T>");
		else return (CommandMonad<T>) this;
	}
	
	default Just<R> asJust(){
		if(this instanceof Just) return (Just<R>) this;
		else throw new IllegalStateException("Error and Nothing can not be cast to Just");
	}
	
	class Error<R> implements CommandMonad<R> {
		
		public final String error;
		
		private Error(String error){
			this.error = error;
		}
		
	}
	
}
