/*     */ package net.eq2online.macros.scripting.parser;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.permissions.MacroModPermissions;
/*     */ import net.eq2online.macros.scripting.ScriptActionBase;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionProcessor;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionStackEntry;
/*     */ import net.eq2online.macros.scripting.api.IReturnValue;
/*     */ import net.eq2online.macros.scripting.api.IScriptAction;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.variable.ItemID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScriptAction
/*     */   extends ScriptActionBase
/*     */ {
/*  31 */   protected static int tickedActionCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   protected static int execActionCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  41 */   protected static int opActionCount = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean parseVars = true;
/*     */ 
/*     */ 
/*     */   
/*     */   ScriptAction(ScriptContext context) {
/*  50 */     super(context, "NULL");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ScriptAction(ScriptContext context, String actionName) {
/*  60 */     super(context, actionName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getTickedActionCount() {
/*  70 */     return tickedActionCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onActionExecuted() {
/*  80 */     execActionCount++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getExecutedActionCount() {
/*  90 */     return execActionCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onActionSkipped() {
/* 100 */     opActionCount++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getSkippedActionCount() {
/* 110 */     return opActionCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onInit() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onStopped(IScriptActionProvider provider, IMacro macro, IMacroAction instance) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final void onTickInGame(IScriptActionProvider provider) {
/* 132 */     tickedActionCount = 0;
/* 133 */     execActionCount = 0;
/* 134 */     opActionCount = 0;
/*     */     
/* 136 */     if (provider != null) {
/* 137 */       provider.onTick();
/*     */     }
/* 139 */     for (IScriptAction action : ScriptContext.MAIN.getActionsList())
/*     */     {
/* 141 */       tickedActionCount += action.onTick(provider);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isThreadSafe() {
/* 148 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStackPushOperator() {
/* 157 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExpectedPopCommands() {
/* 165 */     return "missing statement";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStackPopOperator() {
/* 174 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBePoppedBy(IScriptAction action) {
/* 183 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean executeStackPush(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 192 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean executeStackPop(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params, IMacroAction popAction) {
/* 201 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBreak(IMacroActionProcessor processor, IScriptActionProvider provider, IMacro macro, IMacroAction instance, IMacroAction breakAction) {
/* 210 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConditionalOperator() {
/* 219 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConditionalElseOperator(IScriptAction action) {
/* 228 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesConditionalOperator(IScriptAction action) {
/* 237 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean executeConditional(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 246 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void executeConditionalElse(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params, IMacroActionStackEntry top) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 263 */     if (MacroModSettings.enableDebug) Log.info("Executed NULL script action, maybe there is an error in your macro script"); 
/* 264 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canExecuteNow(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 273 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClocked() {
/* 279 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPermissable() {
/* 288 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPermissionGroup() {
/* 297 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkExecutePermission() {
/* 306 */     return (!isPermissable() || checkPermission(this.actionName, "execute"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkPermission(String actionName, String permission) {
/* 315 */     if (permission.equals("execute")) {
/*     */       
/* 317 */       String group = getPermissionGroup();
/*     */       
/* 319 */       return MacroModPermissions.hasPermission("script." + ((group == null) ? "" : (group + ".")) + actionName.toLowerCase());
/*     */     } 
/*     */     
/* 322 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerPermissions(String actionName, String actionGroup) {
/* 331 */     String permissionPath = "script.";
/*     */     
/* 333 */     if (actionGroup != null) {
/* 334 */       permissionPath = permissionPath + actionGroup + ".";
/*     */     }
/* 336 */     MacroModPermissions.registerPermission(permissionPath + actionName.toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int onTick(IScriptActionProvider provider) {
/* 345 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static ItemID tryParseItemID(String value) {
/* 350 */     return new ItemID(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void initPermissions() {
/* 355 */     Set<String> actionGroups = new HashSet<String>();
/* 356 */     MacroModPermissions.registerPermission("script.*");
/*     */     
/* 358 */     for (ScriptContext context : ScriptContext.getAvailableContexts()) {
/*     */       
/* 360 */       if (context.isCreated())
/*     */       {
/* 362 */         for (Map.Entry<String, IScriptAction> action : context.getActions().entrySet()) {
/*     */           
/* 364 */           if (((IScriptAction)action.getValue()).isPermissable()) {
/*     */             
/* 366 */             String actionGroup = ((IScriptAction)action.getValue()).getPermissionGroup();
/* 367 */             if (actionGroup != null && !actionGroups.contains(actionGroup)) {
/*     */               
/* 369 */               actionGroups.add(actionGroup);
/* 370 */               MacroModPermissions.registerPermission("script." + actionGroup + ".*");
/*     */             } 
/*     */             
/* 373 */             ((IScriptAction)action.getValue()).registerPermissions(action.getKey(), ((IScriptAction)action.getValue()).getPermissionGroup());
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static ArrayList<String> getDeniedActionList(ScriptContext context) {
/* 382 */     ArrayList<String> deniedActions = new ArrayList<String>();
/*     */     
/* 384 */     for (IScriptAction action : context.getActionsList()) {
/*     */       
/* 386 */       if (!action.checkExecutePermission())
/*     */       {
/* 388 */         deniedActions.add(action.getName().toUpperCase());
/*     */       }
/*     */     } 
/*     */     
/* 392 */     return deniedActions;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\parser\ScriptAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */