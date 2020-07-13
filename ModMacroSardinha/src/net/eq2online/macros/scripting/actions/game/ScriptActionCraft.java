/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import cio;
/*    */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.crafting.AutoCraftingToken;
/*    */ import net.eq2online.macros.scripting.crafting.IAutoCraftingInitiator;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ import net.eq2online.macros.scripting.variable.ItemID;
/*    */ 
/*    */ public class ScriptActionCraft
/*    */   extends ScriptActionSlotClick
/*    */   implements IAutoCraftingInitiator
/*    */ {
/*    */   public ScriptActionCraft(ScriptContext context) {
/* 20 */     super(context, "craft");
/*    */   }
/*    */ 
/*    */   
/*    */   protected ScriptActionCraft(ScriptContext context, String actionName) {
/* 25 */     super(context, actionName);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 34 */     return "craft";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 40 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 49 */     doCrafting(provider, macro, params);
/* 50 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected AutoCraftingToken doCrafting(IScriptActionProvider provider, IMacro macro, String[] params) {
/* 59 */     if (params.length > 0) {
/*    */       
/* 61 */       cio thePlayer = AbstractionLayer.getPlayer();
/*    */       
/* 63 */       ItemID itemId = tryParseItemID(ScriptCore.parseVars(provider, macro, params[0], false));
/*    */       
/* 65 */       int amount = 1;
/* 66 */       if (params.length > 1) amount = Math.min(Math.max(ScriptCore.tryParseInt(ScriptCore.parseVars(provider, macro, params[1], false), 1), 1), 999);
/*    */       
/* 68 */       boolean shouldThrowResult = false;
/* 69 */       if (params.length > 2) {
/*    */         
/* 71 */         String shouldThrowArg = ScriptCore.parseVars(provider, macro, params[2], false);
/* 72 */         shouldThrowResult = (shouldThrowArg.toLowerCase().equals("true") || shouldThrowArg.equals("1"));
/*    */       } 
/*    */       
/* 75 */       boolean verbose = false;
/* 76 */       if (params.length > 3) {
/*    */         
/* 78 */         String verboseArg = ScriptCore.parseVars(provider, macro, params[3], false);
/* 79 */         verbose = (verboseArg.toLowerCase().equals("true") || verboseArg.equals("1"));
/*    */       } 
/*    */       
/* 82 */       return provider.actionCraft(this, thePlayer, itemId.identifier, itemId.damage, amount, shouldThrowResult, verbose);
/*    */     } 
/*    */     
/* 85 */     return new AutoCraftingToken(null, "BADPARAMS");
/*    */   }
/*    */   
/*    */   public void notifyTokenProcessed(AutoCraftingToken token, String reason) {}
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionCraft.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */