/*    */ package net.eq2online.macros.scripting.actions.input;
/*    */ 
/*    */ import bsr;
/*    */ import bto;
/*    */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*    */ import net.eq2online.macros.compatibility.PrivateFields;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ import um;
/*    */ 
/*    */ public class ScriptActionToggleKey
/*    */   extends ScriptAction {
/*    */   public ScriptActionToggleKey(ScriptContext context) {
/* 19 */     super(context, "togglekey");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 25 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPermissable() {
/* 34 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 43 */     return "input";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 49 */     if (params.length > 0) {
/*    */       
/* 51 */       String parameter = ScriptCore.parseVars(provider, macro, params[0], false);
/* 52 */       bto gameSettings = AbstractionLayer.getGameSettings();
/*    */       
/* 54 */       if (parameter.equalsIgnoreCase("forward")) { bsr.a(gameSettings.U.i(), !gameSettings.U.d()); return null; }
/* 55 */        if (parameter.equalsIgnoreCase("back")) { bsr.a(gameSettings.W.i(), !gameSettings.W.d()); return null; }
/* 56 */        if (parameter.equalsIgnoreCase("left")) { bsr.a(gameSettings.V.i(), !gameSettings.V.d()); return null; }
/* 57 */        if (parameter.equalsIgnoreCase("right")) { bsr.a(gameSettings.X.i(), !gameSettings.X.d()); return null; }
/* 58 */        if (parameter.equalsIgnoreCase("jump")) { bsr.a(gameSettings.Y.i(), !gameSettings.Y.d()); return null; }
/* 59 */        if (parameter.equalsIgnoreCase("sneak")) { bsr.a(gameSettings.Z.i(), !gameSettings.Z.d()); return null; }
/* 60 */        if (parameter.equalsIgnoreCase("playerlist")) { bsr.a(gameSettings.ah.i(), !gameSettings.ah.d()); return null; }
/* 61 */        if (parameter.equalsIgnoreCase("sprint")) { bsr.a(gameSettings.af.i(), !gameSettings.af.d()); return null; }
/*    */       
/* 63 */       int keyCode = ScriptCore.tryParseInt(parameter, 0);
/*    */       
/* 65 */       if (keyCode > 0 && keyCode < 255) {
/*    */         
/* 67 */         bsr keyBinding = (bsr)((um)PrivateFields.StaticFields.keyBindHash.get()).a(keyCode);
/*    */         
/* 69 */         if (keyBinding != null && keyBinding != gameSettings.ad && keyBinding != gameSettings.ab)
/*    */         {
/* 71 */           bsr.a(keyCode, !keyBinding.d());
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 76 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\input\ScriptActionToggleKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */