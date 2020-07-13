/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionInc
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionInc(ScriptContext context) {
/* 15 */     super(context, "inc");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 21 */     int increment = 1;
/* 22 */     String counter = (params.length > 0) ? ScriptCore.parseVars(provider, macro, params[0], false) : "counter";
/*    */     
/* 24 */     if (params.length > 1) {
/* 25 */       increment = ScriptCore.tryParseInt(ScriptCore.parseVars(provider, macro, params[1], false), 1);
/*    */     }
/* 27 */     provider.incrementCounterVariable(macro, counter, increment);
/*    */     
/* 29 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionInc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */