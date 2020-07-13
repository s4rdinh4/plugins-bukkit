/*     */ package net.eq2online.macros.scripting.parser;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.eq2online.macros.scripting.IActionFilter;
/*     */ import net.eq2online.macros.scripting.IDocumentor;
/*     */ import net.eq2online.macros.scripting.IErrorLogger;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionContext;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventManager;
/*     */ import net.eq2online.macros.scripting.api.IMessageFilter;
/*     */ import net.eq2online.macros.scripting.api.IScriptAction;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.IScriptParser;
/*     */ import net.eq2online.macros.scripting.api.IScriptedIterator;
/*     */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScriptContext
/*     */ {
/*  28 */   private static final Map<String, ScriptContext> contexts = new HashMap<String, ScriptContext>();
/*     */   
/*  30 */   public static final ScriptContext MAIN = new ScriptContext("main");
/*     */   
/*  32 */   public static final ScriptContext CHATFILTER = new ScriptContext("chatfilter");
/*     */ 
/*     */ 
/*     */   
/*     */   private final String name;
/*     */ 
/*     */ 
/*     */   
/*     */   private ScriptCore core;
/*     */ 
/*     */ 
/*     */   
/*     */   private Constructor<? extends IMacroActionContext> actionContextCtor;
/*     */ 
/*     */   
/*     */   private IErrorLogger logger;
/*     */ 
/*     */   
/*     */   private IActionFilter actionFilter;
/*     */ 
/*     */ 
/*     */   
/*     */   private ScriptContext(String name) {
/*  55 */     if (contexts.containsKey(name))
/*     */     {
/*  57 */       throw new IllegalArgumentException("Context with name \"" + name + "\" already exists, use getContext() instead");
/*     */     }
/*     */     
/*  60 */     this.name = name;
/*     */     
/*  62 */     contexts.put(this.name, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  70 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setCore(ScriptCore core) {
/*  78 */     this.core = core;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScriptCore getCore() {
/*  86 */     return this.core;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setActionContextClass(Class<? extends IMacroActionContext> actionContextClass) {
/*     */     try {
/*  93 */       this.actionContextCtor = actionContextClass.getDeclaredConstructor(new Class[] { ScriptContext.class, IScriptActionProvider.class, IVariableProvider.class });
/*     */     }
/*  95 */     catch (Exception ex) {
/*     */       
/*  97 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IScriptActionProvider getScriptActionProvider() {
/* 106 */     return this.core.getScriptActionProvider();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IScriptParser getParser() {
/* 116 */     return this.core.getParser();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IDocumentor getDocumentor() {
/* 126 */     return this.core.getDocumentor();
/*     */   }
/*     */ 
/*     */   
/*     */   public IMessageFilter getMessageFilter() {
/* 131 */     return this.core.getMessageFilter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<? extends IScriptedIterator> getIterator(String iteratorName) {
/* 140 */     return this.core.getIterator(iteratorName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, IScriptAction> getActions() {
/* 148 */     return this.core.getActions();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IScriptAction> getActionsList() {
/* 156 */     return this.core.getActionsList();
/*     */   }
/*     */ 
/*     */   
/*     */   public IScriptAction getAction(String actionName) {
/* 161 */     return this.core.getAction(actionName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMacroActionContext createActionContext(IVariableProvider contextVariableProvider) {
/*     */     try {
/* 168 */       return this.actionContextCtor.newInstance(new Object[] { this, getScriptActionProvider(), contextVariableProvider });
/*     */     }
/* 170 */     catch (Exception ex) {
/*     */       
/* 172 */       throw new RuntimeException(ex);
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
/*     */   
/*     */   public boolean create(IScriptActionProvider provider, IMacroEventManager eventManager, IScriptParser defaultParser, IErrorLogger logger, IDocumentor documentor, IActionFilter actionFilter, Class<? extends IMacroActionContext> actionContextClass) {
/* 187 */     this.logger = logger;
/* 188 */     this.actionFilter = actionFilter;
/* 189 */     setActionContextClass(actionContextClass);
/*     */     
/* 191 */     return ScriptCore.createCoreForContext(this, provider, eventManager, defaultParser, logger, documentor);
/*     */   }
/*     */ 
/*     */   
/*     */   public void initActions() {
/* 196 */     this.core.initActions(this.actionFilter, this.logger);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCreated() {
/* 204 */     return (this.core != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 213 */     return (this == obj || this.name.equals(obj));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 222 */     return this.name.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 228 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ScriptContext getContext(String name) {
/* 233 */     if (!contexts.containsKey(name))
/*     */     {
/* 235 */       return new ScriptContext(name);
/*     */     }
/*     */     
/* 238 */     return contexts.get(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Collection<ScriptContext> getAvailableContexts() {
/* 243 */     return Collections.unmodifiableCollection(contexts.values());
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\parser\ScriptContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */