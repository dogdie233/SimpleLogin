package dogdie233.bukkit.simpleLogin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerTipListener implements Listener {
	@EventHandler
    private void onPlayerJoin(PlayerJoinEvent e) {
		e.getPlayer().setAllowFlight(true);
        if (LoginManager.isRegistered(e.getPlayer().getUniqueId())) {
        	e.getPlayer().sendMessage(MessageSender.getFormatMessageFromConfig("joinServer", e.getPlayer().getName()));
        } else {
        	e.getPlayer().sendMessage(MessageSender.getFormatMessageFromConfig("firstJoinServer", e.getPlayer().getName()));
        }
    }
}
