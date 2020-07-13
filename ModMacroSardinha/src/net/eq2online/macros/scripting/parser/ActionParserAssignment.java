/*    */ package net.eq2online.macros.scripting.parser;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import net.eq2online.macros.scripting.Variable;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IMacroActionProcessor;
/*    */ 
/*    */ 
/*    */ public class ActionParserAssignment
/*    */   extends ActionParserAbstract
/*    */ {
/*    */   public ActionParserAssignment(ScriptContext context) {
/* 13 */     super(context);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IMacroAction parse(IMacroActionProcessor actionProcessor, String scriptEntry) {
/* 19 */     int equals = scriptEntry.indexOf('=');
/* 20 */     if (equals > -1) {
/*    */       
/* 22 */       String varName = scriptEntry.substring(0, equals).trim();
/* 23 */       if (Variable.isValidVariableOrArraySpecifier(varName)) {
/*    */         
/* 25 */         String expression = scriptEntry.substring(equals + 1).trim();
/* 26 */         Matcher scriptExpressionActionPatternMatcher = scriptActionPattern.matcher(expression);
/*    */         
/* 28 */         if (scriptExpressionActionPatternMatcher.matches())
/*    */         {
/* 30 */           return (IMacroAction)parse(actionProcessor, scriptExpressionActionPatternMatcher.group(1), scriptExpressionActionPatternMatcher.group(2), varName);
/*    */         }
/*    */         
/* 33 */         if (expression.trim().startsWith("\"") && expression.trim().endsWith("\"")) {
/*    */           
/* 35 */           StringBuilder rawParams = new StringBuilder();
/* 36 */           ScriptCore.tokenize(expression, ' ', '"', '"', '\\', rawParams);
/* 37 */           expression = (rawParams.length() > 0) ? rawParams.substring(1) : "";
/*    */         } 
/*    */         
/* 40 */         return (IMacroAction)getInstance(actionProcessor, "ASSIGN", expression, new String[] { varName, expression }, (String)null);
/*    */       } 
/*    */     } 
/*    */     
/* 44 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\parser\ActionParserAssignment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */