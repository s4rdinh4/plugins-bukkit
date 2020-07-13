/*     */ package net.eq2online.macros.permissions;
/*     */ 
/*     */ import bsu;
/*     */ import com.mumfrey.liteloader.Permissible;
/*     */ import com.mumfrey.liteloader.permissions.PermissionsManagerClient;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
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
/*     */ 
/*     */ public abstract class MacroModPermissions
/*     */ {
/*     */   public static final String MOD_NAME = "macros";
/*     */   private static PermissionsManagerClient permissionsManager;
/*     */   private static Permissible mod;
/*     */   private static boolean initDone;
/*     */   
/*     */   public static void init(Permissible mod, PermissionsManagerClient permissionsManager) {
/*  41 */     if (!initDone || MacroModPermissions.permissionsManager != permissionsManager) {
/*     */       
/*  43 */       initDone = true;
/*  44 */       MacroModPermissions.mod = mod;
/*  45 */       MacroModPermissions.permissionsManager = permissionsManager;
/*     */       
/*  47 */       initPermissions();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initPermissions() {
/*  56 */     registerPermission("*");
/*     */     
/*  58 */     MacroModCore.getMacroManager().getEventManager().initPermissions();
/*  59 */     ScriptAction.initPermissions();
/*     */   }
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
/*     */   public static void refreshPermissions(bsu minecraft) {
/*     */     try {
/*  73 */       permissionsManager.tamperCheck();
/*  74 */       permissionsManager.sendPermissionQuery(mod);
/*     */     }
/*  76 */     catch (IllegalArgumentException illegalArgumentException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long getLastUpdateTime() {
/*  86 */     return permissionsManager.getPermissionUpdateTime(mod).longValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerPermission(String permission) {
/*  96 */     permissionsManager.tamperCheck();
/*  97 */     permissionsManager.registerModPermission(mod, permission);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasPermission(String permission) {
/* 108 */     permissionsManager.tamperCheck();
/* 109 */     return permissionsManager.getModPermission(mod, permission);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\permissions\MacroModPermissions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */