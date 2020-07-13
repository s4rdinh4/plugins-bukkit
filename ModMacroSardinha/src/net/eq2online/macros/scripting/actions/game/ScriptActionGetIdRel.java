/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import atr;
/*    */ import bec;
/*    */ import cen;
/*    */ import cio;
/*    */ import dt;
/*    */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ import uv;
/*    */ 
/*    */ public class ScriptActionGetIdRel
/*    */   extends ScriptActionGetId
/*    */ {
/*    */   public ScriptActionGetIdRel(ScriptContext context) {
/* 23 */     super(context, "getidrel");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 29 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 38 */     ReturnValue retVal = new ReturnValue(Macros.getBlockName(null));
/*    */     
/* 40 */     if (params.length > 2) {
/*    */       
/* 42 */       cen theWorld = AbstractionLayer.getWorld();
/* 43 */       cio thePlayer = AbstractionLayer.getPlayer();
/* 44 */       if (theWorld != null && thePlayer != null) {
/*    */         
/* 46 */         int playerPosX = uv.c(thePlayer.s);
/* 47 */         int playerPosY = uv.c(thePlayer.t);
/* 48 */         int playerPosZ = uv.c(thePlayer.u);
/*    */         
/* 50 */         int xPos = playerPosX + ScriptCore.tryParseInt(ScriptCore.parseVars(provider, macro, params[0], false), 0);
/* 51 */         int yPos = playerPosY + ScriptCore.tryParseInt(ScriptCore.parseVars(provider, macro, params[1], false), 0);
/* 52 */         int zPos = playerPosZ + ScriptCore.tryParseInt(ScriptCore.parseVars(provider, macro, params[2], false), 0);
/* 53 */         dt blockPos = new dt(xPos, yPos, zPos);
/*    */         
/* 55 */         bec blockState = theWorld.p(blockPos);
/* 56 */         atr block = blockState.c();
/* 57 */         retVal.setString(Macros.getBlockName(block));
/*    */         
/* 59 */         if (params.length > 3) {
/*    */           
/* 61 */           String idVarName = ScriptCore.parseVars(provider, macro, params[3], false).toLowerCase();
/* 62 */           ScriptCore.setVariable(provider, macro, idVarName, Macros.getBlockName(block));
/*    */         } 
/*    */         
/* 65 */         if (params.length > 4) {
/*    */           
/* 67 */           String dmgVarName = ScriptCore.parseVars(provider, macro, params[4], false).toLowerCase();
/* 68 */           int blockDamage = block.a(blockState);
/* 69 */           ScriptCore.setVariable(provider, macro, dmgVarName, blockDamage);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 74 */     return (IReturnValue)retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionGetIdRel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */