/*     */ package net.eq2online.macros.gui.ext;
/*     */ 
/*     */ import bsu;
/*     */ import bul;
/*     */ import bvx;
/*     */ import bxf;
/*     */ import com.mumfrey.liteloader.client.overlays.IGuiTextField;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.compatibility.PrivateFields;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.controls.GuiDropDownMenu;
/*     */ import net.eq2online.macros.gui.controls.GuiMiniToolbarButton;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiLayout;
/*     */ import net.eq2online.macros.gui.designable.LayoutManager;
/*     */ import net.eq2online.macros.gui.screens.GuiDesigner;
/*     */ import net.eq2online.macros.gui.screens.GuiMacroEdit;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiChatAdapter
/*     */   extends bxf
/*     */ {
/*     */   private Macros macros;
/*     */   private bvx connectedScreen;
/*     */   private DesignableGuiLayout inChatLayout;
/*  34 */   private Rectangle boundingBox = new Rectangle();
/*     */   
/*  36 */   private GuiDropDownMenu contextMenu = new GuiDropDownMenu(true);
/*     */   
/*  38 */   private Point contextMenuLocation = new Point();
/*     */   
/*  40 */   private DesignableGuiControl clickedControl = null;
/*     */   
/*     */   protected GuiMiniToolbarButton btnGui;
/*     */ 
/*     */   
/*     */   public GuiChatAdapter(Macros macros) {
/*  46 */     this.macros = macros;
/*     */     
/*  48 */     this.contextMenu.addItem("execute", "Execute Macro");
/*  49 */     this.contextMenu.addItem("edit", "Edit Macro");
/*  50 */     this.contextMenu.addItem("design", "§e" + LocalisationProvider.getLocalisedString("tooltip.guiedit"), 26, 16);
/*     */ 
/*     */     
/*  53 */     this.btnGui = new GuiMiniToolbarButton(this.j, 4, 104, 64);
/*  54 */     this.j = bsu.z();
/*  55 */     this.q = this.j.k;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick(bvx source) {
/*  60 */     if (this.l != source.l || this.m != source.m || this.connectedScreen != source) {
/*     */       
/*  62 */       this.l = source.l;
/*  63 */       this.m = source.m;
/*  64 */       this.boundingBox = new Rectangle(0, 0, this.l, this.m - 14);
/*     */       
/*  66 */       bul textField = (bul)PrivateFields.chatTextField.get(source);
/*  67 */       if (textField != null) {
/*     */         
/*  69 */         int internalWidth = ((IGuiTextField)textField).getInternalWidth();
/*  70 */         ((IGuiTextField)textField).setInternalWidth(internalWidth - 20);
/*     */       } 
/*     */     } 
/*     */     
/*  74 */     if (this.connectedScreen != source) {
/*     */       
/*  76 */       this.connectedScreen = source;
/*  77 */       this.inChatLayout = LayoutManager.getBoundLayout("inchat", false);
/*     */     } 
/*     */     
/*  80 */     this.connectedScreen.l = this.l - 20;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float partialTicks) {
/*  86 */     this.connectedScreen.l = this.l;
/*     */     
/*  88 */     if (this.inChatLayout != null && this.boundingBox != null)
/*     */     {
/*  90 */       this.inChatLayout.draw(this.boundingBox, mouseX, mouseY);
/*     */     }
/*     */     
/*  93 */     if (this.btnGui.drawControlAt(this.j, mouseX, mouseY, this.l - 20, this.m - 14, -16716288, -2147483648))
/*     */     {
/*  95 */       drawTooltip(LocalisationProvider.getLocalisedString("tooltip.guiedit"), mouseX, mouseY, -1118482, -1342177280);
/*     */     }
/*     */     
/*  98 */     this.contextMenu.drawControlAt(this.contextMenuLocation, mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/* 104 */     if (this.inChatLayout != null)
/*     */     {
/* 106 */       this.inChatLayout.onTick();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean handleKeyTyped(char keyChar, int keyCode) {
/* 112 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean handleMouseClicked(int mouseX, int mouseY, int button) {
/* 117 */     boolean handled = false;
/*     */     
/* 119 */     String menuItem = this.contextMenu.mousePressed(mouseX, mouseY);
/* 120 */     if (menuItem != null) {
/*     */       
/* 122 */       if (menuItem.equals("execute") && this.clickedControl != null) {
/*     */         
/* 124 */         if (this.clickedControl.getWidgetIsBindable())
/*     */         {
/* 126 */           playMacro();
/*     */         }
/* 128 */         return true;
/*     */       } 
/* 130 */       if (menuItem.equals("edit") && this.clickedControl != null) {
/*     */         
/* 132 */         if (this.clickedControl.getWidgetIsBindable())
/*     */         {
/* 134 */           AbstractionLayer.displayGuiScreen((bxf)new GuiMacroEdit(this.clickedControl.id, this.j.m));
/*     */         }
/* 136 */         return true;
/*     */       } 
/* 138 */       if (menuItem.equals("design")) {
/*     */         
/* 140 */         AbstractionLayer.displayGuiScreen((bxf)new GuiDesigner("inchat", this.j.m, true));
/* 141 */         return true;
/*     */       } 
/* 143 */       if (!menuItem.equals("properties") || this.clickedControl != null);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 148 */       handled = true;
/*     */     } 
/*     */     
/* 151 */     if (button == 0 && this.btnGui.c(this.j, mouseX, mouseY)) {
/*     */       
/* 153 */       AbstractionLayer.displayGuiScreen((bxf)new GuiDesigner("inchat", this.j.m, true));
/* 154 */       return true;
/*     */     } 
/*     */     
/* 157 */     if (!handled && this.inChatLayout != null && this.boundingBox != null) {
/*     */       
/* 159 */       this.clickedControl = this.inChatLayout.getControlAt(this.boundingBox, mouseX, mouseY, -1);
/*     */       
/* 161 */       if (this.clickedControl != null && !this.clickedControl.isVisible()) {
/* 162 */         this.clickedControl = null;
/*     */       }
/* 164 */       if (this.clickedControl != null)
/*     */       {
/* 166 */         if (button == 0) {
/*     */           
/* 168 */           handled = true;
/* 169 */           playMacro();
/* 170 */           return true;
/*     */         } 
/*     */       }
/*     */       
/* 174 */       if (!handled && button == 1) {
/*     */         
/* 176 */         this.contextMenu.showDropDown();
/* 177 */         Dimension contextMenuSize = this.contextMenu.getSize();
/* 178 */         this.contextMenuLocation = new Point(Math.min(mouseX, this.l - contextMenuSize.width), Math.min(mouseY - 8, this.m - contextMenuSize.height));
/*     */       } 
/*     */     } 
/*     */     
/* 182 */     return handled;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playMacro() {
/* 187 */     if (this.clickedControl.getCloseGuiOnClick()) AbstractionLayer.displayGuiScreen(null); 
/* 188 */     this.macros.playMacro(this.clickedControl.id, false, ScriptContext.MAIN, null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawTooltip(String tooltipText, int mouseX, int mouseY, int colour, int backgroundColour) {
/* 193 */     int textSize = this.q.a(tooltipText);
/* 194 */     mouseX = Math.min(this.l - textSize - 6, mouseX - 6);
/* 195 */     mouseY = Math.max(0, mouseY - 18);
/*     */     
/* 197 */     a(mouseX, mouseY, mouseX + textSize + 6, mouseY + 16, backgroundColour);
/* 198 */     c(this.q, tooltipText, mouseX + 3, mouseY + 4, colour);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\ext\GuiChatAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */