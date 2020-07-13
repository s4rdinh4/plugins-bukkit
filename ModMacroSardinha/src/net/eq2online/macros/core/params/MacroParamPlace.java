/*     */ package net.eq2online.macros.core.params;
/*     */ 
/*     */ import bxf;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.Macro;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.core.MacroParamProvider;
/*     */ import net.eq2online.macros.core.MacroParams;
/*     */ import net.eq2online.macros.gui.controls.GuiTextFieldEx;
/*     */ import net.eq2online.macros.gui.screens.GuiEditListEntry;
/*     */ import net.eq2online.macros.interfaces.IListObject;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*     */ import net.eq2online.macros.struct.Place;
/*     */ 
/*     */ public class MacroParamPlace
/*     */   extends MacroParamGenericEditableList {
/*     */   public MacroParamPlace(MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProvider provider) {
/*  19 */     super(type, target, params, provider);
/*  20 */     this.enableTextField = Boolean.valueOf(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean replace() {
/*  29 */     if (this.target.getIteration() == 1 && this.target.getParamStore() != null) this.target.getParamStore().setStoredParam(this.type, getParameterValue());
/*     */     
/*  31 */     Place location = Place.getByName(getParameterValue());
/*     */     
/*  33 */     if (location != null) {
/*     */       
/*  35 */       String script = this.target.getTargetString();
/*  36 */       script = script.replaceAll("\\$\\$px", "" + location.x);
/*  37 */       script = script.replaceAll("\\$\\$py", "" + location.y);
/*  38 */       script = script.replaceAll("\\$\\$pz", "" + location.z);
/*  39 */       script = script.replaceAll("\\$\\$pn", "" + Macro.escapeReplacement(location.name));
/*  40 */       script = script.replaceAll("\\$\\$p", "" + String.format(MacroModSettings.coordsFormat, new Object[] { Integer.valueOf(location.x), Integer.valueOf(location.y), Integer.valueOf(location.z) }));
/*  41 */       this.target.setTargetString(script);
/*     */     }
/*     */     else {
/*     */       
/*  45 */       this.target.setTargetString(this.provider.matcher(this.target.getTargetString()).replaceAll(""));
/*     */     } 
/*     */     
/*  48 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPromptMessage() {
/*  57 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAutoPopulateSupported() {
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addItem(GuiEditListEntry gui, String newItem, String displayName, int iconID, Object newItemData) {
/*  75 */     Place place = Place.parsePlace(newItem, gui.getXCoordText(), gui.getYCoordText(), gui.getZCoordText(), true);
/*     */     
/*  77 */     if (place != null) {
/*     */       
/*  79 */       if (Place.exists(place.name)) {
/*     */         
/*  81 */         String placeName = place.name;
/*  82 */         int offset = 1;
/*     */         
/*  84 */         while (Place.exists(placeName + "[" + offset + "]")) {
/*  85 */           offset++;
/*     */         }
/*  87 */         place.name = placeName + "[" + offset + "]";
/*     */       } 
/*     */       
/*  90 */       super.addItem(gui, place.name, displayName, -1, place);
/*     */     } 
/*     */     
/*  93 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void editItem(GuiEditListEntry gui, String editedText, String displayName, int editedIconID, IListObject editedObject) {
/*     */     try {
/* 104 */       int x = Integer.parseInt(gui.getXCoordText());
/* 105 */       int y = Integer.parseInt(gui.getYCoordText());
/* 106 */       int z = Integer.parseInt(gui.getZCoordText());
/*     */       
/* 108 */       Place place = (Place)editedObject.getData();
/*     */       
/* 110 */       place.name = Place.resolveConflictingName(editedText, place);
/* 111 */       place.x = x;
/* 112 */       place.y = y;
/* 113 */       place.z = z;
/*     */       
/* 115 */       editedObject.setText(place.name);
/* 116 */       this.itemListBox.save();
/*     */       
/* 118 */       AbstractionLayer.displayGuiScreen((bxf)this.parentScreen);
/*     */     }
/* 120 */     catch (NumberFormatException numberFormatException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteSelectedItem(boolean response) {
/* 130 */     if (this.itemListBox != null)
/*     */     {
/* 132 */       if (response) {
/*     */         
/* 134 */         this.itemListBox.removeSelectedItem();
/* 135 */         this.itemListBox.save();
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 140 */         setParameterValue(((Place)this.itemListBox.getSelectedItem().getData()).name);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleListItemClick(IListObject selectedItem) {
/* 151 */     setParameterValue(((Place)selectedItem.getData()).name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupEditEntryWindow(GuiEditListEntry gui, boolean editing) {
/* 160 */     gui.displayText = LocalisationProvider.getLocalisedString(editing ? "entry.editplace" : "entry.newplace");
/* 161 */     gui.enableIconChoice = false;
/* 162 */     gui.enableCoords = true;
/* 163 */     gui.windowHeight = 140;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupEditEntryTextbox(GuiTextFieldEx textField) {
/* 172 */     textField.minStringLength = 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\params\MacroParamPlace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */