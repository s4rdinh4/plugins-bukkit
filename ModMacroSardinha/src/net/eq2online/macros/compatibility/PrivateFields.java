/*     */ package net.eq2online.macros.compatibility;
/*     */ 
/*     */ import ajk;
/*     */ import amj;
/*     */ import aos;
/*     */ import aot;
/*     */ import bdj;
/*     */ import bsr;
/*     */ import bul;
/*     */ import buv;
/*     */ import bvx;
/*     */ import bwl;
/*     */ import byf;
/*     */ import byi;
/*     */ import byj;
/*     */ import byz;
/*     */ import bzm;
/*     */ import cji;
/*     */ import ckn;
/*     */ import com.mumfrey.liteloader.core.runtime.Obf;
/*     */ import com.mumfrey.liteloader.util.ObfuscationUtilities;
/*     */ import ctq;
/*     */ import cwc;
/*     */ import cwf;
/*     */ import ho;
/*     */ import iz;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.eq2online.obfuscation.ObfTbl;
/*     */ import oa;
/*     */ import qi;
/*     */ import qn;
/*     */ import qt;
/*     */ import um;
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
/*     */ public class PrivateFields<P, T>
/*     */ {
/*     */   public final Class<P> parentClass;
/*     */   private final String fieldName;
/*     */   
/*     */   private PrivateFields(Class<P> owner, Obf mapping) {
/*  70 */     this.parentClass = owner;
/*  71 */     this.fieldName = ObfuscationUtilities.getObfuscatedFieldName(mapping);
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
/*     */   public T get(P instance) {
/*     */     try {
/*  85 */       return Reflection.getPrivateValue(this.parentClass, instance, this.fieldName);
/*     */     }
/*  87 */     catch (Exception ex) {
/*     */       
/*  89 */       return null;
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
/*     */   
/*     */   public T set(P instance, T value) {
/*     */     try {
/* 104 */       Reflection.setPrivateValue(this.parentClass, instance, this.fieldName, value);
/*     */     }
/* 106 */     catch (Exception exception) {}
/*     */     
/* 108 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class StaticFields<P, T>
/*     */     extends PrivateFields<P, T>
/*     */   {
/*     */     public StaticFields(Class<P> owner, ObfTbl mapping) {
/* 120 */       super(owner, (Obf)mapping);
/* 121 */     } public T get() { return get(null); } public void set(T value) {
/* 122 */       set(null, value);
/*     */     }
/*     */     
/* 125 */     public static final StaticFields<cwc, cwf> locale = new StaticFields((Class)cwc.class, ObfTbl.currentLocale);
/* 126 */     public static final StaticFields<byz, wa> creativeInventory = new StaticFields((Class)byz.class, ObfTbl.creativeTempInventory);
/* 127 */     public static final StaticFields<bsr, um> keyBindHash = new StaticFields((Class)bsr.class, ObfTbl.keyBindHash);
/* 128 */     public static final StaticFields<cji, oa[]> shaders = new StaticFields((Class)cji.class, ObfTbl.shaders);
/*     */   }
/*     */   
/* 131 */   public static final PrivateFields<aos, Integer> shapedRecipeWidth = new PrivateFields((Class)aos.class, (Obf)ObfTbl.shapedRecipeWidth);
/* 132 */   public static final PrivateFields<aos, Integer> shapedRecipeHeight = new PrivateFields((Class)aos.class, (Obf)ObfTbl.shapedRecipeHeight);
/* 133 */   public static final PrivateFields<aos, amj[]> shapedRecipeItems = new PrivateFields((Class)aos.class, (Obf)ObfTbl.shapedRecipeItems);
/* 134 */   public static final PrivateFields<aot, List<amj>> shapelessRecipeItems = new PrivateFields((Class)aot.class, (Obf)ObfTbl.shapelessRecipeItems);
/* 135 */   public static final PrivateFields<bzm, bdj> editingSign = new PrivateFields((Class)bzm.class, (Obf)ObfTbl.editingSign);
/* 136 */   public static final PrivateFields<bwl, Integer> coolDownTimer = new PrivateFields((Class)bwl.class, (Obf)ObfTbl.coolDownTimer);
/* 137 */   public static final PrivateFields<byz, ajk> creativeBinSlot = new PrivateFields((Class)byz.class, (Obf)ObfTbl.creativeBinSlot);
/* 138 */   public static final PrivateFields<byz, Float> creativeGuiScroll = new PrivateFields((Class)byz.class, (Obf)ObfTbl.creativeGuiScroll);
/* 139 */   public static final PrivateFields<qt, qn> serverEntityTracker = new PrivateFields((Class)qt.class, (Obf)ObfTbl.serverEntityTracker);
/* 140 */   public static final PrivateFields<cwf, Map<String, String>> translateTable = new PrivateFields((Class)cwf.class, (Obf)ObfTbl.translateTable);
/* 141 */   public static final PrivateFields<ctq, BufferedImage> downloadedImage = new PrivateFields((Class)ctq.class, (Obf)ObfTbl.downloadedImage);
/* 142 */   public static final PrivateFields<bsr, Integer> keyBindPresses = new PrivateFields((Class)bsr.class, (Obf)ObfTbl.keyBindPresses);
/* 143 */   public static final PrivateFields<bsr, Boolean> keyBindPressed = new PrivateFields((Class)bsr.class, (Obf)ObfTbl.keyBindPressed);
/* 144 */   public static final PrivateFields<byj, byf> keyBindingList = new PrivateFields((Class)byj.class, (Obf)ObfTbl.keyBindingList);
/* 145 */   public static final PrivateFields<byf, buv[]> keyBindingEntries = new PrivateFields((Class)byf.class, (Obf)ObfTbl.keyBindingEntries);
/* 146 */   public static final PrivateFields<byi, bsr> keyEntryBinding = new PrivateFields((Class)byi.class, (Obf)ObfTbl.keyEntryBinding);
/* 147 */   public static final PrivateFields<iz, ho> serverChatComponent = new PrivateFields((Class)iz.class, (Obf)ObfTbl.serverChatComponent);
/* 148 */   public static final PrivateFields<bvx, bul> chatTextField = new PrivateFields((Class)bvx.class, (Obf)ObfTbl.chatTextField);
/*     */   
/* 150 */   public static final PrivateFields<ckn, HashMap<Integer, qi>> damagedBlocks = new PrivateFields((Class)ckn.class, (Obf)ObfTbl.damagedBlocks);
/*     */ 
/*     */   
/* 153 */   public static final PrivateFields<ajk, ajk> creativeSlotInnerSlot = new PrivateFields((Class)PrivateClasses.SlotCreativeInventory.Class, (Obf)ObfTbl.creativeSlotInnerSlot);
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\compatibility\PrivateFields.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */