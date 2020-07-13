/*     */ package net.eq2online.macros.gui.ext;
/*     */ 
/*     */ import a;
/*     */ import bsr;
/*     */ import bsu;
/*     */ import bto;
/*     */ import bug;
/*     */ import buv;
/*     */ import byj;
/*     */ import cwc;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OverrideKeyEntry
/*     */   implements buv
/*     */ {
/*     */   private final byj controlsGui;
/*     */   private final bsu minecraft;
/*     */   private final int maxDescriptionWidth;
/*     */   private final bsr binding;
/*     */   private final String description;
/*     */   private final bug bindButton;
/*     */   private final bug resetButton;
/*  27 */   private String textLink = LocalisationProvider.getLocalisedString("gui.link");
/*  28 */   private String textUnlink = LocalisationProvider.getLocalisedString("gui.unlink");
/*  29 */   private String textDisable = LocalisationProvider.getLocalisedString("gui.disable");
/*  30 */   private String textDisabled = LocalisationProvider.getLocalisedString("gui.key.disabled");
/*  31 */   private String textSprint = cwc.a("key.sprint", new Object[0]).toUpperCase();
/*     */ 
/*     */   
/*     */   public OverrideKeyEntry(bsu minecraft, byj controlsGui, int maxDescriptionWidth, bsr binding) {
/*  35 */     this.binding = binding;
/*  36 */     this.controlsGui = controlsGui;
/*  37 */     this.minecraft = minecraft;
/*  38 */     this.maxDescriptionWidth = maxDescriptionWidth;
/*     */     
/*  40 */     this.description = cwc.a(binding.g(), new Object[0]);
/*  41 */     this.bindButton = new bug(0, 0, 0, 75, 18, cwc.a(binding.g(), new Object[0]));
/*  42 */     this.resetButton = new bug(0, 0, 0, 50, 18, this.textLink);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int id, int xPosition, int yPosition, int width, int height, int mouseX, int mouseY, boolean mouseOver) {
/*  48 */     int keyCode = this.binding.i();
/*     */     
/*  50 */     boolean isEnabled = MacroModSettings.enableOverride;
/*  51 */     boolean isDefault = (keyCode == this.binding.h());
/*  52 */     boolean isActive = (this.controlsGui.f == this.binding);
/*  53 */     boolean isConflicting = false;
/*     */     
/*  55 */     if (keyCode != 0)
/*     */     {
/*  57 */       for (bsr other : this.minecraft.t.at) {
/*     */         
/*  59 */         if (other != this.binding && other.i() == keyCode) {
/*     */           
/*  61 */           isConflicting = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/*  67 */     this.minecraft.k.a(this.description, xPosition + 90 - this.maxDescriptionWidth, yPosition + height / 2 - this.minecraft.k.a / 2, 16777215);
/*     */     
/*  69 */     this.resetButton.h = xPosition + 190;
/*  70 */     this.resetButton.i = yPosition;
/*  71 */     this.resetButton.j = !isEnabled ? this.textLink : (isDefault ? this.textUnlink : this.textDisable);
/*  72 */     this.resetButton.a(this.minecraft, mouseX, mouseY);
/*     */     
/*  74 */     this.bindButton.h = xPosition + 105;
/*  75 */     this.bindButton.i = yPosition;
/*  76 */     this.bindButton.l = isEnabled;
/*  77 */     this.bindButton.j = !isEnabled ? this.textDisabled : (isDefault ? (a.l + this.textSprint) : bto.c(keyCode));
/*     */     
/*  79 */     if (isActive) {
/*     */       
/*  81 */       this.bindButton.j = a.p + "> " + a.o + this.bindButton.j + a.p + " <";
/*     */     }
/*  83 */     else if (isConflicting) {
/*     */       
/*  85 */       this.bindButton.j = a.m + this.bindButton.j;
/*     */     } 
/*     */     
/*  88 */     this.bindButton.a(this.minecraft, mouseX, mouseY);
/*  89 */     this.bindButton.l = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean a(int id, int mouseX, int mouseY, int button, int relX, int relY) {
/*  95 */     boolean isEnabled = MacroModSettings.enableOverride;
/*     */     
/*  97 */     if (this.bindButton.c(this.minecraft, mouseX, mouseY)) {
/*     */       
/*  99 */       if (!isEnabled) {
/*     */         
/* 101 */         MacroModSettings.enableOverride = true;
/* 102 */         MacroModSettings.save();
/* 103 */         this.minecraft.t.a(this.binding, this.binding.h());
/* 104 */         return true;
/*     */       } 
/*     */       
/* 107 */       this.controlsGui.f = this.binding;
/* 108 */       return true;
/*     */     } 
/* 110 */     if (this.resetButton.c(this.minecraft, mouseX, mouseY)) {
/*     */       
/* 112 */       boolean isDefault = (this.binding.i() == this.binding.h());
/* 113 */       int newValue = !isEnabled ? this.binding.h() : (isDefault ? 29 : this.binding.h());
/* 114 */       this.minecraft.t.a(this.binding, newValue);
/* 115 */       bsr.b();
/*     */       
/* 117 */       if (!isEnabled) {
/*     */         
/* 119 */         MacroModSettings.enableOverride = true;
/* 120 */         MacroModSettings.save();
/*     */       }
/* 122 */       else if (isEnabled && !isDefault) {
/*     */         
/* 124 */         MacroModSettings.enableOverride = false;
/* 125 */         MacroModSettings.save();
/*     */       } 
/*     */       
/* 128 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b(int id, int mouseX, int mouseY, int button, int p_148277_5_, int p_148277_6_) {
/* 139 */     this.bindButton.a(mouseX, mouseY);
/* 140 */     this.resetButton.a(mouseX, mouseY);
/*     */   }
/*     */   
/*     */   public void a(int p_178011_1_, int p_178011_2_, int p_178011_3_) {}
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\gui\ext\OverrideKeyEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */