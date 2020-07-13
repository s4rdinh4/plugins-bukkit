package br.com.sgcraft.sglogger;

import org.bukkit.plugin.java.*;

import br.com.sgcraft.sglogger.commands.*;
import br.com.sgcraft.sglogger.config.*;
import br.com.sgcraft.sglogger.listeners.*;
import br.com.sgcraft.sglogger.mysql.*;

import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import org.bukkit.command.*;
import java.io.*;

public class Main extends JavaPlugin
{
    File subdir;
    File subdir2;
    public static Main plugin;
    private playerloggerCommand executor;
    
    public Main() {
        this.subdir = new File("plugins/SgLogger/Players");
        this.subdir2 = new File("plugins/SgLogger/Staff");
    }
    
    public void onEnable() {
        Main.plugin = this;
        if (!this.subdir.exists()) {
            this.subdir.mkdir();
        }
        if (Main.plugin.getConfig().getBoolean("Log.PastaStaffSeparada") && !this.subdir2.exists()) {
            this.subdir2.mkdir();
        }
        config.LoadConfiguration();
        getConfig.getValues();
        mysql.createDatabase();
        Bukkit.getServer().getPluginManager().registerEvents((Listener)new PListener(this), (Plugin)this);
        this.executor = new playerloggerCommand(this);
        this.getCommand("logger").setExecutor((CommandExecutor)this.executor);
    }
    
    public void onDisable() {
    }
}
