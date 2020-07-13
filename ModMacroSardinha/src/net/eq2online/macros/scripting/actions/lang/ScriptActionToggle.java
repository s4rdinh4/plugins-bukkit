/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionToggle
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionToggle(ScriptContext context) {
/* 14 */     super(context, "toggle");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 20 */     String flag = (params.length > 0) ? params[0] : "flag";
/* 21 */     boolean value = !provider.getFlagValue(macro, flag);
/* 22 */     provider.setFlagVariable(macro, flag, value);
/* 23 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionToggle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */