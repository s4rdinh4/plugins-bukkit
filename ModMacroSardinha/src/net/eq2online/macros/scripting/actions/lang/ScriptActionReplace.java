/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.Variable;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionReplace
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionReplace(ScriptContext context) {
/* 17 */     super(context, "replace");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/*    */     ReturnValue returnValue;
/* 26 */     IReturnValue retVal = null;
/*    */     
/* 28 */     if (params.length > 1) {
/*    */       
/* 30 */       String variableName = params[0].toLowerCase();
/*    */       
/* 32 */       if (Variable.couldBeString(variableName)) {
/*    */         
/* 34 */         String sourceString = ScriptCore.parseVars(provider, macro, "%" + variableName + "%", false);
/* 35 */         String searchFor = ScriptCore.parseVars(provider, macro, params[1], false);
/* 36 */         String replaceWith = "";
/*    */         
/* 38 */         if (params.length > 2)
/*    */         {
/* 40 */           replaceWith = ScriptCore.parseVars(provider, macro, params[2], false);
/*    */         }
/*    */         
/* 43 */         String result = sourceString.replace(searchFor, replaceWith);
/*    */         
/* 45 */         if (instance.hasOutVar()) {
/* 46 */           returnValue = new ReturnValue(result);
/*    */         } else {
/* 48 */           ScriptCore.setVariable(provider, macro, variableName, result);
/*    */         } 
/*    */       } 
/*    */     } 
/* 52 */     return (IReturnValue)returnValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionReplace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */