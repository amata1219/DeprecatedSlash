package amata1219.slash;

import static amata1219.slash.Interval.*;
import static amata1219.slash.SampleCommand.Message.*;
import static amata1219.slash.dsl.CommandMonad.*;
import static amata1219.slash.dsl.component.Matcher.*;

import org.bukkit.command.CommandSender;

import amata1219.slash.dsl.Command;
import amata1219.slash.dsl.component.ErrorMessage;

public class SampleCommand implements Command {
	
	@SuppressWarnings("unchecked")
	public void onCommand(CommandSender sender, ArgList args){
		args.next(E1).match(
			Case("add", "+").label(() -> args.nextInt(E2).otherwise(Range(0, 10)::contains, i -> E3.format(i)).whenR(
				n -> add(sender, n)
			)),
			Case("sub", "-").label(() -> args.nextInt(E4).when(Range(Integer.MIN_VALUE, 0)::contains, i -> E5.format(i)).whenR(
				n -> sub(sender, n)
			)),
			Case(s -> s.length() == 5, () -> args.next(E6).flatBind(
				s -> args.next(E7).whenR(
				t -> execute(s, t)
			))),
			Else(() -> Error(E8))
		).whenE(sender::sendMessage);
		
	}
	
	public void add(CommandSender sender, int n){
		System.out.println("add: " + n);
	}
	
	public void sub(CommandSender sender, int n){
		System.out.println("sub: " + n);
	}
	
	public void execute(String s, String t){
		System.out.println("exe: " + s + " : " + t);
	}
	
	enum Message implements ErrorMessage {
		
		E1("&c-第1引数を指定して下さい。"),
		E2("&c-0以上10未満の整数値を指定して下さい。"),
		E3("&c-指定された値%sは不正です。0以上10未満の整数値を指定して下さい。"),
		E4(""),
		E5(""),
		E6(""),
		E7(""),
		E8("");
		
		private final String raw;
		
		private Message(String raw){
			this.raw = raw;
		}

		@Override
		public String get() {
			return raw;
		}
		
	}
	
}
