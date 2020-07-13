/*     */ package net.eq2online.macros.event.providers;
/*     */ 
/*     */ import ahd;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.scripting.api.IMacroEvent;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventVariableProvider;
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
/*     */ public class OnChatProvider
/*     */   implements IMacroEventVariableProvider
/*     */ {
/*     */   private static final long guessLifeSpan = 500L;
/*  29 */   private static final Pattern vanillaQuotePattern = Pattern.compile("^\\<([a-z0-9_]{2,16})\\>", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  34 */   private static final Pattern likelyQuotePattern = Pattern.compile("^([a-z0-9_]{2,16}):|^\\<(.+?)\\>", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  39 */   private static final Pattern bestGuessQuotePattern = Pattern.compile("\\<(.+?)\\>|\\[(.+?)\\]|\\((.+?)\\)", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  44 */   private static final Pattern actualNameQuotePattern = Pattern.compile("([a-z0-9_]{2,16})", 2);
/*     */ 
/*     */ 
/*     */   
/*     */   private String lastChatPlayerName;
/*     */ 
/*     */ 
/*     */   
/*     */   private ahd lastChatPlayer;
/*     */ 
/*     */   
/*     */   private long lastChatPlayerTime;
/*     */ 
/*     */   
/*     */   private boolean followOnLikely = false;
/*     */ 
/*     */   
/*     */   String chat;
/*     */ 
/*     */   
/*     */   String chatClean;
/*     */ 
/*     */   
/*     */   String chatMessage;
/*     */ 
/*     */ 
/*     */   
/*     */   public OnChatProvider(IMacroEvent event) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateVariables(boolean clock) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getVariable(String variableName) {
/*  80 */     if (variableName.equals("CHAT")) return this.chat; 
/*  81 */     if (variableName.equals("CHATCLEAN")) return (this.chatClean != null) ? this.chatClean : ""; 
/*  82 */     if (variableName.equals("CHATPLAYER")) return (this.lastChatPlayerName != null) ? this.lastChatPlayerName : ""; 
/*  83 */     if (variableName.equals("CHATMESSAGE")) return (this.chatMessage != null) ? this.chatMessage : "";
/*     */     
/*  85 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getVariables() {
/*  91 */     Set<String> variables = new HashSet<String>();
/*  92 */     variables.add("CHAT");
/*  93 */     variables.add("CHATCLEAN");
/*  94 */     variables.add("CHATPLAYER");
/*  95 */     variables.add("CHATMESSAGE");
/*  96 */     return variables;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onInit() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void initInstance(String[] instanceVariables) {
/* 107 */     this.chat = instanceVariables[0];
/* 108 */     this.chatClean = instanceVariables[1];
/*     */ 
/*     */     
/*     */     try {
/* 112 */       this.chatMessage = guessPlayer(this.chatClean);
/*     */     }
/* 114 */     catch (Exception ex) {
/*     */       
/* 116 */       this.chatMessage = this.chatClean;
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
/*     */   protected String guessPlayer(String message) {
/* 129 */     String guess = null;
/*     */ 
/*     */     
/* 132 */     if (this.followOnLikely && this.lastChatPlayerName != null && System.currentTimeMillis() < this.lastChatPlayerTime + 500L) {
/*     */       
/* 134 */       guess = this.lastChatPlayerName;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 139 */       this.lastChatPlayerName = null;
/* 140 */       this.lastChatPlayer = null;
/* 141 */       this.lastChatPlayerTime = 0L;
/*     */     } 
/*     */ 
/*     */     
/* 145 */     this.followOnLikely = (message.length() >= 100);
/*     */ 
/*     */     
/* 148 */     if (this.chatClean.startsWith("<") && this.chatClean.indexOf('>') > -1) {
/*     */       
/* 150 */       Matcher vanillaMatcher = vanillaQuotePattern.matcher(this.chatClean);
/*     */       
/* 152 */       if (vanillaMatcher.find()) {
/*     */         
/* 154 */         this.lastChatPlayerName = vanillaMatcher.group(1);
/* 155 */         this.lastChatPlayer = findGuessedPlayer(this.lastChatPlayerName);
/* 156 */         this.lastChatPlayerTime = System.currentTimeMillis();
/* 157 */         return vanillaMatcher.replaceFirst("").trim();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 162 */     String bestGuess = null;
/*     */     
/* 164 */     Matcher likelyQuotePatternMatcher = likelyQuotePattern.matcher(this.chatClean);
/* 165 */     Matcher bestGuessMatcher = bestGuessQuotePattern.matcher(this.chatClean);
/*     */ 
/*     */     
/* 168 */     Matcher successfulMatcher = null;
/*     */ 
/*     */     
/* 171 */     if (likelyQuotePatternMatcher.find()) {
/*     */       
/* 173 */       successfulMatcher = likelyQuotePatternMatcher;
/*     */       
/* 175 */       bestGuess = (likelyQuotePatternMatcher.group(1) != null) ? likelyQuotePatternMatcher.group(1) : likelyQuotePatternMatcher.group(2);
/*     */     }
/* 177 */     else if (bestGuessMatcher.find()) {
/*     */       
/* 179 */       successfulMatcher = bestGuessMatcher;
/*     */ 
/*     */       
/* 182 */       if (bestGuessMatcher.group(1) != null) bestGuess = bestGuessMatcher.group(1); 
/* 183 */       if (bestGuessMatcher.group(2) != null) bestGuess = bestGuessMatcher.group(2); 
/* 184 */       if (bestGuessMatcher.group(3) != null) bestGuess = bestGuessMatcher.group(3);
/*     */     
/*     */     } 
/*     */     
/* 188 */     if (bestGuess != null && successfulMatcher != null) {
/*     */       
/* 190 */       Matcher matchingPlayerName = actualNameQuotePattern.matcher(bestGuess);
/*     */       
/* 192 */       if (matchingPlayerName.find()) {
/*     */         
/* 194 */         bestGuess = matchingPlayerName.group(1);
/* 195 */         this.lastChatPlayerTime = System.currentTimeMillis();
/*     */         
/* 197 */         message = successfulMatcher.replaceFirst("").trim();
/*     */       }
/* 199 */       else if (guess != null) {
/*     */         
/* 201 */         bestGuess = guess;
/*     */       } 
/*     */       
/* 204 */       this.lastChatPlayerName = bestGuess;
/* 205 */       this.lastChatPlayer = findGuessedPlayer(bestGuess);
/* 206 */       return message;
/*     */     } 
/*     */ 
/*     */     
/* 210 */     if (guess != null) {
/*     */       
/* 212 */       this.lastChatPlayer = findGuessedPlayer(guess);
/*     */       
/* 214 */       if (this.lastChatPlayer != null && this.followOnLikely)
/*     */       {
/* 216 */         this.lastChatPlayerTime = System.currentTimeMillis();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 221 */     return message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ahd findGuessedPlayer(String guess) {
/* 232 */     if (AbstractionLayer.getWorld() != null && (AbstractionLayer.getWorld()).j != null)
/*     */     {
/* 234 */       for (Object entity : (AbstractionLayer.getWorld()).j) {
/*     */         
/* 236 */         ahd playerEntity = (ahd)entity;
/*     */         
/* 238 */         if (playerEntity.d_().equalsIgnoreCase(guess)) {
/*     */           
/* 240 */           this.lastChatPlayerName = playerEntity.d_();
/* 241 */           return playerEntity;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 246 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\event\providers\OnChatProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */