/*    */ package net.eq2online.macros.gui.list;
/*    */ 
/*    */ import com.mumfrey.liteloader.util.render.Icon;
/*    */ import net.eq2online.macros.compatibility.IconTiled;
/*    */ import net.eq2online.macros.res.ResourceLocations;
/*    */ import net.eq2online.macros.struct.Place;
/*    */ import oa;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ListObjectPlace
/*    */   extends ListObjectEditable
/*    */ {
/*    */   public ListObjectPlace(int id, Place data) {
/* 18 */     super(id, 15, (String)null, ResourceLocations.EXT, data);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initIcon(oa iconTexture, int iconID) {
/* 24 */     this.iconTexture = iconTexture;
/* 25 */     this.icon = (Icon)new IconTiled(iconTexture, 35, 32);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setData(Object newData) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getText() {
/* 37 */     return ((Place)this.data).name;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDisplayName() {
/* 43 */     return getText();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDisplayName(String newDisplayName) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void setText(String newText) {
/* 54 */     ((Place)this.data).name = newText;
/* 55 */     super.setText(newText);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 61 */     return super.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String serialise() {
/* 67 */     return ((Place)this.data).toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\list\ListObjectPlace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */