/*    */ package net.eq2online.macros.scripting.actions;
/*    */ 
/*    */ import net.eq2online.macros.gui.designable.LayoutManager;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
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
/*    */ public class ScriptActionGui
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionGui(ScriptContext context) {
/* 25 */     super(context, "gui");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 31 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 37 */     if (params.length > 2 && "bind".equalsIgnoreCase(params[0])) {
/*    */       
/* 39 */       String slotName = ScriptCore.parseVars(provider, macro, params[1], false);
/* 40 */       String layoutName = ScriptCore.parseVars(provider, macro, params[2], false);
/*    */       
/* 42 */       LayoutManager.setBinding(slotName, layoutName);
/*    */     }
/* 44 */     else if (params.length > 0) {
/*    */       
/* 46 */       provider.actionDisplayGuiScreen(params[0], this.context);
/*    */     }
/*    */     else {
/*    */       
/* 50 */       provider.actionDisplayGuiScreen(null, this.context);
/*    */     } 
/*    */     
/* 53 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\ScriptActionGui.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */