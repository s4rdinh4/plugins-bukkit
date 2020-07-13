package br.com.sgcraft.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class SpawnerQuebrar implements Listener {

	@EventHandler
	public void quebrar(final BlockBreakEvent e) {
		final Player p = e.getPlayer();
		final Block b = e.getBlock();
		if (b.getType() == Material.MOB_SPAWNER) {
			if (!p.hasPermission("bspawners.silk") && p.getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
				e.setExpToDrop(0);
				e.setCancelled(true);
				p.sendMessage(
						"§cVoc\u00ea n\u00e3o pode quebrar spawners. Para isso voc\u00ea precisar chamar um Admin, SubDiretor ou Diretor!");
			}
		}
	}
}
