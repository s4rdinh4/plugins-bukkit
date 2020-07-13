/*    */ package net.eq2online.macros.core;
/*    */ 
/*    */ import bxf;
/*    */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*    */ import net.eq2online.macros.gui.screens.GuiChatEx;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroActionContext;
/*    */ import net.eq2online.macros.scripting.api.IMacroActionProcessor;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ 
/*    */ public class MacroActionChat
/*    */   extends MacroAction {
/*    */   private String message;
/*    */   
/*    */   public MacroActionChat(IMacroActionProcessor actionProcessor, String message) {
/* 16 */     super(actionProcessor);
/* 17 */     this.message = message;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canExecuteNow(IMacroActionContext context, IMacro macro) {
/* 23 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean execute(IMacroActionContext context, IMacro macro, boolean stop, boolean allowLatent) {
/* 29 */     if (this.actionProcessor.getConditionalExecutionState()) {
/*    */       
/* 31 */       String[] messages = this.message.split("[\\x7C\\x82]");
/*    */ 
/*    */       
/* 34 */       if (messages.length == 0 && stop) {
/*    */         
/* 36 */         processStop("");
/* 37 */         return true;
/*    */       } 
/*    */ 
/*    */       
/* 41 */       for (int messageIndex = 0; messageIndex < messages.length; messageIndex++) {
/*    */         
/* 43 */         if (stop && messageIndex == messages.length - 1) {
/*    */           
/* 45 */           processStop(messages[messageIndex]);
/*    */         
/*    */         }
/*    */         else {
/*    */           
/* 50 */           ScriptAction.onActionExecuted();
/*    */           
/* 52 */           context.getProvider().actionSendChatMessage(macro, this, messages[messageIndex]);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 57 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void processStop(String buffer) {
/* 67 */     AbstractionLayer.displayGuiScreen((bxf)new GuiChatEx(buffer));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 73 */     return this.message;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\MacroActionChat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */