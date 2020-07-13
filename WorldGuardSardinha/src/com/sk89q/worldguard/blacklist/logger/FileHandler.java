/*     */ package com.sk89q.worldguard.blacklist.logger;
/*     */ 
/*     */ import com.sk89q.worldedit.Vector;
/*     */ import com.sk89q.worldedit.blocks.ItemType;
/*     */ import com.sk89q.worldguard.LocalPlayer;
/*     */ import com.sk89q.worldguard.blacklist.event.BlacklistEvent;
/*     */ import com.sk89q.worldguard.blacklist.target.Target;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class FileHandler
/*     */   implements LoggerHandler
/*     */ {
/*  46 */   private static Pattern pattern = Pattern.compile("%.");
/*  47 */   private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */   
/*  49 */   private int cacheSize = 10;
/*     */   private String pathPattern;
/*     */   private String worldName;
/*  52 */   private TreeMap<String, LogFileWriter> writers = new TreeMap<String, LogFileWriter>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Logger logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileHandler(String pathPattern, String worldName, Logger logger) {
/*  64 */     this.pathPattern = pathPattern;
/*  65 */     this.worldName = worldName;
/*  66 */     this.logger = logger;
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
/*     */   public FileHandler(String pathPattern, int cacheSize, String worldName, Logger logger) {
/*  78 */     if (cacheSize < 1) {
/*  79 */       throw new IllegalArgumentException("Cache size cannot be less than 1");
/*     */     }
/*  81 */     this.pathPattern = pathPattern;
/*  82 */     this.cacheSize = cacheSize;
/*  83 */     this.worldName = worldName;
/*  84 */     this.logger = logger;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String buildPath(String playerName) {
/*  94 */     GregorianCalendar calendar = new GregorianCalendar();
/*     */     
/*  96 */     Matcher m = pattern.matcher(this.pathPattern);
/*  97 */     StringBuffer buffer = new StringBuffer();
/*     */ 
/*     */     
/* 100 */     while (m.find()) {
/* 101 */       String group = m.group();
/* 102 */       String rep = "?";
/*     */       
/* 104 */       if (group.matches("%%")) {
/* 105 */         rep = "%";
/* 106 */       } else if (group.matches("%u")) {
/* 107 */         rep = playerName.toLowerCase().replaceAll("[^A-Za-z0-9_]", "_");
/* 108 */         if (rep.length() > 32) {
/* 109 */           rep = rep.substring(0, 32);
/*     */         }
/*     */       }
/* 112 */       else if (group.matches("%w")) {
/* 113 */         rep = this.worldName.toLowerCase().replaceAll("[^A-Za-z0-9_]", "_");
/* 114 */         if (rep.length() > 32) {
/* 115 */           rep = rep.substring(0, 32);
/*     */         
/*     */         }
/*     */       }
/* 119 */       else if (group.matches("%Y")) {
/* 120 */         rep = String.valueOf(calendar.get(1));
/* 121 */       } else if (group.matches("%m")) {
/* 122 */         rep = String.format("%02d", new Object[] { Integer.valueOf(calendar.get(2)) });
/* 123 */       } else if (group.matches("%d")) {
/* 124 */         rep = String.format("%02d", new Object[] { Integer.valueOf(calendar.get(5)) });
/* 125 */       } else if (group.matches("%W")) {
/* 126 */         rep = String.format("%02d", new Object[] { Integer.valueOf(calendar.get(3)) });
/* 127 */       } else if (group.matches("%H")) {
/* 128 */         rep = String.format("%02d", new Object[] { Integer.valueOf(calendar.get(11)) });
/* 129 */       } else if (group.matches("%h")) {
/* 130 */         rep = String.format("%02d", new Object[] { Integer.valueOf(calendar.get(10)) });
/* 131 */       } else if (group.matches("%i")) {
/* 132 */         rep = String.format("%02d", new Object[] { Integer.valueOf(calendar.get(12)) });
/* 133 */       } else if (group.matches("%s")) {
/* 134 */         rep = String.format("%02d", new Object[] { Integer.valueOf(calendar.get(13)) });
/*     */       } 
/*     */       
/* 137 */       m.appendReplacement(buffer, rep);
/*     */     } 
/*     */     
/* 140 */     m.appendTail(buffer);
/*     */     
/* 142 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void log(LocalPlayer player, String message, String comment) {
/* 153 */     String path = buildPath(player.getName());
/*     */     try {
/* 155 */       String date = dateFormat.format(new Date());
/* 156 */       String line = "[" + date + "] " + player.getName() + ": " + message + ((comment != null) ? (" (" + comment + ")") : "") + "\r\n";
/*     */ 
/*     */       
/* 159 */       LogFileWriter writer = this.writers.get(path);
/*     */ 
/*     */       
/* 162 */       if (writer != null) {
/*     */         try {
/* 164 */           BufferedWriter bufferedWriter = writer.getWriter();
/* 165 */           bufferedWriter.write(line);
/* 166 */           bufferedWriter.flush();
/* 167 */           writer.updateLastUse();
/*     */           return;
/* 169 */         } catch (IOException e) {}
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 175 */       File file = new File(path);
/* 176 */       File parent = file.getParentFile();
/* 177 */       if (parent != null && !parent.exists()) {
/* 178 */         parent.mkdirs();
/*     */       }
/*     */       
/* 181 */       FileWriter stream = new FileWriter(path, true);
/* 182 */       BufferedWriter out = new BufferedWriter(stream);
/* 183 */       out.write(line);
/* 184 */       out.flush();
/* 185 */       writer = new LogFileWriter(path, out);
/* 186 */       this.writers.put(path, writer);
/*     */ 
/*     */       
/* 189 */       if (this.writers.size() > this.cacheSize) {
/*     */         
/* 191 */         Iterator<Map.Entry<String, LogFileWriter>> it = this.writers.entrySet().iterator();
/*     */ 
/*     */         
/* 194 */         while (it.hasNext()) {
/* 195 */           Map.Entry<String, LogFileWriter> entry = it.next();
/*     */           try {
/* 197 */             ((LogFileWriter)entry.getValue()).getWriter().close();
/* 198 */           } catch (IOException ignore) {}
/*     */           
/* 200 */           it.remove();
/*     */ 
/*     */           
/* 203 */           if (this.writers.size() <= this.cacheSize) {
/*     */             break;
/*     */           }
/*     */         }
/*     */       
/*     */       } 
/* 209 */     } catch (IOException e) {
/* 210 */       this.logger.log(Level.WARNING, "Failed to log blacklist event to '" + path + "': " + e
/* 211 */           .getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getCoordinates(Vector pos) {
/* 222 */     return "@" + pos.getBlockX() + "," + pos.getBlockY() + "," + pos.getBlockZ();
/*     */   }
/*     */   
/*     */   private void logEvent(BlacklistEvent event, String text, Target target, Vector pos, String comment) {
/* 226 */     log(event.getPlayer(), "Tried to " + text + " " + target.getFriendlyName() + " " + getCoordinates(pos), comment);
/*     */   }
/*     */ 
/*     */   
/*     */   public void logEvent(BlacklistEvent event, String comment) {
/* 231 */     logEvent(event, event.getDescription(), event.getTarget(), event.getPosition(), comment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getFriendlyItemName(int id) {
/* 241 */     ItemType type = ItemType.fromID(id);
/* 242 */     if (type != null) {
/* 243 */       return type.getName() + " (#" + id + ")";
/*     */     }
/* 245 */     return "#" + id + "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 251 */     for (Map.Entry<String, LogFileWriter> entry : this.writers.entrySet()) {
/*     */       try {
/* 253 */         ((LogFileWriter)entry.getValue()).getWriter().close();
/* 254 */       } catch (IOException ignore) {}
/*     */     } 
/*     */ 
/*     */     
/* 258 */     this.writers.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\logger\FileHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */