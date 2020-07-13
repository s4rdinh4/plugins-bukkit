/*    */ package net.eq2online.macros.scripting.actions;
/*    */ 
/*    */ import bsu;
/*    */ import btz;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionClearChat
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionClearChat(ScriptContext context) {
/* 16 */     super(context, "clearchat");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 22 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 31 */     btz inGameGui = (bsu.z()).q;
/*    */     
/* 33 */     if (inGameGui != null && inGameGui.d() != null) {
/* 34 */       inGameGui.d().a();
/*    */     }
/* 36 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\ScriptActionClearChat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */