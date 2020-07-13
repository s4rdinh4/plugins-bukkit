/*    */ package net.eq2online.macros.gui.ext;
/*    */ 
/*    */ import bsu;
/*    */ import bug;
/*    */ import buv;
/*    */ import bxf;
/*    */ import byj;
/*    */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*    */ import net.eq2online.macros.gui.screens.GuiMacroBind;
/*    */ 
/*    */ 
/*    */ public class BindingButtonEntry
/*    */   implements buv
/*    */ {
/*    */   private byj controlsGui;
/*    */   private final bsu minecraft;
/*    */   private final bug bindingButton;
/*    */   
/*    */   public BindingButtonEntry(bsu minecraft, byj controlsGui) {
/* 20 */     this.controlsGui = controlsGui;
/* 21 */     this.minecraft = minecraft;
/*    */     
/* 23 */     this.bindingButton = new bug(0, 0, 0, 110, 18, LocalisationProvider.getLocalisedString("gui.bindings.goto"));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(int id, int xPosition, int yPosition, int width, int height, int mouseX, int mouseY, boolean mouseOver) {
/* 29 */     this.bindingButton.h = xPosition + 105;
/* 30 */     this.bindingButton.i = yPosition;
/*    */     
/* 32 */     this.bindingButton.a(this.minecraft, mouseX, mouseY);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean a(int id, int mouseX, int mouseY, int button, int relX, int relY) {
/* 38 */     if (this.bindingButton.c(this.minecraft, mouseX, mouseY)) {
/*    */       
/* 40 */       this.minecraft.a((bxf)new GuiMacroBind(true, (this.minecraft.f == null) ? (bxf)this.controlsGui : null));
/* 41 */       return true;
/*    */     } 
/*    */     
/* 44 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void b(int id, int mouseX, int mouseY, int button, int p_148277_5_, int p_148277_6_) {
/* 50 */     this.bindingButton.a(mouseX, mouseY);
/*    */   }
/*    */   
/*    */   public void a(int p_178011_1_, int p_178011_2_, int p_178011_3_) {}
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\ext\BindingButtonEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */