/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.ReturnValueChat;
/*    */ import net.eq2online.macros.scripting.api.IExpressionEvaluator;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionIIf
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionIIf(ScriptContext context) {
/* 17 */     super(context, "iif");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 23 */     if (params.length > 1) {
/*    */       
/* 25 */       IExpressionEvaluator evaluator = provider.getExpressionEvaluator(macro, ScriptCore.parseVars(provider, macro, params[0], true));
/*    */       
/* 27 */       if (evaluator.evaluate() != 0)
/*    */       {
/* 29 */         return (IReturnValue)new ReturnValueChat(ScriptCore.parseVars(provider, macro, params[1], false));
/*    */       }
/* 31 */       if (params.length > 2)
/*    */       {
/* 33 */         return (IReturnValue)new ReturnValueChat(ScriptCore.parseVars(provider, macro, params[2], false));
/*    */       }
/*    */     } 
/*    */     
/* 37 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPermissable() {
/* 46 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 55 */     return "chat";
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionIIf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */