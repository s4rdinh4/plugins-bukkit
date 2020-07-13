/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionIfEndsWith
/*    */   extends ScriptActionIf
/*    */ {
/*    */   public ScriptActionIfEndsWith(ScriptContext context) {
/* 13 */     super(context, "ifendswith");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean executeConditional(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 22 */     if (params.length > 1) {
/*    */       
/* 24 */       String haystack = ScriptCore.parseVars(provider, macro, params[0], false).toLowerCase().trim();
/* 25 */       String needle = ScriptCore.parseVars(provider, macro, params[1], false).toLowerCase().trim();
/*    */       
/* 27 */       return haystack.endsWith(needle);
/*    */     } 
/*    */     
/* 30 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionIfEndsWith.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */