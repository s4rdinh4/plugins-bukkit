/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import net.eq2online.macros.gui.helpers.HelperContainerSlots;
/*    */ import net.eq2online.macros.scripting.Variable;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ import net.eq2online.macros.scripting.variable.ItemID;
/*    */ 
/*    */ public class ScriptActionGetSlot
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionGetSlot(ScriptContext context) {
/* 19 */     super(context, "getslot");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 30 */     ReturnValue retVal = new ReturnValue(-1);
/*    */     
/* 32 */     if (params.length > 1) {
/*    */       
/* 34 */       String varName = ScriptCore.parseVars(provider, macro, params[1], false).toLowerCase();
/* 35 */       if (!Variable.couldBeInt(varName)) return (IReturnValue)retVal;
/*    */       
/* 37 */       int slotContaining = findItem(provider, macro, ScriptCore.parseVars(provider, macro, params[0], false), (params.length > 2) ? ScriptCore.parseVars(provider, macro, params[2], false) : null);
/* 38 */       if (slotContaining > -1) {
/*    */         
/* 40 */         ScriptCore.setVariable(provider, macro, varName, slotContaining);
/* 41 */         retVal.setInt(slotContaining);
/* 42 */         return (IReturnValue)retVal;
/*    */       } 
/*    */       
/* 45 */       ScriptCore.setVariable(provider, macro, varName, -1);
/*    */     }
/* 47 */     else if (params.length > 0 && instance.hasOutVar()) {
/*    */       
/* 49 */       retVal.setInt(findItem(provider, macro, params[0], null));
/*    */     } 
/*    */     
/* 52 */     return (IReturnValue)retVal;
/*    */   }
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
/*    */   public int findItem(IScriptActionProvider provider, IMacro macro, String unparsedId, String unparsedStart) {
/* 65 */     ItemID itemId = tryParseItemID(ScriptCore.parseVars(provider, macro, unparsedId, false));
/*    */     
/* 67 */     int startSlot = 0;
/* 68 */     if (unparsedStart != null)
/*    */     {
/* 70 */       startSlot = Math.max(0, ScriptCore.tryParseInt(ScriptCore.parseVars(provider, macro, unparsedStart, false), 0));
/*    */     }
/*    */     
/* 73 */     if (itemId.isValid())
/*    */     {
/* 75 */       return HelperContainerSlots.getSlotContaining(itemId, startSlot);
/*    */     }
/*    */     
/* 78 */     return -1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPermissable() {
/* 87 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 96 */     return "inventory";
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionGetSlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */