/*     */ package net.eq2online.macros.event;
/*     */ 
/*     */ import bsu;
/*     */ import com.mumfrey.liteloader.util.render.Icon;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.PriorityQueue;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.compatibility.IconTiled;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.core.MacroType;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.layout.LayoutButton;
/*     */ import net.eq2online.macros.gui.layout.LayoutPanelEvents;
/*     */ import net.eq2online.macros.interfaces.ISaveSettings;
/*     */ import net.eq2online.macros.interfaces.ISettingsProvider;
/*     */ import net.eq2online.macros.permissions.MacroModPermissions;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ import net.eq2online.macros.scripting.api.IMacroEvent;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventDispatcher;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventManager;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventProvider;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventVariableProvider;
/*     */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
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
/*     */ public class MacroEventManager
/*     */   implements IMacroEventManager, ISaveSettings
/*     */ {
/*  55 */   private static Pattern eventPattern = Pattern.compile("^Event\\[([0-9]{1,4})\\]\\.([a-z0-9_\\-\\[\\]]+)=(.+)$", 2);
/*     */   
/*     */   private boolean haveLoadedIDs = false;
/*     */   
/*  59 */   private int nextEventID = MacroType.Event.getMinId();
/*     */   
/*  61 */   private Map<String, Integer> eventIDs = new HashMap<String, Integer>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   protected Map<String, IMacroEvent> eventsByName = new HashMap<String, IMacroEvent>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   protected Map<Integer, IMacroEvent> eventsByID = new TreeMap<Integer, IMacroEvent>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Macros macros;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   protected PriorityQueue<MacroEventQueueEntry> eventQueue = new PriorityQueue<MacroEventQueueEntry>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   protected List<IMacroEventProvider> providerList = new LinkedList<IMacroEventProvider>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   protected List<IMacroEventDispatcher> dispatcherList = new LinkedList<IMacroEventDispatcher>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   protected Object queueLock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MacroEventManager(Macros macros, bsu minecraft) {
/* 106 */     this.macros = macros;
/*     */     
/* 108 */     MacroModCore.registerSettingsProvider(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerEventProvider(IMacroEventProvider provider) {
/* 114 */     if (!this.providerList.contains(provider)) {
/*     */       
/* 116 */       this.providerList.add(provider);
/* 117 */       provider.registerEvents(this);
/*     */       
/* 119 */       IMacroEventDispatcher dispatcher = provider.getDispatcher();
/* 120 */       if (dispatcher != null)
/*     */       {
/* 122 */         registerDispatcher(dispatcher);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerDispatcher(IMacroEventDispatcher dispatcher) {
/* 129 */     if (!this.dispatcherList.contains(dispatcher)) {
/* 130 */       this.dispatcherList.add(dispatcher);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initPermissions() {
/* 138 */     Set<String> eventGroups = new HashSet<String>();
/* 139 */     MacroModPermissions.registerPermission("events.*");
/*     */     
/* 141 */     for (String eventName : this.eventsByName.keySet()) {
/*     */       
/* 143 */       IMacroEvent event = getEvent(eventName);
/*     */       
/* 145 */       if (event != null && event.isPermissible()) {
/*     */         
/* 147 */         String eventGroup = event.getPermissionGroup();
/* 148 */         if (!eventGroups.contains(eventGroup)) {
/*     */           
/* 150 */           eventGroups.add(eventGroup);
/* 151 */           MacroModPermissions.registerPermission("events." + eventGroup + ".*");
/*     */         } 
/*     */         
/* 154 */         MacroModPermissions.registerPermission(event.getPermissionName());
/*     */       } 
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
/*     */   public boolean checkPermission(int eventId) {
/* 167 */     IMacroEvent event = getEvent(eventId);
/* 168 */     return (event != null && checkPermission(event));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkPermission(String eventName) {
/* 179 */     IMacroEvent event = getEvent(eventName);
/* 180 */     return (event != null && checkPermission(event));
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkPermission(IMacroEvent event) {
/* 185 */     return (!event.isPermissible() || MacroModPermissions.hasPermission(event.getPermissionName()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEventID(String name) {
/* 191 */     if (!this.eventIDs.containsKey(name)) {
/*     */       
/* 193 */       this.eventIDs.put(name, Integer.valueOf(this.nextEventID));
/* 194 */       this.nextEventID++;
/*     */     } 
/*     */     
/* 197 */     return ((Integer)this.eventIDs.get(name)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEventID(IMacroEvent event) {
/* 203 */     return getEventID(event.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IMacroEvent> getEvents() {
/* 209 */     List<IMacroEvent> eventList = new ArrayList<IMacroEvent>();
/*     */     
/* 211 */     eventList.addAll(this.eventsByID.values());
/*     */     
/* 213 */     return eventList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTick(bsu minecraft) {
/* 223 */     for (IMacroEventDispatcher dispatcher : this.dispatcherList) {
/*     */ 
/*     */       
/*     */       try {
/* 227 */         dispatcher.onTick(this, minecraft);
/*     */       }
/* 229 */       catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 234 */       synchronized (this.queueLock)
/*     */       {
/* 236 */         MacroEventQueueEntry nextEvent = this.eventQueue.poll();
/*     */         
/* 238 */         if (nextEvent != null)
/*     */         {
/* 240 */           dispatchEvent(nextEvent.eventName, false, nextEvent.eventArgs);
/*     */         }
/*     */       }
/*     */     
/* 244 */     } catch (NullPointerException ex) {
/*     */       
/* 246 */       if (this.eventQueue != null)
/*     */       {
/* 248 */         purgeQueue();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void purgeQueue() {
/* 258 */     synchronized (this.queueLock) {
/*     */       
/* 260 */       this.eventQueue.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendEvent(IMacroEvent event, String... eventArgs) {
/* 267 */     sendEvent(event.getName(), 50, eventArgs);
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
/*     */   public void sendEvent(String eventName, int priority, String... eventArgs) {
/* 280 */     boolean synchronous = (priority == Integer.MAX_VALUE);
/*     */     
/* 282 */     if (priority == 100 || synchronous) {
/*     */       
/* 284 */       if (checkPermission(eventName))
/*     */       {
/* 286 */         dispatchEvent(eventName, synchronous, eventArgs);
/*     */       }
/*     */     }
/* 289 */     else if (priority < 0 || priority > 100) {
/*     */       
/* 291 */       Log.info("Event {0} was not dispatched because an invalid priority was specified: " + priority);
/*     */     }
/* 293 */     else if (this.eventsByName.containsKey(eventName)) {
/*     */       
/* 295 */       if (checkPermission(eventName)) {
/*     */         
/* 297 */         synchronized (this.queueLock)
/*     */         {
/* 299 */           this.eventQueue.add(new MacroEventQueueEntry(eventName, priority, eventArgs));
/*     */         }
/*     */       
/* 302 */       } else if (MacroModSettings.enableDebug) {
/*     */         
/* 304 */         Log.info("Event {0} was denied by the server", new Object[] { eventName });
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 309 */       Log.info("Event {0} was not dispatched because no mapping was found");
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
/*     */   private void dispatchEvent(String eventName, boolean synchronous, String... eventArgs) {
/* 321 */     if (this.eventsByName.containsKey(eventName)) {
/*     */       
/* 323 */       IMacroEvent event = this.eventsByName.get(eventName);
/* 324 */       IMacroEventVariableProvider eventVariableProvider = event.getVariableProvider(eventArgs);
/*     */       
/* 326 */       if (MacroModSettings.enableDebug)
/*     */       {
/* 328 */         Log.info("Dispatching event {0}", new Object[] { eventName });
/*     */       }
/*     */       
/* 331 */       event.onDispatch();
/* 332 */       this.macros.playMacro(getEventID(event.getName()), false, ScriptContext.MAIN, (IVariableProvider)eventVariableProvider, synchronous);
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
/*     */   public IMacroEvent getEvent(int eventId) {
/* 345 */     return this.eventsByID.get(Integer.valueOf(eventId));
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
/*     */   public IMacroEvent getEvent(String eventName) {
/* 357 */     return this.eventsByName.get(eventName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void clearEvents() {
/* 365 */     this.eventIDs.clear();
/* 366 */     this.eventsByID.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void reloadEvents() {
/* 371 */     for (IMacroEventProvider provider : this.providerList)
/*     */     {
/* 373 */       provider.registerEvents(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMacroEvent registerEvent(IMacroEventProvider provider, String name) {
/* 380 */     return registerEvent(provider, name, null);
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
/*     */   public IMacroEvent registerEvent(IMacroEventProvider provider, String name, String permissionGroup) {
/* 393 */     if (!this.providerList.contains(provider) || provider == null)
/*     */     {
/* 395 */       throw new IllegalArgumentException("Attempted to register an event with an invalid provider, call registerProvider() first!");
/*     */     }
/*     */     
/* 398 */     int eventId = getEventID(name);
/*     */     
/* 400 */     if (this.eventsByName.containsKey(name)) {
/*     */       
/* 402 */       IMacroEvent existingEvent = this.eventsByName.get(name);
/*     */       
/* 404 */       this.eventsByID.put(Integer.valueOf(eventId), existingEvent);
/* 405 */       existingEvent.setIcon(getEventIcon(eventId));
/*     */       
/* 407 */       refreshGui();
/* 408 */       return existingEvent;
/*     */     } 
/*     */     
/* 411 */     IMacroEvent newEvent = new MacroEvent(provider, name, (permissionGroup != null), permissionGroup, getEventIcon(eventId));
/* 412 */     this.eventsByName.put(name, newEvent);
/* 413 */     this.eventsByID.put(Integer.valueOf(eventId), newEvent);
/*     */     
/* 415 */     refreshGui();
/* 416 */     return newEvent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refreshGui() {
/* 424 */     LayoutPanelEvents panel = this.macros.getEventLayout();
/* 425 */     if (panel != null) panel.loadPanelLayout("");
/*     */   
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
/*     */   public IMacroEvent registerEvent(IMacroEvent event) {
/* 438 */     if (!this.providerList.contains(event.getProvider()) || event.getProvider() == null)
/*     */     {
/* 440 */       throw new IllegalArgumentException("Attempted to register an event with an invalid provider, call registerProvider() first!");
/*     */     }
/*     */     
/* 443 */     String name = event.getName();
/* 444 */     int eventId = getEventID(name);
/*     */     
/* 446 */     if (this.eventsByName.containsKey(name)) {
/*     */       
/* 448 */       if (this.eventsByName.get(name) == event) {
/*     */         
/* 450 */         this.eventsByID.put(Integer.valueOf(eventId), event);
/* 451 */         event.setIcon(getEventIcon(eventId));
/*     */         
/* 453 */         refreshGui();
/* 454 */         return event;
/*     */       } 
/*     */       
/* 457 */       throw new IllegalArgumentException("An instance of the specified event " + name + " is already registered");
/*     */     } 
/*     */     
/* 460 */     this.eventsByName.put(name, event);
/* 461 */     this.eventsByID.put(Integer.valueOf(eventId), event);
/*     */     
/* 463 */     refreshGui();
/* 464 */     return event;
/*     */   }
/*     */ 
/*     */   
/*     */   private Icon getEventIcon(int eventId) {
/* 469 */     eventId = MacroType.Event.getRelativeId(eventId);
/* 470 */     int iconU = eventId % 10 * 24;
/* 471 */     int iconV = eventId / 10 * 24;
/* 472 */     return (Icon)new IconTiled(ResourceLocations.EXT, eventId, iconU, iconV, 24, 24, 256, 256);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifySettingsCleared() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifySettingsLoaded(ISettingsProvider settings) {
/* 483 */     if (!this.haveLoadedIDs) {
/*     */       
/* 485 */       BufferedReader bufferedreader = null;
/*     */ 
/*     */       
/*     */       try {
/* 489 */         File eventsFile = new File(MacroModCore.getMacrosDirectory(), ".events.txt");
/* 490 */         if (!eventsFile.exists())
/* 491 */           return;  clearEvents();
/*     */         
/* 493 */         bufferedreader = new BufferedReader(new FileReader(eventsFile));
/*     */         
/* 495 */         for (String configLine = ""; (configLine = bufferedreader.readLine()) != null;) {
/*     */ 
/*     */           
/*     */           try {
/* 499 */             Matcher eventConfigLineMatcher = eventPattern.matcher(configLine);
/*     */             
/* 501 */             if (eventConfigLineMatcher.matches())
/*     */             {
/* 503 */               int eventId = Integer.parseInt(eventConfigLineMatcher.group(1));
/*     */               
/* 505 */               if (MacroType.Event.supportsId(eventId)) {
/*     */                 
/* 507 */                 if (eventConfigLineMatcher.group(2).equalsIgnoreCase("name")) {
/*     */                   
/* 509 */                   String eventName = eventConfigLineMatcher.group(3);
/* 510 */                   this.eventIDs.put(eventName, Integer.valueOf(eventId));
/*     */                 } 
/*     */                 
/*     */                 continue;
/*     */               } 
/* 515 */               Log.info("Skipping bad event ID: " + eventId);
/*     */             }
/*     */           
/*     */           }
/* 519 */           catch (Exception ex) {
/*     */             
/* 521 */             Log.info("Skipping bad event mapping: " + configLine);
/*     */           } 
/*     */         } 
/*     */         
/* 525 */         this.haveLoadedIDs = true;
/*     */       }
/* 527 */       catch (Exception ex) {
/*     */         
/* 529 */         Log.printStackTrace(ex);
/*     */       } finally {
/*     */ 
/*     */         
/*     */         try {
/*     */           
/* 535 */           if (bufferedreader != null) {
/* 536 */             bufferedreader.close();
/*     */           }
/* 538 */         } catch (IOException iOException) {}
/*     */       } 
/*     */       
/* 541 */       reloadEvents();
/*     */     } 
/*     */     
/* 544 */     LayoutButton.notifySettingsLoaded(settings);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveSettings(ISettingsProvider settings) {
/*     */     try {
/* 553 */       File eventsFile = new File(MacroModCore.getMacrosDirectory(), ".events.txt");
/*     */       
/* 555 */       PrintWriter printwriter = new PrintWriter(new FileWriter(eventsFile));
/*     */       
/* 557 */       printwriter.println("#");
/* 558 */       printwriter.println("# events.txt");
/* 559 */       printwriter.println("# This file stores mapping of hookable event names event ID's to event names, DO NOT modify the");
/* 560 */       printwriter.println("# contents of this file unless you know what you are doing, in fact, not even then since all your");
/* 561 */       printwriter.println("# event bindings will be messed up if you edit this file, seriously, just don't. Event names are");
/* 562 */       printwriter.println("# CASE SENSITIVE and event ID's must fall within the defined event ID range.");
/* 563 */       printwriter.println("#\n");
/*     */       
/* 565 */       for (Map.Entry<String, Integer> event : this.eventIDs.entrySet()) {
/*     */         
/* 567 */         printwriter.println(String.format("Event[%s].Name=%s", new Object[] { ((Integer)event.getValue()).toString(), event.getKey() }));
/*     */       } 
/*     */       
/* 570 */       printwriter.close();
/*     */     }
/* 572 */     catch (Exception ex) {
/*     */       
/* 574 */       Log.printStackTrace(ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\event\MacroEventManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */