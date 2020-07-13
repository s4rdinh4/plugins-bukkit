/*    */ package net.eq2online.macros.event;
/*    */ 
/*    */ public enum BuiltinEvents
/*    */ {
/*  5 */   onJoinGame("onJoinGame"),
/*  6 */   onChat("onChat"),
/*  7 */   onHealthChange("onHealthChange"),
/*  8 */   onFoodChange("onFoodChange"),
/*  9 */   onArmourChange("onArmourChange"),
/* 10 */   onWorldChange("onWorldChange"),
/* 11 */   onModeChange("onModeChange"),
/* 12 */   onInventorySlotChange("onInventorySlotChange"),
/* 13 */   onOxygenChange("onOxygenChange"),
/* 14 */   onXPChange("onXPChange"),
/* 15 */   onLevelChange("onLevelChange"),
/* 16 */   onItemDurabilityChange("onItemDurabilityChange"),
/* 17 */   onWeatherChange("onWeatherChange"),
/* 18 */   onPickupItem("onPickupItem"),
/* 19 */   onPlayerJoined("onPlayerJoined"),
/* 20 */   onShowGui("onShowGui"),
/* 21 */   onArmourDurabilityChange("onArmourDurabilityChange"),
/* 22 */   onAutoCraftingComplete("onAutoCraftingComplete"),
/* 23 */   onConfigChange("onConfigChange"),
/* 24 */   onFilterableChat("onFilterableChat"),
/* 25 */   onSendChatMessage("onSendChatMessage");
/*    */   
/*    */   private String name;
/*    */ 
/*    */   
/*    */   BuiltinEvents(String name) {
/* 31 */     this.name = name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 36 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\event\BuiltinEvents.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */