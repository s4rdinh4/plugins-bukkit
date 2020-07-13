/*     */ package net.eq2online.macros.scripting.actions.game;
/*     */ 
/*     */ import ahb;
/*     */ import amj;
/*     */ import amk;
/*     */ import bdj;
/*     */ import bsu;
/*     */ import bxf;
/*     */ import cio;
/*     */ import ho;
/*     */ import hy;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.LocalisationProvider;
/*     */ import net.eq2online.macros.compatibility.PrivateFields;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.scripting.ScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IReturnValue;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*     */ 
/*     */ 
/*     */ public class ScriptActionPlaceSign
/*     */   extends ScriptAction
/*     */ {
/*     */   private boolean handlePlacingSign = false;
/*     */   private boolean closeGui = true;
/*  32 */   private int elapsedTicks = 0;
/*     */   
/*  34 */   private String[] signText = new String[4];
/*     */ 
/*     */   
/*     */   public ScriptActionPlaceSign(ScriptContext context) {
/*  38 */     super(context, "placesign");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isThreadSafe() {
/*  44 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPermissable() {
/*  53 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPermissionGroup() {
/*  62 */     return "world";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/*  68 */     bsu minecraft = bsu.z();
/*  69 */     cio thePlayer = AbstractionLayer.getPlayer();
/*     */     
/*  71 */     int signSlotID = -1;
/*     */     
/*  73 */     if (thePlayer != null && thePlayer.bg != null) {
/*     */       
/*  75 */       ahb inventory = thePlayer.bg;
/*     */       
/*  77 */       for (int i = 0; i < 9; i++) {
/*     */         
/*  79 */         if (inventory.a[i] != null && inventory.a[i].b() == amk.ap) {
/*     */           
/*  81 */           signSlotID = i;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*  86 */       if (signSlotID > -1) {
/*     */         
/*  88 */         this.elapsedTicks = 0;
/*  89 */         this.handlePlacingSign = true;
/*     */         
/*  91 */         amj itemstack = inventory.a[signSlotID];
/*  92 */         provider.actionUseItem(minecraft, thePlayer, itemstack, signSlotID);
/*     */         
/*  94 */         this.signText[0] = (params.length > 0) ? Macros.replaceInvalidChars(ScriptCore.parseVars(provider, macro, params[0], false)) : "";
/*  95 */         this.signText[1] = (params.length > 1) ? Macros.replaceInvalidChars(ScriptCore.parseVars(provider, macro, params[1], false)) : "";
/*  96 */         this.signText[2] = (params.length > 2) ? Macros.replaceInvalidChars(ScriptCore.parseVars(provider, macro, params[2], false)) : "";
/*  97 */         this.signText[3] = (params.length > 3) ? Macros.replaceInvalidChars(ScriptCore.parseVars(provider, macro, params[3], false)) : "";
/*     */         
/*  99 */         if (params.length > 4)
/*     */         {
/* 101 */           this.closeGui = (!params[4].equalsIgnoreCase("true") && !params[4].equals("1"));
/*     */         }
/*     */         else
/*     */         {
/* 105 */           this.closeGui = true;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 110 */         provider.actionAddChatMessage(LocalisationProvider.getLocalisedString("script.error.nosign"));
/*     */       } 
/*     */     } 
/*     */     
/* 114 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int onTick(IScriptActionProvider provider) {
/* 120 */     if (this.handlePlacingSign) {
/*     */       
/* 122 */       this.elapsedTicks++;
/*     */       
/* 124 */       if (this.elapsedTicks > 200) {
/*     */         
/* 126 */         System.err.println("Timed out waiting for sign GUI");
/* 127 */         this.handlePlacingSign = false;
/*     */       } else {
/*     */ 
/*     */         
/*     */         try {
/*     */           
/* 133 */           bxf currentScreen = (ScriptActionProvider.getMinecraft()).m;
/*     */           
/* 135 */           if ((this.closeGui || this.elapsedTicks > 10) && currentScreen instanceof bzm) {
/*     */             
/* 137 */             this.handlePlacingSign = false;
/*     */             
/* 139 */             bdj entitySign = (bdj)PrivateFields.editingSign.get(currentScreen);
/*     */             
/* 141 */             if (entitySign != null) {
/*     */               
/* 143 */               System.err.println("Setting sign entity text");
/* 144 */               for (int i = 0; i < 4; i++) {
/*     */                 
/* 146 */                 if (this.signText[i].length() > 15) this.signText[i] = this.signText[i].substring(0, 14); 
/* 147 */                 entitySign.a[i] = (ho)new hy(this.signText[i]);
/*     */               } 
/*     */               
/* 150 */               if (this.closeGui) {
/*     */                 
/* 152 */                 System.err.println("Marking dirty and closing screen");
/* 153 */                 entitySign.o_();
/* 154 */                 AbstractionLayer.displayGuiScreen(null);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } catch (Exception ex) {
/* 159 */           Log.printStackTrace(ex);
/*     */         } 
/*     */       } 
/*     */     } 
/* 163 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionPlaceSign.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */