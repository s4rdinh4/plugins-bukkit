package br.com.sgcraft.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class VipZeroFix implements Listener{

    @EventHandler
    private void onCmd(final PlayerCommandPreprocessEvent e) {
        if ((e.getMessage().toLowerCase().startsWith("/vipzero") || e.getMessage().toLowerCase().startsWith("/bukkit:vipzero") || e.getMessage().toLowerCase().startsWith("/usekey") || e.getMessage().toLowerCase().startsWith("/usarkey") || e.getMessage().toLowerCase().startsWith("/vipzero:usarkey") || e.getMessage().toLowerCase().startsWith("/vipzero:usekey")) && !this.valida(e.getMessage().toLowerCase())) {
            e.setCancelled(true);
            final Player p = e.getPlayer();
            for (final Player p2 : Bukkit.getOnlinePlayers()) {
                if (p2.isOp()) {
                    p2.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "VipZero" + ChatColor.GRAY + "]" + ChatColor.RED + " O jogador " + p.getName() + " tentou se aproveitar da falha no VipZero.");
                }
            }
        }
    }
    
    public boolean valida(final String msg) {
        final String p = "^\\/[a-zA-Z0-9\\s\\-]*$";
        return msg.matches(p);
    }

}
