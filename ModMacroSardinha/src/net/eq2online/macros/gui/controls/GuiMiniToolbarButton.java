/*    */ package net.eq2online.macros.gui.controls;
/*    */ 
/*    */ import bsu;
/*    */ import com.mumfrey.liteloader.gl.GL;
/*    */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*    */ import net.eq2online.macros.gui.shared.GuiControlEx;
/*    */ import net.eq2online.macros.res.ResourceLocations;
/*    */ 
/*    */ public class GuiMiniToolbarButton
/*    */   extends GuiControlEx
/*    */ {
/*    */   protected int u;
/*    */   protected int v;
/*    */   protected int colour;
/*    */   protected int backColour;
/*    */   public boolean selected = false;
/*    */   
/*    */   public GuiMiniToolbarButton(bsu minecraft, int controlId, int u, int v) {
/* 19 */     super(minecraft, controlId, -100, -100, 18, 12, "");
/* 20 */     this.u = u;
/* 21 */     this.v = v;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean drawControlAt(bsu minecraft, int mouseX, int mouseY, int xPos, int yPos, int colour, int backColour, boolean selected) {
/* 26 */     this.selected = selected;
/* 27 */     return drawControlAt(minecraft, mouseX, mouseY, xPos, yPos, colour, backColour);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean drawControlAt(bsu minecraft, int mouseX, int mouseY, int xPos, int yPos, int colour, int backColour) {
/* 32 */     this.colour = colour;
/* 33 */     this.backColour = backColour;
/* 34 */     setXPosition(xPos);
/* 35 */     setYPosition(yPos);
/* 36 */     drawControl(minecraft, mouseX, mouseY);
/* 37 */     return c(minecraft, mouseX, mouseY);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawControl(bsu minecraft, int mouseX, int mouseY) {
/* 44 */     if (this.m) {
/*    */       
/* 46 */       int bg = this.backColour;
/*    */       
/* 48 */       if (this.selected) {
/*    */         
/* 50 */         a(this.h - 1, this.i - 1, this.h + this.f + 1, this.i + this.g + 1, this.colour);
/* 51 */         bg |= 0xFF000000;
/*    */       } 
/*    */       
/* 54 */       a(this.h, this.i, this.h + this.f, this.i + this.g, bg);
/*    */       
/* 56 */       AbstractionLayer.bindTexture(ResourceLocations.MAIN);
/*    */       
/* 58 */       if (c(minecraft, mouseX, mouseY)) {
/*    */         
/* 60 */         GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*    */       }
/*    */       else {
/*    */         
/* 64 */         float red = (this.colour >> 16 & 0xFF) / 255.0F;
/* 65 */         float blue = (this.colour >> 8 & 0xFF) / 255.0F;
/* 66 */         float green = (this.colour & 0xFF) / 255.0F;
/* 67 */         float alpha = (this.colour >> 24 & 0xFF) / 255.0F;
/* 68 */         GL.glColor4f(red, blue, green, alpha);
/*    */       } 
/*    */       
/* 71 */       drawTexturedModalRect(this.h + 3, this.i + 2, this.h + this.f - 3, this.i + this.g - 2, this.u, this.v, this.u + 24, this.v + 16);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\controls\GuiMiniToolbarButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */