/*    */ package net.eq2online.macros.scripting.actions;
/*    */ 
/*    */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*    */ import net.eq2online.macros.core.MacroModCore;
/*    */ import net.eq2online.macros.core.params.MacroParam;
/*    */ import net.eq2online.macros.gui.controls.GuiListBox;
/*    */ import net.eq2online.macros.gui.controls.specialised.GuiListBoxPlaces;
/*    */ import net.eq2online.macros.scripting.ScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.struct.Place;
/*    */ import uv;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScriptActionStore
/*    */   extends ScriptAction
/*    */ {
/*    */   protected boolean overwrite = false;
/*    */   
/*    */   public ScriptActionStore(ScriptContext context) {
/* 30 */     super(context, "store");
/*    */   }
/*    */ 
/*    */   
/*    */   protected ScriptActionStore(ScriptContext context, String actionName) {
/* 35 */     super(context, actionName);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 44 */     doStore((ScriptActionProvider)provider, params, this.overwrite);
/*    */     
/* 46 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   private void doStore(ScriptActionProvider scriptActionProvider, String[] params, boolean replace) {
/* 51 */     if (params.length > 0)
/*    */     {
/* 53 */       if (params[0].equalsIgnoreCase("place")) {
/*    */         
/* 55 */         String placeName = (params.length > 1) ? params[1] : "Saved place";
/* 56 */         Place newPlace = Place.parsePlace(placeName, String.valueOf(uv.c((AbstractionLayer.getPlayer()).s)), String.valueOf(uv.c((AbstractionLayer.getPlayer()).t)), String.valueOf(uv.c((AbstractionLayer.getPlayer()).u)), true);
/* 57 */         GuiListBoxPlaces placesList = (GuiListBoxPlaces)MacroModCore.getInstance().getListProvider().getListBox(MacroParam.Type.Place.toString());
/*    */         
/* 59 */         if (replace && Place.exists(placeName)) {
/*    */           
/* 61 */           Place oldPlace = Place.getByName(placeName);
/*    */ 
/*    */           
/* 64 */           oldPlace.x = newPlace.x;
/* 65 */           oldPlace.y = newPlace.y;
/* 66 */           oldPlace.z = newPlace.z;
/*    */           
/* 68 */           placesList.save();
/*    */         }
/*    */         else {
/*    */           
/* 72 */           scriptActionProvider.actionAddItemToList((GuiListBox)placesList, MacroParam.Type.Place, newPlace.name, newPlace.name, -1, newPlace);
/*    */         } 
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\ScriptActionStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */