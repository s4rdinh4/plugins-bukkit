/*     */ package net.eq2online.macros.modules.chatfilter;
/*     */ 
/*     */ import com.mumfrey.liteloader.ChatFilter;
/*     */ import com.mumfrey.liteloader.core.LiteLoaderEventBroker;
/*     */ import ho;
/*     */ import hy;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.LiteModMacros;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.event.providers.OnChatProvider;
/*     */ import net.eq2online.macros.gui.screens.GuiMacroBind;
/*     */ import net.eq2online.macros.interfaces.ISaveSettings;
/*     */ import net.eq2online.macros.interfaces.ISettingsProvider;
/*     */ import net.eq2online.macros.modules.chatfilter.gui.GuiEditChatFilter;
/*     */ import net.eq2online.macros.modules.chatfilter.scriptactions.ScriptActionChatFilter;
/*     */ import net.eq2online.macros.modules.chatfilter.scriptactions.ScriptActionFilter;
/*     */ import net.eq2online.macros.modules.chatfilter.scriptactions.ScriptActionModify;
/*     */ import net.eq2online.macros.modules.chatfilter.scriptactions.ScriptActionPass;
/*     */ import net.eq2online.macros.scripting.api.IScriptAction;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*     */ import net.eq2online.macros.scripting.exceptions.ScriptException;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import vb;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChatFilterManager
/*     */   implements ISaveSettings, ChatFilter
/*     */ {
/*     */   private static ChatFilterManager instance;
/*  49 */   private static Pattern filterPattern = Pattern.compile("^Filter\\[([^\\]]*)\\]\\.([a-z0-9_\\-\\[\\]]+)=(.+)$", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   private TreeMap<String, ChatFilterTemplate> templates = new TreeMap<String, ChatFilterTemplate>();
/*     */ 
/*     */ 
/*     */   
/*     */   private File configFile;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean enabled = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ChatFilterManager getInstance() {
/*  68 */     if (instance == null) {
/*     */       
/*  70 */       instance = new ChatFilterManager(MacroModCore.getMacrosDirectory());
/*  71 */       GuiMacroBind.registerCustomScreen("Edit Chat Filter", GuiEditChatFilter.class);
/*  72 */       LiteModMacros.registerChatFilter(instance);
/*     */     } 
/*     */     
/*  75 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatFilterManager(File parentDir) {
/*  85 */     this.configFile = new File(parentDir, ".chatfilter.txt");
/*     */     
/*  87 */     ScriptContext.CHATFILTER.getCore().registerScriptAction((IScriptAction)new ScriptActionPass(ScriptContext.CHATFILTER));
/*  88 */     ScriptContext.CHATFILTER.getCore().registerScriptAction((IScriptAction)new ScriptActionFilter(ScriptContext.CHATFILTER));
/*  89 */     ScriptContext.CHATFILTER.getCore().registerScriptAction((IScriptAction)new ScriptActionModify(ScriptContext.CHATFILTER));
/*  90 */     ScriptContext.MAIN.getCore().registerScriptAction((IScriptAction)new ScriptActionPass(ScriptContext.MAIN));
/*  91 */     ScriptContext.MAIN.getCore().registerScriptAction((IScriptAction)new ScriptActionFilter(ScriptContext.MAIN));
/*     */     
/*  93 */     ScriptContext.MAIN.getCore().registerScriptAction((IScriptAction)new ScriptActionChatFilter(ScriptContext.MAIN));
/*     */ 
/*     */     
/*  96 */     load();
/*     */     
/*  98 */     MacroModCore.registerSettingsProvider(this);
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
/*     */   public ChatFilterTemplate getTemplate(String configName, boolean createIfNotFound) {
/* 110 */     if (this.templates.containsKey("") && ((ChatFilterTemplate)this.templates.get("")).global) {
/* 111 */       return this.templates.get("");
/*     */     }
/* 113 */     ChatFilterTemplate tpl = this.templates.get(configName);
/*     */     
/* 115 */     if (tpl == null && createIfNotFound) {
/*     */       
/* 117 */       tpl = new ChatFilterTemplate(this, MacroModCore.getMacroManager(), configName);
/* 118 */       this.templates.put(configName, tpl);
/* 119 */       return tpl;
/*     */     } 
/*     */     
/* 122 */     return tpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void deleteTemplate(String configName) {
/* 132 */     this.templates.remove(configName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load() {
/*     */     try {
/* 142 */       Log.info("Loading chat filter templates...");
/*     */ 
/*     */       
/* 145 */       this.templates.clear();
/*     */       
/* 147 */       if (!this.configFile.exists()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 152 */       BufferedReader bufferedreader = new BufferedReader(new FileReader(this.configFile));
/*     */       
/* 154 */       for (String configLine = ""; (configLine = bufferedreader.readLine()) != null;) {
/*     */ 
/*     */         
/*     */         try {
/* 158 */           Matcher filterPatternMatcher = filterPattern.matcher(configLine);
/*     */           
/* 160 */           if (filterPatternMatcher.matches())
/*     */           {
/* 162 */             String currentConfigName = filterPatternMatcher.group(1);
/*     */             
/* 164 */             ChatFilterTemplate tpl = getTemplate(currentConfigName, true);
/* 165 */             tpl.loadFrom(configLine, filterPatternMatcher.group(2), filterPatternMatcher.group(3));
/*     */ 
/*     */             
/* 168 */             if (tpl.global && currentConfigName.length() > 0)
/*     */             {
/* 170 */               deleteTemplate(currentConfigName);
/*     */             }
/*     */           }
/*     */         
/* 174 */         } catch (Exception ex) {
/*     */           
/* 176 */           Log.info("Skipping bad filter declaration: {0}", new Object[] { configLine });
/*     */         } 
/*     */       } 
/*     */       
/* 180 */       bufferedreader.close();
/*     */     }
/* 182 */     catch (Exception ex) {
/*     */       
/* 184 */       Log.info("Failed to chat filters:");
/* 185 */       Log.printStackTrace(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save() {
/*     */     try {
/*     */       try {
/* 198 */         this.configFile.getParentFile().mkdirs();
/*     */       }
/* 200 */       catch (Exception exception) {}
/*     */       
/* 202 */       PrintWriter printwriter = new PrintWriter(new FileWriter(this.configFile));
/*     */       
/* 204 */       printwriter.println("#");
/* 205 */       printwriter.println("# .chatfilter.txt");
/* 206 */       printwriter.println("# This file stores the macro templates for the chat filter module. Do not edit this file.");
/* 207 */       printwriter.println("#\n");
/*     */       
/* 209 */       for (Map.Entry<String, ChatFilterTemplate> entry : this.templates.entrySet()) {
/*     */         
/* 211 */         if (MacroModCore.getMacroManager().hasConfig(entry.getKey())) {
/*     */           
/*     */           try {
/*     */             
/* 215 */             ((ChatFilterTemplate)entry.getValue()).saveTemplate(printwriter);
/*     */           }
/* 217 */           catch (Exception exception) {}
/*     */         }
/*     */       } 
/*     */       
/* 221 */       printwriter.close();
/*     */     }
/* 223 */     catch (Exception ex) {
/*     */       
/* 225 */       Log.info("Failed to save .macros.txt");
/* 226 */       Log.printStackTrace(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifySettingsCleared() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifySettingsLoaded(ISettingsProvider settings) {
/* 245 */     this.enabled = settings.getSetting("chatfilter.enabled", true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveSettings(ISettingsProvider settings) {
/* 255 */     save();
/*     */     
/* 257 */     settings.setSetting("chatfilter.enabled", this.enabled);
/* 258 */     settings.setSettingComment("chatfilter.enabled", "Set to 1 to enable the chat filter module");
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
/*     */   public boolean onChat(ho chat, String message, LiteLoaderEventBroker.ReturnValue<ho> newMessage) {
/* 271 */     if (this.enabled) {
/*     */       
/* 273 */       ChatFilterTemplate activeTemplate = getTemplate(MacroModCore.getMacroManager().getActiveConfig(), false);
/*     */ 
/*     */       
/*     */       try {
/* 277 */         if (activeTemplate != null)
/*     */         {
/* 279 */           MacroModCore.getInstance().updateServer();
/*     */           
/* 281 */           IScriptActionProvider scriptActionProvider = ScriptContext.CHATFILTER.getScriptActionProvider();
/* 282 */           scriptActionProvider.updateVariableProviders(false);
/*     */           
/* 284 */           List<String> messages = new ArrayList<String>();
/* 285 */           String[] chatLines = message.split("\\r?\\n");
/* 286 */           boolean modified = false;
/*     */           
/* 288 */           for (String chatLine : chatLines) {
/*     */             
/* 290 */             ChatFilterMacro macro = processChatMessage(activeTemplate, chatLine);
/* 291 */             if (macro.pass) {
/*     */               
/* 293 */               if (macro.newMessage != null)
/*     */               {
/* 295 */                 modified = true;
/* 296 */                 messages.add(macro.newMessage);
/*     */               }
/*     */               else
/*     */               {
/* 300 */                 messages.add(chatLine);
/*     */               }
/*     */             
/*     */             } else {
/*     */               
/* 305 */               modified = true;
/*     */             } 
/*     */           } 
/*     */           
/* 309 */           if (!modified) return true; 
/* 310 */           if (messages.size() == 0) return false;
/*     */           
/* 312 */           StringBuilder messageBuilder = new StringBuilder();
/* 313 */           boolean first = true;
/*     */           
/* 315 */           for (String msg : messages) {
/*     */             
/* 317 */             if (!first) messageBuilder.append("\n");  first = false;
/* 318 */             messageBuilder.append(msg);
/*     */           } 
/*     */           
/* 321 */           newMessage.set(new hy(messageBuilder.toString()));
/*     */           
/* 323 */           return true;
/*     */         }
/*     */       
/* 326 */       } catch (Exception ex) {
/*     */         
/* 328 */         ex.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 332 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ChatFilterMacro processChatMessage(ChatFilterTemplate activeTemplate, String message) throws ScriptException {
/* 337 */     String chatClean = vb.a(message);
/*     */     
/* 339 */     OnChatProvider contextProvider = new OnChatProvider(null);
/* 340 */     contextProvider.initInstance(new String[] { message, chatClean });
/*     */     
/* 342 */     ChatFilterMacro macro = (ChatFilterMacro)activeTemplate.createInstance(false, ScriptContext.CHATFILTER.createActionContext((IVariableProvider)contextProvider));
/* 343 */     macro.playMacro(false, true);
/*     */     
/* 345 */     return macro;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabledState) {
/* 355 */     this.enabled = enabledState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/* 365 */     return this.enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 371 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 377 */     return null;
/*     */   }
/*     */   
/*     */   public void init(File configPath) {}
/*     */   
/*     */   public void upgradeSettings(String version, File configPath, File oldConfigPath) {}
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\modules\chatfilter\ChatFilterManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */