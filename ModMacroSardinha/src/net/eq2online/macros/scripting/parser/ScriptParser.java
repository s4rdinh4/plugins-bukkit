/*    */ package net.eq2online.macros.scripting.parser;
/*    */ 
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import net.eq2online.macros.core.Macro;
/*    */ import net.eq2online.macros.scripting.ActionParser;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IMacroActionProcessor;
/*    */ import net.eq2online.macros.scripting.api.IScriptParser;
/*    */ 
/*    */ 
/*    */ public class ScriptParser
/*    */   implements IScriptParser
/*    */ {
/*    */   private final ScriptContext context;
/* 16 */   private List<ActionParser> parsers = new LinkedList<ActionParser>();
/*    */ 
/*    */   
/*    */   public ScriptParser(ScriptContext context) {
/* 20 */     this.context = context;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addActionParser(ActionParser parser) {
/* 29 */     this.parsers.add(parser);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<IMacroAction> parseScript(IMacroActionProcessor actionProcessor, String script) {
/* 38 */     List<IMacroAction> actions = new LinkedList<IMacroAction>();
/*    */     
/* 40 */     String[] scriptEntries = script.replace('', ';').split(";");
/* 41 */     for (String scriptEntry : scriptEntries) {
/*    */       
/* 43 */       scriptEntry = scriptEntry.replaceAll(Macro.pipeReplacement, "\\\\|").trim();
/*    */       
/* 45 */       if (!scriptEntry.startsWith("//")) {
/*    */         
/* 47 */         IMacroAction action = null;
/*    */         
/* 49 */         for (ActionParser parser : this.parsers) {
/*    */           
/* 51 */           action = parser.parse(actionProcessor, scriptEntry);
/* 52 */           if (action != null) {
/*    */             
/* 54 */             actions.add(action);
/*    */             
/*    */             break;
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/* 61 */     return actions;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ScriptContext getContext() {
/* 67 */     return this.context;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\parser\ScriptParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */