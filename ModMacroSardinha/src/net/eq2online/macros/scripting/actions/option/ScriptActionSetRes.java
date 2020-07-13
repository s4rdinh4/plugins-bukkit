/*    */ package net.eq2online.macros.scripting.actions.option;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionSetRes
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionSetRes(ScriptContext context) {
/* 15 */     super(context, "setres");
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
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 30 */     if (params.length > 1) {
/*    */       
/* 32 */       int width = Math.min(Math.max(ScriptCore.tryParseInt(params[0], 854), 0), 3840);
/* 33 */       int height = Math.min(Math.max(ScriptCore.tryParseInt(params[1], 480), 0), 2160);
/*    */       
/* 35 */       provider.actionScheduleResChange(width, height);
/*    */     } 
/*    */     
/* 38 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\option\ScriptActionSetRes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */