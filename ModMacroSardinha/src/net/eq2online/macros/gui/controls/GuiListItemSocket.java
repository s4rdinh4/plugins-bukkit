/*     */ package net.eq2online.macros.gui.controls;
/*     */ 
/*     */ import bsu;
/*     */ import bty;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Point;
/*     */ import java.security.InvalidParameterException;
/*     */ import java.util.ArrayList;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.gui.shared.GuiControlEx;
/*     */ import net.eq2online.macros.interfaces.IDragDrop;
/*     */ import net.eq2online.macros.interfaces.IListObject;
/*     */ import net.eq2online.macros.interfaces.ISocketListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiListItemSocket
/*     */   extends GuiControlEx
/*     */   implements IDragDrop
/*     */ {
/*  22 */   protected ArrayList<IDragDrop> dragTargets = new ArrayList<IDragDrop>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  27 */   protected IListObject dragItem = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  32 */   protected Point dragOffset = new Point();
/*     */   
/*     */   protected IListObject item;
/*     */   
/*     */   private int iconOffset;
/*     */   
/*     */   private int iconSpacing;
/*     */   
/*     */   private int textOffset;
/*     */   
/*  42 */   public int backColour = -2146562560;
/*     */   
/*     */   private ISocketListener socketListener;
/*     */   
/*     */   private IListObject mouseDownObject;
/*     */   
/*     */   private int timer;
/*     */ 
/*     */   
/*     */   public GuiListItemSocket(bsu minecraft, int controlId, int xPos, int yPos, int controlWidth, int controlHeight, String displayText, ISocketListener socketListener) {
/*  52 */     super(minecraft, controlId, xPos, yPos, controlWidth, controlHeight, displayText);
/*     */     
/*  54 */     this.socketListener = socketListener;
/*     */     
/*  56 */     this.textOffset = (controlHeight - 8) / 2;
/*  57 */     this.iconOffset = (controlHeight - 16) / 2;
/*  58 */     this.iconSpacing = 20;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidDragTarget() {
/*  64 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidDragSource() {
/*  70 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addDragTarget(IDragDrop target) {
/*  81 */     addDragTarget(target, false);
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
/*     */   public void addDragTarget(IDragDrop target, boolean mutual) {
/*  93 */     if (target != null && target.isValidDragTarget()) {
/*     */       
/*  95 */       if (!this.dragTargets.contains(target))
/*     */       {
/*  97 */         this.dragTargets.add(target);
/*     */       }
/*     */       
/* 100 */       if (mutual && target.isValidDragSource())
/*     */       {
/* 102 */         target.addDragTarget(this, false);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 107 */       throw new InvalidParameterException("Target control is not a valid drag target");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeDragTarget(IDragDrop target) {
/* 119 */     removeDragTarget(target, false);
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
/*     */   public void removeDragTarget(IDragDrop target, boolean mutual) {
/* 131 */     if (this.dragTargets.contains(target)) {
/* 132 */       this.dragTargets.remove(target);
/*     */     }
/* 134 */     if (mutual) {
/* 135 */       target.removeDragTarget(this, false);
/*     */     }
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
/*     */   
/*     */   public boolean dragDrop(IDragDrop source, IListObject object, int mouseX, int mouseY) {
/* 150 */     if (isEnabled() && source != this && mouseX >= getXPosition() && mouseY >= getYPosition() && mouseX < getXPosition() + getWidth() && mouseY < getYPosition() + getHeight())
/*     */     {
/* 152 */       setItem(object);
/*     */     }
/*     */     
/* 155 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawControl(bsu minecraft, int mouseX, int mouseY) {
/* 161 */     if (!isVisible()) {
/*     */       return;
/*     */     }
/*     */     
/* 165 */     GL.glPushMatrix();
/* 166 */     GL.glTranslatef(0.0F, 0.0F, 100.0F);
/* 167 */     GL.glEnableDepthTest();
/* 168 */     a(getXPosition(), getYPosition(), getXPosition() + getWidth(), getYPosition() + getHeight(), this.backColour);
/*     */     
/* 170 */     GL.glTranslatef(0.0F, 0.0F, -50.0F);
/* 171 */     GL.glEnableDepthTest();
/* 172 */     a(getXPosition() - 1, getYPosition() - 1, getXPosition() + getWidth() + 1, getYPosition() + getHeight() + 1, -1);
/*     */     
/* 174 */     GL.glTranslatef(0.0F, 0.0F, 100.0F);
/* 175 */     if (this.item != null) {
/*     */       
/* 177 */       drawItem(this.item, this.mc, mouseX, mouseY, getXPosition(), getYPosition(), getWidth(), getHeight(), -1);
/*     */     }
/*     */     else {
/*     */       
/* 181 */       c(this.mc.k, this.j, getXPosition() + 4, getYPosition() + getHeight() / 2 - 4, -8355712);
/*     */     } 
/*     */     
/* 184 */     GL.glPopMatrix();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawItem(IListObject item, bsu minecraft, int mouseX, int mouseY, int itemX, int itemY, int itemWidth, int itemHeight, int itemTextColour) {
/* 202 */     bty fontrenderer = AbstractionLayer.getFontRenderer();
/*     */ 
/*     */     
/* 205 */     if (item.getCustomDrawBehaviour() == IListObject.CustomDrawBehaviour.FullCustomDraw) {
/*     */       
/* 207 */       item.drawCustom(true, minecraft, mouseX, mouseY, itemX, itemY, itemWidth, itemHeight, this.updateCounter);
/*     */     }
/*     */     else {
/*     */       
/* 211 */       if (!item.renderIcon(minecraft, itemX + this.iconOffset, itemY + this.iconOffset) && item.hasIcon() && item.getIcon() != null) {
/*     */         
/* 213 */         a(itemX + this.iconOffset, itemY + this.iconOffset, itemX + this.iconOffset + 16, itemY + this.iconOffset + 16, 1090519039);
/* 214 */         drawIcon(item.getIconTexture(), item.getIcon(), itemX + this.iconOffset, itemY + this.iconOffset, itemX + this.iconOffset + 16, itemY + this.iconOffset + 16);
/*     */       } 
/*     */ 
/*     */       
/* 218 */       drawStringWithEllipsis(fontrenderer, "" + item.getDisplayName(), itemX + this.iconSpacing + 4, itemY + this.textOffset, getWidth() - 32 - this.iconSpacing, itemTextColour);
/*     */ 
/*     */       
/* 221 */       if (item.getCustomDrawBehaviour() == IListObject.CustomDrawBehaviour.CustomDrawExtra)
/*     */       {
/* 223 */         item.drawCustom(true, minecraft, mouseX, mouseY, itemX, itemY, itemWidth, itemHeight, this.updateCounter);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IListObject getItem() {
/* 230 */     return this.item;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(IListObject item) {
/* 235 */     IListObject oldItem = this.item;
/* 236 */     this.item = item;
/*     */     
/* 238 */     if (this.socketListener != null)
/*     */     {
/* 240 */       if (this.item == null && oldItem != null) {
/*     */         
/* 242 */         this.socketListener.onSocketCleared(this);
/*     */       }
/* 244 */       else if (this.item != null && this.item != oldItem) {
/*     */         
/* 246 */         this.socketListener.onSocketChanged(this, this.item);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean c(bsu minecraft, int mouseX, int mouseY) {
/* 254 */     if (super.c(minecraft, mouseX, mouseY)) {
/*     */       
/* 256 */       if (this.item != null) {
/*     */ 
/*     */         
/* 259 */         if (this.item.getCustomDrawBehaviour() != IListObject.CustomDrawBehaviour.NoCustomDraw) {
/*     */           
/* 261 */           this.mouseDownObject = this.item;
/* 262 */           this.actionPerformed = this.item.mousePressed(true, minecraft, mouseX, mouseY, getXPosition(), getYPosition(), getWidth(), getHeight());
/*     */         } 
/*     */         
/* 265 */         if (this.updateCounter - this.timer < 9) {
/*     */           
/* 267 */           this.actionPerformed = true;
/* 268 */           this.doubleClicked = true;
/*     */         } 
/*     */         
/* 271 */         this.timer = this.updateCounter;
/*     */       } 
/*     */       
/* 274 */       return true;
/*     */     } 
/*     */     
/* 277 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY) {
/* 283 */     if (this.mouseDownObject != null) {
/*     */       
/* 285 */       this.mouseDownObject.mouseReleased(mouseX, mouseY);
/* 286 */       this.mouseDownObject = null;
/*     */     } 
/*     */     
/* 289 */     if (this.socketListener != null && isMouseOver(null, mouseX, mouseY)) {
/*     */       
/* 291 */       this.socketListener.onSocketClicked(this, this.actionPerformed);
/* 292 */       this.actionPerformed = false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\controls\GuiListItemSocket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */