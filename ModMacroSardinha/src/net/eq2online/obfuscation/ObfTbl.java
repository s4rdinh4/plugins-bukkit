/*    */ package net.eq2online.obfuscation;
/*    */ 
/*    */ import com.mumfrey.liteloader.core.runtime.Obf;
/*    */ import net.minecraft.launchwrapper.Launch;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ObfTbl
/*    */   extends Obf
/*    */ {
/* 17 */   public static final ObfTbl shapedRecipeWidth = new ObfTbl("field_77576_b", "a", "recipeWidth");
/* 18 */   public static final ObfTbl shapedRecipeHeight = new ObfTbl("field_77577_c", "b", "recipeHeight");
/* 19 */   public static final ObfTbl shapedRecipeItems = new ObfTbl("field_77574_d", "c", "recipeItems");
/* 20 */   public static final ObfTbl shapelessRecipeItems = new ObfTbl("field_77579_b", "b", "recipeItems");
/* 21 */   public static final ObfTbl editingSign = new ObfTbl("field_146848_f", "a", "tileSign");
/* 22 */   public static final ObfTbl coolDownTimer = new ObfTbl("field_146347_a", "a", "enableButtonsTimer");
/* 23 */   public static final ObfTbl creativeBinSlot = new ObfTbl("field_147064_C", "C");
/* 24 */   public static final ObfTbl creativeGuiScroll = new ObfTbl("field_147067_x", "x", "currentScroll");
/* 25 */   public static final ObfTbl serverEntityTracker = new ObfTbl("field_73062_L", "J", "theEntityTracker");
/* 26 */   public static final ObfTbl damagedBlocks = new ObfTbl("field_72738_E", "w", "damagedBlocks");
/* 27 */   public static final ObfTbl currentLocale = new ObfTbl("field_135054_a", "a", "i18nLocale");
/* 28 */   public static final ObfTbl translateTable = new ObfTbl("field_135032_a", "a", "properties");
/* 29 */   public static final ObfTbl downloadedImage = new ObfTbl("field_110560_d", "l", "bufferedImage");
/* 30 */   public static final ObfTbl creativeTempInventory = new ObfTbl("field_147060_v", "v");
/* 31 */   public static final ObfTbl creativeSlotInnerSlot = new ObfTbl("field_148332_b", "b", "slot");
/* 32 */   public static final ObfTbl itemsList = new ObfTbl("field_148330_a", "a", "itemList");
/* 33 */   public static final ObfTbl keyBindHash = new ObfTbl("field_74514_b", "b", "hash");
/* 34 */   public static final ObfTbl keyBindPresses = new ObfTbl("field_151474_i", "i", "pressTime");
/* 35 */   public static final ObfTbl keyBindPressed = new ObfTbl("field_74513_e", "h", "pressed");
/* 36 */   public static final ObfTbl keyBindingList = new ObfTbl("field_146494_r", "s", "keyBindingList");
/* 37 */   public static final ObfTbl keyBindingEntries = new ObfTbl("field_148190_m", "w", "listEntries");
/* 38 */   public static final ObfTbl keyEntryBinding = new ObfTbl("field_148282_b", "b", "keybinding");
/* 39 */   public static final ObfTbl serverChatComponent = new ObfTbl("field_148919_a", "a", "chatComponent");
/* 40 */   public static final ObfTbl shaders = new ObfTbl("field_147712_ad", "ab", "shaderResourceLocations");
/* 41 */   public static final ObfTbl chatTextField = new ObfTbl("field_146415_a", "a", "inputField");
/*    */   
/* 43 */   public static final ObfTbl getSlotAtPosition = new ObfTbl("func_146975_c", "c", "getSlotAtPosition");
/* 44 */   public static final ObfTbl handleMouseClick = new ObfTbl("func_146984_a", "a", "handleMouseClick");
/* 45 */   public static final ObfTbl setCurrentCreativeTab = new ObfTbl("func_147050_b", "b", "setCurrentCreativeTab");
/* 46 */   public static final ObfTbl scrollTo = new ObfTbl("func_148329_a", "a", "scrollTo");
/*    */   
/* 48 */   public static final ObfTbl SlotCreativeInventory = new ObfTbl("net.minecraft.client.gui.inventory.GuiContainerCreative$CreativeSlot", "bzb");
/* 49 */   public static final ObfTbl ContainerCreative = new ObfTbl("net.minecraft.client.gui.inventory.GuiContainerCreative$ContainerCreative", "bza");
/* 50 */   public static final ObfTbl GuiContainer = new ObfTbl("net.minecraft.client.gui.inventory.GuiContainer", "byl");
/* 51 */   public static final ObfTbl GuiContainerCreative = new ObfTbl("net.minecraft.client.gui.inventory.GuiContainerCreative", "byz");
/*    */ 
/*    */   
/*    */   static {
/* 55 */     String ect = "ector";
/* 56 */     String dot = ".";
/* 57 */     String nj = "ndlerInj";
/* 58 */     String mac = "macros";
/* 59 */     String inp = "InputHa";
/* 60 */     Launch.classLoader.registerTransformer("net" + dot + "eq2online" + dot + mac + dot + "input" + dot + inp + nj + ect);
/*    */   }
/*    */ 
/*    */   
/*    */   private ObfTbl(String seargeName, String obfName, String mcpName) {
/* 65 */     super(seargeName, obfName, mcpName);
/*    */   }
/*    */ 
/*    */   
/*    */   private ObfTbl(String seargeName, String obfName) {
/* 70 */     super(seargeName, obfName);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\obfuscation\ObfTbl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */