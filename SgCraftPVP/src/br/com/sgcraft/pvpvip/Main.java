package br.com.sgcraft.pvpvip;

import org.bukkit.plugin.java.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.logging.*;
import org.bukkit.plugin.*;
import java.io.*;
import java.util.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

public class Main extends JavaPlugin implements Listener
{
    File folder;
    File file;
    ArrayList<UUID> safePlayers;
    
    public Main() {
        this.folder = new File("plugins/PvPDisabler");
        this.file = new File("plugins/PvPDisabler/enabledplayers.txt");
        this.safePlayers = new ArrayList<UUID>();
    }
    
    public void onEnable() {
        try {
            if (!this.folder.exists() && !this.file.exists()) {
                this.folder.mkdirs();
                this.file.createNewFile();
            }
            else {
                try {
                    Throwable t = null;
                    try {
                        final BufferedReader reader = Files.newBufferedReader(Paths.get(this.file.getPath(), new String[0]), Charset.defaultCharset());
                        try {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                this.safePlayers.add(this.getServer().getOfflinePlayer(line).getUniqueId());
                            }
                        }
                        finally {
                            if (reader != null) {
                                reader.close();
                            }
                        }
                    }
                    finally {
                        if (t == null) {
                            final Throwable t2 = null;
                            t = t2;
                        }
                        else {
                            final Throwable t2 = null;
                            if (t != t2) {
                                t.addSuppressed(t2);
                            }
                        }
                    }
                }
                catch (Exception e) {
                    this.getLogger().log(Level.SEVERE, "Erro na leitura de " + this.file.getName() + ".", e);
                }
            }
        }
        catch (Exception e) {
            this.getLogger().log(Level.SEVERE, "Erro ao criar os arquivos", e);
        }
        this.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);
        this.getLogger().info(String.valueOf(this.getName()) + " Foi Inicializado com Sucesso!");
    }
    
    public void onDisable() {
        try {
            Throwable t = null;
            try {
                final FileWriter writer = new FileWriter(this.file);
                try {
                    for (final UUID uuid : this.safePlayers) {
                        writer.write(String.valueOf(uuid.toString()) + "\r\n");
                    }
                }
                finally {
                    if (writer != null) {
                        writer.close();
                    }
                }
            }
            finally {
                if (t == null) {
                    final Throwable t2 = null;
                    t = t2;
                }
                else {
                    final Throwable t2 = null;
                    if (t != t2) {
                        t.addSuppressed(t2);
                    }
                }
            }
        }
        catch (Exception e) {
            this.getLogger().log(Level.SEVERE, "Erro ao salvar aquivos de " + this.file.getName() + ".", e);
        }
        this.getLogger().info(String.valueOf(this.getName()) + " Foi desativado com sucesso.");
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!label.equalsIgnoreCase("pvp")) {
            return false;
        }
        final Player player = (Player)sender;
        if (args.length == 0) {
            return false;
        }
        if (args[0].equalsIgnoreCase("on")) {
        	if (player.hasPermission("sgcraft.modopvp")) {
            if (this.safePlayers.contains(player.getUniqueId())) {
                this.safePlayers.remove(player.getUniqueId());
                player.sendMessage("  �f>> �aAgora voce esta com o PVP �4ATIVADO!");
            }
            else {
                player.sendMessage("�cSeu PVP ja esta ATIVADO!");
            }
        }
        	else {
        		player.sendMessage("�cVoce nao tem permissao!");
        	}
        }
        else if (args[0].equalsIgnoreCase("off")) {
        	if (player.hasPermission("sgcraft.modopvp")) {
            if (!this.safePlayers.contains(player.getUniqueId())) {
                this.safePlayers.add(player.getUniqueId());
                player.sendMessage("  �f>> �aAgora voce esta com o PVP �4DESATIVADO!");
            }
            else {
                player.sendMessage("�cSeu PVP ja esta DESATIVADO!");
            }
        }
        	else {
        		player.sendMessage("�cVoce nao tem permissao!");
        	}
        }
        else {
            player.sendMessage("�cErro no comando. Use /pvp on ou /pvp off");
        }
        return true;
    }
    
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        if (this.safePlayers.contains(player.getUniqueId())) {
        	this.safePlayers.remove(player.getUniqueId());
        }
    }
    
    @EventHandler
    private void onCmd(final PlayerCommandPreprocessEvent e) {
    	final Player player = e.getPlayer();
		if ((e.getMessage().toLowerCase().startsWith("/pvp") || e.getMessage().toLowerCase().startsWith("/sgcraftpvp:pvp"))) {
			if(player.getWorld().getName().equalsIgnoreCase("spawn") || player.getWorld().getName().equalsIgnoreCase("eventos")){
			e.setCancelled(true);
			player.sendMessage("  �f>> �cVoce nao pode usar isso nesse mundo!!");
		}
		}
    }
    
    
	@EventHandler
	public void worldJoin(final PlayerChangedWorldEvent e) {
		final Player player = e.getPlayer();
		if (player.getWorld().getName().equalsIgnoreCase("spawn") || player.getWorld().getName().equalsIgnoreCase("eventos")) {
			this.safePlayers.remove(player.getUniqueId());
			player.sendMessage("  �f>> �aSeu PVP foi �4ATIVADO, �aporque trocou de mundo!");
		}
	}
    
    @EventHandler
    public void onPlayerHit(final EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            final Player damager = (Player)e.getDamager();
            final Player victim = (Player)e.getEntity();
            if (!this.safePlayers.contains(damager.getUniqueId()) && this.safePlayers.contains(victim.getUniqueId())) {
                damager.sendMessage("  �f>> �cO jogador � �6VIP �ce esta com o PVP �4DESATIVADO�c!");
                e.setCancelled(true);
            }
            else if (this.safePlayers.contains(damager.getUniqueId()) && !this.safePlayers.contains(victim.getUniqueId())) {
                damager.sendMessage("  �f>> �cVoce esta com o PVP �4DESATIVADO�c!");
                e.setCancelled(true);
            }
            else if (this.safePlayers.contains(damager.getUniqueId()) && this.safePlayers.contains(victim.getUniqueId())) {
                damager.sendMessage("  �f>> �cAmbos os jogadores estao com PVP &4DESATIVADO!");
                e.setCancelled(true);
            }
        }
    }
    
    
}
