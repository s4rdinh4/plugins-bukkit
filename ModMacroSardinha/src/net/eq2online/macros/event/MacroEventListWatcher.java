/*    */ package net.eq2online.macros.event;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import net.eq2online.console.Log;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MacroEventListWatcher<T extends Collection<L>, L>
/*    */   extends MacroEventValueWatcher<T>
/*    */ {
/*    */   protected T myList;
/*    */   protected L newObject;
/*    */   
/*    */   public MacroEventListWatcher(String eventName, int eventPriority, boolean suppressInitial) {
/* 23 */     super(eventName, eventPriority, suppressInitial);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean checkValue(T value) {
/* 33 */     if (value != null && this.myList == null) {
/*    */       
/*    */       try {
/*    */         
/* 37 */         this.myList = (T)new ArrayList();
/*    */       }
/* 39 */       catch (Exception ex) {
/*    */         
/* 41 */         Log.printStackTrace(ex);
/*    */       } 
/*    */     }
/*    */     
/* 45 */     if (value != null && this.myList != null) {
/*    */       
/* 47 */       boolean result = false;
/*    */       
/* 49 */       Iterator<L> valueIterator = value.iterator();
/*    */       
/* 51 */       while (valueIterator.hasNext()) {
/*    */         
/* 53 */         L obj = valueIterator.next();
/*    */         
/* 55 */         if (!this.myList.contains(obj)) {
/*    */           
/* 57 */           result = true;
/* 58 */           this.newObject = obj;
/* 59 */           this.myList.add(obj);
/*    */           
/*    */           break;
/*    */         } 
/*    */       } 
/* 64 */       Iterator<L> listIterator = this.myList.iterator();
/*    */       
/* 66 */       while (listIterator.hasNext()) {
/*    */         
/* 68 */         L obj = listIterator.next();
/*    */         
/* 70 */         if (!value.contains(obj)) {
/* 71 */           listIterator.remove();
/*    */         }
/*    */       } 
/* 74 */       if (!this.suppressNext) {
/* 75 */         return result;
/*    */       }
/* 77 */       this.suppressNext = false;
/*    */     } 
/*    */     
/* 80 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void reset() {
/* 85 */     this.myList = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public L getNewObject() {
/* 90 */     return this.newObject;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\event\MacroEventListWatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */