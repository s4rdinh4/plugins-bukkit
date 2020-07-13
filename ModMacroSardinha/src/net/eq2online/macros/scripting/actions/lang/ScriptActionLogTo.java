/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileWriter;
/*    */ import java.io.PrintWriter;
/*    */ import net.eq2online.macros.core.MacroModCore;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ 
/*    */ public class ScriptActionLogTo
/*    */   extends ScriptAction
/*    */ {
/*    */   File logsFolder;
/*    */   
/*    */   public ScriptActionLogTo(ScriptContext context) {
/* 22 */     super(context, "logto");
/*    */   }
/*    */ 
/*    */   
/*    */   private void initLogsFolder() {
/* 27 */     if (this.logsFolder == null) {
/*    */       
/* 29 */       this.logsFolder = new File(MacroModCore.getMacrosDirectory(), "logs");
/*    */ 
/*    */       
/*    */       try {
/* 33 */         if (!this.logsFolder.exists())
/*    */         {
/* 35 */           this.logsFolder.mkdirs();
/*    */         }
/*    */       }
/* 38 */       catch (Exception exception) {}
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 48 */     if (params.length > 1) {
/*    */       
/* 50 */       String targetName = ScriptCore.parseVars(provider, macro, params[0], false);
/*    */       
/* 52 */       if (targetName.toLowerCase().endsWith(".txt")) {
/*    */         
/* 54 */         String fileName = MacroModCore.sanitiseFileName(targetName);
/* 55 */         initLogsFolder();
/*    */ 
/*    */         
/*    */         try {
/* 59 */           if (fileName != null && this.logsFolder != null && this.logsFolder.exists())
/*    */           {
/* 61 */             File logFile = new File(this.logsFolder, fileName);
/*    */             
/* 63 */             PrintWriter printWriter = new PrintWriter(new FileWriter(logFile, true));
/* 64 */             printWriter.println(ScriptCore.parseVars(provider, macro, params[1], false));
/* 65 */             printWriter.close();
/*    */           }
/*    */         
/* 68 */         } catch (Exception exception) {}
/*    */       }
/*    */       else {
/*    */         
/* 72 */         String logMessage = ScriptCore.parseVars(provider, macro, params[1], false).replaceAll("(?<!&)&([0-9a-fklmnor])", "§$1").replaceAll("&&", "&");
/* 73 */         provider.actionAddLogMessage(targetName, logMessage);
/*    */       } 
/*    */     } 
/*    */     
/* 77 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionLogTo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */