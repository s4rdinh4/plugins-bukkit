/*    */ package net.eq2online.macros.scripting.actions;
/*    */ 
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionStoreOver
/*    */   extends ScriptActionStore
/*    */ {
/*    */   public ScriptActionStoreOver(ScriptContext context) {
/*  9 */     super(context, "storeover");
/* 10 */     this.overwrite = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\ScriptActionStoreOver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */