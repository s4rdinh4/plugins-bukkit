/*    */ package net.eq2online.macros.scripting.actions.mod;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionUnimport
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionUnimport(ScriptContext context) {
/* 14 */     super(context, "unimport");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 20 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 26 */     boolean verbose = (params.length > 0 && (params[0].equals("1") || params[0].equalsIgnoreCase("true")));
/* 27 */     provider.actionOverlayConfig(null, false, verbose);
/* 28 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\mod\ScriptActionUnimport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */