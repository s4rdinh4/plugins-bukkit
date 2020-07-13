/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import net.eq2online.macros.scripting.FloatInterpolator;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionLooks
/*    */   extends ScriptActionLook
/*    */ {
/*    */   public ScriptActionLooks(ScriptContext context) {
/* 10 */     super(context, "looks");
/*    */     
/* 12 */     this.interpolationType = FloatInterpolator.InterpolationType.Smooth;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionLooks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */