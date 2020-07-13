/*    */ package net.eq2online.macros.scripting.parser;
/*    */ 
/*    */ import net.eq2online.macros.core.MacroAction;
/*    */ import net.eq2online.macros.scripting.ActionParser;
/*    */ import net.eq2online.macros.scripting.api.IMacroActionProcessor;
/*    */ import net.eq2online.macros.scripting.api.IScriptAction;
/*    */ 
/*    */ public abstract class ActionParserAbstract
/*    */   extends ActionParser
/*    */ {
/*    */   protected ActionParserAbstract(ScriptContext context) {
/* 12 */     super(context);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final MacroAction getInstance(IMacroActionProcessor actionProcessor, String actionName, String rawParams, String[] params, String outVar) {
/* 24 */     IScriptAction scriptAction = this.context.getAction(actionName);
/*    */     
/* 26 */     if (scriptAction != null) {
/*    */       
/* 28 */       if (scriptAction.checkExecutePermission())
/*    */       {
/* 30 */         return new MacroAction(actionProcessor, scriptAction, rawParams, params, outVar);
/*    */       }
/*    */       
/* 33 */       if (scriptAction.isPermissable())
/*    */       {
/* 35 */         return new MacroAction(actionProcessor, (IScriptAction)new DeniedAction(this.context), actionName, params, outVar);
/*    */       }
/*    */     } 
/*    */     
/* 39 */     return new MacroAction(actionProcessor, (IScriptAction)new ScriptAction(this.context), rawParams, params, outVar);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final MacroAction parse(IMacroActionProcessor actionProcessor, String actionName, String unparsedParams, String outVar) {
/* 52 */     StringBuilder rawParams = new StringBuilder();
/* 53 */     char firstParamQuote = actionName.toLowerCase().matches("^(if|elseif|iif)$") ? Character.MIN_VALUE : '"';
/* 54 */     String[] params = ScriptCore.tokenize(unparsedParams, ',', firstParamQuote, '"', '\\', rawParams);
/*    */     
/* 56 */     return getInstance(actionProcessor, actionName, (rawParams.length() > 0) ? rawParams.substring(1) : "", params, outVar);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\parser\ActionParserAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */