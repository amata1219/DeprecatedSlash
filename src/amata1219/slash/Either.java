package amata1219.slash;

import java.util.function.Consumer;
import java.util.function.Function;

public interface Either<E, R> {
	
	public static void main(String[] $){
		Result.create("result")
		.map(String::length)
		.match(
			error -> println(error),
			result -> println(result)
		);
		
		Error.create("error")
		.map(Object::hashCode)
		.match(
			error -> println(error),
			result -> println(result)
		);
	}
	
	default void match(Consumer<E> whenE, Consumer<R> whenR){
		if(this instanceof Error) whenE.accept(((Error<E, R>) this).error);
		else whenR.accept(((Result<E, R>) this).result);
	}
	
	default Either<E, R> when(boolean predicate, E error){
		return this instanceof Error ? new Error<>(error) : this;
	}
	
	@SuppressWarnings("unchecked")
	default <T> Either<E, T> flatMap(Function<R, Either<E, T>> mapper){
		return this instanceof Error ? ((Error<E, T>) this) : mapper.apply(((Result<E, R>) this).result);
	}
	
	default <T> Either<E, T> map(Function<R, T> mapper){
		return flatMap(result -> new Result<E, T>(mapper.apply(result)));
	}
	
	class Error<E, R> implements Either<E, R> {
		
		public static <E> Either<E, ?> create(E error){
			return new Error<>(error);
		}
		
		public final E error;
		
		public Error(E error){
			this.error = error;
		}
		
	}
	
	class Result<E, R> implements Either<E, R> {
		
		public static <R> Either<?, R> create(R result){
			return new Result<>(result);
		}
		
		public final R result;
		
		public Result(R result){
			this.result = result;
		}
		
	}
	
	public static <T> void println(T t){
		System.out.println(t);
	}
	
}
