package br.com.sgcraft.sglogger.config;

import java.util.*;

import br.com.sgcraft.sglogger.*;

public class getConfig
{
    Main plugin;
    private static boolean logFilesEnabled;
    private static boolean PlayerJoins;
    private static boolean PlayerQuit;
    private static boolean PlayerChat;
    private static boolean PlayerCommands;
    private static boolean PlayerDeaths;
    private static boolean PlayerEnchants;
    private static boolean PlayerPvp;
    private static boolean PlayerBucketPlace;
    private static boolean PlayerSignText;
    private static boolean ConsoleCommands;
    private static boolean SeparateFolderforStaff;
    private static boolean PlayerNamestoLowerCase;
    private static boolean LogOnlyStaff;
    private static String LogDateFormat;
    private static boolean LogBlackListedBlocks;
    private static List<String> Blocks;
    private static boolean BlackListCommands;
    private static boolean BlackListCommandsMySQL;
    private static List<String> CommandsToBlock;
    private static boolean MySQLEnabled;
    private static String MySQLServer;
    private static String MySQLDatabase;
    private static String MySQLUser;
    private static String MySQLPassword;
    
    public getConfig(final Main instance) {
        this.plugin = instance;
    }
    
    public static void getValues() {
        getConfig.logFilesEnabled = Main.plugin.getConfig().getBoolean("File.LogToFiles");
        getConfig.MySQLEnabled = Main.plugin.getConfig().getBoolean("MySQL.Enabled");
        getConfig.PlayerJoins = Main.plugin.getConfig().getBoolean("Log.PlayerJoins");
        getConfig.PlayerQuit = Main.plugin.getConfig().getBoolean("Log.PlayerQuit");
        getConfig.PlayerChat = Main.plugin.getConfig().getBoolean("Log.PlayerChat");
        getConfig.PlayerCommands = Main.plugin.getConfig().getBoolean("Log.PlayerCommands");
        getConfig.PlayerDeaths = Main.plugin.getConfig().getBoolean("Log.PlayerDeaths");
        getConfig.PlayerEnchants = Main.plugin.getConfig().getBoolean("Log.PlayerEnchants");
        getConfig.PlayerPvp = Main.plugin.getConfig().getBoolean("Log.Pvp");
        getConfig.PlayerBucketPlace = Main.plugin.getConfig().getBoolean("Log.PlayerBucketPlace");
        getConfig.PlayerSignText = Main.plugin.getConfig().getBoolean("Log.PlayerSignText");
        getConfig.ConsoleCommands = Main.plugin.getConfig().getBoolean("Log.ConsoleCommands");
        getConfig.SeparateFolderforStaff = Main.plugin.getConfig().getBoolean("Log.SeparateFolderforStaff");
        getConfig.PlayerNamestoLowerCase = Main.plugin.getConfig().getBoolean("Log.PlayerNamestoLowerCase");
        getConfig.LogOnlyStaff = Main.plugin.getConfig().getBoolean("Log.LogOnlyStaff");
        getConfig.LogDateFormat = Main.plugin.getConfig().getString("Log.DateFormat");
        getConfig.LogBlackListedBlocks = Main.plugin.getConfig().getBoolean("BlackList.LogBlackListedBlocks");
        getConfig.Blocks = (List<String>)Main.plugin.getConfig().getStringList("BlackList.Blocks");
        getConfig.BlackListCommands = Main.plugin.getConfig().getBoolean("Commands.BlackListCommands");
        getConfig.BlackListCommandsMySQL = Main.plugin.getConfig().getBoolean("Commands.BlackListCommandsForMySQL");
        getConfig.CommandsToBlock = (List<String>)Main.plugin.getConfig().getStringList("Commands.CommandsToBlock");
        getConfig.MySQLServer = Main.plugin.getConfig().getString("MySQL.Server");
        getConfig.MySQLDatabase = Main.plugin.getConfig().getString("MySQL.Database");
        getConfig.MySQLUser = Main.plugin.getConfig().getString("MySQL.User");
        getConfig.MySQLPassword = Main.plugin.getConfig().getString("MySQL.Password");
    }
    
    public static boolean logFilesEnabled() {
        return getConfig.logFilesEnabled;
    }
    
    public static boolean PlayerJoins() {
        return getConfig.PlayerJoins;
    }
    
    public static boolean PlayerQuit() {
        return getConfig.PlayerQuit;
    }
    
    public static boolean PlayerChat() {
        return getConfig.PlayerChat;
    }
    
    public static boolean PlayerCommands() {
        return getConfig.PlayerCommands;
    }
    
    public static boolean PlayerDeaths() {
        return getConfig.PlayerDeaths;
    }
    
    public static boolean PlayerEnchants() {
        return getConfig.PlayerEnchants;
    }
    
    public static boolean PlayerPvp() {
        return getConfig.PlayerPvp;
    }
    
    public static boolean PlayerBucketPlace() {
        return getConfig.PlayerBucketPlace;
    }
    
    public static boolean PlayerSignText() {
        return getConfig.PlayerSignText;
    }
    
    public static boolean ConsoleCommands() {
        return getConfig.ConsoleCommands;
    }
    
    public static boolean SeparateFolderforStaff() {
        return getConfig.SeparateFolderforStaff;
    }
    
    public static boolean PlayerNamestoLowerCase() {
        return getConfig.PlayerNamestoLowerCase;
    }
    
    public static boolean LogOnlyStaff() {
        return getConfig.LogOnlyStaff;
    }
    
    public static String LogDateFormat() {
        return getConfig.LogDateFormat;
    }
    
    public static boolean LogBlackListedBlocks() {
        return getConfig.LogBlackListedBlocks;
    }
    
    public static List<String> Blocks() {
        return getConfig.Blocks;
    }
    
    public static boolean BlackListCommands() {
        return getConfig.BlackListCommands;
    }
    
    public static boolean BlackListCommandsMySQL() {
        return getConfig.BlackListCommandsMySQL;
    }
    
    public static List<String> CommandsToBlock() {
        return getConfig.CommandsToBlock;
    }
    
    public static boolean MySQLEnabled() {
        return getConfig.MySQLEnabled;
    }
    
    public static String MySQLServer() {
        return getConfig.MySQLServer;
    }
    
    public static String MySQLDatabase() {
        return getConfig.MySQLDatabase;
    }
    
    public static String MySQLUser() {
        return getConfig.MySQLUser;
    }
    
    public static String MySQLPassword() {
        return getConfig.MySQLPassword;
    }
}
