package br.com.sgcraft;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {

	public void OnEnable() {
		getCommand("buff").setExecutor(new ComandoBuff());
		Bukkit.getConsoleSender().sendMessage("[SgCraftEssentials] Plugin Carregado");
		setupEconomy();
	}

	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("[SgCraftEssentials] Plugin Encerrado");
	}

	private boolean setupEconomy() {
		if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null)
			return false;
		RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null)
			return false;
		Economy econ = (Economy) rsp.getProvider();
		return (econ != null);
	}

	public class ComandoMc implements CommandExecutor {
		public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Somente para jogadores");
				return true;
			}
			if (cmd.getName().equalsIgnoreCase("mc")) {
				Player p = (Player) sender;
				if (!p.hasPermission("sgcraft.mc.voar")) {
					p.sendMessage("jogadores VIPs podem usar o §f/mc§c.");
				} else if (p.getAllowFlight()) {
					p.setAllowFlight(false);
					p.sendMessage("§cTapete magico §4DESATIVADO§c!");
					p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 80, 100));
				} else {
					p.setAllowFlight(true);
					p.sendMessage("§aTapete magico §2ATIVADO§c!");
					return true;
				}
			}
			return false;
		}
	}
}
