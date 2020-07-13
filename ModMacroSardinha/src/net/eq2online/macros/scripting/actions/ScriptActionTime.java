/*    */ package net.eq2online.macros.scripting.actions;
/*    */ 
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionTime
/*    */   extends ScriptAction
/*    */ {
/* 17 */   SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
/* 18 */   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
/*    */ 
/*    */   
/*    */   public ScriptActionTime(ScriptContext context) {
/* 22 */     super(context, "time");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 31 */     IReturnValue retVal = null;
/* 32 */     String output = null;
/* 33 */     SimpleDateFormat useFormat = this.isoFormat;
/*    */     
/* 35 */     if (params.length > 1) {
/*    */ 
/*    */       
/*    */       try {
/* 39 */         this.format.applyPattern(ScriptCore.parseVars(provider, macro, params[1], false));
/*    */       }
/* 41 */       catch (Exception ex) {
/*    */         
/* 43 */         this.format.applyPattern("'Bad date format'");
/*    */       } 
/*    */       
/* 46 */       useFormat = this.format;
/*    */     } 
/*    */     
/* 49 */     output = useFormat.format(new Date());
/* 50 */     ReturnValue returnValue = new ReturnValue(output);
/*    */     
/* 52 */     if (params.length > 0)
/*    */     {
/* 54 */       ScriptCore.setVariable(provider, macro, params[0], output);
/*    */     }
/*    */     
/* 57 */     return (IReturnValue)returnValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\ScriptActionTime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */