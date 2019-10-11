package amata1219.slash;

import static amata1219.slash.Command.*;
import static amata1219.slash.Interval.*;
import static amata1219.slash.Matcher.*;
import static amata1219.slash.Sample.M.*;

import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

public final class Sample {
	
	private static final Predicate<String> UUID_MATCHER = Pattern.compile("[0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}").asPredicate();
	
	protected enum M implements ErrorMessage {
		
		E1("&c-第1引数を指定して下さい。"),
		E2("&c-0以上10未満の整数値を指定して下さい。"),
		E3("&c-指定された値%sは不正です。0以上10未満の整数値を指定して下さい。"),
		E4(""),
		E5(""),
		E6(""),
		E7(""),
		E8("");
		
		private final String raw;
		
		private M(String raw){
			this.raw = raw;
		}

		@Override
		public String get() {
			return raw;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void onCommand(CommandSender sender, ArgList args){
		args.next(E1).match(
			Case("add", "+").label(() -> args.nextInt(E2).otherwise(Range(0, 10)::contains, i -> E3.format(i)).whenR(
				n -> add(sender, n)
			)),
			Case("sub", "-").label(() -> args.nextInt(E4).when(Range(Integer.MIN_VALUE, 0)::contains, i -> E5.format(i)).whenR(
				n -> sub(sender, n)
			)),
			Case(UUID_MATCHER, () -> args.next(E6).flatBind(
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
	
	public static void main(String[] $){
		new Sample().onCommand(new DummySender(), new ArgList(new String[]{"add", "2.5"}));
	}
	
	public static class DummySender implements CommandSender {

		@Override
		public PermissionAttachment addAttachment(Plugin arg0) {
			return null;
		}

		@Override
		public PermissionAttachment addAttachment(Plugin arg0, int arg1) {
			return null;
		}

		@Override
		public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2) {
			return null;
		}

		@Override
		public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2, int arg3) {
			return null;
		}

		@Override
		public Set<PermissionAttachmentInfo> getEffectivePermissions() {
			return null;
		}

		@Override
		public boolean hasPermission(String arg0) {
			return false;
		}

		@Override
		public boolean hasPermission(Permission arg0) {
			return false;
		}

		@Override
		public boolean isPermissionSet(String arg0) {
			return false;
		}

		@Override
		public boolean isPermissionSet(Permission arg0) {
			return false;
		}

		@Override
		public void recalculatePermissions() {
		}

		@Override
		public void removeAttachment(PermissionAttachment arg0) {
		}

		@Override
		public boolean isOp() {
			return false;
		}

		@Override
		public void setOp(boolean arg0) {
		}

		@Override
		public String getName() {
			return null;
		}

		@Override
		public Server getServer() {
			return null;
		}

		@Override
		public void sendMessage(String arg0) {
		}

		@Override
		public void sendMessage(String[] arg0) {
		}

		@Override
		public Spigot spigot() {
			return null;
		}
		
		
	}
	
}
