package br.com.sgcraft.listeners;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobsFix implements Listener{
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event) {
	    if (event.getEntity().getType() == EntityType.ZOMBIE) {
	        Zombie zombie = (Zombie) event.getEntity();
	        if(zombie.isBaby()) {
	            event.setCancelled(true);
	            zombie.setBaby(false);
	        }

	    }
	}
	@EventHandler
	public void onCreatureSpawn2(CreatureSpawnEvent event) {
	    if (event.getEntity().getType() == EntityType.PIG_ZOMBIE) {
	        Zombie zombie_pig = (Zombie) event.getEntity();
	        if(zombie_pig.isBaby()) {
	            event.setCancelled(true);
	            zombie_pig.setBaby(false);
	        }

	    }
	}

    @EventHandler
    public void onSpawn(final CreatureSpawnEvent e) {
        if (e.getEntityType() == EntityType.SLIME) {
            final Slime slime = (Slime)e.getEntity();
            slime.setSize(1);
        }
    }
    @EventHandler
    public void onZombiePigmanDead(final EntityDeathEvent e) {
        if (e.getEntity().getType() != EntityType.PIG_ZOMBIE) {
            return;
        }
        e.getDrops().removeIf(is -> is.getType() == Material.GOLD_SWORD);
        e.getDrops().removeIf(is -> is.getType() == Material.GOLD_INGOT);
    }
}
