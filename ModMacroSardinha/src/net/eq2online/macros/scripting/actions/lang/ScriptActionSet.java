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
/*    */ public class ScriptActionSet
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionSet(ScriptContext context) {
/* 15 */     super(context, "set");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 21 */     String variableName = (params.length > 0) ? ScriptCore.parseVars(provider, macro, params[0], false) : "flag";
/* 22 */     String variableValue = (params.length > 1) ? ScriptCore.parseVars(provider, macro, params[1], false) : null;
/*    */     
/* 24 */     ScriptCore.setVariable(provider, macro, variableName, variableValue);
/*    */     
/* 26 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */