/*    */ package net.eq2online.macros.modules.chatfilter;
/*    */ 
/*    */ import java.io.PrintWriter;
/*    */ import java.util.Map;
/*    */ import net.eq2online.macros.core.Macro;
/*    */ import net.eq2online.macros.core.MacroTemplate;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.scripting.api.IMacroActionContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatFilterTemplate
/*    */   extends MacroTemplate
/*    */ {
/*    */   private ChatFilterManager manager;
/* 20 */   private String config = "";
/*    */ 
/*    */   
/*    */   public ChatFilterTemplate(ChatFilterManager manager, Macros macros, String config) {
/* 24 */     super(macros, 9999);
/*    */     
/* 26 */     this.manager = manager;
/* 27 */     this.config = config;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getConfig() {
/* 32 */     return this.config;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Macro createInstance(boolean checkModifiers, IMacroActionContext context) {
/* 41 */     return new ChatFilterMacro(this, this.id, context);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void saveTemplate(PrintWriter writer) {
/* 50 */     if (this.keyDownMacro.length() > 0) {
/*    */       
/* 52 */       String serialisedKeyDownMacro = this.keyDownMacro.replaceAll("\\x82", Macro.escapeReplacement("<BR />"));
/* 53 */       String serialisedFlags = serialiseFlags();
/* 54 */       String serialisedCounters = serialiseCounters();
/*    */       
/* 56 */       writer.println("# Filter for config [" + this.config + "]");
/*    */       
/* 58 */       writer.println("Filter[" + this.config + "].Script=" + serialisedKeyDownMacro);
/* 59 */       if (this.global) writer.println("Filter[" + this.config + "].Global=" + "1"); 
/* 60 */       if (serialisedFlags.length() > 0) writer.println("Filter[" + this.config + "].Flags=" + serialisedFlags); 
/* 61 */       if (serialisedCounters.length() > 0) writer.println("Filter[" + this.config + "].Counters=" + serialisedCounters);
/*    */       
/* 63 */       for (Map.Entry<String, String> string : (Iterable<Map.Entry<String, String>>)this.strings.entrySet())
/*    */       {
/* 65 */         writer.println("Filter[" + this.config + "].String[" + (String)string.getKey() + "]=" + (String)string.getValue());
/*    */       }
/*    */       
/* 68 */       writer.println();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void loadFrom(String line, String key, String value) {
/* 78 */     if (key.equalsIgnoreCase("Script")) {
/*    */       
/* 80 */       setKeyDownMacro(value.replaceAll("\\<[Bb][Rr] \\/\\>", ""));
/*    */     }
/*    */     else {
/*    */       
/* 84 */       super.loadFrom(line, key, value);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void saveTemplates() {
/* 94 */     this.manager.save();
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\modules\chatfilter\ChatFilterTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */