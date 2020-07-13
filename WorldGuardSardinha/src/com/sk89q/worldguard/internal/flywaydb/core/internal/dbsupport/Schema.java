/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.FlywayException;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.jdbc.JdbcUtils;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public abstract class Schema
/*     */ {
/*     */   protected final JdbcTemplate jdbcTemplate;
/*     */   protected final DbSupport dbSupport;
/*     */   protected final String name;
/*     */   
/*     */   public Schema(JdbcTemplate jdbcTemplate, DbSupport dbSupport, String name) {
/*  53 */     this.jdbcTemplate = jdbcTemplate;
/*  54 */     this.dbSupport = dbSupport;
/*  55 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  62 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean exists() {
/*     */     try {
/*  72 */       return doExists();
/*  73 */     } catch (SQLException e) {
/*  74 */       throw new FlywayException("Unable to check whether schema " + this + " exists", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean doExists() throws SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean empty() {
/*     */     try {
/*  93 */       return doEmpty();
/*  94 */     } catch (SQLException e) {
/*  95 */       throw new FlywayException("Unable to check whether schema " + this + " is empty", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean doEmpty() throws SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void create() {
/*     */     try {
/* 112 */       doCreate();
/* 113 */     } catch (SQLException e) {
/* 114 */       throw new FlywayException("Unable to create schema " + this, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void doCreate() throws SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drop() {
/*     */     try {
/* 130 */       doDrop();
/* 131 */     } catch (SQLException e) {
/* 132 */       throw new FlywayException("Unable to drop schema " + this, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void doDrop() throws SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clean() {
/*     */     try {
/* 148 */       doClean();
/* 149 */     } catch (SQLException e) {
/* 150 */       throw new FlywayException("Unable to clean schema " + this, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void doClean() throws SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Table[] allTables() {
/*     */     try {
/* 168 */       return doAllTables();
/* 169 */     } catch (SQLException e) {
/* 170 */       throw new FlywayException("Unable to retrieve all tables in schema " + this, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Table[] doAllTables() throws SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Type[] allTypes() {
/* 188 */     ResultSet resultSet = null;
/*     */     try {
/* 190 */       resultSet = this.jdbcTemplate.getMetaData().getUDTs(null, this.name, null, null);
/*     */       
/* 192 */       List<Type> types = new ArrayList<Type>();
/* 193 */       while (resultSet.next()) {
/* 194 */         types.add(getType(resultSet.getString("TYPE_NAME")));
/*     */       }
/*     */       
/* 197 */       return types.<Type>toArray(new Type[types.size()]);
/* 198 */     } catch (SQLException e) {
/* 199 */       throw new FlywayException("Unable to retrieve all types in schema " + this, e);
/*     */     } finally {
/* 201 */       JdbcUtils.closeResultSet(resultSet);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Type getType(String typeName) {
/* 212 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Table getTable(String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Function getFunction(String functionName, String... args) {
/* 230 */     throw new UnsupportedOperationException("getFunction()");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Function[] allFunctions() {
/*     */     try {
/* 240 */       return doAllFunctions();
/* 241 */     } catch (SQLException e) {
/* 242 */       throw new FlywayException("Unable to retrieve all functions in schema " + this, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Function[] doAllFunctions() throws SQLException {
/* 253 */     return new Function[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 258 */     return this.dbSupport.quote(new String[] { this.name });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 263 */     if (this == o) return true; 
/* 264 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 266 */     Schema schema = (Schema)o;
/* 267 */     return this.name.equals(schema.name);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 272 */     return this.name.hashCode();
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\Schema.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */