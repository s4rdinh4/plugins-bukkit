/*     */ package net.eq2online.macros.core.params;
/*     */ 
/*     */ import bxf;
/*     */ import java.util.ArrayList;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.core.MacroParamProvider;
/*     */ import net.eq2online.macros.core.MacroParams;
/*     */ import net.eq2online.macros.gui.controls.GuiTextFieldEx;
/*     */ import net.eq2online.macros.gui.screens.GuiEditListEntry;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*     */ 
/*     */ public class MacroParamFriend
/*     */   extends MacroParamGenericEditableList
/*     */ {
/*     */   public static final String allowedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890_";
/*     */   
/*     */   public MacroParamFriend(MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProvider provider) {
/*  21 */     super(type, target, params, provider);
/*     */     
/*  23 */     if (!Pattern.matches("^[a-zA-Z0-9_]{2,16}$", getParameterValue())) setParameterValue(""); 
/*  24 */     this.maxParameterLength = 16;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean replace() {
/*  33 */     if (this.target.getIteration() == 1 && this.target.getParamStore() != null) this.target.getParamStore().setStoredParam(this.type, 0, getParameterValue());
/*     */     
/*  35 */     if (shouldReplaceFirstOccurrenceOnly()) {
/*     */       
/*  37 */       this.target.setTargetString(this.provider.matcher(this.target.getTargetString()).replaceFirst(getParameterValueEscaped()));
/*     */     }
/*     */     else {
/*     */       
/*  41 */       this.target.setTargetString(this.provider.matcher(this.target.getTargetString()).replaceAll(getParameterValueEscaped()));
/*     */     } 
/*     */     
/*  44 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldReplaceFirstOccurrenceOnly() {
/*  53 */     return (this.replaceFirstOccurrenceOnly || MacroModSettings.getCompilerFlagFriend());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFirstOccurrenceSupported() {
/*  62 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getAllowedCharacters() {
/*  71 */     return "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890_";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPromptMessage() {
/*  80 */     return MacroParam.getLocalisedString("param.prompt.person", new String[] { LocalisationProvider.getLocalisedString("param.prompt.friend"), this.target.getDisplayName() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void autoPopulate() {
/*  89 */     if (this.parentScreen != null)
/*     */     {
/*  91 */       AbstractionLayer.displayGuiScreen((bxf)this.parentScreen);
/*     */     }
/*     */     
/*  94 */     if (this.parentScreen != null)
/*     */     {
/*  96 */       this.parentScreen.onAutoPopulateComplete(this, new ArrayList());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupEditEntryWindow(GuiEditListEntry gui, boolean editing) {
/* 106 */     gui.displayText = LocalisationProvider.getLocalisedString(editing ? "entry.editfriend" : "entry.newfriend");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupEditEntryTextbox(GuiTextFieldEx textField) {
/* 115 */     textField.minStringLength = 2;
/* 116 */     textField.f(16);
/* 117 */     textField.allowedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890_";
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\params\MacroParamFriend.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */