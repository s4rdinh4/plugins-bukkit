package br.com.sgcraft.playerlogger;

import org.bukkit.plugin.java.*;
import java.io.*;
import br.com.sgcraft.playerlogger.commands.*;
import br.com.sgcraft.playerlogger.config.*;
import br.com.sgcraft.playerlogger.mysql.*;
import org.bukkit.*;
import br.com.sgcraft.playerlogger.listeners.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import org.bukkit.command.*;

public class playerlogger extends JavaPlugin
{
    File subdir;
    File subdir2;
    public static playerlogger plugin;
    private playerloggerCommand executor;
    
    public playerlogger() {
        this.subdir = new File("plugins/SgCraftLogs/Players");
        this.subdir2 = new File("plugins/SgCraftLogs/Staff");
    }
    
    public void onEnable() {
        playerlogger.plugin = this;
        if (!this.subdir.exists()) {
            this.subdir.mkdir();
        }
        if (playerlogger.plugin.getConfig().getBoolean("Log.SeparateFolderforStaff") && !this.subdir2.exists()) {
            this.subdir2.mkdir();
        }
        config.LoadConfiguration();
        getConfig.getValues();
        mysql.createDatabase();
        Bukkit.getServer().getPluginManager().registerEvents((Listener)new PListener(this), (Plugin)this);
        this.executor = new playerloggerCommand(this);
        this.getCommand("sglog").setExecutor((CommandExecutor)this.executor);
    }
    
    public void onDisable() {
    }
}
