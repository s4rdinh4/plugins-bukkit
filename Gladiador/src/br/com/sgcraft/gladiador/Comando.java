package br.com.sgcraft.gladiador;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import java.text.*;
import net.sacredlabyrinth.phaed.simpleclans.*;
import org.bukkit.inventory.*;

import br.com.sgcraft.gladiador.mensagens.*;
import br.com.sgcraft.gladiador.utils.*;

import java.util.*;
import org.bukkit.*;

public class Comando implements CommandExecutor
{
    private Main plugin;
    public static SQLite sqlite;
    
    static {
        Comando.sqlite = new SQLite();
    }
    
    public Comando(final Main main) {
        this.plugin = main;
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("gladiadores")) {
            if (args.length == 0) {
                final List<String> l = (List<String>)this.plugin.getConfig().getStringList("Vencedores");
                if (l.size() == 0) {
                    sender.sendMessage(Mensagens.getMensagem("Glads1"));
                    return true;
                }
                if (l.size() == 1) {
                    sender.sendMessage(Mensagens.getMensagem("Glads2").replace("@nome", l.get(0)));
                }
                else {
                    sender.sendMessage(Mensagens.getMensagem("Glads3").replace("@nome1", l.get(0)).replace("@nome2", l.get(1)));
                }
            }
            return true;
        }
        if (!cmd.getName().equalsIgnoreCase("gladiador")) {
            return false;
        }
        if (args.length == 0) {
            if (sender == this.plugin.getServer().getConsoleSender()) {
                sender.sendMessage("§7[§6Gladiador§7] §fConsole bloqueado de executar o comando!");
                return true;
            }
            if (this.plugin.getGladiadorEtapa() == 0) {
                sender.sendMessage(Mensagens.getMensagem("Erro1"));
                return true;
            }
            if (this.plugin.getGladiadorEtapa() > 1) {
                sender.sendMessage(Mensagens.getMensagem("Erro2"));
                return true;
            }
            if (this.plugin.participantes.contains(sender.getName())) {
                sender.sendMessage(Mensagens.getMensagem("Erro3"));
                return true;
            }
            if (this.plugin.getConfig().contains("Bans." + sender.getName().toLowerCase())) {
                sender.sendMessage(Mensagens.getMensagem("Erro4_1"));
                sender.sendMessage(Mensagens.getMensagem("Erro4_2").replace("@nome", this.plugin.getConfig().getString("Bans." + sender.getName().toLowerCase() + ".Por")).replace("@data", this.plugin.getConfig().getString("Bans." + sender.getName().toLowerCase() + ".Data")));
                return true;
            }
            if (this.plugin.core1.getClanManager().getClanPlayer((Player)sender) == null) {
                sender.sendMessage(Mensagens.getMensagem("Erro5"));
                return true;
            }
            if (((Player)sender).isInsideVehicle()) {
                sender.sendMessage(Mensagens.getMensagem("Erro6"));
                return true;
            }
            final String tag = this.plugin.core1.getClanManager().getClanByPlayerName(((Player)sender).getName()).getColorTag();
            if (this.plugin.clann.containsKey(tag) && this.plugin.clann.get(tag) >= this.plugin.getConfig().getInt("Config.Limite")) {
                sender.sendMessage("§7[§6Gladiador§7] §cO seu clan j\u00e1 atingiu o m\u00e1ximo de membros no gladiador!");
                return true;
            }
            this.plugin.addPlayer((Player)sender);
            return true;
        }
        else {
            if (args.length == 2 && args[0].equalsIgnoreCase("apostar")) {
                final Clan clan = this.plugin.core1.getClanManager().getClan(args[1].toString());
                this.plugin.betting((Player)sender, clan);
                return true;
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("apostar")) {
                if (this.plugin.getGladiadorEtapa() != 3) {
                    sender.sendMessage("§7[§6Gladiador§7]  §cO gladiador nao esta ocorrendo!");
                    return true;
                }
                sender.sendMessage("§7[§6Gladiador§7] §eClans Participando:");
                sender.sendMessage(this.plugin.getClansParticipando_core2().toString().replace("[", "").replace("]", ""));
                return true;
            }
            else if (args[0].equalsIgnoreCase("sair")) {
                if (this.plugin.getGladiadorEtapa() == 0) {
                    sender.sendMessage(Mensagens.getMensagem("Erro1"));
                    return true;
                }
                if (this.plugin.getGladiadorEtapa() != 1) {
                    sender.sendMessage(Mensagens.getMensagem("Erro7"));
                    return true;
                }
                final String tag = this.plugin.core1.getClanManager().getClanByPlayerName(((Player)sender).getName()).getColorTag();
                if (this.plugin.clann.get(tag) > 1) {
                    this.plugin.clann.put(tag, this.plugin.clann.get(tag) - 1);
                }
                else {
                    this.plugin.clann.remove(tag);
                }
                this.plugin.removePlayer((Player)sender, 0);
                return true;
            }
            else {
                if (args[0].equalsIgnoreCase("top")) {
                    final Player p = (Player)sender;
                    if (this.plugin.getConfig().getBoolean("MySQL.Ativado")) {
                        this.plugin.getMysql().getTOPWins(p);
                    }
                    else {
                        this.plugin.getSqlite().getTOPWins(p);
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("camarote")) {
                    if (!sender.hasPermission("gladiador.camarote")) {
                        sender.sendMessage(Mensagens.getMensagem("Erro8"));
                        return true;
                    }
                    if (this.plugin.getGladiadorEtapa() < 2) {
                        sender.sendMessage(Mensagens.getMensagem("Erro1"));
                        return true;
                    }
                    if (this.plugin.participantes.contains(sender.getName())) {
                        sender.sendMessage(Mensagens.getMensagem("Erro9"));
                        return true;
                    }
                    final ItemStack[] armors = ((Player)sender).getInventory().getArmorContents();
                    final ItemStack[] contents = ((Player)sender).getInventory().getContents();
                    if (this.checkItemStacks(armors) || this.checkItemStacks(contents)) {
                        ((Player)sender).sendMessage("§7[§6Gladiador§7] §cRemova todos items de seu invet\u00e1rio para entrar no camarote!");
                        return true;
                    }
                    this.plugin.addSpectator((Player)sender);
                    sender.sendMessage(Mensagens.getMensagem("Msg1"));
                    return true;
                }
                else {
                    if (!sender.hasPermission("gladiador.admin")) {
                        sender.sendMessage(Mensagens.getMensagem("Erro10"));
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("forcestart")) {
                        if (this.plugin.getGladiadorEtapa() != 0) {
                            sender.sendMessage("§7[§6Gladiador§7] §cJa existe um evento gladiador sendo executado!");
                            return true;
                        }
                        if (this.plugin.getGladiadorEtapa() == 0 && !this.plugin.canStart) {
                            sender.sendMessage("§7[§6Gladiador§7] §cUm evento gladiador esta sendo finalizado!");
                            return true;
                        }
                        sender.sendMessage("§7[§6Gladiador§7] §aEvento gladiador sendo iniciado!");
                        this.plugin.prepareGladiador();
                        return true;
                    }
                    else if (args[0].equalsIgnoreCase("reset")) {
                        if (sender.hasPermission("gladiador.reset")) {
                            if (this.plugin.getConfig().getBoolean("MySQL.Ativado")) {
                                this.plugin.getMysql().purgeRows();
                            }
                            else {
                                this.plugin.getSqlite().purgeRows();
                            }
                            sender.sendMessage("§7[§6Gladiador§7] §aOs TOPs ganhadores foram resetados!");
                            return true;
                        }
                        sender.sendMessage(Mensagens.getMensagem("Erro10"));
                        return true;
                    }
                    else if (args[0].equalsIgnoreCase("forcestop")) {
                        if (this.plugin.getGladiadorEtapa() == 0) {
                            sender.sendMessage(ChatColor.AQUA + "[GLADIADOR] " + ChatColor.RED + "Nao existe nenhum evento gladiador sendo executado!");
                            return true;
                        }
                        this.plugin.cancelGladiador();
                        sender.sendMessage("§7[§6Gladiador§7] §cEvento gladiador sendo parado!");
                        Main.pl.getServer().dispatchCommand((CommandSender)Main.pl.getServer().getConsoleSender(), "tm bc &6&lGLADIADOR%nl%&cEvento &4&lCANCELADO" );
                        return true;
                    }
                    else if (args[0].equalsIgnoreCase("kick")) {
                        if (args.length < 2) {
                            sender.sendMessage("§7[§6Gladiador§7] §e/gladiador kick <nome>");
                            return true;
                        }
                        final String nome = args[1].toLowerCase();
                        final Player p2 = this.plugin.getServer().getPlayer(nome);
                        if (p2 == null) {
                            sender.sendMessage("§7[§6Gladiador§7] §cJogador nao encontrado!");
                            return true;
                        }
                        this.plugin.removePlayer(p2, 3);
                        sender.sendMessage(" §7[§6Gladiador§7] §c" + nome + " foi kickado do evento gladiador!");
                        return true;
                    }
                    else if (args[0].equalsIgnoreCase("info")) {
                        if (this.plugin.getGladiadorEtapa() != 3) {
                            sender.sendMessage("§7[§6Gladiador§7] §cO evento gladiador nao comecou!");
                            return true;
                        }
                        final StringBuilder sb = new StringBuilder();
                        for (final String xx : this.plugin.clann.keySet()) {
                            sb.append(String.valueOf(String.valueOf(xx.replace("[", "").replace("]", ""))) + " §e[" + this.plugin.clann.get(xx) + "], ");
                        }
                        sender.sendMessage("§7[§6Gladiador§7] §fRestam os clans " + sb.toString());
                        final StringBuilder sab = new StringBuilder();
                        for (final String s : this.plugin.participantes) {
                            sb.append(String.valueOf(String.valueOf(s.replace("[", "").replace("]", ""))) + ", ");
                        }
                        sender.sendMessage("§7[§6Gladiador§7] §fRestam os jogadores " + sab.toString());
                        return true;
                    }
                    else if (args[0].equalsIgnoreCase("ban")) {
                        if (args.length < 2) {
                            sender.sendMessage("§7[§6Gladiador§7] §c/gladiador ban <nome>");
                            return true;
                        }
                        final String nome = args[1].toLowerCase();
                        this.plugin.getConfig().set("Bans." + nome + ".Por", (Object)sender.getName());
                        this.plugin.getConfig().set("Bans." + nome + ".Data", (Object)new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
                        this.plugin.saveConfig();
                        final Player p2 = this.plugin.getServer().getPlayerExact(nome);
                        if (p2 != null) {
                            this.plugin.removePlayer(p2, 3);
                        }
                        sender.sendMessage("§7[§6Gladiador§7] §c" + nome + " foi banido dos eventos gladiadores!");
                        return true;
                    }
                    else if (args[0].equalsIgnoreCase("unban")) {
                        if (args.length < 2) {
                            sender.sendMessage("§7[§6Gladiador§7] §c/gladiador unban <nome>");
                            return true;
                        }
                        final String nome = args[1].toLowerCase();
                        if (!this.plugin.getConfig().contains("Bans." + nome)) {
                            sender.sendMessage("§7[§6Gladiador§7] §cNome nao encontrado!");
                            return true;
                        }
                        this.plugin.getConfig().set("Bans." + nome, (Object)null);
                        this.plugin.saveConfig();
                        sender.sendMessage("§7[§6Gladiador§7] §a" + nome + " foi desbanido dos eventos gladiadores!");
                        return true;
                    }
                    else if (args[0].equalsIgnoreCase("setspawn")) {
                        if (sender == this.plugin.getServer().getConsoleSender()) {
                            sender.sendMessage("§7[§6Gladiador§7] §cConsole bloqueado de executar o comando!");
                            return true;
                        }
                        final Player p = (Player)sender;
                        this.plugin.spawn = p.getLocation();
                        this.plugin.getConfig().set("Arena.Entrada", (Object)(String.valueOf(String.valueOf(this.plugin.spawn.getWorld().getName())) + ";" + this.plugin.spawn.getX() + ";" + this.plugin.spawn.getY() + ";" + this.plugin.spawn.getZ() + ";" + this.plugin.spawn.getYaw() + ";" + this.plugin.spawn.getPitch()));
                        this.plugin.saveConfig();
                        sender.sendMessage("§7[§6Gladiador§7] §aSpawn marcado!");
                        return true;
                    }
                    else if (args[0].equalsIgnoreCase("setsaida")) {
                        if (sender == this.plugin.getServer().getConsoleSender()) {
                            sender.sendMessage("§7[§6Gladiador§7] §cConsole bloqueado de executar o comando!");
                            return true;
                        }
                        final Player p = (Player)sender;
                        this.plugin.saida = p.getLocation();
                        this.plugin.getConfig().set("Arena.Saida", (Object)(String.valueOf(String.valueOf(this.plugin.saida.getWorld().getName())) + ";" + this.plugin.saida.getX() + ";" + this.plugin.saida.getY() + ";" + this.plugin.saida.getZ() + ";" + this.plugin.saida.getYaw() + ";" + this.plugin.saida.getPitch()));
                        this.plugin.saveConfig();
                        sender.sendMessage("§7[§6Gladiador§7] §aSaida marcada!");
                        return true;
                    }
                    else if (args[0].equalsIgnoreCase("setcamarote")) {
                        if (sender == this.plugin.getServer().getConsoleSender()) {
                            sender.sendMessage("§7[§6Gladiador§7] §cConsole bloqueado de executar o comando!");
                            return true;
                        }
                        final Player p = (Player)sender;
                        this.plugin.camarote = p.getLocation();
                        this.plugin.getConfig().set("Arena.Camarote", (Object)(String.valueOf(String.valueOf(this.plugin.camarote.getWorld().getName())) + ";" + this.plugin.camarote.getX() + ";" + this.plugin.camarote.getY() + ";" + this.plugin.camarote.getZ() + ";" + this.plugin.camarote.getYaw() + ";" + this.plugin.camarote.getPitch()));
                        this.plugin.saveConfig();
                        sender.sendMessage("§7[§6Gladiador§7] §aCamarote marcado!");
                        return true;
                    }
                    else {
                        if (!args[0].equalsIgnoreCase("reload")) {
                            this.sendHelp((Player)sender);
                            return true;
                        }
                        if (this.plugin.getGladiadorEtapa() != 0) {
                            sender.sendMessage("§7[§6Gladiador§7] §cNao existe um evento gladiador acontecendo!");
                            return true;
                        }
                        this.plugin.reloadConfig();
                        Mensagens.loadMensagens();
                        final String[] ent = this.plugin.getConfig().getString("Arena.Entrada").split(";");
                        this.plugin.spawn = new Location(this.plugin.getServer().getWorld(ent[0]), Double.parseDouble(ent[1]), Double.parseDouble(ent[2]), Double.parseDouble(ent[3]), Float.parseFloat(ent[4]), Float.parseFloat(ent[5]));
                        final String[] sai = this.plugin.getConfig().getString("Arena.Saida").split(";");
                        this.plugin.saida = new Location(this.plugin.getServer().getWorld(sai[0]), Double.parseDouble(sai[1]), Double.parseDouble(sai[2]), Double.parseDouble(sai[3]), Float.parseFloat(sai[4]), Float.parseFloat(sai[5]));
                        final String[] cam = this.plugin.getConfig().getString("Arena.Camarote").split(";");
                        this.plugin.camarote = new Location(this.plugin.getServer().getWorld(cam[0]), Double.parseDouble(cam[1]), Double.parseDouble(cam[2]), Double.parseDouble(cam[3]), Float.parseFloat(cam[4]), Float.parseFloat(cam[5]));
                        sender.sendMessage("§7[§6Gladiador§7] §aConfiguracao recarregada!");
                        return true;
                    }
                }
            }
        }
    }
    
    public boolean checkItemStacks(final ItemStack[] ises) {
        for (final ItemStack is : ises) {
            if (is != null && is.getType() != Material.AIR) {
                return true;
            }
        }
        return false;
    }
    
    private void sendHelp(final Player p) {
        p.sendMessage(ChatColor.DARK_AQUA + "[GLADIADOR] Comandos do plugin:");
        p.sendMessage(ChatColor.AQUA + "/gladiador ? " + ChatColor.WHITE + "- Lista de comandos");
        p.sendMessage(ChatColor.AQUA + "/gladiador forcestart " + ChatColor.WHITE + "- Inicia o evento gladiador");
        p.sendMessage(ChatColor.AQUA + "/gladiador forcestop " + ChatColor.WHITE + "- Para o evento gladiador");
        p.sendMessage(ChatColor.AQUA + "/gladiador kick <nome> " + ChatColor.WHITE + "- Kicka um jogador do evento gladiador");
        p.sendMessage(ChatColor.AQUA + "/gladiador reset " + ChatColor.WHITE + "- Zera as vit\u00f3rias do Gladiador TOP");
        p.sendMessage(ChatColor.AQUA + "/gladiador ban <nome> " + ChatColor.WHITE + "- Bane um jogador do evento gladiador");
        p.sendMessage(ChatColor.AQUA + "/gladiador unban <nome> " + ChatColor.WHITE + "- Desbane um jogador do evento gladiador");
        p.sendMessage(ChatColor.AQUA + "/gladiador setspawn " + ChatColor.WHITE + "- Marca local de spawn do evento gladiador");
        p.sendMessage(ChatColor.AQUA + "/gladiador setsaida " + ChatColor.WHITE + "- Marca local de saida do evento gladiador");
        p.sendMessage(ChatColor.AQUA + "/gladiador setcamarote " + ChatColor.WHITE + "- Marca local do camarote do evento gladiador");
        p.sendMessage(ChatColor.AQUA + "/gladiador info " + ChatColor.WHITE + "- Mostra quantos jogadores estao dentro do evento gladiador");
        p.sendMessage(ChatColor.AQUA + "/gladiador reload " + ChatColor.WHITE + "- Recarrega a configuracao");
        p.sendMessage(ChatColor.RED + "/castelo spawn " + ChatColor.WHITE + "- Reseta os baus");
        p.sendMessage(ChatColor.RED + "/castelo setbau " + ChatColor.WHITE + "- Marca o local do bau");
        p.sendMessage(ChatColor.RED + "/castelo setcastelo " + ChatColor.WHITE + "- Marca o local do castelo");
        p.sendMessage(ChatColor.RED + "/castelo " + ChatColor.WHITE + "- vai para o castelo");
    }
}
