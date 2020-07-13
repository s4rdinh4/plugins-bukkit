/*     */ package net.eq2online.macros.gui.designable;
/*     */ 
/*     */ import java.awt.Rectangle;
/*     */ import net.eq2online.macros.core.Macro;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxControlProperties;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxLabelProperties;
/*     */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*     */ import net.eq2online.macros.scripting.VariableExpander;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.util.Colour;
/*     */ import vb;
/*     */ 
/*     */ public class DesignableGuiControlLabel
/*     */   extends DesignableGuiControl {
/*     */   private static final int TOP = 0;
/*     */   private static final int MIDDLE = 1;
/*     */   private static final int BOTTOM = 2;
/*  20 */   private int hAlign = 4; private static final int LEFT = 4; private static final int CENTRE = 8; private static final int RIGHT = 16;
/*  21 */   private int vAlign = 1;
/*     */   
/*     */   private String bindingName;
/*     */   
/*     */   private String bindingValue;
/*     */   
/*     */   private boolean updating;
/*     */ 
/*     */   
/*     */   public DesignableGuiControlLabel(int id) {
/*  31 */     super(id);
/*  32 */     this.padding = 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initProperties() {
/*  38 */     setProperty("binding", "");
/*  39 */     setProperty("text", "Label text");
/*  40 */     setProperty("colour", "00FF00");
/*  41 */     setProperty("background", "AA000000");
/*  42 */     setProperty("align", "top left");
/*  43 */     setProperty("shadow", true);
/*  44 */     setProperty("visible", true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTick() {
/*  50 */     IScriptActionProvider provider = ScriptContext.MAIN.getScriptActionProvider();
/*     */     
/*  52 */     if (provider != null) {
/*     */       
/*  54 */       this.bindingName = getProperty("binding", "");
/*  55 */       if (!this.bindingName.equals("")) {
/*     */         
/*  57 */         if (this.bindingName.indexOf('%') > -1) {
/*     */           
/*  59 */           this.bindingValue = (new VariableExpander(provider, null, this.bindingName, false)).toString();
/*     */         }
/*     */         else {
/*     */           
/*  63 */           this.bindingValue = VariableExpander.expand(provider, null, this.bindingName);
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*  69 */     this.bindingValue = "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getControlType() {
/*  75 */     return "label";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getWidgetIsBindable() {
/*  81 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDialogBoxControlProperties getPropertiesDialog(GuiScreenEx parentScreen) {
/*  90 */     return (GuiDialogBoxControlProperties)new GuiDialogBoxLabelProperties(parentScreen, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getText() {
/*  95 */     if (!this.bindingName.equals("")) {
/*     */       
/*  97 */       String text = getProperty("text", "%%");
/*     */       
/*  99 */       if (text.indexOf("%%") > -1) {
/*     */         
/* 101 */         text = text.replaceAll("%%", Macro.escapeReplacement(this.bindingValue));
/* 102 */         return MacroModCore.convertAmpCodes(text);
/*     */       } 
/*     */     } 
/*     */     
/* 106 */     return MacroModCore.convertAmpCodes(getProperty("text", getName()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPropertyWithValidation(String property, String stringValue, int intValue, boolean boolValue) {
/* 112 */     if (property.equals("text") || property.equals("binding")) setProperty(property, stringValue); 
/* 113 */     if (property.equals("shadow")) setProperty(property, boolValue); 
/* 114 */     if (property.equals("colour") || property.equals("background"))
/*     */     {
/* 116 */       setProperty(property, Colour.sanitiseColour(stringValue, property.equals("colour") ? -16711936 : -1442840576));
/*     */     }
/*     */     
/* 119 */     if (property.equals("align") && stringValue.toLowerCase().matches("^(top|middle|bottom) (left|centre|right)$"))
/*     */     {
/* 121 */       setProperty(property, stringValue.toLowerCase());
/*     */     }
/*     */     
/* 124 */     super.setPropertyWithValidation(property, stringValue, intValue, boolValue);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void update() {
/* 130 */     this.foreColour = Colour.parseColour(getProperty("colour", "00FF00"), -16711936);
/* 131 */     this.backColour = Colour.parseColour(getProperty("background", "AA000000"), -1442840576);
/*     */     
/* 133 */     if (!this.updating) {
/*     */       
/* 135 */       this.updating = true;
/* 136 */       setupAlignment();
/*     */     } 
/*     */     
/* 139 */     this.updating = false;
/* 140 */     this.bindingName = getProperty("binding", "");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setupAlignment() {
/* 145 */     String alignment = getProperty("align", "top left").toLowerCase();
/*     */     
/* 147 */     if (alignment.contains("right")) this.hAlign = 16; 
/* 148 */     if (alignment.contains("centre")) this.hAlign = 8; 
/* 149 */     if (alignment.contains("left")) this.hAlign = 4; 
/* 150 */     if (alignment.contains("bottom")) this.vAlign = 2; 
/* 151 */     if (alignment.contains("middle")) this.vAlign = 1; 
/* 152 */     if (alignment.contains("top")) this.vAlign = 0;
/*     */     
/* 154 */     alignment = ((this.vAlign == 2) ? "bottom " : ((this.vAlign == 1) ? "middle " : "top ")) + ((this.hAlign == 16) ? "right" : ((this.hAlign == 8) ? "centre" : "left"));
/*     */ 
/*     */     
/* 157 */     setProperty("align", alignment);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getXPosition(Rectangle boundingBox, float textLength) {
/* 162 */     if (this.hAlign == 16)
/*     */     {
/* 164 */       return (boundingBox.x + boundingBox.width - this.padding) - textLength;
/*     */     }
/* 166 */     if (this.hAlign == 8)
/*     */     {
/* 168 */       return (boundingBox.x + boundingBox.width / 2) - textLength / 2.0F;
/*     */     }
/*     */ 
/*     */     
/* 172 */     return (boundingBox.x + this.padding);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getYPosition(Rectangle boundingBox, float textHeight) {
/* 178 */     if (this.vAlign == 2)
/*     */     {
/* 180 */       return (boundingBox.y + boundingBox.height - this.padding) - textHeight;
/*     */     }
/* 182 */     if (this.vAlign == 1)
/*     */     {
/* 184 */       return (boundingBox.y + boundingBox.height / 2) - textHeight / 2.0F;
/*     */     }
/*     */ 
/*     */     
/* 188 */     return (boundingBox.y + this.padding);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void draw(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY) {
/* 195 */     if (isVisible())
/*     */     {
/* 197 */       drawLabel(parent, boundingBox, mouseX, mouseY);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawWidget(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY, int drawColour) {
/* 204 */     drawLabel(parent, boundingBox, mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawLabel(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY) {
/* 212 */     drawRect(boundingBox, this.backColour);
/*     */     
/* 214 */     String[] text = MacroModCore.convertAmpCodes(getText()).split("\\\\n");
/* 215 */     int[] rowWidths = new int[text.length];
/*     */     
/* 217 */     float textWidth = 0.0F;
/* 218 */     float textHeight = 8.0F;
/*     */     
/* 220 */     for (int row = 0; row < text.length; row++) {
/*     */       
/* 222 */       rowWidths[row] = this.fontRenderer.a(vb.a(text[row])) + 4; textWidth = Math.max(textWidth, (this.fontRenderer.a(vb.a(text[row])) + 4));
/* 223 */       if (row > 0) textHeight += 10.0F;
/*     */     
/*     */     } 
/* 226 */     int yPos = (int)getYPosition(boundingBox, textHeight);
/* 227 */     boolean shadow = getProperty("shadow", true);
/*     */     
/* 229 */     for (int i = 0; i < text.length; i++) {
/*     */       
/* 231 */       int xPos = (int)getXPosition(boundingBox, rowWidths[i]);
/*     */       
/* 233 */       if (shadow) {
/* 234 */         this.fontRenderer.a(text[i] + "§r", xPos, yPos, this.foreColour);
/*     */       } else {
/* 236 */         this.fontRenderer.a(text[i] + "§r", xPos, yPos, this.foreColour);
/* 237 */       }  yPos += 10;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\designable\DesignableGuiControlLabel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */