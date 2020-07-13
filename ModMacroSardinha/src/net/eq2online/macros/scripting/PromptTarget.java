/*     */ package net.eq2online.macros.scripting;
/*     */ 
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.core.MacroParams;
/*     */ import net.eq2online.macros.core.params.MacroParam;
/*     */ import net.eq2online.macros.interfaces.IMacroParamStorage;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PromptTarget
/*     */   implements IMacroParamTarget
/*     */ {
/*     */   private IMacro parent;
/*     */   private String content;
/*     */   private String prompt;
/*     */   private String defaultValue;
/*     */   private MacroParams paramProvider;
/*     */   private boolean completed = false;
/*     */   private boolean success = false;
/*     */   
/*     */   public PromptTarget(IMacro parent, String content, String prompt, String defaultValue) {
/*  28 */     this.parent = parent;
/*  29 */     this.content = content.replace("££", "$$");
/*  30 */     this.prompt = prompt;
/*  31 */     this.defaultValue = defaultValue;
/*  32 */     this.paramProvider = new MacroParams(this);
/*     */     
/*  34 */     compile();
/*     */     
/*  36 */     if (!this.paramProvider.hasRemainingParams()) {
/*     */       
/*  38 */       Log.info("Found no params in \"{0}\"", new Object[] { this.content });
/*  39 */       handleCompleted();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDefaultValue() {
/*  45 */     return this.defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getCompleted() {
/*  50 */     return this.completed;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getSuccess() {
/*  55 */     return this.success;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDisplayName() {
/*  61 */     return (this.parent != null) ? this.parent.getDisplayName() : "Macro";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPromptMessage() {
/*  70 */     return this.prompt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasRemainingParams() {
/*  76 */     return this.paramProvider.hasRemainingParams();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MacroParam getNextParam() {
/*  82 */     return this.paramProvider.getNextParam();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIteration() {
/*  88 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIterationString() {
/*  94 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void compile() {
/* 100 */     this.paramProvider.evaluateParams();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void recompile() {
/* 106 */     compile();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTargetString() {
/* 112 */     return this.content;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTargetString(String newString) {
/* 118 */     this.content = newString;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMacroParamStorage getParamStore() {
/* 124 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleCompleted() {
/* 130 */     this.completed = true;
/* 131 */     this.success = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleCancelled() {
/* 137 */     this.completed = true;
/* 138 */     this.success = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\PromptTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */