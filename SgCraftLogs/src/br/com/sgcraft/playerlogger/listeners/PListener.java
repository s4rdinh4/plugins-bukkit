package br.com.sgcraft.playerlogger.listeners;

import br.com.sgcraft.playerlogger.*;
import br.com.sgcraft.playerlogger.config.*;
import br.com.sgcraft.playerlogger.filehandler.*;
import br.com.sgcraft.playerlogger.mysql.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import org.bukkit.event.enchantment.*;
import java.util.*;
import org.bukkit.enchantments.*;
import org.bukkit.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.server.*;
import org.bukkit.event.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.player.*;

public class PListener implements Listener
{
    playerlogger plugin;
    
    public PListener(final playerlogger instance) {
        this.plugin = instance;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(final PlayerLoginEvent event) {
        if (event.getResult() == PlayerLoginEvent.Result.ALLOWED) {
            final Player player = event.getPlayer();
            final World world = player.getWorld();
            final String worldname = world.getName();
            final String playername = player.getName();
            String ip = "Error";
            Boolean staff = false;
            ip = event.getAddress().getHostAddress();
            final double x = (int)Math.floor(player.getLocation().getX());
            final double y = (int)Math.floor(player.getLocation().getY());
            final double z = (int)Math.floor(player.getLocation().getZ());
            if (getConfig.PlayerJoins()) {
                if (player.hasPermission("sgcraftlog.staff")) {
                    staff = true;
                }
                if (getConfig.logFilesEnabled()) {
                    filehandler.logLogin(playername, worldname, x, y, z, ip, staff);
                }
                if (getConfig.MySQLEnabled()) {
                    addData.add(playername, "join", ip, x, y, z, worldname, staff);
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final World world = player.getWorld();
        final String worldname = world.getName();
        final String playername = player.getName();
        Boolean staff = false;
        final double x = (int)Math.floor(player.getLocation().getX());
        final double y = (int)Math.floor(player.getLocation().getY());
        final double z = (int)Math.floor(player.getLocation().getZ());
        if (getConfig.PlayerQuit()) {
            if (player.hasPermission("sgcraftlog.staff")) {
                staff = true;
            }
            if (getConfig.logFilesEnabled()) {
                filehandler.logQuit(playername, worldname, x, y, z, staff);
            }
            if (getConfig.MySQLEnabled()) {
                addData.add(playername, "quit", "", x, y, z, worldname, staff);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerChat(final AsyncPlayerChatEvent event) {
        this.plugin.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, (Runnable)new Runnable() {
            @Override
            public void run() {
                final Player player = event.getPlayer();
                final World world = player.getWorld();
                final String worldname = world.getName();
                final String playername = player.getName();
                final String msg = event.getMessage();
                final double x = (int)Math.floor(player.getLocation().getX());
                final double y = (int)Math.floor(player.getLocation().getY());
                final double z = (int)Math.floor(player.getLocation().getZ());
                if (getConfig.PlayerChat()) {
                    if (getConfig.logFilesEnabled()) {
                        PListener.this.plugin.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)PListener.this.plugin, (Runnable)new Runnable() {
                            @Override
                            public void run() {
                                Boolean staff = false;
                                if (player.hasPermission("sgcraftlog.staff")) {
                                    staff = true;
                                }
                                filehandler.logChat(playername, msg, worldname, x, y, z, staff);
                            }
                        }, 1L);
                    }
                    if (getConfig.MySQLEnabled()) {
                        PListener.this.plugin.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)PListener.this.plugin, (Runnable)new Runnable() {
                            @Override
                            public void run() {
                                Boolean staff = false;
                                if (player.hasPermission("sgcraftlog.staff")) {
                                    staff = true;
                                }
                                addData.add(playername, "chat", msg, x, y, z, worldname, staff);
                            }
                        }, 1L);
                    }
                }
            }
        }, 1L);
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerCmd(final PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        final World world = player.getWorld();
        final String worldname = world.getName();
        final String playername = player.getName();
        final String msg = event.getMessage();
        final String[] msg2 = event.getMessage().split(" ");
        Boolean staff = false;
        Boolean log = false;
        final double x = (int)Math.floor(player.getLocation().getX());
        final double y = (int)Math.floor(player.getLocation().getY());
        final double z = (int)Math.floor(player.getLocation().getZ());
        if (getConfig.PlayerCommands()) {
            if (getConfig.BlackListCommands() || getConfig.BlackListCommandsMySQL()) {
                for (String m : getConfig.CommandsToBlock()) {
                    m = m.toString().toLowerCase();
                    if (msg2[0].equalsIgnoreCase(m)) {
                        log = true;
                        break;
                    }
                }
            }
            if (log) {
                if (player.hasPermission("sgcraftlog.staff")) {
                    staff = true;
                }
                if (!getConfig.BlackListCommands() && getConfig.logFilesEnabled()) {
                    filehandler.logCmd(playername, msg, worldname, x, y, z, staff);
                }
                if (!getConfig.BlackListCommandsMySQL() && getConfig.MySQLEnabled()) {
                    addData.add(playername, "command", msg, x, y, z, worldname, staff);
                }
            }
            else {
                if (player.hasPermission("sgcraftlog.staff")) {
                    staff = true;
                }
                if (getConfig.logFilesEnabled()) {
                    filehandler.logCmd(playername, msg, worldname, x, y, z, staff);
                }
                if (getConfig.MySQLEnabled()) {
                    addData.add(playername, "command", msg, x, y, z, worldname, staff);
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDeath(final EntityDeathEvent event) {
        final Entity ent = (Entity)event.getEntity();
        if (ent instanceof Player) {
            final Player player = (Player)event.getEntity();
            final World world = player.getWorld();
            final String worldname = world.getName();
            final String playername = player.getName();
            Boolean staff = false;
            final String reason = event.getEventName();
            final double x = (int)Math.floor(player.getLocation().getX());
            final double y = (int)Math.floor(player.getLocation().getY());
            final double z = (int)Math.floor(player.getLocation().getZ());
            if (getConfig.PlayerDeaths()) {
                if (player.hasPermission("sgcraftlog.staff")) {
                    staff = true;
                }
                if (getConfig.logFilesEnabled()) {
                    filehandler.logPlayerDeath(playername, reason, worldname, x, y, z, staff);
                }
                if (getConfig.MySQLEnabled()) {
                    addData.add(playername, "death", "", x, y, z, worldname, staff);
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEnchant(final EnchantItemEvent event) {
        final Player player = event.getEnchanter();
        final String playername = player.getName();
        final World world = player.getWorld();
        Boolean staff = false;
        final String worldname = world.getName();
        final Map<Enchantment, Integer> ench = (Map<Enchantment, Integer>)event.getEnchantsToAdd();
        final String item = event.getItem().getItemMeta().getDisplayName();
        final int cost = event.getExpLevelCost();
        final double x = (int)Math.floor(player.getLocation().getX());
        final double y = (int)Math.floor(player.getLocation().getY());
        final double z = (int)Math.floor(player.getLocation().getZ());
        if (getConfig.PlayerEnchants()) {
            if (player.hasPermission("sgcraftlog.staff")) {
                staff = true;
            }
            if (getConfig.logFilesEnabled()) {
                filehandler.logEnchant(playername, ench, item, cost, worldname, x, y, z, staff);
            }
            if (getConfig.MySQLEnabled()) {
                addData.add(playername, "enchant", String.valueOf(item) + " " + ench + " Xp Cost:" + cost, x, y, z, worldname, staff);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onBucket(final PlayerBucketEmptyEvent event) {
        final Player player = event.getPlayer();
        final String playername = player.getName();
        final World world = player.getWorld();
        Boolean lava = false;
        final String worldname = world.getName();
        Boolean staff = false;
        if (getConfig.PlayerBucketPlace() && !event.isCancelled()) {
            if (event.getBucket() != null && event.getBucket() == Material.LAVA_BUCKET) {
                lava = true;
                final int x = event.getBlockClicked().getLocation().getBlockX();
                final int y = event.getBlockClicked().getLocation().getBlockY();
                final int z = event.getBlockClicked().getLocation().getBlockZ();
                if (player.hasPermission("sgcraftlog.staff")) {
                    staff = true;
                }
                if (getConfig.logFilesEnabled()) {
                    filehandler.logBucket(playername, worldname, x, y, z, lava, staff);
                }
                if (getConfig.MySQLEnabled()) {
                    addData.add(playername, "bucket", "Lava", x, y, z, worldname, staff);
                }
            }
            else if (event.getBucket() != null && event.getBucket() == Material.WATER_BUCKET) {
                lava = false;
                final int x = event.getBlockClicked().getLocation().getBlockX();
                final int y = event.getBlockClicked().getLocation().getBlockY();
                final int z = event.getBlockClicked().getLocation().getBlockZ();
                if (player.hasPermission("PlayerLogger.staff")) {
                    staff = true;
                }
                if (getConfig.logFilesEnabled()) {
                    filehandler.logBucket(playername, worldname, x, y, z, lava, staff);
                }
                if (getConfig.MySQLEnabled()) {
                    addData.add(playername, "bucket", "Water", x, y, z, worldname, staff);
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onSign(final SignChangeEvent event) {
        final Player player = event.getPlayer();
        final String playername = player.getName();
        final World world = player.getWorld();
        Boolean staff = false;
        final String[] lines = event.getLines();
        final String worldname = world.getName();
        final int x = event.getBlock().getLocation().getBlockX();
        final int y = event.getBlock().getLocation().getBlockY();
        final int z = event.getBlock().getLocation().getBlockZ();
        if (!event.isCancelled() && getConfig.PlayerSignText()) {
            if (player.hasPermission("sgcraftlog.staff")) {
                staff = true;
            }
            if (getConfig.logFilesEnabled()) {
                filehandler.logSign(playername, worldname, x, y, z, lines, staff);
            }
            if (getConfig.MySQLEnabled()) {
                addData.add(playername, "sign", "[" + lines[0] + "]" + "[" + lines[1] + "]" + "[" + lines[2] + "]" + "[" + lines[3] + "]", x, y, z, worldname, staff);
            }
        }
    }
    
    @EventHandler
    public void onEntityDeath1(final EntityDeathEvent event) {
        final Entity ply = (Entity)event.getEntity();
        if (event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            final Entity dmgr = ((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager();
            if (ply instanceof Player && dmgr instanceof Player) {
                final String worldname = ply.getWorld().getName();
                final String player = ((Player)ply).getName();
                final String damager = ((Player)dmgr).getName();
                Boolean staff = false;
                Boolean staff2 = false;
                final double x = Math.floor(dmgr.getLocation().getX());
                final double y = Math.floor(dmgr.getLocation().getY());
                final double z = Math.floor(dmgr.getLocation().getZ());
                final double x2 = Math.floor(ply.getLocation().getX());
                final double y2 = Math.floor(ply.getLocation().getY());
                final double z2 = Math.floor(ply.getLocation().getZ());
                if (getConfig.PlayerPvp()) {
                    if (((Player)dmgr).hasPermission("PlayerLogger.staff")) {
                        staff = true;
                    }
                    if (((Player)ply).hasPermission("PlayerLogger.staff")) {
                        staff2 = true;
                    }
                    if (getConfig.logFilesEnabled()) {
                        filehandler.logKill(player, damager, x, y, z, worldname, staff);
                        filehandler.logKilledBy(player, damager, x2, y2, z2, worldname, staff2);
                    }
                    if (getConfig.MySQLEnabled()) {
                        addData.add(damager, "kill", player, x, y, z, worldname, staff);
                        addData.add(player, "killedby", damager, x, y, z, worldname, staff2);
                    }
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerConsoleCommand(final ServerCommandEvent event) {
        final String msg = event.getCommand();
        if (getConfig.ConsoleCommands() && getConfig.logFilesEnabled()) {
            filehandler.logConsole(msg);
        }
        if (getConfig.ConsoleCommands() && getConfig.MySQLEnabled()) {
            addData.add("", "console", msg, 0.0, 0.0, 0.0, "", true);
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlace(final BlockPlaceEvent event) {
        final Player player = event.getPlayer();
        final String playername = player.getName();
        final World world = player.getWorld();
        final String worldname = world.getName();
        Boolean staff = false;
        final int x = event.getBlock().getLocation().getBlockX();
        final int y = event.getBlock().getLocation().getBlockY();
        final int z = event.getBlock().getLocation().getBlockZ();
        final String blockid = new StringBuilder().append(event.getBlock().getTypeId()).toString();
        Boolean log = false;
        if (!event.isCancelled() && getConfig.LogBlackListedBlocks()) {
            for (String m : getConfig.Blocks()) {
                m = m.toString().toLowerCase();
                if (blockid.equals(m) || m.equalsIgnoreCase("*")) {
                    log = true;
                    break;
                }
            }
            if (log) {
                String blockname = event.getBlock().getType().toString();
                blockname = blockname.replaceAll("_", " ");
                if (player.hasPermission("sgcraftlog.staff")) {
                    staff = true;
                }
                if (getConfig.logFilesEnabled()) {
                    filehandler.logPlace(playername, worldname, blockname, x, y, z, staff);
                }
                if (getConfig.MySQLEnabled()) {
                    addData.add(playername, "place", blockname, x, y, z, worldname, staff);
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(final BlockBreakEvent event) {
        final Player player = event.getPlayer();
        final String playername = player.getName();
        final World world = player.getWorld();
        final String worldname = world.getName();
        Boolean staff = false;
        final int x = event.getBlock().getLocation().getBlockX();
        final int y = event.getBlock().getLocation().getBlockY();
        final int z = event.getBlock().getLocation().getBlockZ();
        final String blockid = new StringBuilder().append(event.getBlock().getTypeId()).toString();
        Boolean log = false;
        if (!event.isCancelled() && getConfig.LogBlackListedBlocks()) {
            for (String m : getConfig.Blocks()) {
                m = m.toString().toLowerCase();
                if (blockid.equalsIgnoreCase(m) || m.equalsIgnoreCase("*")) {
                    log = true;
                    break;
                }
            }
            if (log) {
                String blockname = event.getBlock().getType().toString();
                blockname = blockname.replaceAll("_", " ");
                if (player.hasPermission("sgcraftlog.staff")) {
                    staff = true;
                }
                if (getConfig.logFilesEnabled()) {
                    filehandler.logBreak(playername, worldname, blockname, x, y, z, staff);
                }
                if (getConfig.MySQLEnabled()) {
                    addData.add(playername, "break", blockname, x, y, z, worldname, staff);
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onDropItem(final PlayerDropItemEvent event) {
        final Player player = event.getPlayer();
        final String playername = player.getName();
        final World world = player.getWorld();
        final String worldname = world.getName();
        Boolean staff = false;
        final int x = player.getLocation().getBlockX();
        final int y = player.getLocation().getBlockY();
        final int z = player.getLocation().getBlockZ();
        final Boolean log = false;
        if (log) {
            final Item ItemInfo = (Item)event.getItemDrop().getItemStack();
            final String ItemName = ItemInfo.getType().name();
            if (player.hasPermission("sgcraftlog.staff")) {
                staff = true;
            }
            if (getConfig.logFilesEnabled()) {
                filehandler.logDrop(playername, worldname, ItemName, x, y, z, staff);
            }
            if (getConfig.MySQLEnabled()) {
                addData.add(playername, "Drop", ItemName, x, y, z, worldname, staff);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPickupItem(final PlayerPickupItemEvent event) {
        final Player player = event.getPlayer();
        final String playername = player.getName();
        final World world = player.getWorld();
        final String worldname = world.getName();
        Boolean staff = false;
        final int x = player.getLocation().getBlockX();
        final int y = player.getLocation().getBlockY();
        final int z = player.getLocation().getBlockZ();
        final Boolean log = false;
        if (log) {
            final Item ItemInfo = (Item)event.getItem().getItemStack();
            final String ItemName = ItemInfo.getType().name();
            if (player.hasPermission("sgcraftlog.staff")) {
                staff = true;
            }
            if (getConfig.logFilesEnabled()) {
                filehandler.logPickup(playername, worldname, ItemName, x, y, z, staff);
            }
            if (getConfig.MySQLEnabled()) {
                addData.add(playername, "Pickup", ItemName, x, y, z, worldname, staff);
            }
        }
    }
}
