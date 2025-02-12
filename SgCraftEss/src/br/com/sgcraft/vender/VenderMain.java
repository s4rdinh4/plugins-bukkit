package br.com.sgcraft.vender;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import br.com.sgcraft.VaultAPI;

public class VenderMain implements CommandExecutor, Listener {

	public static Inventory vender = Bukkit.createInventory((InventoryHolder) null, 27, "�8Vendas - Menu Principal");
	static {
		final ItemStack item_lapis = new ItemStack(Material.LAPIS_ORE, 1);
		final ItemMeta lapis = (ItemMeta) item_lapis.getItemMeta();
		lapis.setDisplayName("�9Vender L�pis");
		ArrayList<String> lapis_lore = new ArrayList<>();
		lapis_lore.add("�eVenda L�pis Lazulli");
		lapis.setLore(lapis_lore);
		item_lapis.setItemMeta((ItemMeta) lapis);
		vender.setItem(11, item_lapis);

		final ItemStack item_farms = new ItemStack(Material.WHEAT, 1);
		final ItemMeta farms = (ItemMeta) item_lapis.getItemMeta();
		farms.setDisplayName("�6Menu de Farms");
		ArrayList<String> farms_lore = new ArrayList<>();
		farms_lore.add("�eVender Itens de Farm");
		farms.setLore(farms_lore);
		item_farms.setItemMeta((ItemMeta) farms);
		vender.setItem(13, item_farms);

		final ItemStack item_drops = new ItemStack(Material.BONE, 1);
		final ItemMeta drops = (ItemMeta) item_lapis.getItemMeta();
		drops.setDisplayName("�7Menu de Drops");
		ArrayList<String> drops_lore = new ArrayList<>();
		drops_lore.add("�eVender Drops de Mobs");
		drops.setLore(drops_lore);
		item_drops.setItemMeta((ItemMeta) drops);
		vender.setItem(15, item_drops);
	}

	public static Inventory menu_farms = Bukkit.createInventory((InventoryHolder) null, 45, "�8Vendas - Itens de Farm");
	static {
		// fungo
		final ItemStack item_fungo = new ItemStack(Material.NETHER_STALK, 1);
		final ItemMeta fungo = (ItemMeta) item_fungo.getItemMeta();
		fungo.setDisplayName("�cVender Fungo");
		ArrayList<String> fungo_lore = new ArrayList<>();
		fungo_lore.add("�eVenda Fungo");
		fungo.setLore(fungo_lore);
		item_fungo.setItemMeta((ItemMeta) fungo);
		menu_farms.setItem(10, item_fungo);

		// Cana_de_a�ucar
		final ItemStack item_cana = new ItemStack(Material.SUGAR_CANE, 1);
		final ItemMeta cana = (ItemMeta) item_cana.getItemMeta();
		cana.setDisplayName("�aVender Cana-de-A�ucar");
		ArrayList<String> cana_lore = new ArrayList<>();
		cana_lore.add("�eVenda Cana-de-A�ucar");
		cana.setLore(cana_lore);
		item_cana.setItemMeta((ItemMeta) cana);
		menu_farms.setItem(11, item_cana);

		// cenoura
		final ItemStack item_cenoura = new ItemStack(Material.CARROT_ITEM, 1);
		final ItemMeta cenoura = (ItemMeta) item_cenoura.getItemMeta();
		cenoura.setDisplayName("�6Vender Cenoura");
		ArrayList<String> cenoura_lore = new ArrayList<>();
		cenoura_lore.add("�eVenda Cenoura");
		cenoura.setLore(cenoura_lore);
		item_cenoura.setItemMeta((ItemMeta) cenoura);
		menu_farms.setItem(12, item_cenoura);

		// batata
		final ItemStack item_batata = new ItemStack(Material.POTATO_ITEM, 1);
		final ItemMeta batata = (ItemMeta) item_batata.getItemMeta();
		batata.setDisplayName("�eVender Batata");
		ArrayList<String> batata_lore = new ArrayList<>();
		batata_lore.add("�fVenda Batata");
		batata.setLore(batata_lore);
		item_batata.setItemMeta((ItemMeta) batata);
		menu_farms.setItem(13, item_batata);

		// abobora
		final ItemStack item_abobora = new ItemStack(Material.PUMPKIN, 1);
		final ItemMeta abobora = (ItemMeta) item_abobora.getItemMeta();
		abobora.setDisplayName("�6Vender Abobora");
		ArrayList<String> abobora_lore = new ArrayList<>();
		abobora_lore.add("�eVenda Abobora");
		abobora.setLore(abobora_lore);
		item_abobora.setItemMeta((ItemMeta) abobora);
		menu_farms.setItem(14, item_abobora);

		// melancia
		final ItemStack item_melancia = new ItemStack(Material.MELON_BLOCK, 1);
		final ItemMeta melancia = (ItemMeta) item_melancia.getItemMeta();
		melancia.setDisplayName("�cVender Melancia");
		ArrayList<String> melancia_lore = new ArrayList<>();
		melancia_lore.add("�eVenda Melancia");
		melancia.setLore(melancia_lore);
		item_melancia.setItemMeta((ItemMeta) melancia);
		menu_farms.setItem(15, item_melancia);

		// cacto
		final ItemStack item_cacto = new ItemStack(Material.CACTUS, 1);
		final ItemMeta cacto = (ItemMeta) item_cacto.getItemMeta();
		cacto.setDisplayName("�2Vender cacto");
		ArrayList<String> cacto_lore = new ArrayList<>();
		cacto_lore.add("�eVenda cacto");
		cacto.setLore(cacto_lore);
		item_cacto.setItemMeta((ItemMeta) cacto);
		menu_farms.setItem(16, item_cacto);

		// voltar
		final ItemStack item_voltar = new ItemStack(Material.ARROW, 1);
		final ItemMeta voltar = (ItemMeta) item_voltar.getItemMeta();
		voltar.setDisplayName("�3Voltar para Menu Principal");
		item_voltar.setItemMeta((ItemMeta) voltar);
		menu_farms.setItem(29, item_voltar);

		// fechar
		final ItemStack item_fechar = new ItemStack(Material.BARRIER, 1);
		final ItemMeta fechar = (ItemMeta) item_fechar.getItemMeta();
		fechar.setDisplayName("�cFechar Menu");
		item_fechar.setItemMeta((ItemMeta) fechar);
		menu_farms.setItem(33, item_fechar);
	}
	
	public static Inventory menu_drops = Bukkit.createInventory((InventoryHolder) null, 45, "�8Vendas - Drops de Mobs");
	static {
		//carne_podre
		final ItemStack item_carnePodre = new ItemStack(Material.ROTTEN_FLESH, 1);
		final ItemMeta carnePodre = (ItemMeta) item_carnePodre.getItemMeta();
		carnePodre.setDisplayName("�cVender Carne Podre");
		ArrayList<String> carnePodre_lore = new ArrayList<>();
		carnePodre_lore.add("�eVenda Carne Podre");
		carnePodre.setLore(carnePodre_lore);
		item_carnePodre.setItemMeta((ItemMeta) carnePodre);
		menu_drops.setItem(10, item_carnePodre);

		//osso
		final ItemStack item_osso = new ItemStack(Material.BONE, 1);
		final ItemMeta osso = (ItemMeta) item_osso.getItemMeta();
		osso.setDisplayName("�fVender Osso");
		ArrayList<String> osso_lore = new ArrayList<>();
		osso_lore.add("�eVenda Osso");
		osso.setLore(osso_lore);
		item_osso.setItemMeta((ItemMeta) osso);
		menu_drops.setItem(11, item_osso);

		//linha
		final ItemStack item_linha = new ItemStack(Material.STRING, 1);
		final ItemMeta linha = (ItemMeta) item_linha.getItemMeta();
		linha.setDisplayName("�fVender Linha");
		ArrayList<String> linha_lore = new ArrayList<>();
		linha_lore.add("�eVenda Linha");
		linha.setLore(linha_lore);
		item_linha.setItemMeta((ItemMeta) linha);
		menu_drops.setItem(12, item_linha);

		//cenoura
		final ItemStack item_cenoura = new ItemStack(Material.CARROT_ITEM, 1);
		final ItemMeta cenoura = (ItemMeta) item_cenoura.getItemMeta();
		cenoura.setDisplayName("�6Vender Cenoura");
		ArrayList<String> cenoura_lore = new ArrayList<>();
		cenoura_lore.add("�eVenda Cenoura");
		cenoura.setLore(cenoura_lore);
		item_cenoura.setItemMeta((ItemMeta) cenoura);
		menu_drops.setItem(13, item_cenoura);

		//prismarinho
		final ItemStack item_prismarinho = new ItemStack(Material.PRISMARINE_SHARD, 1);
		final ItemMeta prismarinho = (ItemMeta) item_prismarinho.getItemMeta();
		prismarinho.setDisplayName("�3Vender Prismarinho");
		ArrayList<String> prismarinho_lore = new ArrayList<>();
		prismarinho_lore.add("�eVenda Prismarinho");
		prismarinho.setLore(prismarinho_lore);
		item_prismarinho.setItemMeta((ItemMeta) prismarinho);
		menu_drops.setItem(14, item_prismarinho);

		//slime
		final ItemStack item_slime = new ItemStack(Material.SLIME_BALL, 1);
		final ItemMeta slime = (ItemMeta) item_slime.getItemMeta();
		slime.setDisplayName("�aVender Slime");
		ArrayList<String> slime_lore = new ArrayList<>();
		slime_lore.add("�eVenda Slime");
		slime.setLore(slime_lore);
		item_slime.setItemMeta((ItemMeta) slime);
		menu_drops.setItem(15, item_slime);

		//pepita
		final ItemStack item_pepita = new ItemStack(Material.GOLD_NUGGET, 1);
		final ItemMeta pepita = (ItemMeta) item_pepita.getItemMeta();
		pepita.setDisplayName("�6Vender Pepita de Ouro");
		ArrayList<String> pepita_lore = new ArrayList<>();
		pepita_lore.add("�eVenda Pepita de Ouro");
		pepita.setLore(pepita_lore);
		item_pepita.setItemMeta((ItemMeta) pepita);
		menu_drops.setItem(16, item_pepita);

		//voltar
		final ItemStack item_voltar = new ItemStack(Material.ARROW, 1);
		final ItemMeta voltar = (ItemMeta) item_voltar.getItemMeta();
		voltar.setDisplayName("�3Voltar para Menu Principal");
		item_voltar.setItemMeta((ItemMeta) voltar);
		menu_drops.setItem(29, item_voltar);

		//fechar
		final ItemStack item_fechar = new ItemStack(Material.BARRIER, 1);
		final ItemMeta fechar = (ItemMeta) item_fechar.getItemMeta();
		fechar.setDisplayName("�cFechar Menu");
		item_fechar.setItemMeta((ItemMeta) fechar);
		menu_drops.setItem(33, item_fechar);
	} 

	//comando vender
	public boolean onCommand(CommandSender sender, Command cmd, String comando, String[] args) {
		if (!(sender instanceof Player)) {

			Bukkit.getConsoleSender().sendMessage("� Este comando esta desativado no console!");
			return false;
		}
		comando.equalsIgnoreCase("vender");
		Player p = (Player) sender;
		if (p.hasPermission("sgcraft.vender")) {
			p.openInventory(vender);
		} else {
			p.sendMessage("�bApenas jogadores �6�lVIP �bpodem utilizar o /vender!");
		}
		return false;
	}

	@EventHandler
	public void OnVenderLapis(InventoryClickEvent e) {
		if (e.getClickedInventory() != null) {
			if (e.getWhoClicked() instanceof Player) {
				Player player = (Player) e.getWhoClicked();
				if (e.getInventory().getTitle().equals("�8Vendas - Menu Principal")) {
					e.setCancelled(true);
					if (e.getCurrentItem().getType() == Material.LAPIS_ORE) {
						player.playSound(player.getLocation(), Sound.CLICK, 1f, 1f);
						double precoLapis = 1.3392857143;
						int contagem = 0;
						PlayerInventory inv = player.getInventory();
						for (ItemStack item : inv.getContents()) {
							ItemStack inkSack = new ItemStack(Material.INK_SACK, 1, (byte) 4);
							Material matInkSack = inkSack.getType();
							if (item == null)
								continue;
							if (item.getType() == matInkSack && item.getDurability() == 4) {
								contagem = contagem + item.getAmount();
							}
						}
						if (contagem == 0) {
							player.sendMessage("�cVoce nao tem nenhum Lapis Lazulli para vender!");
							player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
						} else {

							ItemStack lapis = new ItemStack(Material.INK_SACK, contagem, (byte) 4);
							inv.removeItem(lapis);
							double precoFinal = contagem * precoLapis;
							VaultAPI.getEconomy().depositPlayer(player, precoFinal);
							BigDecimal precoRound = new BigDecimal(precoFinal).setScale(2, RoundingMode.HALF_EVEN);
							player.sendMessage("�fVoce vendeu " + contagem + " Lapis Lazulli �fpor �aR$ "
									+ precoRound.doubleValue());
							player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
						}
					} else if (e.getCurrentItem() == null) {
						return;
					}
				}
			}
		} else {
			return;
		}
	}

	@EventHandler
	public void OnMenuOpcoes(InventoryClickEvent e) {
		if (e.getClickedInventory() != null) {
			if (e.getWhoClicked() instanceof Player) {
				Player player = (Player) e.getWhoClicked();
				if (e.getInventory().getTitle().equals("�8Vendas - Menu Principal")) {
					e.setCancelled(true);
					if (e.getCurrentItem().getType() == Material.WHEAT) {
						player.playSound(player.getLocation(), Sound.CLICK, 1f, 1f);
						player.openInventory(menu_farms);
					} else if (e.getCurrentItem().getType() == Material.BONE) {
						player.playSound(player.getLocation(), Sound.CLICK, 1f, 1f);
						player.openInventory(menu_drops);
					}
				}
			}
		} else {
			return;
		}
	}
}