/*     */ package com.sk89q.worldguard.bukkit.commands.region;
/*     */ 
/*     */ import com.sk89q.worldedit.BlockVector;
/*     */ import com.sk89q.worldguard.domains.DefaultDomain;
/*     */ import com.sk89q.worldguard.protection.flags.DefaultFlag;
/*     */ import com.sk89q.worldguard.protection.flags.Flag;
/*     */ import com.sk89q.worldguard.protection.flags.RegionGroupFlag;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*     */ import com.sk89q.worldguard.util.profile.cache.ProfileCache;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.concurrent.Callable;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.CommandSender;
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
/*     */ public class RegionPrintoutBuilder
/*     */   implements Callable<String>
/*     */ {
/*     */   private final ProtectedRegion region;
/*     */   @Nullable
/*     */   private final ProfileCache cache;
/*  47 */   private final StringBuilder builder = new StringBuilder();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegionPrintoutBuilder(ProtectedRegion region, @Nullable ProfileCache cache) {
/*  56 */     this.region = region;
/*  57 */     this.cache = cache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void newLine() {
/*  64 */     this.builder.append("\n");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendBasics() {
/*  71 */     this.builder.append(ChatColor.BLUE);
/*  72 */     this.builder.append("Region: ");
/*  73 */     this.builder.append(ChatColor.YELLOW);
/*  74 */     this.builder.append(this.region.getId());
/*     */     
/*  76 */     this.builder.append(ChatColor.GRAY);
/*  77 */     this.builder.append(" (type=");
/*  78 */     this.builder.append(this.region.getType().getName());
/*     */     
/*  80 */     this.builder.append(ChatColor.GRAY);
/*  81 */     this.builder.append(", priority=");
/*  82 */     this.builder.append(this.region.getPriority());
/*  83 */     this.builder.append(")");
/*     */     
/*  85 */     newLine();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendFlags() {
/*  92 */     this.builder.append(ChatColor.BLUE);
/*  93 */     this.builder.append("Flags: ");
/*     */     
/*  95 */     appendFlagsList(true);
/*     */     
/*  97 */     newLine();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendFlagsList(boolean useColors) {
/* 106 */     boolean hasFlags = false;
/*     */     
/* 108 */     for (Flag<?> flag : DefaultFlag.getFlags()) {
/* 109 */       Object val = this.region.getFlag(flag), group = null;
/*     */ 
/*     */       
/* 112 */       if (val != null) {
/*     */ 
/*     */ 
/*     */         
/* 116 */         if (hasFlags) {
/* 117 */           this.builder.append(", ");
/*     */         }
/* 119 */         else if (useColors) {
/* 120 */           this.builder.append(ChatColor.YELLOW);
/*     */         } 
/*     */ 
/*     */         
/* 124 */         RegionGroupFlag groupFlag = flag.getRegionGroupFlag();
/* 125 */         if (groupFlag != null) {
/* 126 */           group = this.region.getFlag((Flag)groupFlag);
/*     */         }
/*     */         
/* 129 */         if (group == null) {
/* 130 */           this.builder.append(flag.getName()).append(": ")
/* 131 */             .append(ChatColor.stripColor(String.valueOf(val)));
/*     */         } else {
/* 133 */           this.builder.append(flag.getName()).append(" -g ")
/* 134 */             .append(group).append(": ")
/* 135 */             .append(ChatColor.stripColor(String.valueOf(val)));
/*     */         } 
/*     */         
/* 138 */         hasFlags = true;
/*     */       } 
/*     */     } 
/* 141 */     if (!hasFlags) {
/* 142 */       if (useColors) {
/* 143 */         this.builder.append(ChatColor.RED);
/*     */       }
/* 145 */       this.builder.append("(none)");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendParents() {
/* 153 */     appendParentTree(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendParentTree(boolean useColors) {
/* 162 */     if (this.region.getParent() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 166 */     List<ProtectedRegion> inheritance = new ArrayList<ProtectedRegion>();
/*     */     
/* 168 */     ProtectedRegion r = this.region;
/* 169 */     inheritance.add(r);
/* 170 */     while (r.getParent() != null) {
/* 171 */       r = r.getParent();
/* 172 */       inheritance.add(r);
/*     */     } 
/*     */     
/* 175 */     ListIterator<ProtectedRegion> it = inheritance.listIterator(inheritance
/* 176 */         .size());
/*     */     
/* 178 */     int indent = 0;
/* 179 */     while (it.hasPrevious()) {
/* 180 */       ProtectedRegion cur = it.previous();
/* 181 */       if (useColors) {
/* 182 */         this.builder.append(ChatColor.GREEN);
/*     */       }
/*     */ 
/*     */       
/* 186 */       if (indent != 0) {
/* 187 */         for (int i = 0; i < indent; i++) {
/* 188 */           this.builder.append("  ");
/*     */         }
/* 190 */         this.builder.append("┗");
/*     */       } 
/*     */ 
/*     */       
/* 194 */       this.builder.append(cur.getId());
/*     */ 
/*     */       
/* 197 */       if (!cur.equals(this.region)) {
/* 198 */         if (useColors) {
/* 199 */           this.builder.append(ChatColor.GRAY);
/*     */         }
/* 201 */         this.builder.append(" (parent, priority=").append(cur.getPriority()).append(")");
/*     */       } 
/*     */       
/* 204 */       indent++;
/* 205 */       newLine();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendDomain() {
/* 213 */     this.builder.append(ChatColor.BLUE);
/* 214 */     this.builder.append("Owners: ");
/* 215 */     addDomainString(this.region.getOwners());
/* 216 */     newLine();
/*     */     
/* 218 */     this.builder.append(ChatColor.BLUE);
/* 219 */     this.builder.append("Members: ");
/* 220 */     addDomainString(this.region.getMembers());
/* 221 */     newLine();
/*     */   }
/*     */   
/*     */   private void addDomainString(DefaultDomain domain) {
/* 225 */     if (domain.size() != 0) {
/* 226 */       this.builder.append(ChatColor.YELLOW);
/* 227 */       this.builder.append(domain.toUserFriendlyString(this.cache));
/*     */     } else {
/* 229 */       this.builder.append(ChatColor.RED);
/* 230 */       this.builder.append("(none)");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendBounds() {
/* 238 */     BlockVector min = this.region.getMinimumPoint();
/* 239 */     BlockVector max = this.region.getMaximumPoint();
/* 240 */     this.builder.append(ChatColor.BLUE);
/* 241 */     this.builder.append("Bounds:");
/* 242 */     this.builder.append(ChatColor.YELLOW);
/* 243 */     this.builder.append(" (").append(min.getBlockX()).append(",").append(min.getBlockY()).append(",").append(min.getBlockZ()).append(")");
/* 244 */     this.builder.append(" -> (").append(max.getBlockX()).append(",").append(max.getBlockY()).append(",").append(max.getBlockZ()).append(")");
/*     */     
/* 246 */     newLine();
/*     */   }
/*     */   
/*     */   private void appendRegionInformation() {
/* 250 */     this.builder.append(ChatColor.GRAY);
/* 251 */     this.builder.append("══════════════");
/* 252 */     this.builder.append(" Region Info ");
/* 253 */     this.builder.append("══════════════");
/* 254 */     newLine();
/* 255 */     appendBasics();
/* 256 */     appendFlags();
/* 257 */     appendParents();
/* 258 */     appendDomain();
/* 259 */     appendBounds();
/*     */     
/* 261 */     if (this.cache != null) {
/* 262 */       this.builder.append(ChatColor.GRAY).append("Any names suffixed by * are 'last seen names' and may not be up to date.");
/* 263 */       newLine();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String call() throws Exception {
/* 269 */     appendRegionInformation();
/* 270 */     return this.builder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void send(CommandSender sender) {
/* 279 */     sender.sendMessage(toString());
/*     */   }
/*     */   
/*     */   public StringBuilder append(boolean b) {
/* 283 */     return this.builder.append(b);
/*     */   }
/*     */   
/*     */   public StringBuilder append(char c) {
/* 287 */     return this.builder.append(c);
/*     */   }
/*     */   
/*     */   public StringBuilder append(char[] str, int offset, int len) {
/* 291 */     return this.builder.append(str, offset, len);
/*     */   }
/*     */   
/*     */   public StringBuilder append(char[] str) {
/* 295 */     return this.builder.append(str);
/*     */   }
/*     */   
/*     */   public StringBuilder append(CharSequence s, int start, int end) {
/* 299 */     return this.builder.append(s, start, end);
/*     */   }
/*     */   
/*     */   public StringBuilder append(CharSequence s) {
/* 303 */     return this.builder.append(s);
/*     */   }
/*     */   
/*     */   public StringBuilder append(double d) {
/* 307 */     return this.builder.append(d);
/*     */   }
/*     */   
/*     */   public StringBuilder append(float f) {
/* 311 */     return this.builder.append(f);
/*     */   }
/*     */   
/*     */   public StringBuilder append(int i) {
/* 315 */     return this.builder.append(i);
/*     */   }
/*     */   
/*     */   public StringBuilder append(long lng) {
/* 319 */     return this.builder.append(lng);
/*     */   }
/*     */   
/*     */   public StringBuilder append(Object obj) {
/* 323 */     return this.builder.append(obj);
/*     */   }
/*     */   
/*     */   public StringBuilder append(String str) {
/* 327 */     return this.builder.append(str);
/*     */   }
/*     */   
/*     */   public StringBuilder append(StringBuffer sb) {
/* 331 */     return this.builder.append(sb);
/*     */   }
/*     */   
/*     */   public StringBuilder appendCodePoint(int codePoint) {
/* 335 */     return this.builder.appendCodePoint(codePoint);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 340 */     return this.builder.toString().trim();
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\commands\region\RegionPrintoutBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */