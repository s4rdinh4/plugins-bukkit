/*    */ package net.eq2online.macros.scripting.actions.option;
/*    */ 
/*    */ import bsr;
/*    */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*    */ import net.eq2online.macros.core.MacroType;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionBind
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionBind(ScriptContext context) {
/* 18 */     super(context, "bind");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 24 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 30 */     if (params.length > 1) {
/*    */       
/* 32 */       int keyBindId = -1;
/* 33 */       int keyCode = 0;
/*    */       
/* 35 */       bsr[] keyBindings = (AbstractionLayer.getGameSettings()).at;
/*    */       int keyBindIndex;
/* 37 */       for (keyBindIndex = 0; keyBindIndex < keyBindings.length; keyBindIndex++) {
/*    */         
/* 39 */         if (keyBindings[keyBindIndex].g().equalsIgnoreCase(params[0])) {
/*    */           
/* 41 */           keyBindId = keyBindIndex;
/*    */           
/*    */           break;
/*    */         } 
/*    */       } 
/* 46 */       if (keyBindId == -1)
/*    */       {
/* 48 */         for (keyBindIndex = 0; keyBindIndex < keyBindings.length; keyBindIndex++) {
/*    */           
/* 50 */           if (keyBindings[keyBindIndex].g().toLowerCase().contains(params[0].toLowerCase())) {
/*    */             
/* 52 */             keyBindId = keyBindIndex;
/*    */             
/*    */             break;
/*    */           } 
/*    */         } 
/*    */       }
/* 58 */       for (int key = 1; key < 255; key++) {
/*    */         
/* 60 */         String keyName = MacroType.getMacroName(key);
/*    */         
/* 62 */         if (keyName != null && keyName.equalsIgnoreCase(params[1])) {
/*    */           
/* 64 */           keyCode = key;
/*    */           
/*    */           break;
/*    */         } 
/*    */       } 
/* 69 */       if (keyCode == 0) {
/* 70 */         keyCode = ScriptCore.tryParseInt(params[1], 0);
/*    */       }
/* 72 */       if (keyBindId > -1 && keyCode > 0) {
/*    */         
/*    */         try {
/*    */           
/* 76 */           provider.actionBindKey(keyBindId, keyCode);
/*    */         }
/* 78 */         catch (Exception exception) {}
/*    */       }
/*    */     } 
/*    */     
/* 82 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\option\ScriptActionBind.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */