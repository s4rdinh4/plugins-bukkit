/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionSetProperty
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionSetProperty(ScriptContext context) {
/* 16 */     this(context, "setproperty");
/*    */   }
/*    */ 
/*    */   
/*    */   protected ScriptActionSetProperty(ScriptContext context, String actionName) {
/* 21 */     super(context, actionName);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 27 */     if (params.length > 2) {
/*    */       
/* 29 */       String controlName = ScriptCore.parseVars(provider, macro, params[0], false);
/*    */ 
/*    */       
/*    */       try {
/* 33 */         DesignableGuiControl control = getControl(controlName);
/*    */         
/* 35 */         if (control != null) {
/*    */           
/* 37 */           String propertyName = ScriptCore.parseVars(provider, macro, params[1], false);
/*    */           
/* 39 */           if (control.hasProperty(propertyName))
/*    */           {
/* 41 */             String propertyValue = ScriptCore.parseVars(provider, macro, params[2], false).replace('§', '&');
/* 42 */             int intValue = ScriptCore.tryParseInt(propertyValue, 0);
/* 43 */             boolean boolValue = (propertyValue.toLowerCase().equals("true") || propertyValue.toLowerCase().equals("yes") || intValue != 0);
/*    */ 
/*    */             
/* 46 */             control.setPropertyWithValidation(propertyName, propertyValue, intValue, boolValue);
/*    */           }
/*    */         
/*    */         } 
/* 50 */       } catch (Exception exception) {}
/*    */     } 
/*    */ 
/*    */     
/* 54 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected DesignableGuiControl getControl(String controlName) {
/* 63 */     DesignableGuiControl control = DesignableGuiControl.getControl(controlName);
/*    */     
/* 65 */     if (control == null && controlName.matches("^[0-9]{1,5}$")) {
/*    */       
/* 67 */       int controlId = Integer.parseInt(controlName);
/* 68 */       return DesignableGuiControl.getControl(controlId);
/*    */     } 
/*    */     
/* 71 */     return control;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionSetProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */