package br.com.sgcraft.listeners;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class BlockFunil implements Listener {
	@EventHandler
	public void quandoAbrirFunil(final InventoryOpenEvent e) {
		final Player p = (Player) e.getPlayer();
		final Inventory inv = e.getInventory();
		if (inv.getType() == InventoryType.HOPPER) {
			e.setCancelled(true);
			p.sendMessage("§cVoce nao pode abrir o Inventario do Funil!");
		}
	}
}