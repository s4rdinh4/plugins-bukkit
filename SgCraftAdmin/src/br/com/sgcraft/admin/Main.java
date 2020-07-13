package br.com.sgcraft.admin;

import org.bukkit.plugin.java.*;

import br.com.sgcraft.admin.utils.*;

import org.bukkit.plugin.*;
import org.bukkit.event.*;
import org.bukkit.command.*;
import org.bukkit.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.enchantments.*;
import java.util.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;

public class Main extends JavaPlugin implements Listener
{
    public static ArrayList<String> Admin;
    public static ArrayList<String> abririnv;
    public static ArrayList<String> mensagemknock;
    public static HashMap<String, ItemStack[]> saveinv;
    public static HashMap<String, ItemStack[]> armadura;
    public static Plugin plugin;
    
    static {
        Main.Admin = new ArrayList<String>();
        Main.abririnv = new ArrayList<String>();
        Main.mensagemknock = new ArrayList<String>();
        Main.saveinv = new HashMap<String, ItemStack[]>();
        Main.armadura = new HashMap<String, ItemStack[]>();
    }
    
    public static int getAmount(final Player p, final Material m) {
        int amount = 0;
        ItemStack[] contents;
        for (int length = (contents = p.getInventory().getContents()).length, i = 0; i < length; ++i) {
            final ItemStack item = contents[i];
            if (item != null && item.getType() == m && item.getAmount() > 0) {
                amount += item.getAmount();
            }
        }
        return amount;
    }
    
    public void onEnable() {
        Bukkit.getServer().getConsoleSender().sendMessage("SgCraft Admin - Ativado");
        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);
        Bukkit.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);
        Main.plugin = (Plugin)this;
        this.getCommand("prender").setExecutor((CommandExecutor)new CageCommand());
    }
    
    public void onDisable() {
        Bukkit.getServer().getConsoleSender().sendMessage("SgCraft Admin - Desativado");
    }
    
    @EventHandler
    public void onMagmaSair(final PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        if (Main.Admin.contains(p.getName()) && p.getItemInHand().getType() == Material.getMaterial(Main.plugin.getConfig().getInt("ItemLeave"))) {
            p.chat("/admin");
        }
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("admin") && !p.hasPermission("zadmin.command.admin")) {
            p.sendMessage(Main.plugin.getConfig().getString("Sem_Permissao").replaceAll("&", "§"));
            if (!(sender instanceof Player)) {
                sender.sendMessage(Main.plugin.getConfig().getString("Console").replaceAll("&", "§"));
            }
        }
        else if (Main.Admin.contains(p.getName())) {
            for (final Player pl : Bukkit.getServer().getOnlinePlayers()) {
                pl.showPlayer(p);
            }
            if (p.getPlayer().hasPermission("sgcraft.admin")) {
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 0.0f);
            }
            p.sendMessage(Main.plugin.getConfig().getString("Mensagen").replaceAll("&", "§"));
            p.getInventory().clear();
            p.sendMessage(Main.plugin.getConfig().getString("InvisivelOff").replaceAll("&", "§"));
            Main.abririnv.remove(p.getName());
            Main.mensagemknock.remove(p.getName());
            p.setPlayerListName(p.getDisplayName());
            p.setGameMode(GameMode.SURVIVAL);
            p.setAllowFlight(true);
            p.setFlying(true);
            p.setFlySpeed(0.1f);
            final ItemStack arco = new ItemStack(Material.getMaterial(Main.plugin.getConfig().getInt("ItemLeave")));
            final Object arcoa = arco.getItemMeta();
            ((ItemMeta)arcoa).setDisplayName(Main.plugin.getConfig().getString("Leave").replaceAll("&", "§"));
            arco.setItemMeta((ItemMeta)arcoa);
            final ItemStack cage = new ItemStack(Material.getMaterial(Main.plugin.getConfig().getInt("ItemCage")));
            final ItemMeta cagea = cage.getItemMeta();
            cagea.setDisplayName(Main.plugin.getConfig().getString("Cage").replaceAll("&", "§"));
            cage.setItemMeta(cagea);
            final ItemStack knokback = new ItemStack(Material.STICK);
            final ItemMeta knokbacka = knokback.getItemMeta();
            knokbacka.setDisplayName(Main.plugin.getConfig().getString("Stick").replaceAll("&", "§"));
            knokback.setItemMeta(knokbacka);
            knokbacka.addEnchant(Enchantment.KNOCKBACK, 10, true);
            knokback.setItemMeta(knokbacka);
            final ItemStack info = new ItemStack(Material.getMaterial(Main.plugin.getConfig().getInt("ItemInfo")));
            final ItemMeta infoa = info.getItemMeta();
            infoa.setDisplayName(Main.plugin.getConfig().getString("Info").replaceAll("&", "§"));
            info.setItemMeta(infoa);
            final ItemStack espada = new ItemStack(Material.DIAMOND_SWORD);
            final ItemMeta espadaa = espada.getItemMeta();
            espadaa.setDisplayName(Main.plugin.getConfig().getString("Sword").replaceAll("&", "§"));
            espada.setItemMeta(espadaa);
            espadaa.addEnchant(Enchantment.DAMAGE_ALL, 10, true);
            espada.setItemMeta(espadaa);
            p.getInventory().remove(arco);
            p.getInventory().remove(cage);
            p.getInventory().remove(knokback);
            p.getInventory().remove(info);
            p.getInventory().remove(espada);
            p.getInventory().setContents((ItemStack[])Main.saveinv.get(p.getName()));
            p.getInventory().setArmorContents((ItemStack[])Main.armadura.get(p.getName()));
            Main.saveinv.remove(p.getName());
            Main.armadura.remove(p.getName());
            Main.Admin.remove(p.getName());
            for (final Player s : Bukkit.getOnlinePlayers()) {
                s.showPlayer(p);
            }
        }
        else if (!Main.Admin.contains(p.getName())) {
            if (p.getPlayer().hasPermission("sgcraft.admin")) {
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
            }
            p.setGameMode(GameMode.SURVIVAL);
            p.setAllowFlight(true);
            p.setFlying(true);
            p.setFlySpeed(0.1f);
            Main.saveinv.put(p.getName(), p.getInventory().getContents());
            Main.armadura.put(p.getName(), p.getInventory().getArmorContents());
            p.sendMessage(Main.plugin.getConfig().getString("Mensagem").replaceAll("&", "§"));
            Main.abririnv.add(p.getName());
            p.getInventory().clear();
            p.getInventory().setArmorContents((ItemStack[])null);
            final Object arcoa = Bukkit.getServer().getOnlinePlayers().iterator();
            while (((Iterator)arcoa).hasNext()) {
                final Player pl = (Player) ((Iterator)arcoa).next();
                pl.hidePlayer(p);
            }
            p.sendMessage(Main.plugin.getConfig().getString("InvisivelOn").replaceAll("&", "§"));
            Main.mensagemknock.add(p.getName());
            p.getInventory().setHelmet((ItemStack)null);
            p.getInventory().setChestplate((ItemStack)null);
            p.getInventory().setLeggings((ItemStack)null);
            p.getInventory().setBoots((ItemStack)null);
            final ItemStack arco = new ItemStack(Material.getMaterial(Main.plugin.getConfig().getInt("ItemLeave")));
            final ItemMeta arcoa2 = arco.getItemMeta();
            arcoa2.setDisplayName(Main.plugin.getConfig().getString("Leave").replaceAll("&", "§"));
            arco.setItemMeta(arcoa2);
            final ItemStack cage2 = new ItemStack(Material.getMaterial(Main.plugin.getConfig().getInt("ItemCage")));
            final ItemMeta cagea2 = cage2.getItemMeta();
            cagea2.setDisplayName(Main.plugin.getConfig().getString("Cage").replaceAll("&", "§"));
            cage2.setItemMeta(cagea2);
            final ItemStack knokback2 = new ItemStack(Material.STICK);
            final ItemMeta knokbacka2 = knokback2.getItemMeta();
            knokbacka2.setDisplayName(Main.plugin.getConfig().getString("Stick").replaceAll("&", "§"));
            knokback2.setItemMeta(knokbacka2);
            knokbacka2.addEnchant(Enchantment.KNOCKBACK, 10, true);
            knokback2.setItemMeta(knokbacka2);
            final ItemStack info2 = new ItemStack(Material.getMaterial(Main.plugin.getConfig().getInt("ItemInfo")));
            final ItemMeta infoa2 = info2.getItemMeta();
            infoa2.setDisplayName(Main.plugin.getConfig().getString("Info").replaceAll("&", "§"));
            info2.setItemMeta(infoa2);
            final ItemStack espada2 = new ItemStack(Material.DIAMOND_SWORD);
            final ItemMeta espadaa2 = espada2.getItemMeta();
            espadaa2.setDisplayName(Main.plugin.getConfig().getString("Sword").replaceAll("&", "§"));
            espada2.setItemMeta(espadaa2);
            espadaa2.addEnchant(Enchantment.DAMAGE_ALL, 10, true);
            espada2.setItemMeta(espadaa2);
            if (Main.plugin.getConfig().getBoolean("Itens")) {
                p.getInventory().setItem(Main.plugin.getConfig().getInt("SlotLeave") - 1, arco);
                p.getInventory().setItem(Main.plugin.getConfig().getInt("SlotStick") - 1, knokback2);
                p.getInventory().setItem(Main.plugin.getConfig().getInt("SlotInfo") - 1, info2);
                p.getInventory().setItem(Main.plugin.getConfig().getInt("SlotSword") - 1, espada2);
                p.getInventory().setItem(Main.plugin.getConfig().getInt("SlotCage") - 1, cage2);
            }
            Main.Admin.add(p.getName());
            for (final Player s2 : Bukkit.getOnlinePlayers()) {
                if (!s2.hasPermission("sgcraft.admin")) {
                    s2.hidePlayer(p);
                }
            }
        }
        return false;
    }
    @EventHandler
    public void onCage(final PlayerInteractEntityEvent e) {
        final Player p = e.getPlayer();
        if (Main.Admin.contains(p.getName()) && p.getItemInHand().getType() == Material.getMaterial(Main.plugin.getConfig().getInt("ItemCage"))) {
            final Player r = (Player)e.getRightClicked();
            p.chat("/prender " + r.getName());
        }
    }
}
