/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import amj;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.gui.helpers.HelperContainerSlots;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionGetSlotItem
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionGetSlotItem(ScriptContext context) {
/* 19 */     super(context, "getslotitem");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 28 */     String itemID = "unknown";
/* 29 */     int stackSize = 0;
/* 30 */     int damage = 0;
/*    */     
/* 32 */     if (params.length > 0) {
/*    */       
/* 34 */       int slotId = Math.max(0, ScriptCore.tryParseInt(ScriptCore.parseVars(provider, macro, params[0], false), 0));
/* 35 */       amj slotStack = HelperContainerSlots.getSlotStack(slotId);
/*    */       
/* 37 */       if (slotStack == null) {
/*    */         
/* 39 */         itemID = Macros.getItemName(null);
/*    */       }
/*    */       else {
/*    */         
/* 43 */         itemID = Macros.getItemName(slotStack.b());
/* 44 */         stackSize = slotStack.b;
/* 45 */         damage = slotStack.i();
/*    */       } 
/*    */     } 
/*    */     
/* 49 */     ReturnValue retVal = new ReturnValue(itemID);
/*    */     
/* 51 */     if (params.length > 1) ScriptCore.setVariable(provider, macro, ScriptCore.parseVars(provider, macro, params[1], false), itemID); 
/* 52 */     if (params.length > 2) ScriptCore.setVariable(provider, macro, ScriptCore.parseVars(provider, macro, params[2], false), stackSize); 
/* 53 */     if (params.length > 3) ScriptCore.setVariable(provider, macro, ScriptCore.parseVars(provider, macro, params[3], false), damage);
/*    */     
/* 55 */     return (IReturnValue)retVal;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPermissable() {
/* 64 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 73 */     return "inventory";
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionGetSlotItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */