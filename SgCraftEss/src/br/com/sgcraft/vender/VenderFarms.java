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

public class VenderFarms implements Listener {

	@EventHandler
	public void OnSubControls(InventoryClickEvent e) {
		if (e.getClickedInventory() != null) {
			if (e.getWhoClicked() instanceof Player) {
				Player player = (Player) e.getWhoClicked();
				if (e.getInventory().getTitle().equals("�8Vendas - Itens de Farm")) {
					e.setCancelled(true);
					if (e.getCurrentItem() == null) return;
					if (e.getCurrentItem().getType() == Material.AIR) return;
					if (e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
					ItemStack itemOpt = e.getCurrentItem();
					switch (itemOpt.getItemMeta().getDisplayName()) {
					case "�cVender Fungo":
						player.playSound(player.getLocation(), Sound.CLICK, 1f, 1f);
						PlayerInventory invFungo = player.getInventory();
						double precoFungo = 2.8211805556;
						int contagemFungo = 0;
						for (ItemStack item : invFungo.getContents()) {
							if (item == null)
								continue;
							if (item.getType() == Material.NETHER_STALK) {
								contagemFungo = contagemFungo + item.getAmount();
							}
						}
						if (contagemFungo == 0) {
							player.sendMessage("�cVoce nao tem nenhum Fungo do Nether para vender!");
							player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
						} else {
							invFungo.remove(Material.NETHER_STALK);
							double precoFinal = contagemFungo * precoFungo;
							VaultAPI.getEconomy().depositPlayer(player, precoFinal);
							BigDecimal precoRound = new BigDecimal(precoFinal).setScale(2, RoundingMode.HALF_EVEN);
							player.sendMessage("�fVoce vendeu " + contagemFungo + " �aFungos do Nether �fpor �aR$ "
									+ precoRound.doubleValue());
							player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
						}
						break;
					case "�aVender Cana-de-A�ucar":
						player.playSound(player.getLocation(), Sound.CLICK, 1f, 1f);
						PlayerInventory invCana = player.getInventory();
						double precoCana = 3.90625;
						int contagemCana = 0;
						for (ItemStack item : invCana.getContents()) {
							if (item == null)
								continue;
							if (item.getType() == Material.SUGAR_CANE) {
								contagemCana = contagemCana + item.getAmount();
							}
						}
						if (contagemCana == 0) {
							player.sendMessage("�cVoce nao tem nenhuma Cana de A�ucar para vender!");
							player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
						} else {
							invCana.remove(Material.SUGAR_CANE);
							double precoFinal = contagemCana * precoCana;
							VaultAPI.getEconomy().depositPlayer(player, precoFinal);
							BigDecimal precoRound = new BigDecimal(precoFinal).setScale(2, RoundingMode.HALF_EVEN);
							player.sendMessage("�fVoce vendeu " + contagemCana + " �aCana de A�ucar �fpor �aR$ "
									+ precoRound.doubleValue());
							player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
						}
						break;
					case "�6Vender Cenoura":
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
							player.sendMessage("�cVoce nao tem nenhuma Cenoura para vender!");
							player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
						} else {
							invCenoura.remove(Material.CARROT_ITEM);
							double precoFinal = contagemCenoura * precoCenoura;
							VaultAPI.getEconomy().depositPlayer(player, precoFinal);
							BigDecimal precoRound = new BigDecimal(precoFinal).setScale(2, RoundingMode.HALF_EVEN);
							player.sendMessage("�fVoce vendeu " + contagemCenoura + " �aCenoura �fpor �aR$ "
									+ precoRound.doubleValue());
							player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
						}
						break;
					case "�eVender Batata":
						player.playSound(player.getLocation(), Sound.CLICK, 1f, 1f);
						PlayerInventory invBatata = player.getInventory();
						double precoBatata = 3.90625;
						int contagemBatata = 0;
						for (ItemStack item : invBatata.getContents()) {
							if (item == null)
								continue;
							if (item.getType() == Material.POTATO_ITEM) {
								contagemBatata = contagemBatata + item.getAmount();
							}
						}
						if (contagemBatata == 0) {
							player.sendMessage("�cVoce nao tem nenhuma Batata para vender!");
							player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
						} else {
							invBatata.remove(Material.POTATO_ITEM);
							double precoFinal = contagemBatata * precoBatata;
							VaultAPI.getEconomy().depositPlayer(player, precoFinal);
							BigDecimal precoRound = new BigDecimal(precoFinal).setScale(2, RoundingMode.HALF_EVEN);
							player.sendMessage("�fVoce vendeu " + contagemBatata + " �aBatata �fpor �aR$ "
									+ precoRound.doubleValue());
							player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
						}
						break;
					case "�6Vender Abobora":
						player.playSound(player.getLocation(), Sound.CLICK, 1f, 1f);
						PlayerInventory invAbobora = player.getInventory();
						double precoAbobora = 3.90625;
						int contagemAbobora = 0;
						for (ItemStack item : invAbobora.getContents()) {
							if (item == null)
								continue;
							if (item.getType() == Material.PUMPKIN) {
								contagemAbobora = contagemAbobora + item.getAmount();
							}
						}
						if (contagemAbobora == 0) {
							player.sendMessage("�cVoce nao tem nenhuma Abobora para vender!");
							player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
						} else {
							invAbobora.remove(Material.PUMPKIN);
							double precoFinal = contagemAbobora * precoAbobora;
							VaultAPI.getEconomy().depositPlayer(player, precoFinal);
							BigDecimal precoRound = new BigDecimal(precoFinal).setScale(2, RoundingMode.HALF_EVEN);
							player.sendMessage("�fVoce vendeu " + contagemAbobora + " �aAbobora �fpor �aR$ "
									+ precoRound.doubleValue());
							player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
						}
						break;
					case "�cVender Melancia":
						player.playSound(player.getLocation(), Sound.CLICK, 1f, 1f);
						PlayerInventory invMelancia = player.getInventory();
						double precoMelancia = 3.90625;
						int contagemMelancia = 0;
						for (ItemStack item : invMelancia.getContents()) {
							if (item == null)
								continue;
							if (item.getType() == Material.MELON_BLOCK) {
								contagemMelancia = contagemMelancia + item.getAmount();
							}
						}
						if (contagemMelancia == 0) {
							player.sendMessage("�cVoce nao tem nenhuma Melancia para vender!");
							player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
						} else {
							invMelancia.remove(Material.MELON_BLOCK);
							double precoFinal = contagemMelancia * precoMelancia;
							VaultAPI.getEconomy().depositPlayer(player, precoFinal);
							BigDecimal precoRound = new BigDecimal(precoFinal).setScale(2, RoundingMode.HALF_EVEN);
							player.sendMessage("�fVoce vendeu " + contagemMelancia + " �aMelancia �fpor �aR$ "
									+ precoRound.doubleValue());
							player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
						}
						break;
					case "�2Vender cacto":
						player.playSound(player.getLocation(), Sound.CLICK, 1f, 1f);
						PlayerInventory invCacto = player.getInventory();
						double precoCacto = 3.90625;
						int contagemCacto = 0;
						for (ItemStack item : invCacto.getContents()) {
							if (item == null)
								continue;
							if (item.getType() == Material.CACTUS) {
								contagemCacto = contagemCacto + item.getAmount();
							}
						}
						if (contagemCacto == 0) {
							player.sendMessage("�cVoce nao tem nenhum Cacto para vender!");
							player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
						} else {
							invCacto.remove(Material.CACTUS);
							double precoFinal = contagemCacto * precoCacto;
							VaultAPI.getEconomy().depositPlayer(player, precoFinal);
							BigDecimal precoRound = new BigDecimal(precoFinal).setScale(2, RoundingMode.HALF_EVEN);
							player.sendMessage("�fVoce vendeu " + contagemCacto + " �aCacto �fpor �aR$ "
									+ precoRound.doubleValue());
							player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
						}
						break;
					case "�3Voltar para Menu Principal":
						player.playSound(player.getLocation(), Sound.CLICK, 1f, 1f);
						player.openInventory(VenderMain.vender);
						break;
					case "�cFechar Menu":
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
