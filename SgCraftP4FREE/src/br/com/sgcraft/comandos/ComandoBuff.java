package br.com.sgcraft.comandos;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ComandoBuff implements CommandExecutor {
	public HashMap<String, Long> cooldowns = new HashMap<String, Long>();

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("buff")) {
			if (!player.hasPermission("sgcraft.buff")) {
				player.sendMessage("§cVocê nao tem permissao para usar o BUFF!");
			} else {
				int cooldownTime = 20;
				if (cooldowns.containsKey(sender.getName())) {
					long secondsLeft = ((cooldowns.get(sender.getName()) / 1000) + cooldownTime)
							- (System.currentTimeMillis() / 1000);
					if (secondsLeft > 0) {
						sender.sendMessage("§cVocê precisa aguardar §c§l" + secondsLeft + " §csegundos para usar o Buff novamente!");
						return true;
					}
				}
				cooldowns.put(sender.getName(), System.currentTimeMillis());
				for (final PotionEffect effect : player.getActivePotionEffects()) {
					player.removePotionEffect(effect.getType());
				}
				player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 4800, 1));
				player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 3600, 4));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 4800, 1));
				player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 4800, 0));
				player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 3600, 0));
				player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 200, 0));
				Bukkit.broadcastMessage("§f[§6BUFF§f] §fO jogador " + player.getName() + "§f é §9§lVIP §fe usou o BUFF!");
			}
		}
		return false;
	}
}
