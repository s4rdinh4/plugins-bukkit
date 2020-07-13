/*    */ package net.eq2online.macros.scripting;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FloatInterpolator
/*    */ {
/*    */   protected InterpolationType interpolationType;
/*    */   public float start;
/*    */   public float target;
/*    */   public long startTime;
/*    */   public long duration;
/*    */   
/*    */   public enum InterpolationType
/*    */   {
/* 17 */     Linear,
/* 18 */     Smooth;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean finished = false;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected float interpolationMultiplier;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FloatInterpolator(float startValue, float targetValue, long duration, InterpolationType interpolationType) {
/* 48 */     this.interpolationType = interpolationType;
/*    */     
/* 50 */     this.startTime = System.currentTimeMillis();
/* 51 */     this.duration = duration;
/*    */     
/* 53 */     this.start = startValue;
/* 54 */     this.target = targetValue;
/*    */     
/* 56 */     if (startValue == targetValue || duration == 0L) {
/* 57 */       this.finished = true;
/* 58 */     } else if (startValue > targetValue) {
/* 59 */       this.interpolationMultiplier = -1.0F;
/*    */     } else {
/* 61 */       this.interpolationMultiplier = 1.0F;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Float interpolate() {
/* 71 */     if (this.finished)
/*    */     {
/* 73 */       return Float.valueOf(this.target);
/*    */     }
/*    */     
/* 76 */     float deltaTime = (float)(System.currentTimeMillis() - this.startTime) / (float)this.duration;
/*    */     
/* 78 */     if (deltaTime >= 1.0F) {
/*    */       
/* 80 */       this.finished = true;
/* 81 */       return Float.valueOf(this.target);
/*    */     } 
/*    */     
/* 84 */     if (this.interpolationType == InterpolationType.Smooth)
/*    */     {
/* 86 */       deltaTime = (float)(Math.sin((deltaTime - 0.5D) * Math.PI) * 0.5D) + 0.5F;
/*    */     }
/*    */     
/* 89 */     float value = this.start + deltaTime * (this.target - this.start);
/*    */     
/* 91 */     if ((this.interpolationMultiplier > 0.0F && value >= this.target) || (this.interpolationMultiplier < 0.0F && value <= this.target)) {
/*    */       
/* 93 */       value = this.target;
/* 94 */       this.finished = true;
/*    */     } 
/*    */     
/* 97 */     return Float.valueOf(value);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\FloatInterpolator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */