package amata1219.slash.dsl.component;

import java.util.function.Supplier;

import amata1219.slash.Text;

public interface ErrorMessage extends Supplier<String> {
	
	default Supplier<String> format(Object... objects){
		return () -> Text.of(get()).format(objects).color().toString();
	}
	
}
