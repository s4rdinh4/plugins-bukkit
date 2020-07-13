/*     */ package net.eq2online.macros.event;
/*     */ 
/*     */ import net.eq2online.macros.core.Macros;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MacroEventValueWatcher<T>
/*     */ {
/*     */   protected boolean suppressNext;
/*     */   protected String eventName;
/*     */   protected int eventPriority;
/*     */   protected T currentValue;
/*     */   protected T lastValue;
/*     */   
/*     */   public MacroEventValueWatcher(String eventName, int eventPriority, boolean suppressInitial) {
/*  50 */     this.eventName = eventName;
/*  51 */     this.eventPriority = eventPriority;
/*  52 */     this.suppressNext = suppressInitial;
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
/*     */   public boolean checkValue(T value) {
/*  64 */     if (value != null && (this.currentValue == null || !this.currentValue.equals(value))) {
/*     */       
/*  66 */       this.lastValue = this.currentValue;
/*  67 */       this.currentValue = value;
/*     */       
/*  69 */       if (!this.suppressNext) {
/*  70 */         return true;
/*     */       }
/*  72 */       this.suppressNext = false;
/*     */     } 
/*     */     
/*  75 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void suppressNext() {
/*  83 */     this.lastValue = this.currentValue;
/*  84 */     this.suppressNext = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkValueAndSuppress(T value) {
/*  94 */     checkValue(value);
/*  95 */     suppressNext();
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
/*     */   public void checkValueAndDispatch(T value, Macros macros, String... eventArgs) {
/* 107 */     if (checkValue(value)) {
/* 108 */       sendEvent(macros, eventArgs);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEvent() {
/* 117 */     return this.eventName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendEvent(Macros macros, String... eventArgs) {
/* 128 */     macros.sendEvent(this.eventName, this.eventPriority, eventArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getLastValue() {
/* 137 */     return this.lastValue;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\event\MacroEventValueWatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */