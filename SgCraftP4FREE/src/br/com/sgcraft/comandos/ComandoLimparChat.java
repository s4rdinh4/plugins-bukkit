package br.com.sgcraft.comandos;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class ComandoLimparChat implements CommandExecutor{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("limparchat")) {
            final Player p = (Player)sender;
            if (p.hasPermission("sgcraft.limparchat")) {
                for (int i = 1; i < 60; ++i) {
                    Bukkit.broadcastMessage("                ");
                }
                Bukkit.broadcastMessage("  §4>> §fO Chat foi limpo por: §c" + p.getName() + "§f!");
            }
        }
        return false;
    }
}