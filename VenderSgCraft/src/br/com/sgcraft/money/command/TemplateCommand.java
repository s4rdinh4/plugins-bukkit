package br.com.sgcraft.money.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import net.eduard.api.setup.manager.CommandManager;

public class TemplateCommand extends CommandManager  {

	public TemplateCommand() {
		super("vender");
	}
	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {
	
		return true;
	}

}