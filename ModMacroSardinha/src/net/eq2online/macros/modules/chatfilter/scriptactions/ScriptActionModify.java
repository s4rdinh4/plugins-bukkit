/*    */ package net.eq2online.macros.modules.chatfilter.scriptactions;
/*    */ 
/*    */ import net.eq2online.macros.core.MacroModCore;
/*    */ import net.eq2online.macros.modules.chatfilter.ChatFilterMacro;
/*    */ import net.eq2online.macros.modules.chatfilter.ChatFilterManager;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionModify
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionModify(ScriptContext context) {
/* 18 */     super(context, "modify");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 27 */     if (macro instanceof ChatFilterMacro) {
/*    */       
/* 29 */       ChatFilterMacro chatFilter = (ChatFilterMacro)macro;
/* 30 */       chatFilter.newMessage = MacroModCore.convertAmpCodes(ScriptCore.parseVars(provider, macro, rawParams, false));
/*    */     } 
/*    */     
/* 33 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onInit() {
/* 42 */     ChatFilterManager.getInstance();
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\modules\chatfilter\scriptactions\ScriptActionModify.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */