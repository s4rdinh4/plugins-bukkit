/*     */ package net.eq2online.macros.scripting.actions.lang;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.scripting.Variable;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IReturnValue;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.ReturnValue;
/*     */ import net.eq2online.macros.scripting.api.ReturnValueArray;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*     */ 
/*     */ 
/*     */ public class ScriptActionMatch
/*     */   extends ScriptAction
/*     */ {
/*     */   public ScriptActionMatch(ScriptContext context) {
/*  23 */     super(context, "match");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/*     */     ReturnValueArray returnValueArray;
/*  34 */     ReturnValue returnValue = new ReturnValue("");
/*     */ 
/*     */     
/*     */     try {
/*  38 */       if (params.length > 1) {
/*     */         
/*  40 */         String regex = ScriptCore.parseVars(provider, macro, params[1], false).replaceAll("(?<!&)&([0-9a-fklmnor])", "§$1").replaceAll("&&", "&");
/*  41 */         String string = ScriptCore.parseVars(provider, macro, params[0], false);
/*  42 */         Pattern pattern = Pattern.compile(regex, 2);
/*  43 */         Matcher match = pattern.matcher(string);
/*     */         
/*  45 */         boolean capture = (params.length > 2);
/*  46 */         boolean captureMultiple = (capture && params[2].startsWith("{") && params[params.length - 1].endsWith("}"));
/*     */         
/*  48 */         if (match.find()) {
/*     */           
/*  50 */           if (captureMultiple) {
/*     */             
/*  52 */             int groupNumber = 1;
/*     */             
/*  54 */             for (String varName : getCaptures(params, 2))
/*     */             {
/*  56 */               ScriptCore.setVariable(provider, macro, varName, (groupNumber <= match.groupCount()) ? getGroup(match, groupNumber) : "");
/*  57 */               groupNumber++;
/*     */             }
/*     */           
/*  60 */           } else if (capture) {
/*     */             
/*  62 */             if (params[2].endsWith("[]") && params[2].length() > 2 && Variable.isValidVariableName(params[2].substring(0, params[2].length() - 2)))
/*     */             {
/*  64 */               String varBaseName = params[2].substring(0, params[2].length() - 2);
/*  65 */               provider.clearArray(macro, varBaseName);
/*  66 */               List<String> strings = new ArrayList<String>();
/*  67 */               for (int groupNumber = 0; groupNumber <= match.groupCount(); groupNumber++) {
/*     */                 
/*  69 */                 String result = getGroup(match, groupNumber);
/*  70 */                 ScriptCore.setVariable(provider, macro, varBaseName + "[" + groupNumber + "]", result);
/*     */               } 
/*     */               
/*  73 */               ReturnValueArray retValArray = new ReturnValueArray(false);
/*  74 */               retValArray.putStrings(strings);
/*  75 */               returnValueArray = retValArray;
/*     */             }
/*     */             else
/*     */             {
/*  79 */               int groupNumber = Math.min(Math.max((params.length > 3) ? ScriptCore.tryParseInt(ScriptCore.parseVars(provider, macro, params[3], false), 0) : 0, 0), match.groupCount());
/*  80 */               String result = getGroup(match, groupNumber);
/*  81 */               ((ReturnValue)returnValueArray).setString(result);
/*  82 */               ScriptCore.setVariable(provider, macro, params[2], result);
/*     */             }
/*     */           
/*     */           } 
/*  86 */         } else if (captureMultiple && params.length > 3) {
/*     */           
/*  88 */           List<String> strings = new ArrayList<String>();
/*  89 */           for (String varName : getCaptures(params, 2))
/*     */           {
/*  91 */             ScriptCore.setVariable(provider, macro, varName, ScriptCore.parseVars(provider, macro, params[3], false));
/*     */           }
/*     */           
/*  94 */           ReturnValueArray retValArray = new ReturnValueArray(false);
/*  95 */           retValArray.putStrings(strings);
/*  96 */           returnValueArray = retValArray;
/*     */         }
/*  98 */         else if (!captureMultiple && params.length > 4) {
/*     */           
/* 100 */           String defaultValue = ScriptCore.parseVars(provider, macro, params[4], false);
/* 101 */           ScriptCore.setVariable(provider, macro, params[2], defaultValue);
/* 102 */           ((ReturnValue)returnValueArray).setString(defaultValue);
/*     */         }
/*     */       
/*     */       } 
/* 106 */     } catch (Exception ex) {
/*     */       
/* 108 */       provider.actionAddChatMessage("§4Error: " + ex.getClass().getSimpleName() + " (" + ex.getMessage() + ")");
/*     */     } 
/*     */     
/* 111 */     return (IReturnValue)returnValueArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGroup(Matcher match, int groupNumber) {
/* 121 */     String group = match.group(groupNumber);
/* 122 */     return (group != null) ? group : "";
/*     */   }
/*     */ 
/*     */   
/*     */   private String[] getCaptures(String[] captureList, int base) {
/* 127 */     String[] captures = new String[captureList.length - 2];
/*     */     
/* 129 */     for (int offset = 0; offset < captures.length; offset++)
/*     */     {
/* 131 */       captures[offset] = captureList[offset + base];
/*     */     }
/*     */     
/* 134 */     captures[0] = captures[0].substring(1);
/* 135 */     captures[captures.length - 1] = captures[captures.length - 1].substring(0, captures[captures.length - 1].length() - 1);
/*     */     
/* 137 */     return captures;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionMatch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */