/*     */ package net.eq2online.macros.scripting;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventProvider;
/*     */ import net.eq2online.macros.scripting.api.IScriptAction;
/*     */ import net.eq2online.macros.scripting.api.IScriptedIterator;
/*     */ import net.eq2online.macros.scripting.api.IVariableProvider;
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
/*     */ public class LoadedModuleInfo
/*     */ {
/*     */   public final File module;
/*     */   public final String name;
/*  33 */   private int customActionCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   private int customVariableProviderCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   private int customIteratorCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   private int customEventProviderCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   private List<String> actions = new ArrayList<String>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   private List<String> providers = new ArrayList<String>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   private List<String> iterators = new ArrayList<String>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private List<String> eventProviders = new ArrayList<String>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoadedModuleInfo(File module) {
/*  75 */     this.module = module;
/*  76 */     this.name = module.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IScriptAction addAction(IScriptAction action) {
/*  84 */     if (action != null && !this.actions.contains(action.toString())) {
/*     */       
/*  86 */       this.customActionCount++;
/*  87 */       this.actions.add(action.toString());
/*     */     } 
/*     */     
/*  90 */     return action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IVariableProvider addProvider(IVariableProvider provider) {
/*  98 */     if (provider != null && !this.providers.contains(provider.getClass().getSimpleName())) {
/*     */       
/* 100 */       this.customVariableProviderCount++;
/* 101 */       this.providers.add(provider.getClass().getSimpleName());
/*     */     } 
/*     */     
/* 104 */     return provider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IScriptedIterator addIterator(IScriptedIterator iterator) {
/* 112 */     if (iterator != null && !this.iterators.contains(iterator.getClass().getSimpleName())) {
/*     */       
/* 114 */       this.customIteratorCount++;
/* 115 */       this.iterators.add(iterator.getClass().getSimpleName());
/*     */     } 
/*     */     
/* 118 */     return iterator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMacroEventProvider addEventProvider(IMacroEventProvider provider) {
/* 126 */     if (provider != null && !this.eventProviders.contains(provider.getClass().getSimpleName())) {
/*     */       
/* 128 */       this.customEventProviderCount++;
/* 129 */       this.eventProviders.add(provider.getClass().getSimpleName());
/*     */     } 
/*     */     
/* 132 */     return provider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printStatus() {
/* 140 */     if (this.customActionCount + this.customVariableProviderCount > 0)
/* 141 */       Log.info("API Loaded module {0} found {1} custom action(s) {2} new variable provider(s) {3} new iterator(s), {4} event provider(s)", new Object[] { this.name, Integer.valueOf(this.customActionCount), Integer.valueOf(this.customVariableProviderCount), Integer.valueOf(this.customIteratorCount), Integer.valueOf(this.customEventProviderCount) }); 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\LoadedModuleInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */