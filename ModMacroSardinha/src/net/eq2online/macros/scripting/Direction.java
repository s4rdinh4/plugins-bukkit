/*     */ package net.eq2online.macros.scripting;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Direction
/*     */ {
/*     */   public float yaw;
/*     */   public float pitch;
/*     */   public boolean includesYaw;
/*     */   public boolean includesPitch;
/*     */   public long duration;
/*     */   
/*     */   public Direction() {}
/*     */   
/*     */   public Direction(float yaw, float pitch) {
/*  47 */     for (; yaw < 0.0F; yaw += 360.0F);
/*  48 */     for (; yaw > 360.0F; yaw -= 360.0F);
/*     */     
/*  50 */     for (; pitch < 0.0F; pitch += 360.0F);
/*  51 */     for (; pitch > 360.0F; pitch -= 360.0F);
/*     */     
/*  53 */     this.yaw = yaw;
/*  54 */     this.pitch = pitch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setYaw(float yaw) {
/*  64 */     this.yaw = yaw;
/*  65 */     this.includesYaw = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPitch(float pitch) {
/*  75 */     this.pitch = pitch;
/*  76 */     this.includesPitch = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setYawAndPitch(float yaw, float pitch) {
/*  87 */     setPitch(pitch);
/*  88 */     setYaw(yaw);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDuration(long duration) {
/*  97 */     this.duration = duration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 106 */     return (!this.includesPitch && !this.includesYaw);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Direction cloneDirection() {
/* 116 */     return new Direction(this.yaw, this.pitch);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\Direction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */