/*     */ package net.eq2online.macros.scripting.actions.input;
/*     */ 
/*     */ import bsr;
/*     */ import bto;
/*     */ import java.util.ArrayList;
/*     */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*     */ import net.eq2online.macros.compatibility.PrivateFields;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IReturnValue;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*     */ import um;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScriptActionKey
/*     */   extends ScriptAction
/*     */ {
/*  26 */   private ArrayList<bsr> pressedKeys = new ArrayList<bsr>();
/*     */ 
/*     */   
/*     */   public ScriptActionKey(ScriptContext context) {
/*  30 */     super(context, "key");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isThreadSafe() {
/*  36 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPermissable() {
/*  45 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/*  51 */     int keyCode = 0;
/*     */     
/*  53 */     if (params.length > 0) {
/*     */       
/*  55 */       String parameter = ScriptCore.parseVars(provider, macro, params[0], false);
/*  56 */       bto gameSettings = AbstractionLayer.getGameSettings();
/*     */       
/*  58 */       if (parameter.equalsIgnoreCase("inventory")) keyCode = gameSettings.aa.i(); 
/*  59 */       if (parameter.equalsIgnoreCase("drop")) keyCode = gameSettings.ac.i(); 
/*  60 */       if (parameter.equalsIgnoreCase("chat")) keyCode = gameSettings.ag.i(); 
/*  61 */       if (parameter.equalsIgnoreCase("attack")) keyCode = gameSettings.ad.i(); 
/*  62 */       if (parameter.equalsIgnoreCase("use")) keyCode = gameSettings.ab.i(); 
/*  63 */       if (parameter.equalsIgnoreCase("pick")) keyCode = gameSettings.ae.i(); 
/*  64 */       if (parameter.equalsIgnoreCase("screenshot")) keyCode = gameSettings.aj.i(); 
/*  65 */       if (parameter.equalsIgnoreCase("smoothcamera")) keyCode = gameSettings.al.i();
/*     */     
/*     */     } 
/*  68 */     if (keyCode != 0) {
/*     */       
/*  70 */       um kb = (um)PrivateFields.StaticFields.keyBindHash.get();
/*  71 */       bsr keyBinding = (bsr)kb.a(keyCode);
/*     */       
/*  73 */       if (keyBinding != null) {
/*     */         
/*  75 */         if (((Integer)PrivateFields.keyBindPresses.get(keyBinding)).intValue() < 1) {
/*  76 */           bsr.a(keyCode);
/*     */         }
/*  78 */         if (!keyBinding.d()) {
/*     */           
/*  80 */           bsr.a(keyBinding.i(), true);
/*  81 */           this.pressedKeys.add(keyBinding);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  86 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int onTick(IScriptActionProvider provider) {
/*  92 */     int tickedActionCount = 0;
/*     */     
/*  94 */     while (this.pressedKeys.size() > 0) {
/*     */       
/*  96 */       bsr keyBinding = this.pressedKeys.remove(0);
/*  97 */       bsr.a(keyBinding.i(), false);
/*  98 */       tickedActionCount++;
/*     */     } 
/*     */     
/* 101 */     return tickedActionCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPermissionGroup() {
/* 110 */     return "input";
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\input\ScriptActionKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */