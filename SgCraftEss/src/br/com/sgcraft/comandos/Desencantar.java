package br.com.sgcraft.comandos;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import br.com.sgcraft.Main;

public class Desencantar implements CommandExecutor {
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
		if (sender instanceof org.bukkit.command.ConsoleCommandSender) {

			sender.sendMessage("§a[Desencantar] §cDesculpe, somente jogadores podem executar este comando!");

			return true;
		}

		if (cmd.getName().equalsIgnoreCase("desencantar")) {

			Player p = (Player) sender;

			ItemStack forItemInHand = p.getItemInHand();
			
			if (p.hasPermission("sgcraft.desencantar")) {
			if (forItemInHand.getType().equals(Material.AIR) || forItemInHand == null || forItemInHand.getTypeId() == 0
					|| !forItemInHand.getItemMeta().hasEnchants()) {

				p.sendMessage(Main.pl.getConfig().getString("Mensagens.Erro").replace("&", "§"));

				return true;
			}

			if (forItemInHand.getEnchantments().size() != 0) {
					forItemInHand.removeEnchantment(Enchantment.ARROW_DAMAGE);
					forItemInHand.removeEnchantment(Enchantment.ARROW_FIRE);
					forItemInHand.removeEnchantment(Enchantment.ARROW_INFINITE);
					forItemInHand.removeEnchantment(Enchantment.ARROW_KNOCKBACK);
					forItemInHand.removeEnchantment(Enchantment.DAMAGE_ALL);
					forItemInHand.removeEnchantment(Enchantment.DAMAGE_ARTHROPODS);
					forItemInHand.removeEnchantment(Enchantment.DAMAGE_UNDEAD);
					forItemInHand.removeEnchantment(Enchantment.DIG_SPEED);
					forItemInHand.removeEnchantment(Enchantment.DURABILITY);
					forItemInHand.removeEnchantment(Enchantment.FIRE_ASPECT);
					forItemInHand.removeEnchantment(Enchantment.KNOCKBACK);
					forItemInHand.removeEnchantment(Enchantment.LOOT_BONUS_BLOCKS);
					forItemInHand.removeEnchantment(Enchantment.LOOT_BONUS_MOBS);
					forItemInHand.removeEnchantment(Enchantment.OXYGEN);
					forItemInHand.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					forItemInHand.removeEnchantment(Enchantment.PROTECTION_EXPLOSIONS);
					forItemInHand.removeEnchantment(Enchantment.PROTECTION_FALL);
					forItemInHand.removeEnchantment(Enchantment.PROTECTION_FIRE);
					forItemInHand.removeEnchantment(Enchantment.PROTECTION_PROJECTILE);
					forItemInHand.removeEnchantment(Enchantment.SILK_TOUCH);
					forItemInHand.removeEnchantment(Enchantment.THORNS);
					forItemInHand.removeEnchantment(Enchantment.WATER_WORKER);
					forItemInHand.removeEnchantment(Enchantment.DEPTH_STRIDER);
					forItemInHand.removeEnchantment(Enchantment.LUCK);
					forItemInHand.removeEnchantment(Enchantment.LURE);
					p.sendMessage(Main.pl.getConfig().getString("Mensagens.Desencantado").replace("&", "§"));

					return true;
				}
			}
		else {
			p.sendMessage("§cVocê não te permissão para usar esse comando!");
		}
		}
		return false;
	}
}
