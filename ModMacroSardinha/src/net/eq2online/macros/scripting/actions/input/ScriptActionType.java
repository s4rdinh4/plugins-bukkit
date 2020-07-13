/*    */ package net.eq2online.macros.scripting.actions.input;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionType
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionType(ScriptContext context) {
/* 15 */     super(context, "type");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 21 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPermissable() {
/* 30 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 39 */     return "input";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 45 */     if (params.length == 0) return null;
/*    */     
/* 47 */     StringBuilder sb = new StringBuilder();
/*    */     
/* 49 */     for (String param : params) {
/* 50 */       sb.append(" ").append(param);
/*    */     }
/* 52 */     provider.actionPumpCharacters(ScriptCore.parseVars(provider, macro, sb.toString().substring(1), false));
/*    */     
/* 54 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\input\ScriptActionType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */