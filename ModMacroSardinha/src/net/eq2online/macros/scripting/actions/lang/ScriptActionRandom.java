/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionRandom
/*    */   extends ScriptAction
/*    */ {
/* 16 */   private static Random rand = new Random();
/*    */ 
/*    */   
/*    */   public ScriptActionRandom(ScriptContext context) {
/* 20 */     super(context, "random");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 29 */     ReturnValue retVal = new ReturnValue(0);
/*    */     
/* 31 */     if (params.length > 0) {
/*    */       
/* 33 */       int min = 0;
/* 34 */       int max = 100;
/*    */       
/* 36 */       if (params.length > 1)
/*    */       {
/* 38 */         max = ScriptCore.tryParseInt(ScriptCore.parseVars(provider, macro, params[1], false), 100);
/*    */       }
/*    */       
/* 41 */       if (params.length > 2)
/*    */       {
/* 43 */         min = ScriptCore.tryParseInt(ScriptCore.parseVars(provider, macro, params[2], false), 0);
/*    */       }
/*    */       
/* 46 */       if (max < min) {
/*    */         
/* 48 */         int swap = min;
/* 49 */         min = max;
/* 50 */         max = swap;
/*    */       } 
/*    */       
/* 53 */       int randomNumber = rand.nextInt(max - min + 1) + min;
/*    */       
/* 55 */       retVal.setInt(randomNumber);
/* 56 */       ScriptCore.setVariable(provider, macro, params[0], randomNumber);
/*    */     }
/*    */     else {
/*    */       
/* 60 */       int randomNumber = rand.nextInt(101);
/* 61 */       retVal.setInt(randomNumber);
/*    */     } 
/*    */     
/* 64 */     return (IReturnValue)retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionRandom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */