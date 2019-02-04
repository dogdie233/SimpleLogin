package dogdie233.bukkit.simpleLogin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class LoginManager {
	
	private static List<String> unloginList = new ArrayList<>();
	private static FileConfiguration playerPWConfig;
	private static File playerPWConfigFile;
	
	public static void init () {
		playerPWConfigFile = new File(SimpleLogin.getInstance().getDataFolder(), "playerPassword.yml");
		playerPWConfig = YamlConfiguration.loadConfiguration(playerPWConfigFile);
	}
	
	public static boolean isLogin (String playerName) {
		return !unloginList.contains(playerName);
	}
	
	public static void setPlayerLogin (String playerName, boolean isLogin) {
		if (isLogin) {
			if (unloginList.contains(playerName)) {
				unloginList.remove(playerName);
			}
		} else {
			if (!unloginList.contains(playerName)) {
				unloginList.add(playerName);
			}
		}
	}
	
	public static boolean isRegistered (String playerName) {
		if (playerPWConfig.getString(playerName) == null) {
			return false;
		}
		return true;
	}
	
	public static void register (String playerName, String password) {
		if (!isRegistered(playerName)) {
			playerPWConfig.set(playerName, password);
			saveConfig();
		}
	}
	
	public static boolean isCorrectPassword(String playerName, String password) {
        if(!isRegistered(playerName))
            return false;
        String pass = playerPWConfig.getString(playerName);
        return pass.equals(password);
    }
	
	public static void changePassword (String playerName, String NewPassword) {
		playerPWConfig.set(playerName, NewPassword);
		saveConfig();
	}
	
	private static void saveConfig () {
		try {
			playerPWConfig.save(playerPWConfigFile);
		} catch (IOException e) {
		}
	}
}
