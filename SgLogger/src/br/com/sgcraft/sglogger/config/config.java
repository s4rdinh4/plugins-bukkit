package br.com.sgcraft.sglogger.config;

import java.util.*;

import br.com.sgcraft.sglogger.*;

public class config
{
    Main plugin;
    
    public config(final Main instance) {
        this.plugin = instance;
    }
    
    public static void LoadConfiguration() {
        final List<String> words = new ArrayList<String>();
        words.add("7");
        words.add("46");
        words.add("57");
        final List<String> cmds = new ArrayList<String>();
        cmds.add("/login");
        cmds.add("/changepassword");
        cmds.add("/register");
        final String path1 = "Log.EntradaPlayer";
        final String path2 = "Log.SaidaPlayer";
        final String path3 = "Log.Chat";
        final String path4 = "Log.Comandos";
        final String path5 = "Log.Mortes";
        final String path6 = "Log.Encantamentos";
        final String path7 = "Log.Balde";
        final String path8 = "Log.Placas";
        final String path9 = "Log.Pvp";
        final String path10 = "Log.Console";
        final String path11 = "Log.Formato_Data";
        final String path12 = "BlackList.Ativado";
        final String path13 = "BlackList.Blocos";
        final String path14 = "Comandos.SalvarLogArquivo";
        final String path15 = "Comandos.SalvarLogMySQL";
        final String path16 = "Comandos.NaoSalvarLog";
        final String path17 = "Log.PastaStaffSeparada";
        final String path18 = "Log.Nomes_em_Minusculo";
        final String path19 = "File.Ativado";
        final String path20 = "Log.ApenasSTAFF";
        final String path21 = "MySQL.Ativado";
        final String path22 = "MySQL.Server";
        final String path23 = "MySQL.Database";
        final String path24 = "MySQL.User";
        final String path25 = "MySQL.Password";
        Main.plugin.getConfig().addDefault(path19, (Object)true);
        Main.plugin.getConfig().addDefault(path1, (Object)true);
        Main.plugin.getConfig().addDefault(path2, (Object)true);
        Main.plugin.getConfig().addDefault(path3, (Object)true);
        Main.plugin.getConfig().addDefault(path4, (Object)true);
        Main.plugin.getConfig().addDefault(path5, (Object)true);
        Main.plugin.getConfig().addDefault(path6, (Object)true);
        Main.plugin.getConfig().addDefault(path9, (Object)true);
        Main.plugin.getConfig().addDefault(path7, (Object)true);
        Main.plugin.getConfig().addDefault(path10, (Object)true);
        Main.plugin.getConfig().addDefault(path11, (Object)"DD/mm/yy HH:mm");
        Main.plugin.getConfig().addDefault(path12, (Object)true);
        Main.plugin.getConfig().addDefault(path13, (Object)words);
        Main.plugin.getConfig().addDefault(path8, (Object)true);
        Main.plugin.getConfig().addDefault(path14, (Object)false);
        Main.plugin.getConfig().addDefault(path15, (Object)false);
        Main.plugin.getConfig().addDefault(path16, (Object)cmds);
        Main.plugin.getConfig().addDefault(path17, (Object)true);
        Main.plugin.getConfig().addDefault(path18, (Object)false);
        Main.plugin.getConfig().addDefault(path20, (Object)false);
        Main.plugin.getConfig().addDefault(path21, (Object)false);
        Main.plugin.getConfig().addDefault(path22, (Object)"IP do MySQL");
        Main.plugin.getConfig().addDefault(path23, (Object)"Nome do Banco");
        Main.plugin.getConfig().addDefault(path24, (Object)"Usuario");
        Main.plugin.getConfig().addDefault(path25, (Object)"Senha");
        Main.plugin.getConfig().options().copyDefaults(true);
        Main.plugin.saveConfig();
    }
}
