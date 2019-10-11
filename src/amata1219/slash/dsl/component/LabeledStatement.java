package amata1219.slash.dsl.component;

import java.util.function.Supplier;

import amata1219.slash.dsl.CommandMonad;

public class LabeledStatement<T, R> {
	
	public final Matcher<T> matcher;
	private final Supplier<CommandMonad<R>> expression;
	
	public LabeledStatement(Matcher<T> matcher, Supplier<CommandMonad<R>> expression){
		this.matcher = matcher;
		this.expression = expression;
	}
	
	public CommandMonad<R> evaluate(){
		return expression.get();
	}

}
