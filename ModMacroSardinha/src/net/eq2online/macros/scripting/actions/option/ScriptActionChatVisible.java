/*    */ package net.eq2online.macros.scripting.actions.option;
/*    */ 
/*    */ import ahg;
/*    */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionChatVisible
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionChatVisible(ScriptContext context) {
/* 17 */     super(context, "chatvisible");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 23 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 29 */     ahg chatVisibility = ahg.a;
/*    */     
/* 31 */     if (params.length > 0) {
/*    */       
/* 33 */       String parsed = ScriptCore.parseVars(provider, macro, params[0], false).toLowerCase().trim();
/*    */       
/* 35 */       if (parsed.startsWith("c") || parsed.equals("1"))
/*    */       {
/* 37 */         chatVisibility = ahg.b;
/*    */       }
/* 39 */       else if (parsed.startsWith("hid") || parsed.startsWith("f") || parsed.equals("off") || parsed.equals("2"))
/*    */       {
/* 41 */         chatVisibility = ahg.c;
/*    */       }
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 47 */       chatVisibility = ((AbstractionLayer.getGameSettings()).l == ahg.a) ? ahg.c : ahg.a;
/*    */     } 
/*    */     
/* 50 */     (AbstractionLayer.getGameSettings()).l = chatVisibility;
/*    */     
/* 52 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\option\ScriptActionChatVisible.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */