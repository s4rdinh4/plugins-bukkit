/*    */ package net.eq2online.macros.scripting.actions.input;
/*    */ 
/*    */ import net.eq2online.macros.core.MacroType;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionPress
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionPress(ScriptContext context) {
/* 16 */     super(context, "press");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 22 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPermissable() {
/* 31 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 40 */     return "input";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 46 */     if (params.length > 0) {
/*    */       
/* 48 */       int keyCode = 0;
/*    */       
/* 50 */       for (int key = 1; key < 255; key++) {
/*    */         
/* 52 */         String keyName = MacroType.getMacroName(key);
/*    */         
/* 54 */         if (keyName != null && keyName.equalsIgnoreCase(params[0])) {
/*    */           
/* 56 */           keyCode = key;
/*    */           
/*    */           break;
/*    */         } 
/*    */       } 
/* 61 */       if (keyCode == 0)
/*    */       {
/* 63 */         keyCode = ScriptCore.tryParseInt(params[0], 0);
/*    */       }
/*    */       
/* 66 */       if (keyCode > 0) {
/*    */         
/* 68 */         boolean deep = false;
/*    */         
/* 70 */         if (params.length > 1)
/*    */         {
/* 72 */           deep = (params[1].equalsIgnoreCase("true") || params[1].equals("1"));
/*    */         }
/*    */         
/* 75 */         provider.actionPumpKeyPress(keyCode, deep);
/*    */       } 
/*    */     } 
/*    */     
/* 79 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\input\ScriptActionPress.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */