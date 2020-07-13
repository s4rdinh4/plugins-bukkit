package br.com.sgcraft.comandos;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import br.com.sgcraft.Main;

public class ComandoX9 implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("x9")) {
            final Player p = (Player)sender;
            if (p.hasPermission("sgcraft.invsee")) {
                if (args.length == 0) {
                    p.sendMessage(String.valueOf(Main.prefix) + " §bUtilize: §c/x9 <nick>");
                }
                else if (args.length == 1) {
                    final Player t = Bukkit.getPlayer(args[0]);
                    if (t == null) {
                        p.sendMessage(String.valueOf(Main.prefix) + Main.playerNotOnline);
                        return true;
                    }
                    if(t == p) {
                    p.sendMessage(String.valueOf(Main.prefix) + " §cVoce nao pode abrir seu inventario!");
                    }
                    else {
                    final Inventory inv = Bukkit.createInventory((InventoryHolder)null, 54, "§8Inventário");
                    inv.setItem(0, t.getInventory().getHelmet());
                    inv.setItem(1, GiveItem(Material.STAINED_GLASS_PANE, "§3", 1, 15));
                    inv.setItem(2, t.getInventory().getChestplate());
                    inv.setItem(3, GiveItem(Material.STAINED_GLASS_PANE, "§3", 1, 15));
                    inv.setItem(6, t.getInventory().getLeggings());
                    inv.setItem(5, GiveItem(Material.STAINED_GLASS_PANE, "§3", 1, 15));
                    inv.setItem(8, t.getInventory().getBoots());
                    inv.setItem(7, GiveItem(Material.STAINED_GLASS_PANE, "§3", 1, 15));
                    final ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
                    final SkullMeta sk = (SkullMeta)skull.getItemMeta();
                    sk.setOwner(t.getName());
                    sk.setDisplayName("§b"+t.getName());
                    ArrayList<String> sklore = new ArrayList<>();
                    sklore.add("§fDinheiro: §a");
                    sk.setLore(sklore);
                    skull.setItemMeta((ItemMeta)sk);
                    inv.setItem(4, skull);
                    inv.setItem(9, GiveItem(Material.STAINED_GLASS_PANE, "§3", 1, 14));
                    inv.setItem(10, GiveItem(Material.STAINED_GLASS_PANE, "§3", 1, 14));
                    inv.setItem(11, GiveItem(Material.STAINED_GLASS_PANE, "§3", 1, 14));
                    inv.setItem(12, GiveItem(Material.STAINED_GLASS_PANE, "§3", 1, 14));
                    inv.setItem(13, GiveItem(Material.STAINED_GLASS_PANE, "§3", 1, 14));
                    inv.setItem(14, GiveItem(Material.STAINED_GLASS_PANE, "§3", 1, 14));
                    inv.setItem(15, GiveItem(Material.STAINED_GLASS_PANE, "§3", 1, 14));
                    inv.setItem(16, GiveItem(Material.STAINED_GLASS_PANE, "§3", 1, 14));
                    inv.setItem(17, GiveItem(Material.STAINED_GLASS_PANE, "§3", 1, 14));
                    int a = 18;
                    for (int i = 0; i < t.getInventory().getContents().length; ++i) {
                        inv.setItem(a, t.getInventory().getItem(i));
                        ++a;
                    }
                    Main.open.put(p, t);
                    p.openInventory(inv);
                }
                }
                else {
                    p.sendMessage(String.valueOf(Main.prefix) + Main.use);
                }
            }
            else {
                p.sendMessage(String.valueOf(Main.prefix) + Main.no_perm);
            }
        }
        return false;
    }
       
    public static ItemStack GiveItem(final Material mat, final String Name, final int amo, final int subid) {
        final ItemStack is = new ItemStack(mat, amo, (short)subid);
        final ItemMeta im = is.getItemMeta();
        im.setDisplayName(Name);
        is.setItemMeta(im);
        return is;
    }
}

