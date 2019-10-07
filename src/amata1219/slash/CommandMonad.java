package amata1219.slash;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import amata1219.slash.Maybe.Just;

public interface CommandMonad<T> {
	
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
	default <T> Either<E, T> flatMap(Function<R, Either<E, T>> mapper){
		return this instanceof Error ? ((Error<E, T>) this) : mapper.apply(((Result<E, R>) this).result);
	}
	
	default <T> Either<E, T> map(Function<R, T> mapper){
		return flatMap(result -> new Result<E, T>(mapper.apply(result)));
	}
	 */
	
	public static <T> Result<T> Result(T result){
		return Result(Maybe.unit(result));
	}
	
	public static <T> Result<T> Result(Maybe<T> result){
		return new Result<>(result);
	}
	
	public static <T> Error<T> Error(String error){
		return new Error<>(error);
	}
	
	default CommandMonad<T> whenN(String error){
		return this instanceof Error ? this : ((Result<T>) this).result instanceof Just ? this : Error(error);
	}
	
	@SuppressWarnings("unchecked")
	default <U> CommandMonad<U> map(Function<T, U> mapper){
		return this instanceof Error ? ((Error<U>) this) : Result(((Result<T>) this).result.bind(mapper));
	}
	
	class Error<T> implements CommandMonad<T> {
		
		public final String error;
		
		public Error(String error){
			this.error = error;
		}
		
	}
	
	class Result<T> implements CommandMonad<T> {
		
		public final Maybe<T> result;
		
		public Result(Maybe<T> result){
			this.result = result;
		}
		
	}
	
}
