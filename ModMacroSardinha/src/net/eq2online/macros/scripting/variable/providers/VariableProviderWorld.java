/*     */ package net.eq2online.macros.scripting.variable.providers;
/*     */ 
/*     */ import bsu;
/*     */ import cen;
/*     */ import cew;
/*     */ import com.google.common.base.Joiner;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.scripting.variable.VariableCache;
/*     */ import vb;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VariableProviderWorld
/*     */   extends VariableCache
/*     */ {
/*  26 */   private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
/*     */   
/*  28 */   private static SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
/*     */   
/*  30 */   private static SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */   
/*  32 */   private static Pattern populationInfoPattern = Pattern.compile("[0-9]+/([0-9]+)");
/*     */   
/*  34 */   private static final String[] emptyStringArray = new String[0];
/*     */   
/*  36 */   private String[] resourcePacks = emptyStringArray;
/*     */   
/*  38 */   private Joiner glue = Joiner.on(",");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateVariables(boolean clock) {
/*  44 */     bsu minecraft = bsu.z();
/*  45 */     cen cen = AbstractionLayer.getWorld();
/*     */     
/*  47 */     if (cen != null) {
/*     */       
/*  49 */       long totalWorldTime = cen.K();
/*  50 */       int totalWorldTicks = (int)(totalWorldTime % 24000L);
/*     */       
/*  52 */       long worldTime = cen.L();
/*  53 */       int worldTicks = (int)(worldTime % 24000L);
/*  54 */       int dayTicks = (int)((worldTime + 6000L) % 24000L);
/*  55 */       int day = (int)(worldTime / 24000L);
/*     */       
/*  57 */       int dayHour = dayTicks / 1000;
/*  58 */       int dayMinute = (int)((dayTicks % 1000) * 0.06D);
/*     */       
/*  60 */       storeVariable("TOTALTICKS", totalWorldTicks);
/*  61 */       storeVariable("TICKS", worldTicks);
/*  62 */       storeVariable("DAY", day);
/*  63 */       storeVariable("DAYTICKS", dayTicks);
/*  64 */       storeVariable("DAYTIME", String.format("%02d:%02d", new Object[] { Integer.valueOf(dayHour), Integer.valueOf(dayMinute) }));
/*  65 */       storeVariable("SEED", String.valueOf(cen.J()));
/*  66 */       storeVariable("RAIN", (int)(cen.j(0.0F) * 100.0F));
/*  67 */       storeVariable("DIFFICULTY", cen.aa().name());
/*     */     }
/*     */     else {
/*     */       
/*  71 */       storeVariable("TOTALTICKS", 0);
/*  72 */       storeVariable("TICKS", 0);
/*  73 */       storeVariable("DAY", 0);
/*  74 */       storeVariable("DAYTICKS", 0);
/*  75 */       storeVariable("DAYTIME", "00:00");
/*  76 */       storeVariable("SEED", "0");
/*  77 */       storeVariable("RAIN", 0);
/*  78 */       storeVariable("DIFFICULTY", "PEACEFUL");
/*     */     } 
/*     */     
/*  81 */     storeVariable("SERVER", minecraft.E() ? "SP" : MacroModCore.getInstance().getLastServerName());
/*     */     
/*  83 */     int maxPlayers = 0;
/*     */     
/*  85 */     if (minecraft.h != null && minecraft.h.a != null) {
/*     */       
/*  87 */       maxPlayers = minecraft.h.a.a;
/*  88 */       storeVariable("ONLINEPLAYERS", minecraft.h.a.d().size());
/*     */     }
/*     */     else {
/*     */       
/*  92 */       storeVariable("ONLINEPLAYERS", 0);
/*     */     } 
/*     */     
/*  95 */     cew serverData = minecraft.C();
/*     */     
/*  97 */     if (serverData != null) {
/*     */       
/*  99 */       storeVariable("SERVERMOTD", (serverData.d == null) ? "" : serverData.d);
/* 100 */       storeVariable("SERVERNAME", (serverData.a == null) ? "" : serverData.a);
/*     */       
/* 102 */       if (serverData.c != null)
/*     */       {
/* 104 */         Matcher populationInfoMatcher = populationInfoPattern.matcher(vb.a(serverData.c));
/* 105 */         if (populationInfoMatcher.find())
/*     */         {
/* 107 */           maxPlayers = Math.max(maxPlayers, Integer.parseInt(populationInfoMatcher.group(1)));
/*     */         }
/*     */       }
/*     */     
/* 111 */     } else if (minecraft.E() && minecraft.D()) {
/*     */       
/* 113 */       storeVariable("SERVERMOTD", "");
/* 114 */       storeVariable("SERVERNAME", minecraft.F().U());
/*     */     }
/*     */     else {
/*     */       
/* 118 */       storeVariable("SERVERMOTD", "");
/* 119 */       storeVariable("SERVERNAME", "Unknown");
/*     */     } 
/*     */     
/* 122 */     storeVariable("MAXPLAYERS", maxPlayers);
/*     */     
/* 124 */     List<String> resourcePackList = minecraft.t.k;
/* 125 */     this.resourcePacks = resourcePackList.<String>toArray(emptyStringArray);
/*     */     
/* 127 */     storeVariable("RESOURCEPACKS", this.glue.join((Object[])this.resourcePacks));
/* 128 */     setCachedVariable("RESOURCEPACKS", this.resourcePacks);
/*     */     
/* 130 */     storeVariable("DATETIME", dateTimeFormatter.format(Long.valueOf(System.currentTimeMillis())));
/* 131 */     storeVariable("DATE", dateFormatter.format(Long.valueOf(System.currentTimeMillis())));
/* 132 */     storeVariable("TIME", timeFormatter.format(Long.valueOf(System.currentTimeMillis())));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getVariable(String variableName) {
/* 138 */     return getCachedValue(variableName);
/*     */   }
/*     */   
/*     */   public void onInit() {}
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\variable\providers\VariableProviderWorld.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */