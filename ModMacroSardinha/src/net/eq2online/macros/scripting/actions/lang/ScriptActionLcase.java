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
/*    */ public class ScriptActionLcase
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionLcase(ScriptContext context) {
/* 16 */     super(context, "lcase");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 22 */     ReturnValue retVal = new ReturnValue("");
/*    */     
/* 24 */     if (params.length > 0) {
/*    */       
/* 26 */       String lowerCase = ScriptCore.parseVars(provider, macro, params[0], false).toLowerCase();
/* 27 */       retVal.setString(lowerCase);
/*    */       
/* 29 */       if (params.length > 1) {
/*    */         
/* 31 */         String variableName = ScriptCore.parseVars(provider, macro, params[1], false).toLowerCase();
/* 32 */         ScriptCore.setVariable(provider, macro, variableName, lowerCase);
/*    */       } 
/*    */     } 
/*    */     
/* 36 */     return (IReturnValue)retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionLcase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */