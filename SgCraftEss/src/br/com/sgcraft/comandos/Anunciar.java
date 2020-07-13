package br.com.sgcraft.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Anunciar implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String comando, String[] args) {
		if (!(sender instanceof Player)) {

			Bukkit.getConsoleSender().sendMessage("» Este comando esta desativado no console!");
			return true;
		}
		comando.equalsIgnoreCase("anunciar");
		Player p = (Player) sender;
		if (p.hasPermission("sgcraft.anunciar")) {
			if (args.length < 1) {
				p.sendMessage("§bUtilize: /anunciar <mensagem>");
				return true;
			} else {
				String s = "";
				byte b;
				int i;
				String[] arrayOfString;
				for (i = (arrayOfString = args).length, b = 0; b < i;) {
					String a = arrayOfString[b];
					s = String.valueOf(s) + a.replace("<3", "❤").replace("lag", "'-'").replace("**", "★")
							.replace(">>", "►").replace("<<", "◀") + " ";
					b++;
				}
				String premsg = ">> §6{player}: §f{msg}";
				premsg = premsg.replace("{player}", p.getName());
				premsg = premsg.replace("{msg}", s);
				premsg = premsg.replace("&", "§");
				String msg = premsg;
				Bukkit.broadcastMessage("§b§m--------------------------------------------------");
				Bukkit.broadcastMessage("     §f§l[§6§lVIP§f§l] " + msg);
				Bukkit.broadcastMessage("§b§m--------------------------------------------------");
			}

		} else {
			p.sendMessage("§bApenas jogadores §6[VIP] §bpodem utilizar o Anunciar!");
		}
		return true;
	}
}
