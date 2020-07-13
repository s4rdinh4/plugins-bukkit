/*     */ package net.eq2online.macros;
/*     */ 
/*     */ import adw;
/*     */ import ahd;
/*     */ import bsu;
/*     */ import buf;
/*     */ import bxf;
/*     */ import cew;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.realmsclient.dto.RealmsServer;
/*     */ import com.mumfrey.liteloader.ChatFilter;
/*     */ import com.mumfrey.liteloader.ChatListener;
/*     */ import com.mumfrey.liteloader.Configurable;
/*     */ import com.mumfrey.liteloader.GameLoopListener;
/*     */ import com.mumfrey.liteloader.InitCompleteListener;
/*     */ import com.mumfrey.liteloader.JoinGameListener;
/*     */ import com.mumfrey.liteloader.OutboundChatFilter;
/*     */ import com.mumfrey.liteloader.PacketHandler;
/*     */ import com.mumfrey.liteloader.Permissible;
/*     */ import com.mumfrey.liteloader.RenderListener;
/*     */ import com.mumfrey.liteloader.ViewportListener;
/*     */ import com.mumfrey.liteloader.api.Listener;
/*     */ import com.mumfrey.liteloader.core.LiteLoader;
/*     */ import com.mumfrey.liteloader.modconfig.ConfigPanel;
/*     */ import com.mumfrey.liteloader.permissions.PermissionsManager;
/*     */ import com.mumfrey.liteloader.permissions.PermissionsManagerClient;
/*     */ import hg;
/*     */ import ho;
/*     */ import id;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import jw;
/*     */ import ln;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.gui.screens.GuiMacroConfigPanel;
/*     */ import net.eq2online.macros.permissions.MacroModPermissions;
/*     */ import wv;
/*     */ import xm;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LiteModMacros
/*     */   implements InitCompleteListener, ChatListener, RenderListener, GameLoopListener, JoinGameListener, Permissible, OutboundChatFilter, Configurable, ViewportListener, PacketHandler
/*     */ {
/*     */   private MacroModCore core;
/*     */   private static ChatFilter pendingChatFilter;
/*     */   
/*     */   public Class<? extends ConfigPanel> getConfigPanelClass() {
/*  49 */     return (Class)GuiMacroConfigPanel.class;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(File configPath) {
/*  55 */     this.core = MacroModCore.getInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onInitCompleted(bsu minecraft, LiteLoader loader) {
/*  64 */     this.core.init(minecraft);
/*     */     
/*  66 */     if (pendingChatFilter != null) {
/*     */       
/*  68 */       LiteLoader.getInterfaceManager().registerListener((Listener)pendingChatFilter);
/*  69 */       pendingChatFilter = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Class<? extends id>> getHandledPackets() {
/*  76 */     return (List<Class<? extends id>>)ImmutableList.of(ln.class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handlePacket(hg netHandler, id packet) {
/*  82 */     onPickupItem(netHandler, (ln)packet);
/*  83 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void upgradeSettings(String version, File configPath, File oldConfigPath) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onViewportResized(buf resolution, int displayWidth, int displayHeight) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFullScreenToggled(boolean fullScreen) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRender() {
/* 104 */     this.core.onRender();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRenderWorld() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRenderGui(bxf currentScreen) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTick(bsu minecraft, float partialTicks, boolean inGame, boolean clock) {
/* 120 */     if (inGame)
/*     */     {
/* 122 */       this.core.onTickInGame(partialTicks, minecraft, clock);
/*     */     }
/*     */     
/* 125 */     if (minecraft.m != null)
/*     */     {
/* 127 */       this.core.onTickInGUI(partialTicks, minecraft, clock, minecraft.m);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRunGameLoop(bsu minecraft) {
/* 134 */     this.core.onTimerUpdate(minecraft);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 140 */     return "Macro / Keybind Mod";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 146 */     return MacroModCore.version();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onChat(ho chat, String message) {
/* 152 */     this.core.onChat(message);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onSendChatMessage(String message) {
/* 158 */     return this.core.onSendChatMessage(message);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onJoinGame(hg netHandler, jw joinGamePacket, cew serverData, RealmsServer realmsServer) {
/* 164 */     this.core.onServerConnect(netHandler);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerChatFilter(ChatFilter newFilter) {
/* 169 */     pendingChatFilter = newFilter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSetupCameraTransform() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerPermissions(PermissionsManagerClient permissionsManager) {
/* 180 */     MacroModPermissions.init(this, permissionsManager);
/* 181 */     this.core.onPermissionsChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPermissionsCleared(PermissionsManager manager) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPermissionsChanged(PermissionsManager manager) {
/* 192 */     this.core.onPermissionsChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPermissibleModName() {
/* 198 */     return "macros";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPermissibleModVersion() {
/* 204 */     return Float.valueOf(MacroModCore.VERSION / 1000.0F).floatValue();
/*     */   }
/*     */ 
/*     */   
/*     */   private void onPickupItem(hg netHandler, ln packet) {
/* 209 */     bsu mc = bsu.z();
/*     */     
/* 211 */     int collectedEntityId = packet.a();
/* 212 */     int collectorEntityid = packet.b();
/*     */ 
/*     */ 
/*     */     
/* 216 */     wv entity = getEntityByID(mc, collectedEntityId);
/* 217 */     xm collector = (xm)getEntityByID(mc, collectorEntityid);
/*     */     
/* 219 */     if (collector == mc.h)
/*     */     {
/* 221 */       if (entity instanceof adw) {
/*     */         
/* 223 */         adw e = (adw)entity;
/* 224 */         MacroModCore.getInstance().onItemPickup((ahd)mc.h, e.l());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static wv getEntityByID(bsu mc, int entityId) {
/* 231 */     return (entityId != mc.h.F()) ? ((mc.f != null) ? mc.f.a(entityId) : null) : (wv)mc.h;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\LiteModMacros.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */