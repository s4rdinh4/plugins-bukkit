/*     */ package net.eq2online.macros.struct;
/*     */ 
/*     */ import alq;
/*     */ import amj;
/*     */ import bss;
/*     */ import bsu;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import com.mumfrey.liteloader.util.render.Icon;
/*     */ import cqh;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.interfaces.IListObject;
/*     */ import net.eq2online.macros.interfaces.ISettingsProvider;
/*     */ import oa;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemStackInfo
/*     */   implements IListObject
/*     */ {
/*  31 */   protected static HashMap<Integer, ArrayList<Integer>> includeItems = new HashMap<Integer, ArrayList<Integer>>();
/*     */   
/*  33 */   protected static cqh itemRenderer = new cqh(bsu.z().N(), bsu.z().af().a().a());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int id;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected alq item;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String itemIdentifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int damageValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected amj itemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStackInfo(int id, alq item, int damage, amj stack) {
/*  83 */     this.id = id;
/*  84 */     this.item = item;
/*  85 */     this.itemIdentifier = Macros.getItemName(item);
/*  86 */     this.name = (item == null) ? "Air" : item.k(stack);
/*     */     
/*  88 */     this.damageValue = damage;
/*  89 */     this.itemStack = stack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasIcon() {
/*  99 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public oa getIconTexture() {
/* 110 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void bindIconTexture() {
/* 116 */     AbstractionLayer.bindTexture(getIconTexture());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Icon getIcon() {
/* 122 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public amj getDisplayItem() {
/* 128 */     return this.itemStack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDamage() {
/* 138 */     return this.damageValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public amj getItemStack() {
/* 147 */     return this.itemStack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean compareTo(ItemStackInfo other) {
/* 158 */     if (includeItems.containsKey(other.itemIdentifier)) {
/*     */       
/* 160 */       ArrayList<Integer> damageValues = includeItems.get(other.itemIdentifier);
/*     */       
/* 162 */       if (damageValues == null && other.damageValue == 0) return false;
/*     */       
/* 164 */       if (damageValues != null)
/*     */       {
/* 166 */         for (Integer damageValue : damageValues) {
/*     */           
/* 168 */           if (damageValue.equals(Integer.valueOf(other.damageValue))) {
/* 169 */             return false;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/* 174 */     return other.getItemStack().a(this.itemStack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 184 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDisplayName() {
/* 190 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/* 201 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/* 210 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIdentifier() {
/* 216 */     return this.itemIdentifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getData() {
/* 225 */     return Integer.valueOf(this.damageValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IListObject.CustomDrawBehaviour getCustomDrawBehaviour() {
/* 234 */     return IListObject.CustomDrawBehaviour.NoCustomDraw;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawCustom(boolean iconEnabled, bsu minecraft, int mouseX, int mouseY, int xPosition, int yPosition, int width, int height, int updateCounter) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mousePressed(boolean iconEnabled, bsu minecraft, int mouseX, int mouseY, int xPosition, int yPosition, int width, int height) {
/* 252 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCustomAction(boolean bClear) {
/* 270 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDraggable() {
/* 279 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIconId(int newIconId) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(String newText) {
/* 290 */     this.name = newText;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisplayName(String newDisplayName) {
/* 296 */     this.name = newDisplayName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(int newId) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setData(Object newData) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanEditInPlace() {
/* 312 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEditingInPlace() {
/* 318 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void beginEditInPlace() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endEditInPlace() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean editInPlaceKeyTyped(char keyChar, int keyCode) {
/* 334 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void editInPlaceDraw(boolean iconEnabled, bsu minecraft, int mouseX, int mouseY, int xPosition, int yPosition, int width, int height, int updateCounter) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean editInPlaceMousePressed(boolean iconEnabled, bsu minecraft, int mouseX, int mouseY, int xPosition, int yPosition, int width, int height) {
/* 349 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String serialise() {
/* 355 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean renderIcon(bsu minecraft, int xPosition, int yPosition) {
/* 361 */     renderIcon(this.itemStack, xPosition, yPosition);
/*     */     
/* 363 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderIcon(amj itemStack, int xPosition, int yPosition) {
/* 368 */     if (itemStack == null)
/*     */       return; 
/* 370 */     GL.glDepthFunc(515);
/* 371 */     GL.glEnableRescaleNormal();
/* 372 */     bss.c();
/*     */     
/* 374 */     itemRenderer.a(itemStack, xPosition, yPosition);
/*     */     
/* 376 */     GL.glDisableRescaleNormal();
/* 377 */     bss.a();
/* 378 */     GL.glDepthFunc(515);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateName() {
/*     */     try {
/* 388 */       this.name = this.itemStack.q() + " (" + this.itemIdentifier;
/*     */       
/* 390 */       if (this.damageValue > 0) {
/* 391 */         this.name += ":" + this.damageValue;
/*     */       }
/* 393 */       this.name += ")";
/*     */     }
/* 395 */     catch (Exception ex) {
/*     */       
/* 397 */       this.name = this.itemStack.getClass().getSimpleName();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void notifySettingsCleared() {
/* 403 */     includeItems.clear();
/* 404 */     includeItems.put(Integer.valueOf(80), null);
/*     */     
/* 406 */     ArrayList<Integer> leafOverrides = new ArrayList<Integer>();
/* 407 */     leafOverrides.add(Integer.valueOf(2));
/*     */     
/* 409 */     includeItems.put(Integer.valueOf(18), leafOverrides);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void notifySettingsLoaded(ISettingsProvider settings) {
/* 414 */     String serialisedOverrides = settings.getSetting("list.items.include", "{18:2}{80}");
/* 415 */     Pattern overridePattern = Pattern.compile("\\{([0-9]+)(:([0-9]+))?\\}");
/* 416 */     Matcher overridePatternMatcher = overridePattern.matcher(serialisedOverrides);
/*     */     
/* 418 */     includeItems.clear();
/*     */     
/* 420 */     while (overridePatternMatcher.find()) {
/*     */       
/* 422 */       Integer itemId = Integer.valueOf(Integer.parseInt(overridePatternMatcher.group(1)));
/*     */       
/* 424 */       if (overridePatternMatcher.group(3) == null) {
/*     */         
/* 426 */         if (!includeItems.containsKey(itemId))
/*     */         {
/* 428 */           includeItems.put(itemId, null);
/*     */         }
/*     */         
/*     */         continue;
/*     */       } 
/* 433 */       Integer itemDamage = Integer.valueOf(Integer.parseInt(overridePatternMatcher.group(3)));
/*     */       
/* 435 */       if (includeItems.get(itemId) == null) {
/* 436 */         includeItems.put(itemId, new ArrayList<Integer>());
/*     */       }
/* 438 */       if (!((ArrayList)includeItems.get(itemId)).contains(itemDamage)) {
/* 439 */         ((ArrayList<Integer>)includeItems.get(itemId)).add(itemDamage);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void saveSettings(ISettingsProvider settings) {
/* 446 */     StringBuilder serialisedOverrides = new StringBuilder();
/*     */     
/* 448 */     for (Map.Entry<Integer, ArrayList<Integer>> override : includeItems.entrySet()) {
/*     */       
/* 450 */       if (override.getValue() == null) {
/*     */         
/* 452 */         serialisedOverrides.append("{").append(override.getKey()).append("}");
/*     */         
/*     */         continue;
/*     */       } 
/* 456 */       for (Integer damageValue : override.getValue())
/*     */       {
/* 458 */         serialisedOverrides.append("{").append(override.getKey()).append(":").append(damageValue).append("}");
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 463 */     settings.setSetting("list.items.include", serialisedOverrides.toString());
/* 464 */     settings.setSettingComment("list.items.include", "Item ID/damage values to include in the items list that aren't automatically enumerated, use {id} for base items and {id:damage} for specific damage values");
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\struct\ItemStackInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */