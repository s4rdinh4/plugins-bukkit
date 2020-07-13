/*     */ package net.eq2online.macros.gui.designable.editor;
/*     */ 
/*     */ import bsu;
/*     */ import bug;
/*     */ import bxf;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.GuiControl;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.controls.GuiCheckBox;
/*     */ import net.eq2online.macros.gui.controls.GuiListBox;
/*     */ import net.eq2online.macros.gui.controls.GuiListItemSocket;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiLayout;
/*     */ import net.eq2online.macros.gui.designable.LayoutManager;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxConfirm;
/*     */ import net.eq2online.macros.gui.dialogs.GuiDialogBoxCreateItem;
/*     */ import net.eq2online.macros.gui.list.ListObjectGuiLayout;
/*     */ import net.eq2online.macros.gui.screens.GuiDesigner;
/*     */ import net.eq2online.macros.gui.screens.GuiScreenWithHeader;
/*     */ import net.eq2online.macros.gui.shared.GuiDialogBox;
/*     */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*     */ import net.eq2online.macros.interfaces.IDragDrop;
/*     */ import net.eq2online.macros.interfaces.IListObject;
/*     */ import net.eq2online.macros.interfaces.ISocketListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiLayoutPatch
/*     */   extends GuiScreenWithHeader
/*     */   implements ISocketListener
/*     */ {
/*     */   private static final int SOCKET_SPACING = 24;
/*     */   private static final int SOCKET_TOP = 92;
/*     */   protected Macros macros;
/*     */   protected bxf parentScreen;
/*     */   protected GuiListBox availableGuiList;
/*  46 */   protected Map<GuiListItemSocket, String> sockets = new HashMap<GuiListItemSocket, String>();
/*     */   
/*  48 */   protected List<String> socketNames = new ArrayList<String>();
/*     */   
/*     */   protected GuiControl btnOk;
/*     */   
/*     */   protected GuiCheckBox chkReset;
/*     */   
/*     */   protected ListObjectGuiLayout mouseOverObject;
/*     */   
/*     */   protected long mouseOverObjectTime;
/*     */ 
/*     */   
/*     */   public GuiLayoutPatch(bxf parentScreen) {
/*  60 */     super(0, 0);
/*     */     
/*  62 */     this.parentScreen = parentScreen;
/*  63 */     this.screenDrawMenuButton = true;
/*  64 */     this.screenDrawBackground = false;
/*  65 */     this.screenCentreBanner = false;
/*  66 */     this.screenBannerColour = 4259648;
/*  67 */     this.screenTitle = LocalisationProvider.getLocalisedString("layout.editor.title");
/*  68 */     this.macros = MacroModCore.getMacroManager();
/*     */     
/*  70 */     this.screenMenu.addItem("create", LocalisationProvider.getLocalisedString("layout.editor.menu.create"))
/*  71 */       .addItem("reset", "§8" + LocalisationProvider.getLocalisedString("layout.editor.menu.reset"))
/*  72 */       .addSeparator()
/*  73 */       .addItem("back", LocalisationProvider.getLocalisedString("gui.exit"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/*  79 */     clearControlList();
/*     */     
/*  81 */     int selectedIndex = -1;
/*  82 */     if (this.availableGuiList != null)
/*     */     {
/*  84 */       selectedIndex = this.availableGuiList.getSelectedItemIndex();
/*     */     }
/*     */     
/*  87 */     addControl((GuiControl)(this.availableGuiList = new GuiListBox(bsu.z(), 0, 4, 40, 194, this.m - 70, 20, true, true, false)));
/*  88 */     addControl(this.btnOk = new GuiControl(1, this.l - 64, this.m - 24, 60, 20, LocalisationProvider.getLocalisedString("gui.ok")));
/*  89 */     addControl((GuiControl)(this.chkReset = new GuiCheckBox(2, 6, this.m - 24, LocalisationProvider.getLocalisedString("layout.options.loadatstartup"), MacroModSettings.loadLayoutBindings)));
/*     */     
/*  91 */     int layoutId = 0;
/*     */     
/*  93 */     Map<String, ListObjectGuiLayout> layoutListObjects = new HashMap<String, ListObjectGuiLayout>();
/*     */     
/*  95 */     for (String layoutName : LayoutManager.getLayoutNames()) {
/*     */       
/*  97 */       ListObjectGuiLayout layoutListObject = getLayoutListObject(layoutId++, layoutName);
/*  98 */       layoutListObjects.put(layoutName, layoutListObject);
/*  99 */       this.availableGuiList.addItem((IListObject)layoutListObject);
/*     */     } 
/*     */     
/* 102 */     this.sockets.clear();
/* 103 */     this.socketNames.clear();
/*     */     
/* 105 */     int socketId = 3;
/* 106 */     int socketPosition = 68;
/*     */     
/* 108 */     for (String socketName : LayoutManager.getSlotNames()) {
/*     */       
/* 110 */       socketPosition += 24; GuiListItemSocket newSocket = new GuiListItemSocket(this.j, socketId++, 275, socketPosition, Math.min(200, this.l - 281), 20, LocalisationProvider.getLocalisedString("layout.editor.nobinding"), this);
/* 111 */       this.sockets.put(newSocket, socketName);
/* 112 */       this.socketNames.add(socketName);
/* 113 */       addControl((GuiControl)newSocket);
/* 114 */       this.availableGuiList.addDragTarget((IDragDrop)newSocket, true);
/*     */       
/* 116 */       newSocket.setItem((IListObject)layoutListObjects.get(LayoutManager.getBoundLayoutName(socketName)));
/*     */     } 
/*     */     
/* 119 */     if (selectedIndex > -1)
/*     */     {
/* 121 */       this.availableGuiList.setSelectedItemIndex(selectedIndex);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ListObjectGuiLayout getLayoutListObject(int id, String layoutName) {
/* 132 */     ListObjectGuiLayout listObject = new ListObjectGuiLayout(id, LayoutManager.getLayout(layoutName));
/* 133 */     listObject.setDraggable(Boolean.valueOf(true));
/* 134 */     return listObject;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float partialTick) {
/* 140 */     if (AbstractionLayer.getWorld() == null) {
/* 141 */       c();
/*     */     }
/* 143 */     a(2, 22, 200, 38, -1607257293);
/* 144 */     a(2, 38, 200, this.m - 28, -1342177280);
/* 145 */     a(202, 22, this.l - 2, 38, -1607257293);
/* 146 */     a(202, 38, this.l - 2, this.m - 28, -1342177280);
/* 147 */     a(2, this.m - 26, this.l - 2, this.m - 2, -1342177280);
/*     */     
/* 149 */     c(this.q, LocalisationProvider.getLocalisedString("layout.editor.available"), 8, 26, -256);
/* 150 */     c(this.q, LocalisationProvider.getLocalisedString("layout.editor.assignments"), 210, 26, -256);
/*     */     
/* 152 */     c(this.q, LocalisationProvider.getLocalisedString("layout.editor.help.line1"), 210, 44, 16755200);
/* 153 */     c(this.q, LocalisationProvider.getLocalisedString("layout.editor.help.line2"), 210, 54, 16755200);
/* 154 */     c(this.q, LocalisationProvider.getLocalisedString("layout.editor.help.line3"), 210, 64, 16755200);
/*     */     
/* 156 */     c(this.q, LocalisationProvider.getLocalisedString("layout.editor.slot"), 210, 79, -256);
/* 157 */     c(this.q, LocalisationProvider.getLocalisedString("layout.editor.screen"), 279, 79, -256);
/*     */     
/* 159 */     int yPos = 74;
/* 160 */     for (String socketName : this.socketNames) {
/*     */       
/* 162 */       yPos += 24; c(this.q, socketName, 210, yPos, -171);
/*     */     } 
/*     */     
/* 165 */     this.screenBanner = LocalisationProvider.getLocalisedString("macro.currentconfig", new Object[] { this.macros.getActiveConfigName() });
/*     */     
/* 167 */     super.a(mouseX, mouseY, partialTick);
/*     */     
/* 169 */     if (this.availableGuiList.isMouseOver(this.j, mouseX, mouseY)) {
/*     */       
/* 171 */       IListObject mouseOverObject = this.availableGuiList.getItemAtPosition(mouseX, mouseY);
/*     */       
/* 173 */       if (mouseOverObject != null)
/*     */       {
/* 175 */         ListObjectGuiLayout layoutEntry = (ListObjectGuiLayout)mouseOverObject;
/* 176 */         if (this.mouseOverObject != layoutEntry) {
/*     */           
/* 178 */           this.mouseOverObject = layoutEntry;
/* 179 */           this.mouseOverObjectTime = this.updateCounter;
/*     */         } 
/*     */         
/* 182 */         if (this.mouseOverObject != null && this.updateCounter - this.mouseOverObjectTime > 6L && !this.mouseOverObject.getLayoutName().equals(this.mouseOverObject.getLayoutDisplayName()))
/*     */         {
/* 184 */           drawTooltip(this.mouseOverObject.getLayoutName(), mouseX, mouseY, -171, -1342177280);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 189 */         this.mouseOverObject = null;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 194 */       this.mouseOverObject = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(bug button) {
/* 201 */     if (button.k == this.availableGuiList.k) {
/*     */       
/* 203 */       IListObject selectedItem = this.availableGuiList.getSelectedItem();
/*     */       
/* 205 */       if (selectedItem != null) {
/*     */         
/* 207 */         String customAction = selectedItem.getCustomAction(true);
/*     */         
/* 209 */         ListObjectGuiLayout selectedLayout = (ListObjectGuiLayout)selectedItem;
/* 210 */         if (customAction.equals("delete"))
/*     */         {
/* 212 */           deleteLayout(selectedLayout);
/*     */         }
/* 214 */         else if (customAction.equals("edit") || this.availableGuiList.isDoubleClicked(true))
/*     */         {
/* 216 */           AbstractionLayer.displayGuiScreen((bxf)new GuiDesigner(selectedLayout.getLayout(), (bxf)this, true));
/*     */         }
/*     */       
/*     */       } 
/* 220 */     } else if (button.k == this.btnOk.k) {
/*     */       
/* 222 */       onCloseClick();
/*     */     }
/* 224 */     else if (button.k == this.chkReset.k) {
/*     */       
/* 226 */       MacroModSettings.loadLayoutBindings = this.chkReset.checked;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(char keyChar, int keyCode) {
/* 233 */     if (keyCode == 1)
/*     */     {
/* 235 */       onCloseClick();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMenuItemClicked(String menuItem) {
/* 242 */     if (menuItem.equals("back")) {
/* 243 */       onCloseClick();
/*     */     }
/* 245 */     if (menuItem.equals("create")) {
/* 246 */       displayDialog((GuiDialogBox)new GuiDialogBoxCreateItem((GuiScreenEx)this, "Create New Screen", "Enter the name for the new screen"));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSocketChanged(GuiListItemSocket socket, IListObject object) {
/* 252 */     String layoutName = (object != null) ? ((ListObjectGuiLayout)object).getLayoutName() : null;
/* 253 */     LayoutManager.setBinding(this.sockets.get(socket), layoutName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSocketCleared(GuiListItemSocket socket) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSocketClicked(GuiListItemSocket socket, boolean doubleClicked) {
/* 264 */     if (socket.getItem() != null) {
/*     */       
/* 266 */       String customAction = socket.getItem().getCustomAction(true);
/* 267 */       ListObjectGuiLayout selectedLayout = (ListObjectGuiLayout)socket.getItem();
/* 268 */       if (customAction.equals("delete")) {
/*     */         
/* 270 */         deleteLayout(selectedLayout);
/*     */       }
/* 272 */       else if (customAction.equals("edit") || doubleClicked) {
/*     */         
/* 274 */         AbstractionLayer.displayGuiScreen((bxf)new GuiDesigner(selectedLayout.getLayout(), (bxf)this, !((String)this.sockets.get(socket)).equals("ingame")));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void deleteLayout(ListObjectGuiLayout layout) {
/* 284 */     if (!LayoutManager.isBuiltinLayout(layout.getLayoutName()))
/*     */     {
/* 286 */       displayDialog((GuiDialogBox)new GuiDialogBoxConfirm((GuiScreenEx)this, LocalisationProvider.getLocalisedString("layout.delete.title"), LocalisationProvider.getLocalisedString("layout.delete.line1"), layout.getLayoutName() + " ?", layout.getLayout()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCloseClick() {
/* 293 */     LayoutManager.saveSettings();
/* 294 */     AbstractionLayer.displayGuiScreen(this.parentScreen);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDialogClosed(GuiDialogBox dialog) {
/* 301 */     super.onDialogClosed(dialog);
/*     */     
/* 303 */     if (dialog instanceof GuiDialogBoxConfirm && dialog.dialogResult == GuiDialogBox.DialogResult.OK) {
/*     */       
/* 305 */       GuiDialogBoxConfirm<DesignableGuiLayout> confirmDeleteDialog = (GuiDialogBoxConfirm<DesignableGuiLayout>)dialog;
/* 306 */       LayoutManager.deleteLayout(((DesignableGuiLayout)confirmDeleteDialog.getMetaData()).name);
/* 307 */       b();
/*     */     }
/* 309 */     else if (dialog instanceof GuiDialogBoxCreateItem && dialog.dialogResult == GuiDialogBox.DialogResult.OK) {
/*     */       
/* 311 */       GuiDialogBoxCreateItem createDialog = (GuiDialogBoxCreateItem)dialog;
/* 312 */       if (LayoutManager.layoutExists(createDialog.getNewItemName())) {
/*     */         
/* 314 */         displayDialog((GuiDialogBox)createDialog);
/* 315 */         createDialog.onDialogSubmissionFailed("Screen '" + createDialog.getNewItemName() + "' already exists");
/*     */       }
/*     */       else {
/*     */         
/* 319 */         DesignableGuiLayout newLayout = LayoutManager.getLayout(createDialog.getNewItemName());
/* 320 */         newLayout.displayName = createDialog.getNewItemDisplayName();
/* 321 */         b();
/* 322 */         AbstractionLayer.displayGuiScreen((bxf)new GuiDesigner(newLayout, (bxf)this, true));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\designable\editor\GuiLayoutPatch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */