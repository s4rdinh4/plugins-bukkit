package br.com.sgcraft.comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ComandoMc implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("Somente para jogadores");
      return true;
    } 
    if (lb.equalsIgnoreCase("mc")) {
      Player p = (Player)sender;
      if (!p.hasPermission("sgcraft.mc.voar")) {
        p.sendMessage("jogadores VIPs podem usar o §f/mc§c.");
      } else if (p.getAllowFlight()) {
        p.setAllowFlight(false);
        p.sendMessage("§cTapete magico §4DESATIVADO§c!");
        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 80, 100));
      } else {
        p.setAllowFlight(true);
        p.sendMessage("§aTapete magico §2ATIVADO§c!");
        return true;
      } 
    } 
    return false;
  }
}
