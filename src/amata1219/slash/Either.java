package amata1219.slash;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Either<E, R> {
	
	default void match(Consumer<E> whenE, Consumer<R> whenR){
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
	
	class Error<E, R> implements Either<E, R> {
		
		public final E error;
		
		public Error(E error){
			this.error = error;
		}
		
	}
	
	class Result<E, R> implements Either<E, R> {
		
		public final R result;
		
		public Result(R result){
			this.result = result;
		}
		
	}
	
}
