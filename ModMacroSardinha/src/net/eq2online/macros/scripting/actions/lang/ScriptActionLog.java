/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.console.Log;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionLog
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionLog(ScriptContext context) {
/* 16 */     super(context, "log");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 22 */     if (params.length == 0) return null;
/*    */     
/* 24 */     String logMessage = ScriptCore.parseVars(provider, macro, rawParams, false).replaceAll("(?<!&)&([0-9a-fklmnor])", "§$1").replaceAll("&&", "&");
/*    */     
/* 26 */     provider.actionAddChatMessage("§b" + logMessage);
/* 27 */     Log.info("[LOG] {0}", new Object[] { logMessage.replaceAll("§[0-9a-fklmnor]", "") });
/*    */     
/* 29 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionLog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */