/*    */ package net.eq2online.macros.gui.layout;
/*    */ 
/*    */ import bty;
/*    */ import com.mumfrey.liteloader.gl.GL;
/*    */ import com.mumfrey.liteloader.util.render.Icon;
/*    */ import java.awt.Rectangle;
/*    */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*    */ import net.eq2online.macros.compatibility.IconTiled;
/*    */ import net.eq2online.macros.core.MacroModCore;
/*    */ import net.eq2online.macros.event.MacroEventManager;
/*    */ import net.eq2online.macros.res.ResourceLocations;
/*    */ import net.eq2online.macros.scripting.api.IMacroEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LayoutButtonEvent
/*    */   extends LayoutButton
/*    */ {
/*    */   protected MacroEventManager eventManager;
/*    */   
/*    */   public LayoutButtonEvent(bty fontRenderer, int widgetId, String name, int width, int height, boolean centre) {
/* 22 */     super(fontRenderer, widgetId, name, width, height, centre);
/* 23 */     this.textOffsetX = 20;
/* 24 */     this.centreText = false;
/*    */     
/* 26 */     this.eventManager = MacroModCore.getMacroManager().getEventManager();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawWidget(LayoutPanelStandard parent, Rectangle boundingBox, int mouseX, int mouseY, LayoutPanelEditMode mode, boolean selected, boolean denied) {
/* 35 */     denied = getWidgetIsDenied();
/*    */     
/* 37 */     super.drawWidget(parent, boundingBox, mouseX, mouseY, mode, selected, denied);
/*    */     
/* 39 */     GL.glEnableTexture2D();
/* 40 */     GL.glDisableLighting();
/* 41 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*    */     
/* 43 */     int xPos = parent.getXPosition() + this.drawX;
/* 44 */     int yPos = parent.getYPosition() + this.yPosition;
/*    */     
/* 46 */     IMacroEvent event = this.eventManager.getEvent(this.widgetId);
/* 47 */     if (event != null) {
/*    */       
/* 49 */       Icon icon = event.getIcon();
/* 50 */       if (icon instanceof IconTiled) ((IconTiled)icon).bind(); 
/* 51 */       drawTexturedModelRectFromIcon(xPos + 4, yPos + 2, icon, 12, 12);
/*    */     } 
/*    */     
/* 54 */     if (denied) {
/*    */       
/* 56 */       AbstractionLayer.bindTexture(ResourceLocations.MAIN);
/* 57 */       drawTexturedModalRect(xPos + 4, yPos + 2, xPos + 16, yPos + 14, 184, 0, 208, 24);
/*    */       
/* 59 */       drawTriangle(0, xPos + 1, yPos + 1, xPos + this.width - 1, yPos + this.height - 1, 1627324416);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean getWidgetIsDenied() {
/* 69 */     return !this.eventManager.checkPermission(this.widgetId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getWidgetDeniedText() {
/* 78 */     return "Permission denied by server";
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\layout\LayoutButtonEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */