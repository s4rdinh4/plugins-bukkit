package br.com.sgcraft;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class ComandoBuff implements CommandExecutor {

	private Economy econ;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cApenas para jogadores");
			return true;
		}
		Player p = (Player) sender;
		if (!p.hasPermission("sgcraft.buff")) {
			p.sendMessage("§cVocê não tem permissão para isso!");
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("buff")) {
			econ = null;
			@SuppressWarnings("deprecation")
			EconomyResponse c = econ.withdrawPlayer(p.getName(), 30000);
			if (c.transactionSuccess()) {
				for (PotionEffect effect : p.getActivePotionEffects()) {
					p.removePotionEffect(effect.getType());
				}
				p.sendMessage("§aFoi deduzido 30k do seu money por usar o BUFF!");
				p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 4800, 1));
				p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 3600, 3));
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 4800, 1));
				p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 4800, 0));
				p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 3600, 0));
				Bukkit.broadcastMessage("§f[§6BUFF§f] §fO jogador " + p.getName() + "§f usou o /buff por 30k");
				return true;
			} else
				p.sendMessage("§Você não tem money suficiente!");
			return false;
		}
		return false;
	}
}
