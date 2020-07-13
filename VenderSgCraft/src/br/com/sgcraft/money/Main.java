package br.com.sgcraft.money;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.sgcraft.money.event.Menu;
import br.com.sgcraft.money.command.TemplateCommand;

public class Main extends net.eduard.api.EduardPlugin {
	private static Main plugin;
	public static  Main getInstance() {
		return plugin;
	}
	public static Main getPlugin() {
		return JavaPlugin.getPlugin(Main.class);
	}
	public void onEnable() {
		plugin = this;
		new Menu().register(this);
		new TemplateCommand().register();
		Bukkit.getConsoleSender().sendMessage("[VenderSgCraft] Plugin Iniciado");
	}
	
	public void save() {
		
	}
	public void reload() {
		// TODO Auto-generated method stub
		
	}

}