/*    */ package net.eq2online.macros.gui.ext;
/*    */ 
/*    */ import bsu;
/*    */ import bug;
/*    */ import buv;
/*    */ import byj;
/*    */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*    */ import net.eq2online.macros.core.MacroModSettings;
/*    */ import net.eq2online.macros.input.InputHandler;
/*    */ 
/*    */ 
/*    */ public class BindScreenOptionEntry
/*    */   implements buv
/*    */ {
/*    */   private final bsu minecraft;
/*    */   private final int maxDescriptionWidth;
/*    */   private final String description;
/*    */   private final bug modeButton;
/*    */   
/*    */   public BindScreenOptionEntry(bsu minecraft, byj controlsGui, int maxDescriptionWidth) {
/* 21 */     this.minecraft = minecraft;
/* 22 */     this.maxDescriptionWidth = maxDescriptionWidth;
/*    */     
/* 24 */     this.description = LocalisationProvider.getLocalisedString("options.option.mode.description");
/* 25 */     this.modeButton = new bug(0, 0, 0, 110, 18, "");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(int id, int xPosition, int yPosition, int width, int height, int mouseX, int mouseY, boolean mouseOver) {
/* 31 */     this.minecraft.k.a(this.description, xPosition + 90 - this.maxDescriptionWidth, yPosition + height / 2 - this.minecraft.k.a / 2, 16777215);
/*    */     
/* 33 */     this.modeButton.h = xPosition + 105;
/* 34 */     this.modeButton.i = yPosition;
/* 35 */     this.modeButton.j = MacroModSettings.bindingMode.getDescription();
/* 36 */     this.modeButton.l = (MacroModSettings.bindingMode != InputHandler.BindingComboMode.Disabled);
/* 37 */     this.modeButton.a(this.minecraft, mouseX, mouseY);
/* 38 */     this.modeButton.l = true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean a(int id, int mouseX, int mouseY, int button, int relX, int relY) {
/* 44 */     if (this.modeButton.c(this.minecraft, mouseX, mouseY)) {
/*    */       
/* 46 */       MacroModSettings.bindingMode = MacroModSettings.bindingMode.getNextMode();
/* 47 */       MacroModSettings.save();
/* 48 */       return true;
/*    */     } 
/*    */     
/* 51 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void b(int id, int mouseX, int mouseY, int button, int p_148277_5_, int p_148277_6_) {
/* 57 */     this.modeButton.a(mouseX, mouseY);
/*    */   }
/*    */   
/*    */   public void a(int p_178011_1_, int p_178011_2_, int p_178011_3_) {}
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\ext\BindScreenOptionEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */