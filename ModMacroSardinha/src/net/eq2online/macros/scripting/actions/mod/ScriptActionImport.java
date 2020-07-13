/*    */ package net.eq2online.macros.scripting.actions.mod;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ 
/*    */ public class ScriptActionImport
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionImport(ScriptContext context) {
/* 15 */     super(context, "import");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 21 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 27 */     if (params.length > 0) {
/*    */       
/* 29 */       boolean toggle = (params.length > 1 && (params[1].equals("1") || params[1].equalsIgnoreCase("true")));
/* 30 */       boolean verbose = (params.length > 2 && (params[2].equals("1") || params[2].equalsIgnoreCase("true")));
/*    */       
/* 32 */       String oldConfig = provider.actionOverlayConfig(params[0], toggle, verbose);
/*    */       
/* 34 */       if (macro.getState("oldOverlayConfig") == null) {
/* 35 */         macro.setState("oldOverlayConfig", oldConfig);
/*    */       
/*    */       }
/*    */     }
/* 39 */     else if (macro.getState("oldOverlayConfig") != null) {
/* 40 */       provider.actionOverlayConfig(macro.getState("oldOverlayConfig").toString(), false, false);
/*    */     } 
/*    */     
/* 43 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\mod\ScriptActionImport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */