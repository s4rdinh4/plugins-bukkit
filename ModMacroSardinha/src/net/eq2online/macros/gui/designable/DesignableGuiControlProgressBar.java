/*     */ package net.eq2online.macros.gui.designable;
/*     */ 
/*     */ import java.awt.Rectangle;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxControlProperties;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxProgressBarProperties;
/*     */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.util.Colour;
/*     */ 
/*     */ 
/*     */ public class DesignableGuiControlProgressBar
/*     */   extends DesignableGuiControl
/*     */ {
/*     */   protected int currentMin;
/*     */   protected int currentMax;
/*     */   protected int currentValue;
/*     */   protected boolean calcMin;
/*     */   protected boolean calcMax;
/*     */   
/*     */   public enum BarStyle
/*     */   {
/*  23 */     Horizontal,
/*  24 */     Vertical;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  31 */   protected BarStyle style = BarStyle.Horizontal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DesignableGuiControlProgressBar(int id) {
/*  38 */     super(id);
/*  39 */     this.padding = 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getWidgetIsBindable() {
/*  48 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initProperties() {
/*  57 */     setProperty("expression", "");
/*  58 */     setProperty("min", 0);
/*  59 */     setProperty("max", 100);
/*  60 */     setProperty("calcmin", false);
/*  61 */     setProperty("calcmax", false);
/*  62 */     setProperty("colour", "00FF00");
/*  63 */     setProperty("background", "AA000000");
/*  64 */     setProperty("style", "horizontal");
/*  65 */     setProperty("visible", true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPropertyWithValidation(String property, String stringValue, int intValue, boolean boolValue) {
/*  71 */     if (property.equals("expression")) setProperty(property, stringValue); 
/*  72 */     if (property.equals("calcmin") || property.equals("calcmax")) setProperty(property, boolValue); 
/*  73 */     if (property.equals("style") && stringValue.matches("^(horizontal|vertical)$")) setProperty(property, stringValue); 
/*  74 */     if (property.equals("colour") || property.equals("background"))
/*     */     {
/*  76 */       setProperty(property, Colour.sanitiseColour(stringValue, property.equals("colour") ? -16711936 : -1442840576));
/*     */     }
/*     */     
/*  79 */     super.setPropertyWithValidation(property, stringValue, intValue, boolValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTick() {
/*  88 */     IScriptActionProvider provider = ScriptContext.MAIN.getScriptActionProvider();
/*  89 */     if (provider != null) {
/*     */       
/*  91 */       calcMinMax(provider);
/*  92 */       this.currentValue = Math.min(Math.max(provider.getExpressionEvaluator(null, getProperty("expression", "100")).evaluate(), this.currentMin), this.currentMax);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getControlType() {
/* 102 */     return "progressbar";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDialogBoxControlProperties getPropertiesDialog(GuiScreenEx parentScreen) {
/* 108 */     return (GuiDialogBoxControlProperties)new GuiDialogBoxProgressBarProperties(parentScreen, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void update() {
/* 117 */     this.foreColour = Colour.parseColour(getProperty("colour", "FF00FF00"), -16711936);
/* 118 */     this.backColour = Colour.parseColour(getProperty("background", "B0000000"), -1442840576);
/*     */     
/* 120 */     this.calcMin = getProperty("calcmin", false);
/* 121 */     this.calcMax = getProperty("calcmax", false);
/*     */     
/* 123 */     calcMinMax(ScriptContext.MAIN.getScriptActionProvider());
/*     */     
/* 125 */     this.currentValue = Math.min(Math.max(this.currentValue, this.currentMin), this.currentMax);
/* 126 */     this.style = getProperty("style", "horizontal").equalsIgnoreCase("vertical") ? BarStyle.Vertical : BarStyle.Horizontal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void calcMinMax(IScriptActionProvider provider) {
/* 134 */     if (provider != null) {
/*     */       
/* 136 */       this.currentMin = this.calcMin ? provider.getExpressionEvaluator(null, getProperty("min", "0")).evaluate() : getProperty("min", 0);
/* 137 */       this.currentMax = this.calcMax ? provider.getExpressionEvaluator(null, getProperty("max", "100")).evaluate() : getProperty("max", 100);
/*     */     }
/*     */     else {
/*     */       
/* 141 */       this.currentMin = getProperty("min", 0);
/* 142 */       this.currentMax = getProperty("max", 100);
/*     */     } 
/*     */     
/* 145 */     if (this.currentMax <= this.currentMin) this.currentMax = this.currentMin + 1;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void draw(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY) {
/* 154 */     if (drawProgressBar(boundingBox, this.backColour, true)) {
/* 155 */       drawRectOutline(boundingBox, this.foreColour, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawWidget(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY, int drawColour) {
/* 164 */     drawProgressBar(boundingBox, 0, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean drawProgressBar(Rectangle boundingBox, int backColour, boolean playback) {
/* 175 */     if (playback && !isVisible()) return false; 
/* 176 */     this.e = -this.zIndex;
/*     */     
/* 178 */     if (backColour != 0)
/*     */     {
/* 180 */       a(boundingBox.x, boundingBox.y, boundingBox.x + boundingBox.width, boundingBox.y + boundingBox.height, backColour);
/*     */     }
/*     */     
/* 183 */     float fValue = (this.currentValue - this.currentMin);
/* 184 */     float fSize = (this.currentMax - this.currentMin);
/*     */     
/* 186 */     if (fSize > 0.0F)
/*     */     
/* 188 */     { switch (this.style)
/*     */       
/*     */       { case Vertical:
/* 191 */           drawVerticalProgressBar(boundingBox, fValue, fSize);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 200 */           return true; }  drawHorizontalProgressBar(boundingBox, fValue, fSize); }  return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawHorizontalProgressBar(Rectangle boundingBox, float fValue, float fSize) {
/* 210 */     int padding = (boundingBox.height > this.padding * 2 + 1) ? this.padding : 0;
/*     */     
/* 212 */     float innerWidth = Math.max(boundingBox.width - padding * 2, 1);
/* 213 */     int innerHeight = Math.max(boundingBox.height - padding * 2, 1);
/*     */     
/* 215 */     int barSize = (int)(fValue / fSize * innerWidth);
/*     */     
/* 217 */     a(boundingBox.x + padding, boundingBox.y + padding, boundingBox.x + padding + barSize, boundingBox.y + padding + innerHeight, this.foreColour);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawVerticalProgressBar(Rectangle boundingBox, float fValue, float fSize) {
/* 227 */     int padding = (boundingBox.width > this.padding * 2 + 1) ? this.padding : 0;
/*     */     
/* 229 */     int innerWidth = Math.max(boundingBox.width - padding * 2, 1);
/* 230 */     float innerHeight = Math.max(boundingBox.height - padding * 2, 1);
/*     */     
/* 232 */     int barSize = (int)(innerHeight - fValue / fSize * innerHeight);
/*     */     
/* 234 */     a(boundingBox.x + padding, boundingBox.y + padding + barSize, boundingBox.x + padding + innerWidth, boundingBox.y + padding + (int)innerHeight, this.foreColour);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\designable\DesignableGuiControlProgressBar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */