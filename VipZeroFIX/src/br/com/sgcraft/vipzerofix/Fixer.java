package br.com.sgcraft.vipzerofix;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Fixer extends JavaPlugin implements Listener {
	public void onEnable() {
		getLogger().info("VipZeroFIX (V2.0) - Autor: Sardinhagamer_HD");
		getServer().getPluginManager().registerEvents(this, (Plugin) this);
	}

	public void onDisable() {
		getLogger().info("VipZeroFIX - Autor: Sardinhagamer_HD");
	}

	@EventHandler
	private void onCmd(PlayerCommandPreprocessEvent bugvip) {
		if ((bugvip.getMessage().startsWith("/usekey") || bugvip.getMessage().startsWith("/usarkey"))
				&& bugvip.getMessage().contains("'"))
			bugvip.setCancelled(true);
		if ((bugvip.getMessage().startsWith("/usekey") || bugvip.getMessage().startsWith("/usarkey"))
				&& bugvip.getMessage().contains("'0'"))
			bugvip.setCancelled(true);
		if ((bugvip.getMessage().startsWith("/usekey") || bugvip.getMessage().startsWith("/usarkey"))
				&& bugvip.getMessage().contains("'-'"))
			bugvip.setCancelled(true);
	}

}
