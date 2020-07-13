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
/*    */ public class OnJoinGameProvider
/*    */   implements IMacroEventVariableProvider
/*    */ {
/*    */   public OnJoinGameProvider(IMacroEvent event) {}
/*    */   
/*    */   public void updateVariables(boolean clock) {}
/*    */   
/*    */   public Object getVariable(String variableName) {
/* 23 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Set<String> getVariables() {
/* 29 */     return new HashSet<String>();
/*    */   }
/*    */   
/*    */   public void onInit() {}
/*    */   
/*    */   public void initInstance(String[] instanceVariables) {}
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\event\providers\OnJoinGameProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */