/*      */ package net.eq2online.macros.core;
/*      */ 
/*      */ import java.io.PrintWriter;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.TreeMap;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import net.eq2online.console.Log;
/*      */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*      */ import net.eq2online.macros.core.params.MacroParam;
/*      */ import net.eq2online.macros.core.params.providers.MacroParamProviderNamed;
/*      */ import net.eq2online.macros.core.params.providers.MacroParamProviderPreset;
/*      */ import net.eq2online.macros.interfaces.IMacroParamStorage;
/*      */ import net.eq2online.macros.scripting.api.IMacroActionContext;
/*      */ import net.eq2online.macros.scripting.variable.VariableProviderArray;
/*      */ import net.eq2online.xml.Xml;
/*      */ import org.lwjgl.input.Keyboard;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.Node;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MacroTemplate
/*      */   extends VariableProviderArray
/*      */   implements IMacroParamStorage
/*      */ {
/*      */   protected int id;
/*      */   protected Macros macros;
/*   53 */   protected MacroPlaybackType playbackType = MacroPlaybackType.OneShot;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   59 */   protected MacroType macroType = MacroType.Key;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   64 */   public int repeatRate = 1000;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   69 */   protected String keyDownMacro = ""; protected String keyHeldMacro = ""; protected String keyUpMacro = ""; protected String macroCondition = "";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean requireControl;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean requireAlt;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean requireShift;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean alwaysOverride;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean inhibitParamLoad;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean global;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  104 */   private String parameter = "";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  109 */   private TreeMap<String, String> namedParameters = new TreeMap<String, String>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  114 */   private String item = "";
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean firstItemOnly = false;
/*      */ 
/*      */   
/*  121 */   private String friend = "";
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean firstFriendOnly = false;
/*      */ 
/*      */   
/*  128 */   private String onlineUser = "";
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean firstOnlineUserOnly = false;
/*      */ 
/*      */   
/*  135 */   private String town = "";
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean firstTownOnly = false;
/*      */ 
/*      */   
/*  142 */   private String warp = "";
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean firstWarpOnly = false;
/*      */ 
/*      */   
/*  149 */   private String file = "";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  154 */   private String home = "";
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean firstHomeOnly = false;
/*      */ 
/*      */   
/*  161 */   private String place = "";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  166 */   private String[] presetText = new String[] { "", "", "", "", "", "", "", "", "", "" };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  171 */   private static Pattern presetTextConfigPattern = Pattern.compile("^PresetText\\[([0-9])\\]$");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  176 */   private static Pattern namedParameterConfigPattern = Pattern.compile("^NamedParam\\[([A-Za-z0-9]{1,32})\\]$");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  181 */   private static Pattern stringVariableConfigPattern = Pattern.compile("^String\\[([a-z]([a-z0-9_\\-]*))\\]$");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  186 */   public static final Pattern namedParameterPattern = Pattern.compile("^[A-Za-z0-9]{1,32}$");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  191 */   public static final Pattern propertyPattern = Pattern.compile("^Property\\[([A-Za-z0-9]{1,32})\\]$");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  196 */   protected HashMap<String, Boolean> flags = new HashMap<String, Boolean>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  201 */   protected HashMap<String, Integer> counters = new HashMap<String, Integer>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  206 */   protected HashMap<String, String> strings = new HashMap<String, String>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  211 */   protected TreeMap<String, String> properties = new TreeMap<String, String>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  216 */   protected int debounceTicks = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MacroTemplate(Macros macros, int macroId) {
/*  227 */     this.macros = macros;
/*  228 */     this.id = macroId;
/*  229 */     this.macroType = MacroType.getFromID(macroId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MacroTemplate(int macroId, MacroTemplate sourceTemplate) {
/*  240 */     this.id = macroId;
/*  241 */     this.macros = sourceTemplate.macros;
/*  242 */     this.playbackType = sourceTemplate.getPlaybackType();
/*  243 */     this.macroType = sourceTemplate.getMacroType();
/*  244 */     this.keyDownMacro = sourceTemplate.getKeyDownMacro();
/*  245 */     this.keyHeldMacro = sourceTemplate.getKeyHeldMacro();
/*  246 */     this.keyUpMacro = sourceTemplate.getKeyUpMacro();
/*  247 */     this.macroCondition = sourceTemplate.getMacroCondition();
/*  248 */     this.repeatRate = sourceTemplate.repeatRate;
/*  249 */     this.requireControl = sourceTemplate.requireControl;
/*  250 */     this.inhibitParamLoad = sourceTemplate.inhibitParamLoad;
/*  251 */     this.requireAlt = sourceTemplate.requireAlt;
/*  252 */     this.requireShift = sourceTemplate.requireShift;
/*  253 */     this.global = sourceTemplate.global;
/*  254 */     this.alwaysOverride = sourceTemplate.alwaysOverride;
/*      */   }
/*      */ 
/*      */   
/*      */   public void onTick() {
/*  259 */     if (this.debounceTicks > 0) {
/*  260 */       this.debounceTicks--;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setID(int newID) {
/*  270 */     this.id = newID;
/*  271 */     this.macroType = MacroType.getFromID(newID);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Macros getMacroManager() {
/*  281 */     return this.macros;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getID() {
/*  291 */     return this.id;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlaybackType(MacroPlaybackType playbackType) {
/*  299 */     if (this.macroType == MacroType.Key || playbackType != MacroPlaybackType.KeyState) {
/*  300 */       this.playbackType = playbackType;
/*      */     } else {
/*  302 */       this.playbackType = MacroPlaybackType.OneShot;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setKeyDownMacro(String macro) {
/*  312 */     this.keyDownMacro = macro;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setKeyHeldMacro(String macro) {
/*  322 */     this.keyHeldMacro = macro;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setKeyUpMacro(String macro) {
/*  332 */     this.keyUpMacro = macro;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMacroCondition(String condition) {
/*  342 */     if (condition.equalsIgnoreCase("true") || condition.equals("1")) {
/*  343 */       this.macroCondition = "";
/*      */     } else {
/*  345 */       this.macroCondition = condition;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MacroPlaybackType getPlaybackType() {
/*  354 */     return this.playbackType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MacroType getMacroType() {
/*  365 */     return this.macroType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getKeyDownMacro() {
/*  375 */     return this.keyDownMacro;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getKeyHeldMacro() {
/*  383 */     return this.keyHeldMacro;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getKeyUpMacro() {
/*  391 */     return this.keyUpMacro;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getMacroCondition() {
/*  396 */     return (this.macroCondition.length() > 0) ? this.macroCondition : "TRUE";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getKeyDownMacroHoverText() {
/*  406 */     return (this.keyDownMacro.length() > 0) ? this.keyDownMacro : LocalisationProvider.getLocalisedString("macro.hover.empty");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getKeyHeldMacroHoverText() {
/*  416 */     return (this.keyHeldMacro.length() > 0) ? this.keyHeldMacro : LocalisationProvider.getLocalisedString("macro.hover.empty");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getKeyUpMacroHoverText() {
/*  426 */     return (this.keyUpMacro.length() > 0) ? this.keyUpMacro : LocalisationProvider.getLocalisedString("macro.hover.empty");
/*      */   }
/*      */ 
/*      */   
/*      */   public String getConditionHovertext() {
/*  431 */     return "IF " + ((this.macroCondition.length() > 0) ? this.macroCondition : "TRUE");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  441 */     return ((this.playbackType == MacroPlaybackType.OneShot && this.keyDownMacro
/*  442 */       .length() == 0) || (this.playbackType == MacroPlaybackType.KeyState && this.keyDownMacro
/*  443 */       .length() == 0 && this.keyHeldMacro
/*  444 */       .length() == 0 && this.keyUpMacro
/*  445 */       .length() == 0) || (this.playbackType == MacroPlaybackType.Conditional && this.keyDownMacro
/*  446 */       .length() == 0 && this.keyUpMacro
/*  447 */       .length() == 0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getModifiers() {
/*  459 */     String modifiers = this.requireControl ? "<CTRL> " : "";
/*  460 */     modifiers = modifiers + (this.requireAlt ? "<ALT> " : "");
/*  461 */     modifiers = modifiers + (this.requireShift ? "<SHIFT> " : "");
/*  462 */     return modifiers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getStoredParam(MacroParamProvider provider) {
/*      */     int index;
/*  477 */     if (this.inhibitParamLoad) return "";
/*      */     
/*  479 */     switch (provider.getType()) {
/*      */       case Normal:
/*  481 */         return this.parameter;
/*  482 */       case NamedParam: return getNamedParameter(((MacroParamProviderNamed)provider).getNextNamedVar());
/*  483 */       case Item: return this.item;
/*  484 */       case Friend: return this.friend;
/*  485 */       case OnlineUser: return this.onlineUser;
/*  486 */       case Town: return this.town;
/*  487 */       case Warp: return this.warp;
/*  488 */       case Home: return this.home;
/*  489 */       case Place: return this.place;
/*      */       case Preset:
/*  491 */         index = ((MacroParamProviderPreset)provider).getNextPresetIndex();
/*  492 */         return (index > -1 && index < 10) ? this.presetText[index] : "";
/*  493 */       case File: return this.file;
/*  494 */     }  return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStoredParam(MacroParam.Type type, String param) {
/*  507 */     setStoredParam(type, 0, param);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStoredParam(MacroParam.Type type, int index, String param) {
/*  520 */     setStoredParam(type, index, "", param);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStoredParam(MacroParam.Type type, int index, String name, String param) {
/*  534 */     switch (type) {
/*      */       case Normal:
/*  536 */         this.parameter = param; saveTemplates(); break;
/*  537 */       case NamedParam: setNamedParameter(name, param); saveTemplates(); break;
/*  538 */       case Item: this.item = param; saveTemplates(); break;
/*  539 */       case Friend: this.friend = param; saveTemplates(); break;
/*  540 */       case OnlineUser: this.onlineUser = param; break;
/*  541 */       case Town: this.town = param; saveTemplates(); break;
/*  542 */       case Warp: this.warp = param; saveTemplates(); break;
/*  543 */       case Home: this.home = param; saveTemplates(); break;
/*  544 */       case Place: this.place = param; saveTemplates(); break;
/*  545 */       case File: this.file = param; saveTemplates(); break;
/*  546 */       case Preset: if (index > -1 && index < 10) { this.presetText[index] = param; saveTemplates(); }
/*      */         
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNamedParameter(String parameterName, String parameterValue) {
/*  558 */     if (parameterName.length() > 0 && namedParameterPattern.matcher(parameterName).matches())
/*      */     {
/*  560 */       this.namedParameters.put(parameterName.toLowerCase(), parameterValue);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNamedParameter(String parameterName) {
/*  572 */     if (this.namedParameters.containsKey(parameterName.toLowerCase())) {
/*  573 */       return this.namedParameters.get(parameterName.toLowerCase());
/*      */     }
/*  575 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReplaceFirstOccurrenceOnly(MacroParam.Type type, boolean newValue) {
/*  584 */     switch (type) {
/*      */       case Item:
/*  586 */         this.firstItemOnly = newValue; saveTemplates(); break;
/*  587 */       case Friend: this.firstFriendOnly = newValue; saveTemplates(); break;
/*  588 */       case OnlineUser: this.firstOnlineUserOnly = newValue; saveTemplates(); break;
/*  589 */       case Town: this.firstTownOnly = newValue; saveTemplates(); break;
/*  590 */       case Warp: this.firstWarpOnly = newValue; saveTemplates(); break;
/*  591 */       case Home: this.firstHomeOnly = newValue; saveTemplates();
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean shouldReplaceFirstOccurrenceOnly(MacroParam.Type type) {
/*  601 */     switch (type) {
/*      */       case Item:
/*  603 */         return this.firstItemOnly;
/*  604 */       case Friend: return this.firstFriendOnly;
/*  605 */       case OnlineUser: return this.firstOnlineUserOnly;
/*  606 */       case Town: return this.firstTownOnly;
/*  607 */       case Warp: return this.firstWarpOnly;
/*  608 */       case Home: return this.firstHomeOnly;
/*  609 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void saveTemplates() {
/*  618 */     if (this.macros != null) this.macros.save();
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Macro createInstance(boolean checkModifiers, IMacroActionContext context) {
/*  628 */     if (checkModifiers) {
/*      */       
/*  630 */       if (this.requireControl && !Keyboard.isKeyDown(29) && !Keyboard.isKeyDown(157)) return null; 
/*  631 */       if (this.requireAlt && !Keyboard.isKeyDown(56) && !Keyboard.isKeyDown(184)) return null; 
/*  632 */       if (this.requireShift && !Keyboard.isKeyDown(42) && !Keyboard.isKeyDown(54)) return null;
/*      */     
/*      */     } 
/*  635 */     if (MacroType.Key.supportsId(this.id)) {
/*      */       
/*  637 */       if (this.debounceTicks > 0) {
/*  638 */         return null;
/*      */       }
/*  640 */       this.debounceTicks = MacroModSettings.templateDebounceEnabled ? MacroModSettings.templateDebounceTicks : 1;
/*      */     } 
/*      */     
/*  643 */     return new Macro(this, this.id, this.playbackType, this.macroType, context);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveTemplate(PrintWriter writer) {
/*  653 */     if (this.keyDownMacro.length() + this.keyHeldMacro.length() + this.keyUpMacro.length() + this.macroCondition.length() > 0) {
/*      */       
/*  655 */       writer.println("# " + MacroType.getMacroNameWithPrefix(this.id));
/*      */       
/*  657 */       writer.println("Macro[" + this.id + "].Macro=" + this.keyDownMacro);
/*  658 */       if (this.keyHeldMacro.length() > 0) writer.println("Macro[" + this.id + "].OnKeyHeld=" + this.keyHeldMacro); 
/*  659 */       if (this.keyUpMacro.length() > 0) writer.println("Macro[" + this.id + "].OnKeyUp=" + this.keyUpMacro); 
/*  660 */       if (this.macroCondition.length() > 0) writer.println("Macro[" + this.id + "].Condition=" + this.macroCondition); 
/*  661 */       if (this.playbackType == MacroPlaybackType.KeyState) writer.println("Macro[" + this.id + "].Mode=" + "keystate"); 
/*  662 */       if (this.playbackType == MacroPlaybackType.Conditional) writer.println("Macro[" + this.id + "].Mode=" + "conditional"); 
/*  663 */       if (this.repeatRate != 1000) writer.println("Macro[" + this.id + "].RepeatRate=" + this.repeatRate); 
/*  664 */       if (this.inhibitParamLoad) writer.println("Macro[" + this.id + "].Inhibit=" + "1"); 
/*  665 */       if (this.requireControl) writer.println("Macro[" + this.id + "].Control=" + "1"); 
/*  666 */       if (this.requireAlt) writer.println("Macro[" + this.id + "].Alt=" + "1"); 
/*  667 */       if (this.requireShift) writer.println("Macro[" + this.id + "].Shift=" + "1"); 
/*  668 */       if (this.global) writer.println("Macro[" + this.id + "].Global=" + "1"); 
/*  669 */       if (this.alwaysOverride) writer.println("Macro[" + this.id + "].Override=" + "1"); 
/*  670 */       if (this.parameter.length() > 0) writer.println("Macro[" + this.id + "].Param=" + this.parameter); 
/*  671 */       if (this.item.length() > 0) writer.println("Macro[" + this.id + "].Item=" + this.item); 
/*  672 */       if (this.friend.length() > 0) writer.println("Macro[" + this.id + "].Friend=" + this.friend); 
/*  673 */       if (this.town.length() > 0) writer.println("Macro[" + this.id + "].Town=" + this.town); 
/*  674 */       if (this.warp.length() > 0) writer.println("Macro[" + this.id + "].Warp=" + this.warp); 
/*  675 */       if (this.home.length() > 0) writer.println("Macro[" + this.id + "].Home=" + this.home); 
/*  676 */       if (this.place.length() > 0) writer.println("Macro[" + this.id + "].Place=" + this.place); 
/*  677 */       if (this.file.length() > 0) writer.println("Macro[" + this.id + "].File=" + this.file);
/*      */       
/*  679 */       String serialisedCompilerFlags = serialiseCompilerFlags();
/*  680 */       if (serialisedCompilerFlags.length() > 0) writer.println("Macro[" + this.id + "].CompilerFlags=" + serialisedCompilerFlags);
/*      */       
/*  682 */       for (int i = 0; i < 10; i++) {
/*      */         
/*  684 */         if (this.presetText[i] != "") writer.println("Macro[" + this.id + "].PresetText[" + i + "]=" + this.presetText[i]);
/*      */       
/*      */       } 
/*  687 */       for (Map.Entry<String, String> namedParam : this.namedParameters.entrySet()) {
/*      */         
/*  689 */         if (namedParam.getValue() != "") writer.println("Macro[" + this.id + "].NamedParam[" + (String)namedParam.getKey() + "]=" + (String)namedParam.getValue());
/*      */       
/*      */       } 
/*  692 */       writer.println();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveVariables(Document xml, Element templateNode) {
/*  702 */     for (Map.Entry<String, Boolean> flag : this.flags.entrySet()) {
/*      */       
/*  704 */       Element flagNode = xml.createElement("boolean");
/*  705 */       flagNode.setAttribute("key", flag.getKey());
/*  706 */       flagNode.setTextContent(((Boolean)flag.getValue()).booleanValue() ? "1" : "0");
/*  707 */       templateNode.appendChild(flagNode);
/*      */     } 
/*      */     
/*  710 */     for (Map.Entry<String, Integer> counter : this.counters.entrySet()) {
/*      */       
/*  712 */       Element counterNode = xml.createElement("int");
/*  713 */       counterNode.setAttribute("key", counter.getKey());
/*  714 */       counterNode.setTextContent(((Integer)counter.getValue()).toString());
/*  715 */       templateNode.appendChild(counterNode);
/*      */     } 
/*      */     
/*  718 */     for (Map.Entry<String, String> string : this.strings.entrySet()) {
/*      */       
/*  720 */       Element stringNode = xml.createElement("string");
/*  721 */       stringNode.setAttribute("key", string.getKey());
/*  722 */       stringNode.setTextContent(string.getValue());
/*  723 */       templateNode.appendChild(stringNode);
/*      */     } 
/*      */     
/*  726 */     super.saveVariables(xml, templateNode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadFrom(String line, String key, String value) {
/*  738 */     if (key.equalsIgnoreCase("Macro")) { this.keyDownMacro = value; }
/*  739 */     else if (key.equalsIgnoreCase("OnKeyHeld")) { this.keyHeldMacro = value; }
/*  740 */     else if (key.equalsIgnoreCase("OnKeyUp")) { this.keyUpMacro = value; }
/*  741 */     else if (key.equalsIgnoreCase("Condition")) { this.macroCondition = value; }
/*  742 */     else if (key.equalsIgnoreCase("Mode")) { this.playbackType = parsePlaybackType(value); }
/*  743 */     else if (key.equalsIgnoreCase("RepeatRate")) { this.repeatRate = tryParse(value, 1000); }
/*  744 */     else if (key.equalsIgnoreCase("Inhibit")) { this.inhibitParamLoad = value.equals("1"); }
/*  745 */     else if (key.equalsIgnoreCase("Control")) { this.requireControl = value.equals("1"); }
/*  746 */     else if (key.equalsIgnoreCase("Alt")) { this.requireAlt = value.equals("1"); }
/*  747 */     else if (key.equalsIgnoreCase("Shift")) { this.requireShift = value.equals("1"); }
/*  748 */     else if (key.equalsIgnoreCase("Global")) { this.global = value.equals("1"); }
/*  749 */     else if (key.equalsIgnoreCase("Override")) { this.alwaysOverride = value.equals("1"); }
/*  750 */     else if (key.equalsIgnoreCase("Param")) { this.parameter = value; }
/*  751 */     else if (key.equalsIgnoreCase("Item")) { this.item = value; }
/*  752 */     else if (key.equalsIgnoreCase("Friend")) { this.friend = value; }
/*  753 */     else if (key.equalsIgnoreCase("Town")) { this.town = value; }
/*  754 */     else if (key.equalsIgnoreCase("Warp")) { this.warp = value; }
/*  755 */     else if (key.equalsIgnoreCase("Home")) { this.home = value; }
/*  756 */     else if (key.equalsIgnoreCase("Place")) { this.place = value; }
/*  757 */     else if (key.equalsIgnoreCase("File")) { this.file = value; }
/*  758 */     else if (key.equalsIgnoreCase("Flags")) { deserialiseFlags(value); }
/*  759 */     else if (key.equalsIgnoreCase("Counters")) { deserialiseCounters(value); }
/*  760 */     else if (key.equalsIgnoreCase("CompilerFlags")) { deserialiseCompilerFlags(value); }
/*  761 */     else if (key.startsWith("PresetText"))
/*      */     
/*  763 */     { Matcher presetTextMatcher = presetTextConfigPattern.matcher(key);
/*  764 */       if (presetTextMatcher.matches())
/*      */       {
/*  766 */         int presetTextIndex = Integer.parseInt(presetTextMatcher.group(1));
/*  767 */         this.presetText[presetTextIndex] = value;
/*      */       }
/*      */        }
/*  770 */     else if (key.startsWith("NamedParam"))
/*      */     
/*  772 */     { Matcher namedParameterMatcher = namedParameterConfigPattern.matcher(key);
/*  773 */       if (namedParameterMatcher.matches())
/*      */       {
/*  775 */         this.namedParameters.put(namedParameterMatcher.group(1), value);
/*      */       } }
/*      */     
/*  778 */     else if (key.startsWith("String"))
/*      */     
/*  780 */     { Matcher stringVariableMatcher = stringVariableConfigPattern.matcher(key);
/*  781 */       if (stringVariableMatcher.matches())
/*      */       {
/*  783 */         this.strings.put(stringVariableMatcher.group(1), value);
/*      */       } }
/*      */     
/*  786 */     else if (this.macroType == MacroType.Control)
/*      */     
/*  788 */     { if (key.startsWith("Property"))
/*      */       {
/*  790 */         Matcher propertyMatcher = propertyPattern.matcher(key);
/*  791 */         if (propertyMatcher.matches())
/*      */         {
/*  793 */           this.properties.put(propertyMatcher.group(1), value);
/*      */         }
/*      */       }
/*      */        }
/*      */     else
/*      */     
/*  799 */     { Log.info("Unrecognised configuration directive for macro [" + this.id + "]: " + key); }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadVariables(Node templateNode) {
/*  809 */     this.flags.clear();
/*  810 */     this.counters.clear();
/*  811 */     this.strings.clear();
/*      */     
/*  813 */     for (Node boolNode : Xml.queryAsArray(templateNode, "boolean")) {
/*      */       
/*  815 */       String flagName = Xml.getAttributeValue(boolNode, "key", "*");
/*  816 */       if (!flagName.equals("*") && ("1".equals(boolNode.getTextContent()) || "true".equalsIgnoreCase(boolNode.getTextContent())))
/*      */       {
/*  818 */         setFlag(flagName);
/*      */       }
/*      */     } 
/*      */     
/*  822 */     for (Node counterNode : Xml.queryAsArray(templateNode, "int")) {
/*      */       
/*  824 */       String counterName = Xml.getAttributeValue(counterNode, "key", "*");
/*  825 */       if (!counterName.equals("*") && counterNode.getTextContent() != null)
/*      */       {
/*  827 */         setCounter(counterName, tryParse(counterNode.getTextContent(), 0));
/*      */       }
/*      */     } 
/*      */     
/*  831 */     for (Node stringNode : Xml.queryAsArray(templateNode, "string")) {
/*      */       
/*  833 */       String stringName = Xml.getAttributeValue(stringNode, "key", "*");
/*  834 */       if (!stringName.equals("*") && stringNode.getTextContent() != null)
/*      */       {
/*  836 */         setString(stringName, stringNode.getTextContent());
/*      */       }
/*      */     } 
/*      */     
/*  840 */     super.loadVariables(templateNode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected MacroPlaybackType parsePlaybackType(String value) {
/*  849 */     if (value.equals("keystate") && this.macroType == MacroType.Key)
/*      */     {
/*  851 */       return MacroPlaybackType.KeyState;
/*      */     }
/*  853 */     if (value.equals("conditional"))
/*      */     {
/*  855 */       return MacroPlaybackType.Conditional;
/*      */     }
/*      */     
/*  858 */     return MacroPlaybackType.OneShot;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String serialiseCompilerFlags() {
/*  866 */     StringBuilder flags = new StringBuilder();
/*  867 */     if (this.firstItemOnly) flags.append("i"); 
/*  868 */     if (this.firstTownOnly) flags.append("t"); 
/*  869 */     if (this.firstWarpOnly) flags.append("w"); 
/*  870 */     if (this.firstHomeOnly) flags.append("h"); 
/*  871 */     if (this.firstOnlineUserOnly) flags.append("u"); 
/*  872 */     if (this.firstFriendOnly) flags.append("f"); 
/*  873 */     return flags.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void deserialiseCompilerFlags(String flags) {
/*  881 */     flags = flags.toLowerCase();
/*      */     
/*  883 */     this.firstItemOnly = flags.contains("i");
/*  884 */     this.firstTownOnly = flags.contains("t");
/*  885 */     this.firstWarpOnly = flags.contains("w");
/*  886 */     this.firstHomeOnly = flags.contains("h");
/*  887 */     this.firstOnlineUserOnly = flags.contains("u");
/*  888 */     this.firstFriendOnly = flags.contains("f");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int tryParse(String text, int defaultValue) {
/*      */     try {
/*  901 */       int parsed = Integer.parseInt(text);
/*  902 */       return parsed;
/*  903 */     } catch (NumberFormatException numberFormatException) {
/*  904 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void deserialiseFlags(String serialisedFlags) {
/*  914 */     String[] flagsList = serialisedFlags.split(",");
/*      */     
/*  916 */     for (String flag : flagsList) {
/*  917 */       setFlag(flag);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String serialiseFlags() {
/*  927 */     StringBuilder serialisedFlags = new StringBuilder();
/*  928 */     boolean append = false;
/*      */     
/*  930 */     for (Map.Entry<String, Boolean> flag : this.flags.entrySet()) {
/*      */       
/*  932 */       if (((Boolean)flag.getValue()).booleanValue()) {
/*      */         
/*  934 */         if (append) serialisedFlags.append(","); 
/*  935 */         serialisedFlags.append(flag.getKey());
/*  936 */         append = true;
/*      */       } 
/*      */     } 
/*      */     
/*  940 */     return serialisedFlags.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getFlag(String flag) {
/*  949 */     if (this.flags.containsKey(flag.toLowerCase())) {
/*  950 */       return ((Boolean)this.flags.get(flag.toLowerCase())).booleanValue();
/*      */     }
/*  952 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFlag(String flag, boolean value) {
/*  961 */     this.flags.put(flag.toLowerCase(), Boolean.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFlag(String flag) {
/*  970 */     this.flags.put(flag.toLowerCase(), Boolean.valueOf(true));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void unsetFlag(String flag) {
/*  979 */     this.flags.put(flag.toLowerCase(), Boolean.valueOf(false));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void deserialiseCounters(String serialisedCounters) {
/*  989 */     String[] counterList = serialisedCounters.split(",");
/*      */     
/*  991 */     for (String counter : counterList) {
/*      */       
/*  993 */       Matcher matcher = Pattern.compile("^([^,=]+)=([\\d\\-]+)$").matcher(counter);
/*      */       
/*  995 */       if (matcher.matches())
/*      */       {
/*  997 */         setCounter(matcher.group(1), tryParse(matcher.group(2), 0));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String serialiseCounters() {
/* 1009 */     StringBuilder serialisedCounters = new StringBuilder();
/* 1010 */     boolean append = false;
/*      */     
/* 1012 */     for (Map.Entry<String, Integer> counter : this.counters.entrySet()) {
/*      */       
/* 1014 */       if (append) serialisedCounters.append(","); 
/* 1015 */       serialisedCounters.append((String)counter.getKey() + "=" + ((Integer)counter.getValue()).toString());
/* 1016 */       append = true;
/*      */     } 
/*      */     
/* 1019 */     return serialisedCounters.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCounter(String counter) {
/* 1028 */     return this.counters.containsKey(counter) ? ((Integer)this.counters.get(counter)).intValue() : 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCounter(String counter, int value) {
/* 1037 */     this.counters.put(counter, Integer.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void unsetCounter(String counter) {
/* 1043 */     this.counters.remove(counter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void incrementCounter(String counter, int increment) {
/* 1052 */     int counterValue = getCounter(counter);
/* 1053 */     counterValue += increment;
/* 1054 */     setCounter(counter, counterValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void decrementCounter(String counter, int decrement) {
/* 1063 */     int counterValue = getCounter(counter);
/* 1064 */     counterValue -= decrement;
/* 1065 */     setCounter(counter, counterValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getString(String stringName) {
/* 1074 */     stringName = stringName.toLowerCase();
/*      */     
/* 1076 */     return this.strings.containsKey(stringName) ? this.strings.get(stringName) : "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setString(String stringName, String value) {
/* 1085 */     this.strings.put(stringName.toLowerCase(), sanitiseString(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void unsetString(String stringName) {
/* 1094 */     this.strings.remove(stringName.toLowerCase());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateVariables(boolean clock) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getVariable(String variableName) {
/* 1111 */     if (variableName.startsWith("#") && this.counters.containsKey(variableName.substring(1)))
/*      */     {
/* 1113 */       return this.counters.get(variableName.substring(1));
/*      */     }
/*      */     
/* 1116 */     if (variableName.startsWith("&") && this.strings.containsKey(variableName.substring(1)))
/*      */     {
/* 1118 */       return this.strings.get(variableName.substring(1));
/*      */     }
/*      */     
/* 1121 */     if (this.flags.containsKey(variableName))
/*      */     {
/* 1123 */       return this.flags.get(variableName);
/*      */     }
/*      */     
/* 1126 */     return super.getVariable(variableName);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<String> getVariables() {
/* 1132 */     Set<String> variables = super.getVariables();
/*      */     
/* 1134 */     for (String counterVar : this.counters.keySet()) {
/* 1135 */       variables.add("#" + counterVar);
/*      */     }
/* 1137 */     for (String stringVar : this.strings.keySet()) {
/* 1138 */       variables.add("&" + stringVar);
/*      */     }
/* 1140 */     variables.addAll(this.flags.keySet());
/*      */     
/* 1142 */     return variables;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onInit() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String sanitiseString(String string) {
/* 1156 */     return (string == null) ? "" : string.replaceAll("[\\n\\r]", "");
/*      */   }
/*      */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\MacroTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */