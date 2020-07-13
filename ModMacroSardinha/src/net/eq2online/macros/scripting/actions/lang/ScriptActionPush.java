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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScriptActionPush
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionPush(ScriptContext context) {
/* 20 */     super(context, "push");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 29 */     if (params.length > 0) {
/*    */       
/* 31 */       String arrayName = ScriptCore.parseVars(provider, macro, params[0], false);
/* 32 */       String variableValue = (params.length > 1) ? ScriptCore.parseVars(provider, macro, params[1], false) : null;
/*    */       
/* 34 */       provider.pushValueToArray(macro, arrayName, variableValue);
/*    */     } 
/*    */     
/* 37 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionPush.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */