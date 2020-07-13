/*     */ package net.eq2online.macros.scripting.api;
/*     */ 
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
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
/*     */ public class ReturnValue
/*     */   implements IReturnValue
/*     */ {
/*     */   private boolean booleanValue;
/*     */   private int integerValue;
/*     */   private String stringValue;
/*     */   
/*     */   public ReturnValue(String value) {
/*  34 */     setString(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReturnValue(int value) {
/*  44 */     setInt(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReturnValue(boolean value) {
/*  54 */     setBool(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setString(String value) {
/*  62 */     this.stringValue = value;
/*  63 */     this.integerValue = ScriptCore.tryParseInt(value, 0);
/*  64 */     this.booleanValue = (value == null || value.toLowerCase().equals("true") || this.integerValue != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInt(int value) {
/*  72 */     this.stringValue = String.valueOf(value);
/*  73 */     this.integerValue = value;
/*  74 */     this.booleanValue = (this.integerValue != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBool(boolean value) {
/*  82 */     this.stringValue = value ? "True" : "False";
/*  83 */     this.integerValue = value ? 1 : 0;
/*  84 */     this.booleanValue = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVoid() {
/*  93 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBoolean() {
/* 102 */     return this.booleanValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInteger() {
/* 111 */     return this.integerValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString() {
/* 120 */     return (this.stringValue != null) ? this.stringValue : "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalMessage() {
/* 129 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRemoteMessage() {
/* 138 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\api\ReturnValue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */