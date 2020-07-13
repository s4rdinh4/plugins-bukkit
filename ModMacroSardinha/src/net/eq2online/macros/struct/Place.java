/*     */ package net.eq2online.macros.struct;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.params.MacroParam;
/*     */ import net.eq2online.macros.gui.controls.specialised.GuiListBoxPlaces;
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
/*     */ public class Place
/*     */ {
/*  22 */   public static Pattern placePattern = Pattern.compile("^(.+?)=\\(([0-9\\-]+),([0-9\\-]+),([0-9\\-]+)\\)$");
/*     */ 
/*     */ 
/*     */   
/*     */   public String name;
/*     */ 
/*     */ 
/*     */   
/*     */   public int x;
/*     */ 
/*     */ 
/*     */   
/*     */   public int y;
/*     */ 
/*     */ 
/*     */   
/*     */   public int z;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Place(String name, int x, int y, int z) {
/*  44 */     this.name = name;
/*     */     
/*  46 */     this.x = x;
/*  47 */     this.y = y;
/*  48 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  54 */     return String.format("%s=(%d,%d,%d)", new Object[] { this.name, Integer.valueOf(this.x), Integer.valueOf(this.y), Integer.valueOf(this.z) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Place parsePlace(String serialisedPlace) {
/*  65 */     Matcher placeInfo = placePattern.matcher(serialisedPlace);
/*     */     
/*  67 */     if (placeInfo.matches())
/*     */     {
/*  69 */       return parsePlace(resolveConflictingName(placeInfo.group(1), null), placeInfo.group(2), placeInfo.group(3), placeInfo.group(4), true);
/*     */     }
/*     */     
/*  72 */     return null;
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
/*     */   public static Place parsePlace(String placeName, String placeXCoordinate, String placeYCoordinate, String placeZCoordinate, boolean resolveConflictingNames) {
/*     */     try {
/*  88 */       int x = Integer.parseInt(placeXCoordinate);
/*  89 */       int y = Integer.parseInt(placeYCoordinate);
/*  90 */       int z = Integer.parseInt(placeZCoordinate);
/*     */       
/*  92 */       return new Place(resolveConflictingNames ? resolveConflictingName(placeName, null) : placeName, x, y, z);
/*     */     }
/*  94 */     catch (NumberFormatException numberFormatException) {
/*     */       
/*  96 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean exists(String placeName) {
/* 107 */     return ((GuiListBoxPlaces)MacroModCore.getInstance().getListProvider().getListBox(MacroParam.Type.Place.toString())).containsPlace(placeName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Place getByName(String placeName) {
/* 118 */     return ((GuiListBoxPlaces)MacroModCore.getInstance().getListProvider().getListBox(MacroParam.Type.Place.toString())).getPlace(placeName);
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
/*     */   public static String resolveConflictingName(String placeName, Place thisPlace) {
/* 130 */     Place existingPlace = getByName(placeName);
/*     */     
/* 132 */     if (existingPlace == null || existingPlace == thisPlace)
/*     */     {
/* 134 */       return placeName;
/*     */     }
/*     */     
/* 137 */     int offset = 1;
/*     */     
/* 139 */     while (exists(placeName + "[" + offset + "]")) {
/* 140 */       offset++;
/*     */     }
/* 142 */     return placeName + "[" + offset + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\struct\Place.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */