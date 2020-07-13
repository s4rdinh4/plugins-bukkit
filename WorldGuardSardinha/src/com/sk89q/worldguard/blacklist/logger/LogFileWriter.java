/*    */ package com.sk89q.worldguard.blacklist.logger;
/*    */ 
/*    */ import java.io.BufferedWriter;
/*    */ import javax.annotation.Nullable;
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
/*    */ public class LogFileWriter
/*    */   implements Comparable<LogFileWriter>
/*    */ {
/*    */   public String path;
/*    */   private BufferedWriter writer;
/*    */   private long lastUse;
/*    */   
/*    */   public LogFileWriter(String path, BufferedWriter writer) {
/* 38 */     this.path = path;
/* 39 */     this.writer = writer;
/* 40 */     this.lastUse = System.currentTimeMillis();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPath() {
/* 49 */     return this.path;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BufferedWriter getWriter() {
/* 56 */     return this.writer;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getLastUse() {
/* 63 */     return this.lastUse;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateLastUse() {
/* 70 */     this.lastUse = System.currentTimeMillis();
/*    */   }
/*    */ 
/*    */   
/*    */   public int compareTo(@Nullable LogFileWriter other) {
/* 75 */     if (other == null)
/* 76 */       return 1; 
/* 77 */     if (this.lastUse > other.lastUse)
/* 78 */       return 1; 
/* 79 */     if (this.lastUse < other.lastUse) {
/* 80 */       return -1;
/*    */     }
/* 82 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\logger\LogFileWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */