/*     */ package net.eq2online.macros.core.params;
/*     */ 
/*     */ import bsu;
/*     */ import java.util.List;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.MacroParamProvider;
/*     */ import net.eq2online.macros.core.MacroParams;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderList;
/*     */ import net.eq2online.macros.gui.controls.GuiListBox;
/*     */ import net.eq2online.macros.gui.controls.GuiListBoxIconic;
/*     */ import net.eq2online.macros.gui.list.ListObjectGeneric;
/*     */ import net.eq2online.macros.gui.list.ListObjectOnlineUser;
/*     */ import net.eq2online.macros.gui.screens.GuiMacroParam;
/*     */ import net.eq2online.macros.gui.shared.GuiControlEx;
/*     */ import net.eq2online.macros.interfaces.IListObject;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*     */ import net.eq2online.macros.scripting.variable.ItemID;
/*     */ import net.eq2online.macros.struct.ItemStackInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MacroParamList
/*     */   extends MacroParamStandard
/*     */ {
/*     */   protected int listPos;
/*     */   protected int listEnd;
/*     */   protected String listName;
/*     */   protected MacroParam.Type listType;
/*     */   protected String[] listOptions;
/*     */   protected boolean allowArrowKeys;
/*     */   private MacroParamProviderList listProvider;
/*     */   
/*     */   public MacroParamList(MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProviderList provider) {
/*  39 */     super(type, target, params, (MacroParamProvider)provider);
/*     */     
/*  41 */     this.listProvider = provider;
/*     */     
/*  43 */     this.listName = this.listProvider.getNextListName();
/*  44 */     this.listType = this.listProvider.getNextListType();
/*  45 */     this.listOptions = this.listProvider.getNextListOptions();
/*  46 */     this.listPos = this.listProvider.getNextListPos();
/*  47 */     this.listEnd = this.listProvider.getNextListEnd();
/*     */     
/*  49 */     setParameterValue(params.getParameterValueFromStore((MacroParamProvider)provider));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean replace() {
/*  58 */     if (this.listPos > -1 && this.listEnd > 0) {
/*     */       
/*  60 */       String macroScript = this.target.getTargetString();
/*     */       
/*  62 */       macroScript = macroScript.substring(0, this.listPos) + getParameterValue() + macroScript.substring(this.listEnd);
/*     */       
/*  64 */       if (this.listName.length() > 0 && !this.listName.equalsIgnoreCase("choice")) {
/*     */         
/*  66 */         macroScript = macroScript.replaceAll("\\$\\$\\[" + this.listName + "\\]", getParameterValueEscaped());
/*  67 */         this.params.removeNamedVar(this.listName);
/*     */       } 
/*     */       
/*  70 */       this.target.setTargetString(macroScript);
/*     */       
/*  72 */       this.listProvider.clearNextList();
/*  73 */       this.target.compile();
/*     */     } 
/*     */     
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPromptMessage() {
/*  85 */     return MacroParam.getLocalisedString("param.prompt.list", new String[] { this.target.getDisplayName() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPromptPrefix() {
/*  94 */     return this.listName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initListBox(int width, int height) {
/* 103 */     this.itemListBox = createListBox(this.listType, this.listOptions);
/*     */     
/* 105 */     if (this.itemListBox != null) {
/*     */       
/* 107 */       this.itemListBox.setSizeAndPosition(2, 2, 180, height - 40, GuiListBox.defaultRowHeight, true);
/*     */       
/* 109 */       IListObject selected = this.itemListBox.getSelectedItem();
/* 110 */       if (selected != null) {
/*     */         
/* 112 */         setParameterValue(selected.getText());
/*     */         
/* 114 */         if (this.listType == MacroParam.Type.Item)
/*     */         {
/* 116 */           setParameterValue(String.valueOf(selected.getId()));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiListBox createListBox(MacroParam.Type listType, String[] listOptions) {
/* 124 */     bsu minecraft = bsu.z();
/*     */     
/* 126 */     if (listType == MacroParam.Type.Normal) {
/*     */       
/* 128 */       GuiListBox listBox = new GuiListBox(minecraft, 2, 2, 2, 180, 180, GuiListBox.defaultRowHeight, false, false, false);
/* 129 */       int id = 0;
/*     */       
/* 131 */       for (String option : listOptions) {
/* 132 */         listBox.addItem((IListObject)new ListObjectGeneric(id++, option));
/*     */       }
/* 134 */       return listBox;
/*     */     } 
/* 136 */     if (listType == MacroParam.Type.Item) {
/*     */       
/* 138 */       GuiListBoxIconic listBox = new GuiListBoxIconic(minecraft, 2);
/* 139 */       this.allowArrowKeys = true;
/*     */       
/* 141 */       for (String option : listOptions) {
/*     */         
/* 143 */         ItemID itemId = new ItemID(option);
/* 144 */         if (itemId.isValid()) {
/*     */           
/* 146 */           List<ItemStackInfo> items = MacroModCore.getInstance().getListProvider().getInfoForItem(itemId);
/*     */           
/* 148 */           if (items != null)
/*     */           {
/* 150 */             for (ItemStackInfo item : items) {
/*     */               
/* 152 */               if (!itemId.hasDamage || item.getDamage() == itemId.damage) {
/* 153 */                 listBox.addItem((IListObject)item);
/*     */               }
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/* 159 */       return (GuiListBox)listBox;
/*     */     } 
/* 161 */     if (listType == MacroParam.Type.OnlineUser) {
/*     */       
/* 163 */       GuiListBox listBox = new GuiListBox(minecraft, 2, 2, 2, 180, 180, 20, false, false, false);
/* 164 */       int playerId = 0;
/*     */       
/* 166 */       for (String option : listOptions) {
/* 167 */         listBox.addItem((IListObject)new ListObjectOnlineUser(playerId++, option));
/*     */       }
/* 169 */       return listBox;
/*     */     } 
/*     */     
/* 172 */     return new GuiListBox(minecraft, 2, 2, 2, 180, 180, 20, false, false, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiControlEx.KeyHandledState keyTyped(char keyChar, int keyCode) {
/* 178 */     if (this.allowArrowKeys) {
/*     */ 
/*     */       
/* 181 */       if (this.itemListBox != null)
/*     */       {
/* 183 */         return this.itemListBox.listBoxKeyTyped(keyChar, keyCode);
/*     */       }
/*     */       
/* 186 */       return GuiControlEx.KeyHandledState.None;
/*     */     } 
/*     */     
/* 189 */     return super.keyTyped(keyChar, keyCode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleListBoxClick(GuiMacroParam guiMacroParam) {
/* 198 */     if (this.listProvider.getNextListType() == MacroParam.Type.Item) {
/*     */       
/* 200 */       setParameterValue(String.valueOf(this.itemListBox.getSelectedItem().getId()));
/*     */     }
/*     */     else {
/*     */       
/* 204 */       setParameterValue(this.itemListBox.getSelectedItem().getText());
/*     */     } 
/*     */ 
/*     */     
/* 208 */     if (this.itemListBox.isDoubleClicked(true))
/*     */     {
/* 210 */       guiMacroParam.apply();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\core\params\MacroParamList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */