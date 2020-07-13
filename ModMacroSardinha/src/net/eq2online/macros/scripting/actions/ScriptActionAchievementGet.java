/*    */ package net.eq2online.macros.scripting.actions;
/*    */ 
/*    */ import amj;
/*    */ import atr;
/*    */ import aty;
/*    */ import bsu;
/*    */ import ho;
/*    */ import hy;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ import net.eq2online.macros.scripting.variable.ItemID;
/*    */ import tk;
/*    */ 
/*    */ public class ScriptActionAchievementGet
/*    */   extends ScriptAction {
/*    */   static class FakeAchievement
/*    */     extends tk {
/*    */     private String text;
/*    */     
/*    */     public FakeAchievement(String text, ItemID itemId) {
/* 26 */       super("fake", "fake", 0, 0, getItemStack(itemId), null);
/* 27 */       this.text = text;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     protected static amj getItemStack(ItemID itemId) {
/*    */       amj itemStack;
/*    */       try {
/* 39 */         itemStack = itemId.toItemStack(1);
/*    */       }
/* 41 */       catch (Exception ex) {
/*    */         
/* 43 */         itemStack = new amj((atr)aty.c, 1, 0);
/*    */       } 
/*    */       
/* 46 */       return itemStack;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public ho e() {
/* 52 */       return (ho)new hy(this.text);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public ScriptActionAchievementGet(ScriptContext context) {
/* 58 */     super(context, "achievementget");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 64 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 73 */     if (params.length > 0) {
/*    */       
/*    */       try {
/*    */         
/* 77 */         ItemID itemId = (params.length > 1) ? tryParseItemID(ScriptCore.parseVars(provider, macro, params[1], false)) : new ItemID("grass", 0);
/* 78 */         tk c = new FakeAchievement(ScriptCore.parseVars(provider, macro, params[0], false), itemId);
/* 79 */         (bsu.z()).p.a(c);
/*    */       }
/* 81 */       catch (Exception exception) {}
/*    */     }
/*    */     
/* 84 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\ScriptActionAchievementGet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */