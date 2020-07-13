/*    */ package net.eq2online.macros.scripting.actions;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionShowGui
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionShowGui(ScriptContext context) {
/* 15 */     super(context, "showgui");
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
/* 29 */       String screenName = ScriptCore.parseVars(provider, macro, params[0], false);
/* 30 */       String backScreenName = null;
/*    */       
/* 32 */       if (params.length > 1)
/*    */       {
/* 34 */         backScreenName = ScriptCore.parseVars(provider, macro, params[1], false);
/*    */       }
/*    */       
/* 37 */       provider.actionDisplayCustomScreen(screenName, backScreenName);
/*    */     } 
/*    */     
/* 40 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\ScriptActionShowGui.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */