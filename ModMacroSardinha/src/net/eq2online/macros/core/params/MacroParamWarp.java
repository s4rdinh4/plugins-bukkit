/*     */ package net.eq2online.macros.core.params;
/*     */ 
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.core.MacroParamProvider;
/*     */ import net.eq2online.macros.core.MacroParams;
/*     */ import net.eq2online.macros.gui.controls.GuiTextFieldEx;
/*     */ import net.eq2online.macros.gui.screens.GuiEditListEntry;
/*     */ import net.eq2online.macros.interfaces.IListObject;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ import oa;
/*     */ 
/*     */ public class MacroParamWarp
/*     */   extends MacroParamGenericEditableList
/*     */ {
/*     */   public MacroParamWarp(MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProvider provider) {
/*  18 */     super(type, target, params, provider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean replace() {
/*  27 */     if (this.target.getIteration() == 1 && this.target.getParamStore() != null) this.target.getParamStore().setStoredParam(this.type, 0, getParameterValue());
/*     */     
/*  29 */     if (shouldReplaceFirstOccurrenceOnly()) {
/*     */       
/*  31 */       this.target.setTargetString(this.provider.matcher(this.target.getTargetString()).replaceFirst(getParameterValueEscaped()));
/*     */     }
/*     */     else {
/*     */       
/*  35 */       this.target.setTargetString(this.provider.matcher(this.target.getTargetString()).replaceAll(getParameterValueEscaped()));
/*     */     } 
/*     */     
/*  38 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldReplaceFirstOccurrenceOnly() {
/*  47 */     return (this.replaceFirstOccurrenceOnly || MacroModSettings.getCompilerFlagWarp());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFirstOccurrenceSupported() {
/*  56 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPromptMessage() {
/*  65 */     return MacroParam.getLocalisedString("param.prompt.warp", new String[] { this.target.getDisplayName() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void editItem(GuiEditListEntry gui, String editedText, String displayName, int editedIconID, IListObject editedObject) {
/*  74 */     editedObject.setDisplayName(displayName);
/*  75 */     super.editItem(gui, editedText, displayName, editedIconID, editedObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupEditEntryWindow(GuiEditListEntry gui, boolean editing) {
/*  84 */     gui.displayText = LocalisationProvider.getLocalisedString(editing ? "entry.editwarp" : "entry.newwarp");
/*  85 */     gui.enableDisplayName = true;
/*  86 */     gui.windowHeight = 198;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupEditEntryTextbox(GuiTextFieldEx textField) {
/*  95 */     textField.minStringLength = 1;
/*  96 */     textField.f(100);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public oa getIconTexture() {
/* 105 */     return ResourceLocations.DYNAMIC_TOWNS;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\params\MacroParamWarp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */