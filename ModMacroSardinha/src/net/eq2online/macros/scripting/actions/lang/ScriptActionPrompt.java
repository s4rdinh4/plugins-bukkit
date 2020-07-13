/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import bsu;
/*    */ import bxf;
/*    */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*    */ import net.eq2online.macros.gui.screens.GuiMacroParam;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ import net.eq2online.macros.scripting.PromptTarget;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionPrompt extends ScriptAction {
/*    */   public ScriptActionPrompt(ScriptContext context) {
/* 20 */     super(context, "prompt");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 26 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canExecuteNow(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 35 */     PromptTarget state = null;
/*    */     
/* 37 */     if (instance.getState() == null) {
/*    */       
/* 39 */       if (params.length < 2) return true;
/*    */       
/* 41 */       String promptSource = (params.length > 2) ? ScriptCore.parseVars(provider, macro, params[2], false) : null;
/* 42 */       String defaultValue = (params.length > 4) ? ScriptCore.parseVars(provider, macro, params[4], false) : "";
/* 43 */       state = new PromptTarget(macro, ScriptCore.parseVars(provider, macro, params[1], false), promptSource, defaultValue);
/*    */       
/* 45 */       if (!state.getCompleted())
/*    */       {
/* 47 */         String arg3 = (params.length > 3) ? ScriptCore.parseVars(provider, macro, params[3], false) : null;
/* 48 */         boolean allowOverride = (arg3 != null && (arg3.equals("1") || arg3.equalsIgnoreCase("true") || arg3.equalsIgnoreCase("yes")));
/*    */         
/* 50 */         instance.setState(state);
/* 51 */         if ((bsu.z()).m == null || allowOverride) {
/*    */           
/* 53 */           AbstractionLayer.displayGuiScreen((bxf)new GuiMacroParam((IMacroParamTarget)state));
/* 54 */           return false;
/*    */         } 
/*    */         
/* 57 */         ScriptCore.setVariable(provider, macro, ScriptCore.parseVars(provider, macro, params[0], false), "");
/* 58 */         return true;
/*    */       }
/*    */     
/*    */     } else {
/*    */       
/* 63 */       state = (PromptTarget)instance.getState();
/*    */     } 
/*    */     
/* 66 */     if (state != null) return state.getCompleted(); 
/* 67 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 76 */     ReturnValue retVal = new ReturnValue("");
/* 77 */     PromptTarget state = (PromptTarget)instance.getState();
/*    */     
/* 79 */     if (state != null)
/*    */     {
/* 81 */       if (state.getCompleted()) {
/*    */         
/* 83 */         String variableName = ScriptCore.parseVars(provider, macro, params[0], false);
/* 84 */         String value = state.getSuccess() ? state.getTargetString() : state.getDefaultValue();
/*    */         
/* 86 */         ScriptCore.setVariable(provider, macro, variableName, value);
/* 87 */         retVal.setString(value);
/*    */       } 
/*    */     }
/*    */     
/* 91 */     return (IReturnValue)retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionPrompt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */