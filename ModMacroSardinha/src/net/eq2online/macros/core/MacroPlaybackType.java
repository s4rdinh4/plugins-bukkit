/*    */ package net.eq2online.macros.core;
/*    */ 
/*    */ import net.eq2online.macros.interfaces.IHighlighter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum MacroPlaybackType
/*    */   implements IHighlighter
/*    */ {
/* 15 */   OneShot,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 20 */   KeyState,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 25 */   Conditional;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String generateHighlightMask(String text) {
/* 33 */     return MacroModCore.stringToHighlightMask(highlight(text));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String highlight(String text) {
/* 39 */     return Macro.highlightMacro(text, this, '6', 'f', '7', 'd');
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\MacroPlaybackType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */