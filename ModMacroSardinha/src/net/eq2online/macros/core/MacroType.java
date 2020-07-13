/*     */ package net.eq2online.macros.core;
/*     */ 
/*     */ import net.eq2online.macros.event.MacroEventManager;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*     */ import net.eq2online.macros.scripting.api.IMacroEvent;
/*     */ import org.lwjgl.input.Keyboard;
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
/*     */ public enum MacroType
/*     */ {
/*  19 */   Key(true, true, 0, 254)
/*     */   {
/*     */     
/*     */     public String getName(int id)
/*     */     {
/*  24 */       if (!supportsId(id)) {
/*  25 */         return super.getName(id);
/*     */       }
/*  27 */       if (id == 248) return "MWHEELUP"; 
/*  28 */       if (id == 249) return "MWHEELDOWN"; 
/*  29 */       if (id == 250) return "LMOUSE"; 
/*  30 */       if (id == 251) return "RMOUSE"; 
/*  31 */       if (id == 252) return "MIDDLEMOUSE"; 
/*  32 */       if (id == 253 || id == -97) return "MOUSE3"; 
/*  33 */       if (id == 254 || id == -96) return "MOUSE4"; 
/*  34 */       if (id == 240 || id == -95) return "MOUSE5"; 
/*  35 */       if (id == 241 || id == -94) return "MOUSE6"; 
/*  36 */       if (id == 242 || id == -93) return "MOUSE7"; 
/*  37 */       if (id == 243 || id == -92) return "MOUSE8"; 
/*  38 */       if (id == 244 || id == -91) return "MOUSE9"; 
/*  39 */       if (id == 245 || id == -90) return "MOUSE10";
/*     */       
/*  41 */       String keyName = Keyboard.getKeyName(id);
/*  42 */       return (keyName != null) ? keyName : String.valueOf(id);
/*     */     }
/*     */   },
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   Control(true, true, 256, 999, 3000, 9999)
/*     */   {
/*     */     
/*     */     public String getName(int id)
/*     */     {
/*  54 */       if (!supportsId(id)) {
/*  55 */         return super.getName(id);
/*     */       }
/*  57 */       return DesignableGuiControl.getControlName(id);
/*     */     }
/*     */   },
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   Event(true, false, 1000, 1999)
/*     */   {
/*     */     
/*     */     public String getName(int id)
/*     */     {
/*  69 */       if (!supportsId(id)) {
/*  70 */         return super.getName(id);
/*     */       }
/*  72 */       MacroEventManager eventManger = MacroModCore.getMacroManager().getEventManager();
/*  73 */       IMacroEvent event = eventManger.getEvent(id);
/*     */       
/*  75 */       if (event != null)
/*     */       {
/*  77 */         return event.getName();
/*     */       }
/*     */       
/*  80 */       return "onEventId" + getRelativeId(id);
/*     */     }
/*     */   },
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   None(false, false, 2000, 2999)
/*     */   {
/*     */ 
/*     */ 
/*     */     
/*  92 */     private int nextFreeIndex = getMinId();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     private String[] macroNames = new String[getMaxId() + 1];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getName(int id) {
/* 103 */       if (!supportsId(id)) {
/* 104 */         return super.getName(id);
/*     */       }
/* 106 */       if (this.macroNames[id] != null) {
/* 107 */         return this.macroNames[id].toUpperCase();
/*     */       }
/* 109 */       return "MACRO" + (getRelativeId(id) + 1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getIndexForName(String macroName) {
/* 121 */       if (macroName == null) return 0;
/*     */       
/* 123 */       for (int code = getMinId(); code <= getMaxId(); code++) {
/*     */         
/* 125 */         if (getName(code).equalsIgnoreCase(macroName))
/*     */         {
/* 127 */           return code;
/*     */         }
/*     */       } 
/*     */       
/* 131 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getNextFreeIndex(String macroName) {
/* 143 */       int existingIndex = getIndexForName(macroName);
/*     */       
/* 145 */       if (existingIndex > 0) {
/* 146 */         return existingIndex;
/*     */       }
/* 148 */       if (this.nextFreeIndex >= MacroType.None.getMaxId()) {
/* 149 */         this.nextFreeIndex = MacroType.None.getMinId();
/*     */       }
/* 151 */       this.macroNames[this.nextFreeIndex] = macroName;
/*     */       
/* 153 */       return this.nextFreeIndex++;
/*     */     }
/*     */   };
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int MAX_TEMPLATES = 10000;
/*     */ 
/*     */   
/*     */   private boolean save;
/*     */   
/*     */   private boolean supportsParams;
/*     */   
/*     */   private final int minId;
/*     */   
/*     */   private final int maxId;
/*     */   
/*     */   private final boolean extendedId;
/*     */   
/*     */   private final int minExtId;
/*     */   
/*     */   private final int maxExtId;
/*     */ 
/*     */   
/*     */   MacroType(boolean save, boolean supportsParams, int minId, int maxId) {
/* 178 */     this.save = save;
/* 179 */     this.supportsParams = supportsParams;
/* 180 */     this.minId = minId;
/* 181 */     this.maxId = maxId;
/* 182 */     this.minExtId = 9999;
/* 183 */     this.maxExtId = 10000;
/* 184 */     this.extendedId = false;
/*     */   }
/*     */ 
/*     */   
/*     */   MacroType(boolean save, boolean supportsParams, int minId, int maxId, int minExtId, int maxExtId) {
/* 189 */     this.save = save;
/* 190 */     this.supportsParams = supportsParams;
/* 191 */     this.minId = minId;
/* 192 */     this.maxId = maxId;
/* 193 */     this.minExtId = minExtId;
/* 194 */     this.maxExtId = maxExtId;
/* 195 */     this.extendedId = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean supportsParams() {
/* 200 */     return this.supportsParams;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMinId() {
/* 205 */     return this.minId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxId() {
/* 210 */     return this.maxId;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean supportsExtendedId() {
/* 215 */     return this.extendedId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMinExtId() {
/* 220 */     return this.minExtId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxExtId() {
/* 225 */     return this.maxExtId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAbsoluteMaxId() {
/* 230 */     return this.extendedId ? this.maxExtId : this.maxId;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean supportsId(int id) {
/* 235 */     return ((id >= this.minId && id <= this.maxId) || (this.extendedId && id >= this.minExtId && id <= this.maxExtId));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRelativeId(int id) {
/* 240 */     return id - this.minId;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName(int id) {
/* 245 */     if (id < 0) {
/*     */       
/* 247 */       if (id == -100) return "LMOUSE"; 
/* 248 */       if (id == -99) return "RMOUSE"; 
/* 249 */       if (id == -98) return "MIDDLEMOUSE";
/*     */     
/*     */     } 
/* 252 */     return "UNKNOWN";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIndexForName(String macroName) {
/* 257 */     throw new UnsupportedOperationException("Cannot lookup macro name for type " + name());
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNextFreeIndex(String macroName) {
/* 262 */     throw new UnsupportedOperationException("Cannot get free index for type " + name());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MacroType getFromID(int id) {
/* 273 */     for (MacroType type : values()) {
/*     */       
/* 275 */       if (type.supportsId(id)) {
/* 276 */         return type;
/*     */       }
/*     */     } 
/* 279 */     return None;
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
/*     */   public static boolean validate(int id, MacroType type) {
/* 291 */     return getFromID(id).equals(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getMacroNameWithPrefix(int mappingId) {
/* 302 */     MacroType macroType = getFromID(mappingId);
/* 303 */     String prefix = macroType.toString().toUpperCase() + "_";
/* 304 */     return prefix + macroType.getName(mappingId).toUpperCase();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getMacroName(int mappingId) {
/* 315 */     return getFromID(mappingId).getName(mappingId);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean getMacroShouldBeSaved(int mappingId) {
/* 320 */     MacroType type = getFromID(mappingId);
/*     */     
/* 322 */     if (type == Control)
/*     */     {
/* 324 */       return (DesignableGuiControl.getControl(mappingId) != null);
/*     */     }
/*     */     
/* 327 */     return type.save;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\MacroType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */