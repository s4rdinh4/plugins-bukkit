/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import net.eq2online.macros.gui.helpers.HelperContainerSlots;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionSlotClick
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionSlotClick(ScriptContext context) {
/* 16 */     super(context, "slotclick");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ScriptActionSlotClick(ScriptContext context, String actionName) {
/* 23 */     super(context, actionName);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 29 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPermissable() {
/* 38 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 47 */     return "inventory";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 56 */     if (params.length > 0) {
/*    */       
/* 58 */       int slotNumber = ScriptCore.tryParseInt(ScriptCore.parseVars(provider, macro, params[0], false), 0);
/* 59 */       int button = 0;
/* 60 */       boolean shift = false;
/*    */       
/* 62 */       if (params.length > 1) {
/*    */         
/* 64 */         String mouseBtn = ScriptCore.parseVars(provider, macro, params[1], false).trim().toLowerCase();
/* 65 */         button = mouseBtn.startsWith("r") ? 1 : 0;
/*    */       } 
/*    */       
/* 68 */       if (params.length > 2) {
/*    */         
/* 70 */         String useShift = ScriptCore.parseVars(provider, macro, params[2], false).trim();
/* 71 */         shift = (useShift.equals("1") || useShift.equalsIgnoreCase("true"));
/*    */       } 
/*    */       
/* 74 */       HelperContainerSlots.containerSlotClick(slotNumber, button, shift);
/*    */     } 
/*    */     
/* 77 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionSlotClick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */