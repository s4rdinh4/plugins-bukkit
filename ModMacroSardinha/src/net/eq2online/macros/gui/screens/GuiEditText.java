/*    */ package net.eq2online.macros.gui.screens;
/*    */ 
/*    */ import net.eq2online.macros.gui.controls.GuiCheckBox;
/*    */ import net.eq2online.macros.gui.controls.GuiTextEditor;
/*    */ import net.eq2online.macros.gui.shared.GuiScreenEx;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class GuiEditText
/*    */   extends GuiScreenWithHeader
/*    */ {
/*    */   protected GuiScreenEx parent;
/*    */   protected GuiTextEditor textEditor;
/*    */   protected GuiCheckBox chkShowHelp;
/*    */   
/*    */   public GuiEditText(GuiScreenEx parent) {
/* 24 */     super(0, 0);
/* 25 */     this.parent = parent;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onCloseClick() {
/* 31 */     close();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void close() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void b() {
/* 45 */     Keyboard.enableRepeatEvents(true);
/*    */     
/* 47 */     super.b();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void m() {
/* 56 */     Keyboard.enableRepeatEvents(false);
/*    */     
/* 58 */     super.m();
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\screens\GuiEditText.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */