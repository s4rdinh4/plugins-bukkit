package br.com.sgcraft.listeners;

import org.bukkit.event.inventory.*;
import org.bukkit.event.*;

public class ListenerX9 implements Listener

{
    @EventHandler
    public void onInventoryClickEvent(final InventoryClickEvent e) {
        if (e.getInventory().getName().equalsIgnoreCase("§8Inventário")){
            e.setCancelled(true);
        }
    }
}
