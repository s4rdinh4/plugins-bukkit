/*    */ package net.eq2online.macros.modules.chatfilter.scriptactions;
/*    */ 
/*    */ import net.eq2online.macros.event.providers.OnSendChatMessageProvider;
/*    */ import net.eq2online.macros.modules.chatfilter.ChatFilterMacro;
/*    */ import net.eq2online.macros.modules.chatfilter.ChatFilterManager;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionPass
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionPass(ScriptContext context) {
/* 18 */     super(context, "pass");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 27 */     if (macro instanceof ChatFilterMacro) {
/*    */       
/* 29 */       ChatFilterMacro chatFilter = (ChatFilterMacro)macro;
/*    */       
/* 31 */       chatFilter.pass = true;
/* 32 */       chatFilter.kill();
/*    */     }
/* 34 */     else if (macro.isSynchronous()) {
/*    */       
/* 36 */       IVariableProvider variableProvider = macro.getContext().getVariableProvider();
/* 37 */       if (variableProvider instanceof OnSendChatMessageProvider)
/*    */       {
/* 39 */         ((OnSendChatMessageProvider)variableProvider).setPass(true);
/*    */       }
/*    */     } 
/*    */     
/* 43 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onInit() {
/* 52 */     ChatFilterManager.getInstance();
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\modules\chatfilter\scriptactions\ScriptActionPass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */