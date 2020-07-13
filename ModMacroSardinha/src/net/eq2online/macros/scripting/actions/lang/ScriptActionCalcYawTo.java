/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import bsu;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ import uv;
/*    */ 
/*    */ public class ScriptActionCalcYawTo
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionCalcYawTo(ScriptContext context) {
/* 18 */     super(context, "calcyawto");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 24 */     ReturnValue retVal = new ReturnValue(0);
/* 25 */     bsu minecraft = bsu.z();
/*    */     
/* 27 */     if (params.length > 1 && minecraft != null && minecraft.h != null) {
/*    */       
/* 29 */       float xPos = ScriptCore.tryParseInt(ScriptCore.parseVars(provider, macro, params[0], false), 0) + 0.5F;
/* 30 */       float zPos = ScriptCore.tryParseInt(ScriptCore.parseVars(provider, macro, params[1], false), 0) + 0.5F;
/*    */       
/* 32 */       double deltaX = xPos - minecraft.h.s;
/* 33 */       double deltaZ = zPos - minecraft.h.u;
/* 34 */       double distance = uv.a(deltaX * deltaX + deltaZ * deltaZ);
/* 35 */       int yaw = (int)(Math.atan2(deltaZ, deltaX) * 180.0D / Math.PI - 90.0D);
/* 36 */       for (; yaw < 0; yaw += 360);
/*    */       
/* 38 */       retVal.setInt(yaw);
/*    */       
/* 40 */       if (params.length > 2) {
/*    */         
/* 42 */         String varName = ScriptCore.parseVars(provider, macro, params[2], false);
/* 43 */         ScriptCore.setVariable(provider, macro, varName, yaw);
/*    */       } 
/*    */       
/* 46 */       if (params.length > 3) {
/*    */         
/* 48 */         String varName = ScriptCore.parseVars(provider, macro, params[3], false);
/* 49 */         ScriptCore.setVariable(provider, macro, varName, uv.c(distance));
/*    */       } 
/*    */     } 
/*    */     
/* 53 */     return (IReturnValue)retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionCalcYawTo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */