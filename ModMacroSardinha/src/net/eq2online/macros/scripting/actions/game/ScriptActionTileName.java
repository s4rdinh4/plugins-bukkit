/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import atr;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionTileName
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionTileName(ScriptContext context) {
/* 18 */     super(context, "tilename");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 24 */     ReturnValue retVal = new ReturnValue("");
/*    */     
/* 26 */     if (params.length > 0) {
/*    */       
/* 28 */       int id = ScriptCore.tryParseInt(ScriptCore.parseVars(provider, macro, params[0], false).trim(), -1);
/* 29 */       atr block = atr.c(id);
/* 30 */       if (block != null)
/*    */       {
/* 32 */         retVal.setString(Macros.getBlockName(block));
/*    */       }
/*    */     } 
/*    */     
/* 36 */     return (IReturnValue)retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionTileName.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */