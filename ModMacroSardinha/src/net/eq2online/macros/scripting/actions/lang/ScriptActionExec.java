/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.regex.Pattern;
/*    */ import net.eq2online.macros.core.MacroExecVariableProvider;
/*    */ import net.eq2online.macros.core.MacroModCore;
/*    */ import net.eq2online.macros.core.MacroTemplate;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.IVariableProvider;
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
/*    */ public class ScriptActionExec
/*    */   extends ScriptAction
/*    */ {
/* 26 */   protected static Pattern fileNamePattern = Pattern.compile("^[A-Za-z0-9\\x20_\\-\\.]+\\.txt$");
/*    */ 
/*    */   
/*    */   public ScriptActionExec(ScriptContext context) {
/* 30 */     super(context, "exec");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 36 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 42 */     if (params.length > 0) {
/*    */       MacroExecVariableProvider macroExecVariableProvider;
/* 44 */       String paramCompiled = ScriptCore.parseVars(provider, macro, params[0], false);
/* 45 */       String macroName = null;
/*    */       
/* 47 */       if (params.length > 1)
/*    */       {
/* 49 */         macroName = ScriptCore.parseVars(provider, macro, params[1], false);
/*    */       }
/*    */       
/* 52 */       IVariableProvider contextProvider = null;
/*    */       
/* 54 */       if (params.length > 2)
/*    */       {
/* 56 */         macroExecVariableProvider = new MacroExecVariableProvider(params, 2, provider, macro);
/*    */       }
/*    */       
/* 59 */       if (fileNamePattern.matcher(paramCompiled).matches())
/*    */       {
/* 61 */         if (instance.getState() != null) {
/*    */           
/* 63 */           MacroTemplate tpl = (MacroTemplate)instance.getState();
/* 64 */           MacroModCore.getMacroManager().playMacro(tpl, false, ScriptContext.MAIN, (IVariableProvider)macroExecVariableProvider);
/*    */         }
/*    */         else {
/*    */           
/* 68 */           File playFile = new File(MacroModCore.getMacrosDirectory(), paramCompiled);
/*    */           
/* 70 */           if (playFile.exists()) {
/*    */             
/* 72 */             MacroTemplate tpl = MacroModCore.getMacroManager().createLooseTemplate("$${$$<" + paramCompiled + ">}$$", macroName);
/* 73 */             instance.setState(tpl);
/*    */             
/* 75 */             MacroModCore.getMacroManager().playMacro(tpl, false, ScriptContext.MAIN, (IVariableProvider)macroExecVariableProvider);
/*    */           } 
/*    */         } 
/*    */       }
/*    */     } 
/*    */     
/* 81 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionExec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */