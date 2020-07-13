/*     */ package net.eq2online.macros.scripting.actions.lang;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.scripting.Variable;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionProcessor;
/*     */ import net.eq2online.macros.scripting.api.IScriptAction;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.IScriptedIterator;
/*     */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*     */ import net.eq2online.macros.scripting.iterators.ScriptedIteratorArray;
/*     */ import net.eq2online.macros.scripting.iterators.ScriptedIteratorEffects;
/*     */ import net.eq2online.macros.scripting.iterators.ScriptedIteratorEnchantments;
/*     */ import net.eq2online.macros.scripting.iterators.ScriptedIteratorEnvironment;
/*     */ import net.eq2online.macros.scripting.iterators.ScriptedIteratorPlayers;
/*     */ import net.eq2online.macros.scripting.iterators.ScriptedIteratorProperties;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*     */ 
/*     */ public class ScriptActionForEach
/*     */   extends ScriptActionDo
/*     */ {
/*  26 */   private static final Pattern expressiveSyntax = Pattern.compile("^(.+?)\\s+as(.+?)(=>(.+?))?$", 2);
/*     */ 
/*     */   
/*     */   public ScriptActionForEach(ScriptContext context) {
/*  30 */     super(context, "foreach");
/*     */ 
/*     */     
/*  33 */     this.context.getCore().registerIterator("enchantments", ScriptedIteratorEnchantments.class);
/*  34 */     this.context.getCore().registerIterator("players", ScriptedIteratorPlayers.class);
/*  35 */     this.context.getCore().registerIterator("effects", ScriptedIteratorEffects.class);
/*  36 */     this.context.getCore().registerIterator("env", ScriptedIteratorEnvironment.class);
/*  37 */     this.context.getCore().registerIterator("properties", ScriptedIteratorProperties.class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isThreadSafe() {
/*  43 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExpectedPopCommands() {
/*  49 */     return "§eFOREACH §cexpects §dNEXT";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean executeStackPush(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/*     */     ScriptedIteratorArray scriptedIteratorArray;
/*  58 */     IScriptedIterator state = (IScriptedIterator)instance.getState();
/*     */     
/*  60 */     if (params.length > 0) {
/*     */       
/*  62 */       if (state == null) {
/*     */ 
/*     */         
/*  65 */         String iteratorName = ScriptCore.parseVars(provider, macro, params[0], false);
/*  66 */         String iteratorVarName = (params.length > 1) ? params[1].toLowerCase() : null;
/*  67 */         String positionVarName = null;
/*     */         
/*  69 */         if (params.length == 1) {
/*     */           
/*  71 */           Matcher syntaxMatcher = expressiveSyntax.matcher(iteratorName);
/*  72 */           if (syntaxMatcher.matches()) {
/*     */             
/*  74 */             iteratorName = syntaxMatcher.group(1).trim();
/*     */             
/*  76 */             if (syntaxMatcher.group(4) != null) {
/*     */               
/*  78 */               positionVarName = syntaxMatcher.group(2).trim();
/*  79 */               iteratorVarName = syntaxMatcher.group(4).trim();
/*     */             }
/*     */             else {
/*     */               
/*  83 */               iteratorVarName = syntaxMatcher.group(2).trim();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/*  88 */         if (this.context.getCore().getIterator(iteratorName.toLowerCase()) != null) {
/*     */           
/*     */           try
/*     */           {
/*  92 */             state = createIterator(iteratorName, provider, macro);
/*  93 */             instance.setState(state);
/*     */           }
/*  95 */           catch (Exception ex)
/*     */           {
/*  97 */             return false;
/*     */           }
/*     */         
/* 100 */         } else if (iteratorVarName != null && Variable.getValidVariableOrArraySpecifier(iteratorName) != null && Variable.isValidScalarVariableName(iteratorVarName)) {
/*     */           
/* 102 */           iteratorName = Variable.getValidVariableOrArraySpecifier(iteratorName);
/* 103 */           if (positionVarName == null) positionVarName = (params.length > 2 && Variable.isValidScalarVariableName(params[2].toLowerCase())) ? params[2].toLowerCase() : null; 
/* 104 */           scriptedIteratorArray = new ScriptedIteratorArray(provider, macro, iteratorName, iteratorVarName, positionVarName);
/* 105 */           instance.setState(scriptedIteratorArray);
/*     */         }
/*     */         else {
/*     */           
/* 109 */           return false;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 114 */         scriptedIteratorArray.increment();
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 119 */       return false;
/*     */     } 
/*     */     
/* 122 */     if (scriptedIteratorArray.continueLooping()) {
/*     */       
/* 124 */       macro.registerVariableProvider((IVariableProvider)scriptedIteratorArray);
/*     */     }
/*     */     else {
/*     */       
/* 128 */       instance.setState(null);
/* 129 */       macro.unregisterVariableProvider((IVariableProvider)scriptedIteratorArray);
/*     */     } 
/*     */     
/* 132 */     return scriptedIteratorArray.continueLooping();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected IScriptedIterator createIterator(String iteratorName, IScriptActionProvider provider, IMacro macro) {
/* 143 */     Class<? extends IScriptedIterator> iteratorClass = this.context.getIterator(iteratorName);
/*     */ 
/*     */     
/*     */     try {
/* 147 */       Constructor<? extends IScriptedIterator> ctor = iteratorClass.getDeclaredConstructor(new Class[] { IScriptActionProvider.class, IMacro.class });
/*     */       
/* 149 */       if (ctor != null)
/*     */       {
/* 151 */         return ctor.newInstance(new Object[] { provider, macro });
/*     */       }
/*     */     }
/* 154 */     catch (Exception exception) {}
/*     */ 
/*     */     
/*     */     try {
/* 158 */       return iteratorClass.newInstance();
/*     */     }
/* 160 */     catch (Exception exception) {
/*     */       
/* 162 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean executeStackPop(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params, IMacroAction popAction) {
/*     */     try {
/* 170 */       IScriptedIterator state = (IScriptedIterator)instance.getState();
/* 171 */       macro.unregisterVariableProvider((IVariableProvider)state);
/*     */     }
/* 173 */     catch (Exception exception) {}
/*     */     
/* 175 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBreak(IMacroActionProcessor processor, IScriptActionProvider provider, IMacro macro, IMacroAction instance, IMacroAction breakAction) {
/* 181 */     IScriptedIterator state = (IScriptedIterator)instance.getState();
/* 182 */     if (state != null) {
/*     */       
/* 184 */       state.breakLoop();
/* 185 */       return true;
/*     */     } 
/*     */     
/* 188 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBePoppedBy(IScriptAction action) {
/* 197 */     return action instanceof ScriptActionNext;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionForEach.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */