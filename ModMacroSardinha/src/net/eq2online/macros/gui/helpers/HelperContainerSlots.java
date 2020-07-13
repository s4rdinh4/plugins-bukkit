/*     */ package net.eq2online.macros.gui.helpers;
/*     */ 
/*     */ import aib;
/*     */ import ajk;
/*     */ import akf;
/*     */ import amj;
/*     */ import bsu;
/*     */ import byl;
/*     */ import byz;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.PrivateClasses;
/*     */ import net.eq2online.macros.compatibility.PrivateFields;
/*     */ import net.eq2online.macros.core.overlays.IContainerCreative;
/*     */ import net.eq2online.macros.core.overlays.IGuiContainer;
/*     */ import net.eq2online.macros.core.overlays.IGuiContainerCreative;
/*     */ import net.eq2online.macros.scripting.variable.ItemID;
/*     */ import wa;
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
/*     */ public class HelperContainerSlots
/*     */ {
/*     */   public static boolean currentScreenIsContainer(bsu minecraft) {
/*  39 */     return (minecraft.m != null && (minecraft.m instanceof byl || minecraft.m.getClass().getSimpleName().equals("GuiInventoryEx")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean currentScreenIsInventory(bsu minecraft) {
/*  47 */     return (minecraft.m != null && (minecraft.m instanceof bzj || minecraft.m.getClass().getSimpleName().equals("GuiInventoryEx")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean noScreenInGame(bsu minecraft) {
/*  56 */     return (minecraft.m == null && minecraft.h != null && minecraft.h.bg != null && minecraft.h.bg.a != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byl getGuiContainer(bsu minecraft) {
/*  65 */     if (minecraft.m instanceof byl)
/*     */     {
/*  67 */       return (byl)minecraft.m;
/*     */     }
/*  69 */     if (minecraft.m.getClass().getSimpleName().equals("GuiInventoryEx")) {
/*     */       
/*     */       try {
/*     */         
/*  73 */         Field fProxy = minecraft.m.getClass().getDeclaredField("proxy");
/*  74 */         fProxy.setAccessible(true);
/*  75 */         byl proxy = (byl)fProxy.get(minecraft.m);
/*  76 */         return proxy;
/*     */       }
/*  78 */       catch (Exception exception) {}
/*     */     }
/*     */     
/*  81 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getContainerSize() {
/*  86 */     bsu minecraft = bsu.z();
/*     */     
/*  88 */     if (currentScreenIsContainer(minecraft)) {
/*     */       
/*  90 */       byl containerGui = getGuiContainer(minecraft);
/*     */       
/*  92 */       if (containerGui instanceof byz)
/*     */       {
/*  94 */         return 600;
/*     */       }
/*     */       
/*  97 */       return containerGui.h.c.size();
/*     */     } 
/*     */     
/* 100 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class SlotClick
/*     */   {
/*     */     public byl craftingGui;
/*     */     public ajk slot;
/*     */     public int slotNumber;
/*     */     public int button;
/*     */     public boolean shift;
/*     */     
/*     */     public SlotClick(byl craftingGui, ajk slot, int slotNumber, int button, boolean shift) {
/* 113 */       this.craftingGui = craftingGui;
/* 114 */       this.slot = slot;
/* 115 */       this.slotNumber = slotNumber;
/* 116 */       this.button = button;
/* 117 */       this.shift = shift;
/*     */     }
/*     */ 
/*     */     
/*     */     public void execute() {
/* 122 */       HelperContainerSlots.survivalInventorySlotClick(this);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 128 */       return String.format("SlotClick[%s,%s,%s,SHIFT=%s]", new Object[] { (this.slot != null) ? Integer.valueOf(this.slot.e) : "-", Integer.valueOf(this.slotNumber), Integer.valueOf(this.button), Boolean.valueOf(this.shift) });
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
/*     */   public static void survivalInventorySlotClick(SlotClick click) {
/* 141 */     survivalInventorySlotClick(click.craftingGui, click.slot, click.slotNumber, click.button, click.shift);
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
/*     */   public static void survivalInventorySlotClick(byl craftingGui, ajk slot, int slotNumber, int button, boolean shift) {
/*     */     try {
/* 155 */       if (craftingGui == null || craftingGui instanceof byz)
/* 156 */         return;  ((IGuiContainer)craftingGui).mouseClick(slot, slotNumber, button, shift ? 1 : 0);
/*     */     }
/* 158 */     catch (Exception ex) {
/*     */       
/* 160 */       Log.printStackTrace(ex);
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
/*     */   public static void containerSlotClick(int slotNumber, int button, boolean shift) {
/*     */     try {
/* 173 */       bsu minecraft = bsu.z();
/*     */       
/* 175 */       if (currentScreenIsContainer(minecraft)) {
/*     */         
/* 177 */         byl containerGui = getGuiContainer(minecraft);
/* 178 */         aib slots = containerGui.h;
/*     */         
/* 180 */         if (containerGui instanceof byz)
/*     */         {
/* 182 */           if (slotNumber < 45) {
/*     */             
/* 184 */             ((IGuiContainerCreative)containerGui).setCreativeTab(akf.m);
/* 185 */             ajk slot = (slotNumber < 5) ? (ajk)PrivateFields.creativeBinSlot.get(containerGui) : slots.a(slotNumber);
/* 186 */             ((IGuiContainerCreative)containerGui).mouseClick(slot, slotNumber, button, shift ? 1 : 0);
/*     */           }
/* 188 */           else if (slotNumber < 54) {
/*     */             
/* 190 */             ajk slot = slots.a(slotNumber);
/* 191 */             ((IGuiContainerCreative)containerGui).mouseClick(slot, slotNumber, button, shift ? 1 : 0);
/*     */           } else {
/* 193 */             if (slotNumber > 599 && slotNumber < 700) {
/*     */               return;
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 200 */             int pageNumber = slotNumber / 100 - 1;
/* 201 */             ((IGuiContainerCreative)containerGui).setCreativeTab(akf.a[pageNumber]);
/* 202 */             slotNumber -= (pageNumber + 1) * 100;
/*     */             
/* 204 */             if (scrollContainerTo((byz)containerGui, slotNumber))
/*     */             {
/* 206 */               slotNumber -= getCreativeInventoryScroll((byz)containerGui);
/* 207 */               ajk slot = slots.a(slotNumber);
/* 208 */               ((IGuiContainerCreative)containerGui).mouseClick(slot, slotNumber, button, shift ? 1 : 0);
/*     */             }
/*     */           
/*     */           }
/*     */         
/*     */         }
/* 214 */         else if ((slotNumber >= 0 && slotNumber < slots.c.size()) || slotNumber == -999)
/*     */         {
/* 216 */           ajk slot = (slotNumber == -999) ? null : slots.a(slotNumber);
/* 217 */           ((IGuiContainer)containerGui).mouseClick(slot, slotNumber, button, shift ? 1 : 0);
/*     */         }
/*     */       
/*     */       }
/* 221 */       else if (noScreenInGame(minecraft) && slotNumber >= 1 && slotNumber <= 9) {
/*     */         
/* 223 */         minecraft.h.bg.c = slotNumber - 1;
/*     */       } 
/*     */     } catch (Exception ex) {
/* 226 */       ex.printStackTrace();
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
/*     */   private static boolean scrollContainerTo(byz containerGui, int slotNumber) throws IllegalArgumentException, SecurityException {
/* 239 */     float currentScroll = 0.0F;
/* 240 */     int lastInventoryScroll = setScrollPosition(containerGui, currentScroll);
/*     */     
/* 242 */     List<amj> itemsList = ((IContainerCreative)containerGui.h).getItemsList();
/* 243 */     float scrollIncrement = (float)(1.0D / (itemsList.size() / 9 - 5 + 1));
/*     */     
/* 245 */     while (!isInRange(slotNumber, getCreativeInventoryScroll(containerGui), 45)) {
/*     */       
/* 247 */       currentScroll += scrollIncrement;
/*     */       
/* 249 */       int inventoryScroll = setScrollPosition(containerGui, currentScroll);
/* 250 */       if (inventoryScroll == lastInventoryScroll)
/*     */       {
/* 252 */         return isInRange(slotNumber, inventoryScroll, 45);
/*     */       }
/*     */     } 
/*     */     
/* 256 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isInRange(int value, int start, int rangeLength) {
/* 267 */     return (value >= start && value < start + rangeLength);
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
/*     */   protected static int setScrollPosition(byz containerGui, float scrollPosition) {
/*     */     try {
/* 281 */       PrivateFields.creativeGuiScroll.set(containerGui, Float.valueOf(scrollPosition));
/* 282 */       ((IContainerCreative)containerGui.h).scrollToPosition(scrollPosition);
/* 283 */       return getCreativeInventoryScroll(containerGui);
/*     */     } catch (Exception ex) {
/* 285 */       ex.printStackTrace();
/*     */       
/* 287 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getSlotContaining(ItemID itemId, int startSlot) {
/*     */     try {
/* 298 */       bsu minecraft = bsu.z();
/*     */       
/* 300 */       if (currentScreenIsContainer(minecraft)) {
/*     */         
/* 302 */         byl containerGui = getGuiContainer(minecraft);
/*     */         
/* 304 */         if (containerGui instanceof byz) {
/*     */           
/* 306 */           int inventoryIndex = searchInventoryFor(itemId, startSlot, (AbstractionLayer.getPlayer()).bh);
/*     */           
/* 308 */           if (inventoryIndex < 0)
/*     */           {
/* 310 */             return searchCreativeTabsFor(itemId, startSlot);
/*     */           }
/*     */           
/* 313 */           return inventoryIndex;
/*     */         } 
/*     */         
/* 316 */         return searchInventoryFor(itemId, startSlot, containerGui.h);
/*     */       } 
/* 318 */       if (noScreenInGame(minecraft))
/*     */       {
/* 320 */         for (int slot = 0; slot < 9; slot++) {
/*     */           
/* 322 */           if (stackMatchesID(itemId, minecraft.h.bg.a[slot])) {
/* 323 */             return slot + 1;
/*     */           }
/*     */         } 
/*     */       }
/* 327 */     } catch (Exception ex) {
/*     */       
/* 329 */       ex.printStackTrace();
/*     */     } 
/*     */     
/* 332 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static int searchInventoryFor(ItemID itemId, int startSlot, aib inventorySlots) {
/* 342 */     ArrayList<ajk> itemStacks = (ArrayList<ajk>)inventorySlots.c;
/*     */     
/* 344 */     for (int slotContaining = startSlot; slotContaining < itemStacks.size(); slotContaining++) {
/*     */       
/* 346 */       amj slotStack = ((ajk)itemStacks.get(slotContaining)).d();
/*     */       
/* 348 */       if (stackMatchesID(itemId, slotStack))
/*     */       {
/* 350 */         return slotContaining;
/*     */       }
/*     */     } 
/*     */     
/* 354 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static int searchCreativeTabsFor(ItemID itemId, int startSlot) {
/* 363 */     int pageNumber = 100;
/*     */     
/* 365 */     for (akf creativeTab : akf.a) {
/*     */       
/* 367 */       if (creativeTab != akf.m && creativeTab != akf.g) {
/*     */         
/* 369 */         List<amj> itemStacks = new ArrayList<amj>();
/* 370 */         creativeTab.a(itemStacks);
/*     */         
/* 372 */         for (int stackIndex = 0; stackIndex < itemStacks.size(); stackIndex++) {
/*     */           
/* 374 */           if (stackMatchesID(itemId, itemStacks.get(stackIndex)) && pageNumber + stackIndex >= startSlot)
/*     */           {
/* 376 */             return pageNumber + stackIndex;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 381 */       pageNumber += 100;
/*     */     } 
/*     */     
/* 384 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean stackMatchesID(ItemID itemId, amj slotStack) {
/* 394 */     return ((slotStack == null && itemId.item == null) || (slotStack != null && slotStack.b() == itemId.item && (itemId.damage == -1 || itemId.damage == slotStack.i())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static amj getSlotStack(int slotId) {
/*     */     try {
/* 405 */       bsu minecraft = bsu.z();
/*     */       
/* 407 */       if (currentScreenIsContainer(minecraft)) {
/*     */         
/* 409 */         byl containerGui = getGuiContainer(minecraft);
/*     */         
/* 411 */         if (containerGui instanceof byz && slotId >= 100)
/*     */         {
/* 413 */           return getStackFromCreativeTabs(slotId);
/*     */         }
/*     */         
/* 416 */         return getStackFromSurvivalInventory(slotId, containerGui.h);
/*     */       } 
/* 418 */       if (noScreenInGame(minecraft) && slotId >= 1 && slotId <= 9)
/*     */       {
/* 420 */         return minecraft.h.bg.a[slotId - 1];
/*     */       }
/*     */     }
/* 423 */     catch (Exception ex) {
/*     */       
/* 425 */       ex.printStackTrace();
/*     */     } 
/*     */     
/* 428 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static amj getStackFromCreativeTabs(int slotId) {
/* 437 */     int pageNumber = 100;
/*     */     
/* 439 */     for (akf creativeTab : akf.a) {
/*     */       
/* 441 */       if (creativeTab != akf.m && creativeTab != akf.g) {
/*     */         
/* 443 */         List<amj> itemStacks = new ArrayList<amj>();
/* 444 */         creativeTab.a(itemStacks);
/*     */         
/* 446 */         for (int stackIndex = 0; stackIndex < itemStacks.size(); stackIndex++) {
/*     */           
/* 448 */           int virtualSlotId = pageNumber + stackIndex;
/*     */           
/* 450 */           if (virtualSlotId == slotId)
/*     */           {
/* 452 */             return itemStacks.get(stackIndex);
/*     */           }
/*     */           
/* 455 */           if (virtualSlotId > slotId)
/*     */           {
/* 457 */             return null;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 462 */       pageNumber += 100;
/*     */     } 
/*     */     
/* 465 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static amj getStackFromSurvivalInventory(int slotId, aib survivalInventory) {
/* 475 */     ArrayList<ajk> itemStacks = (ArrayList<ajk>)survivalInventory.c;
/*     */     
/* 477 */     if (slotId >= 0 && slotId < itemStacks.size()) {
/*     */       
/* 479 */       amj slotStack = ((ajk)itemStacks.get(slotId)).d();
/*     */       
/* 481 */       if (slotStack != null)
/*     */       {
/* 483 */         return slotStack;
/*     */       }
/*     */     } 
/*     */     
/* 487 */     return null;
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
/*     */   public static ajk getMouseOverSlot(byl guiContainer, int mouseX, int mouseY) {
/*     */     try {
/* 504 */       ajk slot = ((IGuiContainer)guiContainer).getSlot(mouseX, mouseY);
/*     */       
/* 506 */       if (guiContainer instanceof byz) {
/*     */         
/* 508 */         byz creativeContainerGui = (byz)guiContainer;
/*     */         
/* 510 */         if (creativeContainerGui.a() == akf.m.a())
/*     */         {
/* 512 */           if (isCreativeSlot(slot))
/*     */           {
/* 514 */             return getInnerSlot(slot);
/*     */           }
/*     */         }
/*     */       } 
/*     */       
/* 519 */       return slot;
/*     */     }
/* 521 */     catch (Exception exception) {
/*     */       
/* 523 */       return null;
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
/*     */   public static int getSlotIndex(byl guiContainer, ajk mouseOverSlot) {
/* 535 */     if (mouseOverSlot != null) {
/*     */       
/* 537 */       int slotNumber = mouseOverSlot.e;
/*     */       
/* 539 */       if (guiContainer instanceof byz)
/*     */       {
/* 541 */         if (slotNumber < 45) {
/*     */           
/* 543 */           int pageNumber = ((byz)guiContainer).a();
/* 544 */           int scrollPosition = getCreativeInventoryScroll((byz)guiContainer);
/* 545 */           if (pageNumber == akf.g.a())
/*     */           {
/* 547 */             return -1;
/*     */           }
/* 549 */           if (pageNumber != akf.m.a())
/*     */           {
/* 551 */             slotNumber += 100 * (pageNumber + 1) + scrollPosition;
/*     */           
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 557 */           slotNumber -= 9;
/*     */         } 
/*     */       }
/*     */       
/* 561 */       return slotNumber;
/*     */     } 
/*     */     
/* 564 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getCreativeInventoryScroll(byz containerGui) {
/*     */     try {
/* 575 */       wa creativeInventory = (wa)PrivateFields.StaticFields.creativeInventory.get();
/* 576 */       amj firstSlotStack = creativeInventory.a(0);
/* 577 */       List<amj> itemsList = ((IContainerCreative)containerGui.h).getItemsList();
/* 578 */       return itemsList.indexOf(firstSlotStack);
/*     */     }
/* 580 */     catch (Exception ex) {
/*     */       
/* 582 */       ex.printStackTrace();
/*     */ 
/*     */       
/* 585 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isCreativeSlot(ajk slot) {
/* 594 */     return slot.getClass().equals(PrivateClasses.SlotCreativeInventory.Class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ajk getInnerSlot(ajk slot) {
/*     */     try {
/* 605 */       return (ajk)PrivateFields.creativeSlotInnerSlot.get(slot);
/*     */     }
/* 607 */     catch (Exception ex) {
/*     */       
/* 609 */       return slot;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\helpers\HelperContainerSlots.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */