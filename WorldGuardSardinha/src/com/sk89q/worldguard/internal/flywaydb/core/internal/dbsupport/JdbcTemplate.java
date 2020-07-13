/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.jdbc.JdbcUtils;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.jdbc.RowMapper;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.Log;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.LogFactory;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLWarning;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class JdbcTemplate
/*     */ {
/*  39 */   private static final Log LOG = LogFactory.getLog(JdbcTemplate.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Connection connection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int nullType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JdbcTemplate(Connection connection, int nullType) {
/*  58 */     this.connection = connection;
/*  59 */     this.nullType = nullType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection getConnection() {
/*  66 */     return this.connection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Map<String, String>> queryForList(String query, String... params) throws SQLException {
/*     */     List<Map<String, String>> result;
/*  78 */     PreparedStatement statement = null;
/*  79 */     ResultSet resultSet = null;
/*     */ 
/*     */     
/*     */     try {
/*  83 */       statement = this.connection.prepareStatement(query);
/*  84 */       for (int i = 0; i < params.length; i++) {
/*  85 */         statement.setString(i + 1, params[i]);
/*     */       }
/*  87 */       resultSet = statement.executeQuery();
/*     */       
/*  89 */       result = new ArrayList<Map<String, String>>();
/*  90 */       while (resultSet.next()) {
/*  91 */         Map<String, String> rowMap = new HashMap<String, String>();
/*  92 */         for (int j = 1; j <= resultSet.getMetaData().getColumnCount(); j++) {
/*  93 */           rowMap.put(resultSet.getMetaData().getColumnLabel(j), resultSet.getString(j));
/*     */         }
/*  95 */         result.add(rowMap);
/*     */       } 
/*     */     } finally {
/*  98 */       JdbcUtils.closeResultSet(resultSet);
/*  99 */       JdbcUtils.closeStatement(statement);
/*     */     } 
/*     */ 
/*     */     
/* 103 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> queryForStringList(String query, String... params) throws SQLException {
/*     */     List<String> result;
/* 115 */     PreparedStatement statement = null;
/* 116 */     ResultSet resultSet = null;
/*     */ 
/*     */     
/*     */     try {
/* 120 */       statement = this.connection.prepareStatement(query);
/* 121 */       for (int i = 0; i < params.length; i++) {
/* 122 */         statement.setString(i + 1, params[i]);
/*     */       }
/* 124 */       resultSet = statement.executeQuery();
/*     */       
/* 126 */       result = new ArrayList<String>();
/* 127 */       while (resultSet.next()) {
/* 128 */         result.add(resultSet.getString(1));
/*     */       }
/*     */     } finally {
/* 131 */       JdbcUtils.closeResultSet(resultSet);
/* 132 */       JdbcUtils.closeStatement(statement);
/*     */     } 
/*     */     
/* 135 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int queryForInt(String query, String... params) throws SQLException {
/*     */     int result;
/* 147 */     PreparedStatement statement = null;
/* 148 */     ResultSet resultSet = null;
/*     */ 
/*     */     
/*     */     try {
/* 152 */       statement = this.connection.prepareStatement(query);
/* 153 */       for (int i = 0; i < params.length; i++) {
/* 154 */         statement.setString(i + 1, params[i]);
/*     */       }
/* 156 */       resultSet = statement.executeQuery();
/* 157 */       resultSet.next();
/* 158 */       result = resultSet.getInt(1);
/*     */     } finally {
/* 160 */       JdbcUtils.closeResultSet(resultSet);
/* 161 */       JdbcUtils.closeStatement(statement);
/*     */     } 
/*     */     
/* 164 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String queryForString(String query, String... params) throws SQLException {
/*     */     String result;
/* 176 */     PreparedStatement statement = null;
/* 177 */     ResultSet resultSet = null;
/*     */ 
/*     */     
/*     */     try {
/* 181 */       statement = this.connection.prepareStatement(query);
/* 182 */       for (int i = 0; i < params.length; i++) {
/* 183 */         statement.setString(i + 1, params[i]);
/*     */       }
/* 185 */       resultSet = statement.executeQuery();
/* 186 */       result = null;
/* 187 */       if (resultSet.next()) {
/* 188 */         result = resultSet.getString(1);
/*     */       }
/*     */     } finally {
/* 191 */       JdbcUtils.closeResultSet(resultSet);
/* 192 */       JdbcUtils.closeStatement(statement);
/*     */     } 
/*     */     
/* 195 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DatabaseMetaData getMetaData() throws SQLException {
/* 205 */     return this.connection.getMetaData();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(String sql, Object... params) throws SQLException {
/* 216 */     PreparedStatement statement = null;
/*     */     try {
/* 218 */       statement = prepareStatement(sql, params);
/* 219 */       statement.execute();
/*     */     } finally {
/* 221 */       JdbcUtils.closeStatement(statement);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void executeStatement(String sql) throws SQLException {
/* 232 */     Statement statement = null;
/*     */     try {
/* 234 */       statement = this.connection.createStatement();
/* 235 */       statement.execute(sql);
/* 236 */       SQLWarning warning = statement.getWarnings();
/* 237 */       while (warning != null) {
/* 238 */         LOG.warn(warning.getMessage() + " (SQL State: " + warning.getSQLState() + " - Error Code: " + warning.getErrorCode() + ")");
/*     */         
/* 240 */         warning = warning.getNextWarning();
/*     */       } 
/*     */     } finally {
/* 243 */       JdbcUtils.closeStatement(statement);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(String sql, Object... params) throws SQLException {
/* 255 */     PreparedStatement statement = null;
/*     */     try {
/* 257 */       statement = prepareStatement(sql, params);
/* 258 */       statement.executeUpdate();
/*     */     } finally {
/* 260 */       JdbcUtils.closeStatement(statement);
/*     */     } 
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
/*     */   private PreparedStatement prepareStatement(String sql, Object[] params) throws SQLException {
/* 273 */     PreparedStatement statement = this.connection.prepareStatement(sql);
/* 274 */     for (int i = 0; i < params.length; i++) {
/* 275 */       if (params[i] == null) {
/* 276 */         statement.setNull(i + 1, this.nullType);
/* 277 */       } else if (params[i] instanceof Integer) {
/* 278 */         statement.setInt(i + 1, ((Integer)params[i]).intValue());
/* 279 */       } else if (params[i] instanceof Boolean) {
/* 280 */         statement.setBoolean(i + 1, ((Boolean)params[i]).booleanValue());
/*     */       } else {
/* 282 */         statement.setString(i + 1, (String)params[i]);
/*     */       } 
/*     */     } 
/* 285 */     return statement;
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
/*     */   public <T> List<T> query(String query, RowMapper<T> rowMapper) throws SQLException {
/*     */     List<T> results;
/* 298 */     Statement statement = null;
/* 299 */     ResultSet resultSet = null;
/*     */ 
/*     */     
/*     */     try {
/* 303 */       statement = this.connection.createStatement();
/* 304 */       resultSet = statement.executeQuery(query);
/*     */       
/* 306 */       results = new ArrayList<T>();
/* 307 */       while (resultSet.next()) {
/* 308 */         results.add((T)rowMapper.mapRow(resultSet));
/*     */       }
/*     */     } finally {
/* 311 */       JdbcUtils.closeResultSet(resultSet);
/* 312 */       JdbcUtils.closeStatement(statement);
/*     */     } 
/*     */     
/* 315 */     return results;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\JdbcTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */