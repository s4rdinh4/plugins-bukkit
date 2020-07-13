/*    */ package net.eq2online.macros.scripting.actions.option;
/*    */ 
/*    */ import cxz;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionVolume
/*    */   extends ScriptActionGamma<cxz>
/*    */ {
/*    */   public ScriptActionVolume(ScriptContext context) {
/* 14 */     super(context, "volume", cxz.a, 0.0F, 100.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected cxz getOption(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String[] params) {
/* 20 */     if (params.length > 1) {
/*    */       
/* 22 */       String categoryName = ScriptCore.parseVars(provider, macro, params[1], false).toLowerCase().trim();
/* 23 */       cxz category = cxz.a(categoryName);
/* 24 */       if (category != null) return category;
/*    */     
/*    */     } 
/* 27 */     return super.getOption(provider, macro, instance, params);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\option\ScriptActionVolume.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */