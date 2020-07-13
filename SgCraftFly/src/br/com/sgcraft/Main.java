package br.com.sgcraft;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Main extends JavaPlugin implements Listener, CommandExecutor{
	
	public void onEnable(){
		  Bukkit.getConsoleSender().sendMessage("Plugin Ativado");
	}
	public void onDisable() {
		  Bukkit.getConsoleSender().sendMessage("Plugin Desativado");
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
	    if (!(sender instanceof Player)) {
	      sender.sendMessage("Somente jogadores podem executar esse comando.");
	      return true;
	    } 
	if (lb.equalsIgnoreCase("mc")) {
        Player p = (Player)sender;
          if(p.hasPermission("mc.voar")) {
      		if(p.getAllowFlight() == true) {
      			p.setAllowFlight(false);
      			p.sendMessage("�cTapete m�gico �4DESATIVADO!");
      			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 80, 100));
      			}
      			else {
      				p.setAllowFlight(true);
      				p.sendMessage("�aTapete m�gico �2ATIVADO!");
            return true; 
      			}
          }else {
        	 p.sendMessage("�cApenas jogadores VIPs podem usar o �f/mc�c.");
		}
         }
	return false;
	}
}
