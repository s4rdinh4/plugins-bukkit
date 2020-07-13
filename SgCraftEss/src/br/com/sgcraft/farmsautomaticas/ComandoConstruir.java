package br.com.sgcraft.farmsautomaticas;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import br.com.sgcraft.Main;
import br.com.sgcraft.VaultAPI;
import br.com.sgcraft.farmsautomaticas.FarmsAutomaticasMain.verificacao;
import net.milkbowl.vault.economy.EconomyResponse;

@SuppressWarnings("deprecation")
public class ComandoConstruir implements CommandExecutor, Listener {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof org.bukkit.command.ConsoleCommandSender) {

			sender.sendMessage("§cDesculpe, somente jogadores podem executar este comando!");
			return true;
		}
		
		return false;
	}

	private void loadSchematic(Player player) {
		Location location = player.getLocation();
		WorldEditPlugin worldEditPlugin = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
		File schematic = new File(Main.pl.getDataFolder() + File.separator + "/schematics/farmcacto.schematic");
		EditSession session = worldEditPlugin.getWorldEdit().getEditSessionFactory()
				.getEditSession(new BukkitWorld(location.getWorld()), 999999999);
		try {
			CuboidClipboard clipboard = MCEditSchematicFormat.getFormat(schematic).load(schematic);
			clipboard.paste(session, new Vector(location.getX(), location.getY(), location.getZ()), true);
		} catch (MaxChangedBlocksException | DataException | IOException e) {
			e.printStackTrace();
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void BuildFarmCacto(final PlayerCommandPreprocessEvent e) {
		Player player = e.getPlayer();
		final String comando = e.getMessage().toLowerCase();
		if (comando.toLowerCase().startsWith("/cactoconstruir")) {
			if (verificacao.construir == 1) {
				final EconomyResponse c = VaultAPI.getEconomy().withdrawPlayer(player.getName(), 650000.0);
				if (c.transactionSuccess()) {
					player.sendMessage("§bConstruindo sua Farm... Aguarde...");
					loadSchematic(player);
					player.sendMessage("§aAproveite sua Farm!");
					Bukkit.broadcastMessage("§f[§2FARMS§f] §aO jogador §f" + player.getName() + "§a construiu uma Farm com o comando §e/farms§a.");
		            verificacao.construir = 0;
				} else {
					player.sendMessage("§cVocê não tem dinheiro, para construir essa farm você precisa ter: §7650k§c!");
				}
			} else {
				player.sendMessage("§c Antes voce deve usar  §f/farms §cpara comprar sua Farm!");
			}
			return;
		}
	}
}
