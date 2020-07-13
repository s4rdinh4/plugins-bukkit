/*    */ package net.eq2online.macros.scripting.variable.providers;
/*    */ 
/*    */ import bsu;
/*    */ import bto;
/*    */ import btr;
/*    */ import cop;
/*    */ import cxz;
/*    */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*    */ import net.eq2online.macros.compatibility.PrivateFields;
/*    */ import net.eq2online.macros.scripting.variable.VariableCache;
/*    */ import oa;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VariableProviderSettings
/*    */   extends VariableCache
/*    */ {
/* 22 */   private bto gameSettings = AbstractionLayer.getGameSettings();
/*    */   
/* 24 */   private bsu mc = bsu.z();
/*    */   
/*    */   private final String[] shaders;
/*    */ 
/*    */   
/*    */   public VariableProviderSettings() {
/* 30 */     oa[] availableShaders = (oa[])PrivateFields.StaticFields.shaders.get();
/* 31 */     this.shaders = new String[availableShaders.length];
/* 32 */     for (int i = 0; i < availableShaders.length; i++)
/*    */     {
/* 34 */       this.shaders[i] = availableShaders[i].toString();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateVariables(boolean clock) {
/* 41 */     if (this.gameSettings != null) {
/*    */       
/* 43 */       storeVariable("FOV", getOptionIntValue(btr.c, 0.0F, 1.0F));
/* 44 */       storeVariable("GAMMA", getOptionIntValue(btr.d, 0.0F, 100.0F));
/* 45 */       storeVariable("SENSITIVITY", getOptionIntValue(btr.b, 0.0F, 200.0F));
/* 46 */       storeVariable("MUSIC", getSoundLevel(cxz.b, 0.0F, 100.0F));
/* 47 */       storeVariable("SOUND", getSoundLevel(cxz.a, 0.0F, 100.0F));
/* 48 */       storeVariable("RECORDVOLUME", getSoundLevel(cxz.c, 0.0F, 100.0F));
/* 49 */       storeVariable("WEATHERVOLUME", getSoundLevel(cxz.d, 0.0F, 100.0F));
/* 50 */       storeVariable("BLOCKVOLUME", getSoundLevel(cxz.e, 0.0F, 100.0F));
/* 51 */       storeVariable("HOSTILEVOLUME", getSoundLevel(cxz.f, 0.0F, 100.0F));
/* 52 */       storeVariable("NEUTRALVOLUME", getSoundLevel(cxz.g, 0.0F, 100.0F));
/* 53 */       storeVariable("PLAYERVOLUME", getSoundLevel(cxz.h, 0.0F, 100.0F));
/* 54 */       storeVariable("AMBIENTVOLUME", getSoundLevel(cxz.i, 0.0F, 100.0F));
/*    */       
/* 56 */       storeVariable("FPS", bsu.ah());
/* 57 */       storeVariable("CHUNKUPDATES", cop.a);
/*    */       
/* 59 */       setCachedVariable("SHADERGROUPS", this.shaders);
/*    */       
/* 61 */       if (this.mc.o != null && this.mc.o.a()) {
/* 62 */         storeVariable("SHADERGROUP", this.mc.o.f().b());
/*    */       } else {
/* 64 */         storeVariable("SHADERGROUP", "");
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getVariable(String variableName) {
/* 71 */     return getCachedValue(variableName);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected int getOptionIntValue(btr option, float min, float max) {
/* 84 */     return (int)(min + this.gameSettings.a(option) * (max - min));
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getSoundLevel(cxz category, float min, float max) {
/* 89 */     return (int)(min + this.gameSettings.a(category) * (max - min));
/*    */   }
/*    */   
/*    */   public void onInit() {}
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\variable\providers\VariableProviderSettings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */