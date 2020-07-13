/*     */ package net.eq2online.macros.event;
/*     */ 
/*     */ import alq;
/*     */ import amj;
/*     */ import amk;
/*     */ import bsu;
/*     */ import ces;
/*     */ import cio;
/*     */ import cwc;
/*     */ import hg;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.SpamFilter;
/*     */ import net.eq2online.macros.event.providers.OnSendChatMessageProvider;
/*     */ import net.eq2online.macros.interfaces.IChatEventListener;
/*     */ import net.eq2online.macros.interfaces.IMultipleConfigurations;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventDispatcher;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventManager;
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
/*     */ public class MacroEventDispatcherBuiltin
/*     */   implements IMacroEventDispatcher, IChatEventListener, IMultipleConfigurations
/*     */ {
/*     */   protected bsu mc;
/*     */   protected Macros macros;
/*     */   protected SpamFilter spamFilter;
/*     */   protected MacroEventValueWatcher<Object> healthWatcher;
/*     */   protected MacroEventValueWatcher<Object> foodWatcher;
/*     */   protected MacroEventValueWatcher<Object> armourWatcher;
/*     */   protected MacroEventValueWatcher<Object> worldWatcher;
/*     */   protected MacroEventValueWatcher<Object> modeWatcher;
/*     */   protected MacroEventValueWatcher<Object> inventorySlotWatcher;
/*     */   protected MacroEventValueWatcher<Object> oxygenWatcher;
/*     */   protected MacroEventValueWatcher<Object> xpWatcher;
/*     */   protected MacroEventValueWatcher<Object> levelWatcher;
/*     */   protected MacroEventValueWatcher<Object> durabilityWatcher;
/*     */   protected MacroEventValueWatcher<Object> weatherWatcher;
/*     */   protected MacroEventValueWatcher<Object> guiWatcher;
/*     */   protected MacroEventValueWatcher<Object> helmDurabilityWatcher;
/*     */   protected MacroEventValueWatcher<Object> chestPlateDurabilityWatcher;
/*     */   protected MacroEventValueWatcher<Object> leggingsDurabilityWatcher;
/*     */   protected MacroEventValueWatcher<Object> bootsDurabilityWatcher;
/*     */   protected MacroEventListWatcher<Collection<ces>, ces> playerListWatcher;
/*     */   protected amj lastItemStack;
/*     */   protected amj lastHelm;
/*     */   protected amj lastChestPlate;
/*     */   protected amj lastLeggings;
/*     */   protected amj lastBoots;
/*  76 */   protected int lastInventorySlot = -1;
/*     */   
/*  78 */   protected long joinedGameDelay = 0L;
/*     */   
/*  80 */   private Map<String, OnSendChatMessageProvider> chatMessageHandlers = new HashMap<String, OnSendChatMessageProvider>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MacroEventDispatcherBuiltin(Macros macros, bsu minecraft) {
/*  90 */     this.macros = macros;
/*  91 */     this.mc = minecraft;
/*     */     
/*  93 */     this.healthWatcher = new MacroEventValueWatcher(BuiltinEvents.onHealthChange.getName(), 10, true);
/*  94 */     this.foodWatcher = new MacroEventValueWatcher(BuiltinEvents.onFoodChange.getName(), 10, true);
/*  95 */     this.armourWatcher = new MacroEventValueWatcher(BuiltinEvents.onArmourChange.getName(), 10, true);
/*  96 */     this.worldWatcher = new MacroEventValueWatcher(BuiltinEvents.onWorldChange.getName(), 1, true);
/*  97 */     this.modeWatcher = new MacroEventValueWatcher(BuiltinEvents.onModeChange.getName(), 10, true);
/*  98 */     this.inventorySlotWatcher = new MacroEventValueWatcher(BuiltinEvents.onInventorySlotChange.getName(), 11, true);
/*  99 */     this.oxygenWatcher = new MacroEventValueWatcher(BuiltinEvents.onOxygenChange.getName(), 10, true);
/* 100 */     this.xpWatcher = new MacroEventValueWatcher(BuiltinEvents.onXPChange.getName(), 10, true);
/* 101 */     this.levelWatcher = new MacroEventValueWatcher(BuiltinEvents.onLevelChange.getName(), 10, true);
/* 102 */     this.durabilityWatcher = new MacroEventValueWatcher(BuiltinEvents.onItemDurabilityChange.getName(), 10, true);
/* 103 */     this.weatherWatcher = new MacroEventValueWatcher(BuiltinEvents.onWeatherChange.getName(), 1, true);
/* 104 */     this.guiWatcher = new MacroEventValueWatcher(BuiltinEvents.onShowGui.getName(), 5, true);
/*     */     
/* 106 */     this.helmDurabilityWatcher = new MacroEventValueWatcher(BuiltinEvents.onArmourDurabilityChange.getName(), 10, true);
/* 107 */     this.chestPlateDurabilityWatcher = new MacroEventValueWatcher(BuiltinEvents.onArmourDurabilityChange.getName(), 10, true);
/* 108 */     this.leggingsDurabilityWatcher = new MacroEventValueWatcher(BuiltinEvents.onArmourDurabilityChange.getName(), 10, true);
/* 109 */     this.bootsDurabilityWatcher = new MacroEventValueWatcher(BuiltinEvents.onArmourDurabilityChange.getName(), 10, true);
/*     */     
/* 111 */     this.playerListWatcher = new MacroEventListWatcher<Collection<ces>, ces>(BuiltinEvents.onPlayerJoined.getName(), 30, true);
/*     */     
/* 113 */     MacroModCore.registerChatListener(this);
/* 114 */     MacroModCore.registerMultipleConfigurationObject(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initSinglePlayer() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTick(IMacroEventManager manager, bsu minecraft) {
/* 130 */     cio thePlayer = AbstractionLayer.getPlayer();
/*     */     
/* 132 */     if (thePlayer == null)
/* 133 */       return;  if (thePlayer.bg == null)
/* 134 */       return;  if (AbstractionLayer.getPlayerController() == null)
/*     */       return; 
/* 136 */     if (this.joinedGameDelay > 0L) {
/* 137 */       this.joinedGameDelay--;
/*     */     }
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
/* 159 */     if (this.worldWatcher.checkValue(AbstractionLayer.getWorld())) {
/*     */       
/* 161 */       this.worldWatcher.sendEvent(this.macros, new String[0]);
/*     */ 
/*     */       
/* 164 */       this.healthWatcher.checkValueAndSuppress(Float.valueOf(thePlayer.bm()));
/* 165 */       this.foodWatcher.checkValueAndSuppress(Integer.valueOf(thePlayer.ck().a()));
/* 166 */       this.armourWatcher.checkValueAndSuppress(Integer.valueOf(thePlayer.bq()));
/* 167 */       this.modeWatcher.checkValueAndSuppress(Boolean.valueOf(AbstractionLayer.getPlayerController().h()));
/* 168 */       this.oxygenWatcher.checkValueAndSuppress(Integer.valueOf(thePlayer.aA()));
/* 169 */       this.xpWatcher.checkValueAndSuppress(Float.valueOf(thePlayer.bB));
/* 170 */       this.levelWatcher.checkValueAndSuppress(Integer.valueOf(thePlayer.bz));
/* 171 */       this.weatherWatcher.checkValueAndSuppress(Float.valueOf(AbstractionLayer.getWorld().j(0.0F)));
/* 172 */       this.guiWatcher.checkValueAndSuppress(minecraft.m);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 178 */     amj currentItemStack = thePlayer.bg.h();
/* 179 */     boolean checkForDurabilityChange = (currentItemStack == this.lastItemStack);
/*     */     
/* 181 */     if (!checkForDurabilityChange && currentItemStack != null)
/*     */     {
/* 183 */       if (this.lastInventorySlot == thePlayer.bg.c && currentItemStack
/* 184 */         .b() != null && (currentItemStack
/* 185 */         .b().equals(amk.aR) || currentItemStack
/* 186 */         .b().equals(amk.be) || currentItemStack
/* 187 */         .b().equals(amk.L) || currentItemStack
/* 188 */         .b().equals(amk.M) || currentItemStack
/* 189 */         .b().equals(amk.K) || currentItemStack
/* 190 */         .b().equals(amk.J) || currentItemStack
/* 191 */         .b().equals(amk.I)))
/*     */       {
/* 193 */         checkForDurabilityChange = true;
/*     */       }
/*     */     }
/*     */     
/* 197 */     if (checkForDurabilityChange) {
/*     */ 
/*     */       
/* 200 */       if (currentItemStack != null && this.durabilityWatcher.checkValue(Integer.valueOf(currentItemStack.i()))) {
/* 201 */         this.durabilityWatcher.sendEvent(this.macros, new String[0]);
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 206 */     else if (this.lastItemStack == null || (currentItemStack != null && !currentItemStack.a(this.lastItemStack))) {
/* 207 */       this.durabilityWatcher.suppressNext();
/*     */     } 
/*     */ 
/*     */     
/* 211 */     this.lastItemStack = currentItemStack;
/* 212 */     this.lastInventorySlot = thePlayer.bg.c;
/*     */     
/* 214 */     boolean armourDurabilityChanged = false;
/*     */ 
/*     */     
/* 217 */     amj currentHelm = thePlayer.bg.e(3);
/*     */     
/* 219 */     if (currentHelm != null && this.lastHelm == null) {
/* 220 */       this.helmDurabilityWatcher.suppressNext();
/*     */     }
/*     */     
/* 223 */     if (currentHelm != null && this.helmDurabilityWatcher.checkValue(Integer.valueOf(currentHelm.i()))) {
/*     */       
/* 225 */       armourDurabilityChanged = true;
/* 226 */       this.helmDurabilityWatcher.sendEvent(this.macros, new String[0]);
/*     */     } 
/*     */ 
/*     */     
/* 230 */     amj currentChestplate = thePlayer.bg.e(2);
/*     */     
/* 232 */     if (currentChestplate != null && this.lastChestPlate == null) {
/* 233 */       this.chestPlateDurabilityWatcher.suppressNext();
/*     */     }
/*     */     
/* 236 */     if (currentChestplate != null && this.chestPlateDurabilityWatcher.checkValue(Integer.valueOf(currentChestplate.i())) && !armourDurabilityChanged) {
/*     */       
/* 238 */       armourDurabilityChanged = true;
/* 239 */       this.chestPlateDurabilityWatcher.sendEvent(this.macros, new String[0]);
/*     */     } 
/*     */ 
/*     */     
/* 243 */     amj currentLeggings = thePlayer.bg.e(1);
/*     */     
/* 245 */     if (currentLeggings != null && this.lastLeggings == null) {
/* 246 */       this.leggingsDurabilityWatcher.suppressNext();
/*     */     }
/*     */     
/* 249 */     if (currentLeggings != null && this.leggingsDurabilityWatcher.checkValue(Integer.valueOf(currentLeggings.i())) && !armourDurabilityChanged) {
/*     */       
/* 251 */       armourDurabilityChanged = true;
/* 252 */       this.leggingsDurabilityWatcher.sendEvent(this.macros, new String[0]);
/*     */     } 
/*     */ 
/*     */     
/* 256 */     amj currentBoots = thePlayer.bg.e(0);
/*     */     
/* 258 */     if (currentBoots != null && this.lastBoots == null) {
/* 259 */       this.bootsDurabilityWatcher.suppressNext();
/*     */     }
/*     */     
/* 262 */     if (currentBoots != null && this.bootsDurabilityWatcher.checkValue(Integer.valueOf(currentBoots.i())) && !armourDurabilityChanged) {
/*     */       
/* 264 */       armourDurabilityChanged = true;
/* 265 */       this.bootsDurabilityWatcher.sendEvent(this.macros, new String[0]);
/*     */     } 
/*     */     
/* 268 */     this.lastHelm = currentHelm;
/* 269 */     this.lastChestPlate = currentChestplate;
/* 270 */     this.lastLeggings = currentLeggings;
/* 271 */     this.lastBoots = currentBoots;
/*     */ 
/*     */     
/* 274 */     this.weatherWatcher.checkValueAndDispatch(Float.valueOf(AbstractionLayer.getWorld().j(0.0F)), this.macros, new String[0]);
/* 275 */     this.modeWatcher.checkValueAndDispatch(Boolean.valueOf(AbstractionLayer.getPlayerController().h()), this.macros, new String[0]);
/* 276 */     this.healthWatcher.checkValueAndDispatch(Float.valueOf(thePlayer.bm()), this.macros, new String[0]);
/* 277 */     this.foodWatcher.checkValueAndDispatch(Integer.valueOf(thePlayer.ck().a()), this.macros, new String[0]);
/* 278 */     this.armourWatcher.checkValueAndDispatch(Integer.valueOf(thePlayer.bq()), this.macros, new String[0]);
/* 279 */     this.oxygenWatcher.checkValueAndDispatch(Integer.valueOf(thePlayer.aA()), this.macros, new String[0]);
/* 280 */     this.xpWatcher.checkValueAndDispatch(Float.valueOf(thePlayer.bB), this.macros, new String[0]);
/* 281 */     this.levelWatcher.checkValueAndDispatch(Integer.valueOf(thePlayer.bz), this.macros, new String[0]);
/* 282 */     this.guiWatcher.checkValueAndDispatch(minecraft.m, this.macros, new String[0]);
/*     */     
/* 284 */     if (this.inventorySlotWatcher.checkValue(Integer.valueOf(thePlayer.bg.c))) {
/* 285 */       this.inventorySlotWatcher.sendEvent(this.macros, new String[] { this.inventorySlotWatcher.getLastValue().toString() });
/*     */     }
/* 287 */     if ((AbstractionLayer.getWorld()).D)
/*     */     {
/* 289 */       if (this.playerListWatcher.checkValue(thePlayer.a.d()) && this.joinedGameDelay <= 0L) {
/*     */         
/* 291 */         ces NetworkPlayerInfo = this.playerListWatcher.getNewObject();
/*     */         
/* 293 */         String trimmedPlayerName = vb.a(NetworkPlayerInfo.a().getName());
/* 294 */         if (trimmedPlayerName.length() > MacroModSettings.trimCharsUserListStart) trimmedPlayerName = trimmedPlayerName.substring(MacroModSettings.trimCharsUserListStart); 
/* 295 */         if (trimmedPlayerName.length() > MacroModSettings.trimCharsUserListEnd) trimmedPlayerName = trimmedPlayerName.substring(0, trimmedPlayerName.length() - MacroModSettings.trimCharsUserListEnd);
/*     */         
/* 297 */         if (!trimmedPlayerName.equals(thePlayer.d_())) {
/* 298 */           this.playerListWatcher.sendEvent(this.macros, new String[] { trimmedPlayerName });
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onJoinGame(String serverName, SpamFilter spamFilter) {
/* 310 */     this.macros.sendEvent(BuiltinEvents.onJoinGame.getName(), 0, new String[] { serverName });
/* 311 */     this.playerListWatcher.reset();
/* 312 */     this.joinedGameDelay = 200L;
/* 313 */     this.spamFilter = spamFilter;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onItemPickup(amj itemStack) {
/* 318 */     String itemName = "Unknown Item";
/* 319 */     alq item = itemStack.b();
/*     */     
/* 321 */     if (item != null)
/*     */     {
/* 323 */       itemName = cwc.a(item.k(itemStack) + ".name", new Object[0]);
/*     */     }
/*     */     
/* 326 */     this.macros.sendEvent(BuiltinEvents.onPickupItem.getName(), 20, new String[] { Macros.getItemName(item), itemName, String.valueOf(itemStack.b), String.valueOf(itemStack.i()) });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void receiveChatPacket(String chatMessage, String chatMessageNoColours) {
/* 332 */     String[] chatLines = chatMessage.split("\\r?\\n");
/*     */     
/* 334 */     for (String chatLine : chatLines) {
/*     */       
/* 336 */       this.macros.sendEvent(BuiltinEvents.onChat.getName(), 40, new String[] { chatLine, vb.a(chatLine) });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onSendChatMessage(String message) {
/* 343 */     boolean pass = true;
/*     */     
/* 345 */     if (!this.spamFilter.isSending()) {
/*     */       
/* 347 */       String messageId = UUID.randomUUID().toString();
/* 348 */       this.macros.sendEvent(BuiltinEvents.onSendChatMessage.getName(), 2147483647, new String[] { message, messageId });
/* 349 */       OnSendChatMessageProvider handler = this.chatMessageHandlers.get(messageId);
/* 350 */       if (handler != null)
/*     */       {
/* 352 */         pass = handler.isPass();
/*     */       }
/*     */     } 
/*     */     
/* 356 */     return pass;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addChatMessageHandler(String uuid, OnSendChatMessageProvider provider) {
/* 361 */     this.chatMessageHandlers.put(uuid, provider);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onServerConnect(hg netclienthandler, SpamFilter spamFilter) {
/* 366 */     this.playerListWatcher.reset();
/* 367 */     this.joinedGameDelay = 200L;
/* 368 */     this.spamFilter = spamFilter;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyChangeConfiguration() {
/* 374 */     this.macros.sendEvent(BuiltinEvents.onConfigChange.getName(), 5, new String[0]);
/*     */   }
/*     */   
/*     */   public void addConfiguration(String configurationName, boolean copy) {}
/*     */   
/*     */   public void removeConfiguration(String configurationName) {}
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\event\MacroEventDispatcherBuiltin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */