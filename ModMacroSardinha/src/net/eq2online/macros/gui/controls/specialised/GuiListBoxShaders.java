/*    */ package net.eq2online.macros.gui.controls.specialised;
/*    */ 
/*    */ import bsu;
/*    */ import com.mumfrey.liteloader.util.render.Icon;
/*    */ import net.eq2online.macros.compatibility.IconTiled;
/*    */ import net.eq2online.macros.gui.list.ListObjectGeneric;
/*    */ import net.eq2online.macros.res.ResourceLocations;
/*    */ 
/*    */ public class GuiListBoxShaders extends GuiListBoxResourcePack {
/* 10 */   private static final String[] shaders = new String[] { "notch", "fxaa", "art", "bumpy", "blobs2", "pencil", "color_convolve", "deconverge", "flip", "invert", "ntsc", "outline", "phosphor", "scan_pincushion", "sobel", "bits", "desaturate", "green", "blur", "wobble", "blobs", "antialias", "creeper", "spider" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GuiListBoxShaders(bsu minecraft, int controlId, boolean showIcons) {
/* 21 */     super(minecraft, controlId, showIcons);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void refresh() {
/* 30 */     this.items.clear();
/* 31 */     this.items.add(new ListObjectGeneric(0, "No shader", "", (Icon)new IconTiled(ResourceLocations.SHADERS, 0, 16, 128)));
/*    */     
/* 33 */     int id = 1;
/* 34 */     for (String shader : shaders)
/*    */     {
/* 36 */       this.items.add(new ListObjectGeneric(id, shader, shader, (Icon)new IconTiled(ResourceLocations.SHADERS, id++, 16, 128)));
/*    */     }
/*    */     
/* 39 */     this.scrollBar.setMax(Math.max(0, this.items.size() - this.displayRowCount));
/* 40 */     updateScrollPosition();
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\controls\specialised\GuiListBoxShaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */