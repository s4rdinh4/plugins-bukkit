package br.com.sgcraft.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class BlockCmds implements Listener {
    @EventHandler
    public void onPlayerCommandPreprocess(final PlayerCommandPreprocessEvent e) {
        final Player p = e.getPlayer();
        if ((e.getMessage().equalsIgnoreCase("/plugins") || e.getMessage().equalsIgnoreCase("/pl")) && !e.getPlayer().hasPermission("sgcraft.cmdbypass")) {
            e.setCancelled(true);
            p.sendMessage("�cVoce nao pode ver os plugins do servidor!");
        }
        if ((e.getMessage().equalsIgnoreCase("/help") || e.getMessage().equalsIgnoreCase("/?")) && !e.getPlayer().hasPermission("sgcraft.cmdbypass")) {
            e.setCancelled(true);
            p.sendMessage("�cVoce nao pode usar esse comando!");
        }
        if (e.getMessage().equalsIgnoreCase("/version") && !e.getPlayer().hasPermission("sgcraft.cmdbypass")) {
            e.setCancelled(true);
            p.sendMessage("�cVoce nao pode usar esse comando!");
        }
        if (e.getMessage().equalsIgnoreCase("/nuke") && !e.getPlayer().hasPermission("sgcraft.cmdbypass")) {
            e.setCancelled(true);
            p.sendMessage("�cVoce nao pode usar esse comando!");
        }
        if (e.getMessage().equalsIgnoreCase("/bukkit:?") && !e.getPlayer().hasPermission("sgcraft.cmdbypass")) {
            e.setCancelled(true);
            p.sendMessage("�cVoce nao pode usar esse comando!");
        }
        if (e.getMessage().equalsIgnoreCase("/?") && !e.getPlayer().hasPermission("sgcraft.cmdbypass")) {
            e.setCancelled(true);
            p.sendMessage("�cVoce nao pode usar esse comando!");
        }
        if (e.getMessage().equalsIgnoreCase("/ver") && !e.getPlayer().hasPermission("sgcraft.cmdbypass")) {
            e.setCancelled(true);
            p.sendMessage("�cVoce nao pode usar esse comando!");
        }
        if (e.getMessage().equalsIgnoreCase("/pl") && !e.getPlayer().hasPermission("sgcraft.cmdbypass")) {
            e.setCancelled(true);
            p.sendMessage("�cVoce nao pode usar esse comando!");
        }
}
}
