/*    */ package com.sk89q.worldguard.util.report;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Modifier;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
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
/*    */ public class ShallowObjectReport
/*    */   extends DataReport
/*    */ {
/* 31 */   private static final Logger log = Logger.getLogger(ShallowObjectReport.class.getCanonicalName());
/*    */   
/*    */   public ShallowObjectReport(String title, Object object) {
/* 34 */     super(title);
/* 35 */     Preconditions.checkNotNull(object, "object");
/*    */     
/* 37 */     Class<?> type = object.getClass();
/*    */     
/* 39 */     for (Field field : type.getDeclaredFields()) {
/* 40 */       if (!Modifier.isStatic(field.getModifiers()))
/*    */       {
/*    */ 
/*    */         
/* 44 */         if (field.getAnnotation(Unreported.class) == null) {
/*    */ 
/*    */ 
/*    */           
/* 48 */           field.setAccessible(true);
/*    */           try {
/* 50 */             Object value = field.get(object);
/* 51 */             append(field.getName(), String.valueOf(value));
/* 52 */           } catch (IllegalAccessException e) {
/* 53 */             log.log(Level.WARNING, "Failed to get value of '" + field.getName() + "' on " + type);
/*    */           } 
/*    */         } 
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\report\ShallowObjectReport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */