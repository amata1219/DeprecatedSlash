package amata1219.slash;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import amata1219.slash.Either.Error;
import amata1219.slash.Either.Result;

public class Slash extends JavaPlugin {
	
	private static Slash plugin;
	
	@Override
	public void onEnable(){
		plugin = this;
	}
	
	@Override
	public void onDisable(){
		
	}
	
	public static Slash plugin(){
		return plugin;
	}
	
	public void execute(CommandSender sender, String[] args){
		/*
		 * Result(next()).when(Maybe::isNothing, "err: a").flatBind(
		 * a -> Result(next()).when(Maybe::isNothing, "err: b").flatBind(
		 * b -> b.match(s -> b.equals(s),
		 *         case("hi", () -> sender.sendMessage("res: hi")),
		 *         case("bye", Result(next()).when(Maybe::isNothing, "err: c").ifJust(
		 *             c -> execute(a, c)
		 *         ))
		 *      )
		 * )).ifNothing(sender::sendMessage);
		 * 
		 * Result(next()).whenN("err: a").flatBind(
		 * a -> Result(next()).whenN("err: b").flatBind(
		 * b -> b.match(s -> b.equals(s),
		 *         case("hi", () -> sender.sendMessage("res: hi")),
		 *         case("bye", Result(next()).whenN("err: c").ifJust(
		 *             c -> execute(a, c)
		 *         )),
		 *         else(() -> Error("err: else"))
		 *      )
		 * )).ifNothing(sender::sendMessage);
		 * 
		 * 
		 * Result(next()): Result<Maybe<T>>
		 * whenN("err: a"): err: Error<String> / Result<Maybe<T>>
		 * flatBind(a -> Result(next()): Result<Maybe<U>>
		 * whenN("err: b"): err: Error<String> / Result<Maybe<U>>
		 * flatBind(b -> b.match(~)): Error<String>
		 * case("hi", ~): Either<String, Maybe<U>>
		 * case("bye", either(next)): Result<Maybe<V>>
		 * whenN("err: c"): err: Error<String> / Result<Maybe<V>>
		 * isJust(~): Result<Maybe<V>>
		 * execute(a, c): void
		 * else(() -> Error("err: else")): Error<String>
		 * ifNothing(~): void
		 * 
		 */
		
	}
	
}
