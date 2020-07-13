/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
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
/*    */ public class ScriptActionWait
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionWait(ScriptContext context) {
/* 25 */     super(context, "wait");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 31 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canExecuteNow(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 38 */     if (instance.getState() == null) {
/*    */       
/* 40 */       if (params.length > 0) {
/*    */         
/* 42 */         String timeoutParam = ScriptCore.parseVars(provider, macro, params[0], false).toLowerCase();
/* 43 */         long multiplier = 1000L;
/*    */         
/* 45 */         if (timeoutParam.endsWith("t")) {
/*    */           
/* 47 */           timeoutParam = timeoutParam.substring(0, timeoutParam.length() - 1);
/* 48 */           Integer ticks = Integer.valueOf(ScriptCore.tryParseInt(timeoutParam, 0));
/*    */           
/* 50 */           if (ticks.intValue() > 0)
/*    */           {
/* 52 */             instance.setState(ticks);
/* 53 */             return false;
/*    */           }
/*    */         
/*    */         } else {
/*    */           
/* 58 */           if (timeoutParam.endsWith("ms")) {
/*    */             
/* 60 */             timeoutParam = timeoutParam.substring(0, timeoutParam.length() - 2);
/* 61 */             multiplier = 1L;
/*    */           } 
/*    */ 
/*    */           
/* 65 */           long timeout = ScriptCore.tryParseLong(timeoutParam, 0L);
/* 66 */           if (timeout > 0L)
/*    */           {
/* 68 */             instance.setState(new Long(System.currentTimeMillis() + timeout * multiplier));
/* 69 */             return false;
/*    */           }
/*    */         
/*    */         } 
/*    */       } 
/*    */     } else {
/*    */       
/* 76 */       if (instance.getState() instanceof Long)
/*    */       {
/* 78 */         return (System.currentTimeMillis() >= ((Long)instance.getState()).longValue());
/*    */       }
/* 80 */       if (instance.getState() instanceof Integer) {
/*    */         
/* 82 */         int ticks = ((Integer)instance.getState()).intValue() - 1;
/*    */         
/* 84 */         if (ticks > 0) {
/*    */           
/* 86 */           instance.setState(Integer.valueOf(ticks));
/* 87 */           return false;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 92 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 98 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionWait.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */