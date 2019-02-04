package dogdie233.bukkit.simpleLogin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleLogin extends JavaPlugin {
	
	private static SimpleLogin instance;
	
	@Override
	public void onEnable () {
		this.saveDefaultConfig();
		this.saveResource("playerPassword.yml", false);
		instance = this;
		LoginManager.init();
		Bukkit.getPluginManager().registerEvents(new PlayerLimitListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerTipListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerLoginCommand(), this);
		CommandExecutor ce = new PlayerLoginCommand();  //注册指令，这里三个指令公用同一个Executor
        Bukkit.getPluginCommand("login").setExecutor(ce);
        Bukkit.getPluginCommand("register").setExecutor(ce);
        Bukkit.getPluginCommand("changePassword").setExecutor(ce);
		this.getLogger().info(ChatColor.GREEN + "SimpleLogin 加载成功");
	}
	
	@Override
	public void onDisable () {
		this.getLogger().info(ChatColor.RED + "SimpleLogin 已经卸载");
	}
	
	public static SimpleLogin getInstance () {
		return instance;
	}
}
