/*     */ package net.eq2online.macros.scripting.actions.option;
/*     */ 
/*     */ import bsu;
/*     */ import com.mumfrey.liteloader.client.overlays.IEntityRenderer;
/*     */ import dax;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IReturnValue;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*     */ import oa;
/*     */ 
/*     */ 
/*     */ public class ScriptActionShaderGroup
/*     */   extends ScriptAction
/*     */ {
/*     */   public ScriptActionShaderGroup(ScriptContext context) {
/*  20 */     super(context, "shadergroup");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isThreadSafe() {
/*  26 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/*  32 */     bsu minecraft = bsu.z();
/*  33 */     IEntityRenderer entityRenderer = (IEntityRenderer)minecraft.o;
/*     */     
/*  35 */     if (params.length == 0) {
/*     */       
/*  37 */       deactivateShader(entityRenderer);
/*     */     }
/*  39 */     else if (params.length > 0) {
/*     */       
/*  41 */       String shaderPath = ScriptCore.parseVars(provider, macro, params[0], false).trim();
/*     */       
/*  43 */       if (shaderPath.equals("+")) {
/*     */         
/*  45 */         minecraft.o.d();
/*     */       }
/*  47 */       else if (shaderPath.equals("-")) {
/*     */         
/*  49 */         activatePreviousShader(minecraft, entityRenderer);
/*     */       }
/*     */       else {
/*     */         
/*  53 */         if (!shaderPath.endsWith(".json")) shaderPath = shaderPath + ".json"; 
/*  54 */         if (shaderPath.indexOf('/') == -1) shaderPath = "shaders/post/" + shaderPath; 
/*  55 */         oa shaderLocation = new oa(shaderPath);
/*     */         
/*  57 */         selectShader(minecraft, shaderLocation);
/*     */       } 
/*     */     } 
/*     */     
/*  61 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void deactivateShader(IEntityRenderer entityRenderer) {
/*  66 */     entityRenderer.setShaderIndex((entityRenderer.getShaders()).length);
/*  67 */     entityRenderer.setUseShader(false);
/*  68 */     entityRenderer.selectShader(null);
/*     */   }
/*     */ 
/*     */   
/*     */   private void activatePreviousShader(bsu minecraft, IEntityRenderer entityRenderer) {
/*  73 */     oa[] shaders = entityRenderer.getShaders();
/*  74 */     int shaderIndex = entityRenderer.getShaderIndex() - 1;
/*  75 */     if (shaderIndex < 0) {
/*     */       
/*  77 */       deactivateShader(entityRenderer);
/*     */     }
/*     */     else {
/*     */       
/*  81 */       selectShader(minecraft, entityRenderer, shaders, shaderIndex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void selectShader(bsu minecraft, oa shaderLocation) {
/*  87 */     if (!dax.N)
/*     */       return; 
/*  89 */     IEntityRenderer entityRenderer = (IEntityRenderer)minecraft.o;
/*  90 */     oa[] shaders = entityRenderer.getShaders();
/*     */     
/*  92 */     int shaderIndex = -1;
/*  93 */     for (int index = 0; index < shaders.length; index++) {
/*     */       
/*  95 */       if (shaders[index].equals(shaderLocation)) {
/*  96 */         shaderIndex = index;
/*     */       }
/*     */     } 
/*  99 */     selectShader(minecraft, entityRenderer, shaders, shaderIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   private void selectShader(bsu minecraft, IEntityRenderer entityRenderer, oa[] shaders, int shaderIndex) {
/* 104 */     if (shaderIndex < 0)
/*     */       return; 
/* 106 */     if (minecraft.o.f() != null)
/*     */     {
/* 108 */       minecraft.o.f().a();
/*     */     }
/*     */     
/* 111 */     entityRenderer.setShaderIndex(shaderIndex);
/* 112 */     entityRenderer.selectShader(shaders[shaderIndex]);
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\option\ScriptActionShaderGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */