/*    */ package net.eq2online.macros.modules.chatfilter.scriptactions;
/*    */ 
/*    */ import net.eq2online.console.Log;
/*    */ import net.eq2online.macros.modules.chatfilter.ChatFilterManager;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ 
/*    */ public class ScriptActionChatFilter
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionChatFilter(ScriptContext context) {
/* 17 */     super(context, "chatfilter");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 26 */     if (!(macro instanceof net.eq2online.macros.modules.chatfilter.ChatFilterMacro)) {
/*    */       
/* 28 */       if (params.length > 0) {
/*    */         
/* 30 */         ChatFilterManager.getInstance().setEnabled((params[0].equals("1") || params[0].equalsIgnoreCase("on") || params[0].equalsIgnoreCase("true")));
/*    */       }
/*    */       else {
/*    */         
/* 34 */         ChatFilterManager.getInstance().setEnabled(!ChatFilterManager.getInstance().isEnabled());
/*    */       } 
/*    */       
/* 37 */       Log.info("Chat filter " + (ChatFilterManager.getInstance().isEnabled() ? "enabled" : "disabled"));
/*    */     } 
/*    */     
/* 40 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onInit() {
/* 49 */     ChatFilterManager.getInstance();
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\modules\chatfilter\scriptactions\ScriptActionChatFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */