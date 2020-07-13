/*    */ package net.eq2online.macros.scripting.iterators;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Set;
/*    */ import net.eq2online.macros.scripting.ScriptedIterator;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ 
/*    */ 
/*    */ public class ScriptedIteratorEnvironment
/*    */   extends ScriptedIterator
/*    */ {
/*    */   public ScriptedIteratorEnvironment(IScriptActionProvider provider, IMacro macro) {
/* 14 */     super(provider, macro);
/*    */     
/* 16 */     Set<String> environmentVariables = provider.getEnvironmentVariables();
/* 17 */     environmentVariables.addAll(macro.getVariables());
/*    */     
/* 19 */     for (String var : environmentVariables) {
/*    */       
/* 21 */       if (var.matches("^~?[A-Z_]+[0-9]*$")) {
/*    */         
/* 23 */         HashMap<String, Object> vars = new HashMap<String, Object>();
/* 24 */         vars.put("VARNAME", var);
/* 25 */         this.items.add(vars);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\iterators\ScriptedIteratorEnvironment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */