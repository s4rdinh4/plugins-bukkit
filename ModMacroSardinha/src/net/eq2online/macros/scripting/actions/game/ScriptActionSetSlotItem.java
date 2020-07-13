/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import ail;
/*    */ import amj;
/*    */ import bsu;
/*    */ import byy;
/*    */ import cem;
/*    */ import cio;
/*    */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionSetSlotItem
/*    */   extends ScriptAction {
/*    */   public ScriptActionSetSlotItem(ScriptContext context) {
/* 21 */     super(context, "setslotitem");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 27 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 36 */     cio thePlayer = AbstractionLayer.getPlayer();
/* 37 */     cem playerController = AbstractionLayer.getPlayerController();
/*    */     
/* 39 */     if (params.length > 0 && thePlayer != null && thePlayer.bg != null && playerController != null && playerController.h()) {
/*    */       
/* 41 */       amj itemStack = tryParseItemID(ScriptCore.parseVars(provider, macro, params[0], false)).toItemStack(1);
/*    */       
/* 43 */       if (itemStack.b() != null) {
/*    */         
/* 45 */         if (params.length > 2)
/*    */         {
/* 47 */           int maxStackSize = itemStack.c();
/* 48 */           itemStack.b = Math.min(ScriptCore.tryParseInt(ScriptCore.parseVars(provider, macro, params[2], false), 1), maxStackSize);
/*    */         }
/*    */       
/*    */       } else {
/*    */         
/* 53 */         itemStack = null;
/*    */       } 
/*    */       
/* 56 */       int slot = Math.min(Math.max((params.length > 1) ? ScriptCore.tryParseInt(ScriptCore.parseVars(provider, macro, params[1], false), thePlayer.bg.c) : thePlayer.bg.c, 0), 8);
/*    */       
/* 58 */       byy crafting = new byy(bsu.z());
/* 59 */       thePlayer.bh.a((ail)crafting);
/* 60 */       thePlayer.bg.a(slot, itemStack);
/* 61 */       thePlayer.bh.b();
/* 62 */       thePlayer.bh.b((ail)crafting);
/*    */     } 
/*    */     
/* 65 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPermissable() {
/* 75 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 84 */     return "inventory";
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionSetSlotItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */