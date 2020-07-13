/*     */ package net.eq2online.macros.scripting.variable;
/*     */ 
/*     */ import bsu;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import java.util.regex.Matcher;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.MacroType;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.scripting.Variable;
/*     */ import net.eq2online.macros.scripting.api.IArrayProvider;
/*     */ import net.eq2online.macros.scripting.api.ICounterProvider;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IReturnValue;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.ITickableVariableProvider;
/*     */ import net.eq2online.macros.scripting.api.IVariableListener;
/*     */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*     */ import net.eq2online.macros.scripting.api.IVariableProviderShared;
/*     */ import uw;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class VariableManager
/*     */   implements IScriptActionProvider
/*     */ {
/*     */   protected IVariableProviderShared sharedVariableProvider;
/*  36 */   private List<IVariableProvider> variableProviders = new ArrayList<IVariableProvider>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  41 */   private List<IVariableListener> variableListeners = new ArrayList<IVariableListener>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  46 */   private List<IArrayProvider> arrayProviders = new ArrayList<IArrayProvider>();
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTick() {
/*  51 */     for (IVariableProvider variableProvider : this.variableProviders) {
/*     */       
/*  53 */       if (variableProvider instanceof ITickableVariableProvider) {
/*  54 */         ((ITickableVariableProvider)variableProvider).onTick();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IVariableProviderShared getSharedVariableProvider() {
/*  64 */     return this.sharedVariableProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerVariableProvider(IVariableProvider variableProvider) {
/*  73 */     if (!this.variableProviders.contains(variableProvider)) {
/*     */       
/*  75 */       this.variableProviders.add(variableProvider);
/*     */       
/*  77 */       if (variableProvider instanceof IArrayProvider && variableProvider != this.sharedVariableProvider)
/*     */       {
/*  79 */         this.arrayProviders.add((IArrayProvider)variableProvider);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregisterVariableProvider(IVariableProvider variableProvider) {
/*  90 */     this.variableProviders.remove(variableProvider);
/*  91 */     this.arrayProviders.remove(variableProvider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateVariableProviders(boolean clock) {
/* 100 */     uw mcProfiler = (bsu.z()).y;
/* 101 */     IVariableProvider faultingProvider = null;
/*     */     
/* 103 */     mcProfiler.a("VariableManager");
/*     */ 
/*     */     
/*     */     try {
/* 107 */       for (IVariableProvider variableProvider : this.variableProviders)
/*     */       {
/* 109 */         faultingProvider = variableProvider;
/* 110 */         mcProfiler.c(variableProvider.getClass().getSimpleName());
/* 111 */         variableProvider.updateVariables(clock);
/*     */       }
/*     */     
/* 114 */     } catch (Exception ex) {
/*     */       
/* 116 */       Log.printStackTrace(ex);
/*     */       
/* 118 */       if (faultingProvider != null) {
/*     */         
/* 120 */         this.variableProviders.remove(faultingProvider);
/* 121 */         actionAddChatMessage("§4[MACROS] Critical error, removing variable provider " + faultingProvider.getClass().getSimpleName());
/* 122 */         actionAddChatMessage("§4[MACROS] Error was: " + ex.getMessage());
/*     */       } 
/*     */     } 
/*     */     
/* 126 */     mcProfiler.b();
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
/*     */   public IVariableProvider getProviderForVariable(String variableName) {
/* 138 */     updateVariableProviders(false);
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
/* 150 */     for (IVariableProvider variableProvider : this.variableProviders) {
/*     */       
/* 152 */       if (variableProvider.getVariable(variableName) != null) {
/* 153 */         return variableProvider;
/*     */       }
/*     */     } 
/* 156 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerVariableListener(IVariableListener variableListener) {
/* 165 */     if (!this.variableListeners.contains(variableListener))
/*     */     {
/* 167 */       this.variableListeners.add(variableListener);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregisterVariableListener(IVariableListener variableListener) {
/* 177 */     this.variableListeners.remove(variableListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getEnvironmentVariables() {
/* 184 */     Set<String> env = new TreeSet<String>();
/*     */     
/* 186 */     env.add("CONFIG");
/* 187 */     env.add("KEYID");
/* 188 */     env.add("KEYNAME");
/*     */     
/* 190 */     for (IVariableProvider variableProvider : this.variableProviders) {
/* 191 */       env.addAll(variableProvider.getVariables());
/*     */     }
/* 193 */     return env;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getVariable(String variableName, IVariableProvider inContextProvider) {
/* 202 */     IVariableProvider faultingProvider = null;
/*     */ 
/*     */     
/*     */     try {
/* 206 */       if (inContextProvider != null)
/*     */       {
/* 208 */         Object variableValue = inContextProvider.getVariable(variableName);
/* 209 */         if (variableValue != null)
/*     */         {
/* 211 */           return variableValue;
/*     */         }
/*     */       }
/*     */     
/* 215 */     } catch (Exception ex) {
/*     */       
/* 217 */       Log.printStackTrace(ex);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 222 */       for (IVariableProvider variableProvider : this.variableProviders)
/*     */       {
/* 224 */         faultingProvider = variableProvider;
/*     */         
/* 226 */         Object variableValue = variableProvider.getVariable(variableName);
/* 227 */         if (variableValue != null)
/*     */         {
/* 229 */           return variableValue;
/*     */         }
/*     */       }
/*     */     
/* 233 */     } catch (Exception ex) {
/*     */       
/* 235 */       Log.printStackTrace(ex);
/*     */       
/* 237 */       if (faultingProvider != null) {
/*     */         
/* 239 */         this.variableProviders.remove(faultingProvider);
/* 240 */         actionAddChatMessage("§4[MACROS] Critical error, removing variable provider " + faultingProvider.getClass().getSimpleName());
/* 241 */         actionAddChatMessage("§4[MACROS] Error was: " + ex.getMessage());
/*     */       } 
/*     */     } 
/*     */     
/* 245 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getVariable(String variableName, IMacro macro) {
/* 254 */     if (macro != null) {
/*     */       
/* 256 */       if ("KEYID".equals(variableName)) return String.valueOf(macro.getID()); 
/* 257 */       if ("KEYNAME".equals(variableName)) return MacroType.getMacroName(macro.getID());
/*     */     
/*     */     } 
/* 260 */     Macros macros = MacroModCore.getMacroManager();
/*     */     
/* 262 */     if (macros != null)
/*     */     {
/* 264 */       if ("CONFIG".equals(variableName)) return macros.getActiveConfigName();
/*     */     
/*     */     }
/* 267 */     return getVariable(variableName, (macro != null) ? (IVariableProvider)macro : (IVariableProvider)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getFlagValue(IMacro macro, String flagName) {
/* 276 */     Variable var = Variable.getVariable((IVariableProvider)getSharedVariableProvider(), this.arrayProviders, macro, flagName);
/*     */     
/* 278 */     if (var != null) {
/*     */       String stringValue;
/* 280 */       switch (var.type) {
/*     */         
/*     */         case Flag:
/* 283 */           return var.getFlag();
/*     */         
/*     */         case Counter:
/* 286 */           return (var.getCounter() == 1);
/*     */         
/*     */         case String:
/* 289 */           stringValue = var.getString();
/* 290 */           return (stringValue.equals("1") || stringValue.equalsIgnoreCase("true"));
/*     */       } 
/*     */     
/*     */     } 
/* 294 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFlagVariable(IMacro macro, String flagName, boolean value) {
/* 303 */     Variable var = Variable.getVariable((IVariableProvider)getSharedVariableProvider(), this.arrayProviders, macro, flagName);
/* 304 */     if (var != null) {
/*     */       
/* 306 */       macro.markDirty();
/*     */       
/* 308 */       switch (var.type) {
/*     */         
/*     */         case Flag:
/* 311 */           var.setFlag(value);
/*     */           break;
/*     */         
/*     */         case Counter:
/* 315 */           var.setCounter(value ? 1 : 0);
/*     */           break;
/*     */         
/*     */         case String:
/* 319 */           var.setString(value ? "true" : "false");
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVariable(IMacro macro, String variableName, String variableValue, int intValue, boolean boolValue) {
/* 331 */     Variable var = Variable.getVariable((IVariableProvider)getSharedVariableProvider(), this.arrayProviders, macro, variableName);
/* 332 */     if (var != null) {
/*     */       
/* 334 */       macro.markDirty();
/*     */       
/* 336 */       switch (var.type) {
/*     */         
/*     */         case Flag:
/* 339 */           var.setFlag(boolValue);
/*     */           break;
/*     */         
/*     */         case Counter:
/* 343 */           var.setCounter(intValue);
/*     */           break;
/*     */         
/*     */         case String:
/* 347 */           var.setString(variableValue);
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVariable(IMacro macro, String variableName, IReturnValue returnValue) {
/* 359 */     setVariable(macro, variableName, returnValue.getString(), returnValue.getInteger(), returnValue.getBoolean());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unsetVariable(IMacro macro, String variableName) {
/* 368 */     if (Variable.couldBeArraySpecifier(variableName)) {
/*     */       
/* 370 */       String arrayName = Variable.getValidVariableOrArraySpecifier(variableName);
/*     */       
/* 372 */       if (arrayName != null) {
/*     */         
/* 374 */         clearArray(macro, arrayName);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 379 */     Variable var = Variable.getVariable((IVariableProvider)getSharedVariableProvider(), this.arrayProviders, macro, variableName);
/* 380 */     if (var != null) {
/*     */       
/* 382 */       macro.markDirty();
/* 383 */       switch (var.type) {
/*     */         
/*     */         case Flag:
/* 386 */           var.unSetFlag();
/*     */           break;
/*     */         
/*     */         case Counter:
/* 390 */           var.unSetCounter();
/*     */           break;
/*     */         
/*     */         case String:
/* 394 */           var.unSetString();
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void incrementCounterVariable(IMacro macro, String counterName, int increment) {
/* 406 */     Variable var = Variable.getVariable((IVariableProvider)getSharedVariableProvider(), this.arrayProviders, macro, counterName);
/* 407 */     if (var != null && var.type == Variable.Type.Counter) {
/*     */       
/* 409 */       macro.markDirty();
/* 410 */       ICounterProvider counterProvider = var.getCounterProvider();
/* 411 */       if (counterProvider != null) counterProvider.incrementCounter(var.variableName, var.arrayOffset, increment);
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pushValueToArray(IMacro macro, String arrayName, String variableValue) {
/* 421 */     Variable var = Variable.getArrayVariable((IVariableProvider)getSharedVariableProvider(), this.arrayProviders, macro, arrayName);
/* 422 */     if (var != null) {
/*     */       
/* 424 */       if (!var.isShared) macro.markDirty(); 
/* 425 */       var.arrayPush(variableValue);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putValueToArray(IMacro macro, String arrayName, String variableValue) {
/* 435 */     Variable var = Variable.getArrayVariable((IVariableProvider)getSharedVariableProvider(), this.arrayProviders, macro, arrayName);
/* 436 */     if (var != null) {
/*     */       
/* 438 */       if (!var.isShared) macro.markDirty(); 
/* 439 */       var.arrayPut(variableValue);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String popValueFromArray(IMacro macro, String arrayName) {
/* 449 */     Variable var = Variable.getArrayVariable((IVariableProvider)getSharedVariableProvider(), this.arrayProviders, macro, arrayName);
/* 450 */     if (var != null) {
/*     */       
/* 452 */       if (!var.isShared) macro.markDirty(); 
/* 453 */       return var.arrayPop();
/*     */     } 
/*     */     
/* 456 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getArrayIndexOf(IMacro macro, String arrayName, String search, boolean caseSensitive) {
/* 465 */     Variable var = Variable.getArrayVariable((IVariableProvider)getSharedVariableProvider(), this.arrayProviders, macro, arrayName);
/* 466 */     if (var != null)
/*     */     {
/* 468 */       return var.arrayIndexOf(search, caseSensitive);
/*     */     }
/*     */     
/* 471 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearArray(IMacro macro, String arrayName) {
/* 480 */     Variable var = Variable.getArrayVariable((IVariableProvider)getSharedVariableProvider(), this.arrayProviders, macro, arrayName);
/* 481 */     if (var != null) {
/*     */       
/* 483 */       if (!var.isShared) macro.markDirty(); 
/* 484 */       var.arrayClear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getArrayElement(IMacro macro, String arrayName, int offset) {
/* 494 */     Variable var = Variable.getArrayVariable((IVariableProvider)getSharedVariableProvider(), this.arrayProviders, macro, arrayName);
/* 495 */     if (var != null)
/*     */     {
/* 497 */       return var.arrayGetValue(offset);
/*     */     }
/*     */     
/* 500 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getArraySize(IMacro macro, String arrayName) {
/* 509 */     Variable var = Variable.getArrayVariable((IVariableProvider)getSharedVariableProvider(), this.arrayProviders, macro, arrayName);
/* 510 */     if (var != null)
/*     */     {
/* 512 */       return var.arrayGetMaxIndex() + 1;
/*     */     }
/*     */     
/* 515 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getArrayExists(IMacro macro, String arrayName) {
/* 524 */     Variable var = Variable.getArrayVariable((IVariableProvider)getSharedVariableProvider(), this.arrayProviders, macro, arrayName);
/* 525 */     if (var != null)
/*     */     {
/* 527 */       return var.arrayExists();
/*     */     }
/*     */     
/* 530 */     return false;
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
/*     */   protected String sanitiseArrayVariableName(IMacro macro, String arrayName) {
/* 542 */     if (Variable.isValidVariableName(arrayName)) {
/*     */       
/* 544 */       Matcher arrayIndexMatcher = Variable.arrayVariablePattern.matcher(arrayName);
/* 545 */       return arrayIndexMatcher.find() ? arrayName.substring(0, arrayName.indexOf('[')) : arrayName;
/*     */     } 
/*     */     
/* 548 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSoundResourceNamespace() {
/* 554 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\variable\VariableManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */