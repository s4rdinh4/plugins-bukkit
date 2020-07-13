/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import alq;
/*    */ import amj;
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
/*    */ 
/*    */ public class ScriptActionGetItemInfo
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionGetItemInfo(ScriptContext context) {
/* 20 */     super(context, "getiteminfo");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 29 */     ReturnValue retVal = new ReturnValue("None");
/*    */     
/* 31 */     if (params.length > 1) {
/*    */       
/* 33 */       amj itemStack = tryParseItemID(ScriptCore.parseVars(provider, macro, params[0], false)).toItemStack(1);
/*    */       
/* 35 */       alq item = itemStack.b();
/* 36 */       if (item != null) {
/*    */         
/* 38 */         String stackType = "ITEM";
/* 39 */         String idDropped = Macros.getItemName(item);
/*    */         
/* 41 */         if (item instanceof aju)
/*    */         {
/* 43 */           stackType = "TILE";
/*    */         }
/*    */         
/* 46 */         retVal.setString(itemStack.q());
/*    */         
/* 48 */         ScriptCore.setVariable(provider, macro, params[1], itemStack.q());
/* 49 */         if (params.length > 2) ScriptCore.setVariable(provider, macro, params[2], itemStack.c()); 
/* 50 */         if (params.length > 3) ScriptCore.setVariable(provider, macro, params[3], stackType); 
/* 51 */         if (params.length > 4) ScriptCore.setVariable(provider, macro, params[4], idDropped);
/*    */       
/*    */       } else {
/*    */         
/* 55 */         ScriptCore.setVariable(provider, macro, params[1], "None");
/* 56 */         if (params.length > 2) ScriptCore.setVariable(provider, macro, params[2], 0); 
/* 57 */         if (params.length > 3) ScriptCore.setVariable(provider, macro, params[3], "NONE"); 
/* 58 */         if (params.length > 4) ScriptCore.setVariable(provider, macro, params[4], "");
/*    */       
/*    */       } 
/*    */     } 
/* 62 */     return (IReturnValue)retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionGetItemInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */