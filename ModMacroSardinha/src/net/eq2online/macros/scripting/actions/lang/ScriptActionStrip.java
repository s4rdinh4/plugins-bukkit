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
/*    */ import vb;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScriptActionStrip
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionStrip(ScriptContext context) {
/* 25 */     super(context, "strip");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 31 */     ReturnValue retVal = new ReturnValue("");
/*    */     
/* 33 */     if (params.length == 1) {
/*    */       
/* 35 */       String text = vb.a(ScriptCore.parseVars(provider, macro, params[1], false));
/* 36 */       retVal.setString(text);
/*    */     }
/* 38 */     else if (params.length > 1) {
/*    */       
/* 40 */       String variableName = params[0].toLowerCase();
/*    */       
/* 42 */       if (Variable.couldBeString(variableName)) {
/*    */         
/* 44 */         String text = vb.a(ScriptCore.parseVars(provider, macro, params[1], false));
/* 45 */         retVal.setString(text);
/* 46 */         ScriptCore.setVariable(provider, macro, variableName, text);
/*    */       } 
/*    */     } 
/*    */     
/* 50 */     return (IReturnValue)retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionStrip.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */