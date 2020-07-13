/*    */ package net.eq2online.macros.scripting.parser;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IMacroActionProcessor;
/*    */ 
/*    */ 
/*    */ public class ActionParserDirective
/*    */   extends ActionParserAbstract
/*    */ {
/*    */   public ActionParserDirective(ScriptContext context) {
/* 12 */     super(context);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IMacroAction parse(IMacroActionProcessor actionProcessor, String scriptEntry) {
/* 18 */     Matcher scriptDirectivePatternMatcher = scriptDirectivePattern.matcher(scriptEntry);
/* 19 */     if (scriptDirectivePatternMatcher.matches())
/*    */     {
/* 21 */       return (IMacroAction)parse(actionProcessor, scriptDirectivePatternMatcher.group(1), "", (String)null);
/*    */     }
/*    */     
/* 24 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\parser\ActionParserDirective.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */