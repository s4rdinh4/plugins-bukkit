/*    */ package net.eq2online.macros.scripting.iterators;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import net.eq2online.macros.scripting.ScriptedIterator;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScriptedIteratorArray
/*    */   extends ScriptedIterator
/*    */ {
/*    */   public ScriptedIteratorArray(IScriptActionProvider provider, IMacro macro, String arrayName, String valueVarName, String offsetVarName) {
/* 18 */     super(provider, macro);
/*    */     
/* 20 */     if (provider.getArrayExists(macro, arrayName)) {
/*    */       
/* 22 */       int ubound = provider.getArraySize(macro, arrayName);
/*    */       
/* 24 */       for (int offset = 0; offset < ubound; offset++) {
/*    */         
/* 26 */         HashMap<String, Object> vars = new HashMap<String, Object>();
/* 27 */         vars.put(valueVarName, provider.getArrayElement(macro, arrayName, offset));
/* 28 */         if (offsetVarName != null) vars.put(offsetVarName, Integer.valueOf(offset)); 
/* 29 */         this.items.add(vars);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\iterators\ScriptedIteratorArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */