/*    */ package net.eq2online.macros.scripting.parser;
/*    */ 
/*    */ import net.eq2online.console.Log;
/*    */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*    */ import net.eq2online.macros.core.MacroModSettings;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ 
/*    */ public class DeniedAction
/*    */   extends ScriptAction
/*    */ {
/*    */   public DeniedAction(ScriptContext context) {
/* 15 */     super(context);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 24 */     if (MacroModSettings.generatePermissionsWarnings) provider.actionAddChatMessage("§4" + LocalisationProvider.getLocalisedString("script.error.denied", new Object[] { rawParams.toUpperCase() })); 
/* 25 */     if (MacroModSettings.enableDebug) Log.info("Script action {0} was denied by the server", new Object[] { rawParams.toUpperCase() }); 
/* 26 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\parser\DeniedAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */