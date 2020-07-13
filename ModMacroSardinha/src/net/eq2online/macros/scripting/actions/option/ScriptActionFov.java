/*    */ package net.eq2online.macros.scripting.actions.option;
/*    */ 
/*    */ import btr;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ 
/*    */ public class ScriptActionFov
/*    */   extends ScriptActionGamma<btr>
/*    */ {
/*    */   public ScriptActionFov(ScriptContext context) {
/* 11 */     super(context, "fov", btr.c, 70.0F, 110.0F);
/*    */     
/* 13 */     this.minValue = 10.0F;
/* 14 */     this.maxValue = 170.0F;
/*    */     
/* 16 */     setNoScale(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPermissable() {
/* 25 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 34 */     return "option";
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\option\ScriptActionFov.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */