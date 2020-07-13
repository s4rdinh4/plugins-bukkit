/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionGetProperty
/*    */   extends ScriptActionSetProperty
/*    */ {
/*    */   public ScriptActionGetProperty(ScriptContext context) {
/* 16 */     super(context, "getproperty");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 22 */     ReturnValue retVal = new ReturnValue("");
/*    */     
/* 24 */     if (params.length > 1) {
/*    */       
/* 26 */       String controlName = ScriptCore.parseVars(provider, macro, params[0], false);
/*    */ 
/*    */       
/*    */       try {
/* 30 */         DesignableGuiControl control = getControl(controlName);
/*    */         
/* 32 */         if (control != null)
/*    */         {
/* 34 */           String propertyName = ScriptCore.parseVars(provider, macro, params[1], false);
/*    */           
/* 36 */           if (control.hasProperty(propertyName))
/*    */           {
/* 38 */             retVal.setString(control.getProperty(propertyName, ""));
/*    */           }
/*    */         }
/*    */       
/* 42 */       } catch (Exception exception) {}
/*    */     } 
/*    */ 
/*    */     
/* 46 */     return (IReturnValue)retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionGetProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */