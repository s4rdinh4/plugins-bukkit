/*    */ package net.eq2online.macros.scripting.iterators;
/*    */ 
/*    */ import ard;
/*    */ import atr;
/*    */ import bec;
/*    */ import bru;
/*    */ import brv;
/*    */ import bsu;
/*    */ import dt;
/*    */ import java.util.HashMap;
/*    */ import net.eq2online.macros.scripting.ScriptedIterator;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.variable.BlockPropertyTracker;
/*    */ import net.eq2online.macros.scripting.variable.IVariableStore;
/*    */ 
/*    */ public class ScriptedIteratorProperties
/*    */   extends ScriptedIterator
/*    */   implements IVariableStore
/*    */ {
/*    */   public ScriptedIteratorProperties(IScriptActionProvider provider, IMacro macro) {
/* 22 */     super(provider, macro);
/*    */     
/* 24 */     bsu mc = bsu.z();
/* 25 */     bru objectHit = mc.s;
/*    */     
/* 27 */     if (objectHit != null && objectHit.a == brv.b && mc.f != null) {
/*    */       
/* 29 */       dt blockPos = objectHit.a();
/* 30 */       bec blockState = mc.f.p(blockPos);
/* 31 */       atr block = blockState.c();
/* 32 */       bec actualState = block.a(blockState, (ard)mc.f, blockPos);
/* 33 */       new BlockPropertyTracker("", this, false, actualState);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void storeVariable(String name, boolean value) {
/* 40 */     HashMap<String, Object> vars = new HashMap<String, Object>();
/* 41 */     vars.put("PROPNAME", name);
/* 42 */     vars.put("PROPVALUE", Boolean.valueOf(value));
/* 43 */     this.items.add(vars);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void storeVariable(String name, int value) {
/* 49 */     HashMap<String, Object> vars = new HashMap<String, Object>();
/* 50 */     vars.put("PROPNAME", name);
/* 51 */     vars.put("PROPVALUE", Integer.valueOf(value));
/* 52 */     this.items.add(vars);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void storeVariable(String name, String value) {
/* 58 */     HashMap<String, Object> vars = new HashMap<String, Object>();
/* 59 */     vars.put("PROPNAME", name);
/* 60 */     vars.put("PROPVALUE", value);
/* 61 */     this.items.add(vars);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\iterators\ScriptedIteratorProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */