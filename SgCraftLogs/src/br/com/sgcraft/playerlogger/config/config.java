package br.com.sgcraft.playerlogger.config;

import br.com.sgcraft.playerlogger.*;
import java.util.*;

public class config
{
    playerlogger plugin;
    
    public config(final playerlogger instance) {
        this.plugin = instance;
    }
    
    public static void LoadConfiguration() {
        final List<String> words = new ArrayList<String>();
        words.add("7");
        words.add("46");
        words.add("57");
        final List<String> cmds = new ArrayList<String>();
        cmds.add("/login");
        cmds.add("/changepassword");
        cmds.add("/register");
        final String path1 = "Log.PlayerJoins";
        final String path2 = "Log.PlayerQuit";
        final String path3 = "Log.PlayerChat";
        final String path4 = "Log.PlayerCommands";
        final String path5 = "Log.PlayerDeaths";
        final String path6 = "Log.PlayerEnchants";
        final String path7 = "Log.PlayerBucketPlace";
        final String path8 = "Log.PlayerSignText";
        final String path9 = "Log.Pvp";
        final String path10 = "Log.ConsoleCommands";
        final String path11 = "Log.DateFormat";
        final String path12 = "BlackList.LogBlackListedBlocks";
        final String path13 = "BlackList.Blocks";
        final String path14 = "Commands.BlackListCommands";
        final String path15 = "Commands.BlackListCommandsForMySQL";
        final String path16 = "Commands.CommandsToBlock";
        final String path17 = "Log.SeparateFolderforStaff";
        final String path18 = "Log.PlayerNamestoLowerCase";
        final String path19 = "File.LogToFiles";
        final String path20 = "Log.LogOnlyStaff";
        final String path21 = "MySQL.Enabled";
        final String path22 = "MySQL.Server";
        final String path23 = "MySQL.Database";
        final String path24 = "MySQL.User";
        final String path25 = "MySQL.Password";
        playerlogger.plugin.getConfig().addDefault("File.LogToFiles", (Object)true);
        playerlogger.plugin.getConfig().addDefault("Log.PlayerJoins", (Object)true);
        playerlogger.plugin.getConfig().addDefault("Log.PlayerQuit", (Object)true);
        playerlogger.plugin.getConfig().addDefault("Log.PlayerChat", (Object)true);
        playerlogger.plugin.getConfig().addDefault("Log.PlayerCommands", (Object)true);
        playerlogger.plugin.getConfig().addDefault("Log.PlayerDeaths", (Object)true);
        playerlogger.plugin.getConfig().addDefault("Log.PlayerEnchants", (Object)true);
        playerlogger.plugin.getConfig().addDefault("Log.Pvp", (Object)true);
        playerlogger.plugin.getConfig().addDefault("Log.PlayerBucketPlace", (Object)true);
        playerlogger.plugin.getConfig().addDefault("Log.ConsoleCommands", (Object)true);
        playerlogger.plugin.getConfig().addDefault("Log.DateFormat", (Object)"MM-dd-yyyy HH:mm:ss");
        playerlogger.plugin.getConfig().addDefault("BlackList.LogBlackListedBlocks", (Object)true);
        playerlogger.plugin.getConfig().addDefault("BlackList.Blocks", (Object)words);
        playerlogger.plugin.getConfig().addDefault("Log.PlayerSignText", (Object)true);
        playerlogger.plugin.getConfig().addDefault("Commands.BlackListCommands", (Object)false);
        playerlogger.plugin.getConfig().addDefault("Commands.BlackListCommandsForMySQL", (Object)false);
        playerlogger.plugin.getConfig().addDefault("Commands.CommandsToBlock", (Object)cmds);
        playerlogger.plugin.getConfig().addDefault("Log.SeparateFolderforStaff", (Object)true);
        playerlogger.plugin.getConfig().addDefault("Log.PlayerNamestoLowerCase", (Object)false);
        playerlogger.plugin.getConfig().addDefault("Log.LogOnlyStaff", (Object)false);
        playerlogger.plugin.getConfig().addDefault("MySQL.Enabled", (Object)false);
        playerlogger.plugin.getConfig().addDefault("MySQL.Server", (Object)"Server Address eg.Localhost");
        playerlogger.plugin.getConfig().addDefault("MySQL.Database", (Object)"Place Database name here");
        playerlogger.plugin.getConfig().addDefault("MySQL.User", (Object)"Place User of MySQL Database here");
        playerlogger.plugin.getConfig().addDefault("MySQL.Password", (Object)"Place User password here");
        playerlogger.plugin.getConfig().options().copyDefaults(true);
        playerlogger.plugin.saveConfig();
    }
}
