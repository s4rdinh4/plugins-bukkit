package br.com.sgcraft.sglogger.commands;

import org.bukkit.command.*;

import br.com.sgcraft.sglogger.*;
import br.com.sgcraft.sglogger.config.*;
import br.com.sgcraft.sglogger.mysql.*;

import org.bukkit.*;

public class playerloggerCommand implements CommandExecutor
{
    private Main plugin;
    
    public playerloggerCommand(final Main plugin) {
        this.plugin = plugin;
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
        if (commandLabel.equalsIgnoreCase("logger")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Use: /logger reload");
                return false;
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("sgcraft.log.cmd")) {
                    this.plugin.reloadConfig();
                    getConfig.getValues();
                    mysql.createDatabase();
                    sender.sendMessage(ChatColor.GREEN + "SgLogger - Config Recarregada!");
                }
            }
            else if (args.length < 2) {
                return false;
            }
        }
        return false;
    }
}
