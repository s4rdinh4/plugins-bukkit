/*     */ package com.sk89q.worldguard.protection.flags;
/*     */ 
/*     */ import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/*     */ import java.util.Collection;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class StateFlag
/*     */   extends Flag<StateFlag.State>
/*     */ {
/*     */   private boolean def;
/*     */   
/*     */   public enum State
/*     */   {
/*  34 */     ALLOW,
/*  35 */     DENY;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public StateFlag(String name, boolean def, RegionGroup defaultGroup) {
/*  41 */     super(name, defaultGroup);
/*  42 */     this.def = def;
/*     */   }
/*     */   
/*     */   public StateFlag(String name, boolean def) {
/*  46 */     super(name);
/*  47 */     this.def = def;
/*     */   }
/*     */ 
/*     */   
/*     */   public State getDefault() {
/*  52 */     return this.def ? State.ALLOW : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasConflictStrategy() {
/*  57 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public State chooseValue(Collection<State> values) {
/*  63 */     if (!values.isEmpty()) {
/*  64 */       return combine(values);
/*     */     }
/*  66 */     return null;
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
/*     */   public boolean preventsAllowOnGlobal() {
/*  80 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public State parseInput(WorldGuardPlugin plugin, CommandSender sender, String input) throws InvalidFlagFormat {
/*  85 */     input = input.trim();
/*     */     
/*  87 */     if (input.equalsIgnoreCase("allow"))
/*  88 */       return State.ALLOW; 
/*  89 */     if (input.equalsIgnoreCase("deny"))
/*  90 */       return State.DENY; 
/*  91 */     if (input.equalsIgnoreCase("none")) {
/*  92 */       return null;
/*     */     }
/*  94 */     throw new InvalidFlagFormat("Expected none/allow/deny but got '" + input + "'");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public State unmarshal(Object o) {
/* 100 */     String str = o.toString();
/* 101 */     if (str.equalsIgnoreCase("allow"))
/* 102 */       return State.ALLOW; 
/* 103 */     if (str.equalsIgnoreCase("deny")) {
/* 104 */       return State.DENY;
/*     */     }
/* 106 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object marshal(State o) {
/* 112 */     if (o == State.ALLOW)
/* 113 */       return "allow"; 
/* 114 */     if (o == State.DENY) {
/* 115 */       return "deny";
/*     */     }
/* 117 */     return null;
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
/*     */   public static boolean test(State... states) {
/* 129 */     boolean allowed = false;
/*     */     
/* 131 */     for (State state : states) {
/* 132 */       if (state == State.DENY)
/* 133 */         return false; 
/* 134 */       if (state == State.ALLOW) {
/* 135 */         allowed = true;
/*     */       }
/*     */     } 
/*     */     
/* 139 */     return allowed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static State combine(State... states) {
/* 151 */     boolean allowed = false;
/*     */     
/* 153 */     for (State state : states) {
/* 154 */       if (state == State.DENY)
/* 155 */         return State.DENY; 
/* 156 */       if (state == State.ALLOW) {
/* 157 */         allowed = true;
/*     */       }
/*     */     } 
/*     */     
/* 161 */     return allowed ? State.ALLOW : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static State combine(Collection<State> states) {
/* 173 */     boolean allowed = false;
/*     */     
/* 175 */     for (State state : states) {
/* 176 */       if (state == State.DENY)
/* 177 */         return State.DENY; 
/* 178 */       if (state == State.ALLOW) {
/* 179 */         allowed = true;
/*     */       }
/*     */     } 
/*     */     
/* 183 */     return allowed ? State.ALLOW : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static State allowOrNone(boolean flag) {
/* 195 */     return flag ? State.ALLOW : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static State denyToNone(State state) {
/* 206 */     return (state == State.DENY) ? null : state;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\flags\StateFlag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */