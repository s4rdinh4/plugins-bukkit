package br.com.sgcraft.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinMessage implements Listener {
    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        e.setJoinMessage(null);
    }
    
    @EventHandler
    public void onQuit(final PlayerQuitEvent e) {
        e.setQuitMessage(null);
    }
    @EventHandler (priority = EventPriority.LOWEST)
    public void onJoinClearChat(final PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        for (int i = 1; i < 60; ++i) {
            p.sendMessage("                ");
        }
    }
}
