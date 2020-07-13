/*     */ package net.eq2online.macros.scripting.actions.game;
/*     */ 
/*     */ import atr;
/*     */ import bec;
/*     */ import cen;
/*     */ import cio;
/*     */ import dt;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IReturnValue;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.ReturnValue;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*     */ import uv;
/*     */ 
/*     */ public class ScriptActionGetId
/*     */   extends ScriptAction
/*     */ {
/*     */   public ScriptActionGetId(ScriptContext context) {
/*  24 */     super(context, "getid");
/*     */   }
/*     */ 
/*     */   
/*     */   protected ScriptActionGetId(ScriptContext context, String actionName) {
/*  29 */     super(context, actionName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isThreadSafe() {
/*  35 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/*  44 */     ReturnValue retVal = new ReturnValue(Macros.getBlockName(null));
/*     */     
/*  46 */     if (params.length > 2) {
/*     */       
/*  48 */       cen theWorld = AbstractionLayer.getWorld();
/*  49 */       cio thePlayer = AbstractionLayer.getPlayer();
/*  50 */       if (theWorld != null && thePlayer != null) {
/*     */         
/*  52 */         int xPos = getPosition(provider, macro, params[0], thePlayer.s);
/*  53 */         int yPos = getPosition(provider, macro, params[1], thePlayer.t);
/*  54 */         int zPos = getPosition(provider, macro, params[2], thePlayer.u);
/*  55 */         dt blockPos = new dt(xPos, yPos, zPos);
/*     */         
/*  57 */         bec blockState = theWorld.p(blockPos);
/*  58 */         atr block = blockState.c();
/*  59 */         retVal.setString(Macros.getBlockName(block));
/*     */         
/*  61 */         if (params.length > 3) {
/*     */           
/*  63 */           String idVarName = ScriptCore.parseVars(provider, macro, params[3], false).toLowerCase();
/*  64 */           ScriptCore.setVariable(provider, macro, idVarName, Macros.getBlockName(block));
/*     */         } 
/*     */         
/*  67 */         if (params.length > 4) {
/*     */           
/*  69 */           String dmgVarName = ScriptCore.parseVars(provider, macro, params[4], false).toLowerCase();
/*  70 */           int blockDamage = block.a(blockState);
/*  71 */           ScriptCore.setVariable(provider, macro, dmgVarName, blockDamage);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  76 */     return (IReturnValue)retVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getPosition(IScriptActionProvider provider, IMacro macro, String param, double currentPos) {
/*  88 */     String sPos = ScriptCore.parseVars(provider, macro, param, false);
/*  89 */     boolean isRelative = sPos.startsWith("~");
/*  90 */     int iCurrentPosX = isRelative ? uv.c(currentPos) : 0;
/*  91 */     int xPos = iCurrentPosX + ScriptCore.tryParseInt(isRelative ? sPos.substring(1) : sPos, 0);
/*  92 */     return xPos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPermissable() {
/* 101 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPermissionGroup() {
/* 110 */     return "inventory";
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionGetId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */