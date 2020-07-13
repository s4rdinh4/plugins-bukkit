/*     */ package net.eq2online.macros.core;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.core.params.MacroParam;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*     */ import net.eq2online.macros.interfaces.IMacroParamStorage;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*     */ import net.eq2online.macros.scripting.VariableExpander;
/*     */ import net.eq2online.macros.scripting.api.ICounterProvider;
/*     */ import net.eq2online.macros.scripting.api.IExpressionEvaluator;
/*     */ import net.eq2online.macros.scripting.api.IFlagProvider;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionContext;
/*     */ import net.eq2online.macros.scripting.api.IMutableArrayProvider;
/*     */ import net.eq2online.macros.scripting.api.IParameterProvider;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.IScriptParser;
/*     */ import net.eq2online.macros.scripting.api.IStringProvider;
/*     */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*     */ import net.eq2online.macros.scripting.exceptions.ScriptException;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.input.Mouse;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Macro
/*     */   implements IMacro, IMacroParamTarget
/*     */ {
/*     */   protected int id;
/*     */   protected File parentDir;
/*     */   protected MacroTemplate template;
/*  69 */   protected MacroPlaybackType playbackType = MacroPlaybackType.OneShot;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   protected MacroType triggerType = MacroType.Key;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   protected long keyDownRepeatRate = 1000L; protected long lastTriggerTime = 0L;
/*     */ 
/*     */ 
/*     */   
/*     */   protected String keyDownMacro;
/*     */ 
/*     */   
/*     */   protected String keyHeldMacro;
/*     */ 
/*     */   
/*     */   protected String keyUpMacro;
/*     */ 
/*     */   
/*     */   protected String macroCondition;
/*     */ 
/*     */   
/*     */   protected int iteration;
/*     */ 
/*     */   
/*     */   protected boolean stop;
/*     */ 
/*     */   
/*     */   protected boolean built = false;
/*     */ 
/*     */   
/* 104 */   protected long buildTime = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   private static SimpleDateFormat buildTimeFormatter = new SimpleDateFormat("hh:mm:ss");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   private static SimpleDateFormat runTimeFormatter = new SimpleDateFormat("s.S");
/*     */ 
/*     */   
/*     */   protected IScriptActionProvider scriptActionProvider;
/*     */ 
/*     */   
/*     */   protected IMacroActionContext context;
/*     */ 
/*     */   
/* 123 */   protected ArrayList<IVariableProvider> extraProviders = new ArrayList<IVariableProvider>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 128 */   protected HashMap<String, Object> variables = new HashMap<String, Object>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 133 */   protected HashMap<String, Object> stateData = new HashMap<String, Object>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MacroActionProcessor keyDownScriptActions;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MacroActionProcessor keyHeldScriptActions;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MacroActionProcessor keyUpScriptActions;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean keyWasDown = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 158 */   public static String paramPrefixOct = "$$";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 163 */   public static String pipeReplacement = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 168 */   public static String fileLineBreak = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 173 */   protected static Pattern stopPattern = Pattern.compile(MacroParams.paramSequence + "!");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 178 */   protected static Pattern filePattern = Pattern.compile(MacroParams.paramSequence + "\\<([a-z0-9\\x20_\\-\\.]+\\.txt)\\>", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 183 */   protected static Pattern scriptPattern = Pattern.compile(MacroParams.paramSequence + "\\{(.*?)(\\}\\x24\\x24|$)");
/*     */   
/* 185 */   private static Pattern promptActionPattern = Pattern.compile("(prompt)(\\()([^;]+)(\\))", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean killed = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean dirty = false;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean synchronous = false;
/*     */ 
/*     */ 
/*     */   
/*     */   protected MacroParams params;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String highlightMacro(String macro, MacroPlaybackType playbackType, char highlightColour, char normalColour, char endColour, char scriptColour) {
/* 209 */     macro = highlightParams(macro, playbackType, "§" + highlightColour, "§" + normalColour);
/*     */     
/* 211 */     String tempMacro = macro;
/* 212 */     macro = "";
/*     */ 
/*     */     
/* 215 */     Matcher scriptPatternMatcher = scriptPattern.matcher(tempMacro);
/*     */ 
/*     */     
/* 218 */     while (scriptPatternMatcher.find()) {
/*     */       
/* 220 */       char thisScriptColour = (scriptPatternMatcher.group(2).length() > 0) ? scriptColour : '9';
/* 221 */       macro = macro + tempMacro.substring(0, scriptPatternMatcher.start()) + "§" + thisScriptColour + highlightScript(scriptPatternMatcher.group(), thisScriptColour).replaceAll("§" + normalColour, "§" + thisScriptColour) + "§" + normalColour;
/* 222 */       tempMacro = tempMacro.substring(scriptPatternMatcher.end());
/* 223 */       scriptPatternMatcher.reset(tempMacro);
/*     */     } 
/*     */     
/* 226 */     macro = macro + tempMacro;
/*     */     
/* 228 */     if (playbackType != MacroPlaybackType.KeyState)
/*     */     {
/* 230 */       macro = stopPattern.matcher(macro).replaceAll("§" + highlightColour + "$0§" + endColour);
/*     */     }
/*     */     
/* 233 */     return macro;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String highlightScript(String macro, char scriptColour) {
/* 238 */     return ScriptContext.MAIN.getCore().highlight(macro, "§5", "§" + scriptColour);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String highlightParams(String macro, MacroPlaybackType playbackType, String prefix, String suffix) {
/* 249 */     macro = filePattern.matcher(macro).replaceAll(prefix + "$0" + suffix);
/*     */     
/* 251 */     if (playbackType == MacroPlaybackType.KeyState) return macro;
/*     */     
/* 253 */     macro = MacroParams.highlightParams(macro, prefix, suffix);
/* 254 */     return macro;
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
/*     */   public Macro(MacroTemplate owner, int macroId, MacroPlaybackType playbackType, MacroType triggerType, IMacroActionContext context) {
/* 267 */     this.template = owner;
/* 268 */     this.keyDownMacro = owner.getKeyDownMacro();
/* 269 */     this.parentDir = MacroModCore.getMacrosDirectory();
/* 270 */     this.id = macroId;
/* 271 */     this.context = context;
/* 272 */     this.scriptActionProvider = context.getProvider();
/*     */     
/* 274 */     this.triggerType = triggerType;
/* 275 */     this.playbackType = playbackType;
/*     */     
/* 277 */     this.params = new MacroParams(this);
/*     */     
/* 279 */     switch (playbackType) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case KeyState:
/* 285 */         this.keyHeldMacro = owner.getKeyHeldMacro();
/* 286 */         this.keyUpMacro = owner.getKeyUpMacro();
/* 287 */         this.keyDownRepeatRate = owner.repeatRate;
/*     */         break;
/*     */       
/*     */       case Conditional:
/* 291 */         this.keyUpMacro = owner.getKeyUpMacro();
/* 292 */         this.macroCondition = owner.getMacroCondition();
/*     */         break;
/*     */     } 
/*     */     
/* 296 */     initInstanceVariables(this);
/*     */     
/* 298 */     compile();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void recompile() {
/* 304 */     compile();
/* 305 */     this.iteration = 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void compile() {
/* 315 */     if (this.playbackType == MacroPlaybackType.KeyState) {
/*     */       
/* 317 */       compileKeyStateMacro();
/*     */     }
/*     */     else {
/*     */       
/* 321 */       if (this.playbackType == MacroPlaybackType.Conditional)
/*     */       {
/* 323 */         preCompileConditionalMacro();
/*     */       }
/*     */       
/* 326 */       compileMacro();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preCompileConditionalMacro() {
/* 335 */     String expandedCondition = (new VariableExpander(this.scriptActionProvider, this, this.macroCondition, true)).toString();
/* 336 */     IExpressionEvaluator evaluator = this.scriptActionProvider.getExpressionEvaluator(this, expandedCondition);
/*     */     
/* 338 */     if (evaluator.evaluate() == 0)
/*     */     {
/* 340 */       this.keyDownMacro = this.keyUpMacro;
/*     */     }
/*     */     
/* 343 */     this.playbackType = MacroPlaybackType.OneShot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void compileMacro() {
/* 351 */     String macro = processStops(this.keyDownMacro);
/* 352 */     macro = processIncludes(macro);
/* 353 */     macro = processEscapes(macro);
/* 354 */     macro = processStops(macro);
/* 355 */     macro = processPrompts(macro);
/*     */     
/* 357 */     this.keyDownMacro = macro;
/*     */ 
/*     */     
/* 360 */     this.params.evaluateParams();
/*     */ 
/*     */     
/* 363 */     this.iteration++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void compileKeyStateMacro() {
/* 371 */     this.keyDownMacro = processIncludes(this.keyDownMacro);
/* 372 */     this.keyHeldMacro = processIncludes(this.keyHeldMacro);
/* 373 */     this.keyUpMacro = processIncludes(this.keyUpMacro);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSynchronous(boolean synchronous) {
/* 379 */     this.synchronous = synchronous;
/* 380 */     if (synchronous)
/*     */     {
/* 382 */       this.playbackType = MacroPlaybackType.OneShot;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSynchronous() {
/* 389 */     return this.synchronous;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void build() {
/* 398 */     if (this.built)
/* 399 */       return;  this.built = true;
/* 400 */     this.buildTime = System.currentTimeMillis();
/*     */     
/* 402 */     if (this.context.getVariableProvider() instanceof IParameterProvider)
/*     */     {
/* 404 */       this.keyDownMacro = ((IParameterProvider)this.context.getVariableProvider()).provideParameters(this.keyDownMacro);
/*     */     }
/*     */     
/* 407 */     IScriptParser defaultParser = ScriptContext.MAIN.getCore().getParser();
/* 408 */     int maxInstructionsPerTick = MacroModSettings.maxInstructionsPerTick;
/* 409 */     int maxExecutionTime = MacroModSettings.maxExecutionTime;
/*     */     
/* 411 */     this.keyDownScriptActions = MacroActionProcessor.compile(defaultParser, this.keyDownMacro, maxInstructionsPerTick, maxExecutionTime);
/*     */     
/* 413 */     if (this.playbackType == MacroPlaybackType.KeyState) {
/*     */       
/* 415 */       this.keyHeldScriptActions = MacroActionProcessor.compile(defaultParser, this.keyHeldMacro, maxInstructionsPerTick, maxExecutionTime);
/* 416 */       this.keyUpScriptActions = MacroActionProcessor.compile(defaultParser, this.keyUpMacro, maxInstructionsPerTick, maxExecutionTime);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refreshPermissions() {
/* 425 */     if (this.keyDownScriptActions != null) {
/* 426 */       this.keyDownScriptActions.refreshPermissions();
/*     */     }
/* 428 */     if (this.keyHeldScriptActions != null) {
/* 429 */       this.keyHeldScriptActions.refreshPermissions();
/*     */     }
/* 431 */     if (this.keyUpScriptActions != null) {
/* 432 */       this.keyUpScriptActions.refreshPermissions();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleCompleted() {
/* 441 */     this.template.getMacroManager().playMacro(this, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleCancelled() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMacroActionContext getContext() {
/* 455 */     return this.context;
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
/*     */   public boolean playMacro(boolean keyDown, boolean clock) throws ScriptException {
/* 467 */     if (this.killed) return false;
/*     */     
/* 469 */     build();
/*     */     
/* 471 */     if (this.playbackType == MacroPlaybackType.OneShot) {
/*     */       
/* 473 */       if (this.synchronous) {
/*     */         
/* 475 */         while (this.keyDownScriptActions.execute(this, this.context, this.stop, true, clock) && !this.killed);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 480 */         return false;
/*     */       } 
/*     */       
/* 483 */       return (this.keyDownScriptActions.execute(this, this.context, this.stop, true, clock) && !this.killed);
/*     */     } 
/*     */     
/* 486 */     this.keyWasDown |= keyDown;
/*     */     
/* 488 */     boolean keyDownStillPlaying = this.keyDownScriptActions.execute(this, this.context, this.stop, true, clock);
/*     */     
/* 490 */     if (keyDown) {
/*     */       
/* 492 */       long sinceLastTrigger = System.currentTimeMillis() - this.lastTriggerTime;
/*     */       
/* 494 */       if (sinceLastTrigger > this.keyDownRepeatRate) {
/*     */         
/* 496 */         this.lastTriggerTime = System.currentTimeMillis();
/* 497 */         this.keyHeldScriptActions.execute(this, this.context, false, false, clock);
/*     */       } 
/*     */       
/* 500 */       return !this.killed;
/*     */     } 
/* 502 */     if (this.keyWasDown)
/*     */     {
/* 504 */       return (this.keyUpScriptActions.execute(this, this.context, this.stop, true, clock) | keyDownStillPlaying && !this.killed);
/*     */     }
/*     */     
/* 507 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String processEscapes(String macro) {
/* 516 */     macro = macro.replaceAll(MacroParams.escapeCharacter + MacroParams.paramPrefix, MacroParams.nonPrintingEscapeCharacter + escapeReplacement(paramPrefixOct));
/* 517 */     macro = macro.replaceAll(MacroParams.escapeCharacter + "\\x7C", pipeReplacement);
/* 518 */     return macro;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String processStops(String macro) {
/* 527 */     Matcher stopPatternMatcher = stopPattern.matcher(macro);
/*     */     
/* 529 */     if (stopPatternMatcher.find()) {
/*     */       
/* 531 */       macro = macro.substring(0, stopPatternMatcher.start());
/* 532 */       this.stop = true;
/*     */     } 
/*     */     
/* 535 */     return macro;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String processPrompts(String macro) {
/* 540 */     if (macro == null) return null;
/*     */     
/* 542 */     Matcher prompt = promptActionPattern.matcher(macro);
/*     */     
/* 544 */     while (prompt.find()) {
/*     */       
/* 546 */       String parsedPrompt = prompt.group(1) + "£" + prompt.group(2) + prompt.group(3).replaceAll(MacroParams.paramPrefix, "££") + prompt.group(4);
/* 547 */       macro = macro.substring(0, prompt.start()) + parsedPrompt + macro.substring(prompt.end());
/* 548 */       prompt.reset(macro);
/*     */     } 
/*     */     
/* 551 */     return macro;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String processIncludes(String macro) {
/* 562 */     if (macro == null) return null;
/*     */     
/* 564 */     int fileReplacementCounter = 0;
/* 565 */     Matcher filePatternMatcher = filePattern.matcher(macro);
/*     */     
/* 567 */     while (filePatternMatcher.find()) {
/*     */       
/* 569 */       fileReplacementCounter++;
/* 570 */       macro = macro.substring(0, filePatternMatcher.start()) + getFileContents(filePatternMatcher.group(1), fileReplacementCounter) + macro.substring(filePatternMatcher.end());
/* 571 */       filePatternMatcher.reset(macro);
/*     */     } 
/*     */     
/* 574 */     return macro;
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
/*     */   protected String getFileContents(String fileName, int fileReplacementCounter) {
/* 586 */     if (fileReplacementCounter > MacroModSettings.maxIncludes || MacroModCore.checkDisallowedTextFileName(fileName)) return "";
/*     */     
/* 588 */     File includeFile = new File(this.parentDir, fileName);
/* 589 */     String fileContents = "";
/*     */ 
/*     */     
/* 592 */     if (includeFile.exists()) {
/*     */       
/*     */       try {
/*     */         
/* 596 */         BufferedReader bufferedreader = new BufferedReader(new FileReader(includeFile));
/*     */         
/* 598 */         for (String s = ""; (s = bufferedreader.readLine()) != null; ) {
/*     */           
/* 600 */           s = s.trim();
/* 601 */           if (s != "") {
/* 602 */             fileContents = fileContents + ((fileContents.length() == 0) ? s : (fileLineBreak + s));
/*     */           }
/*     */         } 
/* 605 */         bufferedreader.close();
/*     */       }
/* 607 */       catch (Exception ex) {
/*     */         
/* 609 */         Log.info("Macro [{0}] Failed loading include file '{1}'", new Object[] { Integer.valueOf(this.id), fileName });
/*     */       } 
/*     */     }
/*     */     
/* 613 */     return fileContents;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initInstanceVariables(IMacro instance) {
/* 618 */     instance.setVariable("~CTRL", (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)));
/* 619 */     instance.setVariable("~ALT", (Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184)));
/* 620 */     instance.setVariable("~SHIFT", (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)));
/*     */     
/* 622 */     instance.setVariable("~LMOUSE", Mouse.isButtonDown(0));
/* 623 */     instance.setVariable("~RMOUSE", Mouse.isButtonDown(1));
/* 624 */     instance.setVariable("~MIDDLEMOUSE", Mouse.isButtonDown(2));
/*     */     
/* 626 */     for (int key = 0; key < 255; key++) {
/*     */       
/* 628 */       String keyName = Keyboard.getKeyName(key);
/*     */       
/* 630 */       if (keyName != null) {
/* 631 */         instance.setVariable("~KEY_" + keyName.toUpperCase(), Keyboard.isKeyDown(key));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateVariables(boolean clock) {
/* 638 */     this.template.updateVariables(clock);
/*     */     
/* 640 */     if (this.context.getVariableProvider() != null)
/*     */     {
/* 642 */       this.context.getVariableProvider().updateVariables(clock);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getVariable(String variableName) {
/* 649 */     for (IVariableProvider extraProvider : this.extraProviders) {
/*     */       
/* 651 */       Object variableValue = extraProvider.getVariable(variableName);
/* 652 */       if (variableValue != null) return variableValue;
/*     */     
/*     */     } 
/* 655 */     if (this.context.getVariableProvider() != null) {
/*     */       
/* 657 */       Object variableValue = this.context.getVariableProvider().getVariable(variableName);
/* 658 */       if (variableValue != null) return variableValue;
/*     */     
/*     */     } 
/* 661 */     if (this.variables.containsKey(variableName))
/*     */     {
/* 663 */       return this.variables.get(variableName);
/*     */     }
/*     */     
/* 666 */     return this.template.getVariable(variableName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getVariables() {
/* 672 */     Set<String> variables = new HashSet<String>();
/*     */     
/* 674 */     for (IVariableProvider extraProvider : this.extraProviders)
/*     */     {
/* 676 */       variables.addAll(extraProvider.getVariables());
/*     */     }
/*     */     
/* 679 */     if (this.context.getVariableProvider() != null)
/*     */     {
/* 681 */       variables.addAll(this.context.getVariableProvider().getVariables());
/*     */     }
/*     */     
/* 684 */     variables.addAll(this.variables.keySet());
/* 685 */     variables.addAll(this.template.getVariables());
/*     */     
/* 687 */     return variables;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVariable(String variableName, boolean variableValue) {
/* 693 */     this.variables.put(variableName, Boolean.valueOf(variableValue));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVariable(String variableName, int variableValue) {
/* 699 */     this.variables.put(variableName, Integer.valueOf(variableValue));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVariable(String variableName, String variableValue) {
/* 705 */     this.variables.put(variableName, variableValue);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVariables(Map<String, Object> variables) {
/* 711 */     this.variables.putAll(variables);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerVariableProvider(IVariableProvider variableProvider) {
/* 717 */     if (!this.extraProviders.contains(variableProvider))
/*     */     {
/* 719 */       this.extraProviders.add(variableProvider);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregisterVariableProvider(IVariableProvider variableProvider) {
/* 726 */     this.extraProviders.remove(variableProvider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getID() {
/* 737 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDisplayName() {
/* 746 */     return this.triggerType.getName(this.id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPromptMessage() {
/* 755 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MacroPlaybackType getPlaybackType() {
/* 765 */     return this.playbackType;
/*     */   }
/*     */ 
/*     */   
/*     */   public MacroType getTriggerType() {
/* 770 */     return this.triggerType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MacroTemplate getTemplate() {
/* 780 */     return this.template;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMacroParamStorage getParamStore() {
/* 789 */     return this.template;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IFlagProvider getFlagProvider() {
/* 798 */     return (IFlagProvider)this.template;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ICounterProvider getCounterProvider() {
/* 807 */     return (ICounterProvider)this.template;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IStringProvider getStringProvider() {
/* 816 */     return (IStringProvider)this.template;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMutableArrayProvider getArrayProvider() {
/* 822 */     return (IMutableArrayProvider)this.template;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIteration() {
/* 833 */     return this.iteration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIterationString() {
/* 844 */     return this.iteration + getOrdinalSuffix(this.iteration);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTargetString() {
/* 855 */     return this.keyDownMacro;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTargetString(String newScript) {
/* 861 */     this.keyDownMacro = newScript;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {
/* 870 */     this.dirty = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDirty() {
/* 879 */     return this.dirty;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void kill() {
/* 888 */     this.killed = true;
/*     */     
/* 890 */     if (this.keyDownScriptActions != null) this.keyDownScriptActions.onStopped(this, this.context); 
/* 891 */     if (this.keyHeldScriptActions != null) this.keyHeldScriptActions.onStopped(this, this.context); 
/* 892 */     if (this.keyUpScriptActions != null) this.keyUpScriptActions.onStopped(this, this.context);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDead() {
/* 901 */     return this.killed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashMap<String, Object> getStateData() {
/* 910 */     return this.stateData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getState(String key) {
/* 919 */     return this.stateData.get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setState(String key, Object value) {
/* 928 */     this.stateData.put(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasRemainingParams() {
/* 934 */     if (!this.triggerType.supportsParams())
/*     */     {
/* 936 */       return false;
/*     */     }
/*     */     
/* 939 */     if (this.triggerType == MacroType.Control && !DesignableGuiControl.getControl(this.id).getCloseGuiOnClick())
/*     */     {
/* 941 */       return false;
/*     */     }
/*     */     
/* 944 */     return this.params.hasRemainingParams();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MacroParam getNextParam() {
/* 950 */     return this.params.getNextParam();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 956 */     long runTime = System.currentTimeMillis() - this.buildTime;
/*     */     
/* 958 */     return String.format("§aMacro §f[§4%s§f]§a running start §2%s§a run §2%s", new Object[] { this.triggerType.getName(this.id), buildTimeFormatter.format(Long.valueOf(this.buildTime)), runTimeFormatter.format(Long.valueOf(runTime)) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onInit() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getOrdinalSuffix(int value) {
/* 974 */     int teenRemainder = value % 100;
/* 975 */     if (teenRemainder > 9 && teenRemainder < 21) return "th"; 
/* 976 */     switch (value % 10) {
/*     */       case 1:
/* 978 */         return "st";
/* 979 */       case 2: return "nd";
/* 980 */       case 3: return "rd";
/* 981 */     }  return "th";
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
/*     */   public static String escapeReplacement(String param) {
/* 993 */     if (param == null) return "";
/*     */     
/* 995 */     return param.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$");
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\Macro.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */