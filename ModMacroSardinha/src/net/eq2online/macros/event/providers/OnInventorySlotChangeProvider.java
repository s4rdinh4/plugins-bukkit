/*    */ package net.eq2online.macros.event.providers;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import net.eq2online.macros.scripting.api.IMacroEvent;
/*    */ import net.eq2online.macros.scripting.api.IMacroEventVariableProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OnInventorySlotChangeProvider
/*    */   implements IMacroEventVariableProvider
/*    */ {
/*    */   private int oldValue;
/*    */   
/*    */   public OnInventorySlotChangeProvider(IMacroEvent event) {}
/*    */   
/*    */   public void updateVariables(boolean clock) {}
/*    */   
/*    */   public Object getVariable(String variableName) {
/* 30 */     return variableName.equals("OLDINVSLOT") ? Integer.valueOf(this.oldValue) : null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Set<String> getVariables() {
/* 36 */     Set<String> variables = new HashSet<String>();
/* 37 */     variables.add("OLDINVSLOT");
/* 38 */     return variables;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onInit() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void initInstance(String[] instanceVariables) {
/* 49 */     this.oldValue = Integer.parseInt(instanceVariables[0]) + 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\event\providers\OnInventorySlotChangeProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */