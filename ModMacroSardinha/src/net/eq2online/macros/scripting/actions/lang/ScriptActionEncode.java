/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ import org.bouncycastle.util.encoders.Base64;
/*    */ 
/*    */ 
/*    */ public class ScriptActionEncode
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionEncode(ScriptContext context) {
/* 18 */     super(context, "encode");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 24 */     ReturnValue retVal = new ReturnValue("");
/*    */     
/* 26 */     if (params.length > 0) {
/*    */       
/* 28 */       String input = ScriptCore.parseVars(provider, macro, params[0], false);
/* 29 */       String encoded = new String(Base64.encode(input.getBytes()));
/* 30 */       retVal.setString(encoded);
/*    */       
/* 32 */       if (params.length > 1) {
/*    */         
/* 34 */         String variableName = ScriptCore.parseVars(provider, macro, params[1], false);
/* 35 */         ScriptCore.setVariable(provider, macro, variableName, encoded);
/*    */       } 
/*    */     } 
/*    */     
/* 39 */     return (IReturnValue)retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionEncode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */