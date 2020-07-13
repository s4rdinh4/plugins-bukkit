/*     */ package com.sk89q.worldguard.protection.managers.storage.sql;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.sk89q.worldguard.internal.util.sql.StatementUtils;
/*     */ import com.sk89q.worldguard.util.io.Closer;
/*     */ import com.sk89q.worldguard.util.sql.DataSourceConfig;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Logger;
/*     */ import javax.annotation.Nullable;
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
/*     */ abstract class TableCache<V>
/*     */ {
/*  51 */   private static final Logger log = Logger.getLogger(TableCache.class.getCanonicalName());
/*     */   
/*     */   private static final int MAX_NUMBER_PER_QUERY = 100;
/*  54 */   private static final Object LOCK = new Object();
/*     */   
/*  56 */   private final Map<V, Integer> cache = new HashMap<V, Integer>();
/*     */ 
/*     */   
/*     */   private final DataSourceConfig config;
/*     */ 
/*     */   
/*     */   private final Connection conn;
/*     */ 
/*     */   
/*     */   private final String tableName;
/*     */   
/*     */   private final String fieldName;
/*     */ 
/*     */   
/*     */   protected TableCache(DataSourceConfig config, Connection conn, String tableName, String fieldName) {
/*  71 */     this.config = config;
/*  72 */     this.conn = conn;
/*  73 */     this.tableName = tableName;
/*  74 */     this.fieldName = fieldName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract String fromType(V paramV);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract V toType(String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract V toKey(V paramV);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Integer find(V object) {
/* 103 */     return this.cache.get(object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fetch(Collection<V> entries) throws SQLException {
/* 114 */     synchronized (LOCK) {
/* 115 */       Preconditions.checkNotNull(entries);
/*     */ 
/*     */       
/* 118 */       List<V> fetchList = new ArrayList<V>();
/* 119 */       for (V entry : entries) {
/* 120 */         if (!this.cache.containsKey(toKey(entry))) {
/* 121 */           fetchList.add(entry);
/*     */         }
/*     */       } 
/*     */       
/* 125 */       if (fetchList.isEmpty()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 130 */       for (List<V> partition : (Iterable<List<V>>)Lists.partition(fetchList, 100)) {
/* 131 */         Closer closer = Closer.create();
/*     */         try {
/* 133 */           PreparedStatement statement = (PreparedStatement)closer.register(this.conn.prepareStatement(
/* 134 */                 String.format("SELECT id, " + this.fieldName + " " + "FROM `" + this.config
/*     */                   
/* 136 */                   .getTablePrefix() + this.tableName + "` " + "WHERE " + this.fieldName + " IN (%s)", new Object[] {
/*     */                     
/* 138 */                     StatementUtils.preparePlaceHolders(partition.size())
/*     */                   })));
/* 140 */           String[] values = new String[partition.size()];
/* 141 */           int i = 0;
/* 142 */           for (V entry : partition) {
/* 143 */             values[i] = fromType(entry);
/* 144 */             i++;
/*     */           } 
/*     */           
/* 147 */           StatementUtils.setValues(statement, values);
/* 148 */           ResultSet results = closer.register(statement.executeQuery());
/* 149 */           while (results.next()) {
/* 150 */             this.cache.put(toKey(toType(results.getString(this.fieldName))), Integer.valueOf(results.getInt("id")));
/*     */           }
/*     */         } finally {
/* 153 */           closer.closeQuietly();
/*     */         } 
/*     */       } 
/*     */       
/* 157 */       List<V> missing = new ArrayList<V>();
/* 158 */       for (V entry : fetchList) {
/* 159 */         if (!this.cache.containsKey(toKey(entry))) {
/* 160 */           missing.add(entry);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 165 */       if (!missing.isEmpty()) {
/* 166 */         Closer closer = Closer.create();
/*     */         try {
/* 168 */           PreparedStatement statement = (PreparedStatement)closer.register(this.conn.prepareStatement("INSERT INTO `" + this.config
/* 169 */                 .getTablePrefix() + this.tableName + "` (id, " + this.fieldName + ") VALUES (null, ?)", 1));
/*     */ 
/*     */           
/* 172 */           for (V entry : missing) {
/* 173 */             statement.setString(1, fromType(entry));
/* 174 */             statement.execute();
/*     */             
/* 176 */             ResultSet generatedKeys = statement.getGeneratedKeys();
/* 177 */             if (generatedKeys.next()) {
/* 178 */               this.cache.put(toKey(entry), Integer.valueOf(generatedKeys.getInt(1))); continue;
/*     */             } 
/* 180 */             log.warning("Could not get the database ID for entry " + entry);
/*     */           } 
/*     */         } finally {
/*     */           
/* 184 */           closer.closeQuietly();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static class UserNameCache
/*     */     extends TableCache<String>
/*     */   {
/*     */     protected UserNameCache(DataSourceConfig config, Connection conn) {
/* 195 */       super(config, conn, "user", "name");
/*     */     }
/*     */ 
/*     */     
/*     */     protected String fromType(String o) {
/* 200 */       return o;
/*     */     }
/*     */ 
/*     */     
/*     */     protected String toType(String o) {
/* 205 */       return o;
/*     */     }
/*     */ 
/*     */     
/*     */     protected String toKey(String object) {
/* 210 */       return object.toLowerCase();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class UserUuidCache
/*     */     extends TableCache<UUID>
/*     */   {
/*     */     protected UserUuidCache(DataSourceConfig config, Connection conn) {
/* 219 */       super(config, conn, "user", "uuid");
/*     */     }
/*     */ 
/*     */     
/*     */     protected String fromType(UUID o) {
/* 224 */       return o.toString();
/*     */     }
/*     */ 
/*     */     
/*     */     protected UUID toType(String o) {
/* 229 */       return UUID.fromString(o);
/*     */     }
/*     */ 
/*     */     
/*     */     protected UUID toKey(UUID object) {
/* 234 */       return object;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class GroupNameCache
/*     */     extends TableCache<String>
/*     */   {
/*     */     protected GroupNameCache(DataSourceConfig config, Connection conn) {
/* 243 */       super(config, conn, "group", "name");
/*     */     }
/*     */ 
/*     */     
/*     */     protected String fromType(String o) {
/* 248 */       return o;
/*     */     }
/*     */ 
/*     */     
/*     */     protected String toType(String o) {
/* 253 */       return o;
/*     */     }
/*     */ 
/*     */     
/*     */     protected String toKey(String object) {
/* 258 */       return object.toLowerCase();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\managers\storage\sql\TableCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */