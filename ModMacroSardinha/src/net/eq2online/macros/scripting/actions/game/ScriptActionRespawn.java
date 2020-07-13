/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import bsu;
/*    */ import bxf;
/*    */ import net.eq2online.macros.compatibility.PrivateFields;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ 
/*    */ public class ScriptActionRespawn
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionRespawn(ScriptContext context) {
/* 18 */     super(context, "respawn");
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
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPermissable() {
/* 33 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 42 */     return "player";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 51 */     bxf currentScreen = (bsu.z()).m;
/*    */     
/* 53 */     if (currentScreen instanceof bwl) {
/*    */       
/*    */       try {
/*    */         
/* 57 */         int cooldownTimer = ((Integer)PrivateFields.coolDownTimer.get(currentScreen)).intValue();
/*    */         
/* 59 */         if (cooldownTimer >= 20) {
/* 60 */           provider.actionRespawnPlayer();
/*    */         }
/* 62 */       } catch (Exception exception) {}
/*    */     }
/*    */     
/* 65 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionRespawn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */