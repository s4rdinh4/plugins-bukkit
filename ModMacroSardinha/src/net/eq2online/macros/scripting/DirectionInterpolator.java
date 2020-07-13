/*    */ package net.eq2online.macros.scripting;
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
/*    */ 
/*    */ 
/*    */ public class DirectionInterpolator
/*    */ {
/*    */   protected FloatInterpolator yawInterpolator;
/*    */   protected FloatInterpolator pitchInterpolator;
/*    */   public final int id;
/*    */   
/*    */   public DirectionInterpolator(Direction initialDirection, Direction targetDirection, FloatInterpolator.InterpolationType interpolationType, int id) {
/* 23 */     this.id = id;
/*    */     
/* 25 */     if (initialDirection.yaw - targetDirection.yaw >= 180.0F) {
/* 26 */       targetDirection.yaw += 360.0F;
/* 27 */     } else if (targetDirection.yaw - initialDirection.yaw > 180.0F) {
/* 28 */       initialDirection.yaw += 360.0F;
/*    */     } 
/* 30 */     if (initialDirection.pitch - targetDirection.pitch >= 180.0F) {
/* 31 */       targetDirection.pitch += 360.0F;
/* 32 */     } else if (targetDirection.pitch - initialDirection.pitch > 180.0F) {
/* 33 */       initialDirection.pitch += 360.0F;
/*    */     } 
/* 35 */     this.yawInterpolator = new FloatInterpolator(initialDirection.yaw, targetDirection.yaw, targetDirection.duration, interpolationType);
/* 36 */     this.pitchInterpolator = new FloatInterpolator(initialDirection.pitch, targetDirection.pitch, targetDirection.duration, interpolationType);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Direction interpolate() {
/* 46 */     return new Direction(this.yawInterpolator.interpolate().floatValue(), this.pitchInterpolator.interpolate().floatValue());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isFinished() {
/* 56 */     return (this.yawInterpolator.finished && this.pitchInterpolator.finished);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\DirectionInterpolator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */