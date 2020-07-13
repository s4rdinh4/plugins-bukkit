/*    */ package net.eq2online.macros.event.providers;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import net.eq2online.macros.core.MacroModCore;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OnSendChatMessageProvider
/*    */   implements IMacroEventVariableProvider
/*    */ {
/*    */   private String chat;
/*    */   private String uuid;
/*    */   private String newMessage;
/*    */   private boolean pass = true;
/*    */   
/*    */   public OnSendChatMessageProvider(IMacroEvent event) {}
/*    */   
/*    */   public void updateVariables(boolean clock) {}
/*    */   
/*    */   public Object getVariable(String variableName) {
/* 37 */     if (variableName.equals("CHAT")) return this.chat;
/*    */     
/* 39 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Set<String> getVariables() {
/* 45 */     Set<String> variables = new HashSet<String>();
/* 46 */     variables.add("CHAT");
/* 47 */     return variables;
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
/* 58 */     this.chat = instanceVariables[0];
/* 59 */     this.uuid = instanceVariables[1];
/*    */   }
/*    */ 
/*    */   
/*    */   public void setPass(boolean pass) {
/* 64 */     this.pass = pass;
/* 65 */     MacroModCore.getMacroManager().getBuiltinEventDispatcher().addChatMessageHandler(this.uuid, this);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isPass() {
/* 70 */     return this.pass;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isModified() {
/* 75 */     return (this.newMessage != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public String newMessage() {
/* 80 */     return this.newMessage;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\event\providers\OnSendChatMessageProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */