/*    */ package net.eq2online.macros.scripting.actions.option;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionResourcePacks
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionResourcePacks(ScriptContext context) {
/* 15 */     super(context, "resourcepacks");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 21 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 27 */     String[] resourcePacks = new String[params.length];
/* 28 */     for (int index = 0; index < params.length; index++)
/*    */     {
/* 30 */       resourcePacks[index] = ScriptCore.parseVars(provider, macro, params[index], false);
/*    */     }
/*    */     
/* 33 */     provider.actionSelectResourcePacks(resourcePacks);
/*    */     
/* 35 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\option\ScriptActionResourcePacks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */