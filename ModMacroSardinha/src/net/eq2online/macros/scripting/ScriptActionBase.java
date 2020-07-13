/*    */ package net.eq2online.macros.scripting;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IScriptAction;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ScriptActionBase
/*    */   implements IScriptAction
/*    */ {
/*    */   protected final ScriptContext context;
/*    */   protected final String actionName;
/*    */   
/*    */   protected ScriptActionBase(ScriptContext context, String actionName) {
/* 31 */     this.context = context;
/* 32 */     this.actionName = actionName.toLowerCase();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ScriptContext getContext() {
/* 41 */     return this.context;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 50 */     return this.actionName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 59 */     return this.actionName;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\ScriptActionBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */