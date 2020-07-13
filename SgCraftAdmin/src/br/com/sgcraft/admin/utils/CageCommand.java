package br.com.sgcraft.admin.utils;

import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.command.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

import br.com.sgcraft.admin.*;

public class CageCommand implements CommandExecutor
{
    public static ArrayList<Player> preendido;
    private String language;
    
    static {
        CageCommand.preendido = new ArrayList<Player>();
    }
    
    public CageCommand() {
        this.language = Main.plugin.getConfig().getString("Language");
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            if (this.language.equalsIgnoreCase("en_US")) {
                sender.sendMessage("§cConsole Can not Use Command.");
            }
            else if (this.language.equalsIgnoreCase("pt_BR")) {
                sender.sendMessage("§cSomente o Console pode usar Este Comando.");
            }
            return true;
        }
        final Player player = (Player)sender;
        if (label.equalsIgnoreCase("prender") && player.hasPermission("sgcraft.admin")) {
            if (args.length == 0) {
                if (this.language.equalsIgnoreCase("pt_BR")) {
                    player.sendMessage("§cUse: /prender <Jogador>");
                }
                else if (this.language.equalsIgnoreCase("en_US")) {
                    player.sendMessage("§cUse: /prender <Player>");
                }
                return true;
            }
            if (args.length == 1) {
                final Player target = Bukkit.getPlayerExact(args[0]);
                if (target != null) {
                    this.cagePlayer(target);
                    if (this.language.equalsIgnoreCase("pt_BR")) {
                        player.sendMessage("§7Voc\u00ea preendeu o §c" + target.getName());
                        preendido.add(target);
                    }
                    else if (this.language.equalsIgnoreCase("en_US")) {
                        player.sendMessage("§7You arrested §c" + target.getName());
                        preendido.add(target);
                    }
                    if (this.language.equalsIgnoreCase("pt_BR")) {
                        target.sendMessage("§cVoc\u00ea foi apreendido por um Staff, agora voc\u00ea est\u00e1 sob analise, n\u00e3o desconecte-se do Servidor ou Ser\u00e1 Banido Automaticamente.");
                    }
                    else if (this.language.equalsIgnoreCase("en_US")) {
                        target.sendMessage("§cYou have been apprehended by a Staff, now you are under review, do not disconnect from the Server or you will be Automatically Banned.");
                    }
                }
                else if (this.language.equalsIgnoreCase("pt_BR")) {
                    player.sendMessage("§cEsse jogador n\u00e3o esta online");
                }
                else if (this.language.equalsIgnoreCase("en_US")) {
                    player.sendMessage("§cThe Player is Not Online");
                }
            }
        }
        return false;
    }
    
    @EventHandler
    public void onCommands(final PlayerCommandPreprocessEvent e) {
        final Player p = e.getPlayer();
        if (p.getLocation().add(0.0, 13.0, 0.0).getBlock().getType() == Material.BEDROCK && p.getLocation().add(0.0, 11.0, 1.0).getBlock().getType() == Material.BEDROCK && p.getLocation().add(1.0, 11.0, 0.0).getBlock().getType() == Material.BEDROCK && p.getLocation().add(0.0, 11.0, -1.0).getBlock().getType() == Material.BEDROCK && p.getLocation().add(-1.0, 11.0, 0.0).getBlock().getType() == Material.BEDROCK && p.getLocation().add(0.0, 10.0, 0.0).getBlock().getType() == Material.BEDROCK) {
            e.setCancelled(true);
            if (this.language.equalsIgnoreCase("pt_BR")) {
                p.sendMessage("§cVoc\u00ea n\u00e3o pode usar esse comando enquanto estiver sob analise de um Staff.");
            }
            else if (this.language.equalsIgnoreCase("en_US")) {
                p.sendMessage("You can not use this command while under review by a Staff.");
            }
        }
    }
    
    @EventHandler
    public void onLeave(final PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        if (p.getLocation().add(0.0, 13.0, 0.0).getBlock().getType() == Material.BEDROCK && p.getLocation().add(0.0, 11.0, 1.0).getBlock().getType() == Material.BEDROCK && p.getLocation().add(1.0, 11.0, 0.0).getBlock().getType() == Material.BEDROCK && p.getLocation().add(0.0, 11.0, -1.0).getBlock().getType() == Material.BEDROCK && p.getLocation().add(-1.0, 11.0, 0.0).getBlock().getType() == Material.BEDROCK && p.getLocation().add(0.0, 10.0, 0.0).getBlock().getType() == Material.BEDROCK) {
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), Main.plugin.getConfig().getString("Command_Ban").replace("%player%", p.getName()));
        }
    }
    
    public void cagePlayer(final Player target) {
        target.getLocation().add(0.0, 13.0, 0.0).getBlock().setType(Material.BEDROCK);
        target.getLocation().add(0.0, 11.0, 1.0).getBlock().setType(Material.BEDROCK);
        target.getLocation().add(1.0, 11.0, 0.0).getBlock().setType(Material.BEDROCK);
        target.getLocation().add(0.0, 11.0, -1.0).getBlock().setType(Material.BEDROCK);
        target.getLocation().add(-1.0, 11.0, 0.0).getBlock().setType(Material.BEDROCK);
        target.getLocation().add(0.0, 10.0, 0.0).getBlock().setType(Material.BEDROCK);
        target.teleport(target.getLocation().add(0.0, 11.0, -0.05));
    }
}
