/*    */ package net.eq2online.macros.scripting;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.Set;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.IScriptedIterator;
/*    */ 
/*    */ public abstract class ScriptedIterator
/*    */   implements IScriptedIterator
/*    */ {
/* 13 */   protected ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
/*    */   
/* 15 */   protected int pointer = 0;
/*    */   
/*    */   protected IScriptActionProvider provider;
/*    */   
/*    */   protected IMacro macro;
/*    */   
/*    */   protected boolean breakLoop;
/*    */ 
/*    */   
/*    */   public ScriptedIterator(IScriptActionProvider provider, IMacro macro) {
/* 25 */     this.provider = provider;
/* 26 */     this.macro = macro;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueLooping() {
/* 35 */     return (!this.breakLoop && this.pointer < this.items.size());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void increment() {
/* 44 */     this.pointer++;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void reset() {
/* 53 */     this.pointer = 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void breakLoop() {
/* 59 */     this.breakLoop = true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateVariables(boolean clock) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public Set<String> getVariables() {
/* 70 */     return ((HashMap)this.items.get(this.pointer)).keySet();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getVariable(String variableName) {
/* 76 */     if (this.pointer < this.items.size()) {
/*    */       
/* 78 */       HashMap<String, Object> currentScope = this.items.get(this.pointer);
/* 79 */       return currentScope.get(variableName);
/*    */     } 
/*    */     
/* 82 */     return null;
/*    */   }
/*    */   
/*    */   public void onInit() {}
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\ScriptedIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */