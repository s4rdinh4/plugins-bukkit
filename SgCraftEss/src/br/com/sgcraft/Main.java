package br.com.sgcraft;

import org.bukkit.plugin.java.*;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import java.util.*;
import org.bukkit.entity.*;
import net.milkbowl.vault.economy.*;
import br.com.sgcraft.listeners.*;
import org.bukkit.*;
import org.bukkit.plugin.*;
import org.bukkit.event.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.command.*;
import br.com.sgcraft.comandos.*;
import br.com.sgcraft.farmsautomaticas.ComandoConstruir;
import br.com.sgcraft.farmsautomaticas.ComandoContinuar;
import br.com.sgcraft.farmsautomaticas.FarmsAutomaticasEventos;
import br.com.sgcraft.farmsautomaticas.FarmsAutomaticasMain;
import br.com.sgcraft.vender.*;

public class Main extends JavaPlugin implements Listener {
	public static Main pl;
	public static String prefix;
	public static HashMap<Player, Player> open;
	public static String playerNotOnline;
	public static String use;
	public static String no_perm;
	public static int continuar;
	public static Economy econ;
	public static ArrayList<String> Admin;
	public static ArrayList<String> abririnv;
    public static ArrayList<String> mensagemknock;
    public static HashMap<String, ItemStack[]> saveinv;
    public static HashMap<String, ItemStack[]> armadura;

	
	static {
		Main.prefix = "�9[X9]";
		Main.open = new HashMap<Player, Player>();
		Main.playerNotOnline = "�c Esse Jogador esta Offline!";
		Main.use = " �aVoce abriu o invent\u00e1rio do jogador!";
		Main.no_perm = " �cVoce nao tem permissao para fazer isso!";
	}

	@SuppressWarnings("unused")
	public void onEnable() {
		final Server server = this.getServer();
		final PluginManager pm = Bukkit.getPluginManager();
		this.onComandos();
		this.setupWorldEdit();
		Bukkit.getConsoleSender().sendMessage("");
		Bukkit.getConsoleSender().sendMessage("�b[SgCraftEssential] �aATIVADO COM SUCESSO!");
		Bukkit.getConsoleSender().sendMessage("");
		this.saveDefaultConfig();
		this.reloadConfig();
		pm.registerEvents((Listener) new ListenerX9(), (Plugin) this);
		pm.registerEvents((Listener) new VipZeroFix(), (Plugin) this);
		pm.registerEvents((Listener) new JoinMessage(), (Plugin) this);
		pm.registerEvents((Listener) new BlockFunil(), (Plugin) this);
		pm.registerEvents((Listener) new BlockCmds(), (Plugin) this);
		pm.registerEvents((Listener) new FixBuff(), (Plugin) this);
		pm.registerEvents((Listener) new MobsFix(), (Plugin) this);
		pm.registerEvents((Listener) new SpawnerQuebrar(), (Plugin) this);
		pm.registerEvents((Listener) new FarmsAutomaticasEventos(), (Plugin) this);
		pm.registerEvents((Listener) new ComandoConstruir(), (Plugin) this);
		pm.registerEvents((Listener) new VenderMain(), (Plugin) this);
		pm.registerEvents((Listener) new VenderFarms(), (Plugin) this);
		pm.registerEvents((Listener) new VenderDrops(), (Plugin) this);
		Main.pl = this;
	}

	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("");
		Bukkit.getConsoleSender().sendMessage("�b[SgCraftEssential] �cDESATIVADO COM SUCESSO!");
		Bukkit.getConsoleSender().sendMessage("");
		HandlerList.unregisterAll((Plugin) this);
	}
	
	private void setupWorldEdit() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldEdit");
        if (plugin == null || !(plugin instanceof WorldEditPlugin))
            return;
    }
    
	private void onComandos() {
		this.getCommand("desencantar").setExecutor((CommandExecutor) new Desencantar());
		this.getCommand("anunciar").setExecutor((CommandExecutor) new Anunciar());
		this.getCommand("x9").setExecutor((CommandExecutor) new ComandoX9());
		this.getCommand("buff").setExecutor((CommandExecutor) new ComandoBuff());
        this.getCommand("vender").setExecutor((CommandExecutor) new VenderMain());
		this.getCommand("limparchat").setExecutor((CommandExecutor) new ComandoLimparChat());
		this.getCommand("farms").setExecutor((CommandExecutor) new FarmsAutomaticasMain());
		this.getCommand("cactocontinuar").setExecutor((CommandExecutor) new ComandoContinuar());
		this.getCommand("cactoconstruir").setExecutor((CommandExecutor) new ComandoConstruir());
	}	
}
