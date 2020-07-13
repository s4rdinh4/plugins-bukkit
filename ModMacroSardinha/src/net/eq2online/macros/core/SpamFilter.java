/*     */ package net.eq2online.macros.core;
/*     */ 
/*     */ import ab;
/*     */ import ae;
/*     */ import bsu;
/*     */ import bty;
/*     */ import cio;
/*     */ import id;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.LinkedList;
/*     */ import lu;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.gui.screens.GuiMacroModOverlay;
/*     */ import net.eq2online.macros.interfaces.ISettingsProvider;
/*     */ import net.eq2online.macros.interfaces.annotations.DropdownStyle;
/*     */ import net.eq2online.macros.permissions.MacroModPermissions;
/*     */ import net.eq2online.macros.scripting.api.IMessageFilter;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpamFilter
/*     */ {
/*     */   private static final String SPAM_NOLIMIT_PERMISSION = "spam.nolimit";
/*     */   private static final int MAX_PACKETS_LIMITED = 20;
/*     */   private static final int MAX_PACKETS_NORMAL = 250;
/*     */   
/*     */   public enum FilterStyle
/*     */   {
/*  42 */     None,
/*     */ 
/*     */ 
/*     */     
/*  46 */     Queue,
/*     */ 
/*     */     
/*  49 */     Discard,
/*     */     
/*  51 */     Log;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   protected static int spamMessageLimit = 180;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   protected static int spamMessageTicks = 20;
/*     */   
/*  65 */   protected static int maxPacketsPerSecond = 250;
/*     */   
/*  67 */   protected long packetCapTime = 0L;
/*     */   
/*  69 */   protected int packetCapLevel = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   protected int spamLevel = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   protected LinkedList<String> queuedMessages = new LinkedList<String>();
/*     */ 
/*     */   
/*     */   private volatile int sending;
/*     */ 
/*     */   
/*     */   private ab forgeClientCommandHandler;
/*     */ 
/*     */ 
/*     */   
/*     */   public SpamFilter() {
/*  90 */     MacroModPermissions.registerPermission("spam.nolimit");
/*     */ 
/*     */     
/*     */     try {
/*  94 */       Class<?> clientCommandHandler = Class.forName("net.minecraftforge.client.ClientCommandHandler");
/*  95 */       if (clientCommandHandler != null)
/*     */       {
/*  97 */         Field fInstance = clientCommandHandler.getDeclaredField("instance");
/*  98 */         this.forgeClientCommandHandler = (ab)fInstance.get(null);
/*  99 */         Log.info("Forge ClientCommandHandler was found, outbound commands will be processed by Forge");
/*     */       }
/*     */     
/* 102 */     } catch (Throwable th) {
/*     */       
/* 104 */       Log.info("Forge ClientCommandHandler was not found, outbound commands will not be processed by Forge");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTick() {
/* 114 */     if (this.spamLevel > 0) this.spamLevel--;
/*     */     
/* 116 */     long systemTime = bsu.I();
/* 117 */     if (systemTime - this.packetCapTime > 1000L) {
/*     */       
/* 119 */       this.packetCapTime = systemTime;
/* 120 */       this.packetCapLevel = 0;
/*     */     } 
/*     */     
/* 123 */     while (this.spamLevel < spamMessageLimit - spamMessageTicks) {
/*     */       
/* 125 */       if (!processQueue()) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean processQueue() {
/* 136 */     if (this.queuedMessages.size() > 0 && MacroModSettings.spamFilterStyle != FilterStyle.Queue) {
/*     */       
/* 138 */       this.queuedMessages.clear();
/* 139 */       return false;
/*     */     } 
/*     */     
/* 142 */     String chatMessage = this.queuedMessages.poll();
/*     */     
/* 144 */     if (chatMessage != null) {
/*     */       
/* 146 */       dispatchMessage(chatMessage);
/* 147 */       return true;
/*     */     } 
/*     */     
/* 150 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearQueue() {
/* 158 */     this.queuedMessages.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendChatMessage(String chatMessage, ScriptContext context) {
/* 169 */     if (chatMessage == null || chatMessage.length() < 1)
/*     */       return; 
/* 171 */     IMessageFilter messageFilter = context.getMessageFilter();
/* 172 */     if (messageFilter != null)
/*     */     {
/* 174 */       if (!messageFilter.enqueueMessage(chatMessage))
/*     */         return; 
/*     */     }
/* 177 */     if (this.forgeClientCommandHandler != null)
/*     */     {
/* 179 */       if (this.forgeClientCommandHandler.a((ae)(bsu.z()).h, chatMessage) == 1) {
/*     */         return;
/*     */       }
/*     */     }
/* 183 */     if (this.spamLevel < spamMessageLimit - spamMessageTicks || !MacroModSettings.spamFilterEnabled || MacroModSettings.spamFilterStyle == FilterStyle.None) {
/*     */       
/* 185 */       dispatchMessage(chatMessage);
/*     */     }
/* 187 */     else if (MacroModSettings.spamFilterStyle == FilterStyle.Queue && this.queuedMessages.size() < MacroModSettings.spamFilterQueueSize) {
/*     */       
/* 189 */       if (MacroModSettings.spamFilterIgnoreCommands && chatMessage.startsWith("/")) {
/*     */         
/* 191 */         dispatchMessage(chatMessage);
/*     */         
/*     */         return;
/*     */       } 
/* 195 */       this.queuedMessages.offer(chatMessage);
/*     */     }
/* 197 */     else if (MacroModSettings.spamFilterStyle == FilterStyle.Log) {
/*     */       
/* 199 */       AbstractionLayer.addChatMessage("§c[Flood protection] " + vb.a(chatMessage));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dispatchMessage(String chatMessage) {
/* 210 */     cio thePlayer = AbstractionLayer.getPlayer();
/* 211 */     if (thePlayer == null)
/*     */       return; 
/* 213 */     this.spamLevel += spamMessageTicks;
/*     */     
/* 215 */     maxPacketsPerSecond = MacroModPermissions.hasPermission("spam.nolimit") ? 250 : 20;
/* 216 */     if (this.packetCapLevel < maxPacketsPerSecond) {
/*     */       
/* 218 */       this.packetCapLevel++;
/* 219 */       sendChatPacket(chatMessage);
/*     */     }
/*     */     else {
/*     */       
/* 223 */       AbstractionLayer.addChatMessage("§4[Flood protection] [Discarded] §c" + vb.a(chatMessage));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendChatPacket(String message) {
/* 232 */     cio thePlayer = AbstractionLayer.getPlayer();
/* 233 */     if (thePlayer == null) {
/*     */       return;
/*     */     }
/*     */     try {
/* 237 */       bsu mc = bsu.z();
/*     */       
/* 239 */       if (MacroModSettings.chatHistory)
/*     */       {
/*     */ 
/*     */         
/* 243 */         mc.q.d().a(message);
/* 244 */         sendChatpacket(message, mc);
/*     */       
/*     */       }
/*     */       else
/*     */       {
/* 249 */         sendChatpacket(message, mc);
/*     */       }
/*     */     
/* 252 */     } catch (NullPointerException nullPointerException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sendChatpacket(String message, bsu mc) {
/* 261 */     this.sending++;
/* 262 */     lu packet = new lu(message);
/* 263 */     this.sending--;
/*     */     
/* 265 */     mc.h.a.a((id)packet);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSending() {
/* 270 */     return (this.sending > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFilteringEnabled() {
/* 280 */     return (MacroModSettings.spamFilterEnabled && MacroModSettings.spamFilterStyle != FilterStyle.None);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSpamLimit() {
/* 290 */     return spamMessageLimit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSpamMessageTicks() {
/* 300 */     return spamMessageTicks;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSpamLevel() {
/* 305 */     return this.spamLevel + this.queuedMessages.size() * spamMessageTicks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getHasQueue() {
/* 315 */     return (this.queuedMessages.size() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getQueueStatusText() {
/* 325 */     return String.format("§aFlood protection: §f[§4%s§f]§a messages in queue", new Object[] { Integer.valueOf(this.queuedMessages.size()) });
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
/*     */ 
/*     */   
/*     */   public int drawQueueStatus(bty fontRenderer, int left, int top, int width, int height, int mouseX, int mouseY, boolean drawStopButtons) {
/* 341 */     if (getHasQueue())
/*     */     {
/* 343 */       top = GuiMacroModOverlay.drawStatusLine(fontRenderer, getQueueStatusText(), left, top, mouseX, mouseY, drawStopButtons);
/*     */     }
/*     */     
/* 346 */     return top;
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
/*     */   public boolean mousePressed(int left, int top, int mouseX, int mouseY) {
/* 360 */     if (mouseX > 2 && mouseX < 18 && mouseY > top - 2 && mouseY < top + 10) {
/*     */       
/* 362 */       clearQueue();
/* 363 */       return true;
/*     */     } 
/*     */     
/* 366 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void notifyClearSettings() {
/* 374 */     spamMessageLimit = 180;
/* 375 */     spamMessageTicks = 20;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void notifySettingsLoaded(ISettingsProvider settings) {
/* 385 */     spamMessageLimit = settings.getSetting("floodprotection.behaviour.tickslimit", 180);
/* 386 */     spamMessageTicks = settings.getSetting("floodprotection.behaviour.ticksmessage", 20);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void saveSettings(ISettingsProvider settings) {
/* 396 */     settings.setSetting("floodprotection.behaviour.tickslimit", spamMessageLimit);
/* 397 */     settings.setSetting("floodprotection.behaviour.ticksmessage", spamMessageTicks);
/*     */     
/* 399 */     settings.setSettingComment("floodprotection.behaviour.tickslimit", "Flood protection settings");
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\SpamFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */