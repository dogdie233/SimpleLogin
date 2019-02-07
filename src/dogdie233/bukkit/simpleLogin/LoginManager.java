package dogdie233.bukkit.simpleLogin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
	
	public static boolean isLogin (UUID playerUUID) {
		return !unloginList.contains(playerUUID.toString());
	}
	
	public static void setPlayerLogin (UUID playerUUID, boolean isLogin) {
		if (isLogin) {
			if (unloginList.contains(playerUUID.toString())) {
				unloginList.remove(playerUUID.toString());
			}
		} else {
			if (!unloginList.contains(playerUUID.toString())) {
				unloginList.add(playerUUID.toString());
			}
		}
	}
	
	public static boolean isRegistered (UUID playerUUID) {
		if (playerPWConfig.getString(playerUUID.toString()) == null) {
			return false;
		}
		return true;
	}
	
	public static void register (UUID playerUUID, String password) {
		if (!isRegistered(playerUUID)) {
			playerPWConfig.set(playerUUID.toString(), password);
			saveConfig();
		}
	}
	
	public static boolean isCorrectPassword(UUID playerUUID, String password) {
        if(!isRegistered(playerUUID))
            return false;
        String pass = playerPWConfig.getString(playerUUID.toString());
        return pass.equals(password);
    }
	
	public static void changePassword (UUID playerUUID, String NewPassword) {
		playerPWConfig.set(playerUUID.toString(), NewPassword);
		saveConfig();
	}
	
	private static void saveConfig () {
		try {
			playerPWConfig.save(playerPWConfigFile);
		} catch (IOException e) {
		}
	}
}
