/*     */ package com.sk89q.worldguard.internal.flywaydb.core.internal.util.jdbc;
/*     */ 
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.api.FlywayException;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.ClassUtils;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.FeatureDetector;
/*     */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.StringUtils;
/*     */ import java.io.PrintWriter;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.sql.Connection;
/*     */ import java.sql.Driver;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.Properties;
/*     */ import java.util.logging.Logger;
/*     */ import javax.sql.DataSource;
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
/*     */ public class DriverDataSource
/*     */   implements DataSource
/*     */ {
/*     */   private final Driver driver;
/*     */   private final String url;
/*     */   private final String user;
/*     */   private final String password;
/*     */   private final String[] initSqls;
/*     */   private final ClassLoader classLoader;
/*     */   private boolean singleConnectionMode;
/*     */   private Connection singleConnection;
/*     */   
/*     */   public DriverDataSource(ClassLoader classLoader, String driverClass, String url, String user, String password, String... initSqls) throws FlywayException {
/*  91 */     if (!StringUtils.hasText(url)) {
/*  92 */       throw new FlywayException("Missing required JDBC URL. Unable to create DataSource!");
/*     */     }
/*  94 */     if (!url.toLowerCase().startsWith("jdbc:")) {
/*  95 */       throw new FlywayException("Invalid JDBC URL (should start with jdbc:) : " + url);
/*     */     }
/*  97 */     this.classLoader = classLoader;
/*  98 */     this.url = url;
/*     */     
/* 100 */     if (!StringUtils.hasLength(driverClass)) {
/* 101 */       driverClass = detectDriverForUrl(url);
/* 102 */       if (!StringUtils.hasLength(driverClass)) {
/* 103 */         throw new FlywayException("Unable to autodetect Jdbc driver for url: " + url);
/*     */       }
/*     */     } 
/*     */     
/*     */     try {
/* 108 */       this.driver = (Driver)ClassUtils.instantiate(driverClass, classLoader);
/* 109 */     } catch (Exception e) {
/* 110 */       throw new FlywayException("Unable to instantiate jdbc driver: " + driverClass, e);
/*     */     } 
/*     */     
/* 113 */     this.user = user;
/* 114 */     this.password = password;
/* 115 */     this.initSqls = initSqls;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String detectDriverForUrl(String url) {
/* 125 */     if (url.startsWith("jdbc:db2:")) {
/* 126 */       return "com.ibm.db2.jcc.DB2Driver";
/*     */     }
/*     */     
/* 129 */     if (url.startsWith("jdbc:derby:")) {
/* 130 */       return "org.apache.derby.jdbc.EmbeddedDriver";
/*     */     }
/*     */     
/* 133 */     if (url.startsWith("jdbc:h2:")) {
/* 134 */       return "org.h2.Driver";
/*     */     }
/*     */     
/* 137 */     if (url.startsWith("jdbc:hsqldb:")) {
/* 138 */       return "org.hsqldb.jdbcDriver";
/*     */     }
/*     */     
/* 141 */     if (url.startsWith("jdbc:sqlite:")) {
/* 142 */       this.singleConnectionMode = true;
/* 143 */       if ((new FeatureDetector(this.classLoader)).isAndroidAvailable()) {
/* 144 */         return "org.sqldroid.SQLDroidDriver";
/*     */       }
/* 146 */       return "org.sqlite.JDBC";
/*     */     } 
/*     */     
/* 149 */     if (url.startsWith("jdbc:sqldroid:")) {
/* 150 */       return "org.sqldroid.SQLDroidDriver";
/*     */     }
/*     */     
/* 153 */     if (url.startsWith("jdbc:mysql:")) {
/* 154 */       return "com.mysql.jdbc.Driver";
/*     */     }
/*     */     
/* 157 */     if (url.startsWith("jdbc:mariadb:")) {
/* 158 */       return "org.mariadb.jdbc.Driver";
/*     */     }
/*     */     
/* 161 */     if (url.startsWith("jdbc:google:")) {
/* 162 */       return "com.google.appengine.api.rdbms.AppEngineDriver";
/*     */     }
/*     */     
/* 165 */     if (url.startsWith("jdbc:oracle:")) {
/* 166 */       return "oracle.jdbc.OracleDriver";
/*     */     }
/*     */     
/* 169 */     if (url.startsWith("jdbc:postgresql:")) {
/* 170 */       return "org.postgresql.Driver";
/*     */     }
/*     */     
/* 173 */     if (url.startsWith("jdbc:jtds:")) {
/* 174 */       return "net.sourceforge.jtds.jdbc.Driver";
/*     */     }
/*     */     
/* 177 */     if (url.startsWith("jdbc:sqlserver:")) {
/* 178 */       return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
/*     */     }
/*     */     
/* 181 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Driver getDriver() {
/* 188 */     return this.driver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUrl() {
/* 195 */     return this.url;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUser() {
/* 202 */     return this.user;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPassword() {
/* 209 */     return this.password;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getInitSqls() {
/* 216 */     return this.initSqls;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection getConnection() throws SQLException {
/* 226 */     return getConnectionFromDriver(getUser(), getPassword());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection getConnection(String username, String password) throws SQLException {
/* 236 */     return getConnectionFromDriver(username, password);
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
/*     */ 
/*     */   
/*     */   protected Connection getConnectionFromDriver(String username, String password) throws SQLException {
/*     */     Connection connection;
/* 251 */     if (this.singleConnectionMode && this.singleConnection != null) {
/* 252 */       return this.singleConnection;
/*     */     }
/*     */     
/* 255 */     Properties props = new Properties();
/* 256 */     if (username != null) {
/* 257 */       props.setProperty("user", username);
/*     */     }
/* 259 */     if (password != null) {
/* 260 */       props.setProperty("password", password);
/*     */     }
/*     */     
/*     */     try {
/* 264 */       connection = this.driver.connect(this.url, props);
/* 265 */     } catch (SQLException e) {
/* 266 */       throw new FlywayException("Unable to obtain Jdbc connection from DataSource (" + this.url + ") for user '" + this.user + "'", e);
/*     */     } 
/*     */ 
/*     */     
/* 270 */     for (String initSql : this.initSqls) {
/* 271 */       Statement statement = null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 280 */     if (this.singleConnectionMode) {
/* 281 */       InvocationHandler suppressCloseHandler = new SuppressCloseHandler(connection);
/* 282 */       this.singleConnection = (Connection)Proxy.newProxyInstance(this.classLoader, new Class[] { Connection.class }, suppressCloseHandler);
/*     */     } 
/*     */ 
/*     */     
/* 286 */     return connection;
/*     */   }
/*     */   
/*     */   public int getLoginTimeout() throws SQLException {
/* 290 */     return 0;
/*     */   }
/*     */   
/*     */   public void setLoginTimeout(int timeout) throws SQLException {
/* 294 */     throw new UnsupportedOperationException("setLoginTimeout");
/*     */   }
/*     */   
/*     */   public PrintWriter getLogWriter() {
/* 298 */     throw new UnsupportedOperationException("getLogWriter");
/*     */   }
/*     */   
/*     */   public void setLogWriter(PrintWriter pw) throws SQLException {
/* 302 */     throw new UnsupportedOperationException("setLogWriter");
/*     */   }
/*     */   
/*     */   public <T> T unwrap(Class<T> iface) throws SQLException {
/* 306 */     throw new UnsupportedOperationException("unwrap");
/*     */   }
/*     */   
/*     */   public boolean isWrapperFor(Class<?> iface) throws SQLException {
/* 310 */     return DataSource.class.equals(iface);
/*     */   }
/*     */   
/*     */   public Logger getParentLogger() {
/* 314 */     throw new UnsupportedOperationException("getParentLogger");
/*     */   }
/*     */   
/*     */   private static class SuppressCloseHandler implements InvocationHandler {
/*     */     private final Connection connection;
/*     */     
/*     */     public SuppressCloseHandler(Connection connection) {
/* 321 */       this.connection = connection;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/* 326 */       if (!"close".equals(method.getName())) {
/* 327 */         return method.invoke(this.connection, args);
/*     */       }
/*     */       
/* 330 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 338 */     JdbcUtils.closeConnection(this.singleConnection);
/* 339 */     this.singleConnection = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\interna\\util\jdbc\DriverDataSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */