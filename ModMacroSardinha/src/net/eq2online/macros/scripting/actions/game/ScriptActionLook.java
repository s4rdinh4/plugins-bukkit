/*     */ package net.eq2online.macros.scripting.actions.game;
/*     */ 
/*     */ import cio;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.scripting.Direction;
/*     */ import net.eq2online.macros.scripting.DirectionInterpolator;
/*     */ import net.eq2online.macros.scripting.FloatInterpolator;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IReturnValue;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*     */ import wv;
/*     */ 
/*     */ public class ScriptActionLook
/*     */   extends ScriptAction {
/*  20 */   protected FloatInterpolator.InterpolationType interpolationType = FloatInterpolator.InterpolationType.Linear;
/*     */   
/*  22 */   protected static int activeInterpolatorId = 0;
/*     */ 
/*     */   
/*     */   public ScriptActionLook(ScriptContext context) {
/*  26 */     super(context, "look");
/*     */   }
/*     */ 
/*     */   
/*     */   protected ScriptActionLook(ScriptContext context, String actionName) {
/*  31 */     super(context, actionName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isThreadSafe() {
/*  37 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClocked() {
/*  46 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPermissable() {
/*  55 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPermissionGroup() {
/*  64 */     return "input";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canExecuteNow(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/*  73 */     cio thePlayer = AbstractionLayer.getPlayer();
/*     */     
/*  75 */     if (params.length > 1 && thePlayer != null) {
/*     */       
/*  77 */       if (instance.getState() == null) {
/*     */         
/*  79 */         Direction currentDirection = new Direction(thePlayer.y, thePlayer.z);
/*  80 */         Direction targetDirection = getDirection(provider, macro, params, currentDirection);
/*  81 */         instance.setState(new DirectionInterpolator(currentDirection, targetDirection, this.interpolationType, ++activeInterpolatorId));
/*     */       } 
/*     */       
/*  84 */       DirectionInterpolator state = (DirectionInterpolator)instance.getState();
/*     */       
/*  86 */       if (state.id >= activeInterpolatorId) {
/*     */         
/*  88 */         Direction newDirection = state.interpolate();
/*  89 */         provider.actionSetEntityDirection((wv)thePlayer, newDirection.yaw, newDirection.pitch);
/*     */         
/*  91 */         return state.isFinished();
/*     */       } 
/*     */     } 
/*     */     
/*  95 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 104 */     cio thePlayer = AbstractionLayer.getPlayer();
/*     */     
/* 106 */     if (params.length > 0 && thePlayer != null && instance.getState() == null) {
/*     */       
/* 108 */       Direction currentDirection = new Direction(thePlayer.y * 360.0F, thePlayer.z * 360.0F);
/* 109 */       Direction targetDirection = getDirection(provider, macro, params, currentDirection);
/*     */       
/* 111 */       if (!targetDirection.isEmpty())
/*     */       {
/* 113 */         provider.actionSetEntityDirection((wv)thePlayer, targetDirection.yaw, targetDirection.pitch);
/*     */       }
/*     */     } 
/*     */     
/* 117 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Direction getDirection(IScriptActionProvider provider, IMacro macro, String[] params, Direction initialDirection) {
/* 122 */     Direction dir = initialDirection.cloneDirection();
/* 123 */     String[] parsedParams = new String[params.length];
/*     */     
/* 125 */     for (int i = 0; i < params.length; i++)
/*     */     {
/* 127 */       parsedParams[i] = ScriptCore.parseVars(provider, macro, params[i], false);
/*     */     }
/*     */     
/* 130 */     if (parsedParams.length > 0) {
/*     */       
/* 132 */       int delayParam = 1;
/*     */       
/* 134 */       if (parsedParams[0].equalsIgnoreCase("north")) {
/*     */         
/* 136 */         dir.setYawAndPitch(180.0F, 0.0F);
/*     */       }
/* 138 */       else if (parsedParams[0].equalsIgnoreCase("east")) {
/*     */         
/* 140 */         dir.setYawAndPitch(270.0F, 0.0F);
/*     */       }
/* 142 */       else if (parsedParams[0].equalsIgnoreCase("south")) {
/*     */         
/* 144 */         dir.setYawAndPitch(0.0F, 0.0F);
/*     */       }
/* 146 */       else if (parsedParams[0].equalsIgnoreCase("west")) {
/*     */         
/* 148 */         dir.setYawAndPitch(90.0F, 0.0F);
/*     */       }
/* 150 */       else if (parsedParams[0].equalsIgnoreCase("near")) {
/*     */         
/* 152 */         int near = 0;
/* 153 */         if (initialDirection.yaw >= 45.0F && initialDirection.yaw < 135.0F) near = 90; 
/* 154 */         if (initialDirection.yaw >= 135.0F && initialDirection.yaw < 225.0F) near = 180; 
/* 155 */         if (initialDirection.yaw >= 225.0F && initialDirection.yaw < 315.0F) near = 270; 
/* 156 */         dir.setYawAndPitch(near, 0.0F);
/*     */       }
/*     */       else {
/*     */         
/* 160 */         delayParam = 2;
/*     */         
/* 162 */         if (Pattern.matches("^([\\+\\-]?)[0-9]+$", parsedParams[0])) {
/*     */           
/* 164 */           float yawOffset = ScriptCore.tryParseFloat(parsedParams[0], 0.0F);
/*     */           
/* 166 */           if (parsedParams[0].startsWith("+") || parsedParams[0].startsWith("-")) { dir.setYaw(initialDirection.yaw + yawOffset); }
/* 167 */           else { dir.setYaw(yawOffset + 180.0F); }
/*     */         
/*     */         } 
/* 170 */         if (parsedParams.length > 1 && Pattern.matches("^([\\+\\-]?)[0-9]+$", parsedParams[1])) {
/*     */           
/* 172 */           float pitchOffset = ScriptCore.tryParseFloat(parsedParams[1], 0.0F);
/*     */           
/* 174 */           if (parsedParams[1].startsWith("+") || parsedParams[1].startsWith("-")) { dir.setPitch(initialDirection.pitch + pitchOffset); }
/* 175 */           else { dir.setPitch(pitchOffset); }
/*     */         
/*     */         } 
/*     */       } 
/* 179 */       if (parsedParams.length > delayParam)
/*     */       {
/* 181 */         dir.setDuration((long)(ScriptCore.tryParseFloat(parsedParams[delayParam], 0.0F) * 1000.0F));
/*     */       }
/*     */     } 
/*     */     
/* 185 */     return dir;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionLook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */