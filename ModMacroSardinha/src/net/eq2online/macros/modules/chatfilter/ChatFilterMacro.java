/*    */ package net.eq2online.macros.modules.chatfilter;
/*    */ 
/*    */ import net.eq2online.macros.core.Macro;
/*    */ import net.eq2online.macros.core.MacroActionProcessor;
/*    */ import net.eq2online.macros.core.MacroPlaybackType;
/*    */ import net.eq2online.macros.core.MacroTemplate;
/*    */ import net.eq2online.macros.core.MacroType;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroActionContext;
/*    */ import net.eq2online.macros.scripting.exceptions.ScriptException;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ChatFilterMacro
/*    */   extends Macro {
/*    */   public boolean pass = true;
/* 16 */   public String newMessage = null;
/*    */ 
/*    */   
/*    */   public ChatFilterMacro(MacroTemplate owner, int macroId, IMacroActionContext context) {
/* 20 */     super(owner, macroId, MacroPlaybackType.OneShot, MacroType.Event, context);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void build() {
/* 29 */     if (this.built)
/* 30 */       return;  this.built = true;
/* 31 */     this.buildTime = System.currentTimeMillis();
/*    */     
/* 33 */     this.keyDownScriptActions = MacroActionProcessor.compile(ScriptContext.CHATFILTER.getParser(), "$${" + this.keyDownMacro + "}$$", 0, 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean playMacro(boolean keyDown, boolean clock) throws ScriptException {
/* 42 */     if (this.killed) return false;
/*    */     
/* 44 */     build();
/*    */     
/* 46 */     while (this.keyDownScriptActions.execute((IMacro)this, this.context, false, true, clock) && !this.killed);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 51 */     return this.pass;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\modules\chatfilter\ChatFilterMacro.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */