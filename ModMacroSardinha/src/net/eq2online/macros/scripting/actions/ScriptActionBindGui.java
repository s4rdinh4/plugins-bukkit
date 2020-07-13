/*    */ package net.eq2online.macros.scripting.actions;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionBindGui
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionBindGui(ScriptContext context) {
/* 15 */     super(context, "bindgui");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 21 */     if (params.length > 0) {
/*    */       
/* 23 */       String slotName = "playback";
/* 24 */       String screenName = ScriptCore.parseVars(provider, macro, params[0], false);
/*    */       
/* 26 */       if (params.length > 1) {
/*    */         
/* 28 */         slotName = screenName;
/* 29 */         screenName = ScriptCore.parseVars(provider, macro, params[1], false);
/*    */       } 
/*    */       
/* 32 */       provider.actionBindScreenToSlot(slotName, screenName);
/*    */     } 
/*    */     
/* 35 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\ScriptActionBindGui.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */