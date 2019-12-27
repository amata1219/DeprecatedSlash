package amata1219.slash.dsl;

public interface TriFunction<F, S, T, R> {
	
	R apply(F first, S second, T third);

}
