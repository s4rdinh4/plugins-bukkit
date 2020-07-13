/*     */ package net.eq2online.macros.input;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.interfaces.ISettingsProvider;
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
/*     */ public class CharMap
/*     */ {
/*  23 */   private static Pattern charMapEntryPattern = Pattern.compile("\\{([0-9]+),([0-9]+)\\}");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  28 */   private static String defaultMap = "{34,3}{35,40}{32,57}{33,2}{38,8}{39,41}{36,5}{37,6}{42,9}{40,10}{41,11}{46,52}{47,53}{44,51}{45,12}{51,4}{50,3}{49,2}{48,11}{55,8}{54,7}{53,6}{52,5}{59,39}{58,39}{57,10}{56,9}{63,53}{62,52}{61,13}{60,51}{68,32}{69,18}{70,33}{71,34}{64,41}{65,30}{66,48}{67,46}{76,38}{77,50}{78,49}{79,24}{72,35}{73,23}{74,36}{75,37}{85,22}{84,20}{87,17}{86,47}{81,16}{80,25}{83,31}{82,19}{93,27}{92,43}{95,12}{94,7}{89,21}{88,45}{91,26}{90,44}{102,33}{103,34}{100,32}{101,18}{98,48}{99,46}{97,30}{110,49}{111,24}{108,38}{109,50}{106,36}{107,37}{104,35}{105,23}{119,17}{118,47}{117,22}{116,20}{115,31}{114,19}{8962,53}{113,16}{112,25}{126,40}{125,27}{124,43}{123,26}{122,44}{121,21}{120,45}";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String charMap;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   private static HashMap<Integer, Integer> keyChars = new HashMap<Integer, Integer>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static char getKeyChar(int keyCode) {
/*  48 */     for (Map.Entry<Integer, Integer> mapEntry : keyChars.entrySet()) {
/*     */       
/*  50 */       if (((Integer)mapEntry.getValue()).intValue() == keyCode) {
/*  51 */         return (char)((Integer)mapEntry.getKey()).byteValue();
/*     */       }
/*     */     } 
/*  54 */     return Character.MIN_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getKeyCode(int keyChar) {
/*  65 */     return keyChars.containsKey(Integer.valueOf(keyChar)) ? ((Integer)keyChars.get(Integer.valueOf(keyChar))).intValue() : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void init() {
/*  73 */     Matcher charMapEntryPatternMatcher = charMapEntryPattern.matcher(charMap);
/*  74 */     keyChars.clear();
/*     */     
/*  76 */     while (charMapEntryPatternMatcher.find()) {
/*     */       
/*  78 */       int charValue = Integer.parseInt(charMapEntryPatternMatcher.group(1));
/*  79 */       int keyValue = Integer.parseInt(charMapEntryPatternMatcher.group(2));
/*  80 */       keyChars.put(Integer.valueOf(charValue), Integer.valueOf(keyValue));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void notifySettingsCleared() {
/*  89 */     charMap = defaultMap;
/*  90 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void notifySettingsLoaded(ISettingsProvider settings) {
/* 100 */     charMap = settings.getSetting("input.charmap", defaultMap);
/* 101 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void saveSettings(ISettingsProvider settings) {
/* 111 */     settings.setSetting("input.charmap", (charMap != null) ? charMap : defaultMap);
/* 112 */     settings.setSettingComment("input.charmap", "Mapping of ASCII values to LWJGL key codes, this is used by the key event pump to supply key codes for pumped characters");
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\input\CharMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */