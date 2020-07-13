package br.com.sgcraft.playerlogger.filehandler;

import br.com.sgcraft.playerlogger.*;
import br.com.sgcraft.playerlogger.config.*;
import java.io.*;
import org.bukkit.enchantments.*;
import java.util.*;
import java.text.*;

public class filehandler
{
    static String dateformat;
    static final String DATE_FORMAT_NOW;
    playerlogger plugin;
    
    static {
        filehandler.dateformat = getConfig.LogDateFormat();
        DATE_FORMAT_NOW = filehandler.dateformat;
    }
    
    public filehandler(final playerlogger instance) {
        this.plugin = instance;
    }
    
    public static void logLogin(String playername, final String worldname, final double x, final double y, final double z, final String ip, final Boolean staff) {
        final Boolean lowercase = getLowercase();
        if (lowercase) {
            playername = playername.toLowerCase();
        }
        File user;
        if (staff && getStaff()) {
            user = new File("plugins/SgCraftLogs/Staff/" + playername + ".properties");
        }
        else {
            if (getOnlyStaff()) {
                return;
            }
            user = new File("plugins/SgCraftLogs/Players/" + playername + ".properties");
        }
        try {
            final FileWriter outfile = new FileWriter(user, true);
            final PrintWriter out = new PrintWriter(outfile);
            user.createNewFile();
            out.println("[" + getTimestamp() + "] " + playername + " Logou no servidor com o IP: " + ip + " / Mundo: " + worldname + " X: " + (int)x + " Y: " + (int)y + " Z: " + (int)z);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void logQuit(String playername, final String worldname, final double x, final double y, final double z, final Boolean staff) {
        final Boolean lowercase = getLowercase();
        if (lowercase) {
            playername = playername.toLowerCase();
        }
        File user;
        if (staff && getStaff()) {
            user = new File("plugins/SgCraftLogs/Staff/" + playername + ".properties");
        }
        else {
            if (getOnlyStaff()) {
                return;
            }
            user = new File("plugins/SgCraftLogs/Players/" + playername + ".properties");
        }
        try {
            final FileWriter outfile = new FileWriter(user, true);
            final PrintWriter out = new PrintWriter(outfile);
            user.createNewFile();
            out.println("[" + getTimestamp() + "] " + playername + " Desconectou do Servidor " + " / Mundo: " + worldname + " X: " + (int)x + " Y: " + (int)y + " Z: " + (int)z);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void logChat(String playername, final String msg, final String worldname, final double x, final double y, final double z, final Boolean staff) {
        final Boolean lowercase = getLowercase();
        if (lowercase) {
            playername = playername.toLowerCase();
        }
        File user;
        if (staff && getStaff()) {
            user = new File("plugins/SgCraftLogs/Staff/" + playername + ".properties");
        }
        else {
            if (getOnlyStaff()) {
                return;
            }
            user = new File("plugins/SgCraftLogs/Players/" + playername + ".properties");
        }
        try {
            final FileWriter outfile = new FileWriter(user, true);
            final PrintWriter out = new PrintWriter(outfile);
            user.createNewFile();
            out.println("[" + getTimestamp() + "] " + playername + " Disse no Chat: " + msg + " / Mundo: " + worldname + " X: " + (int)x + " Y: " + (int)y + " Z: " + (int)z);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void logPlayerDeath(String playername, final String reason, final String worldname, final double x, final double y, final double z, final Boolean staff) {
        final Boolean lowercase = getLowercase();
        if (lowercase) {
            playername = playername.toLowerCase();
        }
        File user;
        if (staff && getStaff()) {
            user = new File("plugins/SgCraftLogs/Staff/" + playername + ".properties");
        }
        else {
            if (getOnlyStaff()) {
                return;
            }
            user = new File("plugins/SgCraftLogs/Players/" + playername + ".properties");
        }
        try {
            final FileWriter outfile = new FileWriter(user, true);
            final PrintWriter out = new PrintWriter(outfile);
            user.createNewFile();
            out.println("[" + getTimestamp() + "] " + playername + " Morreu na localiza\u00e7\u00e3o: " + worldname + " X: " + (int)x + " Y: " + (int)y + " Z: " + (int)z);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void logEnchant(String playername, final Map<Enchantment, Integer> ench, final String item, final int cost, final String worldname, final double x, final double y, final double z, final Boolean staff) {
        final Boolean lowercase = getLowercase();
        if (lowercase) {
            playername = playername.toLowerCase();
        }
        File user;
        if (staff && getStaff()) {
            user = new File("plugins/SgCraftLogs/Staff/" + playername + ".properties");
        }
        else {
            if (getOnlyStaff()) {
                return;
            }
            user = new File("plugins/SgCraftLogs/Players/" + playername + ".properties");
        }
        try {
            final FileWriter outfile = new FileWriter(user, true);
            final PrintWriter out = new PrintWriter(outfile);
            user.createNewFile();
            out.println("[" + getTimestamp() + "] " + playername + " Encantou o item: " + item + " - " + ench + " XP Gasto: " + cost + " / Mundo: " + worldname + " X:" + (int)x + " Y:" + (int)y + " Z:" + (int)z);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void logBucket(String playername, final String worldname, final int x, final int y, final int z, final Boolean lava, final Boolean staff) {
        final Boolean lowercase = getLowercase();
        if (lowercase) {
            playername = playername.toLowerCase();
        }
        File user;
        if (staff && getStaff()) {
            user = new File("plugins/SgCraftLogs/Staff/" + playername + ".properties");
        }
        else {
            if (getOnlyStaff()) {
                return;
            }
            user = new File("plugins/SgCraftLogs/Players/" + playername + ".properties");
        }
        String water = "Water";
        if (lava) {
            water = "Lava";
        }
        try {
            final FileWriter outfile = new FileWriter(user, true);
            final PrintWriter out = new PrintWriter(outfile);
            user.createNewFile();
            out.println("[" + getTimestamp() + "] " + playername + " Usou um balde de " + water + " / Mundo: " + worldname + " X: " + x + " Y: " + y + " Z: " + z);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void logSign(String playername, final String worldname, final int x, final int y, final int z, final String[] lines, final Boolean staff) {
        final Boolean lowercase = getLowercase();
        if (lowercase) {
            playername = playername.toLowerCase();
        }
        File user;
        if (staff && getStaff()) {
            user = new File("plugins/SgCraftLogs/Staff/" + playername + ".properties");
        }
        else {
            if (getOnlyStaff()) {
                return;
            }
            user = new File("plugins/SgCraftLogs/Players/" + playername + ".properties");
        }
        try {
            final FileWriter outfile = new FileWriter(user, true);
            final PrintWriter out = new PrintWriter(outfile);
            user.createNewFile();
            out.println("[" + getTimestamp() + "] " + playername + " Placa: " + lines[0] + " " + lines[1] + " " + lines[2] + " " + lines[3] + " / Local: " + worldname + " X: " + x + " Y: " + y + " Z: " + z);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void logKill(String player, String damager, final double x, final double y, final double z, final String worldname, final Boolean staff) {
        final Boolean lowercase = getLowercase();
        if (lowercase) {
            damager = damager.toLowerCase();
            player = player.toLowerCase();
        }
        File user;
        if (staff && getStaff()) {
            user = new File("plugins/SgCraftLogs/Staff/" + damager + ".properties");
        }
        else {
            if (getOnlyStaff()) {
                return;
            }
            user = new File("plugins/SgCraftLogs/Players/" + damager + ".properties");
        }
        try {
            final FileWriter outfile = new FileWriter(user, true);
            final PrintWriter out = new PrintWriter(outfile);
            user.createNewFile();
            out.println("[" + getTimestamp() + "] " + damager + " Matou o Jogador " + player + " / Mundo: " + worldname + " X: " + (int)x + " Y: " + (int)y + " Z: " + (int)z);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void logKilledBy(String player, String damager, final double x, final double y, final double z, final String worldname, final Boolean staff2) {
        final Boolean lowercase = getLowercase();
        if (lowercase) {
            damager = damager.toLowerCase();
            player = player.toLowerCase();
        }
        File user;
        if (staff2 && getStaff()) {
            user = new File("plugins/SgCraftLogs/Staff/" + player + ".properties");
        }
        else {
            if (getOnlyStaff()) {
                return;
            }
            user = new File("plugins/SgCraftLogs/Players/" + player + ".properties");
        }
        try {
            final FileWriter outfile = new FileWriter(user, true);
            final PrintWriter out = new PrintWriter(outfile);
            user.createNewFile();
            out.println("[" + getTimestamp() + "] " + player + " Morto por " + damager + " / Mundo: " + worldname + " X: " + (int)x + " Y: " + (int)y + " Z: " + (int)z);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void logConsole(final String msg) {
        final File console = new File("plugins/SgCraftLogs/ConsoleLog.properties");
        try {
            final FileWriter outfile = new FileWriter(console, true);
            final PrintWriter out = new PrintWriter(outfile);
            console.createNewFile();
            out.println("[Console]" + msg + " " + getTimestamp());
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void logPlace(String playername, final String worldname, final String blockname, final int x, final int y, final int z, final Boolean staff) {
        final Boolean lowercase = getLowercase();
        if (lowercase) {
            playername = playername.toLowerCase();
        }
        File user;
        if (staff && getStaff()) {
            user = new File("plugins/SgCraftLogs/Staff/" + playername + ".properties");
        }
        else {
            if (getOnlyStaff()) {
                return;
            }
            user = new File("plugins/SgCraftLogs/Players/" + playername + ".properties");
        }
        try {
            final FileWriter outfile = new FileWriter(user, true);
            final PrintWriter out = new PrintWriter(outfile);
            user.createNewFile();
            out.println("[" + getTimestamp() + "] " + playername + " Colocou o Bloco: " + blockname + " / Local: " + worldname + " X: " + x + " Y: " + y + " Z: " + z);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void logBreak(String playername, final String worldname, final String blockname, final int x, final int y, final int z, final Boolean staff) {
        final Boolean lowercase = getLowercase();
        if (lowercase) {
            playername = playername.toLowerCase();
        }
        File user;
        if (staff && getStaff()) {
            user = new File("plugins/SgCraftLogs/Staff/" + playername + ".properties");
        }
        else {
            if (getOnlyStaff()) {
                return;
            }
            user = new File("plugins/SgCraftLogs/Players/" + playername + ".properties");
        }
        try {
            final FileWriter outfile = new FileWriter(user, true);
            final PrintWriter out = new PrintWriter(outfile);
            user.createNewFile();
            out.println("[" + getTimestamp() + "] " + playername + " Quebrou o Bloco: " + blockname + " / Local: " + worldname + " X: " + x + " Y: " + y + " Z: " + z);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void logDrop(String playername, final String worldname, final String ItemName, final int x, final int y, final int z, final Boolean staff) {
        final Boolean lowercase = getLowercase();
        if (lowercase) {
            playername = playername.toLowerCase();
        }
        File user;
        if (staff && getStaff()) {
            user = new File("plugins/SgCraftLogs/Staff/" + playername + ".properties");
        }
        else {
            if (getOnlyStaff()) {
                return;
            }
            user = new File("plugins/SgCraftLogs/Players/" + playername + ".properties");
        }
        try {
            final FileWriter outfile = new FileWriter(user, true);
            final PrintWriter out = new PrintWriter(outfile);
            user.createNewFile();
            out.println("[" + getTimestamp() + "] " + playername + " Dropou o Item: " + ItemName + " / Local: " + worldname + " X: " + x + " Y: " + y + " Z: " + z);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void logPickup(String playername, final String worldname, final String ItemName, final int x, final int y, final int z, final Boolean staff) {
        final Boolean lowercase = getLowercase();
        if (lowercase) {
            playername = playername.toLowerCase();
        }
        File user;
        if (staff && getStaff()) {
            user = new File("plugins/SgCraftLogs/Staff/" + playername + ".properties");
        }
        else {
            if (getOnlyStaff()) {
                return;
            }
            user = new File("plugins/SgCraftLogs/Players/" + playername + ".properties");
        }
        try {
            final FileWriter outfile = new FileWriter(user, true);
            final PrintWriter out = new PrintWriter(outfile);
            user.createNewFile();
            out.println("[" + getTimestamp() + "] " + playername + " Pegou o Item: " + ItemName + " / Local: " + worldname + " X:" + x + " Y: " + y + " Z: " + z);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void logCmd(String playername, final String msg, final String worldname, final double x, final double y, final double z, final Boolean staff) {
        final Boolean lowercase = getLowercase();
        if (lowercase) {
            playername = playername.toLowerCase();
        }
        File user;
        if (staff && getStaff()) {
            user = new File("plugins/SgCraftLogs/Staff/" + playername + ".properties");
        }
        else {
            if (getOnlyStaff()) {
                return;
            }
            user = new File("plugins/SgCraftLogs/Players/" + playername + ".properties");
        }
        try {
            final FileWriter outfile = new FileWriter(user, true);
            final PrintWriter out = new PrintWriter(outfile);
            user.createNewFile();
            out.println("[" + getTimestamp() + "] " + playername + " Usou o Comando: " + msg + " / Mundo: " + worldname + " X: " + (int)x + " Y: " + (int)y + " Z: " + (int)z);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static String getTimestamp() {
        final Calendar cal = Calendar.getInstance();
        final SimpleDateFormat sdf = new SimpleDateFormat(filehandler.DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }
    
    public static Boolean getLowercase() {
        if (getConfig.PlayerNamestoLowerCase()) {
            return true;
        }
        return false;
    }
    
    public static Boolean getStaff() {
        if (getConfig.SeparateFolderforStaff()) {
            return true;
        }
        return false;
    }
    
    public static Boolean getOnlyStaff() {
        if (getConfig.LogOnlyStaff()) {
            return true;
        }
        return false;
    }
}
