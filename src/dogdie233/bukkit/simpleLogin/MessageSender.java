package dogdie233.bukkit.simpleLogin;

import org.bukkit.ChatColor;

public class MessageSender {
	
	public static String getFormatMessage (String message, String playerName) {
		String sendMessage = message.replace("%player_name%", playerName); //把"%player_name%"替换成玩家名字
		sendMessage = ChatColor.translateAlternateColorCodes('§', sendMessage); //把'§' + 颜色代码转换成该有的颜色
		return sendMessage;
	}
	
	public static String getFormatMessageFromConfig (String path, String playerName) {
		return getFormatMessage (SimpleLogin.getInstance().getConfig().getString(path), playerName);
	}
}
