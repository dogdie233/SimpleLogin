package dogdie233.bukkit.simpleLogin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerLoginCommand implements Listener, CommandExecutor {
	@EventHandler(priority=EventPriority.LOWEST) //用来拦截除了登录插件以外的指令
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
        if(LoginManager.isLogin(e.getPlayer().getName())) {
            return;
        }
        switch (e.getMessage().split(" ")[0].toLowerCase()) {
        case "/register":
        case "/reg":
        case "/login":
        case "/l":
        case "/changePassword":
        case "/changePass":
        	e.setCancelled(false);
        	break;
        default:
        	e.setCancelled(true);
        	break;
        }
    }

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {
		SimpleLogin.getInstance().getLogger().info(cmd.getName());
		if(!(sender instanceof Player)) {
			sender.sendMessage("你不是个玩家，请勿输入本命令");
			return false;
		}
		Player p = (Player)sender;
		if (cmd.getName().equalsIgnoreCase("login") || cmd.getName().equalsIgnoreCase("l")) {
			loginCommand (p, arg);
		}
		if (cmd.getName().equalsIgnoreCase("register") || cmd.getName().equalsIgnoreCase("reg")) {
			registerCommand (p, arg);
		}
		if (cmd.getName().equalsIgnoreCase("changePassword") || cmd.getName().equalsIgnoreCase("changePass")) {
			changePassword (p, arg);
		}
		return true;
	}
	
	private void loginCommand (Player p, String[] args) {
		if (LoginManager.isLogin(p.getName())) {
			p.sendMessage(MessageSender.getFormatMessageFromConfig("loginAgain", p.getName()));
			return;
		}
		if(!LoginManager.isRegistered(p.getName())) {
            p.sendMessage(MessageSender.getFormatMessageFromConfig("noRegister", p.getName()));
            return;
        }
		if(args.length!=1) {
            p.sendMessage(MessageSender.getFormatMessageFromConfig("loginError", p.getName()));
            return;
        }
		if(LoginManager.isCorrectPassword(p.getName(), args[0])) {
            LoginManager.setPlayerLogin(p.getName(), true);
            p.setFlying(false);
            p.sendMessage(MessageSender.getFormatMessageFromConfig("loginAccount", p.getName()));
        } else {
        	p.sendMessage(MessageSender.getFormatMessageFromConfig("passwordError", p.getName()));
        }
	}
	
	private void registerCommand (Player p, String[] args) {
        if (LoginManager.isLogin(p.getName())) {
        	p.sendMessage(MessageSender.getFormatMessageFromConfig("registerAccountAfterLogin", p.getName()));
            return;
        }
        if (LoginManager.isRegistered(p.getName())) {
        	p.sendMessage(MessageSender.getFormatMessageFromConfig("registerAgain", p.getName()));
            return;
        }
        if (args.length!=2) {
        	p.sendMessage(MessageSender.getFormatMessageFromConfig("registerError", p.getName()));
            return;
        }
        if (!args[0].equals(args[1])) {
        	p.sendMessage(MessageSender.getFormatMessageFromConfig("registerPasswordError", p.getName()));
        	return;
        }
        LoginManager.register(p.getName(), args[0]);
        LoginManager.setPlayerLogin(p.getName(), true);
        p.setFlying(false);
        p.sendMessage(MessageSender.getFormatMessageFromConfig("registerAccount", p.getName()));
    }
	
	private void changePassword (Player p, String[] args) {
		if (!LoginManager.isLogin(p.getName())) {
			p.sendMessage(MessageSender.getFormatMessageFromConfig("changePasswordBeforeLogin", p.getName()));
			return;
		}
		if (!LoginManager.isRegistered(p.getName())) {
			p.sendMessage(MessageSender.getFormatMessageFromConfig("noRegister", p.getName()));
			return;
		}
		if (args.length!=2) {
        	p.sendMessage(MessageSender.getFormatMessageFromConfig("changePasswordError", p.getName()));
            return;
        }
		if (!LoginManager.isCorrectPassword(p.getName(), args[0])) {
			p.sendMessage(MessageSender.getFormatMessageFromConfig("passwordError", p.getName()));
			return;
		}
		LoginManager.changePassword(p.getName(), args[1]);
		p.sendMessage(MessageSender.getFormatMessageFromConfig("changeAccountPassword", p.getName()));
	}
}
