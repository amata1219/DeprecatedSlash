package amata1219.slash;

import java.util.function.Supplier;

public class Case<T> {
	
	private final T[] cases;
	private Supplier<CommandMonad<T>> expression;
	
	public Case(T... cases){
		this.cases = cases;
	}

}
