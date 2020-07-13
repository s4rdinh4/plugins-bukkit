/*     */ package net.eq2online.macros.scripting;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.scripting.api.IExpressionEvaluator;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
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
/*     */ public class ExpressionEvaluator
/*     */   implements IExpressionEvaluator
/*     */ {
/*     */   public static boolean enableTrace = false;
/*  25 */   protected static int MAX_DEPTH = 10;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  30 */   protected static Pattern operatorPattern = Pattern.compile("\\={1,2}|\\<\\=|\\>\\=|\\>|\\<|\\!\\=|\\&{2}|\\|{1,2}|\\+|\\-|\\*|\\/", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  35 */   protected static Pattern stringLiteralPattern = Pattern.compile("\\x22([^\\x22]*)\\x22");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   protected static Pattern negativeNumberPattern = Pattern.compile("(?<=(^|\\())-(?=[0-9])");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   protected HashMap<String, Integer> variables = new HashMap<String, Integer>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   protected HashMap<String, Integer> stringLiterals = new HashMap<String, Integer>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   protected HashMap<Integer, String> stringLiteralValues = new HashMap<Integer, String>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   protected int nextStringLiteral = 2147483646;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String originalExpression;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int result;
/*     */ 
/*     */ 
/*     */   
/*     */   protected IScriptActionProvider variableProvider;
/*     */ 
/*     */ 
/*     */   
/*     */   protected IMacro macro;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExpressionEvaluator(String expression, IScriptActionProvider variableProvider, IMacro macro) {
/*  83 */     this.originalExpression = expression;
/*  84 */     this.variableProvider = variableProvider;
/*  85 */     this.macro = macro;
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
/*     */   public void setVariable(String variableName, boolean variableValue) {
/*  97 */     this.variables.put(variableName, Integer.valueOf(variableValue ? 1 : 0));
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
/*     */   public void setVariable(String variableName, int variableValue) {
/* 110 */     this.variables.put(variableName, Integer.valueOf(variableValue));
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
/*     */   public void setVariable(String variableName, String variableValue) {
/* 122 */     this.variables.put(variableName, Integer.valueOf(addStringLiteral(variableValue)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVariables(Map<String, Object> variables) {
/* 131 */     for (Map.Entry<String, Object> variable : variables.entrySet()) {
/*     */       
/* 133 */       if (variable.getValue() instanceof Boolean) {
/* 134 */         setVariable(variable.getKey(), ((Boolean)variable.getValue()).booleanValue()); continue;
/* 135 */       }  if (variable.getValue() instanceof String) {
/* 136 */         setVariable(variable.getKey(), (String)variable.getValue()); continue;
/* 137 */       }  if (variable.getValue() instanceof Integer) {
/* 138 */         setVariable(variable.getKey(), ((Integer)variable.getValue()).intValue());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dumpVariables() {
/* 148 */     for (Map.Entry<String, Integer> variable : this.variables.entrySet()) {
/*     */       
/* 150 */       if (this.stringLiteralValues.containsKey(variable.getValue())) {
/* 151 */         Log.info("dumpVariables() {0}={1}", new Object[] { variable.getKey(), this.stringLiteralValues.get(variable.getValue()) }); continue;
/*     */       } 
/* 153 */       Log.info("dumpVariables() {0}={1}", new Object[] { variable.getKey(), variable.getValue() });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int addStringLiteral(String literalString) {
/* 163 */     if (literalString.length() == 0) return 0;
/*     */     
/* 165 */     String cleanedLiteralString = literalString.replaceAll("\\x20\\|\\&\\!\\>\\<\\=", "");
/*     */     
/* 167 */     if (this.stringLiterals.containsKey(cleanedLiteralString))
/*     */     {
/* 169 */       return ((Integer)this.stringLiterals.get(cleanedLiteralString)).intValue();
/*     */     }
/*     */     
/* 172 */     int literalStringIndex = this.nextStringLiteral--;
/* 173 */     this.stringLiterals.put(cleanedLiteralString, Integer.valueOf(literalStringIndex));
/* 174 */     this.stringLiteralValues.put(Integer.valueOf(literalStringIndex), literalString);
/* 175 */     return literalStringIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int evaluate() {
/*     */     try {
/* 186 */       this.result = evaluate(prepare(), 0);
/*     */     }
/* 188 */     catch (Exception ex) {
/*     */       
/* 190 */       this.result = 0;
/*     */     } 
/*     */     
/* 193 */     return this.result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getResult() {
/* 202 */     return this.result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String prepare() {
/* 213 */     String expression = this.originalExpression;
/*     */     
/* 215 */     Matcher stringLiteralPatternMatcher = stringLiteralPattern.matcher(expression);
/*     */     
/* 217 */     while (stringLiteralPatternMatcher.find()) {
/*     */       
/* 219 */       int stringLiteralIndex = addStringLiteral(stringLiteralPatternMatcher.group(1));
/* 220 */       expression = expression.substring(0, stringLiteralPatternMatcher.start()) + stringLiteralIndex + expression.substring(stringLiteralPatternMatcher.end());
/* 221 */       stringLiteralPatternMatcher.reset(expression);
/*     */     } 
/*     */     
/* 224 */     expression = expression.replaceAll("(?<!\\&)\\& ", "\\&\\&").replaceAll("\\s", "").replaceAll("true", "1").replaceAll("false", "0");
/*     */     
/* 226 */     if (enableTrace) Log.info("[LOG]   Prepared [{0}]", new Object[] { expression });
/*     */     
/* 228 */     return expression;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int evaluate(String expression, int depth) {
/* 239 */     if (expression == null || expression.length() < 1 || depth >= MAX_DEPTH) return 0;
/*     */     
/* 241 */     if (enableTrace) Log.info("[LOG]    Evaluating [{0}]", new Object[] { expression });
/*     */     
/* 243 */     Matcher negativeNumberPatternMatcher = negativeNumberPattern.matcher(expression);
/* 244 */     expression = negativeNumberPatternMatcher.replaceAll("¬");
/*     */     
/* 246 */     if (enableTrace) Log.info("[LOG]     Evaluating [{0}]", new Object[] { expression });
/*     */ 
/*     */     
/* 249 */     if (containsParentheses(expression)) {
/*     */       
/* 251 */       int startPos = expression.indexOf('(');
/* 252 */       int endPos = startPos + 1;
/* 253 */       int stackCount = 0;
/*     */       
/* 255 */       for (; endPos < expression.length(); endPos++) {
/*     */         
/* 257 */         if (expression.charAt(endPos) == '(') stackCount++; 
/* 258 */         if (expression.charAt(endPos) == ')') {
/*     */           
/* 260 */           stackCount--;
/* 261 */           if (stackCount < 0)
/*     */             break; 
/*     */         } 
/*     */       } 
/* 265 */       String subExpression = expression.substring(startPos + 1, endPos);
/* 266 */       int result = evaluate(subExpression, depth + 1);
/* 267 */       expression = ((startPos > 0) ? expression.substring(0, startPos) : "") + result + ((endPos < expression.length()) ? expression.substring(endPos + 1) : "");
/* 268 */       return evaluate(expression, depth);
/*     */     } 
/* 270 */     if (containsOperator(expression)) {
/*     */       
/* 272 */       Matcher operatorMatcher = operatorPattern.matcher(expression);
/* 273 */       operatorMatcher.find();
/* 274 */       String sLHS = expression.substring(0, operatorMatcher.start());
/* 275 */       String sRHS = expression.substring(operatorMatcher.end());
/* 276 */       int expressionResult = evaluate(sLHS, sRHS, operatorMatcher.group(), depth);
/* 277 */       if (enableTrace) Log.info("[LOG]       Calculated [{0}]", new Object[] { Integer.valueOf(expressionResult) }); 
/* 278 */       return expressionResult;
/*     */     } 
/*     */ 
/*     */     
/* 282 */     return evaluateSingle(expression, depth + 1);
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
/*     */   protected static boolean containsOperator(String expression) {
/* 294 */     return operatorPattern.matcher(expression).find();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean containsParentheses(String expression) {
/* 305 */     return (expression.indexOf('(') > -1 && expression.indexOf(')') > -1);
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
/*     */   protected int evaluate(String sLHS, String sRHS, String operator, int depth) {
/* 318 */     int LHS = getValue(sLHS, depth);
/* 319 */     int RHS = getValue(sRHS, depth);
/*     */     
/* 321 */     if (enableTrace) Log.info("[LOG]      Calculating [{0}] with {1} {2} at depth {3} values [{4}] [{5}]", new Object[] { operator, sLHS, sRHS, Integer.valueOf(depth), Integer.valueOf(LHS), Integer.valueOf(RHS) });
/*     */     
/* 323 */     if (operator.equals("+")) return LHS + RHS; 
/* 324 */     if (operator.equals("-")) return LHS - RHS; 
/* 325 */     if (operator.equals("*")) return LHS * RHS; 
/* 326 */     if (operator.equals("/")) return LHS / RHS;
/*     */ 
/*     */     
/* 329 */     return evaluateBoolean(LHS, RHS, operator, depth) ? 1 : 0;
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
/*     */   protected boolean evaluateBoolean(int LHS, int RHS, String operator, int depth) {
/* 343 */     if (operator.equals("=")) return (LHS == RHS); 
/* 344 */     if (operator.equals("==")) return (LHS == RHS); 
/* 345 */     if (operator.equals("!=")) return (LHS != RHS); 
/* 346 */     if (operator.equals("<=")) return (LHS <= RHS); 
/* 347 */     if (operator.equals(">=")) return (LHS >= RHS); 
/* 348 */     if (operator.equals("<")) return (LHS < RHS); 
/* 349 */     if (operator.equals(">")) return (LHS > RHS); 
/* 350 */     if (operator.equals("&")) return (LHS > 0 && LHS < this.nextStringLiteral && RHS > 0 && RHS < this.nextStringLiteral); 
/* 351 */     if (operator.equals("&&")) return (LHS > 0 && LHS < this.nextStringLiteral && RHS > 0 && RHS < this.nextStringLiteral); 
/* 352 */     if (operator.equals("|")) return ((LHS > 0 && LHS < this.nextStringLiteral) || (RHS > 0 && RHS < this.nextStringLiteral)); 
/* 353 */     if (operator.equals("||")) return ((LHS > 0 && LHS < this.nextStringLiteral) || (RHS > 0 && RHS < this.nextStringLiteral));
/*     */     
/* 355 */     return false;
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
/*     */   protected int evaluateSingle(String single, int depth) {
/* 367 */     int value = getValue(single, depth);
/* 368 */     if (enableTrace) Log.info("[LOG]       Single [{0}]", new Object[] { Integer.valueOf(value) }); 
/* 369 */     return (value < this.nextStringLiteral) ? value : 1;
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
/*     */   protected int getValue(String expression, int depth) {
/* 381 */     boolean not = false;
/* 382 */     int intValue = 0;
/*     */ 
/*     */     
/* 385 */     if (expression.startsWith("!") && expression.length() > 1) {
/*     */       
/* 387 */       not = true;
/* 388 */       expression = expression.substring(1);
/*     */     } 
/*     */     
/* 391 */     if (Variable.isValidVariableName(expression)) {
/*     */       
/* 393 */       Object variableValue = this.variableProvider.getVariable(expression, this.macro);
/*     */       
/* 395 */       if (variableValue != null)
/*     */       {
/* 397 */         if (variableValue instanceof String) {
/*     */           
/* 399 */           setVariable(expression, (String)variableValue);
/*     */         }
/* 401 */         else if (variableValue instanceof Integer) {
/*     */           
/* 403 */           setVariable(expression, ((Integer)variableValue).intValue());
/*     */         }
/* 405 */         else if (variableValue instanceof Boolean) {
/*     */           
/* 407 */           setVariable(expression, ((Boolean)variableValue).booleanValue());
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 412 */     if (this.variables.containsKey(expression)) {
/*     */       
/* 414 */       intValue = ((Integer)this.variables.get(expression)).intValue();
/*     */     }
/* 416 */     else if (containsOperator(expression) || containsParentheses(expression)) {
/*     */       
/* 418 */       intValue = evaluate(expression, depth + 1);
/*     */     } else {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 424 */         intValue = Integer.parseInt(expression.replace('¬', '-'));
/*     */       }
/* 426 */       catch (NumberFormatException ex) {
/*     */         
/* 428 */         intValue = 0;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 433 */     if (not)
/*     */     {
/* 435 */       if (intValue < 1) { intValue = 1; } else { intValue = 0; }
/*     */     
/*     */     }
/* 438 */     return intValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isTrue(int value) {
/* 443 */     return (value != 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\ExpressionEvaluator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */