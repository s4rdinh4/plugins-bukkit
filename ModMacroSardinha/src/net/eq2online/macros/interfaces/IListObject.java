/*    */ package net.eq2online.macros.interfaces;public interface IListObject { CustomDrawBehaviour getCustomDrawBehaviour(); void drawCustom(boolean paramBoolean, bsu parambsu, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7); boolean mousePressed(boolean paramBoolean, bsu parambsu, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6); void mouseReleased(int paramInt1, int paramInt2); String getCustomAction(boolean paramBoolean); boolean hasIcon();
/*    */   oa getIconTexture();
/*    */   void bindIconTexture();
/*    */   Icon getIcon();
/*    */   amj getDisplayItem();
/*    */   boolean renderIcon(bsu parambsu, int paramInt1, int paramInt2);
/*    */   String getText();
/*    */   String getDisplayName();
/*    */   int getId();
/*    */   String getIdentifier();
/*    */   Object getData();
/*    */   boolean isDraggable();
/*    */   boolean getCanEditInPlace();
/*    */   boolean isEditingInPlace();
/*    */   void beginEditInPlace();
/*    */   void endEditInPlace();
/*    */   boolean editInPlaceKeyTyped(char paramChar, int paramInt);
/*    */   boolean editInPlaceMousePressed(boolean paramBoolean, bsu parambsu, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*    */   void editInPlaceDraw(boolean paramBoolean, bsu parambsu, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7);
/*    */   void setIconId(int paramInt);
/*    */   void setText(String paramString);
/*    */   void setDisplayName(String paramString);
/*    */   void setId(int paramInt);
/*    */   void setData(Object paramObject);
/*    */   String serialise();
/* 26 */   public enum CustomDrawBehaviour { NoCustomDraw,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 31 */     CustomDrawExtra,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 36 */     FullCustomDraw; }
/*    */    }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\interfaces\IListObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */