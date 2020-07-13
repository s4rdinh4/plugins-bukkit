package br.com.sgcraft.farmsautomaticas;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import br.com.sgcraft.farmsautomaticas.FarmsAutomaticasMain.verificacao;

public class FarmsAutomaticasEventos implements Listener {

	@EventHandler
	public void farm_cacto(InventoryClickEvent e) {
		if (e.getClickedInventory() != null) {
			if (e.getWhoClicked() instanceof Player) {
				Player player = (Player) e.getWhoClicked();
				if (e.getInventory().getTitle().equals("�8Comprar Farm")) {
					e.setCancelled(true);
					if (e.getCurrentItem().getType() == Material.CACTUS) {
						player.closeInventory();
						player.sendMessage("");
						player.sendMessage("  �f�m-------- �2�l FARM DE CACTO �f�m--------");
						player.sendMessage("");
						player.sendMessage("  �cAntes de continuar, tenha certeza de que:");
						player.sendMessage("�f 1- �aExista uma area 50x50 limpa ao seu redor.");
						player.sendMessage("�f 2- �aVoc� precisa ter �7R$25k �apara o terreno ser comprado.");
						player.sendMessage("�f 3- �aVoc� precisa ter �7R$10k �apara o pvp ser desativado.");
						player.sendMessage("�f 4- �aO terreno ser� criado com o nome: �2FCACTO�a.");
						player.sendMessage("");
						player.sendMessage("   �cDeseja continuar? Digite �f/cactocontinuar");
						player.sendMessage("");
						verificacao.continuar = 1;
					}
				}
			}
		} else {
			return;
		}
	}
}
