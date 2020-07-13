/*    */ package com.sk89q.worldguard.blacklist;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.google.common.collect.HashBasedTable;
/*    */ import com.google.common.collect.Table;
/*    */ import com.sk89q.worldguard.blacklist.target.Target;
/*    */ import com.sk89q.worldguard.blacklist.target.TargetMatcher;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
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
/*    */ class MatcherIndex
/*    */ {
/* 35 */   private static final MatcherIndex EMPTY_INSTANCE = new MatcherIndex((Table<Integer, TargetMatcher, BlacklistEntry>)HashBasedTable.create());
/*    */   private final Table<Integer, TargetMatcher, BlacklistEntry> entries;
/*    */   
/*    */   private MatcherIndex(Table<Integer, TargetMatcher, BlacklistEntry> entries) {
/* 39 */     Preconditions.checkNotNull(entries);
/* 40 */     this.entries = entries;
/*    */   }
/*    */   
/*    */   public List<BlacklistEntry> getEntries(Target target) {
/* 44 */     List<BlacklistEntry> found = new ArrayList<BlacklistEntry>();
/* 45 */     for (Map.Entry<TargetMatcher, BlacklistEntry> entry : (Iterable<Map.Entry<TargetMatcher, BlacklistEntry>>)this.entries.row(Integer.valueOf(target.getTypeId())).entrySet()) {
/* 46 */       if (((TargetMatcher)entry.getKey()).test(target)) {
/* 47 */         found.add(entry.getValue());
/*    */       }
/*    */     } 
/* 50 */     return found;
/*    */   }
/*    */   
/*    */   public int size() {
/* 54 */     return this.entries.size();
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 58 */     return this.entries.isEmpty();
/*    */   }
/*    */   
/*    */   public static MatcherIndex getEmptyInstance() {
/* 62 */     return EMPTY_INSTANCE;
/*    */   }
/*    */   
/*    */   public static class Builder {
/* 66 */     private final Table<Integer, TargetMatcher, BlacklistEntry> entries = (Table<Integer, TargetMatcher, BlacklistEntry>)HashBasedTable.create();
/*    */     
/*    */     public Builder add(TargetMatcher matcher, BlacklistEntry entry) {
/* 69 */       Preconditions.checkNotNull(matcher);
/* 70 */       Preconditions.checkNotNull(this.entries);
/* 71 */       this.entries.put(Integer.valueOf(matcher.getMatchedTypeId()), matcher, entry);
/* 72 */       return this;
/*    */     }
/*    */     
/*    */     public MatcherIndex build() {
/* 76 */       return new MatcherIndex(this.entries);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\MatcherIndex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */