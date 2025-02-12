package br.com.sgcraft.gladiador.utils;

import org.bukkit.scheduler.*;

import br.com.sgcraft.gladiador.*;
import br.com.sgcraft.gladiador.mensagens.*;

import org.bukkit.plugin.*;
import org.bukkit.inventory.*;
import java.util.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.block.*;
import org.bukkit.enchantments.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class Utils implements CommandExecutor
{
    private Main plugin;
    static int delay;
	private Location castelo;
    
    public Utils(final Main main) {
        this.plugin = main;
    }
    
    public void definetempo() {
        final Random r = new Random();
        Utils.delay = 480 + r.nextInt(60);
        this.bau();
    }
    
    public void bau() {
        new BukkitRunnable() {
            public void run() {
                Utils.this.spawnChests();
                Utils.this.definetempo();
                this.cancel();
            }
        }.runTaskLater((Plugin)this.plugin, (long)(Utils.delay * 20 * 20));
    }
    
    public static int strToCalendar(final String dia) {
        if (dia.equalsIgnoreCase("domingo")) {
            return 1;
        }
        if (dia.equalsIgnoreCase("segunda")) {
            return 2;
        }
        if (dia.equalsIgnoreCase("terca")) {
            return 3;
        }
        if (dia.equalsIgnoreCase("quarta")) {
            return 4;
        }
        if (dia.equalsIgnoreCase("quinta")) {
            return 5;
        }
        if (dia.equalsIgnoreCase("sexta")) {
            return 6;
        }
        if (dia.equalsIgnoreCase("sabado")) {
            return 7;
        }
        return 7;
    }
    
    public static void addClan(final String tag) {
        Main.pl.getConfig().set("ClanVenceu", (Object)tag);
        Main.pl.saveConfig();
    }
    
    public static void addItem(final String s, final Inventory inv) {
        if (Main.pl.getConfig().contains("Item." + s)) {
            for (final String a : Main.pl.getConfig().getStringList("Item." + s)) {
                ItemStack i = null;
                int slot = 0;
                int id = 0;
                int data = 0;
                int qt = 0;
                final String[] item = a.split(":");
                slot = Integer.parseInt(item[0]);
                id = Integer.parseInt(item[1]);
                data = Integer.parseInt(item[2]);
                qt = Integer.parseInt(item[3]);
                i = new ItemStack(id, qt, (short)data);
                for (int b = 1; b <= 10; ++b) {
                    if (a.contains("enchant" + b)) {
                        final String ench = a.split(",")[b].split(":")[1];
                        final int level = Integer.parseInt(a.split(",")[b].split(":")[2]);
                        final ItemMeta meta = i.getItemMeta();
                        meta.addEnchant(getEnchant(ench), level, true);
                        i.setItemMeta(meta);
                    }
                }
                inv.setItem(slot, i);
            }
        }
    }
    
    public void spawnChests() {
        int s = 1;
        if (!Main.pl.getConfig().contains("LocalBau")) {
            return;
        }
        for (final String a : Main.pl.getConfig().getConfigurationSection("LocalBau").getKeys(false)) {
            final String[] ent = Main.pl.getConfig().getString("LocalBau.Bau" + s).split(";");
            final Location l = new Location(this.plugin.getServer().getWorld(ent[0]), Double.parseDouble(ent[1]), Double.parseDouble(ent[2]), Double.parseDouble(ent[3]));
            if (!l.getBlock().getType().equals((Object)Material.CHEST)) {
                l.getBlock().setType(Material.CHEST);
            }
            final Chest chest = (Chest)l.getBlock().getState();
            chest.getInventory().clear();
            addItem("Bau" + s, chest.getBlockInventory());
            ++s;
        }
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("�6[Castelo] �eBau do castelo foi RESETADO!");
        Bukkit.broadcastMessage("");
    }
    
    public static Enchantment getEnchant(String s) {
        s = s.toLowerCase();
        Enchantment enchant = null;
        final String s2;
        switch (s2 = s) {
            case "inquebravel": {
                enchant = Enchantment.DURABILITY;
                break;
            }
            case "afiada": {
                enchant = Enchantment.DAMAGE_ALL;
                break;
            }
            case "infinidade": {
                enchant = Enchantment.ARROW_INFINITE;
                break;
            }
            case "protecao": {
                enchant = Enchantment.PROTECTION_ENVIRONMENTAL;
                break;
            }
            case "fortuna": {
                enchant = Enchantment.LOOT_BONUS_BLOCKS;
                break;
            }
            case "pilhagem": {
                enchant = Enchantment.LOOT_BONUS_MOBS;
                break;
            }
            case "repulsao": {
                enchant = Enchantment.KNOCKBACK;
                break;
            }
            case "efficiencia": {
                enchant = Enchantment.DIG_SPEED;
                break;
            }
            case "chama": {
                enchant = Enchantment.ARROW_FIRE;
                break;
            }
            case "forca": {
                enchant = Enchantment.ARROW_DAMAGE;
                break;
            }
            case "impacto": {
                enchant = Enchantment.ARROW_KNOCKBACK;
                break;
            }
            case "flamejante": {
                enchant = Enchantment.FIRE_ASPECT;
                break;
            }
            default:
                break;
        }
        return enchant;
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("castelo")) {
            if (args.length == 0 && sender instanceof Player) {
                final Player p = (Player)sender;
                if (this.plugin.core1.getClanManager().getClanPlayer((Player)sender) == null) {
                    sender.sendMessage("�4Voce nao tem clan");
                    return true;
                }
                final String tag = this.plugin.core1.getClanManager().getClanByPlayerName(((Player)sender).getName()).getTag();
                if (!Main.pl.getConfig().contains("ClanVenceu")) {
                    sender.sendMessage("�4Sem clan vencedor no momento!");
                    return true;
                }
                final String tagV = Main.pl.getConfig().getString("ClanVenceu");
                if (!tag.equalsIgnoreCase(tagV)) {
                    sender.sendMessage("�cVoce n�o esta no clan vencedor do Gladiador!");
                }
                else {
                final String[] cas = Main.pl.getConfig().getString("Arena.Castelo").split(";");
                this.castelo = new Location(Main.pl.getServer().getWorld(cas[0]), Double.parseDouble(cas[1]), Double.parseDouble(cas[2]), Double.parseDouble(cas[3]), Float.parseFloat(cas[4]), Float.parseFloat(cas[5]));
                p.teleport(castelo);
                sender.sendMessage(ChatColor.GOLD + "[Castelo] " + ChatColor.GREEN + "Teleportado para o Castelo!");
                }
                return true;
            }
            else if (args[0].equalsIgnoreCase("setcastelo")) {
                if (!sender.hasPermission("gladiador.admin")) {
                    sender.sendMessage(Mensagens.getMensagem("Erro10"));
                    return true;
                }
                final Player p = (Player)sender;
                final Location i = p.getLocation();
                this.plugin.getConfig().set("Arena.Castelo", (Object)(String.valueOf(String.valueOf(p.getWorld().getName())) + ";" + i.getX() + ";" + i.getY() + ";" + i.getZ() + ";" + i.getYaw() + ";" + i.getPitch()));
                this.plugin.saveConfig();
                sender.sendMessage(ChatColor.GOLD + "[Castelo] " + ChatColor.GREEN + "Spawn marcado!");
                return true;
            }
            else if (args[0].equalsIgnoreCase("spawn")) {
                if (!sender.hasPermission("gladiador.admin")) {
                    sender.sendMessage(Mensagens.getMensagem("Erro10"));
                    return true;
                }
                this.spawnChests();
                return true;
            }
            else if (args[0].equalsIgnoreCase("setbau")) {
                if (!sender.hasPermission("gladiador.admin")) {
                    sender.sendMessage(Mensagens.getMensagem("Erro10"));
                    return true;
                }
                int j = 0;
                if (Main.pl.getConfig().contains("LocalBau")) {
                    for (final String a : Main.pl.getConfig().getConfigurationSection("LocalBau").getKeys(false)) {
                        ++j;
                    }
                }
                final Player p2 = (Player)sender;
                final Location k = p2.getLocation();
                this.plugin.getConfig().set("LocalBau.Bau" + (j + 1), (Object)(String.valueOf(String.valueOf(p2.getWorld().getName())) + ";" + k.getX() + ";" + k.getY() + ";" + k.getZ()));
                this.plugin.saveConfig();
                sender.sendMessage(ChatColor.AQUA + "[Castelo] " + ChatColor.GREEN + "Bau" + (j + 1) + " marcado!");
                return true;
            }
        }
        return false;
    }
}
