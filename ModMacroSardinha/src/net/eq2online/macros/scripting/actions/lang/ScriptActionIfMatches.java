/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ 
/*    */ public class ScriptActionIfMatches
/*    */   extends ScriptActionIf
/*    */ {
/*    */   public ScriptActionIfMatches(ScriptContext context) {
/* 16 */     super(context, "ifmatches");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean executeConditional(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 25 */     if (params.length > 1) {
/*    */       
/* 27 */       String subject = ScriptCore.parseVars(provider, macro, params[0], false);
/* 28 */       String pattern = ScriptCore.parseVars(provider, macro, params[1], false);
/*    */ 
/*    */       
/*    */       try {
/* 32 */         Pattern regex = Pattern.compile(pattern, 2);
/* 33 */         Matcher matcher = regex.matcher(subject);
/*    */         
/* 35 */         if (matcher.find())
/*    */         {
/* 37 */           if (params.length > 2) {
/*    */             
/* 39 */             int groupNumber = Math.min(Math.max((params.length > 3) ? ScriptCore.tryParseInt(ScriptCore.parseVars(provider, macro, params[3], false), 0) : 0, 0), matcher.groupCount());
/* 40 */             ScriptCore.setVariable(provider, macro, params[2], matcher.group(groupNumber));
/*    */           } 
/*    */           
/* 43 */           return true;
/*    */         }
/*    */       
/* 46 */       } catch (IllegalArgumentException illegalArgumentException) {}
/*    */     } 
/*    */     
/* 49 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionIfMatches.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */