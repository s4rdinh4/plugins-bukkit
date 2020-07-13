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
/*    */ public class OnPlayerJoinedProvider
/*    */   implements IMacroEventVariableProvider
/*    */ {
/*    */   String joinedPlayer;
/*    */   
/*    */   public OnPlayerJoinedProvider(IMacroEvent event) {}
/*    */   
/*    */   public void updateVariables(boolean clock) {}
/*    */   
/*    */   public Object getVariable(String variableName) {
/* 25 */     return variableName.equals("JOINEDPLAYER") ? this.joinedPlayer : null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Set<String> getVariables() {
/* 31 */     Set<String> variables = new HashSet<String>();
/* 32 */     variables.add("JOINEDPLAYER");
/* 33 */     return variables;
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
/* 44 */     this.joinedPlayer = instanceVariables[0];
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\event\providers\OnPlayerJoinedProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */