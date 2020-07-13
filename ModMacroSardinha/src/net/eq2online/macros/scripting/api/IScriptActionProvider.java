package net.eq2online.macros.scripting.api;

import amj;
import bsu;
import cio;
import java.util.Set;
import net.eq2online.macros.scripting.crafting.AutoCraftingToken;
import net.eq2online.macros.scripting.crafting.IAutoCraftingInitiator;
import net.eq2online.macros.scripting.parser.ScriptContext;
import wv;

public interface IScriptActionProvider {
  void registerVariableProvider(IVariableProvider paramIVariableProvider);
  
  void unregisterVariableProvider(IVariableProvider paramIVariableProvider);
  
  void updateVariableProviders(boolean paramBoolean);
  
  IVariableProvider getProviderForVariable(String paramString);
  
  void registerVariableListener(IVariableListener paramIVariableListener);
  
  void unregisterVariableListener(IVariableListener paramIVariableListener);
  
  IVariableProviderShared getSharedVariableProvider();
  
  Set<String> getEnvironmentVariables();
  
  Object getVariable(String paramString, IVariableProvider paramIVariableProvider);
  
  Object getVariable(String paramString, IMacro paramIMacro);
  
  IExpressionEvaluator getExpressionEvaluator(IMacro paramIMacro, String paramString);
  
  void actionSendChatMessage(IMacro paramIMacro, IMacroAction paramIMacroAction, String paramString);
  
  void actionAddChatMessage(String paramString);
  
  void actionDisconnect();
  
  void actionDisplayGuiScreen(String paramString, ScriptContext paramScriptContext);
  
  void actionDisplayCustomScreen(String paramString1, String paramString2);
  
  void actionBindScreenToSlot(String paramString1, String paramString2);
  
  String actionSwitchConfig(String paramString, boolean paramBoolean);
  
  String actionOverlayConfig(String paramString, boolean paramBoolean1, boolean paramBoolean2);
  
  void actionRenderDistance();
  
  boolean actionInventoryPick(String paramString, int paramInt);
  
  void actionInventorySlot(int paramInt);
  
  void actionInventoryMove(int paramInt);
  
  void actionSetSprinting(boolean paramBoolean);
  
  void actionStopMacros();
  
  void actionStopMacros(IMacro paramIMacro, int paramInt);
  
  boolean getFlagValue(IMacro paramIMacro, String paramString);
  
  void setFlagVariable(IMacro paramIMacro, String paramString, boolean paramBoolean);
  
  void setVariable(IMacro paramIMacro, String paramString1, String paramString2, int paramInt, boolean paramBoolean);
  
  void setVariable(IMacro paramIMacro, String paramString, IReturnValue paramIReturnValue);
  
  void unsetVariable(IMacro paramIMacro, String paramString);
  
  void incrementCounterVariable(IMacro paramIMacro, String paramString, int paramInt);
  
  void pushValueToArray(IMacro paramIMacro, String paramString1, String paramString2);
  
  String popValueFromArray(IMacro paramIMacro, String paramString);
  
  void putValueToArray(IMacro paramIMacro, String paramString1, String paramString2);
  
  void clearArray(IMacro paramIMacro, String paramString);
  
  void deleteArrayElement(IMacro paramIMacro, String paramString, int paramInt);
  
  Object getArrayElement(IMacro paramIMacro, String paramString, int paramInt);
  
  int getArrayIndexOf(IMacro paramIMacro, String paramString1, String paramString2, boolean paramBoolean);
  
  int getArraySize(IMacro paramIMacro, String paramString);
  
  boolean getArrayExists(IMacro paramIMacro, String paramString);
  
  void actionPumpCharacters(String paramString);
  
  void actionPumpKeyPress(int paramInt, boolean paramBoolean);
  
  void actionSelectResourcePacks(String[] paramArrayOfString);
  
  void actionUseItem(bsu parambsu, cio paramcio, amj paramamj, int paramInt);
  
  void actionBindKey(int paramInt1, int paramInt2);
  
  void actionSetEntityDirection(wv paramwv, float paramFloat1, float paramFloat2);
  
  void actionRespawnPlayer();
  
  void actionSetRenderDistance(String paramString);
  
  void onTick();
  
  void actionAddLogMessage(String paramString1, String paramString2);
  
  void actionSetLabel(String paramString1, String paramString2, String paramString3);
  
  AutoCraftingToken actionCraft(IAutoCraftingInitiator paramIAutoCraftingInitiator, cio paramcio, String paramString, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2);
  
  void actionBreakLoop(IMacro paramIMacro, IMacroAction paramIMacroAction);
  
  void actionBeginUnsafeBlock(IMacro paramIMacro, IMacroAction paramIMacroAction, int paramInt);
  
  void actionEndUnsafeBlock(IMacro paramIMacro, IMacroAction paramIMacroAction);
  
  void actionScheduleResChange(int paramInt1, int paramInt2);
  
  String getSoundResourceNamespace();
}


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\api\IScriptActionProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */