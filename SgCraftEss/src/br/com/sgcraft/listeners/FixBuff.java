package br.com.sgcraft.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class FixBuff implements Listener{
	
    @EventHandler(priority = EventPriority.LOWEST)
    public void BlockComand(final PlayerCommandPreprocessEvent e) {
        final String comando = e.getMessage().toLowerCase();
        if (comando.toLowerCase().startsWith("/buff")) {
            e.getPlayer().chat("/sgcraftess:buff");
            e.setCancelled(true);
            return;
        }
    }
	

}
