/*     */ package net.eq2online.macros.gui.layout;
/*     */ 
/*     */ import bty;
/*     */ import civ;
/*     */ import ckx;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Rectangle;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.interfaces.ISettingsProvider;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ import net.eq2online.util.Colour;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LayoutButton
/*     */   extends LayoutWidget
/*     */ {
/*  35 */   public static String[] nameOverrides = new String[10000];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  41 */   public static int[] symbolOverrides = new int[10000];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   public static int[] widthOverrides = new int[10000];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   public static int[] heightOverrides = new int[10000]; protected int textOffsetX;
/*     */   protected int textOffsetY;
/*     */   
/*     */   static {
/*  56 */     nameOverrides[1] = "ESC"; nameOverrides[12] = "-"; nameOverrides[13] = "="; nameOverrides[14] = "<-"; nameOverrides[26] = "[";
/*  57 */     nameOverrides[27] = "]"; nameOverrides[28] = "RTN"; nameOverrides[29] = "CTRL"; nameOverrides[39] = ";"; nameOverrides[40] = "#";
/*  58 */     nameOverrides[41] = "'"; nameOverrides[42] = "#"; nameOverrides[43] = "\\"; nameOverrides[51] = ","; nameOverrides[52] = ".";
/*  59 */     nameOverrides[53] = "/"; nameOverrides[54] = "#"; nameOverrides[55] = "[*]"; nameOverrides[56] = "ALT"; nameOverrides[58] = "CAPS";
/*  60 */     nameOverrides[69] = "NUM"; nameOverrides[70] = "SCRL"; nameOverrides[71] = "[7]"; nameOverrides[72] = "[8]"; nameOverrides[73] = "[9]";
/*  61 */     nameOverrides[74] = "[-]"; nameOverrides[75] = "[4]"; nameOverrides[76] = "[5]"; nameOverrides[77] = "[6]"; nameOverrides[78] = "[+]";
/*  62 */     nameOverrides[79] = "[1]"; nameOverrides[80] = "[2]"; nameOverrides[81] = "[3]"; nameOverrides[82] = "[0]"; nameOverrides[83] = "[.]";
/*  63 */     nameOverrides[209] = "PGDN"; nameOverrides[201] = "PGUP"; nameOverrides[181] = "[/]"; nameOverrides[210] = "INS"; nameOverrides[211] = "DEL";
/*  64 */     nameOverrides[207] = "END"; nameOverrides[200] = "#"; nameOverrides[203] = "#"; nameOverrides[208] = "#"; nameOverrides[205] = "#";
/*  65 */     nameOverrides[156] = "ENT"; nameOverrides[157] = "CTRL";
/*     */     
/*  67 */     symbolOverrides[42] = 30; symbolOverrides[54] = 30; symbolOverrides[156] = 17; symbolOverrides[200] = 24; symbolOverrides[203] = 27;
/*  68 */     symbolOverrides[205] = 26; symbolOverrides[208] = 25; symbolOverrides[248] = 24; symbolOverrides[249] = 25;
/*     */     
/*  70 */     widthOverrides[14] = 36; widthOverrides[28] = 28; widthOverrides[42] = 16; widthOverrides[54] = 52; widthOverrides[57] = 112;
/*  71 */     widthOverrides[55] = 32; widthOverrides[181] = 32; widthOverrides[199] = 32; widthOverrides[210] = 32; widthOverrides[211] = 32;
/*  72 */     widthOverrides[207] = 32; widthOverrides[200] = 32; widthOverrides[203] = 32; widthOverrides[205] = 32; widthOverrides[208] = 32;
/*  73 */     widthOverrides[15] = 24; widthOverrides[69] = 32; widthOverrides[71] = 32; widthOverrides[72] = 32; widthOverrides[73] = 32;
/*  74 */     widthOverrides[74] = 32; widthOverrides[75] = 32; widthOverrides[76] = 32; widthOverrides[77] = 32; widthOverrides[78] = 32;
/*  75 */     widthOverrides[79] = 32; widthOverrides[80] = 32; widthOverrides[81] = 32; widthOverrides[82] = 68; widthOverrides[83] = 32;
/*  76 */     widthOverrides[156] = 32; widthOverrides[248] = 10; widthOverrides[249] = 10;
/*     */     
/*  78 */     heightOverrides[28] = 28; heightOverrides[78] = 30; heightOverrides[156] = 30;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean centreText = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int u;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int v;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LayoutButton(bty fontRenderer, int widgetId, String name, int width, int height, boolean centre) {
/* 108 */     super(fontRenderer, name, width, height, centre);
/*     */     
/* 110 */     this.widgetId = widgetId;
/* 111 */     this.displayText = (nameOverrides[widgetId] == null) ? this.name : nameOverrides[widgetId];
/*     */ 
/*     */     
/* 114 */     if (heightOverrides[widgetId] > 0)
/*     */     {
/* 116 */       this.height = heightOverrides[widgetId];
/*     */     }
/*     */ 
/*     */     
/* 120 */     this.textOffsetX = 0;
/* 121 */     this.textOffsetY = this.height / 2 - 4;
/*     */ 
/*     */     
/* 124 */     if (width > 0) {
/*     */       
/* 126 */       this.width = width;
/*     */     }
/* 128 */     else if (widthOverrides[widgetId] > 0) {
/*     */       
/* 130 */       this.width = widthOverrides[widgetId];
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 135 */       this.width = Math.max(height, fontRenderer.a(this.displayText) + this.textOffsetY * 2);
/* 136 */       this.width += 4 - width % 4;
/*     */     } 
/*     */ 
/*     */     
/* 140 */     this.u = symbolOverrides[widgetId] % 16 * 16;
/* 141 */     this.v = symbolOverrides[widgetId] / 16 * 16;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 150 */     return "{" + this.widgetId + "," + this.xPosition + "," + this.yPosition + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void toggleReservedState() {
/* 159 */     if (this.widgetId > 254)
/*     */       return; 
/* 161 */     MacroModSettings.toggleReservedKeyState(this.widgetId);
/*     */     
/* 163 */     MacroModCore.getMacroManager().save();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawWidget(LayoutPanelStandard parent, Rectangle boundingBox, int mouseX, int mouseY, LayoutPanelEditMode mode, boolean selected, boolean denied) {
/* 172 */     int xPos = parent.getXPosition() + this.drawX;
/* 173 */     int yPos = parent.getYPosition() + this.yPosition;
/*     */     
/* 175 */     boolean bound = getWidgetIsBound();
/* 176 */     boolean special = MacroModCore.getMacroManager().isReservedKey(this.widgetId);
/* 177 */     boolean global = MacroModCore.getMacroManager().isMacroGlobal(this.widgetId, false);
/*     */ 
/*     */     
/* 180 */     int colour = bound ? COLOR_BOUND : COLOR_UNBOUND;
/* 181 */     if (special) colour = bound ? COLOR_BOUNDSPECIAL : COLOR_SPECIAL; 
/* 182 */     if (global) colour = COLOR_BOUNDGLOBAL; 
/* 183 */     if (selected) colour = COLOR_SELECTED; 
/* 184 */     if (denied) colour = COLOR_DENIED;
/*     */ 
/*     */     
/* 187 */     int borderColour = -8355712;
/* 188 */     int backgroundColour = 0;
/*     */     
/* 190 */     if (mode == LayoutPanelEditMode.Reserve)
/*     */     {
/* 192 */       if (MacroModSettings.reservedKeys.contains(Integer.valueOf(this.widgetId))) {
/*     */         
/* 194 */         colour = -65536;
/* 195 */         borderColour = -65536;
/* 196 */         backgroundColour = 872349696;
/*     */       }
/*     */       else {
/*     */         
/* 200 */         if (special) borderColour = COLOR_SPECIAL; 
/* 201 */         colour = special ? COLOR_SPECIAL : COLOR_UNBOUND;
/*     */       } 
/*     */     }
/*     */     
/* 205 */     boolean mouseOver = mouseOver((Rectangle)null, mouseX, mouseY, selected);
/*     */     
/* 207 */     if (mouseOver) {
/*     */ 
/*     */       
/* 210 */       borderColour = -1;
/*     */     }
/* 212 */     else if (mode == LayoutPanelEditMode.EditAll) {
/*     */ 
/*     */       
/* 215 */       borderColour = selected ? -103 : -256;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 220 */     else if (bound) {
/*     */       
/* 222 */       switch (mode) {
/*     */         case Copy:
/* 224 */           borderColour = -16711936; break;
/* 225 */         case Move: borderColour = -16711681; break;
/* 226 */         case Delete: borderColour = -65536;
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 231 */     int width = this.width;
/*     */     
/* 233 */     String trimmedText = this.fontRenderer.a(this.displayText, this.width - this.textOffsetX - 8);
/* 234 */     if (trimmedText.length() < this.displayText.length() && mouseOver) {
/*     */       
/* 236 */       int textWidth = this.fontRenderer.a(this.displayText);
/* 237 */       width = textWidth + this.textOffsetX + 6;
/*     */     } 
/*     */ 
/*     */     
/* 241 */     a(xPos, yPos, xPos + width, yPos + this.height, borderColour);
/* 242 */     a(xPos + 1, yPos + 1, xPos + width, yPos + this.height, 1711276032);
/* 243 */     a(xPos + 1, yPos + 1, xPos + width - 1, yPos + this.height - 1, -16777216);
/* 244 */     drawGradientCornerRect(xPos + 1, yPos + 1, xPos + width - 1, yPos + this.height - 1, mouseOver ? 872415231 : backgroundColour, colour, mouseOver ? 0.5F : 0.25F, mouseOver ? 0.75F : 0.5F);
/*     */     
/* 246 */     if (symbolOverrides[this.widgetId] == 0) {
/*     */ 
/*     */       
/* 249 */       if (this.centreText) {
/* 250 */         a(this.fontRenderer, this.displayText, xPos + width / 2 + this.textOffsetX, yPos + this.textOffsetY, colour);
/*     */       
/*     */       }
/* 253 */       else if (trimmedText.length() < this.displayText.length() && !mouseOver) {
/* 254 */         c(this.fontRenderer, trimmedText + "...", xPos + this.textOffsetX, yPos + this.textOffsetY, colour);
/*     */       } else {
/* 256 */         c(this.fontRenderer, this.displayText, xPos + this.textOffsetX, yPos + this.textOffsetY, colour);
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 262 */       float f = (colour >> 24 & 0xFF) / 255.0F;
/* 263 */       float f1 = (colour >> 16 & 0xFF) / 255.0F;
/* 264 */       float f2 = (colour >> 8 & 0xFF) / 255.0F;
/* 265 */       float f3 = (colour & 0xFF) / 255.0F;
/* 266 */       GL.glColor4f(f1, f2, f3, f);
/*     */ 
/*     */       
/* 269 */       GL.glEnableTexture2D();
/* 270 */       AbstractionLayer.bindTexture(ResourceLocations.DEFAULTFONT);
/* 271 */       GL.glDisableLighting();
/*     */ 
/*     */       
/* 274 */       int x = xPos + this.width / 2 - 4 + this.textOffsetX;
/* 275 */       int y = yPos + this.textOffsetY;
/*     */ 
/*     */       
/* 278 */       drawTexturedModalRect(x, y, x + 8, y + 8, this.u, this.v, this.u + 16, this.v + 16);
/*     */     } 
/*     */     
/* 281 */     if (mode != LayoutPanelEditMode.Reserve) {
/*     */       
/* 283 */       if (MacroModCore.getMacroManager().isKeyAlwaysOverridden(this.widgetId, false, true)) {
/*     */         
/* 285 */         AbstractionLayer.bindTexture(ResourceLocations.MAIN);
/* 286 */         GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 287 */         drawTexturedModalRect(xPos + width - 11, yPos - 1, xPos + width - 1, yPos + 11, 72, 104, 96, 128);
/*     */       } 
/*     */       
/* 290 */       if (MacroModCore.getMacroManager().isKeyOverlaid(this.widgetId))
/*     */       {
/* 292 */         a(xPos, yPos, xPos + width, yPos + this.height, 1610612991);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawGradientCornerRect(int x1, int y1, int x2, int y2, int colour1, int colour2, float colour2opacity, float blendFactor) {
/* 302 */     float zLevel = 0.0F;
/* 303 */     float opacity = (colour1 >> 24 & 0xFF) / 255.0F;
/*     */     
/* 305 */     float red1 = (colour1 >> 16 & 0xFF) / 255.0F;
/* 306 */     float green1 = (colour1 >> 8 & 0xFF) / 255.0F;
/* 307 */     float blue1 = (colour1 & 0xFF) / 255.0F;
/*     */     
/* 309 */     float red2 = (colour2 >> 16 & 0xFF) / 255.0F;
/* 310 */     float green2 = (colour2 >> 8 & 0xFF) / 255.0F;
/* 311 */     float blue2 = (colour2 & 0xFF) / 255.0F;
/*     */     
/* 313 */     float redmid = red1 * (1.0F - blendFactor) + red2 * blendFactor;
/* 314 */     float greenmid = green1 * (1.0F - blendFactor) + green2 * blendFactor;
/* 315 */     float bluemid = blue1 * (1.0F - blendFactor) + blue2 * blendFactor;
/* 316 */     float alphamid = opacity * (1.0F - blendFactor) + colour2opacity * blendFactor;
/*     */     
/* 318 */     GL.glDisableTexture2D();
/* 319 */     GL.glEnableBlend();
/* 320 */     GL.glDisableAlphaTest();
/* 321 */     GL.glBlendFuncSeparate(770, 771, 1, 0);
/* 322 */     GL.glShadeModel(7425);
/* 323 */     ckx tessellator = ckx.a();
/* 324 */     civ worldRender = tessellator.c();
/* 325 */     worldRender.b();
/* 326 */     worldRender.a(redmid, greenmid, bluemid, alphamid);
/* 327 */     worldRender.b(x1, y2, zLevel);
/* 328 */     worldRender.a(red1, green1, blue1, opacity);
/* 329 */     worldRender.b(x2, y2, zLevel);
/* 330 */     worldRender.a(redmid, greenmid, bluemid, alphamid);
/* 331 */     worldRender.b(x2, y1, zLevel);
/* 332 */     worldRender.a(red2, green2, blue2, colour2opacity);
/* 333 */     worldRender.b(x1, y1, zLevel);
/* 334 */     tessellator.b();
/* 335 */     GL.glShadeModel(7424);
/* 336 */     GL.glDisableBlend();
/* 337 */     GL.glEnableAlphaTest();
/* 338 */     GL.glEnableTexture2D();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void notifySettingsLoaded(ISettingsProvider settings) {
/* 348 */     Matcher nameOverrideConfigMatcher = Pattern.compile("\\{([0-9]+),(.+?)\\}(?=[\\r\\n\\{])").matcher(settings.getSetting("keyboard.labels", ""));
/*     */     
/* 350 */     while (nameOverrideConfigMatcher.find()) {
/*     */ 
/*     */       
/*     */       try {
/* 354 */         int key = Integer.parseInt(nameOverrideConfigMatcher.group(1));
/* 355 */         if (key > -1 && key < 10000) {
/* 356 */           nameOverrides[key] = nameOverrideConfigMatcher.group(2);
/*     */         }
/*     */       }
/* 359 */       catch (NumberFormatException numberFormatException) {}
/*     */     } 
/*     */     
/* 362 */     Matcher symbolOverrideConfigPatternMatcher = Pattern.compile("\\{([0-9]+),([0-9]+)\\}").matcher(settings.getSetting("keyboard.symbols", ""));
/*     */     
/* 364 */     while (symbolOverrideConfigPatternMatcher.find()) {
/*     */ 
/*     */       
/*     */       try {
/* 368 */         int key = Integer.parseInt(symbolOverrideConfigPatternMatcher.group(1));
/* 369 */         int symbol = Integer.parseInt(symbolOverrideConfigPatternMatcher.group(2));
/*     */         
/* 371 */         if (key > -1 && key < 10000) {
/* 372 */           symbolOverrides[key] = symbol % 256;
/*     */         }
/*     */       } catch (NumberFormatException ex) {
/* 375 */         Log.printStackTrace(ex);
/*     */       } 
/*     */     } 
/* 378 */     COLOR_UNBOUND = Colour.parseColour(settings.getSetting("keyboard.colour.unbound", ""), -12566464);
/* 379 */     COLOR_BOUND = Colour.parseColour(settings.getSetting("keyboard.colour.bound", ""), -256);
/* 380 */     COLOR_SPECIAL = Colour.parseColour(settings.getSetting("keyboard.colour.reserved", ""), -7864320);
/* 381 */     COLOR_BOUNDSPECIAL = Colour.parseColour(settings.getSetting("keyboard.colour.boundspecial", ""), -22016);
/* 382 */     COLOR_BOUNDGLOBAL = Colour.parseColour(settings.getSetting("keyboard.colour.global", ""), -16711936);
/* 383 */     COLOR_SELECTED = Colour.parseColour(settings.getSetting("keyboard.colour.selected", ""), -1);
/* 384 */     COLOR_DENIED = Colour.parseColour(settings.getSetting("keyboard.colour.denied", ""), -65536);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void saveSettings(ISettingsProvider settings) {
/* 395 */     String nameOverridesConfig = "";
/* 396 */     String symbolOverridesConfig = "";
/*     */     
/* 398 */     for (int key = 0; key < 10000; key++) {
/*     */ 
/*     */       
/* 401 */       if (nameOverrides[key] != null) {
/* 402 */         nameOverridesConfig = nameOverridesConfig + "{" + key + "," + nameOverrides[key].replaceAll("\\}", "\\}") + "}";
/*     */       }
/*     */       
/* 405 */       if (symbolOverrides[key] > 0) {
/* 406 */         symbolOverridesConfig = symbolOverridesConfig + "{" + key + "," + symbolOverrides[key] + "}";
/*     */       }
/*     */     } 
/* 409 */     if (nameOverridesConfig.length() > 0) {
/*     */       
/* 411 */       settings.setSetting("keyboard.labels", nameOverridesConfig);
/* 412 */       settings.setSettingComment("keyboard.labels", "Overrides for some keys in the display, specifies alternate text for the key");
/*     */     } 
/*     */     
/* 415 */     if (symbolOverridesConfig.length() > 0) {
/*     */       
/* 417 */       settings.setSetting("keyboard.symbols", symbolOverridesConfig);
/* 418 */       settings.setSettingComment("keyboard.symbols", "Overrides for some keys in the display, show an ASCII symbol instead of text");
/*     */     } 
/*     */     
/* 421 */     settings.setSetting("keyboard.colour.unbound", Colour.getHexColour(COLOR_UNBOUND));
/* 422 */     settings.setSetting("keyboard.colour.bound", Colour.getHexColour(COLOR_BOUND));
/* 423 */     settings.setSetting("keyboard.colour.reserved", Colour.getHexColour(COLOR_SPECIAL));
/* 424 */     settings.setSetting("keyboard.colour.boundspecial", Colour.getHexColour(COLOR_BOUNDSPECIAL));
/* 425 */     settings.setSetting("keyboard.colour.global", Colour.getHexColour(COLOR_BOUNDGLOBAL));
/* 426 */     settings.setSetting("keyboard.colour.selected", Colour.getHexColour(COLOR_SELECTED));
/* 427 */     settings.setSetting("keyboard.colour.denied", Colour.getHexColour(COLOR_DENIED));
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\layout\LayoutButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */