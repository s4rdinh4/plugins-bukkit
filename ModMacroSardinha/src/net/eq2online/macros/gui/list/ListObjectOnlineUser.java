/*    */ package net.eq2online.macros.gui.list;
/*    */ 
/*    */ import com.mumfrey.liteloader.util.render.Icon;
/*    */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*    */ import net.eq2online.macros.compatibility.IconTiled;
/*    */ import net.eq2online.macros.core.MacroModCore;
/*    */ import net.eq2online.macros.res.ResourceLocations;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ListObjectOnlineUser
/*    */   extends ListObjectGeneric
/*    */ {
/* 19 */   int previousIconID = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ListObjectOnlineUser(int id, String username) {
/* 29 */     super(id, username, username, true, ResourceLocations.PLAYERS, 0);
/*    */ 
/*    */     
/* 32 */     MacroModCore.getInstance().getUserSkinManager().addUser(username);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IconTiled getIcon() {
/* 38 */     return MacroModCore.getInstance().getUserSkinManager().getIconForSkin(this.text);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void bindIconTexture() {
/* 44 */     AbstractionLayer.bindTexture(ResourceLocations.PLAYERS);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\list\ListObjectOnlineUser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */