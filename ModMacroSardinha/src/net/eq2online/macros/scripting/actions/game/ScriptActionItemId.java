/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import alq;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ import oa;
/*    */ 
/*    */ public class ScriptActionItemId
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionItemId(ScriptContext context) {
/* 19 */     super(context, "itemid");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 25 */     ReturnValue retVal = new ReturnValue(-1);
/*    */     
/* 27 */     if (params.length > 0) {
/*    */       
/* 29 */       String identifier = ScriptCore.parseVars(provider, macro, params[0], false).trim();
/* 30 */       alq item = Macros.getItem(new oa(identifier));
/* 31 */       if (item != null)
/*    */       {
/* 33 */         retVal.setInt(alq.b(item));
/*    */       }
/*    */     } 
/*    */     
/* 37 */     return (IReturnValue)retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionItemId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */