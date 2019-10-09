package amata1219.slash;

import java.util.function.Supplier;

public class LabeledStatement<T> {
	
	public final Matcher<T> matcher;
	private final Supplier<Command<T>> expression;
	
	public LabeledStatement(Matcher<T> matcher, Supplier<Command<T>> expression){
		this.matcher = matcher;
		this.expression = expression;
	}
	
	public Command<T> evaluate(){
		return expression.get();
	}

}
