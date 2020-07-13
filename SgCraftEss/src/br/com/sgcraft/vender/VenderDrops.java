package br.com.sgcraft.vender;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import br.com.sgcraft.VaultAPI;

public class VenderDrops implements Listener {

	@EventHandler
	public void OnSubControls(InventoryClickEvent e) {
		if (e.getClickedInventory() != null) {
			if (e.getWhoClicked() instanceof Player) {
				Player player = (Player) e.getWhoClicked();
				if (e.getInventory().getTitle().equals("§8Vendas - Drops de Mobs")) {
					e.setCancelled(true);
					if (e.getCurrentItem() == null) return;
					if (e.getCurrentItem().getType() == Material.AIR) return;
					if (e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
					ItemStack itemOpt = e.getCurrentItem();
					switch (itemOpt.getItemMeta().getDisplayName()) {
					case "§cVender Carne Podre":
						player.playSound(player.getLocation(), Sound.CLICK, 1f, 1f);
						PlayerInventory invCarnePodre = player.getInventory();
						double precoCarnePodre = 2.387152778;
						int contagemCarnePodre = 0;
						for (ItemStack item : invCarnePodre.getContents()) {
							if (item == null)
								continue;
							if (item.getType() == Material.ROTTEN_FLESH) {
								contagemCarnePodre = contagemCarnePodre + item.getAmount();
							}
						}
						if (contagemCarnePodre == 0) {
							player.sendMessage("§cVoce nao tem nenhuma Carne Podre para vender!");
							player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
						} else {
							invCarnePodre.remove(Material.ROTTEN_FLESH);
							double precoFinal = contagemCarnePodre * precoCarnePodre;
							VaultAPI.getEconomy().depositPlayer(player, precoFinal);
							BigDecimal precoRound = new BigDecimal(precoFinal).setScale(2, RoundingMode.HALF_EVEN);
							player.sendMessage("§fVoce vendeu " + contagemCarnePodre + " §aCarne Podre §fpor §aR$ "
									+ precoRound.doubleValue());
							player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
						}
						break;
					case "§fVender Osso":
						player.playSound(player.getLocation(), Sound.CLICK, 1f, 1f);
						PlayerInventory invOsso = player.getInventory();
						double precoOsso = 3.4722222222;
						int contagemOsso = 0;
						for (ItemStack item : invOsso.getContents()) {
							if (item == null)
								continue;
							if (item.getType() == Material.BONE) {
								contagemOsso = contagemOsso + item.getAmount();
							}
						}
						if (contagemOsso == 0) {
							player.sendMessage("§cVoce nao tem nenhum Osso para vender!");
							player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
						} else {
							invOsso.remove(Material.BONE);
							double precoFinal = contagemOsso * precoOsso;
							VaultAPI.getEconomy().depositPlayer(player, precoFinal);
							BigDecimal precoRound = new BigDecimal(precoFinal).setScale(2, RoundingMode.HALF_EVEN);
							player.sendMessage("§fVoce vendeu " + contagemOsso + " §aOsso §fpor §aR$ "
									+ precoRound.doubleValue());
							player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
						}
						break;
					case "§fVender Linha":
						player.playSound(player.getLocation(), Sound.CLICK, 1f, 1f);
						PlayerInventory invLinha = player.getInventory();
						double precoLinha = 4.3402777778;
						int contagemLinha = 0;
						for (ItemStack item : invLinha.getContents()) {
							if (item == null)
								continue;
							if (item.getType() == Material.STRING) {
								contagemLinha = contagemLinha + item.getAmount();
							}
						}
						if (contagemLinha == 0) {
							player.sendMessage("§cVoce nao tem nenhuma Linha para vender!");
							player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
						} else {
							invLinha.remove(Material.STRING);
							double precoFinal = contagemLinha * precoLinha;
							VaultAPI.getEconomy().depositPlayer(player, precoFinal);
							BigDecimal precoRound = new BigDecimal(precoFinal).setScale(2, RoundingMode.HALF_EVEN);
							player.sendMessage("§fVoce vendeu " + contagemLinha + " §aLinha §fpor §aR$ "
									+ precoRound.doubleValue());
							player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
						}
						break;
					case "§6Vender Cenoura":
						player.playSound(player.getLocation(), Sound.CLICK, 1f, 1f);
						PlayerInventory invCenoura = player.getInventory();
						double precoCenoura = 3.90625;
						int contagemCenoura = 0;
						for (ItemStack item : invCenoura.getContents()) {
							if (item == null)
								continue;
							if (item.getType() == Material.CARROT_ITEM) {
								contagemCenoura = contagemCenoura + item.getAmount();
							}
						}
						if (contagemCenoura == 0) {
							player.sendMessage("§cVoce nao tem nenhuma Cenoura para vender!");
							player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
						} else {
							invCenoura.remove(Material.CARROT_ITEM);
							double precoFinal = contagemCenoura * precoCenoura;
							VaultAPI.getEconomy().depositPlayer(player, precoFinal);
							BigDecimal precoRound = new BigDecimal(precoFinal).setScale(2, RoundingMode.HALF_EVEN);
							player.sendMessage("§fVoce vendeu " + contagemCenoura + " §aCenoura §fpor §aR$ "
									+ precoRound.doubleValue());
							player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
						}
						break;
					case "§3Vender Prismarinho":
						player.playSound(player.getLocation(), Sound.CLICK, 1f, 1f);
						PlayerInventory invPrismarinho = player.getInventory();
						double precoPrismarinho = 3.4722222222;
						int contagemPrismarinho = 0;
						for (ItemStack item : invPrismarinho.getContents()) {
							if (item == null)
								continue;
							if (item.getType() == Material.PRISMARINE_SHARD) {
								contagemPrismarinho = contagemPrismarinho + item.getAmount();
							}
						}
						if (contagemPrismarinho == 0) {
							player.sendMessage("§cVoce nao tem nenhum Prismarinho para vender!");
							player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
						} else {
							invPrismarinho.remove(Material.PRISMARINE_SHARD);
							double precoFinal = contagemPrismarinho * precoPrismarinho;
							VaultAPI.getEconomy().depositPlayer(player, precoFinal);
							BigDecimal precoRound = new BigDecimal(precoFinal).setScale(2, RoundingMode.HALF_EVEN);
							player.sendMessage("§fVoce vendeu " + contagemPrismarinho + " §aPrismarinho §fpor §aR$ "
									+ precoRound.doubleValue());
							player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
						}
						break;
					case "§aVender Slime":
						player.playSound(player.getLocation(), Sound.CLICK, 1f, 1f);
						PlayerInventory invSlime = player.getInventory();
						double precoSlime = 15.1909722222;
						int contagemSlime = 0;
						for (ItemStack item : invSlime.getContents()) {
							if (item == null)
								continue;
							if (item.getType() == Material.SLIME_BALL) {
								contagemSlime = contagemSlime + item.getAmount();
							}
						}
						if (contagemSlime == 0) {
							player.sendMessage("§cVoce nao tem nenhuma Gosma de Slime para vender!");
							player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
						} else {
							invSlime.remove(Material.SLIME_BALL);
							double precoFinal = contagemSlime * precoSlime;
							VaultAPI.getEconomy().depositPlayer(player, precoFinal);
							BigDecimal precoRound = new BigDecimal(precoFinal).setScale(2, RoundingMode.HALF_EVEN);
							player.sendMessage("§fVoce vendeu " + contagemSlime + " §aGosma de Slime §fpor §aR$ "
									+ precoRound.doubleValue());
							player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
						}
						break;
					case "§6Vender Pepita de Ouro":
						player.playSound(player.getLocation(), Sound.CLICK, 1f, 1f);
						PlayerInventory invPepita = player.getInventory();
						double precoPepita = 21.70138888888889;
						int contagemPepita = 0;
						for (ItemStack item : invPepita.getContents()) {
							if (item == null)
								continue;
							if (item.getType() == Material.GOLD_NUGGET) {
								contagemPepita = contagemPepita + item.getAmount();
							}
						}
						if (contagemPepita == 0) {
							player.sendMessage("§cVoce nao tem nenhum Pepita de Ouro para vender!");
							player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
						} else {
							invPepita.remove(Material.GOLD_NUGGET);
							double precoFinal = contagemPepita * precoPepita;
							VaultAPI.getEconomy().depositPlayer(player, precoFinal);
							BigDecimal precoRound = new BigDecimal(precoFinal).setScale(2, RoundingMode.HALF_EVEN);
							player.sendMessage("§fVoce vendeu " + contagemPepita + " §aPepita de Ouro §fpor §aR$ "
									+ precoRound.doubleValue());
							player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
						}
						break;
					case "§3Voltar para Menu Principal":
						player.playSound(player.getLocation(), Sound.CLICK, 1f, 1f);
						player.openInventory(VenderMain.vender);
						break;
					case "§cFechar Menu":
						player.playSound(player.getLocation(), Sound.CLICK, 1f, 1f);
						player.closeInventory();
						break;
					default:
						return;
					}
				}
			}
		} else {
			return;
		}
	}
}
