/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScriptActionInventoryUp
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionInventoryUp(ScriptContext context) {
/* 21 */     super(context, "inventoryup");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 27 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 33 */     int count = 1;
/*    */     
/* 35 */     if (params.length > 0) {
/*    */       
/* 37 */       count = ScriptCore.tryParseInt(ScriptCore.parseVars(provider, macro, params[0], false), 0);
/* 38 */       count %= 9;
/* 39 */       if (count < 1) count = 1;
/*    */     
/*    */     } 
/* 42 */     for (int c = 0; c < count; c++) {
/* 43 */       provider.actionInventoryMove(-1);
/*    */     }
/* 45 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPermissable() {
/* 54 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 63 */     return "inventory";
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionInventoryUp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */