package amata1219.slash.parser;

import static amata1219.slash.monad.Either.*;

import java.util.function.Function;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

import amata1219.slash.parser.Interval.End;

public class Parsers {

	public static final Parser<String> identity = x -> success(x);
	
	public static final Parser<Boolean> bool = define(Boolean::valueOf, "&c-真偽値は true 又は false を入力して下さい。");
	
	public static final Parser<Integer> i32 = define(Integer::valueOf, "&c-半角数字で整数値を入力して下さい。");
	
	public static final Parser<Long> i64 = define(Long::valueOf, "&c-半角数字で整数値を入力して下さい。");
	
	public static final Parser<Integer> u32 = limited(i32, Interval.of(End.closed(0), End.closed(Integer.MAX_VALUE)), "自然数を入力して下さい。");
	
	public static final Parser<Long> u64 = limited(i64, Interval.of(End.closed(0L), End.closed(Long.MAX_VALUE)), "自然数を入力して下さい。");
	
	public static final Parser<Float> f32 = define(Float::valueOf, "&c-半角数字で少数値を入力して下さい。");
	
	public static final Parser<Double> f64 = define(Double::valueOf, "&c-半角数字で少数値を入力して下さい。");
	
	public static final Parser<Player> onlinePlayer = define(Bukkit::getPlayerExact, "&c-指定されたプレイヤーはオンラインではありません。");
	
	@SuppressWarnings("deprecation")
	public static final Parser<OfflinePlayer> offlinePlayer = define(Bukkit::getOfflinePlayer, "&c-指定されたプレイヤーは存在しません。")
			.then(OfflinePlayer::hasPlayedBefore, "&c-指定されたプレイヤーは存在しません。");
	
	public static final Parser<World> world = define(Bukkit::getWorld, "&c-指定されたワールドは存在しません。");
	
	public static final <N extends Number & Comparable<N>> Parser<N> limited(Parser<N> parser, Interval<N> interval, CharSequence error){
		return parser.then(interval::contains, error);
	}
	
	private static <T> Parser<T> define(Function<String, T> converter, CharSequence error){
		return x -> {
			try{
				T res = converter.apply(x);
				return res != null ? success(res) : failure(error);
			}catch(Exception e){
				return failure(error);
			}
		};
	}
	
}
