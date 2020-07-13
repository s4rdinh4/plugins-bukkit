/*    */ package net.eq2online.macros.event;
/*    */ 
/*    */ import bsu;
/*    */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*    */ import net.eq2online.macros.core.MacroModSettings;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.scripting.api.IMacroEvent;
/*    */ import net.eq2online.macros.scripting.api.IMacroEventDispatcher;
/*    */ import net.eq2online.macros.scripting.api.IMacroEventManager;
/*    */ import net.eq2online.macros.scripting.api.IMacroEventProvider;
/*    */ 
/*    */ public class MacroEventProviderBuiltin
/*    */   implements IMacroEventProvider
/*    */ {
/*    */   private MacroEventDispatcherBuiltin dispatcher;
/*    */   
/*    */   public MacroEventProviderBuiltin(Macros macros, bsu minecraft) {
/* 18 */     this.dispatcher = new MacroEventDispatcherBuiltin(macros, minecraft);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IMacroEventDispatcher getDispatcher() {
/* 24 */     return this.dispatcher;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void registerEvents(IMacroEventManager manager) {
/* 30 */     manager.registerEvent(this, BuiltinEvents.onJoinGame.getName());
/* 31 */     manager.registerEvent(this, BuiltinEvents.onChat.getName());
/* 32 */     manager.registerEvent(this, BuiltinEvents.onHealthChange.getName(), "player");
/* 33 */     manager.registerEvent(this, BuiltinEvents.onFoodChange.getName(), "player");
/* 34 */     manager.registerEvent(this, BuiltinEvents.onArmourChange.getName(), "player");
/* 35 */     manager.registerEvent(this, BuiltinEvents.onWorldChange.getName(), "world");
/* 36 */     manager.registerEvent(this, BuiltinEvents.onModeChange.getName(), "player");
/* 37 */     manager.registerEvent(this, BuiltinEvents.onInventorySlotChange.getName(), "local");
/* 38 */     manager.registerEvent(this, BuiltinEvents.onOxygenChange.getName(), "player");
/* 39 */     manager.registerEvent(this, BuiltinEvents.onXPChange.getName(), "stats");
/* 40 */     manager.registerEvent(this, BuiltinEvents.onLevelChange.getName(), "stats");
/* 41 */     manager.registerEvent(this, BuiltinEvents.onItemDurabilityChange.getName(), "player");
/* 42 */     manager.registerEvent(this, BuiltinEvents.onWeatherChange.getName(), "world");
/* 43 */     manager.registerEvent(this, BuiltinEvents.onPickupItem.getName(), "player");
/* 44 */     manager.registerEvent(this, BuiltinEvents.onPlayerJoined.getName(), "world");
/* 45 */     manager.registerEvent(this, BuiltinEvents.onShowGui.getName(), "local");
/* 46 */     manager.registerEvent(this, BuiltinEvents.onArmourDurabilityChange.getName(), "player");
/* 47 */     manager.registerEvent(this, BuiltinEvents.onAutoCraftingComplete.getName(), "local");
/* 48 */     manager.registerEvent(this, BuiltinEvents.onConfigChange.getName(), "local");
/*    */     
/* 50 */     if (MacroModSettings.showFilterableChat)
/*    */     {
/* 52 */       manager.registerEvent(this, BuiltinEvents.onFilterableChat.getName());
/*    */     }
/*    */     
/* 55 */     manager.registerEvent(this, BuiltinEvents.onSendChatMessage.getName());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onInit() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getHelp(IMacroEvent macroEvent, int eventId, int line) {
/* 68 */     String key = (eventId < 1019) ? String.valueOf(eventId) : macroEvent.getName().toLowerCase();
/* 69 */     return LocalisationProvider.getLocalisedString("macro.help.event." + key + ".line" + (line + 6));
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\event\MacroEventProviderBuiltin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */