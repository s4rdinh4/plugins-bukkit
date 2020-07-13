package br.com.sgcraft.playerlogger.config;

import br.com.sgcraft.playerlogger.*;
import java.util.*;

public class getConfig
{
    playerlogger plugin;
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
    
    public getConfig(final playerlogger instance) {
        this.plugin = instance;
    }
    
    public static void getValues() {
        getConfig.logFilesEnabled = playerlogger.plugin.getConfig().getBoolean("File.LogToFiles");
        getConfig.MySQLEnabled = playerlogger.plugin.getConfig().getBoolean("MySQL.Enabled");
        getConfig.PlayerJoins = playerlogger.plugin.getConfig().getBoolean("Log.PlayerJoins");
        getConfig.PlayerQuit = playerlogger.plugin.getConfig().getBoolean("Log.PlayerQuit");
        getConfig.PlayerChat = playerlogger.plugin.getConfig().getBoolean("Log.PlayerChat");
        getConfig.PlayerCommands = playerlogger.plugin.getConfig().getBoolean("Log.PlayerCommands");
        getConfig.PlayerDeaths = playerlogger.plugin.getConfig().getBoolean("Log.PlayerDeaths");
        getConfig.PlayerEnchants = playerlogger.plugin.getConfig().getBoolean("Log.PlayerEnchants");
        getConfig.PlayerPvp = playerlogger.plugin.getConfig().getBoolean("Log.Pvp");
        getConfig.PlayerBucketPlace = playerlogger.plugin.getConfig().getBoolean("Log.PlayerBucketPlace");
        getConfig.PlayerSignText = playerlogger.plugin.getConfig().getBoolean("Log.PlayerSignText");
        getConfig.ConsoleCommands = playerlogger.plugin.getConfig().getBoolean("Log.ConsoleCommands");
        getConfig.SeparateFolderforStaff = playerlogger.plugin.getConfig().getBoolean("Log.SeparateFolderforStaff");
        getConfig.PlayerNamestoLowerCase = playerlogger.plugin.getConfig().getBoolean("Log.PlayerNamestoLowerCase");
        getConfig.LogOnlyStaff = playerlogger.plugin.getConfig().getBoolean("Log.LogOnlyStaff");
        getConfig.LogDateFormat = playerlogger.plugin.getConfig().getString("Log.DateFormat");
        getConfig.LogBlackListedBlocks = playerlogger.plugin.getConfig().getBoolean("BlackList.LogBlackListedBlocks");
        getConfig.Blocks = (List<String>)playerlogger.plugin.getConfig().getStringList("BlackList.Blocks");
        getConfig.BlackListCommands = playerlogger.plugin.getConfig().getBoolean("Commands.BlackListCommands");
        getConfig.BlackListCommandsMySQL = playerlogger.plugin.getConfig().getBoolean("Commands.BlackListCommandsForMySQL");
        getConfig.CommandsToBlock = (List<String>)playerlogger.plugin.getConfig().getStringList("Commands.CommandsToBlock");
        getConfig.MySQLServer = playerlogger.plugin.getConfig().getString("MySQL.Server");
        getConfig.MySQLDatabase = playerlogger.plugin.getConfig().getString("MySQL.Database");
        getConfig.MySQLUser = playerlogger.plugin.getConfig().getString("MySQL.User");
        getConfig.MySQLPassword = playerlogger.plugin.getConfig().getString("MySQL.Password");
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
