/*     */ package br.com.sgcraft.Anunciar;
/*     */ 
/*     */ import net.milkbowl.vault.chat.Chat;
/*     */ import net.milkbowl.vault.economy.Economy;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.RegisteredServiceProvider;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Main
/*     */   extends JavaPlugin
/*     */   implements Listener
/*     */ {
/*  30 */   public static Economy economy = null;
/*  31 */   public static Chat chat = null;
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  35 */     Atualizar();
/*  36 */     Iniciar();
/*  37 */     setupChat();
/*  38 */     setupEconomy();
/*  39 */     saveDefaultConfig();
/*  40 */     getServer().getPluginManager().registerEvents(this, (Plugin)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  45 */     saveDefaultConfig();
/*  46 */     Atualizar();
/*     */   }
/*     */ 
/*     */   
/*     */   public void Atualizar() {
/*  51 */     BukkitScheduler update = getServer().getScheduler();
/*  52 */     update.scheduleSyncRepeatingTask((Plugin)this, new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/*  56 */             Main.this.getLogger().info("Atualizando Plugin...");
/*     */             
/*  58 */             Main.this.saveDefaultConfig();
/*     */             
/*  60 */             Main.this.getLogger().info("Plugin foi atualizado com sucesso!");
/*  61 */             Main.this.getLogger().info("Atualizacao necessaria para evitar erros!");
/*     */           }
/*  63 */         },  0L, 326000L);
/*     */   }
/*     */ 
/*     */   
/*     */   public void Iniciar() {
/*  68 */     saveDefaultConfig();
/*  69 */     getLogger().info("Plugin Ativado - by: Sardinhagamer_HD");
/*     */   }
/*     */ 
/*     */   
 private boolean setupEconomy() {
  RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
 if (economyProvider != null) {
   
economy = (Economy)economyProvider.getProvider();
 saveDefaultConfig();
 } 
return (economy != null);
}
 
   private boolean setupChat() {
    RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(Chat.class);
    if (chatProvider != null) {
      chat = (Chat)chatProvider.getProvider();
    }
    return (chat != null);
   }
   
   @SuppressWarnings("deprecation")
public boolean onCommand(CommandSender sender, Command cmd, String comando, String[] args) {
    if (!(sender instanceof Player)) {
       
      getLogger().info("» Este comando esta desativado no console!");
     return true;
     } 
    comando.equalsIgnoreCase("anunciar");
 
    
    Player p = (Player)sender;
     if (p.hasPermission("anunciar.use")) {
       
       if (args.length < 1) {
        
       p.sendMessage(String.valueOf(getConfig().getString("Name").replace("&", "§").replaceAll("%>>%", "»").replaceAll("%<<%", "«")) + " " + getConfig().getString("Mensagem1").replace("&", "§"));
         return true;
       } 
      int custo = 0;
      if (economy.getBalance(p.getName()) < custo)
      {
        p.sendMessage(String.valueOf(getConfig().getString("Name").replace("&", "§").replaceAll("%>>%", "»").replaceAll("%<<%", "«")) + " " + getConfig().getString("Mensagem2").replace("&", "§").replaceAll("@quantidade", getConfig().getString("Quantidade")));
       }
       else
      {
         String s = ""; byte b; int i; String[] arrayOfString;
        for (i = (arrayOfString = args).length, b = 0; b < i; ) { String a = arrayOfString[b];
          s = String.valueOf(s) + a.replace("<3", "❤").replace("lag", "'-'").replace(":(", "☹").replace(":)", "☺").replace("**", "★").replace(":dima:", "💎").replace(">>", "►").replace("<<", "◀") + " "; b++; }
        
        economy.withdrawPlayer(p.getName(), custo);
        String premsg = getConfig().getString("Formato");
         premsg = premsg.replace("{player}", p.getName());
         premsg = premsg.replace("{msg}", s);
         premsg = premsg.replace("&", "§");
        String msg = premsg;
				  Bukkit.broadcastMessage("§5§m--------------------------------------------------");
         Bukkit.broadcastMessage("     §f§l[§6§lVIP§f§l] "+msg);
				  Bukkit.broadcastMessage("§5§m--------------------------------------------------");
       }
    
     } else {
       
     p.sendMessage(String.valueOf(getConfig().getString("Name").replace("&", "§").replaceAll("%>>%", "»").replaceAll("%<<%", "«")) + " " + getConfig().getString("Mensagem3").replace("&", "§"));
    } 
     return true;
   }
 }

