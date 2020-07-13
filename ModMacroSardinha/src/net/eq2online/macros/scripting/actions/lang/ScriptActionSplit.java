/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.regex.Pattern;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValueArray;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ 
/*    */ public class ScriptActionSplit
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionSplit(ScriptContext context) {
/* 20 */     super(context, "split");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 26 */     ReturnValueArray retVal = null;
/*    */     
/* 28 */     if (params.length > 1) {
/*    */       
/* 30 */       String splitter = ScriptCore.parseVars(provider, macro, params[0], false);
/* 31 */       String source = ScriptCore.parseVars(provider, macro, params[1], false);
/*    */       
/* 33 */       List<String> parts = Arrays.asList(source.split(Pattern.quote(splitter)));
/* 34 */       retVal = new ReturnValueArray(false);
/* 35 */       retVal.putStrings(parts);
/*    */       
/* 37 */       if (params.length > 2) {
/*    */         
/* 39 */         String arrayName = ScriptCore.parseVars(provider, macro, params[2], false).toLowerCase();
/* 40 */         provider.clearArray(macro, arrayName);
/*    */         
/* 42 */         for (String part : parts)
/*    */         {
/* 44 */           provider.pushValueToArray(macro, arrayName, part);
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 49 */     return (IReturnValue)retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionSplit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */