package br.com.sgcraft.farmsautomaticas;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import br.com.sgcraft.farmsautomaticas.FarmsAutomaticasMain.verificacao;

public class ComandoContinuar implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (sender instanceof org.bukkit.command.ConsoleCommandSender) {

			sender.sendMessage("�cDesculpe, somente jogadores podem executar este comando!");

			return true;
		}
		if (cmd.getName().equalsIgnoreCase("cactocontinuar")) {
			if (verificacao.continuar == 1) {
				p.sendMessage("");
				p.sendMessage("�bEm seguida sera comprado um terreno e desativado o PVP.");
				p.sendMessage("");
				p.sendMessage("      �4�lMATENHA-SE PARADO NO CENTRO DO TERRENO!");
				p.sendMessage("");
				Bukkit.dispatchCommand(sender, "comprar 50 FCACTO");
				Bukkit.dispatchCommand(sender, "pvparea FCACTO off");
				p.sendMessage("");
				p.sendMessage("�aPara construir sua Farm digite: �f/cactoconstruir�a, OBS: Ser� cobrado o valor de �7650k�a. ");
	            verificacao.construir = 1;
			} else {
				p.sendMessage("�c Antes voce deve usar  �f/farms �cpara comprar sua Farm!");
			}
		}
		return false;
	}
}
