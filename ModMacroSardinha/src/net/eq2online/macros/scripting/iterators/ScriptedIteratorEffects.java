/*    */ package net.eq2online.macros.scripting.iterators;
/*    */ 
/*    */ import cio;
/*    */ import fi;
/*    */ import java.util.HashMap;
/*    */ import net.eq2online.macros.compatibility.AbstractionLayer;
/*    */ import net.eq2online.macros.scripting.ScriptedIterator;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import wp;
/*    */ import wq;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScriptedIteratorEffects
/*    */   extends ScriptedIterator
/*    */ {
/*    */   public ScriptedIteratorEffects(IScriptActionProvider provider, IMacro macro) {
/* 20 */     super(provider, macro);
/*    */     
/* 22 */     cio thePlayer = AbstractionLayer.getPlayer();
/* 23 */     if (thePlayer == null)
/*    */       return; 
/* 25 */     for (wq effect : thePlayer.bk()) {
/*    */       
/* 27 */       wp potion = wp.a[effect.a()];
/* 28 */       String potionName = fi.a(potion.a());
/*    */       
/* 30 */       HashMap<String, Object> vars = new HashMap<String, Object>();
/* 31 */       vars.put("EFFECTID", Integer.valueOf(effect.a()));
/* 32 */       vars.put("EFFECT", potionName.toUpperCase().replace(" ", ""));
/*    */       
/* 34 */       if (effect.c() == 1) { potionName = potionName + " II"; }
/* 35 */       else if (effect.c() == 2) { potionName = potionName + " III"; }
/* 36 */       else if (effect.c() == 3) { potionName = potionName + " IV"; }
/* 37 */       else if (effect.c() == 4) { potionName = potionName + " V"; }
/* 38 */       else if (effect.c() == 5) { potionName = potionName + " VI"; }
/*    */       
/* 40 */       vars.put("EFFECTNAME", potionName);
/* 41 */       vars.put("EFFECTPOWER", Integer.valueOf(effect.c() + 1));
/* 42 */       vars.put("EFFECTTIME", Integer.valueOf(effect.b() / 20));
/*    */       
/* 44 */       this.items.add(vars);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\macros\scripting\iterators\ScriptedIteratorEffects.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */