/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.Variable;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionJoin
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionJoin(ScriptContext context) {
/* 17 */     super(context, "join");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 23 */     ReturnValue retVal = new ReturnValue("");
/*    */     
/* 25 */     if (params.length > 1) {
/*    */       
/* 27 */       String glue = ScriptCore.parseVars(provider, macro, params[0], false);
/* 28 */       String arrayName = ScriptCore.parseVars(provider, macro, params[1], false);
/* 29 */       StringBuilder output = new StringBuilder();
/*    */       
/* 31 */       if (Variable.getValidVariableOrArraySpecifier(arrayName) != null) {
/*    */         
/* 33 */         arrayName = Variable.getValidVariableOrArraySpecifier(arrayName);
/*    */         
/* 35 */         if (provider.getArrayExists(macro, arrayName)) {
/*    */           
/* 37 */           int ubound = provider.getArraySize(macro, arrayName);
/* 38 */           boolean first = true;
/*    */           
/* 40 */           for (int offset = 0; offset < ubound; offset++) {
/*    */             
/* 42 */             if (!first) output.append(glue);  first = false;
/* 43 */             output.append(getArrayElement(provider, macro, arrayName, offset));
/*    */           } 
/*    */         } 
/*    */       } 
/*    */       
/* 48 */       retVal.setString(output.toString());
/*    */       
/* 50 */       if (params.length > 2) {
/*    */         
/* 52 */         String varName = ScriptCore.parseVars(provider, macro, params[2], false).toLowerCase();
/* 53 */         ScriptCore.setVariable(provider, macro, varName, output.toString());
/*    */       } 
/*    */     } 
/*    */     
/* 57 */     return (IReturnValue)retVal;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getArrayElement(IScriptActionProvider provider, IMacro macro, String arrayName, int offset) {
/* 69 */     Object element = provider.getArrayElement(macro, arrayName, offset);
/*    */     
/* 71 */     if (element == null) return "";
/*    */     
/* 73 */     if (element instanceof Integer) return String.valueOf(element); 
/* 74 */     if (element instanceof Boolean) return ((Boolean)element).booleanValue() ? "True" : "False";
/*    */     
/* 76 */     return element.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionJoin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */