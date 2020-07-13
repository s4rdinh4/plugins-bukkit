/*    */ package net.eq2online.macros.scripting.actions.lang;
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
/*    */ public class ScriptActionStop
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionStop(ScriptContext context) {
/* 16 */     super(context, "stop");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 22 */     if (params.length > 0) {
/*    */       
/* 24 */       String string = ScriptCore.parseVars(provider, macro, params[0], false);
/* 25 */       if (string.equalsIgnoreCase("all") || string.equals("*"))
/*    */       {
/* 27 */         provider.actionStopMacros();
/*    */       }
/*    */       else
/*    */       {
/* 31 */         int keyCode = 0;
/*    */         
/* 33 */         for (int key = 1; key < 255; key++) {
/*    */           
/* 35 */           String keyName = MacroType.getMacroName(key);
/*    */           
/* 37 */           if (keyName != null && keyName.equalsIgnoreCase(string)) {
/*    */             
/* 39 */             keyCode = key;
/*    */             
/*    */             break;
/*    */           } 
/*    */         } 
/* 44 */         if (keyCode == 0)
/*    */         {
/* 46 */           keyCode = MacroType.None.getIndexForName(string);
/*    */         }
/*    */         
/* 49 */         if (keyCode == 0)
/*    */         {
/* 51 */           keyCode = ScriptCore.tryParseInt(string, 0);
/*    */         }
/*    */         
/* 54 */         if (keyCode > 0)
/*    */         {
/* 56 */           provider.actionStopMacros(macro, keyCode);
/*    */         }
/*    */       }
/*    */     
/*    */     } else {
/*    */       
/* 62 */       provider.actionStopMacros(macro, macro.getID());
/*    */     } 
/*    */     
/* 65 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionStop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */