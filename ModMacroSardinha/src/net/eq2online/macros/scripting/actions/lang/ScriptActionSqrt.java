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
/*    */ import uv;
/*    */ 
/*    */ public class ScriptActionSqrt
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionSqrt(ScriptContext context) {
/* 17 */     super(context, "sqrt");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 23 */     ReturnValue retVal = new ReturnValue(0);
/*    */     
/* 25 */     if (params.length > 0) {
/*    */       
/* 27 */       double sqrt = uv.a(ScriptCore.tryParseFloat(ScriptCore.parseVars(provider, macro, params[0], false), 0.0F));
/* 28 */       retVal.setInt(uv.c(sqrt));
/*    */       
/* 30 */       if (params.length > 1) {
/*    */         
/* 32 */         String varName = ScriptCore.parseVars(provider, macro, params[1], false);
/* 33 */         ScriptCore.setVariable(provider, macro, varName, uv.c(sqrt));
/*    */       } 
/*    */     } 
/*    */     
/* 37 */     return (IReturnValue)retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionSqrt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */