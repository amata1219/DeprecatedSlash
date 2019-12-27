package amata1219.slash;

import java.util.Queue;

public class ParsedArguments {
	
	private final Queue<Object> arguments;
	
	public ParsedArguments(Queue<Object> arguments){
		this.arguments = arguments;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T next(){
		return (T) arguments.poll();
	}

}
