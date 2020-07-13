/*     */ package net.eq2online.macros.input;
/*     */ 
/*     */ import bsr;
/*     */ import bsu;
/*     */ import bto;
/*     */ import bxf;
/*     */ import java.nio.BufferOverflowException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.compatibility.Reflection;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.screens.GuiMacroBind;
/*     */ import net.eq2online.macros.gui.screens.GuiMacroPlayback;
/*     */ import net.eq2online.macros.interfaces.ISaveSettings;
/*     */ import net.eq2online.macros.interfaces.ISettingsProvider;
/*     */ import org.lwjgl.BufferUtils;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InputHandler
/*     */   implements ISaveSettings
/*     */ {
/*     */   public enum BindingComboMode
/*     */   {
/*  59 */     Normal,
/*     */     
/*  61 */     Direct,
/*     */     
/*  63 */     Disabled;
/*     */ 
/*     */     
/*     */     public String getDescription() {
/*  67 */       if (this == Direct)
/*     */       {
/*  69 */         return LocalisationProvider.getLocalisedString("options.option.mode.direct", new Object[] { bto.c(InputHandler.keybindActivate.i()) });
/*     */       }
/*     */       
/*  72 */       if (this == Disabled)
/*     */       {
/*  74 */         return LocalisationProvider.getLocalisedString("options.option.mode.disabled");
/*     */       }
/*     */       
/*  77 */       return LocalisationProvider.getLocalisedString("options.option.mode.normal", new Object[] { bto.c(InputHandler.keybindSneak.i()), bto.c(InputHandler.keybindActivate.i()) });
/*     */     }
/*     */ 
/*     */     
/*     */     public BindingComboMode getNextMode() {
/*  82 */       if (this == Direct) return Disabled; 
/*  83 */       if (this == Disabled) return Normal; 
/*  84 */       return Direct;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   public static bsr keybindOverride = new bsr("key.macro_override", 0, "key.categories.macros");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   public static bsr keybindActivate = new bsr("key.macros", 41, "key.categories.macros");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   public static bsr keybindSneak = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   private static int overrideKeyCode = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   private static int sneakKeyCode = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean overrideKeyDown = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean modifierKeyDown = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 131 */   private static int fallbackFailureCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 136 */   private static int fallbackFailureLimit = 1000;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean fallbackMode = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 146 */   public int tickAteKeys = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean tickUpdated = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Macros macros;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 161 */   private boolean[] pressedKeys = new boolean[256];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 166 */   private ArrayList<KeyEvent> keyEventQueue = new ArrayList<KeyEvent>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 171 */   private ArrayList<KeyEvent> pendingKeyEvents = new ArrayList<KeyEvent>();
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int BUFFER_SIZE = 50;
/*     */ 
/*     */ 
/*     */   
/*     */   private ByteBuffer keyboardEventBuffer;
/*     */ 
/*     */ 
/*     */   
/*     */   private ByteBuffer mouseEventBuffer;
/*     */ 
/*     */   
/* 186 */   private static ByteBuffer keyDownOverrideBuffer = BufferUtils.createByteBuffer(256);
/*     */   
/* 188 */   private ArrayList<IInputHandlerModule> inputHandlerModules = new ArrayList<IInputHandlerModule>();
/*     */   
/*     */   private static MacroModCore core;
/*     */   
/* 192 */   private int mWheelUpDamping = 0;
/* 193 */   private int mWheelDownDamping = 0;
/*     */   
/* 195 */   private KeyEvent nextEvent = null;
/*     */ 
/*     */   
/*     */   private boolean nextEventProcessed = false;
/*     */ 
/*     */   
/*     */   private ByteBuffer mouseReadBuffer;
/*     */   
/*     */   private ByteBuffer mouseDownBuffer;
/*     */   
/*     */   private ByteBuffer keyReadBuffer;
/*     */   
/*     */   private ByteBuffer keyDownBuffer;
/*     */ 
/*     */   
/*     */   public InputHandler(MacroModCore core) {
/* 211 */     InputHandler.core = core;
/*     */ 
/*     */     
/* 214 */     this.keyboardEventBuffer = ByteBuffer.allocate(900);
/* 215 */     this.mouseEventBuffer = ByteBuffer.allocate(1100);
/*     */ 
/*     */     
/* 218 */     MacroModCore.registerSettingsProvider(this);
/*     */     
/* 220 */     getBuffers();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getBuffers() {
/*     */     try {
/* 231 */       this.mouseReadBuffer = (ByteBuffer)Reflection.getPrivateValue(Mouse.class, null, "readBuffer");
/* 232 */       this.mouseDownBuffer = (ByteBuffer)Reflection.getPrivateValue(Mouse.class, null, "buttons");
/* 233 */       this.keyReadBuffer = (ByteBuffer)Reflection.getPrivateValue(Keyboard.class, null, "readBuffer");
/* 234 */       this.keyDownBuffer = (ByteBuffer)Reflection.getPrivateValue(Keyboard.class, null, "keyDownBuffer");
/*     */     }
/* 236 */     catch (Exception ex) {
/*     */       
/* 238 */       ex.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addModule(IInputHandlerModule module) {
/* 248 */     if (module != null && !this.inputHandlerModules.contains(module)) {
/*     */       
/* 250 */       MacroModCore.registerSettingsProvider(module);
/* 251 */       this.inputHandlerModules.add(module);
/* 252 */       module.initialise(this);
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
/*     */   public boolean register(bsu minecraft, Macros macros) {
/* 265 */     this.macros = macros;
/*     */ 
/*     */     
/* 268 */     if (MacroModSettings.compatibilityMode)
/*     */     {
/* 270 */       fallbackMode = true;
/*     */     }
/*     */     
/* 273 */     if (!fallbackMode) {
/*     */       
/*     */       try {
/*     */         
/* 277 */         for (IInputHandlerModule module : this.inputHandlerModules)
/*     */         {
/* 279 */           module.register(minecraft, (ISettingsProvider)macros);
/*     */         }
/*     */       }
/* 282 */       catch (Exception ex) {
/*     */         
/* 284 */         Log.printStackTrace(ex);
/* 285 */         fallbackMode = true;
/*     */       } 
/*     */     }
/*     */     
/* 289 */     return !fallbackMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPendingKeyEventCount() {
/* 299 */     return (this.pendingKeyEvents != null) ? this.pendingKeyEvents.size() : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifySettingsCleared() {
/* 305 */     CharMap.notifySettingsCleared();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifySettingsLoaded(ISettingsProvider settings) {
/* 311 */     CharMap.notifySettingsLoaded(settings);
/*     */ 
/*     */     
/* 314 */     if (MacroModSettings.compatibilityMode)
/*     */     {
/* 316 */       fallbackMode = true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveSettings(ISettingsProvider settings) {
/* 323 */     CharMap.saveSettings(settings);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pumpKeyChars(String keyChars, boolean deep) {
/* 333 */     for (int c = 0; c < keyChars.length(); c++) {
/* 334 */       pumpKeyChar(keyChars.charAt(c), deep);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pumpKeyChar(char keyChar, boolean deep) {
/* 344 */     this.pendingKeyEvents.add(new KeyEvent(keyChar, true, deep));
/* 345 */     this.pendingKeyEvents.add(new KeyEvent(keyChar, false, deep));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pumpKeyCode(int keyCode, boolean deep) {
/* 355 */     this.pendingKeyEvents.add(new KeyEvent(keyCode, true, deep));
/* 356 */     this.pendingKeyEvents.add(new KeyEvent(keyCode, false, deep));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTimerUpdate(bsu minecraft) {
/* 361 */     if (this.nextEvent != null && this.nextEventProcessed) {
/*     */       
/* 363 */       this.nextEvent = null;
/* 364 */       this.nextEventProcessed = false;
/*     */     } 
/*     */     
/* 367 */     if (this.nextEvent == null && this.pendingKeyEvents.size() > 0)
/*     */     {
/* 369 */       this.nextEvent = this.pendingKeyEvents.remove(0);
/*     */     }
/*     */     
/* 372 */     processBuffers(minecraft, true, true, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void processBuffers(bsu minecraft, boolean readEvents, boolean injectEvents, boolean allowProxyInjection) {
/* 377 */     overrideKeyCode = keybindOverride.i();
/* 378 */     sneakKeyCode = keybindSneak.i();
/*     */     
/* 380 */     if (overrideKeyCode == 0)
/*     */     {
/* 382 */       overrideKeyCode = minecraft.t.af.i();
/*     */     }
/*     */     
/* 385 */     boolean isControlsGui = minecraft.m instanceof byj;
/*     */     
/* 387 */     this.overrideKeyDown = (MacroModSettings.enableOverride && isKeyDown(overrideKeyCode) && !isControlsGui);
/* 388 */     this.modifierKeyDown = isKeyDown(sneakKeyCode);
/* 389 */     this.tickAteKeys = 0;
/*     */ 
/*     */     
/* 392 */     if (!fallbackMode) {
/*     */       
/* 394 */       if (minecraft.f == null && !isControlsGui) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 399 */       minecraft.y.a("inputprocessor");
/*     */ 
/*     */       
/* 402 */       this.tickUpdated = true;
/* 403 */       boolean isOverridableScreen = checkForOverridableScreen(minecraft);
/*     */       
/* 405 */       for (IInputHandlerModule module : this.inputHandlerModules)
/*     */       {
/* 407 */         module.update(this.keyEventQueue, this.overrideKeyDown, this.modifierKeyDown);
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 412 */         boolean processedKeyboardBuffer = false, processedMouseBuffer = false;
/* 413 */         this.keyboardEventBuffer.clear();
/* 414 */         this.mouseEventBuffer.clear();
/*     */         
/* 416 */         if (injectEvents)
/*     */         {
/* 418 */           for (IInputHandlerModule module : this.inputHandlerModules)
/*     */           {
/* 420 */             processedKeyboardBuffer |= module.injectEvents(this.keyboardEventBuffer, this.keyDownBuffer, this.mouseEventBuffer, this.mouseDownBuffer);
/*     */           }
/*     */         }
/*     */         
/* 424 */         if (readEvents && minecraft.f != null) {
/*     */           
/* 426 */           this.mouseReadBuffer.mark();
/*     */ 
/*     */           
/* 429 */           while (this.mouseReadBuffer.hasRemaining()) {
/*     */             
/* 431 */             byte mouseButton = this.mouseReadBuffer.get();
/* 432 */             int mouseBindIndex = mouseButton + ((mouseButton > 4) ? 235 : 250);
/* 433 */             byte mouseButtonState = this.mouseReadBuffer.get();
/* 434 */             int mouseX = this.mouseReadBuffer.getInt();
/* 435 */             int mouseY = this.mouseReadBuffer.getInt();
/* 436 */             int mouseDwheel = this.mouseReadBuffer.getInt();
/* 437 */             long mouseNanos = this.mouseReadBuffer.getLong();
/*     */ 
/*     */             
/* 440 */             boolean damping = false;
/* 441 */             boolean eatMouseEvent = false;
/*     */             
/* 443 */             if (mouseButton == -1 && mouseDwheel != 0)
/*     */             {
/* 445 */               if (mouseDwheel > 0) {
/*     */                 
/* 447 */                 mouseBindIndex = 248;
/* 448 */                 mouseButtonState = 1;
/* 449 */                 if (this.mWheelUpDamping != 0) damping = true; 
/* 450 */                 this.mWheelUpDamping = 1;
/*     */               }
/* 452 */               else if (mouseDwheel < 0) {
/*     */                 
/* 454 */                 mouseBindIndex = 249;
/* 455 */                 mouseButtonState = 1;
/* 456 */                 if (this.mWheelDownDamping != 0) damping = true; 
/* 457 */                 this.mWheelDownDamping = 1;
/*     */               } 
/*     */             }
/*     */             
/* 461 */             boolean override = this.macros.isKeyAlwaysOverridden(mouseBindIndex, true, false);
/* 462 */             if (this.overrideKeyDown) override = !override;
/*     */             
/* 464 */             if (override && isOverridableScreen && minecraft.m == null && this.macros.isMacroBound(mouseBindIndex, true)) {
/*     */               
/* 466 */               processedMouseBuffer = true;
/*     */               
/* 468 */               if (mouseButtonState != 0) eatMouseEvent = true;
/*     */               
/* 470 */               if (!damping) this.keyEventQueue.add(new KeyEvent(mouseBindIndex, false, (mouseButtonState != 0), true, this.modifierKeyDown));
/*     */             
/* 472 */             } else if (MacroModCore.getInstance().isCapturingThumbnail() && mouseButtonState != 0) {
/*     */               
/* 474 */               eatMouseEvent = true;
/* 475 */               processedMouseBuffer = true;
/* 476 */               if (!damping) this.keyEventQueue.add(new KeyEvent(mouseBindIndex, false, (mouseButtonState != 0), true, this.modifierKeyDown));
/*     */             
/*     */             } 
/* 479 */             if (!eatMouseEvent) {
/*     */               
/* 481 */               if ((minecraft.m == null || mouseButtonState == 0) && (this.macros.isMacroBound(mouseBindIndex, true) || mouseBindIndex == keybindActivate.i() || mouseBindIndex == overrideKeyCode))
/*     */               {
/* 483 */                 if (!damping) this.keyEventQueue.add(new KeyEvent(mouseBindIndex, false, (mouseButtonState != 0), this.overrideKeyDown, this.modifierKeyDown));
/*     */               
/*     */               }
/* 486 */               this.mouseEventBuffer.put(mouseButton);
/* 487 */               this.mouseEventBuffer.put(mouseButtonState);
/* 488 */               this.mouseEventBuffer.putInt(mouseX);
/* 489 */               this.mouseEventBuffer.putInt(mouseY);
/* 490 */               this.mouseEventBuffer.putInt(mouseDwheel);
/* 491 */               this.mouseEventBuffer.putLong(mouseNanos);
/*     */             } 
/*     */           } 
/*     */           
/* 495 */           this.mouseReadBuffer.reset();
/*     */           
/* 497 */           this.keyReadBuffer.mark();
/*     */ 
/*     */           
/* 500 */           while (this.keyReadBuffer.hasRemaining()) {
/*     */             
/* 502 */             boolean eatKeyEvent = false;
/*     */             
/* 504 */             int key = this.keyReadBuffer.getInt() & 0xFF;
/* 505 */             byte state = this.keyReadBuffer.get();
/* 506 */             int character = this.keyReadBuffer.getInt();
/* 507 */             long nanos = this.keyReadBuffer.getLong();
/* 508 */             byte repeat = this.keyReadBuffer.get();
/*     */ 
/*     */ 
/*     */             
/* 512 */             boolean override = this.macros.isKeyAlwaysOverridden(key, true, false);
/* 513 */             if (this.overrideKeyDown) override = !override;
/*     */             
/* 515 */             if (state == 0) keyDownOverrideBuffer.put(key, (byte)0);
/*     */             
/* 517 */             if (override && isOverridableScreen && this.macros.isMacroBound(key, true)) {
/*     */               
/* 519 */               processedKeyboardBuffer = true;
/*     */               
/* 521 */               if (state != 0) {
/*     */                 
/* 523 */                 keyDownOverrideBuffer.put(key, (byte)1);
/* 524 */                 eatKeyEvent = true;
/*     */               } 
/*     */               
/* 527 */               if (repeat != 1)
/*     */               {
/* 529 */                 this.keyEventQueue.add(new KeyEvent(key, (char)character, (state != 0), true, this.modifierKeyDown));
/*     */               }
/*     */             }
/* 532 */             else if (MacroModCore.getInstance().isCapturingThumbnail() && state != 0 && (key == 28 || key == 156 || key == 1)) {
/*     */               
/* 534 */               eatKeyEvent = true;
/* 535 */               processedKeyboardBuffer = true;
/* 536 */               this.keyEventQueue.add(new KeyEvent(key, (char)character, (state != 0), true, this.modifierKeyDown));
/*     */             } 
/*     */             
/* 539 */             if (!eatKeyEvent) {
/*     */               
/* 541 */               if ((minecraft.m == null || state == 0) && repeat != 1 && (this.macros.isMacroBound(key, true) || key == keybindActivate.i() || key == overrideKeyCode))
/*     */               {
/* 543 */                 this.keyEventQueue.add(new KeyEvent(key, (char)character, (state != 0), this.overrideKeyDown, this.modifierKeyDown));
/*     */               }
/*     */               
/* 546 */               this.keyboardEventBuffer.putInt(key);
/* 547 */               this.keyboardEventBuffer.put(state);
/* 548 */               this.keyboardEventBuffer.putInt(character);
/* 549 */               this.keyboardEventBuffer.putLong(nanos);
/* 550 */               this.keyboardEventBuffer.put(repeat);
/*     */             } 
/*     */           } 
/*     */           
/* 554 */           this.keyReadBuffer.reset();
/*     */ 
/*     */ 
/*     */           
/* 558 */           for (int k = 0; k < this.keyDownBuffer.capacity(); k++) {
/*     */             
/* 560 */             if (keyDownOverrideBuffer.get(k) > 0)
/*     */             {
/* 562 */               if (this.keyDownBuffer.get(k) != 0) {
/*     */                 
/* 564 */                 this.keyDownBuffer.put(k, (byte)0);
/* 565 */                 this.tickAteKeys++;
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 571 */         if (injectEvents && minecraft.f != null)
/*     */         {
/*     */           
/* 574 */           if (this.nextEvent != null) {
/*     */             
/* 576 */             processedKeyboardBuffer = true;
/*     */ 
/*     */ 
/*     */             
/*     */             try {
/* 581 */               if (!this.nextEventProcessed) {
/*     */                 
/* 583 */                 this.keyboardEventBuffer.putInt(this.nextEvent.key);
/* 584 */                 this.keyboardEventBuffer.put((byte)(this.nextEvent.state ? 1 : 0));
/* 585 */                 this.keyboardEventBuffer.putInt(this.nextEvent.character);
/* 586 */                 this.keyboardEventBuffer.putLong(1L);
/* 587 */                 this.keyboardEventBuffer.put((byte)0);
/*     */               } 
/*     */               
/* 590 */               this.nextEventProcessed = true;
/*     */ 
/*     */               
/* 593 */               this.keyDownBuffer.put(this.nextEvent.key, (byte)1);
/*     */               
/* 595 */               if (!MacroModSettings.disableDeepInjection && this.nextEvent.deep && allowProxyInjection && !(minecraft.h instanceof KeyInjectionProxy))
/*     */               {
/* 597 */                 minecraft.h = new KeyInjectionProxy(this, minecraft);
/*     */               }
/*     */             }
/* 600 */             catch (BufferOverflowException bufferOverflowException) {}
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/* 605 */         if (processedMouseBuffer) {
/*     */           
/* 607 */           this.mouseEventBuffer.flip();
/* 608 */           this.mouseReadBuffer.clear();
/* 609 */           this.mouseReadBuffer.put(this.mouseEventBuffer);
/* 610 */           this.mouseReadBuffer.flip();
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 615 */         if (processedKeyboardBuffer)
/*     */         {
/* 617 */           this.keyboardEventBuffer.flip();
/* 618 */           this.keyReadBuffer.clear();
/* 619 */           this.keyReadBuffer.put(this.keyboardEventBuffer);
/* 620 */           this.keyReadBuffer.flip();
/*     */         }
/*     */       
/* 623 */       } catch (Exception ex) {
/*     */         
/* 625 */         fallbackFailureCount++;
/*     */         
/* 627 */         if (fallbackFailureCount > fallbackFailureLimit) {
/*     */           
/* 629 */           Log.info("Error in custom input handler: {0}", new Object[] { ex.getMessage() });
/* 630 */           fallbackMode = true;
/*     */         } 
/*     */       } 
/*     */       
/* 634 */       minecraft.y.b();
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
/*     */   public void onTick(float f, bsu minecraft) {
/* 646 */     if (this.mWheelUpDamping > 0) {
/* 647 */       this.mWheelUpDamping--;
/*     */     }
/* 649 */     if (this.mWheelDownDamping > 0) {
/* 650 */       this.mWheelDownDamping--;
/*     */     }
/* 652 */     if (!this.tickUpdated && !fallbackMode) {
/*     */       
/* 654 */       Log.info("Enhanced key capture failed, enabling fallback mode");
/* 655 */       fallbackMode = true;
/*     */     } 
/*     */     
/* 658 */     this.tickUpdated = false;
/*     */     
/* 660 */     if (!fallbackMode) {
/*     */       
/* 662 */       for (IInputHandlerModule module : this.inputHandlerModules) {
/*     */         
/* 664 */         if (!module.onTick(this.keyEventQueue))
/*     */           return; 
/*     */       } 
/* 667 */       while (this.keyEventQueue.size() > 0)
/*     */       {
/* 669 */         KeyEvent keyEvent = this.keyEventQueue.remove(0);
/*     */         
/* 671 */         if (keyEvent.state) {
/*     */           
/* 673 */           handleKey(keyEvent, minecraft);
/*     */           
/* 675 */           if (keyEvent.key == 248 || keyEvent.key == 249) {
/*     */             
/* 677 */             keyEvent.state = false;
/* 678 */             this.keyEventQueue.add(keyEvent);
/*     */           } 
/*     */           
/*     */           continue;
/*     */         } 
/* 683 */         this.pressedKeys[keyEvent.key] = false;
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 689 */       int key = Keyboard.getEventKey();
/*     */       
/* 691 */       this.overrideKeyDown = (MacroModSettings.enableOverride && isKeyDown(overrideKeyCode));
/* 692 */       this.modifierKeyDown = isKeyDown(sneakKeyCode);
/*     */       
/* 694 */       if (Keyboard.getEventKeyState()) {
/*     */ 
/*     */         
/* 697 */         if (minecraft.m == null)
/*     */         {
/* 699 */           handleKey(key, this.overrideKeyDown, this.modifierKeyDown, minecraft);
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 704 */         this.pressedKeys[key] = false;
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
/*     */   private void handleKey(KeyEvent event, bsu minecraft) {
/* 717 */     handleKey(event.key, event.overrideKeyDown, event.modifierKeyDown, minecraft);
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
/*     */   private void handleKey(int key, boolean overrideKeyDown, boolean modifierKeyDown, bsu minecraft) {
/* 730 */     if (key == keybindActivate.i() && !overrideKeyDown && !this.pressedKeys[keybindActivate.i()]) {
/*     */       
/* 732 */       if (MacroModSettings.bindingMode != BindingComboMode.Disabled) {
/*     */         
/* 734 */         if (MacroModSettings.bindingMode == BindingComboMode.Direct) modifierKeyDown = !modifierKeyDown;
/*     */ 
/*     */         
/* 737 */         if (modifierKeyDown) {
/* 738 */           minecraft.a((bxf)new GuiMacroBind(true));
/*     */         } else {
/* 740 */           minecraft.a((bxf)new GuiMacroPlayback());
/*     */         } 
/*     */       } else {
/*     */         
/* 744 */         minecraft.a((bxf)new GuiMacroPlayback());
/*     */       }
/*     */     
/* 747 */     } else if (MacroModSettings.compatibilityMode && MacroModSettings.enableOverride && key == overrideKeyCode) {
/*     */ 
/*     */       
/* 750 */       if (!modifierKeyDown && checkForOverridableScreen(minecraft))
/*     */       {
/* 752 */         minecraft.a((bxf)new GuiMacroPlayback(true, null));
/*     */       }
/*     */     }
/* 755 */     else if (MacroModCore.getInstance().isCapturingThumbnail()) {
/*     */       
/* 757 */       if (key > 249 || key == 28 || key == 156)
/*     */       {
/* 759 */         MacroModCore.getInstance().captureThumbnail();
/*     */       }
/* 761 */       else if (key == 1)
/*     */       {
/* 763 */         MacroModCore.getInstance().cancelCaptureThumbnail(true);
/*     */       }
/*     */     
/* 766 */     } else if (key > 0) {
/*     */       
/* 768 */       if (!this.pressedKeys[key]) {
/*     */         
/* 770 */         this.pressedKeys[key] = true;
/* 771 */         this.macros.autoPlayMacro(key, overrideKeyDown);
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
/*     */   public void notifyKeyDown(int key) {
/* 783 */     this.pressedKeys[key] = true;
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
/*     */   public static boolean checkForOverridableScreen(bsu minecraft) {
/* 795 */     if (minecraft.m instanceof net.eq2online.macros.gui.shared.GuiDialogBox) return false; 
/* 796 */     if (minecraft.m instanceof net.eq2online.macros.gui.screens.GuiDesignerBase) return false; 
/* 797 */     if (minecraft.m instanceof net.eq2online.macros.gui.screens.GuiMacroEdit) return false; 
/* 798 */     if (minecraft.m instanceof net.eq2online.macros.gui.screens.GuiCustomGui) return false; 
/* 799 */     if (minecraft.m instanceof net.eq2online.macros.gui.screens.GuiMacroParam) return false; 
/* 800 */     if (minecraft.m instanceof net.eq2online.macros.gui.screens.GuiEditText) return false; 
/* 801 */     if (minecraft.m instanceof bwv) return false; 
/* 802 */     if (minecraft.m instanceof byj) return false; 
/* 803 */     if (minecraft.m instanceof bxr) return false; 
/* 804 */     if (minecraft.m instanceof bws) return false; 
/* 805 */     if (minecraft.m instanceof bvw) return false; 
/* 806 */     if (minecraft.m instanceof bxm) return false; 
/* 807 */     if (minecraft.m instanceof bzx) return false; 
/* 808 */     if (minecraft.m instanceof net.eq2online.macros.gui.designable.editor.GuiLayoutPatch) return false; 
/* 809 */     if (minecraft.m instanceof bvx && !MacroModSettings.enableOverrideChat) return false; 
/* 810 */     if (core != null && core.getAutoDiscoveryAgent().isActive()) return false;
/*     */     
/* 812 */     return true;
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
/*     */   public static boolean isKeyDown(int keyCode) {
/*     */     try {
/* 825 */       if (keyCode == 0)
/*     */       {
/* 827 */         return false;
/*     */       }
/* 829 */       if (keyCode < 0)
/*     */       {
/* 831 */         return Mouse.isButtonDown(keyCode + 100);
/*     */       }
/* 833 */       if (keyCode < 255)
/*     */       {
/* 835 */         return Keyboard.isKeyDown(keyCode);
/*     */       }
/*     */     }
/* 838 */     catch (ArrayIndexOutOfBoundsException ex) {
/*     */       
/* 840 */       return false;
/*     */     } 
/*     */     
/* 843 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isKeyReallyDown(int keyCode) {
/* 848 */     if (keyCode > 0 && keyCode < 255 && keyDownOverrideBuffer.get(keyCode) == 1)
/*     */     {
/* 850 */       return true;
/*     */     }
/*     */     
/* 853 */     return isKeyDown(keyCode);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getOverrideKeyCode() {
/* 858 */     return overrideKeyCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getSneakKeyCode() {
/* 863 */     return sneakKeyCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class KeyEvent
/*     */   {
/*     */     public int key;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public char character;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean state;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean overrideKeyDown;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean modifierKeyDown;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean deep;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KeyEvent(int key, char character, boolean state, boolean overrideKeyDown, boolean modifierKeyDown) {
/* 904 */       this.key = key;
/* 905 */       this.character = character;
/* 906 */       this.state = state;
/* 907 */       this.overrideKeyDown = overrideKeyDown;
/* 908 */       this.modifierKeyDown = modifierKeyDown;
/* 909 */       this.deep = false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KeyEvent(char character, boolean state, boolean deep) {
/* 920 */       this.key = CharMap.getKeyCode(character);
/* 921 */       this.character = character;
/* 922 */       this.state = state;
/* 923 */       this.deep = deep;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KeyEvent(int key, boolean state, boolean deep) {
/* 934 */       this.key = key;
/* 935 */       this.character = CharMap.getKeyChar(key);
/* 936 */       this.state = state;
/* 937 */       this.deep = deep;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\input\InputHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */