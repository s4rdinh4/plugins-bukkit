/*    */ package net.eq2online.macros.gui.controls.specialised;
/*    */ 
/*    */ import bsu;
/*    */ import cvo;
/*    */ import cvs;
/*    */ import java.util.List;
/*    */ import net.eq2online.macros.gui.controls.GuiListBox;
/*    */ import net.eq2online.macros.gui.list.ListObjectTexturePack;
/*    */ import net.eq2online.macros.interfaces.IListObject;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiListBoxResourcePack
/*    */   extends GuiListBox
/*    */ {
/*    */   public GuiListBoxResourcePack(bsu minecraft, int controlId, boolean showIcons) {
/* 29 */     super(minecraft, controlId, showIcons, false, false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void refresh() {
/* 37 */     cvo resourcePackRepository = this.mc.P();
/* 38 */     resourcePackRepository.a();
/*    */ 
/*    */     
/* 41 */     List<cvs> availableResourcePacks = resourcePackRepository.b();
/* 42 */     this.items.clear();
/*    */     
/* 44 */     this.items.add(new ListObjectTexturePack(0, "Default", null));
/*    */     
/* 46 */     String selectedPackName = (this.mc.t.k.size() > 0) ? this.mc.t.k.get(0) : "";
/*    */     
/* 48 */     int resourcePackId = 1;
/* 49 */     for (cvs tp : availableResourcePacks) {
/*    */       
/* 51 */       this.items.add(new ListObjectTexturePack(resourcePackId, tp.d(), tp));
/* 52 */       if (selectedPackName.equals(tp.d()))
/* 53 */         selectId(resourcePackId); 
/* 54 */       resourcePackId++;
/*    */     } 
/*    */     
/* 57 */     this.scrollBar.setMax(Math.max(0, this.items.size() - this.displayRowCount));
/* 58 */     updateScrollPosition();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IListObject removeSelectedItem() {
/* 69 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\controls\specialised\GuiListBoxResourcePack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */