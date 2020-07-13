/*    */ package net.eq2online.macros.gui.layout;
/*    */ 
/*    */ import bsu;
/*    */ import com.mumfrey.liteloader.gl.GL;
/*    */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*    */ import net.eq2online.macros.core.MacroModCore;
/*    */ import net.eq2online.macros.core.MacroType;
/*    */ import net.eq2online.macros.res.ResourceLocations;
/*    */ 
/*    */ public class LayoutPanelKeys
/*    */   extends LayoutPanelStandard
/*    */ {
/*    */   public LayoutPanelKeys(bsu minecraft, int controlId) {
/* 14 */     super(minecraft, controlId, "keyboard.layout", MacroType.Key);
/*    */ 
/*    */     
/* 17 */     MacroModCore.registerSettingsProvider(this);
/*    */     
/* 19 */     this.defaultLayout = "{1,20,4}{2,36,24}{3,56,24}{4,76,24}{5,96,24}{6,116,24}{7,136,24}{8,156,24}{9,176,24}{10,196,24}{11,216,24}{12,236,24}{13,256,24}{14,288,24}{15,16,40}{16,44,40}{17,64,40}{18,84,40}{19,104,40}{20,124,40}{21,144,40}{22,164,40}{23,184,40}{24,204,40}{25,224,40}{26,244,40}{27,264,40}{28,292,40}{29,20,88}{30,48,56}{31,68,56}{32,88,56}{33,108,56}{34,128,56}{35,148,56}{36,168,56}{37,188,56}{38,208,56}{39,228,56}{40,268,56}{41,248,56}{42,12,72}{43,36,72}{44,56,72}{45,76,72}{46,96,72}{47,116,72}{48,136,72}{49,156,72}{50,176,72}{51,196,72}{52,216,72}{53,236,72}{54,280,72}{55,368,116}{57,136,88}{58,20,56}{59,48,4}{60,68,4}{61,88,4}{62,108,4}{63,132,4}{64,152,4}{65,172,4}{66,192,4}{67,216,4}{68,240,4}{69,296,116}{70,368,4}{71,296,132}{72,332,132}{73,368,132}{74,404,116}{75,296,148}{76,332,148}{77,368,148}{78,404,132}{79,296,164}{80,332,164}{81,368,164}{82,314,180}{83,368,180}{87,268,4}{88,296,4}{156,404,164}{157,252,88}{181,332,116}{183,332,4}{197,404,4}{199,368,24}{200,368,72}{201,404,24}{203,332,88}{205,404,88}{207,368,40}{208,368,88}{209,404,40}{210,332,24}{211,332,40}{219,56,88}{220,216,88}{248,92,116}{249,92,132}{250,26,108}{251,164,108}{252,168,152}{253,26,152}{254,26,136}";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawCustomStuff(bsu minecraft, int mouseX, int mouseY) {
/* 25 */     setTexMapSize(256);
/*    */     
/* 27 */     AbstractionLayer.bindTexture(ResourceLocations.MOUSE_IMAGE);
/* 28 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 29 */     GL.glDisableBlend();
/* 30 */     drawTexturedModalRect(getXPosition() + 35, getYPosition() + 108, getXPosition() + 35 + 128, getYPosition() + 108 + 64, 0, 0, 256, 128);
/* 31 */     GL.glDisableBlend();
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\layout\LayoutPanelKeys.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */