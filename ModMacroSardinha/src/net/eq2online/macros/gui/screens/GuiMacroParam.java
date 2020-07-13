/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import ahg;
/*     */ import bug;
/*     */ import bwa;
/*     */ import bxf;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.GuiControl;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.params.MacroParam;
/*     */ import net.eq2online.macros.gui.controls.GuiListBox;
/*     */ import net.eq2online.macros.gui.controls.specialised.GuiListBoxFile;
/*     */ import net.eq2online.macros.gui.shared.GuiControlEx;
/*     */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*     */ import net.eq2online.macros.interfaces.IDragDrop;
/*     */ import net.eq2online.macros.interfaces.IListObject;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import org.lwjgl.input.Keyboard;
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
/*     */ public class GuiMacroParam
/*     */   extends GuiScreenEx
/*     */ {
/*     */   protected IMacroParamTarget target;
/*     */   protected MacroParam param;
/*  50 */   protected MacroParam.Type paramType = null;
/*     */ 
/*     */ 
/*     */   
/*     */   protected GuiListBox itemListBox;
/*     */ 
/*     */ 
/*     */   
/*     */   protected GuiListBox sourceListBox;
/*     */ 
/*     */ 
/*     */   
/*     */   protected GuiControl btnImportAll;
/*     */ 
/*     */ 
/*     */   
/*     */   protected GuiControl btnRefreshList;
/*     */ 
/*     */ 
/*     */   
/*     */   protected bwa yesNoScreen;
/*     */ 
/*     */   
/*     */   protected Macros macros;
/*     */ 
/*     */   
/*     */   private float lastUpdatePartialTick;
/*     */ 
/*     */   
/*  79 */   protected float screenTween = 0.0F;
/*     */   
/*  81 */   protected ahg oldChatVisibility = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiMacroParam(IMacroParamTarget target) {
/*  90 */     this.target = target;
/*  91 */     this.macros = MacroModCore.getMacroManager();
/*     */     
/*  93 */     this.btnImportAll = new GuiControl(0, this.l - 182, this.m - 58, 90, 20, LocalisationProvider.getLocalisedString("gui.importall"));
/*  94 */     this.btnRefreshList = new GuiControl(1, this.l - 62, this.m - 58, 60, 20, LocalisationProvider.getLocalisedString("gui.refresh"));
/*     */ 
/*     */     
/*  97 */     this.param = target.getNextParam();
/*  98 */     this.paramType = this.param.getType();
/*  99 */     this.param.setParent(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reInit() {
/* 108 */     if (this.param.isType(this.paramType) && this.paramType != MacroParam.Type.NamedParam) {
/*     */       
/* 110 */       this.screenTween = 0.5F;
/* 111 */       this.lastUpdatePartialTick = -1.0F;
/*     */     } 
/*     */     
/* 114 */     this.paramType = this.param.getType();
/*     */ 
/*     */     
/* 117 */     if (this.sourceListBox != null) {
/*     */       
/* 119 */       if (this.itemListBox != null) this.itemListBox.removeDragTarget((IDragDrop)this.sourceListBox); 
/* 120 */       this.sourceListBox.clear();
/* 121 */       this.sourceListBox = null;
/*     */     } 
/*     */ 
/*     */     
/* 125 */     this.itemListBox = null;
/* 126 */     b();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/* 135 */     Keyboard.enableRepeatEvents(true);
/*     */     
/* 137 */     if (MacroModSettings.showChatInParamScreen && this.oldChatVisibility == null) {
/*     */       
/* 139 */       this.oldChatVisibility = this.j.t.l;
/* 140 */       this.j.t.l = ahg.a;
/*     */     } 
/*     */     
/* 143 */     clearControlList();
/*     */     
/* 145 */     this.itemListBox = this.param.getListBox(this.l, this.m);
/*     */     
/* 147 */     if (this.itemListBox != null) {
/*     */       
/* 149 */       addControl((GuiControl)this.itemListBox);
/*     */       
/* 151 */       if (this.sourceListBox != null) {
/*     */         
/* 153 */         if (this.itemListBox.isValidDragSource())
/*     */         {
/* 155 */           this.itemListBox.addDragTarget((IDragDrop)this.sourceListBox, true);
/*     */         }
/*     */         
/* 158 */         this.sourceListBox.setSizeAndPosition(this.l - 182, 20, 180, this.m - 80, GuiListBox.defaultRowHeight, true);
/* 159 */         addControl((GuiControl)this.sourceListBox);
/*     */       } 
/*     */     } 
/*     */     
/* 163 */     this.param.initControls(this.l, this.m);
/*     */     
/* 165 */     this.btnRefreshList.setYPosition(this.m - 58);
/* 166 */     this.btnImportAll.setYPosition(this.m - 58);
/* 167 */     this.btnImportAll.setXPosition(this.l - 182);
/* 168 */     this.btnRefreshList.setXPosition(this.l - 62);
/*     */     
/* 170 */     addControl(this.btnImportAll);
/* 171 */     addControl(this.btnRefreshList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void m() {
/* 180 */     Keyboard.enableRepeatEvents(false);
/*     */     
/* 182 */     if (this.oldChatVisibility != null) {
/*     */       
/* 184 */       this.j.t.l = this.oldChatVisibility;
/* 185 */       this.oldChatVisibility = null;
/*     */     } 
/*     */     
/* 188 */     if (this.itemListBox != null && this.sourceListBox != null && this.itemListBox.isValidDragSource())
/*     */     {
/* 190 */       this.itemListBox.removeDragTarget((IDragDrop)this.sourceListBox, true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(bug guibutton) throws IOException {
/* 200 */     boolean shiftDown = (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
/*     */     
/* 202 */     if (guibutton.k == 555)
/*     */     {
/* 204 */       this.param.sortList();
/*     */     }
/*     */ 
/*     */     
/* 208 */     if (guibutton.k == 0 && this.sourceListBox != null)
/*     */     {
/* 210 */       this.param.importAllFrom(this.sourceListBox);
/*     */     }
/*     */ 
/*     */     
/* 214 */     if (guibutton.k == 1 && this.sourceListBox != null)
/*     */     {
/* 216 */       autoPopulate();
/*     */     }
/*     */ 
/*     */     
/* 220 */     if (guibutton.k == 12 && this.sourceListBox != null)
/*     */     {
/* 222 */       if (shiftDown) {
/*     */         
/* 224 */         IListObject selectedItem = this.sourceListBox.getSelectedItem();
/*     */         
/* 226 */         if (selectedItem != null) {
/*     */           
/* 228 */           this.sourceListBox.removeSelectedItem();
/* 229 */           this.itemListBox.addItemAt(selectedItem, this.itemListBox.getItemCount());
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 235 */     if (guibutton.k == 2 && this.itemListBox != null)
/*     */     {
/* 237 */       this.param.handleListBoxClick(this);
/*     */     }
/*     */     
/* 240 */     super.a(guibutton);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void autoPopulate() {
/* 246 */     this.param.setParent(this);
/* 247 */     this.param.autoPopulate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean createFileAndEdit(String text) {
/* 258 */     if (!text.toLowerCase().endsWith(".txt")) {
/*     */       
/* 260 */       if (text.lastIndexOf('.') > 0) {
/* 261 */         text = text.substring(0, text.lastIndexOf('.'));
/*     */       }
/* 263 */       text = text + ".txt";
/*     */     } 
/*     */ 
/*     */     
/* 267 */     if (text.startsWith(".")) {
/* 268 */       text = text.substring(1);
/*     */     }
/*     */     
/* 271 */     if (text.length() > 4) {
/*     */       
/* 273 */       GuiEditText editor = new GuiEditTextFile(this, new File(MacroModCore.getMacrosDirectory(), text), ScriptContext.MAIN);
/* 274 */       AbstractionLayer.displayGuiScreen((bxf)editor);
/* 275 */       return true;
/*     */     } 
/*     */     
/* 278 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFinishEditingTextFile(GuiEditText editor, File file) {
/* 288 */     if (file != null && file.exists() && file.getName().toLowerCase().equals("macros.txt"))
/*     */     {
/* 290 */       this.macros.load();
/*     */     }
/*     */     
/* 293 */     if (this.itemListBox instanceof GuiListBoxFile) {
/*     */       
/* 295 */       ((GuiListBoxFile)this.itemListBox).refresh();
/* 296 */       if (file != null) this.itemListBox.selectData(file.getName());
/*     */     
/*     */     } 
/* 299 */     AbstractionLayer.displayGuiScreen((bxf)this);
/*     */     
/* 301 */     IListObject selectedItem = this.itemListBox.getSelectedItem();
/* 302 */     if (selectedItem != null && selectedItem.getId() > -1)
/*     */     {
/* 304 */       this.param.setParameterValue(selectedItem.getData().toString());
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
/*     */   public void onAutoPopulateComplete(MacroParam macroParam, ArrayList<String> items) {
/* 316 */     if (macroParam.isType(this.param.getType()) && (macroParam.isType(MacroParam.Type.Friend) || items.size() > 0)) {
/*     */       
/* 318 */       if (this.sourceListBox == null) {
/*     */         
/* 320 */         this.sourceListBox = new GuiListBox(this.j, 12, this.l - 182, 20, 180, this.m - 80, GuiListBox.defaultRowHeight, true, true, true);
/* 321 */         this.itemListBox.addDragTarget((IDragDrop)this.sourceListBox, true);
/* 322 */         addControl((GuiControl)this.sourceListBox);
/*     */       }
/*     */       else {
/*     */         
/* 326 */         this.sourceListBox.clear();
/*     */       } 
/*     */       
/* 329 */       if (macroParam.isType(MacroParam.Type.Friend))
/*     */       {
/* 331 */         this.macros.getAutoDiscoveryAgent().populateUserListBox(this.sourceListBox, true);
/*     */       }
/*     */       else
/*     */       {
/* 335 */         for (int i = 0; i < items.size(); i++)
/*     */         {
/* 337 */           if (!this.itemListBox.containsItem(items.get(i)))
/*     */           {
/* 339 */             int iconId = (int)(Math.random() * 255.0D);
/* 340 */             IListObject newItem = this.itemListBox.createObject(items.get(i), iconId);
/* 341 */             this.sourceListBox.addItem(newItem);
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 348 */     } else if (this.sourceListBox != null) {
/*     */       
/* 350 */       this.itemListBox.removeDragTarget((IDragDrop)this.sourceListBox);
/* 351 */       getControlList().remove(this.sourceListBox);
/* 352 */       this.sourceListBox = null;
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
/*     */   public void a(boolean response, int worldIndex) {
/* 366 */     this.param.deleteSelectedItem(response);
/*     */     
/* 368 */     AbstractionLayer.displayGuiScreen((bxf)this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(int mouseX, int mouseY, int button) throws IOException {
/* 377 */     if (this.param.mouseClicked(mouseX, mouseY, button))
/*     */     {
/* 379 */       super.a(mouseX, mouseY, button);
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
/*     */   protected void a(char keyChar, int keyCode) throws IOException {
/* 392 */     if (this.updateCounter < 1 || this.param == null)
/*     */       return; 
/* 394 */     GuiControlEx.KeyHandledState handled = this.param.keyTyped(keyChar, keyCode);
/*     */     
/* 396 */     if (handled == GuiControlEx.KeyHandledState.ActionPerformed) {
/*     */       
/* 398 */       a((bug)this.itemListBox);
/*     */       
/*     */       return;
/*     */     } 
/* 402 */     if (handled == GuiControlEx.KeyHandledState.Handled) {
/*     */       return;
/*     */     }
/* 405 */     if (keyCode == 1) {
/*     */       
/* 407 */       this.target.handleCancelled();
/* 408 */       AbstractionLayer.displayGuiScreen(null);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 413 */     if (keyCode == 28 || keyCode == 156) {
/*     */       
/* 415 */       apply();
/*     */       
/*     */       return;
/*     */     } 
/* 419 */     if (keyChar == '\023' && keyCode == 31) {
/*     */       
/* 421 */       this.param.sortList();
/*     */       
/*     */       return;
/*     */     } 
/* 425 */     this.param.textFieldKeyTyped(keyChar, keyCode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply() {
/* 433 */     if (!this.param.apply()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 439 */     this.param = this.target.getNextParam();
/*     */ 
/*     */     
/* 442 */     if (this.param != null) {
/*     */       
/* 444 */       reInit();
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 449 */       AbstractionLayer.displayGuiScreen(null);
/* 450 */       this.target.handleCompleted();
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
/*     */   protected void mouseWheelScrolled(int mouseWheelDelta) throws IOException {
/* 463 */     if (this.itemListBox != null) {
/*     */       
/* 465 */       mouseWheelDelta /= 120;
/*     */       
/* 467 */       while (mouseWheelDelta > 0) {
/*     */         
/* 469 */         this.itemListBox.up();
/* 470 */         a((bug)this.itemListBox);
/* 471 */         mouseWheelDelta--;
/*     */       } 
/*     */       
/* 474 */       while (mouseWheelDelta < 0) {
/*     */         
/* 476 */         this.itemListBox.down();
/* 477 */         a((bug)this.itemListBox);
/* 478 */         mouseWheelDelta++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/* 489 */     super.e();
/* 490 */     this.param.updateScreen();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float f) {
/* 499 */     drawScreenWithEnabledState(mouseX, mouseY, f, true);
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
/*     */   public void drawScreenWithEnabledState(int mouseX, int mouseY, float partialTick, boolean enabled) {
/* 513 */     if (MacroModSettings.showChatInParamScreen && this.oldChatVisibility != null) {
/*     */       
/* 515 */       this.j.t.l = ahg.a;
/* 516 */       GL.glDisableBlend();
/* 517 */       GL.glBlendFunc(770, 771);
/* 518 */       GL.glDisableAlphaTest();
/* 519 */       GL.glPushMatrix();
/* 520 */       GL.glTranslatef(0.0F, (this.m - 48), 0.0F);
/* 521 */       this.j.q.d().a(0);
/* 522 */       GL.glPopMatrix();
/* 523 */       GL.glEnableAlphaTest();
/* 524 */       this.j.t.l = ahg.c;
/*     */     } 
/*     */     
/* 527 */     if (this.lastUpdatePartialTick == -1.0F)
/*     */     {
/* 529 */       this.lastUpdatePartialTick = this.updateCounter + partialTick;
/*     */     }
/*     */     
/* 532 */     float deltaTime = this.updateCounter + partialTick - this.lastUpdatePartialTick;
/* 533 */     this.lastUpdatePartialTick = this.updateCounter + partialTick;
/*     */     
/* 535 */     if (this.screenTween > 0.0F)
/*     */     {
/* 537 */       this.screenTween -= deltaTime * 0.05F;
/*     */     }
/*     */     
/* 540 */     if (this.screenTween < 0.0F) {
/* 541 */       this.screenTween = 0.0F;
/*     */     }
/* 543 */     if (this.itemListBox != null) this.itemListBox.setEnabled(enabled); 
/* 544 */     if (this.sourceListBox != null) this.sourceListBox.setVisible(enabled);
/*     */     
/* 546 */     this.btnImportAll.setVisible((this.sourceListBox != null && enabled));
/* 547 */     this.btnRefreshList.setVisible((this.sourceListBox != null && enabled));
/*     */     
/* 549 */     GL.glPushMatrix();
/* 550 */     GL.glTranslatef(-180.0F * (1.0F - (float)Math.sin(Math.PI * (0.5F - this.screenTween))), 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */     
/* 554 */     super.a(mouseX, mouseY, partialTick);
/*     */ 
/*     */ 
/*     */     
/* 558 */     GL.glPopMatrix();
/*     */     
/* 560 */     this.param.drawControls(this.j, mouseX, mouseY, partialTick, enabled, this.q, this.l, this.m, this.updateCounter);
/*     */     
/* 562 */     if (enabled && this.sourceListBox != null) {
/*     */       
/* 564 */       a(this.l - 182, 2, this.l - 2, 18, -2146562560);
/* 565 */       c(this.q, LocalisationProvider.getLocalisedString("query.results"), this.l - 178, 6, 16777215);
/*     */     } 
/*     */     
/* 568 */     if (enabled && this.itemListBox != null && this.itemListBox.isSortable() && (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)))
/*     */     {
/* 570 */       drawTooltip(LocalisationProvider.getLocalisedString("macro.prompt.list.sort"), 10, 22, -16711936, -1342177280);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\screens\GuiMacroParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */