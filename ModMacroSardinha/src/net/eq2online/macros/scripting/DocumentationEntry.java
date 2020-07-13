/*     */ package net.eq2online.macros.scripting;
/*     */ 
/*     */ import bty;
/*     */ import bub;
/*     */ import civ;
/*     */ import ckx;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import net.eq2online.xml.Xml;
/*     */ import org.w3c.dom.Node;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DocumentationEntry
/*     */   implements IDocumentationEntry
/*     */ {
/*     */   private final boolean hidden;
/*     */   private final String name;
/*     */   private final String usage;
/*     */   private final String description;
/*     */   private final String returnType;
/*     */   
/*     */   public DocumentationEntry(String name, String usage, String description, String returnType, boolean hidden) {
/*  55 */     this.name = name;
/*  56 */     this.usage = usage;
/*  57 */     this.description = description;
/*  58 */     this.returnType = returnType;
/*  59 */     this.hidden = hidden;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocumentationEntry(Node xmlNode) {
/*  69 */     this.name = Xml.getNodeValue(xmlNode, "sa:name", "");
/*  70 */     this.usage = Xml.getNodeValue(xmlNode, "sa:usage", "");
/*  71 */     this.description = Xml.getNodeValue(xmlNode, "sa:description", "");
/*  72 */     this.returnType = Xml.getNodeValue(xmlNode, "sa:return", "");
/*  73 */     this.hidden = Xml.getAttributeValue(xmlNode, "hidden", "false").equalsIgnoreCase("true");
/*     */   }
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
/*     */   public void drawAt(bty fontRenderer, int xPosition, int yPosition) {
/*  86 */     if (!this.hidden) {
/*     */       
/*  88 */       int descriptionWidth = fontRenderer.a(this.description);
/*  89 */       int usageWidth = fontRenderer.a(this.usage);
/*     */       
/*  91 */       int width = Math.max(descriptionWidth, usageWidth) + 6;
/*  92 */       int height = 25;
/*     */       
/*  94 */       GL.glPushMatrix();
/*  95 */       GL.glTranslatef(xPosition, yPosition, 0.0F);
/*     */       
/*  97 */       bub.a(2, 2, width + 2, height + 2, -2147483648);
/*     */       
/*  99 */       drawGradientCornerRect(0, 0, width, height, -1140850859, -18, 0.73F, 0.5F);
/*     */       
/* 101 */       fontRenderer.a(this.usage, 3, 3, 11141120);
/* 102 */       fontRenderer.a(this.description, 3, 13, 170);
/*     */       
/* 104 */       GL.glPopMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawGradientCornerRect(int x1, int y1, int x2, int y2, int colour1, int colour2, float colour2opacity, float blendFactor) {
/* 113 */     float zLevel = 0.0F;
/* 114 */     float opacity = (colour1 >> 24 & 0xFF) / 255.0F;
/*     */     
/* 116 */     float red1 = (colour1 >> 16 & 0xFF) / 255.0F;
/* 117 */     float green1 = (colour1 >> 8 & 0xFF) / 255.0F;
/* 118 */     float blue1 = (colour1 & 0xFF) / 255.0F;
/*     */     
/* 120 */     float red2 = (colour2 >> 16 & 0xFF) / 255.0F;
/* 121 */     float green2 = (colour2 >> 8 & 0xFF) / 255.0F;
/* 122 */     float blue2 = (colour2 & 0xFF) / 255.0F;
/*     */     
/* 124 */     float redmid = red1 * (1.0F - blendFactor) + red2 * blendFactor;
/* 125 */     float greenmid = green1 * (1.0F - blendFactor) + green2 * blendFactor;
/* 126 */     float bluemid = blue1 * (1.0F - blendFactor) + blue2 * blendFactor;
/* 127 */     float alphamid = opacity * (1.0F - blendFactor) + colour2opacity * blendFactor;
/*     */     
/* 129 */     GL.glDisableTexture2D();
/* 130 */     GL.glEnableBlend();
/* 131 */     GL.glDisableAlphaTest();
/* 132 */     GL.glBlendFunc(770, 771);
/* 133 */     GL.glShadeModel(7425);
/* 134 */     ckx tessellator = ckx.a();
/* 135 */     civ worldRender = tessellator.c();
/* 136 */     worldRender.b();
/* 137 */     worldRender.a(redmid, greenmid, bluemid, alphamid);
/* 138 */     worldRender.b(x1, y2, zLevel);
/* 139 */     worldRender.a(red1, green1, blue1, opacity);
/* 140 */     worldRender.b(x2, y2, zLevel);
/* 141 */     worldRender.a(redmid, greenmid, bluemid, alphamid);
/* 142 */     worldRender.b(x2, y1, zLevel);
/* 143 */     worldRender.a(red2, green2, blue2, colour2opacity);
/* 144 */     worldRender.b(x1, y1, zLevel);
/* 145 */     tessellator.b();
/* 146 */     GL.glShadeModel(7424);
/* 147 */     GL.glDisableBlend();
/* 148 */     GL.glEnableAlphaTest();
/* 149 */     GL.glEnableTexture2D();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHidden() {
/* 155 */     return this.hidden;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 161 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUsage() {
/* 167 */     return this.usage;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 173 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getReturnType() {
/* 179 */     return this.returnType;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\DocumentationEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */