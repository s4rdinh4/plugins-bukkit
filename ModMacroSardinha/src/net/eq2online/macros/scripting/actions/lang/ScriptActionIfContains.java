/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionIfContains
/*    */   extends ScriptActionIf
/*    */ {
/*    */   public ScriptActionIfContains(ScriptContext context) {
/* 13 */     super(context, "ifcontains");
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
/* 24 */       String haystack = ScriptCore.parseVars(provider, macro, params[0], false).toLowerCase();
/* 25 */       String needle = ScriptCore.parseVars(provider, macro, params[1], false).toLowerCase();
/*    */       
/* 27 */       return haystack.contains(needle);
/*    */     } 
/*    */     
/* 30 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionIfContains.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */