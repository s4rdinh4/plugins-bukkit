/*     */ package net.eq2online.macros.input;
/*     */ 
/*     */ import bsu;
/*     */ import com.mumfrey.liteloader.core.LiteLoader;
/*     */ import com.mumfrey.liteloader.util.jinput.ComponentRegistry;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.InputStreamReader;
/*     */ import java.lang.reflect.Field;
/*     */ import java.nio.BufferOverflowException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.interfaces.ISettingsProvider;
/*     */ import net.java.games.input.Component;
/*     */ import net.java.games.input.Controller;
/*     */ import net.java.games.input.Event;
/*     */ import net.java.games.input.EventQueue;
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
/*     */ public class InputHandlerModuleJInput
/*     */   implements IInputHandlerModule
/*     */ {
/*  47 */   private static Pattern configPattern = Pattern.compile("^(\\d{1,4}|x|y|KEY_[a-z0-9]+)=(.+)$", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ComponentRegistry registry;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   private HashMap<Component, Integer> mappings = new HashMap<Component, Integer>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   private HashMap<Component, Float> axisMappingsX = new HashMap<Component, Float>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   private HashMap<Component, Float> axisMappingsY = new HashMap<Component, Float>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   private byte[] states = new byte[10000];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private boolean[] stateMask = new boolean[this.states.length];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   private ArrayList<Controller> pollDevices = new ArrayList<Controller>();
/*     */   
/*     */   private static Field mouseDX;
/*     */   
/*     */   private static Field mouseDY;
/*     */   
/*     */   public InputHandlerModuleJInput() {
/*     */     try {
/*  90 */       this.registry = LiteLoader.getInput().getComponentRegistry();
/*     */       
/*  92 */       mouseDX = Mouse.class.getDeclaredField("dx");
/*  93 */       mouseDY = Mouse.class.getDeclaredField("dy");
/*  94 */       mouseDX.setAccessible(true);
/*  95 */       mouseDY.setAccessible(true);
/*     */     } catch (Exception ex) {
/*  97 */       Log.printStackTrace(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifySettingsCleared() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifySettingsLoaded(ISettingsProvider settings) {
/* 108 */     this.mappings.clear();
/* 109 */     this.pollDevices.clear();
/* 110 */     this.registry.enumerate();
/*     */     
/* 112 */     for (int i = 0; i < this.states.length; i++) {
/*     */       
/* 114 */       this.states[i] = 0;
/* 115 */       this.stateMask[i] = false;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 120 */       BufferedReader bufferedreader = getSettingsReader();
/*     */       
/* 122 */       for (String configLine = ""; (configLine = bufferedreader.readLine()) != null; ) {
/*     */         
/* 124 */         Matcher configLineMatcher = configPattern.matcher(configLine);
/* 125 */         if (configLineMatcher.matches()) {
/*     */           
/* 127 */           String mappingPath = configLineMatcher.group(2);
/*     */           
/* 129 */           if (mappingPath.startsWith("\"") && mappingPath.endsWith("\"")) {
/*     */             
/* 131 */             if (!configLineMatcher.group(1).equalsIgnoreCase("x") && !configLineMatcher.group(1).equalsIgnoreCase("y")) {
/*     */               
/* 133 */               Integer mappingID = getMappingID(configLineMatcher.group(1));
/* 134 */               setKeyName(mappingID.intValue(), mappingPath.substring(1, mappingPath.length() - 1));
/*     */             } 
/*     */             
/*     */             continue;
/*     */           } 
/* 139 */           for (Component component : this.registry.getComponents(mappingPath)) {
/*     */             
/* 141 */             if (configLineMatcher.group(1).equalsIgnoreCase("x") || configLineMatcher.group(1).equalsIgnoreCase("y")) {
/*     */               
/* 143 */               HashMap<Component, Float> mappings = configLineMatcher.group(1).equalsIgnoreCase("x") ? this.axisMappingsX : this.axisMappingsY;
/*     */               
/* 145 */               if (component != null && component.isAnalog())
/*     */               {
/* 147 */                 if (!mappings.containsKey(component)) {
/*     */                   
/* 149 */                   mappings.put(component, Float.valueOf(0.0F));
/*     */                   
/* 151 */                   for (Controller controller : this.registry.getControllers(mappingPath)) {
/*     */                     
/* 153 */                     Log.info("Hooking {0} to mouse {1} axis via JInput", new Object[] { ComponentRegistry.getDescriptor(controller, component), configLineMatcher.group(1) });
/*     */                     
/* 155 */                     if (!this.pollDevices.contains(controller)) {
/* 156 */                       this.pollDevices.add(controller);
/*     */                     }
/*     */                   } 
/*     */                 } 
/*     */               }
/*     */               continue;
/*     */             } 
/* 163 */             Integer mappingID = getMappingID(configLineMatcher.group(1));
/*     */             
/* 165 */             if (mappingID.intValue() > 0 && mappingID.intValue() < 256 && component != null && !component.isAnalog()) {
/*     */               
/* 167 */               for (Controller controller : this.registry.getControllers(mappingPath)) {
/*     */                 
/* 169 */                 if (!this.pollDevices.contains(controller)) {
/* 170 */                   this.pollDevices.add(controller);
/*     */                 }
/*     */               } 
/* 173 */               Log.info("Hooking {0} as ID {1} via JInput", new Object[] { ComponentRegistry.getDescriptor(this.registry.getController(mappingPath), component), mappingID });
/* 174 */               this.mappings.put(component, mappingID);
/* 175 */               this.stateMask[mappingID.intValue()] = true;
/*     */               
/* 177 */               setKeyName(mappingID.intValue(), "<" + mappingID + ">");
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 185 */       bufferedreader.close();
/*     */     }
/* 187 */     catch (Exception ex) {
/*     */       
/* 189 */       Log.printStackTrace(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Integer getMappingID(String strMappingId) {
/* 199 */     Integer mappingID = Integer.valueOf(0);
/*     */ 
/*     */     
/*     */     try {
/* 203 */       if (strMappingId.toLowerCase().startsWith("key_")) {
/*     */         
/* 205 */         String keyName = strMappingId.substring(4).toLowerCase();
/*     */         
/* 207 */         for (int key = 0; key < 255; key++) {
/*     */           
/* 209 */           if (Keyboard.getKeyName(key).toLowerCase().equals(keyName)) {
/*     */             
/* 211 */             mappingID = Integer.valueOf(key);
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 218 */         mappingID = Integer.valueOf(Integer.parseInt(strMappingId));
/*     */       }
/*     */     
/* 221 */     } catch (NumberFormatException numberFormatException) {}
/*     */     
/* 223 */     return mappingID;
/*     */   }
/*     */ 
/*     */   
/*     */   private BufferedReader getSettingsReader() throws FileNotFoundException {
/* 228 */     File settingsFile = new File(MacroModCore.getMacrosDirectory(), ".jinput.txt");
/*     */ 
/*     */     
/* 231 */     if (settingsFile.exists()) {
/* 232 */       return new BufferedReader(new FileReader(settingsFile));
/*     */     }
/*     */     
/* 235 */     return new BufferedReader(new InputStreamReader(InputHandlerModuleJInput.class.getResourceAsStream("/.jinput.txt")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveSettings(ISettingsProvider settings) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialise(InputHandler inputHandler) {
/* 246 */     notifySettingsLoaded((ISettingsProvider)MacroModCore.getMacroManager());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void register(bsu minecraft, ISettingsProvider settings) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(ArrayList<InputHandler.KeyEvent> keyEventQueue, boolean overrideKeyDown, boolean modifierKeyDown) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onTick(ArrayList<InputHandler.KeyEvent> keyEventQueue) {
/* 262 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean injectEvents(ByteBuffer keyboardEventBuffer, ByteBuffer keyDownBuffer, ByteBuffer mouseEventBuffer, ByteBuffer mouseDownBuffer) {
/* 268 */     boolean pushed = false;
/*     */     
/* 270 */     for (Controller controller : this.pollDevices) {
/*     */       
/* 272 */       controller.poll();
/* 273 */       EventQueue controllerQueue = controller.getEventQueue();
/*     */       
/* 275 */       for (Event event = new Event(); controllerQueue.getNextEvent(event); ) {
/*     */         
/* 277 */         Component cmp = event.getComponent();
/*     */         
/* 279 */         if (this.mappings.containsKey(cmp)) {
/*     */           
/* 281 */           int i = ((Integer)this.mappings.get(cmp)).intValue();
/* 282 */           byte state = (byte)((event.getValue() == 1.0F) ? 1 : 0);
/* 283 */           int character = CharMap.getKeyChar(i);
/*     */           
/* 285 */           if (pushKeyboardEvent(keyboardEventBuffer, i, state, character)) {
/*     */             
/* 287 */             this.states[i] = state;
/* 288 */             pushed = true;
/*     */           }  continue;
/*     */         } 
/* 291 */         if (this.axisMappingsX.containsKey(cmp)) {
/*     */           
/* 293 */           this.axisMappingsX.put(cmp, Float.valueOf(event.getValue())); continue;
/*     */         } 
/* 295 */         if (this.axisMappingsY.containsKey(cmp))
/*     */         {
/* 297 */           this.axisMappingsY.put(cmp, Float.valueOf(event.getValue()));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 302 */     for (int key = 0; key < this.states.length; key++) {
/*     */       
/* 304 */       if (this.stateMask[key])
/*     */       {
/* 306 */         keyDownBuffer.put(key, this.states[key]);
/*     */       }
/*     */     } 
/*     */     
/* 310 */     float dx = 0.0F, dy = 0.0F;
/*     */ 
/*     */ 
/*     */     
/* 314 */     try { if (mouseDX != null) {
/*     */         
/* 316 */         for (Float fDX : this.axisMappingsX.values()) dx += fDX.floatValue() * 4.0F;
/*     */         
/* 318 */         mouseDX.setAccessible(true);
/* 319 */         dx += ((Integer)mouseDX.get((Object)null)).intValue();
/* 320 */         mouseDX.set((Object)null, Integer.valueOf((int)dx));
/*     */       } 
/* 322 */       if (mouseDY != null) {
/*     */         
/* 324 */         for (Float fDY : this.axisMappingsY.values()) dy -= fDY.floatValue() * 4.0F;
/*     */         
/* 326 */         mouseDY.setAccessible(true);
/* 327 */         dy += ((Integer)mouseDY.get((Object)null)).intValue();
/* 328 */         mouseDY.set((Object)null, Integer.valueOf((int)dy));
/*     */       }  }
/* 330 */     catch (Exception ex) { Log.printStackTrace(ex); }
/*     */     
/* 332 */     return pushed;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean pushKeyboardEvent(ByteBuffer keyboardEventBuffer, int key, byte state, int character) {
/*     */     try {
/* 339 */       keyboardEventBuffer.putInt(key);
/* 340 */       keyboardEventBuffer.put(state);
/* 341 */       keyboardEventBuffer.putInt(character);
/* 342 */       keyboardEventBuffer.putLong(1L);
/* 343 */       keyboardEventBuffer.put((byte)0);
/*     */     }
/* 345 */     catch (BufferOverflowException ex) {
/*     */       
/* 347 */       return false;
/*     */     } 
/*     */     
/* 350 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean pushMouseEvent(ByteBuffer mouseEventBuffer, byte button, byte state, int mouseX, int mouseY, int dWheel, long nanos) {
/*     */     try {
/* 358 */       mouseEventBuffer.put(button);
/* 359 */       mouseEventBuffer.put(state);
/* 360 */       mouseEventBuffer.putInt(mouseX);
/* 361 */       mouseEventBuffer.putInt(mouseY);
/* 362 */       mouseEventBuffer.putInt(dWheel);
/* 363 */       mouseEventBuffer.putLong(nanos);
/*     */     }
/* 365 */     catch (BufferOverflowException ex) {
/*     */       
/* 367 */       return false;
/*     */     } 
/*     */     
/* 370 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void setKeyName(int key, String name) {
/* 375 */     if (key < 0 || key > 255) {
/*     */       return;
/*     */     }
/*     */     try {
/* 379 */       Field fieldKeyName = Keyboard.class.getDeclaredField("keyName");
/* 380 */       fieldKeyName.setAccessible(true);
/* 381 */       String[] keyName = (String[])fieldKeyName.get((Object)null);
/* 382 */       keyName[key] = name;
/*     */     }
/* 384 */     catch (Exception exception) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\input\InputHandlerModuleJInput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */