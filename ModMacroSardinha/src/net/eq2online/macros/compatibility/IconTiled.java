/*     */ package net.eq2online.macros.compatibility;
/*     */ 
/*     */ import com.mumfrey.liteloader.util.render.Icon;
/*     */ import oa;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IconTiled
/*     */   implements Icon
/*     */ {
/*     */   private oa textureResource;
/*     */   protected int iconID;
/*     */   protected int iconU;
/*     */   protected int iconV;
/*     */   private int width;
/*     */   private int height;
/*     */   private float uCoord;
/*     */   private float uCoord2;
/*     */   private float vCoord;
/*     */   private float vCoord2;
/*     */   private int textureWidth;
/*     */   private int textureHeight;
/*     */   
/*     */   public IconTiled(oa textureResource, int id) {
/*  25 */     this(textureResource, id, 16);
/*     */   }
/*     */ 
/*     */   
/*     */   public IconTiled(oa textureResource, int id, int iconSize) {
/*  30 */     this(textureResource, id, iconSize, 256);
/*     */   }
/*     */ 
/*     */   
/*     */   public IconTiled(oa textureResource, int id, int iconSize, int textureSize) {
/*  35 */     this(textureResource, id, iconSize, id % textureSize / iconSize * iconSize, id / textureSize / iconSize * iconSize, textureSize);
/*     */   }
/*     */ 
/*     */   
/*     */   public IconTiled(oa textureResource, int id, int iconSize, int iconU, int iconV) {
/*  40 */     this(textureResource, id, iconSize, iconU, iconV, 256);
/*     */   }
/*     */ 
/*     */   
/*     */   public IconTiled(oa textureResource, int id, int iconSize, int iconU, int iconV, int textureSize) {
/*  45 */     this(textureResource, id, iconU, iconV, iconSize, iconSize, textureSize, textureSize);
/*     */   }
/*     */ 
/*     */   
/*     */   public IconTiled(oa textureResource, int id, int iconU, int iconV, int width, int height, int textureWidth, int textureHeight) {
/*  50 */     this.iconID = id;
/*  51 */     this.textureResource = textureResource;
/*     */     
/*  53 */     this.textureWidth = textureWidth;
/*  54 */     this.textureHeight = textureHeight;
/*     */     
/*  56 */     this.width = width;
/*  57 */     this.height = height;
/*     */     
/*  59 */     init(iconU, iconV);
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
/*     */   protected void init(int iconU, int iconV) {
/*  72 */     this.iconU = iconU;
/*  73 */     this.iconV = iconV;
/*     */     
/*  75 */     this.uCoord = iconU / this.textureWidth;
/*  76 */     this.uCoord2 = (iconU + this.width) / this.textureWidth;
/*  77 */     this.vCoord = iconV / this.textureHeight;
/*  78 */     this.vCoord2 = (iconV + this.height) / this.textureHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public oa getTextureResource() {
/*  83 */     return this.textureResource;
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind() {
/*  88 */     AbstractionLayer.bindTexture(this.textureResource);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIconID() {
/*  93 */     return this.iconID;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIconID(int id) {
/*  98 */     this.iconID = id;
/*  99 */     init(id % 16 * 16, id / 16 * 16);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIconWidth() {
/* 105 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIconHeight() {
/* 111 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMinU() {
/* 117 */     return this.uCoord;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMaxU() {
/* 123 */     return this.uCoord2 - Float.MIN_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getInterpolatedU(double slice) {
/* 129 */     float uSize = this.uCoord2 - this.uCoord;
/* 130 */     return this.uCoord + uSize * (float)slice / 16.0F - Float.MIN_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMinV() {
/* 136 */     return this.vCoord;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMaxV() {
/* 142 */     return this.vCoord2 - Float.MIN_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getInterpolatedV(double slice) {
/* 148 */     float vSize = this.vCoord2 - this.vCoord;
/* 149 */     return this.vCoord + vSize * (float)slice / 16.0F - Float.MIN_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIconName() {
/* 155 */     return this.textureResource + "_" + this.iconID;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\compatibility\IconTiled.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */