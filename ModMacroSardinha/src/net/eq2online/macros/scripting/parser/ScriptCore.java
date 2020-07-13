/*     */ package net.eq2online.macros.scripting.parser;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.compatibility.Reflection;
/*     */ import net.eq2online.macros.scripting.IActionFilter;
/*     */ import net.eq2online.macros.scripting.IDocumentor;
/*     */ import net.eq2online.macros.scripting.IErrorLogger;
/*     */ import net.eq2online.macros.scripting.ScriptActionBase;
/*     */ import net.eq2online.macros.scripting.VariableExpander;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventManager;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventProvider;
/*     */ import net.eq2online.macros.scripting.api.IMessageFilter;
/*     */ import net.eq2online.macros.scripting.api.IScriptAction;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.IScriptParser;
/*     */ import net.eq2online.macros.scripting.api.IScriptedIterator;
/*     */ import net.eq2online.macros.scripting.api.IVariableListener;
/*     */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*     */ 
/*     */ public final class ScriptCore {
/*  29 */   private static final Map<ScriptContext, ScriptCore> contexts = new HashMap<ScriptContext, ScriptCore>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ScriptContext context;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IScriptActionProvider activeProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IMacroEventManager eventManager;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IScriptParser parser;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IMessageFilter messageFilter;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   private final Map<String, IScriptAction> actions = new HashMap<String, IScriptAction>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private final List<IScriptAction> actionsList = new LinkedList<IScriptAction>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   private Map<String, Class<? extends IScriptedIterator>> iterators = new HashMap<String, Class<? extends IScriptedIterator>>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   private Pattern actionRegex = Pattern.compile("");
/*     */ 
/*     */ 
/*     */   
/*     */   private IDocumentor documentor;
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean createCoreForContext(ScriptContext context, IScriptActionProvider provider, IMacroEventManager eventManager, IScriptParser defaultParser, IErrorLogger logger, IDocumentor documentor) {
/*  84 */     if (!contexts.containsKey(context) && !context.isCreated()) {
/*     */       
/*  86 */       ScriptCore core = new ScriptCore(context, provider, eventManager, defaultParser, logger, documentor);
/*  87 */       contexts.put(context, core);
/*  88 */       return true;
/*     */     } 
/*     */     
/*  91 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ScriptCore(ScriptContext context, IScriptActionProvider provider, IMacroEventManager eventManager, IScriptParser parser, IErrorLogger logger, IDocumentor documentor) {
/* 102 */     this.context = context;
/* 103 */     this.context.setCore(this);
/*     */     
/* 105 */     registerScriptActionProvider(provider);
/* 106 */     this.eventManager = eventManager;
/* 107 */     this.parser = parser;
/* 108 */     this.documentor = documentor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerScriptAction(IScriptAction action) {
/*     */     try {
/* 120 */       registerAction(action);
/*     */     }
/* 122 */     catch (Throwable ex) {
/*     */       
/* 124 */       ex.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IScriptAction getAction(String actionName) {
/* 130 */     return this.actions.get(actionName.toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean registerAction(IScriptAction newAction) {
/* 140 */     if (this.actions.containsKey(newAction.toString())) {
/* 141 */       return false;
/*     */     }
/* 143 */     this.actions.put(newAction.toString(), newAction);
/* 144 */     this.actionsList.add(newAction);
/* 145 */     this.documentor.setDocumentation(newAction);
/* 146 */     updateScriptActionRegex();
/*     */     
/* 148 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateScriptActionRegex() {
/* 156 */     String actionList = "";
/* 157 */     char separator = '(';
/*     */     
/* 159 */     for (IScriptAction action : this.actions.values()) {
/*     */       
/* 161 */       actionList = actionList + separator + action.toString();
/* 162 */       separator = '|';
/*     */     } 
/*     */     
/* 165 */     actionList = actionList + ")";
/*     */     
/* 167 */     this.actionRegex = Pattern.compile(actionList + "([\\(;]|$)", 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, IScriptAction> getActions() {
/* 175 */     return Collections.unmodifiableMap(this.actions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IScriptAction> getActionsList() {
/* 183 */     return this.actionsList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerScriptActionProvider(IScriptActionProvider provider) {
/* 193 */     this.activeProvider = provider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IScriptActionProvider getScriptActionProvider() {
/* 203 */     return this.activeProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IScriptParser getParser() {
/* 213 */     return this.parser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IDocumentor getDocumentor() {
/* 223 */     return this.documentor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerVariableProvider(IVariableProvider variableProvider) {
/* 233 */     if (this.activeProvider != null)
/*     */     {
/* 235 */       this.activeProvider.registerVariableProvider(variableProvider);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerVariableListener(IVariableListener variableListener) {
/* 246 */     if (this.activeProvider != null)
/*     */     {
/* 248 */       this.activeProvider.registerVariableListener(variableListener);
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
/*     */   
/*     */   public static void setVariable(IScriptActionProvider provider, IMacro macro, String variableName, String variableValue) {
/* 262 */     int intValue = tryParseInt(variableValue, 0);
/* 263 */     boolean boolValue = parseBoolean(variableValue, intValue);
/*     */ 
/*     */     
/* 266 */     if (variableValue == null) variableValue = "";
/*     */     
/* 268 */     provider.setVariable(macro, variableName, variableValue, intValue, boolValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setVariable(IScriptActionProvider provider, IMacro macro, String variableName, int variableValue) {
/* 279 */     if (variableName.length() > 0)
/*     */     {
/* 281 */       provider.setVariable(macro, variableName, String.valueOf(variableValue), variableValue, (variableValue != 0));
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
/*     */ 
/*     */   
/*     */   public static String parseVars(IScriptActionProvider provider, IMacro macro, String text, boolean quoteStrings) {
/* 296 */     return (new VariableExpander(provider, macro, text, quoteStrings)).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerIterator(String iteratorName, Class<? extends IScriptedIterator> iterator) {
/* 307 */     if (this.iterators.containsKey(iteratorName)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 313 */     this.iterators.put(iteratorName, iterator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<? extends IScriptedIterator> getIterator(String iteratorName) {
/* 324 */     return this.iterators.get(iteratorName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerEventProvider(IMacroEventProvider eventProvider) {
/* 329 */     if (this.eventManager != null)
/*     */     {
/* 331 */       this.eventManager.registerEventProvider(eventProvider);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerMessageFilter(IMessageFilter messageFilter) {
/* 337 */     this.messageFilter = messageFilter;
/*     */   }
/*     */ 
/*     */   
/*     */   public IMessageFilter getMessageFilter() {
/* 342 */     return this.messageFilter;
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
/*     */   void initActions(IActionFilter actionFilter, IErrorLogger logger) {
/*     */     try {
/* 355 */       Class<? extends ScriptActionBase> packageClass = (Class)Class.forName("net.eq2online.macros.scripting.actions.lang.ScriptActionAssign");
/* 356 */       LinkedList<Class<? extends ScriptActionBase>> actions = Reflection.getSubclassesFor(ScriptActionBase.class, packageClass, "ScriptAction", logger);
/* 357 */       int loadedActions = 0;
/*     */       
/* 359 */       for (Class<? extends IScriptAction> action : actions) {
/*     */ 
/*     */         
/*     */         try {
/* 363 */           IScriptAction newAction = null;
/* 364 */           Constructor<IScriptAction> ctor = (Constructor)action.getDeclaredConstructor(new Class[] { ScriptContext.class });
/* 365 */           if (ctor != null) {
/*     */             
/* 367 */             newAction = ctor.newInstance(new Object[] { this.context });
/*     */             
/* 369 */             if (newAction != null)
/*     */             {
/* 371 */               if (actionFilter.pass(this.context, this, newAction))
/*     */               {
/* 373 */                 if (registerAction(newAction))
/* 374 */                   loadedActions++; 
/*     */               }
/*     */             }
/*     */           } 
/*     */         } catch (Exception ex) {
/* 379 */           ex.printStackTrace();
/*     */         } 
/*     */       } 
/* 382 */       Log.info("Script engine initialised, registered {0} script action(s)", new Object[] { Integer.valueOf(loadedActions) });
/*     */ 
/*     */       
/* 385 */       if (loadedActions == 0)
/*     */       {
/* 387 */         logger.logError("Script engine initialisation error");
/*     */       }
/*     */     }
/* 390 */     catch (ClassNotFoundException ex) {
/*     */       
/* 392 */       logger.logError("Script engine initialisation error, package not found");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean parseBoolean(String variableValue) {
/* 403 */     int intValue = tryParseInt(variableValue, 0);
/* 404 */     return parseBoolean(variableValue, intValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean parseBoolean(String variableValue, int intValue) {
/* 414 */     return (variableValue == null || variableValue.toLowerCase().equals("true") || intValue != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int tryParseInt(String value, int defaultValue) {
/* 425 */     if (value == null) return defaultValue;  
/* 426 */     try { return Integer.parseInt(value.trim().replaceAll(",", "")); } catch (NumberFormatException numberFormatException) { return defaultValue; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long tryParseLong(String value, long defaultValue) {
/*     */     
/* 437 */     try { return Long.parseLong(value.trim()); } catch (NumberFormatException numberFormatException) { return defaultValue; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float tryParseFloat(String value, float defaultValue) {
/*     */     
/* 448 */     try { return Float.parseFloat(value.trim()); } catch (NumberFormatException numberFormatException) { return defaultValue; }
/*     */   
/*     */   }
/*     */   
/*     */   public static int tryParseIntOffset(String value, int defaultValue, int source) {
/* 453 */     int offsetDirection = 1;
/*     */     
/* 455 */     if (value.startsWith("+")) {
/*     */       
/* 457 */       value = value.substring(1);
/*     */     }
/* 459 */     else if (value.startsWith("-")) {
/*     */       
/* 461 */       offsetDirection = -1;
/* 462 */       value = value.substring(1);
/*     */     }
/*     */     else {
/*     */       
/* 466 */       source = 0;
/*     */     } 
/*     */     
/* 469 */     return source + tryParseInt(value, defaultValue) * offsetDirection;
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
/*     */   public static final String[] tokenize(String text, char separator, char firstParamQuote, char otherParamsQuote, char escape, StringBuilder rawString) {
/* 482 */     ArrayList<String> params = new ArrayList<String>();
/*     */ 
/*     */     
/* 485 */     StringBuilder currentParam = new StringBuilder();
/* 486 */     boolean escaped = false;
/* 487 */     boolean quoted = false;
/* 488 */     boolean emptyParam = true;
/* 489 */     char quote = firstParamQuote;
/*     */ 
/*     */     
/* 492 */     for (int charPos = 0; charPos < text.length(); charPos++) {
/*     */       
/* 494 */       char currentChar = text.charAt(charPos);
/*     */       
/* 496 */       if (currentChar == escape) {
/*     */         
/* 498 */         if (escaped)
/*     */         {
/* 500 */           escaped = false;
/* 501 */           currentParam.append(escape);
/*     */         }
/*     */         else
/*     */         {
/* 505 */           escaped = true;
/*     */         }
/*     */       
/* 508 */       } else if (currentChar == quote) {
/*     */         
/* 510 */         if (escaped)
/*     */         {
/* 512 */           escaped = false;
/* 513 */           currentParam.append(currentChar);
/* 514 */           emptyParam = false;
/*     */         }
/* 516 */         else if ((currentParam.length() == 0 || currentParam.toString().matches("^\\s+$")) && !quoted)
/*     */         {
/* 518 */           currentParam = new StringBuilder();
/* 519 */           quoted = true;
/* 520 */           emptyParam = false;
/*     */         }
/* 522 */         else if (quoted)
/*     */         {
/* 524 */           quoted = false;
/*     */         }
/*     */         else
/*     */         {
/* 528 */           currentParam.append(currentChar);
/* 529 */           emptyParam = false;
/*     */         }
/*     */       
/* 532 */       } else if (currentChar == separator) {
/*     */         
/* 534 */         if (escaped)
/*     */         {
/* 536 */           escaped = false;
/* 537 */           currentParam.append(currentChar);
/* 538 */           emptyParam = false;
/*     */         }
/* 540 */         else if (quoted)
/*     */         {
/* 542 */           currentParam.append(currentChar);
/* 543 */           emptyParam = false;
/*     */         }
/*     */         else
/*     */         {
/* 547 */           quote = otherParamsQuote;
/* 548 */           params.add(currentParam.toString());
/* 549 */           if (rawString != null) rawString.append(" ").append(currentParam); 
/* 550 */           currentParam = new StringBuilder();
/* 551 */           emptyParam = true;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 556 */         if (escaped) {
/*     */           
/* 558 */           escaped = false;
/* 559 */           currentParam.append(escape);
/*     */         } 
/*     */         
/* 562 */         currentParam.append(currentChar);
/* 563 */         emptyParam = false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 568 */     if (!emptyParam) {
/*     */       
/* 570 */       params.add(currentParam.toString());
/* 571 */       if (rawString != null) rawString.append(" ").append(currentParam);
/*     */     
/* 573 */     } else if (text.length() > 0 && text.endsWith(String.valueOf(separator))) {
/*     */       
/* 575 */       params.add("");
/*     */     } 
/*     */     
/* 578 */     return params.<String>toArray(new String[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public String highlight(String text) {
/* 583 */     return highlight(text, String.valueOf('�'), String.valueOf('￼'));
/*     */   }
/*     */ 
/*     */   
/*     */   public String highlight(String text, String prefix, String suffix) {
/* 588 */     return this.actionRegex.matcher(text).replaceAll(prefix + "$1" + suffix + "$2");
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\parser\ScriptCore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */