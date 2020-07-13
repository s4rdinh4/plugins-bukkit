/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import net.eq2online.macros.scripting.Variable;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IScriptAction;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ 
/*    */ public class ScriptActionFor
/*    */   extends ScriptActionDo
/*    */ {
/* 17 */   private static final Pattern expressiveSyntax = Pattern.compile("^(.+?)=(.+?) to (.+?)$", 2);
/*    */ 
/*    */   
/*    */   public ScriptActionFor(ScriptContext context) {
/* 21 */     super(context, "for");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getExpectedPopCommands() {
/* 27 */     return "§eFOR §cexpects §dNEXT";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean executeStackPush(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 36 */     ScriptActionDo.LoopState state = (ScriptActionDo.LoopState)instance.getState();
/*    */     
/* 38 */     if (params.length > 0) {
/*    */       
/* 40 */       if (state == null) {
/*    */         
/* 42 */         String variableName = params[0].toLowerCase();
/* 43 */         String strFrom = null;
/* 44 */         String strTo = null;
/*    */         
/* 46 */         Matcher m = expressiveSyntax.matcher(variableName);
/*    */         
/* 48 */         if (params.length > 2) {
/*    */           
/* 50 */           strFrom = ScriptCore.parseVars(provider, macro, params[1], false);
/* 51 */           strTo = ScriptCore.parseVars(provider, macro, params[2], false);
/*    */         }
/* 53 */         else if (m.matches()) {
/*    */           
/* 55 */           variableName = m.group(1).trim();
/* 56 */           strFrom = m.group(2).trim();
/* 57 */           strTo = m.group(3).trim();
/*    */         } 
/*    */         
/* 60 */         if (strFrom != null && strTo != null && Variable.isValidScalarVariableName(variableName))
/*    */         {
/*    */           
/* 63 */           int from = ScriptCore.tryParseInt(strFrom, 0);
/* 64 */           int to = ScriptCore.tryParseInt(strTo, 0);
/* 65 */           state = new ScriptActionDo.LoopState(this, variableName, from, to);
/* 66 */           instance.setState(state);
/*    */         }
/*    */         else
/*    */         {
/* 70 */           return false;
/*    */         }
/*    */       
/*    */       } else {
/*    */         
/* 75 */         state.Increment();
/*    */       }
/*    */     
/*    */     } else {
/*    */       
/* 80 */       return false;
/*    */     } 
/*    */     
/* 83 */     if (state.ContinueLooping())
/*    */     {
/* 85 */       ScriptCore.setVariable(provider, macro, state.variableName, state.GetOffsetCounter());
/*    */     }
/*    */     
/* 88 */     return state.ContinueLooping();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canBePoppedBy(IScriptAction action) {
/* 97 */     return action instanceof ScriptActionNext;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionFor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */