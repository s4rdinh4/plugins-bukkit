/*     */ package net.eq2online.macros.scripting.actions.option;
/*     */ 
/*     */ import btr;
/*     */ import cxz;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.scripting.FloatInterpolator;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IReturnValue;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScriptActionGamma<T extends Enum<?>>
/*     */   extends ScriptAction
/*     */ {
/*     */   protected T option;
/*  27 */   protected float scaleMinValue = 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  32 */   protected float scaleMaxValue = 100.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   protected float minValue = 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   protected float maxValue = 100.0F;
/*     */   
/*     */   private boolean noScale;
/*     */ 
/*     */   
/*     */   public ScriptActionGamma(ScriptContext context) {
/*  48 */     super(context, "gamma");
/*     */     
/*  50 */     this.option = (T)btr.d;
/*  51 */     this.maxValue = 200.0F;
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
/*     */   
/*     */   protected ScriptActionGamma(ScriptContext context, String actionName, T option, float minValue, float maxValue) {
/*  64 */     super(context, actionName);
/*     */     
/*  66 */     this.option = option;
/*  67 */     this.minValue = minValue;
/*  68 */     this.maxValue = maxValue;
/*     */     
/*  70 */     this.scaleMinValue = this.minValue;
/*  71 */     this.scaleMaxValue = this.maxValue;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setNoScale(boolean noScale) {
/*  76 */     this.noScale = noScale;
/*     */   }
/*     */ 
/*     */   
/*     */   protected T getOption(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String[] params) {
/*  81 */     return this.option;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isThreadSafe() {
/*  87 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canExecuteNow(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/*  94 */     if (params.length > 1) {
/*     */       
/*  96 */       if (instance.getState() == null) {
/*     */         
/*  98 */         float currentValue = getCurrentOptionValue(getOption(provider, macro, instance, params));
/*  99 */         instance.setState(new FloatInterpolator(currentValue, targetValue(ScriptCore.parseVars(provider, macro, params[0], false)), (long)(ScriptCore.tryParseFloat(ScriptCore.parseVars(provider, macro, params[1], false), 0.0F) * 1000.0F), FloatInterpolator.InterpolationType.Linear));
/*     */       } 
/*     */       
/* 102 */       float newValue = ((FloatInterpolator)instance.getState()).interpolate().floatValue();
/* 103 */       setOptionValue(getOption(provider, macro, instance, params), newValue);
/*     */       
/* 105 */       return ((FloatInterpolator)instance.getState()).finished;
/*     */     } 
/*     */     
/* 108 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getCurrentOptionValue(T option) {
/* 113 */     if (option instanceof cxz)
/*     */     {
/* 115 */       return AbstractionLayer.getGameSettings().a((cxz)option);
/*     */     }
/*     */     
/* 118 */     return AbstractionLayer.getGameSettings().a((btr)option);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setOptionValue(T option, float newValue) {
/* 123 */     if (option instanceof cxz) {
/*     */       
/* 125 */       AbstractionLayer.getGameSettings().a((cxz)option, newValue);
/*     */       
/*     */       return;
/*     */     } 
/* 129 */     AbstractionLayer.getGameSettings().a((btr)option, newValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClocked() {
/* 138 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 144 */     if (params.length > 0) {
/*     */       
/* 146 */       float newValue = targetValue(ScriptCore.parseVars(provider, macro, params[0], false));
/* 147 */       setOptionValue(getOption(provider, macro, instance, params), newValue);
/*     */     } 
/*     */     
/* 150 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float targetValue(String param) {
/* 161 */     float targetValue = ScriptCore.tryParseFloat(param, this.minValue);
/*     */     
/* 163 */     if (this.noScale) return targetValue;
/*     */     
/* 165 */     if (targetValue < this.minValue) targetValue = this.minValue; 
/* 166 */     if (targetValue > this.maxValue) targetValue = this.maxValue;
/*     */     
/* 168 */     return (targetValue - this.scaleMinValue) / (this.scaleMaxValue - this.scaleMinValue);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\option\ScriptActionGamma.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */