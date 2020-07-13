/*    */ package net.eq2online.macros.event;
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
/*    */ public class MacroEventQueueEntry
/*    */   implements Comparable<MacroEventQueueEntry>
/*    */ {
/*    */   private static int nextSequenceId;
/*    */   public final String eventName;
/*    */   public final String[] eventArgs;
/*    */   public final int priority;
/*    */   protected final int sequence;
/*    */   
/*    */   public MacroEventQueueEntry(String eventName, int priority, String... eventArgs) {
/* 40 */     this.eventName = eventName;
/* 41 */     this.eventArgs = eventArgs;
/* 42 */     this.priority = Math.min(Math.max(priority, 0), 100);
/* 43 */     this.sequence = nextSequenceId++;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int compareTo(MacroEventQueueEntry other) {
/* 50 */     if (other == null)
/*    */     {
/* 52 */       throw new NullPointerException();
/*    */     }
/*    */     
/* 55 */     if (other.priority < this.priority || other.sequence > this.sequence) return -1; 
/* 56 */     if (other.priority > this.priority || other.sequence < this.sequence) return 1;
/*    */     
/* 58 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\event\MacroEventQueueEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */