package amata1219.slash;

import amata1219.slash.Maybe.Just;

public interface CommandMonad<T> extends Either<String, Maybe<T>> {
	
	public static void main(String[] $){
	}
	
	public static <T> CommandResult<T> Result(T result){
		return new CommandResult<>(Maybe.unit(result));
	}
	
	public static <T> CommandError<T> Error(String error){
		return new CommandError<>(error);
	}
	
	@SuppressWarnings("unchecked")
	default CommandMonad<T> whenN(boolean predicate, String error){
		return this instanceof Error ? this : ((Result<String, Maybe<T>>) this).result instanceof Just ? this : Error(error);
	}
	
	class CommandError<T> implements CommandMonad<T> {
		
		public final String error;
		
		public CommandError(String error){
			this.error = error;
		}
		
	}
	
	class CommandResult<T> implements CommandMonad<T> {
		
		public final Maybe<T> result;
		
		public CommandResult(Maybe<T> result){
			this.result = result;
		}
		
	}
	
}
