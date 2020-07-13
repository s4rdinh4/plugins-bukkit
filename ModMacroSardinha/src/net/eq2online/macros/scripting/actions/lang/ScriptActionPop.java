/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
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
/*    */ public class ScriptActionPop
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionPop(ScriptContext context) {
/* 23 */     super(context, "pop");
/*    */   }
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/*    */     ReturnValue returnValue;
/* 29 */     IReturnValue retVal = null;
/*    */     
/* 31 */     if (params.length > 0) {
/*    */       
/* 33 */       String arrayName = ScriptCore.parseVars(provider, macro, params[0], false);
/* 34 */       String poppedValue = provider.popValueFromArray(macro, arrayName);
/*    */       
/* 36 */       returnValue = new ReturnValue(poppedValue);
/*    */       
/* 38 */       if (params.length > 1) {
/*    */         
/* 40 */         String variableName = ScriptCore.parseVars(provider, macro, params[1], false).toLowerCase();
/* 41 */         ScriptCore.setVariable(provider, macro, variableName, (poppedValue != null) ? poppedValue : "");
/*    */       } 
/*    */     } 
/*    */     
/* 45 */     return (IReturnValue)returnValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionPop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */