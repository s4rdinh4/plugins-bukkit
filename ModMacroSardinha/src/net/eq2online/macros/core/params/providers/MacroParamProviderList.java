/*     */ package net.eq2online.macros.core.params.providers;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.core.MacroParamProvider;
/*     */ import net.eq2online.macros.core.MacroParams;
/*     */ import net.eq2online.macros.core.params.MacroParam;
/*     */ import net.eq2online.macros.core.params.MacroParamList;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MacroParamProviderList
/*     */   extends MacroParamProvider
/*     */ {
/*  19 */   public static Pattern listPattern = Pattern.compile(MacroParam.paramSequence + "\\[([a-z0-9\\x20_\\-\\.]*)\\[([^\\]\\[\\x24\\|]+)\\]([iu]?)\\]", 2);
/*     */   
/*  21 */   protected int nextListPos = -1;
/*     */   
/*  23 */   protected int nextListEnd = 0;
/*     */   
/*  25 */   protected String nextListName = "";
/*     */   
/*  27 */   protected String[] nextListOptions = new String[0];
/*     */   
/*  29 */   protected MacroParam.Type nextListType = MacroParam.Type.Normal;
/*     */ 
/*     */   
/*     */   public MacroParamProviderList(MacroParam.Type type) {
/*  33 */     super(type);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/*  39 */     super.reset();
/*  40 */     clearNextList();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Matcher find(String script) {
/*  46 */     Matcher matcher = super.find(script);
/*     */     
/*  48 */     if (this.found) {
/*     */       
/*  50 */       this.nextListPos = matcher.start();
/*  51 */       this.nextListEnd = matcher.end();
/*  52 */       this.nextListOptions = ScriptCore.tokenize(matcher.group(2), ',', '"', '"', '\\', null);
/*     */       
/*  54 */       String typeHint = matcher.group(3);
/*     */       
/*  56 */       if (typeHint.equalsIgnoreCase("i")) {
/*     */         
/*  58 */         this.nextListType = MacroParam.Type.Item;
/*  59 */         this.nextListName = "Item";
/*     */       }
/*  61 */       else if (typeHint.equalsIgnoreCase("u")) {
/*     */         
/*  63 */         this.nextListType = MacroParam.Type.OnlineUser;
/*  64 */         this.nextListName = "User";
/*     */       }
/*     */       else {
/*     */         
/*  68 */         this.nextListType = MacroParam.Type.Normal;
/*  69 */         this.nextListName = "Choice";
/*     */       } 
/*     */       
/*  72 */       if (matcher.group(1).length() > 0) this.nextListName = matcher.group(1);
/*     */     
/*     */     } 
/*  75 */     return matcher;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearNextList() {
/*  80 */     this.nextListPos = -1;
/*  81 */     this.nextListEnd = 0;
/*  82 */     this.nextListName = "";
/*  83 */     this.nextListOptions = new String[0];
/*  84 */     this.nextListType = MacroParam.Type.Normal;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNextListName() {
/*  89 */     return this.nextListName;
/*     */   }
/*     */ 
/*     */   
/*     */   public MacroParam.Type getNextListType() {
/*  94 */     return this.nextListType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getNextListOptions() {
/*  99 */     return this.nextListOptions;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNextListPos() {
/* 104 */     return this.nextListPos;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNextListEnd() {
/* 109 */     return this.nextListEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Pattern getPattern() {
/* 115 */     return listPattern;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MacroParam getMacroParam(IMacroParamTarget target, MacroParams params) {
/* 121 */     return (MacroParam)new MacroParamList(getType(), target, params, this);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\params\providers\MacroParamProviderList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */