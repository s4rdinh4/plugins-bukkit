/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ import net.eq2online.macros.scripting.variable.ItemID;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScriptActionPick
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionPick(ScriptContext context) {
/* 23 */     super(context, "pick");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 29 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPermissable() {
/* 38 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 47 */     return "inventory";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 53 */     ReturnValue retVal = new ReturnValue(-1);
/*    */     
/* 55 */     for (int paramIndex = 0; paramIndex < params.length; paramIndex++) {
/*    */       
/* 57 */       String parsedParam = ScriptCore.parseVars(provider, macro, params[paramIndex], false);
/* 58 */       ItemID itemId = tryParseItemID(parsedParam);
/*    */       
/* 60 */       if (itemId.isValid() && provider.actionInventoryPick(itemId.identifier, itemId.damage)) {
/*    */         
/* 62 */         retVal.setString(itemId.identifier);
/*    */         
/*    */         break;
/*    */       } 
/*    */     } 
/* 67 */     return (IReturnValue)retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionPick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */