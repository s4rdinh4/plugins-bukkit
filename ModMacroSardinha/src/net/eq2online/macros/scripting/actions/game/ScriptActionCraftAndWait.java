/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.crafting.AutoCraftingToken;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionCraftAndWait
/*    */   extends ScriptActionCraft
/*    */ {
/*    */   public ScriptActionCraftAndWait(ScriptContext context) {
/* 14 */     super(context, "craftandwait");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canExecuteNow(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 23 */     if (params.length > 0) {
/*    */       
/* 25 */       AutoCraftingToken token = null;
/*    */       
/* 27 */       if (instance.getState() == null) {
/*    */         
/* 29 */         token = doCrafting(provider, macro, params);
/* 30 */         if (token == null) return true;
/*    */         
/* 32 */         instance.setState(token);
/* 33 */         return token.isCompleted();
/*    */       } 
/*    */       
/* 36 */       token = (AutoCraftingToken)instance.getState();
/* 37 */       return (token == null || token.isCompleted());
/*    */     } 
/*    */     
/* 40 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 49 */     return null;
/*    */   }
/*    */   
/*    */   public void notifyTokenProcessed(AutoCraftingToken token, String reason) {}
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionCraftAndWait.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */