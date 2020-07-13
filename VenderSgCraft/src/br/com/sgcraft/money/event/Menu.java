package br.com.sgcraft.money.event;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import br.com.sgcraft.money.Main;
import net.eduard.api.setup.Mine;
import net.eduard.api.setup.VaultAPI;
import net.eduard.api.setup.manager.TimeManager;

public class Menu extends TimeManager {

	@EventHandler
	public void event(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getItem() == null)
			return;
		if (e.getItem().getType() == Material.DIAMOND) {

			abrirMenu(p);
			e.setCancelled(true);
		}

	}

	public static void abrirMenu(Player p) {
		Inventory inv = Bukkit.createInventory(null, 9 * 3, "§8Menu de Vendas");
		{
			ItemStack item = Mine.newItem(Material.LAPIS_ORE, "§bLápis-Lazuli", 1,
					0,

					" §7Clique aqui para vender seus Lápis.",
					" §bInventário por R$ 5000.");

			inv.setItem(Mine.getPosition(2, 3), item);
		}
		{
			ItemStack item = Mine.newItem(Material.WHEAT, "§eItens de Farm", 1,
					0,

					"§7Clique para abrir o menu de itens de farm.");

			inv.setItem(Mine.getPosition(2, 5), item);
		}
		{
			ItemStack item = Mine.newItem(Material.BONE, "§eDrops de Mobs", 1,
					0,

					"§7Clique para abrir o menu de drops.");

			inv.setItem(Mine.getPosition(2, 7), item);
		}
		
		p.openInventory(inv);
	}
	@EventHandler
	public void event(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getInventory().getTitle().equals("§8Menu de Vendas")) {
				e.setCancelled(true);
				if (e.getRawSlot() == Mine.getPosition(2, 3)) {
					int priceSell = 2;
					if (e.getClick() == ClickType.LEFT) {

						if (Mine.contains(p.getInventory(),
								new ItemStack(Material.INK_SACK, 1, (short) 4), 1)) {
							Mine.remove(p.getInventory(),
									new ItemStack(Material.INK_SACK, 1, (short) 4));
							
							VaultAPI.getEconomy().depositPlayer(p, priceSell);
							p.sendMessage("§aVoce vendeu!");
							

						} else {
							p.sendMessage("§cVoce nao tem dinheiro!");
						}

					} else if (e.getClick() == ClickType.LEFT) {

						if (Mine.contains(p.getInventory(),
								new ItemStack(Material.IRON_INGOT), 2304)) {
							Mine.remove(p.getInventory(),
									new ItemStack(Material.IRON_INGOT), 1);
							
							VaultAPI.getEconomy().depositPlayer(p, priceSell);
							p.sendMessage("§aVoce vendeu!");
							
						}else
						{
							p.sendMessage("§cVoce nao tem este item no inventario!");
						}

					}
				}
			}

		}
	}


}