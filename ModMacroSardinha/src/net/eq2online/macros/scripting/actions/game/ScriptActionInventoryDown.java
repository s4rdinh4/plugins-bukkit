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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScriptActionInventoryDown
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionInventoryDown(ScriptContext context) {
/* 24 */     super(context, "inventorydown");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 30 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 36 */     int count = 1;
/*    */     
/* 38 */     if (params.length > 0) {
/*    */       
/* 40 */       count = ScriptCore.tryParseInt(ScriptCore.parseVars(provider, macro, params[0], false), 0);
/* 41 */       count %= 9;
/* 42 */       if (count < 1) count = 1;
/*    */     
/*    */     } 
/* 45 */     for (int c = 0; c < count; c++) {
/* 46 */       provider.actionInventoryMove(1);
/*    */     }
/* 48 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPermissable() {
/* 57 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 66 */     return "inventory";
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionInventoryDown.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */