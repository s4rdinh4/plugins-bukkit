/*     */ package net.eq2online.macros.core.params;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.MacroModSettings;
/*     */ import net.eq2online.macros.core.MacroParamProvider;
/*     */ import net.eq2online.macros.core.MacroParams;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderItem;
/*     */ import net.eq2online.macros.gui.controls.GuiListBox;
/*     */ import net.eq2online.macros.gui.screens.GuiMacroParam;
/*     */ import net.eq2online.macros.gui.shared.GuiControlEx;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*     */ import net.eq2online.macros.scripting.variable.ItemID;
/*     */ import net.eq2online.macros.struct.ItemStackInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MacroParamItem
/*     */   extends MacroParam
/*     */ {
/*     */   public MacroParamItem(MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProvider provider) {
/*  25 */     super(type, target, params, provider);
/*     */     
/*  27 */     this.enableTextField = Boolean.valueOf(true);
/*  28 */     this.maxParameterLength = 128;
/*  29 */     setParameterValue(params.getParameterValueFromStore(provider));
/*     */     
/*  31 */     if (!getParameterValue().matches("^[a-z_]+(:\\d+)?$"))
/*     */     {
/*  33 */       setParameterValue("");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean replace() {
/*  43 */     String macroScript = this.target.getTargetString();
/*     */     
/*  45 */     if (this.target.getIteration() == 1 && this.target.getParamStore() != null) this.target.getParamStore().setStoredParam(this.type, getParameterValue());
/*     */     
/*  47 */     ItemID parsedItemId = new ItemID(getParameterValue());
/*     */     
/*  49 */     if (shouldReplaceFirstOccurrenceOnly()) {
/*     */       
/*  51 */       Matcher firstItem = this.provider.matcher(macroScript);
/*  52 */       if (firstItem.find())
/*     */       {
/*  54 */         if (firstItem.group().contains(":"))
/*     */         {
/*  56 */           firstItem.reset();
/*  57 */           macroScript = firstItem.replaceFirst(parsedItemId.toString());
/*     */         }
/*  59 */         else if (firstItem.group().contains("i") && parsedItemId.isValid())
/*     */         {
/*  61 */           macroScript = MacroParamProviderItem.itemPatternNoDamage.matcher(macroScript).replaceFirst(parsedItemId.identifier);
/*     */         }
/*  63 */         else if (firstItem.group().contains("d") && parsedItemId.isValid())
/*     */         {
/*  65 */           macroScript = MacroParamProviderItem.itemPatternDamageOnly.matcher(macroScript).replaceFirst(parsedItemId.getDamage());
/*     */         }
/*     */       
/*     */       }
/*     */     } else {
/*     */       
/*  71 */       if (parsedItemId.isValid()) {
/*     */         
/*  73 */         macroScript = MacroParamProviderItem.itemPatternNoDamage.matcher(macroScript).replaceAll(parsedItemId.identifier);
/*  74 */         macroScript = MacroParamProviderItem.itemPatternDamageOnly.matcher(macroScript).replaceAll(parsedItemId.getDamage());
/*     */       } 
/*     */       
/*  77 */       macroScript = this.provider.matcher(macroScript).replaceAll(parsedItemId.toString());
/*     */     } 
/*     */     
/*  80 */     this.target.setTargetString(macroScript);
/*  81 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldReplaceFirstOccurrenceOnly() {
/*  90 */     return (this.replaceFirstOccurrenceOnly || MacroModSettings.getCompilerFlagItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFirstOccurrenceSupported() {
/*  99 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getAllowedCharacters() {
/* 108 */     return "0123456789:abcdefghijklmnopqrstuvwxyz_";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPromptMessage() {
/* 117 */     return MacroParam.getLocalisedString("param.prompt.item", new String[] { this.target.getDisplayName() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initListBox(int width, int height) {
/* 126 */     this.itemListBox = MacroModCore.getInstance().getListProvider().getListBox(this.type.toString());
/* 127 */     this.itemListBox.setSizeAndPosition(2, 2, 180, height - 40, GuiListBox.defaultRowHeight, true);
/* 128 */     this.itemListBox.setSelectedItemIndex(0);
/*     */     
/* 130 */     ItemID itemId = new ItemID(getParameterValue());
/* 131 */     if (itemId.isValid()) {
/*     */       
/* 133 */       if (itemId.hasDamage)
/*     */       {
/* 135 */         this.itemListBox.selectIdentifierWithData(itemId.name, Integer.valueOf(itemId.damage));
/*     */       }
/*     */       else
/*     */       {
/* 139 */         this.itemListBox.selectIdentifier(itemId.name);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 144 */       this.itemListBox.selectId(0);
/* 145 */       setParameterValue("");
/*     */     } 
/*     */     
/* 148 */     this.itemListBox.scrollToCentre();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleListBoxClick(GuiMacroParam guiMacroParam) {
/* 157 */     ItemStackInfo selectedItem = (ItemStackInfo)this.itemListBox.getSelectedItem();
/*     */ 
/*     */     
/* 160 */     String newParameterValue = selectedItem.getIdentifier();
/*     */     
/* 162 */     int damageValue = ((Integer)selectedItem.getData()).intValue();
/* 163 */     if (damageValue > 0)
/*     */     {
/* 165 */       newParameterValue = newParameterValue + ":" + damageValue;
/*     */     }
/*     */     
/* 168 */     setParameterValue(newParameterValue);
/*     */ 
/*     */     
/* 171 */     if (this.itemListBox.isDoubleClicked(true))
/*     */     {
/* 173 */       guiMacroParam.apply();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiControlEx.KeyHandledState keyTyped(char keyChar, int keyCode) {
/* 181 */     if (this.itemListBox != null)
/*     */     {
/* 183 */       return this.itemListBox.listBoxKeyTyped(keyChar, keyCode);
/*     */     }
/*     */     
/* 186 */     return GuiControlEx.KeyHandledState.None;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void autoPopulate() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTextFieldTextChanged() {
/* 200 */     if (this.itemListBox != null) {
/*     */       
/* 202 */       ItemID itemId = new ItemID(getParameterValue());
/* 203 */       if (itemId.isValid())
/*     */       {
/* 205 */         if (itemId.hasDamage) {
/*     */           
/* 207 */           this.itemListBox.selectIdentifierWithData(itemId.name, Integer.valueOf(itemId.damage));
/*     */         }
/*     */         else {
/*     */           
/* 211 */           this.itemListBox.selectIdentifier(itemId.name);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\params\MacroParamItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */