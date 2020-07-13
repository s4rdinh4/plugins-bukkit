/*    */ package net.eq2online.macros.scripting.variable.providers;
/*    */ 
/*    */ import net.eq2online.macros.scripting.variable.VariableCache;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ import org.lwjgl.input.Mouse;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VariableProviderInput
/*    */   extends VariableCache
/*    */ {
/*    */   public void updateVariables(boolean clock) {
/* 19 */     storeVariable("CTRL", (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)));
/* 20 */     storeVariable("ALT", (Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184)));
/* 21 */     storeVariable("SHIFT", (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)));
/*    */     
/* 23 */     storeVariable("LMOUSE", Mouse.isButtonDown(0));
/* 24 */     storeVariable("RMOUSE", Mouse.isButtonDown(1));
/* 25 */     storeVariable("MIDDLEMOUSE", Mouse.isButtonDown(2));
/*    */     
/* 27 */     for (int key = 0; key < 255; key++) {
/*    */       
/* 29 */       String keyName = Keyboard.getKeyName(key);
/*    */       
/* 31 */       if (keyName != null)
/*    */       {
/* 33 */         storeVariable("KEY_" + keyName.toUpperCase(), Keyboard.isKeyDown(key));
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getVariable(String variableName) {
/* 41 */     return getCachedValue(variableName);
/*    */   }
/*    */   
/*    */   public void onInit() {}
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\variable\providers\VariableProviderInput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */