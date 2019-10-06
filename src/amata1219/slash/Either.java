package amata1219.slash;

import java.util.function.Consumer;
import java.util.function.Function;

public interface Either<E, R> {
	
	public static void main(String[] $){
		match(Result.create("result").map(String::length), 
			error -> println(error),
			result -> println(result)
		);
		
		match(Error.create("error").map(Object::hashCode),
			error -> println(error),
			result -> println(result)
		);
	}
	
	public static <E, R> void match(Either<E, R> either, Consumer<E> whenE, Consumer<R> whenR){
		if(either instanceof Error) whenE.accept(((Error<E, R>) either).error);
		else whenR.accept(((Result<E, R>) either).result);
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
		
		private Error(E error){
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
