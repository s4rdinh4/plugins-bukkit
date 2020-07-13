/*    */ package net.eq2online.macros.gui.list;
/*    */ 
/*    */ import com.mumfrey.liteloader.util.render.Icon;
/*    */ import net.eq2online.macros.compatibility.IconTiled;
/*    */ import net.eq2online.macros.gui.designable.DesignableGuiLayout;
/*    */ import net.eq2online.macros.gui.designable.LayoutManager;
/*    */ import net.eq2online.macros.res.ResourceLocations;
/*    */ 
/*    */ public class ListObjectGuiLayout
/*    */   extends ListObjectEditable {
/*    */   private DesignableGuiLayout layout;
/*    */   
/*    */   public ListObjectGuiLayout(int id, DesignableGuiLayout layout) {
/* 14 */     super(id, (layout.name.equals(layout.displayName) ? "" : "§e") + layout.displayName, layout, true, !LayoutManager.isBuiltinLayout(layout.name));
/* 15 */     this.layout = layout;
/* 16 */     this.iconTexture = ResourceLocations.EXT;
/* 17 */     this.icon = (Icon)new IconTiled(this.iconTexture, LayoutManager.isBuiltinLayout(layout.name) ? 32 : 33, 32);
/* 18 */     this.hasIcon = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public DesignableGuiLayout getLayout() {
/* 23 */     return this.layout;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLayoutName() {
/* 28 */     return this.layout.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLayoutDisplayName() {
/* 33 */     return this.layout.displayName;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\list\ListObjectGuiLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */