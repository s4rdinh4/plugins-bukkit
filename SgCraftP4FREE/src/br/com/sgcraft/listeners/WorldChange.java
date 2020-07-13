package br.com.sgcraft.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;

public class WorldChange implements Listener {
	@EventHandler
	public void PlayerWorldSpawn(final PlayerChangedWorldEvent e) {
		final Player player = e.getPlayer();
		if (player.getWorld().getName().equalsIgnoreCase("spawn") && !player.hasPermission("sgcraft.staff")) {
			player.setGameMode(GameMode.SURVIVAL);
			player.getInventory().clear();
            player.getInventory().setHelmet((ItemStack)null);
            player.getInventory().setChestplate((ItemStack)null);
            player.getInventory().setLeggings((ItemStack)null);
            player.getInventory().setBoots((ItemStack)null);
			player.sendMessage("§cATENÇÃO: Seu inventário foi §climpo §ce modo de jogo foi alterado pra §lSobrevivência!");
		}
	}
	
		@EventHandler
		public void PlayerWorldTerrenos(final PlayerChangedWorldEvent e) {
			final Player player = e.getPlayer();
			if (player.getWorld().getName().equalsIgnoreCase("TerrenosP4FREE") && !player.hasPermission("sgcraft.staff")) {
				player.setGameMode(GameMode.CREATIVE);
				player.getInventory().clear();
	            player.getInventory().setHelmet((ItemStack)null);
	            player.getInventory().setChestplate((ItemStack)null);
	            player.getInventory().setLeggings((ItemStack)null);
	            player.getInventory().setBoots((ItemStack)null);
				player.sendMessage("§cATENÇÃO: Seu inventário foi §climpo §ce modo de jogo foi alterado pra §lCriativo!");
			}
		}
}
