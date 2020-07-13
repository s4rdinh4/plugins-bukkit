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
/*    */ public class ScriptActionSetLabel
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionSetLabel(ScriptContext context) {
/* 15 */     super(context, "setlabel");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 24 */     if (params.length > 1) {
/*    */       
/* 26 */       String labelName = ScriptCore.parseVars(provider, macro, params[0], false);
/* 27 */       String labelText = ScriptCore.parseVars(provider, macro, params[1], false).replace('§', '&');
/* 28 */       String labelBinding = null;
/*    */       
/* 30 */       if (params.length > 2)
/*    */       {
/* 32 */         labelBinding = ScriptCore.parseVars(provider, macro, params[2], false).replace('§', '&');
/*    */       }
/*    */       
/* 35 */       provider.actionSetLabel(labelName, labelText, labelBinding);
/*    */     } 
/*    */     
/* 38 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionSetLabel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */