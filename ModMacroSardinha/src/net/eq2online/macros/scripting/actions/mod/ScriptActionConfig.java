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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScriptActionConfig
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionConfig(ScriptContext context) {
/* 24 */     super(context, "config");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 30 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 36 */     if (params.length > 0) {
/*    */       
/* 38 */       boolean verbose = (params.length > 1 && (params[1].equals("1") || params[1].equalsIgnoreCase("true")));
/*    */       
/* 40 */       String oldConfig = provider.actionSwitchConfig(params[0], verbose);
/*    */       
/* 42 */       if (macro.getState("oldConfig") == null) {
/* 43 */         macro.setState("oldConfig", oldConfig);
/*    */       
/*    */       }
/*    */     }
/* 47 */     else if (macro.getState("oldConfig") != null) {
/* 48 */       provider.actionSwitchConfig(macro.getState("oldConfig").toString(), false);
/*    */     } 
/*    */     
/* 51 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\mod\ScriptActionConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */