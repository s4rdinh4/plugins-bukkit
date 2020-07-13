/*     */ package com.sk89q.worldguard.bukkit.cause;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.sk89q.worldguard.bukkit.internal.WGMetadata;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.entity.Creature;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Projectile;
/*     */ import org.bukkit.entity.TNTPrimed;
/*     */ import org.bukkit.entity.Tameable;
/*     */ import org.bukkit.entity.Vehicle;
/*     */ import org.bukkit.metadata.Metadatable;
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
/*     */ public final class Cause
/*     */ {
/*     */   private static final String CAUSE_KEY = "worldguard.cause";
/*  49 */   private static final Cause UNKNOWN = new Cause(Collections.emptyList(), false);
/*     */ 
/*     */ 
/*     */   
/*     */   private final List<Object> causes;
/*     */ 
/*     */   
/*     */   private final boolean indirect;
/*     */ 
/*     */ 
/*     */   
/*     */   private Cause(List<Object> causes, boolean indirect) {
/*  61 */     Preconditions.checkNotNull(causes);
/*  62 */     this.causes = causes;
/*  63 */     this.indirect = indirect;
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
/*     */   public boolean isIndirect() {
/*  75 */     return this.indirect;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isKnown() {
/*  86 */     if (this.causes.isEmpty()) {
/*  87 */       return false;
/*     */     }
/*     */     
/*  90 */     boolean found = false;
/*  91 */     for (Object object : this.causes) {
/*  92 */       if (!(object instanceof TNTPrimed) && !(object instanceof Vehicle)) {
/*  93 */         found = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  98 */     return found;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Object getRootCause() {
/* 103 */     if (!this.causes.isEmpty()) {
/* 104 */       return this.causes.get(0);
/*     */     }
/*     */     
/* 107 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Player getFirstPlayer() {
/* 112 */     for (Object object : this.causes) {
/* 113 */       if (object instanceof Player) {
/* 114 */         return (Player)object;
/*     */       }
/*     */     } 
/*     */     
/* 118 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Entity getFirstEntity() {
/* 123 */     for (Object object : this.causes) {
/* 124 */       if (object instanceof Entity) {
/* 125 */         return (Entity)object;
/*     */       }
/*     */     } 
/*     */     
/* 129 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Block getFirstBlock() {
/* 134 */     for (Object object : this.causes) {
/* 135 */       if (object instanceof Block) {
/* 136 */         return (Block)object;
/*     */       }
/*     */     } 
/*     */     
/* 140 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public EntityType find(EntityType... types) {
/* 151 */     for (Object object : this.causes) {
/* 152 */       if (object instanceof Entity) {
/* 153 */         for (EntityType type : types) {
/* 154 */           if (((Entity)object).getType() == type) {
/* 155 */             return type;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 161 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 166 */     return Joiner.on(" | ").join(this.causes);
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
/*     */   public static Cause create(@Nullable Object... cause) {
/* 178 */     if (cause != null) {
/* 179 */       Builder builder = new Builder(cause.length);
/* 180 */       builder.addAll(cause);
/* 181 */       return builder.build();
/*     */     } 
/* 183 */     return UNKNOWN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cause unknown() {
/* 193 */     return UNKNOWN;
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
/*     */ 
/*     */   
/*     */   public static void trackParentCause(Metadatable target, Object parent) {
/* 209 */     if (target instanceof Block) {
/* 210 */       throw new IllegalArgumentException("Can't track causes on Blocks because Cause doesn't check block metadata");
/*     */     }
/*     */     
/* 213 */     WGMetadata.put(target, "worldguard.cause", parent);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class Builder
/*     */   {
/*     */     private final List<Object> causes;
/*     */     
/* 221 */     private final Set<Object> seen = Sets.newHashSet();
/*     */     private boolean indirect;
/*     */     
/*     */     private Builder(int expectedSize) {
/* 225 */       this.causes = new ArrayList(expectedSize);
/*     */     }
/*     */     
/*     */     private void addAll(@Nullable Object... element) {
/* 229 */       if (element != null)
/* 230 */         for (Object o : element) {
/* 231 */           if (o != null && !this.seen.contains(o)) {
/*     */ 
/*     */ 
/*     */             
/* 235 */             this.seen.add(o);
/*     */             
/* 237 */             if (o instanceof TNTPrimed) {
/* 238 */               addAll(new Object[] { ((TNTPrimed)o).getSource() });
/* 239 */             } else if (o instanceof Projectile) {
/* 240 */               addAll(new Object[] { ((Projectile)o).getShooter() });
/* 241 */             } else if (o instanceof Vehicle) {
/* 242 */               addAll(new Object[] { ((Vehicle)o).getPassenger() });
/* 243 */             } else if (o instanceof Creature && ((Creature)o).getTarget() != null) {
/* 244 */               this.indirect = true;
/* 245 */               addAll(new Object[] { ((Creature)o).getTarget() });
/* 246 */             } else if (o instanceof Tameable) {
/* 247 */               this.indirect = true;
/* 248 */               addAll(new Object[] { ((Tameable)o).getOwner() });
/*     */             } 
/*     */ 
/*     */             
/* 252 */             Object source = o;
/* 253 */             int index = this.causes.size();
/* 254 */             while (source instanceof Metadatable && !(source instanceof Block)) {
/* 255 */               source = WGMetadata.getIfPresent((Metadatable)source, "worldguard.cause", Object.class);
/* 256 */               if (source != null) {
/* 257 */                 this.causes.add(index, source);
/* 258 */                 this.seen.add(source);
/*     */               } 
/*     */             } 
/*     */             
/* 262 */             this.causes.add(o);
/*     */           } 
/*     */         }  
/*     */     }
/*     */     
/*     */     public Cause build() {
/* 268 */       return new Cause(this.causes, this.indirect);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\cause\Cause.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */