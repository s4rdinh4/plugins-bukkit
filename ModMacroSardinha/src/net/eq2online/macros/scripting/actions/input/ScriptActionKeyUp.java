/*    */ package net.eq2online.macros.scripting.actions.input;
/*    */ 
/*    */ import bsr;
/*    */ import bto;
/*    */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScriptActionKeyUp
/*    */   extends ScriptAction
/*    */ {
/*    */   protected boolean keyState = false;
/*    */   
/*    */   public ScriptActionKeyUp(ScriptContext context) {
/* 31 */     super(context, "keyup");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ScriptActionKeyUp(ScriptContext context, String actionName) {
/* 41 */     super(context, actionName);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 47 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPermissable() {
/* 56 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 65 */     return "input";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 71 */     if (params.length > 0) {
/*    */       
/* 73 */       String parameter = ScriptCore.parseVars(provider, macro, params[0], false);
/* 74 */       bto gameSettings = AbstractionLayer.getGameSettings();
/*    */       
/* 76 */       if (parameter.equalsIgnoreCase("forward")) { bsr.a(gameSettings.U.i(), this.keyState); return null; }
/* 77 */        if (parameter.equalsIgnoreCase("back")) { bsr.a(gameSettings.W.i(), this.keyState); return null; }
/* 78 */        if (parameter.equalsIgnoreCase("left")) { bsr.a(gameSettings.V.i(), this.keyState); return null; }
/* 79 */        if (parameter.equalsIgnoreCase("right")) { bsr.a(gameSettings.X.i(), this.keyState); return null; }
/* 80 */        if (parameter.equalsIgnoreCase("jump")) { bsr.a(gameSettings.Y.i(), this.keyState); return null; }
/* 81 */        if (parameter.equalsIgnoreCase("sneak")) { bsr.a(gameSettings.Z.i(), this.keyState); return null; }
/* 82 */        if (parameter.equalsIgnoreCase("playerlist")) { bsr.a(gameSettings.ah.i(), this.keyState); return null; }
/* 83 */        if (parameter.equalsIgnoreCase("sprint")) { bsr.a(gameSettings.af.i(), this.keyState); return null; }
/*    */       
/* 85 */       int keyCode = ScriptCore.tryParseInt(parameter, 0);
/*    */       
/* 87 */       if (keyCode > 0 && keyCode < 255 && keyCode != gameSettings.ad.i() && keyCode != gameSettings.ab.i())
/*    */       {
/* 89 */         bsr.a(keyCode, this.keyState);
/*    */       }
/*    */     } 
/*    */     
/* 93 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\input\ScriptActionKeyUp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */