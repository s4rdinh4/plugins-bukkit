package br.com.sgcraft.playerlogger.commands;

import br.com.sgcraft.playerlogger.*;
import org.bukkit.command.*;
import org.bukkit.*;
import br.com.sgcraft.playerlogger.config.*;
import br.com.sgcraft.playerlogger.mysql.*;

public class playerloggerCommand implements CommandExecutor
{
    private playerlogger plugin;
    
    public playerloggerCommand(final playerlogger plugin) {
        this.plugin = plugin;
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
        if (commandLabel.equalsIgnoreCase("sglog")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Use: /sglog reload");
                return false;
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("sgcraft.log.cmd")) {
                    this.plugin.reloadConfig();
                    getConfig.getValues();
                    mysql.createDatabase();
                    sender.sendMessage(ChatColor.GREEN + "SgCraft Log - Config Recarregada!");
                }
            }
            else if (args.length < 2) {
                return false;
            }
        }
        return false;
    }
}
