/*     */ package com.sk89q.worldguard.blacklist;
/*     */ 
/*     */ import com.sk89q.worldguard.blacklist.action.Action;
/*     */ import com.sk89q.worldguard.blacklist.action.ActionType;
/*     */ import com.sk89q.worldguard.blacklist.event.BlacklistEvent;
/*     */ import com.sk89q.worldguard.blacklist.event.EventType;
/*     */ import com.sk89q.worldguard.blacklist.target.TargetMatcher;
/*     */ import com.sk89q.worldguard.blacklist.target.TargetMatcherParseException;
/*     */ import com.sk89q.worldguard.blacklist.target.TargetMatcherParser;
/*     */ import com.sk89q.worldguard.bukkit.commands.CommandUtils;
/*     */ import com.sk89q.worldguard.internal.guava.cache.CacheBuilder;
/*     */ import com.sk89q.worldguard.internal.guava.cache.CacheLoader;
/*     */ import com.sk89q.worldguard.internal.guava.cache.LoadingCache;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.ChatColor;
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
/*     */ public abstract class Blacklist
/*     */ {
/*  47 */   private static final Logger log = Logger.getLogger(Blacklist.class.getCanonicalName());
/*     */   
/*  49 */   private MatcherIndex index = MatcherIndex.getEmptyInstance();
/*  50 */   private final BlacklistLoggerHandler blacklistLogger = new BlacklistLoggerHandler(); private BlacklistEvent lastEvent;
/*     */   private boolean useAsWhitelist;
/*     */   
/*  53 */   private LoadingCache<String, TrackedEvent> repeatingEventCache = CacheBuilder.newBuilder()
/*  54 */     .maximumSize(1000L)
/*  55 */     .expireAfterAccess(30L, TimeUnit.SECONDS)
/*  56 */     .build(new CacheLoader<String, TrackedEvent>()
/*     */       {
/*     */         public TrackedEvent load(String s) throws Exception {
/*  59 */           return new TrackedEvent();
/*     */         }
/*     */       });
/*     */   
/*     */   public Blacklist(boolean useAsWhitelist) {
/*  64 */     this.useAsWhitelist = useAsWhitelist;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  73 */     return this.index.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemCount() {
/*  82 */     return this.index.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWhitelist() {
/*  91 */     return this.useAsWhitelist;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlacklistLoggerHandler getLogger() {
/* 100 */     return this.blacklistLogger;
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
/*     */   public boolean check(BlacklistEvent event, boolean forceRepeat, boolean silent) {
/* 112 */     List<BlacklistEntry> entries = this.index.getEntries(event.getTarget());
/*     */     
/* 114 */     if (entries == null) {
/* 115 */       return true;
/*     */     }
/*     */     
/* 118 */     boolean ret = true;
/*     */     
/* 120 */     for (BlacklistEntry entry : entries) {
/* 121 */       if (!entry.check(this.useAsWhitelist, event, forceRepeat, silent)) {
/* 122 */         ret = false;
/*     */       }
/*     */     } 
/*     */     
/* 126 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(File file) throws IOException {
/* 136 */     FileReader input = null;
/* 137 */     MatcherIndex.Builder builder = new MatcherIndex.Builder();
/* 138 */     TargetMatcherParser targetMatcherParser = new TargetMatcherParser();
/*     */     
/*     */     try {
/* 141 */       input = new FileReader(file);
/* 142 */       BufferedReader buff = new BufferedReader(input);
/*     */ 
/*     */       
/* 145 */       List<BlacklistEntry> currentEntries = null; String line;
/* 146 */       while ((line = buff.readLine()) != null) {
/* 147 */         line = line.trim();
/*     */ 
/*     */         
/* 150 */         if (line.isEmpty())
/*     */           continue; 
/* 152 */         if (line.charAt(0) == ';' || line.charAt(0) == '#') {
/*     */           continue;
/*     */         }
/*     */         
/* 156 */         if (line.matches("^\\[.*\\]$")) {
/* 157 */           String[] items = line.substring(1, line.length() - 1).split(",");
/* 158 */           currentEntries = new ArrayList<BlacklistEntry>();
/*     */           
/* 160 */           for (String item : items) {
/*     */             try {
/* 162 */               TargetMatcher matcher = targetMatcherParser.fromInput(item.trim());
/* 163 */               BlacklistEntry entry = new BlacklistEntry(this);
/* 164 */               builder.add(matcher, entry);
/* 165 */               currentEntries.add(entry);
/* 166 */             } catch (TargetMatcherParseException e) {
/* 167 */               log.log(Level.WARNING, "Could not parse a block/item heading: " + e.getMessage());
/*     */             } 
/*     */           }  continue;
/* 170 */         }  if (currentEntries != null) {
/* 171 */           String[] parts = line.split("=");
/*     */           
/* 173 */           if (parts.length == 1) {
/* 174 */             log.log(Level.WARNING, "Found option with no value " + file.getName() + " for '" + line + "'");
/*     */             
/*     */             continue;
/*     */           } 
/* 178 */           boolean unknownOption = false;
/*     */           
/* 180 */           for (BlacklistEntry entry : currentEntries) {
/* 181 */             if (parts[0].equalsIgnoreCase("ignore-groups")) {
/* 182 */               entry.setIgnoreGroups(parts[1].split(",")); continue;
/*     */             } 
/* 184 */             if (parts[0].equalsIgnoreCase("ignore-perms")) {
/* 185 */               entry.setIgnorePermissions(parts[1].split(",")); continue;
/*     */             } 
/* 187 */             if (parts[0].equalsIgnoreCase("message")) {
/* 188 */               entry.setMessage(CommandUtils.replaceColorMacros(parts[1].trim())); continue;
/*     */             } 
/* 190 */             if (parts[0].equalsIgnoreCase("comment")) {
/* 191 */               entry.setComment(CommandUtils.replaceColorMacros(parts[1].trim()));
/*     */               continue;
/*     */             } 
/* 194 */             boolean found = false;
/*     */             
/* 196 */             for (EventType type : EventType.values()) {
/* 197 */               if (type.getRuleName().equalsIgnoreCase(parts[0])) {
/* 198 */                 entry.getActions(type.getEventClass()).addAll(parseActions(entry, parts[1]));
/* 199 */                 found = true;
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/* 204 */             if (!found) {
/* 205 */               unknownOption = true;
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 210 */           if (unknownOption)
/* 211 */             log.log(Level.WARNING, "Unknown option '" + parts[0] + "' in " + file.getName() + " for '" + line + "'"); 
/*     */           continue;
/*     */         } 
/* 214 */         log.log(Level.WARNING, "Found option with no heading " + file
/* 215 */             .getName() + " for '" + line + "'");
/*     */       } 
/*     */ 
/*     */       
/* 219 */       this.index = builder.build();
/*     */     } finally {
/*     */       try {
/* 222 */         if (input != null) {
/* 223 */           input.close();
/*     */         }
/* 225 */       } catch (IOException ignore) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private List<Action> parseActions(BlacklistEntry entry, String raw) {
/* 231 */     String[] split = raw.split(",");
/* 232 */     List<Action> actions = new ArrayList<Action>();
/*     */     
/* 234 */     for (String name : split) {
/* 235 */       name = name.trim();
/*     */       
/* 237 */       boolean found = false;
/*     */       
/* 239 */       for (ActionType type : ActionType.values()) {
/* 240 */         if (type.getActionName().equalsIgnoreCase(name)) {
/* 241 */           actions.add(type.parseInput(this, entry));
/* 242 */           found = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 247 */       if (!found) {
/* 248 */         log.log(Level.WARNING, "Unknown blacklist action: " + name);
/*     */       }
/*     */     } 
/*     */     
/* 252 */     return actions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlacklistEvent getLastEvent() {
/* 261 */     return this.lastEvent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notify(BlacklistEvent event, String comment) {
/* 271 */     this.lastEvent = event;
/*     */     
/* 273 */     broadcastNotification(ChatColor.GRAY + "WG: " + ChatColor.LIGHT_PURPLE + event
/* 274 */         .getCauseName() + ChatColor.GOLD + " (" + event
/* 275 */         .getDescription() + ") " + ChatColor.WHITE + event
/*     */         
/* 277 */         .getTarget().getFriendlyName() + ((comment != null) ? (" (" + comment + ")") : "") + ".");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void broadcastNotification(String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoadingCache<String, TrackedEvent> getRepeatingEventCache() {
/* 289 */     return this.repeatingEventCache;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\Blacklist.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */