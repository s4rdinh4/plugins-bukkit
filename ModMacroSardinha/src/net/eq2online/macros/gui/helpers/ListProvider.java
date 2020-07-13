/*     */ package net.eq2online.macros.gui.helpers;
/*     */ 
/*     */ import alq;
/*     */ import amj;
/*     */ import bsu;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.params.MacroParam;
/*     */ import net.eq2online.macros.gui.controls.GuiListBox;
/*     */ import net.eq2online.macros.gui.controls.GuiListBoxFilebound;
/*     */ import net.eq2online.macros.gui.controls.GuiListBoxIconic;
/*     */ import net.eq2online.macros.gui.controls.specialised.GuiListBoxFile;
/*     */ import net.eq2online.macros.gui.controls.specialised.GuiListBoxFriends;
/*     */ import net.eq2online.macros.gui.controls.specialised.GuiListBoxHomes;
/*     */ import net.eq2online.macros.gui.controls.specialised.GuiListBoxPlaces;
/*     */ import net.eq2online.macros.gui.controls.specialised.GuiListBoxPresetText;
/*     */ import net.eq2online.macros.gui.controls.specialised.GuiListBoxResourcePack;
/*     */ import net.eq2online.macros.gui.controls.specialised.GuiListBoxShaders;
/*     */ import net.eq2online.macros.gui.controls.specialised.GuiListBoxTowns;
/*     */ import net.eq2online.macros.gui.controls.specialised.GuiListBoxWarps;
/*     */ import net.eq2online.macros.interfaces.IListObject;
/*     */ import net.eq2online.macros.scripting.variable.ItemID;
/*     */ import net.eq2online.macros.struct.ItemStackInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ListProvider
/*     */ {
/*     */   private bsu mc;
/*  37 */   private List<ItemStackInfo> enumeratedItems = new ArrayList<ItemStackInfo>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   private Map<String, List<ItemStackInfo>> mappedEnumeratedItems = new HashMap<String, List<ItemStackInfo>>();
/*     */ 
/*     */ 
/*     */   
/*     */   private GuiListBox itemsListBox;
/*     */ 
/*     */ 
/*     */   
/*     */   private GuiListBoxFile fileListBox;
/*     */ 
/*     */ 
/*     */   
/*     */   private GuiListBoxResourcePack texturePackListBox;
/*     */ 
/*     */ 
/*     */   
/*     */   private GuiListBoxShaders shadersListBox;
/*     */ 
/*     */   
/*  61 */   private Map<String, GuiListBoxFilebound> listBoxes = new HashMap<String, GuiListBoxFilebound>();
/*     */ 
/*     */   
/*     */   public void init(bsu minecraft) {
/*  65 */     this.mc = minecraft;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  70 */       enumerateItems();
/*     */     }
/*  72 */     catch (Exception exception) {}
/*     */ 
/*     */     
/*  75 */     createListboxes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ItemStackInfo> getInfoForItem(ItemID itemId) {
/*  86 */     return this.mappedEnumeratedItems.get(itemId.identifier);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void enumerateItems() {
/*  92 */     this.enumeratedItems.clear();
/*     */     
/*  94 */     int itemNumber = 0;
/*  95 */     Iterator<alq> iter = alq.e.iterator();
/*     */     
/*  97 */     while (iter.hasNext()) {
/*     */       
/*  99 */       alq theItem = iter.next();
/* 100 */       itemNumber++;
/*     */ 
/*     */       
/* 103 */       if (theItem != null && theItem.a() != null) {
/*     */         
/*     */         try {
/*     */           
/* 107 */           alq currentItem = theItem;
/*     */ 
/*     */ 
/*     */           
/* 111 */           List<amj> subItems = new ArrayList<amj>();
/* 112 */           currentItem.a(currentItem, null, subItems);
/*     */           
/* 114 */           for (amj subItem : subItems) {
/*     */             
/*     */             try
/*     */             {
/*     */ 
/*     */               
/* 120 */               ItemStackInfo newItem = new ItemStackInfo(itemNumber, theItem, subItem.i(), subItem);
/* 121 */               this.enumeratedItems.add(newItem);
/*     */               
/* 123 */               String id = Macros.getItemName(subItem.b());
/* 124 */               List<ItemStackInfo> mappedItems = this.mappedEnumeratedItems.get(id);
/* 125 */               if (mappedItems == null)
/*     */               {
/* 127 */                 this.mappedEnumeratedItems.put(id, mappedItems = new ArrayList<ItemStackInfo>());
/*     */               }
/* 129 */               mappedItems.add(newItem);
/*     */             }
/* 131 */             catch (Exception ex)
/*     */             {
/* 133 */               ex.printStackTrace();
/*     */             }
/*     */           
/*     */           } 
/* 137 */         } catch (Exception exception) {}
/*     */       }
/*     */     } 
/*     */     
/* 141 */     updateItemNames();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateItemNames() {
/* 150 */     for (ItemStackInfo item : this.enumeratedItems) {
/*     */ 
/*     */       
/*     */       try {
/* 154 */         item.updateName();
/*     */       }
/* 156 */       catch (Exception exception) {}
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
/*     */   private void createListboxes() {
/* 168 */     this.itemsListBox = (GuiListBox)new GuiListBoxIconic(this.mc, 2);
/* 169 */     this.itemsListBox.addItem((IListObject)new ItemStackInfo(0, null, 0, null));
/* 170 */     for (int i = 0; i < this.enumeratedItems.size(); i++) {
/* 171 */       this.itemsListBox.addItem((IListObject)this.enumeratedItems.get(i));
/*     */     }
/*     */     
/* 174 */     this.fileListBox = new GuiListBoxFile(this.mc, 2, true, MacroModCore.getMacrosDirectory(), "txt");
/*     */     
/* 176 */     this.texturePackListBox = new GuiListBoxResourcePack(this.mc, 2, true);
/*     */     
/* 178 */     this.shadersListBox = new GuiListBoxShaders(this.mc, 2, true);
/*     */ 
/*     */     
/* 181 */     this.listBoxes.put(MacroParam.Type.Friend.toString(), new GuiListBoxFriends(this.mc, 2));
/* 182 */     ((GuiListBoxFilebound)this.listBoxes.get(MacroParam.Type.Friend.toString())).load();
/* 183 */     ((GuiListBoxFilebound)this.listBoxes.get(MacroParam.Type.Friend.toString())).save();
/*     */ 
/*     */     
/* 186 */     this.listBoxes.put(MacroParam.Type.Town.toString(), new GuiListBoxTowns(this.mc, 2));
/* 187 */     ((GuiListBoxFilebound)this.listBoxes.get(MacroParam.Type.Town.toString())).load();
/*     */ 
/*     */     
/* 190 */     this.listBoxes.put(MacroParam.Type.Warp.toString(), new GuiListBoxWarps(this.mc, 2));
/* 191 */     ((GuiListBoxFilebound)this.listBoxes.get(MacroParam.Type.Warp.toString())).load();
/*     */ 
/*     */     
/* 194 */     this.listBoxes.put(MacroParam.Type.Home.toString(), new GuiListBoxHomes(this.mc, 2));
/* 195 */     ((GuiListBoxFilebound)this.listBoxes.get(MacroParam.Type.Home.toString())).load();
/*     */     
/* 197 */     this.listBoxes.put(MacroParam.Type.Place.toString(), new GuiListBoxPlaces(this.mc, 2));
/* 198 */     ((GuiListBoxFilebound)this.listBoxes.get(MacroParam.Type.Place.toString())).load();
/*     */ 
/*     */     
/* 201 */     for (int l = 0; l < 10; l++) {
/*     */       
/* 203 */       this.listBoxes.put(MacroParam.Type.Preset.toString() + l, new GuiListBoxPresetText(this.mc, 2, ".presettext" + l + ".txt"));
/* 204 */       ((GuiListBoxFilebound)this.listBoxes.get(MacroParam.Type.Preset.toString() + l)).load();
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
/*     */   public GuiListBox getListBox(String listBoxName) {
/* 217 */     if (listBoxName == MacroParam.Type.Item.toString()) {
/* 218 */       return this.itemsListBox;
/*     */     }
/*     */     
/* 221 */     if (listBoxName == MacroParam.Type.File.toString()) {
/* 222 */       return (GuiListBox)this.fileListBox;
/*     */     }
/*     */     
/* 225 */     if (listBoxName == MacroParam.Type.TexturePack.toString()) {
/* 226 */       return (GuiListBox)this.texturePackListBox;
/*     */     }
/*     */     
/* 229 */     if (listBoxName == MacroParam.Type.ShaderGroup.toString()) {
/* 230 */       return (GuiListBox)this.shadersListBox;
/*     */     }
/*     */     
/* 233 */     if (listBoxName == MacroParam.Type.OnlineUser.toString()) {
/*     */       
/* 235 */       GuiListBox userListBox = new GuiListBox(this.mc, 2, 0, 0, 200, 200, GuiListBox.defaultRowHeight, true, false, false);
/* 236 */       MacroModCore.getInstance().getAutoDiscoveryAgent().populateUserListBox(userListBox, false);
/* 237 */       return userListBox;
/*     */     } 
/*     */ 
/*     */     
/* 241 */     return (GuiListBox)this.listBoxes.get(listBoxName);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\helpers\ListProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */