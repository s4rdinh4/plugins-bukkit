package br.com.sgcraft.farmsautomaticas;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FarmsAutomaticasMain implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (sender instanceof org.bukkit.command.ConsoleCommandSender) {

			sender.sendMessage("§cDesculpe, somente jogadores podem executar este comando!");

			return true;
		}
		if (cmd.getName().equalsIgnoreCase("farms")) {
			// criar menu
			if (p.hasPermission("sgcraft.farms.menu")) {
				Inventory farms = Bukkit.createInventory((InventoryHolder) null, 27, "§8Comprar Farm");
				final ItemStack farm_cacto = new ItemStack(Material.CACTUS, 1);
				final ItemMeta cacto = (ItemMeta) farm_cacto.getItemMeta();
				cacto.setDisplayName("§2§lFarm de Cacto");
				ArrayList<String> cacto_lore = new ArrayList<>();
				cacto_lore.add("§aConstrua sua Farm de Cacto Automagicamente, ");
				cacto_lore.add("§acom apenas um Click!");
				cacto_lore.add("");
				cacto_lore.add("§9Para construir sua Farm sera cobrado:");
				cacto_lore.add("§f- §725k  §cPara comprar o terreno.");
				cacto_lore.add("§f- §710k  §cPara desativar o PVP.");
				cacto_lore.add("§f- §7650k §cPara construir a Farm.");
				cacto_lore.add("§f- §7UM §cterreno do seu limite.");
				cacto.setLore(cacto_lore);
				farm_cacto.setItemMeta((ItemMeta) cacto);
				farms.setItem(13, farm_cacto);
				p.openInventory(farms);
			} else {
				p.sendMessage(" §f>> §cVoce nao tem permissao!");
			}
		}
		return false;
	}
	public static class verificacao {
		 
		public static Integer continuar = 0;
		public static Integer construir = 0;
	}
}
