/*    */ package net.eq2online.macros.scripting.iterators;
/*    */ 
/*    */ import ces;
/*    */ import cio;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*    */ import net.eq2online.macros.scripting.ScriptedIterator;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScriptedIteratorPlayers
/*    */   extends ScriptedIterator
/*    */ {
/*    */   public ScriptedIteratorPlayers(IScriptActionProvider provider, IMacro macro) {
/* 18 */     super(provider, macro);
/*    */ 
/*    */     
/* 21 */     cio thePlayer = AbstractionLayer.getPlayer();
/* 22 */     Collection<ces> playerList = thePlayer.a.d();
/*    */     
/* 24 */     for (ces playerEntry : playerList) {
/*    */       
/* 26 */       HashMap<String, Object> vars = new HashMap<String, Object>();
/* 27 */       vars.put("PLAYERNAME", playerEntry.a().getName());
/*    */       
/* 29 */       this.items.add(vars);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\iterators\ScriptedIteratorPlayers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */