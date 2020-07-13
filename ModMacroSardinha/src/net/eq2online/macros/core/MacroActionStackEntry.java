/*     */ package net.eq2online.macros.core;
/*     */ 
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionContext;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionProcessor;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionStackEntry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MacroActionStackEntry
/*     */   implements IMacroActionStackEntry
/*     */ {
/*     */   private int stackPointer;
/*     */   private boolean conditionalFlag = false;
/*     */   private boolean ifFlag = false;
/*     */   private boolean elseFlag = false;
/*     */   private IMacroAction action;
/*     */   
/*     */   public MacroActionStackEntry(int pointer, IMacroAction action, boolean conditional) {
/*  51 */     this.stackPointer = pointer;
/*  52 */     this.action = action;
/*  53 */     this.conditionalFlag = conditional;
/*  54 */     this.ifFlag = conditional;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStackPushOperator() {
/*  63 */     if (this.action == null || this.action.getAction() == null) return false; 
/*  64 */     return this.action.getAction().isStackPushOperator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBePoppedBy(IMacroAction action) {
/*  73 */     if (this.action == null || this.action.getAction() == null) return true; 
/*  74 */     return this.action.getAction().canBePoppedBy(action.getAction());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void executeStackPop(IMacroActionProcessor processor, IMacroActionContext context, IMacro macro, IMacroAction popAction) {
/*  83 */     this.action.executeStackPop(processor, context, macro, popAction);
/*     */     
/*  85 */     if (this.stackPointer == -1) {
/*  86 */       this.action.setState(null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConditionalOperator() {
/*  95 */     if (this.action == null || this.action.getAction() == null) return false; 
/*  96 */     return this.action.getAction().isConditionalOperator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConditionalElseOperator(IMacroAction action) {
/* 105 */     if (this.action == null || action == null || this.action.getAction() == null || action.getAction() == null) return false; 
/* 106 */     return action.getAction().isConditionalElseOperator(this.action.getAction());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesConditionalOperator(IMacroAction action) {
/* 115 */     if (this.action == null || action == null || this.action.getAction() == null || action.getAction() == null) return false; 
/* 116 */     return action.getAction().matchesConditionalOperator(this.action.getAction());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getConditionalFlag() {
/* 125 */     return this.conditionalFlag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConditionalFlag(boolean newFlag) {
/* 134 */     this.conditionalFlag = newFlag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIfFlag() {
/* 143 */     return this.ifFlag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIfFlag(boolean newFlag) {
/* 152 */     this.ifFlag = newFlag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getElseFlag() {
/* 161 */     return this.elseFlag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setElseFlag(boolean newFlag) {
/* 170 */     this.elseFlag = newFlag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMacroAction getAction() {
/* 179 */     return this.action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStackPointer() {
/* 188 */     return this.stackPointer;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\MacroActionStackEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */