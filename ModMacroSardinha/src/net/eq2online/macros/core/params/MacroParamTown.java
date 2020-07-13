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
/*     */ public class MacroParamTown
/*     */   extends MacroParamGenericEditableList
/*     */ {
/*     */   public MacroParamTown(MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProvider provider) {
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
/*  47 */     return (this.replaceFirstOccurrenceOnly || MacroModSettings.getCompilerFlagTown());
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
/*     */   protected String getAllowedCharacters() {
/*  65 */     return super.getAllowedCharacters();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPromptMessage() {
/*  74 */     return MacroParam.getLocalisedString("param.prompt.town", new String[] { this.target.getDisplayName() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void editItem(GuiEditListEntry gui, String editedText, String displayName, int editedIconID, IListObject editedObject) {
/*  83 */     editedObject.setDisplayName(displayName);
/*  84 */     super.editItem(gui, editedText, displayName, editedIconID, editedObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupEditEntryWindow(GuiEditListEntry gui, boolean editing) {
/*  93 */     gui.displayText = LocalisationProvider.getLocalisedString(editing ? "entry.edittown" : "entry.newtown");
/*  94 */     gui.enableDisplayName = true;
/*  95 */     gui.windowHeight = 198;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupEditEntryTextbox(GuiTextFieldEx textField) {
/* 104 */     textField.minStringLength = 1;
/* 105 */     textField.f(100);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public oa getIconTexture() {
/* 114 */     return ResourceLocations.DYNAMIC_TOWNS;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\params\MacroParamTown.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */